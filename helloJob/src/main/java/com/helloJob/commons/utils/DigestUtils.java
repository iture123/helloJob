package com.helloJob.commons.utils;

import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * 加密相关工具类直接使用Spring util封装，减少jar依赖
 * @author L.cm
 */
public class DigestUtils extends org.springframework.util.DigestUtils {

    /**
     * Calculates the MD5 digest and returns the value as a 32 character hex string.
     *
     * @param data Data to digest
     * @return MD5 digest as a hex string
     */
    public static String md5Hex(final String data) {
        return DigestUtils.md5DigestAsHex(data.getBytes(Charsets.UTF_8));
    }
    
    /**
     * Return a hexadecimal string representation of the MD5 digest of the given bytes.
     * @param bytes the bytes to calculate the digest over
     * @return a hexadecimal digest string
     */
    public static String md5Hex(final byte[] bytes) {
        return DigestUtils.md5DigestAsHex(bytes);
    }
    
    /**
     * 使用shiro的hash方式
     * @param algorithmName 算法
     * @param source 源对象
     * @param salt 加密盐
     * @param hashIterations hash次数
     * @return 加密后的字符
     */
    public static String hashByShiro(String algorithmName, Object source, Object salt, int hashIterations) {
        return new SimpleHash(algorithmName, source, salt, hashIterations).toHex();
    }
    
}
