package com.zachary.springboot.blog.pushlian.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zachary.springboot.blog.pushlian.damain.RoncooUser;
import com.zachary.springboot.blog.pushlian.dao.UserMapper;
import com.zachary.springboot.blog.pushlian.mrdatasource.DataSource;
import com.zachary.springboot.blog.pushlian.service.IDemoService;

@Service("demoService")
public class DemoServiceImpl implements IDemoService{
	
	@Resource
	private UserMapper userMapper;

	@Override
	@DataSource
	@Transactional(readOnly = true)
	public List<RoncooUser> queryAll() {
		return userMapper.queryAll();
	}

	@Override
	@DataSource("slave1")
	@Transactional(readOnly = true)
	public RoncooUser queryById(Integer id) {
		return userMapper.selectByPrimaryKey(10);
	}

}
