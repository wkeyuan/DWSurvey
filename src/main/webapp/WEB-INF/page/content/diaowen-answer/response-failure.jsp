<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" >
<title>${surveyName }</title>
<link href="${ctx }/css/response.css" rel="stylesheet" type="text/css" />
</head>
<body style="background: rgb(245, 245, 245);">
<div class="root-body" style="padding-top: 80px;">
		<div class="middle-body" style="padding-top:10px;">
			
			<div class="middle-body-content" style="text-align: center;">
				<p class="msg1" style="font-size: 26px;">
					抱歉问卷提交失败，感谢您的支持！
				</p>
				<p style="text-align: center;">
				<a href="${ctx }/wenjuan/${sId}.html" class="msg1" style="color: rgb(53, 117, 136);">返回重新填写</a>
				</p>
			</div>
			
		</div>
		
		<div class="footer-copyright" style="color: gray;">
				 <a href="http://www.diaowen.net" style="text-decoration: none;color: rgb(53, 117, 136);">调问网</a> 提供支持
		</div>
	</div>
	
	<%@ include file="/WEB-INF/page/layouts/other.jsp"%>
	
</body>
</html>