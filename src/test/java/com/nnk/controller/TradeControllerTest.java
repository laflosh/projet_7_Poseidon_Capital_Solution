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
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;

import jakarta.transaction.Transactional;

@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class TradeControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	TradeRepository tradeRepository;

	@Test
	@WithMockUser
	public void getAllTradesInDatabaseAndReturnListPageWithData() throws Exception {

		//Testing request
		mockMvc.perform(MockMvcRequestBuilders.get("/trade/list"))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("trades"))
			.andExpect(MockMvcResultMatchers.view().name("trade/list"));

	}

	@Test
	@WithMockUser
	public void getAccessToTheAddingPageAndCountainEmptyEntity() throws Exception {

		//Testing request
		mockMvc.perform(MockMvcRequestBuilders.get("/trade/add"))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("trade"))
			.andExpect(MockMvcResultMatchers.view().name("trade/add"));

	}

	@Test
	@WithMockUser
	@Transactional
	public void postNewValidTradeAndRedirectToThePageList() throws Exception {

		//Testing request
		mockMvc.perform(MockMvcRequestBuilders.post("/trade/validate")
				.with(SecurityMockMvcRequestPostProcessors.csrf())
				.param("account", "ACCOUNT")
				.param("type", "TYPE")
				.param("buyQuantity", "1"))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isFound())
			.andExpect(MockMvcResultMatchers.view().name("redirect:/trade/list"));

		//Get the last trade save in database
		List<Trade> trades = tradeRepository.findAll();
		Trade trade = trades.get(trades.size() - 1);

		//Delete the object who was created
		tradeRepository.delete(trade);

	}

	@Test
	@WithMockUser
	public void postNoneValidateTradedAndShowFieldsError() throws Exception {

		//Testing request
		mockMvc.perform(MockMvcRequestBuilders.post("/trade/validate")
				.with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model()
					.attributeHasFieldErrors("trade","account", "type", "buyQuantity"))
			.andExpect(MockMvcResultMatchers.view().name("trade/add"));

	}

	@Test
	@WithMockUser
	@Transactional
	public void getTheUpdatePageAndCountainTheObject() throws Exception {

		//Save a new rating entity
		Trade newTrade = new Trade("ACCOUNT", "TYPE", 1.0);
		tradeRepository.save(newTrade);

		//Get the last trade save in database
		List<Trade> trades = tradeRepository.findAll();
		Trade trade = trades.get(trades.size() - 1);

		//Testing request
		mockMvc.perform(MockMvcRequestBuilders.get("/trade/update/" + trade.getTradeId()))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("trade"))
			.andExpect(MockMvcResultMatchers.view().name("trade/update"));

		//Delete the object who was created
		tradeRepository.delete(trade);

	}

	@Test
	@WithMockUser
	@Transactional
	public void postValidateUpdateTradeAndRedirectToThePageList() throws Exception {

		//Save a new rating entity
		Trade newTrade = new Trade("ACCOUNT", "TYPE", 1.0);
		tradeRepository.save(newTrade);

		//Get the last trade save in database
		List<Trade> trades = tradeRepository.findAll();
		Trade trade = trades.get(trades.size() - 1);

		//Testing request
		mockMvc.perform(MockMvcRequestBuilders.post("/trade/update/" + trade.getTradeId())
				.with(SecurityMockMvcRequestPostProcessors.csrf())
				.param("account", "account")
				.param("type", "TYPE")
				.param("buyQuantity", "3"))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isFound())
			.andExpect(MockMvcResultMatchers.view().name("redirect:/trade/list"));

		//Delete the object who was created
		tradeRepository.delete(trade);

	}

	@Test
	@WithMockUser
	@Transactional
	public void postNoneValidateUpdateTradeAndShowFieldsError() throws Exception {

		//Save a new rating entity
		Trade newTrade = new Trade("ACCOUNT", "TYPE", 1.0);
		tradeRepository.save(newTrade);

		//Get the last trade save in database
		List<Trade> trades = tradeRepository.findAll();
		Trade trade = trades.get(trades.size() - 1);

		//Testing request
		mockMvc.perform(MockMvcRequestBuilders.post("/trade/update/" + trade.getTradeId())
				.with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model()
					.attributeHasFieldErrors("trade","account", "type", "buyQuantity"))
			.andExpect(MockMvcResultMatchers.view().name("trade/update"));

	}

	@Test
	@WithMockUser
	@Transactional
	public void deleteAnExistingBidListObjectInTheDatabase() throws Exception {

		//Save a new rating entity
		Trade newTrade = new Trade("ACCOUNT", "TYPE", 1.0);
		tradeRepository.save(newTrade);

		//Get the last trade save in database
		List<Trade> trades = tradeRepository.findAll();
		Trade trade = trades.get(trades.size() - 1);

		//Testing Request
		mockMvc.perform(MockMvcRequestBuilders.get("/trade/delete/" + trade.getTradeId())
				.with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isFound())
			.andExpect(MockMvcResultMatchers.view().name("redirect:/trade/list"));

	}

}
