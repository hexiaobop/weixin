package com.hxb.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @des    :时间工具类
 * @author hexiaobo 
 * @email  absod0711@gmail.com
 * @date   2016年5月7日
 */
public class DateUtils {
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	
	public static String getDateString(Date date)
	{
		String dateString = sdf.format(date);
		return dateString;
	}

}
