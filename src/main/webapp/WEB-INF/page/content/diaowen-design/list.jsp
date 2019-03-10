<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${ctx }/js/plugs/jquery-ui-1.10.3.custom/css/mycss/jquery-ui-1.10.3.custom.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${ctx }/js/plugs/bootstrap-3.3.0-dist/dist/css/bootstrap.css">
<script src="${ctx }/js/plugs/bootstrap-3.3.0-dist/dist/js/bootstrap.js"></script>
<link href="${ctx }/js/plugs/font-awesome-4.2.0/css/font-awesome.css" rel="stylesheet">
<title>我的问卷</title>
<style type="text/css">
.surveyLeftBtnGroup a{
	color: #5A9ECD;
}
.btn-group{
	/* border: 1px solid #D1D1D1; */
}
.btn-group a{
	/* border-radius: 0px; */
	/* border-color: white; */
}
.btn-group a:hover{
	background: #317BCF;
	color: white;
	border-color: #317BCF;
}
.btn-group>.btn:not(:first-child) {
	margin-left: -1px;
}
.contacts-table tr td{
	font-size: 16px! important;
}
.dialogBtn1{
	border:none;
	font-size: 1em;
	font-weight:bold;
  	cursor: pointer;
  	padding: 8px 10px;
}
.dialogBtn1 .ui-button-text {
    padding: .4em 1em;
    display: block;
    line-height: normal;
}
.dialogBtn1Cencel{
	background: none;
}
.dialogBtn1Cencel:hover{
	background: #f6f6f6;
}
button {
	outline: none;
}

