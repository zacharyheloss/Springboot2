package com.zachary.springboot.blog.pushlian.service;

import java.util.List;

import com.zachary.springboot.blog.pushlian.damain.RoncooUser;

public interface IDemoService {

	 List<RoncooUser> queryAll();
	
	 RoncooUser queryById(Integer id);
}
