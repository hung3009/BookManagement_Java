package com.example.ui;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.example.model.User;
import com.example.service.UserService;

public class LoginUI extends JFrame {

    private JTextField txtUsername;
    private JButton btnExit;
    private JButton btnLogin;
    private JPasswordField txtPassword;
    private JLabel lblForgotPassword;
    private JLabel lblLogin;
    private JLabel lblUsername;
    private JLabel lblPassword;
    private JLabel lblIcon;
    private JPanel headerPanel;
    private JLabel lblTitle;
    private JCheckBox rememberPassword;
    private JButton btnForgotPassword;
    
    private UserService userService;

    public LoginUI() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("Login - Bookstore Management System");
        setSize(500, 300);
        setLocationRelativeTo(null);
        ImageIcon icon = new ImageIcon(getClass().getResource("/icons/logo.png"));
        setIconImage(icon.getImage());
        init();
        
        userService = new UserService();
    }

    private void init() {
        ImageIcon iconBig = new ImageIcon(getClass().getResource("/icons/open-book.png"));
        ImageIcon iconLogin = new ImageIcon(getClass().getResource("/icons/log-in.png"));
        ImageIcon iconExit = new ImageIcon(getClass().getResource("/icons/button.png"));

        Font font = new Font("SansSerif", Font.BOLD, 30);
        Font fontBtn = new Font("SansSerif", Font.BOLD, 15);
        btnForgotPassword = new JButton("Forgot password");

        // Box layout
        Box box = Box.createVerticalBox();

        // Header box
        Box headerBox = Box.createHorizontalBox();
        headerBox.add(Box.createRigidArea(new Dimension(20, 0)));
        headerBox.add(headerPanel = new JPanel());
        headerPanel.add(Box.createVerticalStrut(100));
        headerPanel.add(lblTitle = new JLabel("  LOGIN"));
        lblTitle.setIcon(iconBig);
        lblTitle.setFont(font);
        lblTitle.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        box.add(headerBox);

        // Username box
        Box usernameBox = Box.createHorizontalBox();
        usernameBox.add(Box.createRigidArea(new Dimension(20, 0)));
        usernameBox.add(lblUsername = new JLabel("Username:"));
        usernameBox.add(Box.createRigidArea(new Dimension(70, 0))); // Reduce the gap
        usernameBox.add(txtUsername = new JTextField());
        txtUsername.setPreferredSize(new Dimension(0, 30));
        txtUsername.setToolTipText("Enter your username");
        txtUsername.setText("ADMIN");
        usernameBox.add(Box.createRigidArea(new Dimension(50, 0)));
        box.add(Box.createVerticalStrut(10));
        box.add(usernameBox);

        // Password box
        Box passwordBox = Box.createHorizontalBox();
        passwordBox.add(Box.createRigidArea(new Dimension(20, 0)));
        passwordBox.add(lblPassword = new JLabel("Password:"));
        passwordBox.add(Box.createRigidArea(new Dimension(70, 0))); // Reduce the gap
        passwordBox.add(txtPassword = new JPasswordField());
        txtPassword.setPreferredSize(new Dimension(0, 30));
        txtPassword.setToolTipText("Enter your password");
        txtPassword.setText("1111");
        passwordBox.add(Box.createRigidArea(new Dimension(50, 0)));
        box.add(Box.createVerticalStrut(10));
        box.add(passwordBox);

        // Empty space
        box.add(Box.createVerticalStrut(20));

        // Login and Exit buttons box
        Box buttonsBox = Box.createHorizontalBox();
        buttonsBox.add(btnLogin = new JButton("LOGIN"));
        buttonsBox.add(Box.createRigidArea(new Dimension(50, 0)));
        btnLogin.setFont(fontBtn);
        btnLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnLogin.setIcon(iconLogin);
        buttonsBox.add(Box.createRigidArea(new Dimension(20, 0)));
        buttonsBox.add(btnExit = new JButton("EXIT"));
        btnExit.setIcon(iconExit);
        btnExit.setFont(fontBtn);
        btnExit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        box.add(buttonsBox);

        add(box, BorderLayout.NORTH);
        
     // Sự kiện cho nút Đăng nhập
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = txtUsername.getText().trim();
                String password = new String(txtPassword.getPassword()).trim();

                // Thực hiện kiểm tra đăng nhập bằng UserService
                login(username, password);
            }
        });
    }
    
 // Phương thức kiểm tra đăng nhập sử dụng UserService
    private void login(String username, String password) {
        // Kiểm tra đăng nhập bằng UserService
        User user = userService.checkLogin(username, password);

        if (user != null) {
            // Đăng nhập thành công, có thể chuyển đến giao diện chính hoặc thực hiện các hành động khác
            MainUI mainUI = new MainUI(user);
            dispose();
        } else {
            // Đăng nhập thất bại, hiển thị thông báo
            JOptionPane.showMessageDialog(this, "Vui lòng kiểm tra lại tên đăng nhập và mật khẩu.", "Thông báo", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            LoginUI view = new LoginUI();
            view.setVisible(true);
        });
    }
}
