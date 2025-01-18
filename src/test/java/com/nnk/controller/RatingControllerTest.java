package com.nnk.controller;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.nnk.springboot.Application;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;

import jakarta.transaction.Transactional;

import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;

@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class RatingControllerTest {

	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	RatingRepository ratingRepository;
	
	@Test
	@WithMockUser
	public void getAllRatingsInDatabaseAndReturnListPageWithData() throws Exception {
		
		//Testing request
		mockMvc.perform(MockMvcRequestBuilders.get("/rating/list"))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("ratings"))
			.andExpect(MockMvcResultMatchers.view().name("rating/list"));
		
	}
	
	@Test
	@WithMockUser
	public void getAccessToTheAddingPageAndCountainEmptyEntity() throws Exception {
		
		//Testing request
		mockMvc.perform(MockMvcRequestBuilders.get("/rating/add"))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("rating"))
			.andExpect(MockMvcResultMatchers.view().name("rating/add"));
		
	}
	
	@Test
	@WithMockUser
	@Transactional
	public void postNewValidRatingAndRedirectToThePageList() throws Exception {
		
		//Testing request
		mockMvc.perform(MockMvcRequestBuilders.post("/rating/validate")
				.with(SecurityMockMvcRequestPostProcessors.csrf())
				.param("moodysRating", "1")
				.param("sandPRating", "1")
				.param("fitchRating", "1")
				.param("orderNumber", "1"))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isFound())
			.andExpect(MockMvcResultMatchers.view().name("redirect:/rating/list"));
		
		//Get the last rating save in database
		List<Rating> ratings = ratingRepository.findAll();
		Rating rating = ratings.get(ratings.size() - 1);
		
		//Delete the object who was created
		ratingRepository.delete(rating);
		
	}
	
	@Test
	@WithMockUser
	public void postNoneValidateRatingAndShowFieldsError() throws Exception {
		
		//Testing request
		mockMvc.perform(MockMvcRequestBuilders.post("/rating/validate")
				.with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("rating","moodysRating", "sandPRating", "fitchRating", "orderNumber"))
			.andExpect(MockMvcResultMatchers.view().name("rating/add"));
		
	}
	
	@Test
	@WithMockUser
	@Transactional
	public void getTheUpdatePageAndCountainTheObject() throws Exception {
		
		//Save a new rating entity
		Rating newRating = new Rating("1", "1", "1", 1);
		ratingRepository.save(newRating);
		
		//Get the last rating save in database
		List<Rating> ratings = ratingRepository.findAll();
		Rating rating = ratings.get(ratings.size() - 1);
		
		//Testing request
		mockMvc.perform(MockMvcRequestBuilders.get("/rating/update/" + rating.getId()))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("rating"))
			.andExpect(MockMvcResultMatchers.view().name("rating/update"));
		
		//Delete the object who was created
		ratingRepository.delete(rating);
		
	}
	
	@Test
	@WithMockUser
	@Transactional
	public void postValidateUpdateRatingAndRedirectToThePageList() throws Exception {
		
		//Save a new rating entity
		Rating newRating = new Rating("1", "1", "1", 1);
		ratingRepository.save(newRating);
		
		//Get the last rating save in database
		List<Rating> ratings = ratingRepository.findAll();
		Rating rating = ratings.get(ratings.size() - 1);
		
		//Testing request
		
		mockMvc.perform(MockMvcRequestBuilders.post("/rating/update/" + rating.getId())
				.with(SecurityMockMvcRequestPostProcessors.csrf())
				.param("moodysRating", "2")
				.param("sandPRating", "1")
				.param("fitchRating", "2")
				.param("orderNumber", "1"))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isFound())
			.andExpect(MockMvcResultMatchers.view().name("redirect:/rating/list"));
		
		//Delete the object who was created
		ratingRepository.delete(rating);
		
	}
	
	@Test
	@WithMockUser
	@Transactional
	public void postNoneValidateUpdateRatingAndShowFieldsError() throws Exception {
		
		//Save a new rating entity
		Rating newRating = new Rating("1", "1", "1", 1);
		ratingRepository.save(newRating);
		
		//Get the last rating save in database
		List<Rating> ratings = ratingRepository.findAll();
		Rating rating = ratings.get(ratings.size() - 1);
		
		//Testing request
		mockMvc.perform(MockMvcRequestBuilders.post("/rating/update/" + rating.getId())
				.with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("rating","moodysRating", "sandPRating", "fitchRating", "orderNumber"))
			.andExpect(MockMvcResultMatchers.view().name("rating/update"));
		
	}
	
	@Test
	@WithMockUser
	@Transactional
	public void deleteAnExistingRatingObjectInTheDatabase() throws Exception {
		
		//Save a new rating entity
		Rating newRating = new Rating("1", "1", "1", 1);
		ratingRepository.save(newRating);
		
		//Get the last rating save in database
		List<Rating> ratings = ratingRepository.findAll();
		Rating rating = ratings.get(ratings.size() - 1);
		
		//Testing Request
		mockMvc.perform(MockMvcRequestBuilders.get("/rating/delete/" + rating.getId())
				.with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isFound())
			.andExpect(MockMvcResultMatchers.view().name("redirect:/rating/list"));
		
	}

}
