/**
 *
 * DWSurvey 3.0 中关于问卷设计Javascript
 *
 * @desc: design survey
 * @author: keyuan（@keyuan, keyuan258@gmail.com）
 * @github: https://github.com/wkeyuan/DWSurvey
 *
 * Copyright 2012, 2017 调问问卷(DWSurvey,http://dwsurvey.net)
 *
 */
//判断浏览窗口大小
var browseWidth=$(window).width();
var browseHeight=$(window).height();
var ctx="";
var questionBelongId="";
var svTag=2;//表示题目是问卷题还是题库中题

//题目保存后回调时机比较参数
var quCBNum=0;//比较值1
var quCBNum2=0;//比较值2

var curEditObj=null;
var curEditObjOldHtml="";
var dwDialogObj=null;

var isDrag=false;
var appQuObj=null;

$(document).ready(function(){
	ctx=$("#ctx").val();
	questionBelongId=$("#id").val();
	
	browseWidth=$(window).width();
	resizeWrapSize();
	//窗口大小发生改变时
	$(window).resize(function(){
		browseWidth=$(window).width();
		resizeWrapSize();
	});
	
	//窗口滚动条发生scroll时
	$(window).scroll( function() {
		var scrollTop=$(window).scrollTop();
		var quDesignDialog=$("#tools_wrap");
		
		if(scrollTop>=70){
			quDesignDialog.css({ top: "0px"});
		}else{
			quDesignDialog.css({ top: (70-scrollTop)+"px"});
		}
		var dwBodyLeft=$("#dw_body_left");
		var dwBodyRight=$("#dw_body_right");
		if(scrollTop>=70){
			dwBodyLeft.css({top:"136px"});
			dwBodyRight.css({top:"136px"});
		}else{
			dwBodyLeft.css({ top: (205-scrollTop)+"px"});
			dwBodyRight.css({ top: (205-scrollTop)+"px"});
		}
		
		if(scrollTop>=70 && scrollTop<=100){
			//console.debug("(135+(30-(100-scrollTop)))+px:"+(135+(30-(100-scrollTop)))+"px,scrollTop"+scrollTop);
			$("#dw_body").css({"margin-top":(135+(30-(100-scrollTop)))+"px"});
		}else{
			$("#dw_body").css({"margin-top":"135px"});
		}
		
		//修正当前编辑的浮动编辑区位置 
		if(curEditObj!=null){
			var editOffset=$(curEditObj).offset();
			$("#dwCommonEditRoot").show();
			$("#dwCommonEditRoot").offset({top:editOffset.top,left:editOffset.left});
		}
		if(dwDialogObj!=null){
			var thOffset=$(dwDialogObj).offset();
			$("#dwCommonDialog").show();
			$("#dwCommonDialog").offset({ top: thOffset.top, left: thOffset.left+40 });
		}
		
	} );
	
	/*$( "#dwSurveyQuContent" ).sortable({
		revert: true,
		start: function(event,ui){
			$("#tools_wrap").css({"zIndex":30});
			//ui.item.find(".surveyQuItem").addClass("sortActiveclass");
		},
		sort: function(event,ui){
			
		},
		stop: function(event,ui){
			$("#tools_wrap").css({"zIndex":200});
			//ui.item.find(".surveyQuItem").removeClass("sortActiveclass");
			resetQuItem();
		}
	});*/
	
	//拖入题目到问卷中
	$( ".dragQuUl li" ).draggable({
			//connectToSortable: "#dwSurveyQuContent",
			zIndex:27000,
			cursor: "move",cursorAt:{left: 95, top: -35},
			scroll: true ,
			scrollSensitivity: 30,
			scrollSpeed: 30,
			appendTo: "#dw_body_content",
			helper: function(event){
				return $(this).find(".dwQuTypeModel").html();
			},
			start: function(event, ui) {
				isDrag=true;
				$("#tools_wrap").css({"zIndex":30});
				//$("#tools_wrap").hide();
				//console.debug($(this).attr("class")+":"+$(this).css("zIndex"));
				//bindQuHoverItem();
				dwCommonDialogHide();
				curEditCallback();
			},
		    drag: function(event, ui) {
		    	//console.debug( $( "#designQutypes li" ).draggable('option', 'zIndex'));
		    	//console.debug($(ui.helper).css("zIndex")+$(ui.helper).css("position"));
		    },
		    stop: function(event, ui) {
		    	if(true){
		    		$("#tools_wrap").css({"zIndex":200});
			    	isDrag=false;
			    	//alert(this); 
			    	//判断加入----根据initLine显示状态来判断是否加入进去
			    	if(appQuObj!=null){
				    	//$("#defaultAppQuObj").before($(this).find(".quTypeModel").html());
				    	$(appQuObj).before($(this).find(".dwQuTypeModel").html());
				    	$(appQuObj).prev().hide();
				    	$(appQuObj).prev().removeClass("quDragBody");
				    	$(appQuObj).prev().show("slow");
				    	
				    	//更新orderById
				    	/* var orderById=$(appQuObj).find("input[name='orderById']").val();
				    	//$(".initLine").hide();
				    	//执行题目-保存
				    	$(appQuObj).prev().find("input[name='orderById']").val(orderById);
				    	saveQu($(appQuObj).prev());
				    	//修改数据 quItem*/
				    	resetQuItem();
				    	bindQuHoverItem();
			    	}
		    	}
		    }
	}); 
	
	/* $("#dwSurveyTitle").keydown(function(event){
		if(event.keyCode==13){
			return false;
		}
	}); */

	//绑定变动
	bindQuHoverItem();
	
	//切换设置题目时，选项排列个数 option_range
	$(".option_range").change(function(){
		var selVal=$(this).val();
		$(this).next().hide();
		if(selVal==3){
			//按列  width=690
			$(this).next().show();
		}
	});
	
	//逻辑设置时添加逻辑项
	$(".dwQuDialogAddLogic").click(function(){
		addQuDialogLogicTr(true,function(){},function(){alert("此题已经设置了任意选项!");});
		return false;
	});
	
	//保存逻辑设置 
	$("#dwDialogSaveLogic").click(function(){
		var quItemBody=$(dwDialogObj).parents(".surveyQuItemBody");
		var quLogicInputCase=quItemBody.find(".quLogicInputCase");
		
		var dwQuLogicTrs=$("#dwQuLogicTable tr");
		var quLogicItemHtml=$("#quLogicItemModel").html();
		$.each(dwQuLogicTrs,function(){
			var cgQuItemId=$(this).find(".logicQuOptionSel").val();
			var skQuId=$(this).find(".logicQuSel").val();
			var quLogicItemClass=$(this).attr("class");
		
			//判断已经保存过的，保存过的只做修改
			if(skQuId!="" && cgQuItemId!=""){
				var quLogicItem=quLogicInputCase.find("."+quLogicItemClass);
				if(quLogicItem[0]){
					//已经有值--检查值是否有发生变化 
					var oldSkQuId=quLogicItem.find("input[name='skQuId']").val();
					var oldCgQuItemId=quLogicItem.find("input[name='cgQuItemId']").val();
					if(oldSkQuId!=skQuId || cgQuItemId!=oldCgQuItemId){
						quLogicItem.find("input[name='logicSaveTag']").val("0");
						quItemBody.find("input[name='saveTag']").val("0");
					}
				}else{
					quLogicInputCase.append(quLogicItemHtml);
					quLogicItem=quLogicInputCase.find(".quLogicItem").last();
					quLogicItem.addClass(quLogicItemClass);
					//修改值
					quLogicItem.find("input[name='quLogicId']").val("");
					quLogicItem.find("input[name='skQuId']").val(skQuId);
					quLogicItem.find("input[name='cgQuItemId']").val(cgQuItemId);
					quLogicItem.find("input[name='visibility']").val("1");
					quItemBody.find("input[name='saveTag']").val("0");
				}
			}
		});
		
		refreshQuLogicInfo(quItemBody);
		dwCommonDialogHide();
		return false;
	});
	
	//批量添加弹出窗口-保存事件
	$("#dwDialogSaveMoreItem").click(function(){
		var quItemBody=$(dwDialogObj).parents(".surveyQuItemBody");
		var quType=quItemBody.find("input[name='quType']").val();
		
		var areaVal=$("#dwQuMoreTextarea").val();
		var areaValSplits=areaVal.split("\n");
		$.each(areaValSplits,function(i,item){
			item=$.trim(item);
			if(item!=""){
				if(quType=="RADIO"){
					//添加单选选项
					addRadioItem(quItemBody,item);
				}else if(quType=="CHECKBOX"){
					//添加多选选项
					addCheckboxItem(quItemBody,item);	
				}else if(quType=="SCORE"){
					addScoreItem(quItemBody,item);
				}else if(quType=="ORDERQU"){
					addOrderquItem(quItemBody, "");
				}
			}
		});
		$("#dwQuMoreTextarea").val("");
		bindQuHoverItem();
		dwCommonDialogHide();
	});
	
	//设置窗口保存事件
	$("#dwDialogQuSetSave").click(function(){
		if(dwDialogObj!=null){
			var quItemBody=$(dwDialogObj).parents(".surveyQuItemBody");
			//var quType=quItemBody.find("input[name='quType']").val();
			var setIsRequired=$("#dwCommonDialog input[name='setIsRequired']:checked");
			var setRandOrder=$("#dwCommonDialog input[name='setRandOrder']:checked");
			var setHv=$("#dwCommonDialog select[name='setHv']").val();
			var setCellCount=$("#dwCommonDialog input[name='setCellCount']").val();
			
			var oldHv=quItemBody.find("input[name='hv']").val();
			var oldCellCount=quItemBody.find("input[name='cellCount']").val();
			//alert(set_isRequired+":"+set_randOrder+":"+set_hv);
			quItemBody.find("input[name='isRequired']").val(setIsRequired[0]?1:0);
			quItemBody.find("input[name='hv']").val(setHv);
			quItemBody.find("input[name='randOrder']").val(setRandOrder[0]?1:0);
			quItemBody.find("input[name='cellCount']").val(setCellCount);
			quItemBody.find("input[name='saveTag']").val(0);
			
			var selVal=$(".option_range").val();
			if(selVal==1){
				//横排 transverse
				if(oldHv==3){
					quTableOptoin2Li(quItemBody);
				}
				quItemBody.find(".quCoItem ul").addClass("transverse");	
			}else if(selVal==2){
				if(oldHv==3){
					quTableOptoin2Li(quItemBody);
				}else{
					//竖排
					quItemBody.find(".quCoItem ul").removeClass("transverse");
					quItemBody.find(".quCoItem ul li").width("");
				}
			}else if(selVal==3){
				//按列  width=690
				/*quItemBody.find(".quCoItem ul").addClass("transverse");
				quItemBody.find(".quCoItem ul li").width(600/2);
				quItemBody.find(".quCoItem ul li label").width(600/2-50);*/
				//转换到tr中去
				if(oldHv==3){
					if(oldCellCount!=setCellCount){//列数发生了变化
						//调查quTableOption2Table(quItemBody);
						quTableOption2Table(quItemBody);
					}
				}else{
					quLiOption2Table(quItemBody);					
				}
			}else if(selVal==4){
				//下拉选项
				
			}
		}
		dwCommonDialogHide();
		return false;
	});
	
	function quTableOptoin2Li(quItemBody){
		var quCoItemTds=quItemBody.find(".quCoItem .tableQuColItem tr td");
		var ulLiHtml="<ul>";
		$.each(quCoItemTds,function(){
			var tdHtml=$(this).html();
			if(tdHtml!=""){
				ulLiHtml+="<li class='quCoItemUlLi'>"+tdHtml+"</li>";
			}
		});
		ulLiHtml+="<ul>";
		quItemBody.find(".quCoItem table.tableQuColItem").remove();
		quItemBody.find(".quCoItem").append(ulLiHtml);
		quItemBody.find(".quCoItem ul li").width("");
		quItemBody.find(".quCoItem ul li label").width("");
		bindQuHoverItem();
	}
	
	function quLiOption2Table(quItemBody){
		var quCoItemlis=quItemBody.find(".quCoItem ul li");
		var quCoItemLiSize=quCoItemlis.size();
		var cellCount=$("#dwCommonDialog input[name='setCellCount']").val();
		var rowCount=parseInt(quCoItemLiSize/cellCount);
		var remainder=quCoItemLiSize%cellCount;
		
		var tdWidth=parseInt(600/cellCount);
		var tdLabelWidth=tdWidth-10;
		if(remainder>0){
			rowCount=rowCount+1;
		}
		var tableHtmlBuild="<table class='tableQuColItem'>";
		for(var i=0;i<rowCount;i++){
			tableHtmlBuild+="<tr>";
			//0*2+(1)=1    0*2+(2)=2     1*2+(1)=3   1*2+(2)=4    2*2+1=5    2*2+2=6
			for(var j=0;j<cellCount;j++){
				var liIndex=(i*cellCount)+j;
				if(liIndex<quCoItemLiSize){
					var liObj=$(quCoItemlis).get(liIndex);
					tableHtmlBuild+="<td>"+$(liObj).html()+"</td>";	
				}else{
					tableHtmlBuild+="<td><div class='emptyTd'></div></td>";
				}
			}
			tableHtmlBuild+="</tr>";
		}
		
		tableHtmlBuild+="</table>";
		quItemBody.find(".quCoItem ul").remove();
		quItemBody.find(".quCoItem").append(tableHtmlBuild);
		//设置亮度
		quItemBody.find(".quCoItem .tableQuColItem tr td").width(tdWidth);
		quItemBody.find(".quCoItem .tableQuColItem tr td label").width(tdLabelWidth);
		
		bindQuHoverItem();
	}
	
	//表格变换了行数之后
	function quTableOption2Table(quItemBody){
		var quCoItemTds=quItemBody.find(".quCoItem .tableQuColItem tr td");
		var quCoItemTdSize=quCoItemTds.size();
		var cellCount=$("#dwCommonDialog input[name='setCellCount']").val();
		var rowCount=parseInt(quCoItemTdSize/cellCount);
		var remainder=quCoItemTdSize%cellCount;
		
		var tdWidth=parseInt(600/cellCount);
		var tdLabelWidth=tdWidth-10;
		if(remainder>0){
			rowCount=rowCount+1;
		}
		var tableHtmlBuild="<table class='tableQuColItem'>";
		for(var i=0;i<rowCount;i++){
			tableHtmlBuild+="<tr>";
			//0*2+(1)=1    0*2+(2)=2     1*2+(1)=3   1*2+(2)=4    2*2+1=5    2*2+2=6
			for(var j=0;j<cellCount;j++){
				var tdIndex=(i*cellCount)+j;
				if(tdIndex<quCoItemTdSize){
					var tdObj=$(quCoItemTds).get(tdIndex);
					tableHtmlBuild+="<td>"+$(tdObj).html()+"</td>";	
				}else{
					tableHtmlBuild+="<td><div class='emptyTd'></div></td>";
				}
			}
			tableHtmlBuild+="</tr>";
		}
		
		tableHtmlBuild+="</table>";
		quItemBody.find(".quCoItem table.tableQuColItem").remove();
		quItemBody.find(".quCoItem").append(tableHtmlBuild);
		//设置亮度
		quItemBody.find(".quCoItem .tableQuColItem tr td").width(tdWidth);
		quItemBody.find(".quCoItem .tableQuColItem tr td label").width(tdLabelWidth);
		
		bindQuHoverItem();
	}
	
	
	//点击加边框编辑
	$("#dwSurveyNote").click(function(){
		curEditCallback();
		$(this).addClass("click");
		return false;
	});
	
	var isDialogClick=false;
	
	$(document).click(function(){
		curEditCallback();
		if(!isDialogClick){
			dwCommonDialogHide();
			resetQuItemHover(null);
		}
		isDialogClick=false;
	});
	

	$("#dwCommonEditRoot").unbind();
	$("#dwCommonEditRoot").click(function(){
		return false;
	});
	
	$("#dwCommonDialog").click(function(){
		isDialogClick=true;
	});
	
	$(".dwComEditMenuBtn").click(function(){
		//dwComEditMenuBtn
		var dwMenuUl=$(".dwComEditMenuUl:visible");
		if(dwMenuUl[0]){
			$(".dwComEditMenuUl").hide();	
		}else{
			$(".dwComEditMenuUl").show();
		}
		return false;
	});
	
	$("#dwCommonDialogClose").click(function(){
		dwCommonDialogHide();
		resetQuItemHover(null);
	});
	
	$("#dwComEditContent").keyup(function(){
		$(curEditObj).html($("#dwComEditContent").html());
		$(curEditObj).css("display","inline-block");
		
		var dwEditWidth=$(curEditObj).width();
		//var dwEditWidth=$("#dwComEditContent").width();
		var quItemBody=$(curEditObj).parents(".surveyQuItemBody");
		var hv=quItemBody.find("input[name='hv']").val();
		if(hv==3){
			dwEditWidth>600?dwEditWidth=600:dwEditWidth;
		}else{
			dwEditWidth<200?dwEditWidth=200:dwEditWidth>600?dwEditWidth=600:dwEditWidth;
		}
		//dwEditWidth>600?dwEditWidth=600:dwEditWidth;
		$("#dwCommonEditRoot .dwCommonEdit").css("width",dwEditWidth);
		//设置坐标
		if(curEditObj!=null){
			var editOffset=$(curEditObj).offset();
			$("#dwCommonEditRoot").show();
			$("#dwCommonEditRoot").offset({top:editOffset.top,left:editOffset.left});
		}
	});
	
	$("#saveBtn").click(function(){
		curEditCallback();
		dwCommonDialogHide();
		resetQuItemHover(null);
		var fristQuItemBody=$("#dwSurveyQuContent .surveyQuItemBody").first();
		saveQus(fristQuItemBody,function(){});
		/*var quBodys=$("#dwSurveyQuContent .surveyQuItemBody");
		$.each(quBodys,function(i){
			var quType=$(this).find("input[name='quType']").val();
			if(quType=="RADIO"){
				//保存单选
				saveRadio($(this),i);
				quCBNum++;
			}else if(quType=="CHECKBOX"){
				callbackNum++;
			}
		});
		*/
	});
	resetQuItem();
});

