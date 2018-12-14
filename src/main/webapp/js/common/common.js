$(document).ready(function(){
	$(".refreshJcaptchaImg").click(function(){
		var ctx=$("input[name='ctx']").val();
		$("#jcaptchaImg").attr("src",ctx+"/jcaptcha.action");
		return false;
	});
});

function lgcommon(thFormElementObj){
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