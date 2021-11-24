package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.dto.Admin;

@Repository
public interface AdminDao extends JpaRepository<Admin, Integer> {

	 Admin findByAdminname(String username);

}
