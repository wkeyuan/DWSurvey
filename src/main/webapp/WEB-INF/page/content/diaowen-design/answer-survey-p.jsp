<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${survey.surveyName }</title>
<link href="${ctx }/js/plugs/jquery-ui-1.10.3.custom/css/mycss/jquery-ui-1.10.3.custom.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx }/js/plugs/jquery-ui-1.10.3.custom/js/jquery-1.10.1.js"></script>
<script type="text/javascript" src="${ctx }/js/plugs/jquery-ui-1.10.3.custom/js/jquery-ui-1.10.3.custom.js"></script>
<script type="text/javascript" src="${ctx }/js/plugs/colpick-jQuery/js/colpick.js"></script>
<link href="${ctx }/js/plugs/uploadify-v3.1/uploadify.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx }/js/plugs/uploadify-v3.1/jquery.uploadify-3.1.js"></script>
<script type="text/javascript" src="${ctx }/js/dw/uploadify.js"></script><script type="text/javascript" src="${ctx }/js/common/common.js"></script><script type="text/javascript" src="${ctx }/js/common/ans-p.js"></script>
<script language="javascript" type="text/javascript" src="${ctx }/js/plugs/My97DatePickerBeta/My97DatePicker/WdatePicker.js"></script>
<link href="${ctx }/css/preview-dev.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${ctx }/js/plugs/colpick-jQuery/css/colpick.css" type="text/css"/>
<link href="${ctx}/js/plugs/validate/jquery.validate.css" type="text/css" rel="stylesheet" />
<style type="text/css">
.edui-editor-iframeholder{
	display: none;
}
.edui-default .edui-editor-toolbarboxouter{
	border: none! important;
}
#resultProgressRoot{
  right: -80px;
  bottom: 100px;
  width: 200px;
  z-index: 200;
  position: fixed;
  transform: rotate(90deg);
}
.progress-label {
	font-size:14px;
	font-family: "微软雅黑";
	margin: 0px auto;
	text-align: center;
	line-height: 1.4em;
	color: #83AE00;
}
.progressbarDiv {
	height: 10px! important;
	box-shadow: none! important;
	border: 1px solid #83AE00;
}
 .progressbarDiv .ui-progressbar-value{
	background: #83AE00! important;
	border: none;
} 
.ui-progressbar .ui-progressbar-value{
	margin: 0px;
}
.ui-progressbar {
	position: relative;
	background: none! important;
}
.quOptionEditTd .editAble,.scoreNumTable tr td,.quCoItemTable{
	font-size: 16px;
}
label.error{
	font-size: 14px;
}
</style>
</head>
<body>

<div id="wrap">
<input type="hidden" id="id" name="id" value="${survey.id }">
<input type="hidden" id="ctx" name="ctx" value="${ctx }">
<input type="hidden" id="surveyStyleId" value="${surveyStyle.id }">
<input type="hidden" id="prevHost" value="${ctx }">

