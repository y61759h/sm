package com.bms.pojo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class SysAction {
	
	private String actionID;
	private String menuID;
	private String action;
	private Integer xtype;
	private String createUser;
	private String modifyUser;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createDate;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date modifyDate;
	
	public String getActionID() {
		return actionID;
	}

	public void setActionID(String actionID) {
		this.actionID = actionID;
	}

	public String getMenuID() {
		return menuID;
	}

	public void setMenuID(String menuID) {
		this.menuID = menuID;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Integer getXtype() {
		return xtype;
	}

	public void setXtype(Integer xtype) {
		this.xtype = xtype;
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
		return "SysAction [actionID=" + actionID + ", menuID=" + menuID
				+ ", action=" + action + ", xtype=" + xtype + ", createUser="
				+ createUser + ", createDate=" + createDate + ", modifyUser="
				+ modifyUser + ", modifyDate=" + modifyDate + "]";
	}

}
