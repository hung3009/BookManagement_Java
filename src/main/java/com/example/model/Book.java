package com.example.model;

import java.util.Date;

public class Book {
    private int bookID;
    private String title;
    private String isbn;
    private int categoryID;
    private int publisherID;
    private int authorID;
    private int numberOfPages;
    private String size;
    private Date publicationDate;
    private boolean isActive;
	
	public Book(int bookID, String title, String isbn, int categoryID, int publisherID, int authorID,
			int numberOfPages, String size, Date publicationDate, boolean isActive) {
		super();
		this.bookID = bookID;
		this.title = title;
		this.isbn = isbn;
		this.categoryID = categoryID;
		this.publisherID = publisherID;
		this.authorID = authorID;
		this.numberOfPages = numberOfPages;
		this.size = size;
		this.publicationDate = publicationDate;
		this.isActive = isActive;
	}



	public int getBookID() {
		return bookID;
	}

	public void setBookID(int bookID) {
		this.bookID = bookID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public int getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(int categoryID) {
		this.categoryID = categoryID;
	}

	public int getPublisherID() {
		return publisherID;
	}

	public void setPublisherID(int publisherID) {
		this.publisherID = publisherID;
	}

	public int getAuthorID() {
		return authorID;
	}

	public void setAuthorID(int authorID) {
		this.authorID = authorID;
	}

	public int getNumberOfPages() {
		return numberOfPages;
	}

	public void setNumberOfPages(int numberOfPages) {
		this.numberOfPages = numberOfPages;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public Date getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public Book() {
		super();
	}
    
	
    
    
}