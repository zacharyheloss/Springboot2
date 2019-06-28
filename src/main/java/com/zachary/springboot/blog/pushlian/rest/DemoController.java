package com.zachary.springboot.blog.pushlian.rest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zachary.springboot.blog.pushlian.damain.User;
import com.zachary.springboot.blog.pushlian.dao.UserRepository;

@RestController
public class DemoController {
	
	@Resource
	private UserRepository userRepository;
	
	@RequestMapping("/demo/1")
	public String demo1() {
		
		/*
		 * User user1=new User(); user1.setName("ÕÅÈý");
		 * 
		 * User user2=new User(); user2.setName("1Èý");
		 * 
		 * userRepository.save(user1); userRepository.save(user2);
		 */
		throw new RuntimeException("xxxxxxxxxxxxxxx");
	}
	
	@RequestMapping("/demo/2")
	public Object demo2() {
		List<User> userList=new ArrayList<>();
		Iterator<User> userIter=userRepository.findAll().iterator();
		while(userIter.hasNext()) {
			User user=userIter.next();
			userList.add(user);
		}
		return userList;
	}

}
