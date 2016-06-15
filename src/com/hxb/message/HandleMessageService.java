package com.hxb.message;

import java.util.Date;
import java.util.Map;
import java.util.Random;

import org.apache.tomcat.util.net.NioChannel;
import org.eclipse.jdt.core.compiler.CategorizedProblem;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.AppConstants;
import com.google.gson.JsonObject;
import com.hxb.admin.dao.UserCardDAO;
import com.hxb.admin.dao.WxCardDAO;
import com.hxb.message.dao.WeixinUserDAO;
import com.hxb.util.HttpClientUtils;
import com.hxb.util.MyException;
import com.hxb.util.WeixinConfigUtil;
import com.imooc.po.TextMessage;

@Service
public class HandleMessageService {
	@Autowired
	WeixinUserDAO weixinUserDAO;
	@Autowired
	WxCardDAO wxCardDAO;
	@Autowired
	UserCardDAO userCardDAO;

	public String handleEventMessage(Map<String, String> hashMap) throws MyException {
		// (订阅)
		String openId = hashMap.get("FromUserName");
		String toUserName = hashMap.get("ToUserName");
		System.out.println(openId);
		Integer id = null;
		id = weixinUserDAO.isExistUser(openId);
		System.out.println(id);
		if (hashMap.get("Event").equals("subscribe")) {
			if (id == null || id == 0) {
				String token = WeixinConfigUtil.getAccessToken();
				String userInfoUrl = AppConstants.GET_USER_INFO.replace(AppConstants.ACCESS_TOKEN, token)
						.replace(AppConstants.OPENID, openId);
				JsonObject jsonObject = HttpClientUtils.getJsonObject(userInfoUrl).getAsJsonObject();
				String nickName = jsonObject.get("nickname").getAsString();
				String imageUrl = jsonObject.get("headimgurl").getAsString();
				weixinUserDAO.insertNewUser(openId, nickName, imageUrl);
				System.out.println("================");
				TextMessage textMessage = new TextMessage();
				textMessage.setCreateTime(new Date().getTime());
				// textMessage.setContent(AppConstants.domain +
				// "/biyesheji/view/admin/jstest.jsp");
				textMessage.setContent("欢迎欢迎(●'◡'●)右下角有会员卡可领");
				textMessage.setFromUserName(toUserName);
				textMessage.setToUserName(openId);
				textMessage.setMsgType("text");
				return MessageUtil.textMessageToXml(textMessage);
			} else {
				weixinUserDAO.againUser(id);
				TextMessage textMessage = new TextMessage();
				textMessage.setCreateTime(new Date().getTime());
				// textMessage.setContent(AppConstants.domain +
				// "/biyesheji/view/admin/jstest.jsp");
				textMessage.setContent("欢迎欢迎(●'◡'●)右下角有会员卡可领");
				textMessage.setFromUserName(toUserName);
				textMessage.setToUserName(openId);
				textMessage.setMsgType("text");
				return MessageUtil.textMessageToXml(textMessage);
			}
		}
		// 取消订阅
		else if (hashMap.get("Event").equals("unsubscribe")) {
			weixinUserDAO.deleteUser(id);
			return "";
		} // 领取卡卷
		else if (hashMap.get("Event").equals("user_get_card")) {
			String cardId = hashMap.get("CardId");
			String token = WeixinConfigUtil.getAccessToken();
			String userInfoUrl = AppConstants.GET_USER_INFO.replace(AppConstants.ACCESS_TOKEN, token)
					.replace(AppConstants.OPENID, openId);
			JsonObject jsonObject = HttpClientUtils.getJsonObject(userInfoUrl).getAsJsonObject();
			String nickName = jsonObject.get("nickname").getAsString();
			String imageUrl = jsonObject.get("headimgurl").getAsString();
			// String image = jsonObject.get("nickname").getAsString();
			String code = hashMap.get("UserCardCode");
			System.out.println("handleEventMessage,cardID:" + cardId + ",openId:" + openId + ",code:" + code);
			String cardType = wxCardDAO.isVipCard(cardId);
			if (cardType.equals("MEMBER_CARD")) {
				weixinUserDAO.updateVipScard(cardId, code, openId, nickName, imageUrl);
			} else {
				userCardDAO.usetGeNnormolCard(cardId, code, openId);
			}
			
			return "";
		} else if (hashMap.get("Event").equals("CLICK")) {
			if (hashMap.get("EventKey").equals("haha")) {
				TextMessage textMessage = new TextMessage();
				textMessage.setCreateTime(new Date().getTime());
				textMessage.setContent(getJoke1());
				// textMessage.setContent("给你一个微笑(●'◡'●)");
				textMessage.setFromUserName(toUserName);
				textMessage.setToUserName(openId);
				textMessage.setMsgType("text");
				return MessageUtil.textMessageToXml(textMessage);
			} else if (hashMap.get("EventKey").equals("buy")) {
				TextMessage textMessage = new TextMessage();
				textMessage.setCreateTime(new Date().getTime());
				textMessage.setContent(getBuyWhat());
				// textMessage.setContent("给你一个微笑(●'◡'●)");
				textMessage.setFromUserName(toUserName);
				textMessage.setToUserName(openId);
				textMessage.setMsgType("text");
				return MessageUtil.textMessageToXml(textMessage);
			}
		}
		return "";

	}

