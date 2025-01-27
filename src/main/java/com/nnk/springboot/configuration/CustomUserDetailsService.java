package com.nnk.springboot.configuration;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nnk.springboot.repositories.UserRepository;

/**
 * This service is used by Spring security to load user details
 * from the database based on the username of the user
 * during the authentication process
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

	private static final Logger log = LogManager.getLogger(CustomUserDetailsService.class);

	@Autowired
	UserRepository userRepository;

	/**
	 * Load the user details based oh the username of the user from the database
	 *
	 * @param username of the user
	 * @return user details of the user
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{

		com.nnk.springboot.domain.User user = userRepository.findByUsername(username);

		if(user == null) {

			log.info("User not found with username : {}", username);
			throw new UsernameNotFoundException("User not found");

		}

		log.info("User charged with username : {}", username);
		return new User(
				user.getUsername(),
				user.getPassword(),
				getGrantedAuthority(user.getRole())
				);

	}

	/**
	 * Converts the user's role properties into list of granted authorities
	 *
	 * @param role
	 * @return
	 */
	private List<GrantedAuthority> getGrantedAuthority(String role){

		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()));

		return authorities;

	}

}
