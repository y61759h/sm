package com.bms.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bms.pojo.SysMenu;
import com.bms.pojo.SysRole;
import com.bms.pojo.SysUser;
import com.bms.service.SysMenuService;

@Controller
@RequestMapping("menu")
public class SysMenuController {
	
	public static Logger logger = LogManager.getLogger(SysMenuController.class);

	@Autowired
	private SysMenuService menuService;
	
	@RequestMapping("get_menu")
	@ResponseBody
	public String getMenu() {
		SysRole sysRole = new SysRole();
		sysRole.setRoleID("PRS");
		return menuService.findMenuByRole(sysRole);
	}
	
	@RequestMapping("list_menu")
	@ResponseBody
	public String listMenu(String page, String rows) {
		return menuService.listMenu(page, rows);
	}
	
	@RequestMapping("add_menu")
	@ResponseBody
	public String addMenu(SysMenu sysMenu) {
		SysUser sysUser = new SysUser();
		sysUser.setUserID("ITD");
		return menuService.addMenu(sysMenu, sysUser);
	}

}
