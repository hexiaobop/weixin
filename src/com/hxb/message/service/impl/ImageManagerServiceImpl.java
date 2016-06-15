package com.hxb.message.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hxb.admin.model.MediaModel;
import com.hxb.message.dao.WeixinImageDAO;
import com.hxb.message.service.ImageManagerService;

@Service
public class ImageManagerServiceImpl implements ImageManagerService {
	@Autowired
	WeixinImageDAO weixinImageDAO;

	/**
	 * 返回所有的微信资源ID
	 * 
	 * @return
	 */
	public List<MediaModel> getMediaList(int begin, int length) {
		return weixinImageDAO.getMediaList(begin, length);
	}

	/**
	 * 存入图片
	 * 
	 * @param url
	 * @return
	 */
	public Integer insertImage(String url) {
		return weixinImageDAO.insertImage(url);
	}

}
