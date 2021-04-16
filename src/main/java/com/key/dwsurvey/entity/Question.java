package com.key.dwsurvey.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.key.common.base.entity.IdEntity;
import com.key.common.YesnoOption;
import com.key.common.CheckType;
import com.key.common.QuType;

/**
 *
 * @author keyuan
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */
@Entity
@Table(name="t_question")
public class Question extends IdEntity{

	//所属问卷或题库
	private String belongId;
	//题目名称
	private String quName;
	// 题干
	private String quTitle;
	// 题目说明
	private String quNote;
	// 关键字
	private String keywords;
	// 题目类型
	private QuType quType;
	// 标记     1题库中的题   2问卷中的题
	private Integer tag;
	// 排序ID
	private Integer orderById;
	// 创建时间
	private Date createDate=new Date();
	// 是否是大小题    1默认题  2大题  3大题下面的小题
	private Integer quTag=1;
	// 所属大题  只有小题才有此属性 即quTag=3的题
	private String parentQuId;
	// 是非题的选项
	private YesnoOption yesnoOption;
	// 是否必答 0非必答 1必答
	private Integer isRequired=0;
	//说明的验证方式
	private CheckType checkType;
	// 枚举题 枚举项数目 ,评分题起始分值
	private Integer paramInt01 = 3;
	//评分题，最大分值
	private Integer paramInt02 = 10;
	//是否显示 0不显示   1显示
	private Integer visibility=1;

	//如果是复制的题，则有复制于那一题
	public String copyFromId;

	//控制性属性
	//1水平显示 2垂直显示
	private Integer hv=2;
	//选项随机排列  1随机排列 0不随机排列
	private Integer randOrder=0;
	//按列显示时，列数
	private Integer cellCount=0;

	//联系人属性
	//1关联到联系人属性  0不关联到联系人属性
	private Integer contactsAttr=0;
	//关联的联系人字段
	private String contactsField;

	//填空的input
	private Integer answerInputWidth;
	private Integer answerInputRow;

