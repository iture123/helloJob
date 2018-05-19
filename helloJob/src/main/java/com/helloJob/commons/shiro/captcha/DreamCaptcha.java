package com.helloJob.commons.shiro.captcha;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.helloJob.commons.utils.StringUtils;
import com.helloJob.commons.utils.WebUtils;

/**
 * 如梦验证码
 * @author L.cm
 *
 */
public class DreamCaptcha implements InitializingBean {
	private final static Logger logger = LogManager.getLogger(DreamCaptcha.class);
	private static final String DEFAULT_COOKIE_NAME = "dream-captcha";
	private final static String DEFAULT_CHACHE_NAME = "dreamCaptchaCache";
	private final static int DEFAULT_MAX_AGE = -1; // cookie超时默认为session会话状态
	
	private CacheManager cacheManager;
	private String cacheName;
	private String cookieName;
	
	private Cache<String, String> dreamCaptchaCache;
	
	public DreamCaptcha() {
		this.cacheName = DEFAULT_CHACHE_NAME;
		this.cookieName = DEFAULT_COOKIE_NAME;
	}
	
	public DreamCaptcha(CacheManager cacheManager) {
		this();
		this.cacheManager = cacheManager;
	}
	
	public CacheManager getCacheManager() {
		return cacheManager;
	}
	
	public void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}
	
	public String getCacheName() {
		return cacheName;
	}
	
	public void setCacheName(String cacheName) {
		this.cacheName = cacheName;
	}
	
	public String getCookieName() {
		return cookieName;
	}

	public void setCookieName(String cookieName) {
		this.cookieName = cookieName;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(cacheManager, "cacheManager must not be null!");
		Assert.hasText(cacheName, "cacheName must not be empty!");
		Assert.hasText(cookieName, "cookieName must not be empty!");
		this.dreamCaptchaCache = cacheManager.getCache(cacheName);
	}
	
	/**
	 * 生成验证码
	 */
	public void generate(HttpServletRequest request, HttpServletResponse response) {
		// 先检查cookie的uuid是否存在
		String cookieValue = WebUtils.getCookieValue(request, cookieName);
		boolean hasCookie = true;
		if (StringUtils.isBlank(cookieValue)) {
			hasCookie = false;
			cookieValue = StringUtils.getUUId();
		}
		String captchaCode = CaptchaUtils.generateCode().toUpperCase();// 转成大写重要
		// 不存在cookie时设置cookie
		if (!hasCookie) {
			WebUtils.setCookie(response, cookieName, cookieValue, DEFAULT_MAX_AGE);
		}
		// 生成验证码
		CaptchaUtils.generate(response, captchaCode);
		dreamCaptchaCache.put(cookieValue, captchaCode);
	}
	
	/**
	 * 仅能验证一次，验证后立即删除
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param userInputCaptcha 用户输入的验证码
	 * @return 验证通过返回 true, 否则返回 false
	 */
	public boolean validate(HttpServletRequest request, HttpServletResponse response, String userInputCaptcha) {
		if (logger.isDebugEnabled()) {
			logger.debug("validate captcha userInputCaptcha is " + userInputCaptcha);
		}
		String cookieValue = WebUtils.getCookieValue(request, cookieName);
		if (StringUtils.isBlank(cookieValue)) {
			return false;
		}
		String captchaCode = dreamCaptchaCache.get(cookieValue);
		if (StringUtils.isBlank(captchaCode)) {
			return false;
		}
		// 转成大写重要
		userInputCaptcha = userInputCaptcha.toUpperCase();
		boolean result = userInputCaptcha.equals(captchaCode);
		if (result) {
			dreamCaptchaCache.remove(cookieValue);
			WebUtils.removeCookie(response, cookieName);
		}
		return result;
	}
}
