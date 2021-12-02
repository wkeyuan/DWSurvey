package net.diaowen.dwsurvey.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import net.diaowen.common.base.entity.IdEntity;

/**
 * 答案 是非题结果保存表
 *
 * @author keyuan
 * @date 2012-10-21下午9:26:10
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */
@Entity
@Table(name="t_importError")
public class ImportError extends IdEntity{

    	private String userId;
    	//对应的数据ID
    	private String dbId;
    	//对应的表
    	private String tableName;
	//对应的导入文件名
	private String fileName;
	//对应的行号
	private Integer rowIndex;
	//对应的第一列内容
	private String cell1Value;
	//对应的第二列内容
	private String cell2Value;
	//时间
	private Date createDate=new Date();

	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Integer getRowIndex() {
		return rowIndex;
	}
	public void setRowIndex(Integer rowIndex) {
		this.rowIndex = rowIndex;
	}
	public String getCell1Value() {
		return cell1Value;
	}
	public void setCell1Value(String cell1Value) {
		this.cell1Value = cell1Value;
	}
	public String getCell2Value() {
		return cell2Value;
	}
	public void setCell2Value(String cell2Value) {
		this.cell2Value = cell2Value;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getDbId() {
	    return dbId;
	}
	public void setDbId(String dbId) {
	    this.dbId = dbId;
	}
	public String getTableName() {
	    return tableName;
	}
	public void setTableName(String tableName) {
	    this.tableName = tableName;
	}
	public String getUserId() {
	    return userId;
	}
	public void setUserId(String userId) {
	    this.userId = userId;
	}


}
