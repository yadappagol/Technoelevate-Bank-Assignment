package com.example.demo.securityconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.demo.exception.CustomAccessDeniedException;
import com.example.demo.filter.AccountHolderAuthorizationFilter;
import com.example.demo.filter.CustomAuthenticationFilter;
import com.example.demo.service.AccountHolderServiceImpl;

@Configuration
@EnableWebSecurity
public class ConfigSecurity extends WebSecurityConfigurerAdapter{
	
	
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private AccountHolderServiceImpl accountHolderServiceImpl;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		System.out.println("configure auth");
		
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		System.out.println("configure http");
		CustomAuthenticationFilter authenticattionFilter = new CustomAuthenticationFilter(authenticationManagerBean(),new CustomAccessDeniedException());
		AccountHolderAuthorizationFilter authorizationFilter = new AccountHolderAuthorizationFilter(accountHolderServiceImpl,new CustomAccessDeniedException());
		authenticattionFilter.setFilterProcessesUrl("/api/v1/login");
		http.csrf().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.authorizeRequests().antMatchers("/api/v1/login/**","/api/v1/accountholder/token/refresh/**",
				"/swagger-resources/configuration/ui","/swagger-resources",
				"/swagger-resources/configuration/security","/swagger-ui.html","/webjars/**").permitAll();
		http.authorizeRequests().antMatchers("/api/v1/accountholder/**").hasAnyAuthority("USER");
		http.authorizeRequests().antMatchers("/api/v1/admin/**").hasAnyAuthority("ADMIN");
		http.authorizeRequests().anyRequest().authenticated();
		http.addFilter(authenticattionFilter);
		http.addFilterBefore(authorizationFilter, CustomAuthenticationFilter.class);
	}
	
	@Override
	@Bean
	public AuthenticationManager  authenticationManagerBean() throws Exception{
		return super.authenticationManagerBean();
	}
	
	
	@Bean
	public JWTConfig config() {
		return new JWTConfig();
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/v2/api-docs", "/swagger-resources/configuration/ui", "/swagger-resources",
				"/swagger-resources/configuration/security", "/swagger-ui.html", "/webjars/**");
	}
	

}
