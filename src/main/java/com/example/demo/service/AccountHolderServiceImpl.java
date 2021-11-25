
package com.example.demo.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.dao.AccountHolderDao;
import com.example.demo.dao.AdminDao;
import com.example.demo.dto.AccountHolder;
import com.example.demo.dto.Admin;
import com.example.demo.exception.AccountHolderException;
import com.example.demo.exception.AdminException;
import com.example.demo.message.ResponseMessageInfo;

@Service
public class AccountHolderServiceImpl implements AccountHolderService, UserDetailsService {

	@Autowired
	private AccountHolderDao accountholderdao;

	private AccountHolder accountHolder;

	@Autowired
	private AdminDao adminDao;

	private static final Logger LOGGER = LoggerFactory.getLogger(AccountHolderServiceImpl.class);

	private int count = 0;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
		LOGGER.info("Loading UserName And Password inside AccountholderServiceImpl Class!!");
		if (!username.equals("Admin")) {
			
			accountHolder = accountholderdao.findByAccountholdername(username);
			if (accountHolder == null) {
				LOGGER.error("User Not in Data Base");
				throw new AccountHolderException("Please Enter your Correct User Name");
			}
			authorities.add(new SimpleGrantedAuthority("USER"));
			return new User(accountHolder.getAccountholdername(), accountHolder.getPassword(), authorities);
		} else {
			Admin admin = adminDao.findByAdminname(username);
			if (admin == null) {
				LOGGER.error("User Not in Data Base");
				throw new AdminException("Please Enter your Correct User Name");
			}
			authorities.add(new SimpleGrantedAuthority("ADMIN"));
			return new User(admin.getAdminname(), admin.getAdmin_password(), authorities);
		}
	}

	@Override
	public AccountHolder findByUserName(String username) {
		accountHolder = (AccountHolder) accountholderdao.findByAccountholdername(username);
		LOGGER.info("Successfully Logged in " + username);
		return accountHolder;
	}


	@Override
	public ResponseMessageInfo withdraw(double withdrawamount) {
		if (withdrawamount <= 25000) {
			if (withdrawamount % 100 == 0) {
				if (accountHolder.getBalance() > 1000) {
					if (count < 3) {
						accountHolder.setBalance(accountHolder.getBalance() - Math.round((withdrawamount * 1.0043)));
						accountholderdao.save(accountHolder);
						count++;
						LOGGER.info("WITHDRAWAL IS SUCCESSFUL.THE WITHDRAW AMOUNT IS : Rs." + withdrawamount);
						return new ResponseMessageInfo(HttpStatus.OK.value(), new Date(), false,
								"Rs. "+withdrawamount + " AMOUNT SUCCESSFULLY WITHDRAWN ", accountHolder);
					} else {
						LOGGER.error("YOU EXHAUSTED YOUR MONTHLY WITHDRAW LIMIT!!MONTHLY WITHDRAW LIMIT IS 3!!!");
						throw new AccountHolderException(
								"YOU EXHAUSTED YOUR MONTHLY WITHDRAW LIMIT!!MONTHLY WITHDRAW LIMIT IS 3!!");
					}

				} else {
					LOGGER.error("INSUFFICIENT BALANCE!!");
					throw new AccountHolderException(
							"INSUFFICIENT BALANCE!! AMOUNT IS GRETER THAN MINIMUM BALANCE i.e 1000!!");
				}

			} else {
				LOGGER.error("THE AMOUNT SHOULD BE MULTIPLE OF 100!!");
				throw new AccountHolderException("THE AMOUNT SHOULD BE MULTIPLE OF 100");
			}

		}
		LOGGER.error("YOUR AMOUNT  IS GREATER THAN DAILY WITHDRAW LIMIT!!.THE DAILY WITHDRAW LIMIT IS 25000!!");
		throw new AccountHolderException(
				"YOUR AMOUNT  IS GREATER THAN DAILY WITHDRAW LIMIT!!.THE DAILY WITHDRAW LIMIT IS 25000!! ");
	}

	@Override
	public ResponseMessageInfo deposit(double depositamount) {
		if (depositamount <= 50000) {
			if (depositamount % 100 == 0) {
				if (depositamount >= 500) {
					accountHolder.setBalance(accountHolder.getBalance() + Math.round((depositamount * (1 - 0.0026))));
					accountholderdao.save(accountHolder);
					LOGGER.info("SUCCESSFULLY DEPOSITED AND DEPOSITED AMOUNT IS : " + depositamount);
					return new ResponseMessageInfo(HttpStatus.OK.value(), new Date(), false,
							"Rs. "+depositamount + "  AMOUNT SUCCESSFULLY DEPOSITED  ", accountHolder);
				} else {
					LOGGER.error("YOUR AMOUNT SHOULD BE GREATER THAN  RS.500 !!");
					throw new AccountHolderException("YOUR AMOUNT SHOULD BE GREATER THAN RS.500!!");
				}

			} else {
				LOGGER.error("INVALID AMOUNT ENTRY!!AMOUNT SHOULD BE MULTIPLE OF 100!!");
				throw new AccountHolderException("INVALID AMOUNT ENTRY!!AMOUNT SHOULD BE MULTIPLE OF 100!!");
			}

		}
		LOGGER.error("YOUR AMOUNT IS GREATER THAN MINIMUM DEPOSIT LIMIT!!.MINIMUM DEPOSIT AMOUNT LIMIT IS 50000!!");
		throw new AccountHolderException(
				"YOUR AMOUNT IS GREATER THAN MINIMUM DEPOSIT LIMIT!!.MINIMUM DEPOSIT AMOUNT LIMIT IS 50000!!");
	}

	@Override
	public ResponseMessageInfo checkBalance() {

		AccountHolder accountHolder2 = accountholderdao.findByAccountholdername(accountHolder.getAccountholdername());
		return new ResponseMessageInfo(HttpStatus.OK.value(), new Date(), false,
				"Hi "+accountHolder2.getAccountholdername()+" Your Balance is : Rs." + Math.round(accountHolder2.getBalance()), accountHolder2);

	}
	
	public AccountHolder getAccountHolder() {
		return accountHolder; 
	}

}
