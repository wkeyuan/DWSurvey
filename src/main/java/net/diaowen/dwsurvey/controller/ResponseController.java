package net.diaowen.dwsurvey.controller;

import com.octo.captcha.service.image.ImageCaptchaService;
import net.diaowen.common.QuType;
import net.diaowen.common.base.entity.User;
import net.diaowen.common.base.service.AccountManager;
import net.diaowen.common.plugs.ipaddr.IPService;
import net.diaowen.common.utils.CookieUtils;
import net.diaowen.common.utils.HttpRequestDeviceUtils;
import net.diaowen.common.utils.NumberUtils;
import net.diaowen.dwsurvey.entity.*;
import net.diaowen.dwsurvey.service.SurveyAnswerManager;
import net.diaowen.dwsurvey.service.SurveyDirectoryManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
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
@Controller
@RequestMapping("/response")
public class ResponseController {
	private static final long serialVersionUID = -2289729314160067840L;

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
	@RequestMapping("/dwsurvey.do")
	public ModelAndView execute(HttpServletRequest request,HttpServletResponse response,String sid) throws Exception {
		ModelAndView modelAndView = null;
		SurveyDirectory directory = directoryManager.getSurveyBySid(sid);
		if (directory != null) {
			String surveyId = directory.getId();
			modelAndView = filterStatus(directory,request);
			if(modelAndView==null){
				request.setCharacterEncoding("utf-8");
				response.setContentType("text/html;charset=utf-8");
				if (HttpRequestDeviceUtils.isMobileDevice(request)) {
					String htmlPath = directory.getHtmlPath();
					htmlPath = htmlPath.substring(0,htmlPath.lastIndexOf("/"));
					request.getRequestDispatcher("/" + htmlPath+"/m_"+surveyId+".html").forward(request,response);
				} else {
					String htmlPath = directory.getHtmlPath();
					request.getRequestDispatcher("/" + htmlPath).forward(request,response);
				}
			}
		}
		return modelAndView;
	}

	private ModelAndView filterStatus(SurveyDirectory directory,HttpServletRequest request){
		ModelAndView modelAndView = new ModelAndView();
		String surveyId = directory.getId();
		request.getSession().setAttribute("bgTime"+surveyId,new Date());
		SurveyDetail surveyDetail = directory.getSurveyDetail();
		int rule = surveyDetail.getRule();
		Integer ynEndNum = surveyDetail.getYnEndNum();
		Integer endNum = surveyDetail.getEndNum();
		Integer ynEndTime = surveyDetail.getYnEndTime();
		Date endTime = surveyDetail.getEndTime();
		Integer anserNum = directory.getAnswerNum();
		Integer ynStartTime = surveyDetail.getYnStartTime();
		Date startTime = surveyDetail.getStartTime();

		Integer visibility = directory.getVisibility();
		if(visibility!=1){
			modelAndView.addObject("survey", directory);
			modelAndView.addObject("redType", 10);
			modelAndView.setViewName("/diaowen-answer/response-msg-1");
			return modelAndView;
		}

		if(anserNum!=null && ynEndNum==1 && anserNum >= endNum ){
			modelAndView.addObject("survey", directory);
			modelAndView.addObject("redType", 7);
			modelAndView.setViewName("/diaowen-answer/response-msg-1");
			return modelAndView;
		}

		if(startTime!=null && ynStartTime==1 &&  (new Date().getTime()-startTime.getTime()) < 0 ){
			modelAndView.addObject("survey", directory);
			modelAndView.addObject("redType", 8);
			modelAndView.setViewName("/diaowen-answer/response-msg-1");
			return modelAndView;
		}

		if(endTime!=null && ynEndTime==1 &&  (new Date().getTime()-endTime.getTime()) > 0 ){
			modelAndView.addObject("survey", directory);
			modelAndView.addObject("redType", 9);
			modelAndView.setViewName("/diaowen-answer/response-msg-1");
			directory.setSurveyState(2);
			directoryManager.saveByAdmin(directory);
			return modelAndView;
		}

			if (directory.getSurveyQuNum() <= 0
				|| directory.getSurveyState() != 1 ) {
			modelAndView.addObject("survey", directory);
			modelAndView.addObject("redType", 1);
			modelAndView.setViewName("/diaowen-answer/response-msg-1");
			return modelAndView;
		}
		if (2 == rule) {
			modelAndView.addObject("survey", directory);
			modelAndView.addObject("redType", 2);
			modelAndView.setViewName("/diaowen-answer/response-msg-1");
			return modelAndView;
		} else if (3 == rule) {
			String ruleCode = request.getParameter("ruleCode");
			String surveyRuleCode = surveyDetail.getRuleCode();
			if (ruleCode == null || !ruleCode.equals(surveyRuleCode)) {
				modelAndView.setViewName("/diaowen-answer/response-input-rule");
				return modelAndView;
			}
		}
		return null;
	}

