<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/commons/basejs.jsp" %>
<meta http-equiv="X-UA-Compatible" content="edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="application/javascript">
    $(function(){
        $('#dataGrid').datagrid({
            url : '${path }/user/dataGrid',
            fit : true,
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            idField : 'id',
            sortName : 'createTime',
            sortOrder : 'asc',
            pageSize : 20,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            columns : [ [ {
                width : '80',
                title : '登录名',
                field : 'loginName',
                sortable : true
            }, {
                width : '80',
                title : '姓名',
                field : 'name',
                sortable : true
            },{
                width : '80',
                title : '部门ID',
                field : 'organizationId',
                hidden : true
            },{
                width : '80',
                title : '所属部门',
                field : 'organizationName'
            },{
                width : '130s',
                title : '创建时间',
                field : 'createTime',
                sortable : true
            },  {
                width : '40',
                title : '性别',
                field : 'sex',
                sortable : true,
                formatter : function(value, row, index) {
                    switch (value) {
                        case 0:
                            return '男';
                        case 1:
                            return '女';
                    }
                }
            }, {
                width : '40',
                title : '年龄',
                field : 'age',
                sortable : true
            },{
                width : '120',
                title : '电话',
                field : 'phone',
                sortable : true
            }, {
                    field : 'action',
                    title : '操作',
                    width : 100,
                    formatter : function(value, row, index) {
                        return "<a href=\"#\" class=\"easyui-linkbutton\" data-options=\"iconCls:'icon-add',plain:true\"/a>";
                    }
                }] ],
            onLoadSuccess:function(data){
                $('.easyui-linkbutton').linkbutton({text:'查询',plain:true,iconCls:'icon-add'});
            }
        });
    })
</script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
<%--<div data-options="region:'center',border:true,title:'用户列表'" >--%>
    <%--<table id="dataGrid" data-options="fit:true,border:false"></table>--%>
<%--</div>--%>
<br/>
<h1>从库：${count}</h1>
</body>
</html>