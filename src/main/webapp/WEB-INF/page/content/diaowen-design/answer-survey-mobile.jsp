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
<link href="${ctx }/js/plugs/font-awesome-4.2.0/css/font-awesome.css" rel="stylesheet">

<link href="${ctx}/js/plugs/validate/jquery.validate.css" type="text/css" rel="stylesheet" />

<script type="text/javascript">
$(document).ready(function(){
	
});
</script>
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
<form id="surveyForm" action="${ctx }/response!saveMobile.action" method="post" data-ajax="false">
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
					
					<c:when test="${en.quType eq 'SCORE' }">
					<li class="li_surveyQuItemBody surveyQu_${pageNo }"  style="${pageNo gt 1 ?'display: none':''}">
						<div class="surveyQuItemBody">
							<div class="initLine"></div>
							<div class="quInputCase" style="display: none;">
								<input type="hidden" class="quType" value="SCORE">
								<input type="hidden" class="quId" value="${en.id }">
								<input type="hidden" class="orderById" value="${en.orderById }"/>
								<input type="hidden" class="isRequired" value="${en.isRequired }">
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
									<div class="quCoTitle">
										<legend>
											<span class="quTitleNum">${i.count }、</span>
											<span class="quTitleText">${en.quTitle}</span>
										</legend>
									</div>
									<div class="quCoItem">
											<c:forEach items="${en.quScores }" var="item">
											<div class="scoreRow quScoreOptionTr">
												<input class="dwScoreOptionId" value="${item.id }" disabled="disabled" type="hidden"/>
												<input type="hidden" class="answerTag" value="0" >
												<fieldset class="starRating" data-role="controlgroup" >
													<legend>${item.optionName }</legend>
													<i class="fa fa-star-o"></i>
													<i class="fa fa-star-o"></i>
													<i class="fa fa-star-o"></i>
													<i class="fa fa-star-o"></i>
													<i class="fa fa-star-o"></i>
												</fieldset>
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
					
					<%-- 矩阵单选题 --%>
					<c:when test="${en.quType eq 'CHENRADIO' }">
					<li class="li_surveyQuItemBody surveyQu_${pageNo }"  style="${pageNo gt 1 ?'display: none':''}">
						<div class="surveyQuItemBody">
							<div class="initLine"></div>
							<div class="quInputCase" style="display: none;">
								<input type="hidden" class="quType" value="CHENRADIO">
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
								<input type="hidden" name="qu_${en.quType }_${en.id }" value="item_qu_${en.quType }_${en.id }_" />
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
									<c:forEach items="${en.rows }" var="rowItem">
										<fieldset data-role="controlgroup" class="dwQuCoChenRowTr">
											<legend>${rowItem.optionName }</legend>
											<input type="hidden" class="answerTag" value="0" >
											<c:forEach items="${en.columns }" var="columnItem">
												<div class="dwQuOptionItemContent">
												<label for="item_qu_${en.quType }_${en.id }_${rowItem.id }_${columnItem.id}">${columnItem.optionName }</label>
												<input type="hidden" class="dwChenInputTemp" disabled="disabled" value="${rowItem.id }:${columnItem.id }">
												<input id="item_qu_${en.quType }_${en.id }_${rowItem.id }_${columnItem.id}" name="item_qu_${en.quType }_${en.id }_${rowItem.id }" value="${columnItem.id }" type="radio">
												</div>
											</c:forEach>
										</fieldset>
									</c:forEach>
										
									</div>
									
									<div style="clear: both;"></div>
								</div>
							</div>
					</div>
					</li>
					</c:when>
					
					
					
					<%--矩阵多选题 --%>
					<c:when test="${en.quType eq 'CHENCHECKBOX' }">
					<li class="li_surveyQuItemBody surveyQu_${pageNo }"  style="${pageNo gt 1 ?'display: none':''}">
						<div class="surveyQuItemBody">
							<div class="initLine"></div>
							<div class="quInputCase" style="display: none;">
								<input type="hidden" class="quType" value="CHENCHECKBOX">
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
								<input type="hidden" name="qu_${en.quType }_${en.id }" value="item_qu_${en.quType }_${en.id }_" />
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
										
										<c:forEach items="${en.rows }" var="rowItem">
										<input type="hidden" name="item_qu_${en.quType }_${en.id }_${rowItem.id }"  value="ck_item_qu_${en.quType }_${en.id }_${rowItem.id }_" />
										<input type="hidden" class="answerTag" value="0" >
										<fieldset data-role="controlgroup" class="dwQuCoChenRowTr">
											<legend>${rowItem.optionName }</legend>
											<c:forEach items="${en.columns }" var="columnItem">
											<div class="dwQuOptionItemContent">
												<label for="ck_item_qu_${en.quType }_${en.id }_${rowItem.id }_${columnItem.id}">${columnItem.optionName }</label>
												<input type="hidden" class="dwChenInputTemp" disabled="disabled" value="${rowItem.id }:${columnItem.id }">
												<input id="ck_item_qu_${en.quType }_${en.id }_${rowItem.id }_${columnItem.id}"  name="ck_item_qu_${en.quType }_${en.id }_${rowItem.id }_${columnItem.id}" value="${columnItem.id }" type="checkbox">
											</div> 
											</c:forEach>
										</fieldset>
									</c:forEach>
										
									</div>
									<div style="clear: both;"></div>
								</div>
							</div>
					</div>
					</li>
					</c:when>
					
					<%-- 矩阵填空题 --%>
					<c:when test="${en.quType eq 'CHENFBK' }">
					<li class="li_surveyQuItemBody surveyQu_${pageNo }"  style="${pageNo gt 1 ?'display: none':''}">
						<div class="surveyQuItemBody">
							<div class="initLine"></div>
							<div class="quInputCase" style="display: none;">
								<input type="hidden" class="quType" value="CHENFBK">
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
								<input type="hidden" name="qu_${en.quType }_${en.id }" value="item_qu_${en.quType }_${en.id }_" />
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
										
										<c:forEach items="${en.rows }" var="rowItem">
										<input type="hidden" name="item_qu_${en.quType }_${en.id }_${rowItem.id }"  value="fbk_item_qu_${en.quType }_${en.id }_${rowItem.id }_" />
										<fieldset data-role="controlgroup" >
											<legend>${rowItem.optionName }</legend>
											<c:forEach items="${en.columns }" var="columnItem">
											<div class="dwQuChenFbkOptionItemContent">
											 	<label for="fbk_item_qu_${en.quType }_${en.id }_${rowItem.id }_${columnItem.id }" class="ui-hidden-accessible">姓名</label>
												<input placeholder="${columnItem.optionName }" id="fbk_item_qu_${en.quType }_${en.id }_${rowItem.id }_${columnItem.id }" name="fbk_item_qu_${en.quType }_${en.id }_${rowItem.id }_${columnItem.id }" type="text" class="dwChenMFillblankInput">
												<input type="hidden" class="dwChenInputTemp" disabled="disabled" value="${rowItem.id }:${columnItem.id }">
												<input type="hidden" class="answerTag" value="0" >
											</div> 
											</c:forEach>
										</fieldset>
									</c:forEach>
										
									</div>
									<div style="clear: both;"></div>
								</div>
							</div>
					</div>
					</li>
					</c:when>
					
					<%-- 矩阵评分题 --%>
					<c:when test="${en.quType eq 'CHENSCORE' }">
					<li class="li_surveyQuItemBody surveyQu_${pageNo }"  style="${pageNo gt 1 ?'display: none':''}">
						<div class="surveyQuItemBody">
							<div class="initLine"></div>
							<div class="quInputCase" style="display: none;">
								<input type="hidden" class="quType" value="CHENSCORE">
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
								<input type="hidden" name="qu_${en.quType }_${en.id }" value="item_qu_${en.quType }_${en.id }_" />
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
										
										<c:forEach items="${en.rows }" var="rowItem">
											<input type="hidden" name="item_qu_${en.quType }_${en.id }_${rowItem.id }"  value="cs_item_qu_${en.quType }_${en.id }_${rowItem.id }_" />
											<c:forEach items="${en.columns }" var="columnItem">
												<div class="scoreRow">
													<fieldset class="starRating" data-role="controlgroup" >
													<legend>${rowItem.optionName }&nbsp;&nbsp;${columnItem.optionName }</legend>
													<i class="fa fa-star-o"></i>
													<i class="fa fa-star-o"></i>
													<i class="fa fa-star-o"></i>
													<i class="fa fa-star-o"></i>
													<i class="fa fa-star-o"></i>
													</fieldset>
													<input name="cs_item_qu_${en.quType }_${en.id }_${rowItem.id }_${columnItem.id}" value="0"  type="hidden" class="scoreNumInput" >
													</div>
													<input type="hidden" class="dwChenInputTemp" disabled="disabled" value="${rowItem.id }:${columnItem.id }">
													<input type="hidden" class="answerTag" value="0" >
											</c:forEach>
									</c:forEach>
										
										<%-- <div class="quCoItemLeftChenTableDiv">
										<table class="quCoChenTable" >
												<tr><td></td>
													<c:forEach items="${en.columns }" var="columnItem">
														<td class="quChenColumnTd"><label class="editAble quCoOptionEdit">${columnItem.optionName }</label></td>
													</c:forEach>
												</tr>
												<c:forEach items="${en.rows }" var="rowItem">
												<tr><td class="quChenRowTd"><label class="editAble quCoOptionEdit">${rowItem.optionName }</label></td>
														<c:forEach items="${en.columns }" var="columnItem">
														<td>评分 </td>
														</c:forEach>
													</tr>
												</c:forEach>
										</table>
										</div> --%>
										
									</div>
									<div style="clear: both;"></div>
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
											<input name="jcaptchaInput" type="text" class="" style="" autocomplete="off">
											<img id="register-jcaptchaImg" onclick="refreshAutoCode('register-jcaptchaImg')" src="${ctx }/jcaptcha.action" alt="验证码" height="40"></span>
											<a href="javascript:refreshAutoCode('register-jcaptchaImg');" style="margin-left: 5px;" hidefocus="true">换一张</a>
											<span class="frm_desc">验证码，区分大小写</span>
											<p class="valid-msg fail" style="display: none;"><i>●</i><span class="msg_content">验证码错误，请重新输入</span></p>
										</div>
									</div>
									<div class="errorItem" style="display: none;"><label for="" class="error">验证码错误，请重新输入！</label></div>
									<%-- 验证码：<input type="text" size="8" name="jcaptchaInput">
									&nbsp;<img id="jcaptchaImg" alt="点击刷新" src="${ctx }/jcaptcha.action"  align="top">
									&nbsp;点击图片刷新 --%>
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

  <div data-role="footer">
	  <%--请委必保留以下内容，自觉保留优先技术支持。--%>
  	<h3>Powered by <a href="http://diaowen.net/index-m.jsp" style="text-decoration: none;" rel="external">DWSurvey</a></h3>
  </div>
