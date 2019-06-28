package com.zachary.springboot.blog.pushlian.dao;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.zachary.springboot.blog.pushlian.damain.Esblog;

public interface EsblogRepository extends ElasticsearchRepository<Esblog, String>{
	
}
