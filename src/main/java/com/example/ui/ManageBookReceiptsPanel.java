package com.example.ui;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import com.example.model.Book;
import com.example.model.BookBatch;
import com.example.model.BookReceipt;
import com.example.model.BookReceiptDetail;
import com.example.service.BookReceiptService;
import com.example.service.BookService;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ManageBookReceiptsPanel extends JPanel {

    private JTextField receipIDField;
    private JTextField receiptDateField;
    private JTextField employeeIDField;
    private JTextField receipPriceTotal;
    private JTable bookDetailsTable;
    private DefaultTableModel tableModel;

    private JButton createReceipt;
    private JButton cancelReceipt;

    private JButton selectBookButton;
    private JTextField bookSelected;
    private JLabel lblQuantity;
    private JTextField quantityField;
    private JTextField unitPriceField;
    private JButton addToCartButton;

    private List<BookReceiptDetail> cartDetails;
    
    private JDialog bookSelectionDialog;
    private JTable bookSelectionTable;
    private DefaultTableModel bookSelectionTableModel;
    private JButton selectBookFromDialogButton;

    private List<Book> availableBooks;
    
    private JButton addBatchButton;
    private AddBatchDialog addBatchDialog;
    
    public static List<BookBatch> batchList = new ArrayList<BookBatch>();;
    
    private BookService bookService;
    private BookReceiptService bookReceiptService;

    public ManageBookReceiptsPanel(int userId) {
    	
    	bookService = new BookService();
    	bookReceiptService = new BookReceiptService();
    	
        setLayout(new BorderLayout());

        // Tiêu đề ở phía bắc
        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(new JLabel("Phiếu nhập Sách"));

        // panel trung tâm
        JPanel centerPanel = new JPanel(new BorderLayout());

        JPanel receiptPanel = new JPanel();
        receiptPanel.setLayout(new BorderLayout());
        receiptPanel.setBorder(BorderFactory.createTitledBorder("Thông tin Phiếu nhập"));

        centerPanel.add(receiptPanel, BorderLayout.NORTH);

        JPanel northOfReciptPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        createReceipt = new JButton("Tạo Phiếu nhập");
        cancelReceipt = new JButton("Hủy");
        northOfReciptPanel.add(createReceipt);
        northOfReciptPanel.add(cancelReceipt);
        receiptPanel.add(northOfReciptPanel, BorderLayout.SOUTH);

        JPanel inforReciptPanel = new JPanel();
        inforReciptPanel.setLayout(new GridLayout(1, 2, 100, 50));
        receiptPanel.add(inforReciptPanel, BorderLayout.CENTER);

        JPanel leftOfInforReciptPanel = new JPanel();
        leftOfInforReciptPanel.setLayout(new GridLayout(2, 2, 20, 30));
        inforReciptPanel.add(leftOfInforReciptPanel);

        JPanel rightOfInforReciptPanel = new JPanel();
        rightOfInforReciptPanel.setLayout(new GridLayout(2, 2, 20, 30));
        inforReciptPanel.add(rightOfInforReciptPanel);

        JLabel lblReceiptID = new JLabel("Mã Phiếu:");
        receipIDField = new JTextField();
        receipIDField.setEnabled(false); // Khóa JTextField
        receipIDField.setDisabledTextColor(Color.BLACK); // Đặt màu chữ màu đen

        JLabel lblReceiptDate = new JLabel("Ngày Tạo Phiếu:");
        receiptDateField = new JTextField(getCurrentDate());
        receiptDateField.setEnabled(false);
        receiptDateField.setDisabledTextColor(Color.BLACK);

        JLabel lblEmployeeID = new JLabel("Mã Nhân Viên:");
        employeeIDField = new JTextField(String.valueOf(userId));
        employeeIDField.setEnabled(false);
        employeeIDField.setDisabledTextColor(Color.BLACK);

        JLabel lblPriceTotalID = new JLabel("Tổng giá:");
        receipPriceTotal = new JTextField();
        receipPriceTotal.setEnabled(false);
        receipPriceTotal.setDisabledTextColor(Color.BLACK);

        leftOfInforReciptPanel.add(lblReceiptID);
        leftOfInforReciptPanel.add(receipIDField);

        leftOfInforReciptPanel.add(lblReceiptDate);
        leftOfInforReciptPanel.add(receiptDateField);

        rightOfInforReciptPanel.add(lblEmployeeID);
        rightOfInforReciptPanel.add(employeeIDField);

        rightOfInforReciptPanel.add(lblPriceTotalID);
        rightOfInforReciptPanel.add(receipPriceTotal);

        add(topPanel, BorderLayout.NORTH);

        add(centerPanel, BorderLayout.CENTER);

        JPanel detailReceiptPanel = new JPanel(new BorderLayout());
        detailReceiptPanel.setBorder(BorderFactory.createTitledBorder("Chi tiết Phiếu nhập"));

        centerPanel.add(detailReceiptPanel, BorderLayout.CENTER);

        // Tạo bảng chi tiết và các chức năng ở trung tâm
        tableModel = new DefaultTableModel();
        tableModel.addColumn("STT");
        tableModel.addColumn("ID Sản Phẩm");
        tableModel.addColumn("Số Lượng");
        tableModel.addColumn("Giá Nhập");

        bookDetailsTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(bookDetailsTable);

        detailReceiptPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel functionOfDetailReceiptPanel = new JPanel();
        functionOfDetailReceiptPanel.setLayout(new FlowLayout(FlowLayout.LEADING));

        detailReceiptPanel.add(functionOfDetailReceiptPanel, BorderLayout.NORTH);

        selectBookButton = new JButton("Chọn Sách");
        bookSelected = new JTextField(15);
        lblQuantity = new JLabel("Số lượng");
        quantityField = new JTextField(5);
        unitPriceField = new JTextField(10);
        addToCartButton = new JButton("Thêm vào Giỏ Hàng");

        functionOfDetailReceiptPanel.add(selectBookButton);
        functionOfDetailReceiptPanel.add(bookSelected);
        functionOfDetailReceiptPanel.add(lblQuantity);
        functionOfDetailReceiptPanel.add(quantityField);
        functionOfDetailReceiptPanel.add(new JLabel("Giá nhập:"));
        functionOfDetailReceiptPanel.add(unitPriceField);
        functionOfDetailReceiptPanel.add(addToCartButton);

        add(centerPanel, BorderLayout.CENTER);

        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        southPanel.setPreferredSize(new Dimension(0, 100));
        JButton saveReceipt = new JButton("Lưu Phiếu");
        southPanel.add(saveReceipt);

        add(southPanel, BorderLayout.SOUTH);

        // Khởi tạo danh sách chi tiết phiếu nhập
        cartDetails = new ArrayList<>();
        
     // Sự kiện khi nhấn nút "Tạo Phiếu nhập"
        createReceipt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Tạo mã phiếu nhập khi nhấn nút "Tạo Phiếu nhập"
                String generatedReceiptID = generateReceiptID();
                receipIDField.setText(generatedReceiptID);
            }
        });
        
        // nhấn nút hủy
        cancelReceipt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clearReceiptFields();
				batchList.clear();
			}
		});

        // Sự kiện khi nhấn nút "Chọn Sách"
        selectBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showBookSelectionDialog();
            }
        });

        // Sự kiện khi nhấn nút "Thêm vào Giỏ Hàng"
        addToCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addToCart();
            }
        });

        // Sự kiện khi nhấn nút "Lưu Phiếu"
        saveReceipt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	System.out.println(" check size final: " + batchList.size());
                saveReceipt();
            }
        });
    }
    
    private void openAddBatchDialog(BookReceiptDetail detail) {
        AddBatchDialog addBatchDialog = new AddBatchDialog((Frame) SwingUtilities.getWindowAncestor(this), detail);
        addBatchDialog.setVisible(true);
        
    }

    private void showBookSelectionDialog() {
        bookSelectionDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Chọn Sách", true);
        bookSelectionDialog.setLayout(new BorderLayout());
        bookSelectionDialog.setSize(400, 300);

        // Tạo bảng hiển thị danh sách sách
        bookSelectionTableModel = new DefaultTableModel();
        bookSelectionTableModel.addColumn("Mã Sách");
        bookSelectionTableModel.addColumn("Tên Sách");

        bookSelectionTable = new JTable(bookSelectionTableModel);
        JScrollPane scrollPane = new JScrollPane(bookSelectionTable);

        bookSelectionDialog.add(scrollPane, BorderLayout.CENTER);

        // Nút chọn sách từ hộp thoại
        selectBookFromDialogButton = new JButton("Chọn Sách");
        bookSelectionDialog.add(selectBookFromDialogButton, BorderLayout.SOUTH);

        // Lấy danh sách sách từ cơ sở dữ liệu hoặc mock data
        availableBooks = getAvailableBooks();

        // Cập nhật bảng hiển thị
        updateBookSelectionTable();

        // Sự kiện khi nhấn nút "Chọn Sách" trong hộp thoại
        selectBookFromDialogButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addToCartFromDialog();
                bookSelectionDialog.dispose();
            }
        });

        // Hiển thị hộp thoại ở giữa màn hình
        bookSelectionDialog.setLocationRelativeTo(null);

        bookSelectionDialog.setVisible(true);
    }

    
    private List<Book> getAvailableBooks() {
    	List<Book> books = bookService.getAllBooks();
        return books;
    }

    private void updateBookSelectionTable() {
        for (Book book : availableBooks) {
            bookSelectionTableModel.addRow(new Object[]{book.getBookID(), book.getTitle()});
        }
    }

    private void addToCartFromDialog() {
        int selectedRow = bookSelectionTable.getSelectedRow();
        if (selectedRow >= 0 && selectedRow < availableBooks.size()) {
            Book selectedBook = availableBooks.get(selectedRow);
            bookSelected.setText(selectedBook.getBookID() + "");
            
        }
    }



    private void addToCart() {
        try {
            // Kiểm tra xem có nhập đủ thông tin không
            if (bookSelected.getText().isEmpty() || quantityField.getText().isEmpty() || unitPriceField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đủ thông tin.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Kiểm tra chuyển đổi được định dạng hay không
            int bookID, quantity;
            int unitPrice;

            try {
                bookID = Integer.parseInt(bookSelected.getText());
                quantity = Integer.parseInt(quantityField.getText());
                unitPrice = Integer.parseInt(unitPriceField.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng số.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Tính tổng giá nhập của chi tiết phiếu nhập
            int totalCost = quantity * unitPrice;

            // Thêm chi tiết vào danh sách
            BookReceiptDetail detail = new BookReceiptDetail(bookID, quantity, unitPrice);
            cartDetails.add(detail);

            // Cập nhật bảng hiển thị
            updateTable();
            
         // Mở hộp thoại thêm lô sách với thông tin chi tiết           
            openAddBatchDialog(detail);    
            
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: Xử lý ngoại lệ (nếu cần)
        }
    }

    private void updateTable() {
        // Xóa toàn bộ dữ liệu trong bảng
        tableModel.setRowCount(0);

        // Cập nhật lại số liệu
        double totalCost = 0;
        for (int i = 0; i < cartDetails.size(); i++) {
        	BookReceiptDetail detail = cartDetails.get(i);
            totalCost += detail.getUnitPrice() * detail.getQuantity();
            tableModel.addRow(new Object[]{i + 1, detail.getBookID(), detail.getQuantity(), detail.getUnitPrice()});
        }

        // Hiển thị tổng giá nhập
        receipPriceTotal.setText(String.valueOf(totalCost));
    }

    private void saveReceipt() {
        try {
            // Kiểm tra thông tin trước khi lưu
            if (cartDetails.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Chưa có thông tin chi tiết phiếu nhập.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Tạo mã phiếu nhập
            String receiptID = receipIDField.getText();
            
            if(receiptID.equals("")) {
            	JOptionPane.showMessageDialog(this, "Chưa tạo mã phiếu", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Lấy thông tin cần thiết từ giao diện
            String receiptDate = receiptDateField.getText();
            int employeeID = Integer.parseInt(employeeIDField.getText());
            double totalCost = Double.parseDouble(receipPriceTotal.getText());

            // Kiểm tra xem thông tin có hợp lệ không
            if (receiptDate.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày tạo phiếu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Tạo đối tượng BookReceipt từ thông tin đã lấy
            BookReceipt bookReceipt = new BookReceipt(receiptID, convertStringToDate(receiptDate), employeeID, totalCost);

            // Lưu phiếu nhập vào cơ sở dữ liệu
            boolean checkSave = bookReceiptService.saveBookReceiptWithDetails(bookReceipt, cartDetails, batchList);

            if (checkSave) {
                JOptionPane.showMessageDialog(this, "Lưu phiếu nhập thành công");
                clearReceiptFields();
                batchList.clear();
            } else {
                JOptionPane.showMessageDialog(this, "Lưu phiếu nhập thất bại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng số.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: Xử lý ngoại lệ (nếu cần)
        }
    }

    private String generateReceiptID() {
        // Tạo mã phiếu nhập có dạng PN + mốc thời gian hiện tại tính đến giây
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String timestamp = dateFormat.format(new Date());
        return "PN" + timestamp;
    }

    private Date convertStringToDate(String dateString) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void clearReceiptFields() {
        // Xóa dữ liệu trên giao diện sau khi lưu thành công
        receipIDField.setText("");
        receiptDateField.setText(getCurrentDate());
        receipPriceTotal.setText("");
        bookSelected.setText("");
        quantityField.setText("");
        unitPriceField.setText("");
        batchList.clear();

        // Xóa dữ liệu trong danh sách chi tiết
        cartDetails.clear();

        // Cập nhật lại bảng
        updateTable();
    }

    private String getCurrentDate() {
        // Hàm này trả về ngày hiện tại, bạn có thể thay thế bằng cách lấy ngày từ hệ thống
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(new Date());
    }

}
