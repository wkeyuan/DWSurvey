<%@ page import="net.diaowen.common.plugs.footer.FooterInfo" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
 <%@include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>${survey.surveyName} 调问网</title>
<link rel="stylesheet" href="${ctx }/js/plugs/jquery.mobile-1.4.5/jquery.mobile-1.4.5.min.css">
<script type="text/javascript" src="${ctx }/js/plugs/jquery-ui-1.10.3.custom/js/jquery-1.10.1.js"></script>
<script src="${ctx }/js/plugs/jquery.mobile-1.4.5/jquery.mobile-1.4.5.min.js"></script>
<link href="${ctx }/js/plugs/font-awesome-4.2.0/css/font-awesome.css" rel="stylesheet">
<style type="text/css">
    .ui-header{
        padding: 0px;
        margin: 0px;
        border: none;
    }
    .ui-page .dw_m_header h1 {
        margin: 0;
        padding: 0;
        font-size: 24px;
        line-height:56px;
        text-shadow: 0 1px 0 #281212;
        color: rgb(211, 178, 49);
    }
    .ui-content{
        padding: 0px;
        border: none;
        margin: 0px;
    }
    .dw-m-content1{
        text-align: center;
        font-size: 22px;
        line-height: 46px;
        padding: 56px 0px;
        /*border-bottom: 1px solid #e0e0e0;*/
    }
    .dw-m-content1 p{
        font-size: 16px;
        line-height: 22px;
    }
</style>
</head>
<body>

<div data-role="page" style="background: rgb(243, 245, 237);">
    <div data-role="header" class="dw_m_header" style="background:rgb(245, 245, 245);">
        <%--<h1 style="color:rgb(121, 197, 234);text-shadow:none;font-weight: normal;" >
            &lt;%&ndash;<img alt="" src="http://www.diaowen.net/images/logo/200-200.png" style="vertical-align:middle;margin-top: -6px;" height="60">&ndash;%&gt;
            调 问 网
        </h1>--%>
    </div>

    <div data-role="main" class="ui-content">
        <div class="middle-body-content" style="text-align: center;padding: 50px 10px;background: white;margin: 60px 10px;margin-bottom: 20px;">
            <c:choose>
                <c:when test="${redType eq 1}">
                    <h3 style="color: rgb(62, 147, 158);">${survey.surveyName}</h3>
                    <p class="msg1" style="font-size: 16px;">目前该问卷已暂停收集，请稍后再试!</p>
                </c:when>
                <c:when test="${redType eq 2}">
                    <h3 style="color: rgb(62, 147, 158);">${survey.surveyName}</h3>
                    <p class="msg1" style="font-size: 16px;">目前该问卷无法访问，请稍后再试!</p>
                </c:when>
                <c:when test="${redType eq 3}">
                    <h3 style="color: rgb(248, 28, 15);">${survey.surveyName}</h3>
                    <p class="msg1" style="font-size: 16px;">答卷受到限制，提交未成功!</p>
                </c:when>
                <c:when test="${redType eq 4}">
                    <h3 style="color: rgb(248, 28, 15);">${survey.surveyName}</h3>
                    <p class="msg1" style="font-size: 16px;">验证码不正确，提交未成功!</p>
                    <p><a href="${ctx}/diaowen/${survey.sid}.html" style="text-decoration: none;color: rgb(53, 117, 136);font-weight: normal;" rel="external" >重新填写</a></p>
                </c:when>
                <c:when test="${redType eq 5}">
                    <h3 style="color: rgb(248, 28, 15);">${survey.surveyName}</h3>
                    <p class="msg1" style="font-size: 16px;">发生未知异常，提交未成功!</p>
                </c:when>
                <c:when test="${redType eq 6}">
                    <h3 style="color: rgb(62, 147, 158);">${survey.surveyName}</h3>
                    <p class="msg1" style="font-size: 16px;">答卷提交成功，感谢您的支持!</p>
                </c:when>
                <c:when test="${redType eq 7}">
                    <h3 style="color: rgb(62, 147, 158);">${survey.surveyName}</h3>
                    <p class="msg1" style="font-size: 16px;">答卷已经达到收集上限，感谢您的支持!（数据不被保存）</p>
                </c:when>
                <c:when test="${redType eq 8}">
                    <h3 style="color: rgb(62, 147, 158);">${survey.surveyName}</h3>
                    <p class="msg1" style="font-size: 16px;">问卷未到开始时间，感谢您的支持!（数据不被保存）</p>
                </c:when>
                <c:when test="${redType eq 9}">
                    <h3 style="color: rgb(62, 147, 158);">${survey.surveyName}</h3>
                    <p class="msg1" style="font-size: 16px;">问卷已经到了截止时间，感谢您的支持!（数据不被保存）</p>
                </c:when>
                <c:when test="${redType eq 10}">
                    <h3 style="color: rgb(62, 147, 158);">${survey.surveyName}</h3>
                    <p class="msg1" style="font-size: 16px;">该问卷已删除，无法作答!</p>
                </c:when>
            </c:choose>
        </div>
            <%--<div class="dw-m-content1" style="" >
                <h3 style="color: rgb(120, 171, 178);">问卷提交成功</h3>
                <p>感谢您的支持</p>
            </div>--%>
    </div>

    <%--<div data-role="footer" style="background-color: white;border-color: white;">
        <h3>Powered by <a href="http://www.diaowen.net/index-m.jsp" style="text-decoration: none;" rel="external">DWSurvey</a> </h3>
    </div>--%>
    <div class="dw_foot" style="padding-top:10px;text-align: center;">
        <div class="dw_footcopy" style="font-size: 12px;color: gray;text-align: center;">
            <p style="margin-bottom: 6px;">
                <a href="<%=FooterInfo.getWebInfoSiteUrl() %>" style="text-decoration: none;color: #333;"><strong><%=FooterInfo.getWebInfoSiteName() %></strong></a>
                <a href="/" style="text-decoration: none;color: #333;"><%=FooterInfo.getWebInfoSiteICP() %></a>
            </p>
        </div>
        <!-- 必须保留声明 start -->
        <div class="footer-copyright" style="color: #666;padding-top: 0px;font-size: 12px;padding-bottom: 20px;">
            Powered by <a href="http://www.diaowen.net" style="text-decoration: none;color: #333;"><strong>DWSurvey</strong></a>
        </div>
        <!-- 必须保留声明 end -->
    </div>

</div>


<%@ include file="/WEB-INF/page/layouts/other.jsp"%>

</body>
</html>
