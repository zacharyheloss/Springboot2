package com.zachary.springboot.blog.pushlian.dao;

import org.springframework.data.repository.CrudRepository;

import com.zachary.springboot.blog.pushlian.damain.User;

public interface UserRepository extends CrudRepository<User, Integer>{
	
}
