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
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

import jakarta.servlet.http.HttpServletResponse;

/**
 *
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	CustomUserDetailsService customUserDetailsService;

	/**
	 * @param http
	 * @return
	 * @throws Exception
	 */
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.authorizeHttpRequests(auth -> auth
				.requestMatchers("/login").permitAll()
				.requestMatchers("/app/login").permitAll()
				.requestMatchers("/").permitAll()
				.requestMatchers(
						"/admin/home",
						"/app/secure/article-details",
						"users/delete"
						).hasRole("ADMIN")
				.requestMatchers("/static/**").permitAll()
				.requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
				.requestMatchers("/favicon.ico").permitAll()
				.requestMatchers("/user/**").permitAll()
				.requestMatchers("/rating/**").authenticated()
				.requestMatchers("/ruleName/**").authenticated()
				.requestMatchers("/bidList/**").authenticated()
				.requestMatchers("/curvePoint/**").authenticated()
				.requestMatchers("/trade/**").authenticated()
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
     * @return
     */
    @Bean
    public HttpFirewall allowSemiColonFirewall() {
    	StrictHttpFirewall firewall = new StrictHttpFirewall();

    	firewall.setAllowSemicolon(true);

    	return firewall;
    }

	/**
	 * @return
	 */
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {

		return new BCryptPasswordEncoder();

	}

	/**
	 * @param http
	 * @param bCryptPasswordEncoder
	 * @return
	 * @throws Exception
	 */
	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder) throws Exception{

		AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
		authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(bCryptPasswordEncoder);

		return authenticationManagerBuilder.build();

	}

}
