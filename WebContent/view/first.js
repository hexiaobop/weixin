$(function() {
var oTable;
var isClickSubmit = false;

/*$(document).ready(function() {

	$("#search_btn").click(function(){        
		
		oTable.fnSettings()._iDisplayStart = 0;
		oTable.fnDraw();
	});  
	//重绘tables
*/	generate_table(); 



function generate_table(){
	oTable = $('#example').dataTable({
		"bProcessing": true, //显示是否加载
		"bJQueryUI": true,
		"bDestroy":true,
		"iDisplayLength":15,
		//"aaSorting": [[ 2, "desc" ]],//给列表排序 ，第一个参数表示数组 。4 就是css grade那列。第二个参数为 desc或是asc
		"sPaginationType": "full_numbers", //翻页样式
		"sAjaxSource":"/biyesheji/datatest/list",//请求链接
		"sServerMethod": "POST",
		"bAutoWidth": false,
		"bProcessing" : "正在查询...",
		"bSort": false, //开启排序
		"bFilter": false, //过滤功能
		"bServerSide":true,//打开服务器模式，这个是最重要的
		//"sAjaxDataProp":'data', //json数据源 aaData
		"bLengthChange":false, //关闭每页显示多少条数据
		"bStateSave": false,//状态保存，使用了翻页或者改变了每页显示数据数量，会保存在cookie中，下回访问时会显示上一次关闭页面时的内容。
		//"fnServerData":retrieveData,//自定义数据获取函数
		"fnServerParams":function(aoData){//向服务器传额外的参数 
		
		},
		"oLanguage" : {//语言国际化　"sUrl": "@theme/js/jquery.dataTable.cn.txt"
			"sLengthMenu": "每页显示 _MENU_ 条记录",
			"sZeroRecords": "抱歉， 没有找到",
			"sInfo": "从 _START_ 到 _END_ /共 _TOTAL_ 条数据",
			"sInfoEmpty": "没有数据",
			"sProcessing": "正在加载数据...",
			"sInfoFiltered": "",
			"sZeroRecords": "没有检索到数据",
			"sSearch": "",
			"oPaginate": {
			"sFirst": "首页",
			"sPrevious": "前一页",
			"sNext": "后一页",
			"sLast": "尾页"
			}
		},
		"aoColumnDefs" : [{ //展示列
		                    "aTargets" : [0,1],
		                    "bVisible": true,
		                    "bSearchable": false,
		                    "bStorable": false,
		                    "asSorting": [null]
						},{ //默认属性
		                    "aTargets" : ["_all"],
		                    "bVisible": false,
		                    "bSearchable": false,
		                    "bStorable": false ,
		                 

		                }
		            ],
		"aoColumns": [
					//显示列
		          	//ID,admin,qacount,returnProductCount,recordTime,UpdateTime
		          	{"sTitle":"用户名","mDataProp": "name","sClass": "center","sWidth": "50px","sDefaultContent":"-"},
					{"sTitle":"密码","mDataProp": "pass","sClass": "center","sWidth": "50px","sDefaultContent":"-"},
				],
				/* 	"fnDrawCallback": function (data, x) {
						 $(".modify").editable({
						    type: 'text',
						    url: '/forum/interactive/priority',
						    success: function(response, newValue) {
						    	var info=JSON.parse(response);
						        if(info.code != 0) 
						        	return info.data; //msg will be shown in editable form
						        	else oTable.fnDraw();
						        		
						    }
						});
					} */ 
	});
}

});
