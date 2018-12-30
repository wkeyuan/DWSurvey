<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>${survey.surveyName }</title>
<link href="${ctx }/css/sur-mobile.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${ctx }/js/plugs/jquery.mobile-1.4.5/jquery.mobile-1.4.5.min.css">
<script type="text/javascript" src="${ctx }/js/plugs/jquery-ui-1.10.3.custom/js/jquery-1.10.1.js"></script>
<script src="${ctx }/js/plugs/jquery.mobile-1.4.5/jquery.mobile-1.4.5.min.js"></script>
<script src="${ctx }/js/common/ans-m.js"></script>
<link href="${ctx }/js/plugs/font-awesome-4.2.0/css/font-awesome.css" rel="stylesheet">

<link href="${ctx}/js/plugs/validate/jquery.validate.css" type="text/css" rel="stylesheet" />


<style type="text/css">
.ui-page {
	background:white;
}
.ui-header{
	background-color: #5693C0! important;
	text-shadow:0 1px 0 #3D586C! important;
	padding-bottom: 5px;
}
.ui-content{
	padding: 0px 1em;
}
.ui-footer{
	color: #3D586C! important;
	background: none! important;
}
.ui-header, .ui-footer{
	border: none! important;
}
.starRating{
	font-size: 26px;
}
.starRating .fa{
	cursor: pointer;
}
.starRating .fa-star{
	color: #3388CC;
}
.subbtn{
	opacity:1! important;
	color: white;
}
.quTitleNum{
	/* position: absolute; */
}
.quTitleText{
	/* text-indent: 2em; */
}
#dwSurveyNote{
	padding-top: 0px;
}
.m_quOrderByUi{
	  margin: 5px 0 5px 0;
  padding: 0;
  border: 1px solid #d5d5d5;
  border-radius: 3px;
}
.m_quOrderByUi li{
  border-color: #fff;
  font-size: 16px;
  min-height: 41px;
  position: relative;
  padding-right: 45px!important;
  border-bottom: 1px solid #EBEBEB!important;
}
.m_orderby_num{
  position: absolute;
  right: 10px;
  top: 55%;
  margin-top: -15px;
  min-width: 26px;
  height: 26px;
  background: #85C8FF;
  color: #fff;
  text-align: center;
  line-height: 26px;
  border-radius: 15px;
  z-index: 100;
  display: none;
}
.m_orderby_sel{
  position: absolute;
  top: 0px;
  left: 0px;
  width: 100%;
  height: 100%;
  opacity: 0;
  font-size: 30px;
  z-index: 9999;
  
  background-color: rgb(248, 248, 248);
  border: 1px solid rgb(166, 166, 166);
  border-image-source: initial;
  border-image-slice: initial;
  border-image-width: initial;
  border-image-outset: initial;
  border-image-repeat: initial;
  
    display: inline-block;
}

</style>
</head>
<body>
<form id="surveyForm" action="${ctx }/dws-answer!saveMobile.action" method="post" data-ajax="false">
<input type="hidden" id="surveyId" name="surveyId" value="${survey.id }">
<input type="hidden" name="form-from" value="mobile" >
<div data-role="page" >
  <div data-role="header">
    	<div id="dwSurveyTitle" class="noLogoImg" style="padding-top: 5px;">
    		<!-- <i class="fa fa-star"></i> -->
			<div id="dwSurveyName" class="dwSvyName" style="color:rgb(244, 234, 195);">${survey.surveyName }</div>
		</div>
		<div id="dwSurveyNote">
			<div id="dwSurveyNoteEdit"  style="color:white;font-weight: normal;line-height: 20px;">${survey.surveyDetail.surveyNote }</div>
		</div>
  </div>
  <div id="m-errorMsg"></div>
  <div data-role="content" >

    <div id="dwSurveyQuContent" style="">
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
								<fieldset data-role="controlgroup" >
									<legend>
										<span class="quTitleNum">${i.count }、</span>
										<span class="quTitleText">${en.quTitle}</span>
									</legend>
									<c:forEach items="${en.quRadios }" var="item" >
										<div class="dwQuOptionItemContent">
										<label for="qu_${en.quType }_${en.id }_${item.id}">${item.optionName }</label>
										<input id="qu_${en.quType }_${en.id }_${item.id}" type="radio" name="qu_${en.quType }_${en.id }" value="${item.id }">
											<c:if test="${item.isNote eq 1 }" >
												<input type='text' class='inputSytle_1'  style="width:200px;padding:5px;${item.isNote eq 1 ? '':'display: none;'}"   name="text_qu_${en.quType }_${en.id }_${item.id }"  />
											</c:if>
										</div>
									</c:forEach>
								</fieldset>
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
								<fieldset data-role="controlgroup" >
										<legend>
											<span class="quTitleNum">${i.count }、</span>
											<span class="quTitleText">${en.quTitle}</span>
										</legend>
									<c:forEach items="${en.quCheckboxs }" var="item">
										<div class="dwQuOptionItemContent">
										<label for="tag_qu_${en.quType }_${en.id }_${item.id }" >${item.optionName }</label>
										<input id="tag_qu_${en.quType }_${en.id }_${item.id }" type="checkbox" name="tag_qu_${en.quType }_${en.id }_${item.id }"  value="${item.id }" >
											<c:if test="${item.isNote eq 1 }" >
												<input type='text' class='inputSytle_1'  style="width:200px;padding:5px;"  name="text_tag_qu_${en.quType }_${en.id }_${item.id }" />
											</c:if>
										</div>
									</c:forEach>
								</fieldset>
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
												 <input type="date" name="qu_${en.quType }_${en.id }" class="inputSytle_1 fillblankInput"  >
											 </c:when>
											 <c:when test="${en.answerInputRow > 1 }">
												 <textarea name="qu_${en.quType }_${en.id }" rows="${en.answerInputRow }" class="inputSytle_2 fillblankInput" ></textarea>
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
								<fieldset data-role="controlgroup" >
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
										
								</fieldset>
								</div>
							</div>
							
							
							<%-- <div class="surveyQuItem">
								<div class="surveyQuItemContent">
									<div class="quCoTitle">
										<legend>
										<span>${i.count }、</span>
										<span>${en.quTitle}</span>
										</legend>
									</div>
									<div class="quCoItem quOrderByCoItem">
										<div  class="quOrderByLeft">
										<ul class="quOrderByLeftUl">
										<c:forEach items="${en.quOrderbys }" var="item">
											<li class="quCoItemUlLi">
												<label class="editAble quCoOptionEdit" style="padding: 5px;background: #EDEDED;">${item.optionName }
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
							 --%>
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
				
				
				<li class="li_surveyQuItemBody surveyQu_${pageNo }"  style="${pageNo gt 1 ?'display: none':''}">
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
								<div class="surveyQuItemContent" style="margin-bottom: 0px;min-height:20px;">
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

  <div data-role="footer" >
  	<h3>Powered by <a href="http://diaowen.net/index-m.jsp" style="text-decoration: none;" rel="external">DWSurvey</a></h3>
  </div>
</div>
</form>
<div id="fixedMsg" style="position: fixed;top: 0px;width: 100%;padding: 10px;text-align: center;font-size: 18px;letter-spacing: 4px;line-height: 56px;background-color: #111;background-color: rgba(17,17,17,0.5);color: #fff;color: rgba(255,255,255,0.5);z-index: 200;display: none;"></div>
<%@ include file="/WEB-INF/page/layouts/other.jsp"%>
</body>
</html>