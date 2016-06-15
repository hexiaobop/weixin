package com.hxb.controllers;

import java.util.Map;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.hxb.util.Log4jUtil;
import com.hxb.util.MyException;
import com.hxb.util.WeixinConfigUtil;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;

/**
 * 获取微信config
 * 
 * @author hxb
 *
 * @date 2016年2月18日
 */
@Path("wxapi")
public class WeixinConfigController {
	private final static Logger appLog = Log4jUtil.appLog;
	private final static Logger sysLog = Log4jUtil.sysLog;

	@Get("wxconfig")
	public Object getWeixinConfig(Invocation inv) throws MyException {
		appLog.info("WeixinConfigController,getWeixinConfig");
		// 获 取refer加密试用
		String referURL = inv.getRequest().getHeader("Referer");
		if (referURL == null) {
			sysLog.warn("WeixinConfigController,getWeixinConfig,referURl:" + referURL);
			referURL = "";
		}
		Map<String, String> configMap = WeixinConfigUtil.getConfig(referURL);
		return "@" + (new Gson().toJson(configMap));
	}

	@Get("wxcardconfig")
	public Object getWeixinCardConfig(@Param("cardId") String cardId) throws MyException {
		Map<String, String> configMap = WeixinConfigUtil.getCardConfig(cardId);
		return "@" + (new Gson().toJson(configMap));
	}

}