function resetQuItem(){
	var surveyQuItems=$("#dwSurveyQuContent .surveyQuItemBody");
	var indexNum=1;
	$.each(surveyQuItems,function(i){
		$(this).find(".quInputCase input[name='orderById']").val(i+1);
		var quType=$(this).find("input[name='quType']").val();
		if(quType!="PAGETAG"){
			$(this).find(".quCoTitle .quCoNum").text((indexNum++)+"、");
		}
	});
	//更新分标标记
	var pageTags=$("#dwSurveyQuContent .surveyQuItemBody input[name='quType'][value='PAGETAG']");
	var pageTagSize=pageTags.size()+1;
	$.each(pageTags,function(i){
		var quItemBody=$(this).parents(".surveyQuItemBody");
		var pageQuContent=quItemBody.find(".pageQuContent");
		pageQuContent.text("下一页（"+(i+1)+"/"+pageTagSize+"）");
	});
}

function bindQuHoverItem(){
	
	$("#dwSurveyQuContent .surveyQuItemBody").unbind();
	$("#dwSurveyQuContent .surveyQuItemBody").hover(function(){
		//显示
		if(isDrag){
			$(this).addClass("showLine");
			appQuObj=$(this);
		}else{
			//显示
			$(this).addClass("hover");
			$(".pageBorderTop").removeClass("nohover");
			//如果是填空
			appQuObj=$(this);
		}
	},function(){
		$(".pageBorderTop").addClass("nohover");
		$(this).removeClass("showLine");
		var hoverTag=$(this).find("input[name='hoverTag']").val();
		if(hoverTag!="hover"){
			$(this).removeClass("hover");
		}
		appQuObj=null;
		
	});
	
	$("#dwSurveyQuContent .surveyQuItemBody").click(function(){
		curEditCallback();
		dwCommonDialogHide();
		$(".surveyQuItemBody").removeClass("hover");
		$(".surveyQuItemBody").find("input[name='hoverTag']").val("0");
		$(this).addClass("hover");
		return false;
	});
	
	$(".quCoItemUlLi").unbind();
	$(".quCoItemUlLi").hover(function(){
		if(!isDrag){
			$(this).addClass("hover");	
		}
	},function(){
		var thClass=$(this).attr("class");
		if(thClass.indexOf("menuBtnClick")<=0){
			$(this).removeClass("hover");
		}
	});
	
	//绑定编辑
	$("#dwSurveyQuContent .editAble").unbind();
	$("#dwSurveyQuContent .editAble").click(function(){
		editAble($(this));
		return false;
	});
	
	//绑定题目删除事件
	$(".dwQuDelete").unbind();
	$(".dwQuDelete").click(function(){
		var quBody=$(this).parents(".surveyQuItemBody");
		if(confirm("确认要删除此题吗？")){
			var quId=quBody.find("input[name='quId']").val();
			if(quId!=""){
				var url=ctx+"/design/question!ajaxDelete.action";
				var data="quId="+quId;
				$.ajax({
					url:url,
					data:data,
					type:"post",
					success:function(msg){
						if(msg=="true"){
							quBody.hide("slow",function(){$(this).remove();resetQuItem();});
						}else{
							alert("删除失败，请重试！");
						}
					}
				});
			}else{
				quBody.hide("slow",function(){$(this).remove();resetQuItem();});
			}
		}
		return false;
	});
	
	$(".questionUp").unbind();
	$(".questionUp").click(function(){
		var nextQuBody=$(this).parents(".surveyQuItemBody");
		var prevQuBody=$(nextQuBody).prev();
		if(prevQuBody[0]){
			var prevQuBodyHtml=prevQuBody.html();
			$(nextQuBody).after("<div class='surveyQuItemBody' >"+prevQuBodyHtml+"</div>");
			var newNextObj=$(nextQuBody).next();
			newNextObj.hide();
			newNextObj.slideDown("slow");
			prevQuBody.slideUp("slow",function(){prevQuBody.remove();resetQuItem();bindQuHoverItem();});
			
			nextQuBody.find("input[name='saveTag']").val(0);
			newNextObj.find("input[name='saveTag']").val(0);
		}else{
			alert("已经是第一个了！");
		}
	});
	
	$(".questionDown").unbind();
	$(".questionDown").click(function(){
		var prevQuBody=$(this).parents(".surveyQuItemBody");
		var nextQuBody=$(prevQuBody).next();
		if(nextQuBody[0]){
			var nextQuBodyHtml=nextQuBody.html();
			$(prevQuBody).before("<div class='surveyQuItemBody' >"+nextQuBodyHtml+"</div>");
			var newPrevObj=$(prevQuBody).prev();
			newPrevObj.hide();
			newPrevObj.slideDown("slow");
			nextQuBody.slideUp("slow",function(){nextQuBody.remove();resetQuItem();bindQuHoverItem();});
			
			prevQuBody.find("input[name='saveTag']").val(0);
			newPrevObj.find("input[name='saveTag']").val(0);
		}else{
			alert("已经是最后一个了！");
		}
	});
	
	$(".dwQuSet").unbind();
	$(".dwQuSet").click(function(){
		showDialog($(this));
		var quItemBody=$(this).parents(".surveyQuItemBody");
		resetQuItemHover(quItemBody);
		return false;
	});
	
	//逻辑设置 
	$(".dwQuLogic").unbind();
	$(".dwQuLogic").click(function(){
		showDialog($(this));
		var quItemBody=$(this).parents(".surveyQuItemBody");
		//默认加载图标
		var fristQuItemBody=$("#dwSurveyQuContent .surveyQuItemBody").first();
		saveQus(fristQuItemBody,function(){
			$(".dwQuDialogCon").hide();
			$("#dwCommonDialog .dwQuDialogLogic").show();
			

			resetQuItemHover(quItemBody);
			bindDialogRemoveLogic();
			
			$("#dwQuLogicTable").empty();
			//逻辑数据回显示
			var quLogicItems=quItemBody.find(".quLogicItem");
			if(quLogicItems[0]){
				$.each(quLogicItems,function(){
					var skQuId=$(this).find("input[name='skQuId']").val();
					var cgQuItemId=$(this).find("input[name='cgQuItemId']").val();
					var thClass=$(this).attr("class");
					thClass=thClass.replace("quLogicItem", "");
					thClass=thClass.replace(" ", "");
					//回显相应的选项
					addQuDialogLogicTr(false,function(){
						//执行成功--设置值
						
						var lastTr=$("#dwQuLogicTable").find("tr").last();
						lastTr.attr("class",thClass);
						lastTr.find(".logicQuOptionSel").val(cgQuItemId);
						lastTr.find(".logicQuSel").val(skQuId);
						
						lastTr.find(".logicQuOptionSel").change();
						lastTr.find(".logicQuSel").change();
						
					},function(){});
				});
			}else{
				$(".dwQuDialogAddLogic").click();
			}
		});
		return false;
	});
	
	//添加行选项
	$(".addOption").unbind();
	$(".addOption").click(function(){
		var quItemBody=$(this).parents(".surveyQuItemBody");
		var quType=quItemBody.find("input[name='quType']").val();
		if(quType=="RADIO"){
			//添加单选选项
			editAble(addRadioItem(quItemBody,""));
			//editAble(quItemBody.find(".quCoItem table .editAble").last());
		}else if(quType=="CHECKBOX"){
			editAble(addCheckboxItem(quItemBody, ""));
			//editAble(quItemBody.find(".quCoItem ul li:last .editAble"));
		}else if(quType=="SCORE"){
			editAble(addScoreItem(quItemBody, ""));
		}else if(quType=="ORDERQU"){
			editAble(addOrderquItem(quItemBody, ""));
		}
		bindQuHoverItem();
		return false;
	});
	//批量添加事件 
	$(".addMoreOption").click(function(){
		showDialog($(this));
		var quItemBody=$(this).parents(".surveyQuItemBody");
		resetQuItemHover(quItemBody);
		return false;
	});
	
	//填空题选项设置
	$(".quFillblankItem .dwComEditMenuBtn").unbind();
	$(".quFillblankItem .dwComEditMenuBtn").click(function(){
		showDialog($(this));
		var quItemBody=$(this).parents(".surveyQuItemBody");
		resetQuItemHover(quItemBody);
		$(this).parents(".quCoItemUlLi").addClass("menuBtnClick");
		return false;
	});
	
	$(".dwOptionUp").unbind();
	$(".dwOptionUp").click(function(){
		//curEditObj
		//判断类型区别table跟ul中的排序
		var quItemBody=$(curEditObj).parents(".surveyQuItemBody");
		var hv=quItemBody.find("input[name='hv']").val();
		if(hv==3){
			var nextTd=$(curEditObj).parents("td");
			var prevTd=nextTd.prev();
			if(prevTd[0]){
				dwOptionUp(prevTd, nextTd);
			}else{
				var nextTr=$(curEditObj).parents("tr");
				var prevTr=nextTr.prev();
				if(prevTr[0]){
					prevTd=prevTr.find("td").last();
					dwOptionUp_1(prevTr, nextTr);
				}else{
					alert("已经是第一个了！");
				}
			}
		}else{
			var nextLi=$(curEditObj).parents("li.quCoItemUlLi");
			var prevLi=nextLi.prev();
			if(prevLi[0]){
				var prevLiHtml=prevLi.html();
				$(nextLi).after("<li class='quCoItemUlLi'>"+prevLiHtml+"</li>");
				prevLi.hide();
				prevLi.remove();
				var editOffset=nextLi.find("label.editAble").offset();
				$("#dwCommonEditRoot").show();
				$("#dwCommonEditRoot").offset({top:editOffset.top,left:editOffset.left});
				bindQuHoverItem();
				$(curEditObj).click();
				$(nextLi).find("input[name='quItemSaveTag']").val(0);
				$(nextLi).next().find("input[name='quItemSaveTag']").val(0);
				var quItemBody=$(curEditObj).parents(".surveyQuItemBody");
				quItemBody.find("input[name='saveTag']").val(0);
			}else{
				alert("已经是第一个了！");
			}
		}
		return false;
	});
	
	function dwOptionUp(prevTd,nextTd){
		var prevTdHtml=prevTd.html();
		$(nextTd).after("<td>"+prevTdHtml+"</td>");
		prevTd.hide();
		prevTd.remove();
		var editOffset=nextTd.find("label.editAble").offset();
		$("#dwCommonEditRoot").show();
		$("#dwCommonEditRoot").offset({top:editOffset.top,left:editOffset.left});
		bindQuHoverItem();
		$(curEditObj).click();
		$(nextTd).find("input[name='quItemSaveTag']").val(0);
		$(nextTd).next().find("input[name='quItemSaveTag']").val(0);
		var quItemBody=$(curEditObj).parents(".surveyQuItemBody");
		quItemBody.find("input[name='saveTag']").val(0);
	}
	
	function dwOptionUp_1(prevTr,nextTr){
		var prevTd=prevTr.find("td").last();
		var nextTd=nextTr.find("td").first();
		
		var prevTdHtml=prevTd.html();
		var nextTdHtml=nextTd.html();
		
		prevTd.before("<td>"+nextTdHtml+"</td>");
		$(nextTd).after("<td>"+prevTdHtml+"</td>");
		
		prevTd.hide();
		prevTd.remove();
		
		nextTd.hide();
		nextTd.remove();
		
		 prevTd=prevTr.find("td").last();
		 nextTd=nextTr.find("td").first();
		
		curEditObj=prevTd.find("label.editAble");
		var editOffset=prevTd.find("label.editAble").offset();
		$("#dwCommonEditRoot").show();
		$("#dwCommonEditRoot").offset({top:editOffset.top,left:editOffset.left});
		bindQuHoverItem();
		$(curEditObj).click();
		$(prevTd).find("input[name='quItemSaveTag']").val(0);
		$(nextTd).find("input[name='quItemSaveTag']").val(0);
		var quItemBody=$(curEditObj).parents(".surveyQuItemBody");
		quItemBody.find("input[name='saveTag']").val(0);
	}
	
	$(".dwOptionDown").unbind();
	$(".dwOptionDown").click(function(){
		//判断类型区别table跟ul中的排序
		var quItemBody=$(curEditObj).parents(".surveyQuItemBody");
		var hv=quItemBody.find("input[name='hv']").val();
		if(hv==3){
			var prevTd=$(curEditObj).parents("td");
			var nextTd=prevTd.next();
			if(nextTd[0]){
				dwOptionDown(prevTd, nextTd);
			}else{
				var nextTr=$(curEditObj).parents("tr");
				var prevTr=nextTr.prev();
				if(prevTr[0]){
					prevTd=prevTr.find("td").last();
					dwOptionUp_1(prevTr, nextTr);
				}else{
					alert("已经是第一个了！");
				}
			}
		}else{
			var prevLi=$(curEditObj).parents("li.quCoItemUlLi");
			var nextLi=prevLi.next();
			if(nextLi[0]){
				var nextLiHtml=nextLi.html();
				$(prevLi).before("<li class='quCoItemUlLi'>"+nextLiHtml+"</li>");
				nextLi.hide();
				nextLi.remove();
				var editOffset=prevLi.find("label.editAble").offset();
				$("#dwCommonEditRoot").show();
				$("#dwCommonEditRoot").offset({top:editOffset.top,left:editOffset.left});
				bindQuHoverItem();
				$(curEditObj).click();
				$(prevLi).find("input[name='quItemSaveTag']").val(0);
				$(prevLi).prev().find("input[name='quItemSaveTag']").val(0);
				var quItemBody=$(curEditObj).parents(".surveyQuItemBody");
				quItemBody.find("input[name='saveTag']").val(0);
			}else{
				alert("已经是最后一个了！");
			}
		}
		return false;
	});
	
	function dwOptionDown(prevTd,nextTd){
		var nextTdHtml=nextTd.html();
		$(prevTd).before("<td>"+nextTdHtml+"</td>");
		nextTd.hide();
		nextTd.remove();
		var editOffset=prevTd.find("label.editAble").offset();
		$("#dwCommonEditRoot").show();
		$("#dwCommonEditRoot").offset({top:editOffset.top,left:editOffset.left});
		bindQuHoverItem();
		$(curEditObj).click();
		$(prevTd).find("input[name='quItemSaveTag']").val(0);
		$(prevTd).next().find("input[name='quItemSaveTag']").val(0);
		var quItemBody=$(curEditObj).parents(".surveyQuItemBody");
		quItemBody.find("input[name='saveTag']").val(0);
	}
	
	
	$(".dwOptionDel").unbind();
	$(".dwOptionDel").click(function(){
		deleteDwOption();
		return false;
	});
	
}

