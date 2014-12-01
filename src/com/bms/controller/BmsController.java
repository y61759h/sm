package com.bms.controller;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bms.pojo.SysUser;
import com.bms.service.BmsService;
import com.bms.util.ReportUtil;
import com.bms.vo.Report;

@Controller
@RequestMapping("bms")
public class BmsController {
	
	public static Logger Logger = LogManager.getLogger(BmsController.class);
	
	@Autowired
	public BmsService bmsService;
	
	@RequestMapping("get_company")
	@ResponseBody
	public String getCompany(String params, HttpSession session) {
		SysUser sysUser = (SysUser) session.getAttribute("user");
		return bmsService.getCompany(params, sysUser.getUserID());
	}
	
	@RequestMapping("get_branch")
	@ResponseBody
	public String getBranch(String params, HttpSession session) {
		SysUser sysUser = (SysUser) session.getAttribute("user");
		return bmsService.getBranch(params, sysUser.getUserID());
	}
	
	@RequestMapping("get_region")
	@ResponseBody
	public String getRegion(String params, HttpSession session) {
		SysUser sysUser = (SysUser) session.getAttribute("user");
		return bmsService.getRegion(params, sysUser.getUserID());
	}
	
	@RequestMapping("get_chain")
	@ResponseBody
	public String getChain(String params, HttpSession session) {
		SysUser sysUser = (SysUser) session.getAttribute("user");
		return bmsService.getChain(params, sysUser.getUserID());
	}
	
	@RequestMapping("get_branch_out")
	@ResponseBody
	public String getBranchOut() {
		return bmsService.getBranchOut();
	}
	
	@RequestMapping("get_itemtype")
	@ResponseBody
	public String getItemType() {
		return bmsService.getItemType();
	}
	
	@RequestMapping("get_itemname")
	@ResponseBody
	public String getItemName(String itemName, HttpSession session) {
		ServletContext context = session.getServletContext();
		String result = (String) context.getAttribute("itemNames");
		if (result == null) {
			result = bmsService.getItemName(itemName);;
			context.setAttribute("itemNames", result);
		}
		return result;
	}
	
	@RequestMapping("get_supname")
	@ResponseBody
	public String getSupName(String subName, HttpSession session) {
		ServletContext context = session.getServletContext();
		String result = (String) context.getAttribute("supNames");
		if (result == null) {
			result = bmsService.getSupName(subName);;
			context.setAttribute("supNames", result);
		}
		return result;
	}
	
	@RequestMapping("using_report")
	@ResponseBody
	public String usingReport(Report report, HttpSession session, HttpServletRequest request) {
		String responseText = null;
		if (!ReportUtil.checkDate(report)) {
			responseText = "{\"status\":\"datenull\"}";
			return responseText;
		}
		
		SysUser sysUser = (SysUser) session.getAttribute("user");
		report.setUserID(sysUser.getUserID());
		report.setGroupID(sysUser.getGroupID());
		
		bmsService.filterReport(report);
		
		String pathmx = request.getRealPath("RptTemp/用量表mx.xls");
		String source = request.getRealPath("RptTemp/用量表源数据.xls");
		String pathhz = request.getRealPath("RptTemp/用量表hz.xls");
		String path = null;
		
		if ("mx".equals(report.getReportType())) {
			path = pathmx;
		} else if ("hz".equals(report.getReportType())) {
			path = pathhz;
		} else if ("source".equals(report.getReportType())) {
			path = source;
		}
		
		
		try{
			HSSFWorkbook ws = bmsService.getRpt1Ws(report, path, report.getStartDate(), report.getEndDate(), report.getBrCode(), 
					report.getCompany(), report.getItemType(), report.getFoodType(), report.getReportType(),
					report.getSubCode(), report.getItemCodeStart(), report.getItemCodeEnd(), report.getChainCode(), report.getRegion());
			if (ws == null) {
				responseText = "{\"status\":\"isnull\"}";
			} else {
				session.setAttribute("excel", ws);
				responseText = "{\"status\":\"success\"}";
			}
		} catch (Exception e) {
			responseText = "{\"status\":\"failed\"}";
			Logger.error(e);
		} 
		
		return responseText;
		
	}
	
