package net.diaowen.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class RandomUtils {

	public static void main(String[] args) throws Exception{

	}

	public static String randomWord(int min,int max) {
		String randomStr = "";
		int ranNum = randomInt(min, max);
		for (int i = 0; i < ranNum; i++) {
			char c = 'a';
			c = (char) (c + (int) (Math.random() * 26));
			randomStr += c;
		}
		return randomStr;
	}

	public static String randomWordNum(int max) {

		int ranNum = randomInt(1, max);
		int ranWord=max-ranNum;
		String randomWord=randomWord(ranWord, ranWord);
		if(max>3){
			String randomNum=random(ranNum-2, ranNum)+"";
			return randomNum+randomWord;
		}
		return randomWord;
	}

	public static int randomInt(int minNum, int maxNum) {
		Random random = new Random();
		int randomInt = random.nextInt(maxNum);
		if (randomInt < minNum) {
			return minNum;
		}
		return randomInt;
	}

	  public static Date randomDate(String beginDate, String endDate) {
	       try {
	           SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	           Date start = format.parse(beginDate);// 开始日期
	           Date end = format.parse(endDate);// 结束日期
	           if (start.getTime() >= end.getTime()) {
	               return null;
	           }
	           long date = random(start.getTime(), end.getTime());

	           return new Date(date);
	       } catch (Exception e) {
	           e.printStackTrace();
	       }
	       return null;
	   }

	   public static long random(long begin, long end) {
			if((begin+2)>=end){
				begin = end-2;
			}
		   long rtnn = begin + (long) (Math.random() * (end - begin));
		   if (rtnn == begin || rtnn == end) {
			   return random(begin, end);
		   }
		   return rtnn;
	   }


	  public static String randomStr(int minLen,int maxLen){
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		int length=random.nextInt(maxLen-minLen)+minLen;
		for (int i = 0; i < length; i++) {
		        int number = random.nextInt(base.length());
		        sb.append(base.charAt(number));
		 }
		 return sb.toString();
	  }

	public static String buildOrderCode(){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMddHsSSS");
		String dateFormat = simpleDateFormat.format(new Date());
//		return dateFormat+RandomUtils.randomNum(4,4);
//		dateFormat = RandomUtils.randomWord(5,5).toUpperCase();
		return dateFormat+"-"+RandomUtils.random(100000l,999999l);
	}

	public static long getVerifyCode() {
		return random(100000l,999999l);
	}

	public static String buildCodeyyMMdd(){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMddHHmmsSSS");
		String dateFormat = simpleDateFormat.format(new Date());
//		return dateFormat+RandomUtils.randomNum(4,4);
//		dateFormat = RandomUtils.randomWord(5,5).toUpperCase();
		return dateFormat+"-"+RandomUtils.getVerifyCode();
//		20150806125346
	}

	public static String buildCodeyyMMdd32(){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMddHHmmssSSS");
		String dateFormat = simpleDateFormat.format(new Date());
//		return dateFormat+RandomUtils.randomNum(4,4);
//		dateFormat = RandomUtils.randomWord(5,5).toUpperCase();
		return dateFormat+"-"+RandomUtils.getVerifyCode();
//		yyMMdd HHmmss SSS 15
	}

	public static String buildOrderNo(){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMddHHmmsSSS");
		String dateFormat = simpleDateFormat.format(new Date());
//		return dateFormat+RandomUtils.randomNum(4,4);
//		dateFormat = RandomUtils.randomWord(5,5).toUpperCase();
		return dateFormat+RandomUtils.getVerifyCode();
//		20150806125346
	}

	public static String buildEntCode() {
		return randomWord(4,6);
	}

	public static String buildAppKey() {
		return randomWord(8,10)+randomStr(8,10);
	}

	public static String buildAppSecret() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmsSSS");
		String dateFormat = simpleDateFormat.format(new Date());
		return randomWord(8,10)+System.currentTimeMillis();
	}

}
