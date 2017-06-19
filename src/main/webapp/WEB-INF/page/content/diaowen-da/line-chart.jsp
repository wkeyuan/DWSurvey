<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${ctx }/js/dw/collect.js"></script>

 <script src="${ctx }/js/plugs/amcharts_3.11.2.free/amcharts/amcharts.js" type="text/javascript"></script>
 <script src="${ctx }/js/plugs/amcharts_3.11.2.free/amcharts/serial.js" type="text/javascript"></script>
<script src="${ctx }/js/plugs/amcharts_3.11.2.free/amcharts/pie.js" type="text/javascript"></script>

<!-- scripts for exporting chart as an image -->
<!-- Exporting to image works on all modern browsers except IE9 (IE10 works fine) -->
<!-- Note, the exporting will work only if you view the file from web server -->
<!--[if (!IE) | (gte IE 10)]> -->
<script src="${ctx }/js/plugs/amcharts_3.11.2.free/amcharts/exporting/amexport.js" type="text/javascript"></script>
<script src="${ctx }/js/plugs/amcharts_3.11.2.free/amcharts/exporting/rgbcolor.js" type="text/javascript"></script>
<script src="${ctx }/js/plugs/amcharts_3.11.2.free/amcharts/exporting/canvg.js" type="text/javascript"></script>
<script src="${ctx }/js/plugs/amcharts_3.11.2.free/amcharts/exporting/jspdf.js" type="text/javascript"></script>
<script src="${ctx }/js/plugs/amcharts_3.11.2.free/amcharts/exporting/filesaver.js" type="text/javascript"></script>
<script src="${ctx }/js/plugs/amcharts_3.11.2.free/amcharts/exporting/jspdf.plugin.addimage.js" type="text/javascript"></script>
<!-- <![endif]-->


<link href="${ctx }/js/plugs/font-awesome-4.2.0/css/font-awesome.css" rel="stylesheet">

<title>柱状图</title>
<script type="text/javascript">
	
$(document).ready(function(){
	//chartData surveyResultQu
	$(".linechart_pic,.piechart_pic").click(function(){
		var th=$(this);
		var thClass=th.attr("class");
		var quICount=th.parents(".surveyResultQu").find("input[name='quICount']").val();
		var amchartdivId=null;
		if(thClass.indexOf("linechart_pic")>0){
			amchartdivId="line_chart_"+quICount;
		}else if(thClass.indexOf("piechart_pic")>0){
			amchartdivId="pie_chart_"+quICount;
		}
		if(amchartdivId!=null){
			var amchartdivObj=$("#"+amchartdivId);
			if(!amchartdivObj[0]){
				var resultJson=$("#statJson_"+quICount).html();
				buildPieChart(resultJson,quICount);
			}
		}
		return false;
	});
});

