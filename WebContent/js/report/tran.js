var loadCompany = true;
var loadRegion = true;
var loadBranch = true;
var loadChain = true;

var companySelect = 'all';
var regionSelect = 'all';
var branchSelect = 'all';
var chainSelect = 'all';

var defaultSelect = 'all';

$(document).ready(function () {
	initCombobox();
});

var MaskUtil = (function(){  
    
    var $mask,$maskMsg;  
      
    var defMsg = '正在处理，请稍待。。。';  
      
    function init(){  
        if(!$mask){  
            $mask = $("<div class=\"datagrid-mask mymask\"></div>").appendTo("body");  
        }  
        if(!$maskMsg){  
            $maskMsg = $("<div class=\"datagrid-mask-msg mymask\">"+defMsg+"</div>")  
                .appendTo("body").css({'font-size':'12px'});  
        }  
          
        $mask.css({width:"100%",height:$(document).height()});  
          
        $maskMsg.css({  
            left:($(document.body).outerWidth(true) - 190) / 2,top:($(window).height() - 45) / 2  
        });   
                  
    }  
      
    return {  
        mask:function(msg){  
            init();  
            $mask.show();  
            $maskMsg.html(msg||defMsg).show();  
        }  
        ,unmask:function(){  
            $mask.hide();  
            $maskMsg.hide();  
        }  
    }  
      
}());  

function initCombobox() {
	$('#company').combobox({
		url: '/bms/bms/get_company.do',
		valueField: 'value',
		textField: 'text',
		editable: false,
		onLoadSuccess: function() {
			var data = $('#company').combobox('getData');
			if (data.length > 0) {
				selectCombobox('company', defaultSelect);
			}
		},
		onSelect: function(record) {
			companySelect = record.value;
			if (loadCompany) {
				var params = getParamsForCombobox();
				getRegion(params);
				getChain(params);
			}
		}
	});
	
	$('#brCodeOut').combobox({
		url: '/bms/bms/get_branch_out.do',
		valueField: 'value',
		textField: 'text',
		editable: false,
		onLoadSuccess: function() {
			var data = $('#brCodeOut').combobox('getData');
			if (data.length > 0) {
				selectCombobox('brCodeOut', defaultSelect);
			}
		}
	});
	
	$('#region').combobox({
		url: '/bms/bms/get_region.do',
		valueField: 'value',
		textField: 'text',
		editable: false,
		onLoadSuccess: function() {
			var data = $('#region').combobox('getData');
			if (data.length > 0) {
				selectCombobox('region', defaultSelect);
			}
		},
		onSelect: function(record) {
			regionSelect = record.value;
			if (loadRegion) {
				var params = getParamsForCombobox();
				getCompany(params)
				getChain(params);
			}
			
		}
	});
	
	$('#chainCode').combobox({
		url: '/bms/bms/get_chain.do',
		valueField: 'value',
		textField: 'text',
		editable: false,
		onLoadSuccess: function() {
			var data = $('#chainCode').combobox('getData');
			if (data.length > 0) {
				selectCombobox('chainCode', defaultSelect);
			}
		},
		onSelect: function(record) {
			chainSelect = record.value;
			if (loadChain) {
				var params = getParamsForCombobox();
				getCompany(params)
				getRegion(params);
			}
		}
	});
	
	$('#itemType').combobox({
		url: '/bms/bms/get_itemtype.do',
		valueField: 'value',
		textField: 'text',
		editable: false,
		onLoadSuccess: function() {
			var data = $('#itemType').combobox('getData');
			if (data.length > 0) {
				$('#itemType').combobox('select', defaultSelect);
			}
		}
	});
	
	$('#brCodeIn').combobox({
		url: '/bms/bms/get_branch_out.do',
		valueField: 'value',
		textField: 'text',
		editable: false,
		onLoadSuccess: function() {
			var data = $('#brCodeIn').combobox('getData');
			if (data.length > 0) {
				$('#brCodeIn').combobox('select', defaultSelect);
			}
		}
	});
}

function getCompany(params) {
	$('#company').combobox({
		url: '/bms/bms/get_company.do',
		valueField: 'value',
		textField: 'text',
		method: 'post',
		editable: false,
		onBeforeLoad: function(param) {
			param.params = params
		},
		onLoadSuccess: function() {
			var data = $('#company').combobox('getData');
			var hasValue = false;
			for (var i = 0; i < data.length; i++) {
				if (data[i].value == companySelect) {
					hasValue = true;
				}
			}
			if (hasValue) {
				selectCombobox('company', companySelect);
			} else {
				selectCombobox('company', defaultSelect);
			}
		},
		onSelect: function(record) {
			companySelect = record.value;
			if (loadCompany) {
				var params = getParamsForCombobox();
				getRegion(params);
				getChain(params);
			}
		}
	});
}

/*function getBranch(params) {
	$('#brCode').combobox({
		url: '/bms/bms/get_branch.do',
		valueField: 'value',
		textField: 'text',
		method: 'post',
		editable: false,
		onBeforeLoad: function(param) {
			param.params = params
		},
		onLoadSuccess: function() {
			var data = $('#brCode').combobox('getData');
			var hasValue = false;
			for (var i = 0; i < data.length; i++) {
				if (data[i].value == branchSelect) {
					hasValue = true;
				}
			}
			if (hasValue) {
				selectCombobox('brCode', branchSelect);
			} else {
				selectCombobox('brCode', defaultSelect);
			}
		},
		onSelect: function(record) {
			branchSelect = record.value;
			if (loadBranch) {
				var params = getParamsForCombobox();
				getCompany(params)
				getRegion(params);
				getChain(params);
			}
		}
	});
}*/

