<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>问卷预览</title>
	<link href="${ctx }/js/plugs/jquery-ui-1.10.3.custom/css/mycss/jquery-ui-1.10.3.custom.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="${ctx }/js/plugs/jquery-ui-1.10.3.custom/js/jquery-1.10.1.js"></script>
	<script type="text/javascript" src="${ctx }/js/plugs/jquery-ui-1.10.3.custom/js/jquery-ui-1.10.3.custom.js"></script>
	<script type="text/javascript" src="${ctx }/js/plugs/colpick-jQuery/js/colpick.js"></script>
	<script type="text/javascript" src="${ctx }/js/plugs/clipboard.js/clipboard.min.js"></script>
	<link href="${ctx }/css/preview-dev.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" href="${ctx }/js/plugs/colpick-jQuery/css/colpick.css" type="text/css"/>
	<link href="${ctx }/js/plugs/font-awesome-4.2.0/css/font-awesome.css" rel="stylesheet"/>
	<link href="${ctx}/js/plugs/validate/jquery.validate.css" type="text/css" rel="stylesheet" />
	<script language="javascript" type="text/javascript" src="${ctx }/js/plugs/laydate/laydate.js"></script>
	<script type="text/javascript" src="${ctx }/js/common/ans-common.js"></script>
</head>
<body>
<div id="wrap">
	<input type="hidden" id="id" name="id" value="${survey.id }">
	<input type="hidden" id="ctx" name="ctx" value="${ctx }">
	<input type="hidden" id="surveyStyleId" value="${surveyStyle.id }">
	<input type="hidden" id="prevHost" value="${ctx }">
	<div id="preview_head">
		<div class="leftTabbar" style="position: fixed;left: 0px;z-index: 99999;">
			<ul>
				<li>
					<div class="centerTabbar">
						<a href="#dw_body" class="centerTabbarBtn active" title="PC端"><i class="fa fa-desktop"></i></a>
						<a href="#dwPad" class="centerTabbarBtn" title="平板端"><i class="fa fa-tablet"></i></a>
						<a href="#dwPhone" class="centerTabbarBtn" title="手机端"><i class="fa fa-mobile-phone"></i></a>
					</div>
				</li>
			</ul>
		</div>
		<%--<div style="float: left;padding-top: 10px;top:0px;font-size: 14px;color: #615c5c;font-weight: normal;">
            当前为预览页不记录任何答案，回答不会真提交，请勿发送给他人填写！
        </div>--%>
		<div class="rightTabbar" style="position: fixed;right: 0px;z-index: 99999;">
			<c:if test="${empty param.pid}" >
				<a id="confirgDevSuvey" href="#" class="sbtn24 sbtn24_0">确认发布</a>
				<a href="${ctx }/design/my-survey-design/survey.do?surveyId=${survey.id}" class="sbtn24 sbtn24_1">返回修改</a>
				<a href="${ctx }/design/my-survey/list.do" class="sbtn24 sbtn24_1">返回列表</a>
				<a href="#" class="sbtn24 sbtn24_0" id="previewUrl" title="可以让未登录的用户进行预览">预览地址</a>
			</c:if>
		</div>
		<div style="clear: both;"></div>
		<div style="text-align: center;padding-top: 10px;position: fixed;width: 100%;top:0px;font-size: 14px;color: #615c5c;font-weight: normal;">
			当前为预览页不记录任何答案，回答不会真提交，请勿发送给他人填写！
		</div>
	</div>
	<div id="dw_body" class="dwPreviewBody" style="padding-top:10px;margin-top: 60px;">
		<iframe name="PcSurvey" frameborder="0" src="" class="uploadfile" id="PcSurvey" style="width: 100%;height: 900px;margin: 0px;"></iframe>
	</div>
	<div id="dwPhone" class="dwPreviewBody" style="margin-top: 60px;">
		<iframe name="PhoneSurvey" frameborder="0" src="" class="uploadfile" id="PhoneSurvey" style="width:255px;height:455px;margin:96px 0 0 67px;"></iframe>
	</div>
	<div id="dwPad" class="dwPreviewBody" style="margin-top: 60px;" >
		<iframe name="PadSurvey" frameborder="0" src="" class="uploadfile" id="PadSurvey" style="width: 774px;height: 579px;margin: 27px 0 0 68px;"></iframe>
	</div>
