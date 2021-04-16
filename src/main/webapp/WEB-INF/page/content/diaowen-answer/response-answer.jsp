<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" >
<title>答案结果回显</title>

<link href="${ctx }/js/plugs/jquery-ui-1.10.3.custom/css/mycss/jquery-ui-1.10.3.custom.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx }/js/plugs/jquery-ui-1.10.3.custom/js/jquery-1.10.1.js"></script>
<script type="text/javascript" src="${ctx }/js/plugs/jquery-ui-1.10.3.custom/js/jquery-ui-1.10.3.custom.js"></script>
<script type="text/javascript" src="${ctx }/js/dw/responsive-width.js"></script>
<script language="javascript" type="text/javascript" src="${ctx }/js/plugs/My97DatePickerBeta/My97DatePicker/WdatePicker.js"></script>
<link href="${ctx }/css/dw-user.css" rel="stylesheet" type="text/css" />
<link rel="icon" href="/favicon.ico" type="image/x-icon" />

<link href="${ctx }/css/response.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	$(document).ready(function(){
		$("input,textarea,select").attr("disabled","disabled");


		resetQuNum();
		function resetQuNum(){
			var quCoNums=$(".quCoNum");
			$.each(quCoNums,function(i,item){
				$(this).html((i+1));
			});

		}

	});
	resizeBodyWidth();
