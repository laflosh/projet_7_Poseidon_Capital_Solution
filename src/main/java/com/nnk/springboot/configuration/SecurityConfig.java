package com.nnk.springboot.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.servlet.http.HttpServletResponse;

/**
 * Configuration class for Spring security that defines the application's security policies
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	CustomUserDetailsService customUserDetailsService;

	/**
	 * Configures the security filter chain for HTTP requests.
	 * 
	 * @param http
	 * @return a SecurityFilterChain defining the security configuration
	 * @throws Exception
	 */
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.authorizeHttpRequests(auth -> auth
				.requestMatchers("/login").permitAll()
				.requestMatchers("/app/login").permitAll()
				.requestMatchers("/").permitAll()
				.requestMatchers("/static/**").permitAll()
				.requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
				.requestMatchers("/favicon.ico").permitAll()
				.requestMatchers("/rating/**").authenticated()
				.requestMatchers("/ruleName/**").authenticated()
				.requestMatchers("/bidList/**").authenticated()
				.requestMatchers("/curvePoint/**").authenticated()
				.requestMatchers("/trade/**").authenticated()
				.requestMatchers(
						"/admin/home",
						"/app/secure/article-details",
						"/users/**"
						).hasRole("ADMIN")
				)
			.formLogin(formLogin -> formLogin
					.loginPage("/app/login")
					.loginProcessingUrl("/login")
					.defaultSuccessUrl("/bidList/list", true)
					.failureUrl("/app/login?error=true")
				)
			.logout(logout -> logout
					.logoutUrl("/logout")
        			.logoutSuccessHandler((request, response, authentication) -> {
        				response.setStatus(HttpServletResponse.SC_OK);
        				response.sendRedirect("/");
        			})
        			.invalidateHttpSession(true)
        			.deleteCookies("JSESSIONID")
				)
	        .exceptionHandling(exception -> exception
	                .accessDeniedPage("/app/error")
	            );

		return http.build();

	}

	/**
	 * Configures the password encoder used for securing the user's password
	 * 
	 * @return BCryptPasswordEncoder
	 */
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {

		return new BCryptPasswordEncoder();

	}

	/**
	 * Configures the authentication manager for handling user authentication
	 * 
	 * @param http
	 * @param bCryptPasswordEncoder
	 * @return AuthenticationManager used for authentication
	 * @throws Exception
	 */
	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder) throws Exception{

		AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
		authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(bCryptPasswordEncoder);

		return authenticationManagerBuilder.build();

	}

}
