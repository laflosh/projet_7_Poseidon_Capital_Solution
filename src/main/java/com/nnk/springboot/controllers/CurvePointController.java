package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.service.CurvePointService;

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
public class CurvePointController {

	private static  final Logger log = LogManager.getLogger(CurvePointController.class);
	
	@Autowired
	CurvePointService curvePointService;

    /**
     * @param model
     * @param auth
     * @return
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
     * @param curvePoint
     * @param model
     * @return
     */
    @GetMapping("/curvePoint/add")
    public String addCurvePointForm(CurvePoint curvePoint, Model model) {
    	
    	log.info("Acces to the adding curvepoint page");
    	
    	model.addAttribute("curvePoint", new CurvePoint());
    	
        return "curvePoint/add";
        
    }

    /**
     * @param curvePoint
     * @param result
     * @param model
     * @param auth
     * @return
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
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
       
    	log.info("Acces to the update curvepoint page");
    	
    	CurvePoint curvePoint = curvePointService.getCurvePointById(id);
    	
    	model.addAttribute("curvePoint", curvePoint);
    	
        return "curvePoint/update";
        
    }

    /**
     * @param id
     * @param curvePoint
     * @param result
     * @param model
     * @param auth
     * @return
     */
    @PostMapping("/curvePoint/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint, BindingResult result, Model model, Authentication auth) {
    	
    	if(result.hasErrors()) {
    		
    		log.info("Error in the curvepoint object : {}", result.getAllErrors());
    		return"curvePoint/update";
    		
    	}
    	
    	try {
    		
    		log.info("Trying to update a existing curvepoint in the database : {}", curvePoint);
    		
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
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/curvePoint/delete/{id}")
    public String deleteCurvePoint(@PathVariable("id") Integer id, Model model) {
    	
       	log.info("Trying to delete a existing curvepoint in the database with id : {}",id);
       	
       	boolean idDelete = curvePointService.deleteCurvePoint(id);
    	
       	if(idDelete == true) {
       		
       		return "redirect:/curvePoint/list";
       		
       	}
       	
        return null;
        
    }
}
