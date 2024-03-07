package com.example.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.example.model.BookReceipt;
import com.example.service.BookReceiptService;

import java.awt.*;
import java.util.List;

public class BookReceiptsPanel extends JPanel {
    private int userId;
    private JTable bookReceiptsTable;

    public BookReceiptsPanel(int userId) {
        this.userId = userId;
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        // Tạo một panel chứa tiêu đề
        JPanel titlePanel = new JPanel();
        titlePanel.add(new JLabel("Phiếu nhập sách"));
        add(titlePanel, BorderLayout.NORTH);

        // Tạo một bảng để hiển thị danh sách phiếu nhập sách
        bookReceiptsTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(bookReceiptsTable);
        add(scrollPane, BorderLayout.CENTER);

        // Thực hiện load dữ liệu từ cơ sở dữ liệu vào bảng
        loadBookReceiptsData();
    }

    private void loadBookReceiptsData() {
        // Truyền dữ liệu từ service hoặc DAO để lấy danh sách phiếu nhập sách
        // Ở đây tôi giả sử bạn có một hàm trong BookReceiptService như getAllBookReceipts

        BookReceiptService bookReceiptService = new BookReceiptService();
        List<BookReceipt> bookReceipts = bookReceiptService.getAllBookReceipts();

        // Tạo mô hình cho bảng
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Mã phiếu");
        tableModel.addColumn("Ngày nhập");
        tableModel.addColumn("Nhân viên");
        tableModel.addColumn("Tổng giá trị");

        // Đổ dữ liệu vào bảng
        for (BookReceipt bookReceipt : bookReceipts) {
            Object[] rowData = {
                    bookReceipt.getReceiptID(),
                    bookReceipt.getReceiptDate(),
                    bookReceipt.getEmployeeID(),
                    bookReceipt.getTotalCost()
            };
            tableModel.addRow(rowData);
        }

        // Đặt mô hình vào bảng
        bookReceiptsTable.setModel(tableModel);
    }
}