	@RequestMapping("/answerMobile.do")
	public ModelAndView answerMobile(HttpServletRequest request,HttpServletResponse response,String surveyId) throws Exception {
		ModelAndView modelAndView = null;
		SurveyDirectory directory = directoryManager.getSurvey(surveyId);
		if (directory != null) {
			modelAndView = filterStatus(directory,request);
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=utf-8");
			if(modelAndView==null){
				String htmlPath = directory.getHtmlPath();
				htmlPath = htmlPath.substring(0,htmlPath.lastIndexOf("/"));
				request.getRequestDispatcher("/" + htmlPath+"/m_"+surveyId+".html").forward(request,response);
			}
		}
		return modelAndView;
	}

	@RequestMapping("/save.do")
	public String save(HttpServletRequest request,HttpServletResponse response,String surveyId) throws Exception {
		String formFrom = request.getParameter("form-from");
		SurveyDirectory directory = directoryManager.getSurvey(surveyId);
		try {
			String ipAddr = ipService.getIp(request);
			long ipNum = surveyAnswerManager.getCountByIp(surveyId, ipAddr);
			SurveyDetail surveyDetail = directory.getSurveyDetail();
			int refresh = surveyDetail.getRefresh();
			int refreshNum = surveyDetail.getRefreshNum();
			User user = accountManager.getCurUser();
			SurveyAnswer entity = new SurveyAnswer();
			if (user != null) {
				entity.setUserId(user.getId());
			}

			Integer ynEndNum = surveyDetail.getYnEndNum();
			Integer endNum = surveyDetail.getEndNum();
			Integer ynEndTime = surveyDetail.getYnEndTime();
			Date endTime = surveyDetail.getEndTime();
			Integer anserNum = directory.getAnswerNum();
			Integer ynStartTime = surveyDetail.getYnStartTime();
			Date startTime = surveyDetail.getStartTime();

			Integer visibility = directory.getVisibility();
			if(visibility!=1){
				return "redirect:/response/answer-dwsurvey.do?surveyId="+surveyId+"&redType=10";
			}

			if(anserNum!=null && ynEndNum==1 && anserNum >= endNum ){
				return "redirect:/response/answer-dwsurvey.do?surveyId="+surveyId+"&redType=7";
			}

			if(startTime!=null && ynStartTime==1 &&  (new Date().getTime()-startTime.getTime()) < 0 ){
				return "redirect:/response/answer-dwsurvey.do?surveyId="+surveyId+"&redType=8";
			}

			if(endTime!=null && ynEndTime==1 &&  (new Date().getTime()-endTime.getTime()) > 0 ){
				directory.setSurveyState(2);
				directoryManager.saveByAdmin(directory);
				return "redirect:/response/answer-dwsurvey.do?surveyId="+surveyId+"&redType=9";
			}

			Cookie cookie = CookieUtils.getCookie(request, surveyId);
			Integer effectiveIp = surveyDetail.getEffectiveIp();
			Integer effective = surveyDetail.getEffective();
			if ((effective != null && effective > 1 && cookie != null) || (effectiveIp != null && effectiveIp == 1 && ipNum > 0)) {
				return "redirect:/response/answer-dwsurvey.do?surveyId="+surveyId+"&redType=3";
			}

			String refCookieKey = "r_"+surveyId;
			Cookie refCookie = CookieUtils.getCookie(request, refCookieKey);
			if (( refresh==1 && refCookie!=null)) {
				String code = request.getParameter("jcaptchaInput");
				if (!imageCaptchaService.validateResponseForID(request.getSession().getId(), code)) {
					return "redirect:/response/answer-dwsurvey.do?surveyId="+surveyId+"&redType=4";
				}
			}
			Map<String, Map<String, Object>> quMaps = buildSaveSurveyMap(request);
//			String addr = ipService.getCountry(ipAddr);
//			String city = ipService.getCurCityByCountry(addr);
			entity.setIpAddr(ipAddr);
//			entity.setAddr(addr);
//			entity.setCity(city);
			entity.setSurveyId(surveyId);
			entity.setDataSource(0);
			String bgTimeAttrName = "bgTime"+surveyId;
			Date bgAnTime = (Date)request.getSession().getAttribute(bgTimeAttrName);
			entity.setBgAnDate(bgAnTime);
			entity.setEndAnDate(new Date());
			surveyAnswerManager.saveAnswer(entity, quMaps);
			int effe = surveyDetail.getEffectiveTime();
			CookieUtils.addCookie(response, surveyId, (ipNum + 1) + "", effe * 60, "/");
			CookieUtils.addCookie(response, refCookieKey, (ipNum + 1) + "", null, "/");

			directory = directoryManager.getSurvey(surveyId);
			anserNum = directory.getAnswerNum();
			if(anserNum!=null && ynEndNum==1 && anserNum >= endNum ){
				directory.setSurveyState(2);
				directoryManager.saveByAdmin(directory);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/response/answer-dwsurvey.do?surveyId="+surveyId+"&redType=5";
		}
		return "redirect:/response/answer-dwsurvey.do?surveyId="+surveyId+"&redType=6";
	}

	@RequestMapping("/saveMobile.do")
	public String saveMobile(HttpServletRequest request,HttpServletResponse response,String surveyId) throws Exception {
		try {
			String ipAddr = ipService.getIp(request);
			long ipNum = surveyAnswerManager.getCountByIp(surveyId, ipAddr);
			SurveyDirectory directory = directoryManager.getSurvey(surveyId);
			SurveyDetail surveyDetail = directory.getSurveyDetail();
			int refresh = surveyDetail.getRefresh();
			int refreshNum = surveyDetail.getRefreshNum();
			User user = accountManager.getCurUser();

			SurveyAnswer entity = new SurveyAnswer();
			if (user != null) {
				entity.setUserId(user.getId());
			}

			Integer ynEndNum = surveyDetail.getYnEndNum();
			Integer endNum = surveyDetail.getEndNum();
			Integer ynEndTime = surveyDetail.getYnEndTime();
			Date endTime = surveyDetail.getEndTime();
			Integer anserNum = directory.getAnswerNum();
			Integer ynStartTime = surveyDetail.getYnStartTime();
			Date startTime = surveyDetail.getStartTime();

			Integer visibility = directory.getVisibility();
			if(visibility!=1){
				return "redirect:/response/answer-dwsurvey.do?surveyId="+surveyId+"&redType=10";
			}

			if(anserNum!=null && ynEndNum==1 && anserNum >= endNum ){
				return "redirect:/response/answer-dwsurvey.do?surveyId="+surveyId+"&redType=7";
			}

			if(startTime!=null && ynStartTime==1 &&  (new Date().getTime()-startTime.getTime()) < 0 ){
				return "redirect:/response/answer-dwsurvey.do?surveyId="+surveyId+"&redType=8";
			}

			if(endTime!=null && ynEndTime==1 &&  (new Date().getTime()-endTime.getTime()) > 0 ){
				directory.setSurveyState(2);
				directoryManager.saveByAdmin(directory);
				return "redirect:/response/answer-dwsurvey.do?surveyId="+surveyId+"&redType=9";
			}
			Cookie cookie = CookieUtils.getCookie(request, surveyId);
			Integer effectiveIp = surveyDetail.getEffectiveIp();
			Integer effective = surveyDetail.getEffective();
			if ((effective != null && effective > 1 && cookie != null) || (effectiveIp != null && effectiveIp == 1 && ipNum > 0)) {
				return "redirect:/response/answer-dwsurvey.do?surveyId="+surveyId+"&redType=3";
			}

			String refCookieKey = "r_"+surveyId;
			Cookie refCookie = CookieUtils.getCookie(request, refCookieKey);
			if (( refresh==1 && refCookie!=null)) {
				String code = request.getParameter("jcaptchaInput");
				if (!imageCaptchaService.validateResponseForID(request.getSession().getId(), code)) {
					return "redirect:/response/answer-dwsurvey.do?surveyId="+surveyId+"&redType=4";
				}
			}

			Map<String, Map<String, Object>> quMaps = buildSaveSurveyMap(request);
//			String addr = ipService.getCountry(ipAddr);
//			String city = ipService.getCurCityByCountry(addr);
			entity.setIpAddr(ipAddr);
//			entity.setAddr(addr);
//			entity.setCity(city);
			entity.setSurveyId(surveyId);
			entity.setDataSource(0);
			String bgTimeAttrName = "bgTime"+surveyId;
			Date bgAnTime = (Date)request.getSession().getAttribute(bgTimeAttrName);
			entity.setBgAnDate(bgAnTime);
			entity.setEndAnDate(new Date());
			surveyAnswerManager.saveAnswer(entity, quMaps);
			int effe = surveyDetail.getEffectiveTime();
			CookieUtils.addCookie(response, surveyId, (ipNum + 1) + "",effe * 60, "/");
			CookieUtils.addCookie(response, refCookieKey, (ipNum + 1) + "", null, "/");
			directory = directoryManager.getSurvey(surveyId);
			anserNum = directory.getAnswerNum();
			if(anserNum!=null && ynEndNum==1 && anserNum >= endNum ){
				directory.setSurveyState(2);
				directoryManager.saveByAdmin(directory);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/response/answer-dwsurvey.do?surveyId="+surveyId+"&redType=5";
		}
		return "redirect:/response/answer-dwsurvey.do?surveyId="+surveyId+"&redType=6";
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
			String otherText = request.getParameter("text_qu_"
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
				String otherText = request.getParameter("text_"
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
		for (String key:radioMaps.keySet()) {
			String enId = key;
			String quItemId = radioMaps.get(key).toString();
			String otherText = request.getParameter("text_qu_"
					+ QuType.RADIO + "_" + enId + "_" + quItemId);
			AnRadio anRadio = new AnRadio();
			anRadio.setQuId(enId);
			anRadio.setQuItemId(quItemId);
			anRadio.setOtherText(otherText);
			radioMaps.put(key, anRadio);
		}
		quMaps.put("radioMaps", radioMaps);
		for (String key : checkboxMaps.keySet()) {
			String dfillValue = checkboxMaps.get(key).toString();
			Map<String, Object> map = WebUtils.getParametersStartingWith(
					request, dfillValue);
			for (String key2 : map.keySet()) {
				String quItemId = map.get(key2).toString();
				String otherText = request.getParameter("text_"
						+ dfillValue + quItemId);
				AnCheckbox anCheckbox = new AnCheckbox();
				anCheckbox.setQuItemId(quItemId);
				anCheckbox.setOtherText(otherText);
				map.put(key2, anCheckbox);
			}
			checkboxMaps.put(key, map);
		}
		quMaps.put("checkboxMaps", checkboxMaps);
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


	@RequestMapping("/answer-dwsurvey.do")
	public ModelAndView answerRedirect(HttpServletRequest request,String surveyId, int redType) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		SurveyDirectory directory = directoryManager.getSurvey(surveyId);
		modelAndView.addObject("survey", directory);
		modelAndView.addObject("redType", redType);
		modelAndView.setViewName("/diaowen-answer/response-msg-1");
		return modelAndView;
	}

	/**
	 * 异步有效性验证
	 *
	 * @return
	 */
	@RequestMapping("/ajaxCheckSurvey.do")
	public String ajaxCheckSurvey(HttpServletRequest request,HttpServletResponse response,String surveyId) throws Exception {
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
			int refresh = surveyDetail.getRefresh();
			// cookie
			Cookie refCookie = CookieUtils.getCookie(request, "r_"+surveyId);
			if ((refresh==1 && refCookie!=null)) {
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


}
