package com.hxb.admin.dao;

import java.util.List;

import com.hxb.admin.model.ActiveSecondModel;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

@DAO
public interface ActiveSecondDAO {

	@SQL("INSERT INTO wx_active_list_info (ActiveTypeID,ActiveDes,ActiveCardID,ActiveTitle,CreateTime,UpdateTime) VALUES (:1.activeTypeId,:1.activeDes,:1.activeCardId,:1.activeTitle,NOW(),NOW())")
	public int addActiveSecond(ActiveSecondModel activeSecondModel);

	@SQL("SELECT COUNT(1) FROM wx_active_list_info ")
	public int getActiveSecondCount();

	@SQL("SELECT l.ID,l.ActiveTypeID,l.ActiveCardID,l.ActiveTitle,l.ActiveDes,l.CreateTime,l.UpdateTime,c.CardName,a.ActiveType FROM wx_active_list_info l INNER JOIN wx_card c ON   c.ID=l.ActiveCardID INNER JOIN wx_active_list a ON a.ID=l.ActiveTypeID  ORDER BY ID DESC LIMIT :1,:2")
	public List<ActiveSecondModel> getActiveSecondList(int beginIndex, int length);


	@SQL("SELECT l.ID,l.ActiveTypeID,l.ActiveCardID,l.ActiveTitle,l.ActiveDes,l.CreateTime,l.UpdateTime,c.CardName,a.ActiveType FROM wx_active_list_info l INNER JOIN wx_card c ON   c.ID=l.ActiveCardID INNER JOIN wx_active_list a ON a.ID=l.ActiveTypeID WHERE l.ID=:1")
	public ActiveSecondModel getActiveSecondThroughId(int id);
	
	@SQL("SELECT l.ID,l.ActiveTypeID,l.ActiveCardID,l.ActiveTitle,l.ActiveDes,l.CreateTime,l.UpdateTime,CardID FROM wx_active_list_info l INNER JOIN  wx_card c ON l.activeCardID = c.ID WHERE  l.ActiveTypeID=:1 ORDER BY CreateTime DESC LIMIT 1")
	public List<ActiveSecondModel> getActiveSecondListThroughtypeID(int activeTypeId);

	@SQL("UPDATE wx_active_list_info SET ActiveTypeID =:1.activeTypeId,ActiveCardID=:1.activeCardId,ActiveTitle=:1.activeTitle,ActiveDes=:1.activeDes,UpdateTime=NOW() WHERE ID=:1.id")
	public int updateActiveSecond(ActiveSecondModel activeSecondModel);

	@SQL("DELETE FROM wx_active_list_info WHERE ID=:1")
	public int deleteActiveSecond(int id);

}
