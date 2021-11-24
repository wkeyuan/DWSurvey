package net.diaowen.dwsurvey.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import net.diaowen.common.base.entity.IdEntity;

@Entity
@Table(name="t_survey_req_url")
public class SurveyReqUrl extends IdEntity{
	//短ID
	private String sid;
	private String surveyId;
	private Integer reqUrlType = 0;//0 默认主链接,1 特定用户的链接, 2 预览地址
	
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

	public Integer getReqUrlType() {
		return reqUrlType;
	}

	public void setReqUrlType(Integer reqUrlType) {
		this.reqUrlType = reqUrlType;
	}
}
