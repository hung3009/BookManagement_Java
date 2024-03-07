package com.example.dao;

import com.example.model.Order;
import com.example.model.OrderDetail;
import com.example.util.MySQLConnector;

import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDao {

    public boolean saveOrderWithDetails(Order order, List<OrderDetail> orderDetails) {
        try (Connection connection = MySQLConnector.getConnection()) {
            connection.setAutoCommit(false);

            // Save order information
            if (saveOrderInfo(connection, order)) {
                // Save order details
                if (saveOrderDetails(connection, orderDetails)) {
                    // Update book batch quantity
                    if (updateBookBatchQuantity(connection, orderDetails)) {
                        // Commit transaction if everything is successful
                        connection.commit();
                        return true;
                    }
                }
            }

            // Rollback in case of any error
            connection.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    private boolean saveOrderInfo(Connection connection, Order order) throws SQLException {
        String sql = "INSERT INTO Orders (orderID, orderDate, employeeID, customerID, discount, totalPrice) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, order.getOrderID());
            preparedStatement.setDate(2, new java.sql.Date(order.getOrderDate().getTime()));
            preparedStatement.setInt(3, order.getEmployeeID());

            // Kiểm tra nếu customerID không phải là 0 (hoặc giá trị mặc định của int) thì mới set giá trị cho cột customerID
            if (order.getCustomerID() != 0) {
                preparedStatement.setInt(4, order.getCustomerID());
            } else {
                preparedStatement.setNull(4, java.sql.Types.INTEGER);
            }

            preparedStatement.setDouble(5, order.getDiscount());
            preparedStatement.setInt(6, order.getTotalPrice());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        }
    }


    private boolean saveOrderDetails(Connection connection, List<OrderDetail> orderDetails) throws SQLException {
        String sql = "INSERT INTO OrderDetails (orderID, bookID, lotID, quantity, unitPrice) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            for (OrderDetail orderDetail : orderDetails) {
                preparedStatement.setString(1, orderDetail.getOrderID());
                preparedStatement.setInt(2, orderDetail.getBookID());
                // Không cần set orderDetail.getLotID() và để DB tự động tăng giá trị cho cột lotID
                preparedStatement.setInt(3, orderDetail.getLotID());
                preparedStatement.setInt(4, orderDetail.getQuantity());
                preparedStatement.setDouble(5, orderDetail.getUnitPrice());

                preparedStatement.addBatch();
            }

            int[] rowsAffected = preparedStatement.executeBatch();
            for (int affectedRows : rowsAffected) {
                if (affectedRows <= 0) {
                    return false;
                }
            }

            return true;
        }
    }


    private boolean updateBookBatchQuantity(Connection connection, List<OrderDetail> orderDetails) throws SQLException {
        String sql = "UPDATE BookBatches SET quantityCurrent = quantityCurrent - ? WHERE LotID = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            for (OrderDetail orderDetail : orderDetails) {
                preparedStatement.setInt(1, orderDetail.getQuantity());
                // Không set orderDetail.getLotID() và để DB tự động tăng giá trị cho cột lotID
                // Sử dụng cột LotID để xác định lô sách cần cập nhật
                preparedStatement.setInt(2, orderDetail.getLotID());

                preparedStatement.addBatch();
            }

            int[] rowsAffected = preparedStatement.executeBatch();
            for (int affectedRows : rowsAffected) {
                if (affectedRows <= 0) {
                    return false;
                }
            }

            return true;
        }
    }
    
    public List<Order> getOrdersByBookId(String bookId, Date startDate, Date endDate) {
        List<Order> orders = new ArrayList<>();

        try (Connection connection = MySQLConnector.getConnection()) {
            String sql = "SELECT * FROM Orders o " +
                    "JOIN OrderDetails od ON o.orderID = od.orderID " +
                    "WHERE od.bookID = ? AND o.orderDate BETWEEN ? AND ?";
            
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, bookId);
                preparedStatement.setDate(2, new java.sql.Date(startDate.getTime()));
                preparedStatement.setDate(3, new java.sql.Date(endDate.getTime()));

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Order order = mapOrder(resultSet);
                        orders.add(order);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }

    public List<Order> getOrdersByCategoryId(String categoryId, Date startDate, Date endDate) {
        List<Order> orders = new ArrayList<>();

        try (Connection connection = MySQLConnector.getConnection()) {
            String sql = "SELECT * FROM Orders o " +
                    "JOIN OrderDetails od ON o.orderID = od.orderID " +
                    "JOIN Books b ON od.bookID = b.bookID " +
                    "JOIN BookCategories bc ON b.categoryID = bc.categoryID " +
                    "WHERE b.categoryID = ? AND o.orderDate BETWEEN ? AND ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, categoryId);
                preparedStatement.setDate(2, new java.sql.Date(startDate.getTime()));
                preparedStatement.setDate(3, new java.sql.Date(endDate.getTime()));

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Order order = mapOrder(resultSet);
                        orders.add(order);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }


    public List<Order> getOrdersByEmployeeId(int employeeId, Date startDate, Date endDate) {
        List<Order> orders = new ArrayList<>();

        try (Connection connection = MySQLConnector.getConnection()) {
            String sql = "SELECT * FROM Orders o " +
                    "WHERE o.employeeID = ? AND o.orderDate BETWEEN ? AND ?";
            
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, employeeId);
                preparedStatement.setDate(2, new java.sql.Date(startDate.getTime()));
                preparedStatement.setDate(3, new java.sql.Date(endDate.getTime()));

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Order order = mapOrder(resultSet);
                        orders.add(order);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }

    public List<Order> getOrdersByCustomerId(int customerId, Date startDate, Date endDate) {
        List<Order> orders = new ArrayList<>();

        try (Connection connection = MySQLConnector.getConnection()) {
            String sql = "SELECT * FROM Orders o " +
                    "WHERE o.customerID = ? AND o.orderDate BETWEEN ? AND ?";
            
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, customerId);
                preparedStatement.setDate(2, new java.sql.Date(startDate.getTime()));
                preparedStatement.setDate(3, new java.sql.Date(endDate.getTime()));

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Order order = mapOrder(resultSet);
                        orders.add(order);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }
    
    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();

        try (Connection connection = MySQLConnector.getConnection()) {
            String sql = "SELECT * FROM Orders";
            
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    Order order = mapOrder(resultSet);
                    orders.add(order);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }

    public List<Order> getOrdersInDateRange(Date startDate, Date endDate) {
        List<Order> orders = new ArrayList<>();

        try (Connection connection = MySQLConnector.getConnection()) {
            String sql = "SELECT * FROM Orders WHERE orderDate BETWEEN ? AND ?";
            
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setDate(1, new java.sql.Date(startDate.getTime()));
                preparedStatement.setDate(2, new java.sql.Date(endDate.getTime()));

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Order order = mapOrder(resultSet);
                        orders.add(order);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }

    // Hàm giúp ánh xạ ResultSet thành Order
    private Order mapOrder(ResultSet resultSet) throws SQLException {
        Order order = new Order();
        order.setOrderID(resultSet.getString("orderID"));
        order.setOrderDate(resultSet.getDate("orderDate"));
        order.setEmployeeID(resultSet.getInt("employeeID"));
        order.setCustomerID(resultSet.getInt("customerID"));
        order.setDiscount(resultSet.getDouble("discount"));
        order.setTotalPrice(resultSet.getInt("totalPrice"));

        return order;
    }


}
