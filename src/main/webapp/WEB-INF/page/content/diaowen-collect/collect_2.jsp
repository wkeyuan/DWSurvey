<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%-- <script type="text/javascript" src="${ctx }/js/plugs/zero-clipboard/ZeroClipboard.js"></script> --%>
<script type="text/javascript" src="${ctx }/js/plugs/zeroclipboard-master/dist/ZeroClipboard.js"></script>
<script type="text/javascript" src="${ctx }/js/dw/collect.js"></script>
<title>社交分享 数据收集</title>
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
				<li><a href="${ctx }/design/my-collect.action?surveyId=${surveyId }"  class="clickHideMenu csscStep csscStep5 active"><span class="csscStepLeft">&nbsp;</span><span class="csscStepCenter">数据收集</span><span class="csscStepRight" >&nbsp;</span></a>
				</li>
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
					<a href="${ctx }/design/my-collect.action?surveyId=${surveyId}" class="collectTab tabItem_1 "><span class="collectTabItemLeft">&nbsp;</span><span>答卷地址</span></a>
					<a href="${ctx }/design/my-collect.action?surveyId=${surveyId}&tabId=share" class="collectTab tabItem_4 active" ><span class="collectTabItemLeft">&nbsp;</span><span>社交分享</span></a>
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
									<div class="scmcRightTopLeftTitle">分享到社交网络
									<!-- <span  id="clipLinkSpan"   style="display: none;font-size:16px;color:#80AA00;">复制成功</span> -->
									</div>
									<div class="scmcRightTopLeftNote">赶快分享您的问卷到各大社交网站，让更多关注您的朋友来回答问卷。</div>
								</div>
							</div>
							<div style="clear: both;"></div>
							<div style="padding-bottom: 20px;">
								<div style="border: 1px solid rgb(234, 231, 231);padding: 5px;">
									<textarea id="shareSurTextarea" rows="5" cols="30" style="width: 100%;height: 160px;border: none;outline: none;resize:none;font-size: 16px;" >大家好，我刚刚通过调问网制作了一份调查问卷“${survey.surveyName }”，需要您的帮助，您的意见非常重要。快快来吧帮忙回答。</textarea>
								</div>
								<div style="padding: 5px;background: rgb(234, 231, 231);height: 40px;">
									<div style="float: left;line-height: 45px;">分享到&nbsp;&nbsp;</div>
									<div style="float: left;"><!-- Baidu Button BEGIN -->
											<div id="bdshare" class="bdshare_t bds_tools_32 get-codes-bdshare"  data='{"text":"大家好，我刚刚通过调问网制作了一份调查问卷“${survey.surveyName }”，需要您的帮助，您的意见非常重要。快快来吧帮忙回答。", "url":"http://www.diaowen.net/wenjuan/${survey.sid }.html","pic":"http://www.diaowen.net/images/logo/200-200.png"}'>
												<a class="bds_qzone"></a>
												<a class="bds_tsina"></a>
												<a class="bds_tqq"></a>
												<a class="bds_renren"></a>
												<a class="bds_t163"></a>
												<span class="bds_more"></span>
												</div>
												<script type="text/javascript" id="bdshare_js" data="type=tools&amp;uid=519013" ></script>
												<script type="text/javascript" id="bdshell_js"></script>
												<script type="text/javascript">
												document.getElementById("bdshell_js").src = "http://bdimg.share.baidu.com/static/js/shell_v2.js?cdnversion=" + Math.ceil(new Date()/3600000)
												</script>
												<!-- Baidu Button END -->
												
										<!-- <div class="bshare-custom icon-medium-plus"><div class="bsPromo bsPromo2"></div><a title="分享到QQ空间" class="bshare-qzone"></a><a title="分享到新浪微博" class="bshare-sinaminiblog"></a><a title="分享到人人网" class="bshare-renren"></a><a title="分享到腾讯微博" class="bshare-qqmb"></a><a title="分享到网易微博" class="bshare-neteasemb"></a><a title="更多平台" class="bshare-more bshare-more-icon more-style-addthis"></a></div>
										<script type="text/javascript" charset="utf-8" src="http://static.bshare.cn/b/buttonLite.js#style=-1&amp;uuid=&amp;pophcol=2&amp;lang=zh"></script>
										<script type="text/javascript" charset="utf-8" src="http://static.bshare.cn/b/bshareC0.js"></script> -->
										
									</div>
									<div style="text-align: right;float: right;line-height: 45px;">还可以输入：<span id="wordsInfo" style="color: green;">120</span>个字符。</div>
								</div>
							</div>
						</div>
						<div style="clear: both;"></div>
						
						</div>
					</div>
					
					<%-- <div class="scmcLeft">
						<ul>
							<li><a href="${ctx }/design/my-collect.action?surveyId=${surveyId}" class="hover">答卷地址</a></li>
							<li><a href="${ctx }/design/my-collect.action?surveyId=${surveyId}&tabId=weixin">微信收集</a></li>
							<!-- <li><a href="">发布互填</a></li> -->
							<li><a href="${ctx }/design/my-collect.action?surveyId=${surveyId}&tabId=mail">邮件邀请</a></li>
							<li><a href="${ctx }/design/my-collect.action?surveyId=${surveyId}&tabId=sitecomp">网站组件</a></li>
							<li><a href="${ctx }/design/my-collect.action?surveyId=${surveyId}&tabId=iframe">IFrame内嵌</a></li>
						</ul>
					</div> --%>
					<!-- <div class="scmcRight">
						<div style="clear: both;margin-top:10px;overflow: auto;">
							<div style="" >
							<div class="scmcRightTop" >
								<div class="scmcRightTopLeft">
									<div class="scmcRightTopLeftTitle">赶快分享您的问卷到各大社交网站</div>
									<div class="scmcRightTopLeftNote">点击下面分享快速分享您的问卷，让更多关注您的朋友来回答问卷。</div>
								</div>
								<div class="scmcRightTopRight" style="float: right ;margin-top: 30px;">
								</div>
							</div>
							<div style="clear: both;">
								<div class="scmcSurveySNSShare">
									<textarea rows="3" cols="5">大家好，就在刚才我在调问网网制作了一份调查问卷“测试问卷测试问卷测试问卷测试问卷测试问卷测试问卷测试问卷”，需要您的帮助，您的意见非常重要。快快来吧帮忙回答。</textarea>
								</div>
							</div>
						</div>
						</div>
					</div> -->
					
				</div>
			</div>
			
		</div>
		</div>
	</div>
	
