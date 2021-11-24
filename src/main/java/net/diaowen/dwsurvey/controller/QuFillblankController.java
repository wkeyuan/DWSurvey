package net.diaowen.dwsurvey.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.diaowen.common.CheckType;
import net.diaowen.common.QuType;
import net.diaowen.common.base.entity.User;
import net.diaowen.common.base.service.AccountManager;
import net.diaowen.common.plugs.page.Page;
import net.diaowen.dwsurvey.entity.AnFillblank;
import net.diaowen.dwsurvey.entity.Question;
import net.diaowen.dwsurvey.entity.QuestionLogic;
import net.diaowen.dwsurvey.entity.SurveyDirectory;
import net.diaowen.dwsurvey.service.AnFillblankManager;
import net.diaowen.dwsurvey.service.QuestionManager;
import net.diaowen.dwsurvey.service.SurveyDirectoryManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;
/**
 * 填空题 action
 * @author KeYuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 *
 */
@Controller
@RequestMapping("/design/qu-fillblank")
public class QuFillblankController{
	@Autowired
	private QuestionManager questionManager;
	@Autowired
	private AnFillblankManager anFillblankManager;
	@Autowired
	private SurveyDirectoryManager surveyDirectoryManager;
	@Autowired
	private AccountManager accountManager;

	@RequestMapping("/ajaxSave.do")
	public String ajaxSave(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			Question entity=ajaxBuildSaveOption(request);
			questionManager.save(entity);
			String resultJson=buildResultJson(entity);
			response.getWriter().write(resultJson);
			//返回各部分ID
		}catch (Exception e) {
			e.printStackTrace();
			response.getWriter().write("error");
		}
		return null;
	}

	private Question ajaxBuildSaveOption(HttpServletRequest request) throws UnsupportedEncodingException {
		String quId=request.getParameter("quId");
		String belongId=request.getParameter("belongId");
		String quTitle=request.getParameter("quTitle");
		String orderById=request.getParameter("orderById");
		String tag=request.getParameter("tag");
		//isRequired 是否必选
		String isRequired=request.getParameter("isRequired");

		String answerInputWidth=request.getParameter("answerInputWidth");
		String answerInputRow=request.getParameter("answerInputRow");

		String contactsAttr=request.getParameter("contactsAttr");
		String contactsField=request.getParameter("contactsField");

		String checkType=request.getParameter("checkType");
		String paramStr01=request.getParameter("paramStr01");
		String paramInt01=request.getParameter("paramInt01");

		/** 某一类型题目特有的 **/
		//hv 1水平显示 2垂直显示
		String hv=request.getParameter("hv");
		//randOrder 选项随机排列
		String randOrder=request.getParameter("randOrder");
		String cellCount=request.getParameter("cellCount");

		if("".equals(quId)){
			quId=null;
		}
		Question entity=questionManager.getModel(quId);
		entity.setBelongId(belongId);
		if(quTitle!=null){
			quTitle=URLDecoder.decode(quTitle,"utf-8");
			entity.setQuTitle(quTitle);
		}
		entity.setOrderById(Integer.parseInt(orderById));
		entity.setTag(Integer.parseInt(tag));
		entity.setQuType(QuType.FILLBLANK);
		//参数
		isRequired=(isRequired==null || "".equals(isRequired))?"0":isRequired;
		hv=(hv==null || "".equals(hv))?"0":hv;
		randOrder=(randOrder==null || "".equals(randOrder))?"0":randOrder;
		cellCount=(cellCount==null || "".equals(cellCount))?"0":cellCount;
		paramInt01=(paramInt01==null || "".equals(paramInt01))?"0":paramInt01;

		contactsAttr=(contactsAttr==null || "".equals(contactsAttr))?"0":contactsAttr;
		entity.setContactsAttr(Integer.parseInt(contactsAttr));
		entity.setContactsField(contactsField);

		answerInputWidth=(answerInputWidth==null || "".equals(answerInputWidth))?"300":answerInputWidth;
		answerInputRow=(answerInputRow==null || "".equals(answerInputRow))?"1":answerInputRow;
		entity.setAnswerInputWidth(Integer.parseInt(answerInputWidth));
		entity.setAnswerInputRow(Integer.parseInt(answerInputRow));

		entity.setIsRequired(Integer.parseInt(isRequired));
		entity.setHv(Integer.parseInt(hv));
		entity.setRandOrder(Integer.parseInt(randOrder));
		entity.setCellCount(Integer.parseInt(cellCount));
		entity.setParamStr01(paramStr01);
		entity.setParamInt01(Integer.parseInt(paramInt01));

		checkType=(checkType==null || "".equals(checkType))?"NO":checkType;
		entity.setCheckType(CheckType.valueOf(checkType));
		//逻辑选项设置
		Map<String, Object> quLogicIdMap=WebUtils.getParametersStartingWith(request, "quLogicId_");
		List<QuestionLogic> quLogics=new ArrayList<QuestionLogic>();
		for (String key : quLogicIdMap.keySet()) {
			String cgQuItemId=request.getParameter("cgQuItemId_"+key);
			String skQuId=request.getParameter("skQuId_"+key);
			String visibility=request.getParameter("visibility_"+key);
			String logicType=request.getParameter("logicType_"+key);
			Object quLogicId=quLogicIdMap.get(key);
			String quLogicIdValue=(quLogicId!=null)?quLogicId.toString():"";

			QuestionLogic quLogic=new QuestionLogic();
			if("".equals(quLogic)){
				quLogic=null;
			}
			quLogic.setId(quLogicIdValue);
			quLogic.setCgQuItemId(cgQuItemId);
			quLogic.setSkQuId(skQuId);
			quLogic.setVisibility(Integer.parseInt(visibility));
			quLogic.setTitle(key);
			quLogic.setLogicType(logicType);
			quLogics.add(quLogic);
		}
		entity.setQuestionLogics(quLogics);

		return entity;
	}

	public static String buildResultJson(Question entity){
		//{id:'null',quItems:[{id:'null',title:'null'},{id:'null',title:'null'}]}
		StringBuffer strBuf=new StringBuffer();
		strBuf.append("{id:'").append(entity.getId());
		strBuf.append("',orderById:");
		strBuf.append(entity.getOrderById());
		strBuf.append(",quLogics:[");
		List<QuestionLogic> questionLogics=entity.getQuestionLogics();
		if(questionLogics!=null){
			for (QuestionLogic questionLogic : questionLogics) {
				strBuf.append("{id:'").append(questionLogic.getId());
				strBuf.append("',title:'").append(questionLogic.getTitle()).append("'},");
			}
		}
		int strLen=strBuf.length();
		if(strBuf.lastIndexOf(",")==(strLen-1)){
			strBuf.replace(strLen-1, strLen, "");
		}
		strBuf.append("]}");
		return strBuf.toString();
	}

	@RequestMapping("/answers.do")
	public ModelAndView answers(Page<AnFillblank> anPage,String quId,String surveyId) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		anPage.setPageSize(1000);
		// 得到频数分析数据
		User user = accountManager.getCurUser();
		if(user!=null){
			SurveyDirectory directory = surveyDirectoryManager.getSurvey(surveyId);
			String userId = user.getId();
			if(userId.equals(directory.getUserId()) || userId.equals("1")){
				anPage = anFillblankManager.findPage(anPage, quId);
				modelAndView.addObject("directory",directory);
				modelAndView.addObject("surveyId",surveyId);
				modelAndView.addObject("anPage",anPage);
				modelAndView.setViewName("/diaowen-da/fillblank");
			}
		}
		return modelAndView;
	}

}
