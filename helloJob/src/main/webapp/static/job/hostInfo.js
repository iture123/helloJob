var hostDg;
$(function() {
	hostDg = $('#hostDg').datagrid({
    	url:path+"/host/datagrid",
    	 fit : false,  striped : false, pagination : true, singleSelect : true, fitColumns:false,
        idField : 'ID',   sortName : 'create_time',   sortOrder : 'desc',pageSize : 20,
        columns:[[
            {field:'id',title:'序号',width:50,sortable:true},
            {field:'protocol',title:'协议',width:60},
            {field:'username',title:'账号',width:80},
            {field:'passwd',title:'密码',width:80},
            {field:'host',title:'ip',width:100},
            {field:'port',title:'端口',width:50},
            {field:'driverClass',title:'driver class ',width:150},
            {field:'jdbcUrl',title:'jdbc url',width:300},
            {field:'createTime',title:'创建时间',width:140}
        ]],
        toolbar : '#orgToolbar'
    });
	hostMvc.View.init();
});
var hostMvc={
		View:{
			init:function(){
				hostMvc.View.initProtocolCombobox();
			}
			,initProtocolCombobox:function(){
				$("#protocol").combobox({
					onChange:function(newValue,oldValue){
						if(newValue=="ssh"){
							$("#addHostDlg .ssh").show();
							$("#addHostDlg .jdbc").hide();
						}else if(newValue=="jdbc"){
							$("#addHostDlg .ssh").hide();
							$("#addHostDlg .jdbc").show();
						}
						$("#addHostDlg").dialog("resize");
					} 
				});
			}
		},
		Controller:{
			add:function(){
				$("#addHostDlg").openDialog(function(){
					var param = easyuiUtils.getParam("addHostDlg");
					easyUtils.post(path+"/host/add",param,function(obj){
						hostDg.datagrid("reload");
					});
				});
				easyuiUtils.clearParam("addHostDlg");
				$("#protocol").combobox("setText","ssh");
				$("#addHostDlg .jdbc").hide();
				$("#addHostDlg").dialog("resize");
			}
			,edit:function(){
				var row = hostMvc.Service.getSelectRow();
				var id = row.id;
				if(id==1){
				       parent.$.messager.alert('警告', "该记录不允许修改！", 'warning');
				       return;
				}
				easyUtils.post(path+"/host/get",{id:id},function(obj,msg){
				    $("#addHostDlg").openDialog(function(){
				    	var param = jobMvc.Service.getAddJobParam();
						param.id =jobId ;
						easyUtils.post(path+"/job/update",param,function(result){
								jobDg.datagrid("reload");
						});
				    },{
				    	title:'修改作业'
				    });
					easyuiUtils.clearParam("addHostDlg",obj);
					$("#protocol").combobox("setValue",obj.protocol);
					easyuiUtils.fillParam("addHostDlg",obj);
			 });
			}
		}
		,Service:{
			getSelectRow:function(){
				var row = hostDg.datagrid("getSelected");
				if(row ==null){
			        parent.$.messager.alert('提示', "请选中一行", 'warning');
			        return null;
				}
				return row;
			}
		}
}
var jobType={
	getJobTypeSelectRow:function(){
			var row = jobTypeDg.datagrid("getSelected");
			if(row ==null){
		        parent.$.messager.alert('提示', "请选中一行", 'warning');
		        throw 'please select one row!';
			}
			return row;
	},
	add:function(){
		$("#addJobType").show().dialog(
				{
					top : 30,
					buttons : [
						{
							text : '提交',
							iconCls : 'icon-ok',
							modal: true,
							handler:function(){
								var param = {};
								param.name = $.trim($("#jobType").val());
								param.seq = $("#seq").val();
								if(param.name==""){
									$.messager.alert("警告", "名称不能为空","error");  
									return;
								}
								$.post(path+"/jobType/add",param,function(result){
									if(result.success){
										$("#addJobType").dialog("close");
										jobTypeDg.datagrid("reload");
									}else{
										$.messager.alert("警告", result.msg,"error");  
									}
								},"json");
							}
						},
						{
							text : '取消',
							handler : function() {
								$("#addJobType").dialog("close");
							}
						}
					]
				}).dialog("open");
	},
	update:function(){
		var row = jobType.getJobTypeSelectRow();
		$("#jobType").textbox("setValue",row.name);
		$("#seq").numberspinner("setValue",row.seq);
		$("#addJobType").show().dialog(
				{
					title:"修改",
					top : 30,
					buttons : [
						{
							text : '提交',
							iconCls : 'icon-ok',
							modal: true,
							handler:function(){
								var param = {};
								param.id = row.id;
								param.name = $.trim($("#jobType").val());
								param.seq = $("#seq").numberspinner("getValue");
								if(param.name==""){
									$.messager.alert("警告", "名称不能为空","error");  
									return;
								}
								$.post(path+"/jobType/update",param,function(result){
									if(result.success){
										$("#addJobType").dialog("close");
										jobTypeDg.datagrid("reload");
									}else{
										$.messager.alert("警告", result.msg,"error");  
									}
								},"json");
							}
						},
						{
							text : '取消',
							handler : function() {
								$("#addJobType").dialog("close");
							}
						}
					]
				}).dialog("open");
	}
}

function getSelectRow(){
	var row = jobTypeDg.datagrid("getSelected");
	if(row ==null){
        parent.$.messager.alert('提示', "请选中一行", 'warning');
        return null;
	}
	return row;
}