.dialogMessage select, .dialogMessage input {
    padding: 5px;
    color: #333333;
    border: 1px solid #98C5C3;
}
</style>
</head>
<body>
	<input type="hidden" id="id" name="id" value="${survey.id }">
	<div style="clear: both;"></div>
	<div id="dwBody" style="margin-top: 15px;">
		<div id="dwBodyContent" class="bodyCenter" style="">
		<div id="dwBodyUser">
			<div class="surveyCollectMiddle">
				<div class="surveyCollectMiddleContent">
					<div style="padding: 25px 45px;overflow: auto;padding-top: 20px;">
							<div style="padding: 5px;color: #666565;letter-spacing: 2px;">
							所有问卷&nbsp;&nbsp;|&nbsp;&nbsp;
								<a href="${ctx }/design/my-survey-create!save.action" id="surveyAdd-a" style="outline: none;text-decoration: none;" ><i class="fa fa-plus " aria-hidden="true"></i>&nbsp;新建问卷</a>
							</div>

							<form action="${ctx}/design/my-survey.action" method="post" >
							<div class="contacts_search" style="padding: 5px;color:#666565;" >
								<div style="padding-left: 20px;padding-top: 8px;padding-bottom: 8px;">
									<span style="font-size: 14px;vertical-align: middle;">状态&nbsp;</span>
									<select name="surveyState" style="vertical-align: middle;">  <option value="">不限</option><option value="0">设计</option><option value="1">发布</option><option value="2">结束</option> </select>
									&nbsp;&nbsp;
									<span style="font-size: 14px;vertical-align: middle;">名称&nbsp;</span>
									<input type="text" class="inputS1" name="surveyName" value="${surveyName}">
									<input type="submit" value="查询" class="sbtn25 sbtn25_1" style="font-size: 16px;"/>
								</div>
								
							</div>
							</form>
							
							<div style="margin-top: 15px;">
							<!-- <div style="padding: 5px;color: #666565;text-align: right;">
								<a href="" class="export-contacts active"><span>&nbsp;</span>导出联系人</a>
							</div> -->
							<div style="padding: 5px;color:#666565; ">
								<table class="contacts-table" width="100%" cellpadding="0" cellspacing="0">
									<tr>
										<th style="text-align: center;" width="30"><!-- <input type="checkbox">  --></th>
										<th align="left" >问卷</th>
										<th align="left" width="100">创建者</th>
										<th align="left" width="200">创建时间</th>
										<th align="left" width="60">答卷</th>
										<th align="left" width="80">状态</th>
										<th align="center" width="220" style="padding-left: 10px;">操作</th>
									</tr>
									<c:choose>
									<c:when test="${page.totalItems > 0}">
									<c:forEach items="${page.result }" var="en">
									<tr>
										<td align="center">
											<input type="hidden" name='surveyId' value="${en.id }">
										</td>
										<td align="left"><a target="_blank" href="${ctx }/dwsurvey/${en.sid }.html" class="titleTag">${en.surveyName }</a></td>
										<td align="left" width="100" >${en.userName }</td>
										<td align="left">
											<fmt:formatDate value="${en.createDate }" pattern="yyyy年MM月dd日 HH:mm"/>
										</td>
										<td align="left">${empty(en.answerNum) ? '0':en.answerNum  }&nbsp;</td>
										<td align="left" >
											${en.surveyState eq 0 ? '设计':en.surveyState eq 1?'发布':en.surveyState eq 2?'收集完成':'' }
										</td>
										<td align="left">
											<div class="btn-group surveyLeftBtnGroup">
											  <a class="btn btn-default" href="${ctx }/design/my-survey-design.action?surveyId=${en.id}" title="设计"data-toggle="tooltip" data-placement="top" ><i class="fa fa-pencil-square-o"></i></a>
											  <a class="btn btn-default" href="${ctx }/design/my-collect.action?surveyId=${en.id}" title="收集答卷" data-toggle="tooltip" data-placement="top" ><i class="fa fa-comments-o"></i></a>
											  <a class="btn btn-default" href="${ctx }/da/survey-report!defaultReport.action?surveyId=${en.id}" title="分析报告" data-toggle="tooltip" data-placement="top" ><i class="fa fa-line-chart"></i></a>
											  <a class="btn btn-default copySurvey" href="#${en.id}" title="复制一份" data-toggle="tooltip" data-placement="top" ><i class="fa fa-files-o"></i></a>
											  <a class="btn btn-default deleteSurvey" href="${ctx}/design/my-survey!delete.action?id=${en.id}" title="删除问卷" data-toggle="tooltip" data-placement="top" ><i class="fa fa-trash-o fa-fw"></i></a>
											</div>&nbsp;
											<div class="btn-group" style="display: none;">
												<!-- <a class="btn btn-default" href="#"><i class="fa fa-eye"></i></a> -->
											    <a class="btn btn-default" href="#"><i class="fa fa-trash-o fa-fw"></i></a>
											</div>
										</td>
									</tr>
									</c:forEach>
									</c:when>
									<c:otherwise>
										<tr>
											<td colspan="7">

												<div style="padding: 60px;font-size: 22px;text-align: center;color: #b1aeae;">还没有数据！</div>

											</td>
										</tr>
									</c:otherwise>
									</c:choose>
								</table>
								<div style="padding-top: 15px;text-align: center;">
									<div class="btn-group">
										<c:if test="${page.pageNo > 1}">
											<a href="${ctx }/design/my-survey.action?page.pageNo=${page.pageNo-1}" class="btn btn-default">&lt;</a>
										</c:if>
										<c:if test="${page.startpage > 1}">
											<a href="${ctx }/design/my-survey.action?page.pageNo=1" class="btn btn-default">1</a>
										</c:if>
										<c:forEach begin="${page.startpage }" end="${page.endpage }" var="en">
											<c:choose>
												<c:when test="${page.pageNo eq en }"><a href="${ctx }/design/my-survey.action?page.pageNo=${en }" class="btn btn-default" style="background: #D3DEED;">${en }</a></c:when>
												<c:otherwise><a href="${ctx }/design/my-survey.action?page.pageNo=${en}" class="btn btn-default">${en }</a></c:otherwise>
											</c:choose>
										</c:forEach>
										<c:if test="${page.totalPage > (page.endpage)}">
											<a href="${ctx }/design/my-survey.action?page.pageNo=${page.totalPage}" class="btn btn-default">${page.totalPage }</a>
										</c:if>
										<c:if test="${page.totalPage > page.pageNo}">
											<a href="${ctx }/design/my-survey.action?page.pageNo=${page.pageNo+1}" class="btn btn-default">&gt;</a>
										</c:if>
										
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
	
	<%--批量添加 --%>


<script type="text/javascript">

	currentMenu("mysurvey");

	$("select[name='surveyState']").val("${surveyState}");

var options={
		animation:true,
		delay:100,
		container:"body",
		trigger:'hover' //触发tooltip的事件
	};
$('a[data-toggle=tooltip]').tooltip(options);

