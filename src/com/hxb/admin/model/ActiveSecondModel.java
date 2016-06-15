package com.hxb.admin.model;

import java.util.Date;

public class ActiveSecondModel {

	private int id;
	private int activeTypeId;
	private int activeCardId;
	private String activeTitle;
	private String activeDes;
	private String cardName;
	private String activeType;
	private String cardId;
	private Date createTime;
	
	private Date updateTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getActiveTypeId() {
		return activeTypeId;
	}

	public void setActiveTypeId(int activeTypeId) {
		this.activeTypeId = activeTypeId;
	}

	public int getActiveCardId() {
		return activeCardId;
	}

	public void setActiveCardId(int activeCardId) {
		this.activeCardId = activeCardId;
	}

	public String getActiveTitle() {
		return activeTitle;
	}

	public void setActiveTitle(String activeTitle) {
		this.activeTitle = activeTitle;
	}

	public String getActiveDes() {
		return activeDes;
	}

	public void setActiveDes(String activeDes) {
		this.activeDes = activeDes;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public String getActiveType() {
		return activeType;
	}

	public void setActiveType(String activeType) {
		this.activeType = activeType;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	

}
