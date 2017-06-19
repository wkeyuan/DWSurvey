package com.key.common;

public enum CheckType {
	NO("无验证", 0), 
	EMAIL("Email", 1), 
	STRLEN("字符长度", 2), 
	UNSTRCN("禁止中文", 3),
	STRCN("仅许中文", 4),
	NUM("数值", 5),
	TELENUM("电话号码", 6),
	PHONENUM("手机号码", 7),
	DATE("日期", 8),
	IDENTCODE("身份证号", 9),
	ZIPCODE("邮政编码", 10),
	URL("网址", 11),
	TELE_PHONE_NUM("电话或手机号", 12);
	
	private String name;
	private int index;
	private CheckType(String name,int index){
		this.name=name;
		this.index=index;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	
	
}