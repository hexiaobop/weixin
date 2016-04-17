package com.hxb.controllers.Interceptor;

import java.lang.annotation.Annotation;

import javax.servlet.http.HttpSession;

import com.AppConstants;

import net.paoding.rose.web.ControllerInterceptorAdapter;
import net.paoding.rose.web.Invocation;
/**
 * 
 * @des    : 网页授权判断拦截器
 * @author hexiaobo 
 * @email  absod0711@gmail.com
 * @date   2016年4月17日
 */
public class IsLoginInterceptor extends ControllerInterceptorAdapter {

	@Override
	protected Object before(Invocation inv) throws Exception {
		HttpSession session = inv.getRequest().getSession();
		System.out.println("before");
		System.out.println(session.getAttribute("appid").toString());
		if (session.getAttribute("appid") == null || session.getAttribute("appid").toString().equals("") ) {
			return "/view/authorize.jsp";
		}
		return true;
	}

	@Override
	protected Class<? extends Annotation> getRequiredAnnotationClass() {
		return IsLogin.class;
	}

}
