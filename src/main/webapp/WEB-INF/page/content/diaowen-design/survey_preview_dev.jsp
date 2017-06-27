<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>问卷编辑</title>
<link href="${ctx }/js/plugs/jquery-ui-1.10.3.custom/css/mycss/jquery-ui-1.10.3.custom.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx }/js/plugs/jquery-ui-1.10.3.custom/js/jquery-1.10.1.js"></script>
<script type="text/javascript" src="${ctx }/js/plugs/jquery-ui-1.10.3.custom/js/jquery-ui-1.10.3.custom.js"></script>
<script type="text/javascript" src="${ctx }/js/plugs/colpick-jQuery/js/colpick.js"></script>
<link href="${ctx }/js/plugs/uploadify-v3.1/uploadify.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx }/js/plugs/uploadify-v3.1/jquery.uploadify-3.1.js"></script>
<script type="text/javascript" src="${ctx }/js/dw/uploadify.js"></script>
<script language="javascript" type="text/javascript" src="${ctx }/js/plugs/My97DatePickerBeta/My97DatePicker/WdatePicker.js"></script>

<link href="${ctx }/css/preview-dev.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${ctx }/js/plugs/colpick-jQuery/css/colpick.css" type="text/css"/>

<link href="${ctx }/js/plugs/font-awesome-4.2.0/css/font-awesome.css" rel="stylesheet"/>
<script type="text/javascript">
$(document).ready(function(){
	
	$("#confirgDevSuvey").click(function(){
		window.location.href="${ctx}/design/my-survey-design!devSurvey.action?surveyId=${param['surveyId']}";
	});
	
	$("#preview_head .leftTabbar ul li").hover(function(){
		var visibleDialog=$(this).find(".tabbarDialog:visible");
		if(!visibleDialog[0]){	
			$(this).addClass("hover");
		}
	},function(){
		$(this).removeClass("hover");	
	});
	
	$("#dw_body").hover(function(){
		var visibleDialog=$("#preview_head").find(".tabbarDialog:visible");
		if(visibleDialog[0]){
			var visibleDialogClass=visibleDialog.attr("class");
			if(visibleDialogClass.indexOf("tabbarDialogTheme")>=0){
				visibleDialog.css({opacity:0.2,borderWidth:"2px"});
				visibleDialog.find(".tabbarDialogContent").css({visibility:"hidden"});				
			}
		}
	},function(){
		var visibleDialog=$("#preview_head").find(".tabbarDialog:visible");
		if(visibleDialog[0]){
			var visibleDialogClass=visibleDialog.attr("class");
			if(visibleDialogClass.indexOf("tabbarDialogTheme")>=0){
				visibleDialog.css({opacity:1,borderWidth:"1px"});
				visibleDialog.find(".tabbarDialogContent").css({visibility:"visible"});				
			}
		}
	});
	
	var leftTabbarLiClickStatus=0;
	$(".tabbarDialog").click(function(){
		leftTabbarLiClickStatus=1;
	});
	
	$("#preview_head .leftTabbar ul li").click(function(){
		if(leftTabbarLiClickStatus!=1){
			$(".tabbarDialog").not($(this).find(".tabbarDialog")).hide();
			$(this).find(".tabbarDialog").toggle();
			$(".js-tabselected").removeClass("js-tabselected");
			$(this).removeClass("hover");
			var visibleDialog=$(this).find(".tabbarDialog:visible");
			if(visibleDialog[0]){
				$(this).addClass("js-tabselected");
				var visibleDialogClass=visibleDialog.attr("class");
				if(visibleDialogClass.indexOf("tabbarDialogTheme")>=0){
					visibleDialog.css({opacity:1,borderWidth:"1px"});
					visibleDialog.find(".tabbarDialogContent").css({visibility:"visible"});				
				}
			}
		}
		leftTabbarLiClickStatus=2;
	});
	
	var inputTempVal=null;
	$(".surveyPaddingInput").focus(function(){
		inputTempVal=$(this).val();
	});
	$(".surveyPaddingInput").blur(function(){
		var thVal=$(this).val();
		if(/^\d+$/.test(thVal)){ 
			setSurveyStyle($(this));
		 }else{
			 $(this).val(inputTempVal);
		 }
	});
	
	$(".surveyStyleChange").change(function(){
		setSurveyStyle($(this));
	});
	
	function setSurveyStyle(eventObj){
		var objName=$(eventObj).attr("name");
		var thPaddingVal=$(eventObj).val();
		if(objName=="surveyHeadPaddingTop"){
			$("#dwSurveyHeader").css({"padding-top":thPaddingVal+"px"});
		}else if(objName=="surveyHeadPaddingBottom"){
			$("#dwSurveyHeader").css({"padding-bottom":thPaddingVal+"px"});
		}else if(objName=="surveyContentPaddingLeft"){
			$("#dwSurveyQuContentBg").css({"padding-left":thPaddingVal+"px"});
		}else if(objName=="surveyContentPaddingRight"){
			$("#dwSurveyQuContentBg").css({"padding-right":thPaddingVal+"px"});
		}else if(objName=="surveyWidth"){
			$("#dw_body_content").width(thPaddingVal);
		}
	}
	
	/* surveyHeadPaddingTop
	surveyHeadPaddingBottom
	surveyContentPaddingLeft
	surveyContentPaddingRight 
	surveyPaddingInput
	*/
	/*
	$(".surveyPaddingSelect").change(function(){
		var selectName=$(this).attr("name");
		var thPaddingVal=$(this).val();
		if(selectName=="surveyHeadPaddingTop"){
			$("#dwSurveyHeader").css({"padding-top":thPaddingVal+"px"});
		}else if(selectName=="surveyHeadPaddingBottom"){
			$("#dwSurveyHeader").css({"padding-bottom":thPaddingVal+"px"});
		}else if(selectName=="surveyContentPaddingLeft"){
			$("#dwSurveyQuContentBg").css({"padding-left":thPaddingVal+"px"});
		}else if(selectName=="surveyContentPaddingRight"){
			$("#dwSurveyQuContentBg").css({"padding-right":thPaddingVal+"px"});
		}
	});*/
	
	
	$(document).click(function(){
		if(leftTabbarLiClickStatus==0){
			$(".tabbarDialog").hide();
			$(".js-tabselected").removeClass("js-tabselected");	
		}
		leftTabbarLiClickStatus=0;
	});
	
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
	
	$("#saveStyleDev").click(function(){
		var url="${ctx}/design/my-survey-style!save.action";
		var surveyId=$("#id").val();
		var bodyBgColor=$("input[name='bodyBgColor']").val();
		var bodyBgImage=$("input[name='bodyBgImage']").val();
		var showBodyBi=$("input[name='showBodyBi']").prop("checked")?1:0;
		var data="surveyId="+surveyId+"&surveyStyleType=0&bodyBgColor="+bodyBgColor+"&bodyBgImage="+bodyBgImage+"&showBodyBi="+showBodyBi;
		
		//收集规则 
		var effective=$("input[name='effective']:checked")[0]?"4":"0";
		var effectiveIp=$("input[name='effectiveIp']:checked")[0]?"1":"0";
		var rule=$("input[name='rule']:checked")[0]?"3":"0";
		var ruleCode=$("input[name='ruleCode']").val();
		var refresh=$("input[name='refresh']:checked")[0]?"1":"0";
		var mailOnly=$("input[name='mailOnly']:checked")[0]?"1":"0";
		var ynEndNum=$("input[name='ynEndNum']:checked")[0]?"1":"0";
		var ynEndTime=$("input[name='ynEndTime']:checked")[0]?"1":"0";
		var endTime=$("input[name='endTime']").val();
		var endNum=$("input[name='endNum']").val();
		var showShareSurvey=$("input[name='showShareSurvey']:checked")[0]?"1":"0";
		var showAnswerDa=$("input[name='showAnswerDa']:checked")[0]?"1":"0";
		
		data+="&effective="+effective+"&effectiveIp="+effectiveIp+"&rule="+rule+"&refresh="+refresh+"&ruleCode="+ruleCode+"&mailOnly="+mailOnly;
		data+="&ynEndNum="+ynEndNum+"&ynEndTime="+ynEndTime+"&endTime="+endTime+"&endNum="+endNum;
		data+="&showShareSurvey="+showShareSurvey+"&showAnswerDa="+showAnswerDa;
		
		var surveyWidth=$("select[name='surveyWidth']").val();
		if(surveyWidth==null || surveyWidth==""){
			surveyWidth=900;
		}
		data+="&surveyWidth="+surveyWidth;
		
		var surveyHeadBgColor=$("input[name='surveyHeadBgColor']").val();
		var surveyHeadBgImage=$("input[name='surveyHeadBgImage']").val();
		//表头边距
		var surveyHeadPaddingTop=$("input[name='surveyHeadPaddingTop']").val();
		var surveyHeadPaddingBottom=$("input[name='surveyHeadPaddingBottom']").val();
		var showSurveyHbgi=$("input[name='showSurveyHbgi']").prop("checked")?1:0;
		var surveyBtnBgColor=$("input[name='surveyBtnBgColor']").val();
		
		data+="&surveyHeadBgColor="+surveyHeadBgColor+"&surveyHeadBgImage="+surveyHeadBgImage+"&surveyHeadPaddingTop="+surveyHeadPaddingTop+"&surveyHeadPaddingBottom="+surveyHeadPaddingBottom+"&showSurveyHbgi="+showSurveyHbgi;
		data+="&surveyBtnBgColor="+surveyBtnBgColor;
		var surveyContentBgColorMiddle=$("input[name='surveyContentBgColorMiddle']").val();
		var surveyContentBgImageMiddle=$("input[name='surveyContentBgImageMiddle']").val();
		var showSurveyCbim=$("input[name='showSurveyCbim']").prop("checked")?1:0;
		data+="&surveyContentBgColorMiddle="+surveyContentBgColorMiddle+"&surveyContentBgImageMiddle="+surveyContentBgImageMiddle+"&showSurveyCbim="+showSurveyCbim;
		
		var surveyLogoImage=$("input[name='surveyLogoImage']").val();
		var showSurveyLogo=$("input[name='showSurveyLogo']").prop("checked")?1:0;
		data+="&surveyLogoImage="+surveyLogoImage+"&showSurveyLogo="+showSurveyLogo;
		//文本样式
		var questionTitleTextColor=$("input[name='questionTitleTextColor']").val();
		var questionOptionTextColor=$("input[name='questionOptionTextColor']").val();
		var surveyTitleTextColor=$("input[name='surveyTitleTextColor']").val();
		var surveyNoteTextColor=$("input[name='surveyNoteTextColor']").val();
		data+="&questionTitleTextColor="+questionTitleTextColor+"&questionOptionTextColor="+questionOptionTextColor+"&surveyTitleTextColor="+surveyTitleTextColor+"&surveyNoteTextColor="+surveyNoteTextColor;
	

		//显示头序号,进度条
		var showTiNum=$("input[name='showTiNum']:checked")[0]?"1":"0";
		var showProgressbar=$("input[name='showProgressbar']:checked")[0]?"1":"0";
		data+="&showTiNum="+showTiNum+"&showProgressbar="+showProgressbar;
		
		//表头文本显示
		var showSurTitle=$("input[name='showSurTitle']:checked")[0]?"1":"0";
		var showSurNote=$("input[name='showSurNote']:checked")[0]?"1":"0";
		data+="&showSurTitle="+showSurTitle+"&showSurNote="+showSurNote;
		
		$.ajax({
			url : url,
			data : data,
			type : "post",
			success : function(msg){
				//alert(msg);
				notify("保存成功！",5000);
			}
		});
		return false;
	});
	

	//var prevHost="http://diaowenwebfile.oss-cn-shenzhen.aliyuncs.com";
	var prevHost=$("#prevHost").val();
	//初始化弹出窗口参数
	var surveyStyleId=$("#surveyStyleId").val();
	if(surveyStyleId!=""){
		/** 背景样式 **/
		//surveyStyle.showBodyBi
		var showBodyBi="${surveyStyle.showBodyBi}";
		
		//surveyStyle.bodyBgColor
		var bodyBgColor="${surveyStyle.bodyBgColor}";
		var bodyBgColorObj=$("input[name='bodyBgColor']");
		bodyBgColorObj.val(bodyBgColor);
		var bodyBCThemeParamObj=bodyBgColorObj.parents(".theme_param");
		bodyBCThemeParamObj.find(".color_box").css({"background-color":bodyBgColor});
		//$("#wrap").css({"background-color":bodyBgColor});
		$("body").css({"background-color":bodyBgColor});
		
		//surveyStyle.bodyBgImage
		var bodyBgImage="${surveyStyle.bodyBgImage}";
		var bodyBgImageObj=$("input[name='bodyBgImage']");
		var bodyBIThemeParamObj=bodyBgImageObj.parents(".theme_param");
		bodyBgImageObj.val(bodyBgImage);
		bodyBIThemeParamObj.find(".previewImage").attr("src",prevHost+bodyBgImage);
		if(showBodyBi==1){
			//$("#wrap").css({"background-image":"url("+bodyBgImage+")"});
			$("body").css({"background-image":"url("+prevHost+bodyBgImage+")"});
			$("input[name='showBodyBi']").prop("checked",true);
		}
		
		/** 表头样式 **/
		//surveyStyle.showBodyBi
		var showSurveyHbgi="${surveyStyle.showSurveyHbgi}";
		
		//surveyStyle.bodyBgColor
		var surveyHeadBgColor="${surveyStyle.surveyHeadBgColor}";
		var surveyHeadBgColorObj=$("input[name='surveyHeadBgColor']");
		var surveyHBCThemeParamObj=surveyHeadBgColorObj.parents(".theme_param");
		surveyHeadBgColorObj.val(surveyHeadBgColor);
		surveyHBCThemeParamObj.find(".color_box").css({"background-color":surveyHeadBgColor});
		$("#dwSurveyHeader").css({"background-color":surveyHeadBgColor});
		
		//surveyStyle.bodyBgImage
		var surveyHeadBgImage="${surveyStyle.surveyHeadBgImage}";
		var surveyHeadBgImageObj=$("input[name='surveyHeadBgImage']");
		var surveyHBIThemeParamObj=surveyHeadBgImageObj.parents(".theme_param");
		surveyHeadBgImageObj.val(surveyHeadBgImage);
		surveyHBIThemeParamObj.find(".previewImage").attr("src",prevHost+surveyHeadBgImage);
		if(showSurveyHbgi==1){
			$("#dwSurveyHeader").css({"background-image":"url("+prevHost+surveyHeadBgImage+")"});
			$("input[name='showSurveyHbgi']").prop("checked",true);
		}
		
		
		//表头边距
		var surveyHeadPaddingTop="${surveyStyle.surveyHeadPaddingTop}";
		var surveyHeadPaddingBottom="${surveyStyle.surveyHeadPaddingBottom}";
		$("input[name='surveyHeadPaddingTop']").val(surveyHeadPaddingTop);
		$("input[name='surveyHeadPaddingBottom']").val(surveyHeadPaddingBottom);
		
		$("#dwSurveyHeader").css({"padding-top":surveyHeadPaddingTop+"px"});
		$("#dwSurveyHeader").css({"padding-bottom":surveyHeadPaddingBottom+"px"});
		
		/** 内容中间样式 **/
		//surveyStyle.showBodyBi
		var showSurveyCbim="${surveyStyle.showSurveyCbim}";
		
		//surveyStyle.bodyBgColor
		var surveyContentBgColorMiddle="${surveyStyle.surveyContentBgColorMiddle}";
		var surveyContentBgColorMiddleObj=$("input[name='surveyContentBgColorMiddle']");
		var surveyCBCMThemeParamObj=surveyContentBgColorMiddleObj.parents(".theme_param");
		surveyContentBgColorMiddleObj.val(surveyContentBgColorMiddle);
		surveyCBCMThemeParamObj.find(".color_box").css({"background-color":surveyContentBgColorMiddle});;
		$("#dwSurveyQuContentBg").css({"background-color":surveyContentBgColorMiddle});
		
		//surveyStyle.bodyBgImage
		var surveyContentBgImageMiddle="${surveyStyle.surveyContentBgImageMiddle}";
		var surveyContentBgImageMiddleObj=$("input[name='surveyContentBgImageMiddle']");
		var surveyCBIMThemeParamObj=surveyContentBgImageMiddleObj.parents(".theme_param");
		surveyContentBgImageMiddleObj.val(surveyContentBgImageMiddle);
		surveyCBIMThemeParamObj.find(".previewImage").attr("src",prevHost+surveyContentBgImageMiddle);
		if(showSurveyCbim==1){
			$("#dwSurveyQuContentBg").css({"background-image":"url("+prevHost+surveyContentBgImageMiddle+")"});
			$("input[name='showSurveyCbim']").prop("checked",true);
		}
		
		/** 文本样式 **/
		var questionTitleTextColor="${surveyStyle.questionTitleTextColor}";
		var questionTitleTextColorObj=$("input[name='questionTitleTextColor']");
		var questionTTCThemeParamObj=questionTitleTextColorObj.parents(".theme_param");
		questionTitleTextColorObj.val(questionTitleTextColor);
		questionTTCThemeParamObj.find(".color_box").css({"background-color":questionTitleTextColor});
		$(".quCoTitle").css({"color":questionTitleTextColor});
		
		var questionOptionTextColor="${surveyStyle.questionOptionTextColor}";
		var questionOptionTextColorObj=$("input[name='questionOptionTextColor']");
		var questionOTCThemeParamObj=questionOptionTextColorObj.parents(".theme_param");
		questionOptionTextColorObj.val(questionOptionTextColor);
		questionOTCThemeParamObj.find(".color_box").css({"background-color":questionOptionTextColor});
		$(".quCoOptionEdit").css({"color":questionOptionTextColor});
		
		var surveyTitleTextColor="${surveyStyle.surveyTitleTextColor}";
		var surveyTitleTextColorObj=$("input[name='surveyTitleTextColor']");
		var surveyTTCThemeParamObj=surveyTitleTextColorObj.parents(".theme_param");
		surveyTitleTextColorObj.val(surveyTitleTextColor);
		surveyTTCThemeParamObj.find(".color_box").css({"background-color":surveyTitleTextColor});
		$("#dwSurveyTitle").css({"color":surveyTitleTextColor});
		
		var surveyNoteTextColor="${surveyStyle.surveyNoteTextColor}";
		var surveyNoteTextColorObj=$("input[name='surveyNoteTextColor']");
		var surveyNTCThemeParamObj=surveyNoteTextColorObj.parents(".theme_param");
		surveyNoteTextColorObj.val(surveyNoteTextColor);
		surveyNTCThemeParamObj.find(".color_box").css({"background-color":surveyNoteTextColor});
		$("#dwSurveyNoteEdit").css({"color":surveyNoteTextColor});
		
		var surveyWidth="${surveyStyle.surveyWidth}";
		$("#dw_body_content").width(surveyWidth);
		$("select[name='surveyWidth']").val(surveyWidth);
		
		var surveyBtnBgColor="${surveyStyle.surveyBtnBgColor}";
		if(surveyBtnBgColor!==""){
			$("#dw_body_content .sbtn24").css({"background":"none"});
			$("#dw_body_content .sbtn24,.progressbarDiv .ui-progressbar-value").css({"background-color":surveyBtnBgColor});
			$(".progressbarDiv").css({"border-color":surveyBtnBgColor});
			$(".progress-label ").css({"color":surveyBtnBgColor});
			var surveyBtnBgColorObj=$("input[name='surveyBtnBgColor']");
			surveyBtnBgColorObj.val(surveyBtnBgColor);
			var btnBcThemeParamObj=surveyBtnBgColorObj.parents(".theme_param");
			btnBcThemeParamObj.find(".color_box").css({"background-color":surveyBtnBgColor});
		}
		
		//显示序号及进度条
		var showTiNum="${surveyStyle.showTiNum}";
		var showProgressbar="${surveyStyle.showProgressbar}";
		if(showTiNum==0){
			$("input[name='showTiNum']").prop("checked",false);
			$(".quCoNum").hide();
		}
		if(showProgressbar==0){
			$("input[name='showProgressbar']").prop("checked",false);		
			$("#resultProgressRoot").hide();
		}
			
		//表头文本显示
		var showSurTitle="${surveyStyle.showSurTitle}";
		var showSurNote="${surveyStyle.showSurNote}";
		if(showSurTitle==0){
			$("input[name='showSurTitle']").prop("checked",false);
			$("#dwSurveyTitle").hide();
		}
		if(showSurNote==0){
			$("input[name='showSurNote']").prop("checked",false);
			$("#dwSurveyNote").hide();
		}
	}
	
	$(".themenail").click(function(){
		var styleModelId=$(this).find(".styleModelId").val();
		//alert(styleModelId);
		//应用模板样式
		var url="${ctx}/design/my-survey-style!ajaxGetStyle.action";
		var data="id="+styleModelId;
		$.ajax({
			url:url,
			data:data,
			type:"post",
			success:function(msg){
				//alert(msg);
				var surveyStyle=eval("("+msg+")");
				
				var showBodyBi=surveyStyle.showBodyBi;
				
				//surveyStyle.bodyBgColor
				var bodyBgColor=surveyStyle.bodyBgColor;
				var bodyBgColorObj=$("input[name='bodyBgColor']");
				bodyBgColorObj.val(bodyBgColor);
				var bodyBCThemeParamObj=bodyBgColorObj.parents(".theme_param");
				bodyBCThemeParamObj.find(".color_box").css({"background-color":bodyBgColor});
				//$("#wrap").css({"background-color":bodyBgColor});
				$("body").css({"background-color":bodyBgColor});
				
				//surveyStyle.bodyBgImage
				var bodyBgImage=surveyStyle.bodyBgImage;
				var bodyBgImageObj=$("input[name='bodyBgImage']");
				var bodyBIThemeParamObj=bodyBgImageObj.parents(".theme_param");
				bodyBgImageObj.val(bodyBgImage);
				bodyBIThemeParamObj.find(".previewImage").attr("src","${ctx}"+bodyBgImage);
				if(showBodyBi==1){
					//$("#wrap").css({"background-image":"url("+bodyBgImage+")"});
					$("body").css({"background-image":"url("+"${ctx}"+bodyBgImage+")"});
					$("input[name='showBodyBi']").prop("checked",true);
				}
				
				/** 表头样式 **/
				//surveyStyle.showBodyBi
				var showSurveyHbgi=surveyStyle.showSurveyHbgi;
				
				//surveyStyle.bodyBgColor
				var surveyHeadBgColor=surveyStyle.surveyHeadBgColor;
				var surveyHeadBgColorObj=$("input[name='surveyHeadBgColor']");
				var surveyHBCThemeParamObj=surveyHeadBgColorObj.parents(".theme_param");
				surveyHeadBgColorObj.val(surveyHeadBgColor);
				surveyHBCThemeParamObj.find(".color_box").css({"background-color":surveyHeadBgColor});
				$("#dwSurveyHeader").css({"background-color":surveyHeadBgColor});
				
				//surveyStyle.bodyBgImage
				var surveyHeadBgImage=surveyStyle.surveyHeadBgImage;
				var surveyHeadBgImageObj=$("input[name='surveyHeadBgImage']");
				var surveyHBIThemeParamObj=surveyHeadBgImageObj.parents(".theme_param");
				surveyHeadBgImageObj.val(surveyHeadBgImage);
				surveyHBIThemeParamObj.find(".previewImage").attr("src","${ctx}"+surveyHeadBgImage);
				if(showSurveyHbgi==1){
					$("#dwSurveyHeader").css({"background-image":"url("+"${ctx}"+surveyHeadBgImage+")"});
					$("input[name='showSurveyHbgi']").prop("checked",true);
				}
				
				/** 内容中间样式 **/
				//surveyStyle.showBodyBi
				var showSurveyCbim=surveyStyle.showSurveyCbim;
				
				//surveyStyle.bodyBgColor
				var surveyContentBgColorMiddle=surveyStyle.surveyContentBgColorMiddle;
				var surveyContentBgColorMiddleObj=$("input[name='surveyContentBgColorMiddle']");
				var surveyCBCMThemeParamObj=surveyContentBgColorMiddleObj.parents(".theme_param");
				surveyContentBgColorMiddleObj.val(surveyContentBgColorMiddle);
				surveyCBCMThemeParamObj.find(".color_box").css({"background-color":surveyContentBgColorMiddle});;
				$("#dwSurveyQuContentBg").css({"background-color":surveyContentBgColorMiddle});
				
				//surveyStyle.bodyBgImage
				var surveyContentBgImageMiddle=surveyStyle.surveyContentBgImageMiddle;
				var surveyContentBgImageMiddleObj=$("input[name='surveyContentBgImageMiddle']");
				var surveyCBIMThemeParamObj=surveyContentBgImageMiddleObj.parents(".theme_param");
				surveyContentBgImageMiddleObj.val(surveyContentBgImageMiddle);
				surveyCBIMThemeParamObj.find(".previewImage").attr("src","${ctx}"+surveyContentBgImageMiddle);
				if(showSurveyCbim==1){
					$("#dwSurveyQuContentBg").css({"background-image":"url("+"${ctx}"+surveyContentBgImageMiddle+")"});
					$("input[name='showSurveyCbim']").prop("checked",true);
				}
				
				/** 文本样式 **/
				var questionTitleTextColor=surveyStyle.questionTitleTextColor;
				var questionTitleTextColorObj=$("input[name='questionTitleTextColor']");
				var questionTTCThemeParamObj=questionTitleTextColorObj.parents(".theme_param");
				questionTitleTextColorObj.val(questionTitleTextColor);
				questionTTCThemeParamObj.find(".color_box").css({"background-color":questionTitleTextColor});
				$(".quCoTitle").css({"color":questionTitleTextColor});
				
				var questionOptionTextColor=surveyStyle.questionOptionTextColor;
				var questionOptionTextColorObj=$("input[name='questionOptionTextColor']");
				var questionOTCThemeParamObj=questionOptionTextColorObj.parents(".theme_param");
				questionOptionTextColorObj.val(questionOptionTextColor);
				questionOTCThemeParamObj.find(".color_box").css({"background-color":questionOptionTextColor});
				$(".quCoOptionEdit").css({"color":questionOptionTextColor});
				
				var surveyTitleTextColor=surveyStyle.surveyTitleTextColor;
				var surveyTitleTextColorObj=$("input[name='surveyTitleTextColor']");
				var surveyTTCThemeParamObj=surveyTitleTextColorObj.parents(".theme_param");
				surveyTitleTextColorObj.val(surveyTitleTextColor);
				surveyTTCThemeParamObj.find(".color_box").css({"background-color":surveyTitleTextColor});
				$("#dwSurveyTitle").css({"color":surveyTitleTextColor});
				
				var surveyNoteTextColor=surveyStyle.surveyNoteTextColor;
				var surveyNoteTextColorObj=$("input[name='surveyNoteTextColor']");
				var surveyNTCThemeParamObj=surveyNoteTextColorObj.parents(".theme_param");
				surveyNoteTextColorObj.val(surveyNoteTextColor);
				surveyNTCThemeParamObj.find(".color_box").css({"background-color":surveyNoteTextColor});
				$("#dwSurveyNoteEdit").css({"color":surveyNoteTextColor});
				
				var surveyBtnBgColor=surveyStyle.surveyBtnBgColor;
				if(surveyBtnBgColor!==""){
					$("#dw_body_content .sbtn24").css({"background":"none"});
					$("#dw_body_content .sbtn24,.progressbarDiv .ui-progressbar-value").css({"background-color":surveyBtnBgColor});
					$(".progressbarDiv").css({"border-color":surveyBtnBgColor});
					$(".progress-label ").css({"color":surveyBtnBgColor});
					var surveyBtnBgColorObj=$("input[name='surveyBtnBgColor']");
					surveyBtnBgColorObj.val(surveyBtnBgColor);
					var btnBcThemeParamObj=surveyBtnBgColorObj.parents(".theme_param");
					btnBcThemeParamObj.find(".color_box").css({"background-color":surveyBtnBgColor});
				}
				
			}
		});
		return false;
	});
	
	$("input[name='showTiNum']").change(function(){
		if($(this).prop("checked")){
			//$("#resultProgressRoot").show();
			$(".quCoNum").show();
		}else{
			$(".quCoNum").hide();
			//$("#resultProgressRoot").hide();
		}
		return false;
	});
	
	$("input[name='showProgressbar']").change(function(){
		if($(this).prop("checked")){
			$("#resultProgressRoot").show();
		}else{
			$("#resultProgressRoot").hide();
		}
		return false;
	});
	
	//问卷标题
	$("input[name='showSurTitle']").change(function(){
		if($(this).prop("checked")){
			$("#dwSurveyTitle").show();
		}else{
			$("#dwSurveyTitle").hide();
		}
		return false;
	});

	$("input[name='showSurNote']").change(function(){
		if($(this).prop("checked")){
			$("#dwSurveyNote").show();
		}else{
			$("#dwSurveyNote").hide();
		}
		return false;
	});
	
	$("input[name='showSurHead']").change(function(){
		if($(this).prop("checked")){
			$("#dwSurveyHeader").show();
		}else{
			$("#dwSurveyHeader").hide();
		}
		return false;
	});
	
	//参数survey
	/* var effective="${survey.surveyDetail.effective}";
	var effectiveIp="${survey.surveyDetail.effectiveIp}";
	var rule="${survey.surveyDetail.rule}";
	var ruleCode="${survey.surveyDetail.ruleCode}";
	var mailOnly="${survey.surveyDetail.mailOnly}";
	var ynEndNum="${survey.surveyDetail.ynEndNum}";
	var ynEndTime="${survey.surveyDetail.ynEndTime}";
	var endTime="${survey.surveyDetail.endTime}";
	var endNum="${survey.surveyDetail.endNum}";
	var showShareSurvey="${survey.surveyDetail.showShareSurvey}";
	var showAnswerDa="${survey.surveyDetail.showAnswerDa}";
	if(effective==="4"){
		$("input[name='effective']").attr("checked",true);
	}
	if(effectiveIp==="1"){
		$("input[name='effectiveIp']").attr("checked",true);
	}
	if(rule==="3"){
		$("input[name='rule']").attr("checked",true);
		$("input[name='ruleCode']").val(ruleCode);
	}
	if(mailOnly==="1"){
		$("input[name='mailOnly']").attr("checked",true);
	}
	if(ynEndNum==="1"){
		$("input[name='ynEndNum']").attr("checked",true);
	}
	if(ynEndTime==="1"){
		$("input[name='ynEndTime']").attr("checked",true);
	}
	if(showShareSurvey==="1"){
		$("input[name='showShareSurvey']").attr("checked",true);
	}
	if(showAnswerDa==="1"){
		$("input[name='showAnswerDa']").attr("checked",true);
	}
	$("input[name='refresh'][value='${survey.surveyDetail.refresh}']").attr("checked",true); */
	
	//$("input[name='effective'][value='${survey.surveyDetail.effective}']").attr("checked",true);
	
	if("${survey.surveyDetail.effective}">1){
		$("input[name='effective']").attr("checked",true);	
	}else{
		$("input[name='effective']").attr("checked",false);
	}
	
	$("input[name='effectiveIp'][value='${survey.surveyDetail.effectiveIp}']").attr("checked",true);
	$("input[name='rule'][value='${survey.surveyDetail.rule}']").attr("checked",true);
	$("input[name='ruleCode']").val("${survey.surveyDetail.ruleCode}");
	$("input[name='refresh'][value='${survey.surveyDetail.refresh}']").attr("checked",true);
	$("input[name='mailOnly'][value='${survey.surveyDetail.mailOnly}']").attr("checked",true);
	$("input[name='ynEndNum'][value='${survey.surveyDetail.ynEndNum}']").attr("checked",true);
	$("input[name='endNum']").val("${survey.surveyDetail.endNum}");
	$("input[name='ynEndTime'][value='${survey.surveyDetail.ynEndTime}']").attr("checked",true);
	$("input[name='endTime']").val("${survey.surveyDetail.endTime}");
	$("input[name='showShareSurvey'][value='${survey.surveyDetail.showShareSurvey}']").attr("checked",true);
	
	$("input[name='showAnswerDa'][value='${survey.surveyDetail.showAnswerDa}']").attr("checked",true);
	
	
});
</script>
<style type="text/css">
.edui-editor-iframeholder{
	display: none;
}
.edui-default .edui-editor-toolbarboxouter{
	border: none! important;
}
#resultProgressRoot .ui-slider-range { background: #B01820; }
#resultProgressRoot .ui-slider-handle { border-color: #B01820; }

