package com.bms.util;

public class ErrorCode {
	
	public static final String SUCCESS					= "0";
	public static final String ERROR 					= "-1";
	
	/****************************************************
	 * 													*
	 *					       登陆模块错误代码					    *
	 * 													*
	 ****************************************************/
	public static final String LOGIN_NO_USER			= "101";
	public static final String LOGIN_WRONG_PASSWORD		= "102";
	
	
	
	/****************************************************
	 * 													*
	 *					      权限模块错误代码					    *
	 * 													*
	 ****************************************************/
	public static final String LOGIN_URL				= "/pages/to_login.do";
	public static final String PAGE_NO_LOGIN			= "/pages/error/nologin.jsp";

}
