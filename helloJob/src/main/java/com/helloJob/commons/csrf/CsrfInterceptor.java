package com.helloJob.commons.csrf;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * Csrf拦截器，用来生成或去除CsrfToken
 * 
 * @author L.cm
 */
public class CsrfInterceptor extends HandlerInterceptorAdapter {
	private static final Logger logger = LogManager.getLogger(CsrfInterceptor.class);
	
	@Autowired 
	private CsrfTokenRepository csrfTokenRepository;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		// 非控制器请求直接跳出
		if (!(handler instanceof HandlerMethod)) {
			return true;
		}
		CsrfToken csrfToken = handlerMethod.getMethodAnnotation(CsrfToken.class);
		// 判断是否含有@CsrfToken注解
		if (null == csrfToken) {
			return true;
		}
		// create、remove同时为true时异常
		if (csrfToken.create() && csrfToken.remove()) {
			logger.error("CsrfToken attr create and remove can Not at the same time to true!");
			return renderError(request, response, "CsrfToken attr create and remove can Not at the same time to true!");
		}
		// 创建
		if (csrfToken.create()) {
			CsrfTokenBean token = csrfTokenRepository.generateToken(request);
			csrfTokenRepository.saveToken(token, request, response);
			request.setAttribute(token.getParameterName(), token);
			return true;
		}
		// 校验，并且清除
		CsrfTokenBean tokenBean = csrfTokenRepository.loadToken(request);
		if (tokenBean == null) {
			return renderError(request, response, "CsrfToken is null!");
		}
		String actualToken = request.getHeader(tokenBean.getHeaderName());
		if (actualToken == null) {
			actualToken = request.getParameter(tokenBean.getParameterName());
		}
		if (!tokenBean.getToken().equals(actualToken)) {
			return renderError(request, response, "CsrfToken not eq!");
		}
		return true;
	}
	
	private boolean renderError(HttpServletRequest request, HttpServletResponse response, 
			String message) throws IOException {
		// 非ajax CsrfToken校验异常，先清理token
		csrfTokenRepository.saveToken(null, request, response);
		throw new RuntimeException(message);
	}
	
	/**
	 * 用于清理@CsrfToken保证只能请求成功一次
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		// 非控制器请求直接跳出
		if (!(handler instanceof HandlerMethod)) {
			return;
		}
		CsrfToken csrfToken = handlerMethod.getMethodAnnotation(CsrfToken.class);
		if (csrfToken == null || !csrfToken.remove()) {
			return;
		}
		csrfTokenRepository.saveToken(null, request, response);
	}

}
