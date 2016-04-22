<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="head.jsp"%>
<style>
body {
	background: #f5f5f5;
}

.leftsss {
	background: #f5f5f5;
	margin-left: 10px;
	margin-right: 10px;
	margin-top: 50px;
}

.ui-state-default {
	background: #efefef
}

.admin-content {
	background: #f5f5f5
}

.dataTables_info {
	color: #0e90d2
}

.searchFrom {
	border: 1;
	bordercolor: #0e90d2;
	margin-right: 10px;
}
</style>
</head>
<body>
	<%@include file="/amaz/menu.jsp"%>
	
		<div class="admin-content">
		<div class="searchFrom am-g">
			<div class="am-input-group am-u-sm-6">
				<span class="am-input-group-label"><i class=""></i>sadfsdf</span> <input
					type="text" class="am-form-field" placeholder="Username">
			</div>

			<div class="am-input-group am-u-lg-6">
				<span class="am-input-group-label"><i
					class="am-icon-lock am-icon-fw"></i></span> <input type="text"
					class="am-form-field" placeholder="Password">
			</div>

			<div class="am-input-group">
				<input type="text" class="am-form-field"> <span
					class="am-input-group-label">.00</span>
			</div>

			<div class="am-input-group">
				<span class="am-input-group-label">$</span> <input type="text"
					class="am-form-field"> <span class="am-input-group-label">.00</span>
			</div>

		</div>
	</div>
	<div>
		<table class="am-table am-table-bordered am-table-hover " id="example">
		</table>
	</div>


</body>

<script src="/biyesheji/view/first.js"></script>
</html>