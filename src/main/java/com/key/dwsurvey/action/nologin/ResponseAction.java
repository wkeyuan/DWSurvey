package com.key.dwsurvey.action.nologin;

import com.key.common.QuType;
import com.key.common.base.action.CrudActionSupport;
import com.key.common.base.entity.User;
import com.key.common.base.service.AccountManager;
import com.key.common.plugs.ipaddr.IPService;
import com.key.common.utils.CookieUtils;
import com.key.common.utils.HttpRequestDeviceUtils;
import com.key.common.utils.NumberUtils;
import com.key.common.utils.web.Struts2Utils;
import com.key.dwsurvey.entity.*;
import com.key.dwsurvey.service.SurveyAnswerManager;
import com.key.dwsurvey.service.SurveyDirectoryManager;
import com.octo.captcha.service.image.ImageCaptchaService;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.convention.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * 答卷 action
 * @author KeYuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 *
 */

@Namespaces({ @Namespace("/") })
@InterceptorRefs({ @InterceptorRef(value = "paramsPrepareParamsStack") })
@Results({
		@Result(name = ResponseAction.RESULT_FREQUENCY, location = "/WEB-INF/page/surveydir/survey/stats/response-frequency.jsp", type = Struts2Utils.DISPATCHER),
		@Result(name = CrudActionSupport.INPUT, location = "/WEB-INF/page/surveydir/survey/response/response-survey.jsp", type = Struts2Utils.DISPATCHER),
		@Result(name = ResponseAction.INPUT_IFRAME, location = "/WEB-INF/page/surveydir/survey/response/response-answer-iframe.jsp", type = Struts2Utils.DISPATCHER),

		@Result(name = ResponseAction.ANSWER_SUCCESS, location = "/WEB-INF/page/content/diaowen-answer/response-success.jsp", type = Struts2Utils.DISPATCHER),
		@Result(name = ResponseAction.ANSWER_FAILURE, location = "/WEB-INF/page/content/diaowen-answer/response-failure.jsp", type = Struts2Utils.DISPATCHER),

		@Result(name = ResponseAction.ANSWER_SUCCESS_M, location = "/WEB-INF/page/content/diaowen-answer/response-success-m.jsp", type = Struts2Utils.DISPATCHER),

		@Result(name = ResponseAction.ANSWER_INPUT_RULE, location = "/WEB-INF/page/content/diaowen-answer/response-input-rule.jsp", type = Struts2Utils.DISPATCHER),
		@Result(name = ResponseAction.ANSWER_ERROR, location = "/WEB-INF/page/content/diaowen-answer/response-input-error.jsp", type = Struts2Utils.DISPATCHER),
		@Result(name = ResponseAction.ANSWER_ERROR_M, location = "/WEB-INF/page/content/diaowen-answer/response-input-error-m.jsp", type = Struts2Utils.DISPATCHER),
		@Result(name = ResponseAction.ANSWER_CODE_ERROR, location = "/wenjuan/${sid}.html?errorcode=3", type = Struts2Utils.REDIRECT),
		@Result(name = ResponseAction.ANSWER_CODE_ERROR_M, location = "/survey!answerSurveryMobile.action?surveyId=${surveyId}&errorcode=3", type = Struts2Utils.REDIRECT),
		@Result(name = ResponseAction.RELOAD_ANSWER_SUCCESS, location = "response!answerSuccess.action?sid=${sid}", type = Struts2Utils.REDIRECT),
		@Result(name = ResponseAction.RELOAD_ANSWER_FAILURE, location = "response!answerFailure.action?surveyId=${surveyId}", type = Struts2Utils.REDIRECT),
		@Result(name = ResponseAction.RELOAD_ANSER_ERROR, location = "response!answerError.action?surveyId=${surveyId}", type = Struts2Utils.REDIRECT),
		@Result(name = ResponseAction.RELOAD_ANSER_ERROR_M, location = "response!answerErrorM.action?surveyId=${surveyId}", type = Struts2Utils.REDIRECT),

		@Result(name = ResponseAction.RESPONSE_MSG, location = "/WEB-INF/page/content/diaowen-answer/response-msg.jsp", type = Struts2Utils.DISPATCHER),

		@Result(name = ResponseAction.RELOAD_ANSWER_SUCCESS_M, location = "response!answerSuccessM.action?surveyId=${surveyId}", type = Struts2Utils.REDIRECT),
		@Result(name = ResponseAction.RESPONSE_MOBILE, location = "response!answerMobile.action?surveyId=${surveyId}", type = Struts2Utils.REDIRECT) })

