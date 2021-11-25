package com.te.assignment.testpkg.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.demo.dao.AccountHolderDao;
import com.example.demo.dao.AdminDao;
import com.example.demo.dto.AccountHolder;
import com.example.demo.message.ResponseMessageInfo;
import com.example.demo.service.AdminServiceImpl;

@SuppressWarnings("serial")
@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class AdminServieTest implements Serializable {

	@Mock
	private AdminDao adminDao;
	@Mock
	private AccountHolderDao accountHolderDao;
	@InjectMocks
	private AdminServiceImpl serviceImpl;

	@SuppressWarnings("unchecked")
	@Test
	public void serviceGetAllTest() {
		List<AccountHolder> list = new ArrayList<AccountHolder>();
		AccountHolder accountHolder = new AccountHolder(20, "Raju", 456789235, "yby123", 10000);
		list.add(accountHolder);
		Mockito.when(accountHolderDao.findAll()).thenReturn(list);
		ResponseMessageInfo msg = serviceImpl.getAllAccountHolders();
		ArrayList<AccountHolder> accountHolders1 = (ArrayList<AccountHolder>) msg.getDetails();
		assertEquals(accountHolder.getAccountholdername(), accountHolders1.get(0).getAccountholdername());

	}

}
