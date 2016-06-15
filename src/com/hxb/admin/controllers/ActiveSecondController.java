package com.hxb.admin.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hxb.admin.dao.ActiveFirstDAO;
import com.hxb.admin.dao.ActiveSecondDAO;
import com.hxb.admin.dao.WxCardDAO;
import com.hxb.admin.model.ActiveFirst;
import com.hxb.admin.model.ActiveSecondModel;
import com.hxb.admin.model.WxCardModel;
import com.hxb.util.DataTableUtil;
import com.hxb.util.WeixinRequestUtil;
import com.hxb.util.DataTableUtil.DataTableRequestModel;
import com.hxb.util.DataTableUtil.DataTableResponseModel;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;

@Path("admin/activetwo")
public class ActiveSecondController {
	private DataTableUtil util = DataTableUtil.getInstance();
	@Autowired
	ActiveFirstDAO activeFirstDAO;
	@Autowired
	WxCardDAO wxCardDAO;
	@Autowired
	ActiveSecondDAO activeSecondDAO;

	@Get("add")
	public Object addActiveSecond(Invocation inv) {
		List<ActiveFirst> activeList = activeFirstDAO.getActiveFirstList(0, 1000);
		List<WxCardModel> cardList = wxCardDAO.getCardList(0, 1000);
		inv.addModel("activeFirst", activeList);
		inv.addModel("cardList", cardList);
		return "/view/admin/active_add.jsp";
	}

	@Get("list")
	public Object getListPage() {
		return "/view/admin/active_info_list.jsp";
	}

	@Post("list")
	public Object getActiveList(Invocation inv) {
		int resultNum = 0;
		int allResult = 0;
		allResult = activeSecondDAO.getActiveSecondCount();
		DataTableRequestModel dataModel = util.getRequestModel(inv.getRequest());
		List<ActiveSecondModel> list = activeSecondDAO.getActiveSecondList(dataModel.getiDisplayStart(),
				dataModel.getiDisplayLength());
		if (list != null) {
			resultNum = list.size();
		}

		DataTableResponseModel responseModel = util.getResponseModel(resultNum, allResult, dataModel.getsEcho(), list);
		return WeixinRequestUtil.succAjax(responseModel);
	}
	@Get("update")
	public Object getUpdatePage(Invocation inv ,@Param("id") int id)
	{
		ActiveSecondModel activeSecondModel = activeSecondDAO.getActiveSecondThroughId(id);
		inv.addModel("activeInfo", activeSecondModel);
		return "/view/admin/active_update.jsp";
	}
	@Post("update")
	public Object updateActive(ActiveSecondModel activeSecondModel, @Param("editorValue") String editorValue)
	{
		if (editorValue == null) {
			editorValue = " ";
		}
		activeSecondModel.setActiveDes(editorValue);
		if (activeSecondDAO.updateActiveSecond(activeSecondModel) == 0) {
			return WeixinRequestUtil.error(-200, "更新失败");
		} else {
			return "r:/biyesheji/admin/activetwo/list";
		}
	}
	@Post("add")
	public Object addActiveSecond(ActiveSecondModel activeSecondModel, @Param("editorValue") String editorValue) {
		if (editorValue == null) {
			editorValue = " ";
		}
		activeSecondModel.setActiveDes(editorValue);
		if (activeSecondDAO.addActiveSecond(activeSecondModel) == 0) {
			return WeixinRequestUtil.error(-200, "添加失败");
		} else {
			return "r:/biyesheji/admin/activetwo/list";
		}

	}

	@Post("delete")
	public Object deleteActiveSecond(@Param("id") int id) {
		if (activeSecondDAO.deleteActiveSecond(id) == 0) {
			return WeixinRequestUtil.error(-200, "删除失败");
		} else {
			return WeixinRequestUtil.succ();
		}
	}

	public Object deleteActiveSecond() {
		return null;
	}
	

	
}
