package com.nnk.springboot.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.util.DataValidator;

import jakarta.validation.Valid;

/**
 * This service contain all the methods to execute CRUD operations for the trade domain
 */
@Service
public class TradeService {

	private static final Logger log = LogManager.getLogger(TradeService.class);

	@Autowired
	private TradeRepository tradeRepository;

	@Autowired
	private DataValidator dataValidator;


	/**
	 * Fetching all trades entity in the database
	 *
	 * @return A list of trades
	 */
	public List<Trade> getAllTrades() {

		log.info("Get all trades in the database");

		List<Trade> trades = tradeRepository.findAll();

		return trades;

	}

	/**
	 * Fetching one trade entity depending of the id in the database
	 *
	 * @param id of the user
	 * @return User
	 */
	public Trade getTradeById(Integer id) {

		log.info("Get one trade in the database");

		Trade trade = tradeRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Trade not found"));

		return trade;

	}

	/**
	 * Add a new trade entity in the database
	 *
	 * @param new trade
	 */
	public void addNewTrade(@Valid Trade trade) {

		if(dataValidator.checkString(trade.getAccount()) &&
			dataValidator.checkString(trade.getType()) &&
			dataValidator.checkDouble(trade.getBuyQuantity().toString())) {

			log.info("Add new trade object in the database : {} .", trade);

			tradeRepository.save(trade);

		}

	}

	/**
	 * Update the existing trade entity in the database
	 *
	 * @param update trade
	 */
	public void updateTrade(@Valid Trade trade) {

		if(dataValidator.checkString(trade.getAccount()) &&
			dataValidator.checkString(trade.getType()) &&
			dataValidator.checkDouble(trade.getBuyQuantity().toString())) {

			log.info("Update trade object existing in the database with id : {} .", trade.getTradeId());

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

	}

	/**
	 * Delete an existing trade entity in the database depending of the id
	 *
	 * @param id of the trade
	 * @return true if delete
	 */
	public boolean deleteTrade(Integer id) {

		log.info("Delete trade object in the database with id : {} .", id);

		if(tradeRepository.existsById(id)) {

			tradeRepository.deleteById(id);

			return true;

		}

		return false;

	}

}
