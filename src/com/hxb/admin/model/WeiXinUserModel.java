package com.hxb.admin.model;

/**
 * 
 * @des : 微信用户信息
 * @author hexiaobo
 * @email absod0711@gmail.com
 * @date 2016年5月27日
 */

public class WeiXinUserModel {
	private int id;
	private String nickName;
	private int accountNumber;
	private String code;
	private String cardId;
	private String openId;
	private String imageUrl;
	private String vipNumber;
	private int vip;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}



	public int getAccountNumber() {
		return accountNumber;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getVipNumber() {
		return vipNumber;
	}

	public void setVipNumber(String vipNumber) {
		this.vipNumber = vipNumber;
	}

	public int getVip() {
		return vip;
	}

	public void setVip(int vip) {
		this.vip = vip;
	}

}
