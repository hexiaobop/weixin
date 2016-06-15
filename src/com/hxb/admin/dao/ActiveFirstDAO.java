package com.hxb.admin.dao;

import java.util.List;

import com.hxb.admin.model.ActiveFirst;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

/**
 * 表：wx_active_list
 * 
 * @des :
 * @author hexiaobo
 * @email absod0711@gmail.com
 * @date 2016年5月28日
 */
@DAO
public interface ActiveFirstDAO {
	@SQL("INSERT INTO wx_active_list (ImageUrl,ActiveName,ActiveType,ActiveDes,CreateTime,UpdateTime) VALUES (:1.imageUrl,:1.activeName,:1.activeType,:1.activeDes,NOW(),NOW())")
	public int addActiveFirst(ActiveFirst activeFirst);

	@SQL("SELECT ID,ImageUrl,ActiveType,ActiveName,ActiveDes,CreateTime,UpdateTime FROM wx_active_list ORDER BY ID DESC LIMIT :1,:2")
	public List<ActiveFirst> getActiveFirstList(int beginIndex, int length);

	@SQL("SELECT COUNT(1) FROM wx_active_list")
	public int getActiveListCount();

	@SQL("UPDATE wx_active_list SET ImageUrl=:1.imageUrl,ActiveName=:1.activeName,ActiveType=:1.activeType,ActiveDes=:1.activeDes,UpdateTime=NOW() WHERE ID=:1.id LIMIT 1")
	public int updateActiveFirst(ActiveFirst activeFirst);

	@SQL("DELETE FROM wx_active_list WHERE ID = :1 LIMIT 1")
	public int delete(int id);
}
