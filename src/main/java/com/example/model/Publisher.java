package com.example.model;

public class Publisher {
    private int publisherID;
    private String publisherName;
    private String address;
    private String phoneNumber;
    private String email;
    private boolean isActive;
    
	
	
	public Publisher(int publisherID, String publisherName, String address, String phoneNumber, String email,
			boolean isActive) {
		super();
		this.publisherID = publisherID;
		this.publisherName = publisherName;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.isActive = isActive;
	}

	public Publisher() {
		super();
	}
	
	public int getPublisherID() {
		return publisherID;
	}
	public void setPublisherID(int publisherID) {
		this.publisherID = publisherID;
	}
	public String getPublisherName() {
		return publisherName;
	}
	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return this.publisherName;
	}

	

}
