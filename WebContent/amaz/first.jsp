<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
	border-image-source: initial;
	border-image-slice: initial;
	border-image-width: initial;
	border-image-outset: initial;
	border-image-repeat: initial;
	margin: 10px 20px;
	height: 600px;
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
			<div class="am-btn-group">
				<button type="button" class="am-btn am-btn-default">左手</button>

			</div>
			<div class="am-btn-group">
				<button type="button" class="am-btn am-btn-default">左手</button>
				</br>

			</div>
			<div class="am-btn-group">
				<button type="button" class="am-btn am-btn-default">左手</button>

			</div>
			<div class="am-btn-group">
				<button type="button" class="am-btn am-btn-default">左手</button>

			</div>
		</div>
		<div">
			<table class="am-table am-table-bordered am-table-hover "
				id="example">
			</table>
		</div>
	</div>



</body>

<script src="/biyesheji/view/first.js"></script>
</html>