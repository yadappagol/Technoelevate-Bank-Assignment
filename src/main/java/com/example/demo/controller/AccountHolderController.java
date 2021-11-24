package com.example.demo.controller;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.dto.AccountHolder;
import com.example.demo.message.MessageInfo;
import com.example.demo.securityconfig.JWTConfig;
import com.example.demo.service.AccountHolderService;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(path = "/api/v1/accountholder")
@Api(value = "/api/v1/accountholder", tags = "Bank Assignment")
public class AccountHolderController {

	@Autowired
	private AccountHolderService accountHolderService;

	@Autowired
	private JWTConfig config;

	private static final Logger LOGGER = LoggerFactory.getLogger(AccountHolderController.class);


	@PutMapping(path = "/withdraw/{amount}")
	@ApiOperation(value="withdraw Money", notes="withdraw Money",tags="Bank Assignment")
	@ApiResponses(value= { @ApiResponse(code=200,message="Withdraw Successfully!!"),
	@ApiResponse(code=404,message="Invalid AccoountHolder Id"),@ApiResponse(code=403,message="Access Denied!!")})
	public ResponseEntity<MessageInfo> withdraw(@PathVariable double amount) {
			LOGGER.info("WITHDRAWN SUCCESSFULLY.THE WITHDRAWN AMOUNT IS : " + amount);
			return new ResponseEntity<MessageInfo>(accountHolderService.withdraw(amount), HttpStatus.OK);
	}

	@PutMapping(path = "/deposit/{amount}")
	@ApiOperation(value="Deposite Money", notes="deposite Money",tags="Bank Assignment")
	@ApiResponses(value= { @ApiResponse(code=200,message="Depositted Successfully!!"),
	@ApiResponse(code=404,message="Invalid AccoountHolder Id"),@ApiResponse(code=403,message="Access Denied!!")})
	public ResponseEntity<MessageInfo> deposite(@PathVariable("amount") double amount) {
		LOGGER.info("DEPOSITTED SUCCESSFULLY.THE DEPOSITTED AMOUNT IS : " + amount);
		return new ResponseEntity<MessageInfo>(accountHolderService.deposit(amount), HttpStatus.OK);
	}

	@GetMapping(path = "/checkbalance")
	@ApiOperation(value="Available Money", notes="Available Money",tags="Bank Assignment")
	@ApiResponses(value= { @ApiResponse(code=200,message="Balance Fetched SuccessFully.."),
	@ApiResponse(code=404,message="Invalid AccoountHolder Id"),@ApiResponse(code=403,message="Access Denied!!")})
	public ResponseEntity<MessageInfo> checkBalance() {
		LOGGER.info("YOUR BALANCE IS : " + accountHolderService.checkBalance());
		return new ResponseEntity<MessageInfo>(accountHolderService.checkBalance(), HttpStatus.OK);
	}


	@GetMapping("/token/refresh")
	@ApiOperation(value="Generate new Access Token", notes="Generate new Access Token",tags="Bank Assignment")
	@ApiResponses(value= { @ApiResponse(code=200,message="new Access Token generated SuccessFully.."),
	@ApiResponse(code=404,message="Refresh Token is Missing"),@ApiResponse(code=403,message="Access Denied!!")})
	public void refreashToken(HttpServletRequest request, HttpServletResponse response)
			throws JsonGenerationException, JsonMappingException, IOException {
		String header = request.getHeader(AUTHORIZATION);
		if (header != null && header.startsWith("Bearer ")) {
			try {
				String refresh_token = header.substring("Bearer ".length());
				Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
				JWTVerifier verifier = JWT.require(algorithm).build();
				DecodedJWT decodedJWT = verifier.verify(refresh_token);
				String username = decodedJWT.getSubject();
				AccountHolder accountHolder = accountHolderService.findByUserName(username);
				String access_token = JWT.create().withSubject(accountHolder.getAccountholdername())
						.withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
						.withIssuer(request.getRequestURI().toString())
						.withClaim("roles", new ArrayList<>().add(new SimpleGrantedAuthority("USER"))).sign(algorithm);
				HashMap<String, String> tokens = new HashMap<>();
				tokens.put("access_token", access_token);
				tokens.put("refresh_token", refresh_token);
				response.setContentType(MediaType.APPLICATION_JSON_VALUE);
				new ObjectMapper().writeValue(response.getOutputStream(), tokens);
			} catch (Exception exception) {
				response.setHeader("error", exception.getMessage());
				response.setStatus(FORBIDDEN.value());
				HashMap<String, String> error = new HashMap<>();
				error.put("error", exception.getMessage());
				response.setContentType(MediaType.APPLICATION_JSON_VALUE);
				new ObjectMapper().writeValue(response.getOutputStream(), error);
			}
		} else
			throw new RuntimeException("Refresh token is missing");

	}

}
