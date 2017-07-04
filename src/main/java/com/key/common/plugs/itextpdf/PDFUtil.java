package com.key.common.plugs.itextpdf;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import org.springframework.context.i18n.LocaleContextHolder;
import org.w3c.tidy.Configuration;
import org.w3c.tidy.Tidy;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.itextpdf.text.pdf.BaseFont;

public class PDFUtil {
	
	public static void main(String[] args) {
		String url="http://localhost:8080/survey/pdfTest.jsp";
		try {
			PDFUtil.exportPdfFile(url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// 导出pdf add by huangt 2012.6.1
	public static File exportPdfFile(String urlStr) throws Exception {
		// String outputFile = this.fileRoot + "/" +
		// ServiceConstants.DIR_PUBINFO_EXPORT + "/" + getFileName() + ".pdf";
		String outputFile = "D:/Documents/test3.pdf";
		OutputStream os = new FileOutputStream(outputFile);
		ITextRenderer renderer = new ITextRenderer();

		String str = getHtmlFile(urlStr);
		renderer.setDocumentFromString(str);
		ITextFontResolver fontResolver = renderer.getFontResolver();

		fontResolver.addFont("F:/keyworkspace/survey/src/conf/itextpdf/simsun.ttc",BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
		// // 宋体字
		fontResolver.addFont("F:/keyworkspace/survey/src/conf/itextpdf/ARIALUNI.TTF",BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);// 宋体字
		renderer.layout();

		renderer.createPDF(os);

		os.flush();
		os.close();
		return new File(outputFile);

	}

	// 读取页面内容 add by huangt 2012.6.1
	public static String getHtmlFile(String urlStr) throws Exception {
		URL url;
		if (urlStr.indexOf("?") != -1) {
			urlStr = urlStr + "&locale="
					+ LocaleContextHolder.getLocale().toString();
		} else {
			urlStr = urlStr + "?locale="
					+ LocaleContextHolder.getLocale().toString();
		}
		url = new URL(urlStr);

		URLConnection uc = url.openConnection();
		InputStream is = uc.getInputStream();

		Tidy tidy = new Tidy();

		OutputStream os2 = new ByteArrayOutputStream();
		tidy.setXHTML(true); // 设定输出为xhtml(还可以输出为xml)
		tidy.setCharEncoding(Configuration.UTF8); // 设定编码以正常转换中文
		tidy.setTidyMark(false); // 不设置它会在输出的文件中给加条meta信息
		tidy.setXmlPi(true); // 让它加上<?xml version="1.0"?>
		tidy.setIndentContent(true); // 缩进，可以省略，只是让格式看起来漂亮一些
		tidy.parse(is, os2);

		is.close();

		// 解决乱码 --将转换后的输出流重新读取改变编码
		String temp;
		StringBuffer sb = new StringBuffer();
		BufferedReader in = new BufferedReader(new InputStreamReader(
				new ByteArrayInputStream(
						((ByteArrayOutputStream) os2).toByteArray()), "utf-8"));
		while ((temp = in.readLine()) != null) {
			sb.append(temp);
		}
		return sb.toString();
	}
}