function deleteDwOption(){
	if(curEditObj!=null){
		var quItemBody=$(curEditObj).parents(".surveyQuItemBody");
		var quType=quItemBody.find("input[name='quType']").val();
		if(quType=="RADIO"){
			//添加单选选项
			deleteRadioOption();
		}else if(quType=="CHECKBOX"){
			deleteCheckboxOption();
		}else if(quType=="SCORE"){
			deleteScoreOption();
		}else if(quType=="ORDERQU"){
			deleteOrderquOption();
		}
	}
}

function curEditCallback(){
	if(curEditObj!=null){
		var dwEditHtml=$("#dwComEditContent").html();
		//var curEditObjHtml=$(curEditObj).html();
		if(dwEditHtml==""){
			deleteDwOption();
		}else if(dwEditHtml!=curEditObjOldHtml){
			//更新值
			$(curEditObj).html(dwEditHtml);
			//修改保存状态
			setSaveTag0();
		}
		dwCommonEditHide();
	}
	$("#dwSurveyNote").removeClass("click");
}

function dwCommonEditHide(){
	$("#dwCommonEditRoot").hide();
	$(".dwComEditMenuUl").hide();
	curEditObj=null;
}

//显示弹出层
function showDialog(thDialogObj){
	curEditCallback();
	//var item=thDialogObj.parents(".surveyQuItemBody");
	var thOffset=thDialogObj.offset();
	$("#dwCommonDialog").show();
	$("#dwCommonDialog").offset({ top: thOffset.top, left: thOffset.left+40 });
	//"dwQuSetCon" dwQuAddMore
	var thObjClass=thDialogObj.attr("class");
	var quItemBody=$(thDialogObj).parents(".surveyQuItemBody");
	$("#dwCommonDialog .dwQuDialogCon").hide();
	if(thObjClass.indexOf("addMoreOption")>=0){
		$("#dwCommonDialog .dwQuAddMore").show();
	}else if(thObjClass.indexOf("dwQuSet")>=0){
		$("#dwCommonDialog .dwQuSetCon").show();
		var isRequired=quItemBody.find("input[name='isRequired']").val();
		var hv=quItemBody.find("input[name='hv']").val();
		var randOrder=quItemBody.find("input[name='randOrder']").val();
		var cellCount=quItemBody.find("input[name='cellCount']").val();
		
		$("#dwCommonDialog input[name='setIsRequired']").prop("checked",false);
		$("#dwCommonDialog input[name='setRandOrder']").prop("checked",false);
		$("#dwCommonDialog select[name='setHv']").val(2);
		
		if(isRequired==1){
			//alert($("#dwCommonDialog input[name='setIsRequired']").val());
			$("#dwCommonDialog input[name='setIsRequired']").prop("checked",true);
			//alert("set");
		}
		if(randOrder==1){
			$("#dwCommonDialog input[name='setRandOrder']").prop("checked",true);
		}
		if(hv==3){
			$("#dwCommonDialog .option_range_3").show();
		}else{
			$("#dwCommonDialog .option_range_3").hide();
		}
		$("#dwCommonDialog select[name='setHv']").val(hv);
		$("#dwCommonDialog input[name='setCellCount']").val(cellCount);
	}else if(thObjClass.indexOf("dwQuLogic")>=0){
		$("#dwCommonDialog .dwQuDialogLoad").show();
	}else{
		//暂时加的
		$("#dwCommonDialog .dwQuAddMore").show();
	}
	dwDialogObj=thDialogObj;
}

