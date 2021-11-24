package com.example.demo.securityconfig;

public class JWTConfig{

	private org.springframework.core.env.Environment environment;
	
	private long access_token;
	private long refresh_token;
	
	public long getAccess_token() {
		return Long.parseLong(environment.getProperty("access.token"));
	}
	public long getRefresh_token() {
		return Long.parseLong(environment.getProperty("refresh.token"));
	}
	
	
	
	
}
