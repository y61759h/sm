package com.bms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bms.mapper.TestMapper;
import com.bms.pojo.Test;
import com.bms.service.TestService;

@Service
public class TestServiceImpl implements TestService {
	
	@Autowired
	private TestMapper testMapper;

	@Override
	public void addTest(Test test) {
		// TODO Auto-generated method stub
		testMapper.addTest(test);
	}

	@Override
	public void updateTest(Test test) {
		// TODO Auto-generated method stub
		testMapper.updateTest(test);
	}

	@Override
	public void deleteTest(Test test) {
		// TODO Auto-generated method stub
		testMapper.deleteTest(test);
	}

	@Override
	public List<Test> findTest(String id) {
		// TODO Auto-generated method stub
		return testMapper.findTest(id);
	}

	public TestMapper getTestMapper() {
		return testMapper;
	}

	public void setTestMapper(TestMapper testMapper) {
		this.testMapper = testMapper;
	}

}