</script>
<style type="text/css">
.middle-body{
	height: auto! important;
}
</style>
</head>
<body>
<div id="wrap">
<div class="root-body" style="padding-top: 20px;">
		<div class="middle-body" style="padding-top:10px;">
		<form action="${ctx }/sy/user/nosm/survey-answer!save.action" method="post" >
		<input type="hidden" name="surveyId" value="${param['parentId'] }"/>
			<div class="middle-body-title">
					<h3>${directory.surveyName }</h3>
					<p>${directory.surveyDetail.surveyNote }</p>
					<div style="clear: both;"></div>
			</div>
			<div class="middle-body-content">
				<c:forEach items="${questions }" var="en" varStatus="i">

				<c:if test="${en.quType ne 'PARAGRAPH' && en.quType ne 'PAGETAG' }">

						<%--题干 --%>
						<div class="r-qu-body">
							<div class="r-qu-body-title"><span class="quCoNum">${i.count }</span>、${en.quTitle }[${en.quType.cnName }]</div>
							<div class="r-qu-body-content">
								<c:choose>
									<%--是非题 --%>
										<c:when test="${en.quType eq 'YESNO' }">
											<ul class="r-qu-body-ul1 r-qu-body-ul2">
												<c:choose>
													<c:when test="${en.anYesno.yesnoAnswer eq 1 }">
														<li> <input type="radio" name="qu_${en.quType }_${en.id }" value="${en.yesnoOption.trueValue }"  checked="checked"/>${en.yesnoOption.trueValue }</li>
														<li> <input type="radio" name="qu_${en.quType }_${en.id }" value="${en.yesnoOption.falseValue }" />${en.yesnoOption.falseValue } </li>
													</c:when>
													<c:when test="${en.anYesno.yesnoAnswer eq 0 }">
														<li> <input type="radio" name="qu_${en.quType }_${en.id }" value="${en.yesnoOption.trueValue }"  />${en.yesnoOption.trueValue }</li>
														<li> <input type="radio" name="qu_${en.quType }_${en.id }" value="${en.yesnoOption.falseValue }"  checked="checked"/>${en.yesnoOption.falseValue } </li>
													</c:when>
													<c:otherwise>
														<li> <input type="radio" name="qu_${en.quType }_${en.id }" value="${en.yesnoOption.trueValue }"  />${en.yesnoOption.trueValue }</li>
														<li> <input type="radio" name="qu_${en.quType }_${en.id }" value="${en.yesnoOption.falseValue }" />${en.yesnoOption.falseValue } </li>
													</c:otherwise>
												</c:choose>
											</ul>
										</c:when>
										<%--单选题 --%>
										<c:when test="${en.quType eq 'RADIO' }">
											<ul class="r-qu-body-ul1 ${en.hv eq 1 ?'r-qu-body-ul2':'' }">
												<c:forEach items="${en.quRadios }" var="quItem">
													<li>
														<ul class="quItem-ul">
															<c:choose>
																	<c:when test="${en.anRadio.quItemId eq quItem.id }">
																		<li><input type="radio" name="qu_${en.quType }_${en.id }" value="${quItem.id }" checked="checked"  /></li>
																		<li>${quItem.optionName }
																			<c:if test="${quItem.isNote eq 1}"> <input type="text" value="${en.anRadio.otherText }" disabled > </c:if>
																		</li>
																	</c:when>
																	<c:otherwise>
																		<li><input type="radio" name="qu_${en.quType }_${en.id }" value="${quItem.id }"  /></li>
																		<li>${quItem.optionName }
																			<c:if test="${quItem.isNote eq 1}"> <input type="text" value="${en.anRadio.otherText }" disabled > </c:if>
																		</li>
																	</c:otherwise>
															</c:choose>
														</ul>
													</li>
												</c:forEach>
											</ul>
										</c:when>
										<%--多选题 --%>
										<c:when test="${en.quType eq 'CHECKBOX' }">
											<input type="hidden" name="qu_${en.quType }_${en.id }" value="qu_${en.quType }_${en.id }_" />
											<ul class="r-qu-body-ul1 ${en.hv eq 1 ?'r-qu-body-ul2':'' }">
												<c:choose>
													<c:when test="${fn:length(en.anCheckboxs) gt 0 }">
														<c:forEach items="${en.quCheckboxs }" var="quItem">
															<li><ul class="quItem-ul">
																	<c:set var="isBreak" value="0"></c:set>
																	<c:forEach items="${en.anCheckboxs }" var="anCk" varStatus="iStatus">
																		<c:choose>
																			<c:when test="${anCk.quItemId eq quItem.id }">
																				<li><input type="checkbox" name="tag_qu_${en.quType }_${en.id }_${quItem.id }"  value="${quItem.id }"  checked="checked"/></li>
																				<li>${quItem.optionName }
																					<c:if test="${quItem.isNote eq 1}"> <input type="text" value="${anCk.otherText }" disabled > </c:if>
																				</li>
																				<c:set var="isBreak" value="1"></c:set>
																			</c:when>
																			<c:when test="${(fn:length(en.anCheckboxs) eq iStatus.count ) && (isBreak eq 0) }">
																				<li><input type="checkbox" name="tag_qu_${en.quType }_${en.id }_${quItem.id }"  value="${quItem.id }" /></li>
																				<li>${quItem.optionName }
																					<c:if test="${quItem.isNote eq 1}"> <input type="text" value="${anCk.otherText }" disabled > </c:if>
																				</li>
																				<c:set var="isBreak" value="0"></c:set>
																			</c:when>
																		</c:choose>
																	</c:forEach>
																</ul></li>
														</c:forEach>
													</c:when>
													<c:otherwise>
														<c:forEach items="${en.quCheckboxs }" var="quItem">
															<li><ul class="quItem-ul">
																<li><input type="checkbox" name="tag_qu_${en.quType }_${en.id }_${quItem.id }"  value="${quItem.id }" /></li>
																<li>
																	${quItem.optionName }
																	<c:if test="${quItem.isNote eq 1}"> <input type="text" value="" disabled > </c:if>
																</li>
															</ul></li>
														</c:forEach>
													</c:otherwise>
												</c:choose>
											</ul>
										</c:when>
										<%--填空 --%>
										<c:when test="${en.quType eq 'FILLBLANK' }">
											<ul class="r-qu-body-ul1 r-qu-body-ul3">
													<li><input type="text" value="${en.anFillblank.answer }" disabled > <%-- <input type="text"  name="qu_${en.quType }_${en.id }" value="${en.anFillblank.answer }"/> --%></li>
											</ul>
										</c:when>
										<%--多行填空 --%>
										<c:when test="${en.quType eq 'ANSWER' }">
											<ul class="r-qu-body-ul1 r-qu-body-ul3">
													<li> <textarea rows="5" cols="50" name="qu_${en.quType }_${en.id }">${en.anAnswer.answer }</textarea> </li>
											</ul>
										</c:when>
										<%--复合单选 --%>
										<c:when test="${en.quType eq 'COMPRADIO' }">
											<ul class="r-qu-body-ul1">
												<c:forEach items="${en.quRadios }" var="quItem">
													<li>
														<ul class="quItem-ul ">
															<c:choose>
																<c:when test="${en.anRadio.quItemId eq quItem.id }">
																	<li class="${quItem.isNote eq 1 ?'quItem-ul01li':''}"><input type="radio" name="qu_${en.quType }_${en.id }" value="${quItem.id }" checked="checked"/></li>
																	<li>${quItem.optionName }<c:if test="${quItem.isNote eq 1 }">&nbsp;&nbsp; 说明：<input type="text" name="text_qu_${en.quType }_${en.id }_${quItem.id }"  value="${en.anRadio.otherText }" /> </c:if></li>
																</c:when>
																<c:otherwise>
																	<li class="${quItem.isNote eq 1 ?'quItem-ul01li':''}"><input type="radio" name="qu_${en.quType }_${en.id }" value="${quItem.id }"/></li>
																	<li>${quItem.optionName }<c:if test="${quItem.isNote eq 1 }">&nbsp;&nbsp; 说明：<input type="text" name="text_qu_${en.quType }_${en.id }_${quItem.id }" /> </c:if></li>
																</c:otherwise>
															</c:choose>
														</ul>
													</li>
												</c:forEach>
											</ul>
										</c:when>
										<%--复合多选 --%>
										<c:when test="${en.quType eq 'COMPCHECKBOX' }">
											<input type="hidden" name="qu_${en.quType }_${en.id }" value="qu_${en.quType }_${en.id }_" />
											<ul class="r-qu-body-ul1">
												<li>${fn:length(en.anCheckboxs) }</li>
												<c:choose>
													<c:when test="${fn:length(en.anCheckboxs) gt 0 }">
														<c:forEach items="${en.quCheckboxs }" var="quItem">
															<li><ul class="quItem-ul">
															<c:set var="isBreak" value="0"></c:set>
															<c:forEach items="${en.anCheckboxs }" var="anCk" varStatus="iStatus" >
																<c:choose>
																<c:when test="${anCk.quItemId eq quItem.id }">
																	<li class="${quItem.isNote eq 1 ?'quItem-ul01li':''}"><input type="checkbox" name="tag_qu_${en.quType }_${en.id }_${quItem.id}" value="${quItem.id }" checked="checked"/></li>
																	<li>${quItem.optionName }<c:if test="${quItem.isNote eq 1 }">&nbsp;&nbsp; 说明：<input type="text" name="text_qu_${en.quType }_${en.id }_${quItem.id}"  value="${anCk.otherText }"/> </c:if> </li>
																	<c:set var="isBreak" value="1"></c:set>
																</c:when>
																<c:when test="${(fn:length(en.anCheckboxs) eq iStatus.count ) && (isBreak eq 0)}">
																	<li class="${quItem.isNote eq 1 ?'quItem-ul01li':''}"><input type="checkbox" name="tag_qu_${en.quType }_${en.id }_${quItem.id}" value="${quItem.id }"/></li>
																	<li>${quItem.optionName }<c:if test="${quItem.isNote eq 1 }">&nbsp;&nbsp; 说明：<input type="text" name="text_qu_${en.quType }_${en.id }_${quItem.id}" /> </c:if> </li>
																	<c:set var="isBreak" value="0"></c:set>
																</c:when>
																</c:choose>
															</c:forEach>
															</ul></li>
														</c:forEach>
													</c:when>
													<c:otherwise>
														<c:forEach items="${en.quCheckboxs }" var="quItem">
															<li><ul class="quItem-ul">
																<li class="${quItem.isNote eq 1 ?'quItem-ul01li':''}"><input type="checkbox" name="tag_qu_${en.quType }_${en.id }_${quItem.id}" value="${quItem.id }"/></li>
																<li>${quItem.optionName }<c:if test="${quItem.isNote eq 1 }">&nbsp;&nbsp; 说明：<input type="text" name="text_qu_${en.quType }_${en.id }_${quItem.id}" /> </c:if> </li>
															</ul></li>
														</c:forEach>
													</c:otherwise>
												</c:choose>
											</ul>
										</c:when>
										<%--枚举题 --%>
										<c:when test="${en.quType eq 'ENUMQU' }">
											<ul class="r-qu-body-ul1 r-qu-body-ul3" >
												<c:choose>
														<c:when test="${fn:length(en.anEnumqus) gt 0 }">
															<c:forEach begin="1" end="${en.paramInt01 }" var="quItem">
																<c:set var="isBreak" value="0"></c:set>
																<c:forEach items="${en.anEnumqus }" var="anEn" varStatus="iStatus">
																<c:choose>
																	<c:when test="${anEn.enumItem eq quItem }">
																		<li>${quItem }、 <input type="text" name="qu_${en.quType }_${en.id }_${quItem}" value="${anEn.answer }"/></li>
																		<c:set var="isBreak" value="1"></c:set>
																	</c:when>
																	<c:when test="${(fn:length(en.anEnumqus) eq iStatus.count ) && (isBreak eq 0)}">
																		<li>${quItem }、 <input type="text" name="qu_${en.quType }_${en.id }_${quItem}"/></li>
																		<c:set var="isBreak" value="0"></c:set>
																	</c:when>
																</c:choose>
																</c:forEach>
															</c:forEach>
														</c:when>
														<c:otherwise>
															<c:forEach begin="1" end="${en.paramInt01 }" var="quItem">
																<li>${quItem }、 <input type="text" name="qu_${en.quType }_${en.id }_${quItem}"/></li>
															</c:forEach>
														</c:otherwise>
													</c:choose>
											</ul>
										</c:when>
										<%--组合填空 --%>
										<c:when test="${en.quType eq 'MULTIFILLBLANK' }">
											<input type="hidden" name="qu_${en.quType }_${en.id }" value="qu_${en.quType }_${en.id }_" />
											<ul class="r-qu-body-ul1 r-qu-body-ul3" >
											<c:choose>
												<c:when test="${fn:length(en.anDFillblanks) gt 0 }">
													<c:forEach items="${en.quMultiFillblanks }" var="quItem">
														<c:set var="isBreak" value="0"></c:set>
														<c:forEach items="${en.anDFillblanks }" var="anDf" varStatus="iStatus">
															<c:choose>
																<c:when test="${anDf.quItemId eq quItem.id }">
																		<li>${quItem.optionName } <input type="text" name="text_qu_${en.quType }_${en.id }_${quItem.id }"  value="${anDf.answer }"/></li>
																		<c:set var="isBreak" value="1"></c:set>
																</c:when>
																<c:when test="${(fn:length(en.anDFillblanks) eq iStatus.count ) && (isBreak eq 0)}">
																		<li>${quItem.optionName } <input type="text" name="text_qu_${en.quType }_${en.id }_${quItem.id }"  value=""/></li>
																		<c:set var="isBreak" value="0"></c:set>
																</c:when>
															</c:choose>
														</c:forEach>
													</c:forEach>
												</c:when>
												<c:otherwise>
													<c:forEach items="${en.quMultiFillblanks }" var="quItem">
														<li>${quItem.optionName } <input type="text" name="text_qu_${en.quType }_${en.id }_${quItem.id }"  value=""/></li>
													</c:forEach>
												</c:otherwise>
											</c:choose>
											</ul>
										</c:when>
										<%--评分题 --%>
										<c:when test="${en.quType eq 'SCORE' }">
											<input type="hidden" name="qu_${en.quType }_${en.id }" value="item_qu_${en.quType }_${en.id }_" />
											<table class="qusTable" width="90%" >
												<tr>
													<th width="80">行/分</th>
													<c:forEach begin="${en.paramInt01 }" end="${en.paramInt02 }" var="enCol">
														<th align="center">${enCol }</th>
													</c:forEach>
												</tr>
												<c:forEach items="${en.quScores }" var="quScore">
													<tr>
														<td align="center">${quScore.optionName }</td>
														<c:forEach begin="${en.paramInt01 }" end="${en.paramInt02 }" var="enCol">
															<td align="center"><input type="radio"  name="item_qu_${en.quType }_${en.id }_${quScore.id }" value="${enCol }"/> </td>
														</c:forEach>
													</tr>
												</c:forEach>
											</table>
											<c:forEach items="${en.anScores }" var="enAn">
												<script type="text/javascript">
													$("input[name='item_qu_${en.quType }_${enAn.quId }_${enAn.quRowId }'][value='${enAn.answserScore }']").attr("checked",true);
												</script>
											</c:forEach>
										</c:when>
										<%--矩阵单选题 --%>
										<c:when test="${en.quType eq 'CHENRADIO' }">
										<input type="hidden" name="qu_${en.quType }_${en.id }" value="item_qu_${en.quType }_${en.id }_" />
											<table class="qusTable" width="90%" >
												<tr>
													<th width="80">行/列</th>
													<c:forEach items="${en.columns }" var="enCol">
														<th align="center">${enCol.optionName }</th>
													</c:forEach>
												</tr>
												<c:forEach items="${en.rows }" var="enRow">
													<tr>
														<td align="center">${enRow.optionName }</td>
														<c:forEach items="${en.columns }" var="enCol">
															<td align="center"><input type="radio"  name="item_qu_${en.quType }_${en.id }_${enRow.id }" value="${enCol.id }"/> </td>
														</c:forEach>
													</tr>
												</c:forEach>
											</table>
											<c:forEach items="${en.anChenRadios }" var="enAn">
												<script type="text/javascript">
													$("input[name='item_qu_${en.quType }_${enAn.quId }_${enAn.quRowId }'][value='${enAn.quColId }']").attr("checked",true);
												</script>
											</c:forEach>
										</c:when>
										<%--矩阵多选题 --%>
										<c:when test="${en.quType eq 'CHENCHECKBOX' }">
											<input type="hidden" name="qu_${en.quType }_${en.id }" value="item_qu_${en.quType }_${en.id }_" />
											<table class="qusTable" width="90%" >
												<tr>
													<th width="80">行/列</th>
													<c:forEach items="${en.columns }" var="enCol">
														<th align="center">${enCol.optionName }</th>
													</c:forEach>
												</tr>
												<c:forEach items="${en.rows }" var="enRow">
													<tr>
														<td align="center">${enRow.optionName }<input type="hidden" name="item_qu_${en.quType }_${en.id }_${enRow.id }"  value="ck_item_qu_${en.quType }_${en.id }_${enRow.id }" /> </td>
														<c:forEach items="${en.columns }" var="enCol">
															<td align="center"><input type="checkbox"  name="ck_item_qu_${en.quType }_${en.id }_${enRow.id }_${enCol.id}" value="${enCol.id }"/> </td>
														</c:forEach>
													</tr>
												</c:forEach>
											</table>
										<c:forEach items="${en.anChenCheckboxs }" var="enAn">
												<script type="text/javascript">
													$("input[name='ck_item_qu_${en.quType }_${enAn.quId }_${enAn.quRowId }_${enAn.quColId }'][value='${enAn.quColId }']").attr("checked",true);
												</script>
										</c:forEach>
										</c:when>
										<%--矩阵填空题 --%>
										<c:when test="${en.quType eq 'CHENFBK' }">
										<input type="hidden" name="qu_${en.quType }_${en.id }" value="item_qu_${en.quType }_${en.id }_" />
											<table class="qusTable" width="90%" >
												<tr>
													<th width="80">行/列</th>
													<c:forEach items="${en.columns }" var="enCol">
														<th align="center">${enCol.optionName }</th>
													</c:forEach>
												</tr>
												<c:forEach items="${en.rows }" var="enRow">
													<tr>
														<td align="center">${enRow.optionName }<input type="hidden" name="item_qu_${en.quType }_${en.id }_${enRow.id }"  value="fbk_item_qu_${en.quType }_${en.id }_${enRow.id }" /></td>
														<c:forEach items="${en.columns }" var="enCol">
															<td align="center"><input type="text"  name="fbk_item_qu_${en.quType }_${en.id }_${enRow.id }_${enCol.id }" size="6"/> </td>
														</c:forEach>
													</tr>
												</c:forEach>
											</table>
										<c:forEach items="${en.anChenFbks }" var="enAn">
												<script type="text/javascript">
													$("input[name='fbk_item_qu_${en.quType }_${enAn.quId }_${enAn.quRowId }_${enAn.quColId }']").val("${enAn.answerValue}");
												</script>
										</c:forEach>
										</c:when>
										<%--复合矩阵单选题 --%>
										<c:when test="${en.quType eq 'COMPCHENRADIO' }">
											<input type="hidden" name="qu_${en.quType }_${en.id }" value="item_qu_${en.quType }_${en.id }_" />
											<table class="qusTable" width="90%" >
												<tr>
													<th width="80">行&nbsp;/&nbsp;列</th>
													<c:forEach items="${en.columns }" var="enCol">
														<th align="center">${enCol.optionName }</th>
													</c:forEach>
												</tr>
												<c:forEach items="${en.rows }" var="enRow">
													<tr>
														<td align="center">${enRow.optionName }<input type="hidden" name="item_qu_${en.quType }_${en.id }_${enRow.id}" value="sel_item_qu_${en.quType }_${en.id }_${enRow.id}" /></td>
														<c:forEach items="${en.columns }" var="enCol">
															<td align="center">
															<select name="sel_item_qu_${en.quType }_${en.id }_${enRow.id}_${enCol.id}">
																	<option>-请选择-</option>
																<c:forEach items="${en.options }" var="enOption">
																	<option value="${enOption.id }">${enOption.optionName } </option>
																</c:forEach>
															</select>
															</td>
														</c:forEach>
													</tr>
												</c:forEach>
											</table>
											<c:forEach items="${en.anCompChenRadios }" var="enAn">
												<script type="text/javascript">
													$("select[name='sel_item_qu_${en.quType }_${enAn.quId }_${enAn.quRowId }_${enAn.quColId }']").val("${enAn.quOptionId}");
												</script>
										</c:forEach>
										</c:when>
										<c:when test="${en.quType eq 'ORDERQU' }">

											<table class="qusTable" style="padding:5px;" width="90%">
												<tr><th align="left" style="padding-left: 13px;">选项</th><th align="center">排序号</th></tr>
												<c:forEach items="${en.quOrderbys }" var="item" varStatus="itemVarStatus">
													<tr class="quOrderByTableTr"><td class="quOrderTabConnect" style="padding-left: 13px;" >${item.optionName }</td>
														<td id="${item.id}_orderQuItem" align="center"></td></tr>
												</c:forEach>
											</table>

											<c:forEach items="${en.anOrders}" var="enOrder">
												<script type="text/javascript">
													$("#${enOrder.quRowId}_orderQuItem").text(${enOrder.orderyNum});
												</script>
											</c:forEach>

										</c:when>
										<c:otherwise>
											<!-- 题型开发中.. -->
										</c:otherwise>
								</c:choose>
							</div>
						</div>

				</c:if>
				</c:forEach>
			</div>

			<div class="button-body-btn">

			</div>
			</form>
		</div>

	</div>
</div>

<%@ include file="/WEB-INF/page/layouts/other.jsp"%>
</body>
</html>