<form id="surveyForm" action="${ctx }/dws-answer!save.action" method="post" >
<input type="hidden" id="surveyId" name="surveyId" value="${survey.id }">
<input type="hidden" id="sid" name="sid" value="${survey.sid }">
<div id="dw_body" style="padding-top:10px;">
	<div id="dw_body_content">
		<div id="dwSurveyHeader">
			<div id="dwSurveyLogo"><img src="${ctx }/images/logo/sample_logo.png" height="70"/> </div>
			<%-- <div id="dwSurveyTitle" class="noLogoImg" >${survey.surveyName }</div>
			<div id="dwSurveyNote">
				<div id="dwSurveyNoteTools">参考样例</div>
				<div id="dwSurveyNoteEdit"  >${survey.surveyDetail.surveyNote }</div>
			</div> --%>
			<div id="dwSurveyTitle" class="noLogoImg">
				<div id="dwSurveyName" class="editAble dwSvyName">${survey.surveyName }</div>
			</div>
			<div id="dwSurveyNote">
				<div id="dwSurveyNoteTools">参考样例</div>
				<div id="dwSurveyNoteEdit"  class="editAble">${survey.surveyDetail.surveyNote }</div>
			</div>
		</div>
		<div id="dwSurveyQuContent" style="min-height: 300px;">
			<div id="dwSurveyQuContentBg">
			<!-- <div style="border-top: 3px solid #81AB00;margin:0px auto;padding-bottom: 15px;"></div> -->
			<c:set var="pageNo" value="1"></c:set>
			<c:set var="isNextPage" value="0"></c:set>
			<ul id="dwSurveyQuContentAppUl">
				<!-- 题目内容 -->
				<c:forEach items="${survey.questions }" var="en" varStatus="i">
				
				<c:choose>
					<c:when test="${en.quType eq 'RADIO' }">
					<li class="li_surveyQuItemBody surveyQu_${pageNo }"  style="${pageNo gt 1 ?'display: none':''}">
						<div class="surveyQuItemBody">
							<div class="initLine"></div>
							<div class="quInputCase" style="display: none;">
								<input type="hidden" class="quType" value="RADIO" >
								<input type="hidden" class="quId" value="${en.id }"  >
								<input type="hidden" class="orderById" value="${en.orderById }"/>
								<input type="hidden" class="isRequired" value="${en.isRequired }">
								<input type="hidden" class="answerTag" value="0" >
								<div class="quLogicInputCase">
								<c:forEach items="${en.questionLogics }" var="quLogicEn" varStatus="logicSts">
									<div class="quLogicItem quLogicItem_${logicSts.count }">
										<input type="hidden" class="cgQuItemId" value="${quLogicEn.cgQuItemId }"/>
										<input type="hidden" class="skQuId" value="${quLogicEn.skQuId }"/>
										<input type="hidden" class="logicId" value="${quLogicEn.id }"/>
										<input type="hidden" class="logicType" value="${quLogicEn.logicType }"/>
									</div>
								</c:forEach>
								</div>
							</div>
							<div class="surveyQuItem">
								
								<div class="surveyQuItemContent">
									<div class="quCoTitle">
										<div class="quCoNum">${i.count }、</div>
										<div class="editAble quCoTitleEdit" >${en.quTitle}</div>
									</div>
									<div class="quCoItem">
									<c:choose>
										<c:when test="${en.hv eq 3 }">
											<table class='tableQuColItem'>
												<c:forEach begin="0" end="${fn:length(en.quRadios)-1 }" var="j" step="${en.cellCount }">
												<tr>
												<c:forEach begin="1" end="${en.cellCount }" var="k">
												<td width="${600/en.cellCount }">
													<!-- 判断不为空，访止数组越界 -->
													<c:set var="quOptionIndex" value="${(j+k-1) }" ></c:set>
													<c:choose>
														<c:when test="${quOptionIndex < fn:length(en.quRadios) }">
															<div class="dwQuOptionItemContent">
																<label class="dwRedioStyle dwQuInputLabel" ></label>
																<input type="radio" name="qu_${en.quType }_${en.id }" value="${en.quRadios[quOptionIndex].id }" ><label style="width:${600/en.cellCount-10 }px;" class="editAble quCoOptionEdit quCoOptionPadding">${en.quRadios[quOptionIndex].optionName }</label>
																<input type='text' class='inputSytle_1'  style="width:200px;padding:5px;${item.isNote eq 1 ? '':'display: none;'}"  name="text_qu_${en.quType }_${en.id }_${en.quRadios[quOptionIndex].id }" />
															</div>
														</c:when>
														<c:otherwise><div class="emptyTd"></div></c:otherwise>
													</c:choose>
												</td>
												</c:forEach>
												</tr>
												</c:forEach>
											</table>
										</c:when>
										<c:when test="${en.hv eq 1 }">
											<ul class="transverse">
												<c:forEach items="${en.quRadios }" var="item">
												<li class="quCoItemUlLi">
													<div class="dwQuOptionItemContent">
													<label class="dwRedioStyle dwQuInputLabel" ></label>
													<input type="radio" name="qu_${en.quType }_${en.id }" value="${item.id }"><label class="editAble quCoOptionEdit quCoOptionPadding">${item.optionName }</label>
														<input type='text' class='inputSytle_1'  style="width:200px;padding:5px;${item.isNote eq 1 ? '':'display: none;'}"  name="text_qu_${en.quType }_${en.id }_${item.id }"  />
													</div>
												</li>
												<!-- <li><select> <option>可想而知</option> </select> </li> -->
												</c:forEach>
											</ul>
										</c:when>
										<c:otherwise>
											<ul>
												<c:forEach items="${en.quRadios }" var="item">
												<li class="quCoItemUlLi">
													<div class="dwQuOptionItemContent">
													<label class="dwRedioStyle dwQuInputLabel" ></label>
													<input type="radio" name="qu_${en.quType }_${en.id }" value="${item.id }" ><label class="editAble quCoOptionEdit quCoOptionPadding">${item.optionName }</label>
														<input type='text' class='inputSytle_1'  style="width:200px;padding:5px;${item.isNote eq 1 ? '':'display: none;'}"   name="text_qu_${en.quType }_${en.id }_${item.id }"  />
													</div>
												</li>
												<!-- <li><select> <option>可想而知</option> </select> </li> -->
												</c:forEach>
											</ul>
										</c:otherwise>
									</c:choose>
									</div>
								</div>
								
							</div>
					</div>
					</li>
					</c:when>
					
					<c:when test="${en.quType eq 'CHECKBOX' }">
					<li class="li_surveyQuItemBody surveyQu_${pageNo }"  style="${pageNo gt 1 ?'display: none':''}">
						<div class="surveyQuItemBody">
							<div class="initLine"></div>
							<div class="quInputCase" style="display: none;">
								<input type="hidden" class="quType" value="CHECKBOX">
								<input type="hidden" class="quId" value="${en.id }">
								<input type="hidden" class="orderById" value="${en.orderById }"/>
								<input type="hidden" class="isRequired" value="${en.isRequired }">
								<input type="hidden" class="answerTag" value="0" >
								<div class="quLogicInputCase">
									<c:forEach items="${en.questionLogics }" var="quLogicEn" varStatus="logicSts">
									<div class="quLogicItem quLogicItem_${logicSts.count }">
										<input type="hidden" class="cgQuItemId" value="${quLogicEn.cgQuItemId }"/>
										<input type="hidden" class="skQuId" value="${quLogicEn.skQuId }"/>
										<input type="hidden" class="logicId" value="${quLogicEn.id }"/>
										<input type="hidden" class="logicType" value="${quLogicEn.logicType }"/>
									</div>
									</c:forEach>
								</div>
								<input type="hidden" name="qu_${en.quType }_${en.id }" value="tag_qu_${en.quType }_${en.id }_" />
							</div>
							<div class="surveyQuItem">
								
								<div class="surveyQuItemContent">
									<div class="quCoTitle">
										<div class="quCoNum">${i.count }、</div>
										<div class="editAble quCoTitleEdit" >${en.quTitle}</div>
									</div>
									<div class="quCoItem">
										<c:choose>
										<c:when test="${en.hv eq 3 }">
											<table class='tableQuColItem'>
												<c:forEach begin="0" end="${fn:length(en.quCheckboxs)-1 }" var="j" step="${en.cellCount }" >
												<tr>
												<c:forEach begin="1" end="${en.cellCount }" var="k">
												<td width="${600/en.cellCount }">
													<!-- 判断不为空，访止数组越界 -->
													<c:set var="quOptionIndex" value="${(j+k-1) }" ></c:set>
													<c:choose>
														<c:when test="${quOptionIndex < fn:length(en.quCheckboxs) }">
														<div class="dwQuOptionItemContent">
																<label class="dwCheckboxStyle dwQuInputLabel" ></label>
																<input type="checkbox" name="tag_qu_${en.quType }_${en.id }_${en.quCheckboxs[quOptionIndex].id }"  value="${en.quCheckboxs[quOptionIndex].id }" ><label style="width:${600/en.cellCount-10 }px;" class="editAble quCoOptionEdit quCoOptionPadding">${en.quCheckboxs[quOptionIndex].optionName }</label>
															<input type='text' class='inputSytle_1'  style="width:200px;padding:5px;${item.isNote eq 1 ? '':'display: none;'}"  name="text_tag_qu_${en.quType }_${en.id }_${en.quCheckboxs[quOptionIndex].id }"  />
														</div>
														</c:when>
														<c:otherwise><div class="emptyTd"></div></c:otherwise>
													</c:choose>
												</td>
												</c:forEach>
												</tr>
												</c:forEach>
											</table>
										</c:when>
										<c:when test="${en.hv eq 1 }">
											<ul class="transverse">
												<c:forEach items="${en.quCheckboxs }" var="item">
												<li class="quCoItemUlLi">
													<div class="dwQuOptionItemContent">
														<label class="dwCheckboxStyle dwQuInputLabel" ></label>
														<input type="checkbox" name="tag_qu_${en.quType }_${en.id }_${item.id }"  value="${item.id }" ><label class="editAble quCoOptionEdit quCoOptionPadding">${item.optionName }</label>
														<input type='text' class='inputSytle_1'  style="width:200px;padding:5px;${item.isNote eq 1 ? '':'display: none;'}"  name="text_tag_qu_${en.quType }_${en.id }_${item.id }" />
													</div>
												</li>
												<!-- <li><select> <option>可想而知</option> </select> </li> -->
												</c:forEach>
											</ul>
										</c:when>
										<c:otherwise>
											<ul>
												<c:forEach items="${en.quCheckboxs }" var="item">
												<li class="quCoItemUlLi">
												<div class="dwQuOptionItemContent">
													<label class="dwCheckboxStyle dwQuInputLabel" ></label>
													<input type="checkbox" name="tag_qu_${en.quType }_${en.id }_${item.id }"  value="${item.id }" ><label class="editAble quCoOptionEdit quCoOptionPadding">${item.optionName }</label>
													<input type='text' class='inputSytle_1'  style="width:200px;padding:5px;${item.isNote eq 1 ? '':'display: none;'}"  name="text_tag_qu_${en.quType }_${en.id }_${item.id }" />
												</div>
												</li>
												<!-- <li><select> <option>可想而知</option> </select> </li> -->
												</c:forEach>
											</ul>
										</c:otherwise>
									</c:choose>
									</div>
									
								</div>
								
							</div>
					</div>
					</li>
					</c:when>
					
					<c:when test="${en.quType eq 'FILLBLANK' }">
						<li class="li_surveyQuItemBody surveyQu_${pageNo }"  style="${pageNo gt 1 ?'display: none':''}">
							<div class="surveyQuItemBody">
								<div class="initLine"></div>
								<div class="quInputCase" style="display: none;">
									<input type="hidden" class="quType" value="FILLBLANK">
									<input type="hidden" class="quId" value="${en.id }">
									<input type="hidden" class="orderById" value="${en.orderById }"/>
									<input type="hidden" class="isRequired" value="${en.isRequired }">
									<input type="hidden" class="checkType" value="${en.checkType }">
									<input type="hidden" class="answerTag" value="0" >
									<div class="quLogicInputCase">
										<c:forEach items="${en.questionLogics }" var="quLogicEn" varStatus="logicSts">
											<div class="quLogicItem quLogicItem_${logicSts.count }">
												<input type="hidden" class="cgQuItemId" value="${quLogicEn.cgQuItemId }"/>
												<input type="hidden" class="skQuId" value="${quLogicEn.skQuId }"/>
												<input type="hidden" class="logicId" value="${quLogicEn.id }"/>
												<input type="hidden" class="logicType" value="${quLogicEn.logicType }"/>
											</div>
										</c:forEach>
									</div>
								</div>
								<div class="surveyQuItem">
									<div class="surveyQuItemContent">
										<div class="quCoTitle">
											<div class="quCoNum">${i.count }、</div>
											<div class="editAble quCoTitleEdit" >${en.quTitle}</div>
										</div>
										<div class="quCoItem"><ul>
											<li class="quCoItemUlLi">
												<div class="quFillblankItem">
														<%-- <input type="text" name="qu_${en.quType }_${en.id }" style="width:200px;padding:5px;" class="inputSytle_1 fillblankInput"> --%>
													<c:choose>
														<c:when test="${en.checkType eq 'DATE'}">
															<input type="text" name="qu_${en.quType }_${en.id }" style="width: 300px;padding: 6px 10px 5px;border: 1px solid #83ABCB;outline: none;" class=" fillblankInput Wdate" onClick="WdatePicker()" >
														</c:when>
														<c:when test="${en.answerInputRow > 1 }">
															<textarea name="qu_${en.quType }_${en.id }" rows="${en.answerInputRow }" style="width:${empty(en.answerInputWidth)?'300':en.answerInputWidth}px;"class="inputSytle_2 fillblankInput" ></textarea>
														</c:when>
														<c:otherwise>
															<input type="text" name="qu_${en.quType }_${en.id }" style="width:${empty(en.answerInputWidth)?'300':en.answerInputWidth}px;" class="inputSytle_1 fillblankInput" >
														</c:otherwise>
													</c:choose>
													<div class="dwComEditMenuBtn" ></div>
												</div>
											</li>
										</ul>
										</div>
									</div>

								</div>
							</div>
						</li>
					</c:when>

					<c:when test="${en.quType eq 'ORDERQU' }">
					<li class="li_surveyQuItemBody surveyQu_${pageNo }"  style="${pageNo gt 1 ?'display: none':''}">
						<div class="surveyQuItemBody">
							<div class="initLine"></div>
							<div class="quInputCase" style="display: none;">
								<input type="hidden" class="quType" value="ORDERQU">
								<input type="hidden" class="quId" value="${en.id }">
								<input type="hidden" class="orderById" value="${en.orderById }"/>
								<input type="hidden" class="isRequired" value="${en.isRequired }">
								<input type="hidden" class="answerTag" value="0" >
								<div class="quLogicInputCase">
									<c:forEach items="${en.questionLogics }" var="quLogicEn" varStatus="logicSts">
									<div class="quLogicItem quLogicItem_${logicSts.count }">
										<input type="hidden" class="cgQuItemId" value="${quLogicEn.cgQuItemId }"/>
										<input type="hidden" class="skQuId" value="${quLogicEn.skQuId }"/>
									</div>
									</c:forEach>
								</div>
								<input type="hidden" name="qu_${en.quType }_${en.id }" value="item_qu_${en.quType }_${en.id }_" />
							</div>
							<div class="surveyQuItem">
								<div class="surveyQuItemContent">
									<div class="quCoTitle">
										<div class="quCoNum">${i.count }、</div>
										<div class="editAble quCoTitleEdit" >${en.quTitle}</div>
									</div>
									<div class="quCoItem quOrderByCoItem">
										<div class="quOrderByRight">
											<table class="quOrderByTable" style="padding:5px;">
											<c:forEach items="${en.quOrderbys }" var="item" varStatus="itemVarStatus">
													<tr class="quOrderByTableTr"><td class="quOrderyTableTd">${itemVarStatus.count }</td><td class="quOrderTabConnect"><%-- <label class="quOrderItemLabel">drag content ${itemVarStatus.count }</label> --%> </td></tr>
											</c:forEach>
											</table>
										</div>
										<%-- <div class="quOrderByLeft">
											<ul class="quOrderByNumUl">
											<c:forEach items="${en.quOrderbys }" var="item" varStatus="itemVarStatus">
													<li><label class="quOrderyNumLabel">${itemVarStatus.count }</label>&nbsp;请夺</li>
											</c:forEach>
											</ul>
										</div> --%>
										<div  class="quOrderByLeft">
										<ul class="quOrderByLeftUl">
										<c:forEach items="${en.quOrderbys }" var="item">
											<li class="quCoItemUlLi">
												<label class="editAble quCoOptionEdit">${item.optionName }
													<input  name="item_qu_${en.quType }_${en.id }_${item.id }"  value="1" type="hidden" class="quOrderItemHidInput" >
												</label>
											</li>
										</c:forEach>
										</ul>
										</div>
										<div style="clear: both;"></div>
									</div>
								</div>
								
							</div>
					</div>
					</li>
					</c:when>
					
					<%-- 分页题 --%>
					<c:when test="${en.quType eq 'PAGETAG' }">
					<li class="li_surveyQuItemBody surveyQu_${pageNo }"  style="${pageNo gt 1 ?'display: none':''}">
						<div class="surveyQuItemBody">
							<div class="initLine"></div>
							<div class="quInputCase" style="display: none;">
								<input type="hidden" class="quType" value="PAGETAG">
								<input type="hidden" class="quId" value="${en.id }">
								<input type="hidden" class="orderById" value="${en.orderById }"/>
								<input type="hidden" class="isRequired" value="${en.isRequired }">
								<div class="quLogicInputCase">
									<c:forEach items="${en.questionLogics }" var="quLogicEn" varStatus="logicSts">
									<div class="quLogicItem quLogicItem_${logicSts.count }">
										<input type="hidden" class="cgQuItemId" value="${quLogicEn.cgQuItemId }"/>
										<input type="hidden" class="skQuId" value="${quLogicEn.skQuId }"/>
									</div>
									</c:forEach>
								</div>
							</div>
							<div class="surveyQuItem">
								<div class="pageBorderTop nohover"  ></div>
								<div class="surveyQuItemContent" style="padding-top: 12px;height: 30px;min-height: 30px;">
									<!-- <div class="pageQuContent">下一页（1/2）</div> -->
									<a href="#" class="sbtn24 sbtn24_0 nextPage_a" >下一页</a>&nbsp;&nbsp;
									<c:if test="${pageNo > 1 }">
									<a href="#" class="sbtn24 sbtn24_1 prevPage_a">上一页</a>
									<input type="hidden" name="prevPageNo" value="${pageNo-1 }">
									</c:if>
									<c:set var="pageNo" value="${pageNo+1 }"></c:set>
									<input type="hidden" name="nextPageNo" value="${pageNo }">
								</div>
							</div>
					</div>
					</li>
					</c:when>
					
					<%--段落说明 --%>
					<c:when test="${en.quType eq 'PARAGRAPH' }">
					<li class="li_surveyQuItemBody surveyQu_${pageNo }"  style="${pageNo gt 1 ?'display: none':''}">
						<div class="surveyQuItemBody">
							<div class="initLine"></div>
							<div class="quInputCase" style="display: none;">
								<input type="hidden" class="quType" value="PARAGRAPH">
								<input type="hidden" class="quId" value="${en.id }">
								<input type="hidden" class="orderById" value="${en.orderById }"/>
								<input type="hidden" class="isRequired" value="${en.isRequired }">
								<div class="quLogicInputCase">
									<c:forEach items="${en.questionLogics }" var="quLogicEn" varStatus="logicSts">
									<div class="quLogicItem quLogicItem_${logicSts.count }">
										<input type="hidden" class="cgQuItemId" value="${quLogicEn.cgQuItemId }"/>
										<input type="hidden" class="skQuId" value="${quLogicEn.skQuId }"/>
									</div>
									</c:forEach>
								</div>
							</div>
							<div class="surveyQuItem">
								<div class="surveyQuItemContent" style="min-height: 35px;">
									<div class="quCoTitle" style="background: rgb(243, 247, 247);">
										<%-- <div class="quCoNum" >${i.count }、</div> --%>
										<div class="editAble quCoTitleEdit" style="padding-left: 15px;">${en.quTitle}</div>
									</div>
								</div>
							</div>
					</div>
					</li>
					</c:when>
					
					<%--多项填空题 --%>
					<c:when test="${en.quType eq 'MULTIFILLBLANK' }">
					<li class="li_surveyQuItemBody surveyQu_${pageNo }"  style="${pageNo gt 1 ?'display: none':''}">
						<div class="surveyQuItemBody">
							<div class="initLine"></div>
							<div class="quInputCase" style="display: none;">
								<input type="hidden" class="quType" value="MULTIFILLBLANK">
								<input type="hidden" class="quId" value="${en.id }">
								<input type="hidden" class="orderById" value="${en.orderById }"/>
								<input type="hidden" class="isRequired" value="${en.isRequired }">
								<div class="quLogicInputCase">
									<c:forEach items="${en.questionLogics }" var="quLogicEn" varStatus="logicSts">
									<div class="quLogicItem quLogicItem_${logicSts.count }">
										<input type="hidden" class="cgQuItemId" value="${quLogicEn.cgQuItemId }"/>
										<input type="hidden" class="skQuId" value="${quLogicEn.skQuId }"/>
										<input type="hidden" class="logicId" value="${quLogicEn.id }"/>
										<input type="hidden" class="logicType" value="${quLogicEn.logicType }"/>
									</div>
									</c:forEach>
								</div>
								<input type="hidden" name="qu_${en.quType }_${en.id }" value="text_qu_${en.quType }_${en.id }_" />
							</div>
							<div class="surveyQuItem">
								
								<div class="surveyQuItemContent">
									<div class="quCoTitle">
										<div class="quCoNum">${i.count }、</div>
										<div class="editAble quCoTitleEdit" >${en.quTitle}</div>
									</div>
									<div class="quCoItem">
										<table class="mFillblankTable" cellpadding="0" cellspacing="0">
										<c:forEach items="${en.quMultiFillblanks }" var="item">
										<tr class="mFillblankTableTr">
											<td align="right" class="mFillblankTableEditTd"><label class="editAble quCoOptionEdit">${item.optionName }</label>
											<input class="dwMFillblankOptionId" value="${item.id }" disabled="disabled" type="hidden"/>
											<input type="hidden" class="answerTag" value="0" >
											</td>
											<td><input name="text_qu_${en.quType }_${en.id }_${item.id }"  type="text" style="width:200px;padding:5px;" class="inputSytle_1 dwMFillblankInput"></td>
										</tr>
										</c:forEach>
									</table>
									
									</div>
								</div>
								
							</div>
					</div>
					</li>
					</c:when>

				</c:choose>
				
				</c:forEach>
				
				
				<li class="li_surveyQuItemBody surveyQu_${pageNo }"  style="${pageNo gt 1 ?'display: none':''}">
					<div class="surveyQuItemBody">
							<div class="surveyQuItem">
								<!-- <div class="pageBorderTop nohover"  ></div> -->
								<div id="jcaptchaImgBody" class="r-qu-body" style="display: none;">
									
									<div class="frmItem" style="">
										<label for="" class="frm_label">验证码</label>
										<div class="frm_controls">
											<span class="auth-code" id="verifycodeImgArea">
											<input name="jcaptchaInput" type="text" class="" style="width:100px;" autocomplete="off">
											<img id="register-jcaptchaImg" onclick="refreshAutoCode('register-jcaptchaImg')" src="${ctx }/jcaptcha.action" alt="验证码" height="40"></span>
											<a href="javascript:refreshAutoCode('register-jcaptchaImg');" style="margin-left: 5px;" hidefocus="true">换一张</a>
											<span class="frm_desc">输入下面图片的字符，区分大小写</span>
											<p class="valid-msg fail" style="display: none;"><i>●</i><span class="msg_content">验证码错误，请重新输入</span></p>
										</div>
									</div>
									<div class="errorItem" style="display: none;"><label for="" class="error">验证码错误，请重新输入！</label></div>
									<%-- 验证码：<input type="text" size="8" name="jcaptchaInput">
									&nbsp;<img id="jcaptchaImg" alt="点击刷新" src="${ctx }/jcaptcha.action"  align="top">
									&nbsp;点击图片刷新 --%>
								</div>
								<input type="hidden" class="quType" value="submitSurveyBtn">
								<div class="surveyQuItemContent" style="padding-top: 12px;height: 30px;min-height: 30px;">
									<a href="#" id="submitSurvey" class="sbtn24 sbtn24_0 submitSurvey" >提&nbsp;交</a>&nbsp;&nbsp;
									&nbsp;&nbsp;
									<%-- <a href="${ctx }/report.action?sid=${survey.sid}" style="color: gray;text-decoration: none;" target="_blank">查看结果</a> --%>
									<c:if test="${pageNo > 1 }">
									<a href="#" class="sbtn24 sbtn24_1 prevPage_a">上一页</a>
									<input type="hidden" name="prevPageNo" value="${pageNo-1 }">
									</c:if>
									<c:set var="pageNo" value="${pageNo+1 }"></c:set>
									<input type="hidden" name="nextPageNo" value="${pageNo }">
								</div>
							</div>
					</div>
				</li>
				
			</ul>
			</div>
		</div>
		<%--答题进度 --%>
		<div id="resultProgressRoot">
			<div class="progress-label">完成度：0%</div>
			<div id="resultProgress" class="progressbarDiv"></div>
		</div>
		
	</div>