</div>
<%@ include file="../../layouts/other.jsp"%>
<!-- Diaowen.net Button BEGIN -->
<div id="webSiteFixedRight" class="websiteFixed" style="position: fixed;right: 0px;top: 60px;z-index: 9999;display: none;">
	<a id="mobileTdId" href="＃" style="background: #1C658B;width: 15px;background: #8CBCD1;display: block;padding: 5px;padding-top: 10px;padding-bottom:10px;font-weight: bold;color: white;cursor: pointer;float: right;vertical-align: middle;text-decoration: none;font-size: 12px;">手机地址</a>
	<img alt="" src="${ctx }/survey/answerTD.do?surveyId=${survey.id}" height="130" style="padding: 10px;background: white;display: none;" />
</div>
<!-- Diaowen.net Button END -->
<script type="text/javascript">
	//$("#dw_body").hide();
	$("#dwPhone").hide();
	$("#dwPad").hide();
	$("#PcSurvey").attr("src","${ctx}/survey/answerSurvey.do?surveyId=${survey.id}&tag=p");
	$("#PhoneSurvey").attr("src","${ctx}/survey/answerSurveryMobile.do?surveyId=${survey.id}&tag=p");
	$("#PadSurvey").attr("src","${ctx}/survey/answerSurveryMobile.do?surveyId=${survey.id}&tag=p");
	$(".centerTabbarBtn").click(function(){
		$(".centerTabbarBtn").removeClass("active");
		$(this).addClass("active");
		var thHref=$(this).attr("href");
		$(".dwPreviewBody").hide();
		$(thHref).show();
	});

	$("#confirgDevSuvey").click(function(){
		window.location.href="${ctx}/design/my-survey-design/devSurvey.do?surveyId=${param['surveyId']}";
	});

	$("#previewUrl").click(function(){
		var previewUrl = "${baseUrl}/survey/preview.do?pid=${surveyReqUrl.id}";
		$("body").append("<div id=\"myDialogRoot\"><div class='dialogMessage' style='padding-top:40px;margin-left:20px;padding-bottom:0px;'>"+
				"<div><p style='line-height: 30px;'>请复制以下地址给需要预览的用户</p><p><input id='clipLinkValue' type='text' style='padding:6px;width:520px;color:rgb(14, 136, 158);background: #dadada;border: none;' value='"+previewUrl+"' readonly ></p>" +
				"<p id='clipLinkSpan' style='color: green;padding-top: 10px;'></p>"+
				"<div style='display: none;'><input type='button' value='复制地址' id='clipLink' data-clipboard-action='copy' data-clipboard-target='#clipLinkValue'/></div>"+
				"</div></div></div>");
//		<button class="btn" data-clipboard-action="copy" data-clipboard-target="#foo">Copy</button>
		var myDialog=$( "#myDialogRoot" ).dialog({
			width:600,
			//autoOpen: true,
			modal:true,
			position:["center","center"],
			title:"生成预览地址",
			resizable:false,
			draggable:false,
			closeOnEscape:false,
			show: {effect:"blind",direction:"up",duration: 500},
			hide: {effect:"blind",direction:"left",duration: 200},
			buttons: {
				"OK":{
					text: "复制地址",
					addClass:'dialogMessageButton dialogBtn1',
					click: function() {
						//执行发布
						$("#clipLink").click();
					}
				},
				"CENCEL":{
					text: "关闭",
					addClass:"dialogBtn1 dialogBtn1Cencel",
					click: function() {
						$( this ).dialog( "close" );
					}
				}
			},
			open:function(event,ui){
				var clipboard = new ClipboardJS('#clipLink');
				clipboard.on('success', function(e) {
					$("#clipLinkSpan").text("复制成功");
					$("#clipLinkSpan").show().delay(5000).fadeOut("slow");
				});

				clipboard.on('error', function(e) {
					$("#clipLinkSpan").text("浏览器不支持！");
					$("#clipLinkSpan").show().delay(5000).fadeOut("slow");
				});
			},
			close:function(event,ui){
				$("#myDialogRoot").remove();
			}
		});
		return false;
	});
</script>
</body>
</html>
