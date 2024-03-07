package com.example.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.example.model.Order;
import com.example.service.OrderService;
import com.toedter.calendar.JDateChooser;

public class ManageStatisticsPanel extends JPanel implements ActionListener {

    private JComboBox<String> comboBoxTimeRange;
    private JDateChooser dateChooserStart;
    private JDateChooser dateChooserEnd;
    private JTextField txtBookId;
    private JTextField txtCategoryId;
    private JTextField txtEmployeeId;
    private JTextField txtCustomerId;
    private JButton btnSearchBook;
    private JButton btnSearchCategory;
    private JButton btnSearchEmployee;
    private JButton btnSearchCustomer;
    private JTable resultTable;
    private OrderService orderService;
    private NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
    
    private JLabel lblNumberOfOrders;
    private JLabel lblTotalRevenue;

    public ManageStatisticsPanel() {
        setLayout(new BorderLayout());
        orderService = new OrderService();

        JPanel panelInput = createInputPanel();
        JPanel panelResult = createResultPanel();
        JPanel panelSummary = createSummaryPanel();
        panelSummary.setPreferredSize(new Dimension(0, 100));

        add(panelInput, BorderLayout.NORTH);
        add(panelResult, BorderLayout.CENTER);
        add(panelSummary, BorderLayout.SOUTH);
    }

    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 1));  // Chia thành 2 hàng và 1 cột

        JPanel topRowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel bottomRowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        comboBoxTimeRange = new JComboBox<>(new String[]{"1 Week", "1 Month", "Custom"});
        dateChooserStart = new JDateChooser();
        dateChooserEnd = new JDateChooser();
     // Đặt kích thước mới cho JDateChooser
        Dimension preferredSize = new Dimension(150, 30);
        dateChooserStart.setPreferredSize(preferredSize);
        dateChooserEnd.setPreferredSize(preferredSize);
        txtBookId = new JTextField(10);
        txtCategoryId = new JTextField(10);
        txtEmployeeId = new JTextField(10);
        txtCustomerId = new JTextField(10);  // Thêm input mã khách hàng
        btnSearchBook = new JButton("Search Book");
        btnSearchCategory = new JButton("Search Category");
        btnSearchEmployee = new JButton("Search Employee");
        btnSearchCustomer = new JButton("Search Customer");  // Thêm nút tìm kiếm theo khách hàng

        dateChooserStart.setEnabled(false);
        dateChooserEnd.setEnabled(false);

        topRowPanel.add(new JLabel("Time Range:"));
        topRowPanel.add(comboBoxTimeRange);
        topRowPanel.add(new JLabel("Start Date:"));
        topRowPanel.add(dateChooserStart);
        topRowPanel.add(new JLabel("End Date:"));
        topRowPanel.add(dateChooserEnd);

        bottomRowPanel.add(new JLabel("Book ID:"));
        bottomRowPanel.add(txtBookId);
        bottomRowPanel.add(btnSearchBook);
        bottomRowPanel.add(new JLabel("Category ID:"));
        bottomRowPanel.add(txtCategoryId);
        bottomRowPanel.add(btnSearchCategory);
        bottomRowPanel.add(new JLabel("Employee ID:"));
        bottomRowPanel.add(txtEmployeeId);
        bottomRowPanel.add(btnSearchEmployee);
        bottomRowPanel.add(new JLabel("Customer ID:"));
        bottomRowPanel.add(txtCustomerId);
        bottomRowPanel.add(btnSearchCustomer);  // Thêm nút tìm kiếm theo khách hàng

        panel.add(topRowPanel);
        panel.add(bottomRowPanel);

        comboBoxTimeRange.addActionListener(this);
        btnSearchBook.addActionListener(this);
        btnSearchCategory.addActionListener(this);
        btnSearchEmployee.addActionListener(this);
        btnSearchCustomer.addActionListener(this);  // Thêm lắng nghe sự kiện cho nút tìm kiếm theo khách hàng

        return panel;
    }

    private JPanel createResultPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Tạo mô hình cho JTable
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Order ID");
        tableModel.addColumn("Order Date");
        tableModel.addColumn("Employee ID");
        tableModel.addColumn("Customer ID");
        tableModel.addColumn("Discount");
        tableModel.addColumn("Total Price");

        resultTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(resultTable);

        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source.equals(comboBoxTimeRange)) {
            handleTimeRangeSelection();
        } else if (source.equals(btnSearchBook)) {
            handleSearchButtonClick("book");
        } else if (source.equals(btnSearchCategory)) {
            handleSearchButtonClick("category");
        } else if (source.equals(btnSearchEmployee)) {
            handleSearchButtonClick("employee");
        } else if (source.equals(btnSearchCustomer)) {
            handleSearchButtonClick("customer");
        }
    }

    private void handleTimeRangeSelection() {
        String selectedOption = (String) comboBoxTimeRange.getSelectedItem();
        if (selectedOption.equals("Custom")) {
            dateChooserStart.setEnabled(true);
            dateChooserEnd.setEnabled(true);
        } else {
            dateChooserStart.setEnabled(false);
            dateChooserEnd.setEnabled(false);
        }
    }

    private void handleSearchButtonClick(String searchType) {
        String timeRange = (String) comboBoxTimeRange.getSelectedItem();
        String input = "";

        switch (searchType) {
            case "book":
                input = txtBookId.getText();
                break;
            case "category":
                input = txtCategoryId.getText();
                break;
            case "employee":
                input = txtEmployeeId.getText();
                break;
            case "customer":
                input = txtCustomerId.getText();
                break;
            case "custom":
                // Handle custom search parameters
                break;
        }

        Date startDate = null;
        Date endDate = null;

        if (timeRange.equals("Custom")) {
            // Lấy ngày bắt đầu và kết thúc từ date choosers
            startDate = dateChooserStart.getDate();
            endDate = dateChooserEnd.getDate();
        } else {
            // Lấy ngày bắt đầu là ngày đầu tiên của khoảng thời gian được chọn
            startDate = calculateStartDate(timeRange);
            endDate = new Date();  // Ngày hiện tại
        }

        // Gọi các hàm thống kê tương ứng từ OrderService
        List<Order> orders = null;
        switch (searchType) {
            case "book":
                orders = orderService.getOrdersByBookId(input, startDate, endDate);
                break;
            case "category":
                orders = orderService.getOrdersByCategoryId(input, startDate, endDate);
                break;
            case "employee":
                int employeeId = Integer.parseInt(input);
                orders = orderService.getOrdersByEmployeeId(employeeId, startDate, endDate);
                break;
            case "customer":
                int customerId = Integer.parseInt(input);
                orders = orderService.getOrdersByCustomerId(customerId, startDate, endDate);
                break;
            case "custom":
                // Handle custom search parameters
                break;
        }

        // Hiển thị kết quả trong JTable
        displayResults(orders);
    }

    private Date calculateStartDate(String timeRange) {
        // Tính ngày bắt đầu dựa trên khoảng thời gian được chọn
        // Đây chỉ là một ví dụ, bạn có thể thích ứng dựa trên logic của mình
        // ở đây tôi sử dụng thư viện Apache Commons Lang để tính toán ngày
        Date currentDate = new Date();
        switch (timeRange) {
            case "1 Week":
                return org.apache.commons.lang3.time.DateUtils.addWeeks(currentDate, -1);
            case "1 Month":
                return org.apache.commons.lang3.time.DateUtils.addMonths(currentDate, -1);
            default:
                return currentDate;
        }
    }

    private JPanel createSummaryPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        lblNumberOfOrders = new JLabel("Number of Orders: 0");
        lblTotalRevenue = new JLabel("Total Revenue: " + currencyFormat.format(0.0));

        panel.add(lblNumberOfOrders);
        panel.add(Box.createRigidArea(new Dimension(20, 0)));  // Tạo khoảng cách giữa các thành phần
        panel.add(lblTotalRevenue);

        return panel;
    }

    private void displayResults(List<Order> orders) {
        DefaultTableModel tableModel = (DefaultTableModel) resultTable.getModel();
        tableModel.setRowCount(0);

        int numberOfOrders = 0;
        double totalRevenue = 0.0;

        if (orders != null) {
            for (Order order : orders) {
                numberOfOrders++;
                totalRevenue += order.getTotalPrice();

                Object[] rowData = {
                        order.getOrderID(),
                        order.getOrderDate(),
                        order.getEmployeeID(),
                        order.getCustomerID(),
                        order.getDiscount(),
                        order.getTotalPrice()
                };
                tableModel.addRow(rowData);
            }
        }

        lblNumberOfOrders.setText("Number of Orders: " + numberOfOrders);
        lblTotalRevenue.setText("Total Revenue: " + currencyFormat.format(totalRevenue));
    }
}
