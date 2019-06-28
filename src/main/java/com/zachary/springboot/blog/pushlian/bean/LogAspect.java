package com.zachary.springboot.blog.pushlian.bean;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class LogAspect {
	
	private static final org.slf4j.Logger logger=LoggerFactory.getLogger(LogAspect.class);
	
	@Pointcut("execution(* com.zachary.springboot.blog.pushlian.rest..*.*(..))")
	public void rest() {
	}
	
	@Pointcut("execution(* com.zachary.springboot.blog.pushlian.dao..*.*(..))")
	public void dao() {
	}
	
	@Before("rest() || dao()")
	public void log(JoinPoint joinpoint) {
		logger.info("....."+joinpoint.getTarget().getClass()+"....."+joinpoint.getArgs());
	}

}
