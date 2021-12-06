package net.diaowen.dwsurvey.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;

import net.diaowen.common.base.entity.IdEntity;

/**
 * 题库
 * @author KeYuan
 * @date 2013下午9:46:29
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */
@Entity
@Table(name="t_question_bank")
public class QuestionBank extends IdEntity{

	private String parentId="";
	private String bankName;
	//说明
	private String bankNote;
	//1目录，2题库
	private Integer dirType=1;
	//创建时间
	private Date createDate=new Date();
	//是否显示  1显示 0不显示
	private Integer visibility=1;

	//是用户共享题库还是官方问卷库
	//共享题库 0 官方库   1用户共享
	private Integer bankTag=0;
	//状态 0设计状态  1发布状态
	private Integer bankState=0;
	//创建者
	private String userId;
	//问卷所属的组  功能分组
	private String groupId1;
	//分组2	行业分组
	private String groupId2;
	//题目数
	private Integer quNum=0;
	//引用次数
	private Integer excerptNum=0;

	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public Integer getDirType() {
		return dirType;
	}
	public void setDirType(Integer dirType) {
		this.dirType = dirType;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Integer getVisibility() {
		return visibility;
	}
	public void setVisibility(Integer visibility) {
		this.visibility = visibility;
	}
	public String getGroupId1() {
		return groupId1;
	}
	public void setGroupId1(String groupId1) {
		this.groupId1 = groupId1;
	}
	public String getGroupId2() {
		return groupId2;
	}
	public void setGroupId2(String groupId2) {
		this.groupId2 = groupId2;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Integer getBankTag() {
		return bankTag;
	}
	public void setBankTag(Integer bankTag) {
		this.bankTag = bankTag;
	}
	public String getBankNote() {
		return bankNote;
	}
	public void setBankNote(String bankNote) {
		this.bankNote = bankNote;
	}
	public Integer getBankState() {
		return bankState;
	}
	public void setBankState(Integer bankState) {
		this.bankState = bankState;
	}
	public Integer getQuNum() {
		return quNum;
	}
	public void setQuNum(Integer quNum) {
		this.quNum = quNum;
	}
	public Integer getExcerptNum() {
		return excerptNum;
	}
	public void setExcerptNum(Integer excerptNum) {
		this.excerptNum = excerptNum;
	}

	//用户名
	private String userName;
	@Formula("(select o.name from t_user o where o.id = user_id)")
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

}
