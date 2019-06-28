package com.zachary.springboot.blog.pushlian.bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.zachary.springboot.blog.pushlian.DemoInteceptor;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

	/*
	 * @Override public void addInterceptors(InterceptorRegistry registry) {
	 * registry.addInterceptor(new DemoInteceptor()); }
	 */

}
