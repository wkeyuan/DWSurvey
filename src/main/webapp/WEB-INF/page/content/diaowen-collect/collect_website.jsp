<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${ctx }/js/plugs/zero-clipboard/ZeroClipboard.js"></script>
<script type="text/javascript" src="${ctx }/js/plugs/colpick-jQuery/js/colpick.js"></script>
<link rel="stylesheet" href="${ctx }/js/plugs/colpick-jQuery/css/colpick.css" type="text/css"/>
<script type="text/javascript" src="${ctx }/js/dw/collect.js"></script>

<title>网站组件</title>
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
	<input type="hidden" id="id" name="id" value="${surveyId }">
	<input type="hidden" id="surveyState" name="surveyState" value="${survey.surveyState }">

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
						发布时间：<span class="collectInfoSpan"><fmt:formatDate value="${survey.createDate }" pattern="yyyy年MM月dd日 HH时mm分" /> </span>
						</span>
					</div>
				</div>
				
				<div class="surveyCollectMiddleContent">
					
					<div class="collect_1_content">
					<div style="margin: 0px auto; width: 950px;">
						<div style="clear: both;"></div>
						<div style="padding-top: 10px;">
							<div class="scmcRightTop" >
								<div class="scmcRightTopLeft">
									<div class="scmcRightTopLeftTitle">通过您的网站收集问卷</div>
								</div>
								<div style="padding-top: 15px;overflow: auto;clear: both;">
								
								<div class="scmcsiteLeft">
									<div class="scmcsiteTitle">1、风格选择</div>
									<div class="scmcsiteLeftContent">
										<!-- <div class="scmcsiteLItem" ><span>二维码：</span>
											<label><input type="radio" name="rwCode"/>&nbsp;显示</label>&nbsp;&nbsp;
											<label><input type="radio" name="rwCode"/>&nbsp;不显示</label>
										</div> -->
										<div class="scmcsiteLItem"><span>&nbsp;浮&nbsp;&nbsp;动：</span>
											<label><input type="radio" name="floatPos" value="left"/>&nbsp;左边</label>&nbsp;&nbsp;
											<label><input type="radio" name="floatPos" value="right" checked="checked"/>&nbsp;右边</label>
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<!-- <span>形状：</span>
											<label><input type="radio" name="xinz1"/>&nbsp;条形</label>&nbsp;&nbsp;
											<label><input type="radio" name="xinz1" />&nbsp;主形</label> -->
										</div>
									</div>
									<div class="siteColorContent">
											<div id="leftColorContent">
												<div style="color: #6C6C6C;">&nbsp;&nbsp;
													<label><input type="radio" name="colorbox" checked="checked" value="1"/>&nbsp;背景色</label>&nbsp;&nbsp;
													<label><input type="radio" name="colorbox" value="2"/>&nbsp;文字色</label>
												</div>
												<div id="site_color_box">&nbsp;</div>
											</div>
											<div id="site_show_top">
												<div style="text-align: center;font-size: 10px;color: #9A9A9A;">上</div>
												<div id="site_show_top_slider" style="height:155px;margin:5px auto;" ></div>
												<div style="text-align: center;font-size: 10px;color: #9A9A9A;">下</div>
												<div style="margin: 0px auto;width: 75px;color: #636363;font-size: 12px;margin-top: 2px;">高&nbsp;度：<input id="showPosition" type="text" size="3"  value="60" style="font-size:12px;color: #555;outline: none;padding: 1px;padding-left:2px;background: #f3f3f3;border: 1px solid #BDBDBD;"></div>
											</div>
											<div id="site_show_right"></div>
									</div>
									<div style="clear: both;"></div>
									<div style="color: #9B9B9B;font-size: 14px;padding-top: 10px;">高度与背景色可自动调节，请从页面左右侧预览实际效果。</div>
									
								</div>
								<div class="scmcsiteRight">
									<div class="scmcsiteTitle">2、代码复制</div>
									<div class="scmcsiteRightContent">
										<textarea id="compCodeText" rows="" cols="" class="scmcsiteRCEdit" readonly="readonly">
										
										</textarea>
										<div style="text-align: center;padding-top: 8px;">
										<span id="compCopyMsg" style="display:none;color: #7BA400;font-size: 22px;position: absolute;margin-left: -120px;">复制成功！</span>
										<a href="#" class="sbtn25 sbtn25_1" id="compTextareaCodeBtn">复制代码</a>
										</div>
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
	
<div id="compStyle1" style="display: none;"><!-- DIAOWEN.NET Button BEGIN --><div style="position: fixed;right: 0px;top: 100px;z-index: 9999;"><a href="http://www.diaowen.net/wenjuan/${survey.sid }.html" style="background: #1C658B;width: 15px;height: 100px;background: #8CBCD1;display: block;padding: 5px;padding-top: 10px;font-weight: bold;color: white! important;cursor: pointer;float: right;vertical-align: middle;text-decoration: none;font-size: 12px;-moz-border-radius-topleft: 5px; -webkit-border-top-left-radius: 5px;-khtml-border-top-left-radius: 5px;border-top-left-radius: 5px;-moz-border-radius-bottomleft: 5px; -webkit-border-bottom-left-radius: 5px;-khtml-border-bottom-left-radius: 5px; border-bottom-left-radius: 5px;">问卷调查</a></div><!-- DIAOWEN.NET Button END --></div>
	
