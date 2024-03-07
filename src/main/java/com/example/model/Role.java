package com.example.model;

public class Role {
	private int roleID;
	private String roleName;
	public Role(int roleID, String roleString) {
		super();
		this.roleID = roleID;
		this.roleName = roleString;
	}
	public Role() {
		super();
	}
	public int getRoleID() {
		return roleID;
	}
	public void setRoleID(int roleID) {
		this.roleID = roleID;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleString(String roleName) {
		this.roleName = roleName;
	}
	@Override
	public String toString() {
		return this.roleName;
	}
	
	
}
