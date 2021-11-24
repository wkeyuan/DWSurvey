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
                                <div class="seli"><a class="nx-2" href="${ctx }/design/my-survey-design/previewDev.do?surveyId=${surveyId}">样式设置</a></div>
                                <div class="seli"><a class="nx-6 sur_edit" href="${ctx }/design/my-survey-design/survey.do?surveyId=${surveyId}">问卷编辑</a></div>
                            </div>
                        </div>
                    </div>
                </div>
            </li>
            <li><span class="csscStep csscStepLine"><span class="csscStepLeft">&nbsp;</span><span class="csscStepRight">&nbsp;</span></span></li>
            <li><a href="${ctx }/design/my-collect.do?surveyId=${surveyId }"  class="clickHideMenu csscStep csscStep5"><span class="csscStepLeft">&nbsp;</span><span class="csscStepCenter">数据收集</span><span class="csscStepRight" >&nbsp;</span></a>
                <div class="a-w-sel">
                    <div class="w-sel" style="margin-top: 4px;">
                        <div class="selc">
                            <div class="selcc tbtag">
                                <div class="seli"><a class="nx-1" href="${ctx }/collect/my-collect/collect.do?surveyId=${surveyId}">答卷地址</a></div>
                                <div class="seli"><a class="nx-2" href="${ctx }/collect/my-collect/collect.do?surveyId=${surveyId}&tabId=share">社交分享</a></div>
                                <div class="seli"><a class="nx-3" href="${ctx }/collect/my-collect/collect.do?surveyId=${surveyId}&tabId=sitecomp">网站组件</a></div>
                                <div class="seli"><a class="nx-3" href="${ctx }/collect/my-collect/collect.do?surveyId=${surveyId}&tabId=weixin">微信收集</a></div>
                            </div>
                        </div>
                    </div>
                </div>
            </li>
            <li><span class="csscStep csscStepLine"><span class="csscStepLeft">&nbsp;</span><span class="csscStepRight">&nbsp;</span></span></li>
            <li><a href="${ctx }/da/survey-report/defaultReport.do?surveyId=${surveyId}"  class="clickHideMenu csscStep csscStep6 active"><span class="csscStepLeft">&nbsp;</span><span class="csscStepCenter">数据分析</span><span class="csscStepRight">&nbsp;</span></a>
            </li>
        </ul>
    </div>
</div>

<div style="">
    <div class="main-tabs-content bodyCenter">
        <div class="tab-content">
            <div class="tab-content-collectTab">
                <a href="${ctx }/da/survey-report/defaultReport.do?surveyId=${surveyId}&tabId=def" id="defMenu" class="collectTab tabItem_1 active"><span class="collectTabItemLeft">&nbsp;</span><span>默认报告</span></a>
                <a href="${ctx }/da/my-survey-answer/answer.do?surveyId=${surveyId}&tabId=answer" id="answerMenu" class="collectTab tabItem_3"><span class="collectTabItemLeft">&nbsp;</span><span>原始数据</span></a>
                <a href="#" class="collectTab tabItem_3" style="display: none;"><span class="collectTabItemLeft">&nbsp;</span><span>问卷日志</span></a>
            </div>
        </div>
    </div>
</div>


<input type="hidden" id="collect_tabId" value="${param.tabId}" />

<script type="text/javascript">
    var tabId = $("#collect_tabId").val();
    if(tabId!="") {
        $(".collectTab").removeClass("active")
        $("#" + tabId + "Menu").addClass("active");
    }
</script>
