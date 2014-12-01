package com.bms.vo;

public class Tran {
	
	private String startDate;		//开始日期
	private String endDate;			//结束日期
	private String region;			//区域
	private String company;			//公司
	private String brCodeIn;		//转入门店
	private String brCodeOut;		//转出门店
	private String chainCode;		//数据链
	private String itemType;		//货品分类
	private String foodType;		//食物分类
	private String subCode;			//供应商编号
	private String itemCodeStart;	//产品开始编号
	private String itemCodeEnd;		//产品结束编号
	private String reportType;		//报表类型
	private String userID;			//用户ID
	private String tranNo;			//转货单号
	private String lastMonStartDate; //开始日期的上个月
	private String lastMonEndDate;	//结束日期的上个月
	
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getChainCode() {
		return chainCode;
	}

	public void setChainCode(String chainCode) {
		this.chainCode = chainCode;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public String getFoodType() {
		return foodType;
	}

	public void setFoodType(String foodType) {
		this.foodType = foodType;
	}

	public String getSubCode() {
		return subCode;
	}

	public void setSubCode(String subCode) {
		this.subCode = subCode;
	}

	public String getItemCodeStart() {
		return itemCodeStart;
	}

	public void setItemCodeStart(String itemCodeStart) {
		this.itemCodeStart = itemCodeStart;
	}

	public String getItemCodeEnd() {
		return itemCodeEnd;
	}

	public void setItemCodeEnd(String itemCodeEnd) {
		this.itemCodeEnd = itemCodeEnd;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getLastMonStartDate() {
		return lastMonStartDate;
	}

	public void setLastMonStartDate(String lastMonStartDate) {
		this.lastMonStartDate = lastMonStartDate;
	}

	public String getLastMonEndDate() {
		return lastMonEndDate;
	}

	public void setLastMonEndDate(String lastMonEndDate) {
		this.lastMonEndDate = lastMonEndDate;
	}

	public String getBrCodeIn() {
		return brCodeIn;
	}

	public void setBrCodeIn(String brCodeIn) {
		this.brCodeIn = brCodeIn;
	}

	public String getBrCodeOut() {
		return brCodeOut;
	}

	public void setBrCodeOut(String brCodeOut) {
		this.brCodeOut = brCodeOut;
	}

	public String getTranNo() {
		return tranNo;
	}

	public void setTranNo(String tranNo) {
		this.tranNo = tranNo;
	}

}
