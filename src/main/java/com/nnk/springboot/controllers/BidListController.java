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
    public String addBidForm(BidList bidList, Model model) {
    	
    	log.info("Access to the bidlist add page.");
    	
    	model.addAttribute("bidList", new BidList());
    	
        return "bidList/add";
        
    }

    /**
     * @param bid
     * @param result
     * @param model
     * @param auth
     * @return
     */
    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bidList, BindingResult result, Model model, Authentication auth) {
    	
    	if(result.hasErrors()) {
    		
    		log.info("Error in the bidlist object : {} .", result.getAllErrors());
    		return "bidList/add";
    		
    	}
    	
    	try {
    		
    		log.info("Trying to save a new bidlist in the database : {} .", bidList);
    		
    		bidListService.addNewBidList(bidList);
    		
    		model.addAttribute("bidLists", bidListService.getAllBidLists());
    		model.addAttribute("username", auth.getName());
    		
    		return "bidList/list";
    		
    	} catch (Exception e) {
    		
    		log.info("Error during saving the bidlist object : {} .", e);
    		return "bidList/add";
    		
    	}
        
    }

    /**
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        
    	log.info("Access to the bidlist update page.");
    	
    	BidList bidList = bidListService.getBidListById(id);
    	
    	model.addAttribute("bidList", bidList);
    	
        return "bidList/update";
    }

    /**
     * @param id
     * @param bidList
     * @param result
     * @param model
     * @param auth
     * @return
     */
    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList, BindingResult result, Model model, Authentication auth) {
        
    	if(result.hasErrors()) {
    		
    		log.info("Error in the bidlist object : {} .", result.getAllErrors());
    		return"bidList/update";
    		
    	}
    	
    	try {
    		
    		log.info("Trying to update the existing bidlist in database : {} .", bidList);
    		
    		bidListService.updateBidList(bidList);
    		
    		model.addAttribute("bidLists", bidListService.getAllBidLists());
    		model.addAttribute("username", auth.getName());
    		
            return "redirect:/bidList/list";
    		
    	} catch (Exception e) {
    		
    		log.info("Error during updating the bidlist object : {} .", e);
    		return"bidList/update";
    		
    	}
        
    }

    /**
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {

    	log.info("Trying to delete an existing bidlist in the database with id : {} .", id);

       	boolean isDelete = bidListService.deleteBidList(id);
    	
       	if(isDelete == true) {
       		
       		return "redirect:/bidList/list";
       		
       	}
       	
        return null;
        
    }
    
}
