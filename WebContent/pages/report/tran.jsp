<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Tran</title>
<link rel="stylesheet" type="text/css"
	href="easyui/themes/bootstrap/easyui.css">
<link rel="stylesheet" type="text/css" href="easyui/themes/icon.css">
<script type="text/javascript" src="easyui/jquery.min.js"></script>
<script type="text/javascript" src="easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="js/report/tran.js"></script>
</head>
<body>
	<div>
		<form id="tran_form" method="post">
			<table>
				<tr>
					<td>日期：</td>
					<td>
						<input name="startDate" id="startDate" type="text" class="easyui-datebox" data-options="editable: false, required:true">
						到 <input name="endDate" id="endDate" type="text" class="easyui-datebox" data-options="editable: false, required:true">
					</td>
				</tr>
				<tr>
					<td>产品编号：</td>
					<td><input name="itemCodeStart" id="itemCodeStart" type="text" class="easyui-textbox"> 
						到 <input name="itemCodeEnd" id="itemCodeEnd" type="text" class="easyui-textbox">
					</td>
				</tr>
				<tr>
					<td>公司:</td>
					<td>
						<select name="company" id="company" style="width: 150px">
						</select>
					</td>
				</tr>
				<tr>
					<td>区域：</td>
					<td>
						<select name="region" id="region" style="width: 150px">
						</select>
					</td>
				</tr>
				<tr>
					<td>转入门店：</td>
					<td>
						<select name="brCodeIn" id="brCodeIn" style="width: 150px">
						</select>
					</td>
				</tr>
				<tr>
					<td>转出门店：</td>
					<td>
						<select name="brCodeOut" id="brCodeOut" style="width: 150px">
						</select>
					</td>
				</tr>
				<tr>
					<td>数据链：</td>
					<td>
						<select name="chainCode" id="chainCode" style="width: 150px">
						</select>
					</td>
				</tr>
				<tr>
					<td>货品分类：</td>
					<td>
						<select name="itemType" id="itemType" style="width: 150px" class="easyui-combobox">
						</select>
					</td>
				</tr>
				<tr>
					<td>食物分类：</td>
					<td>
						<select name="foodType" id="foodType" style="width: 150px" class="easyui-combobox" data-options="editable: false">
							<option value="all">——全部——</option>
							<option value="food">食物</option>
							<option value="nonfood">非食物</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>供应商编号：</td>
					<td><input name="subCode" id="subCode" type="text" class="easyui-textbox"></td>
				</tr>
				<tr>
					<td>转货单号：</td>
					<td><input name="tranNo" id="tranNo" type="text" class="easyui-textbox"></td>
				</tr>
				<tr>
					<td>报表类型：</td>
					<td>
						<select name="reportType" id="reportType" class="easyui-combobox" style="width: 150px" data-options="editable: false">
							<option value="mx">明细</option>
							<option value="hz">汇总</option>
						</select>
					</td>
				</tr>
			</table>
			<a href="javascript:void(0)" onclick="tran()" class="easyui-linkbutton" data-options="iconCls:'icon-large-smartart'" style="padding:5px 0px;width:100%;">
                <span style="font-size:14px;">导出Excel</span>
            </a>
		</form>
	</div>
	
	<form id="download_excel">
	</form>
</body>
</html>