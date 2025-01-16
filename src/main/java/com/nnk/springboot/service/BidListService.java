package com.nnk.springboot.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;

import jakarta.validation.Valid;

/**
 *
 */
@Service
public class BidListService {

	private static final Logger log = LogManager.getLogger(BidListService.class);

	@Autowired
	private BidListRepository bidListRepository;

	/**
	 * @return
	 */
	public List<BidList> getAllBidLists() {

		log.info("Get all bidlists in the database");
		
		List<BidList> bidLists = bidListRepository.findAll();

		return bidLists;
	}

	/**
	 * @param id
	 * @return
	 */
	public BidList getBidListById(Integer id) {

		log.info("Get one bidlist in the database");
		
		BidList bidList = bidListRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("BidList not found."));

		return bidList;

	}

	/**
	 * @param bid
	 */
	public void addNewBidList(@Valid BidList bid) {

		log.info("Add new bidlist object in the database : {} .", bid);
		
		bidListRepository.save(bid);

	}

	/**
	 * @param bidList
	 */
	public void updateBidList(@Valid BidList bidList) {

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

	/**
	 * @param id
	 * @return
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
