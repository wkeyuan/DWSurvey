<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">


<script type="text/javascript" src="${ctx }/js/dw/collect.js"></script>
<link href="${ctx }/js/plugs/font-awesome-4.2.0/css/font-awesome.css" rel="stylesheet">
<title>默认报告</title>
<script type="text/javascript">

</script>
<style type="text/css">
.dw_btn026{
	border: 1px solid transparent;
}
.suQuTable{
	/* border-left: 1px dashed #c5b6b6! important;
	border-right: 1px dashed #c5b6b6! important;
	border-bottom: 1px dashed #c5b6b6! important; */
	border: 1px solid #c5b6b6! important;
	width: 100%;
}
.anColumnTable{
	width: 100%;
}
.quTrOptions  td{
	border-top: 1px dashed #c5b6b6! important;
}
.rowItemTr td{
	border-top: 1px solid #c5b6b6! important;
}
.columnItemTr td{
	border-top: 1px dashed #c5b6b6! important;
}

.suQuTable .quTrOptions:FIRST-CHILD  td,.suQuTable .rowItemTr:FIRST-CHILD  td,.suQuTable .columnItemTr:FIRST-CHILD td {
 	border-top: none! important; 
}
.r-qu-body-title .quCoNum {
    float: left;
    padding: 5px 0px;
    padding-left: 8px;
}
.r-qu-body-title .quCoTitleText {
    float: left;
    padding: 5px 3px;
    cursor: text;
    table-layout: fixed;
    word-break: break-all;
    display: inline-block;
}
#content-tableList tr td{
	padding: 5px!important;
}
.higChartSvg{
	
}
</style>
</head>
<body>
	<input type="hidden" id="id" name="id" value="${surveyId }">
	
	<div class="creatgeSurveyStepBody">
		<div class="creatgeSurveyStepContent bodyCenter" >
				<ul class="createSsUl">
				<li><a href=""  class="clickHideMenu csscStep csscStep4"><span class="csscStepLeft">&nbsp;</span><span class="csscStepCenter">设计问卷</span><span class="csscStepRight">&nbsp;</span></a>
					<div class="a-w-sel">
		            	<div class="w-sel" style="margin-top: 4px;">
		                	<div class="selc">
		                    	<div class="selcc tbtag">
		                            <div class="seli"><a class="nx-1 sur_collectSet" href="#collectSet">收集规则</a></div>
		                            <div class="seli"><a class="nx-6 sur_edit" href="${ctx }/design/my-survey-design.action?surveyId=${surveyId}">问卷编辑</a></div>
		                        </div>
		                    </div>
		                </div>
		            </div>
				</li>
				<li><span class="csscStep csscStepLine"><span class="csscStepLeft">&nbsp;</span><span class="csscStepRight">&nbsp;</span></span></li>
				<li><a href="${ctx }/design/my-collect.action?surveyId=${surveyId }"  class="clickHideMenu csscStep csscStep5"><span class="csscStepLeft">&nbsp;</span><span class="csscStepCenter">数据收集</span><span class="csscStepRight" >&nbsp;</span></a>
					<div class="a-w-sel">
		            	<div class="w-sel" style="margin-top: 4px;">
		                	<div class="selc">
		                    	<div class="selcc tbtag">
		                            <div class="seli"><a class="nx-1" href="${ctx }/design/my-collect.action?surveyId=${surveyId}">答卷地址</a></div>
		                            <div class="seli"><a class="nx-2" href="">社交分享</a></div>
		                            <div class="seli"><a class="nx-3" href="${ctx }/design/my-collect.action?surveyId=${surveyId}&tabId=sitecomp">网站组件</a></div>
		                            <div class="seli"><a class="nx-3" href="${ctx }/design/my-collect.action?surveyId=${surveyId}&tabId=weixin">微信收集</a></div>
		                        </div>
		                    </div>
		                </div>
		            </div>
				</li>
				<li><span class="csscStep csscStepLine"><span class="csscStepLeft">&nbsp;</span><span class="csscStepRight">&nbsp;</span></span></li>
				<li><a href="${ctx }/da/survey-report!defaultReport.action?surveyId=${surveyId}"  class="clickHideMenu csscStep csscStep6 active"><span class="csscStepLeft">&nbsp;</span><span class="csscStepCenter">数据分析</span><span class="csscStepRight">&nbsp;</span></a>
				</li>
			</ul>
		</div>
	</div>
	
	<div style="">
		<div class="main-tabs-content bodyCenter">
			<div class="tab-content">
				<div class="tab-content-collectTab">
					<a href="${ctx }/da/survey-report!defaultReport.action?surveyId=${surveyId}" class="collectTab tabItem_1 active"><span class="collectTabItemLeft">&nbsp;</span><span>默认报告</span></a>
					<a href="${ctx }/da/my-survey-answer.action?surveyId=${surveyId}" class="collectTab tabItem_3"><span class="collectTabItemLeft">&nbsp;</span><span>原始数据</span></a>
					<a href="#" class="collectTab tabItem_3" style="display: none;"><span class="collectTabItemLeft">&nbsp;</span><span>问卷日志</span></a>
				</div>
			</div>
		</div>
	</div>
	<div style="clear: both;"></div>
	<div id="dwBody" >
		<div id="dwBodyContent" class="bodyCenter" style="border:1px solid #C1DAEC;">
		<div id="dwBodyUser">
			<div class="surveyCollectMiddle">
				
				
				<div class="surveyCollectTop">
					<div class="surveyCollectTitleDiv">
						<span class="surveyCollectTitle">${directory.surveyName }</span>
						<div class="scmTabRight" >
							<a href="" class="sbtn25 sbtn25_2">停止收集</a>
						</div>
					</div>
					<div class="surveyCollectInfoDiv">
						<span class="surveyCollectInfoLeft">
						状态：<span class="collectInfoSpan">收集中</span>&nbsp;&nbsp;&nbsp;&nbsp;
						参与人数：<span class="collectInfoSpan">${fn:length(anPage.result)}${directory.answerNum }</span>
						</span>
						<span class="surveyCollectInfoRight">
						创建时间：<span class="collectInfoSpan"><fmt:formatDate value="${directory.createDate }" pattern="yyyy年MM月dd日 HH:mm"/></span>
						</span>
					</div>
				</div>
				
				<div class="surveyCollectMiddleContent">
					<div style="padding: 15px 25px;overflow: auto;">
							<div style="overflow: auto;">
								<div style="float: left;" >
									<a href="" class="dw_btn025 tabpic active"><i class="fa fa-refresh"></i>&nbsp;刷新</a>
									<%-- <a href="${ctx }/da/survey-report!lineChart.action?surveyId=${surveyId }" class="dw_btn025 linepic" style="margin-left: 10px;"><i class="fa fa-bar-chart"></i>&nbsp;柱状图</a>
									<a href="${ctx }/da/survey-report!pieChart.action?surveyId=${surveyId }" class="dw_btn025 piepic " style="margin-left: 10px;"><i class="fa fa-pie-chart"></i>&nbsp;饼状图</a> --%> 
								</div>
								<div style="float: right;" >
									<%--<a href="${ctx }/da/my-survey-answer!exportXLS.action?surveyId=${surveyId }" class="dw_btn025"><i class="fa fa-download"></i>下载数据</a>--%>
									<!-- <a href="" class="dw_btn025"><i class="fa fa-share"></i>分享</a>-->
									</div> 
							</div>
							<div style="padding-top:8px;">
								<div class="" style="border: 1px solid #D1D6DD;padding: 0px;background: rgb(245, 245, 245);">
									<table id="content-tableList" width="100%"  cellpadding="0" cellspacing="0">

								<c:forEach items="${anPage.result}" var="en" varStatus="i">
								<tr id="quTr_${en.id }" >
									<td style="height: 32px;text-align: center;" width="30">${i.count}</td>
									<td>${en.answer}</td>
								</tr>
								</c:forEach>
								</table>
									
								</div>
							</div>			
					</div>
					
				</div>
			</div>
			
		</div>
		</div>
	</div>
<script type="text/javascript">
$(document).ready(function(){
	//.quTrOptions  td
});
</script>
</body>
</html>