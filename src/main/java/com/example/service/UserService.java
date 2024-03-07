package com.example.service;

import java.util.List;

import com.example.dao.UserDao;
import com.example.model.Role;
import com.example.model.User;

public class UserService {
    private UserDao userDao;

    public UserService() {
        this.userDao = new UserDao();
    }

    // Thêm một người dùng mới
    public boolean addUser(User user) {
        return userDao.addUser(user);
    }

    // Cập nhật thông tin người dùng
    public boolean updateUser(User user) {
        return userDao.updateUser(user);
    }

    // Kích hoạt một người dùng
    public boolean activateUser(int userID) {
        return userDao.activateUser(userID);
    }

    // Vô hiệu hóa một người dùng
    public boolean deactivateUser(int userID) {
        return userDao.deactivateUser(userID);
    }

    // Lấy thông tin người dùng bằng ID
    public User getUserById(int userID) {
        return userDao.getUserById(userID);
    }

    // Lấy danh sách tất cả người dùng
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    // Kiểm tra đăng nhập
    public User checkLogin(String username, String password) {
        return userDao.getUserByUsernameAndPassword(username, password);
    }
    
 // Tìm kiếm người dùng theo tên
    public List<User> searchUsers(String keyword) {
        return userDao.searchUsersByFullName(keyword);
    }

    public List<User> sortUsers(String sortOrder) {
        return userDao.sortUsers(sortOrder);
    }
    
 // Đổi mật khẩu người dùng theo ID và kiểm tra mật khẩu gốc
    public boolean changePassword(int userID, String oldPassword, String newPassword) {
        return userDao.changePassword(userID, oldPassword, newPassword);
    }
}
