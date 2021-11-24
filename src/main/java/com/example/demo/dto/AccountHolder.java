package com.example.demo.dto;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "AccountHolder_Table")
@JsonIgnoreProperties({ "password" ,"balance"})
public class AccountHolder implements Serializable {
	@Id
	private int accountholder_id;
	private String accountholdername;	
	private long account_no;
	private String password;
	private double balance;
	
	
	public AccountHolder(int accountholder_id, String password) {
		this.accountholder_id = accountholder_id;
		this.password = password;
	}

	
	
	
	
}
