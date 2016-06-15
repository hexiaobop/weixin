package com.hxb.admin.controllers;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;

import com.hxb.admin.dao.ActiveFirstDAO;
import com.hxb.admin.dao.ActiveSecondDAO;
import com.hxb.admin.dao.WxCardDAO;
import com.hxb.admin.model.ActiveFirst;
import com.hxb.admin.model.ActiveSecondModel;
import com.hxb.util.MyException;
import com.hxb.util.WeixinConfigUtil;
import com.hxb.util.WeixinRequestUtil;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;

@Path("h5/active")
public class H5DataController {

	@Autowired
	ActiveFirstDAO activeFirstDAO;

	@Autowired
	ActiveSecondDAO activeSecondDAO;
	
	@Autowired
	WxCardDAO wxCardDAO;

	@Get("list")
	public Object getActive(Invocation inv) {
		inv.getResponse().addHeader("Access-Control-Allow-Origin", "*");
		List<ActiveFirst> list = activeFirstDAO.getActiveFirstList(0, 1000);
		if (list == null || list.size() == 0) {
			return WeixinRequestUtil.succ(Collections.emptyList());
		}
		return WeixinRequestUtil.succ(list);
	}

	@Get("list/info")
	public Object getListInfo(Invocation inv, @Param("id") int id) {
		inv.getResponse().addHeader("Access-Control-Allow-Origin", "*");
		List<ActiveSecondModel> list = activeSecondDAO.getActiveSecondListThroughtypeID(id);
		if (list == null || list.size() == 0) {
			return WeixinRequestUtil.succ(Collections.emptyList());
		}
		return WeixinRequestUtil.succ(list);
	}

	@Get("card")
	public Object getCard(Invocation inv, @Param("cardId") String cardId) throws MyException {
		inv.getResponse().addHeader("Access-Control-Allow-Origin", "*");
		Map<String, String> configMap = WeixinConfigUtil.getCardConfig(cardId);
		inv.addModel("cardConfig", configMap);
		return "/view/admin/card.jsp";
	}
	
	@Get("crazycard")
	public Object getCardFree(Invocation inv) throws MyException {
		String cardId = wxCardDAO.getCarzyCard();
		if(StringUtil.isBlank(cardId))
		{
			return WeixinRequestUtil.error(-200, "活动还没开始");
		}
		//inv.getResponse().addHeader("Access-Control-Allow-Origin", "*");
		Map<String, String> configMap = WeixinConfigUtil.getCardConfig(cardId);
		inv.addModel("cardConfig", configMap);
		return "/view/admin/card.jsp";
	}

}
