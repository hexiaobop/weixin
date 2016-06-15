package com.hxb.message.dao;

import java.util.List;

import com.hxb.admin.model.MediaModel;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

/**
 * 
 * @des :
 * @author hexiaobo
 * @email absod0711@gmail.com
 * @date 2016年5月17日
 */
@DAO
public interface WeixinImageDAO {
	/**
	 * 返回所有的微信资源ID
	 * @param begin TODO
	 * @param length TODO
	 * 
	 * @return
	 */
	@SQL("SELECT ID,MediaID,MediaType,MediaURL,CreateTime,UpdateTime,ISValid FROM media_id LIMIT :1,:2")
	public List<MediaModel> getMediaList(int begin, int length);

	/**
	 * 存入图片
	 * 
	 * @param url
	 * @return
	 */
	@SQL("INSERT INTO media_id (MediaURL,CreateTime,UpdateTime,ISValid) VALUES (:1,NOW(),NOW(),1)")
	public Integer insertImage(String url);

}
