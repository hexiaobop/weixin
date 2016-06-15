<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="/amaz/common/head.jsp"%>
<style>
body {
	background: #f5f5f5;
}

.ui-state-default {
	background: #efefef
}

.admin-content {
	padding-top: 20px;
	background: #f5f5f5
}

.dataTables_info {
	color: #0e90d2
}

#search {
	background: #efefef;
	border: 1px solid #efefef;
	border-top-color: rgb(197, 213, 221);
	border-top-style: solid;
	border-top-width: 1px;
	border-right-color: rgb(197, 213, 221);
	border-right-style: solid;
	border-right-width: 1px;
	border-bottom-color: rgb(197, 213, 221);
	border-bottom-style: solid;
	border-bottom-width: 1px;
	border-left-color: rgb(197, 213, 221);
	border-left-style: solid;
	border-left-width: 1px;
	margin: 10px 20px;
	padding: 10px;
	box-sizing: content-box;
	margin-top：100px;
}

.am-g am-datatable-hd {
	margin-top: 110px;
}
}
</style>
</head>
<body>
	<%@include file="/amaz/menu.jsp"%>

	<div class="admin-content">
		<div class="searchFrom" id="search">
			<form action="${pageContext.request.contextPath}/admin/image/upload"
				enctype="multipart/form-data" method="post" style="height: 80px;">
				<!-- 			<button type="button" class="am-btn am-btn-primary">上传</button>

				<input type="file" name="file" class="am-btn am-btn-secondary"></input> -->
				<div class="am-form-group am-form-file">
					<button type="button" class="am-btn am-btn-default am-btn-sm">
						<i class="am-icon-cloud-upload"></i> 选择要上传的文件
					</button>
					<input name="file" type="file" multiple>
				</div>
				<input type="submit" value="提交" style="float: left;">

			</form>
		</div>
		<div style="margin-top: 10px;">
			<table class="am-table am-table-bordered am-table-hover "
				id="example">
			</table>
		</div>

	</div>

