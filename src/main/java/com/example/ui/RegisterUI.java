package com.example.ui;

import com.example.model.User;
import com.example.service.UserService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterUI extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JTextField txtFullname;
    private JTextField txtEmail;
    private JTextField txtPhoneNumber;
    private JTextField txtAddress;
    private JTextField txtGender;
    private JButton btnSubmit;
    private JButton btnBack;
    private UserService userService;

    public RegisterUI() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("Register - Bookstore Management System");
        setSize(600, 400);
        setLocationRelativeTo(null);
        ImageIcon icon = new ImageIcon(getClass().getResource("/icons/logo.png"));
        setIconImage(icon.getImage());
        init();

        userService = new UserService();
    }

    private void init() {
        Font fontBtn = new Font("SansSerif", Font.BOLD, 15);

        // Box layout
        Box box = Box.createVerticalBox();

        // Username box
        Box usernameBox = Box.createHorizontalBox();
        usernameBox.add(Box.createRigidArea(new Dimension(20, 0)));
        usernameBox.add(new JLabel("Username:"));
        usernameBox.add(Box.createRigidArea(new Dimension(70, 0))); // Reduce the gap
        usernameBox.add(txtUsername = new JTextField());
        txtUsername.setPreferredSize(new Dimension(0, 30));
        txtUsername.setToolTipText("Enter your username");
        usernameBox.add(Box.createRigidArea(new Dimension(50, 0)));
        box.add(Box.createVerticalStrut(10));
        box.add(usernameBox);

        // Password box
        Box passwordBox = Box.createHorizontalBox();
        passwordBox.add(Box.createRigidArea(new Dimension(20, 0)));
        passwordBox.add(new JLabel("Password:"));
        passwordBox.add(Box.createRigidArea(new Dimension(70, 0))); // Reduce the gap
        passwordBox.add(txtPassword = new JPasswordField());
        txtPassword.setPreferredSize(new Dimension(0, 30));
        txtPassword.setToolTipText("Enter your password");
        passwordBox.add(Box.createRigidArea(new Dimension(50, 0)));
        box.add(Box.createVerticalStrut(10));
        box.add(passwordBox);

        // Fullname box
        Box fullnameBox = Box.createHorizontalBox();
        fullnameBox.add(Box.createRigidArea(new Dimension(20, 0)));
        fullnameBox.add(new JLabel("Fullname:"));
        fullnameBox.add(Box.createRigidArea(new Dimension(70, 0))); // Reduce the gap
        fullnameBox.add(txtFullname = new JTextField());
        txtFullname.setPreferredSize(new Dimension(0, 30));
        txtFullname.setToolTipText("Enter your fullname");
        fullnameBox.add(Box.createRigidArea(new Dimension(50, 0)));
        box.add(Box.createVerticalStrut(10));
        box.add(fullnameBox);

        // Email box
        Box emailBox = Box.createHorizontalBox();
        emailBox.add(Box.createRigidArea(new Dimension(20, 0)));
        emailBox.add(new JLabel("Email:"));
        emailBox.add(Box.createRigidArea(new Dimension(70, 0))); // Reduce the gap
        emailBox.add(txtEmail = new JTextField());
        txtEmail.setPreferredSize(new Dimension(0, 30));
        txtEmail.setToolTipText("Enter your email");
        emailBox.add(Box.createRigidArea(new Dimension(50, 0)));
        box.add(Box.createVerticalStrut(10));
        box.add(emailBox);

        // PhoneNumber box
        Box phoneNumberBox = Box.createHorizontalBox();
        phoneNumberBox.add(Box.createRigidArea(new Dimension(20, 0)));
        phoneNumberBox.add(new JLabel("Phone Number:"));
        phoneNumberBox.add(Box.createRigidArea(new Dimension(70, 0))); // Reduce the gap
        phoneNumberBox.add(txtPhoneNumber = new JTextField());
        txtPhoneNumber.setPreferredSize(new Dimension(0, 30));
        txtPhoneNumber.setToolTipText("Enter your phone number");
        phoneNumberBox.add(Box.createRigidArea(new Dimension(50, 0)));
        box.add(Box.createVerticalStrut(10));
        box.add(phoneNumberBox);

        // Address box
        Box addressBox = Box.createHorizontalBox();
        addressBox.add(Box.createRigidArea(new Dimension(20, 0)));
        addressBox.add(new JLabel("Address:"));
        addressBox.add(Box.createRigidArea(new Dimension(70, 0))); // Reduce the gap
        addressBox.add(txtAddress = new JTextField());
        txtAddress.setPreferredSize(new Dimension(0, 30));
        txtAddress.setToolTipText("Enter your address");
        addressBox.add(Box.createRigidArea(new Dimension(50, 0)));
        box.add(Box.createVerticalStrut(10));
        box.add(addressBox);

        // Gender box
        Box genderBox = Box.createHorizontalBox();
        genderBox.add(Box.createRigidArea(new Dimension(20, 0)));
        genderBox.add(new JLabel("Gender:"));
        genderBox.add(Box.createRigidArea(new Dimension(70, 0))); // Reduce the gap
        genderBox.add(txtGender = new JTextField());
        txtGender.setPreferredSize(new Dimension(0, 30));
        txtGender.setToolTipText("Enter your gender");
        genderBox.add(Box.createRigidArea(new Dimension(50, 0)));
        box.add(Box.createVerticalStrut(10));
        box.add(genderBox);

        // Empty space
        box.add(Box.createVerticalStrut(20));

        // Submit and Back buttons box
        Box buttonsBox = Box.createHorizontalBox();
        buttonsBox.add(btnSubmit = new JButton("SUBMIT"));
        btnSubmit.setFont(fontBtn);
        btnSubmit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        buttonsBox.add(Box.createRigidArea(new Dimension(20, 0)));
        buttonsBox.add(btnBack = new JButton("BACK"));
        btnBack.setFont(fontBtn);
        btnBack.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        box.add(buttonsBox);

        add(box, BorderLayout.NORTH);

        // Sự kiện cho nút Submit
        btnSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Tạo một đối tượng User mới từ thông tin nhập vào
                User user = new User();
                user.setUsername(txtUsername.getText().trim());
                user.setPassword(new String(txtPassword.getPassword()).trim());
                user.setFullname(txtFullname.getText().trim());
                user.setEmail(txtEmail.getText().trim());
                user.setPhoneNumber(txtPhoneNumber.getText().trim());
                user.setAddress(txtAddress.getText().trim());
                user.setGender(txtGender.getText().trim());
                // Gọi phương thức register() để thêm người dùng mới
                registerUser(user);
            }
        });

        // Sự kiện cho nút Back
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Quay lại giao diện đăng nhập khi nhấn nút Back
                LoginUI loginUI = new LoginUI();
                loginUI.setVisible(true);
                dispose();
            }
        });
    }

    // Phương thức để thêm người dùng mới
    private void registerUser(User user) {
        boolean success = userService.register(user);
        if (success) {
            // Hiển thị thông báo thành công và quay lại giao diện đăng nhập
            JOptionPane.showMessageDialog(this, "Đăng ký thành công!");
            LoginUI loginUI = new LoginUI();
            loginUI.setVisible(true);
            this.dispose();
        } else {
            // Hiển thị thông báo lỗi
            JOptionPane.showMessageDialog(this, "Đăng ký thất bại. Vui lòng thử lại.");
        }
    }
}
