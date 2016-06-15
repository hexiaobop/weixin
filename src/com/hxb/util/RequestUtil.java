package com.hxb.util;

import com.google.gson.Gson;
import com.hxb.util.model.ResponseModel;

/**
 * 
 * @des    :返回数据util
 * @author hexiaobo 
 * @email  absod0711@gmail.com
 * @date   2016年3月26日
 */
public class RequestUtil {
	private static Gson gson = new Gson();
	public static String succ(Object data)
	{
		try{
			ResponseModel rm = new ResponseModel();
			rm.setCode(0);
			rm.setData(data);
			return "@"+gson.toJson(rm);
		}
		catch(Exception e)
		{
			return "@返回错误";
		}
		
	}

}
