package com.hxb.weixinconfig.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.util.WebUtils;

import com.hxb.util.Log4jUtil;
import com.hxb.util.WeixinRequestUtil;

import net.paoding.rose.web.ControllerErrorHandler;
import net.paoding.rose.web.Invocation;

/**
 * 
 * @des    : 抛出异常 返回数据
 * @author hexiaobo 
 * @email  absod0711@gmail.com
 * @date   2016年4月14日
 */
public class ErrorHandler implements ControllerErrorHandler {

	public Object onError(Invocation inv, MyException ex) throws Throwable {
		MyException me = (MyException) ex;
		//对于MochaException,清除Servlet Error.
		HttpServletRequest request = inv.getRequest();
		/***
		 * rose.doNext() 执行时,我们抛出了 MochaException , 被catch捕获, 同时交给了我们定义的 MochaErrorHandler进行处理. 
		 * 但是,交由MochaErrorHandler处理前, rose 调用了WebUtils.exposeErrorRequestAttributes(request, cause, null); 
		 * 如果我们的MochaErrorHandler里不清除request里的error, 
		 * 则执行完后, web容易会再进行一次异常捕获判断,并跳转到 exception.jsp里. 所以就返回了Html
		 * 
		 * 所以我们定义的MochaErrorHandler在处理MochaExceltion后,要执行WebUtils.clearErrorRequestAttributes(request);
		 */
		WebUtils.clearErrorRequestAttributes(request);
		return WeixinRequestUtil.error(500, me.getMessage());
	}

	@Override
	public Object onError(Invocation inv, Throwable ex) throws Throwable {
		//记录异常,清除Servlet Error.
		Log4jUtil.exception((Exception) ex);
		HttpServletRequest request = inv.getRequest();
		WebUtils.clearErrorRequestAttributes(request);
		return WeixinRequestUtil.error(500, "服务器错误");
	}
}
