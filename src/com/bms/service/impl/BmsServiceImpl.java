package com.bms.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bms.mapper.BmsMapper;
import com.bms.service.BmsService;
import com.bms.util.JsonUtil;
import com.bms.vo.Report;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Service
public class BmsServiceImpl implements BmsService {
	
	public static Logger logger = LogManager.getLogger(BmsServiceImpl.class);
	
	@Autowired
	public BmsMapper bmsMapper;

	@Override
	public String getCompany(String jsonParam, String userID) {
		Map<String, Object> params = turnParamsForMap(jsonParam);
		params.put("userID", userID);
		List<Map<String, Object>> companys = turnForCombobox(bmsMapper.getCompany(params));
		
		try {
			return JsonUtil.beanToJson(companys);
		} catch (Exception e) {
			logger.error(e);
			throw new RuntimeException();
		}
	}

	@Override
	public String getBranch(String jsonParam, String userID) {
		Map<String, Object> params = turnParamsForMap(jsonParam);
		params.put("userID", userID);
		List<Map<String, Object>> branchs = turnForComboboxByListMap(bmsMapper.getBranch(params));
		try {
			return JsonUtil.beanToJson(branchs);
		} catch (Exception e) {
			logger.error(e);
			throw new RuntimeException();
		}
	}

	@Override
	public String getRegion(String jsonParam, String userID) {
		Map<String, Object> params = turnParamsForMap(jsonParam);
		params.put("userID", userID);
		List<Map<String, Object>> regions = turnForCombobox(bmsMapper.getRegion(params));
		
		try {
			return JsonUtil.beanToJson(regions);
		} catch (Exception e) {
			logger.error(e);
			throw new RuntimeException();
		}
	}

	@Override
	public String getChain(String jsonParam, String userID) {
		Map<String, Object> params = turnParamsForMap(jsonParam);
		params.put("userID", userID);
		List<Map<String, Object>> chains = turnForCombobox(bmsMapper.getChain(params));
		
		try {
			return JsonUtil.beanToJson(chains);
		} catch (Exception e) {
			logger.error(e);
			throw new RuntimeException();
		}
	}
	
	@Override
	public String getBranchOut() {
		List<Map<String, Object>> branchs = bmsMapper.getBranchOut();
		List<Map<String, Object>> datas = turnForComboboxByListMap(branchs);
		
		try {
			return JsonUtil.beanToJson(datas);
		} catch (Exception e) {
			logger.error("Error while turn bean to json in BmsService.getBranchOut()!", e);
			throw new RuntimeException();
		}
	}

	@Override
	public String getItemType() {
		List<String> itemTypes = bmsMapper.getItemType();
		List<Map<String, Object>> datas = turnForCombobox(itemTypes);
		
		try {
			return JsonUtil.beanToJson(datas);
		} catch (Exception e) {
			logger.error("Error while turn bean to json in BmsService.getItemType()!", e);
			throw new RuntimeException();
		}
	}
	
	public String getItemName(String pattern) {
		if (pattern == null) {
			pattern = "";
		}
		//pattern = "%" + pattern + "%";
		pattern = "%%";
		List<String> supNames = bmsMapper.getItemName(pattern);
		ObjectMapper mapper = JsonUtil.getMapper();
		ArrayNode ja = mapper.createArrayNode();
		for (int i = 0; i < supNames.size(); i++) {
			ObjectNode node = mapper.createObjectNode();
			node.put("value", supNames.get(i));
			ja.add(node);
		}
		return JsonUtil.writeObject(ja);
	}
	
	public String getSupName(String pattern) {
		if (pattern == null) {
			pattern = "";
		}
		//pattern = "%" + pattern + "%";
		pattern = "%%";
		List<String> supNames = bmsMapper.getSupName(pattern);
		ObjectMapper mapper = JsonUtil.getMapper();
		ArrayNode ja = mapper.createArrayNode();
		for (int i = 0; i < supNames.size(); i++) {
			ObjectNode node = mapper.createObjectNode();
			node.put("value", supNames.get(i));
			ja.add(node);
		}
		return JsonUtil.writeObject(ja);
	}
	
