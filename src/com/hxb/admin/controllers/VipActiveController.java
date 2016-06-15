package com.hxb.admin.controllers;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.AppConstants;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hxb.message.dao.WeixinUserDAO;
import com.hxb.util.HttpClientUtils;
import com.hxb.util.MyException;
import com.hxb.util.WeixinConfigUtil;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;

/**
 * 
 * @des :
 * @author hexiaobo
 * @email absod0711@gmail.com
 * @date 2016年5月26日
 */
@Path("admin/vip")
public class VipActiveController {
	@Autowired
	WeixinUserDAO weixinUserDAO;
	private static String code1 = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=#APPID#&secret=#APPSERT#&code=#CODE#&grant_type=authorization_code";
	private static String redirecturl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=#APPID#&redirect_uri=#domain#&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";

	@Get("active")
	public Object vipAvtive(Invocation inv) {
		String code = inv.getParameter("code");
		// inv.addModel("code", code);
		inv.addModel("openid", inv.getParameter("openid"));
		inv.addModel("cardid", inv.getParameter("card_id"));
		if (StringUtils.isBlank(inv.getParameter("openid")) || StringUtils.isBlank(inv.getParameter("card_id"))) {
			return "@请领取会员卡在激活";
		}
		return "/view/user/vipactive.jsp";

	}

	@Get("redirect")
	public String redirectActive() {
		String url = redirecturl.replace("#APPID#", AppConstants.APPID).replace("#domain#",
				AppConstants.domain + "/biyesheji/admin/vip/active");
		System.out.println("redirectUrl:" + url);
		return "r:" + url;
	}

	@Post("activedata")
	public String createCard(Invocation inv) throws MyException {
//		String code = inv.getParameter("code");
//		String s = inv.getParameter("vipnumber");
//		String url = code.replace("#APPID#", AppConstants.APPID).replace("#APPSERT#", AppConstants.APPSECRET)
//				.replace("#CODE#", code);
//		String openId = HttpClientUtils.getJsonObject(url).getAsJsonObject().get("openid").getAsString();
//		System.out.println("网页授权的openid" + openId);
		String openId = inv.getParameter("openid");
		String cardId = inv.getParameter("cardid");
		String vipnumber = inv.getParameter("vipnumber");
		if(StringUtils.isBlank(vipnumber))
		{
			vipnumber = "";
		}
		if(weixinUserDAO.vipCreate(vipnumber, openId) == 0)
		{
			return "@服务器错误";
		}

		else{
			String code = weixinUserDAO.getCodeEveryVip(openId, cardId);
			ActiveCard ac =new ActiveCard();
			ac.setCard_id(cardId);
			ac.setCode(code);
			ac.setInit_balance(100);
			ac.setInit_bonus(20);
			ac.setMembership_number(code);
			ac.setInit_custom_field_value1("满级");
			System.out.println("激活会员卡：openID"+openId+",:code"+code+",cardid:"+cardId);
			String token = WeixinConfigUtil.getAccessToken();
			String url = AppConstants.ACTIVE_CARD_URL.replace(AppConstants.ACCESS_TOKEN, token);
			String resultString = HttpClientUtils.sendPost("",url,new Gson().toJson(ac));
			JsonObject jsonObject = new JsonParser().parse(resultString).getAsJsonObject();
			
			if(jsonObject.get("errcode").getAsInt() == 0)
			{
				return "/view/admin/jstest.jsp";
			}
			else
			{
				return "@"+jsonObject.get("errmsg").getAsString();
			}
			
		}

	}
	
	



}
/*
 * "init_bonus": 100, "init_balance": 200, "membership_number": "AAA00000001",
 * "code": "12312313", "card_id": "xxxx_card_id", "init_custom_field_value1":
 * "xxxxx" }
 */

class ActiveCard
{
	private int init_bonus;
	private int init_balance;
	private String membership_number;
	private String code;
	private String card_id;
	private String init_custom_field_value1;
	public int getInit_bonus() {
		return init_bonus;
	}
	public void setInit_bonus(int init_bonus) {
		this.init_bonus = init_bonus;
	}
	public int getInit_balance() {
		return init_balance;
	}
	public void setInit_balance(int init_balance) {
		this.init_balance = init_balance;
	}
	public String getMembership_number() {
		return membership_number;
	}
	public void setMembership_number(String membership_number) {
		this.membership_number = membership_number;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCard_id() {
		return card_id;
	}
	public void setCard_id(String card_id) {
		this.card_id = card_id;
	}
	public String getInit_custom_field_value1() {
		return init_custom_field_value1;
	}
	public void setInit_custom_field_value1(String init_custom_field_value1) {
		this.init_custom_field_value1 = init_custom_field_value1;
	}
	
}
