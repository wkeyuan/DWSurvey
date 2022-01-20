package net.diaowen.common.dao;

import org.apache.http.Header;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

/**
 *
* @ClassName: SuperHttpDao
* @Description: TODO(http请求超类，http请求更高级原始的封装)
* @author keyuan
* @date 2016年9月17日 上午11:22:21
*
 */
@Component
public class SuperHttpDao {

	private static Logger logger = LogManager.getLogger(SuperHttpDao.class.getName());

	@Autowired
	protected CloseableHttpClient httpClient;
	@Autowired
	protected RequestConfig requestConfig;


	public String doGet(HttpGet httpGet) {
		CloseableHttpResponse response = null;
		try {
			// 执行请求
			response = httpClient.execute(httpGet);
			// 判断返回状态是否为200
			StatusLine statusLine = response.getStatusLine();
			if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
				return EntityUtils.toString(response.getEntity(), "UTF-8");
			}
		} catch (ClientProtocolException e) {
			logger.error("doGet(HttpGet httpGet)  ClientProtocolException : {} ", httpGet.getURI().toString());
		} catch (IOException e) {
			logger.error("doGet(HttpGet httpGet)  IOException : {} ", httpGet.getURI().toString());
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

	public String doPost(HttpPost httpPost) {
		CloseableHttpResponse response = null;
		try {
			// 执行请求
			response = httpClient.execute(httpPost);
			// 判断返回状态是否为200
			if (response.getStatusLine().getStatusCode() == 200) {
				return EntityUtils.toString(response.getEntity(), "UTF-8");
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			logger.error("doPost(HttpPost httpPost) ClientProtocolException : {} ", httpPost.getURI().toString());
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("doPost(HttpPost httpPost) IOException : {} ", httpPost.getURI().toString());
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

	public String doDelete(HttpDelete httpDelete) {
		CloseableHttpResponse response = null;
		try {
			// 执行请求
			response = httpClient.execute(httpDelete);
			// 判断返回状态是否为200
			if (response.getStatusLine().getStatusCode() == 200) {
				return EntityUtils.toString(response.getEntity(), "UTF-8");
			}
		} catch (ClientProtocolException e) {
			logger.error("doDelete(HttpDelete httpDelete) ClientProtocolException : {} ", httpDelete.getURI().toString());
		} catch (IOException e) {
			logger.error("doDelete(HttpDelete httpDelete) IOException : {} ", httpDelete.getURI().toString());
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

	public String doPatch(HttpPatch httpPatch) {
		CloseableHttpResponse response = null;
		try {
			Header[] headers2 = httpPatch.getAllHeaders();
			// 执行请求
			response = httpClient.execute(httpPatch);
			// 判断返回状态是否为200
			if (response.getStatusLine().getStatusCode() == 200) {
				return EntityUtils.toString(response.getEntity(), "UTF-8");
			}
		} catch (ClientProtocolException e) {
			logger.error("doPatch(HttpPatch httpPatch) ClientProtocolException : {} ", httpPatch.getURI().toString());
		} catch (IOException e) {
			logger.error("doPatch(HttpPatch httpPatch) IOException : {} ", httpPatch.getURI().toString());
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


	public String doPut(HttpPut httpPut) {
		CloseableHttpResponse response = null;
		try {
			// 执行请求
			response = httpClient.execute(httpPut);
			// 判断返回状态是否为200
			if (response.getStatusLine().getStatusCode() == 200) {
				return EntityUtils.toString(response.getEntity(), "UTF-8");
			}
		} catch (ClientProtocolException e) {
			logger.error("doPatch(HttpPatch httpPatch) ClientProtocolException : {} ", httpPut.getURI().toString());
		} catch (IOException e) {
			logger.error("doPatch(HttpPatch httpPatch) IOException : {} ", httpPut.getURI().toString());
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

	/* (non-Javadoc)
	 * @see net.diaowen.youwe.dao.impl.SuperHttpDao#doGet(java.lang.String)
	 */
	public String doGet(String url) {
		// 创建http GET请求
		HttpGet httpGet = new HttpGet(url);
		httpGet.setConfig(this.requestConfig);
		return doGet(httpGet);
	}

	/* (non-Javadoc)
	 * @see net.diaowen.youwe.dao.impl.SuperHttpDao#doGet(java.lang.String, java.util.Map)
	 */
	public String doGet(String url, Map<String, String> params) {
		try {
			URIBuilder uriBuilder = new URIBuilder(url);
			for (String key : params.keySet()) {
				uriBuilder.addParameter(key, params.get(key));
			}
			return this.doGet(uriBuilder.build().toString());
		} catch (URISyntaxException e) {
			logger.error("doGet(String url, Map<String, String> params) URISyntaxException : {} ", url);
		}
		return null;
	}


	public String buildUrl(String url, Map<String, String> params) {
		try {
			URIBuilder uriBuilder = new URIBuilder(url);
			for (String key : params.keySet()) {
				uriBuilder.addParameter(key, params.get(key));
			}
			return uriBuilder.build().toString();
		} catch (URISyntaxException e) {
			logger.error("doGet(String url, Map<String, String> params) URISyntaxException : {} ", url);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see net.diaowen.youwe.dao.impl.SuperHttpDao#doPost(java.lang.String, java.util.Map)
	 */
	/*
	public HttpResult doPost(String url, Map<String, String> params) throws IOException {
		// 创建http POST请求
		HttpPost httpPost = new HttpPost(url);
		httpPost.setConfig(this.requestConfig);
		if (params != null) {
			// 设置2个post参数，一个是scope、一个是q
			List<NameValuePair> parameters = new ArrayList<NameValuePair>();
			for (String key : params.keySet()) {
				parameters.add(new BasicNameValuePair(key, params.get(key)));
			}
			// 构造一个form表单式的实体
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters, "UTF-8");
			// 将请求实体设置到httpPost对象中
			httpPost.setEntity(formEntity);
		}

		CloseableHttpResponse response = null;
		try {
			// 执行请求
			response = httpClient.execute(httpPost);
			return new HttpResult(response.getStatusLine().getStatusCode(),
					EntityUtils.toString(response.getEntity(), "UTF-8"));
		} finally {
			if (response != null) {
				response.close();
			}
		}
	}
*/
	/* (non-Javadoc)
	 * @see net.diaowen.youwe.dao.impl.SuperHttpDao#doPost(java.lang.String)
	 */
//	public HttpResult doPost(String url) throws IOException {
//		return this.doPost(url, null);
//	}

	/* (non-Javadoc)
	 * @see net.diaowen.youwe.dao.impl.SuperHttpDao#doPostJson(java.lang.String, java.lang.String)
	 */
/*
	public HttpResult doPostJson(String url, String json) throws ClientProtocolException, IOException {
		// 创建http POST请求
		HttpPost httpPost = new HttpPost(url);
		httpPost.setConfig(this.requestConfig);

		if (json != null) {
			// 构造一个form表单式的实体
			StringEntity stringEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
			// 将请求实体设置到httpPost对象中
			httpPost.setEntity(stringEntity);
		}

		CloseableHttpResponse response = null;
		try {
			// 执行请求
			response = this.httpClient.execute(httpPost);
			return new HttpResult(response.getStatusLine().getStatusCode(),EntityUtils.toString(response.getEntity(), "UTF-8"));
		} finally {
			if (response != null) {
				response.close();
			}
		}
	}
	*/

}
