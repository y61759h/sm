package com.bms.mapper;

import com.bms.pojo.SysUser;

public interface SysUserMapper {
	
	public SysUser login(SysUser sysUser);
	public Long findCountForUserByuserID(String userID);

}
