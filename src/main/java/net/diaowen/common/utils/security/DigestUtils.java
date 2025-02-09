/**
 * Copyright (c) 2005-2011 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id: DigestUtils.java 799 2009-12-31 15:34:10Z calvinxiu $
 */
package net.diaowen.common.utils.security;

import net.diaowen.common.utils.EncodeUtils;
import net.diaowen.common.utils.ExceptionUtils;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;

/**
 * 支持SHA-1/MD5消息摘要的工具类.
 * 
 * 支持Hex与Base64两种编码方式.
 *
 */
public abstract class DigestUtils {

	private static final String SHA1 = "SHA-1";
	private static final String MD5 = "MD5";

	//-- String Hash function --//
	/**
	 * 对输入字符串进行sha1散列, 返回Hex编码的结果.
	 */
	public static String sha1Hex(String input) {
		byte[] digestResult = digest(input, SHA1);
		return EncodeUtils.encodeHex(digestResult);
	}

	/**
	 * 对输入字符串进行sha1散列, 返回Base64编码的结果.
	 */
	public static String sha1Base64(String input) {
		byte[] digestResult = digest(input, SHA1);
		return EncodeUtils.encodeBase64(digestResult);
	}

	/**
	 * 对输入字符串进行sha1散列, 返回Base64编码的URL安全的结果.
	 */
	public static String sha1Base64UrlSafe(String input) {
		byte[] digestResult = digest(input, SHA1);
		return EncodeUtils.encodeUrlSafeBase64(digestResult);
	}

	/**
	 * 对字符串进行散列, 支持md5与sha1算法.
	 */
	private static byte[] digest(String input, String algorithm) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
			return messageDigest.digest(input.getBytes());
		} catch (GeneralSecurityException e) {
			throw ExceptionUtils.unchecked(e);
		}
	}

	//-- File Hash function --//
	/**
	 * 对文件进行md5散列, 返回Hex编码结果.
	 */
	public static String md5Hex(InputStream input) throws IOException {
		return digest(input, MD5);
	}

	/**
	 * 对文件进行sha1散列, 返回Hex编码结果.
	 */
	public static String sha1Hex(InputStream input) throws IOException {
		return digest(input, SHA1);
	}

	private static String digest(InputStream input, String algorithm) throws IOException {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
			int bufferLength = 1024;
			byte[] buffer = new byte[bufferLength];
			int read = input.read(buffer, 0, bufferLength);

			while (read > -1) {
				messageDigest.update(buffer, 0, read);
				read = input.read(buffer, 0, bufferLength);
			}

			return EncodeUtils.encodeHex(messageDigest.digest());

		} catch (GeneralSecurityException e) {
			throw ExceptionUtils.unchecked(e);
		}
	}

}
