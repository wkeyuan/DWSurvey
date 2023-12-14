package net.diaowen.dwsurvey.entity;

import net.diaowen.common.base.entity.IdEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/**
 * 具体的一次问卷调查的记录
 *
 * @author keyuan
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */
@Entity
@Table(name="t_survey_answer")
public class SurveyAnswer extends IdEntity{
	/**
	 * 问卷ID
	 */
	private String surveyId;
	/**
	 * 回答者ID
	 */
	private String userId;
	/**
	 * 回答的开始时间
	 */
	private Date bgAnDate;
	/**
	 * 回答的结束时间
	 */
	private Date endAnDate=new Date();
	/**
	 * 回答的用时
	 */
	private Float totalTime;
	/**
	 * 回答者的IP地址
	 */
	private String ipAddr;
	/**
	 * 回答者的详细地址
	 */
	private String addr;
	/**
	 * 回答者所在的城市
	 */
	private String city;
	/**
	 * 回答者的MAC地址
	 */
	private String pcMac;
	/**
	 * 本次调查问卷的总题数
	 */
	private Integer quNum;
	/**
	 * 是否完成全部问卷  1表示完成
	 */
	private Integer isComplete;
	/**
	 * 回答的题数
	 */
	private Integer completeNum;
	/**
	 * 回答的题项目数 ---- 表示有些题下面会有多重回答
	 */
	private Integer completeItemNum;
	/**
	 * 根据设计问卷时指定的必填项判断,是否是有效数据  1表示有效
	 */
	private Integer isEffective;
	/**
	 * 问卷填写记录的审核状态  0表示未处理 1表示通过 2表示不通过
	 */
	private Integer handleState=0;
	/**
	 * 问卷记录的数据来源  0表示网调  1表示录入数据 2表示移动数据 3表示导入数据
	 */
	private Integer dataSource=0;
	/**
	 * 问卷的目录及问卷
	 */
	private SurveyDirectory surveyDirectory;

	/**
	 * 获取问卷的ID
	 *
	 * @return 问卷的ID
	 */
	public String getSurveyId() {
		return surveyId;
	}

	/**
	 * 设置问卷的ID
	 *
	 * @param surveyId 新的问卷的ID
	 */
	public void setSurveyId(String surveyId) {
		this.surveyId = surveyId;
	}

	/**
	 * 获取本次调查问卷填写的开始时间
	 *
	 * @return 本次调查问卷填写的开始时间
	 */
	public Date getBgAnDate() {
		return bgAnDate;
	}

	/**
	 * 设置本次调查问卷填写的开始时间
	 *
	 * @param bgAnDate 新的本次调查问卷填写的开始时间
	 */
	public void setBgAnDate(Date bgAnDate) {
		this.bgAnDate = bgAnDate;
	}

	/**
	 * 获取本次调查问卷填写的结束时间
	 *
	 * @return 本次调查问卷填写的结束时间
	 */
	public Date getEndAnDate() {
		return endAnDate;
	}

	/**
	 * 设置本次调查问卷填写的结束时间
	 *
	 * @param endAnDate 新的本次调查问卷填写的结束时间
	 */
	public void setEndAnDate(Date endAnDate) {
		this.endAnDate = endAnDate;
	}

	/**
	 * 获取本次调查文件填写者的IP地址
	 *
	 * @return 本次调查文件填写者的IP地址
	 */
	public String getIpAddr() {
		return ipAddr;
	}

	/**
	 * 设置本次调查文件填写者的IP地址
	 *
	 * @param ipAddr 新的本次调查文件填写者的IP地址
	 */
	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	/**
	 * 获取本次调查问卷的总题数
	 *
	 * @return 本次调查问卷的总题数
	 */
	public Integer getQuNum() {
		return quNum;
	}

	/**
	 * 设置本次调查问卷的总题数
	 *
	 * @param quNum 新的本次调查问卷的总题数
	 */
	public void setQuNum(Integer quNum) {
		this.quNum = quNum;
	}

	/**
	 * 获取本次调查问卷所用的总时间
	 *
	 * @return 本次调查问卷所用的总时间
	 */
	public Float getTotalTime() {
		return totalTime;
	}

	/**
	 * 设置本次调查问卷所用的总时间
	 *
	 * @param totalTime 新的本次调查问卷所用的总时间
	 */
	public void setTotalTime(Float totalTime) {
		this.totalTime = totalTime;
	}

	/**
	 * 获取回答者的MAC地址
	 *
	 * @return 回答者的MAC地址
	 */
	public String getPcMac() {
		return pcMac;
	}

