package com.helloJob.utils;

import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.ApplicationContext;

import net.sf.ehcache.CacheManager;

public class ApplicationContextUtil  {
	private static ApplicationContext context = null;//声明一个静态变量保存 
	
	public static void setContext(ApplicationContext ac) {
		if(context == null){
			context = ac;
		}else{
			throw new RuntimeException("ApplicationContext 不可重复赋值");
		}
		
	}

	public static ApplicationContext getContext(){ 
		return context; 
	}
	public static CacheManager getCacheManager() {
		return getContext().getBean(EhCacheCacheManager.class).getCacheManager();
	}
}
