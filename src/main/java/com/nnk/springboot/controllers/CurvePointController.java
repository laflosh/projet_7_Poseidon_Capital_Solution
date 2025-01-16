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

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.service.CurvePointService;

import jakarta.validation.Valid;

/**
 * This controller manage all the front end page and CRUD operations for the curvepoint domain
 */
@Controller
public class CurvePointController {

	private static  final Logger log = LogManager.getLogger(CurvePointController.class);

	@Autowired
	private CurvePointService curvePointService;

    /**
     * Fetching all the curvepoints and return the page list to see all curvepoints
     * 
     * @param model
     * @param User auth
     * @return curvepoint's list page
     */
    @RequestMapping("/curvePoint/list")
    public String home(Model model, Authentication auth)
    {

    	log.info("Trying to get all curvepoints in the database.");

    	List<CurvePoint> curvePoints = curvePointService.getAllCurvePoints();

    	model.addAttribute("curvePoints", curvePoints);
    	model.addAttribute("username", auth.getName());

        return "curvePoint/list";

    }

    /**
     * Return the add page with an empty curvepoint entity
     * 
     * @param New empty curvePoint
     * @param model
     * @return curvepoint's add page
     */
    @GetMapping("/curvePoint/add")
    public String addCurvePointForm(CurvePoint curvePoint, Model model) {

    	log.info("Access to the adding curvepoint page");

    	model.addAttribute("curvePoint", new CurvePoint());

        return "curvePoint/add";

    }

    /**
     * Check all the data in the new entity, save it in the database 
     * and redirect to the curvepoint's list page
     * 
     * @param new curvePoint
     * @param result
     * @param model
     * @param User auth
     * @return redirect to the curvepoint's list page
     */
    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model, Authentication auth) {

    	if(result.hasErrors()) {

    		log.info("Error in the curvepoint object : {}", result.getAllErrors());
            return "curvePoint/add";

    	}

    	try {

    		log.info("Trying to save a new curvepoint in the database : {}", curvePoint);

    		curvePointService.addNewCurvePoint(curvePoint);

    		model.addAttribute("curvePoints", curvePointService.getAllCurvePoints());
    		model.addAttribute("username", auth.getName());

            return "curvePoint/list";

    	} catch(Exception e) {

    		log.info("Error during saving the curvepoint object : {}", e);
            return "curvePoint/add";

    	}


    }

    /**
     * Get the curvepoint entity to update the data, add it to the model of the update page 
     * and return the update page with the curvepoint entity
     * 
     * @param id of the curvepoint
     * @param model
     * @return curvepoint's update page
     */
    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

    	log.info("Access to the update curvepoint page");

    	CurvePoint curvePoint = curvePointService.getCurvePointById(id);

    	model.addAttribute("curvePoint", curvePoint);

        return "curvePoint/update";

    }

    /**
     * Check all data in the update entity, save it in the database
     * and redirect to the curvepoint's list page
     * 
     * @param id of the curvepoint
     * @param update curvePoint
     * @param result
     * @param model
     * @param User auth
     * @return redirect to the curvepoint's list page
     */
    @PostMapping("/curvePoint/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint, BindingResult result, Model model, Authentication auth) {

    	if(result.hasErrors()) {

    		log.info("Error in the curvepoint object : {}", result.getAllErrors());
    		return"curvePoint/update";

    	}

    	try {

    		log.info("Trying to update an existing curvepoint in the database with id : {}", id);

    		curvePointService.updateCurvePoint(curvePoint);

    		model.addAttribute("curvePoints", curvePointService.getAllCurvePoints());
    		model.addAttribute("username", auth.getName());

            return "redirect:/curvePoint/list";

    	} catch(Exception e) {

    		log.info("Error during updating the curvepoint object : {}", e);
    		return"curvePoint/update";

    	}

    }

    /**
     * Delete the selected curvepoint entity and redirect to the curvepoint's list page
     * 
     * @param id
     * @param model
     * @param User auth
     * @return redirect to the curvepoint's list page
     */
    @GetMapping("/curvePoint/delete/{id}")
    public String deleteCurvePoint(@PathVariable("id") Integer id, Model model, Authentication auth) {

       	log.info("Trying to delete a existing curvepoint in the database with id : {}",id);

       	boolean isDelete = curvePointService.deleteCurvePoint(id);

       	if(isDelete) {

    		model.addAttribute("curvePoints", curvePointService.getAllCurvePoints());
    		model.addAttribute("username", auth.getName());

       		return "redirect:/curvePoint/list";

       	}

        return null;

    }
}
