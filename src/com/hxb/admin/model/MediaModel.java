package com.hxb.admin.model;

import java.util.Date;

/**
 * 
 * @des    : 微信资源model
 * @author hexiaobo 
 * @email  absod0711@gmail.com
 * @date   2016年5月17日
 */
public class MediaModel {
	private int id;
	private int mediaType;
	private String mediaId;
	private String mediaUrl;
	private Date createTime;
	private Date updateTime;
	private int isValid;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMediaType() {
		return mediaType;
	}
	public void setMediaType(int mediaType) {
		this.mediaType = mediaType;
	}
	public String getMediaId() {
		return mediaId;
	}
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	public String getMediaUrl() {
		return mediaUrl;
	}
	public void setMediaUrl(String mediaUrl) {
		this.mediaUrl = mediaUrl;
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
	public int getIsValid() {
		return isValid;
	}
	public void setIsValid(int isValid) {
		this.isValid = isValid;
	}
	
	

}
