package com.example.dao;

import com.example.model.Role;
import com.example.util.MySQLConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoleDao {

    public List<Role> getAllRoles() throws Exception {
        List<Role> roles = new ArrayList<>();

        String query = "SELECT * FROM roles";
        try (Connection connection = MySQLConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int roleID = resultSet.getInt("RoleID");
                String roleString = resultSet.getString("RoleName");

                Role role = new Role(roleID, roleString);
                roles.add(role);
            }
        }

        return roles;
    }
    
    public Role getRoleById(int roleID) throws Exception {
        String query = "SELECT * FROM roles WHERE roleID = ?";
        
        try (Connection connection = MySQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, roleID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int roleId = resultSet.getInt("RoleID");
                String roleString = resultSet.getString("roleName");
                return new Role(roleId, roleString);
            }

        } catch (SQLException e) {
            throw new Exception("Error fetching role by ID: " + e.getMessage());
        }

        return null; // Return null if no role is found with the specified ID
    }

}
