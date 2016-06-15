package com.hxb.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.AppConstants;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * 
 * @des :微信获取accesstoken，jsapitoken
 * @author hexiaobo
 * @email absod0711@gmail.com
 * @date 2016年4月14日
 */
public class WeixinConfigUtil {
	private static final Logger svcLog = Log4jUtil.svcLog;
	private static final Logger sysLog = Log4jUtil.sysLog;
	private static String access_token = null;
	private static String jsapi_ticket = null;
	private static String card_token = null;
	private static long access_token_time = 0;
	private static long jsapi_token_time = 0;
	private static long card_token_time = 0;
	private static String timetStamp=null;
	private static String nonStr=null;
	private static final String APP_ID = AppConstants.APPID;
	private static final String APP_SECRET = AppConstants.APPSECRET;
	// 获取jsapi_token url
	private static final String GET_JSAPI_TICKET_URL = AppConstants.JS_TOKEN_URL;
	// 获取普通access_token url
	private static final String GET_ACCESS_TOKEN_URL = AppConstants.ACCESS_TOKEN_URL;
	private static JsonParser parser = new JsonParser();

	public static void main(String[] args) throws MyException {
		// 测试
		//getConfig("sipanzi");
		//getCardConfig("p8902t4u-ceu0h9poEcchAsI0JRw");
		getAccessToken();
	}

	// 获取jsapitoken 发送access_token 和type 参数
	//
	// https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi
	public static synchronized String getJsApiTicket(String accessToken) throws MyException {
		System.out.println(
				"current time：" + System.currentTimeMillis() + " " + "jsapitoken expiration time:" + jsapi_token_time);
		if (System.currentTimeMillis() - (6000 * 1000L) < jsapi_token_time) {
			return jsapi_ticket;
		}
		// 获取accesstoken URL
		String get_jsapi_ticket = GET_JSAPI_TICKET_URL.replace("ACCESS_TOKEN", accessToken);
		// 向微信服务器发送请求,请求jsapitoken
		String resultString = HttpClientUtils.getWeixinContent("https://api.weixin.qq.com", get_jsapi_ticket);

		System.out.println("WeixinConfigUtil,getJsApiTicket,resultString:" + resultString);
		// jsapitoken 加密需要使用
		//
		// {"errcode":0,"errmsg":"ok","ticket":"sM4AOVdWfPE4DxkXG","expires_in":7200}
		JsonElement el = parser.parse(resultString);
		// 是否获取到toen
		if (el.getAsJsonObject().get("errcode").getAsInt() != 0) {
			sysLog.warn("current time：" + System.currentTimeMillis() + " " + "jsapitoken expiration time:"
					+ jsapi_token_time);
			sysLog.warn("WeixinConfigUtil,getJsApiTicket,resultString:" + resultString);
			throw new MyException(999, "第三方(weixin)数据错误");
		}
		String temp_jsapi_ticket = el.getAsJsonObject().get("ticket").getAsString();
		if (temp_jsapi_ticket != null && temp_jsapi_ticket.trim().length() != 0) {
			jsapi_token_time = System.currentTimeMillis();
			jsapi_ticket = temp_jsapi_ticket;
		} else {
			sysLog.warn("WeixinConfigUtil,getJsApiTicket,temp_jsapi_ticket:" + temp_jsapi_ticket);
			throw new MyException(999, "第三方(weixinJS)数据错误");
		}
		return jsapi_ticket;
	}

	/**
	 * 获取access_token
	 * 
	 * @return
	 * @throws MyException
	 */
	// 获取普通access_token
	// https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx8153609e12ca6d6a&secret=c42badf908f5df0808e905d25ea161f7
	public static synchronized String getAccessToken() throws MyException {
		System.out.println("current time：" + System.currentTimeMillis() + "  " + "accesstoken expiration time:"
				+ access_token_time);
		if (System.currentTimeMillis() - (6000 * 1000L) < access_token_time) {
			return access_token;
		}
		// String get_access_token = GET_ACCESS_TOKEN_URL +
		// "?grant_type=client_credential&appid=" + APP_ID + "&secret="
		// + APP_SECRET;
		String get_access_token = GET_ACCESS_TOKEN_URL.replace("APPID", APP_ID);
		get_access_token = get_access_token.replace("APPSECRET", APP_SECRET);

		// 向微信服务器发送请求,请求accesstoken
		String resultString = HttpClientUtils.getHtml(get_access_token);
		System.out.println("WeixinConfigUtil,getAccessToken,resultString from weixin:" + resultString);
		JsonElement el = parser.parse(resultString);
		String temp_access_token = el.getAsJsonObject().get("access_token").getAsString();
		// 返回数据：{"access_token":"lUiqNb_17LRCNGAKLZ4EVUBU3ubxcevckSdUC5GaTugHu3BcZzCcfeQ-WExbdQ_j2wMFwV-IpTDEGDAZVLDZemjzmUUJKlP7DRoy4S-QqKvdTewPKELSJSHjNygoPZWKGJDgAJAORL","expires_in":7200}
		if (temp_access_token != null && temp_access_token.trim().length() != 0) {
			access_token_time = System.currentTimeMillis();
			access_token = temp_access_token;
		} else {
			sysLog.warn("current time：" + System.currentTimeMillis() + "  " + "accesstoken expiration time:"
					+ access_token_time);
			sysLog.warn("WeixinConfigUtil,getAccessToken,resultString from weixin:" + resultString);
			throw new MyException(-1, "第三方(weixin)数据错误");
		}
		System.out.println(access_token);
		return access_token;
	}

