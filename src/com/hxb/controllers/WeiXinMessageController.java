package com.hxb.controllers;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;

import com.hxb.everydaytask.EveryDayTask;
import com.hxb.message.HandleMessageService;
import com.hxb.message.MessageUtil;
import com.hxb.message.SendAllUserTask;
import com.hxb.message.dao.WeixinUserDAO;
import com.hxb.message.service.ArticlesService;
import com.hxb.message.service.ArticlesService;
import com.hxb.util.BeanFactory;
import com.hxb.util.CheckUtil;
import com.hxb.util.MyException;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;

/**
 * 
 * @des : 微信首次验证验证，微信各种token的获取,消息处理
 * @author hexiaobo
 * @email absod0711@gmail.com
 * @date 2016年3月26日
 */
@Path("home")
public class WeiXinMessageController {
	@Autowired
	HandleMessageService handleMessageService;

	/***
	 * 首次验证 get方式
	 * 
	 * @param inv
	 * @return
	 * @throws Exception
	 */
	@Get("access")
	public Object firstAccess(Invocation inv) throws Exception {
		HttpServletRequest req = inv.getRequest();
		String signature = req.getParameter("signature");
		String timestamp = req.getParameter("timestamp");
		String nonce = req.getParameter("nonce");
		String echostr = req.getParameter("echostr");
		if (CheckUtil.checkSignature(signature, timestamp, nonce)) {
			return "@" + echostr;
		} else {
			return "@error";
		}
	}

	/**
	 * 处理微信主动发送的请求消息
	 * @return
	 * @throws DocumentException 
	 * @throws IOException 
	 * @throws MyException 
	 */
	@Post("access")
	public String getName(Invocation inv) throws IOException, DocumentException, MyException
	{
		inv.getRequest().setCharacterEncoding("UTF-8");
		inv.getResponse().setCharacterEncoding("UTF-8");
		Enumeration enu=inv.getRequest().getParameterNames();  
		while(enu.hasMoreElements()){  
		String paraName=(String)enu.nextElement();  
		System.out.println(paraName+": "+inv.getRequest().getParameter(paraName));  
		}  
		Map<String,String> hashMap = MessageUtil.xmlToMap(inv.getRequest());
		if(hashMap.get("MsgType").equals("event"))
		{
			
			System.out.println(hashMap.get("Event"));
			System.out.println("------------------------------");
			String result = handleMessageService.handleEventMessage(hashMap);
			return "@"+result;
		}
		else if(hashMap.get("MsgType").equals("text")){	
			System.out.println("text");
//			try {
//				//EveryDayTask.startTask(SendAllUserTask.class, new int[] { 23, 41, 0 }, true);
//			} catch (InstantiationException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IllegalAccessException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			String result = handleMessageService.handleGeneralMessage(hashMap);
	        return "@"+result;
		  

		}
		return "";
	}
	
/*	<xml> <ToUserName><![CDATA[toUser]]></ToUserName> 
	<FromUserName><![CDATA[FromUser]]></FromUserName> 
	<FriendUserName><![CDATA[FriendUser]]></FriendUserName> 
	<CreateTime>123456789</CreateTime> 
	<MsgType><![CDATA[event]]></MsgType> 
	<Event><![CDATA[user_get_card]]></Event> 
	<CardId><![CDATA[cardid]]></CardId> 
	<IsGiveByFriend>1</IsGiveByFriend>
	<UserCardCode><![CDATA[12312312]]></UserCardCode>
	<OuterId>0</OuterId>
	</xml>*/

}
