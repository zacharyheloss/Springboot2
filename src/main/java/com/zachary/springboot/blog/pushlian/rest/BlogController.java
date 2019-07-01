package com.zachary.springboot.blog.pushlian.rest;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zachary.springboot.blog.pushlian.damain.RoncooUser;
import com.zachary.springboot.blog.pushlian.service.IDemoService;
import com.zachary.springboot.blog.pushlian.util.EmsAuthEncryptUtil;

@RestController
@RequestMapping("/openApi")
@CrossOrigin(origins  = {"http://127.0.0.1:8080"})
public class BlogController {
	
	@Resource
	private IDemoService demoService;
	
	@Resource
	private EmsAuthEncryptUtil emsAuthEncryptUtil;
	
	@RequestMapping("/blog/1")
	public List<RoncooUser> es1() {
		List<RoncooUser> useList=demoService.queryAll();
		return useList;
	}
	
	
	@RequestMapping("/blog/2")
	public RoncooUser es2() {
		RoncooUser roncooUser=demoService.queryById(10);
		String emsAuthEncry=emsAuthEncryptUtil.encrypt(roncooUser.getName());
		String emsAuthDecry=emsAuthEncryptUtil.decrypt(emsAuthEncry);
		System.out.println(emsAuthEncry+"\n"+emsAuthDecry);
		return roncooUser;
	}
	

}