	/**
	 * 获取api_ticket 卡卷需要用到
	 * 
	 * @return
	 * @throws MyException
	 */
	public static String getCardToken() throws MyException {
		if (System.currentTimeMillis() - (6000 * 1000L) < card_token_time) {
			System.out.println("card_token_time:" + card_token_time);
			return card_token;
		}
		String token = getAccessToken();
		String url = AppConstants.CARD_TOKEN.replace(AppConstants.ACCESS_TOKEN, token);
		String cardToken = HttpClientUtils.getJsonObject(url).getAsJsonObject().get("ticket").getAsString();
		if (cardToken != null && StringUtils.isNotBlank(cardToken)) {
			card_token = cardToken;
			card_token_time = System.currentTimeMillis();
			System.out.println("card_token_time:" + card_token_time);
			return cardToken;
		} else {
			return null;
		}
	}

	public static Map<String, String> getCardConfig(String cardId) throws MyException {
		String timestamp = create_timestamp();
		String nonce_str = create_nonce_str();
		WxCardSign signer1 = new WxCardSign();
		String cardToken = getCardToken();
		signer1.AddData(cardToken);
		signer1.AddData(timestamp);
		signer1.AddData(cardId);
		String ssss = signer1.GetSignature();
		
		
		WxCardSign signer = new WxCardSign();
		//signer.AddData(cardId);
		signer.AddData(cardToken);
		signer.AddData(AppConstants.APPID);
		signer.AddData(nonce_str);
		
		signer.AddData(timestamp);
		String signature = signer.GetSignature();
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("timestamp", timestamp);
		ret.put("signature", signature);
		ret.put("nonceStr", nonStr);
		ret.put("signType", "SHA1");
		ret.put("cardSign", signature);
		ret.put("signature", ssss);
		ret.put("cardId", cardId);
		System.out.println("timestamp:" + timestamp + ",:nonce_str:" + nonce_str + ",cardToken:" + cardToken
				+ ",signature:" + signature+"cardExt:"+ssss);
		return ret;

	}


	// 官方加密方法
	private static Map<String, String> sign(String jsapi_ticket, String url) {
		Map<String, String> ret = new HashMap<String, String>();
		if (url == null) {
			url = "";
		}
		String nonce_str = create_nonce_str();
		nonStr = nonce_str;
		String timestamp = create_timestamp();
		timetStamp =timestamp; 
		String string1;
		String signature = "";
		// 注意这里参数名必须全部小写，且必须有序
		string1 = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonce_str + "&timestamp=" + timestamp + "&url=" + url;
		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(string1.getBytes("UTF-8"));
			signature = byteToHex(crypt.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		ret.put("url", url);
		// ret.put("jsapi_ticket", jsapi_ticket);
		ret.put("nonceStr", nonce_str);
		ret.put("timestamp", timestamp);
		ret.put("signature", signature);
		ret.put("appId", APP_ID);
		return ret;
	}

	/**
	 * 返回config传入url加密需要使用
	 * 
	 * @param referURL
	 * @return
	 * @throws MyException
	 */
	public static Map<String, String> getConfig(String referURL) throws MyException {
		String access_token = getAccessToken();
		String jsapi_ticket = getJsApiTicket(access_token);
		Map<String, String> config = sign(jsapi_ticket, referURL);
		return config;

	}

	private static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}

	private static String create_nonce_str() {
		return UUID.randomUUID().toString();
	}

	private static String create_timestamp() {
		return Long.toString(System.currentTimeMillis() / 1000);
	}

}