</div>

</form>

<div id="fixedMsg" style="position: fixed;top: 0px;width: 100%;padding: 10px;text-align: center;font-size: 18px;letter-spacing: 4px;line-height: 56px;background-color: #111;background-color: rgba(17,17,17,0.5);color: #fff;color: rgba(255,255,255,0.5);z-index: 200;display: none;"></div>

<script type="text/javascript">
$(document).ready(function(){
	//分页设置 nextPage_a prevPage_a
	$(".nextPage_a").click(function(){
		var thParent=$(this).parent();
		var nextPageNo=thParent.find("input[name='nextPageNo']").val();
		$(".li_surveyQuItemBody").hide();
		$(".surveyQu_"+nextPageNo).fadeIn("slow");
		//$(window).scrollTop(10);
		$("html,body").animate({scrollTop:10},500);
		return false;
	});
	$(".prevPage_a").click(function(){
		var thParent=$(this).parent();
		var prevPageNo=thParent.find("input[name='prevPageNo']").val();
		$(".li_surveyQuItemBody").hide();
		$(".surveyQu_"+prevPageNo).fadeIn("slow");
		$(window).scrollTop(10);
		return false;
	});
	
	/**排序题*/
	$(".m_clickQuOrderItem").click(function(){
		var visibleOrderbyNum=$(this).parents().find(".m_orderby_num:visible");
		var thOrderbyNum=$(this).find(".m_orderby_num");
		if($(this).find(".m_orderby_num:visible")[0]){
			
		}else{
			
			var thNum=0;
			$(this).append("<select class='m_orderby_sel' > </select>");
			var mOrderbySel=$(this).find(".m_orderby_sel");
			var quOrderItems=$(this).parents().find(".m_clickQuOrderItem");
			$.each(quOrderItems,function(i,item){
				mOrderbySel.append("<option value='"+(i+1)+"'>移至排序"+(i+1)+"</option>");
				var targetHid=$(this).parents(".ui-controlgroup-controls ").find(".quOrderItemHidInput[value='"+(i+1)+"']");
				if(!targetHid[0] && thNum===0){
					thNum=(i+1);
				}
			});
			
			thOrderbyNum.text(thNum);
			thOrderbyNum.show();
			mOrderbySel.val(thNum)
			$(this).find(".quOrderItemHidInput").val(thNum);
		}
		bindEvent();
		
		runlogic($(this));
		validateCheck($(this).parents(".li_surveyQuItemBody"),false);
		return false;
	});
	function bindEvent(){
		$(".m_orderby_sel").unbind();
		$(".m_orderby_sel").change(function(){
			//交换个排
			var thOrderbyItem=$(this).parents(".m_clickQuOrderItem");
			var thOrderByNum=thOrderbyItem.find(".quOrderItemHidInput").val();
			var thChangeNum=$(this).val();
			var targetHid=$(this).parents(".ui-controlgroup-controls ").find(".quOrderItemHidInput[value='"+thChangeNum+"']");
			if(targetHid[0]){
				var targetOrderbyItem=targetHid.parents(".m_clickQuOrderItem");
				targetOrderbyItem.find(".m_orderby_num").text(thOrderByNum);
				targetOrderbyItem.find(".quOrderItemHidInput").val(thOrderByNum);
				targetOrderbyItem.find(".m_orderby_sel").val(thOrderByNum);
			}
			
			thOrderbyItem.find(".m_orderby_num").text(thChangeNum);
			thOrderbyItem.find(".quOrderItemHidInput").val(thChangeNum);
			thOrderbyItem.find(".m_orderby_sel").val(thChangeNum);
			
		});
	}
	
	
	/**评分题*/
	$(".starRating  .fa").hover(function(){
		var prevAll=$(this).prevAll();
		prevAll.removeClass("fa-star-o");
		prevAll.addClass("fa-star");
		$(this).removeClass("fa-star-o");
		$(this).addClass("fa-star");
	},function(){
		var scoreRow=$(this).parents(".scoreRow");
		var starNum=scoreRow.find(".scoreNumInput").val();
		if(starNum==""&&starNum<=0){
			var thParent=$(this).parent();
			var fas=thParent.find(".fa");
			fas.removeClass("fa-star");
			fas.addClass("fa-star-o");	
		}
	}); 
	
	$(".starRating  .fa").click(function(){
		var thParent=$(this).parent();
		var fas=thParent.find(".fa");
		fas.removeClass("fa-star");
		fas.addClass("fa-star-o");	
		
		var prevAll=$(this).prevAll();
		prevAll.removeClass("fa-star-o");
		prevAll.addClass("fa-star");
		$(this).removeClass("fa-star-o");
		$(this).addClass("fa-star");
		
		var scoreRow=$(this).parents(".scoreRow");
		var starNum=0;
		if(prevAll[0]){
			starNum=prevAll.size();
		}
		scoreRow.find(".scoreNumInput").val(starNum+1);
		
		runlogic($(this));
		 
		validateCheck($(this).parents(".li_surveyQuItemBody"),false);
		return false;
	});
	
	
	
	//表单验证
	/**初始化表单骓证配置**/
	function validateForms(){
		var result=true;
		var surveyQuItemBodys=$(".li_surveyQuItemBody");
		var firstError=null;
		$.each(surveyQuItemBodys,function(){
			var quItemBody=$(this);
			if(!validateCheck(quItemBody,true)){
				//定位到这题 
				if(firstError==null){
					firstError=quItemBody;
				}
				result=false;
			}
			// || quType==="CHENRADIO" || quType==="CHENCHECKBOX" || quType==="CHENSCORE" || quType==="CHENFBK"
		});
		if(firstError!=null){
			$(window).scrollTop(firstError.offset().top);
		}

		if($("#jcaptchaImgBody").is(":visible")){
			var jcaptchaInput = $("input[name='jcaptchaInput']").val();
			if(jcaptchaInput===""){
				$("#jcaptchaImgBody .errorItem").show();
				result = false;
			}else{
				$("#jcaptchaImgBody .errorItem").hide();
			}
		}

		return result;
	}
	
	
	function validateCheck(quItemBody,isSubForm){
		if(quItemBody.is(":visible")){

			var quId=quItemBody.find(".quId").val();
			var quType=quItemBody.find(".quType").val();
			var isRequired=quItemBody.find(".isRequired").val();
			
			var validateStatus=false;

			if(isRequired==="0"){
				validateStatus = true;
				return true;
			}

			if(quType==="RADIO"){
				validateStatus=quItemBody.find("input[type='radio']:checked")[0];
			}else if(quType==="CHECKBOX"){
				validateStatus=quItemBody.find("input[type='checkbox']:checked")[0];
			}else if(quType==="FILLBLANK"){
				validateStatus=quItemBody.find(".fillblankInput").val()!="";
			}else if(quType==="ORDERQU"){
				//quItemBody.find(".quOrderByLeftUl label");
				validateStatus=!quItemBody.find(".quOrderItemHidInput[value=0]")[0];
			}else if(quType==="SCORE"){
				
				validateStatus=true;
				var quScoreOptionTrs=quItemBody.find(".quScoreOptionTr");
				$.each(quScoreOptionTrs,function(){
					var scoreNumInput=$(this).find(".scoreNumInput");
					if(scoreNumInput.val()===""){
						validateStatus=false;
						return false;
					}
				});
				
			}else if(quType==="MULTIFILLBLANK"){
				
				validateStatus=true;
				var quScoreOptionTrs=quItemBody.find(".mFillblankTableTr");
				$.each(quScoreOptionTrs,function(){
					var scoreNumInput=$(this).find(".dwMFillblankInput");
					if(scoreNumInput.val()===""){
						validateStatus=false;
						return false;
					}
				});
				
			}else if(quType==="CHENRADIO"){
				validateStatus=true;
				var quScoreOptionTrs=quItemBody.find(".dwQuCoChenRowTr");
				$.each(quScoreOptionTrs,function(){
					var tempInputs=$(this).find("input[type='radio']:checked");
					if(!tempInputs[0]){
						validateStatus=false;
						return false;
					}
				});
			}else if(quType==="CHENCHECKBOX"){
				validateStatus=true;
				var quScoreOptionTrs=quItemBody.find(".dwQuCoChenRowTr");
				$.each(quScoreOptionTrs,function(){
					var tempInputs=$(this).find("input[type='checkbox']:checked");
					if(!tempInputs[0]){
						validateStatus=false;
						return false;
					}
				});
			}else if(quType==="CHENSCORE"){
				/* var quChenScores=quItemBody.find(".quChenScoreSelect");
				validateStatus=true;
				$.each(quChenScores,function(){
					var tempInputs=$(this);
					if(tempInputs.val()==="0"){
						validateStatus=false;
						return false;
					}
				}); */
				validateStatus=!(quItemBody.find(".scoreNumInput[value=0]")[0]);
			}else if(quType==="CHENFBK"){
				var dwCMFBKs=quItemBody.find(".dwChenMFillblankInput");
				validateStatus=true;
				$.each(dwCMFBKs,function(){
					var tempInputs=$(this);
					if(tempInputs.val()===""){
						validateStatus=false;
						return false;
					}
				});
			}else if(quType==="submitSurveyBtn" || quType==="PARAGRAPH" || quType==="PAGETAG"){
				return true;
			}
			
		}else{
			validateStatus=true;
		}
		
		if(validateStatus){
			quItemBody.find(".errorItem").remove();
		}else{
			if(isSubForm && !quItemBody.find(".errorItem")[0]){
				var errorHtml="<div class=\"errorItem\"><label for=\"\" class=\"error\">请检查题目答案，为必答项！</label></div>";
				quItemBody.find(".surveyQuItemContent").append(errorHtml);
			}
		}
		return validateStatus;
	}
	
	$(".submitSurvey").click(function(){
		if(validateForms()){
			$("#surveyForm").submit();
		}
		return false;
	});
	
	//绑定操作事件
	$("#dwSurveyQuContent input[type='radio'],#dwSurveyQuContent input[type='checkbox']").change(function(){
		runlogic($(this));
		validateCheck($(this).parents(".li_surveyQuItemBody"),false);
	});
	
	//填空题
	$(".fillblankInput,.dwMFillblankInput,.dwChenMFillblankInput").blur(function(){
		 //$(this).css("borderColor","#D6D6FF");
		 runlogic($(this));
		 validateCheck($(this).parents(".li_surveyQuItemBody"),false);
	});
	
	
	function resetQuNum(){
		var quTitleNums=$(".quTitleNum");
		$.each(quTitleNums,function(i,item){
			$(this).text((i+1)+"、")
		});
	}
	
	resetQuNum();
	
	//处理标题因有p标签影响题目序号换行的问题
	var quTitleNums=$(".quTitleNum");
	$.each(quTitleNums,function(){
		var nextObj=$(this).next();
		if(nextObj[0]){
			var childrens=nextObj.children();
			if(childrens[0]){
				var firstChildren=childrens.first();
				if(firstChildren.is("p")){
					$(this).prependTo(firstChildren);
				}
			}
		}
	});
	
	
});




