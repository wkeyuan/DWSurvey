<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${ctx }/js/plugs/jquery-ui-1.10.3.custom/css/mycss/jquery-ui-1.10.3.custom.css" rel="stylesheet" type="text/css" />
	<script src="${ctx }/js/plugs/jquery-ui-1.10.3.custom/js/jquery-1.10.1.js" type="text/javascript" ></script>

	<link href="${ctx}/js/plugs/validate/jquery.validate.css" type="text/css" rel="stylesheet" />
	<script src="${ctx}/js/plugs/validate/jquery.validate.js" type="text/javascript"></script>
	<script src="${ctx}/js/plugs/validate/messages_cn.js" type="text/javascript"></script>
	<script src="${ctx }/js/plugs/validate/jquery.metadata.js" type="text/javascript"></script>
	<script src="${ctx }/js/common/common.js" type="text/javascript"></script>
<title>我的账号</title>
	<style type="text/css>">
		.pwdTd{
			display: none;
		}
	</style>
<script type="text/javascript">
	
$(document).ready(function(){
	var inputFormValidate=$("#input_form").validate({
		rules: {
			email: {required: true, email: true, maxlength: 60},
		},
		messages: {
			email: {
				email: "格式不正确！",
				remote: "邮箱已存在"
			}
		},
		errorPlacement: function(error, element) {
			//error.appendTo(element.parent().parent());
			element.parent().append(error);
			//	$(element).css("borderColor","#C40000");
		}
	});

});

	
</script>
</head>
<body>
	<input type="hidden" id="id" name="id" value="${survey.id }">
	
	<div style="margin-top: 15px;">
		<div class="main-tabs-content bodyCenter">
			<div class="tab-content">
				<div class="tab-content-collectTab" style="text-align: left;">
					<a href="" class="nav_a">我的账号</a>
				</div>
			</div>
		</div>
	</div>
	<div style="clear: both;"></div>
	<div id="dwBody" >
		<div id="dwBodyContent" class="bodyCenter" style="">
		<div class="tab-content" style="background: #D9D9D9;padding: 0px 5px;">
				<div class="tab-content-collectTab icTab" style="text-align: left;">
					<a href="${ctx }/ic/user/myaccount.do" class="collectTab tabItem_1 active"><span class="collectTabItemLeft">&nbsp;</span><span>我的账号</span></a>
				</div>
		</div>
		<div id="dwBodyUser">
			<div class="surveyCollectMiddle">
				
				<div class="surveyCollectMiddleContent">
					<div style="padding: 25px 45px;overflow: auto;padding-top: 35px;">
							<div style="border-bottom: 1px solid #DFDFDF;padding: 5px;color: #666565;">账号信息</div>
							<div style="padding: 5px;color:#666565; ">
								<form id="input_form" action="${ctx }/ic/user/save.do" method="post" >
								<table class="ac-form-table">
									<tr>
										<td width="80" align="right">账号</td>
										<td class="ac-input-td"><input type="text" name="loginName" value="${user.loginName }" readonly="readonly"  style="background: rgb(240, 240, 240);" > </td>
									</tr>
									<tr>
										<td width="80" align="right">邮箱</td>
										<td class="ac-input-td"><input type="text" id="email" name="email" value="${user.email }"> </td>
									</tr>
									<tr>
										<td width="80" align="right">手机号</td>
										<td class="ac-input-td"><input type="text" name="cellphone" value="${user.cellphone }"> </td>
									</tr>
									<tr>
										<td width="80" align="right">姓名</td>
										<td class="ac-input-td"><input type="text" name="name" value="${user.name }"> </td>
									</tr>
									<tr>
										<td width="80" align="right">密码</td>
										<td class="ac-input-td"><a href="${ctx}/ic/user/pwd.do" >修改密码</a> </td>
									</tr>
									<tr>
										<td></td>
										<td class="ac-input-td"> <button type="submit" class="sbtn25 sbtn25_1" > 保存修改 </button></td>
									</tr>
								</table>
								</form>
							</div>

					</div>
					
				</div>
			</div>
			
		</div>
		</div>
	</div>
<script type="text/javascript">

</script>
</body>
</html>