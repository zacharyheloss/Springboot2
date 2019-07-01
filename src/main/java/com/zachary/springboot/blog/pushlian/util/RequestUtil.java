package com.zachary.springboot.blog.pushlian.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 
 * @author zachary
 * @desc reqeust操作类
 *
 */
public class RequestUtil {
	protected static final Logger logger = LoggerFactory.getLogger(RequestUtil.class);

	public static HttpServletRequest getRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}

	public static String getMobileType(String userAgent) {
		Map<String, String> deviceArray = new HashMap<>();
		deviceArray.put("android", "ANDROID");
		deviceArray.put("mac", "IOS");

		String mobileType = "";
		if (userAgent != null) {
			userAgent = userAgent.toLowerCase();
			for (String key : deviceArray.keySet()) {
				if (userAgent.indexOf(key) >= 0) {
					mobileType = (String) deviceArray.get(key);
					break;
				}
			}
		}
		return mobileType;
	}

	public static boolean isMobileDevice(String userAgent) {
		String[] deviceArray = { "android", "mac os", "windows phone" };
		if (userAgent == null) {
			return false;
		}
		userAgent = userAgent.toLowerCase();
		for (int i = 0; i < deviceArray.length; i++) {
			if (userAgent.indexOf(deviceArray[i]) >= 0) {
				return true;
			}
		}
		return false;
	}

	public static String getRemoteAddrIp(HttpServletRequest request) {
		if (request == null) {
			request = getRequest();
		}
		String ip = request.getHeader("X-Forwarded-For");
		if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
			if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
				ip = request.getHeader("Proxy-Client-IP");
				logger.info("getIpAddress(HttpServletRequest) - Proxy-Client-IP - String ip=" + ip);
			}
			if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
				ip = request.getHeader("WL-Proxy-Client-IP");
				logger.info("getIpAddress(HttpServletRequest) - WL-Proxy-Client-IP - String ip=" + ip);
			}
			if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
				ip = request.getHeader("HTTP_CLIENT_IP");
				logger.info("getIpAddress(HttpServletRequest) - HTTP_CLIENT_IP - String ip=" + ip);
			}
			if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
				ip = request.getHeader("HTTP_X_FORWARDED_FOR");
				logger.info("getIpAddress(HttpServletRequest) - HTTP_X_FORWARDED_FOR - String ip=" + ip);
			}
			if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
				ip = request.getRemoteAddr();
				logger.info("getIpAddress(HttpServletRequest) - getRemoteAddr - String ip=" + ip);
			}
		} else if (ip.length() > 15) {
			String[] ips = ip.split(",");
			for (int index = 0; index < ips.length; index++) {
				String strIp = ips[index];
				if (!"unknown".equalsIgnoreCase(strIp)) {
					ip = strIp;
					break;
				}
			}
		}
		return ip;
	}

	public static String getFullUrl(HttpServletRequest request) {
		StringBuffer url = request.getRequestURL();
		return getFullUrl(request, url.toString(), null);
	}

	private static String getFullUrl(HttpServletRequest request, String url, Map<String, String> customData) {
		if (StringUtils.isEmpty(url)) {
			return url;
		}
		StringBuffer fullurl = new StringBuffer(url);
		String queryString = request.getQueryString();
		if (!StringUtils.isEmpty(queryString)) {
			fullurl.append("?").append(queryString);
		}
		if ((customData != null) && (!customData.isEmpty())) {
			if (fullurl.toString().indexOf("?") < 0) {
				fullurl.append("?");
			}
			for (String key : customData.keySet()) {
				if (!fullurl.toString().endsWith("?")) {
					fullurl.append("&");
				}
				fullurl.append(key).append("=").append((String) customData.get(key));
			}
		}
		return fullurl.toString();
	}

	public static void writeJson2Response(HttpServletResponse response, String json) {
		PrintWriter out = null;
		try {
			response.setCharacterEncoding("UTF-8");

			response.setContentType("application/json");
			out = response.getWriter();

			out.println(json);
		} catch (IOException e) {
			logger.error("数据写入response流异常", e);
		} finally {
			if (null != out) {
				out.flush();
				out.close();
			}
		}
	}

	public static boolean isAjax(HttpServletRequest request) {
		String header = request.getHeader("X-Requested-With");
		if ("XMLHttpRequest".equalsIgnoreCase(header)) {
			return Boolean.TRUE.booleanValue();
		}
		return Boolean.FALSE.booleanValue();
	}

	public static String getAccessToken(HttpServletRequest request) {
		if (request == null) {
			request = getRequest();
		}
		return request.getHeader("accesstoken");
	}

	public static String getJwttoken(HttpServletRequest request) {
		if (request == null) {
			request = getRequest();
		}
		return request.getHeader("Authorization");
	}

	public static String getSwitchKind(HttpServletRequest request) {
		if (request == null) {
			request = getRequest();
		}
		return request.getHeader("SwitchKind");
	}

	public static String getUserAgent(HttpServletRequest request) {
		if (request == null) {
			request = getRequest();
		}
		return request.getHeader("User-Agent");
	}

	public static String getOrigin(HttpServletRequest request) {
		if (request == null) {
			request = getRequest();
		}
		return request.getHeader("Origin");
	}

	public static Map<String, Object> getMapValue(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<>();
		Enumeration<?> pNames = request.getParameterNames();
		while (pNames.hasMoreElements()) {
			String name = (String) pNames.nextElement();
			String value = request.getParameter(name);
			resultMap.put(name, value);
		}
		return resultMap;
	}

	public static String readPostData(HttpServletRequest req) {
		InputStream is = null;
		BufferedReader br = null;
		try {
			is = req.getInputStream();
			br = new BufferedReader(new InputStreamReader(is, "utf-8"));

			String line = null;
			StringBuffer content = new StringBuffer();
			while ((line = br.readLine()) != null) {
				content.append(line);
			}
			return content.toString();
		} catch (IOException e) {
			logger.error("从request获取post数据异常", e);
			return null;
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					logger.error("从request获取post数据异常", e);
				}
			}
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					logger.error("从request获取post数据异常", e);
				}
			}
		}
	}

	public static boolean verifyMicroMessenger(String userAgent) {
		return userAgent.contains("micromessenger");
	}
}
