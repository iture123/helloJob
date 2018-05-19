<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/commons/basejs.jsp" %> 
<meta http-equiv="Access-Control-Allow-Origin" content="*">  
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>作业管理</title>
<style>
ul{
list-style-type:none;
padding:0;
}
ul li{
margin:10px;
padding:0;
}
table.pgTable{
	border-spacing:0;
	border-left:1px solid #888;
	border-top:1px solid #888;
	margin:0 20px;
/* background:#efefef; */
}
table.pgTable td{
	border-right:1px solid #888;
	border-bottom:1px solid #888;
	padding:5px ;
	line-height:16px;
	height:16px;
	font-size:12px;
	text-align:center;

}
table.pgTable td.tdKey{
	width:120px;
	font-weight:550;
	text-align:right;
}
table.pgTable td.tdValue{
	width:580px;
	font-weight:300;
	text-align:left;
}
table.pgTable td.tdTitle{
	background-color: #A8D7FF;
}
</style>
</head>
<body>
<div style="margin:5px auto" id="jobForm">
	&nbsp;&nbsp;编号<input class="easyui-numberbox" style="width:100px" name="jobId">
	&nbsp;&nbsp;名称<input class="easyui-textbox" style="width:100px" name="jobName">
	&nbsp;&nbsp;创建人<select class="easyui-combobox" name="creater" data-options="panelHeight:'auto',valueField:'id',textField:'text',url:' ${staticPath}/job/getHasJobUserList' "     style="width:120px"></select>
	
	 <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onClick="jobMvc.Controller.searchJob()" style="width:80px">查询</a>	
	 <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-reload'" onClick="jobMvc.Controller.cleanParam()" style="width:80px">清空</a>
	
</div>
 <div id="tb">
 	<a href="#" class="easyui-linkbutton" iconCls="icon-tip" plain="true" onclick="jobMvc.Controller.seeJob()">查看详细</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="jobMvc.Controller.addJob()">添加</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="jobMvc.Controller.editJob()">修改</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="jobMvc.Controller.delJob()">删除</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-tip" plain="true" onclick="jobMvc.Controller.seeJobTree()">作业树</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="jobMvc.Controller.scheduleJob()">挂载调度</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-no" plain="true" onclick="jobMvc.Controller.stopSchedule()">停用调度</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-ok" plain="true" onclick="jobMvc.Controller.runOnce()">手工执行</a>
</div>
 <table id="jobDg" style="height:660px"
            toolbar="#tb">
 </table>


<div id="addJob" title="添加作业"  closed="true" class="easyui-dialog" style="width:800px;height:‘auto''"  data-options="closed:'true' ">
	<form class="">
		 <ul>
			  <li>
			  	<div>作业名称  <input data-options="required:true" class="easyui-textbox" name="jobName" style="width:250px" ></div>
			  </li>
			  <li>
			  	作业类型
			  	<input id="jobType"  name="jobType" class="easyui-combobox"  style="width:150px"
   			 data-options="panelWidth:150,valueField:'id',textField:'name',url:'${path}/jobType/combobox',required:true">
			  </li>
			  <li>
				  	<div class="cmd" >
				  	 	执行命令	  <input class="easyui-textbox" label="hive sql"  name="command" multiline="true" prompt="请输入要执行的命令" style="width:700px;height:120px">
				  	</div>
			  </li>
			 <li id="">
		    ip
    	  	<input name="ip" type="text" class="easyui-textbox"  prompt="请输入作业部署机器的ip"  required />
    	  	  &nbsp;&nbsp;账号
    	  	<input name="jobUser" type="text" class="easyui-textbox"   prompt="请输入作业执行用户的账号"   required>
    	  &nbsp;&nbsp;密码
    	  	<input name="passwd" type="text" class="easyui-textbox"   prompt="请输入作业执行用户的密码" required />
		  </li>
			  <li><span style="margin-left:25px;margin-right:5px">备注</span><input name="remark" class="easyui-textbox" style="width:600px" ></li>
		 </ul>
	</form>
