<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name ="viewport" content ="initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no">
<%@include file="/amaz/common/head.jsp"%>
<title>同步积分信息</title>
</head>
<body>

		<form method="post" class="am-form"
			action="/biyesheji/admin/vip/activedata">
			<label for="email">已有线下vip账户（选填）:</label> <input type="text"
				name="vipnumber" id="" value=""> <br> <label
				for="password"></label> <input type="hidden" name="code"
				id="code" value="${code}"> <input type="hidden"
				name="openid" id="code" value="${openid}"> <input
				type="hidden" name="cardid" id="code" value="${cardid}"> <br>

			<br />
			<div class="am-cf">
				<input type="submit" name="" value="激活会员卡"
					class="am-btn am-btn-primary am-btn-sm am-fl">

			</div>
		</form>

</body>
</html>