	@RequestMapping("invoice_report")
	@ResponseBody
	public String invoiceReport(Report report, HttpSession session, HttpServletRequest request) {
		String responseText = null;
		if (!ReportUtil.checkDate(report)) {
			responseText = "{\"status\":\"datenull\"}";
			return responseText;
		}
		
		SysUser sysUser = (SysUser) session.getAttribute("user");
		report.setUserID(sysUser.getUserID());
		report.setGroupID(sysUser.getGroupID());
		
		bmsService.filterReport(report);
		bmsService.setItemCodeAndSupCode(report);
		
		String path = null;
		if("hz".equals(report.getReportType())){
			path = request.getRealPath("RptTemp/FAC来货汇总.xls");
			
		}else if("mx".equals(report.getReportType())){
			path = request.getRealPath("RptTemp/FAC来货明细.xls");
		}else{
			path = request.getRealPath("RptTemp/FAC来货源数据.xls");
		}
		
		try{
			HSSFWorkbook ws = bmsService.getRpt2Ws(report, path, report.getStartDate(), report.getEndDate(), report.getBrCode(), report.getCompany(), 
					report.getItemType(), report.getFoodType(),report.getSubCode(), report.getItemCodeStart(), report.getInvNo(),
					report.getChainCode(),report.getItemCodeEnd(),report.getRegion(),report.getReportType());
			
			if (ws == null) {
				responseText = "{\"status\":\"isnull\"}";
			} else {
				session.setAttribute("excel", ws);
				responseText = "{\"status\":\"success\"}";
			}
		} catch (Exception e) {
			responseText = "{\"status\":\"failed\"}";
			Logger.error(e);
		} 
		
		return responseText;
	}
	
	@RequestMapping("tran_report")
	@ResponseBody
	public String tranReport(Report report, HttpSession session, HttpServletRequest request) {
		String responseText = null;
		if (!ReportUtil.checkDate(report)) {
			responseText = "{\"status\":\"datenull\"}";
			return responseText;
		}
		
		SysUser sysUser = (SysUser) session.getAttribute("user");
		report.setUserID(sysUser.getUserID());
		report.setGroupID(sysUser.getGroupID());
		
		bmsService.filterReport(report);
		
		String pathmx = request.getRealPath("RptTemp/转货mx.xls");
		String pathhz = request.getRealPath("RptTemp/转货hz.xls");
		String path = "";
		
		if ("mx".equals(report.getReportType())) {
			path=pathmx;
		} else if ("hz".equals(report.getReportType())) {
			path=pathhz;
		}
		
		try{
			HSSFWorkbook ws = bmsService.getRpt3Ws(report, path, report.getStartDate(), report.getEndDate(), report.getBrCodeIn(), report.getCompany(), 
					report.getItemType(), report.getFoodType(), report.getReportType(), report.getSubCode(), report.getItemCodeStart(), report.getBrCodeOut(), 
					report.getTranNo(),report.getItemCodeEnd(),report.getChainCode(),report.getRegion());
			
			if (ws == null) {
				responseText = "{\"status\":\"isnull\"}";
			} else {
				session.setAttribute("excel", ws);
				responseText = "{\"status\":\"success\"}";
			}
		} catch (Exception e) {
			responseText = "{\"status\":\"failed\"}";
			Logger.error(e);
		} 
		
		return responseText;
	}
	
	@RequestMapping("download_excel")
	public void downloadExcel(HttpServletResponse response, HttpSession session) {
		HSSFWorkbook ws = (HSSFWorkbook) session.getAttribute("excel");
		OutputStream os = null;
		try {
			response.setHeader("Content-Type", "application/xls");
			response.setHeader("Content-Disposition",
					"inline; filename=\"report.xls\"");
			os = new BufferedOutputStream(response.getOutputStream(), 2048000);
			ws.write(os);
		} catch (Exception e) {
			Logger.error(e);
		} finally {
			try {
				os.close();
			} catch (IOException e) {
				Logger.error(e);
			}
		}
	}

}
