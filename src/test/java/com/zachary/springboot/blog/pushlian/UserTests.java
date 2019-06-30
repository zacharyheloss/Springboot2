package com.zachary.springboot.blog.pushlian;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.alibaba.fastjson.JSONObject;
import com.zachary.springboot.blog.pushlian.damain.RoncooUser;
import com.zachary.springboot.blog.pushlian.service.IDemoService;


@SpringBootTest
@AutoConfigureMockMvc
class UserTests {

	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private IDemoService demoService;
	
	@Test
	void contextLoads() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/demo/1").
				accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}

	@Test
	public void test1() {
		List<RoncooUser> roncooUserList=demoService.queryAll();
		System.out.println(JSONObject.toJSONString(roncooUserList));
	}
	
}
