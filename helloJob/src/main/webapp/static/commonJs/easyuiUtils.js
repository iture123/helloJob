var easyuiUtils = {
	validateParam:function(param,name,options){
		if(options.required){
			if( (options.multiple && param[name].length ==0) || ! param[name] ){
				$.messager.alert("警告","请输入完整参数！","warning");
				throw name + " validatebox is not valid";
			}
		}
	}
	,getParam:function(domId){
		var param =  {};
		//easyui-combobox
		$("#"+domId+" .combobox-f").each(function(index,dom){
			if($(this).next().is(":visible")){
				var name = $(this).attr("textboxname"); 
				var options = $(this).combobox("options");
				if(options.multiple){
					param[name] =  $(this).combobox("getValues");
				}else{
					param[name] =  $(this).combobox("getValue");
				}
				easyuiUtils.validateParam(param,name,options);
			}
		});
		//easyui-textbox
		$("#"+domId+" .easyui-textbox").each(function(index,dom){
			if($(this).next().is(":visible")){
				var name = $(this).attr("textboxname"); 
				param[name] = $.trim( $(this).textbox("getValue"));
				var options = $(this).textbox("options");
				easyuiUtils.validateParam(param,name,options);
			}
		});
		//easyui-combogrid
		$("#"+domId+" .easyui-combogrid").each(function(index,dom){
			if($(this).next().is(":visible")){
				var name = $(this).attr("textboxname"); 
				var options = $(this).combogrid("options");
				if(options.multiple){
					param[name] =  $(this).combogrid("getValues");
				}else{
					param[name] =  $(this).combogrid("getValue");
				}
				easyuiUtils.validateParam(param,name,options);
			}
		});
		//easyui-switchbutton
		$("#"+domId+" .easyui-switchbutton").each(function(index,dom){
			var name = $(this).attr("switchbuttonname");  
			param[name] = $(this).switchbutton("options").checked;
		});
		//获取easyui-datebox
		$("#"+domId+" .easyui-datebox").each(function(index,dom){
			if($(this).next().is(":visible")){
				var name = $(this).attr("textboxname"); 
				var options = $(this).datebox("options");
				param[name] =  $(this).datebox("getValue");
				easyuiUtils.validateParam(param,name,options);
			}
		});
		//easyui-numberbox
		$("#"+domId+" .easyui-numberbox").each(function(index,dom){
			if($(this).next().is(":visible")){
				var name = $(this).attr("textboxname"); 
				var options = $(this).numberbox("options");
				param[name] =  $(this).numberbox("getValue");
				easyuiUtils.validateParam(param,name,options);
			}
		});
		//easyui-propertygrid
		$("#"+domId+" .datagrid-btable").each(function(index,dom){
			if($(this).is(":visible")){
		//		var rows = $(this).propertygrid("getData").rows;
			//	console.log(rows);
			}
		});
		return param;
	}
	,clearParam:function(domId){
		$("#"+domId+" .easyui-textbox").each(function(index,dom){
			$(this).textbox("clear");
		});
		$("#"+domId+" .easyui-datebox").each(function(index,dom){
			$(this).datebox("clear");
		});
		$("#"+domId+" .easyui-numberbox").each(function(index,dom){
			$(this).numberbox("clear");
		});
		$("#"+domId+" .easyui-combobox").each(function(index,dom){
			$(this).combobox("clear");
			$(this).combobox("setValue","");
		});
	}
	,fillParam:function(domId,data){
		$("#"+domId+" .easyui-textbox").each(function(index,dom){
			var name = $(this).attr("textboxname"); 
			if($(this).next().is(":visible")){
				var value = data[name];
				if(value)$(this).textbox("setValue",value);
			}
		});
		$("#"+domId+" .easyui-numberbox").each(function(index,dom){
			var name = $(this).attr("textboxname"); 
			if($(this).next().is(":visible")){
				var value = data[name];
				if(value)$(this).numberbox("setValue",value);
			}
		});
		$("#"+domId+" .easyui-combobox").each(function(index,dom){
			if($(this).next().is(":visible")){
				var name = $(this).attr("textboxname"); 
				var value = data[name];
				if(value)$(this).combobox("setValue",value);
			}
		});
		$("#"+domId+" .easyui-datebox").each(function(index,dom){
			var name = $(this).attr("textboxname"); 
			if($(this).next().is(":visible")){
				var value = data[name];
				if(value)$(this).datebox("setValue",value+"");
			}
		});
	}
}