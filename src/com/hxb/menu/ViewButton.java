package com.hxb.menu;

public class ViewButton extends Button{
	//view类型菜单url
	private String url;

	public String getUrl() {
		return url;
	}
	

	public ViewButton() {
		super.setType("view");
	}


	public void setUrl(String url) {
		this.url = url;
		
	}
	
}
