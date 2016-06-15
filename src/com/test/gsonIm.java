package com.test;

import java.util.ArrayList;

import com.AppConstants;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hxb.util.HttpClientUtils;

public class gsonIm {
	public static void main(String[] args) {
		String userInfoUrl = AppConstants.GET_USER_INFO.replace(AppConstants.ACCESS_TOKEN, "RL9K5VKEx8ca6BAb5xi05t8xGvm-TA3ZiqUkZvt-sXYUsJIW1IzcUbwns3pCmhQVmn9ioGz-rthcHfg7Ur1xSyug1uVNkU0ONFRlR9wVS1iNnAj3fAKrY1UkcPsEjMO7IFDhABAXUS").replace(AppConstants.OPENID, "o8902txNRtnok552gQSeGdjztcCw");
		JsonObject jsonObject = HttpClientUtils.getJsonObject(userInfoUrl).getAsJsonObject();
		String nickName = jsonObject.get("nickname").getAsString();
		String imageUrl = jsonObject.get("headimgurl").getAsString();
		System.out.println(nickName + imageUrl);
	}

}
