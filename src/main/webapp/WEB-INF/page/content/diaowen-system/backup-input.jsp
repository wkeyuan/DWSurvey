<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" >
<title>新数据备份</title>
<script type="text/javascript" src="${ctx }/js/plugs/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="${ctx }/js/common/dialog-common.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$("#inputForm").validate({
		rules:{
			backupName:{
				required:true,
				remote:'${ctx}/sy/yb/yang-ben!checkEmailUn.action'
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
			<form id="inputForm" action="${ctx }/sy/system/sys-backup!save.action" method="post" >
			<input type="hidden" name="id" value="${id }" />
			<table class="fromTable">
				<tr>
					<td align="right" ><span class="red-color">*</span>名称：</td>
					<td><input type="text" name="backupName" value="<fmt:formatDate value="${createDate }" pattern="数备_yyyyMMdd_HHmmss"/>" size="50" /></td>
				</tr>
				<tr>
					<td align="right" ><span class="red-color">*</span>备注：</td>
					<td><textarea rows="5" cols="60" name="des"></textarea> </td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td> <input type="submit" value="保  存" class="btn001" /> 
					<input type="button" value="取  消" class="btn004 closeBtn" />
					</td>
				</tr>
			</table>
			</form>
		</div>
	</div>
</body>
</html>