package com.zachary.springboot.blog.pushlian.rest;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zachary.springboot.blog.pushlian.damain.RoncooUser;
import com.zachary.springboot.blog.pushlian.service.IDemoService;

@RestController
@RequestMapping("/openApi")
public class BlogController {
	
	@Resource
	private IDemoService demoService;
	
	@RequestMapping("/blog/1")
	@CrossOrigin(origins  = {"http://127.0.0.1:8080"},exposedHeaders = "bbs")
	public List<RoncooUser> es1() {
		List<RoncooUser> useList=demoService.queryAll();
		return useList;
	}
	
	
	@RequestMapping("/blog/2")
	@CrossOrigin(origins  = {"http://127.0.0.1:8080"},exposedHeaders = "bbs")
	public RoncooUser es2() {
		RoncooUser roncooUser=demoService.queryById(10);
		return roncooUser;
	}
	

}
