package com.hxb.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.google.gson.annotations.SerializedName;

public class DataTableUtil {
	private static DataTableUtil util = null;

	private DataTableUtil() {
	}

	public static DataTableUtil getInstance() {
		if (util == null) {
			util = new DataTableUtil();
		}
		return util;
	}

	/**
	 * 对于服务器来说，可以通过请求参数来获得当前的操作信息。
	 * 
	 * 类型 名称 说明
	 *  int iDisplayStart 显示的起始索引 
	 *  int iDisplayLength 显示的行数 
	 *  int iColumns 显示的列数
	 *  string sSearch 全局搜索字段 
	 *  boolean bEscapeRegex 全局搜索是否正则
	 *  --boolean bSortable_(int) 表示一列是否在客户端被标志位可排序
	 *  --boolean bSearchable_(int) 表示一列是否在客户端被标志位可搜索 
	 *  --string sSearch_(int) 个别列的搜索 
	 *  --boolean bEscapeRegex_(int) 个别列是否使用正则搜索
	 *  int iSortingCols 排序的列数 
	 *  int iSortCol_(int) 被排序的列
	 *  string sSortDir_(int) 排序的方向 "desc" 或者 "asc". 
	 *  string sEcho DataTables 用来生成的信息
	 */
	public DataTableRequestModel getRequestModel(HttpServletRequest request) {
		DataTableRequestModel model = new DataTableRequestModel();
		int iDisplayStart = getInt(request.getParameter("iDisplayStart"));
		int iDisplayLength = getInt(request.getParameter("iDisplayLength"));
		int iColumns = getInt(request.getParameter("iColumns"));
		String sSearch = getStr(request.getParameter("sSearch"));
		boolean bEscapeRegex = getBool(request.getParameter("bEscapeRegex"));
		int iSortingCols = getInt(request.getParameter("iSortingCols"));
		String sEcho = getStr(request.getParameter("sEcho"));
		Map<Integer,String> iSortList = null;
		if(iSortingCols > 0){
			iSortList = getMap(request.getParameterMap(),"sSortDir_");
		}
		model.setbEscapeRegex(bEscapeRegex);
		model.setiColumns(iColumns);
		model.setiDisplayLength(iDisplayLength);
		model.setiDisplayStart(iDisplayStart);
		model.setiSortingCols(iSortingCols);
		model.setiSortList(iSortList);
		model.setsEcho(sEcho);
		model.setsSearch(sSearch);
		return model;
	}
	
	private Map<Integer,String> getMap(Map temp,String beginStr){
		Map<Integer,String> map = new HashMap<Integer,String>();
		String tempStr = null;
		String str = null;
		for(Object obj : temp.keySet()){
			str = (String)obj;
			if(str.indexOf(beginStr) == 0){
				tempStr = str.substring(beginStr.length(),str.length());
				map.put(Integer.parseInt(tempStr), ((String[])temp.get(str))[0]);
				System.out.println(tempStr + "," + ((String[])temp.get(str))[0]);
			}
		}
		return map;
	}
	
	
	private boolean getBool(String str){
		if(str == null || "".equals(str) || "undefined".equals(str)){
			return false;
		}
		return Boolean.parseBoolean(str);
	}
	
	private String getStr(String str){
		if(str == null || "".equals(str) || "undefined".equals(str)){
			return "";
		}
		return str.trim();
	}
	
	
	private Integer getInt(String str){
		if(str == null || "".equals(str) || "undefined".equals(str)){
			return 0;
		}
		return Integer.parseInt(str);
	}

	/**
	 * 服务器应该返回如下的 JSON 格式数据
	 *  类型 名称 说明
	 * int iTotalRecords 实际的行数 
	 * int iTotalDisplayRecords 过滤之后，实际的行数。 
	 * string sEcho 来自客户端 sEcho 的没有变化的复制品，
	 * string sColumns 可选，以逗号分隔的列名，
	   array array mixed aaData
	 * 数组的数组，表格中的实际数据。　　　　
	 * */
	public DataTableResponseModel getResponseModel(int iTotalRecords,
			int iTotalDisplayRecords,String echo,List aaData) {
		DataTableResponseModel model = new DataTableResponseModel();
		model.setAaData(aaData);
		model.setiTotalDisplayRecords(iTotalDisplayRecords);
		model.setiTotalRecords(iTotalRecords);
		model.setsColumns("");
		model.setsEcho(echo);
		return model;
	}

