<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">


<script type="text/javascript" src="${ctx }/js/dw/collect.js"></script>
<link href="${ctx }/js/plugs/font-awesome-4.2.0/css/font-awesome.css" rel="stylesheet">

<script src="${ctx }/js/plugs/echarts/echarts-master/dist/echarts.js"></script>
<script src="${ctx }/js/plugs/echarts/echarts-master/theme/shine.js"></script>

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

	resetQuNum();
	function resetQuNum(){
		var quCoNums=$(".quCoNum");
		$.each(quCoNums,function(i,item){
			$(this).html((i+1)+"、");
		});

	}
});


function getHighchartsData(quItemBody,charType){
	var quType=quItemBody.find("input[name='quType']").val();

	var categories=[];
	var series=new Array();
	var seriesData=new Array();
	var tagText="次数";
	var legendData=new Array();

	var seriesType = 'bar';
	if (charType==="Line") {
		seriesType = 'line';
	}

	if(quType==="CHENRADIO" || quType==="CHENCHECKBOX" ||  quType==="CHENSCORE"){

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
					//seriesData.push([rowItemOptionName+"|"+columnItemOptionName, parseInt(anCount)]);

					var data = {};
				    data["value"] = parseInt(anCount);
				    data["name"] = rowItemOptionName+"|"+columnItemOptionName;
				    series.push(data);

				});
			});

			/*
			series=[{                                 //指定数据列
		        name: '选项',                          //数据列名
		        data: seriesData                      //数据
		    }];
			*/

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
			        //type:'bar',
			        type: seriesType,
			        data: seriesData                      //数据
			    });

				legendData.push(rowItemOptionName);

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
					//seriesData.push([quOptionName,   parseFloat(avgScore)]);
					var data = {};
				    data["value"] = parseFloat(avgScore);
				    data["name"] = quOptionName;
				    seriesData.push(data);
				}else{
					seriesData.push(parseFloat(avgScore));
				}
				tagText="分数";
			} else if(quType==="ORDERQU"){
				if(charType==="PIE"){
					var data = {};
				    data["value"] = parseInt(anCount);
				    data["name"] = quOptionName;
				    seriesData.push(data);
				}else{
					seriesData.push(parseInt(anCount));
				}
				tagText="排名";
			} else{
				if(charType==="PIE"){
					//seriesData[i]=new Array();
					//seriesData[i][0]=quOptionName;
					//seriesData[i][1]=parseInt(anCount);
					var data = {};
				    data["value"] = parseInt(anCount);
				    data["name"] = quOptionName;
				    seriesData.push(data);
				}else{
					seriesData.push(parseInt(anCount));
				}
			}


		});

		if(charType==="PIE"){
			/*
			series=[{
	            type: 'pie',
	            data: seriesData
	        }];
			*/
			series = seriesData;
		}else if(charType === "BAR"){
			series=[{
				//name: '选项',
				name: tagText,
	            type: 'bar',
	            data: seriesData
	        }];
		}else{
			/*series=[{                                 //指定数据列
		        name: '选项',                          //数据列名
		        data: seriesData                      //数据
		    }];*/
			//series = seriesData;
		   series = [{
		        //name: '选项',
		        name: tagText,
		        //type: 'bar',
		        type: seriesType,
		       // barWidth: 80,
		        //data: [5, 20, 36, 10, 10, 20]
		        //data: [5, 20]
		        data: seriesData
		    }]
		}

	}

	return [categories,series,tagText,legendData];
}