</div>
</form>

</div>


<div class="footer-copyright footer-pb" style="color: gray;padding-bottom: 5px;">
		Powered by <a href="http://www.diaowen.net" style="text-decoration: none;color: gray;">DWSurvey</a>&nbsp;
</div>
		
<div id="fixedMsg" style="position: fixed;top: 0px;width: 100%;padding: 10px;text-align: center;font-size: 18px;letter-spacing: 4px;line-height: 56px;background-color: #111;background-color: rgba(17,17,17,0.5);color: #fff;color: rgba(255,255,255,0.5);z-index: 200;display: none;"></div>
<script type="text/javascript">
var bfbFloat=0;
$("#resultProgress").progressbar({value: bfbFloat,orientation: "vertical"});

function refreshAutoCode(codeImgId){
	var ctx=$("#ctx").val();
	$("#"+codeImgId).attr("src",ctx+"/jcaptcha.action");
}

//判则判断 
var url="${ctx}/dwsurvey!ajaxCheckSurvey.action";
var data="surveyId=${survey.id}";
$.ajax({
	url:url,
	data:data,
	type:"post",
	success:function(msg){
		var json=eval("("+msg+")");
		if(json.isCheckCode==="3"){
			$("#jcaptchaImgBody").show();
		}
		
		if(json.surveyStatus!="0"){
			$("#fixedMsg").show();
			$("#fixedMsg").html("您已经回答过此问卷！");
			$("#submitSurvey").remove();
			$("form").attr("action","#");
		}
	}
});

var errorcode="${param['errorcode']}";
if(errorcode=="3"){
	var errorHtml="<div class=\"errorItem\" style=\"padding-left:30px;\" ><label for=\"\" class=\"error\">验证码不正确，请重新回答！</label></div>";
	$("#dwSurveyHeader").append(errorHtml);
}

</script>
<%@ include file="/WEB-INF/page/layouts/other.jsp"%>

<!-- Diaowen.net Button BEGIN -->
	<div id="webSiteFixedRight" class="websiteFixed" style="position: fixed;right: 0px;top: 20px;z-index: 9999;">
		<a id="mobileTdId" href="＃" style="background: #1C658B;width: 15px;background: #8CBCD1;display: block;padding: 5px;padding-top: 10px;padding-bottom:10px;font-weight: bold;color: white;cursor: pointer;float: right;vertical-align: middle;text-decoration: none;font-size: 12px;">手机地址</a>
		<img alt="" src="${ctx }/survey!answerTD.action?surveyId=${survey.id}" height="130" style="padding: 10px;background: white;display: none;" />
	</div>
<!-- Diaowen.net Button END -->

</body>
</html>