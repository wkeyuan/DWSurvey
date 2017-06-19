package com.key.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class RandomUtils {

	public static void main(String[] args) {
		System.out.println(randomWord(5, 12));;
		System.out.println(randomStr(5,12));;
		/*for (int i = 0; i < 1000; i++) {
			System.out.println(randomWordNum(5));;
		}*/
		/*ChineseNameUtils chineseName = new ChineseNameUtils();
		for (int i = 0; i < 1000; i++) {
			chineseName.getName();
		    String names=chineseName.getNames();
		    String pids=chineseName.getPid();
		    System.out.println(names+":"+pids);
		    System.out.println(randomNum(2,4));
//			 System.out.println(pids.replace(",", ""));
		}*/
//		for (int i = 0; i < 100; i++) {
//			String randomStr = randomWord();
//			System.out.println(randomStr);
//		}
	}
	
	public static String randomWord(int min,int max) {
		String randomStr = "";
		int ranNum = randomInt(min, max);
		for (int i = 0; i < ranNum; i++) {
			char c = 'a';
			c = (char) (c + (int) (Math.random() * 26));
			// System.out.println(c);
			randomStr += c;
		}
		return randomStr;
	}
	
	public static String randomNum(int minSize,int maxSize){
		String randNums="";
		int temp=randomInt(minSize,maxSize);
		for (int i = 0; i <temp ; i++) {
			randNums+=random(0, 10);
		}
		return randNums;
	}
	
	public static String randomWordNum(int max) {
		int ranNum = randomInt(1, max);
		int ranWord=max-ranNum;
		String randomNum=randomNum(ranNum, ranNum);
		String randomWord=randomWord(ranWord, ranWord);
		return randomNum+randomWord;
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
	 
	   private static long random(long begin, long end) {
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

}
