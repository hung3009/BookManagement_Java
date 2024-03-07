package com.example.ui;

import javax.swing.*;

import com.example.model.User;

import java.awt.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainUI extends JFrame {

    private JPanel contentPanel;
    private JPanel centerOfContentPanel;
    private JPanel currentPanel;
    private JPanel menuPanel;
    private JPanel topPanel;

    private ImageIcon[] adminMenuIcons = {
            createScaledImageIcon("/img/user.png", 35, 35),
            createScaledImageIcon("/img/statistics.png", 35, 35),
            createScaledImageIcon("/img/change-password.png", 35, 35),  // Thêm icon cho menu "Cài đặt"
            createScaledImageIcon("/img/logout.png", 35, 35)
    };

    private ImageIcon[] employeeMenuIcons = {
            createScaledImageIcon("/img/book.png", 35, 35),
            createScaledImageIcon("/img/author.png", 35, 35),
            createScaledImageIcon("/img/publisher.png", 35, 35),
            createScaledImageIcon("/img/order.png", 35, 35),
            createScaledImageIcon("/img/receipt.png", 35, 35),
            createScaledImageIcon("/img/category.png", 35, 35),
            createScaledImageIcon("/img/customer.png", 35, 35),
            createScaledImageIcon("/img/book-receipt.png", 35, 35),  // Icon cho Phiếu nhập sách
            createScaledImageIcon("/img/book-order.png", 35, 35),    // Icon cho Phiếu đặt sách
            createScaledImageIcon("/img/change-password.png", 35, 35),  // Thêm icon cho menu "Cài đặt"
            createScaledImageIcon("/img/logout.png", 35, 35)
    };

    private User user;

    public MainUI(User user) {
        this.user = user;
        initializeUI();
        addTopPanel();
    }

    private void initializeUI() {
        setTitle("Bookstore Management System");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize);
     // Set cửa sổ mở rộng sang full màn hình
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        add(contentPanel);

        centerOfContentPanel = new JPanel();
        centerOfContentPanel.setLayout(new BorderLayout());
        contentPanel.add(centerOfContentPanel, BorderLayout.CENTER);

        currentPanel = new JPanel();
        currentPanel.setLayout(new BorderLayout());
        centerOfContentPanel.add(currentPanel, BorderLayout.CENTER);

        menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setPreferredSize(new Dimension(200, getHeight()));
        menuPanel.setBackground(new Color(153, 255, 255));
        contentPanel.add(menuPanel, BorderLayout.WEST);

        topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        topPanel.setBackground(new Color(136, 136, 136)); // Màu cho header panel
        centerOfContentPanel.add(topPanel, BorderLayout.NORTH);

        // Thêm menu dựa vào roleID
        addMenuBasedOnRole();

        setLocationRelativeTo(null);
        setVisible(true);
        
     // Gọi hàm để thiết lập hình ảnh nền
        setBackgroundImage();
    }
    
    private void setBackgroundImage() {
        // Lấy kích thước hiện tại của currentPanel
        Dimension panelSize = currentPanel.getSize();

        // Kiểm tra nếu chiều rộng hoặc chiều cao là 0, gán giá trị mặc định
        int width = Math.max(panelSize.width, 1);
        int height = Math.max(panelSize.height, 1);

        // Thêm hình ảnh nền vào panel
        ImageIcon backgroundImage = createScaledImageIcon("/img/homeView.jpg", width, height);
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setHorizontalAlignment(JLabel.CENTER);
        backgroundLabel.setVerticalAlignment(JLabel.CENTER);
        backgroundLabel.setLayout(new BorderLayout());

        currentPanel.add(backgroundLabel, BorderLayout.CENTER);
    }

    private void addTopPanel() {
        JLabel welcomeLabel = new JLabel("Xin chào, " + user.getFullname());
        welcomeLabel.setForeground(Color.WHITE); // Màu chữ cho header panel
        topPanel.add(welcomeLabel);

        JLabel dateLabel = new JLabel(getCurrentDate());
        dateLabel.setForeground(Color.WHITE); // Màu chữ cho header panel
        topPanel.add(dateLabel);
    }

    private void addMenuBasedOnRole() {
        int roleID = user.getRoleID();

        if (roleID == 1) {
            addAdminMenus();
        } else if (roleID == 2) {
            addEmployeeMenus();
        }
    }

    private void addAdminMenus() {
        String[] menusToAdd = {"Quản lý Người dùng", "Thống kê", "Đổi mật khẩu", "Đăng xuất"};  // Thêm menu "Cài đặt"

        for (int i = 0; i < menusToAdd.length; i++) {
            JLabel menuLabel = new JLabel(menusToAdd[i]);
            menuLabel.setPreferredSize(new Dimension(150, 60));
            menuLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            menuLabel.addMouseListener(new MenuMouseListener(menusToAdd[i]));
            menuLabel.setBackground(new Color(153, 255, 255));
            menuLabel.setOpaque(true);
            menuLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            menuLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

            if (i < adminMenuIcons.length) {
                menuLabel.setIcon(adminMenuIcons[i]);
            }

            menuPanel.add(menuLabel);
        }
    }

    private void addEmployeeMenus() {
        String[] menusToAdd = {
            "Quản lý Sách",
            "Quản lý Tác giả",
            "Quản lý Nhà xuất bản",
            "Đặt sách",
            "Nhập sách",
            "Danh mục sách",
            "Quản lý Khách hàng",
            "Phiếu nhập sách",
            "Phiếu đặt sách",
            "Đổi mật khẩu",
            "Đăng xuất"
        };

        for (int i = 0; i < menusToAdd.length; i++) {
            JLabel menuLabel = new JLabel(menusToAdd[i]);
            menuLabel.setPreferredSize(new Dimension(150, 60));
            menuLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            menuLabel.addMouseListener(new MenuMouseListener(menusToAdd[i]));
            menuLabel.setBackground(new Color(153, 255, 255));
            menuLabel.setOpaque(true);
            menuLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            menuLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

            if (i < employeeMenuIcons.length) {
                menuLabel.setIcon(employeeMenuIcons[i]);
            }

            menuPanel.add(menuLabel);
        }
    }

    private ImageIcon createScaledImageIcon(String path, int width, int height) {
        ImageIcon icon;

        URL resource = getClass().getResource(path);
        if (resource != null) {
            icon = new ImageIcon(resource);
        } else {
            icon = new ImageIcon(path);
        }

        Image image = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(image);
    }

    private class MenuMouseListener extends java.awt.event.MouseAdapter {
        private String menuName;

        public MenuMouseListener(String menuName) {
            this.menuName = menuName;
        }

        @Override
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            if (menuName.equals("Đổi mật khẩu")) {
                handleChangePassword();
            } else {
                switchPanel(menuName);
            }
        }
    }
    
    private void handleChangePassword() {
        // Mở hộp thoại đổi mật khẩu
        ChangePasswordDialog changePasswordDialog = new ChangePasswordDialog(this, user.getUserID());
        changePasswordDialog.setVisible(true);
    }

    private void switchPanel(String panelName) {
        if (currentPanel != null) {
        	centerOfContentPanel.remove(currentPanel);
        }

        switch (panelName) {
            case "Quản lý Sách":
                currentPanel = new ManageBooksPanel();
                break;
            case "Quản lý Tác giả":
                currentPanel = new ManageAuthorsPanel();
                break;
            case "Quản lý Nhà xuất bản":
                currentPanel = new ManagePublishersPanel();
                break;
            case "Đặt sách":
                currentPanel = new ManageOrdersPanel(user.getUserID());
                break;
            case "Nhập sách":
                currentPanel = new ManageBookReceiptsPanel(user.getUserID());
                break;
            case "Danh mục sách":
                currentPanel = new ManageCategoriesPanel();
                break;
            case "Quản lý Khách hàng":
                currentPanel = new ManageCustomersPanel();
                break;
            case "Quản lý Người dùng":
                currentPanel = new ManageUsersPanel();
                break;
            case "Thống kê":
                currentPanel = new ManageStatisticsPanel();
                break;
            case "Phiếu nhập sách":
                currentPanel = new BookReceiptsPanel(user.getUserID());
                break;
            case "Phiếu đặt sách":
                currentPanel = new BookOrdersPanel();
                break;
            case "Đăng xuất":
                handleLogout();
                break;
            default:
                currentPanel = new JPanel();
        }

        centerOfContentPanel.add(currentPanel, BorderLayout.CENTER);
        centerOfContentPanel.revalidate();
        centerOfContentPanel.repaint();
    }

    private void handleLogout() {
        int result = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn đăng xuất?", "Đăng xuất", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            dispose(); // Đóng cửa sổ hiện tại
            
            // mở màn hình đăng nhập
            EventQueue.invokeLater(() -> {
                LoginUI view = new LoginUI();
                view.setVisible(true);
            });
        }
    }

    private String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return dateFormat.format(new Date());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            User loggedInUser = new User(1, "admin", "admin", 1, "Admin User", "admin@example.com", "123456789", "123 Main St", "Male", true);
            new MainUI(loggedInUser);
        });
    }
}
