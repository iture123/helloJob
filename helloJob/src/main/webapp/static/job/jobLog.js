var jobLogDg;
$(function() {
	jobLogMvc.Init.initJobLogDg();
});
var jobLogMvc = {
	Init : {
		initJobLogDg : function(param) {
			jobLogDg = $('#jobLogDg')
					.datagrid(
							{
								url : path + "/jobLog/grid.do",
								queryParams : param,
								fit : false,
								striped : false,
								pagination : true,
								singleSelect : true,
								fitColumns : false,
								idField : 'id',
								sortName : 'beginTime',
								sortOrder : 'desc',
								pageSize : 20,
								columns : [ [
										{
											field : 'jobId',
											title : '作业编号',
											width : 70,
											sortable : true,
											formatter : function(value, row,
													index) {
												if (row.jobImg)
													return eval('('
															+ row.jobImg + ')').id;
											}
										},
										{
											field : 'job_name',
											title : '作业名称',
											width : 200,
											formatter : function(value, row,
													index) {
												if (row.jobImg)
													return eval('('
															+ row.jobImg + ')').jobName;
												return row.name;
											}
										},
										{
											field : 'jobState',
											title : '作业状态',
											width : 70,
											formatter : function(value, row,
													index) {
												if (value == "失败") {
													return "<span style='color:red'>失败</span>";
												} else if (value == "执行中") {
													return "<span style='color:#0000FF'>执行中</span>";
												} else if (value == "警告") {
													return "<span style='color:#CC9900'>警告</span>";
												} else {
													return "<span style='color:#009900'>"
															+ value + "</span>";
												}

											}
										}, {
											field : 'elapsedTime',
											title : '作业耗时',
											width : 70
										}, {
											field : 'beginTime',
											title : '开始时间',
											width : 130,
											sortable : true
										}, {
											field : 'endTime',
											title : '结束时间',
											width : 130,
											sortable : true
										}, {
											field : 'dt',
											title : 'dt',
											width : 70,
											sortable : true
										} ] ],
								onLoadSuccess : function(data) {
									$('.linkbutton').linkbutton();
								},
							});
		}
	},
	Controller : {
		seeLog : function() {
			var row = jobLogMvc.Service.getSelectRow();
			if (row.jobImg) {
				easyUtils.post(path + "/jobLog/seeLog.do", {
					jobLogId : row.id
				}, function(obj) {
					$("#logDiv").html(obj);
					$("#seeLogDlg").openDialog();
				});
			} else {
				window.open(row.trackingUrl);
			}
		},
		setSuccess : function() {
			var row = jobLogMvc.Service.getSelectRow();
			var jobState = row.jobState;
			 if (jobState == "成功") {
				parent.$.messager.alert('提示', "已经是成功状态 ！", 'warning');
				return;
			} else if(jobState =="执行中") {
				parent.$.messager.alert('提示', "请先停掉作业 ！", 'warning');
			}else{
				$.confirmDialog('您确定要设置为成功吗？', function() {
					easyUtils.post(path + "/jobLog/setSuccess.do", {
						jobLogId : row.id
					}, function(obj) {
						jobLogDg.datagrid("reload");
					});
				});
			}
		},
		searchJobLog : function() {
			var param = jobLogMvc.Service.getJobLogParam();
			jobLogMvc.Init.initJobLogDg(param);
		},
		cleanParam : function() {
			easyuiUtils.clearParam("jobLogForm");
			jobLogMvc.Controller.searchJobLog();
		},
		killJob : function() {
			var row = jobLogMvc.Service.getSelectRow();
			if (row.jobState == "执行中") {
				$.confirmDialog("确定终止该作业吗",function() {
					easyUtils.post(path + "/jobLog/killJob", {
						jobLogId : row.id
					}, function(obj) {
						jobLogDg.datagrid("reload");
					});
				});
			} else {
				parent.$.messager.alert('提示', "该作业已经停止 ！", 'warning');
			}
		},
		runOnce : function() {
			var row = jobLogMvc.Service.getSelectRow();
			$("#runOnceDt").datebox("setValue", row.dt + "");
			$("#runOnceDlg").openDialog(
					function() {
						var param = {};
						param.jobId = row.jobId;
						param.dt = $("#runOnceDt").datebox("getValue");
						param.isSelfRely = $("#runOnceIsSelfRely").combobox("getValue");
						easyUtils.post(path + "/sche/runOnce.do", param,
								function(obj) {
									jobLogDg.datagrid("reload");
								});
					});
		},
		seeJobTree : function() {
			var row = jobLogMvc.Service.getSelectRow();
			easyUtils.post(path + "/scheRelyJob/getTreeList?", {jobId : row.jobId}, function(obj) {
				$('#jobTree').tree(
						{
							data : obj,
							parentField : 'pid',
							lines : true,
							checkbox : false,
							onClick : function(node) {
								$("#jobTree .tree-title").each(
										function(index, dom) {
											if (node.text == $(this).html()) {
												$(this).parent().addClass(
														"tree-node-selected");
											} else {
												$(this).parent().removeClass(
														"tree-node-selected");
											}
										});
							},
							onLoadSuccess : function(node, data) {
								$("#jobTreeDlg").show().dialog({
									top : 30,
									buttons : [ {
										text : '关闭',
										handler : function() {
											$("#jobTreeDlg").dialog("close");
										}
									} ]
								}).dialog("open");
							},
							animate : true
						});
			});
		}
	}
	,Service : {
		getJobLogParam : function() {
			var param =	easyuiUtils.getParam("jobLogForm");
			return param;
		},
		getSelectRow : function() {
			var row = jobLogDg.datagrid("getSelected");
			if (row == null) {
				parent.$.messager.alert('提示', "请选中一行", 'warning');
				throw 'please select one row!';
			}
			return row;
		}
	}

}