#resultProgressRoot{
	width: 220px;
	z-index: 200;
	position: absolute;
	right: 60px;
	/*
	top:100px;
	right: 20px; */
	
	/* width:18px;
	height: 200px;
	border: 1px solid #83AE00; */
}
.progress-label {
	font-size:14px;
	font-family: "微软雅黑";
	margin: 0px auto;
	text-align: center;
	line-height: 1.4em;
	color: #83AE00;
}
.progressbarDiv {
	height: 6px! important;
	box-shadow: none! important;
	border: 1px solid #83AE00;
}
 .progressbarDiv .ui-progressbar-value{
	background: #83AE00;
	border: none;
} 
.ui-progressbar .ui-progressbar-value{
	margin: 0px;
}
.ui-progressbar {
	position: relative;
}
</style>
</head>
<body>

<div id="wrap">
<input type="hidden" id="id" name="id" value="${survey.id }">
<input type="hidden" id="ctx" name="ctx" value="${ctx }">
<input type="hidden" id="surveyStyleId" value="${surveyStyle.id }">
<input type="hidden" id="prevHost" value="${ctx }">
<div id="preview_head">
	<div class="leftTabbar">
		<ul>
			<li>
				<div class="tabbarTitle">收集规则</div>
				<div class="tabbarDialog">
					<div class="p_DialogContent">
						<div class="p_DialogContentTitle">回答限制</div>
						<div class="p_DialogContentRoot">
								<div class="p_DialogContentItem"><label><input type="checkbox" name="effective" value="4"> 每台电脑或手机只能答一次</label></div>
								<div class="p_DialogContentItem"><label><input type="checkbox" name="effectiveIp" value="1"> 每个IP只能答一次</label></div>
								<div class="p_DialogContentItem"><label><input type="checkbox" name="rule" value="3"> 启用访问密码</label>
								&nbsp;&nbsp;&nbsp;设置密码：<input type="text" size="10"  name="ruleCode" class="inputSytle_1"></div>
								<div class="p_DialogContentItem"><label><input type="checkbox" name="refresh" value="1"> 有重复回答启用验证码</label></div>
								<div class="p_DialogContentItem" style="display: none;" ><label><input type="checkbox" name="mailOnly" value="1"> 只有邮件邀请唯一链接的受访者可回答</label></div>
						</div>
					</div>
					<div class="p_DialogContent">
						<div class="p_DialogContentTitle">何时结束</div>
						<div class="p_DialogContentRoot">
								<div class="p_DialogContentItem"><label><input type="checkbox" name="ynEndNum" value="1"> 收集到&nbsp;<input value="${survey.surveyDetail.endNum}" name="endNum" type="text" size="10"  class="inputSytle_1">&nbsp;份答卷时结束</label></div>
								<div class="p_DialogContentItem"><label><input type="checkbox" name="ynEndTime" value="1"> 到&nbsp;<input value="${survey.surveyDetail.endTime}" name="endTime" type="text" size="16"  class="inputSytle_1 Wdate" onfocus="WdatePicker({minDate:'%y-%M-{%d+1}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" >&nbsp;时结束 </label></div>
						</div>
					</div>
					<div class="p_DialogContent">
						<div class="p_DialogContentTitle">答完后</div>
						<div class="p_DialogContentRoot">
								<div class="p_DialogContentItem"><label><input type="checkbox" name="showShareSurvey" value="1"> 显示分享按钮，分享答题链接到更多社交网站</label></div>
								<div class="p_DialogContentItem"><label><input type="checkbox" name="showAnswerDa" value="1"> 允许受访人答完问卷后查看结果</label></div>
						</div>
					</div>
				</div>
			</li>
			<li>
				<div class="tabbarTitle">样式模板</div>
				<div class="tabbarDialog tabbarDialogTheme" >
				<div class="tabbarDialogContent">
					<div class="pc_themeContentScorll">
						<div class="pc_themeContent">
					<div class="themeitem" >
                  		<div class="themenail">
                    		<img src="${ctx }/images/style-model/1123/zhi.png">
                    		<input type="hidden" class="styleModelId" value="402880e849f0f5e70149f104e47d0000">
                  		</div>
                  		<h5>植</h5>
                	</div>
                	<div class="themeitem" >
                  		<div class="themenail">
                    		<img src="${ctx }/images/style-model/1123/ha.png">
                    		<input type="hidden" class="styleModelId" value="402880e849f0f5e70149f1090d600001">
                  		</div>
                  		<h5>果</h5>
                	</div>
                	<div class="themeitem" >
                  		<div class="themenail">
                    		<img src="${ctx }/images/style-model/1123/zu.png">
                    		<input type="hidden" class="styleModelId" value="402880e849f0f5e70149f10c0c7e0002">
                  		</div>
                  		<h5>筑</h5>
                	</div>
                	<div class="themeitem" >
                  		<div class="themenail">
                    		<img src="${ctx }/images/style-model/1123/rr.png">
                    		<input type="hidden" class="styleModelId" value="402880ea484021d10148402e42070000">
                  		</div>
                  		<h5>教</h5>
                	</div>
                	<div class="themeitem" >
                  		<div class="themenail">
                    		<img src="${ctx }/images/style-model/1123/jo.png">
                    		<input type="hidden" class="styleModelId" value="402880e849f0f5e70149f10edd0c0003">
                  		</div>
                  		<h5>育1</h5>
                	</div>
                	<div class="themeitem" >
                  		<div class="themenail">
                    		<img src="${ctx }/images/style-model/1123/ru.png">
                    		<input type="hidden" class="styleModelId" value="402880e849f0f5e70149f1121f320004">
                  		</div>
                  		<h5>育2</h5>
                	</div>
                	
                	<div class="themeitem" >
                  		<div class="themenail">
                    		<img src="${ctx }/images/style-model/1123/kj.png">
                    		<input type="hidden" class="styleModelId" value="402880eb49dcabfd0149dcb359370000">
                  		</div>
                  		<h5>科</h5>
                	</div>
                	<div class="themeitem" >
                  		<div class="themenail">
                    		<img src="${ctx }/images/style-model/1123/ly.png">
                    		<input type="hidden" class="styleModelId" value="402880e849f0f5e70149f13f18ba0007">
                  		</div>
                  		<h5>游</h5>
                	</div>
                	<div class="themeitem" >
                  		<div class="themenail">
                    		<img src="${ctx }/images/style-model/1123/da.png">
                    		<input type="hidden" class="styleModelId" value="402880e849f0f5e70149f141f3700008">
                  		</div>
                  		<h5>谈</h5>
                	</div>
                	<div class="themeitem" >
                  		<div class="themenail">
                    		<img src="${ctx }/images/style-model/1123/da1.png">
                    		<input type="hidden" class="styleModelId" value="402880e849f0f5e70149f1441b000009">
                  		</div>
                  		<h5>兰</h5>
                	</div>
                	<div class="themeitem" >
                  		<div class="themenail">
                    		<img src="${ctx }/images/style-model/1123/da2.png">
                    		<input type="hidden" class="styleModelId" value="402880e849f0f5e70149f1461613000a">
                  		</div>
                  		<h5>花</h5>
                	</div>
                	<div class="themeitem" >
                  		<div class="themenail">
                    		<img src="${ctx }/images/style-model/1123/qo.png">
                    		<input type="hidden" class="styleModelId" value="402880e849f0f5e70149f147530d000b">
                  		</div>
                  		<h5>秋</h5>
                	</div>
                	<div class="themeitem" >
                  		<div class="themenail">
                    		<img src="${ctx }/images/style-model/1123/xq.png">
                    		<input type="hidden" class="styleModelId" value="402880eb49dcabfd0149dcbe2db10002">
                  		</div>
                  		<h5>庆</h5>
                	</div>
                	<div class="themeitem" >
                  		<div class="themenail">
                    		<img src="${ctx }/images/style-model/1123/g1.png">
                    		<input type="hidden" class="styleModelId" value="402880e849f0f5e70149f117022d0006">
                  		</div>
                  		<h5>黑</h5>
                	</div>
                	
					<%-- <div class="themeitem" >
                  		<div class="themenail">
                    		<img src="${ctx }/images/style-model/5629500633724777696.png">
                  		</div>
                  		<h5>起点</h5>
                	</div>
                	<div class="themeitem" >
                  		<div class="themenail">
                    		<img src="${ctx }/images/style-model/6597088458353679859.jpg">
                  		</div>
                  		<h5>尚</h5>
                	</div>
                	<div class="themeitem" >
                  		<div class="themenail">
                    		<img src="${ctx }/images/style-model/6597088458353679861.jpg">
                  		</div>
                  		<h5>商</h5>
                	</div>
                	<div class="themeitem" >
                  		<div class="themenail">
                    		<img src="${ctx }/images/style-model/5629507230794542837.jpg">
                  		</div>
                  		<h5>天猫</h5>
                	</div>
                	<div class="themeitem" >
                  		<div class="themenail">
                    		<img src="${ctx }/images/style-model/5629512728352684315.png">
                  		</div>
                  		<h5>运行</h5>
                	</div>
                	<div class="themeitem" >
                  		<div class="themenail">
                    		<img src="${ctx }/images/style-model/5629513827864306743.jpg">
                  		</div>
                  		<h5>青春</h5>
                	</div>
                	<div class="themeitem" >
                  		<div class="themenail">
                    		<img src="${ctx }/images/style-model/5629518225910816657.jpg">
                  		</div>
                  		<h5>灰色良品</h5>
                	</div>
                	<div class="themeitem" >
                  		<div class="themenail">
                    		<img src="${ctx }/images/style-model/6597081861283910754.jpg">
                  		</div>
                  		<h5>学校</h5>
                	</div>
                	<div class="themeitem" >
                  		<div class="themenail">
                    		<img src="${ctx }/images/style-model/6597081861283913694.jpg">
                  		</div>
                  		<h5>女生</h5>
                	</div>
                	<div class="themeitem" >
                  		<div class="themenail">
                    		<img src="${ctx }/images/style-model/6597085159818795727.png">
                  		</div>
                  		<h5>设计</h5>
                	</div>
                	<div class="themeitem" >
                  		<div class="themenail">
                    		<img src="${ctx }/images/style-model/6597085159818795728.jpg">
                  		</div>
                  		<h5>科</h5>
                	</div>
                	 --%>
					</div>
					</div>
				</div>
				</div>
			</li>


			<li style="display: none;">
				<input class="paramtag" type="hidden" name="bodyBgColor" value="#E8E9EB">
				<input class="paramtag" type="hidden" name="surveyHeadBgColor" value="#FFFFFF">
				<input class="paramtag" type="hidden" name="surveyContentBgColorMiddle" value="#FFFFFF">
				<input class="paramtag" type="hidden" name="surveyBtnBgColor" value="#7EA800">

				<input class="paramtag" type="hidden" name="bodyBgImage" value="/images/style-model/5629512728352684315.png">
				<input class="upUseImgCheck" name="showBodyBi" type="checkbox" style="margin-right:3px;">
				<input class="paramtag" type="hidden" name="surveyHeadBgImage" value="/images/style-model/5629512728352684315.png">
				<input class="upUseImgCheck" type="checkbox" name="showSurveyHbgi"  style="margin-right:3px;">
				<input class="paramtag" type="hidden" name="surveyContentBgImageMiddle" value="/images/style-model/1123/29153737.jpg">
				<input class="upUseImgCheck" type="checkbox" name="showSurveyCbim" style="margin-right:3px;">
				<input class="paramtag" type="hidden" name="surveyLogoImage" value="${ctx }/images/logo/108-108.jpg">
				<input class="paramtag" type="hidden" name="questionTitleTextColor" value="#333333">
				<input class="paramtag" type="hidden" name="questionOptionTextColor" value="#333333">
				<input class="paramtag" type="hidden" name="surveyTitleTextColor" value="#222222">
				<input class="paramtag" type="hidden" name="surveyNoteTextColor" value="#333333">
				<input type="hidden" name="surveyWidth" value="950" >
				<input value="0" type="hidden" size="5" name="surveyHeadPaddingTop" >
				<input value="0" type="hidden" size="5" name="surveyHeadPaddingBottom" >
				<input name="showTiNum" type="checkbox" checked="checked" style="margin-right:3px;">
				<input name="showProgressbar" type="checkbox"  checked="checked" style="margin-right:3px;">
				<input name="showSurTitle" type="checkbox"  checked="checked" style="margin-right:3px;">
				<input name="showSurNote" type="checkbox"  checked="checked" style="margin-right:3px;">
			</li>


			<li style="display: none;">
				<div class="tabbarTitle">手机端样式</div>
				<div class="tabbarDialog">
					<div class="p_DialogContent">
						<div class="p_DialogContentTitle">回答限制</div>
						<div class="p_DialogContentRoot">
								<div class="p_DialogContentItem"><label><input type="checkbox"> 每台电脑或手机只能答一次</label></div>
								<div class="p_DialogContentItem"><label><input type="checkbox"> 每个IP只能答一次</label></div>
								<div class="p_DialogContentItem"><label><input type="checkbox"> 启用访问密码</label></div>
								<div class="p_DialogContentItem"><label><input type="checkbox"> 有重复回答启用验证码</label></div>
								<div class="p_DialogContentItem"><label><input type="checkbox"> 只有邮件邀请唯一链接的受访者可回答</label></div>
						</div>
					</div>
					<div class="p_DialogContent">
						<div class="p_DialogContentTitle">何时结束</div>
						<div class="p_DialogContentRoot">
								<div class="p_DialogContentItem"><label><input type="checkbox"> 收集到&nbsp;<input type="text" size="8"  class="inputSytle_1">&nbsp;份答卷时结束</label></div>
								<div class="p_DialogContentItem"><label><input type="checkbox"> 到&nbsp;<input type="text" size="2"  class="inputSytle_1">&nbsp;时结束 </label></div>
						</div>
					</div>
					<div class="p_DialogContent">
						<div class="p_DialogContentTitle">答完后</div>
						<div class="p_DialogContentRoot">
								<div class="p_DialogContentItem"><label><input type="checkbox"> 显示分享按钮，分享答题链接到更多社交网站</label></div>
								<div class="p_DialogContentItem"><label><input type="checkbox"> 允许受访人答完问卷后查看结果</label></div>
						</div>
					</div>
				</div>
			</li>
			<li>
				<div class="centerTabbar">
					<!-- <a href="" class="centerTabbarBtn active">PC端</a>
					<a href="" class="centerTabbarBtn">手机端</a>
					<a href="" class="centerTabbarBtn">平板端</a> -->
					<a href="#pc" class="centerTabbarBtn active" title="PC端"><i class="fa fa-desktop"></i></a>
					<!-- <a href="" class="centerTabbarBtn" title="平板端"><i class="fa fa-tablet"></i></a> -->
					<a href="#phone" class="centerTabbarBtn" title="手机端"><i class="fa fa-mobile-phone"></i></a>
				</div>
			</li>
		</ul>
	</div>
	<div class="rightTabbar">
		<a id="confirgDevSuvey" href="#" class="sbtn24 sbtn24_0">确认发布</a>
		<a href="#" class="sbtn24 sbtn24_0" id="saveStyleDev">保　存</a>
		<a href="${ctx }/design/my-survey-design.action?surveyId=${survey.id}" class="sbtn24 sbtn24_1">返回修改</a>
	</div>
	<div style="clear: both;"></div>
	<!-- <div class="centerTabbar">
		<a href="" class="centerTabbarBtn active">PC端</a>
		<a href="" class="centerTabbarBtn">移动端</a>
	</div> -->
