package com.hxb.util;

import com.AppConstants;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class WeixinCardUtils {
	private static Gson gson = new Gson();
	private static JsonParser jsonParser = new JsonParser();

	/*
	 * { "code": "12312313", "card_id":"p1Pj9jr90_SQRaVqYI239Ka1erkI",
	 * "record_bonus": "消费30元，获得3积分", "add_bonus": 3,//可以传入积分增减的差值
	 * "add_balance": -3000,//可以传入余额本次增减的差值 "record_balance":
	 * "购买焦糖玛琪朵一杯，扣除金额30元。", "custom_field_value1": "xxxxx", }
	 */
	public static String updateCardAccountNumber(String cardId, String code, int bonus, String record) throws MyException {
		String token = WeixinConfigUtil.getAccessToken();
		String url = AppConstants.UPDATE_CARD_INFO.replace(AppConstants.ACCESS_TOKEN, token);
		UpdateCard updateCard = new UpdateCard();
		updateCard.setAdd_bonus(bonus);
		updateCard.setRecord_bonus(record);
		updateCard.setCard_id(cardId);
		updateCard.setCode(code);
		String resultString = HttpClientUtils.sendPost("", url, gson.toJson(updateCard));
		JsonObject jsonObject = jsonParser.parse(resultString).getAsJsonObject();
		int resultBonus = jsonObject.get("result_bonus").getAsInt();
		return jsonObject.get("errmsg").getAsString();

		
		
	}
	/*
	 * { "errcode": 0, "errmsg": "ok", "openid": "obLatjjwDolFj******wNqRXw",
	 * "nickname": "*******", "membership_number": "658*****445", "bonus": 995,
	 * "sex": "MALE",
	 * 
	 * { "card_id": "pbLatjtZ7v1BG_ZnTjbW85GYc_E8", "code": "916679873278" } }
	 */

	public static String getAccountNumber(String cardId, String code) throws MyException {
		String token = WeixinConfigUtil.getAccessToken();
		String url = AppConstants.GET_CARD_INFO.replace(AppConstants.ACCESS_TOKEN, token);
		JsonObject jsonObject = new JsonObject();

		jsonObject.addProperty("card_id", cardId);
		jsonObject.addProperty("code", code);

		String resultString = HttpClientUtils.sendPost("", url, jsonObject.toString());
		JsonObject jsonObjectresult = jsonParser.parse(resultString).getAsJsonObject();

		if (jsonObjectresult.get("errcode").getAsInt() == 0) {
			return jsonObjectresult.get("bonus").getAsString();
		} else {
			return jsonObjectresult.get("errmsg").getAsString();
		}

	}

}

class UpdateCard {
	private String code;
	private String card_id;
	private String record_bonus;
	private int add_bonus;
	private String record_balance;
	private String custom_field_value1;

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

	public String getRecord_bonus() {
		return record_bonus;
	}

	public void setRecord_bonus(String record_bonus) {
		this.record_bonus = record_bonus;
	}

	public int getAdd_bonus() {
		return add_bonus;
	}

	public void setAdd_bonus(int add_bonus) {
		this.add_bonus = add_bonus;
	}

	public String getRecord_balance() {
		return record_balance;
	}

	public void setRecord_balance(String record_balance) {
		this.record_balance = record_balance;
	}

	public String getCustom_field_value1() {
		return custom_field_value1;
	}

	public void setCustom_field_value1(String custom_field_value1) {
		this.custom_field_value1 = custom_field_value1;
	}

}
