package com.zachary.springboot.blog.pushlian.bean;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.zachary.springboot.blog.pushlian.exception.BusinessException;
import com.zachary.springboot.blog.pushlian.util.ResultCode;

/**
 * 
 * @author zachary
 * @desc 全局异常捕捉
 *
 */
@RestControllerAdvice
public class ExceptionHandle {
	
	private static final Logger logger=LoggerFactory.getLogger(ExceptionHandle.class);
	
	@ExceptionHandler(value = {BusinessException.class,Exception.class} )
	public ResultCode<String> exceptionHandle(HttpServletRequest request, Exception e) {
		
		if(e instanceof MethodArgumentNotValidException) {
			MethodArgumentNotValidException methodArgumentNotValidException=(MethodArgumentNotValidException)e;
			BindingResult paramsErrorResult = methodArgumentNotValidException.getBindingResult();
			if (paramsErrorResult.hasErrors()) {
				List<ObjectError> errors = paramsErrorResult.getAllErrors();
				for(ObjectError error:errors) {
					logger.warn("Data check failure : object{"+error.getObjectName()+":"+error.getCode()+"},errorMessage{"+error.getDefaultMessage()+"}");
					return ResultCode.getFailure("F999", "参数验证错误："+error.getDefaultMessage());
				}
			}
			
		}else if(e instanceof BusinessException){
			logger.warn("业务操作异常",e);
		}else {
			logger.warn("系统异常",e);
		}
		return ResultCode.getFailure("F999", "服务异常，请稍后重试！");
	}
		
}
