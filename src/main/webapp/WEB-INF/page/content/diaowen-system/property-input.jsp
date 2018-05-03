<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${ctx }/js/plugs/jquery-ui-1.10.3.custom/css/mycss/jquery-ui-1.10.3.custom.css" rel="stylesheet" type="text/css" />
<title>系统设置</title>
<link href="${ctx }/js/plugs/uploadify-v3.1/uploadify.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx }/js/plugs/uploadify-v3.1/jquery.uploadify-3.1.js"></script>
<script type="text/javascript" src="${ctx }/js/dw/uploadify.js"></script>
<script type="text/javascript">
$(document).ready(function(){

	currentMenu("systemset");

	binduploadImg("loginBgfile","loginBgfileQueueid","loginBgfileImgPath","上传");
	
	$("#login_bg_div img").hover(function(){
		$(this).addClass("active_img_hover");
	},function(){
		$(this).removeClass("active_img_hover");
	});
	
	$("#login_bg_div img").click(function(){
		$("#login_bg_div img").removeClass("active_img");
		$(this).addClass("active_img");
		$("input[name='loginBgImg']").val($(this).attr("src").replace("${ctx}",""));
	});
	
	/* $("#useLogo_checked").change(function(){
		return false;
	}); */
	
	$("#login_bg_div img").removeClass("active_img");
	$("#login_bg_div img[src='${ctx}${loginBgImg}']").addClass("active_img");
});

	
</script>
<style type="text/css">
.ac-input-td input,.ac-input-td select{
	padding: 4px! important;
	font-size: 14px;
}
.red-color{
	color: red;
}
#login_bg_div img{
	padding: 6px;
	border: 1px solid transparent;
	cursor: pointer;
}
.active_img,.active_img_hover{
	border: 1px solid #43AB75! important;
	padding: 6px;
}
</style>
</head>
<body>
	<input type="hidden" id="id" name="id" value="${survey.id }">
	
	<div style="margin-top: 15px;">
	</div>
	<div style="clear: both;"></div>
	<div id="dwBody" >
		<div id="dwBodyContent" class="bodyCenter" style="">
		
		<div id="dwBodyUser">
			<div class="surveyCollectMiddle">
				
				<form id="inputForm" action="${ctx }/sy/system/sys-property!save.action" method="post" >
				<div class="surveyCollectMiddleContent">
					<div style="padding: 25px 45px;overflow: auto;padding-top: 12px;">
							
							<div style="padding: 5px;color: #666565;letter-spacing: 2px;font-size: 18px;">
								系统设置
							<%--&nbsp;&nbsp;|&nbsp;&nbsp;
							 <a href="${ctx }/sy/user/nosm/user-admin!input.action" class="a-style-1" title="新用户">新账号</a> --%>
								<%-- <a href="${ctx }/sy/user/user-admin.action" class="a-style-1" title="新用户">邮件设置</a> --%>
								<%-- <a href="${ctx }/sy/user/user-admin.action" class="a-style-1" title="新用户">数据备份</a>
								&nbsp;&nbsp;|&nbsp;&nbsp; --%>
								<%-- <a href="${ctx }/sy/user/user-admin.action" class="a-style-1" title="新用户">系统日志</a>
								&nbsp;&nbsp;|&nbsp;&nbsp;
								<a href="${ctx }/sy/user/user-admin.action" class="a-style-1" title="新用户">激活</a> --%>
							</div>
							
							<div style="padding: 5px;color:#666565;border: 1px solid #DFDFDF;border-radius: 5px;margin-top: 15px;">
								
								
								<table width="100%">
									<tr>
										<td valign="top" align="left" width="500">
											<table class="ac-form-table">
												<tr>
													<td width="80" align="right">管理员邮箱</td>
													<td class="ac-input-td"><input type="text" name="adminEmail" value="${adminEmail }" /> </td>
												</tr>
												<tr>
													<td width="80" align="right">电话</td>
													<td class="ac-input-td"><input type="text" name="adminTelephone" value="${adminTelephone }" /> </td>
												</tr>
												<tr>
													<td width="80" align="right" >备案代码</td>
													<td class="ac-input-td"><input type="text" name="icpCode" value="${icpCode }"> </td>
												</tr>
												<%--
												<tr style="display: none;">
													<td width="80" align="right" >内容版权</td>
													<td class="ac-input-td"><input type="text" name="contentCopyright" value="${contentCopyright }" ></td>
												</tr>
												<tr><td>&nbsp;</td><td class="ac-input-td" valign="top" style="padding-top: 0px;">所属机构名称如：调问网 </td></tr>--%>
											</table>
										</td>
										<td valign="top" align="left" >
											<table class="ac-form-table">
												<tr>
												<td width="80" align="right" valign="top">登录背景图</td>
													<td class="ac-input-td">
														<div class="upResImg">
					                                	<%-- <input type="hidden" name="loginBgImgPath" value="${fileImgPath }" /> --%>
					                                	<span id="loginBgimgfilename"></span>
					                                	<input type="file" id="loginBgfile" name="loginBgfile" />
					                                	<span class="uploadifySpan" style="line-height:26px;"><br/>请上传超清1024*968图片！</span>
					                                	<div id="loginBgfileQueueid" ></div>
														</div>
														<div>点击选择背景图或上传自定义的背景图</div>
														<div id="login_bg_div">
															<img class="active_img" src="${ctx }/images/style-model/login_bg/1.jpg" style="opacity: 1;" width="60" >
															<img src="${ctx }/images/style-model/login_bg/2.jpg" style="opacity: 1;" width="60" >
															<img src="${ctx }/images/style-model/login_bg/3.jpg" style="opacity: 1;" width="60" >
															<img src="${ctx }/images/style-model/login_bg/4.jpg" style="opacity: 1;" width="60" >
															<img src="${ctx }/images/style-model/login_bg/5.jpg" style="opacity: 1;" width="60" >
															<img src="${ctx }/images/style-model/login_bg/6.jpg" style="opacity: 1;" width="60" >
															<img src="${ctx }/images/style-model/login_bg/7.jpg" style="opacity: 1;" width="60" >
															<c:choose>
																<c:when test="${fn:startsWith(loginBgImg, '/file/images') }"><img src="${ctx }${loginBgImg }" style="opacity: 1;" width="60" id="loginBgImgSrc" ></c:when>
																<c:otherwise>
																<img src="${ctx }" style="opacity: 1;display: none;" width="60" id="loginBgImgSrc" >
																</c:otherwise>
															</c:choose>
															<input type="hidden" id="loginBgfileImgPath" name="loginBgImg" value="${loginBgImg }" >
														</div>
													</td>
												</tr>
												
											</table>
										</td>
									</tr>
									<tr>
										<td height="50">
											<input type="submit" value="保存修改" class="sbtn25 sbtn25_0" style="margin-left: 125px;">
											<input type="button" value="放弃修改" class="sbtn24 sbtn24_1" style="margin-left: 35px;">
										</td>
										<td class="ac-input-td"> </td>
									</tr>
								</table>
							</div>
							
					</div>
				</div>
				</form>
				
			</div>
			
		</div>
		</div>
	</div>
</body>
</html>