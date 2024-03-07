package com.example.service;

import com.example.dao.BookCategoryDao;
import com.example.model.BookCategory;

import java.util.List;

public class BookCategoryService {

    private final BookCategoryDao bookCategoryDao;

    public BookCategoryService() {
        this.bookCategoryDao = new BookCategoryDao();
    }

    // Lấy danh sách tất cả danh mục sách
    public List<BookCategory> getAllCategories() {
        return bookCategoryDao.getAllCategories();
    }

    // Tìm kiếm danh mục sách theo tên
    public BookCategory getCategoryByName(String categoryName) {
        return bookCategoryDao.getCategoryByName(categoryName);
    }

    // Thêm mới một danh mục sách
    public boolean addCategory(BookCategory category) {
        // Kiểm tra xem danh mục có tồn tại chưa
        BookCategory existingCategory = bookCategoryDao.getCategoryByName(category.getCategoryName());
        if (existingCategory != null) {
            // Danh mục đã tồn tại
            return false;
        }

        // Thêm danh mục mới
        return bookCategoryDao.addCategory(category);
    }

    // Cập nhật thông tin danh mục sách
    public boolean updateCategory(BookCategory category) {
        // Kiểm tra xem danh mục có tồn tại chưa
        BookCategory existingCategory = bookCategoryDao.getCategoryByName(category.getCategoryName());
        if (existingCategory != null && existingCategory.getCategoryID() != category.getCategoryID()) {
            // Danh mục đã tồn tại với tên khác chính nó
            return false;
        }

        // Cập nhật thông tin danh mục
        return bookCategoryDao.updateCategory(category);
    }

    // Tắt/ẩn danh mục sách
    public boolean deactivateCategory(int categoryId) {
        return bookCategoryDao.deactivateCategory(categoryId);
    }

    // Bật/hiển thị danh mục sách
    public boolean activateCategory(int categoryId) {
        return bookCategoryDao.activateCategory(categoryId);
    }
    
    public BookCategory getCategoryById(int categoryId) {
        return bookCategoryDao.getCategoryById(categoryId);
    }
    
    public List<BookCategory> searchCategories(String keyword) {
        return bookCategoryDao.searchCategories(keyword);
    }

    public List<BookCategory> sortCategories(String sortOrder) {
        return bookCategoryDao.sortCategories(sortOrder);
    }
    
 // Lấy toàn bộ danh mục sách có trạng thái hoạt động là true
    public List<BookCategory> getAllActiveCategories() {
        return bookCategoryDao.getAllActiveCategories();
    }
}
