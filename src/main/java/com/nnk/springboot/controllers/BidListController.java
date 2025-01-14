package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.service.BidListService;

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
public class BidListController {
	
	private static  final Logger log = LogManager.getLogger(BidListController.class);
    
	@Autowired
	BidListService bidListService;

    /**
     * @param model
     * @param auth
     * @return
     */
    @RequestMapping("/bidList/list")
    public String home(Model model, Authentication auth)
    {
    	log.info("Trying to get all bidlists in database.");

    	List<BidList> bidLists = bidListService.getAllBidLists();
    	
    	model.addAttribute("bidLists", bidLists); 
    	model.addAttribute("username", auth.getName());    	
        return "bidList/list";
        
    }

    /**
     * @param bid
     * @param model
     * @return
     */
    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid, Model model) {
    	
    	log.info("Cacces to the bidlist add page.");
    	
    	model.addAttribute("bidList", new BidList());
    	
        return "bidList/add";
        
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bid, BindingResult result, Model model, Authentication auth) {
    	
    	if(result.hasErrors()) {
    		
    		log.info("Error in the bidlist object : {} .", result.getAllErrors());
    		return "bidList/add";
    		
    	}
    	
    	try {
    		
    		log.info("Trying to save a new bidlist in the database : {} .", bid);
    		
    		bidListService.addNewBidList(bid);
    		
    		model.addAttribute("bidLists", bidListService.getAllBidLists());
    		model.addAttribute("username", auth.getName());
    		
    		return "bidList/list";
    		
    	} catch (Exception e) {
    		
    		log.info("Error during saving the bidlist object : {} .", e);
    		return "bidList/add";
    		
    	}
        
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get Bid by Id and to model then show to the form
        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList, BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Bid and return list Bid
        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Bid by Id and delete the bid, return to Bid list
        return "redirect:/bidList/list";
    }
}