@AllowedMethods({"saveMobile","answerSuccess","answerMobile","answerFailure","answerError","answerSuccessM","ajaxCheckSurvey"})
public class ResponseAction extends ActionSupport {
	private static final long serialVersionUID = -2289729314160067840L;

	protected static final String RESULT_FREQUENCY = "resultFrequency";
	protected final static String INPUT_IFRAME = "input_iframe";
	protected final static String ANSWER_SUCCESS = "answerSuccess";
	protected final static String ANSWER_FAILURE = "answerFailure";
	protected final static String ANSWER_ERROR = "answerError";
	protected final static String ANSWER_ERROR_M = "answerErrorM";

	protected final static String ANSWER_SUCCESS_M = "answerSuccessM";

	protected final static String RELOAD_ANSWER_SUCCESS = "reloadAnswerSuccess";
	protected final static String RELOAD_ANSWER_FAILURE = "reloadAnswerFailure";
	protected final static String RELOAD_ANSER_ERROR = "reloadAnserError";// 已经答过，在间隔时间内
	protected final static String RELOAD_ANSER_ERROR_M = "reloadAnserErrorM";// 已经答过，在间隔时间内

	protected final static String ANSWER_CODE_ERROR = "answerCodeError";// 验证码不正确
	protected final static String ANSWER_CODE_ERROR_M = "answerCodeErrorM";// 验证码不正确
	protected final static String ANSWER_INPUT_RULE = "answer_input_rule";// 令牌

	protected final static String RELOAD_ANSWER_SUCCESS_M = "reloadAnswerSuccessM";//

	protected final static String SURVEY_RESULT = "surveyResult";

	protected final static String RESPONSE_MSG = "responseMsg";

	protected final static String RESPONSE_MOBILE = "responseMobile";

	private String sid;
	private String surveyId;
	@Autowired
	private SurveyAnswerManager surveyAnswerManager;
	@Autowired
	private SurveyDirectoryManager directoryManager;
	@Autowired
	private IPService ipService;
	@Autowired
	private AccountManager accountManager;
	// @Autowired
	// private GenericManageableCaptchaService captchaService;
	@Autowired
	private ImageCaptchaService imageCaptchaService;

	/**
	 * 进入答卷页面
	 */
	public String execute() throws Exception {
		HttpServletRequest request = Struts2Utils.getRequest();
		HttpServletResponse response = Struts2Utils.getResponse();
		SurveyDirectory directory = directoryManager.getSurveyBySid(sid);
		if (directory != null) {
			surveyId = directory.getId();
			String filterStatus = filterStatus(directory,request);
			if(filterStatus!=null){
				return filterStatus;
			}
			if (HttpRequestDeviceUtils.isMobileDevice(request)) {
				return RESPONSE_MOBILE;
			} else {
				String htmlPath = directory.getHtmlPath();
				request.getRequestDispatcher("/" + htmlPath).forward(request,
						response);
			}
		}

		return NONE;
	}

