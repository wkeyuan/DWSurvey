<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>完成问卷</title>
<link rel="stylesheet" href="${ctx }/js/plugs/jquery.mobile-1.4.5/jquery.mobile-1.4.5.min.css">
<script type="text/javascript" src="${ctx }/js/plugs/jquery-ui-1.10.3.custom/js/jquery-1.10.1.js"></script>
<script src="${ctx }/js/plugs/jquery.mobile-1.4.5/jquery.mobile-1.4.5.min.js"></script>
<link href="${ctx }/js/plugs/font-awesome-4.2.0/css/font-awesome.css" rel="stylesheet">
<style type="text/css">
.ui-page {
	background:white;
}
.ui-header{
	/* background-color: #3D586C! important; */
	text-shadow:0 1px 0 #5C4242! important;
	background-color: #5693C0! important;
}
.ui-content{
	padding: 40px 1em;
	text-align: center;
}
.ui-footer{
	color: #3D586C! important;
	background: none! important;
}
.ui-header, .ui-footer{
	border: none! important;
}
.starRating{
	font-size: 26px;
}
.starRating .fa{
	cursor: pointer;
}
.starRating .fa-star{
	color: #3388CC;
}
</style>
</head>
<body>

<div data-role="page">
  <div data-role="header" >
    <h1 style="color:rgb(244, 234, 195);">${directory.surveyName }</h1>
  </div>

  <div data-role="content">
   <p>问卷提交成功，感谢您的参与!</p>
  </div>

  <div data-role="footer">
  <h3><a href="${ctx }/index-m.jsp" style="text-decoration: none;" rel="external">调问网</a> 提供支持</h3>
  </div>
</div> 

<%@ include file="/WEB-INF/page/layouts/other.jsp"%>

</body>
</html>