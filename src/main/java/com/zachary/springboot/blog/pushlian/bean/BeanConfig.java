package com.zachary.springboot.blog.pushlian.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class BeanConfig {
	
	//@Bean(name = "myDog",initMethod = "init" ,destroyMethod = "destory")
	/*
	 * @Bean public Dog initDog() { return new Dog(); }
	 */
	
	@Autowired
	private Environment env;
	
	@Value("${logging.pattern.levels:warn}")
	private String level;
	
	public void show() {
		System.out.println(env.getProperty("logging.pattern.level"));
		System.out.println(level);
	}
	
	/*
	 * public static void main(String[] args) { AnnotationConfigApplicationContext
	 * context=new AnnotationConfigApplicationContext(BeanConfig.class);
	 * 
	 * System.out.println(context.getBean("initDog"));
	 * System.out.println(context.getBean(Dog.class)); }
	 */

}
