package com.te.assignment.testpkg.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.demo.dao.AccountHolderDao;
import com.example.demo.dto.AccountHolder;
import com.example.demo.service.AccountHolderServiceImpl;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class AccountHolderServiceTest {

	@Mock
	private AccountHolderDao accountHolderDao;

	@InjectMocks
	private AccountHolderServiceImpl accountHolderServiceImpl;

	@BeforeEach
	public void setup() {
		accountHolderServiceImpl.findByUserName(Mockito.any());
	}

	@Test
	public void withdrawServiceTest() {
		AccountHolder accountHolder = new AccountHolder(20, "Raju", 456789235, "yby123", 10000);
		System.out.println(accountHolder);
		Mockito.when(accountHolderDao.findByAccountholdername(accountHolder.getAccountholdername()))
				.thenReturn(accountHolder);
		accountHolderServiceImpl.findByUserName(accountHolder.getAccountholdername());
		AccountHolder accountHolder2 = (AccountHolder) accountHolderServiceImpl.withdraw(1500).getDetails();
		assertEquals(accountHolder.getAccountholdername(), accountHolder2.getAccountholdername());
	}

	@Test
	public void depositServiceTest() {
		AccountHolder accountHolder = new AccountHolder(20, "Raju", 456789235, "yby123", 10000);
		System.out.println(accountHolder);
		Mockito.when(accountHolderDao.findByAccountholdername(accountHolder.getAccountholdername()))
				.thenReturn(accountHolder);
		accountHolderServiceImpl.findByUserName(accountHolder.getAccountholdername());
		AccountHolder accountHolder2 = (AccountHolder) accountHolderServiceImpl.deposit(1500).getDetails();
		assertEquals(accountHolder.getAccountholdername(), accountHolder2.getAccountholdername());
	}

	@Test
	public void checkbalanceServiceTest() {
		AccountHolder accountHolder = new AccountHolder(20, "Raju", 456789235, "yby123", 10000);
		System.out.println(accountHolder);
		Mockito.when(accountHolderDao.findByAccountholdername(accountHolder.getAccountholdername()))
				.thenReturn(accountHolder);
		AccountHolder accountHolder2=accountHolderServiceImpl.findByUserName(accountHolder.getAccountholdername());
		assertEquals(accountHolder.getAccountholdername(), accountHolder2.getAccountholdername());
	}
}
