package com.helloJob.test;

import java.io.InputStream;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class EhcacheTest {

	public static void main(String[] args) {
		InputStream in = EhcacheTest.class.getClassLoader().getResourceAsStream("xml/ehcache.xml");
        CacheManager cacheManager = CacheManager.create(in);  
        cacheManager.addCache("hello");
		Cache cache = cacheManager.getCache("hello");
		 final String key = "greeting";  
	        final String key1 = "greeting1";
	        //创建一个数据容器来存放我们所创建的element  
	        final Element putGreeting = new Element(key, "Hello, World!");  
	         Element putGreeting1 = new Element(key1, "Hello Ehcache");
	        
	        //将数据放入到缓存实例中  
	        cache.put(putGreeting);  
	        cache.put(putGreeting1);
	        System.out.println(cache.get("greeting1"));
	        Element putGreeting2 = new Element(key1, "Hello Ehcache...");
	        cache.put(putGreeting2);
	        System.out.println(cache.get("greeting1"));
	}

}