//delete
$(".deleteSurvey").click(function(){
	if(confirm("确认删除吗？")){
		var th=$(this);
		var url=$(this).attr("href");
		var data="";
		$.ajax({
			url:url,
			data:data,
			type:"post",
			success:function(msg){
				if(msg==="true"){
					th.parents("tr").hide("slow");
					th.parents("tr").remove();
				}else{
					alert("删除失败，未登录或没有权限！");
				}
			}
		});
	}
	return false;
});

$(".copySurvey").click(function(){

	var surveyId=$(this).parents("tr").find("input[name='surveyId']").val();
	var titleValue=$(this).parents("tr").find(".titleTag").text();
	var model_groupId1=$(this).parents("tr").find("input[name='groupId1']").val();
	var model_groupId2=$(this).parents("tr").find("input[name='groupId2']").val();

	$("body").append("<div id=\"myDialogRoot\"><div class='dialogMessage' style='padding-top:40px;margin-left:20px;padding-bottom:0px;'>"+
			"<div>复制标题：<input id='surTitleTemp' type='text' style='padding:3px;width:320px;color:rgb(14, 136, 158);' value=''></div></div></div>");

	var myDialog=$( "#myDialogRoot" ).dialog({
		width:500,
		height:220,
		autoOpen: true,
		modal:true,
		position:["center","center"],
		title:"复制问卷、表单",
		resizable:false,
		draggable:false,
		closeOnEscape:false,
		show: {effect:"blind",direction:"up",duration: 500},
		hide: {effect:"blind",direction:"left",duration: 200},
		buttons: {
			"OK":{
				text: "确认复制",
				addClass:'dialogMessageButton dialogBtn1',
				click: function() {
					//执行发布
					var surveyName=$("#surTitleTemp").val();
					surveyName=optionValue=escape(encodeURIComponent(surveyName));

					var params="surveyName="+surveyName;
					params+="&fromBankId="+surveyId;
					window.location.href="${ctx}/design/my-survey-design!copySurvey.action?"+params;
				}
			},
			"CENCEL":{
				text: "取消",
				addClass:"dialogBtn1 dialogBtn1Cencel",
				click: function() {
					$( this ).dialog( "close" );
				}
			}
		},
		open:function(event,ui){
			$(".ui-dialog-titlebar-close").hide();
			$("#surTitleTemp").val(titleValue+"－副本");
		},
		close:function(event,ui){
			$("#myDialogRoot").remove();
		}
	});
});

$("#surveyAdd-a").click(function(){
	
	var surveyId=$(this).parents("tr").find("input[name='surveyId']").val();
	var titleValue=$(this).parents("tr").find(".titleTag").text();
	

	
	$("body").append("<div id=\"myDialogRoot\"><div class='dialogMessage' style='padding-top:40px;margin-left:20px;padding-bottom:0px;'>"+
			"<div>问卷标题：<input id='surTitleTemp' type='text' style='padding:5px;width:320px;color:rgb(14, 136, 158);' value=''></div></div></div>");

	var myDialog=$( "#myDialogRoot" ).dialog({
		width:500,
		height:220,
		autoOpen: true,
		modal:true,
		position:["center","center"],
		title:"新建问卷、表单",
		resizable:false,
		draggable:false,
		closeOnEscape:false,
		show: {effect:"blind",direction:"up",duration: 500},
		hide: {effect:"blind",direction:"left",duration: 200},
		buttons: {
			"OK":{
	            text: "确认新建",
	            addClass:'dialogMessageButton dialogBtn1',
	            click: function() {
	                //执行发布
	                var surveyName=$("#surTitleTemp").val();
	                surveyName=optionValue=escape(encodeURIComponent(surveyName));
	                
	                var params="surveyName="+surveyName;
	            	window.location.href="${ctx}/design/my-survey-create!save.action?"+params;
	            }
			},
			"CENCEL":{
	            text: "取消",
	            addClass:"dialogBtn1 dialogBtn1Cencel",
	            click: function() {
	              $( this ).dialog( "close" );
	            }
			}
		},
		open:function(event,ui){
			$(".ui-dialog-titlebar-close").hide();
			$("#surTitleTemp").val(titleValue+"");
		},
		close:function(event,ui){
			$("#myDialogRoot").remove();
		}
	});
	return false;
});


function setSelectText(el) {
    try {
        window.getSelection().selectAllChildren(el[0]); //全选
        window.getSelection().collapseToEnd(el[0]); //光标置后
    } catch (err) {
        //在此处理错误
    }
}


</script>
</body>
</html>