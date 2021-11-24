<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${ctx }/js/plugs/jquery-ui-1.10.3.custom/css/mycss/jquery-ui-1.10.3.custom.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="${ctx }/js/plugs/jquery-ui-1.10.3.custom/js/jquery-1.10.1.js"></script>
	<script type="text/javascript" src="${ctx }/js/plugs/jquery-ui-1.10.3.custom/js/jquery-ui-1.10.3.custom.js"></script>
	<script type="text/javascript" src="${ctx }/js/plugs/colpick-jQuery/js/colpick.js"></script>

	<style type="text/css">
		.btn-success {
			color: #fff;
			background-color: #67646b;
			border-color: #67646a;
			border-radius: 0px;
			color: #423f3f;
			border-style:dashed;
			background-color: #efeeee;
			border-color: #2e2d34;
		}
		.btn-success:hover{
			/*background-color: #2e2d34;*/
			color: #423f3f;
			background-color: #ffffff;
		}
		.progress{
			height: 10px;
			border-radius: 1px;
			margin-bottom: 10px;
			background: #efeaea;
		}
		.progress-bar-success {
			background-color: #4ca1de;
			height: 10px;
		}
		.fileinput-button input{
			padding-bottom: 13px;
		}
		.fileinput-button{
			padding:3px 6px;
			border-width: 1px;
		}
	</style>

	<link rel="stylesheet" href="${ctx}/js/plugs/jQuery-File-Upload-9.19.2/css/jquery.fileupload.css">
	<link rel="stylesheet" href="${ctx}/js/plugs/jQuery-File-Upload-9.19.2/css/jquery.fileupload-ui.css">
	<script src="${ctx}/js/plugs/jQuery-File-Upload-9.19.2/js/vendor/jquery.ui.widget.js"></script>
	<script src="${ctx}/js/plugs/jQuery-File-Upload-9.19.2/js/jquery.fileupload.js"></script>
	<script src="${ctx}/js/plugs/jQuery-File-Upload-9.19.2/js/jquery.iframe-transport.js"></script>

<title>系统设置</title>
<script type="text/javascript">
$(document).ready(function(){

	currentMenu("systemset");

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

	var url = '${ctx}/up/up-file.do';
	$('.fileupload').fileupload({
		url: url,
		maxFileCount:1,
		dataType: 'json',
		add: function(e, data) {
			var acceptFileTypes = /(\.|\/)(jpg|jpeg|png)$/i;
			//文件类型判断
			if(!acceptFileTypes.test(data.originalFiles[0].name)) {
				alert("上传文件类型不对，请上传图片文件！");
				return ;
			}
			data.submit();
		},
		change: function (e, data) {
			//alert("change:"+data.files.length);
			/*
			 $.each(data.files, function (index, file) {
			 alert('Selected file: ' + file.name);
			 console.log(file);
			 });
			 */
		},
		done: function (e, data) {
			console.debug(data);
				// alert(JSON.stringify(data));
//				alert(data);
//				"{\"success\":\"true\",\"filename\":\"粉刷.jpeg  \",\"location\":\"/file/images/粉刷.jpeg\"}"
			var location = null;
			var fileName = null;
			var httpResult = data.result;
			console.debug(httpResult);
			var resultCode = httpResult.resultCode;
			var resultData = httpResult.data;
			console.debug(resultCode);
			if(resultCode==200){
				//实际只会有一条
				$.each(resultData,function (i,item){
					location = item.location;
					fileName = item.filename;
				});
				$("#loginBgImgSrc").attr("src","${ctx}"+ location);
				$("#loginBgImgSrc").show();
				$("#loginBgfileImgPath").val(location);
			}else{
				alert(httpResult.resultMsg);
			}

		},
		progressall: function (e, data) {
			var upImgItem = $(this).parents(".upImgItem");
			var progress = parseInt(data.loaded / data.total * 100, 10);
			upImgItem.find('.progress').show();
			upImgItem.find('.progress .progress-bar').css(
					'width',
					progress + '%'
			);
			upImgItem.find('.progressAfter').remove();
			upImgItem.find('.progress').after("<div class='progressAfter' style='font-size: 14px;color: #189ad0;margin-top: 10px;'>上传进度："+progress+"%</div>");
		}
	}).prop('disabled', !$.support.fileInput).parent().addClass($.support.fileInput ? undefined : 'disabled');
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

				<form id="inputForm" action="${ctx }/sy/system/sys-property/save.do" method="post" >
				<div class="surveyCollectMiddleContent">
					<div style="padding: 25px 45px;overflow: auto;padding-top: 12px;">

							<div style="padding: 5px;color: #666565;letter-spacing: 2px;font-size: 18px;">
								系统设置
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
												<%-- <tr>
													<td width="80" align="right" valign="top">统计代码</td>
													<td class="ac-input-td"><textarea name="tongjiCode" rows="3" cols="25" style="border-radius: 2px;border: 1px solid #B2B2B2;font-size: 18px;color: #727779;width: 320px;padding: 5px;outline: none;" >${tongjiCode }</textarea> </td>
												</tr> --%>
											</table>
										</td>
										<td valign="top" align="left" >
											<table class="ac-form-table">
												<tr>
												<td width="80" align="right" valign="top">登录背景图</td>
													<td class="ac-input-td">
														<div class="upResImg">
															<div class="upImgItem">
																<!-- The fileinput-button span is used to style the file input field as button -->
																<span class="btn btn-success fileinput-button">
																	<span>上传文件</span>
																	<input class="fileupload" type="file" name="file" multiple >
																	<%--<input class="fileuploadPath" type="hidden" upHidInputName="qu_${en.quType }_${en.id }" >--%>
																</span>
																<!-- The global progress bar -->
																<div class="progress" style="display: none;margin-top: 10px;">
																	<div class="progress-bar progress-bar-success"></div>
																</div>
																<!-- The container for the uploaded files -->
																<div class="files"></div>
															</div>
															<%--
					                                		<span id="loginBgimgfilename"></span>
					                                		<input type="file" name="loginBgfile" id="fileupload" data-url="${ctx}/up/up-file-wb.do" multiple />
					                                		<span class="uploadifySpan" style="line-height:26px;"><br/>请上传超清1024*968图片！</span>
					                                		<div id="loginBgfileQueueid" ></div>
															--%>
														</div>
														<div>点击选择背景图或上传自定义的背景图</div>
														<div id="login_bg_div">
															<img src="${ctx }/images/style-model/login_bg/1.jpg" style="opacity: 1;" width="60" >
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
