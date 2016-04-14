package com.hxb.util;

import com.google.gson.Gson;
import com.hxb.util.model.ResponseModel;

/**
 * 
 * @des :对应微信接口返回json数据;
 * @author hexiaobo
 * @email absod0711@gmail.com
 * @date 2016年4月14日
 */
public class WeixinRequestUtil {

	private static Gson gson = new Gson();

	public static String succ() {
		return succ("");
	}

	/***
	 * 默认使用的;
	 * 
	 * @param data
	 * @return
	 */
	public static String succ(Object data) {
		try {
			ResponseModel rm = new ResponseModel();
			rm.setCode(0);
			rm.setData(data);
			return "@" + gson.toJson(rm);
		} catch (Exception exp) {
			exp.printStackTrace();
			Log4jUtil.exception(exp);
			return "@返回错误";
		}

	}

	public static String error(int errno, String errmsg) {
		try {
			ResponseModel model = new ResponseModel();
			model.setCode(errno);
			model.setData(errmsg);
			return "@" + gson.toJson(model);
		} catch (Exception exp) {
			exp.printStackTrace();
			Log4jUtil.exception(exp);
		}
		return "@INTER_INTERNET_BUSY";
	}
}
