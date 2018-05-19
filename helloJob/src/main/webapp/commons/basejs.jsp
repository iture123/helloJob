<%--标签 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="edge" />
<link rel="shortcut icon" href="${staticPath }/static/style/images/favicon.ico" />
<%-- [EasyUI] --%>
<link rel="stylesheet" type="text/css" href="${staticPath }/static/js/jquery-easyui/themes/gray/easyui.css">
<link rel="stylesheet" type="text/css" href="${staticPath }/static/js/jquery-easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${staticPath }/static/js/jquery-easyui/themes/color.css">
<link rel="stylesheet" type="text/css" href="${staticPath }/static/style/css/common.css">
<link rel="stylesheet" type="text/css" href="${staticPath }/static/style/css/icon.css">
<%-- [my97日期时间控件] --%>
<script type="text/javascript" src="${staticPath }/static/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${staticPath }/static/js/jquery-easyui/jquery.min.js"></script>
<script type="text/javascript" src="${staticPath }/static/js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${staticPath }/static/js/jquery-easyui/locale/easyui-lang-zh_CN.js"></script>
<%-- [扩展JS] --%>
<script type="text/javascript" src="${staticPath }/static/js/arrayToTree.js"></script>
<script type="text/javascript" src="${staticPath }/static/js/extJs.js"></script>
<script type="text/javascript" src="${staticPath }/static/commonJs/easyUtils.js" charset="utf-8"></script>
<script type="text/javascript" src="${path}/static/commonJs/comboboxUtils.js"></script>
<script type="text/javascript" src="${path}/static/commonJs/easyuiUtils.js"></script>
<script type="text/javascript" src="${path}/static/commonJs/easyuiExtend.js"></script>
<script type="text/javascript" src="${path}/static/js/juicer/juicer-min.js"></script>
<script type="text/javascript">
    var basePath = "${staticPath }";
    var path = "${staticPath }";
    
    juicer.set({
    	'tag::operationOpen': '{@',
    	'tag::operationClose': '}',
    	'tag::interpolateOpen': '{{',
    	'tag::interpolateClose': '}}',
    	'tag::noneencodeOpen': '~~{',
    	'tag::noneencodeClose': '}',
    	'tag::commentOpen': '{#',
    	'tag::commentClose': '}'
    });    
    $.fn.datebox.defaults.formatter = function(date){
    	return easyUtils.toYyyyymmdd(date);
    }
    $.fn.datebox.defaults.parser = function(s){
    	if (!s) return new Date();
		var y = parseInt(s.substring(0,4));
		var m =  parseInt(s.substring(4,6));
		var d =  parseInt(s.substring(6,8));
		return new Date(y,m-1,d);
    }
</script>
