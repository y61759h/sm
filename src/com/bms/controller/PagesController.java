package com.bms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("pages")
public class PagesController {

	@RequestMapping("to_login")
	public String toLogin() {
		return "login";
	}
	
	@RequestMapping("to_main")
	public String toMain() {
		return "main";
	}
	
	@RequestMapping("to_using_report")
	public String toUsingReport() {
		return "report/using";
	}
	
	@RequestMapping("to_invoice_report")
	public String toInvoiceReport() {
		return "report/invoice";
	}
	
	@RequestMapping("to_tran_report")
	public String toTranReport() {
		return "report/tran";
	}
	
}
