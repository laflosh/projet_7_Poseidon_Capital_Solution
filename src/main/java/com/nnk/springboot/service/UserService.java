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
 * This service contain all the methods to execute CRUD operations for the user domain
 */
@Service
public class UserService {

	private static final Logger log = LogManager.getLogger(UserService.class);

	@Autowired
	private UserRepository userRepository;

	/**
	 * Fetching all users entity in the database
	 *
	 * @return A list of users
	 */
	public List<User> getAllUsers() {

		log.info("Get all users in the database.");

		List<User> users = userRepository.findAll();

		return users;

	}

	/**
	 * Fetching one user entity in the database depending of the id
	 *
	 * @param id of the user
	 * @return User
	 */
	public User getUserById(Integer id) {

		log.info("Get one user in the database with id : {} .", id);

        User user = userRepository.findById(id)
        		.orElseThrow(() -> new RuntimeException("Invalid user Id:" + id));

		return user;

	}

	/**
	 * Add a new user entity in the database
	 *
	 * @param new user
	 */
	public void addNewUser(@Valid User user) {

		log.info("Add new user object in the database : {} .", user);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));

        userRepository.save(user);

	}

	/**
	 * Update the existing user entity in the database
	 *
	 * @param update user
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
	 * Delete an existing user entity in the database depending of the id
	 *
	 * @param id of the user
	 * @return true if delete
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
