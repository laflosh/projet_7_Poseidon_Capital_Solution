package com.nnk.controller;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.nnk.springboot.Application;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;

import jakarta.transaction.Transactional;

@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
class UserControllerTest {

	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	UserRepository userRepository;
	
	@Test
	@WithMockUser
	public void getAllUsersInDatabaseAndReturnListPageWithData() throws Exception {
		
		//Testing request
		mockMvc.perform(MockMvcRequestBuilders.get("/user/list"))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("users"))
			.andExpect(MockMvcResultMatchers.view().name("user/list"));
		
	}
	
	@Test
	@WithMockUser
	public void getAccessToTheAddingPageAndCountainEmptyEntity() throws Exception {
		
		//Testing request
		mockMvc.perform(MockMvcRequestBuilders.get("/user/add"))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("user"))
			.andExpect(MockMvcResultMatchers.view().name("user/add"));
		
	}
	
	@Test
	@WithMockUser
	@Transactional
	public void postNewValidUserAndRedirectToThePageList() throws Exception {
		
		//Testing request
		mockMvc.perform(MockMvcRequestBuilders.post("/user/validate")
				.with(SecurityMockMvcRequestPostProcessors.csrf())
				.param("fullname", "User")
				.param("username", "user")
				.param("password", "123456")
				.param("role", "USER"))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isFound())
			.andExpect(MockMvcResultMatchers.view().name("redirect:/user/list"));
		
		//Get the last user save in database
		List<User> users = userRepository.findAll();
		User user = users.get(users.size() - 1);
		
		//Delete the object who was created
		userRepository.delete(user);
		
	}
	
	@Test
	@WithMockUser
	public void postNoneValidateUserdAndShowFieldsError() throws Exception {
		
		//Testing request
		mockMvc.perform(MockMvcRequestBuilders.post("/user/validate")
				.with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model()
					.attributeHasFieldErrors("user","fullname", "username", "password", "role"))
			.andExpect(MockMvcResultMatchers.view().name("user/add"));
		
	}
	
	@Test
	@WithMockUser
	@Transactional
	public void getTheUpdatePageAndCountainTheObject() throws Exception {
		
		//Save a new rating entity
		User newUser = new User("User", "user", "123456", "USER");
		userRepository.save(newUser);
		
		//Get the last user save in database
		List<User> users = userRepository.findAll();
		User user = users.get(users.size() - 1);
		
		//Testing request
		mockMvc.perform(MockMvcRequestBuilders.get("/user/update/" + user.getId()))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("user"))
			.andExpect(MockMvcResultMatchers.view().name("user/update"));
		
		//Delete the object who was created
		userRepository.delete(user);
		
	}
	
	@Test
	@WithMockUser
	@Transactional
	public void postValidateUpdateUserAndRedirectToThePageList() throws Exception {
		
		//Save a new rating entity
		User newUser = new User("User", "user", "123456", "USER");
		userRepository.save(newUser);
		
		//Get the last user save in database
		List<User> users = userRepository.findAll();
		User user = users.get(users.size() - 1);
		
		//Testing request
		mockMvc.perform(MockMvcRequestBuilders.post("/user/update/" + user.getId())
				.with(SecurityMockMvcRequestPostProcessors.csrf())
				.param("fullname", "User")
				.param("username", "user")
				.param("password", "654321")
				.param("role", "USER"))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isFound())
			.andExpect(MockMvcResultMatchers.view().name("redirect:/user/list"));
		
		//Delete the object who was created
		userRepository.delete(user);
		
	}
	
	@Test
	@WithMockUser
	@Transactional
	public void postNoneValidateUpdateUserAndShowFieldsError() throws Exception {
		
		//Save a new rating entity
		User newUser = new User("User", "user", "123456", "USER");
		userRepository.save(newUser);
		
		//Get the last user save in database
		List<User> users = userRepository.findAll();
		User user = users.get(users.size() - 1);
		
		//Testing request
		mockMvc.perform(MockMvcRequestBuilders.post("/user/update/" + user.getId())
				.with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model()
					.attributeHasFieldErrors("user","fullname", "username", "password", "role"))
			.andExpect(MockMvcResultMatchers.view().name("user/update"));
		
	}
	
	@Test
	@WithMockUser
	@Transactional
	public void deleteAnExistingBidListObjectInTheDatabase() throws Exception {
		
		//Save a new rating entity
		User newUser = new User("User", "user", "123456", "USER");
		userRepository.save(newUser);
		
		//Get the last user save in database
		List<User> users = userRepository.findAll();
		User user = users.get(users.size() - 1);
		
		//Testing Request
		mockMvc.perform(MockMvcRequestBuilders.get("/user/delete/" + user.getId())
				.with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isFound())
			.andExpect(MockMvcResultMatchers.view().name("redirect:/user/list"));
		
	}

}
