<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${survey.surveyName }</title>
<link href="${ctx }/js/plugs/jquery-ui-1.10.3.custom/css/mycss/jquery-ui-1.10.3.custom.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx }/js/plugs/jquery-ui-1.10.3.custom/js/jquery-1.10.1.js"></script>
<script type="text/javascript" src="${ctx }/js/plugs/jquery-ui-1.10.3.custom/js/jquery-ui-1.10.3.custom.js"></script>
<script type="text/javascript" src="${ctx }/js/plugs/colpick-jQuery/js/colpick.js"></script>
<link href="${ctx }/js/plugs/uploadify-v3.1/uploadify.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx }/js/plugs/uploadify-v3.1/jquery.uploadify-3.1.js"></script>
<script type="text/javascript" src="${ctx }/js/dw/uploadify.js"></script>

<link href="${ctx }/css/preview-dev.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${ctx }/js/plugs/colpick-jQuery/css/colpick.css" type="text/css"/>

<link href="${ctx}/js/plugs/validate/jquery.validate.css" type="text/css" rel="stylesheet" />
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
	
	//var prevHost="http://file.diaowen.net";
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
		
		var surveyHeadPaddingTop="${surveyStyle.surveyHeadPaddingTop}";
		var surveyHeadPaddingBottom="${surveyStyle.surveyHeadPaddingBottom}";
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
			$(".quCoNum").hide();
		}
		if(showProgressbar==0){
			$("#resultProgressRoot").hide();			
		}
		
		//表头文本显示
		var showSurTitle="${surveyStyle.showSurTitle}";
		var showSurNote="${surveyStyle.showSurNote}";
		if(showSurTitle==0){
			$("#dwSurveyTitle").hide();
		}
		if(showSurNote==0){
			$("#dwSurveyNote").hide();
		}
	}
	
	$(".submitSurvey").click(function(){
		if(validateForms()){
			$("#surveyForm").submit();			
		}
		return false;
	});
	
	//评分题
	$(".scoreNumTable tr td").click(function(){
		//scoreNumInput
		var quScoreOptionTr=$(this).parents(".quScoreOptionTr");
		var tdText=$(this).text();
		quScoreOptionTr.find(".scoreNumTable tr td").css({"background":"white"});
		quScoreOptionTr.find(".scoreNumText").html($(this).text()+"&nbsp;分");
		
		$(this).prevAll().css({"background":""});
		$(this).css({"background":""});
		
		quScoreOptionTr.find(".scoreNumInput").val(tdText);
		quScoreOptionTr.find(".scoreNumText").html(tdText+"&nbsp;分");
		
		runlogic($(this));
		answerProgressbar($(this));
		validateCheck($(this).parents(".li_surveyQuItemBody"),false);
	});
	
	bindScoreNumTdHover();
	function bindScoreNumTdHover(){
		$(".scoreNumTable tr td").hover(function(){
			var quScoreOptionTr = $(this).parents(".quScoreOptionTr");
			var scoreNumInput=quScoreOptionTr.find(".scoreNumInput").val();
			if(scoreNumInput==""){
				$(this).prevAll().css({"background":""});
				$(this).css({"background":""});
				quScoreOptionTr.find(".scoreNumText").html($(this).text()+"&nbsp;分");
			}
		},function(){
			var quScoreOptionTr = $(this).parents(".quScoreOptionTr");
			var scoreNumInput=quScoreOptionTr.find(".scoreNumInput").val();
			if(scoreNumInput==""){
				$(this).prevAll().css({"background":"white"});
				$(this).css({"background":"white"});
				quScoreOptionTr.find(".scoreNumText").html("分");
			}
		});
	}
	
	//排序题
	//quOrderByCoItem
	bindQuOrderBySorts();
	function bindQuOrderBySorts() {
		var quOrderByCoItems=$(".quOrderByCoItem");
		$.each(quOrderByCoItems,function(){
			
			/* $(this).find( ".quOrderByLeftUl li" ).draggable({
				connectToSortable: $(this).find(".quOrderByTable td"),
				helper: "clone",
				zIndex:2000,
				//revert :true,
				start: function(event, ui) {
					var quOrderByCoItem=$(this).parents(".quOrderByCoItem");
					quOrderByCoItem.find( ".quOrderTabConnect" ).css({"background":"","border":"1px dotted red"});
				},
		    	drag: function(event, ui) {
		    		
		    	},
		    	stop: function(event,ui){
		    		var quOrderByCoItem=$(this).parents(".quOrderByCoItem");
					quOrderByCoItem.find( ".quOrderTabConnect" ).css({"background":"","border":"1px solid #dbdbdb"});
		    	}
			}); */
			
			$(this).find( ".quOrderByLeftUl li" ).sortable({
				zIndex:1000,
				scroll :false,
				opacity : 0.8,
				placeholderType:false,
				connectWith:$(this).find( ".quOrderByTable td" ),
				helper: function(event,ui){
					return "<label class='quOrderItemLabel'>"+$(this).text()+"</label>";
				},
				over:function(event,ui){
					
				},
				start: function(event, ui) {
					
				},
		    	drag: function(event, ui) {
		    		
		    	},
		    	stop: function(event,ui){
		    		$(".quOrderByTable td").css({"background":""});
		    		//ui.item.html("<label class='quOrderItemLabel'>"+ui.item.text()+"</label>");
		    		answerProgressbar($(this));
		    		validateCheck($(this).parents(".li_surveyQuItemBody"),false);
		    	}
			});
			var sortObjTempHtml=null;
			$(this).find( ".quOrderByTable td" ).sortable({
				//revert: true
				//dropOnEmpty:false,
				zIndex:1000,
				scroll :false,
				opacity : 0.9,
				placeholderType:true,
				placeholder:"qu-order-highlight",
				connectWith:$(this).find( ".quOrderByTable td" ),
				over:function(event,ui){
				    $(".quOrderByTable td").css({"background":""});
					$(this).css({"background":"#FAEDC0"});
					var quOrderItemLabel=$(this).find("label.quOrderItemLabel");
					sortObjTempHtml="";
					if(quOrderItemLabel[0]){
						sortObjTempHtml=quOrderItemLabel.html();	
					}
					/*sortObjTempHtml="";
					 if(lastLabelHtml!=""){
						sortObjTempHtml="<label class='quOrderItemLabel'>"+$(this).find("label:last-child").html()+"</label>";
					} */
					//console.debug($(ui.helper).css("zIndex")+$(ui.helper).css("position"));
				},
				receive:function(event, ui){//当一个已连接的sortable对象接收到另一个sortable对象的元素后触发此事件。
					//判断如果是从右边新移入的，但当前td中已经有了，就交换到右边去
					
					var uiSenderClass=ui.sender.attr("class");
					ui.sender.empty();
					/* if(uiSenderClass.indexOf("quCoItemUlLi")<0){
						ui.sender.append(sortObjTempHtml);
					} */
					if(uiSenderClass.indexOf("quCoItemUlLi")<0){
						if(sortObjTempHtml!=""){
							ui.sender.append("<label class='quOrderItemLabel'>"+sortObjTempHtml+"</label>");
						}
					}else{
						if(sortObjTempHtml!=""){
								ui.sender.append("<label class='editAble quCoOptionEdit'>"+sortObjTempHtml+"</label>");	
						}
					}
					
					$(this).empty();
					ui.item.clone().appendTo($(this));
					var quCoOptionEdit=$(this).find(".quCoOptionEdit");
					if(quCoOptionEdit[0]){
						quCoOptionEdit.removeClass();
						quCoOptionEdit.addClass("quOrderItemLabel");
					}
					$(".quOrderByTable td").css({"background":""});
					//更新排序ID  quCoItem quOrderByTableTr
					//bindQuOrderBySorts();
					var quOrderyByTrs=$(".quCoItem .quOrderByTableTr");
					$.each(quOrderyByTrs,function(i){
						var quOrderItemHidInput=$(this).find(".quOrderItemHidInput");
						quOrderItemHidInput.val(i+1);
					});
				},
				start: function(event, ui) {
					$(".quOrderByTable td").css({"background":""});
					$(this).css({"background":"#FAEDC0"});
				},
		    	drag: function(event, ui) {
		    		$(".quOrderByTable td").css({"background":""});
					$(this).css({"background":"#FAEDC0"});
		    	},
		    	stop: function(event,ui){
		    		$(".quOrderByTable td").css({"background":""});
		    	},
		    	out: function(event,ui){
		    		//$(".quOrderByTable td").css({"background":""});
		    	},
		    	 activate: function( event, ui ) {
		    		//$(".quOrderByTable td").css({"background":""});
					//$(this).css({"background":"#FAEDC0"});
		    	 }
			});
		});
	}
	
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
		return result;
	}
	
	
	function validateCheck(quItemBody,isSubForm){
		if(quItemBody.is(":visible")){

			var quId=quItemBody.find(".quId").val();
			var quType=quItemBody.find(".quType").val();
			var isRequired=quItemBody.find(".isRequired").val();
			
			var validateStatus=false;
			
			if(quType==="RADIO"){
				validateStatus=quItemBody.find("input[type='radio']:checked")[0];
			}else if(quType==="CHECKBOX"){
				validateStatus=quItemBody.find("input[type='checkbox']:checked")[0];
			}else if(quType==="FILLBLANK"){
				validateStatus=quItemBody.find(".fillblankInput").val()!="";
			}else if(quType==="ORDERQU"){
				//quItemBody.find(".quOrderByLeftUl label");
				validateStatus=!quItemBody.find(".quOrderByLeftUl label")[0];
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
				var quChenScores=quItemBody.find(".quChenScoreSelect");
				validateStatus=true;
				$.each(quChenScores,function(){
					var tempInputs=$(this);
					if(tempInputs.val()==="0"){
						validateStatus=false;
						return false;
					}
				});
				
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
				quItemBody.find(".quCoItem").append(errorHtml);
			}
		}
		return validateStatus;
	}
	
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
	
	/** 单选与多选条件触发 自定义单选多选效果 操作结束后得调用逻辑判断 **/
	$(".dwQuOptionItemContent").click(function(){
		var thObj=$(this);
		var quItemBody=thObj.parents(".li_surveyQuItemBody");
		var quType=quItemBody.find(".quType").val();
		var dwQuInputLabel=thObj.find(".dwQuInputLabel");
		if("RADIO"===quType){
			//单选题
			quItemBody.find(".dwQuInputLabel").removeClass("checked");
			quItemBody.find("input[type='radio']").prop("checked",false);
			dwQuInputLabel.addClass("checked");
			thObj.find("input[type='radio']").prop("checked",true);
			
			runlogic(thObj.find("input[type='radio']"));
		}else if("CHECKBOX"===quType){
			//多选题
			//quItemBody.find(".dwQuInputLabel").removeClass("checked");
			var quInputLabelClass=dwQuInputLabel.attr("class");
			if(quInputLabelClass.indexOf("checked")>0){
				dwQuInputLabel.removeClass("checked");
				thObj.find("input[type='checkbox']").prop("checked",false);
			}else{
				dwQuInputLabel.addClass("checked");
				thObj.find("input[type='checkbox']").prop("checked",true);
			}
			
			runlogic(thObj.find("input[type='checkbox']"));
		}else if("CHENRADIO"===quType){
			//矩陈单选
			var chenRow=thObj.parents("tr");
			chenRow.find(".dwQuInputLabel").removeClass("checked");
			chenRow.find("input[type='radio']").prop("checked",false);
			dwQuInputLabel.addClass("checked");
			thObj.find("input[type='radio']").prop("checked",true);
			
			runlogic(thObj.find("input[type='radio']"));
		}else if("CHENCHECKBOX"===quType){
			//矩陈多选
			var quInputLabelClass=dwQuInputLabel.attr("class");
			if(quInputLabelClass.indexOf("checked")>0){
				dwQuInputLabel.removeClass("checked");
				thObj.find("input[type='checkbox']").prop("checked",false);
			}else{
				dwQuInputLabel.addClass("checked");
				thObj.find("input[type='checkbox']").prop("checked",true);
			}
			
			runlogic(thObj.find("input[type='checkbox']"));
			
		}
		
		answerProgressbar(thObj);
		validateCheck(quItemBody,false);
	});

	//填空题
	$(".fillblankInput,.dwMFillblankInput,.dwChenMFillblankInput").blur(function(){
		 //$(this).css("borderColor","#D6D6FF");
		 var thVal=$(this).val();
		 runlogic($(this));
		 answerProgressbar($(this));
		 validateCheck($(this).parents(".li_surveyQuItemBody"),false);
	});
	
	$(".quChenScoreSelect").change(function(){
		 answerProgressbar($(this));
		 validateCheck($(this).parents(".li_surveyQuItemBody"),false);
	});
	
	//只要触发事件
	
	
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

	resetQuNum();
	function resetQuNum(){
		var quCoNums=$(".quCoNum");
		$.each(quCoNums,function(i,item){
			$(this).html((i+1)+"、");
		});
		
	}
	
	/**********************处理答题进度条************************/
	//$("#resultProgress").progressbar({value: bfbFloat});
	function answerProgressbar(thObj){
		var quItemBody=thObj.parents(".li_surveyQuItemBody ");
		var quType=quItemBody.find(".quType").val();
		
		if(quType==="RADIO"){
			//quItemBody.find(".answerTag").val(1);
			var checks=quItemBody.find("input[type='radio']:checked");
			if(checks[0]){
				quItemBody.find(".answerTag").val(1);				
			}else{
				quItemBody.find(".answerTag").val(0);
			}
		}else if(quType=="CHECKBOX"){
			var checks=quItemBody.find("input[type='checkbox']:checked");
			if(checks[0]){
				quItemBody.find(".answerTag").val(1);				
			}else{
				quItemBody.find(".answerTag").val(0);
			}
		}else if(quType==="FILLBLANK"){
			var thVal=thObj.val();
			if(thVal!=""){
				quItemBody.find(".answerTag").val(1);				
			}else{
				quItemBody.find(".answerTag").val(0);
			}
		}else if(quType==="ORDERQU"){
			//quOrderByLeftUl
			var orderByLabels=quItemBody.find(".quOrderByLeftUl label");
			if(!orderByLabels[0]){
				quItemBody.find(".answerTag").val(1);		
			}else{
				quItemBody.find(".answerTag").val(0);
			}
		}else if( quType==="SCORE" ){
			//<input type="hidden" class="answerTag" value="0" >
			var quScoreOptionTr=thObj.parents(".quScoreOptionTr");
			var scoreNumInput=quScoreOptionTr.find(".scoreNumInput");
			if(scoreNumInput.val()!=""){
				quScoreOptionTr.find(".answerTag").val(1);				
			}else{
				quScoreOptionTr.find(".answerTag").val(0);
			}
		}else if(quType==="MULTIFILLBLANK"){
			var mFillblankTableTr=thObj.parents(".mFillblankTableTr");
			if(thObj.val()!=""){
				mFillblankTableTr.find(".answerTag").val(1);				
			}else{
				mFillblankTableTr.find(".answerTag").val(0);
			}
		}else if(quType==="CHENRADIO"){
			// || || quType==="CHENFBK"
			var dwQuCoChenRowTr=thObj.parents(".dwQuCoChenRowTr");
			dwQuCoChenRowTr.find(".answerTag").val(1);	
		}else if(quType==="CHENCHECKBOX"){
			var dwQuCoChenRowTr=thObj.parents(".dwQuCoChenRowTr");
			var checks=dwQuCoChenRowTr.find("input[type='checkbox']:checked");
			if(checks[0]){
				dwQuCoChenRowTr.find(".answerTag").val(1);				
			}else{
				dwQuCoChenRowTr.find(".answerTag").val(0);
			}
		}else if(quType==="CHENSCORE" ){
			var dwQuScoreOptionItemContent=thObj.parents(".dwQuScoreOptionItemContent");
			if(thObj.val()!="0"){
				dwQuScoreOptionItemContent.find(".answerTag").val(1);				
			}else{
				dwQuScoreOptionItemContent.find(".answerTag").val(0);
			}
		}else if(quType==="CHENFBK"){
			var dwQuChenFbkOptionItemContent=thObj.parents(".dwQuChenFbkOptionItemContent");
			if(thObj.val()!=""){
				dwQuChenFbkOptionItemContent.find(".answerTag").val(1);				
			}else{
				dwQuChenFbkOptionItemContent.find(".answerTag").val(0);
			}
		}
		
		var totalQuSize=$(".answerTag:enabled").size();
		var answerTag1=$(".answerTag[value='1']:enabled");
		var answerQuSize=0;
		if(answerTag1[0]){
			answerQuSize=answerTag1.size();
		}
		var newValue = parseInt(answerQuSize/totalQuSize*100);
		$("#resultProgressRoot .progress-label").text("完成度："+newValue+"%");
		$("#resultProgress").progressbar("option", "value", newValue);
	}
	/*
	$("input").unbind("click");
	$("input").click(function(){
		var quItemBody=$(this).parents(".li_surveyQuItemBody ");
		var quType=quItemBody.find(".quType").val();
		if(quType=="RADIO"){
			quItemBody.find(".answerTag").val(1);
		}
		var totalQuSize=$(".answerTag:enabled").size();
		var answerTag1=$(".answerTag[value='1']:enabled");
		var answerQuSize=0;
		if(answerTag1[0]){
			answerQuSize=answerTag1.size();
		}
		var newValue = parseInt(answerQuSize/totalQuSize*100);
		$("#resultProgressRoot .progress-label").text(newValue+"%");
		$("#resultProgress").progressbar("option", "value", newValue);
	});
	*/
	
	$("#mobileTdId").click(function(){
		$(this).next().slideToggle();
		return false;
	});
	
});
</script>
<style type="text/css">
.edui-editor-iframeholder{
	display: none;
}
.edui-default .edui-editor-toolbarboxouter{
	border: none! important;
}
#resultProgressRoot{
  right: -80px;
  bottom: 100px;
  width: 200px;
  z-index: 200;
  position: fixed;
  transform: rotate(90deg);
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
	height: 10px! important;
	box-shadow: none! important;
	border: 1px solid #83AE00;
}
 .progressbarDiv .ui-progressbar-value{
	background: #83AE00! important;
	border: none;
} 
.ui-progressbar .ui-progressbar-value{
	margin: 0px;
}
.ui-progressbar {
	position: relative;
	background: none! important;
}
.quOptionEditTd .editAble,.scoreNumTable tr td,.quCoItemTable{
	font-size: 16px;
}
label.error{
	font-size: 14px;
}
</style>
</head>
<body>

