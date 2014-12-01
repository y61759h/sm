package com.bms.pojo;

import java.util.Date;

public class SysRoleMenu {
	
	private String roleID;
	private String menuID;
	private String createUser;
	private Date createDate;
	private String modifyUser;
	private Date modifyDate;
	
	public String getRoleID() {
		return roleID;
	}

	public void setRoleID(String roleID) {
		this.roleID = roleID;
	}

	public String getMenuID() {
		return menuID;
	}

	public void setMenuID(String menuID) {
		this.menuID = menuID;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
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
		return "SysRoleMenu [roleID=" + roleID + ", menuID=" + menuID
				+ ", createUser=" + createUser + ", createDate=" + createDate
				+ ", modifyUser=" + modifyUser + ", modifyDate=" + modifyDate
				+ "]";
	}

}
