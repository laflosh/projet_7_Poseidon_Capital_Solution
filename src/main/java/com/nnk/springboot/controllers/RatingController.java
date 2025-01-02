package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.service.RatingService;

import jakarta.validation.Valid;

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

/**
 * 
 */
@Controller
public class RatingController {
	
	private static  final Logger log = LogManager.getLogger(RatingController.class);
    
	@Autowired
	RatingService ratingService;

    /**
     * @param model
     * @param auth
     * @return
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
     * @param rating
     * @param model
     * @return
     */
    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating, Model model) {
    	
    	log.info("Acces to the adding rating page");
    	
    	model.addAttribute("rating", new Rating());
    	
        return "rating/add";
        
    }

    /**
     * @param rating
     * @param result
     * @param model
     * @param auth
     * @return
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
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
    	
    	log.info("Acces to the update rating page");
    	
    	Rating rating  = ratingService.getRatingById(id);
    	
    	model.addAttribute("rating", rating);
    	
        return "rating/update";
        
    }

    /**
     * @param id
     * @param rating
     * @param result
     * @param model
     * @param auth
     * @return
     */
    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating, BindingResult result, Model model, Authentication auth) {
    	
    	if(result.hasErrors()) {
    		
    		log.info("Error in the rating object : {}", result.getAllErrors());
    		return "rating/update";
    		
    	}
    	
    	try {
    		
    		log.info("Trying to update a existing rating in the database : {}", rating);
    		
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
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
    	
    	log.info("Trying to delete a existing rating in the database with id : {}",id);
    	
    	boolean isDelete = ratingService.deleteRating(id);
    	
    	if(isDelete == true) {
    		
    		return "redirect:/rating/list";
    		
    	}
    	
		return null;
		
    }
}
