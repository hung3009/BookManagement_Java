package com.example.model;

import java.util.Date;

public class Author {
    private int authorID;
    private String authorName;
    private String email;
    private String phoneNumber;
    private Date birthDate;
    private String gender;
    private String hometown;
    private boolean isActive;
    
    public Author() {
		super();
	}

	public Author(int authorID, String authorName, String email, String phoneNumber, Date birthDate, String gender,
			String hometown, boolean isActive) {
		super();
		this.authorID = authorID;
		this.authorName = authorName;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.birthDate = birthDate;
		this.gender = gender;
		this.hometown = hometown;
		this.isActive = isActive;
	}

	// Getter và Setter
    public int getAuthorID() {
        return authorID;
    }

    public void setAuthorID(int authorID) {
        this.authorID = authorID;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
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

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

	@Override
	public String toString() {
		return this.authorName;
	}
    
    
}
