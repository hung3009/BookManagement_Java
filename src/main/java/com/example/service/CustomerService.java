package com.example.service;

import com.example.dao.CustomerDao;
import com.example.model.Customer;

import java.util.List;

public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService() {
        this.customerDao = new CustomerDao();
    }

    // Get all customers
    public List<Customer> getAllCustomers() {
        return customerDao.getAllCustomers();
    }

    // Search customers by name, address, or contact number
    public List<Customer> searchCustomers(String keyword) {
        return customerDao.searchCustomers(keyword);
    }

    // Add a new customer
    public boolean addCustomer(Customer customer) {
        return customerDao.addCustomer(customer);
    }

    // Update customer information
    public boolean updateCustomer(Customer customer) {
        return customerDao.updateCustomer(customer);
    }

    // Deactivate customer
    public boolean deactivateCustomer(int customerId) {
        return customerDao.deactivateCustomer(customerId);
    }

    // Activate customer
    public boolean activateCustomer(int customerId) {
        return customerDao.activateCustomer(customerId);
    }

    // Get customer by ID
    public Customer getCustomerById(int customerId) {
        return customerDao.getCustomerById(customerId);
    }

    // Get all active customers
//    public List<Customer> getAllActiveCustomers() {
//        return customerDao.getAllActiveCustomers();
//    }
}
