//本文件收集常用的函数操作.

function $(agr){
	//id选择器正则
	var id_selecter = /^#\w+/;
	//class选择器正则
	var class_selecter = /^\.\w+/;
	//name选择器正则
	var name_selecter = /^name=\w+/;
	//tagname选择器正则
	var tag_selecter = /\w+/;
	if(id_selecter.test(agr))
		return document.getElementById(agr.substring(1));
	else if(class_selecter.test(agr))
		return document.getElementsByClassName(agr.substring(1));
	else if(name_selecter.test(agr))
		return document.getElementsByName(agr.substring(5));
	else if( tag_selecter.test(agr))
		return document.getElementsByTagName(agr);
	
}
function ajax(url,callback,str){
	var request = new XMLHttpRequest;
	if(str){
		request.open("POST",url,true);
		request.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	}
	else
		request.open("GET",url,true);
	request.send(str);
	request.onreadystatechange = function(){
		if(request.readyState == 4){
			if(request.status==200)
				callback(request.responseText);
			else
				callback("{\"error\":"+request.status+"}");
		}		
	}
}
