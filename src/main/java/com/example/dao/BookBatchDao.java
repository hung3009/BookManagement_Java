package com.example.dao;

import com.example.model.BookBatch;
import com.example.util.MySQLConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookBatchDao {

    // Lấy danh sách lô sách theo ID sách
    public List<BookBatch> getBatchesByBookId(int bookId) {
        List<BookBatch> batches = new ArrayList<>();
        String query = "SELECT * FROM BookBatches WHERE BookID = ?";

        try (Connection connection = MySQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, bookId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                BookBatch batch = resultSetToBookBatch(resultSet);
                batches.add(batch);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return batches;
    }

    // TODO: Thêm các phương thức khác cần thiết cho quản lý lô sách

    // Hàm chuyển đổi ResultSet thành đối tượng BookBatch
    private BookBatch resultSetToBookBatch(ResultSet resultSet) throws SQLException {
        BookBatch batch = new BookBatch();
        batch.setLotID(resultSet.getInt("LotID")); // Thêm dòng này để lấy LotID từ ResultSet
        batch.setBookID(resultSet.getInt("BookID"));
        batch.setQuantityOriginal(resultSet.getInt("QuantityOriginal"));
        batch.setQuantityCurrent(resultSet.getInt("QuantityCurrent"));
        batch.setPurchaseDate(resultSet.getDate("PurchaseDate"));
        batch.setUnitPrice(resultSet.getInt("UnitPrice"));
        batch.setReceiptDetailID(resultSet.getInt("ReceiptDetailID"));
        batch.setStorageLocation(resultSet.getString("StorageLocation"));
        return batch;
    }
}