	public List<Map<String, Object>> turnForCombobox(List<String> lists) {
		List<Map<String, Object>> datas = new ArrayList<Map<String,Object>>();
		
		Map<String, Object> all = new HashMap<String, Object>();
		all.put("value", "all");
		all.put("text", "——全部——");
		datas.add(all);
		
		for (String str : lists) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("value", str);
			map.put("text", str);
			datas.add(map);
		}
		return datas;
	}
	
	public List<Map<String, Object>> turnForComboboxByListMap(List<Map<String, Object>> lists) {
		List<Map<String, Object>> datas = new ArrayList<Map<String,Object>>();
		
		Map<String, Object> all = new HashMap<String, Object>();
		all.put("value", "all");
		all.put("text", "——全部——");
		datas.add(all);
		
		for (Map<String, Object> branch : lists) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("value", branch.get("BRCODE"));
			map.put("text", branch.get("BRNAME"));
			datas.add(map);
		}
		return datas;
	}
	
	public Map<String, Object> turnParamsForMap(String jsonParam) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (jsonParam == null || "".equals(jsonParam)) {
			return map;
		}
		
		JsonNode params = null;
		
		try {
			params = JsonUtil.getMapper().readTree(jsonParam);
		} catch (Exception e) {
			logger.error(e);
		} 
		
		if (params.size() <= 0) {
			return map;
		}
		
		Iterator<String> it = params.fieldNames();
		while (it.hasNext()) {
			String key = it.next();
			String value = params.get(key).asText();
			map.put(key, value);
		}
		
		return map;
	}
	
	public String getLastMon(String date,int monadd){
		String [] strs=date.split("-");
		Date d=new Date(Integer.parseInt(strs[0]),Integer.parseInt( strs[1])-monadd-1,
				Integer.parseInt(strs[2]));
		int year=d.getYear();
		int m=d.getMonth()+1;
		return year+"-"+(m>=10?m:("0"+m));
	}
	

	public void filterReport(Report report) {
		if (report.getBrCode() == null || "".equals(report.getBrCode()) || "all".equals(report.getBrCode())) {
			report.setBrCode(null);
		}
		
		if (report.getChainCode() == null || "".equals(report.getChainCode()) || "all".equals(report.getChainCode())) {
			report.setChainCode(null);
		}
		
		if (report.getCompany() == null || "".equals(report.getCompany()) || "all".equals(report.getCompany())) {
			report.setCompany(null);
		}
		
		if (report.getEndDate() == null || "".equals(report.getEndDate())) {
			report.setEndDate(null);
		}
		
		if (report.getFoodType() == null || "".equals(report.getFoodType()) || "all".equals(report.getFoodType())) {
			report.setFoodType(null);
		}
		
		if (report.getItemCodeEnd() == null || "".equals(report.getItemCodeEnd())) {
			report.setItemCodeEnd(null);
		}
		
		if (report.getItemCodeStart() == null || "".equals(report.getItemCodeStart())) {
			report.setItemCodeStart(null);
		}
		
		if (report.getItemType() == null || "".equals(report.getItemType())  || "all".equals(report.getItemType())) {
			report.setItemType(null);
		}
		
		if (report.getLastMonEndDate() == null || "".equals(report.getLastMonEndDate())) {
			report.setLastMonEndDate(null);
		}
		
		if (report.getLastMonStartDate() == null || "".equals(report.getLastMonStartDate())) {
			report.setLastMonStartDate(null);
		}
		
		if (report.getRegion() == null || "".equals(report.getRegion()) || "all".equals(report.getRegion())) {
			report.setRegion(null);
		}
		
		if (report.getReportType() == null || "".equals(report.getReportType())) {
			report.setReportType(null);
		}
		
		if (report.getStartDate() == null || "".equals(report.getStartDate())) {
			report.setStartDate(null);
		}
		
		if (report.getSubCode() == null || "".equals(report.getSubCode())) {
			report.setSubCode(null);
		}
		
		if (report.getBrCodeIn() == null || "".equals(report.getBrCodeIn()) || "all".equals(report.getBrCodeIn())) {
			report.setBrCodeIn(null);
		}
		
		if (report.getBrCodeOut() == null || "".equals(report.getBrCodeOut()) || "all".equals(report.getBrCodeOut())) {
			report.setBrCodeOut(null);
		}
		
		if (report.getInvNo() == null || "".equals(report.getInvNo()) || "all".equals(report.getInvNo())) {
			report.setInvNo(null);
		}
		
		if (report.getTranNo() == null || "".equals(report.getTranNo()) || "all".equals(report.getTranNo())) {
			report.setTranNo(null);
		}
		
	}

	@Override
	public List<Map<String, Object>> usingSourceReport(Report report) {
		filterReport(report);
		setBranchByGroupID(report);
		report.setLastMonStartDate(getLastMon(report.getStartDate(), 1));
		report.setLastMonEndDate(getLastMon(report.getEndDate(), 0));
		List<Map<String, Object>> lists = bmsMapper.usingSourceReport(report);
		return lists;
	}

	@Override
	public List<Map<String, Object>> usingSummaryReport(Report report) {
		filterReport(report);
		setBranchByGroupID(report);
		report.setLastMonStartDate(getLastMon(report.getStartDate(), 1));
		report.setLastMonEndDate(getLastMon(report.getEndDate(), 0));
		List<Map<String, Object>> lists = bmsMapper.usingSummaryReport(report);
		return lists;
	}

	@Override
	public List<Map<String, Object>> usingDetailReport(Report report) {
		filterReport(report);
		setBranchByGroupID(report);
		report.setLastMonStartDate(getLastMon(report.getStartDate(), 1));
		report.setLastMonEndDate(getLastMon(report.getEndDate(), 0));
		List<Map<String, Object>> lists = bmsMapper.usingDetailReport(report);
		return lists;
	}

	@Override
	public List<Map<String, Object>> subInvoiceSummary(Report report) {
		filterReport(report);
		setBranchByGroupID(report);
		List<Map<String, Object>> lists = bmsMapper.subInvoiceSummary(report);
		return lists;
	}

	@Override
	public List<Map<String, Object>> invoiceDetail(Report report) {
		filterReport(report);
		setBranchByGroupID(report);
		List<Map<String, Object>> lists = bmsMapper.invoiceDetail(report);
		return lists;
	}

	@Override
	public List<Map<String, Object>> invoiceItemDetail(Report report) {
		filterReport(report);
		setBranchByGroupID(report);
		List<Map<String, Object>> lists = bmsMapper.invoiceItemDetail(report);
		return lists;
	}

	@Override
	public List<Map<String, Object>> invoiceData(Report report) {
		filterReport(report);
		setBranchByGroupID(report);
		List<Map<String, Object>> lists = bmsMapper.invoiceData(report);
		return lists;
	}

	@Override
	public List<Map<String, Object>> tranInSummary(Report report) {
		filterReport(report);
		setBranchByGroupID(report);
		List<Map<String, Object>> lists = bmsMapper.tranInSummary(report);
		return lists;
	}

	@Override
	public List<Map<String, Object>> tranOutSummary(Report report) {
		filterReport(report);
		setBranchByGroupID(report);
		List<Map<String, Object>> lists = bmsMapper.tranOutSummary(report);
		return lists;
	}

	@Override
	public List<Map<String, Object>> tranOutDetail(Report report) {
		filterReport(report);
		setBranchByGroupID(report);
		List<Map<String, Object>> lists = bmsMapper.tranOutDetail(report);
		return lists;
	}

	@Override
	public List<Map<String, Object>> tranInDetail(Report report) {
		filterReport(report);
		setBranchByGroupID(report);
		List<Map<String, Object>> lists = bmsMapper.tranInDetail(report);
		return lists;
	}

	
	
	
	
	/***************************************
	 * 
	 * Excel
	 * 
	 ***************************************/
	public List getRpt1SummarData(Report report, String strdate, String endate, String br_cde,
			String cmp, String itemtype, String foodtype,String sup_cde,String item_cde,
			String item_cde2,String chain_cde,String region) {
		List list = new ArrayList();
		List<Map<String, Object>> summarys = this.usingSummaryReport(report);
		for (int i = 0; i < summarys.size(); i++) {
			Map<String, Object> map = summarys.get(i);
			logger.debug("########—————————————————summary == "+i+"———————————————########");
			Object[] obj = new Object[12];
			obj[0] = map.get("ITEM_CAT_DESC");
			obj[1] = map.get("LJC_COUNT");
			obj[2] = map.get("GH_COUNT");
			obj[3] = map.get("ZH_COUT");
			obj[4] = map.get("JC_COUNT");
			obj[5] = map.get("THIS_U_COUNT");
			obj[6] = map.get("LJC_AMT");
			obj[7] = map.get("GH_AMT");
			obj[8] = map.get("ZH_AMT");
			obj[9] = map.get("JC_AMT");
			obj[10] = map.get("THIS_U_AMT");
			obj[11] = map.get("PRICE");
			list.add(obj);
		}
		return list;
	}
	
	public List getRpt1DetailData(Report report, String strdate, String endate, String br_cde,
			String cmp, String itemtype, String foodtype,String sup_cde,String item_cde,
			String item_cde2,String chain_cde,String region) {
		List list = new ArrayList();
		List<Map<String, Object>> summarys = this.usingDetailReport(report);
		for (int i = 0; i < summarys.size(); i++) {
			Map<String, Object> map = summarys.get(i);
			logger.debug("########—————————————————detail == "+i+"———————————————########");
			Object[] obj = new Object[17];
			obj[0] = map.get("ITEM_CAT_DESC");
			obj[1] = map.get("ITEM_CDE");
			obj[2] = map.get("ITEM_DESC");
			obj[3] = map.get("ITEM_ACC_CDE");
			obj[4] = map.get("ITEM_PACKING");
			obj[5] = map.get("UNIT");
			obj[6] = map.get("LJC_COUNT");
			obj[7] = map.get("GH_COUNT");
			obj[8] = map.get("ZH_COUT");
			obj[9] = map.get("JC_COUNT");
			obj[10] = map.get("THIS_U_COUNT");
			obj[11] = map.get("LJC_AMT");
			obj[12] = map.get("GH_AMT");
			obj[13] = map.get("ZH_AMT");
			obj[14] = map.get("JC_AMT");
			obj[15] = map.get("THIS_U_AMT");
			obj[16] = map.get("PRICE");
			list.add(obj);
		}
		return getRpt1DetailList(list);
	}
	
	public List getRpt1SourceData(Report report, String strdate, String endate, String br_cde,
			String cmp, String itemtype, String foodtype,String sup_cde,String item_cde,
			String item_cde2,String chain_cde,String region) {
		List list = new ArrayList();
		List<Map<String, Object>> summarys = this.usingSourceReport(report);
		for (int i = 0; i < summarys.size(); i++) {
			Map<String, Object> map = summarys.get(i);
			logger.debug("########—————————————————source == "+i+"———————————————########");
			Object[] obj = new Object[18];
			obj[0] = ((Map<String, Object>) ((Map<String, Object>) map.get("X")).get("NAME||MS")).get("BR_NAME");
			obj[1] = map.get("ITEM_CAT_DESC");
			obj[2] = map.get("ITEM_CDE");
			obj[3] = map.get("ITEM_DESC");
			obj[4] = map.get("ITEM_ACC_CDE");
			obj[5] = map.get("ITEM_PACKING");
			obj[6] = map.get("UNIT");
			obj[7] = map.get("LJC_COUNT");
			obj[8] = map.get("GH_COUNT");
			obj[9] = map.get("ZH_COUT");
			obj[10] = map.get("JC_COUNT");
			obj[11] = map.get("THIS_U_COUNT");
			obj[12] = map.get("LJC_AMT");
			obj[13] = map.get("GH_AMT");
			obj[14] = map.get("ZH_AMT");
			obj[15] = map.get("JC_AMT");
			obj[16] = map.get("THIS_U_AMT");
			obj[17] = map.get("PRICE");
			list.add(obj);
		}
		return list;
	}
	
	public List getRpt1DetailList(List details) {
		List last = new ArrayList();
		//last = list;
		String catfalg = "";
		double d4 = 0;
		double d5 = 0;
		double d6 = 0;
		double d7 = 0;
		double d8 = 0;
		double d9 = 0;
		double d10 = 0;
		double d11 = 0;
		double d12 = 0;
		double d13 = 0;

		for (int i = 0; i < details.size(); i++) {
			Object[] subs = (Object[]) details.get(i);
			String item_cat = (subs[0] == null ? "" : subs[0].toString().trim());
//			logger.debug(i+"-"+list.size());
			if (catfalg.equals(item_cat)) {
				
				if(i==details.size()-1){
					last.add(subs);
					
					d13 += subs[15] == null ? 0 : Double.parseDouble(subs[15]
					                          							.toString());
  					d4 += subs[6] == null ? 0 : Double.parseDouble(subs[6]
  							.toString());
  					d5 += subs[7] == null ? 0 : Double.parseDouble(subs[7]
  							.toString());
  					d6 += subs[8] == null ? 0 : Double.parseDouble(subs[8]
  							.toString());
  					d7 += subs[9] == null ? 0 : Double.parseDouble(subs[9]
  							.toString());
  					d8 += subs[10] == null ? 0 : Double.parseDouble(subs[10]
  							.toString());
  					d9 += subs[11] == null ? 0 : Double.parseDouble(subs[11]
  							.toString());
  					d10 += subs[12] == null ? 0 : Double.parseDouble(subs[12]
  							.toString());
  					d11 += subs[13] == null ? 0 : Double.parseDouble(subs[13]
  							.toString());
  					d12 += subs[14] == null ? 0 : Double.parseDouble(subs[14]
  							.toString());

					Object[] obj = new Object[] { "", catfalg + "&汇总", "","", "","",
							d4, d5, d6, d7, d8, d9, d10, d11, d12, d13,
							d8 == 0 ? 0 : d13 / d8 };
					last.add(obj);
					d13 = 0;d4 = 0;d5 = 0;d6 = 0;d7 = 0;d8 = 0;
					d9 = 0;d10 = 0;d11 = 0;d12 = 0;
					catfalg = item_cat;
					
				}else{
						d13 += subs[15] == null ? 0 : Double.parseDouble(subs[15]
								.toString());
						d4 += subs[6] == null ? 0 : Double.parseDouble(subs[6]
								.toString());
						d5 += subs[7] == null ? 0 : Double.parseDouble(subs[7]
								.toString());
						d6 += subs[8] == null ? 0 : Double.parseDouble(subs[8]
								.toString());
						d7 += subs[9] == null ? 0 : Double.parseDouble(subs[9]
								.toString());
						d8 += subs[10] == null ? 0 : Double.parseDouble(subs[10]
								.toString());
						d9 += subs[11] == null ? 0 : Double.parseDouble(subs[11]
								.toString());
						d10 += subs[12] == null ? 0 : Double.parseDouble(subs[12]
								.toString());
						d11 += subs[13] == null ? 0 : Double.parseDouble(subs[13]
								.toString());
						d12 += subs[14] == null ? 0 : Double.parseDouble(subs[14]
								.toString());
						last.add(subs);
						catfalg = item_cat;
				}
			} else {// 产品类别不同
				// 1：第一次循环
				if (i == 0) {
					d13 += subs[15] == null ? 0 : Double.parseDouble(subs[15]
							.toString());
					d4 += subs[6] == null ? 0 : Double.parseDouble(subs[6]
							.toString());
					d5 += subs[7] == null ? 0 : Double.parseDouble(subs[7]
							.toString());
					d6 += subs[8] == null ? 0 : Double.parseDouble(subs[8]
							.toString());
					d7 += subs[9] == null ? 0 : Double.parseDouble(subs[9]
							.toString());
					d8 += subs[10] == null ? 0 : Double.parseDouble(subs[10]
							.toString());
					d9 += subs[11] == null ? 0 : Double.parseDouble(subs[11]
							.toString());
					d10 += subs[12] == null ? 0 : Double.parseDouble(subs[12]
							.toString());
					d11 += subs[13] == null ? 0 : Double.parseDouble(subs[13]
							.toString());
					d12 += subs[14] == null ? 0 : Double.parseDouble(subs[14]
							.toString());
					last.add(subs);
					catfalg = item_cat;
				} else if (i == details.size() - 1) {
					
					System.out.println("最后一项了。。。。。。");
					last.add(subs);
					
					d13 += subs[15] == null ? 0 : Double.parseDouble(subs[15]
					                          							.toString());
  					d4 += subs[6] == null ? 0 : Double.parseDouble(subs[6]
  							.toString());
  					d5 += subs[7] == null ? 0 : Double.parseDouble(subs[7]
  							.toString());
  					d6 += subs[8] == null ? 0 : Double.parseDouble(subs[8]
  							.toString());
  					d7 += subs[9] == null ? 0 : Double.parseDouble(subs[9]
  							.toString());
  					d8 += subs[10] == null ? 0 : Double.parseDouble(subs[10]
  							.toString());
  					d9 += subs[11] == null ? 0 : Double.parseDouble(subs[11]
  							.toString());
  					d10 += subs[12] == null ? 0 : Double.parseDouble(subs[12]
  							.toString());
  					d11 += subs[13] == null ? 0 : Double.parseDouble(subs[13]
  							.toString());
  					d12 += subs[14] == null ? 0 : Double.parseDouble(subs[14]
  							.toString());

					Object[] obj = new Object[] { "", catfalg + "&汇总", "","", "","",
							d4, d5, d6, d7, d8, d9, d10, d11, d12, d13,
							d8 == 0 ? 0 : d13 / d8 };
					last.add(obj);
					d13 = 0;d4 = 0;d5 = 0;d6 = 0;d7 = 0;d8 = 0;
					d9 = 0;d10 = 0;d11 = 0;d12 = 0;
					catfalg = item_cat;
				} else {
					// 2：变换了产品类别这个时候需条件一天汇总数据
					Object[] obj = new Object[] { "", catfalg + "&汇总", "","", "","",
							d4, d5, d6, d7, d8, d9, d10, d11, d12, d13,
							d8 == 0 ? 0 : d13 / d8 };
					last.add(obj);
					last.add(subs);
					d13 = 0;d4 = 0;d5 = 0;d6 = 0;d7 = 0;
					d8 = 0;d9 = 0;d10 = 0;d11 = 0;d12 = 0;
					
					d13 += subs[15] == null ? 0 : Double.parseDouble(subs[15]
					                          							.toString());
				d4 += subs[6] == null ? 0 : Double.parseDouble(subs[6]
						.toString());
				d5 += subs[7] == null ? 0 : Double.parseDouble(subs[7]
						.toString());
				d6 += subs[8] == null ? 0 : Double.parseDouble(subs[8]
						.toString());
				d7 += subs[9] == null ? 0 : Double.parseDouble(subs[9]
						.toString());
				d8 += subs[10] == null ? 0 : Double.parseDouble(subs[10]
						.toString());
				d9 += subs[11] == null ? 0 : Double.parseDouble(subs[11]
						.toString());
				d10 += subs[12] == null ? 0 : Double.parseDouble(subs[12]
						.toString());
				d11 += subs[13] == null ? 0 : Double.parseDouble(subs[13]
						.toString());
				d12 += subs[14] == null ? 0 : Double.parseDouble(subs[14]
						.toString());
								
					catfalg = item_cat;
				}

			}
		}
		return last;
	}
	
	//public HSSFCellStyle cs1;
	private static ThreadLocal<HSSFCellStyle> tcs1 = new ThreadLocal<HSSFCellStyle>();

	public HSSFWorkbook getRpt1Ws(Report report, String filePth, String strdate,
			String endate, String br_cde, String cmp, String itemtype,
			String foodtype, String rpttype,String sup_cde,String item_cde,
			String item_cde2,String chain_cde,String region) {
		HSSFWorkbook ws = null;
		File f = new File(filePth);
		if (f.exists()) {
			try {
				FileInputStream fs = new FileInputStream(f);
				ws = new HSSFWorkbook(fs);

				HSSFCellStyle cs = ws.createCellStyle();
				HSSFDataFormat format = ws.createDataFormat();
				cs.setDataFormat(format.getFormat("#,##0.00"));
				tcs1.set(cs);
				
				if (rpttype.equals("hz")) {
					// 请求汇总数据
					List datasummary = this.getRpt1SummarData(report, strdate, endate,
							br_cde, cmp, itemtype, foodtype,sup_cde,item_cde,item_cde2,chain_cde,region);
					 if(datasummary==null||datasummary.size()==0){
						 return null;
					 }
					picSheetOfSummary(ws, datasummary, strdate, endate, br_cde,
							cmp, itemtype, foodtype,sup_cde,item_cde,item_cde2,chain_cde,region);
				} else if(rpttype.equals("mx")) {
					// 请求明细数据
					List dataDetail = this.getRpt1DetailData(report, strdate, endate,
							br_cde, cmp, itemtype, foodtype,sup_cde,item_cde,item_cde2,chain_cde,region);
					
					 if(dataDetail==null||dataDetail.size()==0){
						 return null;
					 }
					
					picSheetOfDetail(ws, dataDetail, strdate, endate, br_cde,
							cmp, itemtype, foodtype,sup_cde,item_cde,item_cde2,chain_cde,region);
				}else{
					List dataSource = getRpt1SourceData(report, strdate, endate,
							br_cde, cmp, itemtype, foodtype,sup_cde,item_cde,item_cde2,chain_cde,region);
					 if(dataSource==null||dataSource.size()==0){
						 return null;
					 }
					
					picSheetOfSource(ws, dataSource, strdate, endate, br_cde,
							cmp, itemtype, foodtype,sup_cde,item_cde,item_cde2,chain_cde,region);

				}

			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("用量表 >>" + e.getMessage());
			}

		} else {
			System.out.println("File is not Exist!");
		}

		return ws;

	}
	
	private void picSheetOfSource(HSSFWorkbook ws, List data, String strdate,
			String endate, String br_cde, String cmp, String itemtype,
			String foodType,String sup_cde,String item_cde1,String item_cde2,
			String chain_cde,String region) {
		HSSFSheet sheet = ws.getSheetAt(0);// 获得sheet

		PicHead(strdate, endate, br_cde, cmp, itemtype, sheet, foodType,sup_cde, 
				item_cde1, item_cde2, chain_cde,region);
		
		int size = data.size();
		
		HSSFSheet[] sheets = new HSSFSheet[size / 65000];
		
		for (int i = 0; i < size / 65000; i++) {
			sheets[i] = ws.createSheet("sheet" + (i+1));
		}

		for (int i = 0; i < size; i++) {
			Object[] subs = (Object[]) data.get(i);
			
			if ( (i / 65000) > 0 ) {
				int index = i / 65000 - 1;
				int row = i % 65000;
				HSSFRow r = sheets[index].getRow(row) == null ? sheets[index].createRow(row)
						: sheets[index].getRow(row);
				for (int j = 0; j < subs.length; j++) {
					HSSFCell c = r.getCell(j) == null ? r.createCell(j) : r
							.getCell(j);
					if (j <= 6) {
						c.setCellValue(subs[j] == null ? "" : subs[j].toString());
					} else {
						c.setCellStyle(tcs1.get());
						c.setCellValue(subs[j] == null ? 0 : Double
								.parseDouble(subs[j].toString()));
					}

				}
			} 
			else {
				HSSFRow r = sheet.getRow(i + 12) == null ? sheet.createRow(i + 12)
						: sheet.getRow(i + 12);
				for (int j = 0; j < subs.length; j++) {
					HSSFCell c = r.getCell(j) == null ? r.createCell(j) : r
							.getCell(j);
					if (j <= 6) {
						c.setCellValue(subs[j] == null ? "" : subs[j].toString());
					} else {
						c.setCellStyle(tcs1.get());
						c.setCellValue(subs[j] == null ? 0 : Double
								.parseDouble(subs[j].toString()));
					}

				}
			}
			
			
		}	//end for
	}

	/**
	 * 寫Excel sheet1
	 * 
	 * @param strdate
	 * @param endate
	 * @param item_cde
	 * @param br_cde
	 * @param cmp
	 */
	private void picSheetOfDetail(HSSFWorkbook ws, List data, String strdate,
			String endate, String br_cde, String cmp, String itemtype,
			String foodType,String sup_cde,String item_cde1,String item_cde2,String chain_cde,String region) {
		HSSFSheet sheet = ws.getSheetAt(0);// 获得sheet

		PicHead(strdate, endate, br_cde, cmp, itemtype, sheet, foodType,sup_cde, item_cde1, item_cde2, chain_cde,region);

		int rowfrom = 10;
		int rowstep = 0;
		String itemcatfalg = "";
		int size = data.size();
		
		HSSFSheet[] sheets = new HSSFSheet[size / 65000];
		
		for (int i = 0; i < size / 65000; i++) {
			sheets[i] = ws.createSheet("sheet" + (i+1));
		}
		
		for (int i = 0; i < size; i++) {
			Object[] subs = (Object[]) data.get(i);
			
			if ( (i / 65000) > 0 ) {
				int index = i / 65000 - 1;
				int row = i % 65000;
				HSSFRow r = sheets[index].getRow(row) == null ? sheets[index].createRow(row)
						: sheets[index].getRow(row);
				String itemCatMove = subs[0] == null ? "" : subs[0].toString()
						.trim();// 移动变量
				for (int j = 0; j < subs.length; j++) {
					HSSFCell c = r.getCell(j) == null ? r.createCell(j) : r
							.getCell(j);
					if (j <= 5) {
						c.setCellValue(subs[j] == null ? "" : subs[j].toString());
					} else {
						c.setCellStyle(tcs1.get());
						c.setCellValue(subs[j] == null ? 0 : Double
								.parseDouble(subs[j].toString()));
					}

				}
			} 
			else {
				HSSFRow r = sheet.getRow(i + 12) == null ? sheet.createRow(i + 12)
						: sheet.getRow(i + 12);
				String itemCatMove = subs[0] == null ? "" : subs[0].toString()
						.trim();// 移动变量
				for (int j = 0; j < subs.length; j++) {
					HSSFCell c = r.getCell(j) == null ? r.createCell(j) : r
							.getCell(j);
					if (j <= 5) {
						c.setCellValue(subs[j] == null ? "" : subs[j].toString());
					} else {
						c.setCellStyle(tcs1.get());
						c.setCellValue(subs[j] == null ? 0 : Double
								.parseDouble(subs[j].toString()));
					}

				}
			}
			
		}	//end for
	}

	/**
	 * 寫Excel sheet1
	 * 
	 * @param strdate
	 * @param endate
	 * @param item_cde
	 * @param br_cde
	 * @param cmp
	 */
	private void picSheetOfSummary(HSSFWorkbook ws, List data, String strdate,
			String endate, String br_cde, String cmp, String itemtype,
			String foodtype,String sup_cde,String item_cde1,
			String item_cde2,String chain_cde,String region) {
		HSSFSheet sheet = ws.getSheetAt(0);// 获得sheet

		PicHead(strdate, endate, br_cde, cmp, itemtype, sheet, foodtype,sup_cde, item_cde1, item_cde2, chain_cde,region);
		double[] last = new double[10];

		for (int i = 0; i < data.size(); i++) {
			Object[] subs = (Object[]) data.get(i);
			HSSFRow r = sheet.getRow(i + 12) == null ? sheet.createRow(i + 12)
					: sheet.getRow(i + 12);

			for (int j = 0; j < subs.length; j++) {

				switch (j) {
				case 1:
					last[0] += subs[j] == null ? 0 : Double.parseDouble(subs[j]
							.toString());
					break;
				case 2:
					last[1] += subs[j] == null ? 0 : Double.parseDouble(subs[j]
							.toString());
					break;
				case 3:
					last[2] += subs[j] == null ? 0 : Double.parseDouble(subs[j]
							.toString());
					break;
				case 4:
					last[3] += subs[j] == null ? 0 : Double.parseDouble(subs[j]
							.toString());
					break;
				case 5:
					last[4] += subs[j] == null ? 0 : Double.parseDouble(subs[j]
							.toString());
					break;
				case 6:
					last[5] += subs[j] == null ? 0 : Double.parseDouble(subs[j]
							.toString());
					break;
				case 7:
					last[6] += subs[j] == null ? 0 : Double.parseDouble(subs[j]
							.toString());
					break;
				case 8:
					last[7] += subs[j] == null ? 0 : Double.parseDouble(subs[j]
							.toString());
					break;
				case 9:
					last[8] += subs[j] == null ? 0 : Double.parseDouble(subs[j]
							.toString());
					break;

				case 10:
					last[9] += subs[j] == null ? 0 : Double.parseDouble(subs[j]
							.toString());
					break;

				default:
					break;
				}

				HSSFCell c = r.getCell(j) == null ? r.createCell(j) : r
						.getCell(j);
				if (j == 0) {
					c.setCellValue(subs[j] == null ? "" : subs[j].toString());
				} else {
					c.setCellValue(subs[j] == null ? 0.0 : Double
							.parseDouble(subs[j].toString()));
					c.setCellStyle(tcs1.get());
				}

			}
		}

		// sheetmain.shiftRows(i +rowStr1, sheetmain.getLastRowNum(),
		// 1, true, false);
		// r = sheetmain.createRow(i + rowStr1);

		HSSFRow rlast = sheet.createRow(sheet.getLastRowNum() + 1);

		HSSFCell rlast_c1 = rlast.createCell(0);
		rlast_c1.setCellValue("总计");

		for (int i = 0; i < last.length; i++) {
			HSSFCell c = rlast.createCell(i + 1);
			c.setCellValue(last[i]);
			c.setCellStyle(tcs1.get());
		}
		HSSFCell rlast_c12 = rlast.createCell(last.length + 1);
		rlast_c12.setCellValue(last[4] == 0 ? 0.0 : (last[9] / last[4]));// 单价
		rlast_c12.setCellStyle(tcs1.get());
	}

	private void PicHead(String strdate, String endate, String br_cde,
			String cmp, String itemtype, HSSFSheet sheet, String foodtype,
			String sup_cde,String item_cde1, String item_cde2,String chain_cde,String region) {
		HSSFRow r3 = sheet.getRow(2) == null ? sheet.createRow(2) : sheet
				.getRow(2);
		HSSFCell c3_2 = r3.getCell(1) == null ? r3.createCell(1) : r3
				.getCell(1);
		c3_2.setCellValue(strdate + " 至  " + endate);

		HSSFRow r4 = sheet.getRow(3) == null ? sheet.createRow(3) : sheet
				.getRow(3);
		HSSFCell c4_2 = r4.getCell(1) == null ? r4.createCell(1) : r4
				.getCell(1);
		c4_2.setCellValue(cmp == null || cmp.equals("")?"全部":cmp);

		HSSFRow r5 = sheet.getRow(4) == null ? sheet.createRow(4) : sheet
				.getRow(4);
		HSSFCell c5_2 = r5.getCell(1) == null ? r5.createCell(1) : r5
				.getCell(1);
		c5_2.setCellValue( (region == null || region.equals("")?"":region)+(br_cde == null || br_cde.equals("") ? "全部" : br_cde));

		HSSFRow r6 = sheet.getRow(5) == null ? sheet.createRow(5) : sheet
				.getRow(5);
		HSSFCell c6_2 = r6.getCell(1) == null ? r6.createCell(1) : r6
				.getCell(1);
		c6_2.setCellValue(itemtype == null || itemtype.equals("") ? "全部" : itemtype);

		HSSFRow r7 = sheet.getRow(6) == null ? sheet.createRow(6) : sheet
				.getRow(6);
		HSSFCell c7_2 = r7.getCell(1) == null ? r7.createCell(1) : r7
				.getCell(1);
		c7_2.setCellValue(foodtype == null || foodtype.equals("") ? "全部"
				: foodtype.equals("food") ? "食物" : "非食物");
		
		HSSFRow r8= sheet.getRow(7) == null ? sheet.createRow(7) : sheet
				.getRow(7);
		HSSFCell c8_2 = r8.getCell(1) == null ? r8.createCell(1) : r8
				.getCell(1);
		c8_2.setCellValue(chain_cde == null || chain_cde.equals("") ? "全部"
				: chain_cde);

		HSSFRow r9= sheet.getRow(8) == null ? sheet.createRow(8) : sheet
				.getRow(8);
		HSSFCell c9_2 = r9.getCell(1) == null ? r9.createCell(1) : r9
				.getCell(1);
		c9_2.setCellValue(item_cde1 == null || item_cde1.equals("") ? "全部"
				: item_cde1+"-"+item_cde2);

		
		HSSFRow r10= sheet.getRow(9) == null ? sheet.createRow(9) : sheet
				.getRow(9);
		HSSFCell c10_2 = r10.getCell(1) == null ? r10.createCell(1) : r10
				.getCell(1);
		c10_2.setCellValue(sup_cde == null || sup_cde.equals("") ? "全部"
				: sup_cde);

		
	}
	
	/************************************************
	 * 
	 * 			来货			
	 *  
	 ************************************************/
	
	public List getDataOfSheet1(Report report, String strdate, String endate, String br_cde,
			String cmp, String itemtype, String foodtype, String sup_cde,String chain_cde,
			String item1,String item2,String region) {
		List list = new ArrayList();
		List<Map<String, Object>> summarys = this.subInvoiceSummary(report);
		for (int i = 0; i < summarys.size(); i++) {
			Map<String, Object> map = summarys.get(i);
			logger.debug("########—————————————————invoice_summary == "+i+"———————————————########");
			Object[] obj = new Object[5];
			obj[0] = map.get("SEQ");
			obj[1] = map.get("SUP_CDE");
			obj[2] = map.get("SUP_NAME");
			obj[3] = map.get("INVDATE");
			obj[4] = map.get("SUM(INV_AMT)");
			list.add(obj);
		}
		return list;
	}
	
	public List getDataOfSheet2(Report report, String strdate, String endate, String br_cde,
			String cmp, String itemtype, String foodtype, String sup_cde,
			String item_cde1, String inv_no,String chain_cde,String item_cde2,String region) {
		List list = new ArrayList();
		List<Map<String, Object>> details = this.invoiceDetail(report);
		for (int i = 0; i < details.size(); i++) {
			Map<String, Object> map = details.get(i);
			logger.debug("########—————————————————invoice_detail == "+i+"———————————————########");
			Object[] obj = new Object[12];
			obj[0] = map.get("NAME");
			obj[1] = map.get("RECEIVE_DATE");
			obj[2] = map.get("ITEM_CDE");
			obj[3] = map.get("REPLACE_ITEM_DESC");
			obj[4] = map.get("ITEM_ACC_CDE");
			obj[5] = map.get("REPLACE_PACKING");
			obj[6] = map.get("UOM_CDE");
			obj[7] = map.get("F_QTY");
			//obj[8] = map.get("CONV_FACTOR");
			obj[8] = map.get("F_AMT");
			obj[9] = map.get("PRC1");
			obj[10] = map.get("PRC2");
			obj[11] = map.get("PRC3");
			list.add(obj);
		}
		return list;
	}
	
	public List getDataOfSheet3(Report report, String strdate, String endate, String br_cde,
			String cmp, String itemtype, String foodtype, String sup_cde,
			String item_cde1, String inv_no,String chain_cde,String item_cde2,String region) {
		List list = new ArrayList();
		List<Map<String, Object>> details = this.invoiceItemDetail(report);
		for (int i = 0; i < details.size(); i++) {
			Map<String, Object> map = details.get(i);
			logger.debug("########—————————————————invoice_detail == "+i+"———————————————########");
			Object[] obj = new Object[9];
			obj[0] = map.get("ITEM_CDE");
			obj[1] = map.get("ITEM_DESC");
			obj[2] = map.get("ITEM_ACC_CDE");
			obj[3] = map.get("PACKING");
			obj[4] = map.get("UOM_CDE");
			obj[5] = map.get("ITEM_PRICE");
			obj[6] = map.get("SUM(RPT_QTY)");
			obj[7] = map.get("SUM(KG_QTY)");
			obj[8] = map.get("SUM(AMT)");
			list.add(obj);
		}
		return list;
	}
	
	public List getDataOfSheet4(Report report, String strdate, String endate, String br_cde,
			String cmp, String itemtype, String foodtype, String sup_cde,
			String item_cde1, String inv_no,String chain_cde,String item_cde2,String region) {
		List list = new ArrayList();
		List<Map<String, Object>> sources = this.invoiceData(report);
		for (int i = 0; i < sources.size(); i++) {
			Map<String, Object> map = sources.get(i);
			logger.debug("########—————————————————invoice_source == "+i+"———————————————########");
			Object[] obj = new Object[13];
			obj[0] = map.get("SUP_CDE");
			obj[1] = map.get("SUP_NAME");
			obj[2] = map.get("RECEIVE_DATE");
			obj[3] = map.get("NAME");
			obj[4] = map.get("ITEM_CAT_DESC");
			obj[5] = map.get("ITEM_CDE");
			obj[6] = map.get("ITEM_DESC");
			obj[7] = map.get("ITEM_ACC_CDE");
			obj[8] = map.get("PACKING");
			obj[9] = map.get("UOM_CDE");
			obj[10] = map.get("SUM(KG_QTY)");
			obj[11] = map.get("SUM(RPT_QTY)");
			obj[12] = map.get("SUM(AMT)");
			list.add(obj);
		}
		return list;
	}
	
	//private HSSFCellStyle cs11;
	//private HSSFCellStyle cs2;
	private static ThreadLocal<HSSFCellStyle> tcs11 = new ThreadLocal<HSSFCellStyle>();
	private static ThreadLocal<HSSFCellStyle> tcs2 = new ThreadLocal<HSSFCellStyle>();
	
	public HSSFWorkbook getRpt2Ws(Report report, String filePth, String strdate,
			String endate, String br_cde, String cmp, String itemtype,
			String foodtype,String sup_cde, String item_cde1,String inv_no,
			String chain_cde,String item_cde2,String region,String rptType) {

		HSSFWorkbook ws = null;
		File f = new File(filePth);
		if (f.exists()) {
			try {
				FileInputStream fs = new FileInputStream(f);
				ws = new HSSFWorkbook(fs);

				 HSSFCellStyle cs11 = ws.createCellStyle();
				 HSSFDataFormat format = ws.createDataFormat();
				 cs11.setDataFormat(format.getFormat("#,##0.00"));
				 
				 HSSFCellStyle cs2 = ws.createCellStyle();
				 
				 tcs11.set(cs11);
				 tcs2.set(cs2);
				 if(rptType.equals("hz")){
					 List lis1=getDataOfSheet1(report, strdate, endate, br_cde, cmp, itemtype, foodtype, sup_cde,chain_cde,item_cde1,item_cde2,region);
						if(lis1==null||lis1.size()==0){
							return null;
						}
					 
					 setValueOfSheet1(lis1, ws,cs11,cs2, strdate, endate, br_cde, cmp, itemtype, foodtype, sup_cde, item_cde1, inv_no,item_cde2,chain_cde,region);
				 }else if(rptType.equals("mx")){
					 	List lis2=getDataOfSheet2(report, strdate, endate, br_cde, cmp, itemtype, foodtype, sup_cde, item_cde1, inv_no,chain_cde,item_cde2,region);
						List lis3=getDataOfSheet3(report, strdate, endate, br_cde, cmp, itemtype, foodtype, sup_cde, item_cde1, inv_no,chain_cde,item_cde2,region);
						
						if(lis2==null&&lis3==null){
							return null;
						}
						if(lis2.size()==0&&lis3.size()==0){
							return null;
						}
						
						
						setValueOfSheet2(lis2, ws,cs11,cs2, strdate, endate, br_cde, cmp, itemtype, foodtype, sup_cde, item_cde1, inv_no,item_cde2,chain_cde,region);
						setValueOfSheet3(lis3, ws, cs11,cs2,strdate, endate, br_cde, cmp, itemtype, foodtype, sup_cde, item_cde1, inv_no,item_cde2,chain_cde,region);
				 }else{//源数据
						List lis4=getDataOfSheet4(report, strdate, endate, br_cde, cmp, itemtype, foodtype, sup_cde, item_cde1, inv_no,chain_cde,item_cde2,region);
						if(lis4==null||lis4.size()==0){
							return null;
						}
						
						
						setValueOfSheet4(lis4, ws,cs11,cs2, strdate, endate, br_cde, cmp, itemtype, foodtype, sup_cde, item_cde1, inv_no,item_cde2,chain_cde,region);
				 }
				 
				
				
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("FAC门店来货明细汇总 >>" + e.getMessage());
			}

		} else {
			System.out.println("File is not Exist!");
		}

		return ws;
	}
	
	public void setValueOfSheet1(List data,HSSFWorkbook ws, HSSFCellStyle cs11,HSSFCellStyle cs2, String strdate, String endate, String br_cde,
			String cmp, String itemtype, String foodtype, String sup_cde,String item_cde,String inv_no,
			String item_cde2,String chain_Cde,String region){
			HSSFSheet sheet=ws.getSheetAt(0)==null?ws.createSheet():ws.getSheetAt(0);
			picHead(sheet, strdate, endate, br_cde, cmp, itemtype, foodtype, sup_cde, item_cde,
					inv_no,item_cde2,chain_Cde,region);
		
			Double total = 0.00;
			
			for (int i = 0; i < data.size(); i++) {
				Object[] columns = (Object[]) data.get(i);
				if ("2".equals(columns[0].toString())) {
					total += Double.parseDouble(columns[4].toString());
				}
			}
			
			Object[] all = {3, "ALL", "ALL", "-", total};
			data.add(all);
			
		for (int i = 0; i < data.size(); i++) {
			Object[] subs=(Object[]) data.get(i);
			
			HSSFRow r=sheet.getRow(i+13)==null?sheet.createRow(i+13):sheet.getRow(i+13);
			for (int j =1; j < subs.length; j++) {
				HSSFCell c=r.createCell((short)(j-1));
				if(j<=3){
					c.setCellStyle(cs2);
					c.setCellValue(subs[j]+"");
				}else{
					c.setCellStyle(cs11);
					c.setCellValue(subs[j]==null?0:Double.parseDouble(subs[j].toString()));
				}
				
				if (j == 3 && "-".equals(subs[j])) {
					int index = i - 1;
					String sup_code = (String) ((Object[]) data.get(index))[1];
					String sup_name = (String) ((Object[]) data.get(index))[2];
					
					while (sup_code != null && sup_code.equals(subs[1])) {
						if (sup_name != null && !"".equals(sup_name)) {
							sup_name = subs[2].toString().replace("—汇总", sup_name + "—汇总");
							HSSFCell sup_name_cell = r.getCell((short)1);
							sup_name_cell.setCellValue(sup_name);
							break;
						}
						index--;
						sup_code = (String) ((Object[]) data.get(index))[1];
						sup_name = (String) ((Object[]) data.get(index))[2];
					}
					
					Double percent = Double.parseDouble(subs[4].toString()) / total * 100;
					HSSFCell cell=r.createCell((short)(4));
					cell.setCellStyle(cs11);
					cell.setCellValue(percent);
				}
			}
			
		}
		
	}
	
	public void setValueOfSheet2(List data,HSSFWorkbook ws,HSSFCellStyle cs11,HSSFCellStyle cs2,String strdate, String endate, String br_cde,
			String cmp, String itemtype, String foodtype, String sup_cde,String item_cde,
			String inv_no,String item_cde2,String chain_cde,String region){
		HSSFSheet sheet=ws.getSheetAt(1)==null?ws.createSheet():ws.getSheetAt(0);
		picHead(sheet, strdate, endate, br_cde, cmp, itemtype, foodtype, sup_cde, item_cde, 
				inv_no,item_cde2,chain_cde,region);
		//double count = 0;
		for (int i = 0; i < data.size(); i++) {
			Object[] subs=(Object[]) data.get(i);
			HSSFRow r=sheet.getRow(i+13)==null?sheet.createRow(i+13):sheet.getRow(i+13);
			for (int j = 0; j < subs.length; j++) {
				HSSFCell c=r.createCell((short)j);
				
				if(j<=6){
					c.setCellStyle(cs2);
					c.setCellValue(subs[j]==null?"":subs[j].toString().trim());
				} /*else if (j == 8) {
					c.setCellStyle(cs11);
					if (subs[1] != null && subs[1].toString().contains("小计")) {
						c.setCellValue(count);
						count = 0;
					} else {
						Double qty = (subs[7] == null ? 0 : Double.parseDouble(subs[7].toString()));
						Double conv = (subs[j] == null ? 0 : Double.parseDouble(subs[j].toString()));
						count += qty * conv;
						c.setCellValue(qty * conv);
					}
				}*/ else {
					c.setCellStyle(cs11);
					c.setCellValue(subs[j]==null?0:Double.parseDouble(subs[j].toString()));
				}
			}
		}
	}
	
	public void setValueOfSheet3(List data,HSSFWorkbook ws,HSSFCellStyle cs11,HSSFCellStyle cs2,String strdate, String endate, String br_cde,
			String cmp, String itemtype, String foodtype, String sup_cde,String item_cde,
			String inv_no,String item_cde2,String chain_cde,String region){
		HSSFSheet sheet=ws.getSheetAt(1)==null?ws.createSheet():ws.getSheetAt(1);
		picHead(sheet, strdate, endate, br_cde, cmp, itemtype, foodtype, sup_cde, item_cde, 
				inv_no,item_cde2,chain_cde,region);
		for (int i = 0; i < data.size(); i++) {
			Object[] subs=(Object[]) data.get(i);
			HSSFRow r=sheet.getRow(i+13)==null?sheet.createRow(i+13):sheet.getRow(i+13);
			for (int j = 0; j < subs.length; j++) {
				HSSFCell c=r.createCell((short)j);
				if(j<=4){
					c.setCellStyle(cs2);
					c.setCellValue(subs[j]==null?"":subs[j].toString().trim());
				}else{
					c.setCellStyle(cs11);
					c.setCellValue(subs[j]==null?0:Double.parseDouble(subs[j].toString()));
				}
			}
		}
	}
	
	public void setValueOfSheet4(List data,HSSFWorkbook ws,HSSFCellStyle cs11,HSSFCellStyle cs2,String strdate, String endate, String br_cde,
			String cmp, String itemtype, String foodtype, String sup_cde,String item_cde,
			String inv_no,String item_cde2,String chain_cde,String region){
		HSSFSheet sheet=ws.getSheetAt(0)==null?ws.createSheet():ws.getSheetAt(0);
		
		int size = data.size();
		
		System.out.println("%%%%%%%%%%%%====== "+size+" =====%%%%%%%%%%%%");
		HSSFSheet[] sheets = new HSSFSheet[size / 65000];
		
		for (int i = 0; i < size / 65000; i++) {
			sheets[i] = ws.createSheet("sheet" + (i+1));
		}
		
		picHead(sheet, strdate, endate, br_cde, cmp, itemtype, foodtype, sup_cde,
				item_cde, inv_no,item_cde2,chain_cde,region);
		for (int i = 0; i < size; i++) {
			Object[] subs=(Object[]) data.get(i);
			
			if (i / 65000 > 0) {
				int index = (i / 65000) - 1;
				int row = i % 65000;
				HSSFRow r = sheets[index].getRow(row) == null ? sheets[index].createRow(row) : sheets[index].getRow(row);
				for (int j = 0; j < subs.length; j++) {
					HSSFCell c=r.createCell((short)j);
					if(j<=9){
						c.setCellStyle(cs2);
						c.setCellValue(subs[j]==null?"":subs[j].toString().trim());
					}else if(j==10){
						c.setCellStyle(cs2);
						c.setCellValue(subs[j]+"");
					}else{
						c.setCellStyle(cs11);
						c.setCellValue(subs[j]==null?0:Double.parseDouble(subs[j].toString()));
					}
				}
			} 
			else {
				HSSFRow r=sheet.getRow(i+13)==null?sheet.createRow(i+13):sheet.getRow(i+13);
				for (int j = 0; j < subs.length; j++) {
					HSSFCell c=r.createCell((short)j);
					if(j<=9){
						c.setCellStyle(cs2);
						c.setCellValue(subs[j]==null?"":subs[j].toString().trim());
					}else if(j==10){
						c.setCellStyle(cs11);
						c.setCellValue(Double.parseDouble(subs[j].toString()));
					}else{
						c.setCellStyle(cs11);
						c.setCellValue(subs[j]==null?0:Double.parseDouble(subs[j].toString()));
					}
				}
			}
				
			
		}	//end for
	}
	/**
	 * 写查询条件
	 * @param sheet
	 * @param strdate
	 * @param endate
	 * @param br_cde
	 * @param cmp
	 * @param itemtype
	 * @param foodtype
	 * @param sup_cde
	 * @param item_cde
	 * @param inv_no
	 */
	private void picHead(HSSFSheet sheet, String strdate,
			String endate, String br_cde, String cmp, String itemtype,
			String foodtype,String sup_cde, String item_cde1,
			String inv_no,String item_cde2,String chain_cde,String region){
		
		HSSFRow r3=sheet.getRow(2);
		HSSFCell c3_2=r3.getCell(1);
		c3_2.setCellValue(strdate+"/"+endate);
		
		HSSFRow r4=sheet.getRow(3);
		HSSFCell c4_2=r4.getCell(1);
		c4_2.setCellValue(cmp == null || cmp.equals("")?"全部":cmp);
		
		HSSFRow r5=sheet.getRow(4);
		HSSFCell c5_2=r5.getCell(1);
		c5_2.setCellValue( (region == null || region.equals("")?"":region)+(br_cde == null || br_cde.equals("")?"全部":br_cde));
		
		
		HSSFRow r6=sheet.getRow(5);
		HSSFCell c6_2=r6.getCell(1);
		c6_2.setCellValue(foodtype == null || foodtype.equals("")?"全部":foodtype.equals("food")?"食物":"非食物");
		
		HSSFRow r7=sheet.getRow(6);
		HSSFCell c7_2=r7.getCell(1);
		c7_2.setCellValue(sup_cde == null || sup_cde.equals("")?"全部":sup_cde);
		
		HSSFRow r8=sheet.getRow(7);
		HSSFCell c8_2=r8.getCell(1);
		c8_2.setCellValue(itemtype == null || itemtype.equals("")?"全部":itemtype);
		
		HSSFRow r9=sheet.getRow(8);
		HSSFCell c9_2=r9.getCell(1);
		c9_2.setCellValue(item_cde1 == null || item_cde1.equals("")?"全部":item_cde1+"-"+(item_cde2 == null || item_cde2.equals("")?item_cde1:item_cde2));
		HSSFRow r10=sheet.getRow(9);
		HSSFCell c10_2=r10.getCell(1);
		c10_2.setCellValue(inv_no == null || inv_no.equals("")?"全部":inv_no);
		
		//数据链
		HSSFRow r11=sheet.getRow(10);
		HSSFCell c11_2=r11.getCell(1);
		c11_2.setCellValue(chain_cde == null || chain_cde.equals("")?"全部":chain_cde);
		
	}
	
	
	/***********************************************
	 * 
	 * 		转货
	 * 
	 **********************************************/
	
	public List getZHSumIN(Report report, String strdate, String endate, String br_cde_in,
			String br_cde_out,
			String cmp, String itemtype, String foodType, String sup_Cde,
			String item_cde,String item_cde2,String chain_cde,String region) {
		List list = new ArrayList();
		List<Map<String, Object>> summarys = this.tranInSummary(report);
		for (int i = 0; i < summarys.size(); i++) {
			Map<String, Object> map = summarys.get(i);
			logger.debug("########—————————————————tran_summary == "+i+"———————————————########");
			Object[] obj = new Object[4];
			obj[0] = map.get("SEQ");
			obj[1] = map.get("BR_CDE_OUT");
			obj[2] = map.get("BR_CDE_IN");
			obj[3] = map.get("AMTS");
			list.add(obj);
		}
		return list;
	}
	
	public List getZHSumOUT(Report report, String strdate, String endate, String br_cde_in,
			String br_cde_out,
			String cmp, String itemtype, String foodType, String sup_Cde,
			String item_cde,String item_cde2,String chain_cde,String region)  {
		List list = new ArrayList();
		List<Map<String, Object>> summarys = this.tranOutSummary(report);
		for (int i = 0; i < summarys.size(); i++) {
			Map<String, Object> map = summarys.get(i);
			logger.debug("########—————————————————invoice_summary == "+i+"———————————————########");
			Object[] obj = new Object[4];
			obj[0] = map.get("SEQ");
			obj[1] = map.get("BR_CDE_IN");
			obj[2] = map.get("BR_CDE_OUT");
			obj[3] = map.get("AMTS");
			list.add(obj);
		}
		return list;
	}
	
	public List getZHDetailDataOUT(Report report, String strdate, String endate,
			String br_cde_in, String br_cde_out, String cmp, String itemtype,
			String foodtype, String sup_cde, String item_cde,String item_cde2,String chain_cde,String region) {
		List list = new ArrayList();
		List<Map<String, Object>> summarys = this.tranOutDetail(report);
		for (int i = 0; i < summarys.size(); i++) {
			Map<String, Object> map = summarys.get(i);
			logger.debug("########—————————————————invoice_summary == "+i+"———————————————########");
			Object[] obj = new Object[11];
			obj[0] = map.get("SEQ");
			obj[1] = map.get("BR_CDE_IN");
			obj[2] = map.get("BR_CDE_OUT");
			obj[3] = map.get("TRAN_NO");
			obj[4] = map.get("ITEM_CDE");
			obj[5] = map.get("ITEM_DESC");
			obj[6] = map.get("ITEM_ACC_CDE");
			obj[7] = map.get("QTYS");
			obj[8] = map.get("UOM");
			obj[9] = map.get("PRICE");
			obj[10] = map.get("AMTS");
			list.add(obj);
		}
		return list;
	}
	
	public List getZHDetailDataIN(Report report, String strdate, String endate,
			String br_cde_in, String br_cde_out, String cmp, String itemtype,
			String foodtype, String sup_cde, String item_cde,String item_cde2,
			String chain_cde,String region) {
		List list = new ArrayList();
		List<Map<String, Object>> summarys = this.tranInDetail(report);
		for (int i = 0; i < summarys.size(); i++) {
			Map<String, Object> map = summarys.get(i);
			logger.debug("########—————————————————invoice_summary == "+i+"———————————————########");
			Object[] obj = new Object[11];
			obj[0] = map.get("SEQ");
			obj[1] = map.get("BR_CDE_OUT");
			obj[2] = map.get("BR_CDE_IN");
			obj[3] = map.get("TRAN_NO");
			obj[4] = map.get("ITEM_CDE");
			obj[5] = map.get("ITEM_DESC");
			obj[6] = map.get("ITEM_ACC_CDE");
			obj[7] = map.get("QTYS");
			obj[8] = map.get("UOM");
			obj[9] = map.get("PRICE");
			obj[10] = map.get("AMTS");
			list.add(obj);
		}
		return list;
	}
	
	
	
	//public HSSFCellStyle cs111;
	public static ThreadLocal<HSSFCellStyle> tcs111 = new ThreadLocal<HSSFCellStyle>();

	public HSSFWorkbook getRpt3Ws(Report report, String filePth, String strdate,
			String endate, String br_cde_in, String cmp, String itemtype,
			String foodtype, String rpttype, String sup_cde, String item_cde,
			String br_cde_out,String tran_no,String item_cde2,String chain_cde,String region) {
		HSSFWorkbook ws = null;
		File f = new File(filePth);
		if (f.exists()) {
			try {
				FileInputStream fs = new FileInputStream(f);
				ws = new HSSFWorkbook(fs);

				HSSFCellStyle cs111 = ws.createCellStyle();
				HSSFDataFormat format = ws.createDataFormat();
				cs111.setDataFormat(format.getFormat("#,##0.00"));
				tcs111.set(cs111);

				if (rpttype.equals("hz")) {
					// 请求汇总数据
					List hzIN = this.getZHSumIN(report, strdate, endate, br_cde_in, br_cde_out, cmp, itemtype, foodtype, sup_cde, item_cde,item_cde2,chain_cde,region);
					List hzOUT=this.getZHSumOUT(report, strdate, endate, br_cde_in, br_cde_out, cmp, itemtype, foodtype, sup_cde, item_cde,item_cde2,chain_cde,region);
					if(hzIN==null&&hzOUT==null){
						return null;
					}
					
					picSheetOfSumIN(ws, hzIN, strdate, endate, br_cde_in, br_cde_out, cmp, itemtype, foodtype, sup_cde, tran_no, item_cde,item_cde2,chain_cde,region);
					picSheetOfSumOUT(ws, hzOUT, strdate, endate, br_cde_in, br_cde_out, cmp, itemtype, foodtype, sup_cde, tran_no, item_cde,item_cde2,chain_cde,region);
					
				} else {
					// 请求明细数据
					List mxIN = this.getZHDetailDataIN(report, strdate, endate, br_cde_in, br_cde_out, cmp, itemtype, foodtype, sup_cde, item_cde,item_cde2,chain_cde,region);
					List mxOUT = getZHDetailDataOUT(report, strdate, endate, br_cde_in, br_cde_out, cmp, itemtype, foodtype, sup_cde, item_cde,item_cde2,chain_cde,region);
					if(mxIN==null&&mxOUT==null||(mxIN.size()<=1&&mxOUT.size()<=1)){
						return null;
					}
					picSheetOfDetailIN(ws, mxIN, strdate, endate, br_cde_in, br_cde_out, cmp, itemtype, foodtype, sup_cde, tran_no, item_cde,item_cde2,chain_cde,region);
					picSheetOfDetailOUT(ws, mxOUT, strdate, endate, br_cde_in, br_cde_out, cmp, itemtype, foodtype, sup_cde, tran_no, item_cde,item_cde2,chain_cde,region);
				}

			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("转货表 >>" + e.getMessage());
			}

		} else {
			System.out.println("File is not Exist!");
		}

		return ws;

	}
	
	
	/**
	 * 寫Excel sheet1
	 * 
	 * @param strdate
	 * @param endate
	 * @param item_cde
	 * @param br_cde
	 * @param cmp
	 */
	private void picSheetOfDetailIN(HSSFWorkbook ws, List data, String strdate,
			String endate, String br_cde_in,String br_cde_out, String cmp, String itemtype,
			String foodType, String sup_cde,String tran_no,String item_cde,String item_cde2,
			String chain_cde,String region) {
		HSSFSheet sheet = ws.getSheetAt(1);// 获得sheet

		PicHead(strdate, endate, br_cde_in, cmp, itemtype, sheet, foodType
				, sup_cde, tran_no, item_cde,item_cde2,chain_cde,region);

		for (int i = 0; i < data.size(); i++) {
			Object[] subs = (Object[]) data.get(i);
			HSSFRow r = sheet.getRow(i + 13) == null ? sheet.createRow(i + 13)
					: sheet.getRow(i + 13);
			for (int j = 1; j < subs.length; j++) {
				HSSFCell c = r.getCell(j-1) == null ? r.createCell(j-1) : r
						.getCell(j-1);
				if (j <= 6) {
					c.setCellValue(subs[j] == null ? "" : subs[j].toString());
				}else if(j==8){
					c.setCellValue(subs[j] == null ? "" : subs[j].toString());
				} else {
					c.setCellStyle(tcs111.get());
					c.setCellValue(subs[j] == null ? 0 : Double
							.parseDouble(subs[j].toString()));
				}

			}
		}
	}

	/**
	 * 寫Excel sheet1
	 * 
	 * @param strdate
	 * @param endate
	 * @param item_cde
	 * @param br_cde
	 * @param cmp
	 */
	private void picSheetOfDetailOUT(HSSFWorkbook ws, List data, String strdate,
			String endate, String br_cde_in,String br_cde_out, String cmp, String itemtype,
			String foodType, String sup_cde,String tran_no,String item_cde,String item_cde2,
			String chain_cde,String region) {
		HSSFSheet sheet = ws.getSheetAt(0);// 获得sheet

		PicHead(strdate, endate, br_cde_in, cmp, itemtype, sheet, foodType
				, sup_cde, tran_no, item_cde,item_cde2,chain_cde,region);

		for (int i = 0; i < data.size(); i++) {
			Object[] subs = (Object[]) data.get(i);
			HSSFRow r = sheet.getRow(i + 13) == null ? sheet.createRow(i + 13)
					: sheet.getRow(i + 13);
			for (int j = 1; j < subs.length; j++) {
				HSSFCell c = r.getCell(j-1) == null ? r.createCell(j-1) : r
						.getCell(j-1);
				if (j <=6) {
					c.setCellValue(subs[j] == null ? "" : subs[j].toString());
				}else if(j==8){
					c.setCellValue(subs[j] == null ? "" : subs[j].toString());
				} else {
					c.setCellStyle(tcs111.get());
					c.setCellValue(subs[j] == null ? 0 : Double
							.parseDouble(subs[j].toString()));
				}

			}
		}
	}

	/**
	 * 寫Excel 
	 * 
	 * @param strdate
	 * @param endate
	 * @param item_cde
	 * @param br_cde
	 * @param cmp
	 */
	private void picSheetOfSumIN(HSSFWorkbook ws, List data, String strdate,
			String endate, String br_cde_in,String br_cde_out, String cmp, String itemtype,
			String foodType, String sup_cde,String tran_no,String item_cde,
			String item_cde2,String chain_cde,String region) {
		HSSFSheet sheet = ws.getSheetAt(0);// 获得sheet

		PicHead(strdate, endate, br_cde_in, cmp, itemtype, sheet, foodType
				, sup_cde, tran_no, item_cde,item_cde2,chain_cde,region);
		
		for (int i = 0; i < data.size(); i++) {
			Object[] subs = (Object[]) data.get(i);
			HSSFRow r = sheet.getRow(i + 13) == null ? sheet.createRow(i + 13)
					: sheet.getRow(i + 13);
			for (int j = 1; j < subs.length; j++) {
				HSSFCell c = r.getCell(j-1) == null ? r.createCell(j-1) : r
						.getCell(j-1);
				if (j<=2) {
					c.setCellValue(subs[j] == null ? "" : subs[j].toString());
				} else {
					c.setCellStyle(tcs111.get());
					c.setCellValue(subs[j] == null ? 0 : Double
							.parseDouble(subs[j].toString()));
				}

			}
		}
	
		
	
	}

	
	private void picSheetOfSumOUT(HSSFWorkbook ws, List data, String strdate,
			String endate, String br_cde_in,String br_cde_out, String cmp, String itemtype,
			String foodType, String sup_cde,String tran_no,String item_cde,
			String item_cde2,String chain_cde,String region) {
		HSSFSheet sheet = ws.getSheetAt(1);// 获得sheet

		PicHead(strdate, endate, br_cde_in, cmp, itemtype, sheet, foodType
				, sup_cde, tran_no, item_cde,item_cde2,chain_cde,region);

		for (int i = 0; i < data.size(); i++) {
			Object[] subs = (Object[]) data.get(i);
			HSSFRow r = sheet.getRow(i + 13) == null ? sheet.createRow(i + 13)
					: sheet.getRow(i + 13);
			for (int j = 1; j < subs.length; j++) {
				HSSFCell c = r.getCell(j-1) == null ? r.createCell(j-1) : r
						.getCell(j-1);
				if (j<=2) {
					c.setCellValue(subs[j] == null ? "" : subs[j].toString());
				} else {
					c.setCellStyle(tcs111.get());
					c.setCellValue(subs[j] == null ? 0 : Double
							.parseDouble(subs[j].toString()));
				}

			}
		}
	}
	/**
	 * 写头部
	 * @param strdate
	 * @param endate
	 * @param br_cde
	 * @param cmp
	 * @param itemtype
	 * @param sheet
	 * @param foodtype
	 * @param sup_cde
	 * @param tran_no
	 * @param item_cde
	 */
	private void PicHead(String strdate, String endate, String br_cde,
			String cmp, String itemtype, HSSFSheet sheet, String foodtype,
			String sup_cde,String tran_no,String item_cde,String item_cde2,
			String chain_cde,String region) {
		HSSFRow r3 = sheet.getRow(2) == null ? sheet.createRow(2) : sheet
				.getRow(2);
		HSSFCell c3_2 = r3.getCell(1) == null ? r3.createCell(1) : r3
				.getCell(1);
		c3_2.setCellValue(strdate + " 至  " + endate);

		HSSFRow r4 = sheet.getRow(3) == null ? sheet.createRow(3) : sheet
				.getRow(3);
		HSSFCell c4_2 = r4.getCell(1) == null ? r4.createCell(1) : r4
				.getCell(1);
		c4_2.setCellValue(cmp == null || cmp.equals("")?"全部":cmp);

		HSSFRow r5 = sheet.getRow(4) == null ? sheet.createRow(4) : sheet
				.getRow(4);
		HSSFCell c5_2 = r5.getCell(1) == null ? r5.createCell(1) : r5
				.getCell(1);
		c5_2.setCellValue( (region == null ||  region.equals("")?"":region) + (br_cde == null || br_cde.equals("") ? "全部" : br_cde));

		HSSFRow r6 = sheet.getRow(5) == null ? sheet.createRow(5) : sheet
				.getRow(5);
		HSSFCell c6_2 = r6.getCell(1) == null ? r6.createCell(1) : r6
				.getCell(1);
		c6_2.setCellValue(itemtype == null || itemtype.equals("") ? "全部" : itemtype);

		HSSFRow r7 = sheet.getRow(6) == null ? sheet.createRow(6) : sheet
				.getRow(6);
		HSSFCell c7_2 = r7.getCell(1) == null ? r7.createCell(1) : r7
				.getCell(1);
		c7_2.setCellValue(sup_cde == null || sup_cde.equals("")?"全部":sup_cde);

		HSSFRow r8 = sheet.getRow(7) == null ? sheet.createRow(7) : sheet
				.getRow(7);
		HSSFCell c8_2 = r8.getCell(1) == null ? r8.createCell(1) : r8
				.getCell(1);
		c8_2.setCellValue(foodtype == null || foodtype.equals("") ? "全部"
				: foodtype.equals("food") ? "食物" : "非食物");
		
		
		
		HSSFRow r9 = sheet.getRow(8) == null ? sheet.createRow(8) : sheet
				.getRow(8);
		HSSFCell c9_2 = r9.getCell(1) == null ? r9.createCell(1) : r9
				.getCell(1);
		c9_2.setCellValue(item_cde == null || item_cde.equals("") ? "全部" : item_cde+"-"+(item_cde2.equals("")?item_cde:item_cde2));

		
		
		HSSFRow r10 = sheet.getRow(9) == null ? sheet.createRow(9) : sheet
				.getRow(9);
		HSSFCell c10_2 = r10.getCell(1) == null ? r10.createCell(1) : r10
				.getCell(1);
		c10_2.setCellValue(tran_no == null || tran_no.equals("") ? "全部" : tran_no);
		
		
		HSSFRow r11 = sheet.getRow(10) == null ? sheet.createRow(10) : sheet
				.getRow(10);
		HSSFCell c11_2 = r11.getCell(1) == null ? r11.createCell(1) : r11
				.getCell(1);
		c11_2.setCellValue(chain_cde == null || chain_cde.equals("") ? "全部" : chain_cde);


	}
	
	public void setBranchByGroupID(Report report) {
		
		if (report.getBrCode() != null && (report.getBrCode().contains("=") || report.getBrCode().contains("'"))) {
			return;
		}
		
		if (report.getBrTran() != null && (report.getBrTran().contains("=") || report.getBrTran().contains("'"))) {
			return;
		}
		
		if (report.getBrCode() == null || "".equals(report.getBrCode())) {
			if ("PRS".equals(report.getGroupID())) {
				List<String> branchs = bmsMapper.getBranchByUserID(report.getUserID());
				if (branchs.size() > 1) {
					String brSql = " in (";
					for (String branch : branchs) {
						brSql = brSql + "'" + branch + "',";
					}
					brSql = brSql.substring(0, brSql.length() - 1) + ")";
					report.setBrCode(brSql);
				} else {
					String brSql = " = '" + branchs.get(0) + "'";
					report.setBrCode(brSql);
				}
			}
		} else {
			String brSql =  " = '" + report.getBrCode() + "'";
			report.setBrCode(brSql);
		}
		
		if (report.getBrTran() == null || "".equals(report.getBrTran())) {
			if ("PRS".equals(report.getGroupID())) {
				report.setIsPRS("yes");
				List<String> branchs = bmsMapper.getBranchByUserID(report.getUserID());
				if (branchs.size() > 1) {
					String brSql = " in (";
					for (String branch : branchs) {
						brSql = brSql + "'" + branch + "',";
					}
					brSql = brSql.substring(0, brSql.length() - 1) + ")";
					report.setBrTran(brSql);
				} else {
					String brSql = " = '" + branchs.get(0) + "'";
					report.setBrTran(brSql);
				}
			} else {
				report.setIsPRS(null);
			}
			
		}
		
	}
	
	public void setItemCodeAndSupCode(Report report) {
		String supName = report.getSupName();
		String itemName = report.getItemName();
		if (supName != null && !"".equals(supName)) {
			String supCode = supName.substring(0, supName.indexOf("-"));
			if (supCode != null && !"".equals(supCode)) {
				report.setSubCode(supCode);
			}
		}
		
		if (itemName != null && !"".equals(itemName)) {
			String itemCode = itemName.substring(0, itemName.indexOf("-"));
			if (itemCode != null && !"".equals(itemCode)) {
				report.setItemCodeStart(itemCode);
			}
		}
	}
	
	
}
