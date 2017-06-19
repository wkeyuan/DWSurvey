<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${ctx }/js/plugs/zero-clipboard/ZeroClipboard.js"></script>
<script type="text/javascript" src="${ctx }/js/dw/collect.js"></script>
<title>已发送邮件 数据收集</title>
</head>
<body>
	<input type="hidden" id="id" name="id" value="${surveyId }">
	<input type="hidden" id="surveyState" name="surveyState" value="${survey.surveyState }">
	<div class="creatgeSurveyStepBody">
		<div class="creatgeSurveyStepContent bodyCenter">
			<ul class="createSsUl">
				<li><a href=""  class="clickHideMenu csscStep csscStep4"><span class="csscStepLeft">&nbsp;</span><span class="csscStepCenter">设计问卷</span><span class="csscStepRight">&nbsp;</span></a>
					<div class="a-w-sel">
		            	<div class="w-sel" style="margin-top: 4px;">
		                	<div class="selc">
		                    	<div class="selcc tbtag">
		                            <div class="seli"><a class="nx-1 sur_collectSet" href="#collectSet">收集规则</a></div>
		                            <div class="seli"><a class="nx-2" href="${ctx }/design/my-survey-design!previewDev.action?surveyId=${surveyId}">样式设置</a></div>
		                            <div class="seli"><a class="nx-6 sur_edit" href="${ctx }/design/my-survey-design.action?surveyId=${surveyId}">问卷编辑</a></div>
		                        </div>
		                    </div>
		                </div>
		            </div>
				</li>
				<li><span class="csscStep csscStepLine"><span class="csscStepLeft">&nbsp;</span><span class="csscStepRight">&nbsp;</span></span></li>
				<li><a href=""  class="csscStep csscStep5 active"><span class="csscStepLeft">&nbsp;</span><span class="csscStepCenter">数据收集</span><span class="csscStepRight" >&nbsp;</span></a></li>
				<li><span class="csscStep csscStepLine"><span class="csscStepLeft">&nbsp;</span><span class="csscStepRight">&nbsp;</span></span></li>
				<li><a href="${ctx }/da/survey-report!defaultReport.action?surveyId=${surveyId}"  class="clickHideMenu csscStep csscStep6"><span class="csscStepLeft">&nbsp;</span><span class="csscStepCenter">数据分析</span><span class="csscStepRight">&nbsp;</span></a>
					<div class="a-w-sel">
		            	<div class="w-sel" style="margin-top: 4px;margin-left: 15px;">
		                	<div class="selc">
		                    	<div class="selcc tbtag">
		                            <div class="seli"><a class="nx-1" href="${ctx }/da/survey-report!defaultReport.action?surveyId=${surveyId}">统计表格</a></div>
		                            <div class="seli"><a class="nx-2" href="${ctx }/da/survey-report!lineChart.action?surveyId=${surveyId}">柱状图</a></div>
		                            <div class="seli"><a class="nx-3" href="${ctx }/da/survey-report!pieChart.action?surveyId=${surveyId}">饼状图</a></div>
		                        </div>
		                    </div>
		                </div>
		            </div>
				</li>
			</ul>
		</div>
	</div>
	
	<div style="">
		<div class="main-tabs-content bodyCenter">
			<div class="tab-content">
				<div class="tab-content-collectTab">
					<a href="${ctx }/design/my-collect.action?surveyId=${surveyId}" class="collectTab tabItem_1"><span class="collectTabItemLeft">&nbsp;</span><span>答卷地址</span></a>
					<a href="${ctx }/design/my-collect.action?surveyId=${surveyId}&tabId=share" class="collectTab tabItem_4" ><span class="collectTabItemLeft">&nbsp;</span><span>社交分享</span></a>
					<a href="${ctx }/design/my-collect.action?surveyId=${surveyId}&tabId=mail" class="collectTab tabItem_3 active"><span class="collectTabItemLeft">&nbsp;</span><span>邮件邀请</span></a>
					<a href="${ctx }/design/my-collect.action?surveyId=${surveyId}&tabId=sitecomp" class="collectTab tabItem_5"><span class="collectTabItemLeft">&nbsp;</span><span>网站组件</span></a>
					<%-- <a href="${ctx }/design/my-collect.action?surveyId=${surveyId}&tabId=iframe" class="collectTab tabItem_6"><span class="collectTabItemLeft">&nbsp;</span><span>IFrame嵌入</span></a> --%>
					<a href="${ctx }/design/my-collect.action?surveyId=${surveyId}&tabId=weixin" class="collectTab tabItem_2"><span class="collectTabItemLeft">&nbsp;</span><span>微信收集</span></a>
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
						发布时间：<span class="collectInfoSpan"><fmt:formatDate value="${survey.createDate }" pattern="yyyy年MM月dd日 HH时mm分" /> </span>
						</span>
					</div>
				</div>
				
				<div class="surveyCollectMiddleContent">
					
					<div class="collect_1_content">
					<div style="padding:0px 60px;">
						<div style="" >
							<div class="scmcRightTop" >
								<div class="scmcRightTopLeft">
									<div class="scmcRightTopLeftTitle">通过邮件邀请
										<span style="font-size:14px;color:#9B9A9A;">邮件收件可以跟踪保证唯一性的问卷链接</span>
									</div>
									<!-- <div class="scmcRightTopLeftNote">邮件收件可以跟踪保证唯一性的问卷链接到QQ，Email等工具中直接发给被用户</div> -->
								</div>
								<a href="${ctx }/design/my-collect.action?surveyId=${surveyId }&tabId=mail" style="float: right;" class="btn-a-1 ">添加新任务</a>
							</div>
							<!-- <div class="emailCollectTab">
								<a href="" class="emailCTabStep active"><span></span><span>1、收件人</span><span></span></a>
								<span class="emailCTabStep emailCTabLine"><span></span></span>
								<a href="" class="emailCTabStep"><span></span><span>2、编辑内容</span><span></span></a>
								<span class="emailCTabStep emailCTabLine"><span></span></span>
								<a href="" class="emailCTabStep"><span></span><span>3、发送邮件</span><span></span></a>
							</div> -->
							<!-- 添加收件人 -->
							<!-- <div><a href="" class="addInboxUser"><span class="addInboxUserLeft">&nbsp;</span><span>添加收件人</span></a></div> -->
							
							<div style="padding-top: 5px;">
								<table class="emailInboxList" width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr>
										<th>主题</th>
										<th>发件人</th>
										<th>回复邮箱</th>
										<th>已发送/总数</th>
										<th>状态</th>
										<th>操作</th>
									</tr>
									<c:forEach items="${page.result }" var="en">
									<tr>
										<td>${en.subject }</td>
										<td>${en.dwSendUserName }</td>
										<td>${en.dwSendUserMail }</td>
										<td>${en.sendNum}/${en.inboxNum }</td>
										<td>
											${en.audit eq 0 ? '待审核': en.audit eq 1 ? '已经通过':'未通过'}
											<%-- -${en.status eq 0 ? '未发送':en.status eq 1 ? '正在发送':en.status eq 2 ? '发送完成' : en.status eq 3 ? '发送失败':'' } --%>
										</td>
										<td><a href="${ctx }/design/mail-collect!inviteDetail.action?surveyId=${surveyId }&surveyInviteId=${en.id}">详情</a></td>
									</tr>
									</c:forEach>
								</table>
							</div>
							
							<div style="padding-top:10px;height: 30px;">
								<!-- <div style="float: left;">已仿人数：20人</div> -->
								<div class="listPageNos" style="float: right;">
								
								<c:if test="${page.pageNo > 1}">
									<a href="${ctx }/design/mail-collect.action?page.pageNo=${page.pageNo-1}&surveyId=${surveyId }" class="pageNo">&lt;</a>
								</c:if>
								<c:if test="${page.startpage > 1}">
									<a href="${ctx }/design/mail-collect.action?page.pageNo=1&surveyId=${surveyId }" class="pageNo">1</a>
									<c:if test="${page.startpage > 2 }">
										<span>...</span>
									</c:if>
								</c:if>
								<c:forEach begin="${page.startpage }" end="${page.endpage }" var="en">
									<c:choose>
										<c:when test="${page.pageNo eq en }"><a href="${ctx }/design/mail-collect.action?page.pageNo=${en }&surveyId=${surveyId }" class="pageNo active">${en }</a></c:when>
										<c:otherwise><a href="${ctx }/design/mail-collect.action?page.pageNo=${en}&surveyId=${surveyId }" class="pageNo">${en }</a></c:otherwise>
									</c:choose>
								</c:forEach>
								<c:if test="${page.totalPage > (page.endpage)}">
									<c:if test="${page.totalPage > (page.endpage+1)}">
										<span>...</span>
									</c:if>
									<a href="${ctx }/design/mail-collect.action?page.pageNo=${page.totalPage}&surveyId=${surveyId }" class="pageNo">${page.totalPage }</a>
								</c:if>
								<c:if test="${page.totalPage > page.pageNo}">
									<a href="${ctx }/design/mail-collect.action?page.pageNo=${page.pageNo+1}&surveyId=${surveyId }" class="pageNo">&gt;</a>
								</c:if>
								
									<!-- <a href="" class="pageNo active">1</a>
									<a href="" class="pageNo">2</a>
									<a href="" class="pageNo">3</a>
									<a href="" class="pageNo">下一页</a> -->
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

</script>
</body>
</html>