	private String filterStatus(SurveyDirectory directory,HttpServletRequest request){
		SurveyDetail surveyDetail = directory.getSurveyDetail();
		int rule = surveyDetail.getRule();
		Integer ynEndNum = surveyDetail.getYnEndNum();
		Integer endNum = surveyDetail.getEndNum();
		Integer ynEndTime = surveyDetail.getYnEndTime();
		Date endTime = surveyDetail.getEndTime();
		Integer anserNum = directory.getAnswerNum();

		if (directory.getSurveyQuNum() <= 0
				|| directory.getSurveyState() != 1 ||
				(anserNum!=null && ynEndNum==1 && anserNum > endNum ) ||
				(endTime!=null && ynEndTime==1 && endTime.getTime() < (new Date().getTime())) ){
			request.setAttribute("surveyName", "目前该问卷已暂停收集，请稍后再试");
			request.setAttribute("msg", "目前该问卷已暂停收集，请稍后再试");
			return RESPONSE_MSG;
		}
		if (2 == rule) {
			request.setAttribute("msg", "rule2");
			return RELOAD_ANSER_ERROR;
		} else if (3 == rule) {
			String ruleCode = request.getParameter("ruleCode");
			String surveyRuleCode = surveyDetail.getRuleCode();
			if (ruleCode == null || !ruleCode.equals(surveyRuleCode)) {
				return ANSWER_INPUT_RULE;
			}
		}
		return null;
	}

	public String answerMobile() throws Exception {
		HttpServletRequest request = Struts2Utils.getRequest();
		HttpServletResponse response = Struts2Utils.getResponse();
		response.setContentType("text/html;charset=UTF-8");
		SurveyDirectory directory = directoryManager.getSurvey(surveyId);

		if (directory != null) {
			String filterStatus = filterStatus(directory,request);
			if(filterStatus!=null){
				return filterStatus;
			}
			String htmlPath = directory.getHtmlPath();
			htmlPath = htmlPath.substring(0,htmlPath.lastIndexOf("/"));
			request.getRequestDispatcher("/" + htmlPath+"/m_"+surveyId+".html").forward(request,response);
			return NONE;
		}

		return NONE;
	}

	public String save() throws Exception {
		HttpServletRequest request = Struts2Utils.getRequest();
		HttpServletResponse response = Struts2Utils.getResponse();
		String formFrom = request.getParameter("form-from");
		try {
			String ipAddr = ipService.getIp(request);
			long ipNum = surveyAnswerManager.getCountByIp(surveyId, ipAddr);
			SurveyDirectory directory = directoryManager.getSurvey(surveyId);
			SurveyDetail surveyDetail = directory.getSurveyDetail();
			int refreshNum = surveyDetail.getRefreshNum();
			User user = accountManager.getCurUser();
			SurveyAnswer entity = new SurveyAnswer();
			if (user != null) {
				entity.setUserId(user.getId());
			}
			Cookie cookie = CookieUtils.getCookie(request, surveyId);
			Integer effectiveIp = surveyDetail.getEffectiveIp();
			Integer effective = surveyDetail.getEffective();
			if ((effective != null && effective > 1 && cookie != null) || (effectiveIp != null && effectiveIp == 1 && ipNum > 0)) {
				return RELOAD_ANSER_ERROR;
			}
			if (ipNum >= refreshNum) {
				String code = request.getParameter("jcaptchaInput");
				if (!imageCaptchaService.validateResponseForID(request.getSession().getId(), code)) {
					return ANSWER_CODE_ERROR;
				}
			}
			Map<String, Map<String, Object>> quMaps = buildSaveSurveyMap(request);
			String addr = ipService.getCountry(ipAddr);
			String city = ipService.getCurCityByCountry(addr);
			entity.setIpAddr(ipAddr);
			entity.setAddr(addr);
			entity.setCity(city);
			entity.setSurveyId(surveyId);
			entity.setDataSource(0);
			surveyAnswerManager.saveAnswer(entity, quMaps);
			int effe = surveyDetail.getEffectiveTime();
			CookieUtils.addCookie(response, surveyId, (ipNum + 1) + "",
					effe * 60, "/");
			// exambatchManager.savePaperAnswer(entity,quMaps);
		} catch (Exception e) {
			e.printStackTrace();
			return RELOAD_ANSWER_FAILURE;
		}
		return RELOAD_ANSWER_SUCCESS;
	}

