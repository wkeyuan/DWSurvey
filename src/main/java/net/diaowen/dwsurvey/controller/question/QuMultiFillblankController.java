package net.diaowen.dwsurvey.controller.question;

import net.diaowen.common.QuType;
import net.diaowen.common.base.entity.User;
import net.diaowen.common.base.service.AccountManager;
import net.diaowen.common.plugs.page.Page;
import net.diaowen.dwsurvey.entity.*;
import net.diaowen.dwsurvey.service.AnDFillblankManager;
import net.diaowen.dwsurvey.service.QuMultiFillblankManager;
import net.diaowen.dwsurvey.service.QuestionManager;
import net.diaowen.dwsurvey.service.SurveyDirectoryManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 多项填空题 action
 * @author KeYuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 *
 */
@Controller
@RequestMapping("/api/dwsurvey/app/design/qu-multi-fillblank")
public class QuMultiFillblankController{
	@Autowired
	private QuestionManager questionManager;
	@Autowired
	private QuMultiFillblankManager quMultiFillblankManager;
	@Autowired
	private AnDFillblankManager anDFillblankManager;

	@RequestMapping("/ajaxSave.do")
	public String ajaxSave(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			Question entity=ajaxBuildSaveOption(request);
			questionManager.save(entity);
			String resultJson=buildResultJson(entity);
			response.getWriter().write(resultJson);
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
		String isRequired=request.getParameter("isRequired");
		//hv 1水平显示 2垂直显示
		String hv=request.getParameter("hv");
		//randOrder 选项随机排列
		String randOrder=request.getParameter("randOrder");
		String cellCount=request.getParameter("cellCount");
		String paramInt01=request.getParameter("paramInt01");//最小分
		String paramInt02=request.getParameter("paramInt02");//最大分
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
		entity.setQuType(QuType.MULTIFILLBLANK);
		//参数
		isRequired=(isRequired==null || "".equals(isRequired))?"0":isRequired;
		hv=(hv==null || "".equals(hv))?"0":hv;
		randOrder=(randOrder==null || "".equals(randOrder))?"0":randOrder;
		cellCount=(cellCount==null || "".equals(cellCount))?"0":cellCount;
		paramInt01=(paramInt01==null || "".equals(paramInt01))?"0":paramInt01;
		entity.setIsRequired(Integer.parseInt(isRequired));
		entity.setHv(Integer.parseInt(hv));
		entity.setRandOrder(Integer.parseInt(randOrder));
		entity.setCellCount(Integer.parseInt(cellCount));
		entity.setParamInt01(Integer.parseInt(paramInt01));
		entity.setParamInt02(10);
		Map<String, Object> optionNameMap=WebUtils.getParametersStartingWith(request, "optionValue_");
		List<QuMultiFillblank> quMFillblanks=new ArrayList<QuMultiFillblank>();
		for (String key : optionNameMap.keySet()) {
			String optionId=request.getParameter("optionId_"+key);
			Object optionName=optionNameMap.get(key);
			String optionNameValue=(optionName!=null)?optionName.toString():"";
			QuMultiFillblank quMultiFillblank=new QuMultiFillblank();
			if("".equals(optionId)){
				optionId=null;
			}
			quMultiFillblank.setId(optionId);
//			quRadio.setOptionTitle(key);
			optionNameValue=URLDecoder.decode(optionNameValue,"utf-8");
			quMultiFillblank.setOptionName(optionNameValue);
			quMultiFillblank.setOrderById(Integer.parseInt(key));
			quMFillblanks.add(quMultiFillblank);
		}
		entity.setQuMultiFillblanks(quMFillblanks);

		//逻辑选项设置
		Map<String, Object> quLogicIdMap=WebUtils.getParametersStartingWith(request, "quLogicId_");
		List<QuestionLogic> quLogics=new ArrayList<QuestionLogic>();
		for (String key : quLogicIdMap.keySet()) {
			String cgQuItemId=request.getParameter("cgQuItemId_"+key);
			String skQuId=request.getParameter("skQuId_"+key);
			String visibility=request.getParameter("visibility_"+key);
			String logicType=request.getParameter("logicType_"+key);
			Object quLogicId=quLogicIdMap.get(key);
			String quLogicIdValue=(quLogicId!=null)?quLogicId.toString():null;

			QuestionLogic quLogic=new QuestionLogic();
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
		StringBuffer strBuf=new StringBuffer();
		strBuf.append("{id:'").append(entity.getId());
		strBuf.append("',orderById:");
		strBuf.append(entity.getOrderById());
		strBuf.append(",quItems:[");
		List<QuMultiFillblank> quMultiFillblanks=entity.getQuMultiFillblanks();
		for (QuMultiFillblank quMultiFillblank : quMultiFillblanks) {
			strBuf.append("{id:'").append(quMultiFillblank.getId());
			strBuf.append("',title:'").append(quMultiFillblank.getOrderById()).append("'},");
		}
		int strLen=strBuf.length();
		if(strBuf.lastIndexOf(",")==(strLen-1)){
			strBuf.replace(strLen-1, strLen, "");
		}
		strBuf.append("]");

		strBuf.append(",quLogics:[");
		List<QuestionLogic> questionLogics=entity.getQuestionLogics();
		if(questionLogics!=null){
			for (QuestionLogic questionLogic : questionLogics) {
				strBuf.append("{id:'").append(questionLogic.getId());
				strBuf.append("',title:'").append(questionLogic.getTitle()).append("'},");
			}
		}
		strLen=strBuf.length();
		if(strBuf.lastIndexOf(",")==(strLen-1)){
			strBuf.replace(strLen-1, strLen, "");
		}
		strBuf.append("]}");
		return strBuf.toString();
	}

	/**
	 * 删除选项
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/ajaxDelete.do")
	public String ajaxDelete(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			String quItemId=request.getParameter("quItemId");
			quMultiFillblankManager.ajaxDelete(quItemId);
			response.getWriter().write("true");
		}catch(Exception e){
			e.printStackTrace();
			response.getWriter().write("error");
		}
		return null;
	}

}
