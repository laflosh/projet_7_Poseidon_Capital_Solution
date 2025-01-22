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

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.service.TradeService;

import jakarta.validation.Valid;

/**
 * This controller manage all the front end page and CRUD operations for the trade domain
 */
@Controller
public class TradeController {

	private static final Logger log = LogManager.getLogger(TradeController.class);

	@Autowired
	private TradeService tradeService;

    /**
     * Fetching all the trades and return the page list to see all trades
     * 
     * @param model
     * @param User auth
     * @return trade's list page
     */
    @RequestMapping("/trade/list")
    public String home(Model model, Authentication auth)
    {

    	log.info("Trying to get all trades in the database.");

    	List<Trade> trades = tradeService.getAllTrades();

    	model.addAttribute("trades", trades);
    	model.addAttribute("username", auth.getName());

        return "trade/list";

    }

    /**
     * Return the add page with an empty trade entity
     * 
     * @param New empty trade
     * @param model
     * @return trade's add page
     */
    @GetMapping("/trade/add")
    public String addUser(Trade trade, Model model) {

    	log.info("Access to the trade add page.");

    	model.addAttribute("trade", new Trade());

        return "trade/add";

    }

    /**
     * Check all the data in the new entity, save it in the database 
     * and redirect to the trade's list page
     * 
     * @param New trade
     * @param result
     * @param model
     * @param User auth
     * @return redirect to the trade's list page
     */
    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model, Authentication auth) {

    	if(result.hasErrors()) {

    		log.info("Error in the trade object : {} .", result.getAllErrors());
    		return "trade/add";

    	}

    	try {

    		log.info("Trying to save a new trade in database : {} .", trade);

    		tradeService.addNewTrade(trade);

    		model.addAttribute("trades", tradeService.getAllTrades());
    		model.addAttribute("username", auth.getName());

    		return"redirect:/trade/list";

    	} catch (Exception e) {

    		log.info("Error during saving the trade object : {} .", e);
    		return"trade/add";

    	}

    }

    /**
     * Get the trade entity to update the data, add it to the model of the update page 
     * and return the update page with the trade entity
     * 
     * @param id of the trade
     * @param model
     * @return trade's update page
     */
    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

    	log.info("Access to the update trade page");

    	Trade trade = tradeService.getTradeById(id);

    	model.addAttribute("trade", trade);

        return "trade/update";

    }

    /**
     * Check all data in the update entity, save it in the database
     * and redirect to the trade's list page
     * 
     * @param id of the trade
     * @param update trade
     * @param result
     * @param model
     * @param User auth
     * @return redirect to the trade's list page
     */
    @PostMapping("/trade/update/{tradeId}")
    public String updateTrade(@PathVariable("tradeId") Integer tradeId, @Valid Trade trade, BindingResult result, Model model, Authentication auth) {

    	if(result.hasErrors()) {

    		log.info("Error in the trade object : {}", result.getAllErrors());
    		return"trade/update";

    	}

    	try {

        	log.info("Trying to update the existing trade in the database with id : {} .", tradeId);

        	tradeService.updateTrade(trade);

        	model.addAttribute("trades", tradeService.getAllTrades());
        	model.addAttribute("username", auth.getName());

            return "redirect:/trade/list";

    	} catch(Exception e) {

    		log.info("Error during updating the trade object : {} .", e);
    		return"trade/update";

    	}

    }

    /**
     * Delete the selected trade entity and redirect to the trade's list page
     * 
     * @param id of the trade
     * @param model
     * @param User auth
     * @return redirect to the trade's list page
     */
    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model, Authentication auth) {

    	log.info("Trying to delete an existing trade in database with id : {} .", id);

    	boolean isDeleted = tradeService.deleteTrade(id);

    	if(isDeleted) {

        	model.addAttribute("trades", tradeService.getAllTrades());
        	model.addAttribute("username", auth.getName());

            return "redirect:/trade/list";

    	}

    	return "redirect:/trade/list";

    }
}
