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
		
		List<User> users = userRepository.findAll();
		
		return users;
		
	}
	
	/**
	 * @param id
	 * @return
	 */
	public User getUserById(Integer id) {
		
        User user = userRepository.findById(id)
        		.orElseThrow(() -> new RuntimeException("Invalid user Id:" + id));
        
		return user;
		
	}

	/**
	 * @param user
	 */
	public void addNewUser(@Valid User user) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        
        userRepository.save(user);
		
	}

	/**
	 * @param user
	 */
	public void updateUser(@Valid User user) {
		
		User existingUser = getUserById(user.getId());
		
		existingUser.setFullname(user.getFullname());
		existingUser.setUsername(user.getUsername());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        existingUser.setPassword(encoder.encode(user.getPassword()));
        existingUser.setRole(user.getRole());
        
        userRepository.save(existingUser);
		
	}

	/**
	 * @param id
	 * @return
	 */
	public boolean deleteUser(Integer id) {
		
		if(userRepository.existsById(id)) {
			
			userRepository.deleteById(id);
			
			return true;
			
		}
		
		return false;
		
	}
	
}
