package com.usermanagement.requestDto;

public class LoginRequestDto {
	
	private String name;
	private String password;
	public LoginRequestDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public LoginRequestDto(String name, String password) {
		super();
		this.name = name;
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
