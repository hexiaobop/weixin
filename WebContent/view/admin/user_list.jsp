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

.am-g am-datatable-hd {
	margin-top: 110px;
}
}
</style>
</head>
<body>
	<%@include file="/amaz/menu.jsp"%>

	<div class="admin-content">




		<div class="searchFrom " id="search">
			<div class="am-g">

				<form class="am-form-inline am-u-sm-8" role="form">
					<div class="am-form-group">
						<input type="text" class="am-form-field" id="username"
							placeholder="用户名">
					</div>

					<div class="am-form-group">
						<input type="text" class="am-form-field" id="code"
							placeholder="微信会员卡号">
					</div>
					<div class="am-form-group">
						<input type="text" class="am-form-field" id="vipnumber"
							placeholder="线下账号">
					</div>
				</form>
				<button type="button" class="am-btn am-btn-secondary am-u-sm-3"
					id="search_btn">查询</button>

			</div>
		</div>
		<div>
			<table class="am-table am-table-bordered am-table-hover "
				id="example">
			</table>
		</div>
	</div>


	<div class="am-modal am-modal-no-btn" tabindex="-1" id="my-modal">
		<div class="am-modal-dialog">
			<div class="am-modal-hd">
				积分 <a href="javascript: void(0)" class="am-close am-close-spin"
					data-am-modal-close>&times;</a>
			</div>
			<div class="am-modal-bd" id="accountnumber"></div>
		</div>
	</div>

	<div class="am-modal am-modal-no-btn" tabindex="-1" id="my-modal1">
		<div class="am-modal-dialog">
			<div class="am-modal-hd">
				操作积分 <a href="javascript: void(0)" class="am-close am-close-spin"
					data-am-modal-close>&times;</a>
			</div>
			<div class="am-modal-bd">
				<div class="am-g">
					<div class="am-u-md-8 am-u-sm-centered">
						<div class="am-form">
							<fieldset class="am-form-set">
								<input type="text" placeholder="积分修改理由" id="reson"
									required="required"> <input type="number"
									placeholder="积分" id="number">
							</fieldset>
							<button id="updatenumber"
								class="am-btn am-btn-primary am-btn-block" required="required">修改积分</button>
						</div>
					</div>
				</div>
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
			"sAjaxSource" : "/biyesheji/admin/user/list",//请求链接
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
				aoData.push({
					"name" : "username",
					"value" : $('#username').val()
				});
				aoData.push({
					"name" : "code",
					"value" : $('#code').val()
				});
				aoData.push({
					"name" : "vipnumber",
					"value" : $('#vipnumber').val()
				});
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
				"aTargets" : [ 0, 1, 2, 3, 4, 5, 6, 7,8 ],
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
				"sTitle" : "cardType",
				"mDataProp" : "cardType",
				"sDefaultContent" : "",
				"sWidth" : "10px",
				"sClass" : "center",
				"sWidth" : "50px",
				"sDefaultContent" : "-"
			}, {
				"sTitle" : "openID",
				"mDataProp" : "openId",
				"sDefaultContent" : "",
				"sWidth" : "10px",
				"sClass" : "center",
				"sWidth" : "50px",
				"sDefaultContent" : "-"
			}, {
				"sTitle" : "用户名",
				"mDataProp" : "nickName",
				"sDefaultContent" : "",
				"sWidth" : "10px",
				"sClass" : "center",
				"sWidth" : "50px",
				"sDefaultContent" : "-"
			}, {
				"sTitle" : "头像",
				"mRender" : showHeadImage,
				"sClass" : "center",
				"sWidth" : "50px",
				"sDefaultContent" : "-"
			}, {
				"sTitle" : "微信卡号",
				"mDataProp" : "code",
				"sDefaultContent" : "",
				"sWidth" : "10px",
				"sClass" : "center",
				"sWidth" : "50px",
				"sDefaultContent" : "-"
			}, {
				"sTitle" : "是否激活",
				"mDataProp" : "vip",
				"sDefaultContent" : "",
				"sWidth" : "10px",
				"sClass" : "center",
				"sWidth" : "50px",
				"sDefaultContent" : "-"
			}, {
				"sTitle" : "线下vip卡号",
				"mDataProp" : "vipNumber",
				"sDefaultContent" : "",
				"sWidth" : "10px",
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

		$("#sure").click(function() {
			alert($("#cardData").val());
			$.ajax({
				type : 'POST',
				url : '/biyesheji/admin/card/create',
				data : {
					"data" : $("#cardData").val()
				},
				success : updateStatusCallback,
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

	}
	function renderButton(data, type, full) {
		return '<button class="am-btn am-btn-primary" style="margin-top:2px" onclick="getAccountNumber(this);">查看积分</button><br><button class="am-btn am-btn-primary" style="margin-top:2px"'
				+ 'onclick="updateAccountNumber(this);">加减积分</button>';
	}
	function showHeadImage(data, type, full) {
		var htmlStr = '<a href='+full.imageUrl+'><img src="'+full.imageUrl+'" style="max-width:100px;"/></a>'
		return htmlStr;
	}
	function getAccountNumber(obj) {
		var contextObj = $(obj).parents('td');
		var nTr = $(obj).parents('tr')[0];
		var obj1 = oTable.fnGetData(nTr);

		var cradid = obj1.cardId;
		var code = obj1.code;
		$.ajax({
			type : 'POST',
			url : '/biyesheji/admin/card/accountnumber',
			data : {
				"card" : cradid,
				"code" : code
			},
			success : function(data) {
				// $(obj).popover.popover('setContent', data.data)
				$(obj).removeAttr("disabled");
				$("#accountnumber").html(data.data);

				$('#my-modal').modal('toggle');
			},
			dataType : "json",

		});
		$(obj).attr("disabled", true);
	}

	function updateAccountNumber(obj) {

		$("#my-modal1").modal('toggle');
		$('#updatenumber').unbind("click");
		$("#updatenumber").click(function() {
			var contextObj = $(obj).parents('td');
			var nTr = $(obj).parents('tr')[0];
			var obj1 = oTable.fnGetData(nTr);

			var cradid = obj1.cardId;
			var code = obj1.code;
			var number = $("#number").val();
			var reson = $("#reson").val();
			if (number.length == 0 || reson.length == 0) {
				alert("没填数据");
				return;
			}
			$.ajax({
				type : 'POST',
				url : '/biyesheji/admin/card/updateaccountnumber',
				data : {
					"card" : cradid,
					"code" : code,
					"record" : reson,
					"bonus" : number,
				},
				success : function(data) {
					// $(obj).popover.popover('setContent', data.data)
					$(obj).removeAttr("disabled");
					$("#accountnumber").html(data.data);

					$('#my-modal').modal('toggle');
				},
				dataType : "json",

			});
			$("#my-modal1").modal('close');
			$(obj).attr("disabled", true);

		})

	}
</script>
</html>