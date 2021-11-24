package com.example.demo.controller;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.message.MessageInfo;
import com.example.demo.service.AdminService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@RestController
@RequestMapping(path="/api/v1/admin")
@Api(value = "/api/v1/admin", tags = "Bank Assignment")
public class AdminController {

	
	private static final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);
	
	@Autowired
	private AdminService adminservice;

	@GetMapping(path = "/accountholders")
	@ApiOperation(value="All Accountholders", notes="All Accountholders",tags="Bank Assignment")
	@ApiResponses(value= { @ApiResponse(code=200,message="Fetched Successfully!!"),
	@ApiResponse(code=404,message="No AccoountHolder Found"),@ApiResponse(code=403,message="Access Denied!!")})
	public ResponseEntity<MessageInfo> getAllCustomer() {
		LOGGER.info("Fetched Succesfully..");
		return new ResponseEntity<MessageInfo>(adminservice.getAllAccountHolders(), HttpStatus.OK);
	}
	
}
