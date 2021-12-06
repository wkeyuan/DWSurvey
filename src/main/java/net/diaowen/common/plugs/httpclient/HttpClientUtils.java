package net.diaowen.common.plugs.httpclient;

import net.diaowen.common.utils.DiaowenProperty;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.http.HttpHeaders;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class HttpClientUtils {

    public static String buildHttpUrl(String url) {
        /*if (url == null) return DiaowenProperty.API_URL_BASE;
        if (url.startsWith("http:") || url.startsWith("https:")) return url;
        return DiaowenProperty.API_URL_BASE + url;*/
        return url;
    }

    /**
     * 请求最基本的封装，带有认证信息及地址识别
     *
     * @param url 一个除去根路径的相对地址
     * @return
     * @throws URISyntaxException
     */
    public static URIBuilder uriBuilder(String url) throws URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder(url);
        return uriBuilder;
    }

    /**
     * 调用些方法不用再传认证信息
     *
     * @param url    一个除去根路径的相对地址
     * @param params
     * @return
     * @throws URISyntaxException
     */
    public static URIBuilder uriBuilderAuth(String url, Map params)
            throws URISyntaxException {
        URIBuilder uriBuilder = uriBuilder(url);
        if (params != null) {
            for (Object key : params.keySet()) {
                if (params.get(key) != null) {
                    uriBuilder.addParameter(key.toString(), params.get(key).toString());
                }
            }
        }
        return uriBuilder;
    }

    public static URIBuilder uriBuilder(String url, Map params)
            throws URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder(url);
        if (params != null) {
            for (Object key : params.keySet()) {
                if (params.get(key) != null) {
                    uriBuilder.addParameter(key.toString(), params.get(key).toString());
                }
            }
        }
        return uriBuilder;
    }


    /**
     * 创建HttpGet 不用带认证参数
     *
     * @param url     相对路径
     * @param params  方法参数
     * @param headers
     * @return
     * @throws URISyntaxException
     */
    public static HttpGet buildHttpGet(String url, Map<String, String> params,
                                       Map<String, String> headers) throws URISyntaxException {
        URIBuilder uriBuilder = uriBuilder(url, params);
        HttpGet httpGet = new HttpGet(uriBuilder.build());
        if (headers == null) {
            headers = new HashMap<String, String>();
        }
        headers.putAll(HttpClientUtils.buildHeaderMap());
        for (String key : headers.keySet()) {
            httpGet.addHeader(key, headers.get(key));
        }
        httpGet.addHeader("Content-Type", "text/html;charset=UTF-8");
        return httpGet;
    }

    /**
     * 创建httpPost 不用带认证参数
     *
     * @param url     相对路径
     * @param params  方法参数
     * @param headers
     * @return
     * @throws URISyntaxException
     */
    public static HttpPost buildHttpPost(String url, Map<String, String> params,
                                         Map<String, String> headers) throws URISyntaxException {
        URIBuilder uriBuilder = uriBuilder(url, params);
        HttpPost httpPost = new HttpPost(uriBuilder.build());
        if (headers == null) {
            headers = new HashMap<String, String>();
        }
        headers.putAll(HttpClientUtils.buildHeaderMap());
        for (String key : headers.keySet()) {
            httpPost.addHeader(key, headers.get(key));
        }
        httpPost.addHeader("Conetent-Type", "application/json");
        return httpPost;
    }


    public static HttpPost buildHttpPost(String url) throws URISyntaxException {
        return buildHttpPost(url, null, null);
    }


    public static HttpPost buildHttpPostFormJson(String url, String formJson) throws URISyntaxException {
        return buildHttpPostFormJson(url, null, null, formJson);
    }


    public static HttpPost buildHttpPostFormJson(String url, Map<String, String> params,
                                                 Map<String, String> headers, String formJson) throws URISyntaxException {
        HttpPost httpPost = buildHttpPost(url, params, headers);
        StringEntity entity = new StringEntity(formJson, "utf-8");//解决中文乱码问题
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        httpPost.setEntity(entity);
        return httpPost;
    }

    public static HttpPut buildHttpPutFormJson(String url, String formJson) throws URISyntaxException {
        HttpPut httpPut = buildHttpPut(url, null, null);
        StringEntity entity = new StringEntity(formJson, "utf-8");
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        httpPut.setEntity(entity);
        return httpPut;
    }

    public static HttpPatch buildHttpPatch(String url, String formJson) throws URISyntaxException {
        return buildHttpPatch(url, null, null, formJson);
    }

    public static HttpPatch buildHttpPatch(String url, Map<String, String> params, Map<String, String> headers) throws URISyntaxException {
        URIBuilder uriBuilder = uriBuilder(url, params);
        HttpPatch httpPatch = new HttpPatch(uriBuilder.build());
        if (headers == null) {
            headers = new HashMap<String, String>();
        }
        headers.putAll(HttpClientUtils.buildHeaderMap());
        for (String key : headers.keySet()) {
            httpPatch.addHeader(key, headers.get(key));
        }
        return httpPatch;
    }

    public static HttpPatch buildHttpPatch(String url, Map<String, String> params,
                                           Map<String, String> headers, String formJson) throws URISyntaxException {
        URIBuilder uriBuilder = uriBuilder(url, params);
        HttpPatch httpPatch = new HttpPatch(uriBuilder.build());
        if (headers == null) {
            headers = new HashMap<String, String>();
        }
        headers.putAll(HttpClientUtils.buildHeaderMap());
        for (String key : headers.keySet()) {
            httpPatch.addHeader(key, headers.get(key));
        }
        StringEntity entity = new StringEntity(formJson, "utf-8");//解决中文乱码问题
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        httpPatch.setEntity(entity);
        return httpPatch;
    }


    public static HttpPut buildHttpPut(String url, Map<String, String> params,
                                       Map<String, String> headers) throws URISyntaxException {
        URIBuilder uriBuilder = uriBuilder(url, params);
        HttpPut httpPut = new HttpPut(uriBuilder.build());
        if (headers == null) {
            headers = new HashMap<String, String>();
        }
        headers.putAll(HttpClientUtils.buildHeaderMap());
        for (String key : headers.keySet()) {
            httpPut.addHeader(key, headers.get(key));
        }
        return httpPut;
    }

    public static HttpPut buildHttpPut(String url, String formJson) throws URISyntaxException {
        return buildHttpPut(url, null, null, formJson);
    }


    public static HttpPut buildHttpPut(String url, Map<String, String> params,
                                       Map<String, String> headers, String formJson) throws URISyntaxException {
        URIBuilder uriBuilder = uriBuilder(url, params);
        HttpPut httpPut = new HttpPut(uriBuilder.build());
        if (headers == null) {
            headers = new HashMap<String, String>();
        }
        headers.putAll(HttpClientUtils.buildHeaderMap());
        for (String key : headers.keySet()) {
            httpPut.addHeader(key, headers.get(key));
        }
        StringEntity entity = new StringEntity(formJson, "utf-8");//解决中文乱码问题
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        httpPut.setEntity(entity);
        return httpPut;
    }

    public static HttpDelete buildHttpDelete(String url, Map<String, String> params) throws URISyntaxException {
        return buildHttpDelete(url, params, null);
    }

    public static HttpDelete buildHttpDelete(String url, Map<String, String> params,
                                             Map<String, String> headers) throws URISyntaxException {
        URIBuilder uriBuilder = uriBuilder(url, params);
        HttpDelete httpDelete = new HttpDelete(uriBuilder.build());
        if (headers == null) {
            headers = new HashMap<String, String>();
        }
        headers.putAll(HttpClientUtils.buildHeaderMap());
        for (String key : headers.keySet()) {
            httpDelete.addHeader(key, headers.get(key));
        }
        return httpDelete;
    }

    public static HttpHeaders buildHeader() {
        HttpHeaders headers = new HttpHeaders();
// 		headers.add("openId", getOpenId());
//        headers.add("token",getToken());
        return headers;
    }

    public static Map<String, String> buildHeaderMap() {
        Map<String, String> headers = new HashMap<String, String>();
//	 	 headers.put("openId", getOpenId());
//        headers.put("token", getToken());
        return headers;
    }

    public static String getOpenId() {
        return getSessionAttribute("openId");
    }

    public static String getToken(){
        return getSessionAttribute("token");
    }

    public static String getUserId(){
    	return getSessionAttribute("userId");
    }

    public static String getSessionAttribute(String attributeName) {
        Subject subject = SecurityUtils.getSubject();
        if (subject != null) {
            Session shiroSession = subject.getSession();
            Object attributeValue = shiroSession.getAttribute(attributeName);
            if (attributeValue != null) {
                return attributeValue.toString();
            }
        }
        return null;
    }

    public static String setSessionAttribute(String attributeName,String value) {
        Subject subject = SecurityUtils.getSubject();
        if (subject != null) {
            Session shiroSession = subject.getSession();
            Object attributeValue = shiroSession.getAttribute(attributeName);
            if (attributeValue != null) {
                return attributeValue.toString();
            }
        }
        return null;
    }

}
