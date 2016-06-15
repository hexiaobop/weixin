<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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

/* .am-g am-datatable-hd {
	margin-top: 110px;
} */
}
</style>
</head>
<body>
	<%@include file="/amaz/menu.jsp"%>

	<div class="admin-content">


		<div class="searchFrom" id="search">
			<button type="button" class="am-btn am-btn-secondary"
				data-am-modal="{target: '#my-prompt'}">新增卡卷</button>

			<button type="button" class="am-btn am-btn-secondary"
				data-am-modal="{target: '#my-prompt1'}">领卷白名单</button>
			<div class="am-modal am-modal-prompt" id="my-prompt">

				<div class="am-modal-dialog" style="width: 800px">
					<div class="am-modal-hd">新增卡卷</div>
					<!-- 					<div class="am-form-group">
						<label for="doc-select-1">卡卷类型</label> <select id="doc-select-1">
							<option value="option1">会员卡</option>
							<option value="option2">优惠卷</option>
						</select> <span class="am-form-caret"></span>
					</div> -->
					<div class="am-form-group">
					<label for="" class="am-u-sm-2 am-form-label">卡卷用途</label>
					<div class="am-u-sm-10">
						<input type="text" id="cardName" name="cardName"
							placeholder="描述卡卷用于什么活动">
					</div>
				</div>
				<br>
					<div class="am-form-group">
						<label for="doc-ta-1">文本域</label>
						<textarea class="" rows="30" style="width: 500px" id="cardData"></textarea>
					</div>

					<div class="am-modal-footer">
						<span class="am-modal-btn" data-am-modal-cancel>取消</span> <span
							class="am-modal-btn" data-am-modal-confirm id="sure">提交</span>
					</div>
				</div>

			</div>


		</div>
		<div>
			<table
				class="am-table am-table-striped am-table-bordered am-table-compact dataTables_wrapper no-footer"
				id="example">
			</table>

		</div>

	</div>


	<div class="am-modal am-modal-prompt1" id="my-prompt1">

		<div class="am-modal-dialog" style="width: 800px">
			<div class="am-modal-hd">测试白名单</div>
			<div class="am-form-group">
				<label for="doc-ta-1">文本域</label>
				<textarea class="" rows="30" style="width: 500px" id="whitelist">{
  "openid": [
      "o1Pj9jmZvwSyyyyyyBa4aULW2mA", 
      "o1Pj9jmZvxxxxxxxxxULW2mA"
               ],
  "username": [
      "afdvvf",
      "abcd"
                ]
 }</textarea>
			</div>

			<div class="am-modal-footer">
				<span class="am-modal-btn" data-am-modal-cancel>取消</span> <span
					class="am-modal-btn" data-am-modal-confirm id="sure1">提交</span>
			</div>
		</div>

	</div>

</body>

<script type="text/javascript">
	var oTable;
	var isClickSubmit = false;
	$(document).ready(function() {

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
			"sAjaxSource" : "/biyesheji/admin/card/list",//请求链接
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
				"aTargets" : [ 0, 1, 2, 3, 4, 5,6 ],
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
				"sDefaultContent" : "-"
			}, {
				"sTitle" : "cardType",
				"mDataProp" : "cardType",
				"sClass" : "center",
				"sWidth" : "50px",
				"sDefaultContent" : "-"
			}, {
				"sTitle" : "卡卷用途",
				"mDataProp" : "cardName",
				"sClass" : "center",
				"sDefaultContent" : "-"
			}, {
				"sTitle" : "cardID",
				"mDataProp" : "cardId",
				"sDefaultContent" : "",
				"sWidth" : "50px",
				"sClass" : "center",
			}, {
				"sTitle" : "cardData",
				"mDataProp" : "cardData",
				"sDefaultContent" : "-",
				"sWidth" : "50px",
				"sClass" : "center",
			}, {
				"sTitle" : "创建时间",
				"mDataProp" : "createTime",
				"sClass" : "center",
				"sDefaultContent" : "-"
			}, {
				"sTitle" : "操作",
				"mRender" : renderButton,
				"sClass" : "center",
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
			htmlStr = htmlStr
					+ '<button class="am-btn am-btn-danger " style="margin-top:2px" onclick=updateIsValid("'
					+ full.id + '")>删除</button> ';
			return htmlStr;
		}

		$("#sure").click(function() {
			alert($("#cardData").val());
			$.ajax({
				type : 'POST',
				url : '/biyesheji/admin/card/create',
				data : {
					"data" : $("#cardData").val(),
					"cardname":$("#cardName").val()
				},
				success : updateStatusCallback,
				dataType : "json",

			});
		});

	}
	$("#sure1").click(function() {
		$.ajax({
			type : 'POST',
			url : '/biyesheji/admin/card/whitelist',
			data : {
				"whitelist" : $("#whitelist").val()
			},
			success : updateStatusCallback1,
			dataType : "json",

		});
	});

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
			url : '/biyesheji/admin/card/delete',
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
</script>
</html>