var jobTypeDg;
$(function() {
	jobTypeDg = $('#jobTypeDg').datagrid({
    	url:path+"/jobType/grid",
    	 fit : false,  striped : false, pagination : true, singleSelect : true, fitColumns:false,
        idField : 'ID',   sortName : 'create_time',   sortOrder : 'desc',pageSize : 20,
        columns:[[
            {field:'id',title:'序号',width:40,sortable:true},
            {field:'name',title:'名称',width:200},
            {field:'seq',title:'排序',width:80},
            {field:'createTime',title:'创建时间',width:140,sortable:true}
        ]],
        toolbar : '#orgToolbar'
    });
});
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
function getJobTypeDgSelectRow(){
	var row = jobTypeDg.datagrid("getSelected");
	if(row ==null){
        parent.$.messager.alert('提示', "请选中一行", 'warning');
        return null;
	}
	return row;
}