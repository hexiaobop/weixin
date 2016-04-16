package com.hxb.menu;

public class ClickButton extends Button{
	//Click类型菜单key
	private String key;

	public String getKey() {
		return key;
	}
	
	public ClickButton() {
		super();
		this.setType("click");
	}

	public void setKey(String key) {
		this.key = key;
	}
}
