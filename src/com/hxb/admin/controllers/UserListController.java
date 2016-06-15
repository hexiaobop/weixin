package com.hxb.admin.controllers;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.hxb.admin.model.MediaModel;
import com.hxb.admin.model.WeiXinUserModel;
import com.hxb.message.dao.WeixinUserDAO;
import com.hxb.util.DataTableUtil;
import com.hxb.util.WeixinRequestUtil;
import com.hxb.util.DataTableUtil.DataTableRequestModel;
import com.hxb.util.DataTableUtil.DataTableResponseModel;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;

@Path("admin/user")
public class UserListController {
	private DataTableUtil util = DataTableUtil.getInstance();
	@Autowired
	WeixinUserDAO weixinUserDAO;

	@Get("list")
	public String getUserPage() {
		return "/view/admin/user_list.jsp";
	}

	@Post("list")
	public Object getUserList(Invocation inv, @Param("username") String userName, @Param("vipnumber") String vipNumber,
			@Param("code") String code) {
		int resultNum = 0;
		int allResult = 0;
		String where = "";
		String order = " ID ASC ";
		DataTableRequestModel dataModel = util.getRequestModel(inv.getRequest());
		if (StringUtils.isNotBlank(userName)) {
			where += " AND NickName LIKE '%" + userName.trim() + "%' ";
		}
		if (StringUtils.isNotBlank(code)) {
			where += " AND Code LIKE '%" + code.trim() + "%' ";
		}
		if (StringUtils.isNotBlank(vipNumber)) {
			where += " AND VipNumber LIKE '%" + vipNumber.trim() + "%' ";
		}
		allResult = weixinUserDAO.getUserCount(where);
		System.out.println(where);
		List<WeiXinUserModel> list = weixinUserDAO.getWeixinUserList(where,order,dataModel.getiDisplayStart(),dataModel.getiDisplayLength());
		if (list != null) {
			resultNum = list.size();
		}
		
		DataTableResponseModel responseModel = util.getResponseModel(resultNum, allResult, dataModel.getsEcho(), list);
		return WeixinRequestUtil.succAjax(responseModel);
	}
}
