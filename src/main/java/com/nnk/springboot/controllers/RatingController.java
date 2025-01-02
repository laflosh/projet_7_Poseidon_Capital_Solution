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

@Controller
public class RatingController {
	
	private static  final Logger log = LogManager.getLogger(RatingController.class);
    
	@Autowired
	RatingService ratingService;

    @RequestMapping("/rating/list")
    public String home(Model model, Authentication auth )
    {
    	
    	log.info("Trying to get all ratings in the database.");
    	
        List<Rating> ratings = ratingService.getAllRatings();
        
        model.addAttribute("ratings", ratings);
        model.addAttribute("username", auth.getName());
    	
        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating, Model model) {
    	
    	log.info("Acces to the adding rating page");
    	
    	model.addAttribute("rating", new Rating());
    	
        return "rating/add";
        
    }

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

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get Rating by Id and to model then show to the form
        return "rating/update";
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Rating and return Rating list
        return "redirect:/rating/list";
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Rating by Id and delete the Rating, return to Rating list
        return "redirect:/rating/list";
    }
}