function higColumnChart(resultJson,quId){
	//$("#"+chartdivId).css({"margin-top":"10px"});
	var chartdivId="column_chart_"+quId;
	$("#amchart_"+quId).prepend("<div id='"+chartdivId+"' class=\"higChartSvg\"></div>");
	$("#"+chartdivId).css({"height": "300px"});
	//$("#"+chartdivId).css({"width": "800px"});

	var quItemBody=$("#quTr_"+quId);
	var quTitle=quItemBody.find(".quCoTitleText").text();

	var quTypeName=quItemBody.find("input[name='quTypeCnName']").val();

	var datas=getHighchartsData(quItemBody,"column");
	var categories=datas[0];
	var series=datas[1];
	var tagText=datas[2];
	var legendData = datas[3];

	var myChart = echarts.init($('#'+chartdivId)[0],"shine");
	// 指定图表的配置项和数据
	var option = {
	    title: {
	        text: quTitle,
	   //     subtext: '来自调问 www.diaowen.net',
	        top: 8,
	       /*  left: 10, */
	        textStyle: {
	        	fontSize: 16
	        },
			x:'center'
	    },
	  	toolbox: {
	        show : true,
	        feature : {
	           // magicType : {show: true, type: ['line', 'bar']},
	            saveAsImage : {show: true}
	        }
	    },
	    color: ['#7cb5ec'],
	    backgroundColor: '#fff',
	    tooltip : {
	        trigger: 'axis',
	        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
	            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
	        },
	    	top:20
	    },
	  	grid: {
	        left: '3%',
	        right: '3%',
	        bottom: '5%',
	        containLabel: true
	    },
	    xAxis: {
	        //data: ["衬衫","羊毛衫","雪纺衫","裤子","高跟鞋","袜子"],
	    	type : 'category',
	    	data: categories,
	    	axisTick: {
	    		alignWithLabel: true
	    	},
	    	nameGap: 20,
	    	axisLabel: {
	    		//rotate: 5,
	    		interval: 0,
	    		margin: 15
	    	},
	    	axisLine:{
	    		show: false,
	    		lineStyle:{width: 1}
	    	},
	    	axisTick: {
	    		show: false
	    	}/*,
	    	splitLine: {show:true}

	    	splitArea:{
	    		interval: 0,
	    		show : true
	    	} */
	    },
	    yAxis: {
	    	//minInterval: 2,
	    	splitNumber: 5,
	    	//nameLocation: 'middle',
	    	//boundaryGap: ['30%', '30%'],
	    	//nameGap: 20,
	    	type:'value',
	    	axisLine: {
	    		show: false
	    	},
	    	axisTick: {
	    		show: false
	    	},
	    	name: tagText
	    },
	    legend: {
	    	bottom: 0,
	        data:legendData
	    },
	    series: series
	};

	/*
	option = {
		    tooltip: {
		        trigger: 'axis'
		    },
		    toolbox: {
		        feature: {
		            dataView: {show: true, readOnly: false},
		            magicType: {show: true, type: ['line', 'bar']},
		            restore: {show: true},
		            saveAsImage: {show: true}
		        }
		    },
		    backgroundColor: '#fff',
		    color: ['#7cb5ec'],
		    legend: {
		    	top: 10,
		        data:['蒸发量','蒸发量1','蒸发量2']
		    },
		    xAxis: [
		        {
		            type: 'category',
		            data: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
		           // data: series
		        }
		    ],
		    yAxis: [
		        {
					type: 'value',
		            name: '水量',
		            min: 0,
		            max: 250,
		            interval: 50,
		            axisLabel: {
		                formatter: '{value} ml'
		            }
		        }
		    ],
		    series: [
		        {
		            name:'蒸发量',
		            type:'bar',
		            data:[2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3]
		        },
		        {
		            name:'降水量',
		            type:'bar',
		            data:[2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6.0, 2.3]
		        },
		        {
		            name:'降水2量',
		            type:'bar',
		            data:[2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6.0, 2.3]
		        }
		    ]
		};
	 */
	// 使用刚指定的配置项和数据显示图表。
	myChart.setOption(option);
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
	var categories = datas[0]
	var legendData = datas[3];

    // Build the chart
    var myChart = echarts.init($('#'+chartdivId)[0],"shine");
    var option = {
		    title : {
		    	text: quTitle,
		    	//subtext: '来自调问 www.diaowen.net',
			    top: 8,
			       /*  left: 10, */
			        textStyle: {
			        	fontSize: 16
			        },
		        x:'center'
		    },
		    tooltip : {
		        trigger: 'item',
		        formatter: "{a} <br/>{b} : {c} ({d}%)"
		    },
		    backgroundColor: '#fff',
		    toolbox: {
		        show : true,
		        feature : {
		            saveAsImage : {show: true}
		        }
		    },
		    legend: {
		       // orient: 'vertical',
		        x : 'center',
		        y : 'bottom',
		        data: categories//['直接访问','邮件营销','联盟广告','视频广告','搜索引擎']
		    },
		    series :// series
		    	[
		        {
		            //name: '访问来源',
		            name: tagText,
		            type: 'pie',
		            radius : '55%',
		            center: ['50%', '60%'],
		            data: series
		            /*[
		                {value:335, name:'直接访问'},
		                {value:310, name:'邮件营销'},
		                {value:234, name:'联盟广告'},
		                {value:135, name:'视频广告'},
		                {value:1548, name:'搜索引擎'}
		            ]*/,
		            itemStyle: {
		                emphasis: {
		                    shadowBlur: 10,
		                    shadowOffsetX: 0,
		                    shadowColor: 'rgba(0, 0, 0, 0.5)'
		                }
		            }
		        }
		    ]
		};
    myChart.setOption(option);

}



