package com.key.dwsurvey.action.survey;

import com.key.common.base.entity.User;
import com.key.common.base.service.AccountManager;
import com.key.common.utils.DiaowenProperty;
import com.key.common.utils.JspToHtml;
import com.key.common.utils.web.Struts2Utils;
import com.key.dwsurvey.entity.Question;
import com.key.dwsurvey.entity.SurveyDetail;
import com.key.dwsurvey.entity.SurveyDirectory;
import com.key.dwsurvey.entity.SurveyStyle;
import com.key.dwsurvey.service.QuestionManager;
import com.key.dwsurvey.service.SurveyDirectoryManager;
import com.key.dwsurvey.service.SurveyReqUrlManager;
import com.key.dwsurvey.service.SurveyStyleManager;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.*;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * 设计问卷
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 *
 */

@Namespace("/design")
@InterceptorRefs({ @InterceptorRef("paramsPrepareParamsStack")})
@Results({
	@Result(name=ActionSupport.SUCCESS,location="/WEB-INF/page/content/diaowen-design/design-survey.jsp",type=Struts2Utils.DISPATCHER),
	@Result(name=MySurveyDesignAction.PREVIEWDEV,location="/WEB-INF/page/content/diaowen-design/survey_preview_dev.jsp",type=Struts2Utils.DISPATCHER),
	@Result(name=MySurveyDesignAction.COLLECTSURVEY,location="my-collect.action?surveyId=${surveyId}",type=Struts2Utils.REDIRECT),
	@Result(name=MySurveyDesignAction.RELOADDESIGN,location="/design/my-survey-design.action?surveyId=${surveyId}",type=Struts2Utils.REDIRECT)
})
@AllowedMethods({"previewDev","devSurvey","ajaxSave","copySurvey"})
public class MySurveyDesignAction extends ActionSupport{
	//发布设置
	protected final static String PREVIEWDEV="previewDev";
	protected final static String COLLECTSURVEY="collectSurvey";
	protected final static String RELOADDESIGN="reloadDesign";
	
	@Autowired
	private QuestionManager questionManager;
	@Autowired
	private SurveyDirectoryManager surveyDirectoryManager;
	@Autowired
	private SurveyStyleManager surveyStyleManager;
	@Autowired
	private SurveyReqUrlManager surveyReqUrlManager;
	@Autowired
	private AccountManager accountManager;

	private String surveyId;
	
	@Override
	public String execute() throws Exception {
		buildSurvey();
		return SUCCESS;
	}
	
	public String previewDev() throws Exception {
		buildSurvey();
		
		return PREVIEWDEV;
	}
	