</div>

<div id="dw_body" style="">
	<div id="dw_body_content">
		<div id="dwSurveyHeader">
			<div id="dwSurveyLogo"  style="position: absolute;right: 0;padding-top:0px;"><img src="${ctx }/images/logo/sample_logo.png" height="50"/> </div>
			<%-- <div id="dwSurveyTitle" class="noLogoImg editAble" >${survey.surveyName }</div> --%>
			<div id="dwSurveyTitle" class="noLogoImg">
				<div id="dwSurveyName" class="editAble dwSvyName">${survey.surveyName }</div>
			</div>
			<div id="dwSurveyNote">
				<div id="dwSurveyNoteEdit"  class="editAble">${survey.surveyDetail.surveyNote }</div>
			</div>
			
		</div>
		<div id="dwSurveyQuContent" style="">
			<div id="dwSurveyQuContentBg">
			<c:set var="pageNo" value="1"></c:set>
			<c:set var="isNextPage" value="0"></c:set>
			<ul id="dwSurveyQuContentAppUl">
				<!-- 题目内容 -->
				<c:forEach items="${survey.questions }" var="en" varStatus="i">
				<li class="li_surveyQuItemBody surveyQu_${pageNo }"  style="${pageNo gt 1 ?'display: none':''}">
				<c:choose>
					<c:when test="${en.quType eq 'RADIO' }">
						<div class="surveyQuItemBody">
							<div class="initLine"></div>
							<div class="quInputCase" style="display: none;">
								<input type="hidden" name="quType" value="RADIO">
								<input type="hidden" name="quId" value="${en.id }">
								<input type="hidden" name="orderById" value="${en.orderById }"/>
								<input type="hidden" name="saveTag" value="1">
								<input type="hidden" name="hoverTag" value="0">
								<input type="hidden" name="isRequired" value="${en.isRequired }">
								<input type="hidden" name="hv" value="${en.hv }">
								<input type="hidden" name="randOrder" value="${en.randOrder }">
								<input type="hidden" name="cellCount" value="${en.cellCount }">
								<div class="quLogicInputCase">
								<input type="hidden" name="quLogicItemNum" value="${fn:length(en.questionLogics) }">
								<c:forEach items="${en.questionLogics }" var="quLogicEn" varStatus="logicSts">
									<div class="quLogicItem quLogicItem_${logicSts.count }">
										<input type="hidden" name="quLogicId" value="${quLogicEn.id }"/>
										<input type="hidden" name="cgQuItemId" value="${quLogicEn.cgQuItemId }"/>
										<input type="hidden" name="skQuId" value="${quLogicEn.skQuId }"/>
										<input type="hidden" name="visibility" value="1">
										<input type="hidden" name="logicSaveTag" value="1">
									</div>
								</c:forEach>
								</div>
							</div>
							<div class="surveyQuItem">
								
								<div class="surveyQuItemContent">
									<div class="quCoTitle">
										<div class="quCoNum">${i.count }、</div>
										<div class="editAble quCoTitleEdit" >${en.quTitle}</div>
										<input type="hidden" name="quTitleSaveTag" value="1">
									</div>
									<div class="quCoItem">
									<c:choose>
										<c:when test="${en.hv eq 3 }">
											<table class='tableQuColItem'>
												<c:forEach begin="0" end="${fn:length(en.quRadios)-1 }" var="j" step="${en.cellCount }">
												<tr>
												<c:forEach begin="1" end="${en.cellCount }" var="k">
												<td width="${600/en.cellCount }">
													<!-- 判断不为空，访止数组越界 -->
													<c:set var="quOptionIndex" value="${(j+k-1) }" ></c:set>
													<c:choose>
														<c:when test="${quOptionIndex < fn:length(en.quRadios) }">
																<div class="dwQuOptionItemContent">
																<label class="dwRedioStyle dwQuInputLabel" ></label>
																<input type="radio" ><label style="width:${600/en.cellCount-10 }px;" class="editAble quCoOptionEdit quCoOptionPadding">${en.quRadios[quOptionIndex].optionName }</label>
																<div class="quItemInputCase"><input type="hidden" name="quItemId" value="${en.quRadios[quOptionIndex].id }"><input type="hidden" name="quItemSaveTag" value="1"></div>
																</div>
														</c:when>
														<c:otherwise><div class="emptyTd"></div></c:otherwise>
													</c:choose>
												</td>
												</c:forEach>
												</tr>
												</c:forEach>
											</table>
										</c:when>
										<c:when test="${en.hv eq 1 }">
											<ul class="transverse">
												<c:forEach items="${en.quRadios }" var="item">
												<li class="quCoItemUlLi">
													<div class="dwQuOptionItemContent">
													<label class="dwRedioStyle dwQuInputLabel" ></label>
													<input type="radio"><label class="editAble quCoOptionEdit quCoOptionPadding">${item.optionName }</label>
													<div class="quItemInputCase"><input type="hidden" name="quItemId" value="${item.id }"><input type="hidden" name="quItemSaveTag" value="1"></div>
													</div>
												</li>
												<!-- <li><select> <option>可想而知</option> </select> </li> -->
												</c:forEach>
											</ul>
										</c:when>
										<c:otherwise>
											<ul>
												<c:forEach items="${en.quRadios }" var="item">
												<li class="quCoItemUlLi">
													<div class="dwQuOptionItemContent">
													<label class="dwRedioStyle dwQuInputLabel" ></label>
													<input type="radio"><label class="editAble quCoOptionEdit quCoOptionPadding">${item.optionName }</label>
													<div class="quItemInputCase"><input type="hidden" name="quItemId" value="${item.id }"><input type="hidden" name="quItemSaveTag" value="1"></div>
													</div>
												</li>
												<!-- <li><select> <option>可想而知</option> </select> </li> -->
												</c:forEach>
											</ul>
										</c:otherwise>
									</c:choose>
									</div>
								</div>
								
							</div>
					</div>
					</c:when>
					
					<c:when test="${en.quType eq 'CHECKBOX' }">
						<div class="surveyQuItemBody">
							<div class="initLine"></div>
							<div class="quInputCase" style="display: none;">
								<input type="hidden" name="quType" value="CHECKBOX">
								<input type="hidden" name="quId" value="${en.id }">
								<input type="hidden" name="orderById" value="${en.orderById }"/>
								<input type="hidden" name="saveTag" value="1">
								<input type="hidden" name="hoverTag" value="0">
								<input type="hidden" name="isRequired" value="${en.isRequired }">
								<input type="hidden" name="hv" value="${en.hv }">
								<input type="hidden" name="randOrder" value="${en.randOrder }">
								<input type="hidden" name="cellCount" value="${en.cellCount }">
								<div class="quLogicInputCase">
									<input type="hidden" name="quLogicItemNum" value="${fn:length(en.questionLogics) }">
									<c:forEach items="${en.questionLogics }" var="quLogicEn" varStatus="logicSts">
									<div class="quLogicItem quLogicItem_${logicSts.count }">
										<input type="hidden" name="quLogicId" value="${quLogicEn.id }"/>
										<input type="hidden" name="cgQuItemId" value="${quLogicEn.cgQuItemId }"/>
										<input type="hidden" name="skQuId" value="${quLogicEn.skQuId }"/>
										<input type="hidden" name="visibility" value="1">
										<input type="hidden" name="logicSaveTag" value="1">
									</div>
									</c:forEach>
								</div>
							</div>
							<div class="surveyQuItem">
								
								<div class="surveyQuItemContent">
									<div class="quCoTitle">
										<div class="quCoNum">${i.count }、</div>
										<div class="editAble quCoTitleEdit" >${en.quTitle}</div>
										<input type="hidden" name="quTitleSaveTag" value="1">
									</div>
									<div class="quCoItem">
										<c:choose>
										<c:when test="${en.hv eq 3 }">
											<table class='tableQuColItem'>
												<c:forEach begin="0" end="${fn:length(en.quCheckboxs)-1 }" var="j" step="${en.cellCount }" >
												<tr>
												<c:forEach begin="1" end="${en.cellCount }" var="k">
												<td width="${600/en.cellCount }">
													<!-- 判断不为空，访止数组越界 -->
													<c:set var="quOptionIndex" value="${(j+k-1) }" ></c:set>
													<c:choose>
														<c:when test="${quOptionIndex < fn:length(en.quCheckboxs) }">
														<div class="dwQuOptionItemContent">
															<label class="dwCheckboxStyle dwQuInputLabel" ></label>
															<input type="checkbox"><label style="width:${600/en.cellCount-10 }px;" class="editAble quCoOptionEdit quCoOptionPadding">${en.quCheckboxs[quOptionIndex].optionName }</label>
															<div class="quItemInputCase"><input type="hidden" name="quItemId" value="${en.quCheckboxs[quOptionIndex].id }"><input type="hidden" name="quItemSaveTag" value="1"></div>
														</div>
														</c:when>
														<c:otherwise><div class="emptyTd"></div></c:otherwise>
													</c:choose>
												</td>
												</c:forEach>
												</tr>
												</c:forEach>
											</table>
										</c:when>
										<c:when test="${en.hv eq 1 }">
											<ul class="transverse">
												<c:forEach items="${en.quCheckboxs }" var="item">
												<li class="quCoItemUlLi">
												<div class="dwQuOptionItemContent">
													<label class="dwCheckboxStyle dwQuInputLabel" ></label>
													<input type="checkbox"><label class="editAble quCoOptionEdit quCoOptionPadding">${item.optionName }</label>
													<div class="quItemInputCase"><input type="hidden" name="quItemId" value="${item.id }"><input type="hidden" name="quItemSaveTag" value="1"></div>
												</div>
												</li>
												<!-- <li><select> <option>可想而知</option> </select> </li> -->
												</c:forEach>
											</ul>
										</c:when>
										<c:otherwise>
											<ul>
												<c:forEach items="${en.quCheckboxs }" var="item">
												<li class="quCoItemUlLi">
												<div class="dwQuOptionItemContent">
													<label class="dwCheckboxStyle dwQuInputLabel" ></label>
													<input type="checkbox"><label class="editAble quCoOptionEdit quCoOptionPadding">${item.optionName }</label>
													<div class="quItemInputCase"><input type="hidden" name="quItemId" value="${item.id }"><input type="hidden" name="quItemSaveTag" value="1"></div>
												</div>
												</li>
												<!-- <li><select> <option>可想而知</option> </select> </li> -->
												</c:forEach>
											</ul>
										</c:otherwise>
									</c:choose>
									</div>
									
								</div>
								
							</div>
					</div>
					</c:when>

					<c:when test="${en.quType eq 'FILLBLANK' }">
						<div class="surveyQuItemBody">
							<div class="initLine"></div>
							<div class="quInputCase" style="display: none;">
								<input type="hidden" name="quType" value="FILLBLANK">
								<input type="hidden" name="quId" value="${en.id }">
								<input type="hidden" name="orderById" value="${en.orderById }"/>
								<input type="hidden" name="saveTag" value="1">
								<input type="hidden" name="hoverTag" value="0">
								<input type="hidden" name="isRequired" value="${en.isRequired }">
								<input type="hidden" name="hv" value="${en.hv }">
								<input type="hidden" name="randOrder" value="${en.randOrder }">
								<input type="hidden" name="cellCount" value="${en.cellCount }">
								<div class="quLogicInputCase">
									<input type="hidden" name="quLogicItemNum" value="${fn:length(en.questionLogics) }">
									<c:forEach items="${en.questionLogics }" var="quLogicEn" varStatus="logicSts">
										<div class="quLogicItem quLogicItem_${logicSts.count }">
											<input type="hidden" name="quLogicId" value="${quLogicEn.id }"/>
											<input type="hidden" name="cgQuItemId" value="${quLogicEn.cgQuItemId }"/>
											<input type="hidden" name="skQuId" value="${quLogicEn.skQuId }"/>
											<input type="hidden" name="visibility" value="1">
											<input type="hidden" name="logicSaveTag" value="1">
										</div>
									</c:forEach>
								</div>
							</div>
							<div class="surveyQuItem">
								<div class="surveyQuItemContent">
									<div class="quCoTitle">
										<div class="quCoNum">${i.count }、</div>
										<div class="editAble quCoTitleEdit" >${en.quTitle}</div>
										<input type="hidden" name="quTitleSaveTag" value="1">
									</div>
									<div class="quCoItem"><ul>
										<li class="quCoItemUlLi">
											<div class="quFillblankItem">
												<c:choose>
													<c:when test="${en.answerInputRow > 1 }">
														<textarea name="qu_${en.quType }_${en.id }" rows="${en.answerInputRow }" style="width:${empty(en.answerInputWidth)?'300':en.answerInputWidth}px;"class="inputSytle_2 fillblankInput" ></textarea>
													</c:when>
													<c:otherwise>
														<input type="text" name="qu_${en.quType }_${en.id }" style="width:${empty(en.answerInputWidth)?'300':en.answerInputWidth}px;" class="inputSytle_1 fillblankInput" >
													</c:otherwise>
												</c:choose>
												<div class="dwComEditMenuBtn" ></div>
											</div>
										</li>
									</ul>
									</div>
								</div>

							</div>
						</div>
					</c:when>
					
					<c:when test="${en.quType eq 'SCORE' }">
						<div class="surveyQuItemBody">
							<div class="initLine"></div>
							<div class="quInputCase" style="display: none;">
								<input type="hidden" name="quType" value="SCORE">
								<input type="hidden" name="quId" value="${en.id }">
								<input type="hidden" name="orderById" value="${en.orderById }"/>
								<input type="hidden" name="saveTag" value="1">
								<input type="hidden" name="hoverTag" value="0">
								<input type="hidden" name="isRequired" value="${en.isRequired }">
								<input type="hidden" name="hv" value="${en.hv }">
								<input type="hidden" name="randOrder" value="${en.randOrder }">
								<input type="hidden" name="cellCount" value="${en.cellCount }">
								<div class="quLogicInputCase">
									<input type="hidden" name="quLogicItemNum" value="${fn:length(en.questionLogics) }">
									<c:forEach items="${en.questionLogics }" var="quLogicEn" varStatus="logicSts">
									<div class="quLogicItem quLogicItem_${logicSts.count }">
										<input type="hidden" name="quLogicId" value="${quLogicEn.id }"/>
										<input type="hidden" name="cgQuItemId" value="${quLogicEn.cgQuItemId }"/>
										<input type="hidden" name="skQuId" value="${quLogicEn.skQuId }"/>
										<input type="hidden" name="visibility" value="1">
										<input type="hidden" name="logicSaveTag" value="1">
									</div>
									</c:forEach>
								</div>
							</div>
							<div class="surveyQuItem">
								<div class="surveyQuItemContent">
									<div class="quCoTitle">
										<div class="quCoNum">${i.count }、</div>
										<div class="editAble quCoTitleEdit" >${en.quTitle}</div>
										<input type="hidden" name="quTitleSaveTag" value="1">
									</div>
									<div class="quCoItem">
										<table class="quCoItemTable" cellpadding="0" cellspacing="0">
											<c:forEach items="${en.quScores }" var="item">
											<tr class="quScoreOptionTr">
												<td class="quCoItemTableTd quOptionEditTd">
													<label class="editAble quCoOptionEdit">${item.optionName }</label>
													<div class="quItemInputCase"><input type="hidden" name="quItemId" value="${item.id }"><input type="hidden" name="quItemSaveTag" value="1"></div>
												</td>
												<td class="quCoItemTableTd"><table class="scoreNumTable"><tr><td>1</td><td>2</td><td>3</td><td>4</td><td>5</td></tr></table></td>
												<td class="quCoItemTableTd">分</td>
											</tr>
											</c:forEach>
										</table>
									</div>
								</div>
								
							</div>
					</div>
					</c:when>
					
					<c:when test="${en.quType eq 'ORDERQU' }">
						<div class="surveyQuItemBody">
							<div class="initLine"></div>
							<div class="quInputCase" style="display: none;">
								<input type="hidden" name="quType" value="ORDERQU">
								<input type="hidden" name="quId" value="${en.id }">
								<input type="hidden" name="orderById" value="${en.orderById }"/>
								<input type="hidden" name="saveTag" value="1">
								<input type="hidden" name="hoverTag" value="0">
								<input type="hidden" name="isRequired" value="${en.isRequired }">
								<input type="hidden" name="hv" value="${en.hv }">
								<input type="hidden" name="randOrder" value="${en.randOrder }">
								<input type="hidden" name="cellCount" value="${en.cellCount }">
								<div class="quLogicInputCase">
									<input type="hidden" name="quLogicItemNum" value="${fn:length(en.questionLogics) }">
									<c:forEach items="${en.questionLogics }" var="quLogicEn" varStatus="logicSts">
									<div class="quLogicItem quLogicItem_${logicSts.count }">
										<input type="hidden" name="quLogicId" value="${quLogicEn.id }"/>
										<input type="hidden" name="cgQuItemId" value="${quLogicEn.cgQuItemId }"/>
										<input type="hidden" name="skQuId" value="${quLogicEn.skQuId }"/>
										<input type="hidden" name="visibility" value="1">
										<input type="hidden" name="logicSaveTag" value="1">
									</div>
									</c:forEach>
								</div>
							</div>
							<div class="surveyQuItem">
								<div class="surveyQuItemContent">
									<div class="quCoTitle">
										<div class="quCoNum">${i.count }、</div>
										<div class="editAble quCoTitleEdit" >${en.quTitle}</div>
										<input type="hidden" name="quTitleSaveTag" value="1">
									</div>
									<div class="quCoItem">
										<div  class="quOrderByLeft">
										<ul>
										<c:forEach items="${en.quOrderbys }" var="item" >
											<li class="quCoItemUlLi"><label class="editAble quCoOptionEdit">${item.optionName }</label>
											<div class="quItemInputCase"><input type="hidden" name="quItemId" value="${item.id }"><input type="hidden" name="quItemSaveTag" value="1"></div></li>
										</c:forEach>
										</ul>
										</div>
										<div class="quOrderByRight">
											<table class="quOrderByTable">
											<c:forEach items="${en.quOrderbys }" var="item" varStatus="itemVarStatus">
													<tr><td class="quOrderyTableTd">${itemVarStatus.count }</td><td></td></tr>
											</c:forEach>
											</table>
										</div>
										<div style="clear: both;"></div>
									</div>
								</div>
								
							</div>
					</div>
					</c:when>
					
					<%-- 分页题 --%>
					<c:when test="${en.quType eq 'PAGETAG' }">
						<div class="surveyQuItemBody">
							<div class="initLine"></div>
							<div class="quInputCase" style="display: none;">
								<input type="hidden" name="quType" value="PAGETAG">
								<input type="hidden" name="quId" value="${en.id }">
								<input type="hidden" name="orderById" value="${en.orderById }"/>
								<input type="hidden" name="saveTag" value="1">
								<input type="hidden" name="hoverTag" value="0">
								<input type="hidden" name="isRequired" value="${en.isRequired }">
								<input type="hidden" name="hv" value="${en.hv }">
								<input type="hidden" name="randOrder" value="${en.randOrder }">
								<input type="hidden" name="cellCount" value="${en.cellCount }">
								<div class="quLogicInputCase">
									<input type="hidden" name="quLogicItemNum" value="${fn:length(en.questionLogics) }">
									<c:forEach items="${en.questionLogics }" var="quLogicEn" varStatus="logicSts">
									<div class="quLogicItem quLogicItem_${logicSts.count }">
										<input type="hidden" name="quLogicId" value="${quLogicEn.id }"/>
										<input type="hidden" name="cgQuItemId" value="${quLogicEn.cgQuItemId }"/>
										<input type="hidden" name="skQuId" value="${quLogicEn.skQuId }"/>
										<input type="hidden" name="visibility" value="1">
										<input type="hidden" name="logicSaveTag" value="1">
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
					</c:when>
					
					<%--段落说明 --%>
					<c:when test="${en.quType eq 'PARAGRAPH' }">
						<div class="surveyQuItemBody">
							<div class="initLine"></div>
							<div class="quInputCase" style="display: none;">
								<input type="hidden" name="quType" value="PARAGRAPH">
								<input type="hidden" name="quId" value="${en.id }">
								<input type="hidden" name="orderById" value="${en.orderById }"/>
								<input type="hidden" name="saveTag" value="1">
								<input type="hidden" name="hoverTag" value="0">
								<input type="hidden" name="isRequired" value="${en.isRequired }">
								<input type="hidden" name="hv" value="${en.hv }">
								<input type="hidden" name="randOrder" value="${en.randOrder }">
								<input type="hidden" name="cellCount" value="${en.cellCount }">
								<div class="quLogicInputCase">
									<input type="hidden" name="quLogicItemNum" value="${fn:length(en.questionLogics) }">
									<c:forEach items="${en.questionLogics }" var="quLogicEn" varStatus="logicSts">
									<div class="quLogicItem quLogicItem_${logicSts.count }">
										<input type="hidden" name="quLogicId" value="${quLogicEn.id }"/>
										<input type="hidden" name="cgQuItemId" value="${quLogicEn.cgQuItemId }"/>
										<input type="hidden" name="skQuId" value="${quLogicEn.skQuId }"/>
										<input type="hidden" name="visibility" value="1">
										<input type="hidden" name="logicSaveTag" value="1">
									</div>
									</c:forEach>
								</div>
							</div>
							<div class="surveyQuItem">
								<div class="surveyQuItemContent" style="min-height: 35px;">
									<div class="quCoTitle" style="background: rgb(243, 247, 247);">
										<%-- <div class="quCoNum" >${i.count }、</div> --%>
										<div class="editAble quCoTitleEdit" style="padding-left: 15px;">${en.quTitle}</div>
									</div>
								</div>
							</div>
					</div>
					</c:when>
					
					<%--多项填空题 --%>
					<c:when test="${en.quType eq 'MULTIFILLBLANK' }">
						<div class="surveyQuItemBody">
							<div class="initLine"></div>
							<div class="quInputCase" style="display: none;">
								<input type="hidden" name="quType" value="MULTIFILLBLANK">
								<input type="hidden" name="quId" value="${en.id }">
								<input type="hidden" name="orderById" value="${en.orderById }"/>
								<input type="hidden" name="saveTag" value="1">
								<input type="hidden" name="hoverTag" value="0">
								<input type="hidden" name="isRequired" value="${en.isRequired }">
								<input type="hidden" name="hv" value="${en.hv }">
								<input type="hidden" name="randOrder" value="${en.randOrder }">
								<input type="hidden" name="cellCount" value="${en.cellCount }">
								<div class="quLogicInputCase">
									<input type="hidden" name="quLogicItemNum" value="${fn:length(en.questionLogics) }">
									<c:forEach items="${en.questionLogics }" var="quLogicEn" varStatus="logicSts">
									<div class="quLogicItem quLogicItem_${logicSts.count }">
										<input type="hidden" name="quLogicId" value="${quLogicEn.id }"/>
										<input type="hidden" name="cgQuItemId" value="${quLogicEn.cgQuItemId }"/>
										<input type="hidden" name="skQuId" value="${quLogicEn.skQuId }"/>
										<input type="hidden" name="visibility" value="1">
										<input type="hidden" name="logicSaveTag" value="1">
									</div>
									</c:forEach>
								</div>
							</div>
							<div class="surveyQuItem">
								
								<div class="surveyQuItemContent">
									<div class="quCoTitle">
										<div class="quCoNum">${i.count }、</div>
										<div class="editAble quCoTitleEdit" >${en.quTitle}</div>
										<input type="hidden" name="quTitleSaveTag" value="1">
									</div>
									<div class="quCoItem">
										<table class="mFillblankTable" cellpadding="0" cellspacing="0">
										<c:forEach items="${en.quMultiFillblanks }" var="item">
										<tr class="mFillblankTableTr">
											<td align="right" class="mFillblankTableEditTd"><label class="editAble quCoOptionEdit">${item.optionName }</label>
											<div class="quItemInputCase"><input type="hidden" name="quItemId" value="${item.id }"><input type="hidden" name="quItemSaveTag" value="1"></div>
											</td><td><input type="text" style="width:200px;padding:5px;"></td>
										</tr>
										</c:forEach>
									</table>
									
									</div>
								</div>
								
							</div>
					</div>
					</c:when>
					
					<%-- 矩阵单选题 --%>
					<c:when test="${en.quType eq 'CHENRADIO' }">
						<div class="surveyQuItemBody">
							<div class="initLine"></div>
							<div class="quInputCase" style="display: none;">
								<input type="hidden" name="quType" value="CHENRADIO">
								<input type="hidden" name="quId" value="${en.id }">
								<input type="hidden" name="orderById" value="${en.orderById }"/>
								<input type="hidden" name="saveTag" value="1">
								<input type="hidden" name="hoverTag" value="0">
								<input type="hidden" name="isRequired" value="${en.isRequired }">
								<input type="hidden" name="hv" value="${en.hv }">
								<input type="hidden" name="randOrder" value="${en.randOrder }">
								<input type="hidden" name="cellCount" value="${en.cellCount }">
								<div class="quLogicInputCase">
									<input type="hidden" name="quLogicItemNum" value="${fn:length(en.questionLogics) }">
									<c:forEach items="${en.questionLogics }" var="quLogicEn" varStatus="logicSts">
									<div class="quLogicItem quLogicItem_${logicSts.count }">
										<input type="hidden" name="quLogicId" value="${quLogicEn.id }"/>
										<input type="hidden" name="cgQuItemId" value="${quLogicEn.cgQuItemId }"/>
										<input type="hidden" name="skQuId" value="${quLogicEn.skQuId }"/>
										<input type="hidden" name="visibility" value="1">
										<input type="hidden" name="logicSaveTag" value="1">
									</div>
									</c:forEach>
								</div>
							</div>
							<div class="surveyQuItem">
								<div class="surveyQuItemContent">
									<div class="quCoTitle">
										<div class="quCoNum">${i.count }、</div>
										<div class="editAble quCoTitleEdit" >${en.quTitle}</div>
										<input type="hidden" name="quTitleSaveTag" value="1">
									</div>
									
									<div class="quCoItem">
										<div class="quCoItemLeftChenTableDiv">
										<table class="quCoChenTable" >
												<tr><td></td>
													<c:forEach items="${en.columns }" var="columnItem">
														<td class="quChenColumnTd"><label class="editAble quCoOptionEdit">${columnItem.optionName }</label>
														<div class="quItemInputCase"><input type="hidden" name="quItemId" value="${columnItem.id }"><input type="hidden" name="quItemSaveTag" value="1"></div></td>
													</c:forEach>
												</tr>
												<c:forEach items="${en.rows }" var="rowItem">
												<tr><td class="quChenRowTd"><label class="editAble quCoOptionEdit">${rowItem.optionName }</label>
														<div class="quItemInputCase"><input type="hidden" name="quItemId" value="${rowItem.id }"><input type="hidden" name="quItemSaveTag" value="1"></div></td>
														<c:forEach items="${en.columns }" var="columnItem">
														<td><input type="radio"> </td>
														</c:forEach>
													</tr>
												</c:forEach>
										</table>
										</div>
									</div>
									<div style="clear: both;"></div>
								</div>
							</div>
					</div>
					</c:when>
					
					
					
					<%--矩阵多选题 --%>
					<c:when test="${en.quType eq 'CHENCHECKBOX' }">
						<div class="surveyQuItemBody">
							<div class="initLine"></div>
							<div class="quInputCase" style="display: none;">
								<input type="hidden" name="quType" value="CHENCHECKBOX">
								<input type="hidden" name="quId" value="${en.id }">
								<input type="hidden" name="orderById" value="${en.orderById }"/>
								<input type="hidden" name="saveTag" value="1">
								<input type="hidden" name="hoverTag" value="0">
								<input type="hidden" name="isRequired" value="${en.isRequired }">
								<input type="hidden" name="hv" value="${en.hv }">
								<input type="hidden" name="randOrder" value="${en.randOrder }">
								<input type="hidden" name="cellCount" value="${en.cellCount }">
								<div class="quLogicInputCase">
									<input type="hidden" name="quLogicItemNum" value="${fn:length(en.questionLogics) }">
									<c:forEach items="${en.questionLogics }" var="quLogicEn" varStatus="logicSts">
									<div class="quLogicItem quLogicItem_${logicSts.count }">
										<input type="hidden" name="quLogicId" value="${quLogicEn.id }"/>
										<input type="hidden" name="cgQuItemId" value="${quLogicEn.cgQuItemId }"/>
										<input type="hidden" name="skQuId" value="${quLogicEn.skQuId }"/>
										<input type="hidden" name="visibility" value="1">
										<input type="hidden" name="logicSaveTag" value="1">
									</div>
									</c:forEach>
								</div>
							</div>
							<div class="surveyQuItem">
								
								<div class="surveyQuItemContent">
									<div class="quCoTitle">
										<div class="quCoNum">${i.count }、</div>
										<div class="editAble quCoTitleEdit" >${en.quTitle}</div>
										<input type="hidden" name="quTitleSaveTag" value="1">
									</div>
									
									<div class="quCoItem">
										<div class="quCoItemLeftChenTableDiv">
										<table class="quCoChenTable" >
												<tr><td></td>
													<c:forEach items="${en.columns }" var="columnItem">
														<td class="quChenColumnTd"><label class="editAble quCoOptionEdit">${columnItem.optionName }</label>
														<div class="quItemInputCase"><input type="hidden" name="quItemId" value="${columnItem.id }"><input type="hidden" name="quItemSaveTag" value="1"></div></td>
													</c:forEach>
												</tr>
												<c:forEach items="${en.rows }" var="rowItem">
												<tr><td class="quChenRowTd"><label class="editAble quCoOptionEdit">${rowItem.optionName }</label>
														<div class="quItemInputCase"><input type="hidden" name="quItemId" value="${rowItem.id }"><input type="hidden" name="quItemSaveTag" value="1"></div></td>
														<c:forEach items="${en.columns }" var="columnItem">
														<td><input type="checkbox"> </td>
														</c:forEach>
													</tr>
												</c:forEach>
										</table>
										</div>
									</div>
									<div style="clear: both;"></div>
								</div>
							</div>
					</div>
					</c:when>
					
					<%-- 矩阵填空题 --%>
					<c:when test="${en.quType eq 'CHENFBK' }">
						<div class="surveyQuItemBody">
							<div class="initLine"></div>
							<div class="quInputCase" style="display: none;">
								<input type="hidden" name="quType" value="CHENFBK">
								<input type="hidden" name="quId" value="${en.id }">
								<input type="hidden" name="orderById" value="${en.orderById }"/>
								<input type="hidden" name="saveTag" value="1">
								<input type="hidden" name="hoverTag" value="0">
								<input type="hidden" name="isRequired" value="${en.isRequired }">
								<input type="hidden" name="hv" value="${en.hv }">
								<input type="hidden" name="randOrder" value="${en.randOrder }">
								<input type="hidden" name="cellCount" value="${en.cellCount }">
								<div class="quLogicInputCase">
									<input type="hidden" name="quLogicItemNum" value="${fn:length(en.questionLogics) }">
									<c:forEach items="${en.questionLogics }" var="quLogicEn" varStatus="logicSts">
									<div class="quLogicItem quLogicItem_${logicSts.count }">
										<input type="hidden" name="quLogicId" value="${quLogicEn.id }"/>
										<input type="hidden" name="cgQuItemId" value="${quLogicEn.cgQuItemId }"/>
										<input type="hidden" name="skQuId" value="${quLogicEn.skQuId }"/>
										<input type="hidden" name="visibility" value="1">
										<input type="hidden" name="logicSaveTag" value="1">
									</div>
									</c:forEach>
								</div>
							</div>
							<div class="surveyQuItem">
								
								<div class="surveyQuItemContent">
									<div class="quCoTitle">
										<div class="quCoNum">${i.count }、</div>
										<div class="editAble quCoTitleEdit" >${en.quTitle}</div>
										<input type="hidden" name="quTitleSaveTag" value="1">
									</div>
									
									<div class="quCoItem">
										<div class="quCoItemLeftChenTableDiv">
										<table class="quCoChenTable" >
												<tr><td></td>
													<c:forEach items="${en.columns }" var="columnItem">
														<td class="quChenColumnTd"><label class="editAble quCoOptionEdit">${columnItem.optionName }</label>
														<div class="quItemInputCase"><input type="hidden" name="quItemId" value="${columnItem.id }"><input type="hidden" name="quItemSaveTag" value="1"></div></td>
													</c:forEach>
												</tr>
												<c:forEach items="${en.rows }" var="rowItem">
												<tr><td class="quChenRowTd"><label class="editAble quCoOptionEdit">${rowItem.optionName }</label>
														<div class="quItemInputCase"><input type="hidden" name="quItemId" value="${rowItem.id }"><input type="hidden" name="quItemSaveTag" value="1"></div></td>
														<c:forEach items="${en.columns }" var="columnItem">
														<td><input type="text"> </td>
														</c:forEach>
													</tr>
												</c:forEach>
										</table>
										</div>
									</div>
									<div style="clear: both;"></div>
								</div>
							</div>
					</div>
					</c:when>
					
					
					<%-- 矩阵评分题 --%>
					<c:when test="${en.quType eq 'CHENSCORE' }">
						<div class="surveyQuItemBody">
							<div class="initLine"></div>
							<div class="quInputCase" style="display: none;">
								<input type="hidden" name="quType" value="CHENSCORE">
								<input type="hidden" name="quId" value="${en.id }">
								<input type="hidden" name="orderById" value="${en.orderById }"/>
								<input type="hidden" name="saveTag" value="1">
								<input type="hidden" name="hoverTag" value="0">
								<input type="hidden" name="isRequired" value="${en.isRequired }">
								<input type="hidden" name="hv" value="${en.hv }">
								<input type="hidden" name="randOrder" value="${en.randOrder }">
								<input type="hidden" name="cellCount" value="${en.cellCount }">
								<div class="quLogicInputCase">
									<input type="hidden" name="quLogicItemNum" value="${fn:length(en.questionLogics) }">
									<c:forEach items="${en.questionLogics }" var="quLogicEn" varStatus="logicSts">
									<div class="quLogicItem quLogicItem_${logicSts.count }">
										<input type="hidden" name="quLogicId" value="${quLogicEn.id }"/>
										<input type="hidden" name="cgQuItemId" value="${quLogicEn.cgQuItemId }"/>
										<input type="hidden" name="skQuId" value="${quLogicEn.skQuId }"/>
										<input type="hidden" name="visibility" value="1">
										<input type="hidden" name="logicSaveTag" value="1">
									</div>
									</c:forEach>
								</div>
							</div>
							<div class="surveyQuItem">
								
								<div class="surveyQuItemContent">
									<div class="quCoTitle">
										<div class="quCoNum">${i.count }、</div>
										<div class="editAble quCoTitleEdit" >${en.quTitle}</div>
										<input type="hidden" name="quTitleSaveTag" value="1">
									</div>
									
									<div class="quCoItem">
										<div class="quCoItemLeftChenTableDiv">
										<table class="quCoChenTable" >
												<tr><td></td>
													<c:forEach items="${en.columns }" var="columnItem">
														<td class="quChenColumnTd"><label class="editAble quCoOptionEdit">${columnItem.optionName }</label>
														<div class="quItemInputCase"><input type="hidden" name="quItemId" value="${columnItem.id }"><input type="hidden" name="quItemSaveTag" value="1"></div></td>
													</c:forEach>
												</tr>
												<c:forEach items="${en.rows }" var="rowItem">
												<tr><td class="quChenRowTd"><label class="editAble quCoOptionEdit">${rowItem.optionName }</label>
														<div class="quItemInputCase"><input type="hidden" name="quItemId" value="${rowItem.id }"><input type="hidden" name="quItemSaveTag" value="1"></div></td>
														<c:forEach items="${en.columns }" var="columnItem">
														<td>评分 </td>
														</c:forEach>
													</tr>
												</c:forEach>
										</table>
										</div>
										
									</div>
									<div style="clear: both;"></div>
								</div>
								
							</div>
					</div>
					</c:when>
				</c:choose>
				</li>
				</c:forEach>
				
				<li class="li_surveyQuItemBody surveyQu_${pageNo }"  style="${pageNo gt 1 ?'display: none':''}">
					
					<div class="surveyQuItemBody">
							<div class="surveyQuItem">
								<!-- <div class="pageBorderTop nohover"  ></div> -->
								
								<%--答题进度 --%>
								<div id="resultProgressRoot">
									<div class="progress-label">50%</div>
									<div id="resultProgress" class="progressbarDiv"></div>
								</div>
								<!-- <div id="resultProgressRoot">
								</div> -->
								
								<div class="surveyQuItemContent" style="padding-top: 12px;height: 30px;min-height: 30px;">
									<a href="#" class="sbtn24 sbtn24_0 submitSurvey" >提&nbsp;交</a>&nbsp;&nbsp;
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
</div>


