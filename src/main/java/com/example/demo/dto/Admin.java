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
@Table(name = "Admin_Table")
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({ "admin_id" })
public class Admin implements Serializable {
	@Id
	private int admin_id;
	private String adminname;
	private String admin_password;

	public Admin(String adminname, String admin_password) {
		this.adminname = adminname;
		this.admin_password = admin_password;
	}

}
