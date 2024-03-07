package com.example.dao;

import com.example.model.BookBatch;
import com.example.model.BookReceipt;
import com.example.model.BookReceiptDetail;
import com.example.util.MySQLConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BookReceiptDao {

	public boolean saveBookReceiptWithDetails(BookReceipt bookReceipt, List<BookReceiptDetail> details, List<BookBatch> batchList) {
	    Connection connection = null;
	    try {
	        connection = MySQLConnector.getConnection();
	        connection.setAutoCommit(false);

	        // Lưu thông tin vào bảng BookReceipts
	        if (saveBookReceipt(connection, bookReceipt)) {
	            int receiptDetailID = -1;

	            for (BookReceiptDetail detail : details) {
	                // Lưu thông tin vào bảng BookReceiptDetails và lấy ReceiptDetailID
	                receiptDetailID = saveBookReceiptDetailAndGetID(connection, bookReceipt.getReceiptID(), detail);

	                // Kiểm tra xem có lỗi khi lưu chi tiết phiếu nhập không
	                if (receiptDetailID == -1) {
	                    // Nếu có lỗi khi lưu chi tiết phiếu nhập, rollback và trả về false
	                    connection.rollback();
	                    return false;
	                }

	                // Lưu thông tin vào bảng BookBatches
	                if (!saveBookBatchList(connection, batchList, receiptDetailID, detail.getBookID())) {
	                    // Nếu có lỗi khi lưu danh sách lô sách, rollback và trả về false
	                    connection.rollback();
	                    return false;
	                }
	            }

	            // Nếu mọi thứ đều thành công, commit và trả về true
	            connection.commit();
	            return true;
	        } else {
	            // Nếu có lỗi khi lưu phiếu, trả về false
	            return false;
	        }
	    } catch (SQLException e) {
	        try {
	            if (connection != null) {
	                connection.rollback();
	            }
	        } catch (SQLException rollbackException) {
	            rollbackException.printStackTrace();
	        }
	        e.printStackTrace();
	    } finally {
	        try {
	            if (connection != null) {
	                connection.setAutoCommit(true);
	                connection.close();
	            }
	        } catch (SQLException closeException) {
	            closeException.printStackTrace();
	        }
	    }
	    // Trả về false nếu có bất kỳ lỗi nào xảy ra
	    return false;
	}
	
	private boolean saveBookReceipt(Connection connection, BookReceipt bookReceipt) throws SQLException {
	    String insertBookReceiptQuery = "INSERT INTO BookReceipts (ReceiptID, ReceiptDate, EmployeeID, TotalCost) VALUES (?, ?, ?, ?)";
	    try (PreparedStatement preparedStatement = connection.prepareStatement(insertBookReceiptQuery)) {
	        preparedStatement.setString(1, bookReceipt.getReceiptID());
	        preparedStatement.setDate(2, new java.sql.Date(bookReceipt.getReceiptDate().getTime()));
	        preparedStatement.setInt(3, bookReceipt.getEmployeeID());
	        preparedStatement.setDouble(4, bookReceipt.getTotalCost());
	        int rowsAffected = preparedStatement.executeUpdate();
	        return rowsAffected > 0;
	    }
	}

	private int saveBookReceiptDetailAndGetID(Connection connection, String receiptID, BookReceiptDetail detail) throws SQLException {
	    String insertBookReceiptDetailQuery = "INSERT INTO BookReceiptDetails (ReceiptID, BookID, Quantity, UnitPrice) VALUES (?, ?, ?, ?)";
	    try (PreparedStatement preparedStatement = connection.prepareStatement(insertBookReceiptDetailQuery, Statement.RETURN_GENERATED_KEYS)) {
	        preparedStatement.setString(1, receiptID);
	        preparedStatement.setInt(2, detail.getBookID());
	        preparedStatement.setInt(3, detail.getQuantity());
	        preparedStatement.setDouble(4, detail.getUnitPrice());
	        int rowsAffected = preparedStatement.executeUpdate();

	        // Kiểm tra xem có lỗi khi lưu chi tiết phiếu nhập không
	        if (rowsAffected > 0) {
	            // Lấy ResultSet chứa các khóa tự động được sinh ra (nếu có)
	            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
	            if (generatedKeys.next()) {
	                return generatedKeys.getInt(1); // Trả về ReceiptDetailID đầu tiên
	            } else {
	                throw new SQLException("Lưu chi tiết phiếu nhập thất bại, không có khóa tự động được sinh ra.");
	            }
	        } else {
	            // Trả về -1 để biểu thị lỗi khi lưu chi tiết phiếu nhập
	            return -1;
	        }
	    }
	}

	
	private boolean saveBookBatchList(Connection connection, List<BookBatch> batchList, int receiptDetailID, int bookID) throws SQLException {
	    String insertBookBatchQuery = "INSERT INTO BookBatches (BookID, QuantityOriginal, QuantityCurrent, PurchaseDate, UnitPrice, ReceiptDetailID, StorageLocation) VALUES (?, ?, ?, ?, ?, ?, ?)";
	    System.out.println("ReceiptDetailID: " + receiptDetailID);

	    try (PreparedStatement preparedStatement = connection.prepareStatement(insertBookBatchQuery)) {
	        for (BookBatch batch : batchList) {
	        	if(batch.getBookID() == bookID) {
	        		preparedStatement.setInt(1, batch.getBookID());
		            preparedStatement.setInt(2, batch.getQuantityOriginal());
		            preparedStatement.setInt(3, batch.getQuantityCurrent());
		            preparedStatement.setDate(4, new java.sql.Date(batch.getPurchaseDate().getTime()));
		            preparedStatement.setDouble(5, batch.getUnitPrice());
		            preparedStatement.setInt(6, receiptDetailID); // Sử dụng ReceiptDetailID tương ứng
		            preparedStatement.setString(7, batch.getStorageLocation());

		            preparedStatement.addBatch();
	        	}
	        }

	        // Chỉ thực hiện executeUpdate() ở đây, ngoài vòng lặp
	        int[] rowsAffected = preparedStatement.executeBatch();
	        for (int affectedRows : rowsAffected) {
	            if (affectedRows <= 0) {
	                // Nếu có lỗi khi lưu lô sách, rollback và trả về false
	                connection.rollback();
	                return false;
	            }
	        }

	        return true;
	    }
	}


	public List<BookReceipt> getAllBookReceipts() {
        List<BookReceipt> bookReceipts = new ArrayList<>();

        try (Connection connection = MySQLConnector.getConnection()) {
            String sql = "SELECT * FROM BookReceipts";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        BookReceipt bookReceipt = mapBookReceipt(resultSet);
                        bookReceipts.add(bookReceipt);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bookReceipts;
    }

    private BookReceipt mapBookReceipt(ResultSet resultSet) throws SQLException {
        String receiptID = resultSet.getString("ReceiptID");
        Date receiptDate = resultSet.getDate("ReceiptDate");
        int employeeID = resultSet.getInt("EmployeeID");
        double totalCost = resultSet.getDouble("TotalCost");

        return new BookReceipt(receiptID, receiptDate, employeeID, totalCost);
    }


}
