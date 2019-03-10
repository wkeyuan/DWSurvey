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

	public static String STORAGE_URL_PREFIX = null;


//	private static Map<String, String> ctxPropertiesMap;

	@Override
	protected void processProperties(
			ConfigurableListableBeanFactory beanFactoryToProcess,
			Properties props) throws BeansException {
		super.processProperties(beanFactoryToProcess, props);
		
		/*
		ctxPropertiesMap = new HashMap<String, String>();
		for (Object key : props.keySet()) {
			String keyStr = key.toString();
			String value = props.getProperty(keyStr);
			ctxPropertiesMap.put(keyStr, value);
		}
		*/
	}
/*
	public static String getContextProperty(String name) {
	    return ctxPropertiesMap.get(name);
	}
*/
	public void diaowenInit(){
		System.out.println(ServletActionContext.getContext());
	}

}
