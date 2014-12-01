package com.bms.mapper;

import java.util.List;
import java.util.Map;

import com.bms.vo.Report;

public interface BmsMapper {
	
	public List<String> getCompany(Map<String, Object> map);
	
	public List<Map<String, Object>> getBranch(Map<String, Object> map);
	
	public List<String> getRegion(Map<String, Object> map);
	
	public List<String> getChain(Map<String, Object> map);
	
	public List<Map<String, Object>> getBranchOut();
	
	public List<String> getItemType();
	
	public List<String> getItemName(String pattern);
	
	public List<String> getSupName(String pattern);
	
	public List<Map<String, Object>> usingSourceReport(Report report);
	public List<Map<String, Object>> usingSummaryReport(Report report);
	public List<Map<String, Object>> usingDetailReport(Report report); 
			
	public List<Map<String, Object>> subInvoiceSummary(Report report); 
	public List<Map<String, Object>> invoiceDetail(Report report); 
	public List<Map<String, Object>> invoiceItemDetail(Report report); 
	public List<Map<String, Object>> invoiceData(Report report); 
	
	public List<Map<String, Object>> tranInSummary(Report report); 
	public List<Map<String, Object>> tranOutSummary(Report report); 
	public List<Map<String, Object>> tranOutDetail(Report report); 
	public List<Map<String, Object>> tranInDetail(Report report); 
	
	public List<String> getBranchByUserID(String userID);
	
}
