/**
 *
 * DWSurvey 3.0
 *
 * @desc: design survey
 * @author: keyuan（@keyuan, keyuan258@gmail.com）
 * @github: https://github.com/wkeyuan/DWSurvey
 *
 * Copyright 2012, 2017 调问问卷(DWSurvey,http://dwsurvey.net)
 *
 */

$(document).ready(function(){
//	$(".createSsUl li").unbind();
//	
//	$(".createSsUl li").hover(function(){
//		if(toggleTag){
//			toggleTag=false;
//			var stepLi=$(this);
////				$(".a-w-sel").not(stepLi.find(".a-w-sel")).slideUp("slow",function(){
////					stepLi.find(".a-w-sel").slideDown("slow",function(){
////						toggleTag=true;
////					});
////				});
//			stepLi.find(".a-w-sel").slideToggle("slow",function(){
//				toggleTag=true;
//			});
//			$(".a-w-sel").not(stepLi.find(".a-w-sel")).slideUp();
//		}
//		return false;
//	},function(){
//		$(".a-w-sel").slideUp("slow",function(){
//			toggleTag=true;
//		});
//		
//		return false;
//	});
	
	var toggleTag=true;
	$(".clickHideUserMenu,.clickHideMenu").unbind();
	$(".clickHideUserMenu,.clickHideMenu").click(function(){
		if(toggleTag){
			toggleTag=false;
			var stepLi=$(this).parent();
//				$(".a-w-sel").not(stepLi.find(".a-w-sel")).slideUp("slow",function(){
//					stepLi.find(".a-w-sel").slideDown("slow",function(){
//						toggleTag=true;
//					});
//				});
/*			stepLi.find(".a-w-sel").slideToggle("slow",function(){
				toggleTag=true;
			});
			$(".a-w-sel").not(stepLi.find(".a-w-sel")).slideUp();
			*/
			stepLi.find(".a-w-sel").slideToggle(0,function(){
				toggleTag=true;
			});
			//$(".a-w-sel").not(stepLi.find(".a-w-sel")).slideUp();
			$(".a-w-sel").not(stepLi.find(".a-w-sel")).hide();
		}
		return false;
	
	});
	
	$(document).click(function(){
		//$(".a-w-sel").slideUp("slow");
		$(".a-w-sel").hide();
	});
});
	

var browseWidth=$(window).width();
var browseHeight=$(window).height();

function resizeBodyWidth(){
	browseWidth=$(window).width();
	if(browseWidth>=1120){
		$(".bodyCenter").width(browseWidth-160);
	}else{
		$(".bodyCenter").width(960);
	}
	/*if(browseWidth<=900){
		$("#wrap").width(900);
	}else{
		$("#wrap").width("auto");
	}
	if(browseWidth<=1100){
		$("#dwBody").width(900);
		$("#dwBody").css("margin","10px auto");
	}else{
		$("#dwBody").width("auto");
		$("#dwBody").css("margin","10px 100px");
	}*/
	//alert($("#dwBody").width());
}
function resizeBodyHeight(){
	//autoContentHeight
	 browseHeight=$(window).height();
	 var autoCHeight=browseHeight-230;
	 $(".autoContentHeight").height(autoCHeight);
	 $("#autoIframeHeight").height(autoCHeight-5);
}
//窗口大小发生改变时
$(window).resize(function(){
	resizeBodyWidth();
	resizeBodyHeight();
});
function refreshAutoCode(codeImgId){
	var ctx=$("#ctx").val();
	$("#"+codeImgId).attr("src",ctx+"/jcaptcha.action");
}
function currentMenu(menuId){
	$(".dw-menu-a").removeClass("active");
	$("#"+menuId).addClass("active");
}