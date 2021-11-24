<%@ page import="net.diaowen.common.plugs.footer.FooterInfo" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />
	<title>${surveyNameText }</title>
	<link rel="stylesheet" href="${ctx }/js/plugs/jquery.mobile-1.4.5/jquery.mobile-1.4.5.min.css">
	<link href="${ctx }/js/plugs/font-awesome-4.2.0/css/font-awesome.min.css" rel="stylesheet">
	<script type="text/javascript" src="${ctx }/js/plugs/jquery-ui-1.10.3.custom/js/jquery-1.10.1.js"></script>
	<link href="${ctx }/js/plugs/jquery-ui-1.10.3.custom/css/mycss/jquery-ui-1.10.3.custom.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="${ctx }/js/plugs/jquery-ui-1.10.3.custom/js/jquery-ui-1.10.3.custom.js"></script>
	<script type="text/javascript" src="${ctx }/js/plugs/jquery/jquery.cookie.js"></script>
	<link href="${ctx}/js/plugs/validate/jquery.validate.css" type="text/css" rel="stylesheet" />
	<script language="javascript" type="text/javascript" src="${ctx }/js/plugs/laydate/laydate.js"></script>
	<script type="text/javascript" src="${ctx }/js/common/ans-common.js"></script>
	<link href="${ctx }/css/answer-m.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="${ctx }/js/common/ans-m.js"></script>
	<script type="text/javascript">
		var refCookieId = "back_${survey.id}";
		var refCookieValue = $.cookie(refCookieId);
		if(refCookieValue!=null && refCookieValue=="b"){
			$.cookie(refCookieId, null, { path: '/' });
			window.location.reload();
		}
		$(document).ready(function(){

		});
	</script>
</head>
<body>
<div id="laziBody" style="display: none;">
	<div style="text-align: center;color: #6f6e6e;padding: 50px;"><label>数据连接中</label></div>
