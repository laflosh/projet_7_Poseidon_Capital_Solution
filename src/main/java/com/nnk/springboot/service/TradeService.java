package com.nnk.springboot.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;

import jakarta.validation.Valid;

/**
 * 
 */
@Service
public class TradeService {

	private static final Logger log = LogManager.getLogger(TradeService.class);
	
	@Autowired
	TradeRepository tradeRepository;

	/**
	 * @return
	 */
	public List<Trade> getAllTrades() {

		List<Trade> trades = tradeRepository.findAll();

		return trades;
		
	}

	/**
	 * @param trade
	 */
	public void addNewTrade(@Valid Trade trade) {

		tradeRepository.save(trade);
		
	}
	
}
