package com.example.ui;

import com.example.model.Order;
import com.example.service.OrderService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;
import java.awt.*;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Vector;

public class BookOrdersPanel extends JPanel {
    private JDateChooser startDateChooser;
    private JDateChooser endDateChooser;
    private JButton filterButton;
    private JTable ordersTable;
    private JScrollPane tableScrollPane;

    private OrderService orderService;

    public BookOrdersPanel() {
        this.orderService = new OrderService();
        initializeUI();
        loadOrdersTable();
        setupFilterButtonAction();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        // Panel chứa input và nút lọc
        JPanel filterPanel = new JPanel();
        startDateChooser = new JDateChooser();
        endDateChooser = new JDateChooser();
        filterButton = new JButton("Filter");

        filterPanel.add(new JLabel("Start Date:"));
        filterPanel.add(startDateChooser);
        filterPanel.add(new JLabel("End Date:"));
        filterPanel.add(endDateChooser);
        filterPanel.add(filterButton);

        add(filterPanel, BorderLayout.NORTH);

        // Bảng JTable
        ordersTable = new JTable();
        tableScrollPane = new JScrollPane(ordersTable);
        add(tableScrollPane, BorderLayout.CENTER);
    }

    private void loadOrdersTable() {
        List<Order> orders = orderService.getAllOrders();
        displayOrdersInTable(orders);
    }

    private void setupFilterButtonAction() {
        filterButton.addActionListener(e -> {
            Date startDate = startDateChooser.getDate();
            Date endDate = endDateChooser.getDate();

            if (startDate != null && endDate != null) {
                // Nếu cả hai ngày đều được chọn, lấy danh sách trong khoảng thời gian
                List<Order> orders = orderService.getOrdersInDateRange(startDate, endDate);
                displayOrdersInTable(orders);
            } else {
                // Ngược lại, nếu một trong hai ngày không được chọn, load toàn bộ danh sách
                loadOrdersTable();
            }
        });
    }

    private void displayOrdersInTable(List<Order> orders) {
        // Lấy thông tin cột cho bảng
        String[] columns = {"Order ID", "Order Date", "Employee ID", "Customer ID", "Discount", "Total Price"};

        // Tạo Vector dữ liệu cho bảng
        Vector<Vector<Object>> data = new Vector<>();
        for (Order order : orders) {
            Vector<Object> row = new Vector<>();
            row.add(order.getOrderID());
            row.add(order.getOrderDate());
            row.add(order.getEmployeeID());
            row.add(order.getCustomerID());
            row.add(order.getDiscount());
            row.add(order.getTotalPrice());
            data.add(row);
        }

        // Tạo DefaultTableModel để đặt dữ liệu cho bảng
        DefaultTableModel tableModel = new DefaultTableModel(data, new Vector<>(Arrays.asList(columns))) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho phép chỉnh sửa trực tiếp trong ô
            }
        };

        // Đặt DefaultTableModel cho JTable
        ordersTable.setModel(tableModel);
    }
}
