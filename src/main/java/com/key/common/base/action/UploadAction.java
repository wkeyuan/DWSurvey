package com.key.common.base.action;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.key.common.plugs.aliyun.AliyunOSS;
import com.key.common.plugs.baiduyun.BaiduBOS;
import com.key.common.utils.DiaowenProperty;
import com.key.common.utils.web.Struts2Utils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.AllowedMethods;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Namespaces;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;

import com.key.common.utils.FileUtils;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.dispatcher.multipart.StrutsUploadedFile;
import org.apache.struts2.dispatcher.multipart.UploadedFile;

@Namespaces(@Namespace("/up"))
@AllowedMethods({"saveimage","saveFile","saveUpFile"})
public class UploadAction extends ActionSupport{
	
	private static final long serialVersionUID = -7990272086028152723L;

	private File[] files;
	private String[] fileNames;
	private String[] uploadContentTypes;

	/***
	 * 上传图片数据，非安全存储
	 * @return
	 * @throws Exception
	 */
	public String saveimage() throws Exception {
//		System.out.println("saveimgage");
		StringBuffer sb = new StringBuffer();
		HttpServletResponse response = Struts2Utils.getResponse();
		try {
			HttpServletRequest request = Struts2Utils.getRequest();
			String rootPath = request.getSession().getServletContext()
					.getRealPath("/");
			String savePath = "file" + File.separator + "images" + File.separator;

			if (files != null && files.length > 0) {

				String[] newNames = null;

				if("aliyunOSS".equals(DiaowenProperty.DWSTORAGETYPE) || "baiduBOS".equals(DiaowenProperty.DWSTORAGETYPE)){
					newNames = saveFileToYun(savePath, files, fileNames, false);
				}else {
					newNames = FileUtils.transferFile2(rootPath+File.separator+savePath, files, fileNames);
				}
				
				sb.append("{\"success\":\"true\"");
				sb.append(",\"filename\":\"");
				for (int i = 0; i < files.length; i++) {
					sb.append(fileNames[i]);
					sb.append("  ");
				}
				sb.append("\"");
				sb.append(",\"location\":\"");
				for (int i = 0; i < newNames.length; i++) {
					sb.append("/file/images/" + newNames[i]+ ";");
				}
				if(sb!=null&&StringUtils.isNotBlank(sb.toString())&&sb.toString().endsWith(";")){
					sb.deleteCharAt(sb.length()-1);
				}
				sb.append("\"");
			}
			
			sb.append("}");
		} catch (Exception e) {
			sb.append("{\"success\":\"false\",\"error\":\"上传失败\"}");
			e.printStackTrace();
		}
		response.setHeader("Content-Type", "text/plain;charset=UTF-8");
		response.getWriter().print(sb.toString());
		response.getWriter().close();
		return null;
	}

