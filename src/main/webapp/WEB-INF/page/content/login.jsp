<%@ page import="net.diaowen.common.plugs.footer.FooterInfo" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
 <%@include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>调问网-专业的问卷表单系统</title>
<meta name="keywords" content="调问开源问卷表单,DWSurvey，调查问卷，问卷调查系统，调查问卷设计，调查问卷格式，大学生问卷，在线问卷，表单系统" />
<meta name="description" content="调问问卷表单－开源且专业的调研系统" />

<script type="text/javascript" src="${ctx }/js/plugs/jquery-ui-1.10.3.custom/js/jquery-1.10.1.js"></script>
<script type="text/javascript" src="${ctx }/js/dw/responsive-width.js"></script>
<script type="text/javascript" src="${ctx }/js/plugs/jquery-ui-1.10.3.custom/js/jquery-ui-1.10.3.custom.js"></script>
<link href="${ctx }/css/dw-user.css" rel="stylesheet" type="text/css" />

<script src="${ctx}/js/plugs/jquery/jquery.cookie.js" type="text/javascript"></script>
<link href="${ctx}/js/plugs/validate/jquery.validate.css" type="text/css" rel="stylesheet" />
<script src="${ctx}/js/plugs/validate/jquery.validate.js" type="text/javascript"></script>
<script src="${ctx}/js/plugs/validate/messages_cn.js" type="text/javascript"></script>
<script src="${ctx }/js/plugs/validate/jquery.metadata.js" type="text/javascript"></script>
<link rel="icon" href="/favicon.ico" type="image/x-icon" />
<script type="text/javascript">
	$(document).ready(function(){

		var loginFormValidate=$("#loginForm").validate({
			rules:{
				username:{required:true,email:true,minlength:3,maxlength:60},
				//email:{required:true,email:true,maxlength:60},
				password:{required:true,minlength:6,maxlength:40}
			},
			messages: {
				username: {
		    		minlength:"不能少于3个字符！",
					maxlength:"不能超过60个字符！",
					email: "邮箱格式不正确！",
					required: "登录名不能为空！"
				},
				password:{
					required: "密码不能为空！"
		    	}
			},
			errorPlacement: function(error, element) {
			    //error.appendTo(element.parent().parent());
				element.parent().append(error);
			//	$(element).css("borderColor","#C40000");
			}
		});

		var ckCodeError=$.cookie("registerError");
		if(ckCodeError!=null && ckCodeError=="0"){
			$("#register-error").text("验证码不正确!");
			$.removeCookie('registerError',{path:'/'});
		}

	});
