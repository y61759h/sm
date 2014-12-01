package com.bms.mapper;

import java.util.List;

import com.bms.pojo.SysAction;

public interface SysActionMapper {
	
	public List<SysAction> findActionByRoleID(String roleID);

}