function buildPieChart(resultJson,quId){
	//$("#"+chartdivId).css({"margin-top":"10px"});
	var chartdivId="pie_chart_"+quId;
	$("#amchart_"+quId).prepend("<div id='"+chartdivId+"'></div>");
	$("#"+chartdivId).css({"height": "300px","width": "80%","margin":"5px"});
	
	var rows = eval("(" + resultJson  + ")");
	
	var charJsons = "[";
	$.each(rows, function(i, item) {
		//alert(item.optionName);
		var rowName = item.optionName;
		var rowCount = item.count;
		charJsons += "{country:'" + rowName + "',";
		charJsons += "visits:" + rowCount + "";
		charJsons += ",chartitle:'" + (i+1) + "'";
		charJsons = substring(charJsons);
		charJsons += "},";
	});
	charJsons = substring(charJsons);
	charJsons += "]";
	
	 var chart;
	 //var legend;
	 var chartData = eval("(" + charJsons + ")");
	 //AmCharts.ready();
	 (function() {
		// PIE CHART
			chart = new AmCharts.AmPieChart();
			chart.dataProvider = chartData;
			chart.titleField = "chartitle";
			chart.valueField = "visits";
			chart.descriptionField="country";
			chart.pathToImages="${ctx}/js/plugs/amcharts_3.11.2.free/amcharts/images/";
			// this makes the chart 3D
			chart.depth3D = 15;
			chart.angle = 30;
			chart.labelText="[[title]] -[[description]]（[[percents]]%）";
			chart.balloonText="[[title]]（[[percents]]% ）\n[[description]]([[value]])";
			chart.addTitle("分析图-by-调问网", 12);
			chart.marginTop=0;
			chart.marginLeft=0;
			chart.borderAlpha=1;
			chart.borderColor="#D9D9D9";
			chart.backgroundAlpha=0.5;
			chart.backgroundColor="white";
			chart.labelRadius=15;
			chart.radius="50%";
			chart.pieX="40%";
			
			 // LEGEND
                /* legend = new AmCharts.AmLegend();
                legend.align = "center";
                legend.markerType = "circle";
                //chart.balloonText = "[[title]]-[[description]]<br><span style='font-size:14px'><b>[[value]]</b> ([[percents]]%)</span>";
                chart.addLegend(legend); */
                
			chart.amExport = {
                    top: 21,
                    right: 21,
                    buttonColor: '#EFEFEF',
                    buttonRollOverColor:'#DDDDDD',
                    exportPNG:true,
                    exportJPG:true,
                    exportPDF:true,
                    exportSVG:true
                };
			chart.write(chartdivId);
		})();
}
	
	//统计的jsonId,图放写入的divId
	function buildChart(statJsonId,chartdivId){
		var rows = eval("(" + $("#"+statJsonId).html()  + ")");
		
		var charJsons = "[";
		$.each(rows, function(i, item) {
			//alert(item.optionName);
			var rowName = item.optionName;
			var rowCount = item.count;
			charJsons += "{country:'" + rowName + "',";
			charJsons += "visits:" + rowCount + "";
			charJsons += ",color:'#CD0D74'";
			charJsons = substring(charJsons);
			charJsons += "},";
		});
		charJsons = substring(charJsons);
		charJsons += "]";
		
		 var chart;
		 var chartData = eval("(" + charJsons + ")");

		 AmCharts.ready(function() {
				//柱状或条形
				chart = new AmCharts.AmSerialChart();
				chart.dataProvider = chartData;
				chart.categoryField = "country";
				chart.startDuration = 1;
	            chart.depth3D = 20;
	            chart.angle = 30;
	             chart.pathToImages="${ctx}/js/plugs/amcharts_3.11.2.free/amcharts/images/";
				// AXES
				// category
				var categoryAxis = chart.categoryAxis;
				categoryAxis.labelRotation = 15;
				categoryAxis.gridPosition = "start";

				// value
				var valueAxis = new AmCharts.ValueAxis();
				chart.addValueAxis(valueAxis);
		 
				// GRAPH
				var graph = new AmCharts.AmGraph();
				graph.valueField = "visits";
				graph.balloonText = "[[category]]: [[value]]";
				graph.type = "column";
				graph.lineAlpha = 0;
				graph.fillAlphas = 0.8;
				graph.fillAlphas = 1;
				graph.colorField = "color";
				chart.addGraph(graph);
				chart.marginTop=30;
				//chart.color="#0081CC";
				
				 
				chart.amExport = {
	                    top: 21,
	                    right: 21,
	                    buttonColor: '#EFEFEF',
	                    buttonRollOverColor:'#DDDDDD',
	                    exportPNG:true,
	                    exportJPG:true,
	                    exportPDF:true,
	                    exportSVG:true
	                };
				
				//chart.rotate = true;  切换为纵向
	                // WRITE
				chart.write(chartdivId);
			});
	}
	
	function substring(json) {
		var bufLen = json.length;
		var lastIndex = json.lastIndexOf(",");
		if (bufLen == (lastIndex + 1)) {
			json = json.substring(0, lastIndex);
		}
		return json;
	}
	
