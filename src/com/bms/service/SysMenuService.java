package com.bms.service;

import com.bms.pojo.SysMenu;
import com.bms.pojo.SysRole;
import com.bms.pojo.SysUser;

public interface SysMenuService {
	
	public void fuck();
	
	/**
	 * 返回主页显示用的菜单
	 * @param sysRole
	 * @return Json数据
	 */
	public String findMenuByRole(SysRole sysRole);
	
	/**
	 * 返回编辑用的菜单列表
	 * @return Json数据
	 */
	public String listMenu(String page, String size);
	
	public String addMenu(SysMenu sysMenu, SysUser sysUser);
	
}
