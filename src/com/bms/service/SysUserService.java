package com.bms.service;

import com.bms.pojo.SysUser;

public interface SysUserService {
	
	public SysUser login(String userID, String password);
	public boolean hasUser(String userID);

}
