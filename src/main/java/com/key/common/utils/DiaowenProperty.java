package com.key.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.key.common.utils.web.Struts2Utils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import com.baidu.ueditor.ConfigManager;
import com.sun.org.apache.xml.internal.security.Init;

import javax.servlet.ServletContext;

/**
 * 
 * @author keyuan
 *
 */
public class DiaowenProperty extends
		PropertyPlaceholderConfigurer {

	public static String DWSTORAGETYPE = null;
	public static String ACCESS_KEY_ID = null;
	public static String SECRET_ACCESS_KEY = null;
	public static String ENDPOINT = null;
	public static String STORAGE_URL_PREFIX = null;

	public static String WENJUANHTML_BACKET = null;
	public static String UPLOADFILE_BACKET = null;
	public static String UPLOADFILE_JM_BACKET = null;

	public static String contentCopyright = null;
//	private static Map<String, String> ctxPropertiesMap;

	@Override
	protected void processProperties(
			ConfigurableListableBeanFactory beanFactoryToProcess,
			Properties props) throws BeansException {
		super.processProperties(beanFactoryToProcess, props);
		
		DWSTORAGETYPE = props.getProperty("dw.storage.type");
		ACCESS_KEY_ID = props.getProperty("dw.yunos.access_keyid");
		SECRET_ACCESS_KEY = props.getProperty("dw.yunos.access_keysecret");
		ENDPOINT = props.getProperty("dw.yunos.endpoint");
//		FILE_BACKET_DOMAIN = props.getProperty("dw.yunos.file_backet_domain");
		STORAGE_URL_PREFIX = props.getProperty("dw.storage.url_prefix");
		
		WENJUANHTML_BACKET = props.getProperty("dw.yunos.wenjuan_html_backet");
		UPLOADFILE_BACKET = props.getProperty("dw.yunos.upload_file_backet");
		UPLOADFILE_JM_BACKET = props.getProperty("dw.yunos.upload_file_jm_backet");
		/*
		ctxPropertiesMap = new HashMap<String, String>();
		for (Object key : props.keySet()) {
			String keyStr = key.toString();
			String value = props.getProperty(keyStr);
			ctxPropertiesMap.put(keyStr, value);
		}
		*/
		contentCopyright = props.getProperty("contentCopyright");
		if(contentCopyright!=null){
			contentCopyright = unicode2String(contentCopyright);
		}
		System.out.println("contentCopyright:"+contentCopyright);
	}
/*
	public static String getContextProperty(String name) {
	    return ctxPropertiesMap.get(name);
	}
*/
	public void diaowenInit(){
		System.out.println("系统初始化方法。。。");
		System.out.println(ServletActionContext.getContext());
	}

	/**
	 * unicode 转字符串
	 */
	public static String unicode2String(String unicode) {

		StringBuffer string = new StringBuffer();

		String[] hex = unicode.split("\\\\u");

		for (int i = 1; i < hex.length; i++) {

			// 转换出每一个代码点
			int data = Integer.parseInt(hex[i], 16);

			// 追加成string
			string.append((char) data);
		}

		return string.toString();
	}
}
