package com.nnk.springboot.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.controllers.RatingController;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;

import jakarta.validation.Valid;

/**
 * 
 */
@Service
public class RatingService {
	
	private static  final Logger log = LogManager.getLogger(RatingService.class);
	
	@Autowired
	RatingRepository ratingRepository;

	/**
	 * @return
	 */
	public List<Rating> getAllRatings() {

		List<Rating> ratings = ratingRepository.findAll();
		
		return ratings;
		
	}
	
	/**
	 * @param id
	 * @return
	 */
	public Rating getRatingById(Integer id) {
		
		Rating rating = ratingRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Rating not found."));
		
		return rating;
	}

	/**
	 * @param rating
	 */
	public void addNewRating(@Valid Rating rating) {
		
		ratingRepository.save(rating);
		
	}

	/**
	 * @param rating
	 */
	public void updateRating(@Valid Rating rating) {
		
		Rating existingRating = getRatingById(rating.getId());
		
		existingRating.setMoodysRating(rating.getMoodysRating());
		existingRating.setSandPRating(rating.getSandPRating());
		existingRating.setFitchRating(rating.getFitchRating());
		existingRating.setOrderNumber(rating.getOrderNumber());
		
		ratingRepository.save(existingRating);
		
	}

	/**
	 * @param id
	 * @return
	 */
	public boolean deleteRating(Integer id) {

		if(ratingRepository.existsById(id)) {
			
			ratingRepository.deleteById(id);
			
			return true;
			
		}
		
		return false;
	}

}
