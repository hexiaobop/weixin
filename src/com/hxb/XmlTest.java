package com.hxb;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;

import com.google.gson.Gson;
import com.imooc.po.TextMessage;
import com.thoughtworks.xstream.XStream;

public class XmlTest {
	
static	String s ="<xml><ToUserName>hexiaobo</ToUserName> "
		+ "<FromUserName><![CDATA[fromUser]]></FromUserName> "
		+ "<CreateTime>1348831860</CreateTime> "
		+ "<MsgType><![CDATA[text]]></MsgType>"
		+ "<Content><![CDATA[this is a test]]></Content>"
		+ "<MsgId>1234567890123456</MsgId></xml>";
public static void main(String[] args) throws DocumentException {
	   Document   document = DocumentHelper.parseText(s);
	   System.out.println(document.getRootElement().elementText("ToUserName").toString());
	   TextMessage textMessage = new TextMessage();
	   XStream xstream = new XStream();

		xstream.alias("sml", textMessage.getClass());
		xstream.toXML(textMessage);
		System.out.println(xstream.toXML(textMessage));
		
		Gson gson = new Gson();
		System.out.println(gson.toJson(new TextMessage()));
		
	   
	  
}
}
