package com.key.dwsurvey.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.key.common.base.entity.IdEntity;

@Entity
@Table(name="t_surveyReqUrl")
public class SurveyReqUrl extends IdEntity{
	//短ID
	private String sid;
	private String surveyId;
	private Integer state=0;//0 默认主链接,1 特定用户的链接
	
	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(String surveyId) {
		this.surveyId = surveyId;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
	
}
