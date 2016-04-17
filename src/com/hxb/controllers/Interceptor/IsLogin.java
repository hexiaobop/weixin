package com.hxb.controllers.Interceptor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 
 * @des    :判断是否授权的注解
 * @author hexiaobo 
 * @email  absod0711@gmail.com
 * @date   2016年4月17日
 */
//元注解 
//用于类
@Target({ElementType.TYPE,ElementType.METHOD})
//运行时有效
@Retention(RetentionPolicy.RUNTIME)
//继承
@Inherited
//可以被例如javadoc此类的工具文档化
@Documented
public @interface IsLogin {
	

}
