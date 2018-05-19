package com.helloJob.commons.utils;


import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 字符集工具类
 * Author: L.cm
 * Date: 2016年3月29日 下午3:44:52
 */
public class Charsets {

    // 字符集ISO-8859-1
    public static final Charset ISO_8859_1 = StandardCharsets.ISO_8859_1;
    // 字符集GBK
    public static final Charset GBK = Charset.forName("GBK");
    // 字符集utf-8
    public static final Charset UTF_8 = StandardCharsets.UTF_8;

}
