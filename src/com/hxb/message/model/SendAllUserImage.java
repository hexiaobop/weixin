package com.hxb.message.model;

import java.util.Map;
/**
 * 
 * @des    :给所有用户发图文消息
 * @author hexiaobo 
 * @email  absod0711@gmail.com
 * @date   2016年5月8日
 */
public class SendAllUserImage extends SendAllUser{
	private Map<String,String> mpnews;

	public Map<String, String> getMpnews() {
		return mpnews;
	}

	public void setMpnews(Map<String, String> mpnews) {
		this.mpnews = mpnews;
	}


	

}
