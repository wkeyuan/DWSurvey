<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="${ctx }/js/common/list.js" ></script>
<script type="text/javascript">
	$(document).ready(function(){
		//currentMenu(4);
		currentMenu2(6,1);
		// addModel  addTrAfterTag
	});
</script>
</head>
<body>
	<div id="m-content">
			<div class="content-right-title-admin">
						<a href="">首页</a>&nbsp;-&nbsp;
						<a href="">管理中心</a>&nbsp;-&nbsp;
						<a href="">问卷管理</a>
			</div>
			<div class="m-content-menu">
				<%@ include file="group-left.jsp" %>
			<div style="clear: both;"></div>
			</div>
			 <%-- <%@ include file="/WEB-INF/page/layouts/left-admin.jsp"%> --%>
			<div class="m-content-body">
			<%-- <div class="m-content-left-admin">
					<%@ include file="/WEB-INF/page/group/group-left.jsp" %>
			</div> --%>
			<div class="m-content-right" style="width:100%;">
					<div class="m-content-right-body">
						<div class="rightbody-tools">
								<a href="${ctx }/sy/system/nosm/sys-email!input.action" class="btn001 click_a" title="新建文件夹">+&nbsp;新SMTP服务器</a>&nbsp;&nbsp;
						</div>
						
						<form id="listForm" action="${ctx }/sy/ugroup/user-group.action?page.pageNo=${page.pageNo }" method="post">
						<div class="content-list">
							<ul class="catalog_path">
			            		<li><a href="${ctx }/sy/user/survey-directory.action">全部</a></li>
			            	<c:forEach items="${paths }" var="en">
			            		<li><span class="catalogsplit_span">&gt;</span>
			            			<a href="${ctx }/sy/user/${en.dirType eq 1?'survey-directory':'survey-question' }.action?parentId=${en.id}">${en.surveyName }</a>
			            		</li>
			            	</c:forEach>
			            	</ul>
							<table id="content-tableList" width="100%"  cellpadding="0" cellspacing="0">
								<tr id="addTrAfterTag">
									<th width="5%" align="left"><input type="checkbox" name="trId" /> </th>
									<th align="left">发信人地址</th>
									<th width="10%"></th>
									<th width="18%">创建时间</th>
								</tr>
								<c:forEach items="${page.result }" var="en">
								<tr>
									<td align="left"><input type="checkbox" name="trId" value="${en.id }" /></td>
									<td align="left">${en.stmpUser }</td>
									<td align="left"><span class="hoverShow"><a href="${ctx }/sy/system/nosm/sys-email!input.action?id=${en.id}" class="click_a">编辑</a></span></td>
									<td><fmt:formatDate value="${en.createDate }" pattern="yyyy-MM-dd HH:mm"/> </td>
								</tr>
								</c:forEach>
							</table>
							
							<div class="contentlistPage">
								<!-- <a href="" class="btn001 disabled">&lt;</a> -->
								<c:if test="${page.pageNo > 1}">
									<a href="${ctx }/sy/ugroup/user-group.action?page.pageNo=${page.pageNo-1}" class="btn04 pageNoA">&lt;</a>
								</c:if>
								<c:if test="${page.startpage > 1}">
									<a href="${ctx }/sy/ugroup/user-group.action?page.pageNo=1" class="btn04 pageNoA">1</a>
									<c:if test="${page.startpage > 2 }">
										<span>...</span>
									</c:if>
								</c:if>
								<c:forEach begin="${page.startpage }" end="${page.endpage }" var="en">
									<c:choose>
										<c:when test="${page.pageNo eq en }"><a href="${ctx }/sy/ugroup/user-group.action?page.pageNo=${en }" class="btn04 focus pageNoA">${en }</a></c:when>
										<c:otherwise><a href="${ctx }/sy/ugroup/user-group.action?page.pageNo=${en}" class="btn04 pageNoA">${en }</a></c:otherwise>
									</c:choose>
								</c:forEach>
								<c:if test="${page.totalPage > (page.endpage)}">
									<c:if test="${page.totalPage > (page.endpage+1)}">
										<span>...</span>
									</c:if>
									<a href="${ctx }/sy/ugroup/user-group.action?page.pageNo=${page.totalPage}" class="btn04 pageNoA">${page.totalPage }</a>
								</c:if>
								<c:if test="${page.totalPage > page.pageNo}">
									<a href="${ctx }/sy/ugroup/user-group.action?page.pageNo=${page.pageNo+1}" class="btn04 pageNoA">&gt;</a>
								</c:if>
							</div>
						</div>
						</form>
						
					</div>
			</div>
			</div>
	</div>
	
	<table id="addModel" style="display: none;">
		<tbody>
			<tr class="trModel curtd">
					<td align="left"><input type="checkbox" name="trId" value="${en.id }" /></td>
					<td align="left"><input type="text"  name="userGroupName" value="组名称"/> <a href="" class="btn002 saveUserGroup">保存</a>&nbsp;&nbsp;<a href="" class="btn004 cancelUserGroup">取消</a> </td>
					<td align="left"><span class="hoverShow"><a href="">编辑</a></span></td>
					<td><fmt:formatDate value="${en.createTime }" pattern="yyyy-MM-dd HH:mm"/> </td>
			</tr>
		</tbody>
	</table>
	
	<!-- 新窗口要用的参数 -->
	<input type="hidden" name="curEnId" value="" />
	<input type="hidden" name="parentId" value="${param['parentId']}" />
	
	<!-- 页面辅助参数 -->
	 <input type="button" id="closeDailogBtn" style="display:none;" />
	<input type="hidden" id="isrefHid" value="0" />
	
	<input type="button" id="upDailogTitleBtn" style="display:none;" />
	<input type="hidden" id="newDailogTitle" value="" />
	
	<div id="newDialog">
		<iframe id="newDialogFrame" width="620" height="380"  frameborder="0" ></iframe>
	</div>
</body>
</html>