function higBarChart(resultJson,quId){

	//$("#"+chartdivId).css({"margin-top":"10px"});
	var chartdivId="bar_chart_"+quId;
	$("#amchart_"+quId).prepend("<div id='"+chartdivId+"' class=\"higChartSvg\"></div>");
	$("#"+chartdivId).css({"height": "300px"});

	var quItemBody=$("#quTr_"+quId);
	var quTitle=quItemBody.find(".quCoTitleText").text();
	var quTypeName=quItemBody.find("input[name='quTypeCnName']").val();

	var datas=getHighchartsData(quItemBody,"BAR");
	var categories=datas[0];
	var series=datas[1];
	var tagText=datas[2];
	var legendData = datas[3];

	var myChart = echarts.init($('#'+chartdivId)[0],"shine");
	var option = {
	    title: {
	    	text: quTitle,
	      //  subtext: '来自调问 www.diaowen.net',
	        top: 8,
	       /*  left: 10, */
	        textStyle: {
	        	fontSize: 16
	        },
			x:'center'
	    },
	    tooltip: {
	        trigger: 'axis',
	        axisPointer: {
	            type: 'shadow'
	        }
	    },
	    backgroundColor: '#fff',
	    color: ['#7cb5ec'],
	    legend: {
	        data: ['2011年', '2012年']
	    },
	    grid: {
	        left: '3%',
	        right: '4%',
	        bottom: '10%',
	        containLabel: true
	    },
	    toolbox: {
	        show : true,
	        feature : {
	            saveAsImage : {show: true}
	        }
	    },
	    xAxis: {
	       /*  type: 'value',
	        boundaryGap: [0, 0.01] */
	        type : 'value',
	    	axisTick: {
	    		alignWithLabel: true
	    	},
	    	nameGap: 12,
	    	axisLabel: {
	    		//rotate: 5,
	    		interval: 0,
	    		margin: 15
	    	},
	    	axisLine: {
	    		show: false,
	    		lineStyle:{width: 1}
	    	},
	    	axisTick: {
	    		show: false
	    	},
	    	splitLine: {show:true}
	    },
	    yAxis: {
	        type: 'category',
	        //data: ['巴西','印尼','美国','印度','中国','世界人口(万)']
	    	data: categories
	    },
	    legend: {
	    	bottom: 2,
	        data:legendData
	    },
	    series: series
	    /*
	    	[
	        {
	            name: '2011年',
	            type: 'bar',
	            data: [18203, 23489, 29034, 104970, 131744, 630230]
	        },
	        {
	            name: '2012年',
	            type: 'bar',
	            data: [19325, 23438, 31000, 121594, 134141, 681807]
	        }
	    ]*/
	};
	myChart.setOption(option);
}




