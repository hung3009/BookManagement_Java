package com.example.dao;

import com.example.model.Author;
import com.example.util.MySQLConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AuthorDao {

    // Xem danh sách tác giả sách
    public List<Author> getAllAuthors() {
        List<Author> authors = new ArrayList<>();
        String query = "SELECT * FROM Authors";

        try (Connection connection = MySQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Author author = extractAuthorFromResultSet(resultSet);
                authors.add(author);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return authors;
    }

    // Tìm kiếm tác giả sách theo tên, quê quán hoặc năm sinh
    public List<Author> searchAuthors(String keyword) {
        List<Author> authors = new ArrayList<>();
        String query = "SELECT * FROM Authors WHERE AuthorName LIKE ? OR Hometown LIKE ? OR YEAR(BirthDate) = ?";

        try (Connection connection = MySQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, "%" + keyword + "%");
            preparedStatement.setString(2, "%" + keyword + "%");
            preparedStatement.setString(3, keyword);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Author author = extractAuthorFromResultSet(resultSet);
                authors.add(author);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return authors;
    }

    // Sắp xếp tác giả sách theo tên từ A-Z hoặc Z-A
    public List<Author> sortAuthors(String sortOrder) {
        List<Author> authors = new ArrayList<>();
        String query = "SELECT * FROM Authors ORDER BY AuthorName " + (sortOrder.equals("A-Z") ? "ASC" : "DESC");

        try (Connection connection = MySQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Author author = extractAuthorFromResultSet(resultSet);
                authors.add(author);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return authors;
    }

    // Thêm một tác giả sách mới
    public boolean addAuthor(Author author) {
        String query = "INSERT INTO Authors (AuthorName, Email, PhoneNumber, BirthDate, Gender, Hometown, IsActive) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = MySQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, author.getAuthorName());
            preparedStatement.setString(2, author.getEmail());
            preparedStatement.setString(3, author.getPhoneNumber());
            preparedStatement.setDate(4, new java.sql.Date(author.getBirthDate().getTime()));
            preparedStatement.setString(5, author.getGender());
            preparedStatement.setString(6, author.getHometown());
            preparedStatement.setBoolean(7, author.isActive());

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Cập nhật thông tin tác giả sách
    public boolean updateAuthor(Author author) {
        String query = "UPDATE Authors SET AuthorName = ?, Email = ?, PhoneNumber = ?, " +
                "BirthDate = ?, Gender = ?, Hometown = ?, IsActive = ? WHERE AuthorID = ?";

        try (Connection connection = MySQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, author.getAuthorName());
            preparedStatement.setString(2, author.getEmail());
            preparedStatement.setString(3, author.getPhoneNumber());
            preparedStatement.setDate(4, new java.sql.Date(author.getBirthDate().getTime()));
            preparedStatement.setString(5, author.getGender());
            preparedStatement.setString(6, author.getHometown());
            preparedStatement.setBoolean(7, author.isActive());
            preparedStatement.setInt(8, author.getAuthorID());

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Tắt/ẩn tác giả sách
    public boolean deactivateAuthor(int authorId) {
        return updateAuthorStatus(authorId, false);
    }

    // Bật/hiển thị tác giả sách
    public boolean activateAuthor(int authorId) {
        return updateAuthorStatus(authorId, true);
    }

    private boolean updateAuthorStatus(int authorId, boolean isActive) {
        String query = "UPDATE Authors SET IsActive = ? WHERE AuthorID = ?";

        try (Connection connection = MySQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setBoolean(1, isActive);
            preparedStatement.setInt(2, authorId);

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    
 // Lấy thông tin tác giả sách dựa trên ID
    public Author getAuthorById(int authorId) {
        String query = "SELECT * FROM Authors WHERE AuthorID = ?";
        try (Connection connection = MySQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, authorId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return extractAuthorFromResultSet(resultSet);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
    
    public List<Author> getAllActiveAuthors() {
        List<Author> activeAuthors = new ArrayList<>();
        String query = "SELECT * FROM Authors WHERE IsActive = true";

        try (Connection connection = MySQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Author author = extractAuthorFromResultSet(resultSet);
                activeAuthors.add(author);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return activeAuthors;
    }

    private Author extractAuthorFromResultSet(ResultSet resultSet) throws SQLException {
        Author author = new Author();
        author.setAuthorID(resultSet.getInt("AuthorID"));
        author.setAuthorName(resultSet.getString("AuthorName"));
        author.setEmail(resultSet.getString("Email"));
        author.setPhoneNumber(resultSet.getString("PhoneNumber"));
        author.setBirthDate(resultSet.getDate("BirthDate"));
        author.setGender(resultSet.getString("Gender"));
        author.setHometown(resultSet.getString("Hometown"));
        author.setActive(resultSet.getBoolean("IsActive"));
        return author;
    }
}
