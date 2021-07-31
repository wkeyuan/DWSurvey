package com.key.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.apache.struts2.ServletActionContext;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.key.common.utils.web.Struts2Utils;

public class JspToHtml {

	public void postJspToHtml(String postUrl, String filePath,String fileName, String sType, String surveyId) throws Exception{
		HttpServletRequest request=Struts2Utils.getRequest();
		//${pageContext.request.scheme}://${pageContext.request.serverName }:${pageContext.request.serverPort} pageContext.request.contextPath
		String reqTarget = DiaowenProperty.DW_SERVER_WEBSITE_URL;
		if(reqTarget==null) {
			reqTarget = request.getScheme()+"://"+request.getServerName()+(request.getServerPort()==80?"":":"+request.getServerPort())+request.getContextPath();
		}
		reqTarget =reqTarget+"/toHtml";
		//?url="+postUrl+"&filePath="+filePath+"&fileName="+fileName;
		Map<String, String> map=new HashMap<String, String>();
		map.put("filePath", filePath);
		map.put("fileName", fileName);
		map.put("sType", sType);
		map.put("surId", surveyId);
		Connection connection = Jsoup.connect(reqTarget);
		connection.userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31");
		connection.data(map);
		Document doc=connection.timeout(8000).get();
	}

	public void jspWriteToHtml(String url, String filePath,String fileName) throws Exception {
		HttpServletRequest request = Struts2Utils.getRequest();
		HttpServletResponse response = Struts2Utils.getResponse();
		ServletContext sc = ServletActionContext.getServletContext();
		url = "/my-survey-design!previewDev.action?surveyId=402880ea4675ac62014675ac7b3a0000";
		// 这是生成的html文件名,如index.htm
		filePath = filePath.replace("/", File.separator);
		filePath = filePath.replace("\\", File.separator);
		String fileRealPath = sc.getRealPath("/") + filePath;
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

		final PrintWriter pw = new PrintWriter(new OutputStreamWriter(os,
				"UTF-8"));

		HttpServletResponse rep = new HttpServletResponseWrapper(response) {
			public ServletOutputStream getOutputStream() {
				return stream;
			}

			public PrintWriter getWriter() {
				return pw;
			}
		};

		rd.forward(request, response);
		pw.flush();

		File file2 = new File(fileRealPath);
		if (!file2.exists() || !file2.isDirectory()) {
			file2.mkdirs();
		}
		File file = new File(fileRealPath + fileName);
		if (!file.exists()) {
			file.createNewFile();
		}
		FileOutputStream fos = new FileOutputStream(file);
		os.writeTo(fos);
		fos.close();
	}
}
