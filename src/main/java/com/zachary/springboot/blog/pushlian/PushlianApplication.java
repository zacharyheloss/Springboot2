package com.zachary.springboot.blog.pushlian;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 
 * @author zachary
 * @desc �ų�Ĭ�ϵĴ�����ͼ
 *
 */
/*
 * @ComponentScan(value = "com.zachary")
 * 
 * @EntityScan("com.zachary.domain")
 * 
 * @EnableJpaRepositories("com.zachary.dao")
 */

@EnableAspectJAutoProxy
@SpringBootApplication(exclude = ErrorMvcAutoConfiguration.class)
@MapperScan("com.zachary.springboot.blog.pushlian.dao")
public class PushlianApplication extends SpringBootServletInitializer{

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(PushlianApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(PushlianApplication.class, args);
		/*
		 * context.getBean(BeanConfig.class).show();
		 * context.getBean(FileConfig.class).show(); context.close();
		 */
	}

}
