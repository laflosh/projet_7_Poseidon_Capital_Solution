package com.nnk.springboot.controllers;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.service.RatingService;

import jakarta.validation.Valid;

/**
 * This controller manage all the front end page and CRUD operations for the rating domain
 */
@Controller
public class RatingController {

	private static  final Logger log = LogManager.getLogger(RatingController.class);

	@Autowired
	private RatingService ratingService;

    /**
     * Fetching all the ratings and return the page list to see all the rating
     *
     * @param model
     * @param user auth
     * @return rating's page list
     */
    @RequestMapping("/rating/list")
    public String home(Model model, Authentication auth )
    {

    	log.info("Trying to get all ratings in the database.");

        List<Rating> ratings = ratingService.getAllRatings();

        model.addAttribute("ratings", ratings);
        model.addAttribute("username", auth.getName());

        return "rating/list";
    }

    /**
     * Return the add page for rating with an empty rating entity
     *
     * @param New empty rating
     * @param model
     * @return rating's add page
     */
    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating, Model model) {

    	log.info("Access to the adding rating page");

    	model.addAttribute("rating", new Rating());

        return "rating/add";

    }

    /**
     * Check all the data in the new entity, save it in the database
     * and redirect to the rating's list page
     *
     * @param new rating
     * @param result
     * @param model
     * @param user auth
     * @return redirect to the rating's list page
     */
    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model, Authentication auth) {

    	if(result.hasErrors()) {

    		log.info("Error in the rating object : {}", result.getAllErrors());
    		return "rating/add";

    	}

    	try {

    		log.info("Trying to save a new rating in the database : {}", rating);

        	ratingService.addNewRating(rating);

            model.addAttribute("ratings", ratingService.getAllRatings());
            model.addAttribute("username", auth.getName());

            return "redirect:/rating/list";

    	} catch(Exception e ) {

    		log.info("Error during saving the rating object : {}", e);
    		return "rating/add";

    	}

    }

    /**
     * Get the rating entity to update the data, add it to the model of the update page
     * and return the update page with the rating entity
     *
     * @param id of the rating to update
     * @param model
     * @return rating's update page
     */
    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

    	log.info("Access to the update rating page");

    	Rating rating  = ratingService.getRatingById(id);

    	model.addAttribute("rating", rating);

        return "rating/update";

    }

    /**
     * Check all data in the update entity, save it in the database
     * and redirect to the rating's list page
     *
     * @param id of the update rating
     * @param update rating
     * @param result
     * @param model
     * @param User auth
     * @return rating's list page
     */
    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating, BindingResult result, Model model, Authentication auth) {

    	if(result.hasErrors()) {

    		log.info("Error in the rating object : {}", result.getAllErrors());
    		return "rating/update";

    	}

    	try {

    		log.info("Trying to update a existing rating in the database with id : {}", id);

        	ratingService.updateRating(rating);

            model.addAttribute("ratings", ratingService.getAllRatings());
            model.addAttribute("username", auth.getName());

    		 return "redirect:/rating/list";

    	} catch(Exception e) {

    		log.info("Error during updating the rating object : {}", e);
    		return "rating/update";

    	}

    }

    /**
     * Delete the selected rating entity and redirect to the rating's list page
     *
     * @param id of the rating
     * @param model
     * @param User auth
     * @return rating's list page
     */
    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model, Authentication auth) {

    	log.info("Trying to delete a existing rating in the database with id : {}",id);

    	boolean isDelete = ratingService.deleteRating(id);

    	if(isDelete) {

            model.addAttribute("ratings", ratingService.getAllRatings());
            model.addAttribute("username", auth.getName());

    		return "redirect:/rating/list";

    	}

		return "redirect:/rating/list";

    }
}