<div id="wrap">
<input type="hidden" id="id" name="id" value="${survey.id }">
<input type="hidden" id="ctx" name="ctx" value="${ctx }">
<input type="hidden" id="surveyStyleId" value="${surveyStyle.id }">
<input type="hidden" id="prevHost" value="${ctx }">

<form id="surveyForm" action="${ctx }/response!save.action" method="post" >
<input type="hidden" id="surveyId" name="surveyId" value="${survey.id }">
<input type="hidden" id="sid" name="sid" value="${survey.sid }">
<div id="dw_body" style="padding-top:10px;">
	<div id="dw_body_content">
		<div id="dwSurveyHeader">
			<div id="dwSurveyLogo"><img src="${ctx }/images/logo/sample_logo.png" height="70"/> </div>
			<%-- <div id="dwSurveyTitle" class="noLogoImg" >${survey.surveyName }</div>
			<div id="dwSurveyNote">
				<div id="dwSurveyNoteTools">参考样例</div>
				<div id="dwSurveyNoteEdit"  >${survey.surveyDetail.surveyNote }</div>
			</div> --%>
			<div id="dwSurveyTitle" class="noLogoImg">
				<div id="dwSurveyName" class="editAble dwSvyName">${survey.surveyName }</div>
			</div>
			<div id="dwSurveyNote">
				<div id="dwSurveyNoteTools">参考样例</div>
				<div id="dwSurveyNoteEdit"  class="editAble">${survey.surveyDetail.surveyNote }</div>
			</div>
		</div>
		<div id="dwSurveyQuContent" style="min-height: 300px;">
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
									<div class="quCoTitle">
										<div class="quCoNum">${i.count }、</div>
										<div class="editAble quCoTitleEdit" >${en.quTitle}</div>
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
																<input type="radio" name="qu_${en.quType }_${en.id }" value="${en.quRadios[quOptionIndex].id }" ><label style="width:${600/en.cellCount-10 }px;" class="editAble quCoOptionEdit quCoOptionPadding">${en.quRadios[quOptionIndex].optionName }</label>
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
													<input type="radio" name="qu_${en.quType }_${en.id }" value="${item.id }"><label class="editAble quCoOptionEdit quCoOptionPadding">${item.optionName }</label>
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
													<input type="radio" name="qu_${en.quType }_${en.id }" value="${item.id }" ><label class="editAble quCoOptionEdit quCoOptionPadding">${item.optionName }</label>
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
									<div class="quCoTitle">
										<div class="quCoNum">${i.count }、</div>
										<div class="editAble quCoTitleEdit" >${en.quTitle}</div>
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
																<input type="checkbox" name="tag_qu_${en.quType }_${en.id }_${en.quCheckboxs[quOptionIndex].id }"  value="${en.quCheckboxs[quOptionIndex].id }" ><label style="width:${600/en.cellCount-10 }px;" class="editAble quCoOptionEdit quCoOptionPadding">${en.quCheckboxs[quOptionIndex].optionName }</label>
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
														<input type="checkbox" name="tag_qu_${en.quType }_${en.id }_${item.id }"  value="${item.id }" ><label class="editAble quCoOptionEdit quCoOptionPadding">${item.optionName }</label>
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
													<input type="checkbox" name="tag_qu_${en.quType }_${en.id }_${item.id }"  value="${item.id }" ><label class="editAble quCoOptionEdit quCoOptionPadding">${item.optionName }</label>
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
								<div class="surveyQuItemContent">
									<div class="quCoTitle">
										<div class="quCoNum">${i.count }、</div>
										<div class="editAble quCoTitleEdit" >${en.quTitle}</div>
									</div>
									<div class="quCoItem"><ul>
										<li class="quCoItemUlLi">
											<div class="quFillblankItem">
												<input type="text" name="qu_${en.quType }_${en.id }" style="width:200px;padding:5px;" class="inputSytle_1 fillblankInput">
												<div class="dwComEditMenuBtn" ></div>
											</div>
										</li>
									</ul>
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
										<div class="quCoNum">${i.count }、</div>
										<div class="editAble quCoTitleEdit" >${en.quTitle}</div>
									</div>
									<div class="quCoItem">
										<table class="quCoItemTable" cellpadding="0" cellspacing="0">
											<c:forEach items="${en.quScores }" var="item">
											<tr class="quScoreOptionTr">
												<td class="quCoItemTableTd quOptionEditTd">
													<label class="editAble quCoOptionEdit">${item.optionName }</label>
													<input class="dwScoreOptionId" value="${item.id }" disabled="disabled" type="hidden"/>
													<input type="hidden" class="answerTag" value="0" >
												</td>
												<td class="quCoItemTableTd">
													<table class="scoreNumTable" border="0" cellspacing="0" cellpadding="1" ><tr><td style="background-color: white;">1</td><td style="background-color: white;">2</td><td style="background-color: white;">3</td><td style="background-color: white;">4</td><td style="background-color: white;">5</td></tr></table>
													<input name="item_qu_${en.quType }_${en.id }_${item.id }" value=""  type="hidden" class="scoreNumInput" >
												</td>
												<td class="quCoItemTableTd scoreNumText">分</td>
											</tr>
											</c:forEach>
										</table>
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
									<div class="quCoTitle">
										<div class="quCoNum">${i.count }、</div>
										<div class="editAble quCoTitleEdit" >${en.quTitle}</div>
									</div>
									<div class="quCoItem quOrderByCoItem">
										<div class="quOrderByRight">
											<table class="quOrderByTable" style="padding:5px;">
											<c:forEach items="${en.quOrderbys }" var="item" varStatus="itemVarStatus">
													<tr class="quOrderByTableTr"><td class="quOrderyTableTd">${itemVarStatus.count }</td><td class="quOrderTabConnect"><%-- <label class="quOrderItemLabel">drag content ${itemVarStatus.count }</label> --%> </td></tr>
											</c:forEach>
											</table>
										</div>
										<%-- <div class="quOrderByLeft">
											<ul class="quOrderByNumUl">
											<c:forEach items="${en.quOrderbys }" var="item" varStatus="itemVarStatus">
													<li><label class="quOrderyNumLabel">${itemVarStatus.count }</label>&nbsp;请夺</li>
											</c:forEach>
											</ul>
										</div> --%>
										<div  class="quOrderByLeft">
										<ul class="quOrderByLeftUl">
										<c:forEach items="${en.quOrderbys }" var="item">
											<li class="quCoItemUlLi">
												<label class="editAble quCoOptionEdit">${item.optionName }
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
								<div class="surveyQuItemContent" style="min-height: 35px;">
									<div class="quCoTitle" style="background: rgb(243, 247, 247);">
										<%-- <div class="quCoNum" >${i.count }、</div> --%>
										<div class="editAble quCoTitleEdit" style="padding-left: 15px;">${en.quTitle}</div>
									</div>
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
										<div class="quCoNum">${i.count }、</div>
										<div class="editAble quCoTitleEdit" >${en.quTitle}</div>
									</div>
									<div class="quCoItem">
										<table class="mFillblankTable" cellpadding="0" cellspacing="0">
										<c:forEach items="${en.quMultiFillblanks }" var="item">
										<tr class="mFillblankTableTr">
											<td align="right" class="mFillblankTableEditTd"><label class="editAble quCoOptionEdit">${item.optionName }</label>
											<input class="dwMFillblankOptionId" value="${item.id }" disabled="disabled" type="hidden"/>
											<input type="hidden" class="answerTag" value="0" >
											</td>
											<td><input name="text_qu_${en.quType }_${en.id }_${item.id }"  type="text" style="width:200px;padding:5px;" class="inputSytle_1 dwMFillblankInput"></td>
										</tr>
										</c:forEach>
									</table>
									
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
										<div class="quCoNum">${i.count }、</div>
										<div class="editAble quCoTitleEdit" >${en.quTitle}</div>
									</div>
									
									<div class="quCoItem">
										<div class="quCoItemLeftChenTableDiv">
										<table class="quCoChenTable" >
												<tr><td></td>
													<c:forEach items="${en.columns }" var="columnItem">
														<td class="quChenColumnTd"><label class="editAble quCoOptionEdit">${columnItem.optionName }</label></td>
													</c:forEach>
												</tr>
												<c:forEach items="${en.rows }" var="rowItem">
												<tr class="dwQuCoChenRowTr">
														<td class="quChenRowTd"><label class="editAble quCoOptionEdit">${rowItem.optionName }</label>
														<input type="hidden" class="answerTag" value="0" >
														</td>
														<c:forEach items="${en.columns }" var="columnItem">
														<td>
														<div class="dwQuOptionItemContent">
															<label class="dwRedioStyle dwQuInputLabel" ></label>
															<input type="hidden" class="dwChenInputTemp" disabled="disabled" value="${rowItem.id }:${columnItem.id }">
															<input name="item_qu_${en.quType }_${en.id }_${rowItem.id }" value="${columnItem.id }" type="radio"> 
														</div>
														</td>
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
										<div class="quCoNum">${i.count }、</div>
										<div class="editAble quCoTitleEdit" >${en.quTitle}</div>
									</div>
									
									<div class="quCoItem">
										<div class="quCoItemLeftChenTableDiv">
										<table class="quCoChenTable" >
												<tr><td></td>
													<c:forEach items="${en.columns }" var="columnItem">
														<td class="quChenColumnTd"><label class="editAble quCoOptionEdit">${columnItem.optionName }</label></td>
													</c:forEach>
												</tr>
												<c:forEach items="${en.rows }" var="rowItem">
												<tr class="dwQuCoChenRowTr">
													<td class="quChenRowTd"><label class="editAble quCoOptionEdit">${rowItem.optionName }</label>
													<input type="hidden" name="item_qu_${en.quType }_${en.id }_${rowItem.id }"  value="ck_item_qu_${en.quType }_${en.id }_${rowItem.id }_" />
													<input type="hidden" class="answerTag" value="0" > 
													</td>
														<c:forEach items="${en.columns }" var="columnItem">
														<td>
														<div class="dwQuOptionItemContent">
															<label class="dwCheckboxStyle dwQuInputLabel" ></label>
															<input type="hidden" class="dwChenInputTemp" disabled="disabled" value="${rowItem.id }:${columnItem.id }">
															<input  name="ck_item_qu_${en.quType }_${en.id }_${rowItem.id }_${columnItem.id}" value="${columnItem.id }" type="checkbox"> 
														</div>
														</td>
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
										<div class="quCoNum">${i.count }、</div>
										<div class="editAble quCoTitleEdit" >${en.quTitle}</div>
									</div>
									
									<div class="quCoItem">
										<div class="quCoItemLeftChenTableDiv">
										<table class="quCoChenTable" >
												<tr><td></td>
													<c:forEach items="${en.columns }" var="columnItem">
														<td class="quChenColumnTd"><label class="editAble quCoOptionEdit">${columnItem.optionName }</label></td>
													</c:forEach>
												</tr>
												<c:forEach items="${en.rows }" var="rowItem">
												<tr class="dwQuCoChenRowTr"><td class="quChenRowTd"><label class="editAble quCoOptionEdit">${rowItem.optionName }</label>
													<input type="hidden" name="item_qu_${en.quType }_${en.id }_${rowItem.id }"  value="fbk_item_qu_${en.quType }_${en.id }_${rowItem.id }_" />
													</td>
														<c:forEach items="${en.columns }" var="columnItem">
														<td>
														<div class="dwQuChenFbkOptionItemContent">
															<input type="hidden" class="dwChenInputTemp" disabled="disabled" value="${rowItem.id }:${columnItem.id }">
															<input name="fbk_item_qu_${en.quType }_${en.id }_${rowItem.id }_${columnItem.id }" type="text" class="inputSytle_1 dwChenMFillblankInput">
															<input type="hidden" class="answerTag" value="0" >
														</div> 
														</td>
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
										<div class="quCoNum">${i.count }、</div>
										<div class="editAble quCoTitleEdit" >${en.quTitle}</div>
									</div>
									
									<div class="quCoItem">
										<div class="quCoItemLeftChenTableDiv">
										<table class="quCoChenTable" >
												<tr><td></td>
													<c:forEach items="${en.columns }" var="columnItem">
														<td class="quChenColumnTd"><label class="editAble quCoOptionEdit">${columnItem.optionName }</label></td>
													</c:forEach>
												</tr>
												<c:forEach items="${en.rows }" var="rowItem">
												<tr class="dwQuCoChenRowTr"><td class="quChenRowTd"><label class="editAble quCoOptionEdit">${rowItem.optionName }</label>
													<input type="hidden" name="item_qu_${en.quType }_${en.id }_${rowItem.id }"  value="cs_item_qu_${en.quType }_${en.id }_${rowItem.id }_" />
													</td>
														<c:forEach items="${en.columns }" var="columnItem">
														<td>
														<div class="dwQuScoreOptionItemContent">
														<input type="hidden" class="dwChenInputTemp" disabled="disabled" value="${rowItem.id }:${columnItem.id }">
														<select name="cs_item_qu_${en.quType }_${en.id }_${rowItem.id }_${columnItem.id}" class="quChenScoreSelect" > 
															<option value="0">-评分-</option>
														<c:forEach begin="1" end="5" var="scoreNum" >
															<option value="${scoreNum }" >${scoreNum }分</option>
														</c:forEach>
														</select> 
														<input type="hidden" class="answerTag" value="0" >
														</div>
														</td>
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
											<input name="jcaptchaInput" type="text" class="" style="width:100px;" autocomplete="off">
											<img id="register-jcaptchaImg" onclick="refreshAutoCode('register-jcaptchaImg')" src="${ctx }/jcaptcha.action" alt="验证码" height="40"></span>
											<a href="javascript:refreshAutoCode('register-jcaptchaImg');" style="margin-left: 5px;" hidefocus="true">换一张</a>
											<span class="frm_desc">输入下面图片的字符，区分大小写</span>
											<p class="valid-msg fail" style="display: none;"><i>●</i><span class="msg_content">验证码错误，请重新输入</span></p>
										</div>
									</div>
									
									<%-- 验证码：<input type="text" size="8" name="jcaptchaInput">
									&nbsp;<img id="jcaptchaImg" alt="点击刷新" src="${ctx }/jcaptcha.action"  align="top">
									&nbsp;点击图片刷新 --%>
								</div>
								<input type="hidden" class="quType" value="submitSurveyBtn">
								<div class="surveyQuItemContent" style="padding-top: 12px;height: 30px;min-height: 30px;">
									<a href="#" id="submitSurvey" class="sbtn24 sbtn24_0 submitSurvey" >提&nbsp;交</a>&nbsp;&nbsp;
									&nbsp;&nbsp;
									<%-- <a href="${ctx }/report.action?sid=${survey.sid}" style="color: gray;text-decoration: none;" target="_blank">查看结果</a> --%>
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
		<%--答题进度 --%>
		<div id="resultProgressRoot">
			<div class="progress-label">完成度：0%</div>
			<div id="resultProgress" class="progressbarDiv"></div>
		</div>
		
	</div>
