package com.bms.util;

import java.util.ArrayList;
import java.util.List;

import com.bms.pojo.SysMenu;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class SysUtil {
	
	public static String menuTurnJsonForEasyUI(List<SysMenu> menus) throws JsonProcessingException {
		
		ObjectMapper mapper = JsonUtil.getMapper();
		List<ObjectNode> current = new ArrayList<ObjectNode>();
		ArrayNode root = mapper.createArrayNode();
		
		//找出根节点
		for (int i = 0; i < menus.size(); ) {
			SysMenu sysMenu = menus.get(i);
			if (sysMenu.getParentID() == null) {
				ObjectNode node = mapper.createObjectNode();
				node.put("id", sysMenu.getMenuID());
				node.put("text", sysMenu.getMenuName());
				
				if (sysMenu.getLinkSource() != null) {
					node.put("linkSource", sysMenu.getLinkSource());
				} else {
					node.put("state", "closed");
					ArrayNode children = mapper.createArrayNode();
					node.set("children", children);
				}
				
				root.add(node);
				current.add(node);
				menus.remove(i);
			} else {
				i++;
			}
		}
		
		List<ObjectNode> chlidrenNode = new ArrayList<ObjectNode>();
		
		while (menus.size() > 0) {
			for (ObjectNode parent : current) {
				for (int i = 0; i < menus.size(); ) {
					SysMenu sysMenu = menus.get(i);
					if (sysMenu.getParentID().equals(parent.get("id").asText())) {
						ObjectNode node = mapper.createObjectNode();
						node.put("id", sysMenu.getMenuID());
						node.put("text", sysMenu.getMenuName());
						
						if (sysMenu.getLinkSource() != null) {
							node.put("linkSource", sysMenu.getLinkSource());
						} else {
							node.put("state", "closed");
						}
						
						ArrayNode children = (ArrayNode) parent.get("children");
						
						if (children == null) {
							children = mapper.createArrayNode();
						}
						
						children.add(node);
						parent.set("children", children);
						chlidrenNode.add(node);
						menus.remove(i);
					} else {
						i++;
					}
				}
			}
			
			current = chlidrenNode;
			chlidrenNode = new ArrayList<ObjectNode>();
		}
		
		
		
		return mapper.writeValueAsString(root);
		
	}

}
