package com.example.service;

import com.example.dao.BookReceiptDao;
import com.example.model.BookBatch;
import com.example.model.BookReceipt;
import com.example.model.BookReceiptDetail;

import java.util.List;

public class BookReceiptService {

    private final BookReceiptDao bookReceiptDao;

    public BookReceiptService() {
        this.bookReceiptDao = new BookReceiptDao();
    }

    public Boolean saveBookReceiptWithDetails(BookReceipt bookReceipt, List<BookReceiptDetail> details, List<BookBatch> batchList) {
        return bookReceiptDao.saveBookReceiptWithDetails(bookReceipt, details, batchList);
    }

    public List<BookReceipt> getAllBookReceipts() {
        return bookReceiptDao.getAllBookReceipts();
    }
}
