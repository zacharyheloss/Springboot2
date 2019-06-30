package com.zachary.springboot.blog.pushlian.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zachary.springboot.blog.pushlian.damain.RoncooUser;

@Mapper
public interface UserMapper{
	
	List<RoncooUser> queryAll();
	
	RoncooUser selectByPrimaryKey(Integer id);
	
}
