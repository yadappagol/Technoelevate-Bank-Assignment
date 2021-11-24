//package com.te.assignment.testpkg.controller;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.List;
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
//import com.example.demo.dao.AdminDao;
//import com.example.demo.dto.AccountHolder;
//import com.example.demo.dto.Admin;
//import com.example.demo.message.MessageInfo;
//import com.example.demo.service.AdminServiceImpl;
//
//@SuppressWarnings("serial")
//@ExtendWith(MockitoExtension.class)
//@ExtendWith(SpringExtension.class)
////@SpringBootTest
//public class AdminServieTest implements Serializable {
//
//	@Mock
//	private AdminDao adminDao;
//	@Mock
//	private AccountHolderDao accountHolderDao;
//	@InjectMocks
//	private AdminServiceImpl serviceImpl;
//
//	@Test
//	public void loginServiceTest() {
//		Admin admin=new Admin(10,"admin", "admin");
//		Mockito.when(adminDao.findByAdminId(admin.getAdmin_id())).thenReturn(admin);
//		Admin admin2 = (Admin) serviceImpl.authentication(admin.getAdmin_id(), admin.getAdmin_name()).getDetails();
//		assertEquals(admin.getAdmin_name(), admin2.getAdmin_name());
//	}
//	
//	@SuppressWarnings("unchecked")
//	@Test
//	public void serviceGetAllTest() {
//		List<AccountHolder> list = new ArrayList<AccountHolder>();
//		AccountHolder accountHolder=new AccountHolder(20, "Raju",456789235 ,"yby123", 10000);
//		list.add(accountHolder);
//		Mockito.when(accountHolderDao.findAll()).thenReturn(list);
//		MessageInfo msg=serviceImpl.getAllAccountHolders();
//		ArrayList<AccountHolder> accountHolders1=(ArrayList<AccountHolder>)msg.getDetails();
//		assertEquals(accountHolder.getAccountholdername(), accountHolders1.get(0).getAccountholdername());
//		
//	}
//	
//
//}
