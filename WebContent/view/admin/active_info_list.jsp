<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<%@include file="/amaz/common/head.jsp"%>
<%-- <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/ueditor/lang/zh-cn/zh-cn.js"></script> --%>
<script type="text/javascript" charset="utf-8"
	src="${pageContext.request.contextPath}/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8"
	src="${pageContext.request.contextPath}/ueditor/ueditor.all.min.js">
	
</script>
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
			<a href="/biyesheji/admin/activetwo/add"><button type="button" class="am-btn am-btn-secondary"
				data-am-modal="{target: '#my-alert'}">新增发卷活动</button></a>
			<!-- 			<button type="button" class="am-btn am-btn-primary"
				data-am-modal="{target: ''}">Alert</button> -->
		</div>
		<div>
			<table class="am-table am-table-bordered am-table-hover "
				id="example">
			</table>
		</div>
	</div>

</body>

<script type="text/javascript">
	var oTable;
	var isClickSubmit = false;
	$(function() {
		$('#doc-form-file').on(
				'change',
				function() {
					var fileNames = '';
					$.each(this.files, function() {
						fileNames += '<span class="am-badge">' + this.name
								+ '</span> ';
					});
					$('#file-list').html(fileNames);
				});
	});
	$(document).ready(function() {

		function showPicModal(showCaller, valueCaller) {
			uploadPicModalCaller = $('#' + showCaller);
			//图片,显示图片上传按钮
			$('#uploadPicModal').modal('show');
			uploadPicModalValueText = $('#' + valueCaller);
		}
		function back() {
			javascript: history.back();
		}

		//绑定查询事件
		$("#search_btn").click(function() {

			oTable.fnSettings()._iDisplayStart = 0;
			oTable.fnDraw();
		});
		//重绘tables
		generate_table();
		//初始化查询

	});

	function generate_table() {
		oTable = $('#example').dataTable({
			"bProcessing" : true, //显示是否加载
			"bJQueryUI" : true,
			"bDestroy" : true,
			"iDisplayLength" : 15,
			//"aaSorting": [[ 2, "desc" ]],//给列表排序 ，第一个参数表示数组 。4 就是css grade那列。第二个参数为 desc或是asc
			"sPaginationType" : "full_numbers", //翻页样式
			"sAjaxSource" : "/biyesheji/admin/activetwo/list",//请求链接
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
				"aTargets" : [ 0, 1, 2, 3, 4, 5 ],
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
				"sTitle" : "主题",
				"mDataProp" : "activeType",
				"sWidth" : "10px",
				"sClass" : "center",
				"sWidth" : "50px",
				"sDefaultContent" : "-"
			}, {
				"sTitle" : "卡卷",
				"mDataProp" : "cardName",
				"sDefaultContent" : "",
				"sWidth" : "10px",
				"sClass" : "center",
				"sWidth" : "50px",
			}, {
				"sTitle" : "活动标题",
				"mDataProp" : "activeTitle",
				"sWidth" : "10px",
				"sClass" : "center",
				"sWidth" : "50px",
				"sDefaultContent" : "-"
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
		});

		function renderButton(data, type, full) {
			var htmlStr = '';
			htmlStr = htmlStr
					+ '<a href="/biyesheji/admin/activetwo/update?id='+full.id+'"><button class="am-btn am-btn-primary" style="margin-top:2px">查看/修改</button></a><br><button class="am-btn am-btn-danger " style="margin-top:2px" onclick=updateIsValid("'
					+ full.id + '")>删除</button> ';
			return htmlStr;
		}
	}
	function updateStatusCallback(data) {
		if (data.code == 0) {
			oTable.fnDraw();
		} else {
			alert(data.data);
		}
	}

	function updateIsValid(data) {
		$.ajax({
			type : 'POST',
			url : '/biyesheji/admin/activetwo/delete',
			data : {
				"id" : data
			},
			success : updateStatusCallback,
			dataType : "json",

		});
	}
	function updateStatusCallback1(data) {
		alert(data.data);
	}
	function showImage(data, type, full) {
		return '<image style="max-width: 300px;"  src="'+full.imageUrl+'" class="am-img-responsive"></image>';
	}

	function updateActive(obj) {
		$("#my-alert1").modal("open");
		var contextObj = $(obj).parents('td');
		var nTr = $(obj).parents('tr')[0];
		var obj1 = oTable.fnGetData(nTr);
		var id = obj1.id;
		var activeType = obj1.activeType;
		var activeDes = obj1.activeDes;
		var imageUrl = obj1.imageUrl;
		$("#id").val(id);
		$("#activeType").val(activeType);
		$("#activeDes").val(activeDes);
		$("#imageUrl").val(imageUrl);
		return;

	}
</script>
</html>