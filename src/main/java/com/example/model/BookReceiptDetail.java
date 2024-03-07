package com.example.model;

public class BookReceiptDetail {
    private int receiptDetailID;
    private String receiptID;
    private int bookID;
    private int quantity;
    private int unitPrice;
    
	public BookReceiptDetail(int receiptDetailID, String receiptID, int bookID, int quantity, int unitPrice) {
		super();
		this.receiptDetailID = receiptDetailID;
		this.receiptID = receiptID;
		this.bookID = bookID;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
	}
	
	public BookReceiptDetail() {
		super();
	}
	
	public BookReceiptDetail(int bookID, int quantity, int unitPrice) {
		super();
		this.bookID = bookID;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
	}

	public int getReceiptDetailID() {
		return receiptDetailID;
	}
	public void setReceiptDetailID(int receiptDetailID) {
		this.receiptDetailID = receiptDetailID;
	}
	public String getReceiptID() {
		return receiptID;
	}
	public void setReceiptID(String receiptID) {
		this.receiptID = receiptID;
	}
	public int getBookID() {
		return bookID;
	}
	public void setBookID(int bookID) {
		this.bookID = bookID;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(int unitPrice) {
		this.unitPrice = unitPrice;
	}
	
    
    
}
