package com.bms.util;

import java.util.HashMap;
import java.util.Map;

public class BaseUtil {
	
	public static Map<String, Object> pageParameters(String page, String size) {
		return pageForOracle(page, size);
	}
	
	public static Map<String, Object> pageForOracle(String page, String size) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		if (page == null || "".equals(page)) {
			page = "1";
		}
		
		if (size == null || "".equals(size)) {
			size = "25";
		}
		
		map.put("start", (Integer.parseInt(page) - 1) * Integer.parseInt(size));
		map.put("end", Integer.parseInt(page) * Integer.parseInt(size));
		
		return map;
	}
	
	public static Map<String, Object> pageForMysql(String page, String size) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		if (page == null || "".equals(page)) {
			page = "1";
		}
		
		if (size == null || "".equals(size)) {
			size = "25";
		}
		
		map.put("start", (Integer.parseInt(page) - 1) * Integer.parseInt(size));
		map.put("end", Integer.parseInt(size));
		
		return map;
	}

}
