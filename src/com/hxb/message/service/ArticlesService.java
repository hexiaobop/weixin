package com.hxb.message.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.AppConstants;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.hxb.message.ImageAndText;
import com.hxb.message.dao.WeixinUserDAO;
import com.hxb.message.model.SendAllUserImage;
import com.hxb.util.HttpClientUtils;
import com.hxb.util.MyException;
import com.hxb.util.WeixinConfigUtil;

/**
 * @Service
 * @des    :发送所有的
 * @author hexiaobo 
 * @email  absod0711@gmail.com
 * @date   2016年5月15日
 */
public class ArticlesService  {
	@Autowired
	private WeixinUserDAO weixinUserDAO;
	
	public  String creatImageAndText() throws MyException {
		ImageAndText it = new ImageAndText();
		List list = it.getArticles();
		String token = WeixinConfigUtil.getAccessToken();
		String url = AppConstants.UPLOAD_NEWS_URL.replace("ACCESS_TOKEN", token);
		String json = new Gson().toJson(it);
		System.out.println(json);
		String resultString = HttpClientUtils.sendPost("", url, json);		
		System.out.println(resultString);
		JsonParser jsonParser = new JsonParser();
		JsonElement jsonElement = jsonParser.parse(resultString);
		String mediaId = jsonElement.getAsJsonObject().get("media_id").getAsString();		
		return mediaId;
	}
	public void sendAllUser() throws MyException{
		String token = WeixinConfigUtil.getAccessToken();
		String mediaId = creatImageAndText();
		List<String> list = weixinUserDAO.getAllLUser();
		SendAllUserImage sendAllUserImage= new SendAllUserImage();
		sendAllUserImage.setTouser(list);
		Map<String,String> hash  = new HashMap<>();
		hash.put("media_id", mediaId);
		sendAllUserImage.setMpnews(hash);
		sendAllUserImage.setMsgtype("mpnews");
		String url = AppConstants.SEND_NEWS_URL.replace("ACCESS_TOKEN", token);
		String json = new Gson().toJson(sendAllUserImage);
		String resultString = HttpClientUtils.sendPost("", url, json);	
		System.out.println(resultString);
		
	} 
}
