package com.zachary.springboot.blog.pushlian.bean;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.zachary.springboot.blog.pushlian.exception.BusinessException;
import com.zachary.springboot.blog.pushlian.util.ResultCode;

@RestControllerAdvice
public class ExceptionHandle {
	
	private static final Logger logger=LoggerFactory.getLogger(ExceptionHandle.class);
	
	@ExceptionHandler(value = {BusinessException.class,Exception.class} )
	public ResultCode<String> exceptionHandle(HttpServletRequest request, Exception e) {
		if(e instanceof BusinessException) {
			logger.warn("业务操作异常",e);
		}else {
			logger.info("系统异常",e);
		}
		return ResultCode.getFailure("F999", "服务异常，请稍后重试！");
	}
		
}
