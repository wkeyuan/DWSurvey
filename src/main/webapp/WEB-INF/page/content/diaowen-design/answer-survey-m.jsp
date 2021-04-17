<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>${survey.surveyName }</title>
<link rel="stylesheet" href="${ctx }/js/plugs/jquery.mobile-1.4.5/jquery.mobile-1.4.5.min.css">
<script type="text/javascript" src="${ctx }/js/plugs/jquery-ui-1.10.3.custom/js/jquery-1.10.1.js"></script>
<%--<script src="${ctx }/js/plugs/jquery.mobile-1.4.5/jquery.mobile-1.4.5.min.js"></script>--%>
<script type="text/javascript" src="${ctx }/js/common/common.js"></script>
<script src="${ctx }/js/common/ans-m.js"></script>
<link href="${ctx }/js/plugs/font-awesome-4.2.0/css/font-awesome.css" rel="stylesheet">
<link href="${ctx}/js/plugs/validate/jquery.validate.css" type="text/css" rel="stylesheet" />
<link href="${ctx }/css/answer-m.css" rel="stylesheet" type="text/css" />
</head>
<body>
<form id="surveyForm" action="${ctx }/dws-answer!saveMobile.action" method="post" data-ajax="false">
<input type="hidden" id="surveyId" name="surveyId" value="${survey.id }">
<input type="hidden" name="form-from" value="mobile" >
<div style="padding: 0px 10px;">
  <div  id="answer-m-head" >
    	<div id="dwSurveyTitle" class="noLogoImg" style="padding-top: 5px;">
    		<!-- <i class="fa fa-star"></i> -->
			<div id="dwSurveyName" class="dwSvyName" style="">${survey.surveyName }</div>
		</div>
		<div id="dwSurveyNote">
			<div id="dwSurveyNoteEdit" >${survey.surveyDetail.surveyNote }</div>
		</div>
  </div>
  <div id="m-errorMsg"></div>
  <div id="answer-m-content">

    <div id="dwSurveyQuContent" style="">
			<div id="dwSurveyQuContentBg">
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
								<div class="controlgroup" >
									<legend>
										<span class="quTitleNum">${i.count }、</span>
										<span class="quTitleText">${en.quTitle}</span>
									</legend>
									<c:forEach items="${en.quRadios }" var="item" >
										<div class="dwQuOptionItemContent"  >
											<label class="dwRedioStyle dwQuInputLabel" ></label>
											<input id="qu_${en.quType }_${en.id }_${item.id}" type="radio" name="qu_${en.quType }_${en.id }" value="${item.id }">
											<label for="qu_${en.quType }_${en.id }_${item.id}">${item.optionName }</label>
											<c:if test="${item.isNote eq 1 }" >
												<input type='text' class='inputSytle_1 option_other_text' name="text_qu_${en.quType }_${en.id }_${item.id }"  />
											</c:if>
										</div>

									</c:forEach>
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
								<div class="controlgroup" >
										<legend>
											<span class="quTitleNum">${i.count }、</span>
											<span class="quTitleText">${en.quTitle}</span>
										</legend>
									<c:forEach items="${en.quCheckboxs }" var="item">

										<div class="dwQuOptionItemContent" >
											<label class="dwCheckboxStyle dwQuInputLabel" ></label>
											<input id="tag_qu_${en.quType }_${en.id }_${item.id }" type="checkbox" name="tag_qu_${en.quType }_${en.id }_${item.id }"  value="${item.id }" >
											<label for="tag_qu_${en.quType }_${en.id }_${item.id }" >${item.optionName }</label>
											<c:if test="${item.isNote eq 1 }" >
												<input type='text' class='inputSytle_1 option_other_text' name="text_tag_qu_${en.quType }_${en.id }_${item.id }" />
											</c:if>
										</div>

									</c:forEach>
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
								<div class="surveyQuItemContent" >
									 <div style="margin: 0px 5px;">
									 	<label for="qu_${en.quType }_${en.id }">
									 		<span class="quTitleNum">${i.count }、</span>
											<span class="quTitleText">${en.quTitle}</span>
									 	</label>

										 <%--<input id="qu_${en.quType }_${en.id }" type="text" name="qu_${en.quType }_${en.id }" class="fillblankInput" >--%>

										 <c:choose>
											 <c:when test="${en.checkType eq 'DATE'}">
												 <input type="date" name="qu_${en.quType }_${en.id }" class="inputSytle_1 fillblankInput" style="margin-top: 10px;"  >
											 </c:when>
											 <c:when test="${en.answerInputRow > 1 }">
												 <textarea name="qu_${en.quType }_${en.id }" rows="${en.answerInputRow }" class="inputSytle_2 fillblankInput" style="margin-top: 10px;"  > ></textarea>
											 </c:when>
											 <c:otherwise>
												 <input type="text" name="qu_${en.quType }_${en.id }" class="inputSytle_1 fillblankInput" style="margin-top: 10px;" >
											 </c:otherwise>
										 </c:choose>

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
								<div class="controlgroup" >
										<legend>
											<span class="quTitleNum">${i.count }、</span>
											<span class="quTitleText">${en.quTitle}</span>
										</legend>
								<div class="ui-controlgroup-controls ">
									<c:forEach items="${en.quOrderbys }" var="item">
										<div class="ui-checkbox m_clickQuOrderItem">
											<label class="ui-btn ui-corner-all ui-btn-inherit itemOptionname"  style="text-align: left;" >${item.optionName }</label>
											<div class="m_orderby_num">0</div>
											<div style="display: none;">
												<input  name="item_qu_${en.quType }_${en.id }_${item.id }"  value="0" type="hidden" class="quOrderItemHidInput" >
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
										<legend>
											<span class="quTitleNum">${i.count }、</span>
											<span class="quTitleText">${en.quTitle}</span>
										</legend>
									</div>
									<div class="quCoItem">

											<c:forEach items="${en.quMultiFillblanks }" var="item">
											<div class="mFillblankTableTr">
													<label  for="text_qu_${en.quType }_${en.id }_${item.id }">${item.optionName }</label>
													<input id="text_qu_${en.quType }_${en.id }_${item.id }" name="text_qu_${en.quType }_${en.id }_${item.id }"  type="text" class="dwMFillblankInput" >
													<input class="dwMFillblankOptionId" value="${item.id }" disabled="disabled" type="hidden"/>
													<input type="hidden" class="answerTag" value="0" >
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


				<li class="li_surveyQuItemBody surveyQu_${pageNo }"  style="padding-bottom: 40px;padding-top: 20px;${pageNo gt 1 ?'display: none':''}">
					<div class="surveyQuItemBody">
							<div class="surveyQuItem">
								<div id="jcaptchaImgBody" class="r-qu-body" style="display: none;">

									<div class="frmItem" style="">
										<label for="" class="frm_label">验证码</label>
										<div class="frm_controls">
											<span class="auth-code" id="verifycodeImgArea">
											<input name="jcaptchaInput" type="text" class="" style="" autocomplete="off">
											<img id="register-jcaptchaImg" onclick="refreshAutoCode('register-jcaptchaImg')" src="${ctx }/jcaptcha.action" alt="验证码" height="40"></span>
											<a href="javascript:refreshAutoCode('register-jcaptchaImg');" style="margin-left: 5px;" hidefocus="true">换一张</a>
											<span class="frm_desc">验证码，区分大小写</span>
											<p class="valid-msg fail" style="display: none;"><i>●</i><span class="msg_content">验证码错误，请重新输入</span></p>
										</div>
									</div>
									<div class="errorItem" style="display: none;"><label for="" class="error">验证码错误，请重新输入！</label></div>
								</div>



								<input type="hidden" class="quType" value="submitSurveyBtn">
								<div class="surveyQuItemContent" >
									<!-- <a href="#" data-theme="b"  data-role="button">提&nbsp;交</a>&nbsp;&nbsp; -->
									<input type="button" class="submitSurvey" id="submitSurvey" value="提&nbsp;交" data-role="button" data-theme="b" />
									 <!-- <a href="#" class="sbtn24 sbtn24_0 submitSurvey">提&nbsp;交</a>&nbsp;&nbsp; -->
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
  </div>
</div>

</form>

<div class="dw-footer" >
	<div><a href="http://diaowen.net/index-m.jsp" style="text-decoration: none;" rel="external" title="开源的调查问卷系统" >调问网&nbsp;DWSurvey&nbsp;</a>提供技术支持</div>
</div>

<div id="fixedMsg" style="position: fixed;top: 0px;width: 100%;padding: 10px;text-align: center;font-size: 18px;letter-spacing: 4px;line-height: 56px;background-color: #111;background-color: rgba(17,17,17,0.5);color: #fff;color: rgba(255,255,255,0.5);z-index: 200;display: none;"></div>
<%@ include file="/WEB-INF/page/layouts/other.jsp"%>
</body>
</html>