	/**
	 * 设置回答者的MAC地址
	 *
	 * @param pcMac 新的回答者的MAC地址
	 */
	public void setPcMac(String pcMac) {
		this.pcMac = pcMac;
	}

	/**
	 * 获取回答者的详细地址
	 *
	 * @return 回答者的详细地址
	 */
	public String getAddr() {
		return addr;
	}

	/**
	 * 设置回答者的详细地址
	 *
	 * @param addr 新的回答者的详细地址
	 */
	public void setAddr(String addr) {
		this.addr = addr;
	}

	/**
	 * 获取回答者所在的城市
	 *
	 * @return 回答者所在的城市
	 */
	public String getCity() {
		return city;
	}

	/**
	 * 设置回答者所在的城市
	 *
	 * @param city 新的回答者所在的城市
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * 获取是否完成全部问题的标记
	 *
	 * @return 获取是否完成全部问题的标记
	 */
	public Integer getIsComplete() {
		return isComplete;
	}

	/**
	 * 设置是否完成全部问题的标记
	 *
	 * @param isComplete 新的是否完成全部问题的标记
	 */
	public void setIsComplete(Integer isComplete) {
		this.isComplete = isComplete;
	}

	/**
	 * 获取本次问卷的填写记录中完成的题数
	 *
	 * @return 本次问卷的填写记录中完成的题数
	 */
	public Integer getCompleteNum() {
		return completeNum;
	}

	/**
	 * 设置本次问卷的填写记录中完成的题数
	 *
	 * @param completeNum 新的本次问卷的填写记录中完成的题数
	 */
	public void setCompleteNum(Integer completeNum) {
		this.completeNum = completeNum;
	}

	/**
	 * 获取本次问卷填写记录是否有效的标记
	 *
	 * @return 本次问卷填写记录是否有效的标记
	 */
	public Integer getIsEffective() {
		return isEffective;
	}

	/**
	 * 设置本次问卷填写记录是否有效的标记
	 *
	 * @param isEffective 新的本次问卷填写记录是否有效的标记
	 */
	public void setIsEffective(Integer isEffective) {
		this.isEffective = isEffective;
	}

	/**
	 * 获取该问卷填写记录的审核状态
	 *
	 * @return 该文件填写记录的审核状态, 0表示未处理 1表示通过 2表示不通过
	 */
	public Integer getHandleState() {
		return handleState;
	}

	/**
	 * 设置该问卷填写记录的审核状态, 0表示未处理 1表示通过 2表示不通过
	 *
	 * @param handleState 新的该问卷填写记录的审核状态, 0表示未处理 1表示通过 2表示不通过
	 */
	public void setHandleState(Integer handleState) {
		this.handleState = handleState;
	}

	/**
	 * 获取该问卷记录的数据来源, 0表示网调  1表示录入数据 2表示移动数据 3表示导入数据
	 *
	 * @return 该问卷记录的数据来源, 0表示网调  1表示录入数据 2表示移动数据 3表示导入数据
	 */
	public Integer getDataSource() {
		return dataSource;
	}

	/**
	 * 设置该问卷记录的数据来源, 0表示网调  1表示录入数据 2表示移动数据 3表示导入数据
	 *
	 * @param dataSource 新的该问卷记录的数据来源, 0表示网调  1表示录入数据 2表示移动数据 3表示导入数据
	 */
	public void setDataSource(Integer dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * 获取回答的题的项目数
	 *
	 * @return 回答的题的项目数
	 */
	public Integer getCompleteItemNum() {
		return completeItemNum;
	}

	/**
	 * 设置回答的题的项目数
	 *
	 * @param completeItemNum 新的回答的题的项目数
	 */
	public void setCompleteItemNum(Integer completeItemNum) {
		this.completeItemNum = completeItemNum;
	}

	/**
	 * 获取回答者的ID
	 *
	 * @return 回答者的ID
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * 设置回答者的ID
	 *
	 * @param userId 回答者的新ID
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * 获取问卷的目录及问卷
	 *
	 * @return 问卷的目录及问卷
	 */
	@Transient
	public SurveyDirectory getSurveyDirectory() {
		return surveyDirectory;
	}

	/**
	 * 设置问卷的目录及问卷
	 *
	 * @param surveyDirectory 新的问卷的目录及问卷
	 */
	public void setSurveyDirectory(SurveyDirectory surveyDirectory) {
		this.surveyDirectory = surveyDirectory;
	}
}
