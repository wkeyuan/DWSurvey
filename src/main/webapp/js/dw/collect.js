/**
 *
 * DWSurvey 3.0
 *
 * @desc: 收集问卷设置
 * @author: keyuan（@keyuan, keyuan258@gmail.com）
 * @github: https://github.com/wkeyuan/DWSurvey
 *
 * Copyright 2012, 2017 调问问卷(DWSurvey,http://dwsurvey.net)
 *
 */
$(document).ready(function(){
	var ctx=$("input[name='ctx']").val();
	//检查问卷状态－－－如果是设计状态，则提醒先发布，再收集
	var surveyState=$("#surveyState").val();
	if(surveyState==="0"){
		$("body").append("<div id=\"myDialogRoot\"><div class='dialogMessage'>您问卷当前是设计状态，需要先发布才能收集答卷。<br/>是否确认发布问卷！</div></div>");
		var myDialog=$( "#myDialogRoot" ).dialog({
			width:500,
			height:230,
			autoOpen: true,
			modal:true,
			position:["center","center"],
			title:"提示",
			resizable:false,
			draggable:false,
			closeOnEscape:false,
			show: {effect:"blind",direction:"up",duration: 500},
			hide: {effect:"blind",direction:"left",duration: 200},
			buttons: {
				"OK":{
                    text: "确认发布",
                    addClass:'dialogMessageButton',
                    click: function() {
                        //执行发布
                    	var surveyId=$("#id").val();
                    	window.location.href=ctx+"/design/my-survey-design!devSurvey.action?surveyId="+surveyId;
                    }
				},
				"llll":{
                    text: "继续设计",
                    addClass:'dialogMessageButton',
                    click: function() {
                      //$( this ).dialog( "close" );
                    	var surveyId=$("#id").val();
                    	window.location.href=ctx+"/design/my-survey-design.action?surveyId="+surveyId;
                    }
				},
				"CENCEL":{
                    text: "退出",
                    click: function() {
                      //$( this ).dialog( "close" );
                    	window.location.href=ctx+"/design/my-survey.action";
                    }
				}
			},
			open:function(event,ui){
				$(".ui-dialog-titlebar-close").hide();
			},
			close:function(event,ui){
				$("#myDialogRoot").remove();
			}
		});
		
	}
	
	
	//设置收集规则
	$(".sur_collectSet").unbind();
	$(".sur_collectSet").click(function(){
		var url=ctx+"/js/dw/html/collectset.html";
		
		$.ajax({
			url:url,
			type:"get",
			dataType:"html",
			success:function(data){
				
				//$("body").append("<div id=\"myDialogRoot\"><div class='dialogMessage'></div></div>");
				$("body").append("<div id=\"myDialogRoot\">"+data+"</div>");
				$("#myDialogRoot .mailOnlyItem").hide();
				
				url=ctx+"/design/my-survey!attrs.action";
				var data="id="+$("#id").val();
				
				$.ajax({
					url:url,
					data:data,
					type:'post',
					success:function(msg){
						
						var survey=eval("("+msg+")");
						$("#myDialogRoot input[name='effective'][value='"+survey.surveyDetail.effective+"']").attr("checked",true);
						$("#myDialogRoot input[name='effectiveIp'][value='"+survey.surveyDetail.effectiveIp+"']").attr("checked",true);
						$("#myDialogRoot input[name='rule'][value='"+survey.surveyDetail.rule+"']").attr("checked",true);
						$("#myDialogRoot input[name='ruleCode']").val(survey.surveyDetail.ruleCode);
						$("#myDialogRoot input[name='refresh'][value='"+survey.surveyDetail.refresh+"']").attr("checked",true);
						$("#myDialogRoot input[name='mailOnly'][value='"+survey.surveyDetail.mailOnly+"']").attr("checked",true);
						$("#myDialogRoot input[name='ynEndNum'][value='"+survey.surveyDetail.ynEndNum+"']").attr("checked",true);
						$("#myDialogRoot input[name='endNum']").val(survey.surveyDetail.endNum);
						$("#myDialogRoot input[name='ynEndTime'][value='"+survey.surveyDetail.ynEndTime+"']").attr("checked",true);
						if(survey.surveyDetail.endTime!="null"){
							$("#myDialogRoot input[name='endTime']").val(survey.surveyDetail.endTime);	
						}
						$("#myDialogRoot input[name='showShareSurvey'][value='"+survey.surveyDetail.showShareSurvey+"']").attr("checked",true);
						$("#myDialogRoot input[name='showAnswerDa'][value='"+survey.surveyDetail.showAnswerDa+"']").attr("checked",true);
						
					}
				});
				
				
				var myDialog=$( "#myDialogRoot" ).dialog({
					width:550,
					height:490,
					autoOpen: true,
					modal:true,
					position:["center","center"],
					title:"提示",
					resizable:false,
					draggable:false,
					closeOnEscape:false,
					show: {effect:"blind",direction:"up",duration: 500},
					//hide: {effect:"blind",direction:"left",duration: 200},
					buttons: {
						"OK":{
		                    text: "保存",
		                    addClass:'dialogMessageButton',
		                    click: function() {
		                    	//异步保存结果
		                    	saveAttrs();
		                    	$( this ).dialog( "close" );
		                    }
						},
						"CENCEL":{
		                    text: "取消",
		                    click: function() {
		                    	$( this ).dialog( "close" );
		                    	//window.location.href=ctx+"/design/my-survey.action";
		                    }
						}
					},
					open:function(event,ui){
						$(".ui-dialog-titlebar-close").hide();
					},
					close:function(event,ui){
						$("#myDialogRoot").remove();
					}
				});
			}
		});
		
	});
	
	
	function saveAttrs(){

		var url=ctx+"/design/my-survey-style!save.action";
		var surveyId=$("#id").val();
		var data="surveyId="+surveyId;
		//收集规则 
		var effective=$("#myDialogRoot input[name='effective']:checked")[0]?"4":"0";
		var effectiveIp=$("#myDialogRoot input[name='effectiveIp']:checked")[0]?"1":"0";
		var rule=$("#myDialogRoot input[name='rule']:checked")[0]?"3":"0";
		var ruleCode=$("#myDialogRoot input[name='ruleCode']").val();
		var refresh=$("#myDialogRoot input[name='refresh']:checked")[0]?"1":"0";
		var mailOnly=$("#myDialogRoot input[name='mailOnly']:checked")[0]?"1":"0";
		var ynEndNum=$("#myDialogRoot input[name='ynEndNum']:checked")[0]?"1":"0";
		var ynEndTime=$("#myDialogRoot input[name='ynEndTime']:checked")[0]?"1":"0";
		var endTime=$("#myDialogRoot input[name='endTime']").val();
		var endNum=$("#myDialogRoot input[name='endNum']").val();
		var showShareSurvey=$("#myDialogRoot input[name='showShareSurvey']:checked")[0]?"1":"0";
		var showAnswerDa=$("#myDialogRoot input[name='showAnswerDa']:checked")[0]?"1":"0";
		
		data+="&effective="+effective+"&effectiveIp="+effectiveIp+"&rule="+rule+"&refresh="+refresh+"&ruleCode="+ruleCode+"&mailOnly="+mailOnly;
		data+="&ynEndNum="+ynEndNum+"&ynEndTime="+ynEndTime+"&endTime="+endTime+"&endNum="+endNum;
		data+="&showShareSurvey="+showShareSurvey+"&showAnswerDa="+showAnswerDa;
		
		$.ajax({
			url : url,
			data : data,
			type : "post",
			success : function(msg){
				//alert(msg);
				notify("修改保存成功！",5000);
			}
		});
		return false;
	}
	
	//
	$(".sur_edit").unbind();
	$(".sur_edit").click(function(){
		//${ctx }/design/my-survey-design.action?surveyId=${surveyId}
		
		$("body").append("<div id=\"myDialogRoot\"><div class='dialogMessage'>您问卷已经发布，确认要重新编辑使问卷回到设计状态。<br/>是否确认返回设计状态！</div></div>");
		var myDialog=$( "#myDialogRoot" ).dialog({
			width:500,
			height:230,
			autoOpen: true,
			modal:true,
			position:["center","center"],
			title:"提示",
			resizable:false,
			draggable:false,
			closeOnEscape:false,
			//show: {effect:"blind",direction:"up",duration: 500},
			//hide: {effect:"blind",direction:"left",duration: 200},
			buttons: {
				"OK":{
                    text: "确认编辑",
                    addClass:'dialogMessageButton',
                    click: function() {
                        //执行发布
                    	var surveyId=$("#id").val();
                    	window.location.href=ctx+"/design/my-survey-design.action?surveyId="+surveyId;
                    }
				},
				"CENCEL":{
                    text: "取消",
                    click: function() {
                      $( this ).dialog( "close" );
                    }
				}
			},
			open:function(event,ui){
				$(".ui-dialog-titlebar-close").hide();
			},
			close:function(event,ui){
				$("#myDialogRoot").remove();
			}
		});
		
		return false;
	});
	
	$(".surveyStateBtn").unbind();
	$(".surveyStateBtn").click(function(){
		//var thVal=$("#surveyState").val();
		var thBtn=$(this);
		var thText=thBtn.text();
		var surveyState="";
		if(thText==="开始收集"){
			surveyState=1;
		}else if(thText==="停止收集"){
			//alert("结束意味着");
			surveyState=2;
		}else if(thText==="重新打开收集"){
			surveyState=1;
		}
		var ctx=$("#ctx").val();
		var url=ctx+"/design/my-survey!surveyState.action";
		var surveyId=$("#id").val();
		var data="id="+surveyId+"&surveyState="+surveyState;
		$.ajax({
			url:url,
			data:data,
			type:"post",
			success:function(msg){
				if(msg==="true"){
					if(thText==="开始收集"){
						thBtn.text("停止收集");
						$(".surveyStateText").text("收集中");
					}else if(thText==="停止收集"){
						thBtn.text("重新打开收集");
						$(".surveyStateText").text("收集完成");
					}else if(thText==="重新打开收集"){
						thBtn.text("停止收集");
						$(".surveyStateText").text("收集中");
					}
				}
			}
		});
		return false;
	});
	
});

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