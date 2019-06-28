package com.zachary.springboot.blog.pushlian.rest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zachary.springboot.blog.pushlian.damain.Esblog;
import com.zachary.springboot.blog.pushlian.dao.EsblogRepository;

@RestController
public class BlogController {
	
	@Resource
	private EsblogRepository esblogRepository;
	
	@RequestMapping("/es/1")
	public String es1() {
		esblogRepository.save(new Esblog("�ƺ�¥", "��Ȫ��֮�ƺ�¥", "cwncwbwfwp1p0319382ub2"));
		esblogRepository.save(new Esblog("��ҹ", "��Ȫ��֮��ҹ", "hhhhcwncwbwfwp1p0319382ub2"));
		esblogRepository.save(new Esblog("��¥", "��Ȫ��֮��¥", "jjjjjp1p0319382ub2"));
		
		return "Es Successful!";
	}
	
	@RequestMapping("/es/2")
	public Object es2() {
		Iterator<Esblog> iter=esblogRepository.findAll().iterator();
		List<Esblog> esblogList=new ArrayList<Esblog>();
		while(iter.hasNext()) {
			Esblog esblog=iter.next();
			esblogList.add(esblog);
		}
		
		return esblogList;
	}
	

}
