package com.key.common.base.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 
 * @author KeYuan
 * @date 2013下午10:01:45
 *
 */
@Entity
@Table(name = "tracker")
public class Tracker extends IdEntity {

	private String dataType;
	private String dataId;
	private String operation;
	private Date optime = new Date();
	private String loginName;

	public Tracker() {
		super();
	}

	public Tracker(String dataType, String dataId, String operation, String loginName) {
		super();
		this.dataType = dataType;
		this.dataId = dataId;
		this.operation = operation;
		this.loginName = loginName;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getDataId() {
		return dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public Date getOptime() {
		return optime;
	}

	public void setOptime(Date optime) {
		this.optime = optime;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	
}
