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
<title>menu</title>
<link rel="stylesheet" type="text/css" href="easyui/themes/bootstrap/easyui.css">
<link rel="stylesheet" type="text/css" href="easyui/themes/icon.css">
<script type="text/javascript" src="easyui/jquery.min.js"></script>
<script type="text/javascript" src="easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/permission/menu.js"></script>

<script type="text/javascript">
/* var endIndex;

var toolbar = [{
    text:'添加',
    iconCls:'icon-add',
    handler:function(){
    	 $('#dg').datagrid('appendRow',{});
    	 endIndex = $('#dg').datagrid('getRows').length-1;
    	 $('#dg').datagrid('beginEdit', endIndex);
    }
},{
    text:'删除',
    iconCls:'icon-cut',
    handler:function(){
    	var check = $("#dg").datagrid('getChecked');
		var rowindex = $("#dg").datagrid('getRowIndex', check[0]);
		$("#dg").datagrid('deleteRow', rowindex);
    }
},'-',{
    text:'修改',
    iconCls:'icon-save',
    handler:function(){alert('save')}
}];

	$(document).ready(function() {
		var mydata = {"total":28,"rows":[
		                             	{"productid":"FI-SW-01","productname":"Koi","unitcost":10.00,"status":"P","listprice":36.50,"attr1":"Large","itemid":"EST-1"},
		                            	{"productid":"K9-DL-01","productname":"Dalmation","unitcost":12.00,"status":"P","listprice":18.50,"attr1":"Spotted Adult Female","itemid":"EST-10"},
		                            	{"productid":"RP-SN-01","productname":"Rattlesnake","unitcost":12.00,"status":"P","listprice":38.50,"attr1":"Venomless","itemid":"EST-11"},
		                            	{"productid":"RP-SN-01","productname":"Rattlesnake","unitcost":12.00,"status":"P","listprice":26.50,"attr1":"Rattleless","itemid":"EST-12"},
		                            	{"productid":"RP-LI-02","productname":"Iguana","unitcost":12.00,"status":"P","listprice":35.50,"attr1":"Green Adult","itemid":"EST-13"},
		                            	{"productid":"FL-DSH-01","productname":"Manx","unitcost":12.00,"status":"P","listprice":158.50,"attr1":"Tailless","itemid":"EST-14"},
		                            	{"productid":"FL-DSH-01","productname":"Manx","unitcost":12.00,"status":"P","listprice":83.50,"attr1":"With tail","itemid":"EST-15"},
		                            	{"productid":"FL-DLH-02","productname":"Persian","unitcost":12.00,"status":"P","listprice":23.50,"attr1":"Adult Female","itemid":"EST-16"},
		                            	{"productid":"FL-DLH-02","productname":"Persian","unitcost":12.00,"status":"P","listprice":89.50,"attr1":"Adult Male","itemid":"EST-17"},
		                            	{"productid":"AV-CB-01","productname":"Amazon Parrot","unitcost":92.00,"status":"P","listprice":63.50,"attr1":"Adult Male","itemid":"EST-18"}
		                            ]};
		
		$("#dg").datagrid("loadData", mydata);
		
		$('#dg').datagrid({
			onCheck: function(index,field,value){
				if (endIndex != index) {
					$('#dg').datagrid('endEdit', endIndex);
				}
			}
		});
	}); 
	
	function onClickCell(index) {
		
		if (endIndex != index) {
			$('#dg').datagrid('endEdit', endIndex);
		} 
		
		$('#dg').datagrid('beginEdit', index);
		
		endIndex = index;
	}
	
	function remove(){
		
	} */
</script>

</head>
<body>

<!-- 	<table id="dg" class="easyui-datagrid" title="Basic DataGrid" 
            data-options="rownumbers:true,singleSelect:true,collapsible:true,toolbar:toolbar,pagination:true,onDblClickRow:onClickCell,fit:true">
        <thead>
            <tr>
                <th data-options="field:'checkbox',width:30,checkbox:true"></th>
                <th data-options="field:'itemid',width:80,editor:'text'">Item ID</th>
                <th data-options="field:'productid',width:100,editor:'text'">Product</th>
                <th data-options="field:'listprice',width:80,align:'right',editor:'text'">List Price</th>
                <th data-options="field:'unitcost',width:80,align:'right',editor:'text'">Unit Cost</th>
                <th data-options="field:'attr1',width:250,editor:'text'">Attribute</th>
                <th data-options="field:'status',width:60,align:'center',editor:'text'">Status</th>
            </tr>
        </thead>
    </table> -->
    
    <table id="menus"></table>
	
	<div id="add_win">
	
		<form id="add_form" action="menu/add_menu.do" method="post">
			<table align="center" cellpadding="5">
                <tr>
                    <td>菜单编码:</td>
                    <td><input name="menuID" class="easyui-textbox" type="text" ></input></td>
                </tr>
                <tr>
                    <td>父菜单:</td>
                    <td><input name="parentID" type="text" class="easyui-textbox" ></input></td>
                </tr>
                <tr>
                    <td>菜单名:</td>
                    <td><input name="menuName" class="easyui-textbox" type="text" ></input></td>
                </tr>
                <tr>
                    <td>菜单链接:</td>
                    <td><input name="linkSource" class="easyui-textbox" type="text" ></input></td>
                </tr>
            </table>
		</form>
        <div style="text-align:center;padding:5px">
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="addMenu()">添加</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()">清空</a>
        </div>
	
	</div>
</body>
</html>