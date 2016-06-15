<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%-- <%@include file="/amaz/common/head.jsp"%> --%>
<script src="${pageContext.request.contextPath}/amaz/assets/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/amaz/assets/js/amazeui.min.js"></script>
	 <link rel="stylesheet" href="${pageContext.request.contextPath}/amaz/assets/css/amazeui.min.css"/>
	 <link rel="stylesheet" href="${pageContext.request.contextPath}/amaz/assets/css/admin.css">
	 <link rel="stylesheet" href="${pageContext.request.contextPath}/amaz/common/common.css">
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js">
	
</script>
<script>
	var cardObj = {};
	$(document).ready(function() {

		$('#my-modal-loading').modal('toggle');

		$.ajax({
			type : 'get',
			url : '/biyesheji/wxapi/wxconfig',
			data : '',
			success : newsOperateCallback,
			dataType : "json",
		});

		$.ajax({
			type : 'get',
			url : '/biyesheji/admin/card/vipcard',
			data : '',
			success : function(data) {
				$.ajax({
					type : 'get',
					url : '/biyesheji/wxapi/wxcardconfig',
					data : {
						"cardId" : data.data
					},
					success : function(data) {
						cardObj = data;
					},
					dataType : "json",
				});
			},
			dataType : "json",
		});

	});
	function newsOperateCallback(data) {
		htmlobj = data;
		wx.config({
			debug : false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
			appId : data.appId, // 必填，公众号的唯一标识
			timestamp : data.timestamp, // 必填，生成签名的时间戳
			nonceStr : data.nonceStr, // 必填，生成签名的随机串
			signature : data.signature,// 必填，签名，见附录1
			jsApiList : [ 'checkJsApi', 'onMenuShareTimeline',
					'onMenuShareAppMessage', 'onMenuShareQQ',
					'onMenuShareWeibo', 'hideMenuItems', 'showMenuItems',
					'hideAllNonBaseMenuItem', 'showAllNonBaseMenuItem',
					'translateVoice', 'startRecord', 'stopRecord',
					'playVoice', 'pauseVoice', 'stopVoice',
					'uploadVoice', 'downloadVoice', 'chooseImage',
					'previewImage', 'uploadImage', 'downloadImage',
					'getNetworkType', 'openLocation', 'getLocation',
					'hideOptionMenu', 'showOptionMenu', 'closeWindow',
					'scanQRCode', 'chooseWXPay', 'openProductSpecificView',
					'addCard', 'chooseCard', 'openCard' ]
		// 必填，需要使用的JS接口列表，所有JS接口列表见附录2
		});
	}

 	wx.ready(function() {
		// config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
/*		wx.chooseCard({
			shopId : '', // 门店Id
			cardType : '', // 卡券类型
			cardId : 'p8902tzYxNv5dD6LTTyP9JFdABT0', // 卡券Id
			timestamp : cardObj.timestamp, // 卡券签名时间戳
			nonceStr : cardObj.nonecStr, // 卡券签名随机串
			signType : cardObj.singType, // 签名方式，默认'SHA1'
			cardSign : cardObj.cardSign, // 卡券签名
			success : function(res) {
				var cardList = res.cardList; // 用户选中的卡券列表信息
			}
		}); */

		$('#my-modal-loading').modal('close');
 		

  /*            wx.addCard({
              cardList: [
                {
                  cardId: "p8902t4u-ceu0h9poEcchAsI0JRw",
                 // cardExt: '{"code": "", openid": "",timestamp":'+cardObj.timestamp+',"signature":'+cardObj.signature+'}',
                  cardExt: '{"code": "", openid": "",timestamp":'+cardObj.timestamp+',"signature":'+cardObj.signature+'}',
                }
              ],
              success: function (res) {
                alert('已添加卡券：' + JSON.stringify(res.cardList));
              }
            }); */ 
		 wx.addCard({
			      cardList: [
			        {
			          cardId: cardObj.cardId,
			          cardExt: '{"code": "", "openid": "", "timestamp":"'+cardObj.timestamp+'", "signature":"'+cardObj.signature+'"}'
			        }
			      ],
			      success: function (res) {
			        alert('已添加卡券：');// + JSON.stringify(res.cardList)
			      },
			      cancel: function (res) {
			        alert(JSON.stringify(res))
			      }
			    });

	
/* 	wx.openCard({
	    cardList: [{
	        cardId: 'p8902t4u-ceu0h9poEcchAsI0JRw',
	        code: ''
	    }]// 需要打开的卡券列表
	}); */

	});
</script>
<title>Insert title here</title>
</head>
<body>
	<!-- <button
  type="button"
  class="am-btn am-btn-success"
  data-am-modal="{target: '#my-modal-loading'}">
  Modal Loading
</button> -->
<!-- <button id ="ss">领啊煞风景啊螺丝钉解放山东的</button> -->
 	<div class="am-modal am-modal-loading am-modal-no-btn" tabindex="-1"
		id="my-modal-loading">
		<div class="am-modal-dialog">
			<div class="am-modal-hd">查看会员卡的途中...</div>
			<div class="am-modal-bd">
				<span class="am-icon-spinner am-icon-spin"></span>
			</div>
		</div>
	</div> 

</body>
</html>