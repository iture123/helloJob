<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/commons/basejs.jsp" %> 
<meta http-equiv="Access-Control-Allow-Origin" content="*">  
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>业务类型</title>
<body>
	<div class="easyui-layout" data-options="fit:true,border:false">
	    <div data-options="region:'center',border:false"  style="overflow: hidden;">
	        <table id="jobTypeDg"></table>
	    </div>
	    <div id="orgToolbar" style="display: none;">
	    	<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="jobType.add()">添加</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="jobType.update()">修改</a>
	    </div>
	</div>
	<div  id="addJobType"  class="easyui-dialog" title="业务类型" style="width:300px;height:160px;padding:10px"
		 data-options="closed:'true' ">
		<div>名称<input id="jobType"  class="easyui-textbox"  data-options="required:true" />	</div>
		<div style="margin-top:10px">排序<input id="seq" value="10"  class="easyui-numberspinner" 
		style="width: 140px; height: 29px;" required="required" data-options="editable:false"></div>
	</div>
</body>
<script src="${path}/static/job/jobType.js?ttime=20170905"></script>
</html>