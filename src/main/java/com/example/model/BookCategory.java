package com.example.model;

public class BookCategory {
    private int categoryID;
    private String categoryName;
    private boolean isActive;
    
	public BookCategory(int categoryID, String categoryName, boolean isActive) {
		super();
		this.categoryID = categoryID;
		this.categoryName = categoryName;
		this.isActive = isActive;
	}

	public BookCategory() {
		super();
	}

	public int getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(int categoryID) {
		this.categoryID = categoryID;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	@Override
	public String toString() {
		return this.categoryName;
	}
	
	

    
}
