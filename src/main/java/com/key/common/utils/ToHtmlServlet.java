package com.key.common.utils;

import java.io.*;
import java.util.Properties;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import com.itextpdf.text.log.SysoCounter;
import com.key.common.plugs.aliyun.AliyunOSS;
import com.key.common.plugs.baiduyun.BaiduBOS;
import com.key.common.utils.web.Struts2Utils;
import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class ToHtmlServlet extends HttpServlet {
	
	private static final long serialVersionUID = -9118659583515649615L;
	private static String contentCopyright = null;
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext sc = getServletContext();
		String url=request.getParameter("url");
		String filePath=request.getParameter("filePath");
		String fileName=request.getParameter("fileName");
		//url = "/design/my-survey-design!previewDev.action?surveyId=402880ea4675ac62014675ac7b3a0000";
		// 这是生成的html文件名,如index.htm
		filePath = filePath.replace("/", File.separator);
		filePath = filePath.replace("\\", File.separator);
		String fileRealPath = sc.getRealPath("/") +File.separator+ filePath;

//		String content = exeDown(url);
//		printStream(fileRealPath,fileName,content);
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

		final PrintWriter pw = new PrintWriter(new OutputStreamWriter(os,"UTF-8"));
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
		String document = "异常未写入";
		try{
            document = pdocument(fileName,fileRealPath,os);
        }catch (Exception e){
			e.printStackTrace();
			document = e.getMessage();
        }
		printStream(fileRealPath,fileName,document);

		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print("<p align=center><font size=3 color=red>首页已经成功生成！Andrew</font></p>");
	}

	private String pdocument(String fileName,String fileRealPath,ByteArrayOutputStream os) throws Exception{
		Document document = Jsoup.parse(os.toString());
		Elements elements = document.getElementsByTag("a");

		String contentCopyrightStr = "";
		//自定义问卷内容的版权，可以在设置中设置名称，然后就自动显示
		if(DiaowenProperty.contentCopyright!=null && !"".equals(DiaowenProperty.contentCopyright)){
			contentCopyrightStr = new StringBuffer(">\";yarg :roloc;enon :noiuuouutarocuuouued-txet\"=elyts \"/\"=ferh a< 权uuouu版容uuouu内").reverse().toString().replaceAll("uuouu","") +DiaowenProperty.contentCopyright+"</a>";
		}
		// 修改说明：
		// 前部分是官网的保留声明，虽然这块目前是法律的灰色地带，但从维护一个健康的开源社区，从帮助到您的角度，请您能保留下来。
		// 后部分 contentCopyright 是关于问卷内容的版权声明，这个不用说肯定属于您或您公司，写法示例如：某某网
		Elements elements1 = document.getElementsByClass("footer-copyright").remove();
		document.body().append(new StringBuffer(";psbn&>a/<yeuuouuvruSwuuouuD>\";yarg :roloc;enon :noitauuouuroced-txet\"=elyts \"tuuouuen.newoauuouuid.www//:ptuuouuth\"=ferh a<  yb deruuouuewoP  >\";xp5 :mottob-gniddap;yauuouurg :roloc\"=elyts \"retuuouuoof\"=elor-atad \"thgiryuuouupoc-retuuouuoof\"=ssalc vuuouuid<").reverse().toString().replaceAll("uuouu","") + contentCopyrightStr + " </div>");
		// 把jsp输出的内容写到xxx.htm

		return document.html();
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
		//fos.write("http://www.diaowen.net/wenjuan/u2qhavhgufi.html".getBytes());
		fos.close();
		return file;
	}

	private File jspWriteLocal(String fileName, String fileRealPath,
							   final String doc) throws IOException,
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
//		os.writeTo(fos);
		OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
		System.out.println(doc);
		osw.write(doc);
		//fos.write("http://www.diaowen.net/wenjuan/u2qhavhgufi.html".getBytes());
		fos.close();
		return file;
	}

	public static String exeDown(String url){
		String content="";
		Document doc;
		try {
			doc = Jsoup.connect(url)
					.data("query", "nnd")
					.userAgent("Mozilla")
					.cookie("auth", "token")
					.timeout(5000)
					.post();
			content=doc.html();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;
	}


	public void printStream(String savePath,String fileName,String content) throws IOException{
		createFile(savePath);
		FileOutputStream out=null;
		try {
			out=new FileOutputStream(savePath+File.separator+fileName);
			out.write(content.getBytes());
		}catch (Exception e){
			e.printStackTrace();;
		}finally{
			if(out!=null){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static void createFile(String rootPath) {
		File file=new File(rootPath);
		if(!file.exists()){
			file.mkdirs();
		}
	}


}