package com.example.dao;

import com.example.model.User;
import com.example.util.MySQLConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;

import javax.swing.*;

public class UserDao {
    // Trong class UserDAO
    public boolean register(User user) {
        String query = "INSERT INTO Users (Username, Password, RoleId, Fullname, Email, PhoneNumber, Address, Gender, IsActive) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = MySQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, user.getUsername());
            // Hash mật khẩu bằng BCrypt trước khi lưu vào cơ sở dữ liệu
            String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
            preparedStatement.setString(2, hashedPassword);
            preparedStatement.setInt(3, 2); // Mặc định là 2
            preparedStatement.setString(4, user.getFullname());
            preparedStatement.setString(5, user.getEmail());
            preparedStatement.setString(6, user.getPhoneNumber());
            preparedStatement.setString(7, user.getAddress());
            preparedStatement.setString(8, user.getGender());
            preparedStatement.setBoolean(9, true); // Mặc định là true

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public User getUserByUsernameAndPassword(String username, String password) {
        User user = null;
        String query = "SELECT * FROM Users WHERE Username = ?";

        try (Connection connection = MySQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String hashedPassword = resultSet.getString("Password");
                if (BCrypt.checkpw(password, hashedPassword)) {
                    boolean isActive = resultSet.getBoolean("isActive");
                    if (isActive) {
                        user = mapResultSetToUser(resultSet);
                    } else {
                        JOptionPane.showMessageDialog(null, "User is not active.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }


    // Xem danh sách người dùng
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM Users";

        try (Connection connection = MySQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                User user = mapResultSetToUser(resultSet);
                users.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    // Thêm một người dùng mới
    public boolean addUser(User user) {
        String query = "INSERT INTO Users (Username, Password, RoleId, Fullname, Email, PhoneNumber, Address, Gender, IsActive) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = MySQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, user.getUsername());
            String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
            preparedStatement.setString(2, hashedPassword);
            preparedStatement.setInt(3, user.getRoleID());
            preparedStatement.setString(4, user.getFullname());
            preparedStatement.setString(5, user.getEmail());
            preparedStatement.setString(6, user.getPhoneNumber());
            preparedStatement.setString(7, user.getAddress());
            preparedStatement.setString(8, user.getGender());
            preparedStatement.setBoolean(9, user.isActive());

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Cập nhật thông tin người dùng
    public boolean updateUser(User user) {
        String query = "UPDATE Users SET Username = ?, Password = ?, RoleId = ?, Fullname = ?, Email = ?, " +
                       "PhoneNumber = ?, Address = ?, Gender = ?, IsActive = ? WHERE UserID = ?";

        try (Connection connection = MySQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setInt(3, user.getRoleID());
            preparedStatement.setString(4, user.getFullname());
            preparedStatement.setString(5, user.getEmail());
            preparedStatement.setString(6, user.getPhoneNumber());
            preparedStatement.setString(7, user.getAddress());
            preparedStatement.setString(8, user.getGender());
            preparedStatement.setBoolean(9, user.isActive());
            preparedStatement.setInt(10, user.getUserID());

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Tắt/ẩn người dùng
    public boolean deactivateUser(int userID) {
        return updateUserStatus(userID, false);
    }

    // Bật/hiển thị người dùng
    public boolean activateUser(int userID) {
        return updateUserStatus(userID, true);
    }

    private boolean updateUserStatus(int userID, boolean isActive) {
        String query = "UPDATE Users SET IsActive = ? WHERE UserID = ?";

        try (Connection connection = MySQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setBoolean(1, isActive);
            preparedStatement.setInt(2, userID);

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public User getUserById(int userID) {
        User user = null;
        String query = "SELECT * FROM Users WHERE UserID = ?";

        try (Connection connection = MySQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, userID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                user = mapResultSetToUser(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }
    
 // Tìm kiếm người dùng theo tên
    public List<User> searchUsersByFullName(String keyword) {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM Users WHERE Fullname LIKE ?";

        try (Connection connection = MySQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, "%" + keyword + "%");
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    User user = mapResultSetToUser(resultSet);
                    users.add(user);
                }
            }

        } catch (SQLException e) {
            // Handle the exception appropriately, log it, or throw a custom exception
            e.printStackTrace();
        }

        return users;
    }


 // Inside UserDao class
    public List<User> sortUsers(String sortOrder) {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM Users ORDER BY Username ";

        if ("Z-A".equals(sortOrder)) {
            query += "DESC";
        }

        try (Connection connection = MySQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                User user = mapResultSetToUser(resultSet);
                users.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }
    
 // Đổi mật khẩu người dùng theo ID và kiểm tra mật khẩu gốc
    public boolean changePassword(int userID, String oldPassword, String newPassword) {
        String query = "SELECT Password FROM Users WHERE UserID = ?";

        try (Connection connection = MySQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, userID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String storedPassword = resultSet.getString("Password");

                // Kiểm tra mật khẩu gốc
                if (BCrypt.checkpw(oldPassword, storedPassword)) {
                    // Nếu mật khẩu gốc đúng, thực hiện đổi mật khẩu mới
                    return updatePassword(userID, BCrypt.hashpw(newPassword, BCrypt.gensalt()));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Mật khẩu gốc không đúng hoặc có lỗi xảy ra
        return false;
    }


    // Hàm cập nhật mật khẩu mới
    private boolean updatePassword(int userID, String newPassword) {
        String query = "UPDATE Users SET Password = ? WHERE UserID = ?";

        try (Connection connection = MySQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, newPassword);
            preparedStatement.setInt(2, userID);

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }



    private User mapResultSetToUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setUserID(resultSet.getInt("UserID"));
        user.setUsername(resultSet.getString("Username"));
        user.setPassword(resultSet.getString("Password"));
        user.setRoleID(resultSet.getInt("RoleId"));
        user.setFullname(resultSet.getString("Fullname"));
        user.setEmail(resultSet.getString("Email"));
        user.setPhoneNumber(resultSet.getString("PhoneNumber"));
        user.setAddress(resultSet.getString("Address"));
        user.setGender(resultSet.getString("Gender"));
        user.setActive(resultSet.getBoolean("IsActive"));
        return user;
    }
}