	public String devSurvey() throws Exception {
		SurveyDirectory survey=surveyDirectoryManager.get(surveyId);
		Date createDate=survey.getCreateDate();
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy/MM/dd/");
		try{
			String url="/survey!answerSurvey.action?surveyId="+surveyId;
			String filePath="WEB-INF/wjHtml/"+dateFormat.format(createDate);
			String fileName=surveyId+".html";
			new JspToHtml().postJspToHtml(url, filePath, fileName);
			survey.setHtmlPath(filePath+fileName);

			url="/survey!answerSurveryMobile.action?surveyId="+surveyId;
			filePath="WEB-INF/wjHtml/"+dateFormat.format(createDate);
			fileName="m_"+surveyId+".html";
			new JspToHtml().postJspToHtml(url, filePath, fileName);

			List<Question> questions=questionManager.find(surveyId, "2");
			survey.setSurveyQuNum(questions.size());
			survey.setSurveyState(1);
			surveyDirectoryManager.save(survey);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return COLLECTSURVEY;
	}
	
	private void buildSurvey() {
		//判断是否拥有权限
		User user= accountManager.getCurUser();
		if(user!=null){
			String userId=user.getId();
			SurveyDirectory surveyDirectory=surveyDirectoryManager.getSurveyByUser(surveyId, userId);
			if(surveyDirectory!=null){
				surveyDirectoryManager.getSurveyDetail(surveyId, surveyDirectory);
//				SurveyDirectory survey=surveyDirectoryManager.getSurvey(surveyId);
				List<Question> questions=questionManager.findDetails(surveyId, "2");
				surveyDirectory.setQuestions(questions);
				surveyDirectory.setSurveyQuNum(questions.size());
				surveyDirectoryManager.save(surveyDirectory);
				Struts2Utils.setReqAttribute("survey", surveyDirectory);
				SurveyStyle surveyStyle=surveyStyleManager.getBySurveyId(surveyId);
				Struts2Utils.setReqAttribute("surveyStyle", surveyStyle);
				
				Struts2Utils.setReqAttribute("prevHost", DiaowenProperty.STORAGE_URL_PREFIX);
			}else{
				Struts2Utils.setReqAttribute("msg", "未登录或没有相应数据权限");
			}
		}else{
			Struts2Utils.setReqAttribute("msg", "未登录或没有相应数据权限");
		}
	}


	public String ajaxSave() throws Exception {
		HttpServletRequest request=Struts2Utils.getRequest();
		HttpServletResponse response=Struts2Utils.getResponse();
		String svyName=request.getParameter("svyName");
		String svyNote=request.getParameter("svyNote");
		//属性
		String effective=request.getParameter("effective");
		String effectiveIp=request.getParameter("effectiveIp");
		String rule=request.getParameter("rule");
		String ruleCode=request.getParameter("ruleCode");
		String refresh=request.getParameter("refresh");
		String mailOnly=request.getParameter("mailOnly");
		String ynEndNum=request.getParameter("ynEndNum");
		String endNum=request.getParameter("endNum");
		String ynEndTime=request.getParameter("ynEndTime");
		String endTime=request.getParameter("endTime");
		String showShareSurvey=request.getParameter("showShareSurvey");
		String showAnswerDa=request.getParameter("showAnswerDa");
		
		
		SurveyDirectory survey=surveyDirectoryManager.getSurvey(surveyId);
		SurveyDetail surveyDetail=survey.getSurveyDetail();
		User user= accountManager.getCurUser();
		if(user!=null && survey!=null){
			String userId=user.getId();
			if(userId.equals(survey.getUserId())){
				
				if( svyNote!=null){
					svyNote=URLDecoder.decode(svyNote,"utf-8");
					surveyDetail.setSurveyNote(svyNote);
				}
				if(svyName!=null && !"".equals(svyName)){
					svyName=URLDecoder.decode(svyName,"utf-8");
					survey.setSurveyName(svyName);
				}

				//保存属性
				if(effective!=null && !"".equals(effective)){
				    surveyDetail.setEffective(Integer.parseInt(effective));
				}
				if(effectiveIp!=null && !"".equals(effectiveIp)){
				    surveyDetail.setEffectiveIp(Integer.parseInt(effectiveIp));
				}
				if(rule!=null && !"".equals(rule)){
				    surveyDetail.setRule(Integer.parseInt(rule));
				    surveyDetail.setRuleCode(ruleCode);
				}
				if(refresh!=null && !"".equals(refresh)){
				    surveyDetail.setRefresh(Integer.parseInt(refresh));
				}
				if(mailOnly!=null && !"".equals(mailOnly)){
				    surveyDetail.setMailOnly(Integer.parseInt(mailOnly));
				}
				if(ynEndNum!=null && !"".equals(ynEndNum)){
				    surveyDetail.setYnEndNum(Integer.parseInt(ynEndNum));
				    //surveyDetail.setEndNum(Integer.parseInt(endNum));
				    if(endNum!=null && endNum.matches("\\d*")){
					surveyDetail.setEndNum(Integer.parseInt(endNum));			
				    }
				}
				if(ynEndTime!=null && !"".equals(ynEndTime)){
				    surveyDetail.setYnEndTime(Integer.parseInt(ynEndTime));
//				    surveyDetail.setEndTime(endTime);
				    surveyDetail.setEndTime(new Date());
				}
				if(showShareSurvey!=null && !"".equals(showShareSurvey)){
				    surveyDetail.setShowShareSurvey(Integer.parseInt(showShareSurvey));
				    survey.setIsShare(Integer.parseInt(showShareSurvey));
				}
				if(showAnswerDa!=null && !"".equals(showAnswerDa)){
				    surveyDetail.setShowAnswerDa(Integer.parseInt(showAnswerDa));
				    survey.setViewAnswer(Integer.parseInt(showAnswerDa));
				}
				
				surveyDirectoryManager.save(survey);

				response.getWriter().write("true");
				
			}
		}
		
		return NONE;
	}
	
	public String getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(String surveyId) {
		this.surveyId = surveyId;
	}

	public String copySurvey() throws Exception {
		//引用问卷
//		id="402880e541d051000141d0f708ff0004";
		HttpServletRequest request=Struts2Utils.getRequest();
		String fromBankId=request.getParameter("fromBankId");
		String surveyName=request.getParameter("surveyName");
		surveyName=URLDecoder.decode(surveyName,"utf-8");
		String tag=request.getParameter("tag");
		tag="2";
		SurveyDirectory directory=surveyDirectoryManager.createBySurvey(fromBankId,surveyName,tag);
		surveyId=directory.getId();
		return RELOADDESIGN;
	}
	
	private void buildSurveyHtml() throws Exception{
		HttpServletRequest request=Struts2Utils.getRequest();
		HttpServletResponse response=Struts2Utils.getResponse();
		String url = "";
		String name = "";
		ServletContext sc = ServletActionContext.getServletContext();

		String file_name = request.getParameter("file_name");
		url = "/design/my-collect.action?surveyId=402880ea4675ac62014675ac7b3a0000";
		// 这是生成的html文件名,如index.htm.
		name = "/survey.htm";
		name = sc.getRealPath(name);
		
		RequestDispatcher rd = sc.getRequestDispatcher(url);
		final ByteArrayOutputStream os = new ByteArrayOutputStream();

		final ServletOutputStream stream = new ServletOutputStream() {
			public void write(byte[] data, int offset, int length) {
				os.write(data, offset, length);
			}

			public void write(int b) throws IOException {
				os.write(b);
			}
		};
		
		final PrintWriter pw = new PrintWriter(new OutputStreamWriter(os,"utf-8"));

		HttpServletResponse rep = new HttpServletResponseWrapper(response) {
			public ServletOutputStream getOutputStream() {
				return stream;
			}

			public PrintWriter getWriter() {
				return pw;
			}
		};

//		rd.include(request, rep);
		rd.forward(request,rep);
		pw.flush();
		
		// 把jsp输出的内容写到xxx.htm
		File file = new File(name);
		if (!file.exists()) {
			file.createNewFile();
		}
		FileOutputStream fos = new FileOutputStream(file);
		
		os.writeTo(fos);
		fos.close();
	}

	
}
