package net.diaowen.dwsurvey.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.diaowen.common.base.entity.IdEntity;
import net.diaowen.common.base.entity.User;

/**
 * 具体的一次调查
 * @author keyuan
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */
@Entity
@Table(name="t_survey_answer")
public class SurveyAnswer extends IdEntity{
	//问卷ID
	private String surveyId;
	//回答者ID
	private String userId;
	//回答时间
	private Date bgAnDate;
	//回答时间
	private Date endAnDate=new Date();
	//用时
	private Float totalTime;
	//回答者IP
	private String ipAddr;
	//回答者是详细地址
	private String addr;
	//回答者城市
	private String city;
	//回答者MAC
	private String pcMac;
	//回答的题数
	private Integer quNum;

	/** 回复的此条数据统计信息 **/

	/** 数据--完成情况    是否全部题都回答  **/
	//是否完成  1完成
	private Integer isComplete;
	//回答的题数
	private Integer completeNum;
	//回答的题项目数 ---- 表示有些题下面会有多重回答
	private Integer completeItemNum;

	/** 数据--有效情况   根据设计问卷时指定的必填项 **/
	//是否是有效数据  1有效
	private Integer isEffective;
	private Integer breakType;

	/** 数据--审核情况  **/
	//审核状态  0未处理 1通过 2不通过 100 被修改的原始数据
	private Integer handleState=0;

	/** 不同来源数据 **/
	//数据来源  0网调  1录入数据 2移动数据 3导入数据
	private Integer dataSource=0;

	//省
	private String province;
	//终端信息
	//设备 1=pc 2=m
	private Integer pcm;
	//操作系统
	private Integer sys;
	//浏览器
	private Integer bro;

	// 总分（所有题目之和）
	private Float totalScore;
	//是否已经计算分数 0 未计算 1 计算过
	private Integer totalScoreStatus=0;

	private String pwdCode;
	private String openid;
	//外部参数字段
	private String op;
	//第三方平台答题用户标识
	private String anUserKey;

	//修改用户ID
	private String editUserId;
	//修改的原始SurveyAnswerId
	private String editAnswerId;
	//版本分组ID
	private String versionGroupId;

	//第三方平台标记的 编辑用户ID
	private String thirdEditUid;
	//第三方平台标记的 编辑用户姓名
	private String thirdEditUname;
	//第三方平台标记的 编辑用户ID 是否经过认证 0 或 null 经过认证（通过session会话获取）, 1 未经过认证（通过request获取）
	private Integer thirdEditAuth;
	//保存 0或null 原先默认方式（存在各自的t_an表中） 1 最新只保存surveyAnswerJson中（保存quMaps的JSON信息，不进行解析）
	private Integer answerSaveType;
	//保存到JSON的状态 0=默认状态，未保存  1=已经保存到surveyAnswerJson中（保存quMaps的JSON信息，不进行解析）
	private Integer answerJsonStatus;
	//缓存解析状态信息 0=默认状态，未保存  1=已保存对t_an对象中 2=正在同步中 3=同步异常
	private Integer answerObjStatus=0;
	//保存状态 0或null 正式保存 ，1 暂存
	private Integer answerSaveStatus;

	private String httpSessionId;