	public String saveMobile() throws Exception {
		HttpServletRequest request = Struts2Utils.getRequest();
		HttpServletResponse response = Struts2Utils.getResponse();

		try {
			String ipAddr = ipService.getIp(request);
			long ipNum = surveyAnswerManager.getCountByIp(surveyId, ipAddr);
			SurveyDirectory directory = directoryManager.getSurvey(surveyId);
			SurveyDetail surveyDetail = directory.getSurveyDetail();
			int refreshNum = surveyDetail.getRefreshNum();
			User user = accountManager.getCurUser();

			SurveyAnswer entity = new SurveyAnswer();
			if (user != null) {
				entity.setUserId(user.getId());
			}
			Cookie cookie = CookieUtils.getCookie(request, surveyId);
			Integer effectiveIp = surveyDetail.getEffectiveIp();
			Integer effective = surveyDetail.getEffective();
			if ((effective != null && effective > 1 && cookie != null) || (effectiveIp != null && effectiveIp == 1 && ipNum > 0)) {
				return RELOAD_ANSER_ERROR_M;
			}
			if (ipNum >= refreshNum) {
				String code = request.getParameter("jcaptchaInput");
				if (!imageCaptchaService.validateResponseForID(request
						.getSession().getId(), code)) {
					return ANSWER_CODE_ERROR_M;
				}
			}

			Map<String, Map<String, Object>> quMaps = buildSaveSurveyMap(request);
			String addr = ipService.getCountry(ipAddr);
			String city = ipService.getCurCityByCountry(addr);
			entity.setIpAddr(ipAddr);
			entity.setAddr(addr);
			entity.setCity(city);
			entity.setSurveyId(surveyId);
			entity.setDataSource(0);
			surveyAnswerManager.saveAnswer(entity, quMaps);

			int effe = surveyDetail.getEffectiveTime();
			CookieUtils.addCookie(response, surveyId, (ipNum + 1) + "",
					effe * 60, "/");
			// exambatchManager.savePaperAnswer(entity,quMaps);
		} catch (Exception e) {
			e.printStackTrace();
			return RELOAD_ANSWER_FAILURE;
		}
		return RELOAD_ANSWER_SUCCESS_M;
		// return SURVEY_RESULT;
	}


