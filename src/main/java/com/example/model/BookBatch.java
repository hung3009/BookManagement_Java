package com.example.model;

import java.util.Date;

public class BookBatch {
	private int LotID;
    private int bookID;
    private int quantityOriginal;
    private int quantityCurrent;
    private Date purchaseDate;
    private int unitPrice;
    private int receiptDetailID;
    private String storageLocation;
    
    

    public BookBatch(int lotID, int bookID, int quantityOriginal, int quantityCurrent, Date purchaseDate,
			int unitPrice, int receiptDetailID, String storageLocation) {
		super();
		LotID = lotID;
		this.bookID = bookID;
		this.quantityOriginal = quantityOriginal;
		this.quantityCurrent = quantityCurrent;
		this.purchaseDate = purchaseDate;
		this.unitPrice = unitPrice;
		this.receiptDetailID = receiptDetailID;
		this.storageLocation = storageLocation;
	}

	public BookBatch() {
		super();
	}

	public BookBatch(int bookID, int quantityOriginal, int quantityCurrent, Date purchaseDate, int unitPrice, int receiptDetailID, String storageLocation) {
        this.bookID = bookID;
        this.quantityOriginal = quantityOriginal;
        this.quantityCurrent = quantityCurrent;
        this.purchaseDate = purchaseDate;
        this.unitPrice = unitPrice;
        this.receiptDetailID = receiptDetailID;
        this.storageLocation = storageLocation;
    }
	
	

	public int getLotID() {
		return LotID;
	}

	public void setLotID(int lotID) {
		LotID = lotID;
	}

	public int getBookID() {
		return bookID;
	}

	public void setBookID(int bookID) {
		this.bookID = bookID;
	}

	public int getQuantityOriginal() {
		return quantityOriginal;
	}

	public void setQuantityOriginal(int quantityOriginal) {
		this.quantityOriginal = quantityOriginal;
	}

	public int getQuantityCurrent() {
		return quantityCurrent;
	}

	public void setQuantityCurrent(int quantityCurrent) {
		this.quantityCurrent = quantityCurrent;
	}

	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public int getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(int unitPrice) {
		this.unitPrice = unitPrice;
	}

	public int getReceiptDetailID() {
		return receiptDetailID;
	}

	public void setReceiptDetailID(int receiptDetailID) {
		this.receiptDetailID = receiptDetailID;
	}

	public String getStorageLocation() {
		return storageLocation;
	}

	public void setStorageLocation(String storageLocation) {
		this.storageLocation = storageLocation;
	}

	public BookBatch(int bookID, int quantityOriginal, int quantityCurrent, Date purchaseDate, int unitPrice,
			String storageLocation) {
		super();
		this.bookID = bookID;
		this.quantityOriginal = quantityOriginal;
		this.quantityCurrent = quantityCurrent;
		this.purchaseDate = purchaseDate;
		this.unitPrice = unitPrice;
		this.storageLocation = storageLocation;
	}

 
}