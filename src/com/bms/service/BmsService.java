package com.bms.service;

import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.bms.vo.Report;

public interface BmsService {
	
	public String getCompany(String jsonParam, String userID);
	public String getBranch(String jsonParam, String userID);
	public String getRegion(String jsonParam, String userID);
	public String getChain(String jsonParam, String userID);
	public String getItemType();
	public String getBranchOut();
	public String getItemName(String pattern);
	public String getSupName(String pattern);
	public void setItemCodeAndSupCode(Report report);
	
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
	
	
	
	
	
	
	public void filterReport(Report report);
	
	
	public HSSFWorkbook getRpt1Ws(Report report, String filePth, String strdate,
			String endate, String br_cde, String cmp, String itemtype,
			String foodtype, String rpttype,String sup_cde,String item_cde,
			String item_cde2,String chain_cde,String region);
	

	public HSSFWorkbook getRpt2Ws(Report report, String filePth, String strdate,
			String endate, String br_cde, String cmp, String itemtype,
			String foodtype,String sup_cde, String item_cde1,String inv_no,
			String chain_cde,String item_cde2,String region,String rptType);
	
	public HSSFWorkbook getRpt3Ws(Report report, String filePth, String strdate,
			String endate, String br_cde_in, String cmp, String itemtype,
			String foodtype, String rpttype, String sup_cde, String item_cde,
			String br_cde_out,String tran_no,String item_cde2,String chain_cde,String region);
	
	
}
