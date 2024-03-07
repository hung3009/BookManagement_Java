package com.example.ui;

import com.example.model.Customer;
import com.example.service.CustomerService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ManageCustomersPanel extends JPanel {

    private final CustomerService customerService;

    private DefaultTableModel tableModel;
    private JTable customerTable;
    private JTextField customerNameField;
    private JTextField addressField;
    private JTextField contactNumberField;
    private JCheckBox isMemberCheckbox;
    private JButton addButton;
    private JButton updateButton;
    private JButton deactivateButton;
    private JButton activateButton;
    private JTextField searchField;
    private JButton searchButton;

    public ManageCustomersPanel() {
        this.customerService = new CustomerService();
        initializeUI();
        loadCustomers();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout());
        titlePanel.add(new JLabel("Quản lý Khách hàng"));
        titlePanel.setPreferredSize(new Dimension(0, 50));

        JPanel contentPanel = new JPanel(new BorderLayout());

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(BorderFactory.createTitledBorder("Danh sách Khách hàng"));

        tableModel = new DefaultTableModel();
        customerTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(customerTable);

        centerPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel(new BorderLayout());

        contentPanel.add(centerPanel, BorderLayout.CENTER);
        contentPanel.add(controlPanel, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new GridLayout(1, 2, 30, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Thông tin Khách hàng"));

        JPanel inputLeftPanel = new JPanel();
        inputLeftPanel.setLayout(new GridLayout(2, 2, 0, 10));
        JPanel inputRightPanel = new JPanel();
        inputRightPanel.setLayout(new GridLayout(2, 2, 0, 10));

        inputPanel.add(inputLeftPanel);
        inputPanel.add(inputRightPanel);

        customerNameField = new JTextField(15);
        addressField = new JTextField(15);
        contactNumberField = new JTextField(15);
        isMemberCheckbox = new JCheckBox("Là thành viên");

        JLabel customerNameLabel = new JLabel("Tên Khách hàng:");
        JLabel addressLabel = new JLabel("Địa chỉ:");
        JLabel contactNumberLabel = new JLabel("Số điện thoại:");
        JLabel isMemberLabel = new JLabel("Thành viên");

        inputLeftPanel.add(customerNameLabel);
        inputLeftPanel.add(customerNameField);
        inputLeftPanel.add(addressLabel);
        inputLeftPanel.add(addressField);

        inputRightPanel.add(contactNumberLabel);
        inputRightPanel.add(contactNumberField);
        inputRightPanel.add(isMemberLabel);
        inputRightPanel.add(isMemberCheckbox);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBorder(BorderFactory.createTitledBorder("Chức năng"));
        addButton = new JButton("Thêm");
        updateButton = new JButton("Cập nhật");
        deactivateButton = new JButton("Deactivate");
        activateButton = new JButton("Activate");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deactivateButton);
        buttonPanel.add(activateButton);

        addButton.addActionListener(e -> addCustomer());
        updateButton.addActionListener(e -> updateCustomer());
        deactivateButton.addActionListener(e -> deactivateCustomer());
        activateButton.addActionListener(e -> activateCustomer());

        controlPanel.add(inputPanel, BorderLayout.CENTER);
        controlPanel.add(buttonPanel, BorderLayout.SOUTH);

        searchField = new JTextField(15);
        searchButton = new JButton("Tìm kiếm");
        searchButton.addActionListener(e -> searchCustomers());

        add(titlePanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
        
        
     // Add a selection listener to the table
        customerTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = customerTable.getSelectedRow();
            if (selectedRow != -1) {
                fillFieldsFromSelectedRow(selectedRow);
            }
        });
    }

    private void loadCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        updateTable(customers);
    }

    private void addCustomer() {
        String customerName = customerNameField.getText().trim();
        String address = addressField.getText().trim();
        String contactNumber = contactNumberField.getText().trim();
        boolean isMember = isMemberCheckbox.isSelected();

        if (!customerName.isEmpty() && !address.isEmpty() && !contactNumber.isEmpty()) {
            Customer newCustomer = new Customer(0, customerName, address, contactNumber, isMember);

            if (customerService.addCustomer(newCustomer)) {
                loadCustomers();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Không thể thêm khách hàng.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin khách hàng.", "Lưu ý", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void updateCustomer() {
        Customer selectedCustomer = getSelectedCustomer();

        if (selectedCustomer != null) {
            String newCustomerName = customerNameField.getText().trim();
            String newAddress = addressField.getText().trim();
            String newContactNumber = contactNumberField.getText().trim();
            boolean newIsMember = isMemberCheckbox.isSelected();

            if (!newCustomerName.isEmpty() && !newAddress.isEmpty() && !newContactNumber.isEmpty()) {
                Customer updatedCustomer = new Customer(selectedCustomer.getCustomerID(), newCustomerName, newAddress, newContactNumber, newIsMember);

                if (customerService.updateCustomer(updatedCustomer)) {
                    loadCustomers();
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(this, "Không thể cập nhật khách hàng.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin khách hàng.", "Lưu ý", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một khách hàng để cập nhật.", "Lưu ý", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void deactivateCustomer() {
        Customer selectedCustomer = getSelectedCustomer();

        if (selectedCustomer != null) {
            int option = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn Deactivate khách hàng này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                if (customerService.deactivateCustomer(selectedCustomer.getCustomerID())) {
                    loadCustomers();
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(this, "Không thể Deactivate khách hàng.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một khách hàng để Deactivate.", "Lưu ý", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void activateCustomer() {
        Customer selectedCustomer = getSelectedCustomer();

        if (selectedCustomer != null) {
            int option = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn Activate khách hàng này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                if (customerService.activateCustomer(selectedCustomer.getCustomerID())) {
                    loadCustomers();
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(this, "Không thể Activate khách hàng.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một khách hàng để Activate.", "Lưu ý", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void searchCustomers() {
        String searchTerm = searchField.getText().trim();
        if (!searchTerm.isEmpty()) {
            List<Customer> searchResults = customerService.searchCustomers(searchTerm);
            updateTable(searchResults);
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập từ khóa tìm kiếm.", "Lưu ý", JOptionPane.WARNING_MESSAGE);
        }
    }

    private Customer getSelectedCustomer() {
        int selectedRow = customerTable.getSelectedRow();

        if (selectedRow != -1) {
            int customerId = (int) customerTable.getValueAt(selectedRow, 0);
            String customerName = (String) customerTable.getValueAt(selectedRow, 1);
            String address = (String) customerTable.getValueAt(selectedRow, 2);
            String contactNumber = (String) customerTable.getValueAt(selectedRow, 3);
            boolean isMember = (boolean) customerTable.getValueAt(selectedRow, 4);

            return new Customer(customerId, customerName, address, contactNumber, isMember);
        } else {
            return null;
        }
    }

    private void updateTable(List<Customer> customers) {
        tableModel.setRowCount(0);
        tableModel.setColumnCount(0);

        tableModel.addColumn("ID");
        tableModel.addColumn("Tên Khách hàng");
        tableModel.addColumn("Địa chỉ");
        tableModel.addColumn("Số điện thoại");
        tableModel.addColumn("Thành viên");

        for (Customer customer : customers) {
            Object[] rowData = {
                    customer.getCustomerID(),
                    customer.getCustomerName(),
                    customer.getAddress(),
                    customer.getContactNumber(),
                    customer.isMember()
            };
            tableModel.addRow(rowData);
        }

        if (customerTable != null) {
            customerTable.setModel(tableModel);
        }
    }
    
    private void fillFieldsFromSelectedRow(int selectedRow) {
        String customerName = (String) customerTable.getValueAt(selectedRow, 1);
        String address = (String) customerTable.getValueAt(selectedRow, 2);
        String contactNumber = (String) customerTable.getValueAt(selectedRow, 3);
        boolean isMember = (boolean) customerTable.getValueAt(selectedRow, 4);

        customerNameField.setText(customerName);
        addressField.setText(address);
        contactNumberField.setText(contactNumber);
        isMemberCheckbox.setSelected(isMember);
    }


    private void clearFields() {
        customerNameField.setText("");
        addressField.setText("");
        contactNumberField.setText("");
        isMemberCheckbox.setSelected(false);
    }
}
