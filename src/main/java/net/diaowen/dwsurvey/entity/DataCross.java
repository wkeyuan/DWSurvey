package net.diaowen.dwsurvey.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 答案 是非题结果保存表
 *
 * @author keyuan
 * @date 2012-10-21下午9:26:10
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */
public class DataCross {
	
	
	private String optionName;
	
	private int count;
	
//	private int colCount;
	
//	private int rowCount;
	
	private List<DataCross> colDataCrosss=new ArrayList<DataCross>();

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getOptionName() {
		return optionName;
	}

	public void setOptionName(String optionName) {
		this.optionName = optionName;
	}

	public List<DataCross> getColDataCrosss() {
		return colDataCrosss;
	}

	public void setColDataCrosss(List<DataCross> colDataCrosss) {
		this.colDataCrosss = colDataCrosss;
	}
	
	
	
}
