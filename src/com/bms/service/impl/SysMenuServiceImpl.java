package com.bms.service.impl;

import java.io.StringWriter;
import java.io.Writer;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bms.mapper.SysMenuMapper;
import com.bms.pojo.SysMenu;
import com.bms.pojo.SysRole;
import com.bms.pojo.SysUser;
import com.bms.service.SysMenuService;
import com.bms.util.BaseUtil;
import com.bms.util.ErrorCode;
import com.bms.util.JsonUtil;
import com.bms.util.SysUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class SysMenuServiceImpl implements SysMenuService {
	
	public static Logger logger = LogManager.getLogger(SysMenuServiceImpl.class);
	
	@Autowired
	private SysMenuMapper sysMenuMapper;

	public void fuck() {
		System.out.println("Fuck");
	}

	@Override
	public String findMenuByRole(SysRole sysRole) {
		List<SysMenu> menus = sysMenuMapper.findMenuByRole(sysRole);
		String menuString = null;
		try {
			menuString = SysUtil.menuTurnJsonForEasyUI(menus);
		} catch (Exception e) {
			logger.error("Error in SysMenuServiceImpl.findMenuByRole() while turn the menus to json!", e);
			throw new RuntimeException();
		}
		return menuString;
	}

	@Override
	public String listMenu(String page, String size) {
		String menuString = null;
		try {
			Map<String, Object> map = BaseUtil.pageParameters(page, size);
			List<SysMenu> menus = sysMenuMapper.listMenu(map);
			Long total = sysMenuMapper.getTotalMenu();
			
			Map<String, Object> jsonMenu = new HashMap<String, Object>();
			jsonMenu.put("total", total);
			jsonMenu.put("rows", menus);
			
			ObjectMapper mapper = JsonUtil.getMapper();
			Writer writer = new StringWriter();
			mapper.writeValue(writer, jsonMenu);
			menuString = writer.toString();
		} catch (Exception e) {
			logger.error("Error while list menus!", e);
			throw new RuntimeException();
		}
		return menuString;
	}

	@Override
	public String addMenu(SysMenu sysMenu, SysUser sysUser) {
		try {
			Date date = new Date();
			sysMenu.setCreateDate(date);
			sysMenu.setModifyDate(date);
			sysMenu.setCreateUser(sysUser.getUserID());
			sysMenu.setModifyUser(sysUser.getUserID());
			sysMenuMapper.addMenu(sysMenu);
			return ErrorCode.SUCCESS;
		} catch (Exception e) {
			logger.error("Error while add menu!", e);
			throw new RuntimeException();
		}
		
	}

}
