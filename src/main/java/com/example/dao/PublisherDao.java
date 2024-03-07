package com.example.dao;

import com.example.model.Publisher;
import com.example.util.MySQLConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PublisherDao {

    // Xem danh sách các nhà xuất bản
    public List<Publisher> getAllPublishers() {
        List<Publisher> publishers = new ArrayList<>();
        String query = "SELECT * FROM Publishers";

        try (Connection connection = MySQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Publisher publisher = mapResultSetToPublisher(resultSet);
                publishers.add(publisher);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return publishers;
    }

    // Tìm kiếm nhà xuất bản theo tên
    public Publisher getPublisherByName(String publisherName) {
        Publisher publisher = null;
        String query = "SELECT * FROM Publishers WHERE PublisherName = ?";

        try (Connection connection = MySQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, publisherName);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                publisher = mapResultSetToPublisher(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return publisher;
    }

    // Thêm một nhà xuất bản mới
    public boolean addPublisher(Publisher publisher) {
        String query = "INSERT INTO Publishers (PublisherName, IsActive, Address, PhoneNumber, Email) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = MySQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, publisher.getPublisherName());
            preparedStatement.setBoolean(2, publisher.isActive());
            preparedStatement.setString(3, publisher.getAddress());
            preparedStatement.setString(4, publisher.getPhoneNumber());
            preparedStatement.setString(5, publisher.getEmail());

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Cập nhật thông tin nhà xuất bản
    public boolean updatePublisher(Publisher publisher) {
        String query = "UPDATE Publishers SET PublisherName = ?, IsActive = ?, Address = ?, PhoneNumber = ?, Email = ? WHERE PublisherID = ?";

        try (Connection connection = MySQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, publisher.getPublisherName());
            preparedStatement.setBoolean(2, publisher.isActive());
            preparedStatement.setString(3, publisher.getAddress());
            preparedStatement.setString(4, publisher.getPhoneNumber());
            preparedStatement.setString(5, publisher.getEmail());
            preparedStatement.setInt(6, publisher.getPublisherID());

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Tắt/ẩn nhà xuất bản
    public boolean deactivatePublisher(int publisherID) {
        return updatePublisherStatus(publisherID, false);
    }

    // Bật/hiển thị nhà xuất bản
    public boolean activatePublisher(int publisherID) {
        return updatePublisherStatus(publisherID, true);
    }

    private boolean updatePublisherStatus(int publisherID, boolean isActive) {
        String query = "UPDATE Publishers SET IsActive = ? WHERE PublisherID = ?";

        try (Connection connection = MySQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setBoolean(1, isActive);
            preparedStatement.setInt(2, publisherID);

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public Publisher getPublisherById(int publisherID) {
        Publisher publisher = null;
        String query = "SELECT * FROM Publishers WHERE PublisherID = ?";

        try (Connection connection = MySQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, publisherID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                publisher = mapResultSetToPublisher(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return publisher;
    }

    public List<Publisher> searchPublishers(String keyword) {
        List<Publisher> publishers = new ArrayList<>();
        String query = "SELECT * FROM Publishers WHERE PublisherName LIKE ?";

        try (Connection connection = MySQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, "%" + keyword + "%");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Publisher publisher = mapResultSetToPublisher(resultSet);
                publishers.add(publisher);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return publishers;
    }

    public List<Publisher> sortPublishers(String sortOrder) {
        List<Publisher> publishers = new ArrayList<>();
        String query = "SELECT * FROM Publishers ORDER BY PublisherName ";

        if ("Z-A".equals(sortOrder)) {
            query += "DESC";
        }

        try (Connection connection = MySQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Publisher publisher = mapResultSetToPublisher(resultSet);
                publishers.add(publisher);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return publishers;
    }
    
    public List<Publisher> getAllActivePublishers() {
        List<Publisher> activePublishers = new ArrayList<>();
        String query = "SELECT * FROM Publishers WHERE IsActive = true";

        try (Connection connection = MySQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Publisher publisher = mapResultSetToPublisher(resultSet);
                activePublishers.add(publisher);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return activePublishers;
    }

    private Publisher mapResultSetToPublisher(ResultSet resultSet) throws SQLException {
        Publisher publisher = new Publisher();
        publisher.setPublisherID(resultSet.getInt("PublisherID"));
        publisher.setPublisherName(resultSet.getString("PublisherName"));
        publisher.setActive(resultSet.getBoolean("IsActive"));
        publisher.setAddress(resultSet.getString("Address"));
        publisher.setPhoneNumber(resultSet.getString("PhoneNumber"));
        publisher.setEmail(resultSet.getString("Email"));
        return publisher;
    }
}
