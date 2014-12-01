package com.bms.mapper;

import java.util.List;
import java.util.Map;

import com.bms.pojo.SysMenu;
import com.bms.pojo.SysRole;

public interface SysMenuMapper {
	
	public List<SysMenu> findMenuByRole(SysRole sysRole);
	
	public Long getTotalMenu();
	
	public List<SysMenu> listMenu(Map<String, Object> map);
	
	public void addMenu(SysMenu sysMenu);

}
