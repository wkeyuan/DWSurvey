<%--
  Created by IntelliJ IDEA.
  User: keyuan
  Date: 2018/8/14
  Time: 下午4:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/common/taglibs.jsp" %>
<div class="creatgeSurveyStepBody">
    <div class="creatgeSurveyStepContent bodyCenter">
        <ul class="createSsUl">
            <li><a href=""  class="clickHideMenu csscStep csscStep4"><span class="csscStepLeft">&nbsp;</span><span class="csscStepCenter">设计问卷</span><span class="csscStepRight">&nbsp;</span></a>
                <div class="a-w-sel">
                    <div class="w-sel" style="margin-top: 4px;">
                        <div class="selc">
                            <div class="selcc tbtag">
                                <div class="seli"><a class="nx-1 sur_collectSet" href="#collectSet">收集规则</a></div>
                                <div class="seli"><a class="nx-6 sur_edit" href="${ctx }/design/my-survey-design/survey.do?surveyId=${surveyId}">问卷编辑</a></div>
                            </div>
                        </div>
                    </div>
                </div>
            </li>
            <li><span class="csscStep csscStepLine"><span class="csscStepLeft">&nbsp;</span><span class="csscStepRight">&nbsp;</span></span></li>
            <li><a href="${ctx }/collect/my-collect/collect.do?surveyId=${surveyId }"  class="clickHideMenu csscStep csscStep5 active"><span class="csscStepLeft">&nbsp;</span><span class="csscStepCenter">数据收集</span><span class="csscStepRight" >&nbsp;</span></a>
            </li>
            <li><span class="csscStep csscStepLine"><span class="csscStepLeft">&nbsp;</span><span class="csscStepRight">&nbsp;</span></span></li>
            <li><a href="${ctx }/da/survey-report/defaultReport.do?surveyId=${surveyId}"  class="clickHideMenu csscStep csscStep6"><span class="csscStepLeft">&nbsp;</span><span class="csscStepCenter">数据分析</span><span class="csscStepRight">&nbsp;</span></a>
                <div class="a-w-sel">
                    <div class="w-sel" style="margin-top: 4px;margin-left: 15px;">
                        <div class="selc">
                            <div class="selcc tbtag">
                                <div class="seli"><a class="nx-1" href="${ctx }/da/survey-report/defaultReport.do?surveyId=${surveyId}">统计表格</a></div>
                            </div>
                        </div>
                    </div>
                </div>
            </li>
        </ul>
    </div>
</div>

<div style="">
    <div class="main-tabs-content bodyCenter">
        <div class="tab-content">
            <div class="tab-content-collectTab">
                <a href="${ctx }/collect/my-collect/collect.do?surveyId=${surveyId}&tabId=def" id="defMenu" class="collectTab tabItem_1 active"><span class="collectTabItemLeft">&nbsp;</span><span>答卷地址</span></a>
                <a href="${ctx }/collect/my-collect/collect.do?surveyId=${surveyId}&tabId=share" id="shareMenu" class="collectTab tabItem_4" ><span class="collectTabItemLeft">&nbsp;</span><span>社交分享</span></a>
                <a href="${ctx }/collect/my-collect/collect.do?surveyId=${surveyId}&tabId=sitecomp" id="sitecompMenu" class="collectTab tabItem_5"><span class="collectTabItemLeft">&nbsp;</span><span>网站组件</span></a>
                <%-- <a href="${ctx }/design/my-collect.action?surveyId=${surveyId}&tabId=iframe" class="collectTab tabItem_6"><span class="collectTabItemLeft">&nbsp;</span><span>IFrame嵌入</span></a> --%>
                <a href="${ctx }/collect/my-collect/collect.do?surveyId=${surveyId}&tabId=weixin" id="weixinMenu"  class="collectTab tabItem_2"><span class="collectTabItemLeft">&nbsp;</span><span>微信收集</span></a>
            </div>
        </div>
    </div>
</div>

<input type="hidden" id="collect_tabId" value="${param.tabId}" />

<script type="text/javascript">
    var tabId = $("#collect_tabId").val();
    if(tabId!=""){
        $(".collectTab").removeClass("active")
        $("#"+tabId+"Menu").addClass("active");
    }
</script>
