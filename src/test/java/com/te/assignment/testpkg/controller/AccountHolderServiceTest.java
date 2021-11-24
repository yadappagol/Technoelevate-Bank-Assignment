//package com.te.assignment.testpkg.controller;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import com.example.demo.dao.AccountHolderDao;
//import com.example.demo.dto.AccountHolder;
//import com.example.demo.service.AccountHolderServiceImpl;
//
//@ExtendWith(MockitoExtension.class)
//@ExtendWith(SpringExtension.class)
//public class AccountHolderServiceTest {
//
//	@Mock
//	private AccountHolderDao accountHolderDao;
//
//	@InjectMocks
//	private AccountHolderServiceImpl accountHolderServiceImpl;
//
//	@Test
//	public void loginServiceTest() {
//
//		AccountHolder accountHolder = new AccountHolder(20, "Raju", 456789235, "yby123", 10000);
//		System.out.println(accountHolder);
//		Mockito.when(accountHolderDao.findByAccountholderId(accountHolder.getAccountholder_id()))
//				.thenReturn(accountHolder);
//		AccountHolder accountHolder2 = (AccountHolder) accountHolderServiceImpl
//				.verification(accountHolder.getAccountholder_id(), accountHolder.getPassword()).getDetails();
//		System.out.println(accountHolder2);
//		assertEquals(accountHolder.getAccountholdername(), accountHolder2.getAccountholdername());
//	}
//
//	@Test
//	public void withdrawServiceTest() {
//		AccountHolder accountHolder = new AccountHolder(20, "Raju", 456789235, "yby123", 10000);
//		System.out.println(accountHolder);
//		Mockito.when(accountHolderDao.findByAccountholderId(accountHolder.getAccountholder_id()))
//				.thenReturn(accountHolder);
//		AccountHolder accountHolder2 = (AccountHolder) accountHolderServiceImpl.withdraw(2000, accountHolder)
//				.getDetails();
//		System.out.println(accountHolder2);
//		assertEquals(accountHolder.getAccountholdername(), accountHolder2.getAccountholdername());
//	}
//
//	@Test
//	public void depositServiceTest() {
//		AccountHolder accountHolder = new AccountHolder(20, "Raju", 456789235, "yby123", 10000);
//		System.out.println(accountHolder);
//		Mockito.when(accountHolderDao.findByAccountholderId(accountHolder.getAccountholder_id()))
//				.thenReturn(accountHolder);
//		AccountHolder accountHolder2 = (AccountHolder) accountHolderServiceImpl.deposit(1000, accountHolder)
//				.getDetails();
//		System.out.println(accountHolder2);
//		assertEquals(accountHolder.getAccountholdername(), accountHolder2.getAccountholdername());
//	}
//
//	@Test
//	public void checkbalanceServiceTest() {
//		AccountHolder accountHolder = new AccountHolder(20, "Raju", 456789235, "yby123", 10000);
//		Mockito.when(accountHolderDao.findByAccountholderId(accountHolder.getAccountholder_id()))
//				.thenReturn(accountHolder);
//		AccountHolder accountHolder2 = (AccountHolder) accountHolderServiceImpl.checkBalance(accountHolder).getDetails();
//		assertEquals(accountHolder.getBalance(), accountHolder2.getBalance());
//	}
//
//}
