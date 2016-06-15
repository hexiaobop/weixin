package com.hxb.util.uploadfile;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hxb.util.WeixinRequestUtil;

import net.sf.json.JSONObject;

public class UploadFileToWeixin {

	/**
	 * 使用GET提交到目标服务器。
	 * 
	 * @param request
	 * @param response
	 * @param targetUrl
	 * @throws IOException
	 */
	public static void get(HttpServletRequest request, HttpServletResponse response, String targetUrl)
			throws IOException {

		URL url = new URL(targetUrl);

		BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

		String line;
		PrintWriter out = response.getWriter();
		while ((line = in.readLine()) != null) {
			out.println(line);
		}
		out.flush();
		in.close();
	}

	/**
	 * 使用POST提交到目标服务器。
	 * 
	 * @param request
	 * @param response
	 * @param targetUrl
	 * @throws IOException
	 */
	public static String post(MultipartFile request, HttpServletResponse response, String targetUrl,
			HttpServletRequest req) throws IOException {
		URL url = new URL(targetUrl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		// 发送POST请求必须设置如下两行
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.setUseCaches(false);
		Map<String, String> map = new HashMap<String, String>();
		Enumeration headerNames = req.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String key = (String) headerNames.nextElement();
			String value = req.getHeader(key);
			map.put(key, value);
			System.out.println(key + "  " + value);
			conn.setRequestProperty(key, value);
		}

		conn.setRequestMethod("POST");
		// 可以拷贝客户端的head信息作为请求的head参数
		// conn.setRequestProperty("Charsert", "UTF-8");
		// conn.setRequestProperty("Connection", "Keep-Alive");
		//
		// conn.setRequestProperty("Charset", "UTF-8");
		//
		// conn.setRequestProperty("Content-Type", "multipart/form-data;
		// boundary=----WebKitFormBoundaryOpXdLA76y1duOCOA");
		//
		// conn.setRequestProperty("Content-Type",
		// req.getHeader("Content-Type"));
		// System.out.println( req.getHeader("Content-Type"));

		// 直接把客户端的BODY传给目标服务器
		OutputStream send = conn.getOutputStream();
		InputStream body = request.getInputStream();
		IOUtils.copy(body, send);
		send.flush();
		send.close();
		body.close();

		// 获取返回值
		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		PrintWriter out = response.getWriter();
		String line;
		while ((line = in.readLine()) != null) {
			out.println(line);
		}
		out.flush();
		return line;
	}

	public static String handleFile(HttpServletRequest request, MultipartFile[] list)
			throws ServletException, IOException {
		// //得到上传文件的保存目录，将上传的文件存放于WEB-INF目录下，不允许外界直接访问，保证上传文件的安全
		String savePath = Servlet.class.getResource("/").getPath();
		savePath = new File(savePath).getParent();
		savePath = savePath + File.separator + "upload";
		File file = new File(savePath);
		String filePath = null;
		// 判断上传文件的保存目录是否存在
		if (!file.exists() && !file.isDirectory()) {
			System.out.println(savePath + "目录不存在，需要创建");
			// 创建目录
			file.mkdir();
		}
		// //消息提示
		String message = "";
		try {
			// //使用Apache文件上传组件处理文件上传步骤：
			// //1、创建一个DiskFileItemFactory工厂
			// DiskFileItemFactory factory = new DiskFileItemFactory();
			// //2、创建一个文件上传解析器
			// ServletFileUpload upload = new ServletFileUpload(factory);
			// //解决上传文件名的中文乱码
			// upload.setHeaderEncoding("UTF-8");
			// //3、判断提交上来的数据是否是上传表单的数据
			// if(!ServletFileUpload.isMultipartContent(request)){
			// //按照传统方式获取数据
			// return null;
			// }
			// 4、使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个Form表单的输入项

			System.out.println(list);
			for (MultipartFile item : list) {
				// 如果fileitem中封装的是普通输入项的数据
				// 如果fileitem中封装的是上传文件
				// 得到上传的文件名称，
				String filename = item.getOriginalFilename();

				System.out.println(filename);
				System.out.println(item.getOriginalFilename());
				if (filename == null || filename.trim().equals("")) {
					continue;
				}
				// 注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如：
				// c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
				// 处理获取到的上传文件的文件名的路径部分，只保留文件名部分
				filename = filename.substring(filename.lastIndexOf("\\") + 1);

				// 获取item中的上传文件的输入流
				InputStream in = item.getInputStream();
				// 创建一个文件输出流
				File f = new File(savePath + File.separator + filename);
				FileOutputStream out = new FileOutputStream(f);
				filePath = savePath + File.separator + filename;
				// 创建一个缓冲区
				byte buffer[] = new byte[1024];
				// 判断输入流中的数据是否已经读完的标识
				int len = 0;
				// 循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
				while ((len = in.read(buffer)) > 0) {
					// 使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\" +
					// filename)当中
					out.write(buffer, 0, len);
				}
				// 关闭输入流
				in.close();
				// 关闭输出流
				out.close();
				// 删除处理文件上传时生成的临时文件
				// item.delete();
				message = "文件上传成功！";
			}

		} catch (Exception e) {
			message = "文件上传失败！";
			e.printStackTrace();
		}
		return filePath;
	}

