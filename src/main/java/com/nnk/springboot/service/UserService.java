package com.nnk.springboot.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;

import jakarta.validation.Valid;

/**
 *
 */
@Service
public class UserService {

	private static final Logger log = LogManager.getLogger(UserService.class);

	@Autowired
	private UserRepository userRepository;

	/**
	 * @return
	 */
	public List<User> getAllUsers() {

		log.info("Get all users in the database.");
		
		List<User> users = userRepository.findAll();

		return users;

	}

	/**
	 * @param id
	 * @return
	 */
	public User getUserById(Integer id) {

		log.info("Get one user in the database with id : {} .", id);
		
        User user = userRepository.findById(id)
        		.orElseThrow(() -> new RuntimeException("Invalid user Id:" + id));

		return user;

	}

	/**
	 * @param user
	 */
	public void addNewUser(@Valid User user) {

		log.info("Add new user object in the database : {} .", user);
		
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));

        userRepository.save(user);

	}

	/**
	 * @param user
	 */
	public void updateUser(@Valid User user) {

		log.info("Update user object existing in the database with id : {} .", user.getId());
		
		User existingUser = getUserById(user.getId());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		if(user.getFullname() != existingUser.getFullname()) {
			existingUser.setFullname(user.getFullname());
		}
		
		if(user.getUsername() != existingUser.getUsername()) {
			existingUser.setUsername(user.getUsername());
		}
		
        if(user.getPassword() != existingUser.getPassword()) {
        	existingUser.setPassword(encoder.encode(user.getPassword()));
        }
        
        if(user.getRole() != existingUser.getRole()) {
        	existingUser.setRole(user.getRole());
        }

        userRepository.save(existingUser);

	}

	/**
	 * @param id
	 * @return
	 */
	public boolean deleteUser(Integer id) {

		log.info("Delete user object in the database with id : {} .", id);
		
		if(userRepository.existsById(id)) {

			userRepository.deleteById(id);

			return true;

		}

		return false;

	}

}
