<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${ctx }/js/plugs/jquery-ui-1.10.3.custom/css/mycss/jquery-ui-1.10.3.custom.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${ctx }/js/plugs/bootstrap-3.3.0-dist/dist/css/bootstrap.css">
<!-- 可选的Bootstrap主题文件（一般不用引入） -->
<!-- <link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap-theme.min.css"> -->
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<!-- <script src="http://cdn.bootcss.com/bootstrap/3.3.0/js/bootstrap.min.js"></script> -->
<script src="${ctx }/js/plugs/bootstrap-3.3.0-dist/dist/js/bootstrap.js"></script>
<!-- <link href="//maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet"> -->
<link href="${ctx }/js/plugs/font-awesome-4.2.0/css/font-awesome.css" rel="stylesheet">

<title>用户管理</title>
<script type="text/javascript">
	
$(document).ready(function(){
	currentMenu("usermanager");
	$(".checkboxAll").unbind();
	$(".checkboxAll").change(function(){
		if($(this).prop("checked")){
			$(".quItemCheckbox").prop("checked",true);
		}else{
			$(".quItemCheckbox").prop("checked",false);
		}
	});
	
	$(".quItemCheckbox").change(function(){
		var noCheckeds=$(".quItemCheckbox").not(":checked");
		if(noCheckeds[0]){
			$(".checkboxAll").prop("checked",false);
		}else{
			$(".checkboxAll").prop("checked",true);
		}
	});

	
});

	
</script>
<style type="text/css">
.ui-state-default, .ui-widget-content .ui-state-default, .ui-widget-header .ui-state-default{
	background: #EFEFEF;
}
.usGroupUl{
	list-style: none;
	padding: 5px;
}
.usGroupUl li{
	list-style: none;
	font-size: 14px;
}
.usGroupUl li a{
	color: #333;
}
.new-dialog .ui-dialog-buttonset button{
	padding: 5px 10px;
}
</style>
</head>
<body>
	<input type="hidden" id="id" name="id" value="${survey.id }">
	
	<div style="clear: both;"></div>
	<div id="dwBody" style="margin-top: 15px;" >
		<div id="dwBodyContent" class="bodyCenter" style="">
		<div id="dwBodyUser">
			<div class="surveyCollectMiddle">
				
				<div class="surveyCollectMiddleContent">
					<div style="padding: 25px 45px;overflow: auto;padding-top: 12px;">
							<div style="padding: 5px;color: #666565;letter-spacing: 2px;font-size: 18px;">账号</div>
							
							<div class="contacts_search" style="padding: 5px;color:#666565;margin-top: 15px;" >
								<form action="${ctx }/sy/user/user-admin.action" method="post">
								<div style="padding-left: 30px;padding-top: 8px;padding-bottom: 8px;">
										<span style="font-size: 14px;vertical-align: middle;">状态&nbsp;</span>
										<select name="status" style="vertical-align: middle;">
											<option value="">不限</option>
											<option value="1">可用</option>
											<option value="0">禁用</option>
										</select>&nbsp;&nbsp;
									<span style="font-size: 14px;vertical-align: middle;">用户名&nbsp;</span>
									<input type="text"  name="loginName" value="${loginName }" class="inputS1"/>&nbsp;&nbsp;
									<input type="submit" value="查询" class="sbtn25 sbtn25_1" style="font-size: 16px;"/>
									
									<div style="padding: 5px;color: #666565;text-align: right;float: right;margin-right: 20px;">
										<a href="${ctx }/sy/user/user-admin!input.action" class="user-plus active"><i class="fa fa-plus " aria-hidden="true"></i>&nbsp;添加账号</a>
									</div>
								</div>
								</form>
							</div>
							
							<div style="margin-top: 15px;">
							
							<div style="padding: 5px;color:#666565; ">
								
								<div style="">
									<table class="contacts-table" width="100%" cellpadding="0" cellspacing="0">
									<tr>
										<th style="text-align: center;" width="30"><!-- <input type="checkbox" class="checkboxAll">  --></th>
										<th align="left">登录名</th>
										<!-- <th align="left" >邮箱</th> -->
										<th align="left" width="200">创建时间</th>
										<th align="left" width="200">最后登录时间</th>
										<th align="left"  width="80">状态</th>
										<th align="center" style="text-align: center;" width="160">操作</th>
									</tr>

										<c:choose>
											<c:when test="${page.totalItems > 0}">

												<c:forEach items="${page.result }" var="en">
													<tr>
														<td align="center"><%-- <input type="checkbox" class="quItemCheckbox" value="${en.id }">  --%></td>
														<td align="left">${en.loginName }</td>
														<%--<td align="left">${en.email }</td>--%>
														<td align="left"><fmt:formatDate value="${en.createTime }" pattern="yyyy年MM月dd日 HH:mm"/></td>
														<td align="left"><fmt:formatDate value="${en.createTime }" pattern="yyyy年MM月dd日 HH:mm"/></td>
														<td align="left">${en.status ne 0 ? '可用':'不可用' }</td>
														<td align="center">
															<a class="btn btn-default" href="${ctx }/sy/user/nosm/user-admin!input.action?id=${en.id}" title="编辑"data-toggle="tooltip" data-placement="top" ><i class="fa fa-pencil-square-o"></i></a>
															<a class="btn btn-default disUser_a" href="${ctx }/sy/user/nosm/user-admin!delete.action?id=${en.id}" title="禁用"data-toggle="tooltip" data-placement="top" ><i class="fa fa-trash-o" aria-hidden="true"></i></a>
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
											<a href="${ctx }/sy/user/user-admin.action?page.pageNo=${page.pageNo-1}" class="btn btn-default">&lt;</a>
										</c:if>
										<c:if test="${page.startpage > 1}">
											<a href="${ctx }/sy/user/user-admin.action?page.pageNo=1" class="btn btn-default">1</a>
											<c:if test="${page.startpage > 2 }">
												<span>...</span>
											</c:if>
										</c:if>
										<c:forEach begin="${page.startpage }" end="${page.endpage }" var="en">
											<c:choose>
												<c:when test="${page.pageNo eq en }"><a href="${ctx }/sy/user/user-admin.action?page.pageNo=${en }" class="btn btn-default" style="background: #D3DEED;">${en }</a></c:when>
												<c:otherwise><a href="${ctx }/sy/user/user-admin.action?page.pageNo=${en}" class="btn btn-default">${en }</a></c:otherwise>
											</c:choose>
										</c:forEach>
										<c:if test="${page.totalPage > (page.endpage)}">
											<c:if test="${page.totalPage > (page.endpage+1)}">
												<span>...</span>
											</c:if>
											<a href="${ctx }/sy/user/user-admin.action?page.pageNo=${page.totalPage}" class="btn btn-default">${page.totalPage }</a>
										</c:if>
										<c:if test="${page.totalPage > page.pageNo}">
											<a href="${ctx }/sy/user/user-admin.action?page.pageNo=${page.pageNo+1}" class="btn btn-default">&gt;</a>
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
	</div>

	
	
<script type="text/javascript">
$(".disUser_a").click(function(){
	var thVal=$(this).text();
	var th=$(this);
	if(confirm("确定要删除吗？")){
		var url=$(this).attr("href");
		var data="";
		$.ajax({
			url:url,
			data:data,
			type:'post',
			success:function(msg){
				if(msg=="true"){
					$(th).parents("tr").remove();
					/* if(thVal=="禁用"){
						$(th).text("启用");
						$(th).parents("tr").find("td:eq(4)").text("不可用");
					}else{
						$(th).text("禁用");
						$(th).parents("tr").find("td:eq(4)").text("可用");
					} */
				}
			}
		});
	}
	//delete
	return false;
});

$("select[name='status']").val("${status}");

resizeBodyWidth();
$("a").attr("hidefocus",true);

</script>
</body>
</html>
