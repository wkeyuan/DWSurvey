$(document).ready(function(){
	$(".refreshJcaptchaImg").click(function(){
		var ctx=$("input[name='ctx']").val();
		$("#jcaptchaImg").attr("src",ctx+"/jcaptcha.action");
		return false;
	});
});

(function($){
  $.getUrlParam = function(name){
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r!=null) return unescape(r[2]); return null;
  }
  $.getBrowser = function() {
    var ua = navigator.userAgent.toLowerCase();
    var btypeInfo = (ua.match( /firefox|chrome|safari|opera/g ) || "other")[ 0 ];
    if( (ua.match( /msie|trident/g ) || [] )[ 0 ] ) {
      btypeInfo = "msie";
    }
    var pc = "";
    var prefix = "";
    var plat = "";
    // 如果没有触摸事件 判定为PC
    var isTocuh = ("ontouchstart" in window) || (ua.indexOf( "touch" ) !== -1) || (ua.indexOf( "mobile" ) !== -1);
    if( isTocuh ) {
      if( ua.indexOf( "ipad" ) !== -1 )
      {
        pc = "pad";
      } else if( ua.indexOf( "mobile" ) !== -1 )
      {
        pc = "mobile";
      } else if( ua.indexOf( "android" ) !== -1 )
      {
        pc = "androidPad";
      } else
      {
        pc = "pc";
      }
    } else {
      pc = "pc";
    }
    switch( btypeInfo ) {
      case "chrome":
      case "safari":
      case "mobile":
        prefix = "webkit";
        break;
      case "msie":
        prefix = "ms";
        break;
      case "firefox":
        prefix = "Moz";
        break;
      case "opera":
        prefix = "O";
        break;
      default:
        prefix = "webkit";
        break
    }
    plat = (ua.indexOf( "android" ) > 0) ? "android" : navigator.platform.toLowerCase();
    return {
      version: (ua.match( /[\s\S]+(?:rv|it|ra|ie)[/: ]([\d.]+)/ ) || [])[ 1 ],
      plat: plat,
      type: btypeInfo,
      pc: pc,
      prefix: prefix,
      isMobile: pc !== "pc",
    };
  };
  $.answerPage = function(pageName,sid,callback) {
    var browser = $.getBrowser();
    var ruleCode = $("#ruleCode").val();
    var urlParams = "";
    if(ruleCode!="") {
      urlParams="&ruleCode="+ruleCode;
    }
    if(browser.isMobile && pageName==="p"){
      window.location.href="/static/diaowen/diaowen-m.html?sid="+sid+urlParams;
    }
    if(!browser.isMobile && pageName==="m" ){
      window.location.href="/static/diaowen/diaowen.html?sid="+sid+urlParams;
    }
    if(callback!=null){
      callback();
    }
  }
  $.notify = function(msg,delayHid) {
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
})(jQuery);

function lgcommon(thFormElementObj) {
	var quItemBody=thFormElementObj.parents(".li_surveyQuItemBody");
	var quLogicItems=quItemBody.find(".quLogicItem");
	if(quLogicItems[0]){
		var quInputCase=quItemBody.find(".quInputCase");
		var quId=quInputCase.find(".quId").val();
		var quType=quInputCase.find(".quType").val();
		if(quType==="RADIO" || quType==="CHECKBOX" || quType==="SCORE" || quType==="MULTIFILLBLANK" || quType==="CHENRADIO" || quType==="CHENCHECKBOX" || quType==="CHENSCORE" || quType==="CHENFBK" ){
			//遍历每个逻辑设置
			var quOptionItems=null;
			if(quType==="RADIO" || quType==="CHECKBOX"){
				quOptionItems=quItemBody.find(".dwQuOptionItemContent");
			}else if(quType==="SCORE"){
				quOptionItems=quItemBody.find(".quScoreOptionTr");
			}else if(quType==="MULTIFILLBLANK"){
				quOptionItems=quItemBody.find(".mFillblankTableTr");
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
					}else if(quType==="MULTIFILLBLANK"){
						quInput=quCoItem.find(".dwMFillblankOptionId");
						logicStatus=quCoItem.find(".dwMFillblankInput").val()!="";
						curQuItemId=quInput.val();
					}

					if(curQuItemId===cgQuItemId){
						if(logicStatus){
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
							var hidQuItemBody=$(".quId[value='"+skQuId+"']").parents(".li_surveyQuItemBody");
							hidQuItemBody.hide();
							hidQuItemBody.addClass("hidFor"+logicId);
							hidQuItemBody.find(".answerTag").attr("disabled",true);
						}
						return false;
					}
				});

				if(isbreak){
					return false;
				}

			});

		}
	}
}

