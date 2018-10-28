<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/commons/basejs.jsp" %> 
<meta http-equiv="Access-Control-Allow-Origin" content="*">  
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>执行主机</title>
<style type="text/css">
#addJobType table td{
	margin-top:10px;
}
</style>
<body>
	<div class="easyui-layout" data-options="fit:true,border:false">
	    <div data-options="region:'center',border:false"  style="overflow: hidden;">
	        <table id="hostDg"></table>
	    </div>
	    <div id="orgToolbar" style="display: none;">
	    	<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="hostMvc.Controller.add()">添加</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="hostMvc.Controller.edit()">修改</a>
	    </div>
	</div>
	<div  id="addHostDlg"  class="easyui-dialog" title="添加执行主机" style="width:420px;height:'auto';padding:10px"
		 data-options="closed:'true' ">
	     	<table cellPadding="5">
	     	  	<tr><td width="60">协议</td>	<td>
	     	  	<select class="easyui-combobox" id="protocol" name="protocol" style="width:100px;" panelHeight="auto"><option>ssh</option></select>
	     	  	</td></tr>
		     	<tr class="ssh" ><td >主机ip</td>	<td><input name="host"class="easyui-textbox"  prompt=""  required /></td></tr>
		     	<tr class="ssh"><td >ssh端口</td>	<td><input name="port" class="easyui-numberbox "   defaultValue="22"  required /></td></tr>
		     <!-- 	 <tr class="jdbc" ><td >driver class</td>	<td><input name="driverClass" class="easyui-textbox"   defaultValue="com.mysql.jdbc.Driver" style="width:300px"   required /></td></tr>
		     	 <tr class="jdbc" ><td >url</td>	<td><input name="jdbcUrl" class="easyui-textbox"  defaultValue="jdbc:mysql://127.0.0.1:3306/test"   style="width:300px" required /></td></tr> -->
		   		<tr><td>账号</td><td><input name="username" class="easyui-textbox"   prompt=""   required></td></tr>
		   		<tr><td>密码	</td><td><input name="passwd"  class="easyui-textbox"   prompt="" required /></td></tr>
	   		</table>
	</div>
</body>
<script src="${path}/static/job/hostInfo.js"></script>
</html>