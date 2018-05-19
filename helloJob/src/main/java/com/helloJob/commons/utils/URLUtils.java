package com.helloJob.commons.utils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 * url处理工具类
 * @author L.cm
 */
public class URLUtils extends org.springframework.web.util.UriUtils {

	/**
	 * url 编码
	 * @param source url
	 * @param charset 字符集
	 * @return 编码后的url
	 */
	public static String encodeURL(String source, Charset charset) {
		try {
			return URLUtils.encode(source, charset.name());
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}


}