function dwCommonDialogHide(){
	$("#dwCommonDialog").hide();
	$(".menuBtnClick").removeClass("menuBtnClick");
	dwDialogObj=null;
}

function resetQuItemHover(quItemBody){
	$(".surveyQuItemBody").removeClass("hover");
	$(".surveyQuItemBody").find("input[name='hoverTag']").val("0");
	if(quItemBody!=null){
		quItemBody.addClass("hover");
		quItemBody.find("input[name='hoverTag']").val("hover");
	}
}

function setSaveTag0(){
	var quItemBody=$(curEditObj).parents(".surveyQuItemBody");
	quItemBody.find("input[name='saveTag']").val(0);
	
	var thClass=$(curEditObj).attr("class");
	if(thClass.indexOf("quCoTitleEdit")>0){
		//题目标题
		$(curEditObj).parent().find("input[name='quTitleSaveTag']").val(0);
	}else if(thClass.indexOf("quCoOptionEdit")>0){
		//题目选项
		$(curEditObj).parent().find("input[name='quItemSaveTag']").val(0);
	}
}

//触发显示编辑框 
function editAble(editAbleObj){
	dwCommonDialogHide();
	curEditCallback();
	
	var quItemBody=$(editAbleObj).parents(".surveyQuItemBody");
	resetQuItemHover(quItemBody);
	
	var thClass=$(editAbleObj).attr("class");
	var editOffset=$(editAbleObj).offset();
	$("#dwCommonEditRoot").removeClass();
	if(thClass.indexOf("quCoTitleEdit")>0){
		//题目标题
		$("#dwCommonEditRoot").addClass("quEdit");
	}else if(thClass.indexOf("quCoOptionEdit")>0){
		//题目选项
		$("#dwCommonEditRoot").addClass("quOptionEdit");
	}
	$("#dwCommonEditRoot").show();
	$("#dwCommonEditRoot").offset({top:editOffset.top,left:editOffset.left});
	$("#dwComEditContent").focus();
	$("#dwComEditContent").html($(editAbleObj).html());
	var dwEditWidth=$(editAbleObj).width();
	
	//dwEditWidth<200?dwEditWidth=200:dwEditWidth;
	var hv=quItemBody.find("input[name='hv']").val();
	if(hv==3){
		var dwEditText=$(editAbleObj).text();
		if(dwEditText==""){
			dwEditWidth=$(editAbleObj).parents("td").width()-52;
		}
		dwEditWidth>600?dwEditWidth=600:dwEditWidth;
	}else{
		dwEditWidth<200?dwEditWidth=200:dwEditWidth>600?dwEditWidth=600:dwEditWidth;
	}
	$("#dwCommonEditRoot .dwCommonEdit").css("width",dwEditWidth);
	setSelectText($("#dwComEditContent"));
	curEditObj=$(editAbleObj);
	curEditObjOldHtml=$(editAbleObj).html();
}

function resizeWrapSize(){
	if(browseWidth<950){
		$("#wrap").width(950);
		$("#tools_wrap").width(950);
	}else{
		$("#wrap").width("100%");
		$("#tools_wrap").width("100%");
	}
	//保证居中
	if(browseWidth<780){
		$("#dw_body_content").offset({left:0});
	}else{
		var leftOffset=(browseWidth-780)/2;
		$("#dw_body_content").offset({left:leftOffset});
	}
}
/**
 * 保存标记说明
 * saveTag  标记本题有无变动
 * quTitleSaveTag  标记题标题变动
 * quItemSaveTag 标记题选项变动
 * 0=表示有变动，未保存
 * 1=表示已经保存同步
 */
function saveQus(quItemBody,callback){
	if(quItemBody[0]){
		var quType=quItemBody.find("input[name='quType']").val();
		if(quType=="RADIO"){
			//保存单选
			saveRadio(quItemBody,callback);
		}else if(quType=="CHECKBOX"){
			saveCheckbox(quItemBody, callback);
		}else if(quType=="FILLBLANK"){
			saveFillblank(quItemBody, callback);
		}else if(quType=="SCORE"){
			saveScore(quItemBody, callback);
		}else if(quType=="ORDERQU"){
			saveOrderqu(quItemBody, callback);
		}else if(quType=="PAGETAG"){
			savePagetag(quItemBody, callback);
		}else if(quType=="PARAGRAPH"){
			saveParagraph(quItemBody, callback);
		}else{
			callback();
		}
		
	}
}

//*****单选题****//
/**
** 新保存单选题
**/
function saveRadio(quItemBody,callback){
	var saveTag=quItemBody.find("input[name='saveTag']").val();
	if(saveTag==0){
		var url=ctx+"/design/qu-radio!ajaxSave.action";
		var quType=quItemBody.find("input[name='quType']").val();
		var quId=quItemBody.find("input[name='quId']").val();
		var orderById=quItemBody.find("input[name='orderById']").val();
		var isRequired=quItemBody.find("input[name='isRequired']").val();
		var hv=quItemBody.find("input[name='hv']").val();
		var randOrder=quItemBody.find("input[name='randOrder']").val();
		var cellCount=quItemBody.find("input[name='cellCount']").val();
		
		var data="belongId="+questionBelongId+"&orderById="+orderById+"&tag="+svTag+"&quType="+quType+"&quId="+quId;
		data+="&isRequired="+isRequired+"&hv="+hv+"&randOrder="+randOrder+"&cellCount="+cellCount;
		
		var quTitleSaveTag=quItemBody.find("input[name='quTitleSaveTag']").val();
		if(quTitleSaveTag==0){
			var quTitle=quItemBody.find(".quCoTitleEdit").text();
			data+="&quTitle="+quTitle;
		}
		//var data="belongId="+questionBelongId+"&orderById="+orderById+"&tag="+svTag+"&quType="+quType+"&quTitle="+quTitle+"&quId="+quId;
		//var quItemLis=quItemBody.find(".quCoItem li.quCoItemUlLi");
		var quItemOptions=null;
		if(hv==3){
			//还有是table的情况需要处理
			quItemOptions=quItemBody.find(".quCoItem table.tableQuColItem tr td");
		}else{
			quItemOptions=quItemBody.find(".quCoItem li.quCoItemUlLi");
		}
		
		$.each(quItemOptions,function(i){
			var optionValue=$(this).find("label.quCoOptionEdit").text();
			var optionId=$(this).find(".quItemInputCase input[name='quItemId']").val();
			var quItemSaveTag=$(this).find(".quItemInputCase input[name='quItemSaveTag']").val();
			if(quItemSaveTag==0){
				data+="&optionValue_"+i+"="+optionValue;
				data+="&optionId_"+i+"="+optionId;
			}
			//更新 字母 title标记到选项上.
			$(this).addClass("quOption_"+i);
		});
		
		//逻辑选项
		var quLogicItems=quItemBody.find(".quLogicItem");
		$.each(quLogicItems,function(i){
			var thClass=$(this).attr("class");
			thClass=thClass.replace("quLogicItem quLogicItem_","");
			
			var quLogicId=$(this).find("input[name='quLogicId']").val();
			var cgQuItemId=$(this).find("input[name='cgQuItemId']").val();
			var skQuId=$(this).find("input[name='skQuId']").val();
			var logicSaveTag=$(this).find("input[name='logicSaveTag']").val();
			var visibility=$(this).find("input[name='visibility']").val();
			var itemIndex=thClass;
			if(logicSaveTag==0){
				data+="&quLogicId_"+itemIndex+"="+quLogicId;
				data+="&cgQuItemId_"+itemIndex+"="+cgQuItemId;
				data+="&skQuId_"+itemIndex+"="+skQuId;
				data+="&visibility_"+itemIndex+"="+visibility;
			}
		});
		
		$.ajax({
			url:url,
			data:data,
			type:'post',
			success:function(msg){
				//alert(msg);// resultJson quItemId
				if(msg!="error"){
					var jsons=eval("("+msg+")");
					//alert(jsons);
					var quId=jsons.id;
					quItemBody.find("input[name='quId']").val(quId);
					var quItems=jsons.quItems;
					$.each(quItems,function(i,item){
						var quItemOption=quItemBody.find(".quOption_"+item.title);
						quItemOption.find("input[name='quItemId']").val(item.id);
						quItemOption.find(".quItemInputCase input[name='quItemSaveTag']").val(1);
					});
					
					//同步logic Id信息
					var quLogics=jsons.quLogics;
					$.each(quLogics,function(i,item){
						var logicItem=quItemBody.find(".quLogicItem_"+item.title);
						logicItem.find("input[name='quLogicId']").val(item.id);
						logicItem.find("input[name='logicSaveTag']").val(1);
					});
					
					quItemBody.find("input[name='saveTag']").val(1);
					quItemBody.find(".quCoTitle input[name='quTitleSaveTag']").val(1);
					
					//执行保存下一题
					saveQus(quItemBody.next(),callback);
					//同步-更新题目排序号
					quCBNum2++;
					exeQuCBNum();
				}
			}
		});
	}else{
		saveQus(quItemBody.next(),callback);
	}
}

