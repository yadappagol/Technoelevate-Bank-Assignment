package com.example.demo.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.demo.dao.AccountHolderDao;
import com.example.demo.dao.AdminDao;
import com.example.demo.dto.AccountHolder;
import com.example.demo.dto.Admin;
import com.example.demo.exception.AdminException;
import com.example.demo.message.MessageInfo;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AccountHolderDao accountHolderDao;

	private static final Logger LOGGER = LoggerFactory.getLogger(AdminServiceImpl.class);


	@Override
	public MessageInfo getAllAccountHolders() {
		LOGGER.info("DATA OF ALL ACCOUNT HOLDERS HAS BEEN FETCHED SUCCESSFULLY.THIS IS YOUR LIST : "
				+ accountHolderDao.findAll());
		return new MessageInfo(HttpStatus.OK.value(), new Date(), false, "AccountHolders Data :",
				accountHolderDao.findAll());
	}

}
