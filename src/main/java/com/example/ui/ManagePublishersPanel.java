package com.example.ui;

import com.example.model.Publisher;
import com.example.service.PublisherService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ManagePublishersPanel extends JPanel {

    private final PublisherService publisherService;

    private DefaultTableModel tableModel;
    private JTable publisherTable;
    private JTextField publisherNameField;
    private JTextField addressField;
    private JTextField phoneNumberField;
    private JTextField emailField;
    private JCheckBox isActiveCheckbox;
    private JButton addButton;
    private JButton updateButton;
    private JButton disableButton;
    private JButton enableButton;
    private JTextField searchField;
    private JButton searchButton;
    private JComboBox<String> sortComboBox;
    private JButton sortButton;

    public ManagePublishersPanel() {
        this.publisherService = new PublisherService();
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout());
        titlePanel.add(new JLabel("Quản lý Nhà xuất bản sách"));

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(BorderFactory.createTitledBorder("Danh sách Nhà xuất bản sách"));

        JPanel searchAndSortPanel = new JPanel();
        searchAndSortPanel.setLayout(new BoxLayout(searchAndSortPanel, BoxLayout.X_AXIS));
        searchAndSortPanel.setPreferredSize(new Dimension(0, 70));

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout());

        JPanel sortPanel = new JPanel();
        sortPanel.setLayout(new FlowLayout());

        searchField = new JTextField(15);
        searchButton = new JButton("Tìm kiếm");
        sortComboBox = new JComboBox<>(new String[]{"A-Z", "Z-A"});
        sortButton = new JButton("Sắp xếp");

        searchPanel.add(new JLabel("Tìm kiếm:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        sortPanel.add(new JLabel("Sắp xếp:"));
        sortPanel.add(sortComboBox);
        sortPanel.add(sortButton);

        searchAndSortPanel.add(searchPanel);
        searchAndSortPanel.add(Box.createHorizontalStrut(20));
        searchAndSortPanel.add(sortPanel);

        centerPanel.add(searchAndSortPanel, BorderLayout.NORTH);

        String[] columnNames = {"Số thứ tự", "ID", "Tên nhà xuất bản", "Địa chỉ", "Số điện thoại", "Email", "Trạng thái"};
        tableModel = new DefaultTableModel(columnNames, 0);
        publisherTable = new JTable(tableModel);
        publisherTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane tableScrollPane = new JScrollPane(publisherTable);
        centerPanel.add(tableScrollPane, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel(new BorderLayout());
        controlPanel.setPreferredSize(new Dimension(0, 250));

        // Panel for input fields (2x3 grid)
        JPanel inputPanel = new JPanel(new GridLayout(2, 3));

        JPanel publisherNamePanel = new JPanel(new FlowLayout());
        JPanel addressPanel = new JPanel(new FlowLayout());
        JPanel phoneNumberPanel = new JPanel(new FlowLayout());
        JPanel emailPanel = new JPanel(new FlowLayout());
        JPanel isActivePanel = new JPanel(new FlowLayout());

        publisherNameField = new JTextField(15);
        addressField = new JTextField(15);
        phoneNumberField = new JTextField(15);
        emailField = new JTextField(15);
        isActiveCheckbox = new JCheckBox("Hoạt động");

        JLabel publisherNameLabel = new JLabel("Tên nhà xuất bản:");
        JLabel addressLabel = new JLabel("Địa chỉ:");
        JLabel phoneNumberLabel = new JLabel("Số điện thoại:");
        JLabel emailLabel = new JLabel("Email:");

        publisherNameLabel.setPreferredSize(phoneNumberLabel.getPreferredSize());
        addressLabel.setPreferredSize(phoneNumberLabel.getPreferredSize());
        emailLabel.setPreferredSize(phoneNumberLabel.getPreferredSize());

        publisherNamePanel.add(publisherNameLabel);
        publisherNamePanel.add(publisherNameField);

        addressPanel.add(addressLabel);
        addressPanel.add(addressField);

        phoneNumberPanel.add(phoneNumberLabel);
        phoneNumberPanel.add(phoneNumberField);

        emailPanel.add(emailLabel);
        emailPanel.add(emailField);

        isActivePanel.add(isActiveCheckbox);

        inputPanel.add(publisherNamePanel);
        inputPanel.add(addressPanel);
        inputPanel.add(phoneNumberPanel);
        inputPanel.add(emailPanel);
        inputPanel.add(isActivePanel);

        // Panel for buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setPreferredSize(new Dimension(0, 150));
        addButton = new JButton("Thêm");
        updateButton = new JButton("Cập nhật");
        disableButton = new JButton("Ẩn");
        enableButton = new JButton("Hiển thị");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(disableButton);
        buttonPanel.add(enableButton);

        // Set action listeners for buttons
        addButton.addActionListener(e -> addPublisher());
        updateButton.addActionListener(e -> updatePublisher());
        disableButton.addActionListener(e -> disablePublisher());
        enableButton.addActionListener(e -> enablePublisher());

        // Add inputPanel to controlPanel
        controlPanel.add(inputPanel, BorderLayout.CENTER);
        controlPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Set action listeners for search and sort buttons
        searchButton.addActionListener(e -> searchPublishers());
        sortButton.addActionListener(e -> sortPublishers());

        // Add selection listener for publisherTable
        publisherTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = publisherTable.getSelectedRow();
            if (selectedRow != -1) {
                populateFields(selectedRow);
            }
        });

        // Add components to the main layout
        add(titlePanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);

        // Load initial data
        loadPublishers();
    }

    private void loadPublishers() {
        List<Publisher> publishers = publisherService.getAllPublishers();
        updateTable(publishers);
        clearFields();
    }

    private void addPublisher() {
        try {
            String publisherName = publisherNameField.getText().trim();
            String address = addressField.getText().trim();
            String phoneNumber = phoneNumberField.getText().trim();
            String email = emailField.getText().trim();
            boolean isActive = isActiveCheckbox.isSelected();

            if (!publisherName.isEmpty() && !address.isEmpty() && !phoneNumber.isEmpty() && !email.isEmpty()) {

                Publisher newPublisher = new Publisher(0, publisherName, address, phoneNumber, email, isActive);

                if (publisherService.addPublisher(newPublisher)) {
                    loadPublishers();
                } else {
                    JOptionPane.showMessageDialog(this, "Không thể thêm nhà xuất bản sách.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin nhà xuất bản sách.", "Lưu ý", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi khi thêm nhà xuất bản sách.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updatePublisher() {
        Publisher selectedPublisher = getSelectedPublisher();
        if (selectedPublisher != null) {
            try {
                String newPublisherName = publisherNameField.getText().trim();
                String newAddress = addressField.getText().trim();
                String newPhoneNumber = phoneNumberField.getText().trim();
                String newEmail = emailField.getText().trim();
                boolean newIsActive = isActiveCheckbox.isSelected();

                if (!newPublisherName.isEmpty() && !newAddress.isEmpty() && !newPhoneNumber.isEmpty() && !newEmail.isEmpty()) {

                    selectedPublisher.setPublisherName(newPublisherName);
                    selectedPublisher.setAddress(newAddress);
                    selectedPublisher.setPhoneNumber(newPhoneNumber);
                    selectedPublisher.setEmail(newEmail);
                    selectedPublisher.setActive(newIsActive);

                    if (publisherService.updatePublisher(selectedPublisher)) {
                        loadPublishers();
                    } else {
                        JOptionPane.showMessageDialog(this, "Không thể cập nhật nhà xuất bản sách.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin mới cho nhà xuất bản sách.", "Lưu ý", JOptionPane.WARNING_MESSAGE);
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi khi cập nhật nhà xuất bản sách.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một nhà xuất bản sách để cập nhật.", "Lưu ý", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void disablePublisher() {
        Publisher selectedPublisher = getSelectedPublisher();
        if (selectedPublisher != null) {
            selectedPublisher.setActive(false);
            if (publisherService.updatePublisher(selectedPublisher)) {
                loadPublishers();
            } else {
                JOptionPane.showMessageDialog(this, "Không thể ẩn nhà xuất bản sách.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một nhà xuất bản sách để ẩn.", "Lưu ý", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void enablePublisher() {
        Publisher selectedPublisher = getSelectedPublisher();
        if (selectedPublisher != null) {
            selectedPublisher.setActive(true);
            if (publisherService.updatePublisher(selectedPublisher)) {
                loadPublishers();
            } else {
                JOptionPane.showMessageDialog(this, "Không thể hiển thị nhà xuất bản sách.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một nhà xuất bản sách để hiển thị.", "Lưu ý", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void searchPublishers() {
        String keyword = searchField.getText().trim();
        List<Publisher> publishers = publisherService.searchPublishers(keyword);
        updateTable(publishers);
    }

    private void sortPublishers() {
        String sortOrder = (String) sortComboBox.getSelectedItem();
        List<Publisher> publishers = publisherService.sortPublishers(sortOrder);
        updateTable(publishers);
    }

    private void clearFields() {
        publisherNameField.setText("");
        addressField.setText("");
        phoneNumberField.setText("");
        emailField.setText("");
        isActiveCheckbox.setSelected(true);
        searchField.setText("");
        publisherTable.clearSelection();
    }

    private Publisher getSelectedPublisher() {
        int selectedRow = publisherTable.getSelectedRow();
        if (selectedRow != -1) {
            int publisherId = (int) publisherTable.getValueAt(selectedRow, 1);
            return publisherService.getPublisherById(publisherId);
        }
        return null;
    }

    private void updateTable(List<Publisher> publishers) {
        tableModel.setRowCount(0);

        int index = 0;
        for (Publisher publisher : publishers) {
            tableModel.addRow(new Object[]{
                    ++index,
                    publisher.getPublisherID(),
                    publisher.getPublisherName(),
                    publisher.getAddress(),
                    publisher.getPhoneNumber(),
                    publisher.getEmail(),
                    publisher.isActive() ? "Hoạt động" : "Ẩn"
            });
        }
    }

    private void populateFields(int selectedRow) {
        publisherNameField.setText((String) publisherTable.getValueAt(selectedRow, 2));
        addressField.setText((String) publisherTable.getValueAt(selectedRow, 3));
        phoneNumberField.setText((String) publisherTable.getValueAt(selectedRow, 4));
        emailField.setText((String) publisherTable.getValueAt(selectedRow, 5));
        isActiveCheckbox.setSelected("Hoạt động".equals(publisherTable.getValueAt(selectedRow, 6)));
    }
}