/** 添加选项 **/
/** 添加单选选项   **/
function addRadioItem(quItemBody,itemText){
	//得判断是否是table类型
	var hv=quItemBody.find("input[name='hv']").val();
	var cellCount=quItemBody.find("input[name='cellCount']").val();
	var newEditObj=null;
	if(hv==3){
		//表格处理
		var quRadioItemHtml=$("#quRadioItem").html();
		//var quCoItemUl=quItemBody.find(".quCoItem table");
		var quTableObj=quItemBody.find(".quCoItem table.tableQuColItem");
		var emptyTdDiv=quTableObj.find("div.emptyTd");
		if(emptyTdDiv[0]){
			//表示有空位
			var emptyTd=emptyTdDiv.first().parents("td");
			emptyTd.empty();
			emptyTd.append(quRadioItemHtml);
		}else{
			//木有空位，根据cellCount生成新的tr,td
			var appendTr="<tr>";
			for(var i=0;i<cellCount;i++){
				appendTr+="<td>";
				if(i==0){
					appendTr+=quRadioItemHtml;
				}else{
					appendTr+="<div class='emptyTd'></div>";
				}
				appendTr+="</td>";
			}
			appendTr+="</tr>";
			quTableObj.append(appendTr);
		}
		var tdWidth=parseInt(600/cellCount);
		var tdLabelWidth=tdWidth-10;
		quItemBody.find(".quCoItem .tableQuColItem tr td").width(tdWidth);
		quItemBody.find(".quCoItem .tableQuColItem tr td label").width(tdLabelWidth);
		newEditObj=quItemBody.find(".quCoItem table").find(".editAble").last();
		//itemText="fsdfsdf";
	}else{
		//ul li处理
		var quRadioItemHtml=$("#quRadioItem").html();
		var quCoItemUl=quItemBody.find(".quCoItem ul");
		quCoItemUl.append("<li class='quCoItemUlLi'>"+quRadioItemHtml+"</li>");
		quItemBody.find("input[name='saveTag']").val(0);
		newEditObj=quCoItemUl.find("li:last .editAble");
	}
	newEditObj.text(itemText);
	if(itemText==""){
		newEditObj.css("display","inline");
	}
	return newEditObj;
}
/** 删除单选题选项 **/
function deleteRadioOption(){
	//判断是否是table类型
	var quItemBody=$(curEditObj).parents(".surveyQuItemBody");
	var hv=quItemBody.find("input[name='hv']").val();
	var optionParent=null;
	if(hv==3){
		optionParent=$(curEditObj).parents("td");
	}else{
		optionParent=$(curEditObj).parents("li.quCoItemUlLi");
	}
	var quOptionId=$(optionParent).find("input[name='quItemId']").val();
	if(quOptionId!="" && quOptionId!="0" ){
		var url=ctx+"/design/qu-radio!ajaxDelete.action";
		var data="quItemId="+quOptionId;
		$.ajax({
			url:url,
			data:data,
			type:"post",
			success:function(msg){
				if(msg=="true"){
					delQuOptionCallBack(optionParent);
				}
			}
		});
	}else{
		delQuOptionCallBack(optionParent);
	}
}

//*******多选题*******//
/**
** 新保存多选题
**/
function saveCheckbox(quItemBody,callback){
	var saveTag=quItemBody.find("input[name='saveTag']").val();
	if(saveTag==0){
		
		var url=ctx+"/design/qu-checkbox!ajaxSave.action";
		var quType=quItemBody.find("input[name='quType']").val();
		var quId=quItemBody.find("input[name='quId']").val();
		var orderById=quItemBody.find("input[name='orderById']").val();;
		var isRequired=quItemBody.find("input[name='isRequired']").val();
		var hv=quItemBody.find("input[name='hv']").val();
		var randOrder=quItemBody.find("input[name='randOrder']").val();
		var cellCount=quItemBody.find("input[name='cellCount']").val();
		
		var data="belongId="+questionBelongId+"&orderById="+orderById+"&tag="+svTag+"&quType="+quType+"&quId="+quId;
		data+="&isRequired="+isRequired+"&hv="+hv+"&randOrder="+randOrder+"&cellCount="+cellCount;
		
		var quTitleSaveTag=quItemBody.find("input[name='quTitleSaveTag']").val();
		if(quTitleSaveTag==0){
			var quTitle=quItemBody.find(".quCoTitleEdit").text();
			data+="&quTitle="+quTitle;
		}
		//var data="belongId="+questionBelongId+"&orderById="+orderById+"&tag="+svTag+"&quType="+quType+"&quTitle="+quTitle+"&quId="+quId;
		//var quItemLis=quItemBody.find(".quCoItem li.quCoItemUlLi");
		var quItemOptions=null;
		if(hv==3){
			//还有是table的情况需要处理
			quItemOptions=quItemBody.find(".quCoItem table.tableQuColItem tr td");
		}else{
			quItemOptions=quItemBody.find(".quCoItem li.quCoItemUlLi");
		}
		
		$.each(quItemOptions,function(i){
			var optionValue=$(this).find("label.quCoOptionEdit").text();
			var optionId=$(this).find(".quItemInputCase input[name='quItemId']").val();
			var quItemSaveTag=$(this).find(".quItemInputCase input[name='quItemSaveTag']").val();
			if(quItemSaveTag==0){
				data+="&optionValue_"+i+"="+optionValue;
				data+="&optionId_"+i+"="+optionId;
			}
			//更新 字母 title标记到选项上.
			$(this).addClass("quOption_"+i);
		});
		//逻辑选项
		var quLogicItems=quItemBody.find(".quLogicItem");
		$.each(quLogicItems,function(i){
			var thClass=$(this).attr("class");
			thClass=thClass.replace("quLogicItem quLogicItem_","");
			
			var quLogicId=$(this).find("input[name='quLogicId']").val();
			var cgQuItemId=$(this).find("input[name='cgQuItemId']").val();
			var skQuId=$(this).find("input[name='skQuId']").val();
			var logicSaveTag=$(this).find("input[name='logicSaveTag']").val();
			var visibility=$(this).find("input[name='visibility']").val();
			var itemIndex=thClass;
			if(logicSaveTag==0){
				data+="&quLogicId_"+itemIndex+"="+quLogicId;
				data+="&cgQuItemId_"+itemIndex+"="+cgQuItemId;
				data+="&skQuId_"+itemIndex+"="+skQuId;
				data+="&visibility_"+itemIndex+"="+visibility;
			}
		});
		
		$.ajax({
			url:url,
			data:data,
			type:'post',
			success:function(msg){
				//alert(msg);// resultJson quItemId
				if(msg!="error"){
					var jsons=eval("("+msg+")");
					//alert(jsons);
					var quId=jsons.id;
					quItemBody.find("input[name='quId']").val(quId);
					var quItems=jsons.quItems;
					$.each(quItems,function(i,item){
						var quItemOption=quItemBody.find(".quOption_"+item.title);
						quItemOption.find("input[name='quItemId']").val(item.id);
						quItemOption.find(".quItemInputCase input[name='quItemSaveTag']").val(1);
					});
					
					//同步logic Id信息
					var quLogics=jsons.quLogics;
					$.each(quLogics,function(i,item){
						var logicItem=quItemBody.find(".quLogicItem_"+item.title);
						logicItem.find("input[name='quLogicId']").val(item.id);
						logicItem.find("input[name='logicSaveTag']").val(1);
					});
					
					quItemBody.find("input[name='saveTag']").val(1);
					quItemBody.find(".quCoTitle input[name='quTitleSaveTag']").val(1);
					
					//执行保存下一题
					saveQus(quItemBody.next(),callback);
					//同步-更新题目排序号
					quCBNum2++;
					exeQuCBNum();
				}
			}
		});
	}else{
		saveQus(quItemBody.next(),callback);
	}
}

/** 添加选项 **/
/** 添加多选选项   **/
function addCheckboxItem(quItemBody,itemText){
	//得判断是否是table类型
	var hv=quItemBody.find("input[name='hv']").val();
	var cellCount=quItemBody.find("input[name='cellCount']").val();
	var newEditObj=null;
	if(hv==3){
		//表格处理
		var quRadioItemHtml=$("#quCheckboxItem").html();
		//var quCoItemUl=quItemBody.find(".quCoItem table");
		var quTableObj=quItemBody.find(".quCoItem table.tableQuColItem");
		var emptyTdDiv=quTableObj.find("div.emptyTd");
		if(emptyTdDiv[0]){
			//表示有空位
			var emptyTd=emptyTdDiv.first().parents("td");
			emptyTd.empty();
			emptyTd.append(quRadioItemHtml);
		}else{
			//木有空位，根据cellCount生成新的tr,td
			var appendTr="<tr>";
			for(var i=0;i<cellCount;i++){
				appendTr+="<td>";
				if(i==0){
					appendTr+=quRadioItemHtml;
				}else{
					appendTr+="<div class='emptyTd'></div>";
				}
				appendTr+="</td>";
			}
			appendTr+="</tr>";
			quTableObj.append(appendTr);
		}
		var tdWidth=parseInt(600/cellCount);
		var tdLabelWidth=tdWidth-10;
		quItemBody.find(".quCoItem .tableQuColItem tr td").width(tdWidth);
		quItemBody.find(".quCoItem .tableQuColItem tr td label").width(tdLabelWidth);
		newEditObj=quItemBody.find(".quCoItem table").find(".editAble").last();
		//itemText="fsdfsdf";
	}else{
		//ul li处理
		var quRadioItemHtml=$("#quCheckboxItem").html();
		var quCoItemUl=quItemBody.find(".quCoItem ul");
		quCoItemUl.append("<li class='quCoItemUlLi'>"+quRadioItemHtml+"</li>");
		quItemBody.find("input[name='saveTag']").val(0);
		newEditObj=quCoItemUl.find("li:last .editAble");
	}
	newEditObj.text(itemText);
	if(itemText==""){
		newEditObj.css("display","inline");
	}
	return newEditObj;
}
/** 删除多选题选项 **/
function deleteCheckboxOption(){
	//判断是否是table类型
	var quItemBody=$(curEditObj).parents(".surveyQuItemBody");
	var hv=quItemBody.find("input[name='hv']").val();
	var optionParent=null;
	if(hv==3){
		optionParent=$(curEditObj).parents("td");
	}else{
		optionParent=$(curEditObj).parents("li.quCoItemUlLi");
	}
	var quOptionId=$(optionParent).find("input[name='quItemId']").val();
	if(quOptionId!="" && quOptionId!="0" ){
		var url=ctx+"/design/qu-checkbox!ajaxDelete.action";
		var data="quItemId="+quOptionId;
		$.ajax({
			url:url,
			data:data,
			type:"post",
			success:function(msg){
				if(msg=="true"){
					delQuOptionCallBack(optionParent);
				}
			}
		});
	}else{
		delQuOptionCallBack(optionParent);
	}
}

