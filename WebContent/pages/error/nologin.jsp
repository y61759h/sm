<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>未登陆</title>
<link rel="stylesheet" type="text/css" href="easyui/themes/bootstrap/easyui.css">
<link rel="stylesheet" type="text/css" href="easyui/themes/icon.css">
<script type="text/javascript" src="easyui/jquery.min.js"></script>
<script type="text/javascript" src="easyui/jquery.easyui.min.js"></script>
<script type="text/javascript">
	var count = 9;
	function toLogin() {
		if (count <= 0) {
			clearInterval(timer);
			window.top.location.href = "/bms/pages/to_login.do";
		}
		$('#count').html(count--);
	}
	
	var timer = window.setInterval("toLogin()",1000);
	
	function clickToLogin() {
		clearInterval(timer);
		window.top.location.href = "/bms/pages/to_login.do";
	}
</script>
</head>
<body>
	<span>检测到您还没登陆本系统，请先登陆！<br/></span>
	<span><span id="count">10</span>秒钟后自动跳转到登陆界面或者手动点击<a href="javascript:void(0)" onclick="clickToLogin()">这里</a>进行跳转！</span>
</body>