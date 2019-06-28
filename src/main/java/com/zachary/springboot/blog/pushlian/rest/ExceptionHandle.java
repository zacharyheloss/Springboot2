package com.zachary.springboot.blog.pushlian.rest;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandle {
	
	
	@ExceptionHandler(value = {RuntimeException.class,Exception.class} )
	public String demo() {
		System.out.println("xxxxxxxxxxx");
		return "json";
	}
		
}
