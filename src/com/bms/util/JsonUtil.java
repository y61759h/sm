package com.bms.util;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
	
	public static Logger logger = LogManager.getLogger(JsonUtil.class);
	static ObjectMapper mapper = new ObjectMapper();
	
	public static ObjectMapper getMapper() {
		return mapper;
	}
	
	public static String writeObject(Object obj) {
		String jsonStr = null;
		try {
			jsonStr = mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			logger.error("Error while tran the object to Json String!!!", e);
		}
		return jsonStr;
	}
	
	public static String beanToJson(Object obj) throws JsonGenerationException, JsonMappingException, IOException {
		Writer writer = new StringWriter();
		mapper.writeValue(writer, obj);
		return writer.toString();
	}
	

}
