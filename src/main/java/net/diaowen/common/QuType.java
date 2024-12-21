package net.diaowen.common;

/**
 * QuType
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */
public enum QuType {

	YESNO("是非题","yesno", 0),
	RADIO("单选题","radio", 1),
	CHECKBOX("多选题","checkbox", 2),
	FILLBLANK("填空题","fillblank", 3),

	COMPRADIO("复合单选","comp-radio", 17),
	COMPCHECKBOX("复合多选","comp-checkbox", 18),

	MULTIFILLBLANK("多项填空题","multi-fillblank", 4),//组合填空题
	ANSWER("多行填空题","answer", 5),//原问答题
	BIGQU("大题","bigqu", 6),

	ENUMQU("枚举题","enumqu", 7),
	SCORE("评分题","score", 8),
	ORDERQU("排序题","orderby", 9),
	CHENRADIO("矩阵单选题","chen-radio", 11),
	CHENFBK("矩阵填空题","chen-fbk", 12),
	CHENCHECKBOX("矩阵多选题","chen-checkbox", 13),
	COMPCHENRADIO("复合矩阵单选题","comp-chen-radio", 14),

	UPLOADFILE("文件上传题","sendfile",15),
	PAGETAG("分页标记","pagetag",16),
	PARAGRAPH("段落说明","paragraph",17),
	CHENSCORE("矩阵评分题","chen-score", 18),

	// 新版使用
	MATRIX_RADIO("矩阵单选","MATRIX_RADIO", 18),
	MATRIX_CHECKBOX("矩阵多选","MATRIX_CHECKBOX", 18),
	MATRIX_INPUT("矩阵填空","MATRIX_INPUT", 18),
	MATRIX_NUMBER("矩阵数值","MATRIX_NUMBER", 18),
	MATRIX_SCALE("矩阵量表","MATRIX_SCALE", 18),
	MATRIX_SLIDER("矩阵滑块","MATRIX_SLIDER", 18),
	MATRIX_SELECT("矩阵下拉","MATRIX_SELECT", 18),
	MATRIX_GROUP("矩阵组合","MATRIX_GROUP", 18),

	IMGRADIO("图片单选题","img-radio", 19),
	IMGCHECKBOX("图片多选题","img-checkbox", 20),
	SIGNATURE("电子签名题","signature",29),
	GEOLOCATION("定位题","geolocation",29),
	IMAGE_HOT("热力题","image-hot",29),
	MATRIX_AUTO_ADD("矩阵自增题","MATRIX_AUTO_ADD",29),
	UPLOAD_IMAGE("热力题","UPLOADIMAGE",29),
	COLOR_PICKER("颜色选择题","ColorPicker",29),

	//非答题组件
	DIVIDER("分割线","divider",29),
	CAROUSEL("图片轮播","carousel",29),
	MAP("地图组件","map",29);



	private String cnName;
	private String actionName;
	private int index;

	QuType(String cnName,String actionName,int index){
		this.cnName=cnName;
		this.actionName=actionName;
		this.index=index;
	}

	public String getCnName() {
		return cnName;
	}

	public void setCnName(String cnName) {
		this.cnName = cnName;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

}
