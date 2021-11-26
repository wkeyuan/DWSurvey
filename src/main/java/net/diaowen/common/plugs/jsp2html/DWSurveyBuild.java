package net.diaowen.common.plugs.jsp2html;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import net.diaowen.common.utils.DiaowenProperty;
import net.diaowen.common.utils.DwWriteFile;
import net.diaowen.common.utils.SpringContextHolder;
import net.diaowen.dwsurvey.config.DWSurveyConfig;
import net.diaowen.dwsurvey.entity.SurveyDirectory;
import net.diaowen.dwsurvey.service.impl.SurveyDirectoryManagerImpl;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DWSurveyBuild {

	public void devSurvey(HttpServletRequest request,String surveyId,String type) throws Exception{
		String reqTarget = request.getScheme()+"://"+request.getServerName()+(request.getServerPort()==80?"":":"+request.getServerPort())+request.getContextPath();
		if("custom".equals(DWSurveyConfig.DWSURVEY_WEB_BACK_SITE_MODE)){
			reqTarget = DWSurveyConfig.DWSURVEY_WEB_BACK_SITE_URL;
		}
		reqTarget =reqTarget+"/toHtml";
		Map<String, String> map=new HashMap<String, String>();
		map.put("surid", surveyId);
		map.put("surtype", type);
		Connection connection = Jsoup.connect(reqTarget);
		connection.userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31");
		connection.data(map);
		connection.timeout(8000).get();
	}

	public void build(HttpServletRequest request, HttpServletResponse response,ServletContext sc){
		buildSc(request, response, sc );
	}

	private void buildSc(HttpServletRequest request, HttpServletResponse response,ServletContext sc) {
		String surveyId = request.getParameter("surid");
		String btype = request.getParameter("surtype");
		SurveyDirectoryManagerImpl surveyManager = SpringContextHolder.getBean(SurveyDirectoryManagerImpl.class);
		SurveyDirectory surveyDirectory = surveyManager.getSurvey(surveyId);
		if(surveyDirectory!=null){
			buildScBySurvey(request, response, sc, surveyId, btype, surveyDirectory);
		}
	}

	private void buildScBySurvey(HttpServletRequest request, HttpServletResponse response,ServletContext sc, String surveyId, String btype,
			SurveyDirectory surveyDirectory) {
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy/MM/dd/");
		Date createDate=surveyDirectory.getCreateDate();
		String url="/survey/answerSurvey.do?surveyId="+surveyId;
		String filePath="WEB-INF/wjHtml/"+dateFormat.format(createDate);
		String fileName=surveyId+".html";
		if(btype!=null && "m".equals(btype)){
			url="/survey/answerSurveryMobile.do?surveyId="+surveyId;
			filePath="WEB-INF/wjHtml/"+dateFormat.format(createDate);
			fileName="m_"+surveyId+".html";
		}
		executeBuildSurvey( request,response,sc,url,filePath,fileName);
	}

	private void executeBuildSurvey(HttpServletRequest request, HttpServletResponse response, ServletContext sc, String url,String filePath,String  fileName) {
		try{
			filePath = filePath.replace("/", File.separator);
			filePath = filePath.replace("\\", File.separator);
//			String fileRealPath = sc.getRealPath("/") + File.separator + filePath;
			String fileRealPath = filePath;
			RequestDispatcher rd = sc.getRequestDispatcher(url);
			final ByteArrayOutputStream os = new ByteArrayOutputStream();

			final ServletOutputStream stream = new ServletOutputStream() {
				@Override
				public boolean isReady() {
					return false;
				}

				@Override
				public void setWriteListener(WriteListener writeListener) {

				}

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
			rd.forward(request, rep);
			pw.flush();
			DwWriteFile.writeOS(fileName, fileRealPath, os);
			response.setContentType("text/html;charset=utf-8");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
