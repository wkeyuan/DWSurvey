/**
 * Copyright (c) 2005-2011 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 * $Id: ObjectMapper.java 1627 2011-05-23 16:23:18Z calvinxiu $
 */
package net.diaowen.common.plugs.mapper;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.lang.StringUtils;
import org.dozer.DozerBeanMapper;

import com.google.common.collect.Lists;
import net.diaowen.common.utils.ReflectionUtils;

/**
 * 对象转换工具类.
 * 1.封装Dozer, 深度转换对象到对象
 * 2.封装Apache Commons BeanUtils, 将字符串转换为对象.
 *
 */
public abstract class ObjectMapper {

	/**
	 * 持有Dozer单例, 避免重复创建DozerMapper消耗资源.
	 */
	private static DozerBeanMapper dozer = new DozerBeanMapper();

	/**
	 * 基于Dozer转换对象的类型.
	 */
	public static <T> T map(Object source, Class<T> destinationClass) {
		return dozer.map(source, destinationClass);
	}

	/**
	 * 基于Dozer转换Collection中对象的类型.
	 */
	public static <T> List<T> mapList(Collection sourceList, Class<T> destinationClass) {
		List<T> destinationList = Lists.newArrayList();
		for (Object sourceObject : sourceList) {
			T destinationObject = dozer.map(sourceObject, destinationClass);
			destinationList.add(destinationObject);
		}
		return destinationList;
	}

	static {
		//初始化日期格式为yyyy-MM-dd 或 yyyy-MM-dd HH:mm:ss
		registerDateConverter("yyyy-MM-dd,yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 定义Apache BeanUtils日期Converter的格式,可注册多个格式,以','分隔
	 */
	public static void registerDateConverter(String patterns) {
		DateConverter dc = new DateConverter();
		dc.setUseLocaleFormat(true);
		dc.setPatterns(StringUtils.split(patterns, ","));
		ConvertUtils.register(dc, Date.class);
	}

	/**
	 * 基于Apache BeanUtils转换字符串到相应类型.
	 *
	 * @param value 待转换的字符串.
	 * @param toType 转换目标类型.
	 */
	public static Object convertToObject(String value, Class<?> toType) {
		Object cvt_value=null;
		try {
				cvt_value=ConvertUtils.convert(value, toType);
//			if(toType==Date.class){
//				SimpleDateFormat dateFormat=null;
//				try{
//					dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//					cvt_value=dateFormat.parse(value);
//				}catch(Exception e){
//					dateFormat=new SimpleDateFormat("yyyy-MM-dd");
//					cvt_value=dateFormat.parse(value);
//				}
//			}
		} catch (Exception e) {
			throw ReflectionUtils.convertReflectionExceptionToUnchecked(e);
		}
		return cvt_value;
	}

}