</div>
<div id="scheduleJobDlg" title="挂载调度"  closed="true" class="easyui-dialog" style="width:600px;height:400px"  >
	<div style="margin:5px 0 0 5px">
		<div>
			开始时间
    	  	<input name="beginTime" type="text" class="easyui-datebox"   />
		</div>
		<div style="margin:5px 0">
			结束时间
    	  	<input name="endTime" type="text" class="easyui-datebox" />
		</div>
		<div>自依赖
		  		<select name="isSelfRely" class="easyui-combobox" data-options="panelHeight:'auto',width:60" required>
		  			<option>否</option>
		  			<option>是</option>
		  		</select>
		</div>
		<div  style="margin:5px 0">
			失败重试次数<input name="tryCount" min="0" value="0" class="easyui-numberbox" style="width:100">
		</div>
		<div>失败重试间隔(分钟)<input name="tryInterval"  class="easyui-numberbox" min="1"  value="5" style="width:100"></div>
		<div  style="margin:5px 0">邮件告警
		<select name="receiver" class="easyui-combogrid" name="dept" style="width:280px;"
        data-options="
         	multiple:true,
            panelWidth:330,
            panelHeight:'auto', 
            idField:'email',
            textField:'name',
            url:'${path}/user/getAllUserList',
            columns:[[
                {field:'id',title:'编号',width:60},
                {field:'name',title:'姓名',width:100},
                {field:'email',title:'邮箱',width:160}
            ]]
        "></select>
		
		</div>
   	  <div  style="margin:5px 0">
   	  	调度方式<select id="scheType" name="scheType"  class="easyui-combobox" panelHeight="auto" style="width:100px"
   	  	data-options="onChange:function(newValue,oldValue){
   	  		$('#'+newValue).show();$('#'+newValue).siblings().hide();
   	  	}">
			<option value="timeSche">时间调度</option>
			<option value="relyPreJob">依赖上一级</option>
		</select>
		<span>
			<span id="timeSche" style="display:none">
				<input class="easyui-textbox" name="cron"  prompt="请输入cron表达式" style="width:150px" required>
				<div style="color:grey" >
					<div>每天6点执行：0 0 6 * * ?</div>
					<div>每周五6点执行： 0 0 6 ? * FRI</div>
					<div>每月的第一天6点执行： 0 0 6 1 * ?</div>
					<div>每30s执行一次：0/30 * * * * ?</div>
				</div>
			</span>
			<span id="relyPreJob" >
				<input id="relyPreJobCombobox" name="relyPreJob" class="easyui-textbox" style="width:200px"  required data-options="
					icons:[{
								iconCls:'icon-search',
								handler:function(e){
									openRelyJobDlg( jobDg.getSelectRow().id);
								}
							}]
				">
			</span>
		</span>
			
   	  </div>
		
	</div>
</div>