/******************************处理题目逻辑设置 **************************************/
//处理题目逻辑设置 


/** 答题触发事件 **/

//初始化 处理默认逻辑跳转为显示，则先隐藏元素
var quLogics=$("#dwSurveyQuContent .quLogicItem");
$.each(quLogics,function(){
	var loginItem=$(this);
	var cgQuItemId=loginItem.find(".cgQuItemId").val();
	var skQuId=loginItem.find(".skQuId").val();
	var logicId=loginItem.find(".logicId").val();
	var logicType=loginItem.find(".logicType").val();
	
	if(logicType==="2"){
		//逻辑类型为“显示”2  则初始化为隐藏
		var hidQuItemBody=$(".quId[value='"+skQuId+"']").parents(".li_surveyQuItemBody");
		hidQuItemBody.hide();
		hidQuItemBody.addClass("hidFor"+logicId);
		hidQuItemBody.find(".answerTag").attr("disabled",true);
	}
});

function runlogic(thFormElementObj){
	//thFormElementObj 当前关联的form表单元素
	var quItemBody=thFormElementObj.parents(".li_surveyQuItemBody");
	
	var quLogicItems=quItemBody.find(".quLogicItem");
	
	if(quLogicItems[0]){
		
		var quInputCase=quItemBody.find(".quInputCase");
		var quId=quInputCase.find(".quId").val();
		var quType=quInputCase.find(".quType").val();
		
		//$("input[name='qu_"+quType+"_"+quId+"']").change(function(){});
		
		if(quType==="RADIO" || quType==="CHECKBOX" || quType==="SCORE" || quType==="MULTIFILLBLANK" || quType==="CHENRADIO" || quType==="CHENCHECKBOX" || quType==="CHENSCORE" || quType==="CHENFBK" ){
			
			
			//判断是否选中
			var quLgoicItem=null;
			//var thVal=thFormElementObj.val();
			
			//遍历每个逻辑设置
			var quOptionItems=null;
			if(quType==="RADIO" || quType==="CHECKBOX"){
				quOptionItems=quItemBody.find(".dwQuOptionItemContent");
				//thVal=thFormElementObj.val();
			}else if(quType==="SCORE"){
				quOptionItems=quItemBody.find(".quScoreOptionTr");
				//thVal=thFormElementObj.text();
			}else if(quType==="MULTIFILLBLANK"){
				quOptionItems=quItemBody.find(".mFillblankTableTr");
			}else if(quType==="CHENRADIO" || quType==="CHENCHECKBOX"){
				quOptionItems=quItemBody.find(".dwQuOptionItemContent");
			}else if(quType==="CHENFBK" ){
				quOptionItems=quItemBody.find(".dwQuChenFbkOptionItemContent");
			}
			
			$.each(quLogicItems,function(){
				var loginItem=$(this);
				var cgQuItemId=loginItem.find(".cgQuItemId").val();
				var skQuId=loginItem.find(".skQuId").val();
				var logicId=loginItem.find(".logicId").val();
				var logicType=loginItem.find(".logicType").val();
				
				var geLe=null;
				var scoreNum=null;
				if(quType==="SCORE"){
					geLe=loginItem.find(".geLe").val();
					scoreNum=loginItem.find(".scoreNum").val();
				}

				//过滤优先级
				var isbreak=false;
				$.each(quOptionItems,function(){
					var quCoItem=$(this);
					
					var quInput=null;
					var logicStatus=false;
					var curQuItemId=null;
					if(quType==="RADIO"){
						quInput=quCoItem.find("input[type='radio']");
						logicStatus=quInput.prop("checked");
						curQuItemId=quInput.val();
					}else if(quType==="CHECKBOX"){
						quInput=quCoItem.find("input[type='checkbox']");
						logicStatus=quInput.prop("checked");
						curQuItemId=quInput.val();
					}else if(quType==="SCORE"){
						quInput=quCoItem.find(".dwScoreOptionId");
						var curScore=quCoItem.find(".scoreNumInput").val();
						if(curScore!=""){
							logicStatus = (geLe==="le" && curScore<=scoreNum) || (geLe==="ge" && curScore>=scoreNum);
						}
						curQuItemId=quInput.val();
					}else if(quType==="MULTIFILLBLANK"){
						quInput=quCoItem.find(".dwMFillblankOptionId");
						logicStatus=quCoItem.find(".dwMFillblankInput").val()!="";
						curQuItemId=quInput.val();
					}else if(quType==="CHENRADIO"){
						quInput=quCoItem.find("input[type='radio']");
						logicStatus=quInput.prop("checked");
						curQuItemId=quCoItem.find(".dwChenInputTemp").val();
					}else if(quType==="CHENCHECKBOX"){
						quInput=quCoItem.find("input[type='checkbox']");
						logicStatus=quInput.prop("checked");
						curQuItemId=quCoItem.find(".dwChenInputTemp").val();
					}else if(quType==="CHENFBK"){
						quInput=quCoItem.find(".dwChenMFillblankInput");
						logicStatus=quInput.val()!="";
						curQuItemId=quCoItem.find(".dwChenInputTemp").val();
					}
					
					if(curQuItemId===cgQuItemId){
						
						if(logicType==="1"){
							//跳转
							if(logicStatus){
								//逻辑选项被选中状态，激活状态
								skQuestion(quItemBody.next(),skQuId,logicId,function(){
									//重新编题号
									
								});
								if(skQuId==="1" || skQuId==="2" ){
									isbreak=true;
								}
							}else{
								/*
								//逻辑选项未被选中状态，未激活
								//$(".hidFor"+loginId).slideDown("slow");
								$(".hidFor"+loginId).show();
								//$(".hidFor"+loginId).fadeIn();
								$(".hidFor"+loginId).removeClass("hidFor"+loginId);
								//回答标记与逻辑设置没有关系
								$(".hidFor"+loginId).find(".answerTag").attr("disabled",false);
								*/
								var hidQuItemBodys=$(".hidFor"+logicId);
								$(".hidFor"+logicId).removeClass("hidFor"+logicId);
								$.each(hidQuItemBodys,function(){
									var thQuItemBodyClass=$(this).attr("class");
									if(thQuItemBodyClass.indexOf("hidFor")<0){
										$(this).show();
										//$(".hidFor"+loginId).fadeIn();
										//回答标记与逻辑设置没有关系
										$(this).find(".answerTag").attr("disabled",false);
									}
								});
							}	
						}else{
							//逻辑类型为“显示” quType=2
							if(logicStatus){
								//逻辑选项被选中状态，激活状态  显示题
								
								var hidQuItemBodys=$(".hidFor"+logicId);
								$(".hidFor"+logicId).removeClass("hidFor"+logicId);
								$.each(hidQuItemBodys,function(){
									var thQuItemBodyClass=$(this).attr("class");
									if(thQuItemBodyClass.indexOf("hidFor")<0){
										$(this).show();
										$(this).find(".answerTag").attr("disabled",false);
									}
								});
								
							}else{
								/*  隐藏题
								*/
								var hidQuItemBody=$(".quId[value='"+skQuId+"']").parents(".li_surveyQuItemBody");
								hidQuItemBody.hide();
								hidQuItemBody.addClass("hidFor"+logicId);
								hidQuItemBody.find(".answerTag").attr("disabled",true);									
								
							}	
						}
						
						return false;
					}
				});
				
				if(isbreak){
					return false;
				}
				
			});
			
		}else if(quType==="FILLBLANK"){
			

			//遍历每个逻辑设置
			var quOptionItems=quItemBody.find(".dwQuOptionItemContent");
			var thVal=thFormElementObj.val();
			
			$.each(quLogicItems,function(){
				var loginItem=$(this);
				var cgQuItemId=loginItem.find(".cgQuItemId").val();
				var skQuId=loginItem.find(".skQuId").val();
				var logicId=loginItem.find(".logicId").val();
				var logicType=loginItem.find(".logicType").val();
				if(logicType==="1"){
					//跳转
					if(thVal!=""){
						//逻辑选项被选中状态，激活状态
						skQuestion(quItemBody.next(),skQuId,logicId,function(){
							//重新编题号
							
						});
						if(skQuId==="1" || skQuId==="2" ){
							isbreak=true;
						}
					}else{
						//逻辑选项未被选中状态，未激活
						//$(".hidFor"+loginId).slideDown("slow");
						
						var hidQuItemBodys=$(".hidFor"+logicId);
						$(".hidFor"+logicId).removeClass("hidFor"+logicId);
						
						$.each(hidQuItemBodys,function(){
							var thQuItemBodyClass=$(this).attr("class");
							if(thQuItemBodyClass.indexOf("hidFor")<0){
								$(this).show();
								//$(".hidFor"+loginId).fadeIn();
								//回答标记与逻辑设置没有关系
								$(this).find(".answerTag").attr("disabled",false);
							}
						});
					}
				}else{
					//显示
					//逻辑类型为“显示” quType=1
					if(thVal!=""){
						//逻辑选项被选中状态，激活状态  显示题
						var hidQuItemBodys=$(".hidFor"+logicId);
						$(".hidFor"+logicId).removeClass("hidFor"+logicId);
						$.each(hidQuItemBodys,function(){
							var thQuItemBodyClass=$(this).attr("class");
							if(thQuItemBodyClass.indexOf("hidFor")<0){
								$(this).show();
								$(this).find(".answerTag").attr("disabled",false);
							}
						});
						
					}else{
						/*  隐藏题
						*/
						var hidQuItemBody=$(".quId[value='"+skQuId+"']").parents(".li_surveyQuItemBody");
						hidQuItemBody.hide();
						hidQuItemBody.addClass("hidFor"+logicId);
						hidQuItemBody.find(".answerTag").attr("disabled",true);									
					}
				}
				
			});
			
		}
	}
	
	
}



