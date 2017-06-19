<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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


<script type="text/javascript" src="${ctx }/js/dw/collect.js"></script>
<link href="${ctx }/js/plugs/font-awesome-4.2.0/css/font-awesome.css" rel="stylesheet">


<script src="http://cdn.hcharts.cn/highcharts/highcharts.js" type="text/javascript"></script> 
<script src="http://cdn.hcharts.cn/highcharts/modules/exporting.js" type="text/javascript"></script> 
<title>默认报告</title>
<script type="text/javascript">
$(document).ready(function(){
	//chartData surveyResultQu
	$(".linechart_pic,.piechart_pic,.barchart_pic,.columnchart_pic").click(function(){
		var th=$(this);
		var thClass=th.attr("class");
		var quId=th.parents(".surveyResultQu").find("input[name='quId']").val();
		var amchartdivId=null;
		if(thClass.indexOf("linechart_pic")>0){
			amchartdivId="line_chart_"+quId;
		}else if(thClass.indexOf("piechart_pic")>0){
			amchartdivId="pie_chart_"+quId;
		}else if(thClass.indexOf("barchart_pic")>0){
			amchartdivId="bar_chart_"+quId;
		}else if(thClass.indexOf("columnchart_pic")>0){
			amchartdivId="column_chart_"+quId;
		}
		th.parent().find(".dw_btn026.active").removeClass("active");
		th.addClass("active");
		
		if(amchartdivId!=null){
			var amchartdivObj=$("#"+amchartdivId);
			if(!amchartdivObj[0]){
				var url="${ctx}/da/survey-report!chartData.action";
				var data="quId="+quId;
				$.ajax({
					url:url,
					data:data,
					type:"post",
					success:function(msg){
						
						$("#amchart_"+quId).find(".higChartSvg").hide();
						
						if(thClass.indexOf("linechart_pic")>0){
							//buildChart(msg,quId);	
							higLineChart(msg,quId);
						}else if(thClass.indexOf("piechart_pic")>0){
							//buildPieChart(msg,quId);
							higPieChart(msg,quId);
						}else if(thClass.indexOf("barchart_pic")>0){
							higBarChart(msg,quId);
						}else if(thClass.indexOf("columnchart_pic")>0){
							higColumnChart(msg,quId);
						}
							
					}
				});
			}else{
				$("#amchart_"+quId).find(".higChartSvg").hide();
				amchartdivObj.show();
			}
		}
		
		return false;
	});
	
	//$("")
	//$(".linechart_pic").click();
	//$(".piechart_pic").click();
	//$(".barchart_pic").click();
	$(".columnchart_pic").click();
	
});


function getHighchartsData(quItemBody,charType){
	var quType=quItemBody.find("input[name='quType']").val();
	
	var categories=[];
	var series=new Array();
	var seriesData=new Array();
	var tagText="次";
	
	if(quType==="CHENRADIO" || quType==="CHENCHECKBOX"){
		
		if(charType==="PIE"){
			seriesData=new Array();
			var rowItemTrs=quItemBody.find(".rowItemTr");
			$.each(rowItemTrs,function(){
				//var rowItemOptionName=$(this).find("input[name='rowItemOptionName']").val();
				var rowItemOptionName=$(this).find(".rowItemOptionName").text();
				var thColumnItemTrs=$(this).next().find(".columnItemTr");
				$.each(thColumnItemTrs,function(){
					//var columnItemOptionName=$(this).find("input[name='columnItemOptionName']").val();
					var columnItemOptionName=$(this).find(".columnItemOptionName").text();
					var anCount=$(this).find("input[name='columnItemAnCount']").val();
					seriesData.push([rowItemOptionName+"|"+columnItemOptionName, parseInt(anCount)]);
				});
			});
			
			series=[{                                 //指定数据列
		        name: '选项',                          //数据列名
		        data: seriesData                      //数据
		    }];
			
		}else{
			var columnItemTrs=quItemBody.find(".anColumnTable:eq(0) .columnItemTr");
			$.each(columnItemTrs,function(){
				//var columnItemOptionName=$(this).find("input[name='columnItemOptionName']").val();
				var columnItemOptionName=$(this).find(".columnItemOptionName").text();
				categories.push(columnItemOptionName);
			});
			
			var rowItemTrs=quItemBody.find(".rowItemTr");
			$.each(rowItemTrs,function(){
				//var rowItemOptionName=$(this).find("input[name='rowItemOptionName']").val();
				var rowItemOptionName=$(this).find(".rowItemOptionName").text();
				var thColumnItemTrs=$(this).next().find(".columnItemTr");
				seriesData=new Array();
				$.each(thColumnItemTrs,function(){
					var anCount=$(this).find("input[name='columnItemAnCount']").val();
					seriesData.push(parseInt(anCount));
				});
				series.push({                                 //指定数据列
			        name: rowItemOptionName,                //数据列名
			        data: seriesData                      //数据
			    });
			});
		}
		
	}else{
		
		var seriesDataTemp="[";	
		
		var quRadioOptions=quItemBody.find(".quTrOptions");
		$.each(quRadioOptions,function(i,item){
			var quOptionName=$(this).find(".optionName").text();
			var anCount=$(this).find("input[name='quItemAnCount']").val();
			
			if(anCount==""){
				anCount=0;
			}
			categories.push(quOptionName);
			if(quType==="SCORE"){
				var avgScore=$(this).find("input[name='quItemAvgScore']").val();
				//平均分 setAvgScore  
				//alert(avgScore);
				avgScore=parseFloat(avgScore).toFixed(2);
				if(avgScore==="NaN"){
					avgScore="0.00";
				}
				if(charType==="PIE"){
					seriesData.push([quOptionName,   parseFloat(avgScore)]);
				}else{
					seriesData.push(parseFloat(avgScore));	
				}
				tagText="分";
			}else{
				if(charType==="PIE"){
					seriesData[i]=new Array();
					seriesData[i][0]=quOptionName;
					seriesData[i][1]=parseInt(anCount);
				}else{
					seriesData.push(parseInt(anCount));	
				}
			}
		});
		
		if(charType==="PIE"){
			series=[{
	            type: 'pie',
	            data: seriesData
	        }];
		}else{
			series=[{                                 //指定数据列
		        name: '选项',                          //数据列名
		        data: seriesData                      //数据
		    }];
		}
		
	}
	
	
	return [categories,series,tagText];
}