	public Map<String, Map<String, Object>> buildSaveSurveyMap(HttpServletRequest request) {
		Map<String, Map<String, Object>> quMaps = new HashMap<String, Map<String, Object>>();
		Map<String, Object> yesnoMaps = WebUtils.getParametersStartingWith(
				request, "qu_" + QuType.YESNO + "_");//是非
		quMaps.put("yesnoMaps", yesnoMaps);
		Map<String, Object> radioMaps = WebUtils.getParametersStartingWith(
				request, "qu_"+QuType.RADIO + "_");//单选
		Map<String, Object> checkboxMaps = WebUtils.getParametersStartingWith(
				request, "qu_"+QuType.CHECKBOX + "_");//多选
		Map<String, Object> fillblankMaps = WebUtils.getParametersStartingWith(
				request, "qu_" + QuType.FILLBLANK + "_");//填空
		quMaps.put("fillblankMaps", fillblankMaps);
		Map<String, Object> dfillblankMaps = WebUtils
				.getParametersStartingWith(request, "qu_"
						+ QuType.MULTIFILLBLANK + "_");//多项填空
		for (String key : dfillblankMaps.keySet()) {
			String dfillValue = dfillblankMaps.get(key).toString();
			Map<String, Object> map = WebUtils.getParametersStartingWith(
					request, dfillValue);
			dfillblankMaps.put(key, map);
		}
		quMaps.put("multifillblankMaps", dfillblankMaps);
		Map<String, Object> answerMaps = WebUtils.getParametersStartingWith(
				request, "qu_" + QuType.ANSWER + "_");//多行填空
		quMaps.put("answerMaps", answerMaps);
		Map<String, Object> compRadioMaps = WebUtils.getParametersStartingWith(
				request, "qu_" + QuType.COMPRADIO + "_");//复合单选
		for (String key : compRadioMaps.keySet()) {
			String enId = key;
			String quItemId = compRadioMaps.get(key).toString();
			String otherText = Struts2Utils.getParameter("text_qu_"
					+ QuType.COMPRADIO + "_" + enId + "_" + quItemId);
			AnRadio anRadio = new AnRadio();
			anRadio.setQuId(enId);
			anRadio.setQuItemId(quItemId);
			anRadio.setOtherText(otherText);
			compRadioMaps.put(key, anRadio);
		}
		quMaps.put("compRadioMaps", compRadioMaps);
		Map<String, Object> compCheckboxMaps = WebUtils
				.getParametersStartingWith(request, "qu_" + QuType.COMPCHECKBOX
						+ "_");//复合多选
		for (String key : compCheckboxMaps.keySet()) {
			String dfillValue = compCheckboxMaps.get(key).toString();
			Map<String, Object> map = WebUtils.getParametersStartingWith(
					request, "tag_" + dfillValue);
			for (String key2 : map.keySet()) {
				String quItemId = map.get(key2).toString();
				String otherText = Struts2Utils.getParameter("text_"
						+ dfillValue + quItemId);
				AnCheckbox anCheckbox = new AnCheckbox();
				anCheckbox.setQuItemId(quItemId);
				anCheckbox.setOtherText(otherText);
				map.put(key2, anCheckbox);
			}
			compCheckboxMaps.put(key, map);
		}
		quMaps.put("compCheckboxMaps", compCheckboxMaps);
		Map<String, Object> enumMaps = WebUtils.getParametersStartingWith(request, "qu_" + QuType.ENUMQU + "_");//枚举
		quMaps.put("enumMaps", enumMaps);
		Map<String, Object> scoreMaps = WebUtils.getParametersStartingWith(request, "qu_" + QuType.SCORE + "_");//分数
		for (String key : scoreMaps.keySet()) {
			String tag = scoreMaps.get(key).toString();
			Map<String, Object> map = WebUtils.getParametersStartingWith(
					request, tag);
			scoreMaps.put(key, map);
		}
		quMaps.put("scoreMaps", scoreMaps);
		Map<String, Object> quOrderMaps = WebUtils.getParametersStartingWith(
				request, "qu_" + QuType.ORDERQU + "_");//排序
		for (String key : quOrderMaps.keySet()) {
			String tag = quOrderMaps.get(key).toString();
			Map<String, Object> map = WebUtils.getParametersStartingWith(
					request, tag);
			quOrderMaps.put(key, map);
		}
		quMaps.put("quOrderMaps", quOrderMaps);
		Map<String, Object> chenRadioMaps = WebUtils.getParametersStartingWith(
				request, "qu_" + QuType.CHENRADIO + "_");
		for (String key : chenRadioMaps.keySet()) {
			String tag = chenRadioMaps.get(key).toString();
			Map<String, Object> map = WebUtils.getParametersStartingWith(
					request, tag);
			chenRadioMaps.put(key, map);
		}
		quMaps.put("chenRadioMaps", chenRadioMaps);
		Map<String, Object> chenCheckboxMaps = WebUtils
				.getParametersStartingWith(request, "qu_" + QuType.CHENCHECKBOX
						+ "_");
		for (String key : chenCheckboxMaps.keySet()) {
			String tag = chenCheckboxMaps.get(key).toString();
			Map<String, Object> map = WebUtils.getParametersStartingWith(
					request, tag);
			for (String keyRow : map.keySet()) {
				String tagRow = map.get(keyRow).toString();
				Map<String, Object> mapRow = WebUtils
						.getParametersStartingWith(request, tagRow);
				map.put(keyRow, mapRow);
			}
			chenCheckboxMaps.put(key, map);
		}
		quMaps.put("chenCheckboxMaps", chenCheckboxMaps);
		Map<String, Object> chenScoreMaps = WebUtils.getParametersStartingWith(
				request, "qu_" + QuType.CHENSCORE + "_");
		for (String key : chenScoreMaps.keySet()) {
			String tag = chenScoreMaps.get(key).toString();
			Map<String, Object> map = WebUtils.getParametersStartingWith(
					request, tag);
			for (String keyRow : map.keySet()) {
				String tagRow = map.get(keyRow).toString();
				Map<String, Object> mapRow = WebUtils
						.getParametersStartingWith(request, tagRow);
				map.put(keyRow, mapRow);
			}
			chenScoreMaps.put(key, map);
		}
		quMaps.put("chenScoreMaps", chenScoreMaps);
		Map<String, Object> chenFbkMaps = WebUtils.getParametersStartingWith(
				request, "qu_" + QuType.CHENFBK + "_");
		for (String key : chenFbkMaps.keySet()) {
			String tag = chenFbkMaps.get(key).toString();
			Map<String, Object> map = WebUtils.getParametersStartingWith(
					request, tag);
			for (String keyRow : map.keySet()) {
				String tagRow = map.get(keyRow).toString();
				Map<String, Object> mapRow = WebUtils
						.getParametersStartingWith(request, tagRow);
				map.put(keyRow, mapRow);
			}
			chenFbkMaps.put(key, map);
		}
		quMaps.put("chenFbkMaps", chenFbkMaps);
		for (String key:radioMaps.keySet()) {
			String enId = key;
			String quItemId = radioMaps.get(key).toString();
			String otherText = Struts2Utils.getParameter("text_qu_"
					+ QuType.RADIO + "_" + enId + "_" + quItemId);
			AnRadio anRadio = new AnRadio();
			anRadio.setQuId(enId);
			anRadio.setQuItemId(quItemId);
			anRadio.setOtherText(otherText);
			radioMaps.put(key, anRadio);
		}
		quMaps.put("compRadioMaps", radioMaps);
		for (String key : checkboxMaps.keySet()) {
			String dfillValue = checkboxMaps.get(key).toString();
			Map<String, Object> map = WebUtils.getParametersStartingWith(
					request, dfillValue);
			for (String key2 : map.keySet()) {
				String quItemId = map.get(key2).toString();
				String otherText = Struts2Utils.getParameter("text_"
						+ dfillValue + quItemId);
				AnCheckbox anCheckbox = new AnCheckbox();
				anCheckbox.setQuItemId(quItemId);
				anCheckbox.setOtherText(otherText);
				map.put(key2, anCheckbox);
			}
			checkboxMaps.put(key, map);
		}
		quMaps.put("compCheckboxMaps", checkboxMaps);
		Map<String, Object> chenCompChenRadioMaps = WebUtils
				.getParametersStartingWith(request, "qu_"
						+ QuType.COMPCHENRADIO + "_");
		for (String key : chenCompChenRadioMaps.keySet()) {
			String tag = chenCompChenRadioMaps.get(key).toString();
			Map<String, Object> map = WebUtils.getParametersStartingWith(
					request, tag);
			for (String keyRow : map.keySet()) {
				String tagRow = map.get(keyRow).toString();
				Map<String, Object> mapRow = WebUtils
						.getParametersStartingWith(request, tagRow);
				map.put(keyRow, mapRow);
			}
			chenCompChenRadioMaps.put(key, map);
		}
		quMaps.put("compChenRadioMaps", chenCompChenRadioMaps);
		return quMaps;
	}

