package com.example.demo;

import java.io.Serializable;

public class User implements Serializable {
	
	private static final long serialVersionUID = 5465L;
	
	private String username;
	private String password;
	private String email;
	private String phone;
	private String name;
	private String address;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	@Override
	public String toString() {
		return "User [username=" + username + ", email=" + email + ", phone=" + phone + ", name=" + name + ", address="
				+ address + "]";
	}
	
}
