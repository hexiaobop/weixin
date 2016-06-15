package com.hxb.admin.dao;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

@DAO
public interface UserCardDAO {
	@SQL("INSERT INTO user_card (CardID,Code,OpenID,CreateTime) VALUES (:1,:2,:3,NOW())")
	public int usetGeNnormolCard(String cardId,String code,String openId);

}
