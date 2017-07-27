<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" >
<title>${surveyName }</title>
<link href="${ctx }/css/response.css" rel="stylesheet" type="text/css" />
<link href="${ctx }/css/preview-dev.css" rel="stylesheet" type="text/css" />
</head>
<body style="background: rgb(245, 245, 245);">
	<div class="root-body" style="padding-top: 80px;">
		<div class="middle-body" style="padding-top:10px;">
			<form action="" method="post">
			<div class="middle-body-content" style="text-align: center;">
				<p class="msg1" style="font-size: 26px;padding:26px;">
					请先输入口令进入问卷
				</p>
				<p  style="font-size: 18px;line-height: 18px;">
					口令码：<input type="text" size="10" name="ruleCode"  style="padding: 5px;outline: none;border: 1px solid #83ABCB;"/>&nbsp;&nbsp;
					<input type="submit"  class="sbtn24 sbtn24_0" value="继续">
				</p>
			</div>
			</form>
			<div style="font-size: 12px;color: #323232;text-align: right;display: none;"><p>	如有疑问可以与管理员&nbsp;<a href="#" class="msg1" style="color: rgb(53, 117, 136);">联系</a>&nbsp;！</p></div>
		</div>
		
		<div class="footer-copyright" style="color: gray;">
			<%--尊重开源、保留声明，感谢您的大力支持--%>
				 <a href="http://www.diaowen.net" style="text-decoration: none;color: rgb(53, 117, 136);">调问网</a> 提供支持
		</div>
	</div>
	<%@ include file="/WEB-INF/page/layouts/other.jsp"%>
</body>
</html>