</div>
</form>

</div>

<div class="footer-copyright" style="color: gray;">
		 <a href="http://www.diaowen.net" style="text-decoration: none;color: rgb(53, 117, 136);">调问网</a> 提供支持
</div>
		
<div id="fixedMsg" style="position: fixed;top: 0px;width: 100%;padding: 10px;text-align: center;font-size: 18px;letter-spacing: 4px;line-height: 56px;background-color: #111;background-color: rgba(17,17,17,0.5);color: #fff;color: rgba(255,255,255,0.5);z-index: 200;display: none;"></div>
<script type="text/javascript">
var bfbFloat=0;
$("#resultProgress").progressbar({value: bfbFloat,orientation: "vertical"});

function refreshAutoCode(codeImgId){
	var ctx=$("#ctx").val();
	$("#"+codeImgId).attr("src",ctx+"/jcaptcha.action");
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
	var errorHtml="<div class=\"errorItem\" style=\"padding-left:30px;\" ><label for=\"\" class=\"error\">验证码不正确，请重新回答！</label></div>";
	$("#dwSurveyHeader").append(errorHtml);
}

</script>
<%@ include file="/WEB-INF/page/layouts/other.jsp"%>

<!-- Diaowen.net Button BEGIN -->
	<div id="webSiteFixedRight" class="websiteFixed" style="position: fixed;right: 0px;top: 20px;z-index: 9999;">
		<a id="mobileTdId" href="＃" style="background: #1C658B;width: 15px;background: #8CBCD1;display: block;padding: 5px;padding-top: 10px;padding-bottom:10px;font-weight: bold;color: white;cursor: pointer;float: right;vertical-align: middle;text-decoration: none;font-size: 12px;">手机地址</a>
		<img alt="" src="${ctx }/survey!answerTD.action?surveyId=${survey.id}" height="130" style="padding: 10px;background: white;display: none;" />
	</div>
<!-- Diaowen.net Button END -->

</body>
</html>