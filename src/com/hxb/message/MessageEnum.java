package com.hxb.message;
/**
 * 
 * @des    :接收到的消息类型
 * @author hexiaobo 
 * @email  absod0711@gmail.com
 * @date   2016年5月7日
 */
public enum MessageEnum {
	TEXT("text"),EVENT("event");
	private String s;
	
	private MessageEnum(String s) {
		this.s = s;
	}

	public String getS() {
		return s;
	}

}