	/**
	 * 上传文件数据，非安全存储
	 **/
	public String saveFile() throws Exception {
		StringBuffer sb = new StringBuffer();
		HttpServletResponse response = Struts2Utils.getResponse();
		try {
			HttpServletRequest request = Struts2Utils.getRequest();
			MultiPartRequestWrapper multipartRequest = (MultiPartRequestWrapper) request;

			String basePath=request.getParameter("basepath");
			String rootPath = request.getSession().getServletContext().getRealPath("/");
			String savePath = "file" + File.separator + basePath + File.separator;

			if (files != null && files.length > 0) {

//				String[] newNames = FileUtils.transferFile2(savePath, files,fileNames);
				String[] newNames = null;
				
				if("aliyunOSS".equals(DiaowenProperty.DWSTORAGETYPE) || "baiduBOS".equals(DiaowenProperty.DWSTORAGETYPE)){
					newNames = saveFileToYun(savePath, files, fileNames, false);
				}else {
					newNames = FileUtils.transferFile2( rootPath+File.separator+savePath, files, fileNames);
				}
				
				sb.append("{\"success\":\"true\"");
				sb.append(",\"filename\":\"");
				for (int i = 0; i < files.length; i++) {
					sb.append(fileNames[i]);
					sb.append("  ");
				}
				sb.append("\"");
				sb.append(",\"location\":\"");
				for (int i = 0; i < newNames.length; i++) {
					sb.append("/file/"+basePath+"/" + newNames[i]+ ";");
				}
				if(sb!=null&&StringUtils.isNotBlank(sb.toString())&&sb.toString().endsWith(";")){
					sb.deleteCharAt(sb.length()-1);
				}
				sb.append("\"");
			}
			sb.append("}");
		} catch (Exception e) {
			sb.append("{\"success\":\"false\",\"error\":\"上传失败\"}");
			e.printStackTrace();
		}
		response.setHeader("Content-Type", "text/plain;charset=UTF-8");
		response.getWriter().print(sb.toString());
		response.getWriter().close();
		return null;
	}
	
	
	/**
	 * 上传文件数据，安全存储
	 * /WebRoot/WEB-INF/upfile
	 */
	public String saveUpFile() throws Exception {
		StringBuffer sb = new StringBuffer();
		HttpServletResponse response = Struts2Utils.getResponse();
		try {
			HttpServletRequest request = Struts2Utils.getRequest();
			MultiPartRequestWrapper multipartRequest = (MultiPartRequestWrapper) request;
			String basePath=request.getParameter("basepath");
			String rootPath = request.getSession().getServletContext().getRealPath("/");
			String savePath = "WEB-INF"+File.separator+"upfile" + File.separator;

			if (files != null && files.length > 0) {

//				String[] newNames = FileUtils.transferFile2(savePath, files,fileNames);
				String[] newNames = null;
				if("aliyunOSS".equals(DiaowenProperty.DWSTORAGETYPE) || "baiduBOS".equals(DiaowenProperty.DWSTORAGETYPE)){
					newNames = saveFileToYun(savePath, files, fileNames, true);
				}else {
					newNames = FileUtils.transferFile2(rootPath+savePath, files, fileNames);
				}
				
				sb.append("{\"success\":\"true\"");
				sb.append(",\"filename\":\"");
				for (int i = 0; i < files.length; i++) {
					sb.append(fileNames[i]);
					sb.append("  ");
				}
				sb.append("\"");
				sb.append(",\"location\":\"");
				for (int i = 0; i < newNames.length; i++) {
					sb.append("/WEB-INF/upfile/" + newNames[i]+ ";");
				}
				if(sb!=null&&StringUtils.isNotBlank(sb.toString())&&sb.toString().endsWith(";")){
					sb.deleteCharAt(sb.length()-1);
				}
				sb.append("\"");
			}
			sb.append("}");
		} catch (Exception e) {
			sb.append("{\"success\":\"false\",\"error\":\"上传失败\"}");
			e.printStackTrace();
		}
		response.setHeader("Content-Type", "text/plain;charset=UTF-8");
		response.getWriter().print(sb.toString());
//		response.getWriter().close();
		return null;
	}
	
	private static String[] saveFileToYun(String path, File[] files, String[] filenames, boolean isJm)
			throws IllegalStateException, IOException {
		if (files != null && files.length > 0) {
			String[] temp=new String[files.length];
			char[] str = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
					'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
					'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7',
					'8', '9' };
			for (int i = 0; i < files.length; i++) {
				try {
					StringBuffer fileName = new StringBuffer("");
					Random r = new Random();
					int pos = -1;
					for (int j = 0; j < 15; j++) {
						pos = Math.abs(r.nextInt(36));
						fileName.append(str[pos]);
					}
					fileName.append(filenames[i].substring(filenames[i].lastIndexOf(".")));
					System.out.println("path:"+path);
					
					if("aliyunOSS".equals(DiaowenProperty.DWSTORAGETYPE)){
						//保存到aliyun
						if(isJm){
							AliyunOSS.putObject(DiaowenProperty.UPLOADFILE_JM_BACKET, files[i], path + fileName.toString());
						}else{
							AliyunOSS.putObject(DiaowenProperty.UPLOADFILE_BACKET, files[i], path + fileName.toString());
						}
					}else if("baiduBOS".equals(DiaowenProperty.DWSTORAGETYPE)){
						//保存到aliyun
						if(isJm){
							BaiduBOS.putObject(DiaowenProperty.UPLOADFILE_JM_BACKET, files[i], path + fileName.toString());
						}else{
							BaiduBOS.putObject(DiaowenProperty.UPLOADFILE_BACKET, files[i], path + fileName.toString());
						}
					}
					temp[i]=fileName.toString();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			return temp;
		}
		return null;
	}


	public File[] getUploadify() {
		return this.files;
	}
	public void setUploadify(File[] upload) {
		this.files = upload;
	}
	public String[] getUploadifyFileName() {
		return this.fileNames;
	}
	public void setUploadifyFileName(String[] uploadFileName) {
		this.fileNames = uploadFileName;
	}
	public String[] getUploadifyContentType() {
		return this.uploadContentTypes;
	}
	public void setUploadifyContentType(String[] uploadContentType) {
		this.uploadContentTypes = uploadContentType;
	}
	
	
}