function delQuOptionCallBack(optionParent){
	var quItemBody=$(optionParent).parents(".surveyQuItemBody");
	var quType=quItemBody.find("input[name='quType']").val();
	if(quType=="CHECKBOX" || quType=="RADIO"){
		var hv=quItemBody.find("input[name='hv']").val();
		if(hv==3){
			//emptyTd
			var optionTr=$(optionParent).parents("tr");
			var optionNextTr=optionTr.next();
			if(optionNextTr[0]){
				//则后面还有是中间选项，则删除，再依次后面的td往前移动
				$(optionParent).remove();
				moveTabelTd(optionNextTr);
			}else{
				//非中间选项，删除-再添加一个空td
				$(optionParent).remove();
				movePareseLastTr(optionTr);
			}
		}else{
			optionParent.remove();	
		}
	}else{
		optionParent.remove();	
	}
	dwCommonEditHide();
	bindQuHoverItem();
}
function moveTabelTd(nextTr){
	if(nextTr[0]){
		var prevTr=nextTr.prev();
		var nextTds=nextTr.find("td");
		$(nextTds.get(0)).appendTo(prevTr);
		//判断当前next是否是最后一个，是则：判断如果没有选项，则删除tr,如果有选项，则填一个空td
		var nextNextTr=nextTr.next();
		if(!nextNextTr[0]){
			movePareseLastTr(nextTr);
		}
		moveTabelTd($(nextTr).next());
	}
}
function movePareseLastTr(nextTr){
	var editAbles=nextTr.find(".editAble");
	if(editAbles[0]){
		//有选项，则补充一个空td
		var editAbleTd=editAbles.parents("td");
		editAbleTd.clone().prependTo(nextTr);
		nextTr.find("td").last().html("<div class='emptyTd'></div>");
	}else{
		nextTr.remove();
	}
}

//*******填空题*******//
/**
** 新保存填空题
**/
function saveFillblank(quItemBody,callback){
	var saveTag=quItemBody.find("input[name='saveTag']").val();
	if(saveTag==0){
		var url=ctx+"/design/qu-fillblank!ajaxSave.action";
		var quType=quItemBody.find("input[name='quType']").val();
		var quId=quItemBody.find("input[name='quId']").val();
		var orderById=quItemBody.find("input[name='orderById']").val();;
		var isRequired=quItemBody.find("input[name='isRequired']").val();
		var hv=quItemBody.find("input[name='hv']").val();
		var randOrder=quItemBody.find("input[name='randOrder']").val();
		var cellCount=quItemBody.find("input[name='cellCount']").val();
		
		var data="belongId="+questionBelongId+"&orderById="+orderById+"&tag="+svTag+"&quType="+quType+"&quId="+quId;
		data+="&isRequired="+isRequired+"&hv="+hv+"&randOrder="+randOrder+"&cellCount="+cellCount;
		
		var quTitleSaveTag=quItemBody.find("input[name='quTitleSaveTag']").val();
		if(quTitleSaveTag==0){
			var quTitle=quItemBody.find(".quCoTitleEdit").text();
			data+="&quTitle="+quTitle;
		}
		//逻辑选项
		var quLogicItems=quItemBody.find(".quLogicItem");
		$.each(quLogicItems,function(i){
			var thClass=$(this).attr("class");
			thClass=thClass.replace("quLogicItem quLogicItem_","");
			
			var quLogicId=$(this).find("input[name='quLogicId']").val();
			var cgQuItemId=$(this).find("input[name='cgQuItemId']").val();
			var skQuId=$(this).find("input[name='skQuId']").val();
			var logicSaveTag=$(this).find("input[name='logicSaveTag']").val();
			var visibility=$(this).find("input[name='visibility']").val();
			var itemIndex=thClass;
			if(logicSaveTag==0){
				data+="&quLogicId_"+itemIndex+"="+quLogicId;
				data+="&cgQuItemId_"+itemIndex+"="+cgQuItemId;
				data+="&skQuId_"+itemIndex+"="+skQuId;
				data+="&visibility_"+itemIndex+"="+visibility;
			}
		});
		
		$.ajax({
			url:url,
			data:data,
			type:'post',
			success:function(msg){
				//alert(msg);// resultJson quItemId
				if(msg!="error"){
					var jsons=eval("("+msg+")");
					//alert(jsons);
					var quId=jsons.id;
					quItemBody.find("input[name='quId']").val(quId);
					
					//同步logic Id信息
					var quLogics=jsons.quLogics;
					$.each(quLogics,function(i,item){
						var logicItem=quItemBody.find(".quLogicItem_"+item.title);
						logicItem.find("input[name='quLogicId']").val(item.id);
						logicItem.find("input[name='logicSaveTag']").val(1);
					});
					
					quItemBody.find("input[name='saveTag']").val(1);
					quItemBody.find(".quCoTitle input[name='quTitleSaveTag']").val(1);
					//执行保存下一题
					saveQus(quItemBody.next(),callback);
					//同步-更新题目排序号
					quCBNum2++;
					exeQuCBNum();
				}
			}
		});
	}else{
		saveQus(quItemBody.next(),callback);
	}
}



//*****评分题****//
/**
** 新保存评分题
**/
function saveScore(quItemBody,callback){
	var saveTag=quItemBody.find("input[name='saveTag']").val();
	if(saveTag==0){
		var url=ctx+"/design/qu-score!ajaxSave.action";
		var quType=quItemBody.find("input[name='quType']").val();
		var quId=quItemBody.find("input[name='quId']").val();
		var orderById=quItemBody.find("input[name='orderById']").val();
		var isRequired=quItemBody.find("input[name='isRequired']").val();
		var hv=quItemBody.find("input[name='hv']").val();
		var randOrder=quItemBody.find("input[name='randOrder']").val();
		var cellCount=quItemBody.find("input[name='cellCount']").val();
		
		var data="belongId="+questionBelongId+"&orderById="+orderById+"&tag="+svTag+"&quType="+quType+"&quId="+quId;
		data+="&isRequired="+isRequired+"&hv="+hv+"&randOrder="+randOrder+"&cellCount="+cellCount;
		
		var quTitleSaveTag=quItemBody.find("input[name='quTitleSaveTag']").val();
		if(quTitleSaveTag==0){
			var quTitle=quItemBody.find(".quCoTitleEdit").text();
			data+="&quTitle="+quTitle;
		}
		//var data="belongId="+questionBelongId+"&orderById="+orderById+"&tag="+svTag+"&quType="+quType+"&quTitle="+quTitle+"&quId="+quId;
		//var quItemLis=quItemBody.find(".quCoItem li.quCoItemUlLi");
		//评分题选项td
		var quItemOptions=quItemBody.find(".quCoItem table.quCoItemTable tr td.quOptionEditTd");
		$.each(quItemOptions,function(i){
			var optionValue=$(this).find("label.quCoOptionEdit").text();
			var optionId=$(this).find(".quItemInputCase input[name='quItemId']").val();
			var quItemSaveTag=$(this).find(".quItemInputCase input[name='quItemSaveTag']").val();
			if(quItemSaveTag==0){
				data+="&optionValue_"+i+"="+optionValue;
				data+="&optionId_"+i+"="+optionId;
			}
			//更新 字母 title标记到选项上.
			$(this).addClass("quOption_"+i);
		});
		
		//逻辑选项
		var quLogicItems=quItemBody.find(".quLogicItem");
		$.each(quLogicItems,function(i){
			var thClass=$(this).attr("class");
			thClass=thClass.replace("quLogicItem quLogicItem_","");
			
			var quLogicId=$(this).find("input[name='quLogicId']").val();
			var cgQuItemId=$(this).find("input[name='cgQuItemId']").val();
			var skQuId=$(this).find("input[name='skQuId']").val();
			var logicSaveTag=$(this).find("input[name='logicSaveTag']").val();
			var visibility=$(this).find("input[name='visibility']").val();
			var itemIndex=thClass;
			if(logicSaveTag==0){
				data+="&quLogicId_"+itemIndex+"="+quLogicId;
				data+="&cgQuItemId_"+itemIndex+"="+cgQuItemId;
				data+="&skQuId_"+itemIndex+"="+skQuId;
				data+="&visibility_"+itemIndex+"="+visibility;
			}
		});
		
		$.ajax({
			url:url,
			data:data,
			type:'post',
			success:function(msg){
				//alert(msg);// resultJson quItemId
				if(msg!="error"){
					var jsons=eval("("+msg+")");
					//alert(jsons);
					var quId=jsons.id;
					quItemBody.find("input[name='quId']").val(quId);
					var quItems=jsons.quItems;
					$.each(quItems,function(i,item){
						var quItemOption=quItemBody.find(".quOption_"+item.title);
						quItemOption.find("input[name='quItemId']").val(item.id);
						quItemOption.find(".quItemInputCase input[name='quItemSaveTag']").val(1);
					});
					
					//同步logic Id信息
					var quLogics=jsons.quLogics;
					$.each(quLogics,function(i,item){
						var logicItem=quItemBody.find(".quLogicItem_"+item.title);
						logicItem.find("input[name='quLogicId']").val(item.id);
						logicItem.find("input[name='logicSaveTag']").val(1);
					});
					
					quItemBody.find("input[name='saveTag']").val(1);
					quItemBody.find(".quCoTitle input[name='quTitleSaveTag']").val(1);
					
					//执行保存下一题
					saveQus(quItemBody.next(),callback);
					//同步-更新题目排序号
					quCBNum2++;
					exeQuCBNum();
				}
			}
		});
	}else{
		saveQus(quItemBody.next(),callback);
	}
}