	public String handleGeneralMessage(Map<String, String> hashMap) {
		String openID = hashMap.get("FromUserName");
		String toUserName = hashMap.get("ToUserName");
		String magID = hashMap.get("MsgId");
		System.out.println("woshuruijsdf:" + hashMap.get("Content"));
		TextMessage textMessage = new TextMessage();
		// text消息
		if (hashMap.get("MsgType").equals("text")) {

			textMessage.setCreateTime(new Date().getTime());
			// textMessage.setContent(AppConstants.domain +
			// "/biyesheji/view/admin/jstest.jsp");
			textMessage.setContent("给你一个微笑(●'◡'●)");
			textMessage.setFromUserName(toUserName);
			textMessage.setToUserName(openID);
			textMessage.setMsgType("text");
		}
		// System.out.println(MessageUtil.textMessageToXml(textMessage));

		// StringBuffer str = new StringBuffer();
		// str.append("<xml>");
		// str.append("<ToUserName><![CDATA[" + openID + "]]></ToUserName>");
		// str.append("<FromUserName><![CDATA[" + AppConstants.APPID +
		// "]]></FromUserName>");
		// str.append("<CreateTime>" + new Date().getTime() + "</CreateTime>");
		// str.append("<MsgType><![CDATA[" + "text" + "]]></MsgType>");
		// str.append("<Content><![CDATA[sdfsdfsfsdfsdf]]></Content>");
		// str.append("</xml>");
		return MessageUtil.textMessageToXml(textMessage);
		// return str.toString();

	}

	/**
	 * 糗事百科的笑话
	 * 
	 * @return
	 */
	public static String getJoke() {
		String url = "http://www.qiushibaike.com/textnew/page/#PAGE#/";
		url = url.replace("#PAGE#", String.valueOf(getInt(1, 34)));
		System.out.println("糗事百科URL：" + url);
		String resultString = HttpClientUtils.getHtml(url);
		System.out.println(resultString);
		Document document = Jsoup.parse(resultString);
		Elements elements = document.getElementsByClass("content-text");
		System.out.println(elements.get(getInt(0, 19)).text());
		return elements.get(getInt(0, 19)).text();

	}

	/**
	 * 捧腹网的笑话
	 * 
	 * @return
	 */
	public static String getJoke1() {
		String url = "http://www.pengfu.com/xiaohua_#PAGE#.html";
		url = url.replace("#PAGE#", String.valueOf(getInt(1, 19000)));
		System.out.println("捧腹网URL：" + url);
		String resultString = HttpClientUtils.getHtml(url);
		// System.out.println(resultString);
		Document document = Jsoup.parse(resultString);
		Elements elements = document.getElementsByClass("humordatacontent");
		System.out.println(elements.get(getInt(0, 20)).text().replace("捧腹网", ""));
		return elements.get(getInt(0, 19)).text().replace("捧腹网", "");

	}
	
	public static String getJoke2()
	{
		String url = "http://m.pengfu.com/xiaohua_#PAGE#.html";
		url = url.replace("#PAGE#", String.valueOf(getInt(1, 19000)));
		System.out.println("捧腹网URL：" + url);
		String resultString = HttpClientUtils.getHtml(url);
		// System.out.println(resultString);
		Document document = Jsoup.parse(resultString);
		Elements elements = document.getElementsByClass("content-text");
		System.out.println(elements.get(getInt(0, 20)).text().replace("捧腹网", ""));
		return elements.get(getInt(0, 19)).text().replace("捧腹网", "");
		
	}

	public static String getBuyWhat() {
		return "http://7te7t9.com2.z0.glb.qiniucdn.com/002/04/67/37.jpg?v=1";

	}

	public static int getInt(int min, int max) {
		Random random = new Random();
		int s = random.nextInt(max) % (max - min + 1) + min;
		return s;
	}

	public static void main(String[] args) {
		getJoke1();
	}

}
