package com.nnk.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.nnk.springboot.Application;

@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
class LoginControllerTest {

	@Autowired
	MockMvc mockMvc;
	
	@Test
	public void getAccessToTheLoginPage() throws Exception {
		
		//testing request
		mockMvc.perform(MockMvcRequestBuilders.get("/app/login"))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.view().name("login"));
		
	}
	
	@Test
	public void loginInApplicationAndReturnAuthenticated() throws Exception {
		
		//Testing request
		mockMvc.perform(SecurityMockMvcRequestBuilders.formLogin("/login")
				.user("user")
				.password("123456"))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(SecurityMockMvcResultMatchers.authenticated())
			.andExpect(MockMvcResultMatchers.redirectedUrl("/bidList/list"));
		
	}
	
	@Test
	public void loginInApplicationWithWrongArgumentAndReturnUnauthenticated() throws Exception {
		
		//testing request
		mockMvc.perform(SecurityMockMvcRequestBuilders.formLogin("/login")
				.user("user")
				.password("wrongpassword"))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(SecurityMockMvcResultMatchers.unauthenticated());
		
	}

}
