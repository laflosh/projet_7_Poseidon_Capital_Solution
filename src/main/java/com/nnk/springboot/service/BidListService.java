package com.nnk.springboot.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;

import jakarta.validation.Valid;

@Service
public class BidListService {
	
	private static  final Logger log = LogManager.getLogger(BidListService.class);
	
	@Autowired
	BidListRepository bidListRepository;

	public List<BidList> getAllBidLists() {
		
		List<BidList> bidLists = bidListRepository.findAll();
		
		return bidLists;
	}

	public void addNewBidList(@Valid BidList bid) {

		bidListRepository.save(bid);
		
	}

}
