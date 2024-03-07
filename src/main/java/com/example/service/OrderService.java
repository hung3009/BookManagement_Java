package com.example.service;

import com.example.dao.OrderDao;
import com.example.model.Order;
import com.example.model.OrderDetail;
import java.util.Date;
import java.util.List;

public class OrderService {
    private OrderDao orderDao;

    public OrderService() {
        this.orderDao = new OrderDao();
    }

    public List<Order> getOrdersByBookId(String bookId, Date startDate, Date endDate) {
        try {
            return orderDao.getOrdersByBookId(bookId, startDate, endDate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Order> getOrdersByCategoryId(String categoryId, Date startDate, Date endDate) {
        try {
            return orderDao.getOrdersByCategoryId(categoryId, startDate, endDate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Order> getOrdersByEmployeeId(int employeeId, Date startDate, Date endDate) {
        try {
            return orderDao.getOrdersByEmployeeId(employeeId, startDate, endDate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Order> getOrdersByCustomerId(int customerId, Date startDate, Date endDate) {
        try {
            return orderDao.getOrdersByCustomerId(customerId, startDate, endDate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean saveOrder(Order order, List<OrderDetail> orderDetails) {
        try {
            return orderDao.saveOrderWithDetails(order, orderDetails);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Hàm lấy toàn bộ danh sách phiếu đặt sách
    public List<Order> getAllOrders() {
        try {
            return orderDao.getAllOrders();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Hàm lấy danh sách phiếu đặt sách trong một khoảng thời gian bất kỳ
    public List<Order> getOrdersInDateRange(Date startDate, Date endDate) {
        try {
            return orderDao.getOrdersInDateRange(startDate, endDate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
