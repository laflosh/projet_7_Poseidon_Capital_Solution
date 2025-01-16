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
	private TradeRepository tradeRepository;

	/**
	 * @return
	 */
	public List<Trade> getAllTrades() {

		List<Trade> trades = tradeRepository.findAll();

		return trades;

	}

	/**
	 * @param id
	 * @return
	 */
	public Trade getTradeById(Integer id) {

		Trade trade = tradeRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Trade not found"));

		return trade;

	}

	/**
	 * @param trade
	 */
	public void addNewTrade(@Valid Trade trade) {

		tradeRepository.save(trade);

	}

	/**
	 * @param trade
	 */
	public void updateTrade(@Valid Trade trade) {

		Trade existingTrade = getTradeById(trade.getTradeId());

		if(trade.getAccount() != existingTrade.getAccount()) {
			existingTrade.setAccount(trade.getAccount());
		}
		
		if(trade.getType() != existingTrade.getType()) {
			existingTrade.setType(trade.getType());
		}
		
		if(trade.getBuyQuantity() != existingTrade.getBuyQuantity()) {
			existingTrade.setBuyQuantity(trade.getBuyQuantity());
		}

		tradeRepository.save(existingTrade);

	}

	/**
	 * @param id
	 * @return
	 */
	public boolean deleteTrade(Integer id) {

		if(tradeRepository.existsById(id)) {

			tradeRepository.deleteById(id);

			return true;

		}

		return false;

	}

}
