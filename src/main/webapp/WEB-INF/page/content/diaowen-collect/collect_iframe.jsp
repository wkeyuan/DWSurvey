<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${ctx }/js/plugs/zero-clipboard/ZeroClipboard.js"></script>
<script type="text/javascript" src="${ctx }/js/plugs/jquery-ui-1.10.3.custom/js/jquery-1.10.1.js"></script>
<script type="text/javascript" src="${ctx }/js/plugs/jquery-ui-1.10.3.custom/js/jquery-ui-1.10.3.custom.js"></script>
<script type="text/javascript" src="${ctx }/js/dw/responsive-width.js"></script>
<script type="text/javascript" src="${ctx }/js/plugs/colpick-jQuery/js/colpick.js"></script>
<link href="${ctx }/css/dw-user.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${ctx }/js/plugs/colpick-jQuery/css/colpick.css" type="text/css"/>
<link href="${ctx }/js/plugs/jquery-ui-1.10.3.custom/css/base/jquery.ui.all.css" rel="stylesheet" type="text/css" />
<link href="${ctx }/js/plugs/jquery-ui-1.10.3.custom/css/base/jquery.ui.slider.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx }/js/dw/collect.js"></script>

<title>数据收集</title>
<style type="text/css">
.colpick{
	margin-left: 0px;
	margin-top: -10px;
}
#site_show_top_slider{
	font-size:62.5%;
	background: #DADADA;
	border: none;
}
#site_show_top_slider a{
		outline: none;
		outline-style: none;
}
#site_show_top_slider .ui-slider-range { background: #599FD2; }
</style>
</head>
<body>
<div id="wrap">
	<input type="hidden" id="id" name="id" value="${survey.id }">
	<input type="hidden" id="surveyState" name="surveyState" value="${survey.surveyState }">
	
	<input type="hidden" id="ctx" value="${ctx }">
	<div id="header" >
		<div id="headerCenter"  class="bodyCenter">
			<div class="header_Item header_logo">
			<img alt="调问网" src="${ctx }/images/logo/LOGO.png" >
			<!-- <div style="font-family: '微软雅黑';font-size:26px;padding-left:10px;">DIAOWEN</div> -->
			</div>
			<div class="header_Item header_menu">
			<ul>
			<li><a href="" class="active">问卷</a></li>
			<li><a href="">表单</a></li>
			<li><a href="">模板</a></li>
			<li><a href="">社区</a></li>
			<li><a href="">帮助</a></li>
			</ul>
			</div>
			<div class="header_Item header_user" style="float: right;">
			<a href="">keyuan<span class="header_user_icon">&nbsp;</span></a>
			</div>
		</div>
	</div>
	
	<div class="creatgeSurveyStepBody">
		<div class="creatgeSurveyStepContent bodyCenter">
				<a href=""  class="csscStep csscStep4"><span class="csscStepLeft">&nbsp;</span><span class="csscStepCenter">设计问卷</span><span class="csscStepRight">&nbsp;</span></a>
				<span class="csscStep csscStepLine"><span class="csscStepLeft">&nbsp;</span><span class="csscStepRight">&nbsp;</span></span>
				<a href=""  class="csscStep csscStep5 active"><span class="csscStepLeft">&nbsp;</span><span class="csscStepCenter">数据收集</span><span class="csscStepRight" >&nbsp;</span></a>
				<span class="csscStep csscStepLine"><span class="csscStepLeft">&nbsp;</span><span class="csscStepRight">&nbsp;</span></span>
				<a href=""  class="csscStep csscStep6"><span class="csscStepLeft">&nbsp;</span><span class="csscStepCenter">数据分析</span><span class="csscStepRight">&nbsp;</span></a>
		</div>
	</div>
	
	<div style="">
		<div class="main-tabs-content bodyCenter">
			<div class="tab-content">
				<div class="tab-content-collectTab">
					<a href="${ctx }/design/my-collect.action?surveyId=${surveyId}" class="collectTab tabItem_1"><span class="collectTabItemLeft">&nbsp;</span><span>答卷地址</span></a>
					<!-- <a href="" class="collectTab tabItem_4"><span class="collectTabItemLeft">&nbsp;</span><span>社交分享</span></a> -->
					<a href="${ctx }/design/my-collect.action?surveyId=${surveyId}&tabId=sitecomp" class="collectTab tabItem_5"><span class="collectTabItemLeft">&nbsp;</span><span>网站组件</span></a>
					<%-- <a href="${ctx }/design/my-collect.action?surveyId=${surveyId}&tabId=iframe" class="collectTab tabItem_6 active"><span class="collectTabItemLeft">&nbsp;</span><span>IFrame嵌入</span></a> --%>
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
						<span class="surveyCollectTitle">巴黎欧莱雅男士护肤品的市场调查</span>
						<div class="scmTabRight" >
							<a href="" class="sbtn25 sbtn25_2">停止收集</a>
						</div>
					</div>
					<div class="surveyCollectInfoDiv">
						<span class="surveyCollectInfoLeft">
						状态：<span class="collectInfoSpan">收集中</span>&nbsp;&nbsp;&nbsp;&nbsp;
						参数人数：<span class="collectInfoSpan">23429</span>
						</span>
						<span class="surveyCollectInfoRight">
						发布时间：<span class="collectInfoSpan">2014-05-05  22时</span>
						</span>
					</div>
				</div>
				
				<div class="surveyCollectMiddleContent">
					
					<div class="collect_1_content">
					<div style="margin: 0px auto; width: 950px;">
						<div style="clear: both;"></div>
						<div style="padding-top: 35px;">
							<div class="scmcRightTop" >
								<div class="scmcRightTopLeft">
									<div class="scmcRightTopLeftTitle">通过您的网站收集问卷</div>
								</div>
								<div style="padding-top: 15px;overflow: auto;clear: both;">
								
								<div class="scmcsiteLeft">
									<div class="scmcsiteTitle">1、风格选择</div>
									<div class="scmcsiteLeftContent">
										<div class="scmcsiteLItem"><span>二维码：</span>
											<label><input type="radio" />&nbsp;显示</label>&nbsp;&nbsp;
											<label><input type="radio" />&nbsp;不显示</label>
										</div>
										<div class="scmcsiteLItem"><span>&nbsp;浮&nbsp;&nbsp;动：</span>
											<label><input type="radio" />&nbsp;左边</label>&nbsp;&nbsp;
											<label><input type="radio" />&nbsp;右边</label>
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<span>形状：</span>
											<label><input type="radio" />&nbsp;条形</label>&nbsp;&nbsp;
											<label><input type="radio" />&nbsp;主形</label>
										</div>
									</div>
									<div class="siteColorContent">
											<div style="float: left;">
												<iframe src="${ctx }/wenjuan/fyaq85pz81.html" frameborder="0" width="370" height="250" style="border: none;" ></iframe>
											</div>
											<div id="site_show_right"></div>
									</div>
									<div style="clear: both;"></div>
									<div style="color: #9B9B9B;font-size: 14px;padding-top: 10px;">高度与背景色可自动调节，请从页面左右侧预览实际效果。</div>
									
								</div>
								<div class="scmcsiteRight">
									<div class="scmcsiteTitle">2、代码复制</div>
									<div class="scmcsiteRightContent">
										<textarea rows="" cols="" class="scmcsiteRCEdit">background: #F1F1F1;width: 450px;height: 270px;margin-top: 15px;padding: 12px;</textarea>
										<div style="text-align: center;padding-top: 8px;"><a href="" class="sbtn25 sbtn25_1">复制代码</a></div>
									</div>
									<div style="clear: both;"></div>
									<div style="color: #9B9B9B;font-size: 14px;padding-top: 10px;">高度与背景色可自动调节，请从页面左右侧预览实际效果。</div>
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
	</div>
<script type="text/javascript">

resizeBodyWidth();

//色彩选择器
$("#site_color_box").colpick({
	flat:true,
	layout:'hex',
	submit:false,
	colorScheme:"light",
	onChange:function(){
	}
});

$( "#site_show_top_slider" ).slider({
	orientation: "vertical",
	range: "min",
	min: 0,
	max: 100,
	value: 60,
	slide: function( event, ui ) {
		//$( "#amount" ).val( ui.value );
	}
});

</script>
</body>
</html>