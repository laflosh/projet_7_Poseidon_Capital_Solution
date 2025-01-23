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
 * This service contain all the methods to execute CRUD operations for the rating domain
 */
@Service
public class RatingService {

	private static  final Logger log = LogManager.getLogger(RatingService.class);

	@Autowired
	private RatingRepository ratingRepository;

	/**
	 * Fetching all the ratings entity in the database
	 *
	 * @return A list of ratings
	 */
	public List<Rating> getAllRatings() {

		log.info("Get all ratings in the database.");

		List<Rating> ratings = ratingRepository.findAll();

		return ratings;

	}

	/**
	 * Fetching one rating depending of the id in the database
	 *
	 * @param id of the rating
	 * @return Rating
	 */
	public Rating getRatingById(Integer id) {

		log.info("get one rating by the id : {} .", id);

		Rating rating = ratingRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Rating not found."));

		return rating;
	}

	/**
	 * Add a new valid rating entity in the database
	 *
	 * @param new rating
	 */
	public void addNewRating(@Valid Rating rating) {

		log.info("Add a new rating object in the database : {} .", rating);

		ratingRepository.save(rating);

	}

	/**
	 * Update a valid existing rating entity in the database
	 *
	 * @param update rating
	 */
	public void updateRating(@Valid Rating rating) {

		log.info("Update the rating object existing in the database with id : {} .", rating.getId());

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
	 * Delete an existing rating entity in the database depending of the id
	 *
	 * @param id of the rating
	 * @return true if delete
	 */
	public boolean deleteRating(Integer id) {

		log.info("Delete the rating object in the database with id : {} .", id);

		if(ratingRepository.existsById(id)) {

			ratingRepository.deleteById(id);

			return true;

		}

		return false;
	}

}
