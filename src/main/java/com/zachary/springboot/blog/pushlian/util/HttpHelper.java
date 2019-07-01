package com.zachary.springboot.blog.pushlian.util;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @author zachary
 * @desc http请求
 *
 */
public class HttpHelper {
	protected static final Logger logger = LoggerFactory.getLogger(HttpHelper.class);
	private static String error_msg = "";
	private static final int CONNECTION_TIMEOUT = 5000;

	public static HttpResult httpGet(String url, Map<String, String> params, Integer millsecond, boolean isJsonMap) {
		CloseableHttpResponse response = null;
		CloseableHttpClient httpClient = null;
		try {
			URIBuilder builder = new URIBuilder(url);
			if (params != null) {
				Set<String> keys = params.keySet();
				for (Iterator<String> localIterator = keys.iterator(); localIterator.hasNext();) {
					String key = (String) localIterator.next();
					builder.setParameter(key, (String) params.get(key));
				}
			}

			HttpGet httpGet = new HttpGet(builder.build());

			httpClient = HttpClients.createDefault();
			millsecond = Integer.valueOf(millsecond == null ? CONNECTION_TIMEOUT : millsecond.intValue());

			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(millsecond.intValue())
					.setConnectTimeout(millsecond.intValue()).build();
			httpGet.setConfig(requestConfig);

			response = httpClient.execute(httpGet, new BasicHttpContext());
			if (response.getStatusLine().getStatusCode() != 200) {
				return new HttpResult(false, null, response.getStatusLine().getReasonPhrase(),
						Integer.valueOf(response.getStatusLine().getStatusCode()));
			}
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String resultStr = EntityUtils.toString(entity, "UTF-8");
				logger.warn("get请求获取结果" + resultStr);
				/*
				 * if(!BeanUtils.isJsonString(resultStr)) { return new HttpResult(false, null,
				 * "get请求，响应参数错误"); }
				 */
				if (isJsonMap) {
					return new HttpResult(true, JSON.parseObject(resultStr), null);
				}
				return new HttpResult(true, resultStr, null);
			}
			return new HttpResult(false, null, error_msg);
		} catch (Exception e) {
			logger.error("httpGet错误,request url=" + url + ",error:", e);
			error_msg = e.getMessage();
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					logger.error("httpGet/未正常关闭response", e);
				}
			}
			if (httpClient != null) {
				try {
					httpClient.close();
				} catch (IOException e) {
					logger.error("httpGet/未正常关闭客户端", e);
				}
			}
		}
		return null;
	}

	public static HttpResult httpPost(String url, Map<String, String> mapData, Integer millsecond, boolean isJsonMap) {
		CloseableHttpResponse response = null;
		CloseableHttpClient httpClient = null;
		try {
			URIBuilder builder = new URIBuilder(url);
			if (mapData != null) {
				Set<String> keys = mapData.keySet();
				for (Iterator<String> localIterator = keys.iterator(); localIterator.hasNext();) {
					String key = (String) localIterator.next();
					builder.setParameter(key, (String) mapData.get(key));
				}
			}

			HttpPost httpPost = new HttpPost(builder.build());

			httpClient = HttpClients.createDefault();
			millsecond = Integer.valueOf(millsecond == null ? CONNECTION_TIMEOUT : millsecond.intValue());

			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(millsecond.intValue())
					.setConnectTimeout(millsecond.intValue()).build();
			httpPost.setConfig(requestConfig);

			response = httpClient.execute(httpPost, new BasicHttpContext());
			if (response.getStatusLine().getStatusCode() != 200) {
				return new HttpResult(false, null, response.getStatusLine().getReasonPhrase(),
						Integer.valueOf(response.getStatusLine().getStatusCode()));
			}
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String resultStr = EntityUtils.toString(entity, "UTF-8");
				logger.info("post请求响应体数据" + resultStr);
				if (!BeanUtils.isJsonString(resultStr)) {
					return new HttpResult(false, null, "Post请求，响应参数错误");
				}
				if (isJsonMap) {
					return new HttpResult(true, JSON.parseObject(resultStr), null);
				}
				return new HttpResult(true, resultStr, null);
			}
			return new HttpResult(false, null, error_msg);
		} catch (Exception e) {
			logger.error("httpPost, request url=" + url + ", error:", e);
			error_msg = e.getMessage();
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					logger.error("httpPost/未正常关闭response", e);
				}
			}
			if (httpClient != null) {
				try {
					httpClient.close();
				} catch (IOException e) {
					logger.error("httpPost/未正常关闭httpClient", e);
				}
			}
		}
		return null;
	}

}
