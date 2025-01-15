package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.service.TradeService;

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
public class TradeController {

	private static final Logger log = LogManager.getLogger(TradeController.class);
	
	@Autowired
	private TradeService tradeService;

    /**
     * @param model
     * @param auth
     * @return
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
     * @param trade
     * @param model
     * @return
     */
    @GetMapping("/trade/add")
    public String addUser(Trade trade, Model model) {
    	
    	log.info("Access to the trade add page.");
    	
    	model.addAttribute("trade", new Trade());
    	
        return "trade/add";
        
    }

    /**
     * @param trade
     * @param result
     * @param model
     * @param auth
     * @return
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
    		
    		return"trade/list";
    		
    	} catch (Exception e) {
    		
    		log.info("Error during saving the trade object : {} .", e);
    		return"trade/add";
    		
    	}
        
    }

    /**
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

    	log.info("Access to the update trade page");
    	
    	Trade trade = tradeService.getTradeById(id);
    	
    	model.addAttribute("trade", trade);
    	
        return "trade/update";
        
    }

    /**
     * @param id
     * @param trade
     * @param result
     * @param model
     * @param auth
     * @return
     */
    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade, BindingResult result, Model model, Authentication auth) {

    	if(result.hasErrors()) {
    		
    		log.info("Error in the trade object : {}", result.getAllErrors());
    		return"trade/update";
    		
    	}
    	
    	try {
    		
        	log.info("Trying to update the existing trade in the database with id : {} .", id);
        	
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
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model, Authentication auth) {

    	log.info("Trying to delete an existing trade in database with id : {} .", id);
    	
    	boolean isDeleted = tradeService.deleteTrade(id);
    	
    	if(isDeleted == true) {
    		
        	model.addAttribute("trades", tradeService.getAllTrades());
        	model.addAttribute("username", auth.getName());
    		
            return "redirect:/trade/list";
    		
    	}
    	
    	return null;
    	
    }
}
