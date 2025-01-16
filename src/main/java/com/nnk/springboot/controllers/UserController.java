package com.nnk.springboot.controllers;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.service.UserService;

import jakarta.validation.Valid;

/**
 * This controller manage all the front end page and CRUD operations for the user domain
 */
@Controller
public class UserController {

	private static final Logger log = LogManager.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    /**
     * Fetching all the users and return the page list to see all users
     * 
     * @param model
     * @return user's list page
     */
    @RequestMapping("/user/list")
    public String home(Model model)
    {

    	log.info("Trying to get all users in the database.");

    	List<User> users = userService.getAllUsers();

        model.addAttribute("users", users);

        return "user/list";

    }

    /**
     * Return the add page with an empty user entity
     * 
     * @param New empty user
     * @param model
     * @return user's add page
     */
    @GetMapping("/user/add")
    public String addUser(User user, Model model) {

    	log.info("Access to the add user page");

    	model.addAttribute("user", new User());

        return "user/add";

    }

    /**
     * Check all the data in the new entity, save it in the database 
     * and redirect to the user's list page
     * 
     * @param new user
     * @param result
     * @param model
     * @return redirect to the user's list page
     */
    @PostMapping("/user/validate")
    public String validate(@Valid User user, BindingResult result, Model model) {

        if (result.hasErrors()) {

        	log.info("Error in the user object : {} .", result.getAllErrors());
            return "user/add";

        }

        try {

        	log.info("Trying to save a new user in the database : {} .", user);

        	userService.addNewUser(user);

        	model.addAttribute("users", userService.getAllUsers());

            return "redirect:/user/list";

        } catch (Exception e) {

        	log.info("Error during saving user object : {} .", e);
        	return "user/add";

        }

    }

    /**
     * Get the user entity to update the data, add it to the model of the update page 
     * and return the update page with the user entity
     * 
     * @param id of the user
     * @param model
     * @return user's update page
     */
    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

    	log.info("Access to the update user page");

    	User user = userService.getUserById(id);
        user.setPassword("");

        model.addAttribute("user", user);

        return "user/update";

    }

    /**
     * Check all data in the update entity, save it in the database
     * and redirect to the user's list page
     * 
     * @param id of the user
     * @param update user
     * @param result
     * @param model
     * @return redirect to the user's list page
     */
    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid User user, BindingResult result, Model model) {

        if (result.hasErrors()) {

        	log.info("Error in the user object : {} .", result.getAllErrors());
            return "user/update";

        }

        try {

        	log.info("Trying to update an existing user in the database : {} .", user);

        	userService.updateUser(user);

            model.addAttribute("users", userService.getAllUsers());

            return "redirect:/user/list";

        } catch(Exception e) {

        	log.info("Error during updating the user object : {} .", e);
        	return "user/update";

        }

    }

    /**
     * Delete the selected user entity and redirect to the user's list page
     * 
     * @param id of the user
     * @param model
     * @return redirect to the user's list page
     */
    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {

    	log.info("Trying to delete an existing user in the database with id : {} .", id);

    	boolean isDeleted = userService.deleteUser(id);

    	if(isDeleted) {

            model.addAttribute("users", userService.getAllUsers());

            return "redirect:/user/list";

    	}

        return null;

    }

}
