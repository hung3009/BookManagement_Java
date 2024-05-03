package com.example.ui;

import com.example.model.Author;
import com.example.service.AuthorService;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.regex.Pattern;

public class ManageAuthorsPanel extends JPanel {

    private final AuthorService authorService;

    private DefaultTableModel tableModel;
    private JTable authorTable;
    private JTextField authorNameField;
    private JTextField emailField;
    private JTextField phoneNumberField;
    private JDateChooser birthDateChooser;
    private JTextField genderField;
    private JTextField hometownField;
    private JButton addButton;
    private JButton updateButton;
    private JButton hideButton;
    private JButton showButton;
    private JTextField searchField;
    private JButton searchButton;
    private JComboBox<String> sortComboBox;
    private JButton sortButton;

    public ManageAuthorsPanel() {
        this.authorService = new AuthorService();
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout());
        titlePanel.add(new JLabel("Quản lý Tác giả sách"));

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(BorderFactory.createTitledBorder("Danh sách Tác giả sách"));

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

        String[] columnNames = {"Số thứ tự", "ID", "Tên tác giả", "Email", "Số điện thoại", "Ngày sinh", "Giới tính", "Quê quán", "Trạng thái"};
        tableModel = new DefaultTableModel(columnNames, 0);
        authorTable = new JTable(tableModel);
        authorTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane tableScrollPane = new JScrollPane(authorTable);
        centerPanel.add(tableScrollPane, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel(new BorderLayout());
        controlPanel.setPreferredSize(new Dimension(0, 250));

        // Panel for input fields (2x3 grid)
        JPanel inputPanel = new JPanel(new GridLayout(2, 3));

        JPanel authorNamePanel = new JPanel(new FlowLayout());
        JPanel emailPanel = new JPanel(new FlowLayout());
        JPanel phoneNumberPanel = new JPanel(new FlowLayout());
        JPanel birthDatePanel = new JPanel(new FlowLayout());
        JPanel genderPanel = new JPanel(new FlowLayout());
        JPanel hometownPanel = new JPanel(new FlowLayout());

        authorNameField = new JTextField(15);
        emailField = new JTextField(15);
        phoneNumberField = new JTextField(15);
        birthDateChooser = new JDateChooser();
        birthDateChooser.setDateFormatString("yyyy-MM-dd");
        birthDateChooser.setPreferredSize(authorNameField.getPreferredSize());
        genderField = new JTextField(15);
        hometownField = new JTextField(15);

        JLabel authorNameLabel = new JLabel("Tên tác giả:");
        JLabel emailLabel = new JLabel("Email:");
        JLabel phoneNumberLabel = new JLabel("Số điện thoại:");
        JLabel birthDateLabel = new JLabel("Ngày sinh:");
        JLabel genderLabel = new JLabel("Giới tính:");
        JLabel hometownLabel = new JLabel("Quê quán:");

        authorNameLabel.setPreferredSize(phoneNumberLabel.getPreferredSize());
        emailLabel.setPreferredSize(phoneNumberLabel.getPreferredSize());
        birthDateLabel.setPreferredSize(phoneNumberLabel.getPreferredSize());
        genderLabel.setPreferredSize(phoneNumberLabel.getPreferredSize());
        hometownLabel.setPreferredSize(phoneNumberLabel.getPreferredSize());

        authorNamePanel.add(authorNameLabel);
        authorNamePanel.add(authorNameField);

        emailPanel.add(emailLabel);
        emailPanel.add(emailField);

        phoneNumberPanel.add(phoneNumberLabel);
        phoneNumberPanel.add(phoneNumberField);

        birthDatePanel.add(birthDateLabel);
        birthDatePanel.add(birthDateChooser);

        genderPanel.add(genderLabel);
        genderPanel.add(genderField);

        hometownPanel.add(hometownLabel);
        hometownPanel.add(hometownField);

        inputPanel.add(authorNamePanel);
        inputPanel.add(emailPanel);
        inputPanel.add(phoneNumberPanel);
        inputPanel.add(birthDatePanel);
        inputPanel.add(genderPanel);
        inputPanel.add(hometownPanel);

        // Panel for buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setPreferredSize(new Dimension(0, 150));
        addButton = new JButton("Thêm");
        updateButton = new JButton("Cập nhật");
        hideButton = new JButton("Ẩn");
        showButton = new JButton("Hiển thị");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(hideButton);
        buttonPanel.add(showButton);

        // Set action listeners for buttons
        addButton.addActionListener(e -> addAuthor());
        updateButton.addActionListener(e -> updateAuthor());
        hideButton.addActionListener(e -> hideAuthor());
        showButton.addActionListener(e -> showAuthor());

        // Add inputPanel to controlPanel
        controlPanel.add(inputPanel, BorderLayout.CENTER);
        controlPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Set action listeners for search and sort buttons
        searchButton.addActionListener(e -> searchAuthors());
        sortButton.addActionListener(e -> sortAuthors());

        // Add selection listener for authorTable
        authorTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = authorTable.getSelectedRow();
            if (selectedRow != -1) {
                populateFields(selectedRow);
            }
        });

        // Add components to the main layout
        add(titlePanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);

        // Load initial data
        loadAuthors();
    }

    private void loadAuthors() {
        List<Author> authors = authorService.getAllAuthors();
        updateTable(authors);
        clearFields();
    }


    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pat = Pattern.compile(emailRegex);
        return pat.matcher(email).matches();
    }


    private void addAuthor() {
        try {
            String authorName = authorNameField.getText().trim();
            String email = emailField.getText().trim();
            String phoneNumber = phoneNumberField.getText().trim();
            java.util.Date birthDate = birthDateChooser.getDate(); // Lấy giá trị từ JDateChooser
            String gender = genderField.getText().trim();
            String hometown = hometownField.getText().trim();

            if (!authorName.isEmpty() && !email.isEmpty() && !phoneNumber.isEmpty() && birthDate != null
                    && !gender.isEmpty() && !hometown.isEmpty()) {

                //kiểm tra  dịnh dạng email có đúng ko
                if (!isValidEmail(email)) {
                    JOptionPane.showMessageDialog(this, "Email is not in valid format.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Author newAuthor = new Author(0, authorName, email, phoneNumber, birthDate, gender, hometown, true);

                if (authorService.addAuthor(newAuthor)) {
                    loadAuthors();
                } else {
                    JOptionPane.showMessageDialog(this, "Không thể thêm tác giả sách.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin tác giả sách.", "Lưu ý", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Định dạng ngày sinh không hợp lệ.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateAuthor() {
        Author selectedAuthor = getSelectedAuthor();
        if (selectedAuthor != null) {
            try {
                String newName = authorNameField.getText().trim();
                String newEmail = emailField.getText().trim();
                String newPhoneNumber = phoneNumberField.getText().trim();
                java.util.Date newBirthDate = birthDateChooser.getDate(); // Lấy giá trị từ JDateChooser
                String newGender = genderField.getText().trim();
                String newHometown = hometownField.getText().trim();

                if (!newName.isEmpty() && !newEmail.isEmpty() && !newPhoneNumber.isEmpty() && newBirthDate != null
                        && !newGender.isEmpty() && !newHometown.isEmpty()) {

                    //kiểm tra  dịnh dạng email có đúng ko
                    if (!isValidEmail(newEmail)) {
                        JOptionPane.showMessageDialog(this, "Email is not in valid format.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    selectedAuthor.setAuthorName(newName);
                    selectedAuthor.setEmail(newEmail);
                    selectedAuthor.setPhoneNumber(newPhoneNumber);
                    selectedAuthor.setBirthDate(newBirthDate);
                    selectedAuthor.setGender(newGender);
                    selectedAuthor.setHometown(newHometown);

                    if (authorService.updateAuthor(selectedAuthor)) {
                        loadAuthors();
                    } else {
                        JOptionPane.showMessageDialog(this, "Không thể cập nhật tác giả sách.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin mới cho tác giả sách.", "Lưu ý", JOptionPane.WARNING_MESSAGE);
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Định dạng ngày sinh không hợp lệ.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một tác giả sách để cập nhật.", "Lưu ý", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void hideAuthor() {
        Author selectedAuthor = getSelectedAuthor();
        if (selectedAuthor != null) {
            selectedAuthor.setActive(false);
            if (authorService.updateAuthor(selectedAuthor)) {
                loadAuthors();
            } else {
                JOptionPane.showMessageDialog(this, "Không thể ẩn tác giả sách.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một tác giả sách để ẩn.", "Lưu ý", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void showAuthor() {
        Author selectedAuthor = getSelectedAuthor();
        if (selectedAuthor != null) {
            selectedAuthor.setActive(true);
            if (authorService.updateAuthor(selectedAuthor)) {
                loadAuthors();
            } else {
                JOptionPane.showMessageDialog(this, "Không thể hiển thị tác giả sách.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một tác giả sách để hiển thị.", "Lưu ý", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void searchAuthors() {
        String keyword = searchField.getText().trim();
        List<Author> authors = authorService.searchAuthors(keyword);
        updateTable(authors);
    }

    private void sortAuthors() {
        String sortOrder = (String) sortComboBox.getSelectedItem();
        List<Author> authors = authorService.sortAuthors(sortOrder);
        updateTable(authors);
    }

    private void clearFields() {
        authorNameField.setText("");
        emailField.setText("");
        phoneNumberField.setText("");
        birthDateChooser.setDate(null); // Đặt giá trị cho JDateChooser là null
        genderField.setText("");
        hometownField.setText("");
        searchField.setText("");
        authorTable.clearSelection();
    }

    private Author getSelectedAuthor() {
        int selectedRow = authorTable.getSelectedRow();
        if (selectedRow != -1) {
            int authorId = (int) authorTable.getValueAt(selectedRow, 1);
            return authorService.getAuthorById(authorId);
        }
        return null;
    }

    private void updateTable(List<Author> authors) {
        tableModel.setRowCount(0);

        int index = 0;
        for (Author author : authors) {
            tableModel.addRow(new Object[]{
                    ++index,
                    author.getAuthorID(),
                    author.getAuthorName(),
                    author.getEmail(),
                    author.getPhoneNumber(),
                    author.getBirthDate(),
                    author.getGender(),
                    author.getHometown(),
                    author.isActive() ? "Hoạt động" : "Ẩn"
            });
        }
    }

    private void populateFields(int selectedRow) {
        authorNameField.setText((String) authorTable.getValueAt(selectedRow, 2));
        emailField.setText((String) authorTable.getValueAt(selectedRow, 3));
        phoneNumberField.setText((String) authorTable.getValueAt(selectedRow, 4));

        java.util.Date birthDate = (java.util.Date) authorTable.getValueAt(selectedRow, 5);
        birthDateChooser.setDate(birthDate); // Set ngày cho JDateChooser

        genderField.setText((String) authorTable.getValueAt(selectedRow, 6));
        hometownField.setText((String) authorTable.getValueAt(selectedRow, 7));
    }
}
