package com.example.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.example.model.BookBatch;
import com.example.model.BookReceiptDetail;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AddBatchDialog extends JDialog {

    private boolean isBatchCreated = false;

    private JTable batchTable;
    private DefaultTableModel batchTableModel;
    private BookReceiptDetail detail;

    private JTextField bookIDField;
    private JTextField quantityOriginalField;
    private JTextField quantityCurrentField;
    private JTextField purchaseDateField;
    private JTextField unitPriceField;
    private JTextField storageLocationField;
    

    public AddBatchDialog(Frame owner, BookReceiptDetail detail) {
        super(owner, "Thêm Lô Sách", true);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setSize(800, 600);
        this.detail = detail;
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        
        System.out.println("check size: " + ManageBookReceiptsPanel.batchList.size());

        // Tạo các trường nhập liệu
        bookIDField = new JTextField(String.valueOf(detail.getBookID()));
        bookIDField.setEnabled(false);
        bookIDField.setDisabledTextColor(Color.BLACK);
        quantityOriginalField = new JTextField(String.valueOf(detail.getQuantity()));
        quantityOriginalField.setEnabled(false);
        quantityOriginalField.setDisabledTextColor(Color.BLACK);
        quantityCurrentField = new JTextField(String.valueOf(detail.getQuantity()));
        quantityCurrentField.setEnabled(false);
        quantityCurrentField.setDisabledTextColor(Color.BLACK);
        purchaseDateField = new JTextField(getCurrentDate().toString());
        purchaseDateField.setEnabled(false);
        purchaseDateField.setDisabledTextColor(Color.BLACK);
        unitPriceField = new JTextField(String.valueOf(detail.getUnitPrice()));
        unitPriceField.setEnabled(false);
        unitPriceField.setDisabledTextColor(Color.BLACK);
        storageLocationField = new JTextField();

        // Tạo bảng hiển thị danh sách lô sách
        batchTableModel = new DefaultTableModel();
        batchTableModel.addColumn("Book ID");
        batchTableModel.addColumn("Quantity Original");
        batchTableModel.addColumn("Quantity Current");
        batchTableModel.addColumn("Purchase Date");
        batchTableModel.addColumn("Unit Price");
        batchTableModel.addColumn("Storage Location");

        batchTable = new JTable(batchTableModel);
        JScrollPane scrollPane = new JScrollPane(batchTable);

        // Tạo nút lưu lô sách
        JButton saveBatchButton = new JButton("Lưu Lô Sách");
        saveBatchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isBatchCreated) {
                	if(storageLocationField.getText().isEmpty()) {
                		JOptionPane.showMessageDialog(AddBatchDialog.this, "Chưa nhập vị trí để sách", "Lỗi", JOptionPane.ERROR_MESSAGE);
                		return;
                	}
                    addToBatchList();
                    updateBatchTable();
                    isBatchCreated = true;
                }
            }
        });

        // Tạo giao diện
        JPanel northPanel = new JPanel(new BorderLayout());
        add(northPanel, BorderLayout.NORTH);

        JPanel titlePanel = new JPanel(new FlowLayout());
        titlePanel.add(new JLabel("Tạo Lô sách lưu vào kho"));
        northPanel.add(titlePanel, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(6, 2, 20, 20));
        northPanel.add(inputPanel, BorderLayout.CENTER);

        // Thêm các trường nhập liệu vào giao diện
        inputPanel.add(new JLabel("Book ID:"));
        inputPanel.add(bookIDField);
        inputPanel.add(new JLabel("Quantity Original:"));
        inputPanel.add(quantityOriginalField);
        inputPanel.add(new JLabel("Quantity Current:"));
        inputPanel.add(quantityCurrentField);
        inputPanel.add(new JLabel("Purchase Date:"));
        inputPanel.add(purchaseDateField);
        inputPanel.add(new JLabel("Unit Price:"));
        inputPanel.add(unitPriceField);
        inputPanel.add(new JLabel("Storage Location:"));
        inputPanel.add(storageLocationField);

        JPanel functionBooklotsPanel = new JPanel(new FlowLayout());
        functionBooklotsPanel.add(saveBatchButton);
        northPanel.add(functionBooklotsPanel, BorderLayout.SOUTH);

        // Thêm bảng vào giao diện
        add(scrollPane, BorderLayout.CENTER);

        // Tạo nút đóng
        JButton closeButton = new JButton("Đóng");
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isBatchCreated) {
                    dispose();
                } else {
                    // Nếu chưa tạo lô sách, hiển thị thông báo
                    JOptionPane.showMessageDialog(AddBatchDialog.this, "Vui lòng tạo ít nhất một lô sách.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Thêm nút đóng vào giao diện
        add(closeButton, BorderLayout.SOUTH);
        updateBatchTable();

        setLocationRelativeTo(null);
    }

    private void addToBatchList() {
        // Lấy thông tin từ các trường nhập liệu
        int bookID = detail.getBookID();
        int quantityOriginal = detail.getQuantity();
        int quantityCurrent = Integer.parseInt(quantityCurrentField.getText());
        Date purchaseDate = getCurrentDate();
        int unitPrice = detail.getUnitPrice();
        String storageLocation = storageLocationField.getText();

        // Tạo đối tượng BookBatch và thêm vào danh sách
        BookBatch bookBatch = new BookBatch(bookID, quantityOriginal, quantityCurrent, purchaseDate, unitPrice, storageLocation);
        
        ManageBookReceiptsPanel.batchList.add(bookBatch);

        // Xóa dữ liệu nhập sau khi thêm vào danh sách
        clearFields();
    }

    private void updateBatchTable() {
        // Xóa toàn bộ dữ liệu trong bảng
        batchTableModel.setRowCount(0);

        // Cập nhật lại dữ liệu từ danh sách BookBatch
        for (BookBatch batch : ManageBookReceiptsPanel.batchList) {
            batchTableModel.addRow(new Object[]{
                    batch.getBookID(),
                    batch.getQuantityOriginal(),
                    batch.getQuantityCurrent(),
                    batch.getPurchaseDate(),
                    batch.getUnitPrice(),
                    batch.getStorageLocation()
            });
        }
    }

    private Date getCurrentDate() {
        return new Date();
    }

    private void clearFields() {
        storageLocationField.setText("");
    }

}