	public String answerSuccess() throws Exception {
		HttpServletRequest request = Struts2Utils.getRequest();
		SurveyDirectory directory = directoryManager.getSurveyBySid(sid);
		request.setAttribute("surveyName", directory.getSurveyName());
		request.setAttribute("viewAnswer", directory.getViewAnswer());
		request.setAttribute("sid", directory.getSid());
		return ANSWER_SUCCESS;
	}

	public String answerFailure() throws Exception {
		HttpServletRequest request = Struts2Utils.getRequest();
		SurveyDirectory directory = directoryManager.get(surveyId);
		request.setAttribute("surveyName", directory.getSurveyName());
		request.setAttribute("sId", directory.getSid());
		return ANSWER_FAILURE;
	}

	public String answerError() throws Exception {
		HttpServletRequest request = Struts2Utils.getRequest();
		SurveyDirectory directory = directoryManager.get(surveyId);
		request.setAttribute("surveyName", directory.getSurveyName());
		request.setAttribute("sId", directory.getSid());
		String ipAddr = ipService.getIp(request);
		request.setAttribute("ip", ipAddr);
		return ANSWER_ERROR;
	}

	public String answerErrorM() throws Exception {
		HttpServletRequest request = Struts2Utils.getRequest();
		SurveyDirectory directory = directoryManager.get(surveyId);
		request.setAttribute("surveyName", directory.getSurveyName());
		request.setAttribute("sId", directory.getSid());
		String ipAddr = ipService.getIp(request);
		request.setAttribute("ip", ipAddr);
		return ANSWER_ERROR_M;
	}

