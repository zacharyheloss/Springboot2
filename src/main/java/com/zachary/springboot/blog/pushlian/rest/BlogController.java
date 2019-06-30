package com.zachary.springboot.blog.pushlian.rest;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zachary.springboot.blog.pushlian.damain.RoncooUser;
import com.zachary.springboot.blog.pushlian.service.IDemoService;

@RestController
public class BlogController {
	
	@Resource
	private IDemoService demoService;
	
	@RequestMapping("/blog/1")
	public List<RoncooUser> es1() {
		List<RoncooUser> useList=demoService.queryAll();
		return useList;
	}
	
	
	@RequestMapping("/blog/2")
	public RoncooUser es2() {
		RoncooUser roncooUser=demoService.queryById(10);
		return roncooUser;
	}
	

}
