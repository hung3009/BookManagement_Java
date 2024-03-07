package com.example.model;

import java.util.Date;

public class BookReceipt {
    private String receiptID;
    private Date receiptDate;
    private int employeeID;
    private double totalCost;
	public BookReceipt(String receiptID, Date receiptDate, int employeeID, double totalCost) {
		super();
		this.receiptID = receiptID;
		this.receiptDate = receiptDate;
		this.employeeID = employeeID;
		this.totalCost = totalCost;
	}
	public BookReceipt() {
		super();
	}
	public String getReceiptID() {
		return receiptID;
	}
	public void setReceiptID(String receiptID) {
		this.receiptID = receiptID;
	}
	public Date getReceiptDate() {
		return receiptDate;
	}
	public void setReceiptDate(Date receiptDate) {
		this.receiptDate = receiptDate;
	}
	public int getEmployeeID() {
		return employeeID;
	}
	public void setEmployeeID(int employeeID) {
		this.employeeID = employeeID;
	}
	public double getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}
	
    
    
}