	public String getBelongId() {
		return belongId;
	}
	public void setBelongId(String belongId) {
		this.belongId = belongId;
	}
	public String getQuName() {
		return quName;
	}
	public void setQuName(String quName) {
		this.quName = quName;
	}
	public String getQuTitle() {
		return quTitle;
	}
	public void setQuTitle(String quTitle) {
		this.quTitle = quTitle;
	}
	public String getQuNote() {
		return quNote;
	}
	public void setQuNote(String quNote) {
		this.quNote = quNote;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public QuType getQuType() {
		return quType;
	}
	public void setQuType(QuType quType) {
		this.quType = quType;
	}
	public Integer getTag() {
		return tag;
	}
	public void setTag(Integer tag) {
		this.tag = tag;
	}
	public Integer getOrderById() {
		return orderById;
	}
	public void setOrderById(Integer orderById) {
		this.orderById = orderById;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Integer getQuTag() {
		return quTag;
	}
	public void setQuTag(Integer quTag) {
		this.quTag = quTag;
	}
	public String getParentQuId() {
		return parentQuId;
	}
	public void setParentQuId(String parentQuId) {
		this.parentQuId = parentQuId;
	}
	public Integer getVisibility() {
		return visibility;
	}
	public void setVisibility(Integer visibility) {
		this.visibility = visibility;
	}
	public Integer getHv() {
		return hv;
	}
	public void setHv(Integer hv) {
		this.hv = hv;
	}
	public YesnoOption getYesnoOption() {
		return yesnoOption;
	}
	public void setYesnoOption(YesnoOption yesnoOption) {
		this.yesnoOption = yesnoOption;
	}
	public Integer getIsRequired() {
		return isRequired;
	}
	public void setIsRequired(Integer isRequired) {
		this.isRequired = isRequired;
	}
	public Integer getParamInt01() {
		return paramInt01;
	}
	public void setParamInt01(Integer paramInt01) {
		this.paramInt01 = paramInt01;
	}

	public CheckType getCheckType() {
		return checkType;
	}
	public void setCheckType(CheckType checkType) {
		this.checkType = checkType;
	}

	public Integer getParamInt02() {
		return paramInt02;
	}
	public void setParamInt02(Integer paramInt02) {
		this.paramInt02 = paramInt02;
	}


	public String getCopyFromId() {
		return copyFromId;
	}
	public void setCopyFromId(String copyFromId) {
		this.copyFromId = copyFromId;
	}

	public Integer getRandOrder() {
		return randOrder;
	}
	public void setRandOrder(Integer randOrder) {
		this.randOrder = randOrder;
	}

	public Integer getCellCount() {
		return cellCount;
	}
	public void setCellCount(Integer cellCount) {
		this.cellCount = cellCount;
	}

	public Integer getContactsAttr() {
		return contactsAttr;
	}
	public void setContactsAttr(Integer contactsAttr) {
		this.contactsAttr = contactsAttr;
	}
	public String getContactsField() {
		return contactsField;
	}
	public void setContactsField(String contactsField) {
		this.contactsField = contactsField;
	}

	public Integer getAnswerInputWidth() {
		return answerInputWidth;
	}
	public void setAnswerInputWidth(Integer answerInputWidth) {
		this.answerInputWidth = answerInputWidth;
	}
	public Integer getAnswerInputRow() {
		return answerInputRow;
	}
	public void setAnswerInputRow(Integer answerInputRow) {
		this.answerInputRow = answerInputRow;
	}


	/**
	 * 附加属性，不作映射
	 */
	private List<Question> questions=new ArrayList<Question>();
	//题选项
	private List<QuRadio> quRadios=new ArrayList<QuRadio>();
	private List<QuCheckbox> quCheckboxs=new ArrayList<QuCheckbox>();
	private List<QuMultiFillblank> quMultiFillblanks=new ArrayList<QuMultiFillblank>();
	private List<QuScore> quScores=new ArrayList<QuScore>();
	private List<QuOrderby> quOrderbys=new ArrayList<QuOrderby>();

	private List<QuChenOption> options=new ArrayList<QuChenOption>();
	private List<QuChenRow> rows=new ArrayList<QuChenRow>();
	private List<QuChenColumn> columns=new ArrayList<QuChenColumn>();
	private String rowContent="";
	private String colContent="";
	private String optionContent="";
	//删除的ID
	private String[] removeOptionUuIds=null;
	//题答卷
	private AnAnswer anAnswer=new AnAnswer();
	private List<AnCheckbox> anCheckboxs=new ArrayList<AnCheckbox>();
	private List<AnDFillblank> anDFillblanks=new ArrayList<AnDFillblank>();
	private List<AnEnumqu> anEnumqus=new ArrayList<AnEnumqu>();
	private AnFillblank anFillblank=new AnFillblank();
	private AnRadio anRadio=new AnRadio();
	private AnYesno anYesno=new AnYesno();

	private List<AnScore> anScores=new ArrayList<AnScore>();
	private List<AnChenRadio> anChenRadios=new ArrayList<AnChenRadio>();
	private List<AnChenCheckbox> anChenCheckboxs=new ArrayList<AnChenCheckbox>();
	private List<AnChenFbk> anChenFbks=new ArrayList<AnChenFbk>();
	private List<AnCompChenRadio> anCompChenRadios=new ArrayList<AnCompChenRadio>();
	private Integer anCount=0;
	private List<AnChenScore> anChenScores=new ArrayList<AnChenScore>();
	private List<AnOrder> anOrders = new ArrayList<AnOrder>();

	//逻辑设置
	private List<QuestionLogic> questionLogics;

	@Transient
	public List<QuRadio> getQuRadios() {
		return quRadios;
	}
	public void setQuRadios(List<QuRadio> quRadios) {
		this.quRadios = quRadios;
	}
	@Transient
	public List<QuCheckbox> getQuCheckboxs() {
		return quCheckboxs;
	}
	public void setQuCheckboxs(List<QuCheckbox> quCheckboxs) {
		this.quCheckboxs = quCheckboxs;
	}
	@Transient
	public List<QuMultiFillblank> getQuMultiFillblanks() {
		return quMultiFillblanks;
	}
	public void setQuMultiFillblanks(List<QuMultiFillblank> quMultiFillblanks) {
		this.quMultiFillblanks = quMultiFillblanks;
	}
	@Transient
	public List<QuScore> getQuScores() {
		return quScores;
	}
	public void setQuScores(List<QuScore> quScores) {
		this.quScores = quScores;
	}
	@Transient
	public List<QuOrderby> getQuOrderbys() {
		return quOrderbys;
	}
	public void setQuOrderbys(List<QuOrderby> quOrderbys) {
		this.quOrderbys = quOrderbys;
	}

	@Transient
	public List<QuChenOption> getOptions() {
		return options;
	}
	public void setOptions(List<QuChenOption> options) {
		this.options = options;
	}
	@Transient
	public List<QuChenRow> getRows() {
		return rows;
	}
	public void setRows(List<QuChenRow> rows) {
		this.rows = rows;
	}
	@Transient
	public List<QuChenColumn> getColumns() {
		return columns;
	}
	public void setColumns(List<QuChenColumn> columns) {
		this.columns = columns;
	}
	@Transient
	public String getRowContent() {
		return rowContent;
	}
	public void setRowContent(String rowContent) {
		this.rowContent = rowContent;
	}
	@Transient
	public String getColContent() {
		return colContent;
	}
	public void setColContent(String colContent) {
		this.colContent = colContent;
	}
	@Transient
	public String getOptionContent() {
		return optionContent;
	}
	public void setOptionContent(String optionContent) {
		this.optionContent = optionContent;
	}
	@Transient
	public String[] getRemoveOptionUuIds() {
		return removeOptionUuIds;
	}
	public void setRemoveOptionUuIds(String[] removeOptionUuIds) {
		this.removeOptionUuIds = removeOptionUuIds;
	}
	@Transient
	public List<Question> getQuestions() {
		return questions;
	}
	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}
	@Transient
	public AnAnswer getAnAnswer() {
		return anAnswer;
	}
	public void setAnAnswer(AnAnswer anAnswer) {
		this.anAnswer = anAnswer;
	}
	@Transient
	public List<AnCheckbox> getAnCheckboxs() {
		return anCheckboxs;
	}
	public void setAnCheckboxs(List<AnCheckbox> anCheckboxs) {
		this.anCheckboxs = anCheckboxs;
	}
	@Transient
	public List<AnDFillblank> getAnDFillblanks() {
		return anDFillblanks;
	}
	public void setAnDFillblanks(List<AnDFillblank> anDFillblanks) {
		this.anDFillblanks = anDFillblanks;
	}
	@Transient
	public List<AnEnumqu> getAnEnumqus() {
		return anEnumqus;
	}
	public void setAnEnumqus(List<AnEnumqu> anEnumqus) {
		this.anEnumqus = anEnumqus;
	}
	@Transient
	public AnFillblank getAnFillblank() {
		return anFillblank;
	}
	public void setAnFillblank(AnFillblank anFillblank) {
		this.anFillblank = anFillblank;
	}
	@Transient
	public AnRadio getAnRadio() {
		return anRadio;
	}
	public void setAnRadio(AnRadio anRadio) {
		this.anRadio = anRadio;
	}
	@Transient
	public AnYesno getAnYesno() {
		return anYesno;
	}
	public void setAnYesno(AnYesno anYesno) {
		this.anYesno = anYesno;
	}
	@Transient
	public List<AnScore> getAnScores() {
		return anScores;
	}
	public void setAnScores(List<AnScore> anScores) {
		this.anScores = anScores;
	}
	@Transient
	public List<AnChenRadio> getAnChenRadios() {
		return anChenRadios;
	}
	public void setAnChenRadios(List<AnChenRadio> anChenRadios) {
		this.anChenRadios = anChenRadios;
	}
	@Transient
	public List<AnChenCheckbox> getAnChenCheckboxs() {
		return anChenCheckboxs;
	}
	public void setAnChenCheckboxs(List<AnChenCheckbox> anChenCheckboxs) {
		this.anChenCheckboxs = anChenCheckboxs;
	}
	@Transient
	public List<AnChenFbk> getAnChenFbks() {
		return anChenFbks;
	}
	public void setAnChenFbks(List<AnChenFbk> anChenFbks) {
		this.anChenFbks = anChenFbks;
	}
	@Transient
	public List<AnCompChenRadio> getAnCompChenRadios() {
		return anCompChenRadios;
	}
	public void setAnCompChenRadios(List<AnCompChenRadio> anCompChenRadios) {
		this.anCompChenRadios = anCompChenRadios;
	}
	@Transient
	public Integer getAnCount() {
		return anCount;
	}
	public void setAnCount(Integer anCount) {
		this.anCount = anCount;
	}
	@Transient
	public List<QuestionLogic> getQuestionLogics() {
		return questionLogics;
	}
	public void setQuestionLogics(List<QuestionLogic> questionLogics) {
		this.questionLogics = questionLogics;
	}
	@Transient
	public List<AnChenScore> getAnChenScores() {
		return anChenScores;
	}
	public void setAnChenScores(List<AnChenScore> anChenScores) {
		this.anChenScores = anChenScores;
	}
	@Transient
	public List<AnOrder> getAnOrders() {
		return anOrders;
	}

	public void setAnOrders(List<AnOrder> anOrders) {
		this.anOrders = anOrders;
	}

	//统计json
	public String statJson="";
	@Transient
	public String getStatJson() {
		return statJson;
	}
	public void setStatJson(String statJson) {
		this.statJson = statJson;
	}

}
