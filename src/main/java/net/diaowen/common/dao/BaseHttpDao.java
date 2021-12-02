package net.diaowen.common.dao;

import net.diaowen.common.plugs.httpclient.HttpClientUtils;
import net.sf.json.JSONObject;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
//import org.springframework.http.HttpEntity;

/**
 * @author keyuan
 * @ClassName: BaseHttpDao
 * @Description: TODO(Http请求基础类，封装了各类请求方法)
 * @date 2016年9月17日 上午11:21:41
 */
public abstract class BaseHttpDao extends SuperHttpDao {

    private static Logger logger = LogManager.getLogger(BaseHttpDao.class.getName());

    @Override
    public String doGet(String url) {
        try {
            HttpGet httpGet = HttpClientUtils.buildHttpGet(
                    HttpClientUtils.buildHttpUrl(url), null, null);
            httpGet.setConfig(this.requestConfig);
            return doGet(httpGet);
        } catch (URISyntaxException e) {
            logger.error("doGet(String url) URISyntaxException : {}", url);
        }
        return null;
    }

    public String doGet(String url, Map<String, String> params) {
        try {
            HttpGet httpGet = HttpClientUtils.buildHttpGet(
                    HttpClientUtils.buildHttpUrl(url), params, null);
            return this.doGet(httpGet);
        } catch (URISyntaxException e) {
            logger.error("doGet(String url, Map<String, String> params) URISyntaxException : {}", url);
        }
        return null;
    }

    public String doPost(String url) {
        try {
            HttpPost httpPost = HttpClientUtils.buildHttpPost(
                    HttpClientUtils.buildHttpUrl(url), null, null);
            return this.doPost(httpPost);
        } catch (URISyntaxException e) {
            //logger.error(message, params);
            logger.error("doPost(String url) URISyntaxException : {} ", url);
        }
        return null;
    }

    public String doPost(String url, Map<String, String> params) {
        try {
            HttpPost httpPost = HttpClientUtils.buildHttpPost(
                    HttpClientUtils.buildHttpUrl(url), params, null);
//			httpPost.addHeader("Content-Type","text/html;charset=UTF-8");
            return this.doPost(httpPost);
        } catch (URISyntaxException e) {
            logger.error("doPost(String url, Map<String, String> params) URISyntaxException : {} ", url);
        }
        return null;
    }

    public String doPut(String url) {
        try {
            HttpPut httpPost = HttpClientUtils.buildHttpPut(HttpClientUtils.buildHttpUrl(url), null, null);
            return this.doPut(httpPost);
        } catch (URISyntaxException e) {
            //logger.error(message, params);
            logger.error("doPost(String url) URISyntaxException : {} ", url);
        }
        return null;
    }


    public String doPut(String url, Map<String, String> params) {
        try {
            HttpPut httpPost = HttpClientUtils.buildHttpPut(HttpClientUtils.buildHttpUrl(url), params, null);
            return this.doPut(httpPost);
        } catch (URISyntaxException e) {
            logger.error("doPut(String url, Map<String, String> params) URISyntaxException url:{} ,params:{}", url, params);
        }
        return null;
    }

    public String doPatch(String url, Map<String, String> params) {
        try {
            HttpPatch httpPatch = HttpClientUtils.buildHttpPatch(HttpClientUtils.buildHttpUrl(url), params, null);
            return this.doPatch(httpPatch);
        } catch (URISyntaxException e) {
            logger.error("doGet(String url, Map<String, String> params) URISyntaxException : {}", url);
        }
        return null;
    }


    public String doDelete(String url) {
        try {
            HttpDelete httpDelete = HttpClientUtils.buildHttpDelete(url, null, null);
            return doDelete(httpDelete);
        } catch (URISyntaxException e) {
            logger.error("doDelete(String url) URISyntaxException : {} ", url);
        }
        return null;
    }

    public String doDelete(String url, Map<String, String> params) {
        try {
            HttpDelete httpDelete = HttpClientUtils.buildHttpDelete(HttpClientUtils.buildHttpUrl(url), params, null);
            httpDelete.addHeader("Content-Type", "text/html;charset=UTF-8");
            return this.doDelete(httpDelete);
        } catch (URISyntaxException e) {
            logger.error("doDelete(String url, Map<String, String> params) URISyntaxException : {} ", url);
        }
        return null;
    }

