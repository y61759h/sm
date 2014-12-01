$(document).ready(function() {
	
	$("input",$("#input_password").next("span")).keydown(function(event) {
        if (event.which == 13) {
        	var password = $(this).val();
        	$('#input_password').textbox('setValue', password);
        	login();
        }
    });
	
	//登陆窗口
	$('#login').window({
		title: '登陆',
		width: 450,
		modal: true,
		minimizable: false,
		closable: false
	});
	
});

function login() {
	$('#login_form').form('submit', {
		onSubmit: function(param) {
			var value = $('#userID').textbox('getValue');
			$('#userID').textbox('setValue', value.toUpperCase());
		},
		success: function(data) {
			var json = $.parseJSON(data);
			if (json.status == 'success') {
				if (json.login == 'success') {
					window.location.href = '/bms/pages/to_main.do';
				} else {
					if (json.reason == '101') {
						$.messager.alert('登陆失败', '用户不存在！', 'error');
					} else if (json.reason == '102') {
						$.messager.alert('登陆失败', '密码不正确！', 'error');
					} else {
						$.messager.alert('debug', 'reason == ' + json.reason);
					}
				}
			} else {
				window.location.href = '/bms/pages/to_error.do';
			}
		}
	});
}

function clear() {
	$('#login_form').form('clear');
}
