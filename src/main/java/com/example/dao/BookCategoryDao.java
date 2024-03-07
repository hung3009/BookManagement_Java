package com.example.dao;

import com.example.model.BookCategory;
import com.example.util.MySQLConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookCategoryDao {

    // Xem danh sách các danh mục sách
    public List<BookCategory> getAllCategories() {
        List<BookCategory> categories = new ArrayList<>();
        String query = "SELECT * FROM BookCategories";

        try (Connection connection = MySQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                BookCategory category = new BookCategory();
                category.setCategoryID(resultSet.getInt("CategoryID"));
                category.setCategoryName(resultSet.getString("CategoryName"));
                category.setActive(resultSet.getBoolean("IsActive"));
                categories.add(category);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categories;
    }

    // Tìm kiếm danh mục sách theo tên
    public BookCategory getCategoryByName(String categoryName) {
        BookCategory category = null;
        String query = "SELECT * FROM BookCategories WHERE CategoryName = ?";

        try (Connection connection = MySQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, categoryName);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                category = new BookCategory();
                category.setCategoryID(resultSet.getInt("CategoryID"));
                category.setCategoryName(resultSet.getString("CategoryName"));
                category.setActive(resultSet.getBoolean("IsActive"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return category;
    }

    // Thêm một thể loại sách mới
    public boolean addCategory(BookCategory category) {
        String query = "INSERT INTO BookCategories (CategoryName, IsActive) VALUES (?, ?)";

        try (Connection connection = MySQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, category.getCategoryName());
            preparedStatement.setBoolean(2, category.isActive());

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Cập nhật thông tin thể loại sách
    public boolean updateCategory(BookCategory category) {
        String query = "UPDATE BookCategories SET CategoryName = ?, IsActive = ? WHERE CategoryID = ?";

        try (Connection connection = MySQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, category.getCategoryName());
            preparedStatement.setBoolean(2, category.isActive());
            preparedStatement.setInt(3, category.getCategoryID());

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Tắt/ẩn danh mục sách
    public boolean deactivateCategory(int categoryId) {
        return updateCategoryStatus(categoryId, false);
    }

    // Bật/hiển thị danh mục sách
    public boolean activateCategory(int categoryId) {
        return updateCategoryStatus(categoryId, true);
    }

    private boolean updateCategoryStatus(int categoryId, boolean isActive) {
        String query = "UPDATE BookCategories SET IsActive = ? WHERE CategoryID = ?";

        try (Connection connection = MySQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setBoolean(1, isActive);
            preparedStatement.setInt(2, categoryId);

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    
    public BookCategory getCategoryById(int categoryId) {
        BookCategory category = null;
        String query = "SELECT * FROM BookCategories WHERE CategoryID = ?";

        try (Connection connection = MySQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, categoryId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                category = new BookCategory();
                category.setCategoryID(resultSet.getInt("CategoryID"));
                category.setCategoryName(resultSet.getString("CategoryName"));
                category.setActive(resultSet.getBoolean("IsActive"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return category;
    }
    
    public List<BookCategory> searchCategories(String keyword) {
        List<BookCategory> categories = new ArrayList<>();
        String query = "SELECT * FROM BookCategories WHERE CategoryName LIKE ?";

        try (Connection connection = MySQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, "%" + keyword + "%");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                BookCategory category = new BookCategory();
                category.setCategoryID(resultSet.getInt("CategoryID"));
                category.setCategoryName(resultSet.getString("CategoryName"));
                category.setActive(resultSet.getBoolean("IsActive"));
                categories.add(category);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categories;
    }

    public List<BookCategory> sortCategories(String sortOrder) {
        List<BookCategory> categories = new ArrayList<>();
        String query = "SELECT * FROM BookCategories ORDER BY CategoryName ";

        if ("Z-A".equals(sortOrder)) {
            query += "DESC";
        }

        try (Connection connection = MySQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                BookCategory category = new BookCategory();
                category.setCategoryID(resultSet.getInt("CategoryID"));
                category.setCategoryName(resultSet.getString("CategoryName"));
                category.setActive(resultSet.getBoolean("IsActive"));
                categories.add(category);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categories;
    }
    
    public List<BookCategory> getAllActiveCategories() {
        List<BookCategory> activeCategories = new ArrayList<>();
        String query = "SELECT * FROM BookCategories WHERE IsActive = true";

        try (Connection connection = MySQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                BookCategory category = new BookCategory();
                category.setCategoryID(resultSet.getInt("CategoryID"));
                category.setCategoryName(resultSet.getString("CategoryName"));
                category.setActive(resultSet.getBoolean("IsActive"));
                activeCategories.add(category);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return activeCategories;
    }

}
