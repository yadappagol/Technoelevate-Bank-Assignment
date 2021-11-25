package com.example.demo.service;

import com.example.demo.dto.AccountHolder;
import com.example.demo.message.ResponseMessageInfo;

public interface AccountHolderService {

	AccountHolder findByUserName(String username);

	ResponseMessageInfo withdraw(double amount);

	ResponseMessageInfo deposit(double amount);

	ResponseMessageInfo checkBalance();

}
