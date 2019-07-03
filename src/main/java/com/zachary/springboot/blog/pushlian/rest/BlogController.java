package com.zachary.springboot.blog.pushlian.rest;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zachary.springboot.blog.pushlian.api.req.UserReq;
import com.zachary.springboot.blog.pushlian.damain.RoncooUser;
import com.zachary.springboot.blog.pushlian.service.IDemoService;
import com.zachary.springboot.blog.pushlian.util.EmsAuthEncryptUtil;

@RestController
@RequestMapping("/openApi")
@CrossOrigin(origins  = {"http://127.0.0.1:8080"})
public class BlogController {
	
	@Autowired
	private IDemoService demoService;
	
	@Autowired
	private EmsAuthEncryptUtil emsAuthEncryptUtil;
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	
	@RequestMapping("/blog/1")
	public List<RoncooUser> es1() {
		List<RoncooUser> useList=demoService.queryAll();
		return useList;
	}
	
	
	@RequestMapping("/blog/2")
	public RoncooUser es2() {
		stringRedisTemplate.opsForValue().set("s2", "001");
		String s2=stringRedisTemplate.opsForValue().get("s2");
		System.out.println(s2);
		RoncooUser roncooUser=demoService.queryById(10);
		String emsAuthEncry=emsAuthEncryptUtil.encrypt(roncooUser.getName());
		String emsAuthDecry=emsAuthEncryptUtil.decrypt(emsAuthEncry);
		System.out.println(emsAuthEncry+"\n"+emsAuthDecry);
		return roncooUser;
	}
	
	
	
	@RequestMapping(value = "/blog/3",method = RequestMethod.POST)
	public UserReq es3(@Valid @RequestBody UserReq userReq) {
		return userReq;
	}

}