<script type="text/javascript">

/*
ZeroClipboard.setMoviePath( "${ctx}/js/plugs/zero-clipboard/ZeroClipboard.swf" );
function bindClipBoard(textareaId,clipBtn,clipSpanId){
	var clip = new ZeroClipboard.Client();
	clip.setHandCursor( true );
	clip.setCSSEffects( true );
	var clipText=$("#"+textareaId).text();
	clip.setText(clipText); // 设置要复制的文本。
	//这个 button 不一定要求是一个 input 按钮，也可以是其他 DOM 元素。 
	clip.glue(clipBtn); // 和上一句位置不可调换 
	clip.addEventListener('complete', function (client, text) {
		//alert(text+":复成功");//notify("复制成功!");
		$("#"+clipSpanId).show().delay(5000).fadeOut("slow");
		//$("#"+textareaId).select();
	});
	clip.reposition(clipBtn); 
}
bindClipBoard("linkTextarea","clipLink","clipLinkSpan");
*/

var GetLength = function(str) {
    return str.replace(/[^\x00-\xff]/g,"kx").length;
};

$("#shareSurTextarea").keyup(function(){
	var words = parseInt((240 - GetLength($(this).val())) / 2);
	$('#wordsInfo').text(words);
	if (words >= 0){
		$('#wordsInfo').removeClass('redColor');
	}else{
		$('#wordsInfo').addClass('redColor');
	}
	$('#bdshare').attr('data', '{"text":"' + $(this).val() + '", "url":"http://www.diaowen.net/wenjuan/${survey.sid }.html","pic":"http://www.diaowen.net/images/logo/200-200.png"}');
});
$("#shareSurTextarea").keyup();

</script>
</body>
</html>