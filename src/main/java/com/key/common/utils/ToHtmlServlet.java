package com.key.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import com.key.common.plugs.aliyun.AliyunOSS;
import com.key.common.plugs.baiduyun.BaiduBOS;

public class ToHtmlServlet extends HttpServlet {
	
	private static final long serialVersionUID = -9118659583515649615L;
	
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext sc = getServletContext();
		String url=request.getParameter("url");
		String filePath=request.getParameter("filePath");
		String fileName=request.getParameter("fileName");
		System.out.println(url+":"+filePath+":"+fileName);
		//url = "/design/my-survey-design!previewDev.action?surveyId=402880ea4675ac62014675ac7b3a0000";
		// 这是生成的html文件名,如index.htm
		filePath = filePath.replace("/", File.separator);
		filePath = filePath.replace("\\", File.separator);
		String fileRealPath = sc.getRealPath("/") +File.separator+ filePath;
		System.out.println(fileRealPath);
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

		//rd.include(request, rep);
		rd.forward(request, rep);
		pw.flush();
		
		
		// 把jsp输出的内容写到xxx.htm
		File file=jspWriteLocal(fileName, fileRealPath, os);
		
		if("aliyunOSS".equals(DiaowenProperty.DWSTORAGETYPE)){
			// 阿里云支持 将文件写入到aliyun oss
			AliyunOSS.putObject(DiaowenProperty.WENJUANHTML_BACKET,file,fileName);
			//delete本地服务器文件
			file.delete();
		}else if("baiduBOS".equals(DiaowenProperty.DWSTORAGETYPE)){
			BaiduBOS.putObject(DiaowenProperty.WENJUANHTML_BACKET,file,fileName);
			//delete本地服务器文件
			file.delete();
		}
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print("<p align=center><font size=3 color=red>首页已经成功生成！Andrew</font></p>");
	}

	
	/**
	 * JSP内容输入到本地
	 * @param fileName
	 * @param fileRealPath
	 * @param os
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	private File jspWriteLocal(String fileName, String fileRealPath,
			final ByteArrayOutputStream os) throws IOException,
			FileNotFoundException {
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
		return file;
	}
	

}