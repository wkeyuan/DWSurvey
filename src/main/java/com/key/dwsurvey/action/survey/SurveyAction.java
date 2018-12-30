package com.key.dwsurvey.action.survey;

import com.key.common.plugs.ipaddr.IPService;
import com.key.common.plugs.zxing.ZxingUtil;
import com.key.common.utils.DiaowenProperty;
import com.key.common.utils.web.Struts2Utils;
import com.key.dwsurvey.entity.SurveyDirectory;
import com.key.dwsurvey.entity.SurveyStyle;
import com.key.dwsurvey.service.QuestionManager;
import com.key.dwsurvey.service.SurveyAnswerManager;
import com.key.dwsurvey.service.SurveyDirectoryManager;
import com.key.dwsurvey.service.SurveyStyleManager;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.convention.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
//import com.sun.image.codec.jpeg.JPEGCodec;
//import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * 问卷 action
 * @author KeYuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://www.dwsurvey.net
 *
 */
@Namespace("/")
@InterceptorRefs({ @InterceptorRef("paramsPrepareParamsStack")})
@Results({
	@Result(name=SurveyAction.INDEXJSP,location="/index.jsp",type=Struts2Utils.DISPATCHER),
	@Result(name=SurveyAction.ANSERSURVEY,location="/WEB-INF/page/content/diaowen-design/answer-survey-p.jsp",type=Struts2Utils.DISPATCHER),
	@Result(name=SurveyAction.ANSERSURVEY_MOBILE,location="/WEB-INF/page/content/diaowen-design/answer-survey-m.jsp",type=Struts2Utils.DISPATCHER)
})

@AllowedMethods({"answerSurvey","answerSurveryMobile","surveyModel","answerTD","ajaxCheckSurvey"})
public class SurveyAction extends ActionSupport{
	
	protected final static String INDEXJSP="indexJsp";
	protected final static String ANSERSURVEY="answerSurvey";
	protected final static String ANSERSURVEY_MOBILE="answerSurveyMobile";
	protected final static String SURVEYMODEL="surveyModel";
	protected final static String ANSWER_INPUT_ERROR = "answerInputError";//已经答过，在间隔时间内
	protected final static String ANSWER_INPUT_RULE = "answer_input_rule";//令牌
	
	@Autowired
	private SurveyDirectoryManager surveyDirectoryManager;
	@Autowired
	private QuestionManager questionManager;
	@Autowired
	private SurveyStyleManager surveyStyleManager;
	@Autowired
	private IPService ipService;
	@Autowired
	private SurveyAnswerManager surveyAnswerManager;

	private String sid;
	private String surveyId;
	//外部回答公共访问路径--静态访问
	@Override
	public String execute() throws Exception {
		HttpServletRequest request=Struts2Utils.getRequest();
		HttpServletResponse response=Struts2Utils.getResponse();
		String htmlPath="http://wj.diaowen.net/test";
		//request.getRequestDispatcher(htmlPath).forward(request, response);
		response.sendRedirect(htmlPath);
		return NONE;
	}

	//问卷的动态访问方式
	public String answerSurvey() throws Exception {
		HttpServletRequest request = Struts2Utils.getRequest();
		SurveyDirectory survey=surveyDirectoryManager.getSurvey(surveyId);
		buildSurvey(survey);
		return ANSERSURVEY;
	}
	//问卷动态访问-移动端
	public String answerSurveryMobile() throws Exception {
		HttpServletRequest request = Struts2Utils.getRequest();
		SurveyDirectory survey=surveyDirectoryManager.getSurvey(surveyId);
		buildSurvey(survey);
	    return ANSERSURVEY_MOBILE;
	}
	
	private void buildSurvey(SurveyDirectory survey) {
		if (survey==null)
		survey=surveyDirectoryManager.getSurvey(surveyId);
		survey.setQuestions(questionManager.findDetails(surveyId, "2"));
		Struts2Utils.setReqAttribute("survey", survey);
		SurveyStyle surveyStyle=surveyStyleManager.getBySurveyId(surveyId);
		Struts2Utils.setReqAttribute("surveyStyle", surveyStyle);
		Struts2Utils.setReqAttribute("prevHost", DiaowenProperty.STORAGE_URL_PREFIX);
	}

	//回答问卷的二维码
	public String answerTD() throws Exception{
	    	HttpServletRequest request=Struts2Utils.getRequest();
	    	HttpServletResponse response=Struts2Utils.getResponse();
	    	
	    	String down=request.getParameter("down");

			String baseUrl = "";
			baseUrl = request.getScheme() +"://" + request.getServerName()
					+ (request.getServerPort() == 80 ? "" : ":" +request.getServerPort())
					+ request.getContextPath();

	    	try{
				String encoderContent=baseUrl+"/response!answerMobile.action?surveyId="+surveyId;
				ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
				BufferedImage twoDimensionImg = ZxingUtil.qRCodeCommon(encoderContent, "jpg", 7);
				ImageIO.write(twoDimensionImg, "jpg", jpegOutputStream);
				if(down==null){
					response.setHeader("Cache-Control", "no-store");
					response.setHeader("Pragma", "no-cache");
					response.setDateHeader("Expires", 0);
					response.setContentType("image/jpeg");
					ServletOutputStream responseOutputStream = response.getOutputStream();
					responseOutputStream.write(jpegOutputStream.toByteArray());
					responseOutputStream.flush();
					responseOutputStream.close();
				}else{
					response.addHeader("Content-Disposition", "attachment;filename=" + new String(("diaowen_"+surveyId+".jpg").getBytes()));
					byte[] bys = jpegOutputStream.toByteArray();
					response.addHeader("Content-Length", "" + bys.length);
					ServletOutputStream responseOutputStream = response.getOutputStream();
					response.setContentType("application/octet-stream");
					responseOutputStream.write(bys);
					responseOutputStream.flush();
					responseOutputStream.close();
				}
			}catch (Exception e){
				e.printStackTrace();
			}

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
