package com.example.dao;

import com.example.model.Customer;
import com.example.util.MySQLConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDao {

    // Get all customers
    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        String query = "SELECT * FROM Customers";

        try (Connection connection = MySQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Customer customer = extractCustomerFromResultSet(resultSet);
                customers.add(customer);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customers;
    }

    // Search customers by name, address, or contact number
    public List<Customer> searchCustomers(String keyword) {
        List<Customer> customers = new ArrayList<>();
        String query = "SELECT * FROM Customers WHERE CustomerName LIKE ? OR Address LIKE ? OR ContactNumber LIKE ?";

        try (Connection connection = MySQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, "%" + keyword + "%");
            preparedStatement.setString(2, "%" + keyword + "%");
            preparedStatement.setString(3, "%" + keyword + "%");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Customer customer = extractCustomerFromResultSet(resultSet);
                customers.add(customer);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customers;
    }

    // Add a new customer
    public boolean addCustomer(Customer customer) {
        String query = "INSERT INTO Customers (CustomerName, Address, ContactNumber, IsMember) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection connection = MySQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, customer.getCustomerName());
            preparedStatement.setString(2, customer.getAddress());
            preparedStatement.setString(3, customer.getContactNumber());
            preparedStatement.setBoolean(4, customer.isMember());

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Update customer information
    public boolean updateCustomer(Customer customer) {
        String query = "UPDATE Customers SET CustomerName = ?, Address = ?, ContactNumber = ?, IsMember = ? " +
                "WHERE CustomerID = ?";

        try (Connection connection = MySQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, customer.getCustomerName());
            preparedStatement.setString(2, customer.getAddress());
            preparedStatement.setString(3, customer.getContactNumber());
            preparedStatement.setBoolean(4, customer.isMember());
            preparedStatement.setInt(5, customer.getCustomerID());

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Deactivate customer
    public boolean deactivateCustomer(int customerId) {
        return updateCustomerStatus(customerId, false);
    }

    // Activate customer
    public boolean activateCustomer(int customerId) {
        return updateCustomerStatus(customerId, true);
    }

    private boolean updateCustomerStatus(int customerId, boolean isMember) {
        String query = "UPDATE Customers SET IsMember = ? WHERE CustomerID = ?";

        try (Connection connection = MySQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setBoolean(1, isMember);
            preparedStatement.setInt(2, customerId);

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Get customer by ID
    public Customer getCustomerById(int customerId) {
        String query = "SELECT * FROM Customers WHERE CustomerID = ?";
        try (Connection connection = MySQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, customerId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return extractCustomerFromResultSet(resultSet);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private Customer extractCustomerFromResultSet(ResultSet resultSet) throws SQLException {
        Customer customer = new Customer();
        customer.setCustomerID(resultSet.getInt("CustomerID"));
        customer.setCustomerName(resultSet.getString("CustomerName"));
        customer.setAddress(resultSet.getString("Address"));
        customer.setContactNumber(resultSet.getString("ContactNumber"));
        customer.setMember(resultSet.getBoolean("IsMember"));
        return customer;
    }
}
