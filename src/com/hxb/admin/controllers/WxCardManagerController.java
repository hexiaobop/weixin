package com.hxb.admin.controllers;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;

import com.AppConstants;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hxb.admin.dao.WxCardDAO;
import com.hxb.admin.model.WxCardModel;
import com.hxb.util.DataTableUtil;
import com.hxb.util.DataTableUtil.DataTableRequestModel;
import com.hxb.util.DataTableUtil.DataTableResponseModel;
import com.hxb.util.HttpClientUtils;
import com.hxb.util.MyException;
import com.hxb.util.WeixinCardUtils;
import com.hxb.util.WeixinConfigUtil;
import com.hxb.util.WeixinRequestUtil;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;

/**
 * 
 * @des :卡卷管理
 * @author hexiaobo
 * @email absod0711@gmail.com
 * @date 2016年5月23日
 */
@Path("admin/card")
public class WxCardManagerController {
	private DataTableUtil util = DataTableUtil.getInstance();
	private static JsonParser jsonParser = new JsonParser();
	@Autowired
	WxCardDAO wxCardDAO;

	@Get("list")
	public String getCardListPage(Invocation inv) {
		String token = null;
		try {
			token = WeixinConfigUtil.getAccessToken();
		} catch (MyException e) {
			e.printStackTrace();
		}
		inv.addModel("token", token);
		return "/view/admin/wx_card_manager.jsp";
	}

	@Post("list")
	public String getCardList(Invocation inv) {

		int resultNum = 0;
		DataTableRequestModel requestModel = util.getRequestModel(inv.getRequest());
		DataTableRequestModel dataModel = DataTableUtil.getInstance().getRequestModel(inv.getRequest());
		List<WxCardModel> list = wxCardDAO.getCardList(dataModel.getiDisplayStart(), dataModel.getiDisplayLength());
		if (list != null) {
			resultNum = list.size();
		}
		DataTableResponseModel responseModel = util.getResponseModel(resultNum, wxCardDAO.getCardNum(),
				dataModel.getsEcho(), list);
		return WeixinRequestUtil.succAjax(responseModel);

	}

	@Post("delete")
	public String deleteCard(Invocation inv, @Param("id") int id) throws MyException {
		if (wxCardDAO.deleteCard(id) == 1) {
			return WeixinRequestUtil.succ();
		} else {
			return WeixinRequestUtil.error(-200, "成功删除");
		}

	}
	
	@Post("whitelist")
	public String setWhiteList(@Param("whitelist") String whiteList) throws MyException
	{
		String token = WeixinConfigUtil.getAccessToken();
		String url = AppConstants.WHITE_LIST.replace(AppConstants.ACCESS_TOKEN, token);
		String resultString = HttpClientUtils.sendPost("", url, whiteList);
		JsonObject jsonObject = jsonParser.parse(resultString).getAsJsonObject();
		return WeixinRequestUtil.succ(jsonObject.get("errmsg").getAsString());		
		
	}

	@Post("create")
	public String createCard(Invocation inv,@Param("cardname") String cardName, @Param("data") String data) throws MyException {
		String token = WeixinConfigUtil.getAccessToken();
//		try {
//			inv.getRequest().setCharacterEncoding("UTF-8");
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		String url = AppConstants.CREATE_CARD_URL.replace(AppConstants.ACCESS_TOKEN, token);
		String resultString = HttpClientUtils.sendPost("", url, data);
		System.out.println(resultString);
		JsonObject jsonObject = jsonParser.parse(resultString).getAsJsonObject();
		String cardType = jsonParser.parse(data).getAsJsonObject().get("card").getAsJsonObject().get("card_type")
				.getAsString();
		String cardId = jsonObject.get("card_id").getAsString();
		// setField(token,cardId);

		if (wxCardDAO.createCard(cardType, cardId, data,cardName) == 0) {
			return WeixinRequestUtil.error(-200, "创建失败");
		}
		return WeixinRequestUtil.succ();

	}

	@Post("accountnumber")
	public String getAccountNumber(@Param("card") String cardId, @Param("code") String code) throws MyException {
		return WeixinRequestUtil.succ(WeixinCardUtils.getAccountNumber(cardId, code));
	}

	@Post("updateaccountnumber")
	public String updateAccountNumber(@Param("card") String cardId, @Param("code") String code,
			@Param("bonus") int bonus, @Param("record") String record) throws MyException {
		return WeixinRequestUtil.succ(WeixinCardUtils.updateCardAccountNumber(cardId, code, bonus, record));
	}

	/**
	 * 获得会员卡的cardID
	 * 
	 * @return
	 */
	@Get("vipcard")
	public Object getVipCardId() {
		String cardId = wxCardDAO.getVipCard();
		if (cardId == null || StringUtils.isBlank(cardId)) {
			return WeixinRequestUtil.error(-200, "没有会员卡");
		}
		return WeixinRequestUtil.succ(cardId);

	}
	


	public static void setField(String token, String cardId) {
		String field = "{\"card_id\": \"" + cardId
				+ "\", \"required_form\": { \"common_field_id_list\":[\"USER_FORM_INFO_FLAG_MOBILE\", \"USER_FORM_INFO_FLAG_LOCATION\", \"USER_FORM_INFO_FLAG_BIRTHDAY\" ] },}";
		String vipFieldUrl = AppConstants.VIP_FIELD.replace(AppConstants.ACCESS_TOKEN, token);
		HttpClientUtils.sendPost("", vipFieldUrl, field);
	}

	public static void main(String[] args) {

		setField(
				"cMuVoON5sCP8GBg-sh-Tu0gNBG5FQ3RF52L8SAEo6V19FBsbTH93fbu9wm3X6Ngdx2G_c2iXOgmx_vbCtIjZiubSRnvDxg32aR6QlZgnokypn0nsBx6ze5CGqTM0XQiaPNSfAAATRW",
				"p8902t4u-ceu0h9poEcchAsI0JRw");
	}
}
