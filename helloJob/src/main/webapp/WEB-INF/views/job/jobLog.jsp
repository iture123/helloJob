<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/commons/basejs.jsp" %> 
<meta http-equiv="Access-Control-Allow-Origin" content="*">  
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>作业日志</title>
</head>
<body>
<div id="tb">
	<a href="#" class="easyui-linkbutton" iconCls="icon-tip" plain="true" onclick="jobLogMvc.Controller.seeLog()">查看日志</a>
 	<a href="#" class="easyui-linkbutton" iconCls="icon-ok" plain="true" onclick="jobLogMvc.Controller.runOnce()">手工执行</a>
 		<a href="#" class="easyui-linkbutton" iconCls="icon-ok" plain="true" onclick="jobLogMvc.Controller.setSuccess()">设为成功</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-tip" plain="true" onclick="jobLogMvc.Controller.seeJobTree()">作业树</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-no" plain="true" onclick="jobLogMvc.Controller.killJob()">停止运行</a>
</div>
<div style="margin:8px" id="jobLogForm">
	<span>作业id<input name="jobId"  class="easyui-numberbox"></span>
&nbsp;&nbsp;&nbsp;<span>dt<input name="dt"  type="text" class="easyui-datebox" ></span>
	&nbsp;<span>作业状态
		<select class="easyui-combobox" name="jobState" style="width:80px" panelHeight="auto">
			<option value="">全部</option>
			<option>成功</option>
			<option>失败</option>
			<option>执行中</option>
		</select>
	</span>
	
	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onClick="jobLogMvc.Controller.searchJobLog()" style="width:80px">查询</a>
	 <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-reload'" onClick="jobLogMvc.Controller.cleanParam()" style="width:80px">清空</a>
	
</div>


 <table  id="jobLogDg" style="height:630px"
            singleSelect="true" fitColumns="true"  toolbar="#tb">
</table>
<div id="seeLogDlg" class="easyui-dialog" title="查看日志" style="display:none;width:1100px;height:500px;padding:10px"
	 data-options="closed:'true' ">
	 <div id="logDiv"></div>
</div>
<div id="jobTreeDlg" class="easyui-dialog" title="查看作业树" style="display:none;width:500px;height:600px;padding:10px"
	 data-options="closed:'true' ">
		<ul id="jobTree" class="ztree"></ul>
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
<script src="${path }/static/job/jobLog.js?_v=${_version}"></script>
</body>
</html>