//package com.te.assignment.testpkg.controller;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
//
//import java.util.Map;
//import java.util.Map.Entry;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import com.example.demo.controller.AccountHolderController;
//import com.example.demo.dto.AccountHolder;
//import com.example.demo.message.MessageInfo;
//import com.example.demo.service.AccountHolderService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//@ExtendWith(MockitoExtension.class)
//@ExtendWith(SpringExtension.class)
////@SpringBootTest
//public class AccountHolderControllerTest {
//
//	@InjectMocks
//	private AccountHolderController accountholdercontroller;
//
//	@Mock
//	private AccountHolderService accountHolderService;
//
//	private MockMvc mockMvc;
//	private ObjectMapper mapper;
//
//	@BeforeEach
//	public void setup() {
//		this.mockMvc = MockMvcBuilders.standaloneSetup(accountholdercontroller).build();
//		this.mapper = new ObjectMapper();
//	}
//
//	@SuppressWarnings("unchecked")
//	@Test
//	public void accountHolderLogin() throws Exception {
//		AccountHolder accountHolder = new AccountHolder(20, "Raju", 456789235, "yby123", 10000);
//		MessageInfo msg = new MessageInfo();
//		msg.setDetails(accountHolder);
//
//		Mockito.when(
//				accountHolderService.verification(accountHolder.getAccountholder_id(), accountHolder.getPassword()))
//				.thenReturn(msg);
//		String jsonObject = mapper.writeValueAsString(accountHolder);
//		String result = mockMvc
//				.perform(get("/accountholderlogin/" + 20 + "/yby123").contentType(MediaType.APPLICATION_JSON_VALUE)
//						.content(jsonObject).accept(MediaType.APPLICATION_JSON_VALUE))
//				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();
//		MessageInfo msg2 = mapper.readValue(result, MessageInfo.class);
//		Map<Integer, String> map = (Map<Integer, String>) msg2.getDetails();
//		for (Entry<Integer, String> accoountholderdata : map.entrySet()) {
//			assertEquals(accountHolder.getAccountholder_id(), accoountholderdata.getValue());
//			break;
//		}
//
//	}
//
//	@SuppressWarnings("unchecked")
//	@Test
//	public void withdrawTest() throws Exception {
//		AccountHolder accountHolder = new AccountHolder(20, "Raju", 456789235, "yby123", 10000);
//		MessageInfo msg = new MessageInfo();
//		msg.setDetails(accountHolder);
//		Mockito.when(accountHolderService.withdraw(2000, accountHolder)).thenReturn(msg);
//		String jsonObject = mapper.writeValueAsString(accountHolder);
//		String result = mockMvc
//				.perform(put("/withdraw/" + 2000).sessionAttr("AccountHolderLogin", accountHolder)
//						.contentType(MediaType.APPLICATION_JSON_VALUE).content(jsonObject))
//				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();
//		MessageInfo msg2 = mapper.readValue(result, MessageInfo.class);
//		Map<Integer, String> map = (Map<Integer, String>) msg2.getDetails();
//		for (Entry<Integer, String> withdraw : map.entrySet()) {
//			assertEquals(accountHolder.getAccountholdername(), withdraw.getValue());
//			break;
//		}
//	}
//
//	@SuppressWarnings("unchecked")
//	@Test
//	public void depositTest() throws Exception {
//		AccountHolder accountHolder = new AccountHolder(20, "Raju", 456789235, "yby123", 10000);
//		MessageInfo msg = new MessageInfo();
//		msg.setDetails(accountHolder);
//		Mockito.when(accountHolderService.deposit(2000, accountHolder)).thenReturn(msg);
//		String jsonObject = mapper.writeValueAsString(accountHolder);
//		String result = mockMvc
//				.perform(put("/deposit/" + 2000).sessionAttr("AccountHolderLogin", accountHolder)
//						.contentType(MediaType.APPLICATION_JSON_VALUE).content(jsonObject))
//				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();
//		MessageInfo msg2 = mapper.readValue(result, MessageInfo.class);
//		Map<Integer, String> map = (Map<Integer, String>) msg2.getDetails();
//		for (Entry<Integer, String> deposit : map.entrySet()) {
//			assertEquals(accountHolder.getAccountholdername(), deposit.getValue());
//			break;
//		}
//	}
//
//	@SuppressWarnings("unchecked")
//	@Test
//	public void getBalanceTest() throws Exception {
//		AccountHolder accountHolder = new AccountHolder(20, "Raju", 456789235, "yby123", 10000);
//		MessageInfo msg = new MessageInfo();
//		msg.setDetails(accountHolder);
//		Mockito.when(accountHolderService.checkBalance(accountHolder)).thenReturn(msg);
//		String jsonObject = mapper.writeValueAsString(accountHolder);
//		String result = mockMvc
//				.perform(get("/checkbalance").sessionAttr("AccountHolderLogin", accountHolder)
//						.contentType(MediaType.APPLICATION_JSON_VALUE).content(jsonObject))
//				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();
//		MessageInfo msg2 = mapper.readValue(result, MessageInfo.class);
//		Map<Integer, String> map = (Map<Integer, String>) msg2.getDetails();
//		for (Entry<Integer, String> check : map.entrySet()) {
//			assertEquals(accountHolder.getAccountholdername(), check.getValue());
//			break;
//		}
//	}
//
//	@Test
//	public void accountHolderLogoutTest() throws Exception {
//		AccountHolder accountHolder = new AccountHolder(20, "Raju", 456789235, "yby123", 10000);
//		MessageInfo msg = new MessageInfo();
//		msg.setDetails(accountHolder);
//		Mockito.when(accountHolderService.checkBalance(accountHolder)).thenReturn(msg);
//		String jsonObject = mapper.writeValueAsString(accountHolder);
//		String result = mockMvc
//				.perform(get("/accountholderlogout").sessionAttr("AccountHolderLogin", accountHolder)
//						.contentType(MediaType.APPLICATION_JSON_VALUE).content(jsonObject))
//				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();
//		MessageInfo msg2 = mapper.readValue(result, MessageInfo.class);
//		String details = (String) msg2.getDetails();
//		assertEquals(accountHolder.getAccountholdername(), details);
//	}
//
//}
