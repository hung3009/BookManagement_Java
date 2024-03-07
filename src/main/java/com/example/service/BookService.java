package com.example.service;

import com.example.dao.BookDao;
import com.example.model.Book;

import java.util.List;

public class BookService {

    private final BookDao bookDao;

    public BookService() {
        this.bookDao = new BookDao();
    }

    // Xem danh sách sách
    public List<Book> getAllBooks() {
        return bookDao.getAllBooks();
    }

    // Xem danh sách sách nóng/mới
    public List<Book> getHotNewBooks() {
        return bookDao.getHotNewBooks();
    }

    // Thêm một cuốn sách mới
    public boolean addBook(Book book) {
        return bookDao.addBook(book);
    }

    // Cập nhật thông tin sách
    public boolean updateBook(Book book) {
        return bookDao.updateBook(book);
    }

    // Vô hiệu hóa/ẩn một cuốn sách
    public boolean deactivateBook(int bookId) {
        return bookDao.deactivateBook(bookId);
    }

    // Bật/hiển thị một cuốn sách
    public boolean activateBook(int bookId) {
        return bookDao.activateBook(bookId);
    }

    // Xem danh sách các sách hết hàng
    public List<Book> getOutOfStockBooks() {
        return bookDao.getOutOfStockBooks();
    }

    // Thêm một cuốn sách mới
    public boolean addNewBook(Book book) {
        return bookDao.addNewBook(book);
    }

    // Tìm kiếm sách theo tên, tác giả, hoặc năm xuất bản
    public List<Book> searchBooks(String keyword) {
        return bookDao.searchBooksByTitle(keyword);
    }

    // Lấy danh sách tất cả sách được sắp xếp theo tiêu chí
    public List<Book> getAllBooksSortedByTitle(String sortOrder) {
        return bookDao.getAllBooksSortedByTitle(sortOrder);
    }
}
