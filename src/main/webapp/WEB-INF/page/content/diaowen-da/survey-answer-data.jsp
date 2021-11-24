<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${ctx }/js/dw/collect.js"></script>

<link href="${ctx }/js/plugs/jquery-ui-1.10.3.custom/css/mycss/jquery-ui-1.10.3.custom.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${ctx }/js/plugs/bootstrap-3.3.0-dist/dist/css/bootstrap.css">
<script src="${ctx }/js/plugs/bootstrap-3.3.0-dist/dist/js/bootstrap.js"></script>
<link href="${ctx }/js/plugs/font-awesome-4.2.0/css/font-awesome.css" rel="stylesheet">
<title>原始数据</title>
</head>
<body>
	<input type="hidden" id="id" name="id" value="${survey.id }">

	<jsp:include page="menu.jsp"></jsp:include>

	<div style="clear: both;"></div>
	<div id="dwBody" >
		<div id="dwBodyContent" class="bodyCenter" style="border:1px solid #C1DAEC;">
		<div id="dwBodyUser">
			<div class="surveyCollectMiddle">
				
				<div class="surveyCollectTop">
					<div class="surveyCollectTitleDiv">
						<span class="surveyCollectTitle">${survey.surveyName }</span>
						<div class="scmTabRight" >
							<c:choose>
								<c:when test="${survey.surveyState eq 0 }">
									<a href="" class="surveyStateBtn sbtn25 sbtn25_2" style="color: #599fd1;">开始收集</a>
								</c:when>
								<c:when test="${survey.surveyState eq 1 }">
									<a href="" class="surveyStateBtn sbtn25 sbtn25_2">停止收集</a>
								</c:when>
								<c:when test="${survey.surveyState eq 2 }">
									<a href="" class="surveyStateBtn sbtn25 sbtn25_2">重新打开收集</a>
								</c:when>
							</c:choose>
							
						</div>
					</div>
					<div class="surveyCollectInfoDiv">
						<span class="surveyCollectInfoLeft">
						状态：<span class="collectInfoSpan surveyStateText">${survey.surveyState eq 0 ? '设计中':survey.surveyState eq 1?'收集中':survey.surveyState eq 2?'收集完成':'' }</span>&nbsp;&nbsp;&nbsp;&nbsp;
						参加人数：<span class="collectInfoSpan">${survey.answerNum }</span>
						</span>
						<span class="surveyCollectInfoRight">
						创建时间：<span class="collectInfoSpan"><fmt:formatDate value="${survey.createDate }" pattern="yyyy年MM月dd日 HH时mm分" /> </span>
						</span>
					</div>
				</div>
				
				<div class="surveyCollectMiddleContent">
					
					<div class="collect_1_content">
					
					<div style="padding:0px 60px;">
						<div style="" >
							<div style="overflow: auto;">
								<div style="float: right;" >
									<div class="btn-group">
									<a href="${ctx }/da/my-survey-answer/exportXLS.do?surveyId=${survey.id }" class="dw_btn025 btn btn-default"><i class="fa fa-download"></i>&nbsp;导出数据</a>
									</div>
								</div>
							</div>
							<!-- 添加收件人 -->
							<!-- <div><a href="" class="addInboxUser"><span class="addInboxUserLeft">&nbsp;</span><span>添加收件人</span></a></div> -->
							<div style="padding: 5px;display: none;"><a href="http://www.diaowen.net/wenjuan/${survey.sid }.html" class="" >查看问卷</a></div>
							<div style="padding-top: 5px;">
								<table class="emailInboxList contacts-table" width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr>
										<th align="left" style="padding-left: 20px;">回答ip</th>
										<th style="text-align:center;" >回答时间</th>
										<th style="text-align: center;">回答的题数</th>
										<th style="text-align: center;">操作</th>
									</tr>
									<c:forEach items="${page.result }" var="en">
									<tr>
										<td align="left" style="padding-left: 20px;text-align: left;" width="150">${en.ipAddr }</td>
										<td><fmt:formatDate value="${en.endAnDate }" pattern="yyyy年MM月dd日 HH时mm分ss秒" /></td>
										<td>
											<span style="color: #5D71A7;">${en.completeItemNum }</span>
										</td>
										<td>
										   <div class="btn-group surveyLeftBtnGroup" >
											  <a class="btn btn-default" href="${ctx }/da/my-survey-answer/responseAnswer.do?answerId=${en.id}" title="查看" data-toggle="tooltip" data-placement="top" target="_blank" ><i class="fa fa-eye"></i></a>
											  <a class="btn btn-default deleteSurveyAnswer" href="${ctx}/da/my-survey-answer/delete.do?answerId=${en.id}" title="删除" data-toggle="tooltip" data-placement="top" ><i class="fa fa-trash-o fa-fw"></i></a>
										  </div>
										</td>
									</tr>
									</c:forEach>
								</table>
							</div>
							
							<div style="padding-top: 15px;text-align: center;">
									<div class="btn-group">
										<c:if test="${page.pageNo > 1}">
											<a href="${ctx }/da/my-survey-answer/answer.do?surveyId=${surveyId }&pageNo=${page.pageNo-1}" class="btn btn-default">&lt;</a>
										</c:if>
										<c:if test="${page.startpage > 1}">
											<a href="${ctx }/da/my-survey-answer/answer.do?surveyId=${surveyId }&pageNo=1" class="btn btn-default">1</a>
											<c:if test="${page.startpage > 2 }">
												<%--<span>...</span>--%>
											</c:if>
										</c:if>
										<c:forEach begin="${page.startpage }" end="${page.endpage }" var="en">
											<c:choose>
												<c:when test="${page.pageNo eq en }"><a href="${ctx }/da/my-survey-answer/answer.do?surveyId=${surveyId }&pageNo=${en }" class="btn btn-default" style="background: #D3DEED;">${en }</a></c:when>
												<c:otherwise><a href="${ctx }/da/my-survey-answer/answer.do?surveyId=${surveyId }&pageNo=${en}" class="btn btn-default">${en }</a></c:otherwise>
											</c:choose>
										</c:forEach>
										<c:if test="${page.totalPage > (page.endpage)}">
											<c:if test="${page.totalPage > (page.endpage+1)}">
												<%--<span>...</span>--%>
											</c:if>
											<a href="${ctx }/da/my-survey-answer/answer.do?surveyId=${surveyId }&pageNo=${page.totalPage}" class="btn btn-default">${page.totalPage }</a>
										</c:if>
										<c:if test="${page.totalPage > page.pageNo}">
											<a href="${ctx }/da/my-survey-answer/answer.do?surveyId=${surveyId }&pageNo=${page.pageNo+1}" class="btn btn-default">&gt;</a>
										</c:if>
										
									</div>
								</div>
							
							
						</div>
					</div>
					</div>
					
					
					
				</div>
			</div>
			
		</div>
		</div>
	</div>
<script type="text/javascript">

$(".scmcSurveySNSShare textarea").focus(function(){
	$(this).css({"background-color":"#FDF9CD","border-color":"#98C5C3"});
});
$(".scmcSurveySNSShare textarea").blur(function(){
	$(this).css("background-color","#F9F7D7");	
});

$(".deleteSurveyAnswer").click(function(){
	var thTr=$(this).parents("tr");
	if(confirm("确定删除答卷数据？")){
		var url=$(this).attr("href");
		var data="";
		$.ajax({
			url:url,
			data:data,
			type:"post",
			success:function(msg){
				if(msg==="true"){
					thTr.remove();
				}else{
					alert("抱歉，删除失败！");
				}
			}
		});
	}
	return false;
});

</script>
</body>
</html>