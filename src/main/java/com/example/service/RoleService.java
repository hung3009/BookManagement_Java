package com.example.service;

import com.example.dao.RoleDao;
import com.example.model.Role;

import java.sql.Connection;
import java.util.List;

public class RoleService {

    private RoleDao roleDao;

    public RoleService() {
        this.roleDao = new RoleDao();
    }

    public List<Role> getAllRoles() throws Exception {
        return roleDao.getAllRoles();
    }
    
 // Add this method to the UserService class
    public Role getRoleById(int roleID) throws Exception {
        return roleDao.getRoleById(roleID);
    }
}