<div id="likeInputDlg" class="easyui-dialog" title="选择依赖的父级" style="width:905px;height:550px;padding:10px"
	 data-options="closed:'true' ">
	 	 <div style="margin:10px 320px">
	 	<input class="easyui-searchbox" id="likeInputSearchBox" prompt="请输入编号或者名称" style="width:200px"  >
	 </div>
	<div >
		<div id="likeInputDivLeft" style="float:left;width:420px;">
			<table id="relyJobSelectedDg" title="已选择依赖的父作业" data-options="singleSelect:true,height:400,onClickCell:function(index,field,value){
				if(field=='delete'){
						$('#relyJobSelectedDg').datagrid('deleteRow',index);
						$('#relyJobSelectedDg').datagrid('reload');
				}				
			}" >
			<thead>
					<tr>
					<th data-options="field:'id',width:50">编号</th>
						<th data-options="field:'jobName',width:260">名称</th>
						<th data-options="field:'delete',width:90,formatter:function(value,row,index){return '<a class=easyui-linkbutton-del>删除</a>'}">操作</th>
					</tr>
				</thead>
			</table>
		</div>
		<div id="likeInputDivRight" style="display:inline-block;margin-left:5px;width:420px;">
			<table id="likeInputSearchDataGrid" title="搜索结果" class="easyui-datagrid"  data-options="singleSelect:true,height:400,onClickCell:function(index,field,value){
				if(field=='add'){
							$('#likeInputSearchDataGrid').datagrid('selectRow',index);
							var row =  $('#likeInputSearchDataGrid').datagrid('getSelected');
				 			 $('#relyJobSelectedDg').datagrid('appendRow',row);
						     $('#likeInputSearchDataGrid').datagrid('deleteRow',index);
						     $('#likeInputSearchDataGrid').datagrid('reload');
						     $('.easyui-linkbutton-del').linkbutton({	iconCls:'icon-remove'});
				}				
			}" >
				<thead>
					<tr>
						<th data-options="field:'id',width:50">编号</th>
						<th data-options="field:'jobName',width:260">名称</th>	
						<th data-options="field:'add',width:90,formatter:function(value,row,index){return '<a class=easyui-linkbutton-add>添加</a>'}"">操作</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
</div>
<div  id="runOnceDlg"  class="easyui-dialog" title="手工执行一次" style="width:300px;height:250px;padding:10px"
	 data-options="closed:'true' ">
	<div>选择dt<input id="runOnceDt"  class="easyui-datebox" /></div>
	<div style="margin-top:10px">自依赖
			  		<select id="runOnceIsSelfRely" class="easyui-combobox" data-options="panelHeight:'auto',width:60">
			  			<option>否</option>
			  			<option>是</option>
			  		</select>
		</div>
</div>
<div id="jobTreeDlg" class="easyui-dialog" title="查看作业树" style="display:none;width:500px;height:600px;padding:10px"
	 data-options="closed:'true' ">
		<ul id="jobTree" class="ztree"></ul>
</div>
<div id="seeJobDlg" class="easyui-dialog" title="查看作业详细" style=";width:800px;height:'auto'''"
	 data-options="closed:'true' ">
		<div id="seeJobContent"></div>
</div>
<script type="text/juicer" id="seeJobTpl">  
	<ul>
		<li>作业编号：{{job.id}}</li>
		<li>作业名称：{{job.jobName}}</li>
		<li>作业类型：{{job.jobType}}</li>
		<li>部署信息 ip:{{job.ip}}   账号:{{job.jobUser}}  密码:{{job.passwd}}</li>
		<li>
			执行命令：{{job.command}}
		</li>
		<li>
			{@if scheBasicInfo != null }
				<table class="pgTable">
					<tr><td class="tdKey">调度方式</td><td class="tdValue">
						{@if scheBasicInfo.scheType == "relyPreJob" }
						依赖上级&nbsp;&nbsp;{{scheBasicInfo.cron}}
						{@else}
						时间依赖&nbsp;&nbsp;{{scheBasicInfo.cron}}
						{@/if}
					</td></tr>
					<tr><td class="tdKey">自依赖</td><td class="tdValue">{{scheBasicInfo.isSelfRely}}</td></tr>
					<tr><td class="tdKey">开始时间</td><td class="tdValue">{{scheBasicInfo.beginTime}}</td></tr>
					<tr><td class="tdKey">结束时间</td><td class="tdValue">{{scheBasicInfo.endTime}}</td></tr>
					<tr><td class="tdKey">失败重试次数</td><td class="tdValue">{{scheBasicInfo.tryCount}}</td></tr>
					<tr><td class="tdKey">失败重试间隔</td><td class="tdValue">{{scheBasicInfo.tryInterval}}</td></tr>
				</table>
			{@else}
			调度方式：无
			{@/if}
		</li>
		<li>创建时间：{{job.createTime}}</li>
		<li>备注：{{job.remark}}</li>
	</ul>
	  
</script>

</body>
<script src="${path }/static/job/jobBasicInfo.js?_v=${_version}"></script>
</html>