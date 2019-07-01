package com.zachary.springboot.blog.pushlian.bean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.zachary.springboot.blog.pushlian.util.RequestUtil;

/**
 * 
 * @author zachary
 * @desc   处理跨域，处理前后端交互核心参数校验
 *
 */
@Component
public class AccessTokenInterceptor implements HandlerInterceptor {
	
	
	private static final Logger logger=LoggerFactory.getLogger(AccessTokenInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		
		// 如果是OPTIONS请求则结束
		if (HttpMethod.OPTIONS.toString().equals(request.getMethod())) {
			response.setStatus(HttpStatus.NO_CONTENT.value());
			logger.warn("本次请求OPTION：请求url:{}",RequestUtil.getFullUrl(request));
			return false;
		}
		response.setHeader("Access-Control-Allow-Origin", "http://localhost:8080");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("jwttoken", "1111");
		response.addHeader("Access-Control-Expose-Headers", "jwttoken");
		response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");
		return true;
	}

}
