package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.dto.AccountHolder;


@Repository
public interface AccountHolderDao extends JpaRepository<AccountHolder, Integer> {
	
	AccountHolder findByAccountholdername(String username);

}