</div>
<form id="surveyForm" action="${ctx }/response/saveMobile.do" method="post" data-ajax="false">
	<input type="hidden" id="surveyId" name="surveyId" value="${survey.id }">
	<input type="hidden" name="form-from" value="mobile" >
	<div class="dwsurvey-page" >

		<div style="">
			<%--答题进度 --%>
			<div id="resultProgressRoot">
				<div class="progress-label">完成度：0%</div>
				<div id="resultProgress" class="progressbarDiv"></div>
			</div>
		</div>

		<div id="dwsurvey-body">
			<div id="dwsurvey-body-content" >
				<div class="dwsurvey-header">
					<div id="dwSurveyTitle" class="noLogoImg" style="">
						<!-- <i class="fa fa-star"></i> -->
						<div id="dwSurveyName" class="dwSvyName" style="">${survey.surveyName }</div>
					</div>
					<div id="dwSurveyNote">
						<div id="dwSurveyNoteEdit"  style="">${survey.surveyDetail.surveyNote }</div>
					</div>
				</div>
				<div id="m-errorMsg"></div>
				<div class="dwsurvey-content" >
					<div id="dwSurveyQuContent" >
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
														<input type="hidden" class="randOrder" value="${en.randOrder}" >
														<input type="hidden" class="answerTag" value="0" >
														<input type="hidden" class="pageNo" value="${pageNo}" >
														<input type="hidden" class="hv" value="${en.hv}" >
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

															<legend>
																<span class="quTitleNum">${i.count }、</span>
																<span class="quTitleText">${en.quTitle}</span>
															</legend>

															<c:choose>
																<c:when test="${en.hv eq 4 }">
																	<div class="quCoItem radioSelect_coitem" style="padding-top: 10px;">
																		<div class="radioSelectSet">
																			<select class="radioSelect"  name="qu_${en.quType }_${en.id }">
																				<option value="0">--请选择--</option>
																				<c:forEach items="${en.quRadios }" var="item">
																					<option value="${item.id }" >${item.optionName }</option>
																				</c:forEach>
																			</select>
																			<c:forEach items="${en.quRadios }" var="item" >
																				<div class="quItemInputCase" itemid="${item.id }">
																					<input type="hidden" class="isNote" value="${item.isNote }">
																				</div>
																				<c:if test="${item.isNote eq 1}">
																					<div class="dwQuOptionItemNote_input" style="display: none;" ><input type='text' class='inputSytle_1 dwQuOptionItemNote'  name="text_qu_${en.quType }_${en.id }_${item.id }"  /></div>
																				</c:if>
																			</c:forEach>
																		</div>
																	</div>
																</c:when>
																<c:otherwise>
																	<c:forEach items="${en.quRadios }" var="item" >
																		<div class="dwQuOptionItemContent quOptionItemRow" >
																			<label class="dwRedioStyle dwQuInputLabel" ></label>
																			<input id="qu_${en.quType }_${en.id }_${item.id}" type="radio" name="qu_${en.quType }_${en.id }" value="${item.id }">
																			<label for="qu_${en.quType }_${en.id }_${item.id}">${item.optionName }</label>
																			<c:if test="${item.isNote eq 1 }" >
																				<input type='text' class='inputSytle_1'  style="${item.isNote eq 1 ? '':'display: none;'}"   name="text_qu_${en.quType }_${en.id }_${item.id }"  />
																			</c:if>
																			<div class="quItemInputCase">
																				<input type="hidden" class="isNote" value="${item.isNote }">
																				<input type="hidden" class="checkType" value="${item.checkType }">
																				<input type="hidden" class="isRequiredFill" value="${item.isRequiredFill }">
																			</div>
																		</div>
																	</c:forEach>
																</c:otherwise>
															</c:choose>

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
														<input type="hidden" class="randOrder" value="${en.randOrder}" >
														<input type="hidden" class="pageNo" value="${pageNo}" >
														<input type="hidden" class="answerTag" value="0" >
														<input type="hidden" class="paramInt01" value="${en.paramInt01 }">
														<input type="hidden" class="paramInt02" value="${en.paramInt02 }">
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

															<legend>
																<span class="quTitleNum">${i.count }、</span>
																<span class="quTitleText">${en.quTitle}</span>
															</legend>
															<c:forEach items="${en.quCheckboxs }" var="item">
																<div class="dwQuOptionItemContent quOptionItemRow"  >
																	<label class="dwCheckboxStyle dwQuInputLabel" ></label>
																	<input id="tag_qu_${en.quType }_${en.id }_${item.id }" type="checkbox" name="tag_qu_${en.quType }_${en.id }_${item.id }"  value="${item.id }" >
																	<label for="tag_qu_${en.quType }_${en.id }_${item.id }" >${item.optionName }</label>
																	<c:if test="${item.isNote eq 1 }" >
																		<input type='text' class='inputSytle_1'  style=""  name="text_tag_qu_${en.quType }_${en.id }_${item.id }" />
																	</c:if>
																	<div class="quItemInputCase">
																		<input type="hidden" class="isNote" value="${item.isNote }">
																		<input type="hidden" class="checkType" value="${item.checkType }">
																		<input type="hidden" class="isRequiredFill" value="${item.isRequiredFill }">
																	</div>
																</div>
															</c:forEach>

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
														<input type="hidden" class="randOrder" value="${en.randOrder}" >
														<input type="hidden" class="checkType" value="${en.checkType }">
														<input type="hidden" class="pageNo" value="${pageNo}" >
														<input type="hidden" class="answerTag" value="0" >
														<input type="hidden" class="paramInt01" value="${en.paramInt01}">
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
														<div class="surveyQuItemContent" >
															<div style="">
																	<%--<div class="quCoTitle">
                                                                        <div class="quCoNum">${i.count }、</div>
                                                                        <div class="editAble quCoTitleEdit" >${en.quTitle}</div>
                                                                    </div>--%>
																<legend>
																	<span class="quTitleNum">${i.count }、</span>
																	<span class="quTitleText">${en.quTitle}</span>
																</legend>
																<c:choose>
																	<c:when test="${en.answerInputRow > 1 }">
																		<textarea name="qu_${en.quType }_${en.id }" rows="${en.answerInputRow }" style="width: 100%;margin-top: 10px;border-color: #dddddd;" class="inputSytle_2 fillblankInput" ></textarea>
																	</c:when>
																	<c:otherwise>
																		<input type="text" name="qu_${en.quType }_${en.id }" class="inputSytle_1 fillblankInput" >
																	</c:otherwise>
																</c:choose>

															</div>
														</div>
													</div>
												</div>
											</li>
										</c:when>
										<c:when test="${en.quType eq 'SCORE' }">
											<li class="li_surveyQuItemBody surveyQu_${pageNo }"  style="${pageNo gt 1 ?'display: none':''}">
												<div class="surveyQuItemBody">
													<div class="initLine"></div>
													<div class="quInputCase" style="display: none;">
														<input type="hidden" class="quType" value="SCORE">
														<input type="hidden" class="quId" value="${en.id }">
														<input type="hidden" class="orderById" value="${en.orderById }"/>
														<input type="hidden" class="isRequired" value="${en.isRequired }">
														<input type="hidden" class="randOrder" value="${en.randOrder}" >
														<input type="hidden" class="pageNo" value="${pageNo}" >
														<div class="quLogicInputCase">
															<c:forEach items="${en.questionLogics }" var="quLogicEn" varStatus="logicSts">
																<div class="quLogicItem quLogicItem_${logicSts.count }">
																	<input type="hidden" class="cgQuItemId" value="${quLogicEn.cgQuItemId }"/>
																	<input type="hidden" class="skQuId" value="${quLogicEn.skQuId }"/>

																	<input type="hidden" class="logicId" value="${quLogicEn.id }"/>
																	<input type="hidden" class="geLe" value="${quLogicEn.geLe }"/>
																	<input type="hidden" class="scoreNum" value="${quLogicEn.scoreNum }"/>
																	<input type="hidden" class="logicType" value="${quLogicEn.logicType }"/>
																</div>
															</c:forEach>
														</div>
														<input type="hidden" name="qu_${en.quType }_${en.id }" value="item_qu_${en.quType }_${en.id }_" />
													</div>
													<div class="surveyQuItem">
														<div class="surveyQuItemContent">
																<%--<div class="quCoTitle">
                                                                    <div class="quCoNum">${i.count }、</div>
                                                                    <div class="editAble quCoTitleEdit" >${en.quTitle}</div>
                                                                </div>--%>
															<legend>
																<span class="quTitleNum">${i.count }、</span>
																<span class="quTitleText">${en.quTitle}</span>
															</legend>
															<div class="quCoItem">
																<c:forEach items="${en.quScores }" var="item">
																	<div class="scoreRow quScoreOptionTr quOptionItemRow">
																		<input class="dwScoreOptionId" value="${item.id }" disabled="disabled" type="hidden"/>
																		<input type="hidden" class="answerTag" value="0" >
																		<div class="dwsurvey-controlgroup starRating" >
																			<div class="starOptionTitle" ><label>${item.optionName }</label></div>
																			<div class="starOptionContent" ><c:forEach begin="1" end="${en.paramInt02 }" var="scoreNum"><i class="fa fa-star-o"></i></c:forEach></div>
																		</div>
																		<input name="item_qu_${en.quType }_${en.id }_${item.id }" value=""  type="hidden" class="scoreNumInput" >
																	</div>
																</c:forEach>
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
														<input type="hidden" class="randOrder" value="${en.randOrder}" >
														<input type="hidden" class="pageNo" value="${pageNo}" >
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
															<div class="dwsurvey-controlgroup" >
																	<%--<div class="quCoTitle">
                                                                        <div class="quCoNum">${i.count }、</div>
                                                                        <div class="editAble quCoTitleEdit" >${en.quTitle}</div>
                                                                    </div>--%>
																<div>
																	<span class="quTitleNum">${i.count }、</span>
																	<span class="quTitleText">${en.quTitle}</span>
																</div>
																<div class="ui-controlgroup-controls ">
																	<c:forEach items="${en.quOrderbys }" var="item">
																		<div class="ui-checkbox m_clickQuOrderItem quOptionItemRow" >
																			<label class="ui-btn ui-corner-all ui-btn-inherit itemOptionname"  style="text-align: left;" >${item.optionName }</label>
																			<div class="m_orderby_num">0</div>
																			<div style="display: none;">
																				<input  name="item_qu_${en.quType }_${en.id }_${item.id }"  value="" type="hidden" class="quOrderItemHidInput" >
																			</div>
																		</div>
																	</c:forEach>
																</div>

															</div>
														</div>
													</div>

												</div>
											</li>
										</c:when>
										<c:when test="${en.quType eq 'PAGETAG' }">
											<li class="li_surveyQuItemBody surveyQu_${pageNo }"  style="${pageNo gt 1 ?'display: none':''}">
												<div class="surveyQuItemBody">
													<div class="initLine"></div>
													<div class="quInputCase" style="display: none;">
														<input type="hidden" class="quType" value="PAGETAG">
														<input type="hidden" class="quId" value="${en.id }">
														<input type="hidden" class="orderById" value="${en.orderById }"/>
														<input type="hidden" class="isRequired" value="${en.isRequired }">
														<input type="hidden" class="pageNo" value="${pageNo}" >
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
														<div class="pageBorderTop nohover" style="display: none;" ></div>
														<div class="surveyQuItemContent" style="padding: 0px;min-height: 30px;text-align: center;padding-top: 20px;">
															<!-- <div class="pageQuContent">下一页（1/2）</div> -->
															<a href="#" class="sbtn24 sbtn24_0 nextPage_a" >下一页</a>&nbsp;&nbsp;
															<c:if test="${pageNo > 1 }">
																<%--<a href="#" class="sbtn24 sbtn24_1 prevPage_a">上一页</a>--%>
																<input type="hidden" name="prevPageNo" value="${pageNo-1 }">
															</c:if>
															<c:set var="pageNo" value="${pageNo+1 }"></c:set>
															<input type="hidden" name="nextPageNo" value="${pageNo }">
														</div>
													</div>
												</div>
											</li>
										</c:when>
										<c:when test="${en.quType eq 'PARAGRAPH' }">
											<li class="li_surveyQuItemBody surveyQu_${pageNo }"  style="${pageNo gt 1 ?'display: none':''}">
												<div class="surveyQuItemBody">
													<div class="initLine"></div>
													<div class="quInputCase" style="display: none;">
														<input type="hidden" class="quType" value="PARAGRAPH">
														<input type="hidden" class="quId" value="${en.id }">
														<input type="hidden" class="orderById" value="${en.orderById }"/>
														<input type="hidden" class="isRequired" value="${en.isRequired }">
														<input type="hidden" class="pageNo" value="${pageNo}" >
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
														<div class="surveyQuItemContent"  style="min-height: 20px;">
															<div class="quCoTitle">
																	<%-- <div class="quCoNum quTitleNum">${i.count }、</div> --%>
																<div class="quCoTitleEdit" >${en.quTitle}</div>
															</div>
															<hr/>
														</div>
													</div>
												</div>
											</li>
										</c:when>
										<c:when test="${en.quType eq 'MULTIFILLBLANK' }">
											<li class="li_surveyQuItemBody surveyQu_${pageNo }"  style="${pageNo gt 1 ?'display: none':''}">
												<div class="surveyQuItemBody">
													<div class="initLine"></div>
													<div class="quInputCase" style="display: none;">
														<input type="hidden" class="quType" value="MULTIFILLBLANK">
														<input type="hidden" class="quId" value="${en.id }">
														<input type="hidden" class="orderById" value="${en.orderById }"/>
														<input type="hidden" class="isRequired" value="${en.isRequired }">
														<input type="hidden" class="randOrder" value="${en.randOrder}" >
														<input type="hidden" class="pageNo" value="${pageNo}" >
														<input type="hidden" class="paramInt01" value="${en.paramInt01 }">
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
																<%--<div class="quCoTitle">
                                                                    <div class="quCoNum">${i.count }、</div>
                                                                    <div class="editAble quCoTitleEdit" >${en.quTitle}</div>
                                                                </div>--%>
															<legend>
																<span class="quTitleNum">${i.count }、</span>
																<span class="quTitleText">${en.quTitle}</span>
															</legend>
															<div class="quCoItem">

																<c:forEach items="${en.quMultiFillblanks }" var="item">
																	<div class="mFillblankTableTr quOptionItemRow">
																		<label  for="text_qu_${en.quType }_${en.id }_${item.id }">${item.optionName }</label>
																		<input id="text_qu_${en.quType }_${en.id }_${item.id }" name="text_qu_${en.quType }_${en.id }_${item.id }"  type="text" class="dwMFillblankInput" >
																		<input class="dwMFillblankOptionId" value="${item.id }" disabled="disabled" type="hidden"/>
																		<input type="hidden" class="answerTag" value="0" >
																		<div class="quItemInputCase">
																			<input type="hidden" class="checkType" value="${item.checkType }">
																		</div>
																	</div>
																</c:forEach>

															</div>
														</div>

													</div>
												</div>
											</li>
										</c:when>
									</c:choose>
								</c:forEach>
								<li class="li_surveyQuItemBody surveyQu_${pageNo }"  style="${pageNo gt 1 ?'display: none':''}" submitli="1">
									<div class="surveyQuItemBody">
										<div class="surveyQuItem">
											<div id="jcaptchaImgBody" class="r-qu-body" style="display: none;">
												<div class="frmItem" style="font-size: 14px;">
													<label class="frm_label">验证码</label>
													<div class="frm_controls" style="margin-top: 5px;">
														<span class="auth-code" id="verifycodeImgArea">
														<input name="jcaptchaInput" type="text" class="jcaptchaInput" style="" autocomplete="off">
														</span>
													</div>
													<div style="display: flex;flex-direction:row;align-items:center;">
														<img id="register-jcaptchaImg" onclick="refreshAutoCode('register-jcaptchaImg')" src="${ctx }/jcap/jcaptcha.do" alt="验证码" height="40" >
														<a href="javascript:refreshAutoCode('register-jcaptchaImg');" style="margin-left: 5px;" hidefocus="true">换一张</a>
														<span class="frm_desc">验证码，区分大小写</span>
													</div>
												</div>
												<div class="errorItem" style="display: none;"><label class="error">验证码错误，请重新输入！</label></div>
											</div>

											<input type="hidden" class="pageNo" value="${pageNo}" >
											<input type="hidden" class="quType" value="submitSurveyBtn">
											<div class="surveyQuItemContent" style="padding: 20px 0px;min-height: 30px;text-align: center;">
												<c:choose>
													<c:when test="${!empty param.tag && param.tag eq 'p'}">
														<input type="button" class="submitSurvey" id="previewSubmitSurvey" value="提交"  class="dwsurvey-button" />
													</c:when>
													<c:otherwise>
														<input type="button" class="submitSurvey" id="submitSurvey" value="提交"  class="dwsurvey-button" />
													</c:otherwise>
												</c:choose>
												<c:if test="${pageNo > 1 }">
													<%--<a href="#" class="sbtn24 sbtn24_1 prevPage_a">上一页</a>--%>
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
				</div>
			</div>
		</div>
		<div class="dw_foot" style="padding-top:10px;text-align: center;">

			<div class="dw_footcopy" style="font-size: 12px;color: gray;text-align: center;">
				<p style="margin-bottom: 6px;">
					<a href="<%=FooterInfo.getWebInfoSiteUrl() %>" style="text-decoration: none;color: #333;"><strong><%=FooterInfo.getWebInfoSiteName() %></strong></a>
					<a href="/" style="text-decoration: none;color: #333;"><%=FooterInfo.getWebInfoSiteICP() %></a>
				</p>
			</div>
			<!-- 必须保留声明 start -->
			<div class="footer-copyright" style="color: #666;padding-top: 0px;font-size: 12px;padding-bottom: 20px;">
				Powered by <a href="http://www.diaowen.net" style="text-decoration: none;color: #333;"><strong>DWSurvey</strong></a>
			</div>
			<!-- 必须保留声明 end -->
		</div>
	</div>
</form>

<div id="fixedMsg" style="position: fixed;top: 0px;width: 100%;padding: 10px;text-align: center;font-size: 18px;letter-spacing: 4px;line-height: 56px;background-color: #111;background-color: rgba(17,17,17,0.5);color: #fff;color: rgba(255,255,255,0.5);z-index: 200;display: none;"></div>

<script type="text/javascript">

	function refreshAutoCode(codeImgId){
		$("#"+codeImgId).attr("src","${ctx}/jcap/jcaptcha.do");
	}

	//判则判断
	var url="${ctx}/response/ajaxCheckSurvey.do";
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
		var errorHtml="<div class=\"errorItem\" style=\"padding-left:30px;padding-top:10px;\" ><label for=\"\" class=\"error\">验证码不正确，请重新回答！</label></div>";
		$("#m-errorMsg").append(errorHtml);
	}

</script>

<%@ include file="../../layouts/other.jsp"%>
</body>
</html>
