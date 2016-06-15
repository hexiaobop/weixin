package com.hxb.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import net.paoding.rose.load.context.RoseAppContext;

/***
 * 
 * @author hexiaobo
 *
 */
public class BeanFactory implements ApplicationContextAware {
    
    // Spring应用上下文环境  
    private static ApplicationContext applicationContext;  
    /** 
     * 实现ApplicationContextAware接口的回调方法，设置上下文环境 
     *  
     * @param applicationContext 
     */  
    public void setApplicationContext(ApplicationContext applicationContext) {  
        this.applicationContext = applicationContext;  
    }  
    /** 
     * @return ApplicationContext 
     */  
    public static ApplicationContext getApplicationContext() {  
        return applicationContext;  
    }  
    /** 
     * 获取对象 
     *  
     * @param name 
     * @return Object
     * @throws BeansException 
     */  
    public static Object getBean(String name) throws BeansException {  
        return applicationContext.getBean(name);  
    }  
    
  public static <T> T getBean(final Class<T> clazz) {
	//逐个打印出spring自动装配的bean。根据我的测试，类名第一个字母小写即bean的名字  
	for(int i=0;i<applicationContext.getBeanDefinitionCount();i++){  
	    System.out.println( applicationContext.getBeanDefinitionNames()[i]);  
	}
    return clazz.cast(BeanFactoryUtils.beanOfTypeIncludingAncestors(applicationContext, clazz));
}
}

