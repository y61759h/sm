package com.bms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bms.mapper.SysUserMapper;
import com.bms.pojo.SysUser;
import com.bms.service.SysUserService;

@Service
public class SysUserServiceImpl implements SysUserService {
	
	@Autowired
	private SysUserMapper sysUserMapper;

	@Override
	public SysUser login(String userID, String password) {
		SysUser sysUser = new SysUser();
		sysUser.setUserID(userID);;
		sysUser.setPassword(password);
		
		return sysUserMapper.login(sysUser);
	}

	@Override
	public boolean hasUser(String userID) {
		return sysUserMapper.findCountForUserByuserID(userID) > 0;
	}

}
