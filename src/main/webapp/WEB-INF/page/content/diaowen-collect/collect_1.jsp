<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${ctx }/js/plugs/clipboard.js/clipboard.min.js"></script>
<script type="text/javascript" src="${ctx }/js/dw/collect.js"></script>
<title>答案地址 数据收集</title>
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
		                            <div class="seli"><a class="nx-6 sur_edit" href="${ctx }/design/my-survey-design.action?surveyId=${surveyId}">问卷编辑</a></div>
		                        </div>
		                    </div>
		                </div>
		            </div>
				</li>
				<li><span class="csscStep csscStepLine"><span class="csscStepLeft">&nbsp;</span><span class="csscStepRight">&nbsp;</span></span></li>
				<li><a href="${ctx }/design/my-collect.action?surveyId=${surveyId }"  class="clickHideMenu csscStep csscStep5 active"><span class="csscStepLeft">&nbsp;</span><span class="csscStepCenter">数据收集</span><span class="csscStepRight" >&nbsp;</span></a>
				</li>
				<li><span class="csscStep csscStepLine"><span class="csscStepLeft">&nbsp;</span><span class="csscStepRight">&nbsp;</span></span></li>
				<li><a href="${ctx }/da/survey-report!defaultReport.action?surveyId=${surveyId}"  class="clickHideMenu csscStep csscStep6"><span class="csscStepLeft">&nbsp;</span><span class="csscStepCenter">数据分析</span><span class="csscStepRight">&nbsp;</span></a>
					<div class="a-w-sel">
		            	<div class="w-sel" style="margin-top: 4px;margin-left: 15px;">
		                	<div class="selc">
		                    	<div class="selcc tbtag">
		                            <div class="seli"><a class="nx-1" href="${ctx }/da/survey-report!defaultReport.action?surveyId=${surveyId}">统计表格</a></div>
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
					<a href="${ctx }/design/my-collect.action?surveyId=${surveyId}" class="collectTab tabItem_1 active"><span class="collectTabItemLeft">&nbsp;</span><span>答卷地址</span></a>
					<a href="${ctx }/design/my-collect.action?surveyId=${surveyId}&tabId=share" class="collectTab tabItem_4" ><span class="collectTabItemLeft">&nbsp;</span><span>社交分享</span></a>
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
				
				<!-- <div class="surveyCollectMiddleTab">
					<div class="scmTabLeft">
						<div class="scmTabLeftItem"><a href="">问卷设计</a>&nbsp;&nbsp;&gt;</div>
						<div class="scmTabLeftItem"><a href="" class="hover">数据收集</a>&nbsp;&nbsp;&gt;</div>
						<div class="scmTabLeftItem"><a href="">数据分析</a></div>
					</div>
				</div> -->
				<!-- <div class="surveyCollectMiddleTab">
					<div class="scmTabLeft">
						<span class="scmTabLeftItem"><a href="">问卷设计</a>&nbsp;v</span>
						<span>&nbsp;&nbsp;——&gt;&nbsp;&nbsp;</span>
						<span class="scmTabLeftItem"><a href="" class="hover">数据收集</a>&nbsp;v</span>
						<span>&nbsp;&nbsp;——&gt;&nbsp;&nbsp;</span>
						<span class="scmTabLeftItem"><a href="">数据分析</a>&nbsp;v</span>
					</div>
				</div> -->
				<div class="surveyCollectTop">
					<div class="surveyCollectTitleDiv">
						<span class="surveyCollectTitle">${survey.surveyName }</span>
						<div class="scmTabRight" >
							<c:choose>
								<c:when test="${survey.surveyState eq 0 }">
									<a href="" class="surveyStateBtn sbtn25 sbtn25_2" style="color: #599fd1;">发布问卷</a>
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
									<div class="scmcRightTopLeftTitle">答卷地址
									<!-- <span  id="clipLinkSpan"   style="display: none;font-size:16px;color:#80AA00;">复制成功</span> -->
									</div>
									<div class="scmcRightTopLeftNote">复制下面的问卷链接到QQ，Email等工具中直接发给被用户</div>
									<div><span style="color: #d15985;line-height: 36px;">找不到人填问卷？加QQ群：457647860 互填互助快速完成调研</span></div>
								</div>
							</div>
							<div style="clear: both;"></div>
							<div>
								<div class="scmcSurveyLink">
									<span class="scmcSurveyLinkLeft">&nbsp;</span>
									<span class="scmcSurveyLinkCenter"  id="linkTextarea" >
									${baseUrl }/dwsurvey/${survey.sid }.html
									</span>
									<span class="scmcSurveyLinkRight"><span id="clipLinkSpan" style="display: none;">复制成功</span></span>
									<a href="#" style="display: block;" class="clipLink" id="clipLink" data-clipboard-text="${baseUrl }/dwsurvey/${survey.sid }.html">复制链接</a>
									<a target="_blank" href="${baseUrl }/dwsurvey/${survey.sid }.html" style="margin-left: 12px;background: rgb(130, 144, 154);width: 95px;height: 50px;text-align: center;border-radius: 3px;color: #ffffff;" >打开</a>
								</div>
							</div>
						</div>
						<div style="clear: both;"></div>
						<div style="padding-top: 25px;">
							<div class="scmcRightTop" >
								<div class="scmcRightTopLeft">
									<div class="scmcRightTopLeftTitle">二维码手机答题</div>
								</div>
								<div style="padding-top: 15px;overflow: auto;clear: both;">
								<div class="scmcSurveyTdCode">
									<%-- <img alt="" src="${ctx }/images/style-model/secode.png" height="130" > --%>
									<img alt="" src="${ctx }/survey!answerTD.action?surveyId=${survey.id}" height="130" >
								</div>
								<div class="scmcRightTopRight" style="float: left ;padding-left: 30px;">
									<a href="${ctx }/survey!answerTD.action?surveyId=${survey.id}&down" class="sbtn25 sbtn25_1">下载二维码</a>
									<div class="scmcRightTopLeftNote" style="width: 100px;padding-top: 25px;">复制下面的问卷链接到QQ，Email等工具中直接发给被用户</div>
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
	</div>
	
<script type="text/javascript">

/*var client = new ZeroClipboard( document.getElementById("clipLink") );
client.on( "ready", function( readyEvent ) {
  	 alert( "ZeroClipboard SWF is ready!" );
	  client.on( "aftercopy", function( event ) {
	    // `this` === `client`
	    // `event.target` === the element that was clicked
	    // event.target.style.display = "none";
		  $("#clipLinkSpan").show().delay(5000).fadeOut("slow");
	  } );
});*/

var clipboard = new ClipboardJS('.clipLink');

clipboard.on('success', function(e) {
	$("#clipLinkSpan").text("复制成功");
	$("#clipLinkSpan").show().delay(5000).fadeOut("slow");
});

clipboard.on('error', function(e) {
	$("#clipLinkSpan").text("浏览器不支持！");
	$("#clipLinkSpan").show().delay(5000).fadeOut("slow");
});

</script>
</body>
</html>