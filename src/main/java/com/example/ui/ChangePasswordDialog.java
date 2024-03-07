package com.example.ui;

import com.example.service.UserService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class ChangePasswordDialog extends JDialog {
    private JPasswordField currentPasswordField;
    private JPasswordField newPasswordField;
    private JPasswordField confirmPasswordField;
    private UserService userService;

    public ChangePasswordDialog(JFrame parent, int userID) {
        super(parent, "Đổi mật khẩu", true);
        this.userService = new UserService();
        initializeUI();

        JButton changePasswordButton = new JButton("Đổi mật khẩu");
        changePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleChangePassword(userID);
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(changePasswordButton);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(createFormPanel(), BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(parent);
    }

    private void initializeUI() {
        currentPasswordField = new JPasswordField();
        newPasswordField = new JPasswordField();
        confirmPasswordField = new JPasswordField();
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));

        panel.add(new JLabel("Mật khẩu hiện tại:"));
        panel.add(currentPasswordField);
        panel.add(new JLabel("Mật khẩu mới:"));
        panel.add(newPasswordField);
        panel.add(new JLabel("Xác nhận mật khẩu:"));
        panel.add(confirmPasswordField);

        return panel;
    }

    private void handleChangePassword(int userID) {
        // Lấy giá trị từ các trường nhập
        char[] currentPassword = currentPasswordField.getPassword();
        char[] newPassword = newPasswordField.getPassword();
        char[] confirmPassword = confirmPasswordField.getPassword();

        // Kiểm tra xem mật khẩu mới và mật khẩu xác nhận có trùng nhau hay không
        if (!Arrays.equals(newPassword, confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Mật khẩu mới và mật khẩu xác nhận không khớp. Vui lòng thử lại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return; // Không tiếp tục nếu mật khẩu mới và mật khẩu xác nhận không trùng nhau
        }

        // Kiểm tra các điều kiện đổi mật khẩu (ví dụ: mật khẩu mới giống nhau, mật khẩu hiện tại đúng, ...)
        boolean isPasswordValid = true; // Cần thay đổi thành điều kiện kiểm tra thực tế

        if (isPasswordValid) {
            // Gọi đến hàm ở tầng service để thực hiện đổi mật khẩu
            boolean changePasswordResult = userService.changePassword(userID, new String(currentPassword), new String(newPassword));

            if (changePasswordResult) {
                // Đóng hộp thoại sau khi đổi mật khẩu thành công
                dispose();
                JOptionPane.showMessageDialog(this, "Đổi mật khẩu thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Mật khẩu hiện tại không chính xác. Vui lòng thử lại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Mật khẩu không hợp lệ. Vui lòng thử lại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        // Xóa dữ liệu trong các trường nhập
        currentPasswordField.setText("");
        newPasswordField.setText("");
        confirmPasswordField.setText("");
    }
}
