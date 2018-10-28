var jobDg;
var jobMvc = {
		View:{
			init:function(){
				jobMvc.View.initAppTypeCombobox();
			}
			,initAppTypeCombobox:function(){
				$("#appType").combobox({
					onChange:function(newValue,oldValue){
						$("#appTypeLi ."+newValue).show();
						$("#appTypeLi ."+newValue).siblings().hide();
					} 
				});
			}
		}
		,Controller:{
			seeJob:function( ){
				var selectRow = jobDg.getSelectRow();
				easyUtils.post(path+"/job/get",{jobId:selectRow.id},function(obj,msg){
					var html = juicer("#seeJobTpl",obj);
					$("#seeJobContent").html(html);
					$.parser.parse($("#seeJobContent"));
					$("#seeJobDlg").openDialog();
				
				});
			}
			,addJob:function(){
				$("#addJob").openDialog(function(){
					var param = jobMvc.Service.getAddJobParam();
					easyUtils.post(path+"/job/add",param,function(obj){
						jobDg.datagrid("reload");
					});
				});
				easyuiUtils.clearParam("addJob");
				var jobTypeData = $("#jobType").combobox('getData');
				if(jobTypeData.length > 0 )		$("#jobType").combobox("select",jobTypeData[0].id);
		
			}
			,editJob:function(){
				var selectRow = jobDg.getSelectRow();
				var jobId = selectRow.id;
				easyUtils.post(path+"/job/get",{jobId:jobId},function(obj,msg){
				    $("#addJob").openDialog(function(){
				    	var param = jobMvc.Service.getAddJobParam();
						param.id =jobId ;
						easyUtils.post(path+"/job/update",param,function(result){
								jobDg.datagrid("reload");
						});
				    },{
				    	title:'修改作业'
				    });
					easyuiUtils.clearParam("addJob",obj);
					easyuiUtils.fillParam("addJob",obj.job);
				});
			}
			/**
			 * 删除作业
			 */
			,delJob:function (){
				var selectRow =  jobDg.getSelectRow();
				if(selectRow.scheType != null){
					$.messager.alert("错误", "请先停用调度 !","error");  
					return;
				}
				var jobId = selectRow.id;
				$.confirmDialog('您确定要删除该作业吗？',function(){
					easyUtils.post(path+"/job/delJob",{jobId:jobId},function(obj){
						jobDg.datagrid("reload");
					});
				});
			}
			/**
			 * 挂载调度
			 */
			,scheduleJob:function(){
				var selectRow = jobDg.getSelectRow();
				if(selectRow.scheType){
					$.messager.alert("警告", "请先停用调度！","warning");  
					return;
				}
				var jobId = selectRow.id;
				 $('#scheType').combobox('select',"relyPreJob");
				 $("#relyPreJobCombobox").textbox("clear");
				 $("#scheduleJobDlg").openDialog(function(){
					 	var param = easyuiUtils.getParam("scheduleJobDlg");
					 	if(param.relyPreJob) param.relyPreJob = param.relyPreJob.split(",");
					 	console.log(param);
						param.jobId = jobId;
						easyUtils.post(path+"/sche/mount",param,function(obj){
							jobDg.datagrid("reload");
						});
				 });
			}
			,stopSchedule:function(){
				var selectRow = jobDg.getSelectRow();
				if(selectRow.scheType ==null){
					$.messager.alert("警告", "该作业未被调度 !","warning");  
					return;
				}
				var jobId = selectRow.id;
				$.messager.confirm('确认对话框', '您确定要停用该调度吗？', function(r){
					if (r){
						var param ={};
						param.jobId = jobId;
						easyUtils.post(path+"/sche/stopSchedule",param,function(obj){
								jobDg.datagrid("reload");
						});
					}
				});
			}
			,runOnce:function (){
				var selectRow = jobDg.getSelectRow();
				var jobId = selectRow.id;
				$("#runOnceDlg").openDialog(function(){
					var param = {};
					param.jobId = jobId;
					param.dt = $("#runOnceDt").val();
					param.isSelfRely= $("#runOnceIsSelfRely").combobox("getValue");
					easyUtils.post(path+"/sche/runOnce",param,function(obj){
						$.messager.alert("成功", "操作成功！","info");  
					});
				});
			}
			,seeJobTree:function(){
				var selectRow = jobDg.getSelectRow();
				var jobId = selectRow.id;
				easyUtils.post(path+"/scheRelyJob/getTreeList?",{jobId:jobId},function(obj){
					$('#jobTree').tree({
						data:obj,
					    parentField : 'pid',
					    lines : true,
					    checkbox : false,
					    onClick : function(node) {
					    	$("#jobTree .tree-title").each(function(index,dom){
					    		if(node.text==$(this).html()){
					    			$(this).parent().addClass("tree-node-selected");
					    		}else{
					    			$(this).parent().removeClass("tree-node-selected");
					    		}
					    	});
					    },
					    onLoadSuccess : function(node, data) {
					    	$("#jobTreeDlg").show().dialog({
					    		top:10,
					    		buttons : [
					    			{
					    				text : '关闭',
					    				handler : function() {
					    					$("#jobTreeDlg").dialog("close");
					    				}
					    			}
					    		]
					    	}).dialog("open");
					    },
					    animate:true
					 });
				});
			}
			,searchJob:function (){
				var param = easyuiUtils.getParam("jobForm");
				queryJobDg(param);
			}
			,cleanParam:function(){
				easyuiUtils.clearParam("jobForm");
				jobMvc.Controller.searchJob()
			}
		},
		Service:{
			getAddJobParam:function(){
				var param = easyuiUtils.getParam("addJob");
				return param;
			}
		}
}
$(function(){
	jobMvc.View.init();
	queryJobDg();

    $("#cronSelect").combobox({
    	  valueField: 'id',
          textField: 'name',
          panelHeight:'auto',
          url: path+'/cron/list'
    });
	initRelyJobSelectedDlg();
});
function queryJobDg(param){
	jobDg = $('#jobDg').datagrid({
    	url:path+"/job/getJobInfoList", queryParams:param,
    	 fit : false,  striped : false, pagination : true, singleSelect : true, fitColumns:false,
        idField : 'id',   sortName : 'id',   sortOrder : 'desc',pageSize : 20,
        columns:[[
            {field:'id',title:'编号',width:50,sortable:true,rowspan:2},
            {field:'jobType',title:'作业类型',width:80,rowspan:2},
            {field:'jobName',title:'名称',width:200,rowspan:2},
            {field:'host',title:'主机',width:110,rowspan:2},
            {field:'jobUser',title:'账号',width:70,rowspan:2},
         /*   {field:'passwd',title:'密码',width:70,rowspan:2},*/
            {field:'command',title:'命令',width:200,rowspan:2},
            {field:'scheManager',title:'调度',align:'center',colspan:6},
            {field:'creater',title:'创建人',width:70,rowspan:2},
            {field:'createTime',title:'创建时间',width:120,rowspan:2,formatter : function(value, row, index){
            	return value.substring(0,16);
            }}
        ],
        [    
            {field:'sche',title:'调度依赖',width:130,formatter:function(value,row,index){
            	var scheType = row.scheType;
        		if(scheType=='timeSche'){
        			return row.cron;
        		}else if(scheType=='relyPreJob'){
        			return "上级作业："+row.pid;
        		}else{
        			return "无";
        		}
            }},
            {field:'valid_time',title:'有效时间',width:140,formatter:function(value,row,index){
            	if( row.scheType != null){
            		var beginTime = row.beginTime;
                	var endTime = row.endTime;
                	if(beginTime==null && endTime ==null){
                		return "永远";
                	}else if(beginTime==null ){
                		return  "不限~"+ endTime;
                	}else if(endTime ==null){
                		return  beginTime+"~永远";
                	}else{
                	  	return beginTime +"~"+endTime;
                	}
            	}
            }},
            {field:'try_count',title:'重试次数',width:60},
            {field:'try_interval',title:'重试间隔',width:60},
            {field:'receiver',title:'告警邮箱',width:150},
            {field:'isSelfRely',title:'自依赖',width:50,align:'center'}
            ]
        ],
        onLoadSuccess:function(data){
            $('.linkbutton').linkbutton();
        },
    });
}


