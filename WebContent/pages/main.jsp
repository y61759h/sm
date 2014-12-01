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
<title>BMS</title>
<link rel="stylesheet" type="text/css" href="easyui/themes/bootstrap/easyui.css">
<link rel="stylesheet" type="text/css" href="easyui/themes/icon.css">
<script type="text/javascript" src="easyui/jquery.min.js"></script>
<script type="text/javascript" src="easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/main.js"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',split:true" style="height:80px">
	</div>
	<div data-options="region:'south',split:true" style="height:50px;"></div>
    <div data-options="region:'west',title:'菜单',split:true" style="width:200px;">
    	<ul id="menu_tree" class="easyui-tree" data-options="url:'menu/get_menu.do',animate:true,lines:true"></ul>
    </div>
    <div data-options="region:'center'" style="padding:5px;background:#eee;">
    	<div id="tabs" class="easyui-tabs"  fit="true" border="false" >
    		<div id="welcome" title="欢迎使用" style="padding:20px; overflow:hidden;" >
    			<div style="font-size:250px; color:gray; text-align:center;">BMS</div>
    		</div>
    	</div>
    </div>
    <div id="mm" class="easyui-menu" style="width:150px;">
		<div id="mm-tabclose">关闭</div>
		<div id="mm-tabcloseall">全部关闭</div>
		<div id="mm-tabcloseother">除此之外全部关闭</div>
		<div class="menu-sep"></div>
		<div id="mm-tabcloseright">当前页右侧全部关闭</div>
		<div id="mm-tabcloseleft">当前页左侧全部关闭</div>
		<div class="menu-sep"></div>
		<div id="mm-exit">退出</div>
	</div>
</body>
</html>