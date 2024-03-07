package com.example.model;

public class OrderDetail {
    private int orderDetailID;
    private String orderID;
    private int bookID;
    private int lotID;
    private int quantity;
    private double unitPrice;
	public OrderDetail(int orderDetailID, String orderID, int bookID, int lotID, int quantity, double unitPrice) {
		super();
		this.orderDetailID = orderDetailID;
		this.orderID = orderID;
		this.bookID = bookID;
		this.lotID = lotID;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
	}
	public OrderDetail() {
		super();
	}
	public int getOrderDetailID() {
		return orderDetailID;
	}
	public void setOrderDetailID(int orderDetailID) {
		this.orderDetailID = orderDetailID;
	}
	public String getOrderID() {
		return orderID;
	}
	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}
	public int getBookID() {
		return bookID;
	}
	public void setBookID(int bookID) {
		this.bookID = bookID;
	}
	public int getLotID() {
		return lotID;
	}
	public void setLotID(int lotID) {
		this.lotID = lotID;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}

    
}