/** 添加选项 **/
/** 添加评分项   **/
function addScoreItem(quItemBody,itemText){
	//得判断是否是table类型
	var newEditObj=null;
	//ul li处理
	var quScoreItemHtml=$("#quScoreItemModel").html();
	var quCoItemTable=quItemBody.find("table.quCoItemTable");
	quCoItemTable.append("<tr class='quScoreOptionTr'>"+quScoreItemHtml+"</tr>");
	quItemBody.find("input[name='saveTag']").val(0);
	newEditObj=quCoItemTable.find("tr.quScoreOptionTr:last .editAble");
	
	newEditObj.text(itemText);
	if(itemText==""){
		newEditObj.css("display","inline");
	}
	return newEditObj;
}
/** 删除评分Score选项 **/
function deleteScoreOption(){
	var optionParent=null;
	optionParent=$(curEditObj).parents("tr.quScoreOptionTr");
	
	var quOptionId=$(optionParent).find("input[name='quItemId']").val();
	if(quOptionId!="" && quOptionId!="0" ){
		var url=ctx+"/design/qu-score!ajaxDelete.action";
		var data="quItemId="+quOptionId;
		$.ajax({
			url:url,
			data:data,
			type:"post",
			success:function(msg){
				if(msg=="true"){
					delQuOptionCallBack(optionParent);
				}
			}
		});
	}else{
		delQuOptionCallBack(optionParent);
	}
}


//*****排序题****//
/**
** 新保存排序题
**/
function saveOrderqu(quItemBody,callback){
	var saveTag=quItemBody.find("input[name='saveTag']").val();
	if(saveTag==0){
		var url=ctx+"/design/qu-orderqu!ajaxSave.action";
		var quType=quItemBody.find("input[name='quType']").val();
		var quId=quItemBody.find("input[name='quId']").val();
		var orderById=quItemBody.find("input[name='orderById']").val();
		var isRequired=quItemBody.find("input[name='isRequired']").val();
		var hv=quItemBody.find("input[name='hv']").val();
		var randOrder=quItemBody.find("input[name='randOrder']").val();
		var cellCount=quItemBody.find("input[name='cellCount']").val();
		
		var data="belongId="+questionBelongId+"&orderById="+orderById+"&tag="+svTag+"&quType="+quType+"&quId="+quId;
		data+="&isRequired="+isRequired+"&hv="+hv+"&randOrder="+randOrder+"&cellCount="+cellCount;
		
		var quTitleSaveTag=quItemBody.find("input[name='quTitleSaveTag']").val();
		if(quTitleSaveTag==0){
			var quTitle=quItemBody.find(".quCoTitleEdit").text();
			data+="&quTitle="+quTitle;
		}
		//var data="belongId="+questionBelongId+"&orderById="+orderById+"&tag="+svTag+"&quType="+quType+"&quTitle="+quTitle+"&quId="+quId;
		//var quItemLis=quItemBody.find(".quCoItem li.quCoItemUlLi");
		//评分题选项td
		var quItemOptions=quItemBody.find(".quCoItem .quOrderByLeft  li.quCoItemUlLi");
		$.each(quItemOptions,function(i){
			var optionValue=$(this).find("label.quCoOptionEdit").text();
			var optionId=$(this).find(".quItemInputCase input[name='quItemId']").val();
			var quItemSaveTag=$(this).find(".quItemInputCase input[name='quItemSaveTag']").val();
			if(quItemSaveTag==0){
				data+="&optionValue_"+i+"="+optionValue;
				data+="&optionId_"+i+"="+optionId;
			}
			//更新 字母 title标记到选项上.
			$(this).addClass("quOption_"+i);
		});
		
		//逻辑选项
		var quLogicItems=quItemBody.find(".quLogicItem");
		$.each(quLogicItems,function(i){
			var thClass=$(this).attr("class");
			thClass=thClass.replace("quLogicItem quLogicItem_","");
			
			var quLogicId=$(this).find("input[name='quLogicId']").val();
			var cgQuItemId=$(this).find("input[name='cgQuItemId']").val();
			var skQuId=$(this).find("input[name='skQuId']").val();
			var logicSaveTag=$(this).find("input[name='logicSaveTag']").val();
			var visibility=$(this).find("input[name='visibility']").val();
			var itemIndex=thClass;
			if(logicSaveTag==0){
				data+="&quLogicId_"+itemIndex+"="+quLogicId;
				data+="&cgQuItemId_"+itemIndex+"="+cgQuItemId;
				data+="&skQuId_"+itemIndex+"="+skQuId;
				data+="&visibility_"+itemIndex+"="+visibility;
			}
		});
		
		$.ajax({
			url:url,
			data:data,
			type:'post',
			success:function(msg){
				//alert(msg);// resultJson quItemId
				if(msg!="error"){
					var jsons=eval("("+msg+")");
					//alert(jsons);
					var quId=jsons.id;
					quItemBody.find("input[name='quId']").val(quId);
					var quItems=jsons.quItems;
					$.each(quItems,function(i,item){
						var quItemOption=quItemBody.find(".quOption_"+item.title);
						quItemOption.find("input[name='quItemId']").val(item.id);
						quItemOption.find(".quItemInputCase input[name='quItemSaveTag']").val(1);
					});
					
					//同步logic Id信息
					var quLogics=jsons.quLogics;
					$.each(quLogics,function(i,item){
						var logicItem=quItemBody.find(".quLogicItem_"+item.title);
						logicItem.find("input[name='quLogicId']").val(item.id);
						logicItem.find("input[name='logicSaveTag']").val(1);
					});
					
					quItemBody.find("input[name='saveTag']").val(1);
					quItemBody.find(".quCoTitle input[name='quTitleSaveTag']").val(1);
					
					//执行保存下一题
					saveQus(quItemBody.next(),callback);
					//同步-更新题目排序号
					quCBNum2++;
					exeQuCBNum();
				}
			}
		});
	}else{
		saveQus(quItemBody.next(),callback);
	}
}

/** 添加选项 **/
/** 添加排序项   **/
function addOrderquItem(quItemBody,itemText){
	//得判断是否是table类型
	var newEditObj=null;
	//ul li处理 <li class="quCoItemUlLi">
	var quOrderItemLeftHtml=$("#quOrderItemLeftModel").html();
	var quOrderItemRightHtml=$("#quOrderItemRightModel").html();
	
	var quOrderItemLeftUl=quItemBody.find(".quOrderByLeft ul");
	var quOrderByRightTable=quItemBody.find(".quOrderByRight table.quOrderByTable");
	quOrderItemLeftUl.append("<li class='quCoItemUlLi'>"+quOrderItemLeftHtml+"</li>");
	quOrderByRightTable.append("<tr>"+quOrderItemRightHtml+"</tr>");
	
	quItemBody.find("input[name='saveTag']").val(0);
	newEditObj=quOrderItemLeftUl.find("li:last .editAble");
	
	newEditObj.text(itemText);
	if(itemText==""){
		newEditObj.css("display","inline");
	}
	//quOrderyTableTd
	refquOrderTableTdNum(quOrderByRightTable);
	return newEditObj;
}
function refquOrderTableTdNum(quOrderByRightTable){
	var quOrderyTableTds=quOrderByRightTable.find(".quOrderyTableTd");
	$.each(quOrderyTableTds,function(i){
		$(this).text(i+1);
	});
}
/** 删除排序选项 **/
function deleteOrderquOption(){
	var optionParent=null;
	optionParent=$(curEditObj).parents("li.quCoItemUlLi");
	var quItemBody=$(curEditObj).parents(".surveyQuItemBody");
	var rmQuOrderTableTr=quItemBody.find(".quOrderByRight table.quOrderByTable tr:last");
	
	var quOptionId=$(optionParent).find("input[name='quItemId']").val();
	if(quOptionId!="" && quOptionId!="0" ){
		var url=ctx+"/design/qu-orderqu!ajaxDelete.action";
		var data="quItemId="+quOptionId;
		$.ajax({
			url:url,
			data:data,
			type:"post",
			success:function(msg){
				if(msg=="true"){
					delQuOptionCallBack(optionParent);
					rmQuOrderTableTr.remove();
				}
			}
		});
	}else{
		delQuOptionCallBack(optionParent);
		rmQuOrderTableTr.remove();
	}
}


//*******分页标记*******//
/**
** 新保存分页标记
**/
function savePagetag(quItemBody,callback){
	var saveTag=quItemBody.find("input[name='saveTag']").val();
	if(saveTag==0){
		var url=ctx+"/design/qu-pagetag!ajaxSave.action";
		var quType=quItemBody.find("input[name='quType']").val();
		var quId=quItemBody.find("input[name='quId']").val();
		var orderById=quItemBody.find("input[name='orderById']").val();;
		var isRequired=quItemBody.find("input[name='isRequired']").val();
		var hv=quItemBody.find("input[name='hv']").val();
		var randOrder=quItemBody.find("input[name='randOrder']").val();
		var cellCount=quItemBody.find("input[name='cellCount']").val();
		
		var data="belongId="+questionBelongId+"&orderById="+orderById+"&tag="+svTag+"&quType="+quType+"&quId="+quId;
		data+="&isRequired="+isRequired+"&hv="+hv+"&randOrder="+randOrder+"&cellCount="+cellCount;
		
		var quTitleSaveTag=quItemBody.find("input[name='quTitleSaveTag']").val();
		if(quTitleSaveTag==0){
			var quTitle=quItemBody.find(".quCoTitleEdit").text();
			data+="&quTitle="+quTitle;
		}
		//逻辑选项
		var quLogicItems=quItemBody.find(".quLogicItem");
		$.each(quLogicItems,function(i){
			var thClass=$(this).attr("class");
			thClass=thClass.replace("quLogicItem quLogicItem_","");
			
			var quLogicId=$(this).find("input[name='quLogicId']").val();
			var cgQuItemId=$(this).find("input[name='cgQuItemId']").val();
			var skQuId=$(this).find("input[name='skQuId']").val();
			var logicSaveTag=$(this).find("input[name='logicSaveTag']").val();
			var visibility=$(this).find("input[name='visibility']").val();
			var itemIndex=thClass;
			if(logicSaveTag==0){
				data+="&quLogicId_"+itemIndex+"="+quLogicId;
				data+="&cgQuItemId_"+itemIndex+"="+cgQuItemId;
				data+="&skQuId_"+itemIndex+"="+skQuId;
				data+="&visibility_"+itemIndex+"="+visibility;
			}
		});
		
		$.ajax({
			url:url,
			data:data,
			type:'post',
			success:function(msg){
				//alert(msg);// resultJson quItemId
				if(msg!="error"){
					var jsons=eval("("+msg+")");
					//alert(jsons);
					var quId=jsons.id;
					quItemBody.find("input[name='quId']").val(quId);
					
					//同步logic Id信息
					var quLogics=jsons.quLogics;
					$.each(quLogics,function(i,item){
						var logicItem=quItemBody.find(".quLogicItem_"+item.title);
						logicItem.find("input[name='quLogicId']").val(item.id);
						logicItem.find("input[name='logicSaveTag']").val(1);
					});
					
					quItemBody.find("input[name='saveTag']").val(1);
					quItemBody.find(".quCoTitle input[name='quTitleSaveTag']").val(1);
					//执行保存下一题
					saveQus(quItemBody.next(),callback);
					//同步-更新题目排序号
					quCBNum2++;
					exeQuCBNum();
				}
			}
		});
	}else{
		saveQus(quItemBody.next(),callback);
	}
}

