package com.hxb.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/****
 * 
 * @author wangqingsong
 *
 */
@SuppressWarnings("deprecation")
public class HttpClientUtils {
public static final String WEIXIN_USER_AGENT   = "Mozilla/5.0 (Windows; U; Windows NT 5.2) AppleWebKit/525.13 (KHTML, like Gecko) Chrome/0.2.149.27 Safari/525.13";
	
	/***
	 * com.yunyao.mochaweb.util.HttpClientUtils.getContent(HttpClientUtils.java:46)
	 * Invalid use of BasicClientConnManager: connection still allocated.
	 * Make sure to release the connection before allocating another one.
	 * 
	 * BUG解决: 在多线程的情况下, 请求我查查的HttpClient会出现上面的错误.
	 * 解决方案:
	 * new DefaultHttpClient(new ThreadSafeClientConnManager());
	 */
	private static HttpClient httpclient_weixin   = new DefaultHttpClient(new ThreadSafeClientConnManager());
	/***
	 * com.yunyao.mochaweb.util.HttpClientUtils.getContent(HttpClientUtils.java:46)
	 * Invalid use of BasicClientConnManager: connection still allocated.
	 * Make sure to release the connection before allocating another one.
	 * 
	 * BUG解决: 在多线程的情况下, 请求我查查的HttpClient会出现上面的错误.
	 * 解决方案:
	 * new DefaultHttpClient(new ThreadSafeClientConnManager());
	 */
	private static final String ERROR    = "{\"errno\":\"3\"}";
	private static HttpClient httpclient_me = new DefaultHttpClient(new ThreadSafeClientConnManager());
	private static HttpClient httpclient_wochacha = new DefaultHttpClient(new ThreadSafeClientConnManager());
	private static JsonParser jsonParser = new JsonParser();
	
	
	private static HttpClient httpclient_default  = new DefaultHttpClient(new ThreadSafeClientConnManager());
	/** 多盟使用 **/
	private static HttpClient httpclient_domob    = new DefaultHttpClient(new ThreadSafeClientConnManager());
	static {
		httpclient_default.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,  10000); //连接时间10s
		httpclient_default.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,  20000); //数据传输时间20s
		
		httpclient_domob.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,  10000); //连接时间10s
		httpclient_domob.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,  20000); //数据传输时间20s
		
		httpclient_wochacha.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,  10000); //连接时间10s
		httpclient_wochacha.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,  20000); //数据传输时间20s
	}

	
	public static String getWochachaURL(final String url){
		// HttpGet连接对象  
        HttpGet httpRequest = new HttpGet(url);  
        httpRequest.setHeader("User-Agent", "Dalvik/1.6.0 (Linux; U; Android 4.1.1; MI 2 MIUI/JLB21.0)");
        httpRequest.setHeader("Accept", "*/*");
        httpRequest.setHeader("Connection", "keep-alive");
        httpRequest.setHeader("Referer", url);
        try { 
            // 请求HttpClient，取得HttpResponse  
            HttpResponse httpResponse = httpclient_me.execute(httpRequest);  
            // 请求成功  
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {  
                // 取得返回的字符串  
                String strResult = EntityUtils.toString(httpResponse.getEntity());  
                return strResult;
            } else {  
            	return "HttpStatus is NOT OK!";
            } 
        } catch (Exception exp) {  
        	exp.printStackTrace();
        	Log4jUtil.exception(exp);
        } finally {
        	httpRequest.releaseConnection();
        }
        return null;
	}
	
	
	/****
	 * HttpClientUtils类对外公开取得URL的HTML的方法;
	 * @param url
	 * @return
	 */
	public static String getHtml(String url){
		// HttpGet连接对象  
        HttpGet httpRequest = new HttpGet(url);  
        httpRequest.setHeader("User-Agent", "Dalvik/1.6.0 (Linux; U; Android 4.0.2; Galaxy Nexus Build/ICL53F)");
        httpRequest.setHeader("Referer", url);
        try {
            // 请求HttpClient，取得HttpResponse
            HttpResponse httpResponse = httpclient_default.execute(httpRequest);
            // 请求成功 
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) { 
                // 取得返回的html;
                return EntityUtils.toString(httpResponse.getEntity(),"UTF-8");  
            }
        } catch (Exception exp) {
        	exp.printStackTrace();
        	Log4jUtil.exception(exp);
        } finally {
        	httpRequest.releaseConnection();
        }
        return null;
	}
	

	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String baseURL, String path, String paramStr) {
	//	String url = baseURL + "/" + path;
		String url = path;
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setRequestProperty("application", "x-www-form-urlencoded");
			//conn.setRequestProperty("Accept-Charset", "UTF-8");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(paramStr);
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			Log4jUtil.exception(e);
			e.printStackTrace();
		}
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				Log4jUtil.exception(ex);
				ex.printStackTrace();
			}
		}
		return result;
	}
	
	public static String urlEncode(String content) throws UnsupportedEncodingException {
		return java.net.URLEncoder.encode(content, "utf-8");
	}
	public static String getWeixinContent(String host, String url){
		// HttpGet连接对象  
        HttpGet httpRequest = new HttpGet(url);  
        httpRequest.setHeader("Host", host);
        httpRequest.setHeader("User-Agent", WEIXIN_USER_AGENT);
        httpRequest.setHeader("Accept", "*/*");
        httpRequest.setHeader("Referer", url);
        httpRequest.setHeader("Connection", "keep-alive");
        try { 
            // 请求HttpClient，取得HttpResponse  
            HttpResponse httpResponse = httpclient_weixin.execute(httpRequest);  
            // 请求成功  
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {  
                // 取得返回的字符串  
                String strResult = EntityUtils.toString(httpResponse.getEntity());  
                return strResult;
            } else {  
            	return null;
            } 
        } catch (Exception exp) {  
        	exp.printStackTrace();
        	Log4jUtil.exception(exp);
        } finally {
        	httpRequest.releaseConnection();
        }
        return null;
	}
	public static JsonElement getJsonObject(String url)
	{
		String resultString = getHtml(url);
		return jsonParser.parse(resultString);
	}
}
