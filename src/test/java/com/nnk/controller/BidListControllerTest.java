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
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;

import jakarta.transaction.Transactional;

@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
class BidListControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	BidListRepository bidListRepository;

	@Test
	@WithMockUser
	public void getAllBidListsInDatabaseAndReturnListPageWithData() throws Exception {

		//Testing request
		mockMvc.perform(MockMvcRequestBuilders.get("/bidList/list"))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("bidLists"))
			.andExpect(MockMvcResultMatchers.view().name("bidList/list"));

	}

	@Test
	@WithMockUser
	public void getAccessToTheAddingPageAndCountainEmptyEntity() throws Exception {

		//Testing request
		mockMvc.perform(MockMvcRequestBuilders.get("/bidList/add"))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("bidList"))
			.andExpect(MockMvcResultMatchers.view().name("bidList/add"));

	}

	@Test
	@WithMockUser
	@Transactional
	public void postNewValidBidListAndRedirectToThePageList() throws Exception {

		//Testing request
		mockMvc.perform(MockMvcRequestBuilders.post("/bidList/validate")
				.with(SecurityMockMvcRequestPostProcessors.csrf())
				.param("account", "ACCOUNT")
				.param("type", "TYPE")
				.param("bidQuantity", "1"))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isFound())
			.andExpect(MockMvcResultMatchers.view().name("redirect:/bidList/list"));

		//Get the last bidlist save in database
		List<BidList> bidLists = bidListRepository.findAll();
		BidList bidList = bidLists.get(bidLists.size() - 1);

		//Delete the object who was created
		bidListRepository.delete(bidList);

	}

	@Test
	@WithMockUser
	public void postNoneValidateBidListdAndShowFieldsError() throws Exception {

		//Testing request
		mockMvc.perform(MockMvcRequestBuilders.post("/bidList/validate")
				.with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model()
					.attributeHasFieldErrors("bidList","account", "type", "bidQuantity"))
			.andExpect(MockMvcResultMatchers.view().name("bidList/add"));

	}

	@Test
	@WithMockUser
	@Transactional
	public void getTheUpdatePageAndCountainTheObject() throws Exception {

		//Save a new rating entity
		BidList newBidList = new BidList("ACCOUNT", "TYPE", 1);
		bidListRepository.save(newBidList);

		//Get the last bidlist save in database
		List<BidList> bidLists = bidListRepository.findAll();
		BidList bidList = bidLists.get(bidLists.size() - 1);

		//Testing request
		mockMvc.perform(MockMvcRequestBuilders.get("/bidList/update/" + bidList.getBidListId()))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("bidList"))
			.andExpect(MockMvcResultMatchers.view().name("bidList/update"));

		//Delete the object who was created
		bidListRepository.delete(bidList);

	}

	@Test
	@WithMockUser
	@Transactional
	public void postValidateUpdateBidListAndRedirectToThePageList() throws Exception {

		//Save a new rating entity
		BidList newBidList = new BidList("ACCOUNT", "TYPE", 1);
		bidListRepository.save(newBidList);

		//Get the last bidlist save in database
		List<BidList> bidLists = bidListRepository.findAll();
		BidList bidList = bidLists.get(bidLists.size() - 1);

		//Testing request

		mockMvc.perform(MockMvcRequestBuilders.post("/bidList/update/" + bidList.getBidListId())
				.with(SecurityMockMvcRequestPostProcessors.csrf())
				.param("account", "account")
				.param("type", "TYPE")
				.param("bidQuantity", "3"))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isFound())
			.andExpect(MockMvcResultMatchers.view().name("redirect:/bidList/list"));

		//Delete the object who was created
		bidListRepository.delete(bidList);

	}

	@Test
	@WithMockUser
	@Transactional
	public void postNoneValidateUpdateBidListAndShowFieldsError() throws Exception {

		//Save a new rating entity
		BidList newBidList = new BidList("ACCOUNT", "TYPE", 1);
		bidListRepository.save(newBidList);

		//Get the last bidlist save in database
		List<BidList> bidLists = bidListRepository.findAll();
		BidList bidList = bidLists.get(bidLists.size() - 1);

		//Testing request
		mockMvc.perform(MockMvcRequestBuilders.post("/bidList/update/" + bidList.getBidListId())
				.with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model()
					.attributeHasFieldErrors("bidList","account", "type", "bidQuantity"))
			.andExpect(MockMvcResultMatchers.view().name("bidList/update"));

	}

	@Test
	@WithMockUser
	@Transactional
	public void deleteAnExistingBidListObjectInTheDatabase() throws Exception {

		//Save a new rating entity
		BidList newBidList = new BidList("ACCOUNT", "TYPE", 1);
		bidListRepository.save(newBidList);

		//Get the last bidlist save in database
		List<BidList> bidLists = bidListRepository.findAll();
		BidList bidList = bidLists.get(bidLists.size() - 1);

		//Testing Request
		mockMvc.perform(MockMvcRequestBuilders.get("/bidList/delete/" + bidList.getBidListId())
				.with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isFound())
			.andExpect(MockMvcResultMatchers.view().name("redirect:/bidList/list"));

	}

}