	public static String send(String url, String filePath) throws IOException {

		String result = null;

		File file = new File(filePath);
		if (!file.exists() || !file.isFile()) {
			throw new IOException("文件不存在");
		}

		/**
		 * 第一部分
		 */
		URL urlObj = new URL(url);
		// 连接
		HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();

		/**
		 * 设置关键值
		 */
		con.setRequestMethod("POST"); // 以Post方式提交表单，默认get方式
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setUseCaches(false); // post方式不能使用缓存

		// 设置请求头信息
		con.setRequestProperty("Connection", "Keep-Alive");
		con.setRequestProperty("Charset", "UTF-8");

		// 设置边界
		String BOUNDARY = "----------" + System.currentTimeMillis();
		con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

		// 请求正文信息

		// 第一部分：
		StringBuilder sb = new StringBuilder();
		sb.append("--"); // 必须多两道线
		sb.append(BOUNDARY);
		sb.append("\r\n");
		sb.append("Content-Disposition: form-data;name=\"file\";filename=\"" + file.getName() + "\"\r\n");
		sb.append("Content-Type:application/octet-stream\r\n\r\n");

		byte[] head = sb.toString().getBytes("utf-8");

		// 获得输出流
		OutputStream out = new DataOutputStream(con.getOutputStream());
		// 输出表头
		out.write(head);

		// 文件正文部分
		// 把文件已流文件的方式 推入到url中
		DataInputStream in = new DataInputStream(new FileInputStream(file));
		int bytes = 0;
		byte[] bufferOut = new byte[1024];
		while ((bytes = in.read(bufferOut)) != -1) {
			out.write(bufferOut, 0, bytes);
		}
		in.close();

		// 结尾部分
		byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线

		out.write(foot);

		out.flush();
		out.close();

		StringBuffer buffer = new StringBuffer();
		BufferedReader reader = null;
		try {
			// 定义BufferedReader输入流来读取URL的响应
			reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				// System.out.println(line);
				buffer.append(line);
			}
			if (result == null) {
				result = buffer.toString();
			}
		} catch (IOException e) {
			System.out.println("发送POST请求出现异常！" + e);
			e.printStackTrace();
			throw new IOException("数据读取异常");
		} finally {
			if (reader != null) {
				reader.close();
			}

		}
		System.out.println(result);
		JsonObject jsonObj = new JsonParser().parse(result).getAsJsonObject();
		String mediaId = jsonObj.get("url").getAsString().replace("\\", "");
		System.out.println(mediaId);
		return mediaId;
	}

}
