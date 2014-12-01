package com.bms.pojo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class SysRole {
	
	private String RoleID;
	private String RoleName;
	private String CreateUser;
	private String modifyUser;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createDate;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date modifyDate;
	
	public String getRoleID() {
		return RoleID;
	}

	public void setRoleID(String roleID) {
		RoleID = roleID;
	}

	public String getRoleName() {
		return RoleName;
	}

	public void setRoleName(String roleName) {
		RoleName = roleName;
	}

	public String getCreateUser() {
		return CreateUser;
	}

	public void setCreateUser(String createUser) {
		CreateUser = createUser;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	@Override
	public String toString() {
		return "SysRole [RoleID=" + RoleID + ", RoleName=" + RoleName
				+ ", CreateUser=" + CreateUser + ", createDate=" + createDate
				+ ", modifyUser=" + modifyUser + ", modifyDate=" + modifyDate
				+ "]";
	}

}
