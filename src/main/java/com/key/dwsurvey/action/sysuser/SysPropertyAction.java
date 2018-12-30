package com.key.dwsurvey.action.sysuser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import com.itextpdf.text.log.SysoCounter;
import com.key.common.utils.DiaowenProperty;
import com.key.common.utils.EncodeUtils;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.poi.util.StringUtil;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Namespaces;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.key.common.base.action.CrudActionSupport;
import com.key.common.utils.web.Struts2Utils;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 系统配置
 * @author KeYuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 *
 */
@Namespaces({@Namespace("/sy/system"),@Namespace("/sy/system/nosm")})
//@InterceptorRefs({ @InterceptorRef("paramsPrepareParamsStack") })
@Results({
	@Result(name=CrudActionSupport.INPUT,location="/WEB-INF/page/content/diaowen-system/property-input.jsp",type=Struts2Utils.DISPATCHER),
	@Result(name=CrudActionSupport.SUCCESS,location="/sy/system/sys-property!input.action",type=Struts2Utils.REDIRECT)
})
public class SysPropertyAction extends ActionSupport{
	
	@Override
	public String input() throws Exception {
		HttpServletRequest request = Struts2Utils.getRequest();
		
		String fileName="site.properties";
		ServletContext sc = Struts2Utils.getSession().getServletContext();
		String filePath = "/WEB-INF/classes/conf/site/".replace("/", File.separator);
		String fileRealPath = sc.getRealPath("/")+filePath+fileName;
		File file=new File(fileRealPath);
		InputStreamReader fr = new InputStreamReader(new FileInputStream(file),"UTF-8");
		
		Properties p = new Properties();  
	    try {
		    p.load(fr);
		    fr.close();

		   String adminEmail = p.getProperty("adminEmail");
		   String adminQQ = p.getProperty("adminQQ");
		   String adminTelephone = p.getProperty("adminTelephone");
		   String icpCode = p.getProperty("icpCode");
		   String tongjiCode = p.getProperty("tongjiCode");
		   String loginBgImg = p.getProperty("loginBgImg");

		   request.setAttribute("adminEmail", adminEmail);
		   request.setAttribute("adminQQ", adminQQ);
		   request.setAttribute("adminTelephone", adminTelephone);
		   request.setAttribute("icpCode", icpCode);
		   request.setAttribute("tongjiCode", tongjiCode);
		   request.setAttribute("loginBgImg", loginBgImg);
	    } catch (IOException e1) {
	        e1.printStackTrace();
	    }
	    return INPUT;
	}
	
	public String save() throws Exception {
		//管理员邮箱
		String adminEmail = Struts2Utils.getParameter("adminEmail");
		//管理员QQ
		String adminQQ = Struts2Utils.getParameter("adminQQ");
		//管理员电话
		String adminTelephone = Struts2Utils.getParameter("adminTelephone");
		//网站备案信息代码
		String icpCode = Struts2Utils.getParameter("icpCode");
		//网站备案信息代码
		String loginBgImg = Struts2Utils.getParameter("loginBgImg");

		String siteFilePath = "/WEB-INF/classes/conf/site/site.properties".replace("/", File.separator);

		Properties props = new Properties();
		props.put("adminEmail",adminEmail!=null?adminEmail:"");
		props.put("adminQQ",adminQQ!=null?adminQQ:"");
		props.put("adminTelephone",adminTelephone!=null?adminTelephone:"");
		props.put("icpCode",icpCode!=null?icpCode:"");
		props.put("loginBgImg",loginBgImg!=null?loginBgImg:"");

		writeData(siteFilePath, props);

		//写LOGO DATA文件
		String headerData="<a href=\"${ctx }\"><img alt=\"\" src=\"${ctx }/images/logo/LOGO.png\" align=\"middle\" height=\"46\" ><span class=\"titleTempSpan\">OSS</span></a> ";
		String headerDataPath="/WEB-INF/page/layouts/logo-img.jsp".replace("/", File.separator);
		writeData(headerDataPath, headerData);
		
		if(adminTelephone!=null && adminEmail!=null){
			//写footer文件
			String footer1="<div class=\"dw_footcopy\" style=\"font-size: 16px;color: gray;\"><p style=\"margin-bottom: 6px;\">"
	    	+"邮箱："+adminEmail+"&nbsp;&nbsp;&nbsp;电话："+adminTelephone+"&nbsp;&nbsp;&nbsp;"
	    	+"<a href=\"/\" style=\"color: gray;font-size: 16px;\">"+icpCode+"</a></p></div>";
			String footerPath="/WEB-INF/page/layouts/footer-1.jsp".replace("/", File.separator);
			writeData(footerPath, footer1);
			
			String adminInfo="<div style=\"color: gray;\"><h3 style=\"line-height: 40px;\">联系信息</h3><p style=\"line-height: 40px;\">邮箱："+adminEmail+"</p><p style=\"line-height: 40px;\">电话："+adminTelephone+"</p><p style=\"line-height: 40px;\">"+icpCode+"</p></div>";
			String adminInfoPath="/WEB-INF/page/layouts/admin-info.jsp".replace("/", File.separator);
			writeData(adminInfoPath, adminInfo);
		}
		
		if(loginBgImg!=null){
			String loginbgimg="<div class=\"m-logbg\"><img src=\"${ctx }"+loginBgImg+"\" style=\"margin-top:0px; margin-left: 0px; opacity: 1;\" width=\"100%\" ></div>";
			String loginbgimgPath="/WEB-INF/page/layouts/loginbgimg.jsp".replace("/", File.separator);
			writeData(loginbgimgPath, loginbgimg);
		}
		
		return SUCCESS;
	}
	
	
	private void writeData(String filePath,String data) {
		OutputStreamWriter fw = null;
		try {
			ServletContext sc = Struts2Utils.getSession().getServletContext();
			String fileRealPath = sc.getRealPath("/") + filePath;
			File file = new File(fileRealPath);
			if (!file.exists()) {
				file.createNewFile();
			}
			fw = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
			fw.write("<%@ page language=\"java\" contentType=\"text/html; charset=UTF-8\" pageEncoding=\"UTF-8\" %>");
			fw.write(data);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	private void writeData(String filePath,Properties props) {
		OutputStreamWriter fw = null;
		try {
			ServletContext sc = Struts2Utils.getSession().getServletContext();
			String fileRealPath = sc.getRealPath("/") + filePath;
			File file = new File(fileRealPath);
			if (!file.exists()) {
				file.createNewFile();
			}
			fw = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
			props.store(fw,"UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public static String string2Unicode(String string) {

		StringBuffer unicode = new StringBuffer();

		for (int i = 0; i < string.length(); i++) {

			// 取出每一个字符
			char c = string.charAt(i);

			// 转换为unicode
			unicode.append("\\u" + Integer.toHexString(c));
		}

		return unicode.toString();
	}


}
