package com.hxb.menu;

import com.AppConstants;
import com.google.gson.Gson;
import com.hxb.util.HttpClientUtils;
import com.hxb.util.MyException;
import com.hxb.util.WeixinConfigUtil;

/**
 * 
 * @des :工具类 自定义菜单创建
 * @author hexiaobo
 * @email absod0711@gmail.com
 * @date 2016年4月17日
 */
public class CreateMenu {
	/**
	 * 
	 * @return 自定义菜单
	 */
	public static Menu createMenu() {
		Menu menu = new Menu();
		String[][][] menuString = AppConstants.MENU;
		// 循环创建菜单
		Button[] buttonMenu = new Button[menuString.length];
		for (int i = 0; i < menuString.length; i++) {
			Button button = new Button();
			String[][] firstMenu = menuString[i];
			Button[] buttonArraySecond = new Button[firstMenu.length - 1];
			for (int j = 0; j < firstMenu.length; j++) {
				String[] everyMenu = firstMenu[j];
				if (j == 0) {
					String firstButtonName = everyMenu[0];
					System.out.println(firstButtonName);
					button.setName(firstButtonName);
					continue;
				}
				System.out.println(j);
				String buttonType = everyMenu[0];
				String buttonName = everyMenu[1];
				String buttonKeyOrUrl = everyMenu[2];
				Button tempButton = createEveryMenu(buttonType, buttonName, buttonKeyOrUrl);
				buttonArraySecond[j - 1] = tempButton;

			}
			button.setSub_button(buttonArraySecond);
			buttonMenu[i] = button;
		}
		menu.setButton(buttonMenu);
		return menu;

	}

	/**
	 * 测试
	 * @param args
	 * @throws MyException
	 */
	public static void main(String[] args) throws MyException {
		String token = WeixinConfigUtil.getAccessToken();
		String url = AppConstants.CREATE_MENU_URL.replace("ACCESS_TOKEN", token);
		System.out.println(url);
		Gson gson = new Gson();
		String paramStr = gson.toJson(createMenu());
		String resultJson = HttpClientUtils.sendPost("", url, paramStr);
		System.out.println(resultJson);

	}

	/**
	 * 通过传入属性创建按钮
	 * 
	 * @param buttonType
	 * @param buttonName
	 * @param buttonKeyOrUrl
	 * @return 暂时返回两种类型按钮
	 */
	public static Button createEveryMenu(String buttonType, String buttonName, String buttonKeyOrUrl) {
		if (buttonType.equals("view")) {
			ViewButton viewButton = new ViewButton();
			viewButton.setUrl(buttonKeyOrUrl);
			viewButton.setName(buttonName);
			return viewButton;
		} else if (buttonType.equals("click")) {
			ClickButton clickButton = new ClickButton();
			clickButton.setKey(buttonKeyOrUrl);
			clickButton.setName(buttonName);
			return clickButton;
		} else {
			return null;
		}
	}

}