<div id="priviewContent" class="priviewContentFixed">
	<div id="webSiteLeftCode">
	<div id="webSiteFixedLeft" class="websiteFixed" style="position: fixed;left: 0px;top: 520px;z-index: 9999;display: none;">
		<a class="websiteAId" href="http://www.diaowen.net/wenjuan/${survey.sid }.html" style="background: #1C658B;width: 15px;background: #8CBCD1;display: block;padding: 5px;padding-top: 10px;padding-bottom:10px;font-weight: bold;color: white;cursor: pointer;float: right;vertical-align: middle;text-decoration: none;font-size: 12px;-moz-border-radius-topright: 5px;-webkit-border-top-right-radius: 5px;-khtml-border-top-right-radius: 5px;border-top-right-radius: 5px;-moz-border-radius-bottomright: 5px;-webkit-border-bottom-right-radius: 5px;-khtml-border-bottom-right-radius: 5px;border-bottom-right-radius: 5px;">问卷调查</a>
	</div>
	</div>
	
	<div id="webSiteRightCode">
	<div id="webSiteFixedRight" class="websiteFixed" style="position: fixed;right: 0px;top: 520px;z-index: 9999;">
		<a class="websiteAId" href="http://www.diaowen.net/wenjuan/${survey.sid }.html" style="background: #1C658B;width: 15px;background: #8CBCD1;display: block;padding: 5px;padding-top: 10px;padding-bottom:10px;font-weight: bold;color: white;cursor: pointer;float: right;vertical-align: middle;text-decoration: none;font-size: 12px;-moz-border-radius-topleft: 5px; -webkit-border-top-left-radius: 5px;-khtml-border-top-left-radius: 5px;border-top-left-radius: 5px;-moz-border-radius-bottomleft: 5px; -webkit-border-bottom-left-radius: 5px;-khtml-border-bottom-left-radius: 5px; border-bottom-left-radius: 5px;">问卷调查</a>
	</div>
	</div>
</div>	
<script type="text/javascript">

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
		//notify("复制成功!"); alert("复制成功！");
		$("#compCodeText").select();
		$("#"+clipSpanId).show().delay(5000).fadeOut("slow");
	});
}

//$("#compCodeText").text($("#compStyle1").html());

bindClipBoard("compCodeText","compTextareaCodeBtn","compCopyMsg");

$("#compCodeText").click(function(){
	$(this).select();
});

$("input[name='floatPos']").change(function(){
	var thVal=$(this).val();
	if(thVal=="left"){
		$("#webSiteFixedLeft").show();
		$("#webSiteFixedRight").hide();
	}else if(thVal=="right"){
		$("#webSiteFixedLeft").hide();
		$("#webSiteFixedRight").show();
	}
	var docHeight=$(window).height();
	var bottomHeight=$("#showPosition").val();
	var posHeight=docHeight-bottomHeight;
	$(".websiteFixed").offset({top:posHeight});
	updateCode();
});
//色彩选择器
$("#site_color_box").colpick({
	flat:true,
	layout:'hex',
	submit:false,
	colorScheme:"light",
	onChange:function(hsb,hex,rgb,el){
		var colorbox=$("input[name='colorbox']:checked").val();
		if(colorbox=="2"){
			$(".websiteAId").css('color', '#'+hex);
		}else{
			$(".websiteAId").css('background-color', '#'+hex);
		}
		updateCode();
	},
	onSubmit:function(hsb,hex,rgb,el) {
		
	}
});

$( "#site_show_top_slider" ).slider({
	orientation: "vertical",
	range: "min",
	min: 0,
	max: 100,
	value: 20,
	slide: function( event, ui ) {
		//$( "#amount" ).val( ui.value );
		var docHeight=$(window).height();
		var bottomHeight=(docHeight*ui.value*0.01);
		$("#showPosition").val( bottomHeight );
		var posHeight=docHeight-bottomHeight;
		$(".websiteFixed").offset({top:posHeight});
		updateCode();
	}
});

//同步更新代码
function updateCode(){
	//webSiteLeftCode
	var thVal=$("input[name='floatPos']:checked").val();
	var copyCode="";
	if(thVal==="left"){
		copyCode=$("#webSiteLeftCode").html();
	}else if(thVal==="right"){
		copyCode=$("#webSiteRightCode").html();
	}
	$("#compCodeText").text("<!-- Diaowen.net Button BEGIN -->"+copyCode+"<!-- Diaowen.net Button END -->");
	bindClipBoard("compCodeText","compTextareaCodeBtn","compCopyMsg");
}
updateCode();
</script>
</body>
</html>