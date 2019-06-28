package com.zachary.springboot.blog.pushlian.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;

@Configuration
@PropertySources(value ={@PropertySource("classpath:jdbc.properties")})
public class FileConfig {
	
	//@Bean(name = "myDog",initMethod = "init" ,destroyMethod = "destory")
	/*
	 * @Bean public Dog initDog() { return new Dog(); }
	 */
	
	@Autowired
	private Environment env;
	
	@Value("${spring.datasource.url}")
	private String url;
	
	public void show() {
		System.out.println(env.getProperty("spring.datasource.url"));
		System.out.println(url);
	}
	
	/*
	 * public static void main(String[] args) { AnnotationConfigApplicationContext
	 * context=new AnnotationConfigApplicationContext(BeanConfig.class);
	 * 
	 * System.out.println(context.getBean("initDog"));
	 * System.out.println(context.getBean(Dog.class)); }
	 */

}
