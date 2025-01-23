package com.nnk.springboot.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.util.DataValidator;

import jakarta.validation.Valid;

/**
 * This service contain all the methods to execute CRUD operations for the bidlist domain
 */
@Service
public class BidListService {

	private static final Logger log = LogManager.getLogger(BidListService.class);

	@Autowired
	private BidListRepository bidListRepository;
	
	@Autowired
	private DataValidator dataValidator;

	/**
	 * Fetching all the bidlists entity in the database
	 *
	 * @return A list of bidlists
	 */
	public List<BidList> getAllBidLists() {

		log.info("Get all bidlists in the database");

		List<BidList> bidLists = bidListRepository.findAll();

		return bidLists;
	}

	/**
	 * Fetching one bidlist entity in the database depending of the id
	 *
	 * @param id of the bidlist
	 * @return Bidlist
	 */
	public BidList getBidListById(Integer id) {

		log.info("Get one bidlist in the database");

		BidList bidList = bidListRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("BidList not found."));

		return bidList;

	}

	/**
	 * Add a new bidlist entity in the database
	 *
	 * @param new bid
	 */
	public void addNewBidList(@Valid BidList bidList) {
		
		if(dataValidator.checkString(bidList.getAccount()) &&
			dataValidator.checkString(bidList.getType()) && 
			dataValidator.checkDouble(bidList.getBidQuantity().toString()) == true) {
			
			log.info("Add new bidlist object in the database : {} .", bidList);

			bidListRepository.save(bidList);
			
		}

	}

	/**
	 * Update a bidlist entity existing in the database
	 *
	 * @param update bidList
	 */
	public void updateBidList(@Valid BidList bidList) {

		if(dataValidator.checkString(bidList.getAccount()) &&
			dataValidator.checkString(bidList.getType()) && 
			dataValidator.checkDouble(bidList.getBidQuantity().toString()) == true) {
			
			log.info("Update bidlist object existing in the database with id : {} .", bidList.getBidListId());

			BidList existingBidList = getBidListById(bidList.getBidListId());

			if(bidList.getAccount() != existingBidList.getAccount()) {
				existingBidList.setAccount(bidList.getAccount());
			}

			if(bidList.getType() != existingBidList.getType()) {
				existingBidList.setType(bidList.getType());
			}

			if(bidList.getBidQuantity() != existingBidList.getBidQuantity()) {
				existingBidList.setBidQuantity(bidList.getBidQuantity());
			}

			bidListRepository.save(existingBidList);
			
		}

	}

	/**
	 * Delete an existing bidlist entity in the database depending of the id
	 *
	 * @param id of the bidlist
	 * @return true if delete
	 */
	public boolean deleteBidList(Integer id) {

		log.info("Delete bidlist in the database with id : {} .", id);

		if(bidListRepository.existsById(id)) {

			bidListRepository.deleteById(id);

			return true;

		}

		return false;

	}

}
