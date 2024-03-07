package com.example.service;

import com.example.dao.AuthorDao;
import com.example.model.Author;

import java.util.List;

public class AuthorService {

    private final AuthorDao authorDao;

    public AuthorService() {
        this.authorDao = new AuthorDao();
    }

    // Xem danh sách tác giả sách
    public List<Author> getAllAuthors() {
        return authorDao.getAllAuthors();
    }

    // Tìm kiếm tác giả sách theo tên, quê quán hoặc năm sinh
    public List<Author> searchAuthors(String keyword) {
        return authorDao.searchAuthors(keyword);
    }

    // Sắp xếp tác giả sách theo tên từ A-Z hoặc Z-A
    public List<Author> sortAuthors(String sortOrder) {
        return authorDao.sortAuthors(sortOrder);
    }

    // Thêm một tác giả sách mới
    public boolean addAuthor(Author author) {
        return authorDao.addAuthor(author);
    }

    // Cập nhật thông tin tác giả sách
    public boolean updateAuthor(Author author) {
        return authorDao.updateAuthor(author);
    }

    // Tắt/ẩn tác giả sách
    public boolean deactivateAuthor(int authorId) {
        return authorDao.deactivateAuthor(authorId);
    }

    // Bật/hiển thị tác giả sách
    public boolean activateAuthor(int authorId) {
        return authorDao.activateAuthor(authorId);
    }
    
    public Author getAuthorById(int authorId) {
        return authorDao.getAuthorById(authorId);
    }
    
 // Lấy toàn bộ tác giả với trạng thái hoạt động là true
    public List<Author> getAllActiveAuthors() {
        return authorDao.getAllActiveAuthors();
    }
}
