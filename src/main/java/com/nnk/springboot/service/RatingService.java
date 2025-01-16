package com.nnk.springboot.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	private RatingRepository ratingRepository;

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

		if(rating.getMoodysRating() != existingRating.getMoodysRating()) {
			existingRating.setMoodysRating(rating.getMoodysRating());
		}

		if(rating.getSandPRating() != existingRating.getSandPRating()) {
			existingRating.setSandPRating(rating.getSandPRating());
		}

		if(rating.getFitchRating() != existingRating.getFitchRating()) {
			existingRating.setFitchRating(rating.getFitchRating());
		}
		
		if(rating.getOrderNumber() != existingRating.getOrderNumber()) {
			existingRating.setOrderNumber(rating.getOrderNumber());
		}

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
