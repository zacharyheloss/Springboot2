package com.zachary.springboot.blog.pushlian.bean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class Dog {
	
	@PostConstruct
	public void init() {
		System.out.println("=========init=======");
	}
	
	@PreDestroy
	public void destory() {
		System.out.println("=========destorys=======");
	}

}
