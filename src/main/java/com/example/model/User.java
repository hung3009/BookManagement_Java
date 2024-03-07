package com.example.model;

public class User {
    private int userID;
    private String username;
    private String password;
    private int roleID;
    private String fullname;
    private String email;
    private String phoneNumber;
    private String address;
    private String gender;
    private boolean isActive;
	public User(int userID, String username, String password, int roleID, String fullname, String email,
			String phoneNumber, String address, String gender, boolean isActive) {
		super();
		this.userID = userID;
		this.username = username;
		this.password = password;
		this.roleID = roleID;
		this.fullname = fullname;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.gender = gender;
		this.isActive = isActive;
	}
	public User() {
		super();
	}
	
	
	
	public User(String username, String password, int roleID, String fullname, String email, String phoneNumber,
			String address, String gender, boolean isActive) {
		super();
		this.username = username;
		this.password = password;
		this.roleID = roleID;
		this.fullname = fullname;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.gender = gender;
		this.isActive = isActive;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
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
	public int getRoleID() {
		return roleID;
	}
	public void setRoleID(int roleID) {
		this.roleID = roleID;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
    
	
    
}
