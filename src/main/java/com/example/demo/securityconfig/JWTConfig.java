package com.example.demo.securityconfig;

public class JWTConfig{

	private org.springframework.core.env.Environment environment;
	
	@SuppressWarnings("unused")
	private long access_token;
	@SuppressWarnings("unused")
	private long refresh_token;
	
	public long getAccess_token() {
		return Long.parseLong(environment.getProperty("access.token"));
	}
	public long getRefresh_token() {
		return Long.parseLong(environment.getProperty("refresh.token"));
	}
	
	
	
	
}
