package com.example.ui;

import com.example.model.BookBatch;
import com.example.model.Customer;
import com.example.model.Order;
import com.example.model.OrderDetail;
import com.example.service.OrderService;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ManageOrdersPanel extends JPanel {

    private DefaultTableModel orderDetailTableModel;
    private JTable orderDetailTable;
    private JTextField bookIDField;
    private JTextField bookBatchIDField;
    private JTextField quantityField;
    private JTextField unitPriceField;
    private JButton searchBookButton;
//    private JButton updateQuantityButton;
    private JButton deleteButton;
    private JButton cancelOrderButton;
    private JButton createOrderButton;

    private JTextField orderIDField;
    private JTextField orderDateField;
    private JTextField employeeIDField;
    private JTextField customerIDField;
    private JTextField customerNameField;
    private JTextField discountField;
    private JCheckBox isActiveCheckbox;
    private JTextField taxVATField;
    private JTextField totalPriceOrderField;
    private JTextField moneyOfCustomerField;
    private JTextField refundField;
    private JTextField lastPriceField;
    
    private JButton searchCustomerButton;
//    private JButton addToCart;
    private JButton payButton;
    
    public static BookBatch bookBatchSelected = new BookBatch();
    public static int quantityBookSelected = 0;
    
    public static Customer customerSelected = new Customer();
    
    public static List<OrderDetail> orderDetailsList = new ArrayList<>();
    
    private int imployeeID;
    
    private Order order;
    
    private OrderService orderService;

    public ManageOrdersPanel(int userID) {
    	this.imployeeID = userID;
        initializeUI();
        this.orderService = new OrderService();
    }

    private void initializeUI() {
        setLayout(new GridLayout(1, 2));
        
        Border blackBorder = BorderFactory.createLineBorder(Color.BLACK);

        // Left Panel
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createTitledBorder("Danh sách chi tiết đơn hàng"));
        
        JPanel leftTopPanel = new JPanel(new BorderLayout());
        
        leftPanel.add(leftTopPanel, BorderLayout.NORTH);
        
        JPanel titleOfLeftPanel = new JPanel(new FlowLayout());
        titleOfLeftPanel.add(new JLabel("Chi tiết hóa đơn"));
        leftTopPanel.add(titleOfLeftPanel, BorderLayout.NORTH);
        
        JPanel inputOfLeftPanel = new JPanel();
        inputOfLeftPanel.setLayout(new BoxLayout(inputOfLeftPanel, BoxLayout.Y_AXIS));
        leftTopPanel.add(inputOfLeftPanel, BorderLayout.CENTER);
        
        JPanel bookIDPanel = new JPanel(new FlowLayout());
        JLabel bookIDLabel = new JLabel("Mã sách");
        bookIDField = new JTextField(20);
        bookIDPanel.add(bookIDLabel);
        bookIDPanel.add(bookIDField);
        
        JPanel bookBatchIDPanel = new JPanel(new FlowLayout());
        JLabel bookBatchIDLabel = new JLabel("Mã lô sách");
        bookBatchIDField = new JTextField(20);
        bookBatchIDPanel.add(bookBatchIDLabel);
        bookBatchIDPanel.add(bookBatchIDField);
        
        JPanel quantityPanel = new JPanel(new FlowLayout());
        JLabel quantityLabel = new JLabel("Số lượng");
        quantityField = new JTextField(20);
        quantityPanel.add(quantityLabel);
        quantityPanel.add(quantityField);
        
        bookIDLabel.setPreferredSize(bookBatchIDLabel.getPreferredSize());
        quantityLabel.setPreferredSize(bookBatchIDLabel.getPreferredSize());
        
        inputOfLeftPanel.add(bookIDPanel);
        inputOfLeftPanel.add(bookBatchIDPanel);
        inputOfLeftPanel.add(quantityPanel);
        
        JPanel functionOfLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftTopPanel.add(functionOfLeftPanel, BorderLayout.SOUTH);
        
        searchBookButton = new JButton("Tìm kiếm sách");
//        addToCart = new JButton("Thêm vào giỏ hàng");
        functionOfLeftPanel.add(searchBookButton);
//        functionOfLeftPanel.add(addToCart);
        

        // Create table model for order details
        orderDetailTableModel = new DefaultTableModel();
        
        orderDetailTableModel.addColumn("Book ID");
        orderDetailTableModel.addColumn("BookBatch ID");
        orderDetailTableModel.addColumn("Quantity");
        orderDetailTableModel.addColumn("Unit Price");

        orderDetailTable = new JTable(orderDetailTableModel);
        JScrollPane scrollPane = new JScrollPane(orderDetailTable);
        leftPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel leftButtonPanel = new JPanel(new FlowLayout());
//        updateQuantityButton = new JButton("Cập nhật số lượng");
        deleteButton = new JButton("Xóa");
//        leftButtonPanel.add(updateQuantityButton);
        leftButtonPanel.add(deleteButton);

        leftPanel.add(leftButtonPanel, BorderLayout.SOUTH);

        // Right Panel
        JPanel rightPanel = new JPanel(new GridLayout(2, 1));
        rightPanel.setBorder(BorderFactory.createTitledBorder("Thông tin đơn hàng"));
        
        JPanel rightTopPanel = new JPanel(new BorderLayout());
        rightPanel.add(rightTopPanel);
        
        JPanel titleOfRightPanel = new JPanel(new FlowLayout());
        titleOfRightPanel.add(new JLabel("Đơn hàng"));
        rightTopPanel.add(titleOfRightPanel, BorderLayout.NORTH);
        
        JPanel inputOfRightTopPanel = new JPanel();
        inputOfRightTopPanel.setLayout(new BoxLayout(inputOfRightTopPanel, BoxLayout.Y_AXIS));
        inputOfRightTopPanel.setBorder(blackBorder);
        rightTopPanel.add(inputOfRightTopPanel, BorderLayout.CENTER);

        JPanel orderIDPanel = new JPanel(new FlowLayout());
        JPanel orderDatePanel = new JPanel(new FlowLayout());
        JPanel employeeIDPanel = new JPanel(new FlowLayout());
        JPanel customerIDPanel = new JPanel(new FlowLayout());
        JPanel customerNamePanel = new JPanel(new FlowLayout());
        JPanel discountPanel = new JPanel(new FlowLayout());
        
        orderIDField = new JTextField(20);
        orderDateField = new JTextField(20);
        employeeIDField = new JTextField(20);
        customerIDField = new JTextField(20);
        customerNameField = new JTextField(20);
        discountField = new JTextField(20);
        
        JLabel orderIDLabel = new JLabel("Mã đơn hàng:");
        JLabel orderDateLabel = new JLabel("Ngày đặt hàng");
        JLabel employeeIDLabel = new JLabel("Mã nhân viên");
        JLabel customerIDLabel = new JLabel("Mã khách hàng");
        JLabel customerNameLabel = new JLabel("Tên khách hàng");
        JLabel discountLabel = new JLabel("Giảm giá");
        
        orderIDPanel.add(orderIDLabel);
        orderIDPanel.add(orderIDField);
        
        orderDatePanel.add(orderDateLabel);
        orderDatePanel.add(orderDateField);
        
        employeeIDPanel.add(employeeIDLabel);
        employeeIDPanel.add(employeeIDField);
        
        customerIDPanel.add(customerIDLabel);
        customerIDPanel.add(customerIDField);
        
        customerNamePanel.add(customerNameLabel);
        customerNamePanel.add(customerNameField);
        
        discountPanel.add(discountLabel);
        discountPanel.add(discountField);
        
        inputOfRightTopPanel.add(orderIDPanel);
        inputOfRightTopPanel.add(orderDatePanel);
        inputOfRightTopPanel.add(employeeIDPanel);
        inputOfRightTopPanel.add(customerIDPanel);
        inputOfRightTopPanel.add(customerNamePanel);
        inputOfRightTopPanel.add(discountPanel);
        
        JPanel functionOrderPanel = new JPanel(new FlowLayout());
        createOrderButton = new JButton("Tạo đơn hàng");
        searchCustomerButton = new JButton("Tìm Khách hàng");
        cancelOrderButton = new JButton("Hủy");
        functionOrderPanel.add(createOrderButton);
        functionOrderPanel.add(searchCustomerButton);
        functionOrderPanel.add(cancelOrderButton);
        rightTopPanel.add(functionOrderPanel, BorderLayout.SOUTH);

        
        JPanel rightBottomPanel = new JPanel(new BorderLayout());
        
        JPanel northOfRightBottomPanel = new JPanel();
        northOfRightBottomPanel.setPreferredSize(new Dimension(0, 50));
        rightBottomPanel.add(northOfRightBottomPanel, BorderLayout.NORTH);
        
        JPanel inputOfRightBottomPanel = new JPanel();
        inputOfRightBottomPanel.setLayout(new BoxLayout(inputOfRightBottomPanel, BoxLayout.Y_AXIS));
        inputOfRightBottomPanel.setBorder(blackBorder);
        rightBottomPanel.add(inputOfRightBottomPanel, BorderLayout.CENTER);
        
        JPanel taxVATPanel = new JPanel(new FlowLayout());
        JPanel totalPriceOrderPanel = new JPanel(new FlowLayout());
        JPanel moneyOfCustomerPanel = new JPanel(new FlowLayout());
        JPanel refundPanel = new JPanel(new FlowLayout());
        JPanel lastPricePanel = new JPanel(new FlowLayout());
        
        taxVATField = new JTextField(20);
        totalPriceOrderField = new JTextField(20);
        moneyOfCustomerField = new JTextField(20);
        refundField = new JTextField(20);
        lastPriceField = new JTextField(20);
        
        JLabel taxVATLabel = new JLabel("Thuế VAT");
        JLabel totalPriceOrderLabel = new JLabel("Tổng tiền hóa đơn");
        JLabel moneyOfCustomerLabel = new JLabel("Tiền khách hàng đưa");
        JLabel refundLabel = new JLabel("Tiền trả lại Khách hàng");
        JLabel lastPriceLabel = new JLabel("Đơn giá cuối cùng");
        
        taxVATLabel.setPreferredSize(refundLabel.getPreferredSize());
        totalPriceOrderLabel.setPreferredSize(refundLabel.getPreferredSize());
        moneyOfCustomerLabel.setPreferredSize(refundLabel.getPreferredSize());
        lastPriceLabel.setPreferredSize(refundLabel.getPreferredSize());
        
        orderIDLabel.setPreferredSize(refundLabel.getPreferredSize());
        orderDateLabel.setPreferredSize(refundLabel.getPreferredSize());
        employeeIDLabel.setPreferredSize(refundLabel.getPreferredSize());
        customerIDLabel.setPreferredSize(refundLabel.getPreferredSize());
        discountLabel.setPreferredSize(refundLabel.getPreferredSize());
        customerNameLabel.setPreferredSize(refundLabel.getPreferredSize());
        
        taxVATPanel.add(taxVATLabel);
        taxVATPanel.add(taxVATField);
        
        totalPriceOrderPanel.add(totalPriceOrderLabel);
        totalPriceOrderPanel.add(totalPriceOrderField);
        
        moneyOfCustomerPanel.add(moneyOfCustomerLabel);
        moneyOfCustomerPanel.add(moneyOfCustomerField);
        
        refundPanel.add(refundLabel);
        refundPanel.add(refundField);
        
        lastPricePanel.add(lastPriceLabel);
        lastPricePanel.add(lastPriceField);
        
        inputOfRightBottomPanel.add(taxVATPanel);
        inputOfRightBottomPanel.add(totalPriceOrderPanel);
        inputOfRightBottomPanel.add(moneyOfCustomerPanel);
        inputOfRightBottomPanel.add(refundPanel);
        inputOfRightBottomPanel.add(lastPricePanel);
        
        JPanel functionBottomRight = new JPanel(new FlowLayout());
        payButton = new JButton("Thanh toán");
        functionBottomRight.add(payButton);
        
        rightBottomPanel.add(functionBottomRight, BorderLayout.SOUTH);
        
        rightPanel.add(rightBottomPanel);
        
        // Add components to the main layout
        add(leftPanel);
        add(rightPanel);
        
        orderDetailsList.clear();
        
        searchBookButton.addActionListener(e -> openBookSearchDialog());
        searchCustomerButton.addActionListener(e -> openCustomerSearchDialog());
        createOrderButton.addActionListener(e -> createOrder());
        payButton.addActionListener(e -> saveOrder());
    }
    
    private void openBookSearchDialog() {
        BookSearchDialog bookSearchDialog = new BookSearchDialog((JFrame) SwingUtilities.getWindowAncestor(this));
        bookSearchDialog.setVisible(true);

        int bookID = bookBatchSelected.getBookID();
        int bookBatchID = bookBatchSelected.getLotID();
        int quantity = quantityBookSelected;
        int priceBatch = bookBatchSelected.getUnitPrice();

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setBookID(bookID);
        orderDetail.setLotID(bookBatchID);
        orderDetail.setQuantity(quantity);
        orderDetail.setUnitPrice(priceBatch * 1.1);
        
        if(order != null) {
        	orderDetail.setOrderID(order.getOrderID());
        }

        orderDetailsList.add(orderDetail);

        updateOrderDetailTable();
        bookIDField.setText(String.valueOf(bookID));
        bookBatchIDField.setText(String.valueOf(bookBatchID));
        quantityField.setText(String.valueOf(quantity));

        calculateTotalPrice();
        
        bookBatchSelected = null;
        quantityBookSelected = 0;
    }
    
    private void calculateTotalPrice() {
        int total = 0;

        // Tính tổng tiền từ danh sách chi tiết hóa đơn
        for (OrderDetail orderDetail : ManageOrdersPanel.orderDetailsList) {
            total += orderDetail.getQuantity() * orderDetail.getUnitPrice();
        }

        // Cập nhật vào trường Total Price
        totalPriceOrderField.setText(String.valueOf(total));

        // Kiểm tra nếu có thông tin khách hàng thì giảm giá 5%
        if (order != null && order.getCustomerID() != 0) {
            double discount = total * 0.05;
            order.setDiscount(0.05);
            discountField.setText(order.getDiscount()*100 + "%");

            // Cập nhật lại tổng tiền
            total -= discount;
        }

        // Cập nhật vào trường Last Price
        lastPriceField.setText(String.valueOf(total));
        
        if(order != null) {
        	order.setTotalPrice(total);
        }
    }
    
 // Phương thức để mở hộp thoại tìm kiếm khách hàng
    private void openCustomerSearchDialog() {
        if (order == null) {
            JOptionPane.showMessageDialog(this, "Chưa tạo hóa đơn.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        CustomerSearchDialog customerSearchDialog = new CustomerSearchDialog((JFrame) SwingUtilities.getWindowAncestor(this));
        customerSearchDialog.setVisible(true);

        int customerID = customerSelected.getCustomerID();
        String customerName = customerSelected.getCustomerName();

        if (customerID != 0) {
            order.setCustomerID(customerID);
            order.setDiscount(0.05);
            discountField.setText(order.getDiscount() * 100 + "%");
        }
        customerIDField.setText(String.valueOf(customerID));
        customerNameField.setText(customerName);

        calculateTotalPrice();
    }
    
    private void updateOrderDetailTable() {
        DefaultTableModel model = (DefaultTableModel) orderDetailTable.getModel();
        model.setRowCount(0);

        for (OrderDetail orderDetail : orderDetailsList) {
            Object[] rowData = {
                    orderDetail.getBookID(),
                    orderDetail.getLotID(),
                    orderDetail.getQuantity(),
                    orderDetail.getUnitPrice()
            };
            model.addRow(rowData);
        }
    }
    
    private void createOrder() {
        String orderID = generateOrderID();
        Date dateOrder = convertStringToDate(getCurrentDate());

        order = new Order();
        order.setOrderID(generateOrderID());
        order.setOrderDate(dateOrder);
        order.setEmployeeID(imployeeID);
        order.setDiscount(0);

        orderIDField.setText(order.getOrderID());
        orderDateField.setText(getCurrentDate());
        employeeIDField.setText(String.valueOf(imployeeID));
        discountField.setText(order.getDiscount() + "%");
        
        if(orderDetailsList.size() > 0) {
        	for (OrderDetail orderDetail : orderDetailsList) {
                orderDetail.setOrderID(order.getOrderID());
            }
        }
        
        calculateTotalPrice();
    }

    
    private String getCurrentDate() {
        // Hàm này trả về ngày hiện tại, bạn có thể thay thế bằng cách lấy ngày từ hệ thống
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(new Date());
    }

    private String generateOrderID() {
     // Tạo mã phiếu nhập có dạng PX + mốc thời gian hiện tại tính đến giây
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String timestamp = dateFormat.format(new Date());
        return "PX" + timestamp;
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
    
    private void saveOrder() {
        if (orderDetailsList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Hóa đơn không có chi tiết.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (order == null) {
            JOptionPane.showMessageDialog(this, "Chưa tạo hóa đơn.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Gọi hàm lưu hóa đơn từ tầng service
        boolean savedSuccessfully = orderService.saveOrder(order, orderDetailsList);

        if (savedSuccessfully) {
            JOptionPane.showMessageDialog(this, "Hóa đơn đã được lưu thành công.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            resetForm();
            orderDetailsList.clear();
            updateOrderDetailTable();
        } else {
            JOptionPane.showMessageDialog(this, "Lưu hóa đơn không thành công.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }


    
    private void resetForm() {
        JTextField[] fieldsToReset = {
                orderIDField, orderDateField, employeeIDField, customerIDField, customerNameField,
                discountField, taxVATField, totalPriceOrderField, moneyOfCustomerField, refundField, lastPriceField
        };

        for (JTextField field : fieldsToReset) {
            field.setText("");
        }
    }

}
