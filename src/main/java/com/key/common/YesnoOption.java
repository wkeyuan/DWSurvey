package com.key.common;

/**
 * YesnoOption
 * @author KeYuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://www.dwsurvey.net
 *
 */
public enum YesnoOption {
	
	OPTION1("是","否",0),
	OPTION2("对","错",1),
	OPTION3("正确","错误",2),
	OPTION4("同意","不同意",4),
	OPTION5("满意","不满意",5),
	OPTION6("喜欢","不喜欢",6),
	OPTION7("支持","反对", 7),
	OPTION8("Ture","False", 8), 
	OPTION9("Yes","No",9); 
	
	private String trueValue;
	private String falseValue;
	private int index;
	
	YesnoOption(String trueValue,String falseValue,int index) {
		this.trueValue=trueValue;
		this.falseValue=falseValue;
		this.index=index;
	}

	public String getTrueValue() {
		return trueValue;
	}

	public void setTrueValue(String trueValue) {
		this.trueValue = trueValue;
	}

	public String getFalseValue() {
		return falseValue;
	}

	public void setFalseValue(String falseValue) {
		this.falseValue = falseValue;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
	
}
