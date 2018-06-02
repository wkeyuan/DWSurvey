package com.key.common.base.action;

import com.key.common.utils.FileUtils;
import com.key.common.utils.web.Struts2Utils;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.AllowedMethods;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Namespaces;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

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

				newNames = FileUtils.transferFile2( rootPath+File.separator+savePath, files, fileNames);
				
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

				newNames = FileUtils.transferFile2( rootPath+File.separator+savePath, files, fileNames);
				
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
				newNames = FileUtils.transferFile2(rootPath+savePath, files, fileNames);
				
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
