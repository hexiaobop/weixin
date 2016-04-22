package com.hxb.admin.model;
/**
 * 
 * @des    :测试model
 * @author hexiaobo 
 * @email  absod0711@gmail.com
 * @date   2016年4月22日
 */
public class DataTest {
private String name;
private String pass;
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getPass() {
	return pass;
}
public void setPass(String pass) {
	this.pass = pass;
}
public DataTest(String name, String pass) {
	super();
	this.name = name;
	this.pass = pass;
}

}
