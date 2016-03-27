package com.hxb.util.model;
/**
 * 
 * @des    :统一返回数据的model
 * @author hexiaobo 
 * @email  absod0711@gmail.com
 * @date   2016年3月26日
 */
public class ResponseModel {
	private int code ;
	private Object data;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public ResponseModel(int code, Object data) {
		super();
		this.code = code;
		this.data = data;
	}
	public ResponseModel() {
		super();
	}
	
	

}