</body>
<script type="text/javascript">
	var oTable;
	var isClickSubmit = false;
	var foo = {
		name : "123"
	};
	$(document).ready(function() {

		//绑定查询事件
		$("#search_btn").click(function() {

			oTable.fnSettings()._iDisplayStart = 0;
			oTable.fnDraw();
		});
		//重绘tables
		generate_table();
		//初始化查询
		$("#search_tryId").chosen({
			disable_search_threshold : 10,
			no_results_text : "未找到此选项!",
			width : "60%"
		});

	});

	function generate_table() {
		oTable = $('#example').dataTable({
			"bProcessing" : true, //显示是否加载
			"bJQueryUI" : true,
			"bDestroy" : true,
			"iDisplayLength" : 15,
			//"aaSorting": [[ 2, "desc" ]],//给列表排序 ，第一个参数表示数组 。4 就是css grade那列。第二个参数为 desc或是asc
			"sPaginationType" : "full_numbers", //翻页样式
			"sAjaxSource" : "/biyesheji/admin/image/list",//请求链接
			"sServerMethod" : "POST",
			"bAutoWidth" : false,
			"bProcessing" : "正在查询...",
			"bSort" : false, //开启排序
			"bFilter" : false, //过滤功能
			"bServerSide" : true,//打开服务器模式，这个是最重要的
			//"sAjaxDataProp":'data', //json数据源 aaData
			"bLengthChange" : false, //关闭每页显示多少条数据
			"bStateSave" : false,//状态保存，使用了翻页或者改变了每页显示数据数量，会保存在cookie中，下回访问时会显示上一次关闭页面时的内容。
			//"fnServerData":retrieveData,//自定义数据获取函数
			"fnServerParams" : function(aoData) {//向服务器传额外的参数 

			},
			"oLanguage" : {//语言国际化　"sUrl": "@theme/js/jquery.dataTable.cn.txt"
				"sLengthMenu" : "每页显示 _MENU_ 条记录",
				"sZeroRecords" : "抱歉， 没有找到",
				"sInfo" : "从 _START_ 到 _END_ /共 _TOTAL_ 条数据",
				"sInfoEmpty" : "没有数据",
				"sProcessing" : "正在加载数据...",
				"sInfoFiltered" : "",
				"sZeroRecords" : "没有检索到数据",
				"sSearch" : "",
				"oPaginate" : {
					"sFirst" : "首页",
					"sPrevious" : "前一页",
					"sNext" : "后一页",
					"sLast" : "尾页"
				}
			},
			"aoColumnDefs" : [ { //展示列
				"aTargets" : [ 0, 1, 2, 3 ],
				"bVisible" : true,
				"bSearchable" : false,
				"bStorable" : false,
				"asSorting" : [ null ]
			}, { //默认属性
				"aTargets" : [ "_all" ],
				"bVisible" : false,
				"bSearchable" : false,
				"bStorable" : false,
			} ],
			"aoColumns" : [
			//显示列
			//ID,admin,qacount,returnProductCount,recordTime,UpdateTime
			{
				"sTitle" : "ID",
				"mDataProp" : "id",
				"sClass" : "center",
				"sWidth" : "50px",
				"sDefaultContent" : "-"
			}, {
				"sTitle" : "缩略图",
				"mRender" : showImageWeixin,
				"sDefaultContent" : "",
				"sWidth" : "10px",
				"sClass" : "center",
				"sWidth" : "50px"
			}, {
				"sTitle" : "创建时间",
				"mDataProp" : "createTime",
				"sClass" : "center",
				"sWidth" : "50px",
				"sDefaultContent" : "-"
			}, {
				"sTitle" : "操作",
				"mRender" : renderButton,
				"sClass" : "center",
				"sWidth" : "50px",
				"sDefaultContent" : "-"
			}, ],
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
		function renderButton(data, type, full) {
			var htmlStr = '';
			if (full.isValid == 0) {
				htmlStr = htmlStr
						+ '<button class="am-btn am-btn-primary" 			   style="margin-top:2px" onclick="updateIsValid(this,\''
						+ full.id + '\',1);">恢复</button> ';
			} else if (full.isValid == 1) {
				htmlStr = htmlStr
						+ '<button class="am-btn am-btn-danger" style="margin-top:2px" onclick="updateIsValid(this,\''
						+ full.id + '\',0);">屏蔽</button>   ';
			}
			return htmlStr;
		}

		function showImg(frameid2, url) {

			var frameid = frameid2 + Math.random();
			var frameid1 = "image" + (frameid2.toString());
			console.debug(frameid);

			console.debug(url);

			window[frameid1] = '<img id='
					+ frameid1
					+ ' style="width:50%" src=\''
					+ url
					+ '?'
					+ Math.random()
					+ '\' /><script>window.onload = function() { parent.document.getElementById(\''
					+ frameid + '\').height = document.getElementById(\''
					+ frameid1 + '\').height+\'px\'; }<' + '/script>';

			//return '<iframe id="' + frameid + '" src="javascript:parent.'+frameid1+';" frameBorder="0" scrolling="no" width="50%"></iframe>';
			return '<iframe id="' + frameid + '" src="javascript:parent.'
					+ frameid1
					+ ';" frameBorder="0" scrolling="no" width="50%"></iframe>';

		}
		function showImageWeixin(data, type, full) {

			//var htmlStr = '<a href='+full.mediaUrl+'><img src="'+full.mediaUrl+'" style="max-width:100px;"/></a>'
			//var htmlStr = "<div><script type="+"text/javascript>showImg('"+full.mediaUrl+"'\)<\/script></div>";
			console.log("-----------------------" + full.mediaUrl);
			return showImg(full.id, full.mediaUrl);

		}
		function ss() {

		}
	}
</script>
</html>