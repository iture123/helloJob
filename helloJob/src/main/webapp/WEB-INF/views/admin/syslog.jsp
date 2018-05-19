<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    $(function () {
        $('#sysLogDataGrid').datagrid({
            url: '${path }/sysLog/dataGrid',
            striped: true,
            pagination: true,
            singleSelect: true,
            idField: 'id',
            pageSize: 20,
            pageList: [10, 20, 30, 40, 50, 100, 200, 300, 400, 500],
            columns: [[{
                width: '80',
                title: '登录名',
                field: 'loginName',
                sortable: true
            }, {
                width: '80',
                title: '用户名',
                field: 'roleName'
            }, {
                width: '100',
                title: 'IP地址',
                field: 'clientIp',
                sortable: true
            }, {
                width: '800',
                title: '内容',
                field: 'optContent'
            }, {
                width: '130',
                title: '创建时间',
                field: 'createTime'
            }]]
        });
    });
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false">
        <table id="sysLogDataGrid" data-options="fit:true,border:false"></table>
    </div>
</div>