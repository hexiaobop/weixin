package com.hxb.util;

import org.apache.log4j.Logger;

/***
 * 
 * Log4j应用配置信息,在Spring环境中,spring会自动去把log4j的配置文件找到并做相关的初始化;
 * @author wangqingsong
 *
 */
public class Log4jUtil {
	
	// 系统日志, 记录异常信息;
	public static final Logger sysLog = Logger.getLogger("SYS");
	// 应用日志, 记录接口的访问信息;
	public static final Logger appLog = Logger.getLogger("APP");
	// 性能日志, 记录执行SQL,使用文件,网络等资源时的耗时时间信息;
	public static final Logger pfmLog = Logger.getLogger("PFM");
	// 服务日志, 记录服务日志的情况;
	public static final Logger svcLog = Logger.getLogger("SVC");
	// 临时日志, 记录服务日志的情况;
	public static final Logger tmpLog = Logger.getLogger("TMP");
	// 逻辑日志, 记录服务逻辑错误日志,出现逻辑错误,需要手工处理
	public static final Logger lgcLog = Logger.getLogger("LGC");
	
	/***
	 * 异常日志;
	 * @param exp
	 */
	public static void exception(Exception exp) {
		if (exp == null) {
			sysLog.error("unknown error");
			return;
		}
		try {
			StackTraceElement[] s = exp.getStackTrace();
			// 针对java.lang.NollPointerException的异常;
			// exp.getMessage()返回为null; 
			String expMes = getTrimIfNullValueBlank(exp.getMessage());
			StringBuffer em = new StringBuffer(expMes);
			if (s != null) {
				for (int i = 0; i < s.length; i++) {
					StackTraceElement st = s[i];
					em.append("\t\t").append(st.toString()).append("\r\n");
				}
			}
			sysLog.error(em.toString());
		} catch (Exception excp) {
			excp.printStackTrace();
		}
	}
	
	/**
	 * getLogger
	 * @param logName
	 * @return
	 */
	public static Logger getLogger(String logName){
		if(logName==null || logName.trim().length()==0){
			return null;
		}
		try {
			return Logger.getLogger(logName);
		} catch (Exception excp) {
			excp.printStackTrace();
			Log4jUtil.exception(excp);
		}
		return null;
	}
	
	public static void main(String[] arg){
		exception(new NullPointerException());
	}
	public static final String getTrimIfNullValueBlank(String str){
		if(str==null || str.trim().length()==0){
			return "";
		} 
		return str.trim();
	}
}
