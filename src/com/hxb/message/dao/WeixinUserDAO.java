package com.hxb.message.dao;

import java.util.List;

import com.hxb.admin.model.WeiXinUserModel;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.annotation.SQLParam;


/**
 * 
 * @des : 微信事件需要的数据库支持
 * @author hexiaobo
 * @email absod0711@gmail.com
 * @date 2016年5月7日
 */
@DAO
public interface WeixinUserDAO {
	/**
	 * 关注事件
	 * 
	 * @param openID
	 * @return
	 */
	@SQL("INSERT INTO user (OpenID,CreateTime,UpdateTime,ISValid,NickName,ImageUrl) VALUES(:1,NOW(),NOW(),1,:2,:3)")
	public int insertNewUser(String openID,String nickName,String imageUrl);

	/**
	 * 取消关注事件
	 * 
	 */
	@SQL("UPDATE user SET ISValid = 0 WHERE ID =:1 ")

	public int deleteUser(int id);

	/**
	 * 判断是否已存在用户
	 * 
	 * @param openID
	 * @return
	 */
	@SQL("SELECT ID FROM user WHERE OpenID = :1")
	public Integer isExistUser(String openID);

	/**
	 * 老用户又关注
	 * 
	 */
	@SQL("UPDATE user SET ISValid = 1 ")
	public int againUser(int id);

	/**
	 * 获取所有关注的用户
	 * 
	 * @return
	 */
	@SQL("SELECT OpenID FROM user WHERE ISValid =1")
	public List<String> getAllLUser();
	/**
	 * 激活会员卡并修改状态
	 * @param vipNumber
	 * @param openID
	 * @return
	 */
	@SQL("UPDATE user SET vipNumber = :1,Vip =1 WHERE openID = :2")
	public int vipCreate(String vipNumber, String openID);

	@SQL("UPDATE user SET CardID = :1,Code=:2,nickName=:4,ImageUrl=:5 WHERE OpenID =:3 ")
	public Integer updateVipScard(String cardID, String code, String openID,String nickName,String imageUrl);

	@SQL("SELECT code FROM user WHERE OpenID =:1 AND CardID=:2")
	public String getCodeEveryVip(String openId, String cardId);

	@SQL("SELECT ID,OpenID,Vip,VipNumber,AccountNumber,CardID,Code,NickName,ImageUrl FROM user WHERE 1=1 ##(:where) ORDER BY :order LIMIT :beginIndex,:returnNum")
	public List<WeiXinUserModel> getWeixinUserList(@SQLParam("where") String where, @SQLParam("order") String order,
			@SQLParam("beginIndex") int beginIndex, @SQLParam("returnNum") int returnNum);

	@SQL("SELECT COUNT(1) FROM user WHERE 1=1 ##(:where)")
	public int getUserCount(@SQLParam("where") String where);

}
