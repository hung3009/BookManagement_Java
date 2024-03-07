package com.example.model;

public class Customer {
    private int customerID;
    private String customerName;
    private String address;
    private String contactNumber;
    private boolean isMember;
    
	public Customer(int customerID, String customerName, String address, String contactNumber, boolean isMember) {
		super();
		this.customerID = customerID;
		this.customerName = customerName;
		this.address = address;
		this.contactNumber = contactNumber;
		this.isMember = isMember;
	}
	
	public Customer() {
		super();
	}
	
	public int getCustomerID() {
		return customerID;
	}
	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	public boolean isMember() {
		return isMember;
	}
	public void setMember(boolean isMember) {
		this.isMember = isMember;
	}

    
}
