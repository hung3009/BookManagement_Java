package com.example.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnector {
    // Thay đổi thông tin kết nối dựa trên cấu hình của bạn
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/dbbook";
    private static final String USER = "root";
    private static final String PASSWORD = "VuHung3009";

    private static Connection connection;

    // Phương thức để mở kết nối đến MySQL
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    // Phương thức để đóng kết nối
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
