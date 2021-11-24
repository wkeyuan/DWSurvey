<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<%@include file="other.jsp"%>
<div id="header" >
	<div id="headerCenter"  class="bodyCenter" style="position: relative;">
		<div class="header_Item header_logo">
			<%--
			<a href="${ctx }/">
				<img alt="调问网" src="${ctx }/images/logo/LOGO.png" >
			</a>
			--%>
			<%@ include file="logo-img.jsp"%>
		</div>
		<shiro:guest>
			<div class="header_Item header_menu">
				<ul>

				</ul>
			</div>
			<div class="header_Item header_user" style="float: right;">
				<a href="${ctx }/login.jsp" class="btn-a-1">登录</a>
			</div>
		</shiro:guest>

		<shiro:user>
			<div class="header_Item header_menu">
				<ul>
						<%-- <li><a href="${ctx }/" >首页</a></li> --%>
					<li><a href="${ctx }/design/my-survey/list.do" id="mysurvey">我的问卷</a></li>
					<shiro:hasRole name="admin" >
					<li><a href="${ctx }/sy/user/user-admin/list.do" id="usermanager">用户管理</a></li>
					</shiro:hasRole>
					<li><a href="http://www.diaowen.net/">帮助</a></li>
				</ul>
			</div>
			<div class="header_Item header_user" style="float: right;margin-top: 8px;position:absolute;zoom: 1;z-index: 365;display: inline;right: 0px;">
				<a href="#" class="clickHideUserMenu">
						<span class="head_use_name">
						<shiro:principal></shiro:principal>
							<i class="fa fa-caret-down" aria-hidden="true"></i>
						</span>
						<%--<span class="header_user_icon">&nbsp;&nbsp;&nbsp;</span>--%>
				</a>
				<div class="a-w-sel a-w-sel-head" style="">
					<div class="w-sel" style="margin-top: 16px;">
						<div class="selc">
							<div class="selcc tbtag">
								<div class="seli"><a class="nx-1" href="${ctx }/ic/user//myaccount.do">修改密码</a></div>
								<div class="seli"><a class="nx-7" href="http://www.diaowen.net/">帮助及反馈</a></div>
								<div class="seli"><a class="nx-8" href="${ctx }/logout.do">退出</a></div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</shiro:user>
	</div>
	<div style="clear: both;"></div>
</div>
