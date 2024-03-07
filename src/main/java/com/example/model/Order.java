package com.example.model;

import java.util.Date;

public class Order {
    private String orderID;
    private Date orderDate;
    private int employeeID;
    private int customerID;
    private double discount;
    private int totalPrice;
    
	public Order(String orderID, Date orderDate, int employeeID, int customerID, double discount, int totalPrice) {
		super();
		this.orderID = orderID;
		this.orderDate = orderDate;
		this.employeeID = employeeID;
		this.customerID = customerID;
		this.discount = discount;
		this.totalPrice = totalPrice;
	}
	public Order() {
		super();
	}
	public String getOrderID() {
		return orderID;
	}
	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public int getEmployeeID() {
		return employeeID;
	}
	public void setEmployeeID(int employeeID) {
		this.employeeID = employeeID;
	}
	public int getCustomerID() {
		return customerID;
	}
	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public int getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}
	
    
    
}
