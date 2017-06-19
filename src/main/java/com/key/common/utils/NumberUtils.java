package com.key.common.utils;

import java.util.regex.Pattern;

public class NumberUtils {
	// 用正则表达式
	public static boolean isNumeric(String str){ 
	    Pattern pattern = Pattern.compile("[0-9]*"); 
	    return pattern.matcher(str).matches();    
	 } 
}