    protected boolean executeGet(String url, Map<String, String> params) {
        return executeGet(url, params, null);
    }

    protected boolean executePost(String url, Map<String, String> params) {
        return executePost(url, params, null);
    }


    protected boolean executeGet(String url, Map<String, String> params,
                                 Map<String, String> headers) {
        CloseableHttpResponse response = null;
        try {
            HttpGet httpGet = HttpClientUtils.buildHttpGet(HttpClientUtils.buildHttpUrl(url), params, headers);
            response = httpClient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                String resultJson = EntityUtils.toString(response.getEntity());
                JSONObject jsonObject = JSONObject.fromObject(resultJson);
                if (jsonObject != null) {
                    String status = jsonObject.getString("status");
                    if ("success".equals(status)) {
                        return true;
                    }
                }
            }
        } catch (ClientProtocolException e) {
            logger.error("executeGet(String url, Map<String, String> params,Map<String, String> headers)  ClientProtocolException : {} ", url);
        } catch (IOException e) {
            logger.error("executeGet(String url, Map<String, String> params,Map<String, String> headers)  IOException : {} ", url);
        } catch (URISyntaxException e) {
            logger.error("executeGet(String url, Map<String, String> params,Map<String, String> headers)  URISyntaxException : {} ", url);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    protected boolean executePost(String url, Map<String, String> params,
                                  Map<String, String> headers) {
        CloseableHttpResponse response = null;
        try {
            HttpPost httpPost = HttpClientUtils.buildHttpPost(
                    HttpClientUtils.buildHttpUrl(url), params, headers);
            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                String resultJson = EntityUtils.toString(response.getEntity());
                JSONObject jsonObject = JSONObject.fromObject(resultJson);
                if (jsonObject != null) {
                    String status = jsonObject.getString("status");
                    if ("success".equals(status)) {
                        return true;
                    }
                }
            }
        } catch (ClientProtocolException e) {
            logger.error("executePost(String url, Map<String, String> params,Map<String, String> headers)  ClientProtocolException : {} ", url);
        } catch (IOException e) {
            logger.error("executePost(String url, Map<String, String> params,Map<String, String> headers)  IOException :  {} ", url);
        } catch (URISyntaxException e) {
            logger.error("executePost(String url, Map<String, String> params,Map<String, String> headers)  URISyntaxException : {} ", url);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public String doPostFile(String url, Map<String, String> datas, Map<String, String> files) {
        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
        multipartEntityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        multipartEntityBuilder.setCharset(Charset.forName("UTF-8"));

        Iterator<Entry<String, String>> iterator = null;
        if (datas != null) {
            iterator = datas.entrySet().iterator();
            // 发送的数据
            while (iterator.hasNext()) {
                Entry<String, String> entry = (Entry<String, String>) iterator.next();
                multipartEntityBuilder.addTextBody(entry.getKey(), entry.getValue(), ContentType.create("text/plain", Charset.forName("UTF-8")));
            }
        }

        // 发送的文件
        if (files != null) {
            iterator = files.entrySet().iterator();
            while (iterator.hasNext()) {
                Entry<String, String> entry = (Entry<String, String>) iterator.next();
                String path = entry.getValue();
                if ("".equals(path) || path == null)
                    continue;
//				File file = new File(entry.getValue());
//				multipartEntityBuilder.addBinaryBody(entry.getKey(), file);
                FileBody bin = new FileBody(new File(entry.getValue()));
                multipartEntityBuilder.addPart(entry.getKey(), bin);
            }
        }

        // 生成 HTTP 实体
        CloseableHttpResponse response = null;
        try {
            HttpPost httpPost = HttpClientUtils.buildHttpPost(url);
            HttpEntity httpEntity = multipartEntityBuilder.build();
            // 设置 POST 请求的实体部分
            httpPost.setEntity(httpEntity);
            // 发送 HTTP 请求
//			return doPost(httpPost);
            response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == 200) {
                return EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (ClientProtocolException e) {
            logger.error("doPostFile(String url, Map<String, String> datas, Map<String, String> files)  ClientProtocolException : {} ", url);
        } catch (IOException e) {
            logger.error("doPostFile(String url, Map<String, String> datas, Map<String, String> files)  IOException : {} ", url);
        } catch (URISyntaxException e) {
            logger.error("doPostFile(String url, Map<String, String> datas, Map<String, String> files)  URISyntaxException : {} ", url);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


}