<div id="dwPhone">
	<iframe name="PhoneSurvey" frameborder="0" src="" class="uploadfile" id="PhoneSurvey" style="width:255px;height:455px;margin:96px 0 0 67px;"></iframe>
</div>

<!-- <div id="dwPad">
	<iframe name="PhoneSurvey" frameborder="0" scrolling="" src="http://localhost:8080/survey!answerSurveryMobile.action?surveyId=402880e4480248300148024b65960000" class="uploadfile" id="PhoneSurvey" style="width:255px;height:455px;margin:96px 0 0 67px;"></iframe>
</div> -->

</div>
<script type="text/javascript">
//$("#dw_body").hide();
$("#dwPhone").hide();
$("#PhoneSurvey").attr("src","${ctx}/survey!answerSurveryMobile.action?surveyId=${survey.id}");
$(".centerTabbarBtn").click(function(){
	$(".centerTabbarBtn").removeClass("active");
	$(this).addClass("active");
	var thHref=$(this).attr("href");
	if(thHref==="#pc"){
		$("#dw_body").show();
		$("#dwPhone").hide();
	}else{
		$("#dw_body").hide();
		$("#dwPhone").show();
	}
});
var bfbFloat=80;
$("#resultProgress").progressbar({value: bfbFloat});
/* $( "#resultProgressRoot" ).slider({
	orientation: "vertical",
	range: "min",
	min: 0,
	max: 100,
	value: bfbFloat
});
$( "#resultProgressRoot" ).slider( 'disable' );
$("#resultProgressRoot.ui-slider-disabled").css({opacity:1}); */

function notify(msg,delayHid) {
	//var msg = "保存成功";
	//alert(msg);
	$(".notification").remove();
	if(delayHid==null){
		delayHid=5000;
	}
	$( "<div>" )
		.appendTo( document.body )
		.text( msg )
		.addClass( "notification ui-state-default ui-corner-bottom" )
		.position({
			my: "center top",
			at: "center top",
			of: window
		})
		.show({
			effect: "blind"
		})
		.delay( delayHid )
		.hide({
			effect: "blind",
			duration: "slow"
		}, function() {
			$( this ).remove();
		});
}


resetQuNum();
function resetQuNum(){
	var quCoNums=$(".quCoNum");
	$.each(quCoNums,function(i,item){
		$(this).html((i+1)+"、");
	});
	
}
</script>
<%@ include file="/WEB-INF/page/layouts/other.jsp"%>
</body>
</html>