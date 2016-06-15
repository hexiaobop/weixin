package com.hxb.admin.controllers;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.AppConstants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hxb.admin.model.MediaModel;
import com.hxb.message.service.ImageManagerService;
import com.hxb.util.DataTableUtil;
import com.hxb.util.DataTableUtil.DataTableRequestModel;
import com.hxb.util.DataTableUtil.DataTableResponseModel;
import com.hxb.util.MyException;
import com.hxb.util.WeixinConfigUtil;
import com.hxb.util.WeixinRequestUtil;
import com.hxb.util.uploadfile.UploadFileToWeixin;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;

@Path("admin/image")
public class UploadFileController {
	@Autowired
	ImageManagerService ImageManagerService;
	private DataTableUtil util = DataTableUtil.getInstance();

	@Get("list")
	public Object getImageListPage(Invocation inv) {

		return "/view/admin/admin_image_list.jsp";
	}

	@Post("upload")
	public Object upLoadFile(Invocation inv, MultipartFile[] list) throws MyException, ServletException, IOException {
		String fileName = UploadFileToWeixin.handleFile(inv.getRequest(), list);
		// String url = AppConstants.replace("TYPE", "image");
		String token = WeixinConfigUtil.getAccessToken();
		String url = AppConstants.UPLOAD_IMAGE_URL.replace("ACCESS_TOKEN", token);
		System.out.println(fileName);
		String imageURLurl = UploadFileToWeixin.send(url, fileName);
		ImageManagerService.insertImage(imageURLurl);
		return "/view/admin/admin_image_list.jsp";

	}

	@Post("list")
	public Object getImageListPageList(Invocation inv) {
		int resultNum = 0;
		DataTableRequestModel requestModel = util.getRequestModel(inv.getRequest());
		DataTableRequestModel dataModel = DataTableUtil.getInstance().getRequestModel(inv.getRequest());
		List<MediaModel> list = ImageManagerService.getMediaList(dataModel.getiDisplayStart(), dataModel.getiDisplayLength());
		if(list != null)
		{
			resultNum = list.size();
		}
		DataTableResponseModel responseModel = util.getResponseModel(resultNum, 500, dataModel.getsEcho(), list);
		return WeixinRequestUtil.succAjax(responseModel);
	}
public static void main(String[] args) {
	Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create(); 
	System.out.println(gson.toJson(new Date()));
}
}
