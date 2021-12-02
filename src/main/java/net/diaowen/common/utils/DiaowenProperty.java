package net.diaowen.common.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 *
 * @author keyuan
 *
 */
public class DiaowenProperty extends
		PropertyPlaceholderConfigurer {

	public static String STORAGE_URL_PREFIX = null;
	public static String SURVEYURL_MODE = "auto";
	public static String WEBSITE_URL = "http://192.168.3.20:8080/#";
//	private static Map<String, String> ctxPropertiesMap;

	public static String LICENSE_DESC = null;
	public static String LICENSE_ORGAN = null;
	public static String LICENSE_EMAIL = null;
	public static String LICENSE_TYPENAME = null;
	public static String LICENSE_DOMAIN = null;
	public static String LICENSE_CREATIONDATE = null;
	public static String LICENSE_SERVERID = null;
	public static String LICENSE_ID = null;
	public static String LICENSE_VERSION = null;
	public static String LICENSE_EVALUATION = null;
	public static String LICENSE_PUBLICKEY = null;
	public static String LICENSE_SIGN = null;

	@Override
	protected void processProperties(
			ConfigurableListableBeanFactory beanFactoryToProcess,
			Properties props) throws BeansException {
		super.processProperties(beanFactoryToProcess, props);

		/*STORAGE_URL_PREFIX = props.getProperty("dw.storage.url_prefix");

		SURVEYURL_MODE = props.getProperty("dw.surveyurl.mode");
		WEBSITE_URL = props.getProperty("dw.website.url");

		LICENSE_DESC = props.getProperty("dw.license.description");
		LICENSE_ORGAN = props.getProperty("dw.license.organisation");
		LICENSE_EMAIL = props.getProperty("dw.license.email");
		LICENSE_TYPENAME = props.getProperty("dw.license.licenseTypeName");
		LICENSE_CREATIONDATE = props.getProperty("dw.license.creationDate");
		LICENSE_DOMAIN = props.getProperty("dw.license.licenseDomain");
		LICENSE_SERVERID = props.getProperty("dw.license.serverId");
		LICENSE_ID = props.getProperty("dw.license.licenseId");
		LICENSE_VERSION = props.getProperty("dw.license.licenseVersion");
		LICENSE_EVALUATION = props.getProperty("dw.license.evaluation");
		LICENSE_PUBLICKEY = props.getProperty("dw.license.publickey");
		LICENSE_SIGN = props.getProperty("dw.license.sign");
		*/
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

	}

}
