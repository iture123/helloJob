$(function(){
	$('#emailPg').propertygrid({
      showGroup:false,
      scrollbarSize:0,
	    columns:[[
	        {field:'name',title:'名称',width:200},
	        {field:'value',title:'值',width:300}
	    ]]
	});
	loadEmailPg();
});
function loadEmailPg(){
	easyUtils.post(path+"/email/get.do",{},function(email){
		var data = {"total":4,"rows":[
			{name:"账号","id":"sender","value":email.sender,"editor":{
				"type":"validatebox",
				"options":{
					"validType":"email"
				}
			}},
			{"name":"密码",id:'passwd',"value":email.passwd,"editor":"text"},
			{name:"主机","id":"hostName","value":email.hostName,"editor":"text"},
			{name:"主机","id":"port","value":email.port,"editor":"numberbox"},
			{name:"修改人","id":"editor","value":email.editor},
			{name:"修改日期","id":"createTime","value":email.createTime}
			
		]}
		$('#emailPg').propertygrid("loadData",data);
	});
}
function updateEmailServerInfo(){
	var param = {};
	var rows = $('#emailPg').propertygrid("getData").rows;
	for(var i=0; i<4;i++){
		param[rows[i].id]=rows[i].value;
	}
	easyUtils.post(path+"/email/update", param, function(email) {
		$.messager.alert("成功", "更新成功！","info"); 
	});
	
}