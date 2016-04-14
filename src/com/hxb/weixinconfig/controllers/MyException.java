package com.hxb.weixinconfig.controllers;

public class MyException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int code;

	public MyException(String msg) {
		super(msg);
	}

	public MyException(int code, String msg) {
		super(msg);
		this.code = code;
	}

	public int getCode() {
		return this.code;
	}
}
