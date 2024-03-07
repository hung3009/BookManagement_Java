package com.example.service;

import com.example.dao.BookBatchDao;
import com.example.model.BookBatch;

import java.util.List;

public class BookBatchService {

    private final BookBatchDao bookBatchDao;

    public BookBatchService() {
        this.bookBatchDao = new BookBatchDao();
    }

    // Lấy danh sách lô sách theo ID sách
    public List<BookBatch> getBatchesByBookId(int bookId) {
        return bookBatchDao.getBatchesByBookId(bookId);
    }

    // TODO: Thêm các phương thức khác cần thiết cho quản lý lô sách

}
