package com.bms.controller;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bms.pojo.SysUser;
import com.bms.service.SysActionService;
import com.bms.service.SysUserService;
import com.bms.util.ErrorCode;
import com.bms.util.JsonUtil;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Controller
@RequestMapping("user")
public class SysUserController {
	
	public static Logger logger = LogManager.getLogger(SysUserController.class);

	@Autowired
	private SysUserService sysUserService;
	
	@Autowired
	private SysActionService sysActionService;
	
	@RequestMapping("login")
	@ResponseBody
	public String login(@RequestParam String userID, 
						@RequestParam String password, 
						HttpSession session) {
		ObjectNode json = JsonUtil.getMapper().createObjectNode();
		
		try {
			SysUser sysUser = sysUserService.login(userID, password);
			
			if (sysUser == null) {
				json.put("login", "false");
				
				if (sysUserService.hasUser(userID)) {
					json.put("reason", ErrorCode.LOGIN_WRONG_PASSWORD);
				} else {
					json.put("reason", ErrorCode.LOGIN_NO_USER);
				}
				
				json.put("status", "success");
				return JsonUtil.writeObject(json);
			}
			
			session.setAttribute("user", sysUser);
			session.setAttribute("actions", sysActionService.findActionByRoleID(sysUser.getGroupID()));
			
			json.put("login", "success");
			json.put("status", "success");
		} catch (Exception e) {
			json.put("status", "false");
			logger.error("Error in SysUserController.login while change a SysUser to Json!", e);
		}
		
		return JsonUtil.writeObject(json);
	}
	
	@RequestMapping("logout")
	@ResponseBody
	public String logout(HttpSession session) {
		ObjectNode json = JsonUtil.getMapper().createObjectNode();
		try {
			session.invalidate();
			json.put("status", "success");
		} catch (Exception e) {
			json.put("status", "false");
			logger.error("Error while logout!!!", e);
		}
		
		return JsonUtil.writeObject(json);
	}
	
}
