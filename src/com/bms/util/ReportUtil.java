package com.bms.util;

import com.bms.vo.Report;

public class ReportUtil {
	
	public static boolean checkDate(Report report) {
		if (report.getStartDate() == null || "".equals(report.getStartDate())) {
			return false;
		}
		
		if (report.getEndDate() == null || "".equals(report.getEndDate())) {
			return false;
		}
		
		return true;
	}

}