	public class DataTableRequestModel {
		private int iDisplayStart;
		private int iDisplayLength;
		private int iColumns;
		private String sSearch;
		private boolean bEscapeRegex;
		private int iSortingCols;
		private Map<Integer, String> iSortList;
		private String sEcho;

		public int getiDisplayStart() {
			return iDisplayStart;
		}

		public void setiDisplayStart(int iDisplayStart) {
			this.iDisplayStart = iDisplayStart;
		}

		public int getiDisplayLength() {
			return iDisplayLength;
		}

		public void setiDisplayLength(int iDisplayLength) {
			this.iDisplayLength = iDisplayLength;
		}

		public int getiColumns() {
			return iColumns;
		}

		public void setiColumns(int iColumns) {
			this.iColumns = iColumns;
		}

		public String getsSearch() {
			return sSearch;
		}

		public void setsSearch(String sSearch) {
			this.sSearch = sSearch;
		}

		public boolean isbEscapeRegex() {
			return bEscapeRegex;
		}

		public void setbEscapeRegex(boolean bEscapeRegex) {
			this.bEscapeRegex = bEscapeRegex;
		}

		public int getiSortingCols() {
			return iSortingCols;
		}

		public void setiSortingCols(int iSortingCols) {
			this.iSortingCols = iSortingCols;
		}

		public Map<Integer, String> getiSortList() {
			return iSortList;
		}

		public void setiSortList(Map<Integer, String> iSortList) {
			this.iSortList = iSortList;
		}

		public String getsEcho() {
			return sEcho;
		}

		public void setsEcho(String sEcho) {
			this.sEcho = sEcho;
		}
		
		public String show(){
			StringBuffer sb = new StringBuffer();
			sb.append("iDisplayStart:"+iDisplayStart+"\r\n");
			sb.append("iDisplayLength:"+iDisplayLength+"\r\n");
			sb.append("iColumns:"+iColumns+"\r\n");
			sb.append("sSearch:"+sSearch+"\r\n");
			sb.append("bEscapeRegex:"+bEscapeRegex+"\r\n");
			sb.append("iSortingCols:"+iSortingCols+"\r\n");
			sb.append("iSortList.size:"+(iSortList == null ? 0 : iSortList.size())+"\r\n");
			sb.append("sEcho:"+sEcho+"\r\n");
			return sb.toString();
		}

	}

	public class DataTableResponseModel {
		@SerializedName("iTotalRecords")
		private int iTotalRecords;
		@SerializedName("iTotalDisplayRecords")
		private int iTotalDisplayRecords;
		@SerializedName("sEcho")
		private String sEcho;
		@SerializedName("sColumns")
		private String sColumns;
		@SerializedName("aaData")
		private List aaData;

		public int getiTotalRecords() {
			return iTotalRecords;
		}

		public void setiTotalRecords(int iTotalRecords) {
			this.iTotalRecords = iTotalRecords;
		}

		public int getiTotalDisplayRecords() {
			return iTotalDisplayRecords;
		}

		public void setiTotalDisplayRecords(int iTotalDisplayRecords) {
			this.iTotalDisplayRecords = iTotalDisplayRecords;
		}

		public String getsEcho() {
			return sEcho;
		}

		public void setsEcho(String sEcho) {
			this.sEcho = sEcho;
		}

		public String getsColumns() {
			return sColumns;
		}

		public void setsColumns(String sColumns) {
			this.sColumns = sColumns;
		}

		public List getAaData() {
			return aaData;
		}

		public void setAaData(List aaData) {
			this.aaData = aaData;
		}

	}
}
