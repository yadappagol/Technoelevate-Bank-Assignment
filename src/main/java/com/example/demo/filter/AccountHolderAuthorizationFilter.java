package com.example.demo.filter;

import static java.util.Arrays.stream;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.exception.CustomAccessDeniedException;
import com.example.demo.service.AccountHolderServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AccountHolderAuthorizationFilter extends OncePerRequestFilter{

	
	private AccountHolderServiceImpl serviceImpl;
	private CustomAccessDeniedException exception;

	public AccountHolderAuthorizationFilter(AccountHolderServiceImpl serviceImpl,
			CustomAccessDeniedException exception) {
		this.serviceImpl = serviceImpl;
		this.exception = exception;
	}


	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		if (request.getServletPath().equals("/api/v1/login")
				|| request.getServletPath().equals("/api/accountholder/token/refresh")) {
			filterChain.doFilter(request, response);
		} else {
			String header = request.getHeader("Authorization");
			if (header != null && header.startsWith("Bearer ")) {
				try {
					String token = header.substring(7);
					Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
					JWTVerifier verifier = JWT.require(algorithm).build();
					DecodedJWT decodedJWT = verifier.verify(token);
					String username = decodedJWT.getSubject();
					String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
					System.out.println(roles[0]);
					if(!roles[0].equals("ADMIN")) {
						if(!serviceImpl.getAccountHolder().getAccountholdername().equals(username)) {
							try {
								exception.handle(request, response, new AccessDeniedException("UNAUTHORIZED ACCESSS TOKEN"));
							} catch (Exception exception) {
						      System.out.println(exception.getMessage());
							}
						}
					}
					Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
				    stream(roles).forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
					UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
							username, null, authorities);
					SecurityContextHolder.getContext().setAuthentication(authenticationToken);
					filterChain.doFilter(request, response);
				} catch (JWTDecodeException|AccessDeniedException  exception) {
					response.setHeader("error", exception.getMessage());
					response.setStatus(403);
					HashMap<String, String> error = new HashMap<>();
					error.put("error", exception.getMessage());
					response.setContentType(MediaType.APPLICATION_JSON_VALUE);
					new ObjectMapper().writeValue(response.getOutputStream(), error);
				}
			} else
				filterChain.doFilter(request, response);
		}
	}

	}


