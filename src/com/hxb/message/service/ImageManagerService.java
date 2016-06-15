package com.hxb.message.service;

import java.util.List;

import com.hxb.admin.model.MediaModel;

/**
 * 
 * @des :微信上传图片
 * @author hexiaobo
 * @email absod0711@gmail.com
 * @date 2016年5月21日
 */
public interface ImageManagerService {

	/**
	 * 返回所有的微信资源ID
	 * @param begin TODO
	 * @param length TODO
	 * 
	 * @return
	 */
	public List<MediaModel> getMediaList(int begin, int length);

	/**
	 * 存入图片
	 * 
	 * @param url
	 * @return
	 */
	public Integer insertImage(String url);

}
