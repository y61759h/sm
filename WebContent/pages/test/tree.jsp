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
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="easyui/themes/bootstrap/easyui.css">
<link rel="stylesheet" type="text/css" href="easyui/themes/icon.css">
<script type="text/javascript" src="easyui/jquery.min.js"></script>
<script type="text/javascript" src="easyui/jquery.easyui.min.js"></script>
</head>
<body>
	<div class="easyui-panel" style="padding:5px">
		选择角色：<select class="easyui-combobox" name="state" style="width:200px;">
	        <option value="RPC">RPC</option>
	        <option value="CDC">CDC</option>
	        <option value="ITD">ITD</option>
	    </select>
        <ul id="tt" class="easyui-tree" data-options="url:'menu/get_menu.do',method:'post',animate:true,checkbox:true"></ul>
    </div>
</body>
</html>