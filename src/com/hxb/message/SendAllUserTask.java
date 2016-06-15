package com.hxb.message;

import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hxb.message.service.ArticlesService;
import com.hxb.message.service.ArticlesService;
import com.hxb.util.BeanFactory;
import com.hxb.util.MyException;

/*
 * 
 */
public class SendAllUserTask extends TimerTask {
	@Autowired
	ArticlesService articlesService;

	@Override
	public void run() {
		if (articlesService == null) {
			articlesService = (ArticlesService) BeanFactory.getBean("articlesService");
		}
		try {
			articlesService.sendAllUser();
		} catch (MyException e) {

			e.printStackTrace();
		}
	}
}
