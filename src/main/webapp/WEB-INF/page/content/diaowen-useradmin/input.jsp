<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${ctx }/js/plugs/jquery-ui-1.10.3.custom/css/mycss/jquery-ui-1.10.3.custom.css" rel="stylesheet" type="text/css" />
<title>我的账号</title>
<link href="${ctx}/js/plugs/validate/jquery.validate.css" type="text/css" rel="stylesheet" />
<script src="${ctx}/js/plugs/validate/jquery.validate.js" type="text/javascript"></script>
<script src="${ctx}/js/plugs/validate/messages_cn.js" type="text/javascript"></script>
<script src="${ctx }/js/plugs/validate/jquery.metadata.js" type="text/javascript"></script>
<script type="text/javascript">
	
$(document).ready(function(){
	$("#inputForm").validate({
		rules:{
			loginName:{
				required:true,
				remote:{
					url: "${ctx}/sy/user/user-admin!checkLoginNamelUn.action",     //后台处理程序
					type: "post",  //数据发送方式
					data: {   //要传递的数据
						loginName: function() { return $("input[name='loginName']").val(); },
						id:function(){ return $("input[name='id']").val(); }
					}
				}
			},
			email:{
				required:true,
				email:true,
				//remote:'${ctx}/sy/yb/yang-ben!checkEmailUn.action'
				remote:{
						url: "${ctx}/sy/user/user-admin!checkEmailUn.action",     //后台处理程序
						type: "post",  //数据发送方式
						data: {   //要传递的数据
							email: function() { return $("input[name='email']").val(); },
							id:function(){ return $("input[name='id']").val(); }
						}
					}
			},
			name:{required:true},
			pwd:{required:true,minlength:6,maxlength:40},
		},
		errorPlacement: function(error, element) {
		    //error.appendTo(element.parent().parent());
			 element.parent().append(error);
		}
	});
	
	$("#pwdEdit").click(function(){
		var thTd=$(this).parent();
		$(this).remove();
		thTd.append("<input type=\"password\" name=\"pwd\" value=\"\" id=\"pwd\">");
		return false;
	});
});

	
</script>
<style type="text/css">
.ac-input-td input,.ac-input-td select{
	padding: 4px! important;
	font-size: 14px;
}
.red-color{
	color: red;
}
</style>
</head>
<body>
	
	<div style="margin-top: 15px;">
		
	</div>
	<div style="clear: both;"></div>
	<div id="dwBody" >
		<div id="dwBodyContent" class="bodyCenter" style="">
		
		<div id="dwBodyUser">
			<div class="surveyCollectMiddle">
				
				<form id="inputForm" action="${ctx }/sy/user/nosm/user-admin!save.action" method="post" >
				<input type="hidden" name="id" value="${id }" >
				<div class="surveyCollectMiddleContent">
					<div style="padding: 25px 45px;overflow: auto;padding-top: 35px;">
							<div style="border-bottom: 1px solid #DFDFDF;padding: 5px;color: #666565;">账号信息</div>
							<div style="padding: 5px;color:#666565; ">
								<table width="100%">
									<tr>
										<td valign="top" align="left" >
											<table class="ac-form-table">
												<tr>
													<td width="80" align="right"><span class="red-color">*&nbsp;</span>昵称</td>
													<td class="ac-input-td"><input type="text"  name="name" value="${loginName }"  > </td>
												</tr>
												<tr>
													<td width="80" align="right"><span class="red-color">*&nbsp;</span>登录名</td>
													<td class="ac-input-td"><input type="text"  name="loginName" value="${loginName }"  > </td>
												</tr>
												<tr>
													<td width="80" align="right"><span class="red-color">*&nbsp;</span>邮箱</td>
													<td class="ac-input-td"><input type="text"  name="email" value="${email }" > </td>
												</tr>

												<c:if test="${empty(id) }">
												<tr>
													<td width="80" align="right"><span class="red-color">*&nbsp;</span>登录密码</td>
													<td class="ac-input-td"><input type="password" name="pwd" value="" id="pwd">
													</td>
												</tr>
												</c:if>
												<c:if test="${!empty(id) }">
													<tr>
														<td width="80" align="right"><span class="red-color">*&nbsp;</span>状态</td>
														<td class="ac-input-td">
															<input type="radio" name="status" value="1" >可用&nbsp;&nbsp;&nbsp;
															<input type="radio" name="status" value="0" >禁用
														</td>
													</tr>
													<tr>
														<td width="80" align="right"><span class="red-color">*&nbsp;</span>登录密码</td>
														<td class="ac-input-td">
														<a href="#" id="pwdEdit">修改</a>
														</td>
													</tr>
												</c:if>
											</table>
										</td>
									</tr>
									<!-- <tr>
										<td>
										<table class="ac-form-table">
											<tr>
													<td width="80" align="right">地址</td>
													<td class="ac-input-td"><input type="text" > </td>
												</tr>
											</table>
										</td>
									</tr> -->
									<tr>
										<td height="50"><input type="submit" value="保存修改" class="sbtn25 sbtn25_1" style="margin-left: 125px;"> </td>
										<td class="ac-input-td"> </td>
									</tr>
								</table>
							</div>
							
					</div>
				</div>
				</form>
				
			</div>
			
		</div>
		</div>
	</div>
<script type="text/javascript">
$("input[name='status'][value='${status}']").prop("checked",true);
</script>
</body>
</html>