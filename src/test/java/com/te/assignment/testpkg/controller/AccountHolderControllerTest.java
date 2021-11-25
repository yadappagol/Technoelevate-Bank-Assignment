package com.te.assignment.testpkg.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

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

import com.example.demo.controller.AccountHolderController;
import com.example.demo.dto.AccountHolder;
import com.example.demo.message.ResponseMessageInfo;
import com.example.demo.service.AccountHolderService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class AccountHolderControllerTest {

	@InjectMocks
	private AccountHolderController accountholdercontroller;

	@Mock
	private AccountHolderService accountHolderService;

	private MockMvc mockMvc;
	private ObjectMapper mapper;

	@BeforeEach
	public void setup() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(accountholdercontroller).build();
		this.mapper = new ObjectMapper();
	}

	@SuppressWarnings("unchecked")
	@Test
	public void withdrawTest() throws Exception {
		AccountHolder accountHolder = new AccountHolder(20, "Raju", 456789235, "yby123", 10000);
		ResponseMessageInfo msg = new ResponseMessageInfo();
		msg.setDetails(accountHolder);
		Mockito.when(accountHolderService.withdraw(2000)).thenReturn(msg);
		String jsonObject = mapper.writeValueAsString(accountHolder);
		String result = mockMvc
				.perform(put("/api/v1/accountholder/withdraw/" + 2000).contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(jsonObject))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();
		ResponseMessageInfo msg2 = mapper.readValue(result, ResponseMessageInfo.class);
		Map<Integer, String> map = (Map<Integer, String>) msg2.getDetails();
		for (Entry<Integer, String> withdraw : map.entrySet()) {
			assertEquals(accountHolder.getAccountholder_id(), withdraw.getValue());
			break;
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void depositTest() throws Exception {
		AccountHolder accountHolder = new AccountHolder(20, "Raju", 456789235, "yby123", 10000);
		ResponseMessageInfo msg = new ResponseMessageInfo();
		msg.setDetails(accountHolder);
		Mockito.when(accountHolderService.deposit(2000)).thenReturn(msg);
		String jsonObject = mapper.writeValueAsString(accountHolder);
		String result = mockMvc
				.perform(put("/api/v1/accountholder/deposit/" + 2000).contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(jsonObject))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();
		ResponseMessageInfo msg2 = mapper.readValue(result, ResponseMessageInfo.class);
		Map<String, String> map = (Map<String, String>) msg2.getDetails();
		for (Entry<String, String> deposit : map.entrySet()) {
			assertEquals(accountHolder.getAccountholder_id(), deposit.getValue());
			break;
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void getBalanceTest() throws Exception {
		AccountHolder accountHolder = new AccountHolder(20, "Raju", 456789235, "yby123", 10000);
		ResponseMessageInfo msg = new ResponseMessageInfo();
		msg.setDetails(accountHolder);
		Mockito.when(accountHolderService.checkBalance()).thenReturn(msg);
		String jsonObject = mapper.writeValueAsString(accountHolder);
		String result = mockMvc
				.perform(get("/api/v1/accountholder/checkbalance").contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(jsonObject))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();
		ResponseMessageInfo msg2 = mapper.readValue(result, ResponseMessageInfo.class);
		Map<String, String> map = (Map<String, String>) msg2.getDetails();
		for (Entry<String, String> check : map.entrySet()) {
			assertEquals(accountHolder.getAccountholder_id(), check.getValue());
			break;
		}
	}

}
