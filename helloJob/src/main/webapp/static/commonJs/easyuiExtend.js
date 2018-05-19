$.fn.openDialog = function(okCallback,param){
	var $this = $(this);
	var buttons = [];
	if(typeof okCallback == "function"){
		buttons.push({
			text : '提交',
			iconCls : 'icon-ok',
			modal: true,
			handler:function(){
				okCallback();
				$this.dialog("close");
			}
		});
	}
	buttons.push({
		text : '关闭',
		iconCls:"icon-cancel",
		handler : function() {
			$this.dialog("close");
		}
	});
	var defaultParam = {
			top : 30,
			modal: true,
			buttons : buttons
	};
	$.extend(defaultParam,param);
	$this.dialog(defaultParam).dialog("open");
};
$.extend({
	confirmDialog : function(msg,callback){
		$.messager.confirm('确认对话框', msg, function(r){
			if (r){
				callback();
			}
		});
	}
});
$.fn.getSelectRow = function(){
	var row = $(this).datagrid("getSelected");
	if(row ==null){
        parent.$.messager.alert('提示', "请选中一行", 'warning');
        throw new Eerror('please select one row!');
	}
	return row;
}