function higColumnChart(resultJson,quId){
	//$("#"+chartdivId).css({"margin-top":"10px"});
	var chartdivId="column_chart_"+quId;
	$("#amchart_"+quId).prepend("<div id='"+chartdivId+"' class=\"higChartSvg\"></div>");
	$("#"+chartdivId).css({"height": "300px"});
	
	var quItemBody=$("#quTr_"+quId);
	var quTitle=quItemBody.find(".quCoTitleText").text();
	
	var quTypeName=quItemBody.find("input[name='quTypeCnName']").val();
	
	var datas=getHighchartsData(quItemBody,"column");
	var categories=datas[0];
	var series=datas[1];
	var tagText=datas[2];
	
	
	$("#"+chartdivId).highcharts({                   //图表展示容器，与div的id保持一致
		 	legend: {                                                              
	          enabled: true                                                     
	      	},  
		 	credits:{
		    	 enabled:true // 禁用版权信息
	    		 ,text:'来自：调问网'               // 显示的文字
	    		 ,href:'http://www.diaowen.net'   // 链接地址
    			 ,style: {                            // 样式设置
    					cursor: 'pointer',
    					color: 'rgb(199, 195, 195)',
    					fontSize: '12px'
    			 }
			},
			exporting:{
				url:"http://export.hcharts.cn"
				,scale:2
				,width:1200
			},
	        chart: {
	            type: 'column'                         //指定图表的类型，默认是折线图（line）
	        },
	        title: {
	            text: quTitle      //指定图表标题
	        },
	       /*  tooltip: {
	            formatter: function () {
	                var s = this.x + '：';
	                $.each(this.points, function () {
	                    s += '<b>' + this.y + '</b>'+tagText;
	                });
	                return s;
	            },
	            shared: true
	        }, */
	        tooltip: {
	            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
	            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
	                '<td style="padding:0"><b>{point.y:.1f} '+tagText+'</b></td></tr>',
	            footerFormat: '</table>',
	            shared: true,
	            useHTML: true
	        },
	        plotOptions: {
			    column: {
			        pointPadding: 0.2,
			        borderWidth: 0,
			        pointWidth: 50
			    }
			},
	        xAxis: {
	            categories: categories   //指定x轴分组
	        },
	        yAxis: {
	            title: {
	                text: '次数'                  //指定y轴的标题
	            }
	        },
	        series: series
	    });
	
}

function higPieChart(resultJson,quId){
	
	//$("#"+chartdivId).css({"margin-top":"10px"});
	var chartdivId="pie_chart_"+quId;
	$("#amchart_"+quId).prepend("<div id='"+chartdivId+"' class=\"higChartSvg\"></div>");
	$("#"+chartdivId).css({"height": "300px"});
	
	var quItemBody=$("#quTr_"+quId);
	var quTitle=quItemBody.find(".quCoTitleText").text();
	var quTypeName=quItemBody.find("input[name='quTypeCnName']").val();
	
	var datas=getHighchartsData(quItemBody,"PIE");
	var series=datas[1];
	var tagText=datas[2];
	
	/* Highcharts.getOptions().plotOptions.pie.colors = (function () {
        var colors = [],
            base = Highcharts.getOptions().colors[0],
            i;

        for (i = 0; i < 10; i += 1) {
            // Start out with a darkened base color (negative brighten), and end
            // up with a much brighter color
            colors.push(Highcharts.Color(base).brighten((i - 1) / 7).get());
        }
        return colors;
    }()); */
	
    // Build the chart
    $('#'+chartdivId).highcharts({
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false,
            type: 'pie'
        },
        exporting:{
			url:"http://export.hcharts.cn"
			,scale:2
			,width:1200
		},
        credits:{
	      enabled:true // 禁用版权信息
   		 ,text:'来自：调问网'               // 显示的文字
   		 ,href:'http://www.diaowen.net'   // 链接地址
			 ,style: {                       // 样式设置
					cursor: 'pointer',
					color: 'rgb(199, 195, 195)',
					fontSize: '12px'
			 }
		},
        title: {
            text: quTitle
        },
        tooltip: {
            pointFormat: '{point.y}'+tagText+'<br/><b>{point.percentage:.1f}%</b>'
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: true,
                    format: '{point.name}<br/> {point.y}'+tagText+' <br/> 百分比：{point.percentage:.1f} %',
                    style: {
                        color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                    }
                }
            }
        },
        series: series
    });
	
}


