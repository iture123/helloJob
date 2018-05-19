function easyUtils(){
}
/**
 * 日期加减，返回日期字符串格式为yyyyMMdd
 * **/
easyUtils.addDate=function(date,reduce){
	var ms = date.getTime()+reduce*24*60*60*1000;
	return easyUtils.toYyyyymmdd(new Date(ms));
}
easyUtils.toYyyyymmdd=function(date){
	var y = date.getFullYear();
	var m = date.getMonth()+1;
	if(m<10){
		m = "0"+m;
	}
	var d = date.getDate();
	if(d<10){
		d = "0"+d;
	}
	return y+""+m+""+d;
}
easyUtils.getYesterDayStr=function(){
	return easyUtils.addDate(new Date(),-1);
}
easyUtils.post=function(url,param,callback){
	$.messager.progress({title : '请稍等',msg : '正在加载中...'});
	$.post(url,param,function(result){
		$.messager.progress('close');
		if(result.success){
			callback(result.obj,result.msg);
		}else{
			$.messager.alert("错误", result.msg,"error");  
		}
	},"json");
}