function higLineChart(resultJson,quId){
	//根据quId得到数据对象，并且解析

	var chartdivId="line_chart_"+quId;
	$("#amchart_"+quId).prepend("<div id='"+chartdivId+"' class=\"higChartSvg\"></div>");
	$("#"+chartdivId).css({"height": "300px"});

	var quItemBody=$("#quTr_"+quId);
	var quTitle=quItemBody.find(".quCoTitleText").text();
	var quTypeName=quItemBody.find("input[name='quTypeCnName']").val();

	var datas=getHighchartsData(quItemBody,"Line");
	var categories=datas[0];
	var series=datas[1];
	var tagText=datas[2];
	var legendData = datas[3];

	var myChart = echarts.init($('#'+chartdivId)[0],"shine");
	// 指定图表的配置项和数据
	var option = {
	    title: {
	        text: quTitle,
	     //   subtext: '来自调问 www.diaowen.net',
	        top: 8,
	       /*  left: 10, */
	        textStyle: {
	        	fontSize: 16
	        },
			x:'center'
	    },
	    toolbox: {
	        show : true,
	        feature : {
	         //   magicType : {show: true, type: ['line', 'bar']},
	            saveAsImage : {show: true}
	        }
	    },
	    backgroundColor: '#fff',
	    color: ['#3398DB'],
	    tooltip : {
	        trigger: 'axis',
	        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
	            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
	        },
	    	top:20
	    },
	  	grid: {
	        left: '3%',
	        right: '4%',
	        bottom: '3%',
	        containLabel: true
	    },
	    xAxis: {
	        //data: ["衬衫","羊毛衫","雪纺衫","裤子","高跟鞋","袜子"],
	    	/* type : 'category',
	    	data: categories,
	    	axisLine:{
	    		lineStyle:{width: 1}
	    	} */
		    type : 'category',
	    	data: categories,
	    	axisTick: {
	    		alignWithLabel: true
	    	},
	    	nameGap: 20,
	    	axisLabel: {
	    		//rotate: 5,
	    		interval: 0,
	    		margin: 15
	    	},
	    	axisLine:{
	    		show: false,
	    		lineStyle:{width: 1}
	    	},
	    	axisTick: {
	    		show: false
	    	},
	    	splitLine: {show:true}
	    },
	    yAxis: {
	    	minInterval: 1,
	    	name:'次数'
	    },
	    legend: {
	    	bottom: 0,
	        data:legendData
	    },
	    series: series
	   /*
	    series: [{
	        name: '选择',
	        type: 'line',
	        barWidth: 80,
	        //data: [5, 20, 36, 10, 10, 20]
	        //data: [5, 20]
	        data: series
	    }]
	    */
	};
	// 使用刚指定的配置项和数据显示图表。
	myChart.setOption(option);

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
.suQuTable{
	/* border-left: 1px dashed #c5b6b6! important;
	border-right: 1px dashed #c5b6b6! important;
	border-bottom: 1px dashed #c5b6b6! important; */
	border: 1px solid #c5b6b6! important;
	width: 100%;
}
.anColumnTable{
	width: 100%;
}
.quTrOptions  td{
	border-top: 1px dashed #c5b6b6! important;
}
.rowItemTr td{
	border-top: 1px solid #c5b6b6! important;
}
.columnItemTr td{
	border-top: 1px dashed #c5b6b6! important;
}

.suQuTable .quTrOptions:FIRST-CHILD  td,.suQuTable .rowItemTr:FIRST-CHILD  td,.suQuTable .columnItemTr:FIRST-CHILD td {
 	border-top: none! important;
}
.r-qu-body-title .quCoNum {
    float: left;
    padding: 5px 0px;
    padding-left: 8px;
}
.r-qu-body-title .quCoTitleText {
    float: left;
    padding: 5px 3px;
    cursor: text;
    table-layout: fixed;
    word-break: break-all;
    display: inline-block;
}

