package com.hxb.controllers;

import com.hxb.controllers.Interceptor.IsLogin;

import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;

/**
 * 
 * @des : 会员中心 绑定，查询
 * @author hexiaobo
 * @email absod0711@gmail.com
 * @date 2016年4月17日
 */
@IsLogin
@Path("vip")
public class VipController {
	@Get("band")
	public Object bandAccount() {
		System.out.println("jinru ");
		return null;

	}

}
