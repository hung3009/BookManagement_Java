package com.example.dao;

import com.example.model.Book;
import com.example.util.MySQLConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BookDao {

    // Xem danh sách sách
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM Books";

        try (Connection connection = MySQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Book book = resultSetToBook(resultSet);
                books.add(book);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }

    // Xem danh sách sách nóng/mới
    public List<Book> getHotNewBooks() {
        List<Book> books = new ArrayList<>();
        // TODO: Thêm logic để lấy danh sách sách nóng/mới
        return books;
    }

    // Thêm một cuốn sách mới
    public boolean addBook(Book book) {
        String query = "INSERT INTO Books (Title, ISBN, CategoryID, PublisherID, AuthorID, NumberOfPages, Size, PublicationDate, IsActive) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = MySQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            setBookParameters(preparedStatement, book);

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Cập nhật thông tin sách
    public boolean updateBook(Book book) {
        String query = "UPDATE Books SET Title = ?, ISBN = ?, CategoryID = ?, PublisherID = ?, AuthorID = ?, NumberOfPages = ?, Size = ?, PublicationDate = ?, IsActive = ? WHERE BookID = ?";

        try (Connection connection = MySQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            setBookParameters(preparedStatement, book);
            preparedStatement.setInt(10, book.getBookID());

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Vô hiệu hóa/ẩn một cuốn sách
    public boolean deactivateBook(int bookId) {
        return updateBookStatus(bookId, false);
    }

    // Bật/hiển thị một cuốn sách
    public boolean activateBook(int bookId) {
        return updateBookStatus(bookId, true);
    }

    // Xem danh sách các sách hết hàng
    public List<Book> getOutOfStockBooks() {
        List<Book> outOfStockBooks = new ArrayList<>();
        // TODO: Thêm logic để lấy danh sách sách hết hàng
        return outOfStockBooks;
    }

    // Thêm một cuốn sách mới
    public boolean addNewBook(Book book) {
        // TODO: Thêm logic để thêm một cuốn sách mới
        return false;
    }

    // Tìm kiếm sách theo tên sách
    public List<Book> searchBooksByTitle(String keyword) {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM Books WHERE Title LIKE ?";

        try (Connection connection = MySQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, "%" + keyword + "%");

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Book book = resultSetToBook(resultSet); // Use the existing function
                    books.add(book);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }

    // Lấy danh sách tất cả sách được sắp xếp theo tiêu chí
    public List<Book> getAllBooksSortedByTitle(String sortOrder) {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM Books ORDER BY Title " + ("A-Z".equalsIgnoreCase(sortOrder) ? "ASC" : "DESC");

        try (Connection connection = MySQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Book book = resultSetToBook(resultSet); // Use the existing function
                books.add(book);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }

    // Hàm chuyển đổi ResultSet thành đối tượng Book
    private Book resultSetToBook(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setBookID(resultSet.getInt("BookID"));
        book.setTitle(resultSet.getString("Title"));
        book.setIsbn(resultSet.getString("ISBN"));
        book.setCategoryID(resultSet.getInt("CategoryID"));
        book.setPublisherID(resultSet.getInt("PublisherID"));
        book.setAuthorID(resultSet.getInt("AuthorID"));
        book.setNumberOfPages(resultSet.getInt("NumberOfPages"));
        book.setSize(resultSet.getString("Size"));
        book.setPublicationDate(resultSet.getDate("PublicationDate"));
        book.setActive(resultSet.getBoolean("IsActive"));
        return book;
    }

    // Hàm thiết lập tham số cho PreparedStatement từ đối tượng Book
    private void setBookParameters(PreparedStatement preparedStatement, Book book) throws SQLException {
        preparedStatement.setString(1, book.getTitle());
        preparedStatement.setString(2, book.getIsbn());
        preparedStatement.setInt(3, book.getCategoryID());
        preparedStatement.setInt(4, book.getPublisherID());
        preparedStatement.setInt(5, book.getAuthorID());
        preparedStatement.setInt(6, book.getNumberOfPages());
        preparedStatement.setString(7, book.getSize());
        preparedStatement.setDate(8, new java.sql.Date(book.getPublicationDate().getTime()));
        preparedStatement.setBoolean(9, book.isActive());
    }

    // Cập nhật trạng thái của một cuốn sách (kích hoạt hoặc vô hiệu hóa)
    private boolean updateBookStatus(int bookId, boolean isActive) {
        String query = "UPDATE Books SET IsActive = ? WHERE BookID = ?";

        try (Connection connection = MySQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setBoolean(1, isActive);
            preparedStatement.setInt(2, bookId);

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
