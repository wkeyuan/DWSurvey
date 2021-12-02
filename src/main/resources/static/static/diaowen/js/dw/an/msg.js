$(document).ready(function(){
    var sid = $.getUrlParam("sid");
    var respType = $.getUrlParam("respType");
    var answerId = $.getUrlParam("answerId");
    var pwdCode = $.getUrlParam("pwdCode");
    resultStatus2Msg(respType,sid,pwdCode);
    answerAfterData(respType,answerId,function(){
      querySurveyData(respType,sid);
    });
});

function resultStatus2Msg(resptype,sid,ruleCode) {
  var tempMsg = {};
  tempMsg.success = false;
  if(resptype==='1'){
    tempMsg.resultNote = '目前该问卷已暂停收集，请稍后再试!';
    tempMsg.resultColor = "#e70f0f";
  }else if(resptype==='2'){
    tempMsg.resultNote = '目前该问卷无法访问，请稍后再试!';
    tempMsg.resultColor = "#e70f0f";
  }else if(resptype==='3'){
    tempMsg.resultNote = '已经答过本问卷，请不要重复作答!';
    tempMsg.resultColor = "#e70f0f";
  }else if(resptype==='4'){
    tempMsg.resultNote = '验证码不正确，操作未成功!';
    tempMsg.resultColor = "#e70f0f";
    // setReqUrl({reqUrl:'/#/diaowen/'+sid,urlText:'重新答卷'})
    setReqUrl({reqUrl:'/static/diaowen/diaowen.html?sid='+sid,urlText:'重新答卷'})
  }else if(resptype==='5'){
    tempMsg.resultNote = '发生未知异常，操作未成功!';
    tempMsg.resultColor = "#e70f0f";
  }else if(resptype==='6'){
    tempMsg.resultNote = '答卷提交成功，感谢您的支持!';
    tempMsg.resultColor = "#1890ff";
    tempMsg.success = true;
  }else if(resptype==='7'){
    tempMsg.resultNote = '答卷已经达到收集上限，感谢您的支持!（数据不被保存）';
    tempMsg.resultColor = "#e70f0f";
  }else if(resptype==='8'){
    tempMsg.resultNote = '问卷未到开始时间，感谢您的支持!（数据不被保存）';
    tempMsg.resultColor = "#e70f0f";
  }else if(resptype==='9'){
    tempMsg.resultNote = '问卷已经到了截止时间，感谢您的支持!（数据不被保存）';
    tempMsg.resultColor = "#e70f0f";
  }else if(resptype==='10'){
    tempMsg.resultNote = '该问卷已删除，无法作答!';
    tempMsg.resultColor = "#e70f0f";
  }else if(resptype==='21'){
    tempMsg.resultNote = '不在本次调研范围内，无法作答!';
    tempMsg.resultColor = "#e70f0f";
  }else if(resptype==='23'){
    tempMsg.resultNote = '超过单个IP答卷次数限制!';
    tempMsg.resultColor = "#e70f0f";
  }else if(resptype==='106'){
    tempMsg.resultNote = '口令超过使用次数!';
    tempMsg.resultColor = "#e70f0f";
  }else if(resptype==='303'){
    tempMsg.resultNote = '需要口令才可以回答问卷!';
    tempMsg.resultColor = "#e70f0f";
  }else if(resptype==='201'){
    var qrSrc = "/api/dwsurvey/anon/response/answerTD.do?sid="+sid;
    if(ruleCode!==undefined && ruleCode!==""){
      qrSrc = "/api/dwsurvey/anon/response/answerTD.do?sid="+sid+"&ruleCode="+ruleCode;
    }
    tempMsg.resultNote = "<div><img className=\"mobileAnswerQR\" src="+qrSrc+" style=\"padding:0,background:'white'\" /><div style=\"padding:10\">请使用微信扫码答卷</div></div>";
    tempMsg.resultColor = "#1890ff";
  }else if(resptype==='202'){
    tempMsg.resultNote = '超过有效答卷次数!';
    tempMsg.resultColor = "#e70f0f";
  }
  tempMsg.respType = resptype;
  setResultMsg(tempMsg);
}

function setResultMsg(tempMsg){
  $("#resultNote").html(tempMsg.resultNote);
  $("#resultNote").css("color",tempMsg.resultColor);
  $("#respType").html("状态码："+tempMsg.respType);
}

function querySurveyData(respType,sid){
  if (sid != null) {
    querySurvey(sid,function(httpResult){
      if(httpResult!=null && httpResult.hasOwnProperty('resultCode') && httpResult.resultCode === 200 ){
        var resultData = httpResult.data;
        // console.debug(resultData);
        $("#surveyName").html(resultData.surveyName);
        if(resultData.hasOwnProperty("surveyDetail")) {
          var surveyDetail = resultData.surveyDetail;
          var reqUrlType = surveyDetail.reqUrlType;

          if(respType==='6'){
            clearSurveyStorage(resultData.id);
          }

          if(reqUrlType===1) {
            window.location.href=surveyDetail.reqUrl;
          }
          if(reqUrlType===2 && respType==='6'){
            // setReqUrl(surveyDetail.reqUrl);
            setReqUrl({reqUrl:surveyDetail.reqUrl,urlText:surveyDetail.urlText})
          }
        }
      }
    });
  }
}

function querySurvey(sid,callback){
  var url = '/api/dwsurvey/anon/response/survey_info.do';
  var data = "sid="+sid;
  $.ajax({
    url:url,
    data:data,
    // type:"json",
    success:function(httpResult){
      // console.debug(httpResult);
      if(callback!=null){
        callback(httpResult);
      }
    }
  });
}

function setReqUrl(reqObj){
  var reqUrlDiv = $("#reqUrlDiv");
  reqUrlDiv.empty();
  reqUrlDiv.append("<a href=\""+reqObj.reqUrl+"\">"+reqObj.urlText+"</a>");
}

// AnserAfter
function answerAfterData(respType,answerId,callback){
  if (respType==='6' && answerId != null) {
    // console.debug(answerId);
    answerAfter(answerId,function(httpResult){
      // console.debug(httpResult);
      if(httpResult!=null && httpResult.hasOwnProperty('resultCode') && httpResult.resultCode === 200 ){
        var resultData = httpResult.data;
        if(resultData!=null) {
          var surveyId = resultData.surveyId;
          var totalScoreStatus = resultData.totalScoreStatus;
          var totalScore = resultData.totalScore;
          // /static/diaowen/answer-data.html?id=fd258239-4cc1-433b-abb1-aabd2f7198cc&surveyId=1a020ff9-0060-4cd3-aac7-7cbbb4a5de19&tag=1
          var sssw = "<a href=\"/static/diaowen/answer-data.html?id="+answerId+"&surveyId="+surveyId+"&tag=1\" >总得分："+totalScore+"分，点击看详情</a>";
          if(totalScoreStatus!=null && totalScoreStatus===1) setTotalScoreText(sssw);
        }
        if(callback!=null){
          callback();
        }
      }
    });
  }else{
    if(callback!=null){
      callback();
    }
  }
}

function answerAfter(answerId,callback){
  var url = '/api/dwsurvey/anon/response/answer-after.do';
  var data = "answerId="+answerId;
  $.ajax({
    url:url,
    data:data,
    // type:"json",
    success:function(httpResult){
      console.debug(httpResult);
      if(callback!=null){
        callback(httpResult);
      }
    }
  });
}

function setTotalScoreText(sssw){
  $("#totalScore").html(sssw);
}