	public String answerSuccessM() throws Exception {
		HttpServletRequest request = Struts2Utils.getRequest();
		SurveyDirectory directory = directoryManager.get(surveyId);
		request.setAttribute("directory", directory);
		return ANSWER_SUCCESS_M;
	}

	/**
	 * 异步有效性验证
	 * 
	 * @return
	 */
	public String ajaxCheckSurvey() throws Exception {
		HttpServletRequest request = Struts2Utils.getRequest();
		HttpServletResponse response = Struts2Utils.getResponse();
		// 0 1 2
		String ajaxResult = "0";
		try {
			SurveyDirectory directory = directoryManager.getSurvey(surveyId);
			SurveyDetail surveyDetail = directory.getSurveyDetail();
			int effective = surveyDetail.getEffective();
			int rule = surveyDetail.getRule();
			request.setAttribute("directory", directory);
			// 调查规则
			String surveyStatus = "0";
			// cookie
			Cookie cookie = CookieUtils.getCookie(request, surveyId);
			// 根据 源IP
			String ip = ipService.getIp(request);
			Long ipNum = 0L;
			if (effective > 1) {
				// 根据 cookie过滤
				if (cookie != null) {
					String cookieValue = cookie.getValue();
					if (cookieValue != null
							&& NumberUtils.isNumeric(cookieValue)) {
						ipNum = Long.parseLong(cookieValue);
					}
					surveyStatus = "1";
				} else {
					/*
					 * SurveyAnswer surveyAnswer =
					 * surveyAnswerManager.getTimeInByIp(surveyDetail, ip); if
					 * (surveyAnswer != null) { request.setAttribute("msg",
					 * 2);//表示在有效性验证，间隔时间内 surveyStatus="1"; }
					 */
				}
			}

			ipNum = surveyAnswerManager.getCountByIp(surveyId, ip);
			if (ipNum == null) {
				ipNum = 0L;
			}
			Integer effectiveIp = surveyDetail.getEffectiveIp();
			if (effectiveIp != null && effectiveIp == 1 && ipNum > 0) {
				surveyStatus = "2";
			}

			String isCheckCode = "0";
			// 启用验证码
			int refreshNum = surveyDetail.getRefreshNum();
			if (ipNum >= refreshNum) {
				isCheckCode = "3";
			}
			ajaxResult = "{surveyStatus:\"" + surveyStatus
					+ "\",isCheckCode:\"" + isCheckCode + "\"}";
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.getWriter().write(ajaxResult);
		return null;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(String surveyId) {
		this.surveyId = surveyId;
	}

}