</script>
</head>
<body>
	<input type="hidden" id="id" name="id" value="${surveyId }">
	
	<div class="creatgeSurveyStepBody">
		<div class="creatgeSurveyStepContent bodyCenter">
				<ul class="createSsUl">
				<li><a href=""  class="clickHideMenu csscStep csscStep4"><span class="csscStepLeft">&nbsp;</span><span class="csscStepCenter">设计问卷</span><span class="csscStepRight">&nbsp;</span></a>
					<div class="a-w-sel">
		            	<div class="w-sel" style="margin-top: 4px;">
		                	<div class="selc">
		                    	<div class="selcc tbtag">
		                            <div class="seli"><a class="nx-1 sur_collectSet" href="#collectSet">收集规则</a></div>
		                            <div class="seli"><a class="nx-2" href="${ctx }/design/my-survey-design!previewDev.action?surveyId=${surveyId}">样式设置</a></div>
		                            <div class="seli"><a class="nx-6 sur_edit" href="${ctx }/design/my-survey-design.action?surveyId=${surveyId}">问卷编辑</a></div>
		                        </div>
		                    </div>
		                </div>
		            </div>
				</li>
				<li><span class="csscStep csscStepLine"><span class="csscStepLeft">&nbsp;</span><span class="csscStepRight">&nbsp;</span></span></li>
				<li><a href="${ctx }/design/my-collect.action?surveyId=${surveyId }"  class="clickHideMenu csscStep csscStep5"><span class="csscStepLeft">&nbsp;</span><span class="csscStepCenter">数据收集</span><span class="csscStepRight" >&nbsp;</span></a>
					<div class="a-w-sel">
		            	<div class="w-sel" style="margin-top: 4px;">
		                	<div class="selc">
		                    	<div class="selcc tbtag">
		                            <div class="seli"><a class="nx-1" href="${ctx }/design/my-collect.action?surveyId=${surveyId}">答卷地址</a></div>
		                            <div class="seli"><a class="nx-2" href="">社交分享</a></div>
		                            <div class="seli"><a class="nx-3" href="${ctx }/design/my-collect.action?surveyId=${surveyId}&tabId=mail">邮件邀请</a></div>
		                            <div class="seli"><a class="nx-3" href="${ctx }/design/my-collect.action?surveyId=${surveyId}&tabId=sitecomp">网站组件</a></div>
		                            <div class="seli"><a class="nx-3" href="${ctx }/design/my-collect.action?surveyId=${surveyId}&tabId=weixin">微信收集</a></div>
		                        </div>
		                    </div>
		                </div>
		            </div>
				</li>
				<li><span class="csscStep csscStepLine"><span class="csscStepLeft">&nbsp;</span><span class="csscStepRight">&nbsp;</span></span></li>
				<li><a href="${ctx }/da/survey-report!defaultReport.action?surveyId=${surveyId}"  class="clickHideMenu csscStep csscStep6 active"><span class="csscStepLeft">&nbsp;</span><span class="csscStepCenter">数据分析</span><span class="csscStepRight">&nbsp;</span></a>
				</li>
			</ul>
		</div>
	</div>
	
	<div style="">
		<div class="main-tabs-content bodyCenter">
			<div class="tab-content">
				<div class="tab-content-collectTab">
					<a href="${ctx }/design/my-collect.action?surveyId=${surveyId}" class="collectTab tabItem_1 active"><span class="collectTabItemLeft">&nbsp;</span><span>默认报告</span></a>
					<a href="${ctx }/da/my-survey-answer.action?surveyId=${surveyId}" class="collectTab tabItem_3"><span class="collectTabItemLeft">&nbsp;</span><span>原始数据</span></a>
					<a href="#" class="collectTab tabItem_3" style="display: none;"><span class="collectTabItemLeft">&nbsp;</span><span>问卷日志</span></a>
				</div>
			</div>
		</div>
	</div>
	<div style="clear: both;"></div>
	<div id="dwBody" >
		<div id="dwBodyContent" class="bodyCenter" style="border:1px solid #C1DAEC;">
		<div id="dwBodyUser">
			<div class="surveyCollectMiddle">
				
				
				<div class="surveyCollectTop">
					<div class="surveyCollectTitleDiv">
						<span class="surveyCollectTitle">${directory.surveyName }</span>
						<div class="scmTabRight" >
							<a href="" class="sbtn25 sbtn25_2">停止收集</a>
						</div>
					</div>
					<div class="surveyCollectInfoDiv">
						<span class="surveyCollectInfoLeft">
						状态：<span class="collectInfoSpan">收集中</span>&nbsp;&nbsp;&nbsp;&nbsp;
						参数人数：<span class="collectInfoSpan">${directory.answerNum }</span>
						</span>
						<span class="surveyCollectInfoRight">
						创建时间：<span class="collectInfoSpan"><fmt:formatDate value="${directory.createDate }" pattern="yyyy年MM月dd日 HH时mm分" /> </span>
						</span>
					</div>
				</div>
				
				<div class="surveyCollectMiddleContent">
					<div style="padding: 15px 25px;overflow: auto;">
							<div style="overflow: auto;">
								<div style="float: left;" >
									<a href="${ctx }/da/survey-report!defaultReport.action?surveyId=${surveyId }" class="dw_btn025 tabpic"><i class="fa fa-tasks"></i>&nbsp;表格</a>
									<a href="${ctx }/da/survey-report!lineChart.action?surveyId=${surveyId }" class="dw_btn025 linepic active" style="margin-left: 10px;"><i class="fa fa-bar-chart"></i>&nbsp;柱状图</a>
									<a href="${ctx }/da/survey-report!pieChart.action?surveyId=${surveyId }" class="dw_btn025 piepic " style="margin-left: 10px;"><i class="fa fa-pie-chart"></i>&nbsp;饼状图</a></div>
								<div style="float: right;" >
									<a href="" class="dw_btn025"><i class="fa fa-download"></i>下载报告</a>
									<a href="" class="dw_btn025"><i class="fa fa-share"></i>分享</a></div>
							</div>
							<div style="padding-top:8px;">
								<div class="" style="border: 1px solid #D1D6DD;padding: 10px;">
									
									<table id="content-tableList" width="100%"  cellpadding="0" cellspacing="0">
								<c:forEach items="${surveyStats.questions }" var="en" varStatus="i">
								<tr>
									<td colspan="3">
										<div class="surveyResultQu">
										<input type="hidden" name="quICount" value="${i.count }">
							<div class="r-qu-body-title">${i.count }、${en.quTitle }[${en.quType.cnName }]</div>
							<div class="r-qu-body-content">
								<c:choose>
									<%--是非题 --%>
										<c:when test="${en.quType eq 'YESNO' }">
													<table class="suQuTable" border="0" cellpadding="0" cellspacing="0">
														<tr>
															<td width="15px">&nbsp;</td>
															<td width="520px">${en.yesnoOption.trueValue }</td>
															<td width="40px">&nbsp;</td>
														</tr>
														<tr>
															<td width="15px">&nbsp;</td>
															<td width="520px">${en.yesnoOption.falseValue  }</td>
															<td width="40px">&nbsp;</td>
														</tr>
													</table>
													<div id="statJson_${i.count }" style="display: none;">${en.statJson }</div>
													<div id="chartdivId_${i.count }" style="height: 300px;width: 80%;">&nbsp;&nbsp;统计图生成中...</div>
													<script type="text/javascript">
																buildChart("statJson_${i.count }","chartdivId_${i.count }");
													</script>
													<div style="clear:both;"></div>
										</c:when>
										<%--单选题 --%>
										<c:when test="${en.quType eq 'RADIO' }">
											<table class="suQuTable" border="0" cellpadding="0" cellspacing="0">
											<c:forEach items="${en.quRadios }" var="quEn" varStatus="quI">
														<tr>
															<td width="15px">&nbsp;</td>
															<td width="520px">${quEn.optionName }</td>
															<td width="40px">&nbsp;</td>
														</tr>
											</c:forEach>
											</table>
											<div id="statJson_${i.count }" style="display: none;">${en.statJson }</div>
											<div id="chartdivId_${i.count }" style="height: 300px;width: 80%;">&nbsp;&nbsp;统计图生成中...</div>
											<script type="text/javascript">
														buildChart("statJson_${i.count }","chartdivId_${i.count }");
											</script>
											<div style="clear:both;"></div>
											<div class="reportPic">
												<a href="#" class="dw_btn026 piechart_pic">饼状图</a>
												<div style="clear: both;"></div>
												<div id="amchart_${i.count }"></div>
											</div>
											<div style="clear:both;"></div>
										</c:when>
										<%--多选题 --%>
										<c:when test="${en.quType eq 'CHECKBOX' }">
											<table class="suQuTable" border="0" cellpadding="0" cellspacing="0">
											<c:forEach items="${en.quCheckboxs }" var="quEn" varStatus="quI">
														<tr>
															<td width="15px">&nbsp;</td>
															<td width="520px">${quEn.optionName }</td>
															<td width="40px">&nbsp;</td>
														</tr>
													</c:forEach>
											</table>
											<div id="statJson_${i.count }" style="display: none;">${en.statJson }</div>
											<div id="chartdivId_${i.count }" style="height: 300px;width: 80%;">&nbsp;&nbsp;统计图生成中...</div>
											<script type="text/javascript">
														buildChart("statJson_${i.count }","chartdivId_${i.count }");
											</script>
											<div style="clear:both;"></div>
											<div class="reportPic">
												<a href="#" class="dw_btn026 piechart_pic">饼状图</a>
												<div style="clear: both;"></div>
												<div id="amchart_${i.count }"></div>
											</div>
											<div style="clear:both;"></div>
										</c:when>
										<%--填空 --%>
										<c:when test="${en.quType eq 'FILLBLANK' }">
											<table class="suQuTable" border="0" cellpadding="0" cellspacing="0">
													<tr>
														<td width="15px">&nbsp;</td>
														<td class="bfbTd">回答数：${en.anCount }</td>
														<td colspan="4">&nbsp;</td>
													</tr>
												</table>
											<div style="clear:both;"></div>
										</c:when>
										<%--多行填空 --%>
										<c:when test="${en.quType eq 'ANSWER' }">
											<table class="suQuTable" border="0" cellpadding="0" cellspacing="0">
													<tr>
														<td width="15px">&nbsp;</td>
														<td class="bfbTd">回答数：${en.anCount }</td>
														<td colspan="4"></td>
													</tr>
												</table>
											<div style="clear:both;"></div>
										</c:when>
										<%--复合单选 --%>
										<c:when test="${en.quType eq 'COMPRADIO' }">
											<table class="suQuTable" border="0" cellpadding="0" cellspacing="0">
											<c:forEach items="${en.quRadios }" var="quEn" varStatus="quI">
														<tr>
															<td width="15px">&nbsp;</td>
															<td width="520px">${quEn.optionName }</td>
															<td width="40px">&nbsp;</td>
														</tr>
													</c:forEach>
											</table>
											<div id="statJson_${i.count }" style="display: none;">${en.statJson }</div>
											<div id="chartdivId_${i.count }" style="height: 300px;width: 80%;">&nbsp;&nbsp;统计图生成中...</div>
											<script type="text/javascript">
														buildChart("statJson_${i.count }","chartdivId_${i.count }");
											</script>
											<div style="clear:both;"></div>
										</c:when>
										<%--复合多选 --%>
										<c:when test="${en.quType eq 'COMPCHECKBOX' }">
											<table class="suQuTable" border="0" cellpadding="0" cellspacing="0">
											<c:forEach items="${en.quCheckboxs }" var="quEn" varStatus="quI">
														<tr>
															<td width="15px">&nbsp;</td>
															<td width="520px">${quEn.optionName }</td>
															<td width="40px">&nbsp;</td>
														</tr>
													</c:forEach>
											</table>
											<div id="statJson_${i.count }" style="display: none;">${en.statJson }</div>
											<div id="chartdivId_${i.count }" style="height: 300px;width: 80%;">&nbsp;&nbsp;统计图生成中...</div>
											<script type="text/javascript">
														buildChart("statJson_${i.count }","chartdivId_${i.count }");
											</script>
											<div style="clear:both;"></div>
										</c:when>
										<%--枚举题 --%>
										<c:when test="${en.quType eq 'ENUMQU' }">
											<table class="suQuTable" border="0" cellpadding="0" cellspacing="0">
													<tr>
														<td width="15px">&nbsp;</td>
														<td class="bfbTd">回答数：${en.anCount }</td>
														<td colspan="4"></td>
													</tr>
												</table>
											<div style="clear:both;"></div>
										</c:when>
										<%--组合填空 --%>
										<c:when test="${en.quType eq 'MULTIFILLBLANK' }">
											<table class="suQuTable" border="0" cellpadding="0" cellspacing="0">
													<tr>
														<td width="15px">&nbsp;</td>
														<td class="bfbTd">回答数：${en.anCount }</td>
														<td colspan="4"></td>
													</tr>
												</table>
											<div style="clear:both;"></div>
										</c:when>
										<%--评分题 --%>
										<c:when test="${en.quType eq 'SCORE' }">
													<table class="suQuTable" border="0" cellpadding="0" cellspacing="0">
												<c:forEach items="${en.quScores }" var="quEn" varStatus="quI">
														<tr>
															<td width="15px">&nbsp;</td>
															<td width="520px">${quEn.optionName }</td>
															<td width="40px">&nbsp;</td>
														</tr>
														<script type="text/javascript">
															
														</script>
												</c:forEach>
													</table>
													<div style="clear:both;"></div>
										</c:when>
										<%--矩阵单选题 --%>
										<c:when test="${en.quType eq 'CHENRADIO' }">
													<table class="suQuTable" border="0" cellpadding="0" cellspacing="0">
													<c:forEach items="${en.rows }" var="quEn" varStatus="quI">
														<tr>
															<td width="15px">&nbsp;</td>
															<td width="520px">${quEn.optionName }</td>
															<td width="40px">&nbsp;</td>
														</tr>
														<script type="text/javascript">
															
														</script>
													</c:forEach>
													</table>
													<div style="clear:both;"></div>
										</c:when>
										<%--矩阵多选题 --%>
										<c:when test="${en.quType eq 'CHENCHECKBOX' }">
											<table class="suQuTable" border="0" cellpadding="0" cellspacing="0">
													<c:forEach items="${en.rows }" var="quEn" varStatus="quI">
														<tr>
															<td width="15px">&nbsp;</td>
															<td width="520px">${quEn.optionName }</td>
															<td width="40px">&nbsp;</td>
														</tr>
														<script type="text/javascript">
															
														</script>
													</c:forEach>
													</table>
													<div style="clear:both;"></div>
										</c:when>
										<%--矩阵填空题 --%>
										<c:when test="${en.quType eq 'CHENFBK' }">
												<table class="suQuTable" border="0" cellpadding="0" cellspacing="0">
													<tr>
														<td width="15px">&nbsp;</td>
														<td class="bfbTd">回答数：${en.anCount }</td>
														<td colspan="4"></td>
													</tr>
												</table>
											<div style="clear:both;"></div>
											<div class="quItemNote">${en.quNote }</div>
										</c:when>
										<%--复合矩阵单选题 --%>
										<c:when test="${en.quType eq 'COMPCHENRADIO' }">
											<table class="suQuTable" border="0" cellpadding="0" cellspacing="0">
												<tr>
														<td width="15px">&nbsp;</td>
														<td class="bfbTd">回答数：${en.anCount }</td>
														<td colspan="4"></td>
													</tr>
											</table>
											<div style="clear:both;"></div>
										</c:when>
										<c:otherwise>
											题型开发中..
										</c:otherwise>
								</c:choose>
							</div>
						</div>
									</td>
								</tr>
								</c:forEach>
							</table>
									
								</div>
							</div>			
					</div>
					
				</div>
			</div>
			
		</div>
		</div>
	</div>
<script type="text/javascript">

</script>
</body>
</html>