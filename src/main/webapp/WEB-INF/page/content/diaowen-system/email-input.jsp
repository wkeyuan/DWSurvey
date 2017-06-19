<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" >
<title>SMTP服务邮箱</title>
<script type="text/javascript" src="${ctx }/js/plugs/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="${ctx }/js/common/dialog-common.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$("input[name='isCheck'][value='${isCheck}']").attr("checked",true);
	$("input[name='state'][value='${state}']").attr("checked",true);
	
	$("#inputForm").validate({
		rules:{
			sendEmail:{
				required:true,
				email:true,
				//remote:'${ctx}/sy/yb/yang-ben!checkEmailUn.action'
				remote:{
						url: "${ctx}/sy/yb/yang-ben!checkEmailUn.action",     //后台处理程序
						type: "post",  //数据发送方式
						data: {   //要传递的数据
							sendEmail: function() { return $("input[name='sendEmail']").val(); },
							id:function(){ return $("input[name='id']").val(); }
						}
					}
			}
		},
		errorPlacement: function(error, element) {
		    //error.appendTo(element.parent().parent());
			 element.parent().append(error);
		}
	});
});
</script>
<style type="text/css">
	.fromTable tr td{
		height: 40px! important;
	}
</style>
</head>
<body>
	<div class="dailogFrame">
		<div>
			<form id="inputForm" action="${ctx }/sy/system/sys-email!save.action" method="post" >
			<input type="hidden" name="id" value="${id }" />
			<input type="hidden" name="dirType" value="2" />
			<input type="hidden" name="parentId" value="${parentId }" />
			<table class="fromTable">
				<tr>
					<td align="right" ><span class="red-color">*</span>SMTP 服务器：</td>
					<td><input type="text" name="stmpServer" value="${stmpServer }" size="50" class="required"/> </td>
				</tr>
				<tr>
					<td align="right" ><span class="red-color">*</span>端口：</td>
					<td><input type="text" name="post" value="${post }" size="50" class="required"/> </td>
				</tr>
				<tr>
					<td align="right"  >验证：</td>
					<td><input type="radio" name="isCheck" value="1">是&nbsp;<input type="radio" name="isCheck" value="0">否 </td>
				</tr>
				<tr>
					<td align="right" ><span class="red-color">*</span>发信邮件地址：</td>
					<td><input type="text" name="sendEmail"  size="40" value="${sendEmail }"  > </td>
				</tr>
				<tr>
					<td align="right" ><span class="red-color">*</span>验证密码：</td>
					<td><input type="text" name="stmpPwd" value="${stmpPwd }"></td>
				</tr>
				<tr>
					<td align="right" ><span class="red-color"></span>两次发送间隔：</td>
					<td>
						<input type="text" name="stmpJg" value="20" size="5">秒&nbsp;&nbsp;&nbsp;
						<span>每天最多发送<input type="text" name="dayMaxCount" value="1000" size="5">封</span>
						(0或不填无限制)
					</td>
				</tr>
				<tr>
					<td align="right">状态：</td>
					<td><input type="radio" name="state" value="1" >使用&nbsp;<input type="radio" name="state" value="0" checked="checked">不使用 </td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td> <input type="submit" value="保  存" class="btn001" />
					<input type="button" value="测试连接" class="btn004" />
					<input type="button" value="取  消" class="btn004 closeBtn" />
					</td>
				</tr>
			</table>
			</form>
		</div>
	</div>
</body>
</html>