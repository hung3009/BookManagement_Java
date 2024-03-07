package com.example.ui;

import com.example.model.Customer;
import com.example.service.CustomerService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CustomerSearchDialog extends JDialog {

    private JTextField searchField;
    private JTable customerTable;
    private JButton selectButton;
    private CustomerService customerService = new CustomerService();

    public CustomerSearchDialog(JFrame parent) {
        super(parent, "Tìm kiếm khách hàng", true);
        initializeUI();
        loadAllCustomers();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        // Components
        searchField = new JTextField(20);
        customerTable = new JTable();
        selectButton = new JButton("Chọn");

        // Panel to hold search components
        JPanel searchPanel = new JPanel(new FlowLayout());
        searchPanel.add(new JLabel("Tìm kiếm: "));
        searchPanel.add(searchField);
        searchPanel.add(new JButton(new AbstractAction("Tìm kiếm") {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateCustomerTable();
            }
        }));

        // Panel to hold table and select button
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        tablePanel.add(new JScrollPane(customerTable), BorderLayout.CENTER);
        tablePanel.add(selectButton, BorderLayout.SOUTH);

        // Add components to the main layout
        add(searchPanel, BorderLayout.NORTH);
        add(tablePanel, BorderLayout.CENTER);

        // Event handling for select button
        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = customerTable.getSelectedRow();
                if (selectedRow != -1) {
                    int customerID = (int) customerTable.getValueAt(selectedRow, 0);
                    String customerName = (String) customerTable.getValueAt(selectedRow, 1);

                    ManageOrdersPanel.customerSelected = new Customer(customerID, customerName, "", "", false);

                    dispose();
                } else {
                    JOptionPane.showMessageDialog(CustomerSearchDialog.this,
                            "Vui lòng chọn khách hàng trước khi nhấn Chọn",
                            "Thông báo",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }

    private void loadAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        updateCustomerTable(customers);
    }

    private void updateCustomerTable() {
        String keyword = searchField.getText();
        List<Customer> customers = customerService.searchCustomers(keyword);
        updateCustomerTable(customers);
    }

    private void updateCustomerTable(List<Customer> customers) {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Tên khách hàng");

        for (Customer customer : customers) {
            Object[] rowData = {customer.getCustomerID(), customer.getCustomerName()};
            model.addRow(rowData);
        }

        customerTable.setModel(model);
    }
}
