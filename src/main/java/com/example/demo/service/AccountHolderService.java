package com.example.demo.service;

import com.example.demo.dto.AccountHolder;
import com.example.demo.message.MessageInfo;

public interface AccountHolderService {

	// MessageInfo verification(int id, String password);

	AccountHolder findByUserName(String username);

	MessageInfo withdraw(double amount);

	MessageInfo deposit(double amount);

	MessageInfo checkBalance();

}
