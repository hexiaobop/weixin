package com.hxb.admin.dao;

import java.util.List;

import com.hxb.admin.model.WxCardModel;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

@DAO
public interface WxCardDAO {
	@SQL("SELECT ID,CardType,CreateTime,UpdateTime,CardID,CardData,CardName FROM wx_card WHERE ISValid =1 LIMIT :1,:2")
	public List<WxCardModel> getCardList(int begin, int length);

	@SQL("SELECT COUNT(1) FROM wx_card")
	public int getCardNum();

	@SQL("INSERT INTO wx_card (CardType,CreateTime,UpdateTime,CardID,ISValid,CardData,CardName) VALUES (:1,NOW(),NOW(),:2,1,:3,:4)")
	public int createCard(String cardType, String cardId,String cardData,String cardName);
	
	@SQL("SELECT CardID FROM wx_card WHERE ISValid=1 AND CardType='MEMBER_CARD' ORDER BY CreateTime DESC LIMIT 1")
	public String getVipCard();
	
	@SQL("SELECT CardType FROM wx_card WHERE CardID =:1 LIMIT 1")
	public String isVipCard(String cardID);
	
	@SQL("DELETE FROM wx_card WHERE ID=:1 LIMIT 1")
	public int deleteCard(int id);
	
	@SQL("SELECT cardID FROM wx_card WHERE CardName LIKE '%抢%' ORDER BY CreateTime DESC LIMIT 1")
	public String getCarzyCard();
	
	

}