</script>
<style type="text/css">
label.error{
	display: block;
	margin-top: 3px;
}
#header{
	opacity:0.9;
}
#dwLoginContent{
	opacity:0.95;
}
</style>
</head>
<body style="background-color: #537088;">
<%@ include file="../layouts/loginbgimg.jsp"%>
<div id="wrap" class="wrapLogin">
	<input type="hidden" id="id" name="id" value="${survey.id }">
	<input type="hidden" id="ctx" value="${ctx }">
	<div id="header" >
		<div id="headerCenter"  class="bodyCenter" style="position: relative;">
			<div class="header_Item header_logo">
				<%@ include file="../layouts/logo-img.jsp"%>
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

	<div style="clear: both;"></div>
	<div id="dwBody" >
		<div id="dwBodyContent" class="bodyCenter" style="background: none;">

					<div class="dw_login_logo">
						<h1 class="f1">DIAOWEN</h1>
						<h2 class="f2" style="width: 460px;">调问专业<span class="f3">、</span>高效<span class="f3">、</span>实用的调研方式</h2>
					</div>


					<shiro:guest>
					<div id="dwLoginContent">
						<div class="dlcTitleBg" ></div>
						<div class="dlcTitle" >
							<a href="#" class="dlcTitleTab crt" >登录</a>
						</div>

					<div class="loginFormContent">

						<form id="loginForm" action="${ctx }/login.do" method="post">
						<div id="loginTabContent" >
							<div id="login-error" class="errorMsg" style="color: red;padding-left: 60px;">&nbsp;
						   <c:choose>
								<c:when test="${fn:endsWith(shiroLoginFailure,'IncorrectCredentialsException')}">
									用户名或密码不正确
								</c:when>
								<c:when test="${fn:endsWith(shiroLoginFailure,'UnknownAccountException')}">
									用户名或密码不正确
								</c:when>
								<c:when  test="${fn:endsWith(shiroLoginFailure,'ExcessiveAttemptsException')}">
									${username }密码错误超过3次，请与管理员联系
								</c:when>
							</c:choose>
							</div>
							<div class="dlcLeft">
								<div class="frmItem">
									<label class="frm_label">邮箱</label>
									<div class="frm_controls">
										<input id="username" type="text" class="frm-controlM" name="username" value="" >
										<span class="frm_desc">用来登录调问网，接收到激活邮件才能完成注册</span>
									</div>
								</div>
								<div class="frmItem">
									<label class="frm_label">密码</label>
									<div class="frm_controls">
										<input type="password"  name="password" id="passwordInput"  class="frm-controlM" value="" >
										<span class="frm_desc">字母、数字或者英文符号，最短6位，区分大小写</span>
									</div>
								</div>
								<div class="frmItem"  style="display: none;">
									<label class="frm_label">验证码</label>
									<div class="frm_controls">
										<span class="auth-code" id="verifycodeImgArea">
										<input name="jcaptchaInput" type="text"  class="" style="width:100px;" autocomplete="off">
										<img id="jcaptchaImg"  class="refreshJcaptchaImg" src="${ctx }/jcap/jcaptcha.do" alt="验证码"  height="40"></span>
										<a class="refreshJcaptchaImg" href="javascript:;" style="margin-left: 5px;">换一张</a>
										<span class="frm_desc">输入下面图片的字符，不区分大小写</span>
										<p class="valid-msg fail" style="display: none;"><i>●</i><span class="msg_content">验证码错误，请重新输入</span></p>
									</div>
								</div>
								<div class="frmItem" style="display: none;padding: 3px 5px;padding-bottom: 7px;">
									<label class="frm_label">&nbsp;</label>
									<label class="frmItemLabel" ><input id="agree" name="agree" type="checkbox" checked="checked">我同意并遵守<a href="#" target="_blank">《KX调研问卷系统服务协议》</a></label>
								</div>
								<div class="frmItem" style="padding: 6px 5px;">
									<label class="frm_label">&nbsp;</label>
									<input type="submit"  value=" 登 录 " class="btnGreen" style="width: 330px;height: 45px;" />
								</div>

								<div class="frmItem" style="overflow: auto;padding: 6px 5px;">
									<label class="frm_label">&nbsp;</label>
									<!-- <label class="frmItemLabel" style="float: left;">
										<input id="agree" name="agree" type="checkbox" checked="checked" style="color: color: #777;;">
									</label> -->
									<span class="ztagCheckbox checkedTrue">
									<%--<input id="c0" type="checkbox" name="rememberMe" value="true" checked="checked"></span>--%>
									<%--<a class="dw_style_a_1" tabindex="3" href="#">记住密码</a>--%>
									<%--<a class="dw_style_a_1" href="${ctx }/pwd/findPwd.jsp" target="_blank" style="float: right;">忘记密码</a>--%>
								</div>
								<!--
								<div class="frmItem" style="padding: 10px 5px;">
									<label for="" class="frm_label">&nbsp;</label>
									还没有账号&nbsp;&nbsp;<a href="">免费注册</a>
								</div> -->
						</div>
						<div class="dlcRight">
							<%@ include file="../layouts/admin-info.jsp"%>
						</div>
						</div>
						</form>

					</div>
			</div>
			</shiro:guest>
			<shiro:user>
				<div id="dwLoginContent">
					<div class="dlcTitleBg" ></div>
						<div class="dlcTitle" style="padding: 17px;" >
							提示：已登录
						</div>

					<div class="loginFormContent">
							<div>
								<div style="margin: 80px auto;">
									<div style="padding-left: 100px;font-size: 20px;">
										亲爱的&nbsp;<span style="color:#497289;"><shiro:principal></shiro:principal></span>&nbsp;您已经登陆
										<span style="margin-left: 10px;"><a href="${ctx }/design/my-survey/list.do">问卷</a>&nbsp;&nbsp;</span>
										<a href="${ctx }/logout.do">退出登录</a>
									</div>
								 </div>
								  <div>
								  	<c:if test="${!empty param['una'] }">您没有相应的权限!</c:if>
								  </div>
							  </div>
					</div>
				</div>

			</shiro:user>
		</div>
	</div>


	<div class="dw_foot" style="padding-top:20px;background: #fafafa;position: fixed;bottom: 0px;width: 100%;opacity: 0.9;">
		<div class="dw_footcopy" style="font-size: 13px;color: gray;text-align: center;">
			<p style="margin-bottom: 0px;line-height: normal;">
				<a href="<%=FooterInfo.getWebInfoSiteUrl() %>" style="text-decoration: none;color: #333;"><strong><%=FooterInfo.getWebInfoSiteName() %></strong></a>
				<a href="/" style="text-decoration: none;color: #333;"><%=FooterInfo.getWebInfoSiteICP() %></a>
				<%=FooterInfo.getWebInfoSiteMail() %>
			</p>
		</div>
		<!-- 必须保留声明 start -->
		<div class="footer-copyright" style="color: #666;font-size: 13px;padding-top: 6px;margin:0px auto;">
			Powered by <a href="http://www.diaowen.net" style="text-decoration: none;color: #333;"><strong>DWSurvey</strong></a>
			© 2012-2022 <a href="http://www.diaowen.net" style="text-decoration: none;color: #333;"><strong>调问网</strong></a>
		</div>
		<!-- 必须保留声明 end -->
	</div>

	</div>
<script type="text/javascript">
resizeBodyWidth();
$(".dlcTitleBg").animate({opacity:0.6},0);
$("a").attr("hidefocus",true);
</script>

<script type="text/javascript">

$(document).ready(function(){

	 var top=$(window).height()/2-320/2;
	 var left=$(window).width()/2-550/2;
	 //var A=window.open(url,"TencentLogin","top="+top+",left="+left+",width=550,height=320,menubar=0,scrollbars=1,resizable=1,status=1,titlebar=0,toolbar=0,location=1");

});
</script>
</body>
</html>