function openRelyJobDlg(jobId){
	 $('#relyJobSelectedDg').datagrid("loadData",[]);
	initlikeInputSearchDataGrid();
	$("#likeInputDlg").openDialog(function(){
		var data = 	$('#relyJobSelectedDg').datagrid("getData");
		var value = [];
		var rows = data.rows;
		for(var i in rows){
			value.push(rows[i].id);
		}
		$("#relyPreJobCombobox").textbox("clear");
		$("#relyPreJobCombobox").textbox("setValue",value.join(","));
		$("#likeInputDlg").dialog("close");
	});
	
	$("#likeInputSearchBox").searchbox({
		searcher:function(value,name){
			value = $.trim(value);
			easyUtils.post(path+"/job/getPreJobList",{jobId:jobId,jobInfo:value},function(obj){
					initlikeInputSearchDataGrid(obj);
			});
		}
	});
}
/**
 * 初始化选择结果
 */
function initRelyJobSelectedDlg(){
	 $('#relyJobSelectedDg').datagrid({
            onLoadSuccess:function(){
            	$(".easyui-linkbutton-del").linkbutton({	iconCls:'icon-remove'});
            }
	 });
}
/**
 * 生成搜索结果的grid
 */
function initlikeInputSearchDataGrid(dataList){
	 $('#likeInputSearchDataGrid').datagrid({
	            data:dataList,
	            onLoadSuccess:function(){
	            	$(".easyui-linkbutton-add").linkbutton({	iconCls:'icon-add'});
	            }
	 });
	 $('#likeInputSearchDataGrid').datagrid("selectRow",0);
}
function likeInputDelFormatter(index){
	$('#likeInputDataGrid').datagrid("deleteRow",index);
	$('#likeInputDataGrid').datagrid("refresh");
}


