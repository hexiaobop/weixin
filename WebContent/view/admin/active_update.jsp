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
		<form class="am-form" data-am-validator method="POST"
			action="/biyesheji/admin/activetwo/update">
			<fieldset>
				<legend>修改活动</legend>
				<input type="hidden"  name="id" value="${activeInfo.id}"/>
				<div class="am-form-group">
					<label for="" class="am-u-sm-1">主题：</label> <select
						data-am-selected="{btnWidth: '40%', btnSize: 'sm', btnStyle: 'thirdsary'}"
						name="activeTypeId">
						<option value=" "></option>

						<option value="${activeInfo.activeTypeId}" selected="selected">${activeInfo.activeType}</option>

					</select>
				</div>

				<div class="am-form-group">
					<label for="" class="am-u-sm-1">卡卷：</label> <select
						data-am-selected="{btnWidth: '40%', btnSize: 'sm', btnStyle: 'secondary'}"
						name="activeCardId">
						<option value=" "></option>

						<option value="${activeInfo.activeCardId}" selected="selected">${activeInfo.cardName}</option>

					</select>
				</div>
				<div class="am-form-group">
					<label class="am-u-sm-1 am-form-label">标题：</label>
					<div class="am-u-sm-6">
						<input type="text" placeholder="文章title" required
							name="activeTitle" value="${activeInfo.activeTitle}" />
					</div>
					<div class="am-u-sm-5"></div>
				</div>

			</fieldset>
			<div class="am-form-group">
				<label for="doc-vld-pwd-1" class="am-u-sm-1 am-form-label">详情：</label>
				<div class="am-u-sm-11">
					<div style="margin-left: 10px;">
						<div style="width: 800px">
							<script id="ueditor">
								
							</script>

						</div>
					</div>
				</div>
			</div>
			<br> <br>
			<div class="am-form-group">
				<label class="am-u-sm-6 am-form-label"></label>
				<div class="am-u-sm-6">
					<button class="am-btn am-btn-secondary" id="submit">提交</button>

				</div>
			</div>
		</form>
		<br> <br> <br>
	</div>
</body>

<script type="text/javascript">
	var oTable;
	var isClickSubmit = false;
	var ue = UE.getEditor('ueditor', {
		initialFrameHeight : 800,
		initialFrameWidth : 800,
		initialContent : '${activeInfo.activeDes}'

	});
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
</script>
</html>