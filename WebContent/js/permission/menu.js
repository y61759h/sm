var editIndex;

$(document).ready(function() {
	$('#add_win').window({
		title: '菜单添加',
		width: 500,
		height: 300,
		modal: true,
		closed: true
	});
	
	$('#menus').datagrid({
	    url: 'menu/list_menu.do',
	    columns: [[
            {field:'checkboxs',checkbox: true},
	        {field:'menuID',title:'菜单编码',width:50},
	        {field:'parentID',title:'父菜单',width:50,editor: 'text'},
	        {field:'menuName',title:'菜单名',width:80,editor: 'text'},
	        {field:'linkSource',title:'菜单链接',width:100,editor: 'text'},
	        {field:'createUser',title:'创建用户',width:50},
	        {field:'createDate',title:'创建日期',width:100},
	        {field:'modifyUser',title:'更改用户',width:50},
	        {field:'modifyDate',title:'更改日期',width:100}
	    ]],
	    pagination: true,
	    fitColumns: true,
	    fit: true,
	    rownumbers: true,
	    singleSelect: true,
	    /*selectOnCheck: false,*/
	    checkOnSelect: false,
	    toolbar:[{
	    	text:'添加',
		    iconCls:'icon-add',
		    handler:function() {
		    	 /*$('#menus').datagrid('appendRow',{});
		    	 editIndex = $('#menus').datagrid('getRows').length-1;
		    	 $('#menus').datagrid('beginEdit', editIndex);*/
		    	$('#add_win').window('open');
		    }
	    },'-',{
	    	text:'删除',
		    iconCls:'icon-cut',
		    handler:function() {
		    	var check = $("#menus").datagrid('getChecked');
		    	if (check == null || check == '') {
		    		$.messager.alert('提示', '请先选择需要删除的数据！', 'warning');
		    		return;
		    	} else {
		    		$.messager.confirm('警告', '您确定要删除所选数据吗！', function(r) {
		    			if (r) {
		    				var rowindex = $("#menus").datagrid('getRowIndex', check[0]);
		    				$("#menus").datagrid('deleteRow', rowindex);
		    			}
		    		});
		    	}
				
		    }
	    },'-',{
	    	text:'保存修改',
		    iconCls:'icon-save',
		    handler:function(){alert('save')}
	    }],
	    onDblClickCell: function(index, field, value) {
	    	$(this).datagrid('checkRow', index);
	    	cancelEdit();
	    	$(this).datagrid('editCell', {index:index, field:field});
			editIndex = index;
	    }
	});
});


function cancelEdit() {
	if (editIndex == undefined)
		return;
	
	$("#menus").datagrid('cancelEdit', editIndex);
	editIndex = undefined;
	
}

//添加单元格编辑插件
$.extend($.fn.datagrid.methods, {
    editCell: function(jq,param){
        return jq.each(function(){
            var opts = $(this).datagrid('options');
            var fields = $(this).datagrid('getColumnFields',true).concat($(this).datagrid('getColumnFields'));
            for(var i=0; i<fields.length; i++){
                var col = $(this).datagrid('getColumnOption', fields[i]);
                col.editor1 = col.editor;
                if (fields[i] != param.field){
                    col.editor = null;
                }
            }
            $(this).datagrid('beginEdit', param.index);
            for(var i=0; i<fields.length; i++){
                var col = $(this).datagrid('getColumnOption', fields[i]);
                col.editor = col.editor1;
            }
        });
    }
});


function addMenu() {
	$('#add_form').form('submit');
}

function clearForm() {
	$('#add_form').form('clear');
}












