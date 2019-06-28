package com.zachary.springboot.blog.pushlian;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
class UserTests {

	@Autowired
	private MockMvc mvc;
	
	@Test
	void contextLoads() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/demo/1").
				accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}

}
