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
	public static String WEBSITE_URL = "http://localhost:8282/";
//	private static Map<String, String> ctxPropertiesMap;

	public static String LICENSE_DESC = "913205080884374764";
	public static String LICENSE_ORGAN = "szqyzx";
	public static String LICENSE_EMAIL = "huahuihai@szqyzx.com.cn";
	public static String LICENSE_TYPENAME = "ENT";
	public static String LICENSE_DOMAIN = "szqyzx.com.cn";
	public static String LICENSE_CREATIONDATE = "2021-03-26 17:16:12";
	public static String LICENSE_SERVERID = "e88c6551-8e13-11eb-9916-6c4008a0c140";
	public static String LICENSE_ID = "e88c6552-8e13-11eb-9916-6c4008a0c140";
	public static String LICENSE_VERSION = "1";
	public static String LICENSE_EVALUATION = "0";
	public static String LICENSE_PUBLICKEY = "MIIBuDCCASwGByqGSM44BAEwggEfAoGBAP1/U4EddRIpUt9KnC7s5Of2EbdSPO9EAMMeP4C2USZpRV1AIlH7WT2NWPq/xfW6MPbLm1Vs14E7gB00b/JmYLdrmVClpJ+f6AR7ECLCT7up1/63xhv4O1fnxqimFQ8E+4P208UewwI1VBNaFpEy9nXzrith1yrv8iIDGZ3RSAHHAhUAl2BQjxUjC8yykrmCouuEC/BYHPUCgYEA9+GghdabPd7LvKtcNrhXuXmUr7v6OuqC+VdMCz0HgmdRWVeOutRZT+ZxBxCBgLRJFnEj6EwoFhO3zwkyjMim4TwWeotUfI0o4KOuHiuzpnWRbqN/C/ohNWLx+2J6ASQ7zKTxvqhRkImog9/hWuWfBpKLZl6Ae1UlZAFMO/7PSSoDgYUAAoGBAIISoszjO8RSKvqhNhSAkrF/ZRFHZV4uCeFYXUl+NMT2V+UJFtc6kx9ZwrVd05SV/ZSFu894evMabnAM3pbRGcJEKTqk3k8eljUWWfoCfey8fJ4vHGMe6bnD7C4rxfoB+8J3ZDioGHaR+aM1G/FpEy37XjWup1gqhJgSCCtNWw5f";
	public static String LICENSE_SIGN = "MCwCFHBf2HNS8pmeKx1dCpOCwi3rzjnkAhQMZFAOwSUt9cRupJ/3nJczGuKgWQ==";


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