.higChartSvg{

}
</style>
</head>
<body>
	<input type="hidden" id="id" name="id" value="${surveyId }">

	<div class="creatgeSurveyStepBody">
		<div class="creatgeSurveyStepContent bodyCenter">
				<ul class="createSsUl">
					<li><a href=""  class="clickHideMenu csscStep csscStep4"><i class="fa fa-magic" aria-hidden="true"></i><span class="csscStepCenter">设计问卷</span><i class="fa fa-caret-down" aria-hidden="true"></i></a>
					<div class="a-w-sel">
		            	<div class="w-sel" style="margin-top: 4px;">
		                	<div class="selc">
		                    	<div class="selcc tbtag">
		                            <div class="seli"><a class="nx-1 sur_collectSet" href="#collectSet">收集规则</a></div>
		                            <div class="seli"><a class="nx-6 sur_edit" href="${ctx }/design/my-survey-design.action?surveyId=${surveyId}">问卷编辑</a></div>
		                        </div>
		                    </div>
		                </div>
		            </div>
				</li>
				<li><span class="csscStep csscStepLine"><span class="csscStepLeft">&nbsp;</span><span class="csscStepRight">&nbsp;</span></span></li>
				<li><a href="${ctx }/design/my-collect.action?surveyId=${surveyId }"  class="clickHideMenu csscStep csscStep5"><i class="fa fa-chain" aria-hidden="true"></i> <span class="csscStepCenter">数据收集</span><i class="fa fa-caret-down" aria-hidden="true"></i></a>
					<div class="a-w-sel">
		            	<div class="w-sel" style="margin-top: 4px;">
		                	<div class="selc">
		                    	<div class="selcc tbtag">
		                            <div class="seli"><a class="nx-1" href="${ctx }/design/my-collect.action?surveyId=${surveyId}">答卷地址</a></div>
		                            <div class="seli"><a class="nx-2" href="">社交分享</a></div>
		                            <div class="seli"><a class="nx-3" href="${ctx }/design/my-collect.action?surveyId=${surveyId}&tabId=sitecomp">网站组件</a></div>
		                            <div class="seli"><a class="nx-3" href="${ctx }/design/my-collect.action?surveyId=${surveyId}&tabId=weixin">微信收集</a></div>
		                        </div>
		                    </div>
		                </div>
		            </div>
				</li>
				<li><span class="csscStep csscStepLine"><span class="csscStepLeft">&nbsp;</span><span class="csscStepRight">&nbsp;</span></span></li>
				<li><a href="${ctx }/da/survey-report!defaultReport.action?surveyId=${surveyId}"  class="clickHideMenu csscStep csscStep6 active"> <i class="fa fa-line-chart" aria-hidden="true"></i> <span class="csscStepCenter">数据分析</span> <i class="fa fa-caret-down" aria-hidden="true"></i></a>
				</li>
			</ul>
		</div>
	</div>

	<div style="">
		<div class="main-tabs-content bodyCenter">
			<div class="tab-content">
				<div class="tab-content-collectTab">
					<a href="${ctx }/da/survey-report!defaultReport.action?surveyId=${surveyId}" class="collectTab tabItem_1 active">  <i class="fa fa-area-chart" aria-hidden="true"></i>  <span>默认报告</span></a>
					<a href="${ctx }/da/my-survey-answer.action?surveyId=${surveyId}" class="collectTab tabItem_3"> <i class="fa fa-database" aria-hidden="true"></i> <span>原始数据</span></a>
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
						参与人数：<span class="collectInfoSpan">${directory.answerNum }</span>
						</span>
						<span class="surveyCollectInfoRight">
						创建时间：<span class="collectInfoSpan"><fmt:formatDate value="${directory.createDate }" pattern="yyyy年MM月dd日 HH:mm"/></span>
						</span>
					</div>
				</div>

				<div class="surveyCollectMiddleContent">
					<div style="padding: 15px 25px;overflow: auto;">
							<div style="overflow: auto;">
								<div style="float: left;" >
									<a href="${ctx }/da/survey-report!defaultReport.action?surveyId=${surveyId }" class="dw_btn025 tabpic active"><i class="fa fa-refresh"></i>&nbsp;刷新</a>
									<%-- <a href="${ctx }/da/survey-report!lineChart.action?surveyId=${surveyId }" class="dw_btn025 linepic" style="margin-left: 10px;"><i class="fa fa-bar-chart"></i>&nbsp;柱状图</a>
									<a href="${ctx }/da/survey-report!pieChart.action?surveyId=${surveyId }" class="dw_btn025 piepic " style="margin-left: 10px;"><i class="fa fa-pie-chart"></i>&nbsp;饼状图</a> --%>
								</div>
								<div style="float: right;" >
									<a href="${ctx }/da/my-survey-answer!exportXLS.action?surveyId=${surveyId }" class="dw_btn025"><i class="fa fa-download"></i>下载数据</a>
									<!-- <a href="" class="dw_btn025"><i class="fa fa-share"></i>分享</a>-->
									</div>
							</div>
							<div style="padding-top:8px;">
								<div class="" style="border: 1px solid #D1D6DD;padding: 0px;">
									<table id="content-tableList" width="100%"  cellpadding="0" cellspacing="0">
								<c:forEach items="${surveyStats.questions }" var="en" varStatus="i">

								<c:if test="${en.quType ne 'PARAGRAPH' && en.quType ne 'PAGETAG' }">
								<tr id="quTr_${en.id }">
									<td colspan="3">
										<div class="surveyResultQu">
											<input type="hidden" name="quId" value="${en.id }">
											<input type="hidden" name="quType" value="${en.quType }">
											<input type="hidden" name="quAnCount" value="${en.anCount }">
											<%-- <input type="hidden" name="quTitle" value="${en.quTitle }"> --%>
											<input type="hidden" name="quTypeCnName" value="${en.quType.cnName }">



							<div class="r-qu-body-title">
								<div class="quCoNum">${i.count }、</div>
								<div class="quCoTitleText">${en.quTitle }[${en.quType.cnName }]</div>
							</div>
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
											<table class="suQuTable" border="0" cellpadding="0" cellspacing="0" style="border: none! important;margin-top: 8px;">
													<tr>
														<td width="15px">&nbsp;</td>
														<td class="bfbTd">回答数：${en.anCount }条&nbsp;&nbsp;<a href="${ctx}/design/qu-fillblank!answers.action?quId=${en.id}&surveyId=${surveyId}" class="fb_answer" >查看</a></td>
														<td colspan="4">&nbsp;</td>
													</tr>
												</table>
											<!-- <div class="reportPic"></div> -->
											<div style="clear:both;"></div>
										</c:when>
										<%--多行填空 --%>
										<c:when test="${en.quType eq 'ANSWER' }">
											<table class="suQuTable" border="0" cellpadding="0" cellspacing="0" style="border: none;">
													<tr>
														<td width="15px">&nbsp;</td>
														<td class="bfbTd">回答数：${en.anCount }&nbsp;<a href="#">查看</a></td>
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
													<tr class="quTrOptions">
														<td width="15px">&nbsp;</td>
														<td width="520px">${quEn.optionName }</td>
														<td class="bfbTd">回答数：${quEn.anCount }条&nbsp;&nbsp;<a href="${ctx}/design/qu-multi-fillblank!answers.action?quItemId=${quEn.id}&surveyId=${surveyId}">查看</a></td></td>
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
														<tr class="columnItemTr">
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
													<tr class="columnItemTr">
														<td colspan="6">
														<table class="anColumnTable" style="width: 100%;">
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

														<%--
														<c:forEach items="${en.rows }" var="rowItem" varStatus="rowI">
														<tr class="rowItemTr">
															<td width="15px">&nbsp;
																<div class="rowItemOptionName" style="display: none;">${rowItem.optionName }</div>
																<input type="hidden" name="rowItemAnCount" value="${rowItem.anCount }">
															</td>
															<td class="quChenRowTd" colspan="5"><label class="editAble quCoOptionEdit" style="font-size: 14px;">${rowI.count}、${rowItem.optionName }</label></td>
														</tr>
														<tr class="columnItemTr">
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
																	<div class="columnItemOptionName" style="display: none;">${columnItem.optionName }</div>
																	<input type="hidden" name="columnItemAnCount" value="0" id="coumneItemAnCount${en.quType }${i.count }_${rowI.count}_${colI.count}">
															 --%>


													<c:forEach items="${en.rows }" var="rowItem" varStatus="rowI">
													<tr class="rowItemTr">
															<td width="15px">&nbsp;
																<div class="rowItemOptionName" style="display: none;">${rowItem.optionName }</div>
																<input type="hidden" name="rowItemAnCount" value="${rowItem.anCount }">
															</td>
															<td class="quChenRowTd" colspan="5"><label class="editAble quCoOptionEdit" style="font-size: 14px;">${rowI.count}、${rowItem.optionName }</label></td>
													</tr>
													<tr class="columnItemTr">
															<td colspan="6">
															<table class="anColumnTable">
													<c:forEach items="${en.columns }" var="columnItem" varStatus="colI">
													<tr class="columnItemTr">
														<td width="15px">&nbsp;</td>
														<td width="520" class="quChenRowTd" style="padding-left: 15px;"><label class="editAble quCoOptionEdit">${columnItem.optionName }</label></td>
														<td width="180px"><div id="bfbTd${en.quType }${i.count }_${rowI.count}_${colI.count }" class="progressbarDiv progress${rowI.index }"></div></td>
														<td width="60px" align="right" id="bfbNum${en.quType }${i.count }_${rowI.count}_${colI.count}" class="bfbTd">0%</td>
														<%-- <td align="left" id="bfbAnCount${en.quType }${i.count }_${rowI.count}_${colI.count}" class="tdAnCount">&nbsp;0次</td> --%>
														<td align="left" class="tdAnCount">&nbsp;&nbsp;平均</td>
														<td width="40px">&nbsp;</td>
														<td>
																<div class="columnItemOptionName" style="display: none;">${columnItem.optionName }</div>
																<input type="hidden" name="columnItemAnCount" value="0" id="coumneItemAnCount${en.quType }${i.count }_${rowI.count}_${colI.count}">

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

																	$("#coumneItemAnCount${en.quType }${i.count }_${rowI.count}_${colI.count}").val(avgScore);
																</script>
															</c:if>
														</c:forEach>
														</td>
													</tr>
													</c:forEach>
													</table>
													</td>
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
										<%--矩阵填空题 --%>
										<c:when test="${en.quType eq 'CHENFBK' }">
												<table class="suQuTable" border="0" cellpadding="0" cellspacing="0" >
													<c:forEach items="${en.rows }" var="rowItem" varStatus="rowI">
													<tr class="rowItemTr">
														<td width="15px">&nbsp;</td>
														<td class="quChenRowTd" colspan="4"><label class="editAble quCoOptionEdit" style="font-size: 14px;">${rowI.count}、${rowItem.optionName }</label></td>
													</tr>
													<c:forEach items="${en.columns }" var="columnItem" varStatus="colI">
													<tr class="columnItemTr">
														<td width="15px">&nbsp;</td>
														<td width="520" class="quChenRowTd" style="padding-left: 15px;"><label class="editAble quCoOptionEdit">${columnItem.optionName }</label></td>
														<td width="120px" align="left" id="bfbNum${en.quType }${i.count }_${rowI.count}_${colI.count}" class="bfbTd">0%</td>
														<td align="left" id="bfbAnCount${en.quType }${i.count }_${rowI.count}_${colI.count}" class="tdAnCount">&nbsp;0次</td>
														<td width="40px">&nbsp;</td>
														<td>
														<c:forEach items="${en.anChenFbks }" var="anItem">
															<c:if test="${anItem.quRowId eq rowItem.id && anItem.quColId eq columnItem.id }">
																<script type="text/javascript">
																	$("#bfbNum${en.quType }${i.count }_${rowI.count}_${colI.count}").html("回答数：${anItem.anCount}条");
																	$("#bfbAnCount${en.quType }${i.count }_${rowI.count}_${colI.count}").html("&nbsp;&nbsp;<a href=\"#\">查看</a>");
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
										<c:when test="${en.quType eq 'ORDERQU' }">
											<table class="suQuTable" border="0" cellpadding="0" cellspacing="0">
												<c:forEach items="${en.quOrderbys }" var="quEn" varStatus="quI">
														<tr  class="quTrOptions" >
															<td width="15px">&nbsp;</td>
															<td width="520px" class="optionName" >${quEn.optionName }</td>
															<%-- <td width="180px"><div id="bfbTd${en.quType }${i.count }_${quI.count}" class="progressbarDiv progress${quI.index }"></div></td>
															<td width="50px" align="right" id="bfbNum${en.quType }${i.count }_${quI.count}" class="bfbTd">0%</td> --%>
															<td colspan="3" align="left" class="tdAnCount">&nbsp;&nbsp;排第&nbsp;${quI.count }&nbsp;名<%-- （${quEn.anOrderSum }） --%></td>
															<td width="40px">&nbsp;
															<input type="hidden" name="quItemAnCount" value="${quI.count }">
															</td>
														</tr>
												</c:forEach>
											</table>
											<div class="reportPic">
												<div class="chartBtnEvent">
												<a href="#" class="dw_btn026 columnchart_pic"><i class="fa fa-bar-chart"></i>柱状图</a>
												<!-- <a href="#" class="dw_btn026 piechart_pic"><i class="fa fa-pie-chart"></i>饼图</a> -->
												<a href="#" class="dw_btn026 barchart_pic"><i class="fa fa-tasks"></i>条形图</a>
												<a href="#" class="dw_btn026 linechart_pic"><i class="fa fa-line-chart"></i>折线图</a>
												</div>
												<div style="clear: both;"></div>
												<div id="amchart_${en.id }" ></div>
											</div>
											<div style="clear:both;"></div>
										</c:when>
								</c:choose>

							</div>
						</div>

									</td>
								</tr>
								</c:if>

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
$(document).ready(function(){
	//.quTrOptions  td
	currentMenu("mysurvey");
});
</script>
</body>
</html>
