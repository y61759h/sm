$(document).ready(function() {
	
	//监听树
	$('#menu_tree').tree({
		onClick: function(node) {
			
			if (!isLeaf(node)) {
				return;
			}
			
			if (exist(node)) {
				$('#tabs').tabs('select', node.text);
			} else {
				addTabs(node);
			}
			
		}
	});
	
	tabsEvents();
	
	closeTabsEvent();
	
});

//检测是否叶节点
function isLeaf(node) {
	return $('#menu_tree').tree('isLeaf', node.target);
}

//检测标签是否存在
function exist(node) {
	return $('#tabs').tabs('exists', node.text);
}

//新增标签
function addTabs(node) {
	
	$("#tabs").tabs("add", {
		title: node.text,
		content: '<iframe scrolling="yes" frameborder="0"  src="'+node.linkSource+'" style="width:100%;height:100%;"></iframe>',
		selected: true,
		closable: true
	});
	
	tabsEvents();
}

//监听标签事件
function tabsEvents() {
	$(".tabs-inner").dblclick(function(){
		var subtitle = $(this).children("span").text();
		$('#tabs').tabs('close', subtitle);
	})
	
	$('.tabs-inner').bind('contextmenu', function(e){
		$('#mm').menu('show', {
            left: e.pageX,
            top: e.pageY
        });
        
        var tab =$(this).children("span").text();
		$('#mm').data("tab", tab);
		
		return false;
	});
}

//监听右键菜单事件
function closeTabsEvent()
{
	//关闭当前
	$('#mm-tabclose').click(function(){
		var tab = $('#mm').data("tab");
		closeTab(tab);
	})
	//全部关闭
	$('#mm-tabcloseall').click(function(){
		$('.tabs-inner span').each(function(i, n){
			var text = $(n).text();
			closeTab(text);
		});	
	});
	//关闭除当前之外的TAB
	$('#mm-tabcloseother').click(function(){
		var tab = $('#mm').data("tab");
		$('.tabs-inner span').each(function(i, n){
			var text = $(n).text();
			if(text != tab)
				closeTab(text);
		});	
	});
	//关闭当前右侧的TAB
	$('#mm-tabcloseright').click(function(){
		var tab = $('#mm').data("tab");
		$('#tabs').tabs('select', tab);
		var nextall = $('.tabs-selected').nextAll();
		if(nextall.length == 0){
			return false;
		}
		nextall.each(function(i, n){
			var text = $('a:eq(0) span', $(n)).text();
			closeTab(text);
		});
		return false;
	});
	//关闭当前左侧的TAB
	$('#mm-tabcloseleft').click(function(){
		var tab = $('#mm').data("tab");
		$('#tabs').tabs('select', tab);
		var prevall = $('.tabs-selected').prevAll();
		if(prevall.length == 0){
			return false;
		}
		prevall.each(function(i, n){
			var text = $('a:eq(0) span', $(n)).text();
			closeTab(text);
		});
		return false;
	});

	//退出
	$("#mm-exit").click(function(){
		$('#mm').menu('hide');
	})
}

//关闭标签
function closeTab(tab) {
	if (tab != "欢迎使用") {
		$('#tabs').tabs('close', tab);
	}
}