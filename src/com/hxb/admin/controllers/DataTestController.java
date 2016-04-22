package com.hxb.admin.controllers;

import java.util.ArrayList;
import java.util.List;

import com.hxb.admin.model.DataTest;
import com.hxb.util.DataTableUtil;
import com.hxb.util.DataTableUtil.DataTableResponseModel;
import com.hxb.util.WeixinRequestUtil;

import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Post;

/**
 * 
 * @des    :datatable 数据测试
 * @author hexiaobo 
 * @email  absod0711@gmail.com
 * @date   2016年4月22日
 */
@Path("datatest")
public class DataTestController {
	@Post("list")
	public Object getDatatableList()
	{
		List<DataTest> list = new ArrayList<>();
		for(int i =0;i<20;i++)
		{
			list.add(new DataTest("何小波", "123456"));
		}
		DataTableUtil util = DataTableUtil.getInstance();
		DataTableResponseModel responseModel = util.getResponseModel(15, 100,
				"2", list);
		return WeixinRequestUtil.succAjax(responseModel);
	}

}
