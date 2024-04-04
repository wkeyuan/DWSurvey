function checkAnswerSurvey(sid){
  var ctx=$("#ctx").val();
  var ruleCode = $("#ruleCode").val();
  var wxCode = $("#wxCode").val();
  var url=ctx+"/response/check-answer-survey.do";
  var data="surveyId="+$("#id").val()+"&ruleCode="+ruleCode+"&wxCode="+wxCode;
  $.ajax({
    url:url,
    data:data,
    type:"post",
    success:function(httpResult){
      // console.debug(httpResult);
      if(httpResult.resultCode==200){
        var data = httpResult.data;
        var answerCheck=data.answerCheck;
        var answerCheckCode=data.answerCheckCode;
        var imgCheckCode=data.imgCheckCode;
        if(!answerCheck){
          if(answerCheckCode===203){
            //跳转到指定地址
            window.location.href=data.redirectUrl;
          }else{
            //跳转到详情页
            if(ruleCode!=undefined && ruleCode!=""){
              // window.location.href="/#/diaowen-msg-code/"+sid+"/"+answerCheckCode+"/"+ruleCode;
              window.location.href="/static/diaowen/message.html?sid="+sid+"&respType="+answerCheckCode+"&pwdCode="+ruleCode;
            }else{
              // window.location.href="/#/diaowen-msg/"+sid+"/"+answerCheckCode;
              window.location.href="/static/diaowen/message.html?sid="+sid+"&respType="+answerCheckCode;
            }
          }
        }
        if(imgCheckCode){
          $("#jcaptchaImgBody").show();
        }
      }
    }
  });
}
function refreshAutoCode(codeImgId){
  var ctx = $("#ctx").val();
  $("#"+codeImgId).attr("src",ctx+"/jcap/jcaptcha.do");
}
