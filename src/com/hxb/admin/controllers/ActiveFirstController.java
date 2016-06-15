package com.hxb.admin.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.hxb.admin.dao.ActiveFirstDAO;
import com.hxb.admin.model.ActiveFirst;
import com.hxb.admin.model.WeiXinUserModel;
import com.hxb.util.DataTableUtil;
import com.hxb.util.MyException;
import com.hxb.util.WeixinRequestUtil;
import com.hxb.util.DataTableUtil.DataTableRequestModel;
import com.hxb.util.DataTableUtil.DataTableResponseModel;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;

@Path("admin/active")
public class ActiveFirstController {
	private DataTableUtil util = DataTableUtil.getInstance();
	@Autowired
	ActiveFirstDAO activeFirstDAO;

	@Get("list")
	public String getActiveListPage() {
		return "/view/admin/active_list.jsp";
	}

	@Post("list")
	public Object getActiveList(Invocation inv) {
		int resultNum = 0;
		int allResult = 0;
		allResult = activeFirstDAO.getActiveListCount();
		DataTableRequestModel dataModel = util.getRequestModel(inv.getRequest());
		List<ActiveFirst> list = activeFirstDAO.getActiveFirstList(dataModel.getiDisplayStart(),
				dataModel.getiDisplayLength());
		if (list != null) {
			resultNum = list.size();
		}

		DataTableResponseModel responseModel = util.getResponseModel(resultNum, allResult, dataModel.getsEcho(), list);
		return WeixinRequestUtil.succAjax(responseModel);

	}

	@Post("delete")
	public Object deleteActive(@Param("id") int id) {
		if (activeFirstDAO.delete(id) == 0) {
			return WeixinRequestUtil.error(-200, "删除失败");
		} else {
			return WeixinRequestUtil.succ();
		}
	}

	@Post("add")
	public Object getActiveList(Invocation inv, ActiveFirst activeFirst, @Param("file") MultipartFile file)
			throws MyException, IOException {
		if (StringUtil.isBlank(file.getOriginalFilename())) {
			return WeixinRequestUtil.error(-200, "图片为空");
		}
		String imageUrl = savaImage(inv, file);
		activeFirst.setImageUrl(imageUrl);
		if (activeFirstDAO.addActiveFirst(activeFirst) == 0) {
			throw new MyException("添加失败");
		} else {
			return "r:/biyesheji/admin/active/list";
		}

	}

	@Post("update")
	public Object updateActive(Invocation inv, ActiveFirst activeFirst, @Param("file") MultipartFile file)
			throws MyException, IOException {
		if (StringUtil.isBlank(file.getOriginalFilename())) {
			;
		} else {
			String imageUrl = savaImage(inv, file);
			activeFirst.setImageUrl(imageUrl);
		}
		if (activeFirstDAO.updateActiveFirst(activeFirst) == 0) {
			throw new MyException("更新失败");
		} else {
			return "r:/biyesheji/admin/active/list";
		}

	}

	public String savaImage(Invocation inv, MultipartFile file) throws IOException {
		String savaPath = inv.getRequest().getSession().getServletContext().getRealPath("/") + "upload";
		long fileName1 = new Date().getTime();
		String fileName2 = file.getOriginalFilename().split("\\.")[1];
		String fileName = fileName1 + "." + fileName2;
		FileOutputStream out = new FileOutputStream(new File(savaPath + File.separator + fileName));
		out.write(file.getBytes());
		out.close();
		return File.separator + "biyesheji" + File.separator + "upload" + File.separator + fileName;

	}
}
