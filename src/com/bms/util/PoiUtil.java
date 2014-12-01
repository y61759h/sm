/*package com.bms.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class PoiUtil {
	
	public HSSFWorkbook getRpt1Ws(String filePth, String strdate,
			String endate, String br_cde, String cmp, String itemtype,
			String foodtype, String rpttype,String sup_cde,String item_cde,
			String item_cde2,String chain_cde,String region) {
		HSSFWorkbook ws = null;
		File f = new File(filePth);
		if (f.exists()) {
			try {
				FileInputStream fs = new FileInputStream(f);
				ws = new HSSFWorkbook(fs);

				cs1 = ws.createCellStyle();
				HSSFDataFormat format = ws.createDataFormat();
				cs1.setDataFormat(format.getFormat("#,##0.00"));

				if (rpttype.equals("hz")) {
					// 请求汇总数据
					List datasummary = this.getRpt1SummarData(strdate, endate,
							br_cde, cmp, itemtype, foodtype,sup_cde,item_cde,item_cde2,chain_cde,region);
					 if(datasummary==null||datasummary.size()==0){
						 return null;
					 }
					picSheetOfSummary(ws, datasummary, strdate, endate, br_cde,
							cmp, itemtype, foodtype,sup_cde,item_cde,item_cde2,chain_cde,region);
				} else if(rpttype.equals("mx")) {
					// 请求明细数据
					List dataDetail = this.getRpt1DetailData(strdate, endate,
							br_cde, cmp, itemtype, foodtype,sup_cde,item_cde,item_cde2,chain_cde,region);
					
					 if(dataDetail==null||dataDetail.size()==0){
						 return null;
					 }
					
					picSheetOfDetail(ws, dataDetail, strdate, endate, br_cde,
							cmp, itemtype, foodtype,sup_cde,item_cde,item_cde2,chain_cde,region);
				}else{
					List dataSource = getRpt1SourceData(strdate, endate,
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

}
*/