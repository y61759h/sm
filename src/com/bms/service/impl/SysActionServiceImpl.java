package com.bms.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bms.mapper.SysActionMapper;
import com.bms.pojo.SysAction;
import com.bms.service.SysActionService;

@Service
public class SysActionServiceImpl implements SysActionService {
	
	public static Logger logger = LogManager.getLogger(SysActionServiceImpl.class);
	
	@Autowired
	private SysActionMapper sysActionMapper;

	@Override
	public Map<String, SysAction> findActionByRoleID(String roleID) {
		Map<String, SysAction> map = new HashMap<String, SysAction>();
		List<SysAction> actions = sysActionMapper.findActionByRoleID(roleID);
		
		for (SysAction action : actions) {
			map.put(action.getAction(), action);
		}
		
		return map;
	}

}
