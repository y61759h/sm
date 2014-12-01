package com.bms.controller;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bms.pojo.Test;
import com.bms.service.TestService;

@Controller
@RequestMapping("test")
public class TestController {
	
	public static Logger logger = LogManager.getLogger(TestController.class);

	@Autowired
	private TestService testService;
	
	@RequestMapping("test")
	@ResponseBody
	public String test() {
		logger.info("This is A testLog!!!");
		
		Test test = new Test();
		//test.setId("1");
		test.setId("fuck" + (int)(Math.random() * 10000 / 122) );
		test.setName("my name is ");
		test.setTestDatetime(new Date());
		testService.addTest(test);
		
		//testService.deleteTest(test);
//		return "[{\"id\": 1,\"text\": \"Node 1\",\"state\": \"closed\",\"children\": [{\"id\": 11,\"text\": \"Node 11\"},{\"id\": 12,\"text\": \"Node 12\"}]},{\"id\": 2,\"text\":\"Node 2\",\"state\":\"closed\"}]";
		return "[{\"id\": 1,\"text\": \"Node 1\",\"state\": \"closed\",\"children\": [{\"id\": 11,\"text\": \"Node 11\"},{\"id\": 12,\"text\": \"Node 12\"}]},{\"id\": 2,\"text\":\"Node 2\",\"state\":\"closed\"},{\"id\": 3,\"text\":\"Node 3\",\"state\":\"closed\"}]";
	}
	
	@RequestMapping("gettest")
	@ResponseBody
	public String getTest() {
		List<Test> test = testService.findTest("1");
		return test.toString();
	}
	
}
