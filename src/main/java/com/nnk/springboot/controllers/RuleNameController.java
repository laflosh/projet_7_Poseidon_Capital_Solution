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

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.service.RuleNameService;

import jakarta.validation.Valid;

/**
 * This controller manage all the front end page and CRUD operations for the rulename domain
 */
@Controller
public class RuleNameController {

	private static  final Logger log = LogManager.getLogger(RuleNameController.class);

	@Autowired
	private RuleNameService ruleNameService;

    /**
     * Fetching all the rulenames and return the page list to see all rulenames
     * 
     * @param model
     * @param User auth
     * @return rulename's page list
     */
    @RequestMapping("/ruleName/list")
    public String home(Model model, Authentication auth)
    {
    	log.info("Trying to get all rulenames in the database.");

    	List<RuleName> ruleNames = ruleNameService.getAllRuleNames();

        model.addAttribute("ruleNames", ruleNames);
    	model.addAttribute("user", auth.getName());

        return "ruleName/list";
    }

    /**
     * Return the add page with an empty rulename entity
     * 
     * @param New empty ruleName
     * @param model
     * @return rulename's add page
     */
    @GetMapping("/ruleName/add")
    public String addRuleForm(RuleName ruleName, Model model) {

    	log.info("Access to the adding rulename page");

    	model.addAttribute("rulename", new RuleName());

        return "ruleName/add";
    }

    /**
     * Check all the data in the new entity, save it in the database 
     * and redirect to the rulename's list page
     * 
     * @param New ruleName
     * @param result
     * @param model
     * @param User auth
     * @return redirect to rulename's list page
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

        	return "redirect:/ruleName/list";

        } catch(Exception e) {

        	log.info("Error during saving the rulename object : {}", e);
        	return"ruleName/add";

        }

    }

    /**
     * Get the rulename entity to update the data, add it to the model of the update page 
     * and return the update page with the rulename entity
     * 
     * @param id of the rulename
     * @param model
     * @return rulename's update page
     */
    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

    	log.info("Access to the update rulename page");

    	RuleName rulename = ruleNameService.getRuleNameById(id);

    	model.addAttribute("ruleName", rulename);

        return "ruleName/update";
    }

    /**
     * Check all data in the update entity, save it in the database
     * and redirect to the rulename's list page
     * 
     * @param id of the rulename
     * @param update ruleName
     * @param result
     * @param model
     * @param User auth
     * @return redirect to the rulename's list page
     */
    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName, BindingResult result, Model model, Authentication auth) {

    	if(result.hasErrors()) {

    		log.info("Error in the rulename object : {}", result.getAllErrors());
    		return "ruleName/update";

    	}

    	try {

    		log.info("Trying to update a existing rulename in the database with id : {}", id);

    		ruleNameService.updateRuleName(ruleName);

            model.addAttribute("ruleNames", ruleNameService.getAllRuleNames());
            model.addAttribute("username", auth.getName());

            return "redirect:/ruleName/list";

    	} catch(Exception e) {

    		log.info("Error during updating the rulename object : {}", e);
    		return "ruleName/update";

    	}

    }

    /**
     * Delete the selected rulename entity and redirect to the rulename's list page
     * 
     * @param id of the rulename
     * @param model
     * @param User auth
     * @return redirect to the rulename' list page
     */
    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model, Authentication auth) {

    	log.info("Trying to delete a existing rating in the database with id : {}",id);

    	boolean isDelete = ruleNameService.deleteRuleName(id);

    	if(isDelete) {

            model.addAttribute("ruleNames", ruleNameService.getAllRuleNames());
            model.addAttribute("username", auth.getName());

            return "redirect:/ruleName/list";

    	}

		return null;

    }

}
