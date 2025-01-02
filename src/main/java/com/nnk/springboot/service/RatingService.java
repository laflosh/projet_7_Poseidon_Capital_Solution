package com.nnk.springboot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;

import jakarta.validation.Valid;

@Service
public class RatingService {
	
	@Autowired
	RatingRepository ratingRepository;

	public List<Rating> getAllRatings() {

		List<Rating> ratings = ratingRepository.findAll();
		
		return ratings;
		
	}
	
	public Rating getRatingById(Integer id) {
		
		Rating rating = ratingRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Rating not found."));;
		
		return rating;
	}

	public void addNewRating(@Valid Rating rating) {
		
		ratingRepository.save(rating);
		
	}

	public void updateRating(@Valid Rating rating) {
		
		Rating existingRating = getRatingById(rating.getId());
		
		existingRating.setMoodysRating(rating.getMoodysRating());
		existingRating.setSandPRating(rating.getSandPRating());
		existingRating.setFitchRating(rating.getFitchRating());
		existingRating.setOrderNumber(rating.getOrderNumber());
		
		ratingRepository.save(existingRating);
		
	}

}
