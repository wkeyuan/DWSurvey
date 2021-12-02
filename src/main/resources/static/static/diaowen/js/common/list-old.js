$(document).ready(function(){
		bindListTrHover();
		
		$(".pageNoA").click(function(){
			var listForm=$("#listForm");
			listForm.attr("action",$(this).attr("href"));
			listForm.submit();
			return false;
		});
		
		var newDialog=$("#newDialog").dialog({autoOpen:false,modal:true,width:900,height:520,title:'题库',close:function(event,ui){
			//close时的操作
			var isrefHid=$("#isrefHid").val();
			if(isrefHid==1){
				window.location.reload(true);
			}
		}});
		$("#closeDailogBtn").click(function(){
			newDialog.dialog("close");
		});
		$("#upDailogTitleBtn").click(function(){
			//newDialog.dialog("close");
			var newtitle=$("#newDailogTitle").val();
			newDialog.dialog({"title":newtitle});
		});
		var dialogFrameWd=null;
		var dialogFrameHt=null;
		
		$(".click_a").click(function(){
			var newDialogFrame=$("#newDialogFrame");
//			newDialog.dialog({"title":$(this).attr("title")});
			var baseWd=newDialogFrame.width();
			var baseHt=newDialogFrame.height();
			if(dialogFrameWd==null){
				dialogFrameWd=baseWd;
				dialogFrameHt=baseHt;
			}else{
				newDialogFrame.width(dialogFrameWd);
				newDialogFrame.height(dialogFrameHt);
			}
			
			var thWd=$(this).attr("wd");
			var thHt=$(this).attr("ht");
			if(thWd!=null && thWd!=undefined){
				//baseWd=parseInt(thWd);
				//baseHt=parseInt(thHt);
				newDialogFrame.width(thWd);
				newDialogFrame.height(thHt);
			}
			baseWd=newDialogFrame.width();
			baseHt=newDialogFrame.height();
			
			newDialog.dialog({width:baseWd+40,height:baseHt+70});
			newDialog.dialog("open");
			newDialogFrame.attr("src",$(this).attr("href"));
			//newDialogFrame.location=$(this).attr("href");
			return false;
		});
		
		
		//对问卷状态的操作
		$(".executeSurvey").click(function(){
			if(confirm("确认要发布此问卷吗？")){
				window.location=$(this).attr("href");
			}
			return false;
		});
		$(".closeSurvey").click(function(){
			if(confirm("确认要关闭此问卷吗？")){
				window.location=$(this).attr("href");
			}
			return false;
		});
		$(".backDesign").click(function(){
			if(confirm("确认要重新设计此问卷吗？")){
				window.location=$(this).attr("href");
			}
			return false;
		});
		
});

function bindListTrHover(){
	$("#content-tableList tr").unbind();
	$("#content-tableList tr").hover(function(){
		$(this).addClass("curtd");
		$(this).find(".hoverShow").show();
	},function(){
		$(this).removeClass("curtd");
		$(this).find(".hoverShow").hide();
	});
}
function notify(msg) {
	//var msg = "保存成功";
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
		.delay( 5000 )
		.hide({
			effect: "blind",
			duration: "slow"
		}, function() {
			$( this ).remove();
		});
}