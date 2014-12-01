package com.bms.service;

import java.util.Map;

import com.bms.pojo.SysAction;


public interface SysActionService {
	
	public Map<String, SysAction> findActionByRoleID(String roleID);

}
