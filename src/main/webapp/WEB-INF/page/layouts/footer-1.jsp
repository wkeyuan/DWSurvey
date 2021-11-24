<%@ page import="net.diaowen.common.plugs.footer.FooterInfo" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<div class="dw_footcopy" style="font-size: 13px;color: gray;text-align: center;">
    	<p style="margin-bottom: 6px;">
			<a href="<%=FooterInfo.getWebInfoSiteUrl() %>" style="text-decoration: none;color: #333;"><strong><%=FooterInfo.getWebInfoSiteName() %></strong></a>
			<a href="/" style="text-decoration: none;color: #333;"><%=FooterInfo.getWebInfoSiteICP() %></a>
			<%=FooterInfo.getWebInfoSiteMail() %>
    	</p>
    </div>