function skQuestion(nextQuItemBody,skQuId,logicId,callback){
	
	if(nextQuItemBody[0]){
		//submitSurveyBtn
		var nextQuType=nextQuItemBody.find(".quType").val();
		var nextQuId=nextQuItemBody.find(".quId").val();
		var nextAnswerTag=nextQuItemBody.find(".answerTag");

		//var quType=quItemBody.find(".quType").val();
		//var quId=quItemBody.find(".quId").val();

		//判断跳转类型
		if(skQuId==null){
			//对于逻辑选项未被选中的情况
			
		}else if(nextQuItemBody.is(":hidden")){
			skQuestion(nextQuItemBody.next(),skQuId,logicId,function(){
				
			});
		}else if(nextQuType!="submitSurveyBtn" && nextQuType!="PAGETAG" && (skQuId==="1" || skQuId==="2" || nextQuId!=skQuId) ){
			//对于逻辑项是被选定的情况下
			nextAnswerTag.attr("disabled",true);
			//nextQuItemBody.slideUp("slow");
			nextQuItemBody.hide();
			//nextQuItemBody.fadeOut();
			nextQuItemBody.addClass("hidFor"+logicId);
			
			skQuestion(nextQuItemBody.next(),skQuId,logicId,function(){
				
			});
		}
	}else{
		callback();
	}
}


/******************************处理题目逻辑设置结束 **************************************/


function refreshAutoCode(codeImgId){
	$("#"+codeImgId).attr("src","${ctx}/jcaptcha.action");
}

//判则判断 
var url="${ctx}/response!ajaxCheckSurvey.action";
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

<%@ include file="/WEB-INF/page/layouts/other.jsp"%>
</body>
</html>