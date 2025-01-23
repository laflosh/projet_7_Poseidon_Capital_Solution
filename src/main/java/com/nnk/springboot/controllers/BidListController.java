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

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.service.BidListService;

import jakarta.validation.Valid;

/**
 * This controller manage all the front end page and CRUD operations for the bidlist domain
 */
@Controller
public class BidListController {

	private static  final Logger log = LogManager.getLogger(BidListController.class);

	@Autowired
	private BidListService bidListService;

    /**
     * Fetching all the bidlists and return the page list to see all bidlists
     *
     * @param model
     * @param user auth
     * @return bidlist'list page
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
     * Return the add page with an empty bidlist entity
     *
     * @param New empty bid
     * @param model
     * @return bidlist's add page
     */
    @GetMapping("/bidList/add")
    public String addBidForm(BidList bidList, Model model) {

    	log.info("Access to the bidlist add page.");

    	model.addAttribute("bidList", new BidList());

        return "bidList/add";

    }

    /**
     * Check all the data in the new entity, save it in the database
     * and redirect to the bidlist's list page
     *
     * @param new bid
     * @param result
     * @param model
     * @param User auth
     * @return redirect to the bidlist's list page
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

    		return "redirect:/bidList/list";

    	} catch (Exception e) {

    		log.info("Error during saving the bidlist object : {} .", e);
    		return "bidList/add";

    	}

    }

    /**
     * Get the bidlist entity to update the data, add it to the model of the update page
     * and return the update page with the bidlist entity
     *
     * @param id of the bidlist
     * @param model
     * @return bidlist's update page
     */
    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

    	log.info("Access to the bidlist update page.");

    	BidList bidList = bidListService.getBidListById(id);

    	model.addAttribute("bidList", bidList);

        return "bidList/update";
    }

    /**
     * Check all data in the update entity, save it in the database
     * and redirect to the bidlist's list page
     *
     * @param id of the bidlist
     * @param update bidList
     * @param result
     * @param model
     * @param User auth
     * @return redirect to the bidlist's list page
     */
    @PostMapping("/bidList/update/{bidListId}")
    public String updateBid(@PathVariable("bidListId") Integer bidListId, @Valid BidList bidList, BindingResult result, Model model, Authentication auth) {

    	if(result.hasErrors()) {

    		log.info("Error in the bidlist object : {} .", result.getAllErrors());
    		return"bidList/update";

    	}

    	try {

    		log.info("Trying to update the existing bidlist in database with id : {} .", bidListId);

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
     * Delete the selected bidlist entity and redirect to the bidlist's list page
     *
     * @param id of the bidlist
     * @param model
     * @param User auth
     * @return redirect to the bidlist' list page
     */
    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model, Authentication auth) {

    	log.info("Trying to delete an existing bidlist in the database with id : {} .", id);

       	boolean isDelete = bidListService.deleteBidList(id);

       	if(isDelete) {

    		model.addAttribute("bidLists", bidListService.getAllBidLists());
    		model.addAttribute("username", auth.getName());

       		return "redirect:/bidList/list";

       	}

        return "redirect:/bidList/list";

    }

}