//*******段落说明题*******//
/**
** 新保存段落题
**/
function saveParagraph(quItemBody,callback){
	var saveTag=quItemBody.find("input[name='saveTag']").val();
	if(saveTag==0){
		var url=ctx+"/design/qu-paragraph!ajaxSave.action";
		var quType=quItemBody.find("input[name='quType']").val();
		var quId=quItemBody.find("input[name='quId']").val();
		var orderById=quItemBody.find("input[name='orderById']").val();;
		var isRequired=quItemBody.find("input[name='isRequired']").val();
		var hv=quItemBody.find("input[name='hv']").val();
		var randOrder=quItemBody.find("input[name='randOrder']").val();
		var cellCount=quItemBody.find("input[name='cellCount']").val();
		
		var data="belongId="+questionBelongId+"&orderById="+orderById+"&tag="+svTag+"&quType="+quType+"&quId="+quId;
		data+="&isRequired="+isRequired+"&hv="+hv+"&randOrder="+randOrder+"&cellCount="+cellCount;
		
		var quTitleSaveTag=quItemBody.find("input[name='quTitleSaveTag']").val();
		if(quTitleSaveTag==0){
			var quTitle=quItemBody.find(".quCoTitleEdit").text();
			data+="&quTitle="+quTitle;
		}
		//逻辑选项
		var quLogicItems=quItemBody.find(".quLogicItem");
		$.each(quLogicItems,function(i){
			var thClass=$(this).attr("class");
			thClass=thClass.replace("quLogicItem quLogicItem_","");
			
			var quLogicId=$(this).find("input[name='quLogicId']").val();
			var cgQuItemId=$(this).find("input[name='cgQuItemId']").val();
			var skQuId=$(this).find("input[name='skQuId']").val();
			var logicSaveTag=$(this).find("input[name='logicSaveTag']").val();
			var visibility=$(this).find("input[name='visibility']").val();
			var itemIndex=thClass;
			if(logicSaveTag==0){
				data+="&quLogicId_"+itemIndex+"="+quLogicId;
				data+="&cgQuItemId_"+itemIndex+"="+cgQuItemId;
				data+="&skQuId_"+itemIndex+"="+skQuId;
				data+="&visibility_"+itemIndex+"="+visibility;
			}
		});
		
		$.ajax({
			url:url,
			data:data,
			type:'post',
			success:function(msg){
				//alert(msg);// resultJson quItemId
				if(msg!="error"){
					var jsons=eval("("+msg+")");
					//alert(jsons);
					var quId=jsons.id;
					quItemBody.find("input[name='quId']").val(quId);
					
					//同步logic Id信息
					var quLogics=jsons.quLogics;
					$.each(quLogics,function(i,item){
						var logicItem=quItemBody.find(".quLogicItem_"+item.title);
						logicItem.find("input[name='quLogicId']").val(item.id);
						logicItem.find("input[name='logicSaveTag']").val(1);
					});
					
					quItemBody.find("input[name='saveTag']").val(1);
					quItemBody.find(".quCoTitle input[name='quTitleSaveTag']").val(1);
					//执行保存下一题
					saveQus(quItemBody.next(),callback);
					//同步-更新题目排序号
					quCBNum2++;
					exeQuCBNum();
				}
			}
		});
	}else{
		saveQus(quItemBody.next(),callback);
	}
}

/**逻辑设置**/
//添加逻辑选项
function addQuDialogLogicTr(autoClass,trueCallback,falseCallback){
	//当前题的选项
	var quItemBody=$(dwDialogObj).parents(".surveyQuItemBody");
	var quItemInputCases=quItemBody.find(".quItemInputCase");
	var quLogicInputCase=quItemBody.find(".quLogicInputCase");

	var logicQuOptionSels=$("#dwQuLogicTable").find(".logicQuOptionSel");
	var dwLogicQuSels=$("#dwQuLogicTable").find(".logicQuSel");
	//判断有无选项任意选项
	var executeTag=true;
	$.each(logicQuOptionSels,function(){
		var selOptionVal=$(this).val();
		if(selOptionVal=="0"){
			executeTag=false;
			return false;
		}
	});
	
	if(executeTag){
		var appendTrHtml=$("#setQuLogicItemTrModel").html();
		$("#dwQuLogicTable").append("<tr>"+appendTrHtml+"</tr>");
		var lastTr=$("#dwQuLogicTable").find("tr").last();
		if(autoClass){
			var quLogicItemNum=quLogicInputCase.find("input[name='quLogicItemNum']");
			var newQuLogicItemNum=(parseInt(quLogicItemNum.val())+1);
			quLogicItemNum.val(newQuLogicItemNum);
			var newQuLogicItemClass="quLogicItem_"+newQuLogicItemNum;
			lastTr.attr("class",newQuLogicItemClass);
		}
	
		var dwQuOptionSel=lastTr.find(".logicQuOptionSel");
		
		var eachTag=true;
		$.each(quItemInputCases,function(){
			var optionText=$(this).prev().text();
			var optionId=$(this).find("input[name='quItemId']").val();
			eachTag=true;
			$.each(logicQuOptionSels,function(){
				var selOptionVal=$(this).val();
				if(selOptionVal==optionId){
					eachTag=false;
					return false;
				}
			});
			if(eachTag){
				dwQuOptionSel.append("<option value='"+optionId+"'>"+optionText+"</option>");	
			}
		});
		if(logicQuOptionSels.size()==0){
			dwQuOptionSel.append("<option value='0'>任意选项</option>");	
		}else{
			//移除所有-移除已经设置过的
			$("#dwQuLogicTable").find(".logicQuOptionSel option[value='0']").remove();
		}
		
		//当前所有题
		var logicQuSel=lastTr.find(".logicQuSel");
		
		var quItemBodys=$("#dwSurveyQuContent .surveyQuItemBody");
		$.each(quItemBodys,function(){
			//logicQuSels
			if($(this).find(".quCoTitleEdit")[0]){
				var quCoNumText=$(this).find(".quCoNum").text();
				var quTitleText=$(this).find(".quCoTitleEdit").text();
				var quId=$(this).find("input[name='quId']").val();
				eachTag=true;
				$.each(dwLogicQuSels,function(){
					var dwLogicQuSelVal=$(this).val();
					if(dwLogicQuSelVal==quId){
						eachTag=false;
						return false;
					}
				});
				if(eachTag){
					logicQuSel.append("<option value='"+quId+"'>"+quCoNumText+quTitleText+"</option>");	
				}
			}
		});
		logicQuSel.append("<option value='1'>正常结束（计入结果）</option><option value='2'>提前结束（不计入结果）</option>");
		
		if(autoClass){
			logicQuSel.prepend("<option value=''>-请选择选项-</option>");
			dwQuOptionSel.prepend("<option value=''>-请选择选项-</option>");
		}
		
		bindDialogRemoveLogic();
		trueCallback();
	}else{
		falseCallback();
	}
	
}

//绑定逻辑设置中选项删除事件
function bindDialogRemoveLogic(){
	$(".dialogRemoveLogic").unbind();
	$(".dialogRemoveLogic").click(function(){
		var logicItemTr=$(this).parents("tr");
		var logicItemTrClass=logicItemTr.attr("class");
		//同时移除页面只保存的信息--注意如果已经保存到库中，修改
		var quItemBody=$(dwDialogObj).parents(".surveyQuItemBody");
		var quLogicItem=quItemBody.find("."+logicItemTrClass);
		if(quLogicItem[0]){
			//有则判断，是否已经加入到数据库
			var quLogicIdVal=quLogicItem.find("input[name='quLogicId']").val();
			if(quLogicIdVal!=""){
				quLogicItem.find("input[name='visibility']").val(0);
				quLogicItem.find("input[name='logicSaveTag']").val(0);
				quItemBody.find("input[name='saveTag']").val(0);
			}else{
				quLogicItem.remove();
			}
			//更新select option信息
			var logicQuOptionSel=logicItemTr.find(".logicQuOptionSel option:selected");
			var logicQuSel=logicItemTr.find(".logicQuSel option:selected");
			if(logicQuOptionSel.val()!=""){
				$("#dwQuLogicTable").find(".logicQuOptionSel").append("<option value='"+logicQuOptionSel.val()+"'>"+logicQuOptionSel.text()+"</option>");
			}
			if(logicQuSel.val()!=""){
				$("#dwQuLogicTable").find(".logicQuSel").append("<option value='"+logicQuSel.val()+"'>"+logicQuSel.text()+"</option>");
			}
		}
		logicItemTr.remove();
		
		refreshQuLogicInfo(quItemBody);
		return false;
	});
	$(".logicQuOptionSel").unbind();
	$(".logicQuOptionSel").change(function(){
		var thVal=$(this).val();
		//刷新
		$("#dwQuLogicTable").find(".logicQuOptionSel").not(this).find("option[value='"+thVal+"']").remove();
	});
	$(".logicQuSel").unbind();
	$(".logicQuSel").change(function(){
		var thVal=$(this).val();
		$("#dwQuLogicTable").find(".logicQuSel").not(this).find("option[value='"+thVal+"']").remove();
	});
}

//刷新每题的逻辑显示数目
function refreshQuLogicInfo(quItemBody){
	var quLogicItems=quItemBody.find(".quLogicItem input[name='visibility'][value='1']");
	var quLogicItemSize=quLogicItems.size();
	quItemBody.find(".quLogicInfo").text(quLogicItemSize);
}

function exeQuCBNum(){
	if(quCBNum==quCBNum2){
		quCBNum=0;
		quCBNum2=0;
		//全部题排序号同步一次
		//对如新增插入题-需要同步调整其它题的排序
		//对如删除题-需要同步调整其它题的排序
	}
}

function setSelectText(el) {
    try {
        window.getSelection().selectAllChildren(el[0]); //全选
        window.getSelection().collapseToEnd(el[0]); //光标置后
       /*var Check = check_title_select(el.text());
        window.getSelection().selectAllChildren(el[0]); //全选
        if (!Check) {
            window.getSelection().collapseToEnd(el[0]); //光标置后
        }*/
    } catch (err) {
        //在此处理错误
    }
    //      if(document.selection){
    //          
    //      }else{
    //         var Check = check_title_select(el.text());
    //
    //          window.getSelection().selectAllChildren(el[0]);//全选
    //         if(!Check){
    //          window.getSelection().collapseToEnd(el[0]);//光标置后
    //         }
    //      }
}

/*
//触发input菜单框
function showInputMenuBtn(editAbleObj){
	var quItemBody=$(editAbleObj).parents(".surveyQuItemBody");
	var quType=quItemBody.find("input[name='quType']").val();
	if(quType=="FILLBLANK"){
		$("#dwCommonInputMenuRoot").show();
		var fillblankInputObj=$(editAbleObj).find("input");
		var editOffset=$(fillblankInputObj).offset();
		var offLeft=editOffset.left+$(fillblankInputObj).width()+10;
		$("#dwCommonInputMenuRoot").offset({top:editOffset.top,left:offLeft});
		//quItemBody.find("input[name='hoverTag']").val("hover");
	}
}
function hideInputMenuBtn(){
	$("#dwCommonInputMenuRoot").hide();
}
*/
