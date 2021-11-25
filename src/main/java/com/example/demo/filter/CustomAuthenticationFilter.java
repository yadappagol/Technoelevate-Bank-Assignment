package com.example.demo.filter;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.demo.exception.CustomAccessDeniedException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	
	

	@SuppressWarnings("unused")
	private CustomAccessDeniedException customAccessDeniedException;
	
	private final AuthenticationManager authenticationManager;

	public CustomAuthenticationFilter(AuthenticationManager authenticationManager,CustomAccessDeniedException customAccessDeniedException) {
		this.authenticationManager = authenticationManager;
		this.customAccessDeniedException = customAccessDeniedException;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws org.springframework.security.core.AuthenticationException {
		log.info(
				"--------------------------:Controll Inside attemptAuthentication, inside the  CustomAuthenticationFilter class:---------------------");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		log.info("Username is " + username + " And Password Is " + password);
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
				password);
		return authenticationManager.authenticate(authenticationToken);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authentication) throws IOException, ServletException {
		log.info(
				"--------------------------:Controll Inside successfulAuthentication, inside the  CustomAuthenticationFilter class:---------------------");
		User user = (User) authentication.getPrincipal();
		Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
		log.info(
				"--------------------------:Controll Inside successfulAuthentication, inside the  CustomAuthenticationFilter class and generating the access token:---------------------");
		String access_token = JWT.create().withSubject(user.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
				.withIssuer(request.getRequestURI().toString())
				.withClaim("roles",
						user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.sign(algorithm);
		log.info(
				"--------------------------:Controll Inside successfulAuthentication, inside the  CustomAuthenticationFilter class and generating the refresh token:---------------------");
		
		
		String refresh_token = JWT.create().withSubject(user.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
				.withIssuer(request.getRequestURI().toString()).sign(algorithm);
		LinkedHashMap<String, Object> message= new LinkedHashMap<>();
		LinkedHashMap<String, Object> tokens = new LinkedHashMap<>();
		message.put("error", false);
		log.info(
				"--------------------------:Controll Inside successfulAuthentication, inside the  CustomAuthenticationFilter class and setting the tokens:---------------------");
		message.put("message", user.getUsername() +" You Have Logged In Successfully....");
		tokens.put("access_token", access_token);
		tokens.put("refresh_token", refresh_token);
		message.put("data", tokens);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		new ObjectMapper().writeValue(response.getOutputStream(), message);
	}

}