function higBarChart(resultJson,quId){
	//$("#"+chartdivId).css({"margin-top":"10px"});
	var chartdivId="bar_chart_"+quId;
	$("#amchart_"+quId).prepend("<div id='"+chartdivId+"' class=\"higChartSvg\"></div>");
	//$("#"+chartdivId).css({"height": "300px"});
	
	var quItemBody=$("#quTr_"+quId);
	var quTitle=quItemBody.find(".quCoTitleText").text();
	var quTypeName=quItemBody.find("input[name='quTypeCnName']").val();
	
	var datas=getHighchartsData(quItemBody,"BAR");
	var categories=datas[0];
	var series=datas[1];
	var tagText=datas[2];
	
	$('#'+chartdivId).highcharts({
        chart: {
            type: 'bar'
        },
        title: {
            text: quTitle
        },
        xAxis: {
            categories: categories,
            title: {
                text: null
            }
        },
        yAxis: {
            min: 0,
            title: {
                text: tagText+'数'
                //,align: 'high'
            },
            labels: {
                overflow: 'justify'
            }
        },
        tooltip: {
           valueSuffix: tagText
        	//   pointFormat: '<b>{point.y}'+tagText+'</b>'
        },
        plotOptions: {
            bar: {
                dataLabels: {
                    enabled: true
                },
                pointPadding: 0.2,
		        borderWidth: 0,
		        pointWidth: 30
            }
        },
        legend: {
        	enabled: true ,
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'top',
            x: -40,
            y: 100,
            itemMarginBottom:5,
            floating: true,
            borderWidth: 1,
            backgroundColor: ((Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF'),
            shadow: true
        },
        credits:{
	      enabled:true // 禁用版权信息
   		 ,text:'来自：调问网'               // 显示的文字
   		 ,href:'http://www.diaowen.net'   // 链接地址
			 ,style: {                            // 样式设置
					cursor: 'pointer',
					color: 'rgb(199, 195, 195)',
					fontSize: '14px'
			 }/* ,
			 position: {
	             align: 'left',
	             x: 10,
	         }  */
		},
        series: series
    });
	
}



function higLineChart(resultJson,quId){
	//根据quId得到数据对象，并且解析
	
	var chartdivId="line_chart_"+quId;
	$("#amchart_"+quId).prepend("<div id='"+chartdivId+"' class=\"higChartSvg\"></div>");
	$("#"+chartdivId).css({"height": "300px"});
	
	var quItemBody=$("#quTr_"+quId);
	var quTitle=quItemBody.find(".quCoTitleText").text();
	var quTypeName=quItemBody.find("input[name='quTypeCnName']").val();
	
	/* var categories=[];
	var series=[];
	var seriesData=[];
	var quRadioOptions=quItemBody.find(".quTrOptions");
	$.each(quRadioOptions,function(){
		var quOptionName=$(this).find(".optionName").text();
		var anCount=$(this).find("input[name='quItemAnCount']").val();
		categories.push(quOptionName);
		seriesData.push(parseInt(anCount));
	});
	series=[{                                 //指定数据列
        name: '选项',                          //数据列名
        data: seriesData                      //数据
    }]; */
	
	var datas=getHighchartsData(quItemBody,"Line");
	var categories=datas[0];
	var series=datas[1];
	var tagText=datas[2];
	
	$('#'+chartdivId).highcharts({
		credits:{
	   	  enabled:true // 禁用版权信息
   		 ,text:'来自：调问网'               // 显示的文字
   		 ,href:'http://www.diaowen.net'   // 链接地址
		 ,style: {                            // 样式设置
				cursor: 'pointer',
				color: 'rgb(199, 195, 195)',
				fontSize: '14px'
		 }
		},
        title: {
            text: quTitle,
            x: -20 //center
        },
        xAxis: {
            categories: categories
        },
        yAxis: {
            title: {
                text: tagText+'数'
            },
            plotLines: [{
                value: 0,
                width: 1,
                color: '#808080'
            }]
        },
        tooltip: {
              valueSuffix: tagText
           // pointFormat: '<b>{point.y}'+tagText+'</b>'
        },
        plotOptions: {
            line: {
                dataLabels: {
                    enabled: true
                }
            }
        },
        legend: {
        	enabled: true ,
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'middle',
            itemMarginBottom:5,
            borderWidth: 0
        },
        series: series
    });
	
}


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

function buildChart(resultJson,quId){
	var chartdivId="line_chart_"+quId;
	$("#amchart_"+quId).prepend("<div id='"+chartdivId+"'></div>");
	$("#"+chartdivId).css({"height": "300px","width": "80%"});
	
	var rows = eval("(" + resultJson  + ")");
	
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
	
	 //AmCharts.ready();
	 (function() {
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
			chart.validateNow();
		})();
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
<style type="text/css">
.dw_btn026{
	border: 1px solid transparent;
}
</style>
</head>
<body>
	<input type="hidden" id="id" name="id" value="${surveyId }">
	
	<div style="clear: both;"></div>
	<div id="dwBody" >
		<div id="dwBodyContent" class="bodyCenter" style="border:1px solid #C1DAEC;">
		<div id="dwBodyUser">
			<div class="surveyCollectMiddle">
				
				<div class="surveyCollectTop">
					<div class="surveyCollectTitleDiv">
						<span class="surveyCollectTitle">${directory.surveyName }</span>
						<div class="scmTabRight" >
							<!-- <a href="" class="sbtn25 sbtn25_2">停止收集</a> -->
						</div>
					</div>
					<c:if test="${noview ne 1 }">
						<div class="surveyCollectInfoDiv">
						<span class="surveyCollectInfoLeft">
						状态：<span class="collectInfoSpan">收集中</span>&nbsp;&nbsp;&nbsp;&nbsp;
						参与人数：<span class="collectInfoSpan">${directory.answerNum }</span>
						</span>
						<span class="surveyCollectInfoRight">
						<%-- 创建时间：<span class="collectInfoSpan"><fmt:formatDate value="${directory.createDate }" pattern="yyyy年MM月dd日 HH:mm"/></span> --%>
						</span>
					</div>
					</c:if>
					
				</div>
				
				<div class="surveyCollectMiddleContent">
					<div style="padding: 15px 25px;overflow: auto;">
							<div style="overflow: auto;">
								<div style="float: left;" >
									<a href="${ctx }/da/survey-report!defaultReport.action?surveyId=${surveyId }" class="dw_btn025 tabpic active"><i class="fa fa-tasks"></i>&nbsp;报表</a>
									<%-- <a href="${ctx }/da/survey-report!lineChart.action?surveyId=${surveyId }" class="dw_btn025 linepic" style="margin-left: 10px;"><i class="fa fa-bar-chart"></i>&nbsp;柱状图</a>
									<a href="${ctx }/da/survey-report!pieChart.action?surveyId=${surveyId }" class="dw_btn025 piepic " style="margin-left: 10px;"><i class="fa fa-pie-chart"></i>&nbsp;饼状图</a> --%> 
								</div>
									<div style="float: right;" >
									<%-- <a href="${ctx }/da/my-survey-answer!exportXLS.action?surveyId=${surveyId }" class="dw_btn025"><i class="fa fa-download"></i>下载数据</a> --%>
									<a href="http://www.diaowen.net/wenjuan/${directory.sid }.html" class="dw_btn025"><i class="fa fa-share"></i>答卷地址</a>
									</div> 
							</div>
							<div style="padding-top:8px;">
							
							
								<div class="" style="border: 1px solid #D1D6DD;padding: 0px;">
								
								<c:if test="${noview eq 1 }">
								<div style="padding: 60px;">
									<h3>抱歉发起者，没有开放数据查看权限！</h3>
								</div>
							</c:if>
							
									<table id="content-tableList" width="100%"  cellpadding="0" cellspacing="0">
								<c:forEach items="${surveyStats.questions }" var="en" varStatus="i">
								<tr id="quTr_${en.id }">
									<td colspan="3">
										<div class="surveyResultQu">
											<input type="hidden" name="quId" value="${en.id }">
											<input type="hidden" name="quType" value="${en.quType }">
											<input type="hidden" name="quAnCount" value="${en.anCount }">
											<%-- <input type="hidden" name="quTitle" value="${en.quTitle }"> --%>
											<input type="hidden" name="quTypeCnName" value="${en.quType.cnName }">
											
							<div class="r-qu-body-title">${i.count }、<div class="quCoTitleText">${en.quTitle }</div>[${en.quType.cnName }]</div>
							<div class="r-qu-body-content">
								<c:choose>
										<%--单选题 --%>
										<c:when test="${en.quType eq 'RADIO' }">
											<table class="suQuTable" border="0" cellpadding="0" cellspacing="0">
											<c:forEach items="${en.quRadios }" var="quEn" varStatus="quI">
														<tr class="quTrOptions">
															<td width="15px">&nbsp;</td>
															<td width="520px" class="optionName">${quEn.optionName }</td>
															<td width="180px"><div id="bfbTd${en.quType }${i.count }_${quI.count}" class="progressbarDiv progress${quI.index }"></div></td>
															<td width="60px" align="right" id="bfbNum${en.quType }${i.count }_${quI.count}" class="bfbTd">0%</td>
															<td align="left" class="tdAnCount">&nbsp;&nbsp;${quEn.anCount }次</td>
															<td width="40px">&nbsp;
																<input type="hidden" name="quItemAnCount" value="${quEn.anCount }">
															</td>
														</tr>
														<script type="text/javascript">
															var count=parseInt("${en.anCount }");
															var anCount=parseInt("${quEn.anCount }");
															if(count==0){
																count=1;
															}
															var bfbFloat=anCount/count*100;
															var bfbVal = bfbFloat.toFixed(2);
															$("#bfbNum${en.quType }${i.count }_${quI.count}").html(bfbVal+"%");
															$("#bfbTd${en.quType }${i.count }_${quI.count}").progressbar({value: bfbFloat});
														</script>
													</c:forEach>
											</table>
											<div class="reportPic">
												<div class="chartBtnEvent">
												<a href="#" class="dw_btn026 columnchart_pic"><i class="fa fa-bar-chart"></i>柱状图</a>
												<a href="#" class="dw_btn026 piechart_pic"><i class="fa fa-pie-chart"></i>饼图</a>
												<a href="#" class="dw_btn026 barchart_pic"><i class="fa fa-tasks"></i>条形图</a>
												<a href="#" class="dw_btn026 linechart_pic"><i class="fa fa-line-chart"></i>折线图</a>
												</div>
												<div style="clear: both;"></div>
												<div id="amchart_${en.id }" ></div>
											</div>
											<div style="clear:both;">
												<%--<div id="line_chart_${en.id }" style=""></div>
													<div id="pie_chart_${en.id }" style=""></div>
													<div id="zx_chart_${en.id }" style=""></div> --%>
											</div>
										</c:when>
										<%--多选题 --%>
										<c:when test="${en.quType eq 'CHECKBOX' }">
											<table class="suQuTable" border="0" cellpadding="0" cellspacing="0">
											<c:forEach items="${en.quCheckboxs }" var="quEn" varStatus="quI">
														<tr class="quTrOptions">
															<td width="15px">&nbsp;</td>
															<td width="520px" class="optionName">${quEn.optionName }</td>
															<td width="180px"><div id="bfbTd${en.quType }${i.count }_${quI.count}" class="progressbarDiv progress${quI.index }"></div></td>
															<td width="60px" align="right" id="bfbNum${en.quType }${i.count }_${quI.count}" class="bfbTd">0%</td>
															<td align="left" class="tdAnCount">&nbsp;&nbsp;${quEn.anCount }次</td>
															<td width="40px">&nbsp;
															<input type="hidden" name="quItemAnCount" value="${quEn.anCount }">
															</td>
														</tr>
														<script type="text/javascript">
															var count=parseInt("${en.anCount }");
															var anCount=parseInt("${quEn.anCount }");
															var bfbFloat=anCount/count*100;
															var bfbVal = bfbFloat.toFixed(2);
															if(bfbVal==="NaN"){
																bfbVal="0.00";
															}
															$("#bfbNum${en.quType }${i.count }_${quI.count}").html(bfbVal+"%");
															$("#bfbTd${en.quType }${i.count }_${quI.count}").progressbar({value: bfbFloat});
														</script>
													</c:forEach>
											</table>
											<div class="reportPic">
												<div class="chartBtnEvent">
												<a href="#" class="dw_btn026 columnchart_pic"><i class="fa fa-bar-chart"></i>柱状图</a>
												<a href="#" class="dw_btn026 piechart_pic"><i class="fa fa-pie-chart"></i>饼图</a>
												<a href="#" class="dw_btn026 barchart_pic"><i class="fa fa-tasks"></i>条形图</a>
												<a href="#" class="dw_btn026 linechart_pic"><i class="fa fa-line-chart"></i>折线图</a>
												</div>
												<div style="clear: both;"></div>
												<div id="amchart_${en.id }" ></div>
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
											<div class="reportPic"></div>
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
											<div class="reportPic"></div>
											<div style="clear:both;"></div>
										</c:when>
										<%--复合单选 --%>
										<c:when test="${en.quType eq 'COMPRADIO' }">
											<table class="suQuTable" border="0" cellpadding="0" cellspacing="0">
											<c:forEach items="${en.quRadios }" var="quEn" varStatus="quI">
														<tr>
															<td width="15px">&nbsp;</td>
															<td width="520px">${quEn.optionName }</td>
															<td width="180px"><div id="bfbTd${en.quType }${i.count }_${quI.count}" class="progressbarDiv progress${quI.index }"></div></td>
															<td width="50px" align="right" id="bfbNum${en.quType }${i.count }_${quI.count}" class="bfbTd">0%</td>
															<td align="left" class="tdAnCount">&nbsp;&nbsp;${quEn.anCount }次</td>
															<td width="40px">&nbsp;</td>
														</tr>
														<script type="text/javascript">
															var count=parseInt("${en.anCount }");
															var anCount=parseInt("${quEn.anCount }");
															var bfbFloat=anCount/count*100;
															var bfbVal = bfbFloat.toFixed(2);
															if(bfbVal==="NaN"){
																bfbVal="0.00";
															}
															$("#bfbNum${en.quType }${i.count }_${quI.count}").html(bfbVal+"%");
															$("#bfbTd${en.quType }${i.count }_${quI.count}").progressbar({value: bfbFloat});
														</script>
													</c:forEach>
											</table>
											<div class="reportPic"></div>
											<div style="clear:both;"></div>
										</c:when>
										<%--复合多选 --%>
										<c:when test="${en.quType eq 'COMPCHECKBOX' }">
											<table class="suQuTable" border="0" cellpadding="0" cellspacing="0">
											<c:forEach items="${en.quCheckboxs }" var="quEn" varStatus="quI">
														<tr>
															<td width="15px">&nbsp;</td>
															<td width="520px">${quEn.optionName }</td>
															<td width="180px"><div id="bfbTd${en.quType }${i.count }_${quI.count}" class="progressbarDiv progress${quI.index }"></div></td>
															<td width="50px" align="right" id="bfbNum${en.quType }${i.count }_${quI.count}" class="bfbTd">0%</td>
															<td align="left" class="tdAnCount">&nbsp;&nbsp;${quEn.anCount }次</td>
															<td width="40px">&nbsp;</td>
														</tr>
														<script type="text/javascript">
															var count=parseInt("${en.anCount }");
															var anCount=parseInt("${quEn.anCount }");
															var bfbFloat=anCount/count*100;
															var bfbVal = bfbFloat.toFixed(2);
															if(bfbVal==="NaN"){
																bfbVal="0.00";
															}
															$("#bfbNum${en.quType }${i.count }_${quI.count}").html(bfbVal+"%");
															$("#bfbTd${en.quType }${i.count }_${quI.count}").progressbar({value: bfbFloat});
														</script>
													</c:forEach>
											</table>
											<div class="reportPic"></div>
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
											<div class="reportPic"></div>
											<div style="clear:both;"></div>
										</c:when>
										<%--组合填空 --%>
										<c:when test="${en.quType eq 'MULTIFILLBLANK' }">
										<!-- getQuMultiFillblanks -->
											<table class="suQuTable" border="0" cellpadding="0" cellspacing="0">
												<c:forEach items="${en.quMultiFillblanks }" var="quEn" varStatus="quI">
													<tr>
														<td width="15px">&nbsp;</td>
														<td width="520px">${quEn.optionName }</td>
														<td class="bfbTd">回答数：${quEn.anCount }</td>
														<td colspan="4"></td>
													</tr>
												</c:forEach>
												</table>
											<div class="reportPic"></div>
											<div style="clear:both;"></div>
										</c:when>
										<%--评分题 --%>
										<c:when test="${en.quType eq 'SCORE' }">
													<input type="hidden" name="paramInt02" value="${en.paramInt02 }">
													<table class="suQuTable" border="0" cellpadding="0" cellspacing="0">
											<c:forEach items="${en.quScores }" var="quEn" varStatus="quI">
														<tr class="quTrOptions">
															<td width="15px">&nbsp;</td>
															<td width="520px" class="optionName">${quEn.optionName }</td>
															<td width="180px"><div id="bfbTd${en.quType }${i.count }_${quI.count}" class="progressbarDiv progress${quI.index }"></div></td>
															<td width="60px" align="right" id="bfbNum${en.quType }${i.count }_${quI.count}" class="bfbTd">0%</td>
															<%-- <td align="left" class="tdAnCount">&nbsp;&nbsp;${quEn.anCount }次</td> --%>
															<td align="left" class="tdAnCount">&nbsp;&nbsp;平均</td> 
															<td width="40px">&nbsp;
																<input type="hidden" name="quItemAnCount" value="${quEn.anCount }">
																<input type="hidden" name="quItemAvgScore" value="${quEn.avgScore }" >
															</td>
														</tr>
														<script type="text/javascript">
															var count=parseInt("${en.anCount }");
															var anCount=parseInt("${quEn.anCount }");
															var avgScore=parseFloat("${quEn.avgScore}");
															var bfbFloat=avgScore/"${en.paramInt02}"*100;
															var bfbVal = bfbFloat.toFixed(2);
															//平均分 setAvgScore  
															avgScore=avgScore.toFixed(2);
															if(avgScore==="NaN"){
																avgScore="0.00";
															}
															$("#bfbNum${en.quType }${i.count }_${quI.count}").html(avgScore+"分");
															$("#bfbTd${en.quType }${i.count }_${quI.count}").progressbar({value: bfbFloat});
														</script>
													</c:forEach>
													</table>
											<div class="reportPic">
												<div class="chartBtnEvent">
												<a href="#" class="dw_btn026 columnchart_pic"><i class="fa fa-bar-chart"></i>柱状图</a>
												<a href="#" class="dw_btn026 piechart_pic"><i class="fa fa-pie-chart"></i>饼图</a>
												<a href="#" class="dw_btn026 barchart_pic"><i class="fa fa-tasks"></i>条形图</a>
												<a href="#" class="dw_btn026 linechart_pic"><i class="fa fa-line-chart"></i>折线图</a>
												</div>
												<div style="clear: both;"></div>
												<div id="amchart_${en.id }" ></div>
											</div>
											<div style="clear:both;"></div>
										</c:when>
										<%--矩阵单选题 --%>
										<c:when test="${en.quType eq 'CHENRADIO' }">
													<table class="suQuTable" border="0" cellpadding="0" cellspacing="0">
														
														<c:forEach items="${en.rows }" var="rowItem" varStatus="rowI">
														<tr class="rowItemTr">
															<td width="15px">&nbsp;
																<%-- <input type="hidden" name="rowItemOptionName" value="${rowItem.optionName }"> --%>
																<div class="rowItemOptionName" style="display: none;">${rowItem.optionName }</div>
																<input type="hidden" name="rowItemAnCount" value="${rowItem.anCount }">
															</td>
															<td class="quChenRowTd" colspan="5"><label class="editAble quCoOptionEdit" style="font-size: 14px;">${rowI.count}、${rowItem.optionName }</label></td>
														</tr>
														<tr>
															<td colspan="6">
															<table class="anColumnTable">
																<c:forEach items="${en.columns }" var="columnItem" varStatus="colI">
														<tr class="columnItemTr">
															<td width="15px">&nbsp;</td>
															<td width="520" class="quChenRowTd" style="padding-left: 15px;"><label class="editAble quCoOptionEdit">${columnItem.optionName }</label></td>
															<td width="180px"><div id="bfbTd${en.quType }${i.count }_${rowI.count}_${colI.count }" class="progressbarDiv progress${rowI.index }"></div></td>
															<td width="50px" align="right" id="bfbNum${en.quType }${i.count }_${rowI.count}_${colI.count}" class="bfbTd">0%</td>
															<td align="left" id="bfbAnCount${en.quType }${i.count }_${rowI.count}_${colI.count}" class="tdAnCount">&nbsp;0次</td>
															<td>
																<%-- <input type="hidden" name="columnItemOptionName" value="${columnItem.optionName }"> --%>
																	<div class="columnItemOptionName" style="display: none;">${columnItem.optionName }</div>
																	<input type="hidden" name="columnItemAnCount" value="0" id="coumneItemAnCount${en.quType }${i.count }_${rowI.count}_${colI.count}">
															<c:forEach items="${en.anChenRadios }" var="anItem">
																<c:if test="${anItem.quRowId eq rowItem.id && anItem.quColId eq columnItem.id }">
																	<script type="text/javascript">
																		var count=parseInt("${rowItem.anCount }");
																		var anCount=parseInt("${anItem.anCount }");
																		var bfbFloat=anCount/count*100;
																		var bfbVal = bfbFloat.toFixed(2);
																		if(bfbVal==="NaN"){
																			bfbVal="0.00";
																		}
																		$("#bfbNum${en.quType }${i.count }_${rowI.count}_${colI.count}").html(bfbVal+"%");
																		$("#bfbAnCount${en.quType }${i.count }_${rowI.count}_${colI.count}").html("&nbsp;&nbsp;"+anCount+"次");
																		$("#bfbTd${en.quType }${i.count }_${rowI.count}_${colI.count }").progressbar({value: bfbFloat});
																		
																		$("#coumneItemAnCount${en.quType }${i.count }_${rowI.count}_${colI.count}").val(anCount);
																		
																	</script>
																</c:if>
															</c:forEach>
															</td>
														</tr>
														</c:forEach>
															</table>
															</td>
														</tr>
														</c:forEach>
													</table>
											<div class="reportPic">
												<div class="chartBtnEvent">
												<a href="#" class="dw_btn026 columnchart_pic"><i class="fa fa-bar-chart"></i>柱状图</a>
												<a href="#" class="dw_btn026 piechart_pic"><i class="fa fa-pie-chart"></i>饼图</a>
												<a href="#" class="dw_btn026 barchart_pic"><i class="fa fa-tasks"></i>条形图</a>
												<a href="#" class="dw_btn026 linechart_pic"><i class="fa fa-line-chart"></i>折线图</a>
												</div>
												<div style="clear: both;"></div>
												<div id="amchart_${en.id }" ></div>
											</div>
											<div style="clear:both;"></div>
										</c:when>
										<%--矩阵多选题 --%>
										<c:when test="${en.quType eq 'CHENCHECKBOX' }">
											<table class="suQuTable" border="0" cellpadding="0" cellspacing="0">
													
													<c:forEach items="${en.rows }" var="rowItem" varStatus="rowI">
													<tr class="rowItemTr">
														<td width="15px">&nbsp;
															<%-- <input type="hidden" name="rowItemOptionName" value="${rowItem.optionName }"> --%>
															<div class="rowItemOptionName" style="display: none;">${rowItem.optionName }</div>
															<input type="hidden" name="rowItemAnCount" value="${rowItem.anCount }">
														</td>
														<td class="quChenRowTd" colspan="5"><label class="editAble quCoOptionEdit" style="font-size: 14px;">${rowI.count}、${rowItem.optionName }</label></td>
													</tr>
													<tr>
														<td colspan="6">
														<table class="anColumnTable">
															<c:forEach items="${en.columns }" var="columnItem" varStatus="colI">
															<tr class="columnItemTr">
																<td width="15px">&nbsp;</td>
																<td width="520" class="quChenRowTd" style="padding-left: 15px;"><label class="editAble quCoOptionEdit">${columnItem.optionName }</label></td>
																<td width="180px"><div id="bfbTd${en.quType }${i.count }_${rowI.count}_${colI.count }" class="progressbarDiv progress${rowI.index }"></div></td>
																<td width="50px" align="right" id="bfbNum${en.quType }${i.count }_${rowI.count}_${colI.count}" class="bfbTd">0%</td>
																<td align="left" id="bfbAnCount${en.quType }${i.count }_${rowI.count}_${colI.count}" class="tdAnCount">&nbsp;0次</td>
																<td>
																			<%-- <input type="hidden" name="columnItemOptionName" value="${columnItem.optionName }"> --%>
																			<div class="columnItemOptionName" style="display: none;">${columnItem.optionName }</div>
																			<input type="hidden" name="columnItemAnCount" value="0" id="coumneItemAnCount${en.quType }${i.count }_${rowI.count}_${colI.count}">
																<c:forEach items="${en.anChenCheckboxs }" var="anItem">
																	<c:if test="${anItem.quRowId eq rowItem.id && anItem.quColId eq columnItem.id }">
																		<script type="text/javascript">
																			var count=parseInt("${rowItem.anCount }");
																			var anCount=parseInt("${anItem.anCount }");
																			var bfbFloat=anCount/count*100;
																			var bfbVal = bfbFloat.toFixed(2);
																			if(bfbVal==="NaN"){
																				bfbVal="0.00";
																			}
																			$("#bfbNum${en.quType }${i.count }_${rowI.count}_${colI.count}").html(bfbVal+"%");
																			$("#bfbAnCount${en.quType }${i.count }_${rowI.count}_${colI.count}").html("&nbsp;&nbsp;"+anCount+"次");
																			$("#bfbTd${en.quType }${i.count }_${rowI.count}_${colI.count }").progressbar({value: bfbFloat});
																			
																			$("#coumneItemAnCount${en.quType }${i.count }_${rowI.count}_${colI.count}").val(anCount);
																		</script>
																	</c:if>
																</c:forEach>
																</td>
															</tr>
															</c:forEach>
														</table>
														</td>
													</tr>
													</c:forEach>
													
													</table>
											<div class="reportPic">
												<div class="chartBtnEvent">
												<a href="#" class="dw_btn026 columnchart_pic"><i class="fa fa-bar-chart"></i>柱状图</a>
												<a href="#" class="dw_btn026 piechart_pic"><i class="fa fa-pie-chart"></i>饼图</a>
												<a href="#" class="dw_btn026 barchart_pic"><i class="fa fa-tasks"></i>条形图</a>
												<a href="#" class="dw_btn026 linechart_pic"><i class="fa fa-line-chart"></i>折线图</a>
												</div>
												<div style="clear: both;"></div>
												<div id="amchart_${en.id }" ></div>
											</div>
											<div style="clear:both;"></div>
										</c:when>
										<%--矩阵评分题 --%>
										<c:when test="${en.quType eq 'CHENSCORE' }">
											<table class="suQuTable" border="0" cellpadding="0" cellspacing="0">
													
													<c:forEach items="${en.rows }" var="rowItem" varStatus="rowI">
													<tr>
														<td width="15px">&nbsp;</td>
														<td class="quChenRowTd" colspan="6"><label class="editAble quCoOptionEdit" style="font-size: 14px;">${rowI.count}、${rowItem.optionName }</label></td>
													</tr>
													<c:forEach items="${en.columns }" var="columnItem" varStatus="colI">
													<tr>
														<td width="15px">&nbsp;</td>
														<td width="520" class="quChenRowTd" style="padding-left: 15px;"><label class="editAble quCoOptionEdit">${columnItem.optionName }</label></td>
														<td width="180px"><div id="bfbTd${en.quType }${i.count }_${rowI.count}_${colI.count }" class="progressbarDiv progress${rowI.index }"></div></td>
														<td width="60px" align="right" id="bfbNum${en.quType }${i.count }_${rowI.count}_${colI.count}" class="bfbTd">0%</td>
														<%-- <td align="left" id="bfbAnCount${en.quType }${i.count }_${rowI.count}_${colI.count}" class="tdAnCount">&nbsp;0次</td> --%>
														<td align="left" class="tdAnCount">&nbsp;&nbsp;平均</td> 
														<td width="40px">&nbsp;</td>
														<td>
														<c:forEach items="${en.anChenScores }" var="anItem">
															<c:if test="${anItem.quRowId eq rowItem.id && anItem.quColId eq columnItem.id }">
																<!-- <script type="text/javascript">
																	var count=parseInt("${rowItem.anCount }");
																	var anCount=parseInt("${anItem.anCount }");
																	var bfbFloat=anCount/count*100;
																	var bfbVal = bfbFloat.toFixed(2);
																	$("#bfbNum${en.quType }${i.count }_${rowI.count}_${colI.count}").html(bfbVal+"%");
																	$("#bfbAnCount${en.quType }${i.count }_${rowI.count}_${colI.count}").html("&nbsp;&nbsp;"+anCount+"次");
																	$("#bfbTd${en.quType }${i.count }_${rowI.count}_${colI.count }").progressbar({value: bfbFloat});
																</script> -->
																<script type="text/javascript">
																	var avgScore=parseFloat("${anItem.avgScore}");
																	//var bfbFloat=avgScore/"${en.paramInt02}"*100;
																	var bfbFloat=avgScore/5*100;
																	var bfbVal = bfbFloat.toFixed(2);
																	//平均分 setAvgScore  
																	avgScore=avgScore.toFixed(2);
																	if(avgScore==="NaN"){
																		avgScore="0.00";
																	}
																	$("#bfbNum${en.quType }${i.count }_${rowI.count}_${colI.count}").html(avgScore+"分");
																	$("#bfbTd${en.quType }${i.count }_${rowI.count}_${colI.count }").progressbar({value: bfbFloat});
																</script>
															</c:if>
														</c:forEach>
														</td>
													</tr>
													</c:forEach>
													</c:forEach>
													
													</table>
											<div class="reportPic"></div>
											<div style="clear:both;"></div>
										</c:when>
										<%--矩阵填空题 --%>
										<c:when test="${en.quType eq 'CHENFBK' }">
												<table class="suQuTable" border="0" cellpadding="0" cellspacing="0">
													<c:forEach items="${en.rows }" var="rowItem" varStatus="rowI">
													<tr>
														<td width="15px">&nbsp;</td>
														<td class="quChenRowTd" colspan="4"><label class="editAble quCoOptionEdit" style="font-size: 14px;">${rowI.count}、${rowItem.optionName }</label></td>
													</tr>
													<c:forEach items="${en.columns }" var="columnItem" varStatus="colI">
													<tr>
														<td width="15px">&nbsp;</td>
														<td width="520" class="quChenRowTd" style="padding-left: 15px;"><label class="editAble quCoOptionEdit">${columnItem.optionName }</label></td>
														<td width="120px" align="left" id="bfbNum${en.quType }${i.count }_${rowI.count}_${colI.count}" class="bfbTd">0%</td>
														<%-- <td align="left" id="bfbAnCount${en.quType }${i.count }_${rowI.count}_${colI.count}" class="tdAnCount">&nbsp;0次</td> --%>
														<td width="40px">&nbsp;</td>
														<td>
														<c:forEach items="${en.anChenFbks }" var="anItem">
															<c:if test="${anItem.quRowId eq rowItem.id && anItem.quColId eq columnItem.id }">
																<script type="text/javascript">
																	$("#bfbNum${en.quType }${i.count }_${rowI.count}_${colI.count}").html("回答数：${anItem.anCount}");
																</script>
															</c:if>
														</c:forEach>
														</td>
													</tr>
													</c:forEach>
													</c:forEach>
													<%-- <tr>
														<td width="15px">&nbsp;</td>
														<td class="bfbTd">回答数：${en.anCount }</td>
														<td colspan="4"></td>
													</tr> --%>
												</table>
											<div class="reportPic"></div>
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
										<div class="reportPic"></div>
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