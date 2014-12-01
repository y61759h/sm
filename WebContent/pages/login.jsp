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
<title>Login</title>
<link rel="stylesheet" type="text/css" href="easyui/themes/bootstrap/easyui.css">
<link rel="stylesheet" type="text/css" href="easyui/themes/icon.css">
<script type="text/javascript" src="easyui/jquery.min.js"></script>
<script type="text/javascript" src="easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/login.js"></script>
</head>
<body>
	
	<div id="login" style="overflow: hidden">
		<form id="login_form" action="/bms/user/login.do" method="post">
		    <div id="div_1" class="easyui-panel" style="width:100%;padding:30px 70px 20px 70px;">
		        <div style="margin-bottom:10px">
		            <input id="userID" name="userID" class="easyui-textbox" type="text" style="width:300px;height:40px;padding:12px" data-options="prompt:'请输入用户名！',iconCls:'icon-man',iconWidth:38,required:true,missingMessage:'用户名不能为空！'">
		        </div>
		        <div style="margin-bottom:20px">
		            <input id="input_password" name="password" class="easyui-textbox" type="password" style="width:300px;height:40px;padding:12px" data-options="prompt:'请输入密码！',iconCls:'icon-lock',iconWidth:38,required:true,missingMessage:'密码不能为空！'">
		        </div>
		        <div style="margin-bottom:20px">
		            <input type="checkbox" checked="checked">
		            <span>记住密码</span>
		        </div>
		        <div>
		            <a id="bt_login" href="javascript:void(0)" onclick="login()" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" style="padding:5px 0px;width:300px;">
		                <span style="font-size:14px;">登陆</span>
		            </a>
		        </div>
		    </div>
	    </form>
    </div>
    
</body>
</html>