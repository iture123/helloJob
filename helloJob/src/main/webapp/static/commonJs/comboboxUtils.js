function comboboxUtils(){
	
}
/**
 * 添加选择全部
 * **/
comboboxUtils.addAllItem=function(itemList){
	outList.shift({id:'',text:'全部'});
	outList.push(itemList);
	return outList;
}