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



.ui-state-default {
	background: #efefef
}

.admin-content {
	background: #f5f5f5
}

.dataTables_info {
	color: #0e90d2
}


#search {
	border-style: solid; border-width:1px;border-color:#f00;
	margin:10px 20px;
	height:600px;
	padding:300px;
	box-sizing: content-box;
  }
.am-g am-datatable-hd{
	margin-top:10px;
}	
}
</style>
</head>
<body>
	<%@include file="/amaz/menu.jsp"%>

	<div class="admin-content" >
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
		 <div style="margin-top:10px;">
			<table class="am-table am-table-bordered am-table-hover "
				id="example">
			</table>
		</div> 
	</div>



</body>

<script src="/biyesheji/view/first.js"></script>
</html>