package com.hxb.message.model;

import java.util.List;

/**
 * 
 * @des    :每天所用用户消息基础类
 * @author hexiaobo 
 * @email  absod0711@gmail.com
 * @date   2016年5月8日
 */
public class SendAllUser {
	private List<String> touser;
	private String msgtype;
	public List<String> getTouser() {
		return touser;
	}
	public void setTouser(List<String> touser) {
		this.touser = touser;
	}
	public String getMsgtype() {
		return msgtype;
	}
	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}
	
	
	

}
