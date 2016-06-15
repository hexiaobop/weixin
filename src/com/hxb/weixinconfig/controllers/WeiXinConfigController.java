package com.hxb.weixinconfig.controllers;

import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import com.hxb.util.CheckUtil;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;

/**
 * 
 * @des    : 微信首次验证验证，微信各种token的获取
 * @author hexiaobo 
 * @email  absod0711@gmail.com
 * @date   2016年3月26日
 */
@Path("home")
public class WeiXinConfigController {
	
	@Get("access")
	@Post("access")
	public Object firstAccess(Invocation inv) throws Exception
	{
		HttpServletRequest req = inv.getRequest();	
		String signature = req.getParameter("signature");
		String timestamp = req.getParameter("timestamp");
		String nonce = req.getParameter("nonce");
		String echostr = req.getParameter("echostr");				
		if(CheckUtil.checkSignature(signature, timestamp, nonce)){
			return "@"+echostr;
		}
		else
		{
			return "@error";
		}
	}
	@Get("access2")
	public Object getName()
	{
		return "@hexiaobo";
	}

}