function getRegion(params) {
	$('#region').combobox({
		url: '/bms/bms/get_region.do',
		valueField: 'value',
		textField: 'text',
		method: 'post',
		editable: false,
		onBeforeLoad: function(param) {
			param.params = params
		},
		onLoadSuccess: function() {
			var data = $('#region').combobox('getData');
			var hasValue = false;
			for (var i = 0; i < data.length; i++) {
				if (data[i].value == regionSelect) {
					hasValue = true;
				}
			}
			if (hasValue) {
				selectCombobox('region', regionSelect);
			} else {
				selectCombobox('region', defaultSelect);
			}
		},
		onSelect: function(record) {
			regionSelect = record.value;
			if (loadRegion) {
				var params = getParamsForCombobox();
				getCompany(params)
				getChain(params);
			}
		}
	});
}

function getChain(params) {
	$('#chainCode').combobox({
		url: '/bms/bms/get_chain.do',
		valueField: 'value',
		textField: 'text',
		method: 'post',
		editable: false,
		onBeforeLoad: function(param) {
			param.params = params
		},
		onLoadSuccess: function() {
			var data = $('#chainCode').combobox('getData');
			var hasValue = false;
			for (var i = 0; i < data.length; i++) {
				if (data[i].value == chainSelect) {
					hasValue = true;
				}
			}
			if (hasValue) {
				selectCombobox('chainCode', chainSelect);
			} else {
				selectCombobox('chainCode', defaultSelect);
			}
		},
		onSelect: function(record) {
			chainSelect = record.value;
			if (loadChain) {
				var params = getParamsForCombobox();
				getCompany(params)
				getRegion(params);
			}
		}
	});
}

function getItemType() {
	$('#itemType').combobox({
		url:'/bms/bms/get_itemtype.do',
		valueField:'value',
		textField:'text',
		editable: false
	});
}

function getParamsForCombobox() {
	var params = '{';
	var company = $('#company').combobox('getValue');
	var region = $('#region').combobox('getValue');
	var chain = $('#chainCode').combobox('getValue');
	
	if (company != null && company != '' && company != 'all') {
		params = params + '"company":"' + company + '",'
	}
	if (region != null && region != '' && region != 'all') {
		params = params + '"region":"' + region + '",'
	}
	if (chain != null && chain != '' && chain != 'all') {
		params = params + '"chain":"' + chain + '",'
	}
	
	if (params.length > 1) {
		params = params.substring(0, params.length - 1);
	}
	
	params += '}';
	
	return params;
}

function selectCombobox(combobox, selectValue) {
	if (combobox == 'company') {
		loadCompany = false;
	} else if (combobox == 'region') {
		loadRegion = false;
	} else if (combobox == 'brCodeOut') {
		loadBranch = false;
	} else if (combobox == 'chainCode') {
		loadChain = false;
	}
	
	$('#' + combobox).combobox('select', selectValue);
	
	if (combobox == 'company') {
		loadCompany = true;
	} else if (combobox == 'region') {
		loadRegion = true;
	} else if (combobox == 'brCodeOut') {
		loadBranch = true;
	} else if (combobox == 'chainCode') {
		loadChain = true;
	}
}

function tran() {
	MaskUtil.mask('获取报表中，请稍后……'); 
	$('#tran_form').form('submit', {
		url: '/bms/bms/tran_report.do',
		onSubmit: function (param) {
			
		},
		success: function(data) {
			var json = $.parseJSON(data);
			MaskUtil.unmask();
			if (json.status == 'success') {
				$('#download_excel').form('submit', {
					url: '/bms/bms/download_excel.do'
				});
			} else if (json.status == 'isnull') {
				$.messager.alert('提示', '没找到符合查询条件的数据，请确认查询条件是否正确！');
			} else if (json.status == 'failed') {
				$.messager.alert('提示', '系统内部出错，请联系管理员！');
			} else if (json.status == 'datenull') {
				$.messager.alert('提示', '必须选择开始日期和结束日期！');
			}
		}
	});
}

function getParamsForForm() {
	var params = '{';
	var company = $('#company').combobox('getValue');
	var region = $('#region').combobox('getValue');
	var chain = $('#chainCode').combobox('getValue');
	var branch = $('#brCodeOut').combobox('getValue');
	var startDate = $('#startDate').combobox('getValue');
	var endDate = $('#endDate').combobox('getValue');
	var itemCodeStart = $('#itemCodeStart').combobox('getValue');
	var itemCodeEnd = $('#itemCodeEnd').combobox('getValue');
	var foodType = $('#foodType').combobox('getValue');
	
	if (company != null && company != '' && company != 'all') {
		params = params + '"company":"' + company + '",'
	}
	if (region != null && region != '' && region != 'all') {
		params = params + '"region":"' + region + '",'
	}
	if (chain != null && chain != '' && chain != 'all') {
		params = params + '"chain":"' + chain + '",'
	}
	if (branch != null && branch != '' && branch != 'all') {
		params = params + '"branch":"' + branch + '",'
	}
	
	if (params.length > 1) {
		params = params.substring(0, params.length - 1);
	}
	
	params += '}';
	
	return params;
}