package com.te.assignment.testpkg.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.demo.controller.AdminController;
import com.example.demo.dto.AccountHolder;
import com.example.demo.dto.Admin;
import com.example.demo.message.ResponseMessageInfo;
import com.example.demo.service.AdminService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class AdminControllerTest {

	@InjectMocks
	private AdminController admincontroller;

	@Mock
	private AdminService adminService;
	private MockMvc mockMvc;
	private ObjectMapper mapper;

	@BeforeEach
	public void setup() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(admincontroller).build();
		this.mapper = new ObjectMapper();
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testControllerGetAll() throws Exception {
		AccountHolder accountHolder = new AccountHolder(20, "dby123");
		ResponseMessageInfo msg = new ResponseMessageInfo();
		msg.setDetails(accountHolder);
		Mockito.when(adminService.getAllAccountHolders()).thenReturn(msg);
		String jsonObject = mapper.writeValueAsString(accountHolder);
		String result = mockMvc
				.perform(get("/api/v1/admin/accountholders").contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(jsonObject))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();
		ResponseMessageInfo msg2 = mapper.readValue(result, ResponseMessageInfo.class);
		Map<String, String> map = (Map<String, String>) msg2.getDetails();
		for (Map.Entry<String, String> getall : map.entrySet()) {
			assertEquals(accountHolder.getAccountholder_id(), getall.getValue());
			break;
		}
	}

}