	public String getSurveyId() {
		return surveyId;
	}
	public void setSurveyId(String surveyId) {
		this.surveyId = surveyId;
	}
	public Date getBgAnDate() {
		return bgAnDate;
	}
	public void setBgAnDate(Date bgAnDate) {
		this.bgAnDate = bgAnDate;
	}
	public Date getEndAnDate() {
		return endAnDate;
	}
	public void setEndAnDate(Date endAnDate) {
		this.endAnDate = endAnDate;
	}
	public String getIpAddr() {
		return ipAddr;
	}
	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}
	public Integer getQuNum() {
		return quNum;
	}
	public void setQuNum(Integer quNum) {
		this.quNum = quNum;
	}
	public Float getTotalTime() {
		return totalTime;
	}
	public void setTotalTime(Float totalTime) {
		this.totalTime = totalTime;
	}
	public String getPcMac() {
		return pcMac;
	}
	public void setPcMac(String pcMac) {
		this.pcMac = pcMac;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Integer getIsComplete() {
		return isComplete;
	}
	public void setIsComplete(Integer isComplete) {
		this.isComplete = isComplete;
	}
	public Integer getCompleteNum() {
		return completeNum;
	}
	public void setCompleteNum(Integer completeNum) {
		this.completeNum = completeNum;
	}
	public Integer getIsEffective() {
		return isEffective;
	}
	public void setIsEffective(Integer isEffective) {
		this.isEffective = isEffective;
	}
	public Integer getHandleState() {
		return handleState;
	}
	public void setHandleState(Integer handleState) {
		this.handleState = handleState;
	}
	public Integer getDataSource() {
		return dataSource;
	}
	public void setDataSource(Integer dataSource) {
		this.dataSource = dataSource;
	}
	public Integer getCompleteItemNum() {
		return completeItemNum;
	}
	public void setCompleteItemNum(Integer completeItemNum) {
		this.completeItemNum = completeItemNum;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public Integer getPcm() {
		return pcm;
	}

	public void setPcm(Integer pcm) {
		this.pcm = pcm;
	}

	public Integer getSys() {
		return sys;
	}

	public void setSys(Integer sys) {
		this.sys = sys;
	}

	public Integer getBro() {
		return bro;
	}

	public void setBro(Integer bro) {
		this.bro = bro;
	}

	public Float getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(Float totalScore) {
		this.totalScore = totalScore;
	}

	public Integer getTotalScoreStatus() {
		return totalScoreStatus;
	}

	public void setTotalScoreStatus(Integer totalScoreStatus) {
		this.totalScoreStatus = totalScoreStatus;
	}

	public String getPwdCode() {
		return pwdCode;
	}

	public void setPwdCode(String pwdCode) {
		this.pwdCode = pwdCode;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}

	public Integer getBreakType() {
		return breakType;
	}

	public void setBreakType(Integer breakType) {
		this.breakType = breakType;
	}

	public String getAnUserKey() {
		return anUserKey;
	}

	public void setAnUserKey(String anUserKey) {
		this.anUserKey = anUserKey;
	}

	public String getEditUserId() {
		return editUserId;
	}

	public void setEditUserId(String editUserId) {
		this.editUserId = editUserId;
	}

	public String getEditAnswerId() {
		return editAnswerId;
	}

	public void setEditAnswerId(String editAnswerId) {
		this.editAnswerId = editAnswerId;
	}

	public String getVersionGroupId() {
		return versionGroupId;
	}

	public void setVersionGroupId(String versionGroupId) {
		this.versionGroupId = versionGroupId;
	}

	public String getThirdEditUid() {
		return thirdEditUid;
	}

	public void setThirdEditUid(String thirdEditUid) {
		this.thirdEditUid = thirdEditUid;
	}

	public String getThirdEditUname() {
		return thirdEditUname;
	}

	public void setThirdEditUname(String thirdEditUname) {
		this.thirdEditUname = thirdEditUname;
	}

	public Integer getThirdEditAuth() {
		return thirdEditAuth;
	}

	public void setThirdEditAuth(Integer thirdEditAuth) {
		this.thirdEditAuth = thirdEditAuth;
	}

	public Integer getAnswerSaveType() {
		return answerSaveType;
	}

	public void setAnswerSaveType(Integer answerSaveType) {
		this.answerSaveType = answerSaveType;
	}

	public Integer getAnswerJsonStatus() {
		return answerJsonStatus;
	}

	public void setAnswerJsonStatus(Integer answerJsonStatus) {
		this.answerJsonStatus = answerJsonStatus;
	}

	public Integer getAnswerObjStatus() {
		return answerObjStatus;
	}

	public void setAnswerObjStatus(Integer answerObjStatus) {
		this.answerObjStatus = answerObjStatus;
	}

	public Integer getAnswerSaveStatus() {
		return answerSaveStatus;
	}

	public void setAnswerSaveStatus(Integer answerSaveStatus) {
		this.answerSaveStatus = answerSaveStatus;
	}

	public String getHttpSessionId() {
		return httpSessionId;
	}

	public void setHttpSessionId(String httpSessionId) {
		this.httpSessionId = httpSessionId;
	}

	private SurveyDirectory surveyDirectory;
	@Transient
	public SurveyDirectory getSurveyDirectory() {
		return surveyDirectory;
	}
	public void setSurveyDirectory(SurveyDirectory surveyDirectory) {
		this.surveyDirectory = surveyDirectory;
	}

	private WxUserinfo wxUserinfo;
	@Transient
	public WxUserinfo getWxUserinfo() {
		return wxUserinfo;
	}

	public void setWxUserinfo(WxUserinfo wxUserinfo) {
		this.wxUserinfo = wxUserinfo;
	}

	private List<Question> questions;
	@Transient
	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	private SurveyAnswerJson surveyAnswerJson;

	@Transient
	public SurveyAnswerJson getSurveyAnswerJson() {
		return surveyAnswerJson;
	}

	public void setSurveyAnswerJson(SurveyAnswerJson surveyAnswerJson) {
		this.surveyAnswerJson = surveyAnswerJson;
	}

	private User answerUser;
	@Transient
	public User getAnswerUser() {
		return answerUser;
	}

	public void setAnswerUser(User answerUser) {
		this.answerUser = answerUser;
	}

}
