package com.bms.mapper;

import java.util.List;

import com.bms.pojo.Test;

public interface TestMapper {
	
	public void addTest(Test test);
	
	public void updateTest(Test test);

	public void deleteTest(Test test);
	
	public List<Test> findTest(String id);

}
