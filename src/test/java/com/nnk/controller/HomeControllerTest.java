package com.nnk.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.nnk.springboot.Application;

@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
class HomeControllerTest {
	
	@Autowired
	MockMvc mockMvc;

	@Test
	void getAccessToTheHomePage() throws Exception {
		
		//Testing request
		mockMvc.perform(MockMvcRequestBuilders.get("/"))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.view().name("home"));
		
	}
	
	@Test
	@WithMockUser(username = "admin", roles = {"ADMIN"})
	public void getAccessToTheAdminHomePage() throws Exception {
		
		//testing request
		mockMvc.perform(MockMvcRequestBuilders.get("/admin/home"))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isFound())
			.andExpect(MockMvcResultMatchers.view().name("redirect:/bidList/list"));
		
		
	}

}
