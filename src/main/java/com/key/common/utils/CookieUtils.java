package com.key.common.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtils {
	/**
	 * 添加cookie
	 *
	 * @param response
	 * @param name
	 *            Cookie的名称，不能为null
	 * @param value
	 *            Cookie的值，默认值空字符串
	 * @param maxAge
	 * @param path
	 *            默认值'/'
	 */
	public static void addCookie(HttpServletResponse response, String name,
			String value, Integer maxAge, String path) {
		if (value == null) {
			value = "";
		}
		if (path == null) {
			path = "/";
		}

		Cookie cookie = new Cookie(name, value);
		cookie.setPath(path);
		if (maxAge != null) {
			cookie.setMaxAge(maxAge);
		}

		response.addCookie(cookie);
	}

	/**
	 * @param request
	 * @param cookieName
	 * @return 指定的cookie
	 */
	public static Cookie getCookie(HttpServletRequest request, String cookieName) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			return null;
		}

		for (Cookie c : cookies) {
			if (c.getName().equals(cookieName)) {
				return c;
			}
		}

		return null;
	}

}
