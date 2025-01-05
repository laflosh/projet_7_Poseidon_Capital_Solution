package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.service.RuleNameService;

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
public class RuleNameController {
	
	private static  final Logger log = LogManager.getLogger(RuleNameController.class);

	@Autowired
	RuleNameService ruleNameService;

    /**
     * @param model
     * @param auth
     * @return
     */
    @RequestMapping("/ruleName/list")
    public String home(Model model, Authentication auth)
    {
    	
    	List<RuleName> ruleNames = ruleNameService.getAllRuleNames();
    	
        model.addAttribute("ruleNames", ruleNames);
    	model.addAttribute("user", auth.getName());
    	
        return "ruleName/list";
    }

    /**
     * @param ruleName
     * @param model
     * @return
     */
    @GetMapping("/ruleName/add")
    public String addRuleForm(RuleName ruleName, Model model) {
    	
    	log.info("Acces to the adding rulename page");
    	
    	model.addAttribute("rulename", new RuleName());
    	
        return "ruleName/add";
    }

    /**
     * @param ruleName
     * @param result
     * @param model
     * @param auth
     * @return
     */
    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model, Authentication auth) {
    	
    	if(result.hasErrors()) {
    		
    		log.info("Error in the rulename object : {}", result.getAllErrors());
    		return"ruleName/add";
    		
    	}
    	
        try {
        	
        	log.info("Trying to save a new rulename in the database : {}", ruleName);
        	
        	ruleNameService.addNewRuleName(ruleName);
        	
        	model.addAttribute("ruleNames", ruleNameService.getAllRuleNames());
        	model.addAttribute("user", auth.getName());
        	
        	return "ruleName/list";
        	
        } catch(Exception e) {
        	
        	log.info("Error during saving the rulename object : {}", e);
        	return"ruleName/add";
        	
        }
    	
    }

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get RuleName by Id and to model then show to the form
        return "ruleName/update";
    }

    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName, BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update RuleName and return RuleName list
        return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        // TODO: Find RuleName by Id and delete the RuleName, return to Rule list
        return "redirect:/ruleName/list";
    }
}
