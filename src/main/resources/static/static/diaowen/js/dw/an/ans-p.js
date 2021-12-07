/**
 *
 * DWSurvey 3.0 中关于问卷设计Javascript
 *
 * @desc: design survey
 * @author: keyuan（@keyuan, keyuan258@gmail.com）
 * @github: https://github.com/wkeyuan/DWSurvey
 *
 * Copyright 2012, 2017 调问问卷(DWSurvey,http://dwsurvey.net)
 *
 */
$(document).ready(function(){

  var ctx=$("#ctx").val();
  var sid = $.getUrlParam("sid");
  var surveyId = $.getUrlParam("surveyId");
  var tag = $.getUrlParam("tag");
  var ruleCode =  $.getUrlParam("ruleCode");
  $("#sid").val(sid);
  $("#id").val(surveyId);
  $("#tag").val(tag);
  $("#ruleCode").val(ruleCode);
  if(tag!="" && (tag==="p" || tag==="s")){
    $("#preview_head_top").show();
  }else{
    $("#preview_head_top").remove();
  }
  $.answerPage("p",sid,function(){
    querySurveyAll(function(){
      sww();
      if(!$("#preview_head_top")[0]){
        checkAnswerSurvey(sid);
      }
    });
  });

});

function sww() {


  //分页设置 nextPage_a prevPage_a
  $(".nextPage_a").click(function(){
    if(validateForms()){
      var thParent=$(this).parent();
      var nextPageNo=thParent.find("input[name='nextPageNo']").val();
//		$(".li_surveyQuItemBody").hide();
//		$(".surveyQu_"+nextPageNo).fadeIn("slow");
      //$(window).scrollTop(10);
      var curPageNo = parseInt(nextPageNo)-1;
      $(".surveyQu_"+curPageNo).hide();
      var nextPageQus = $(".surveyQu_"+nextPageNo);
      $.each(nextPageQus,function(){
        var quItemBody = $(this);
        var itemBodyClass = quItemBody.attr("class");
        var quType = quItemBody.find(".quType").val();
        if(quType==="PAGETAG" || quType==="submitSurveyBtn"){
          quItemBody.show();
        }
        if(itemBodyClass.indexOf("hidFor")<0){
          quItemBody.show();
        }
      });

      var nextPageItems = $(".surveyQu_"+nextPageNo+":visible");
      if(nextPageItems[0]&&nextPageItems.size()<=1){
        $(nextPageItems[0]).find(".nextPage_a").click();
      }

      $("html,body").animate({scrollTop:10},500);
    };
    return false;
  });
  $(".prevPage_a").click(function(){
    var thParent=$(this).parent();
    var prevPageNo=thParent.find("input[name='prevPageNo']").val();
    //$(".li_surveyQuItemBody").hide();
    //$(".surveyQu_"+prevPageNo).fadeIn("slow");
    var curPageNo = parseInt(prevPageNo)+1;
    $(".surveyQu_"+curPageNo).hide();
    var prevPageQus = $(".surveyQu_"+prevPageNo);
    $.each(prevPageQus,function(){
      var quItemBody = $(this);
      var itemBodyClass = quItemBody.attr("class");
      //alert(itemBodyClass.indexOf("hidFor"));
      var quType = quItemBody.find(".quType").val();
      if(quType==="PAGETAG"){
        quItemBody.show();
      }
      if(itemBodyClass.indexOf("hidFor")<0){
        quItemBody.show();
      }
    });

    $(window).scrollTop(10);
    return false;
  });

  //var prevHost="http://file.diaowen.net";
  var prevHost=$("#prevHost").val();


  $(".submitSurvey").click(function(){
    if(validateForms()){
      var thTxt = $(this).text();
      if($("#preview_head_top")[0]){
        alert("预览模式不可提交！");
        return false;
      }
      if(thTxt==="提 交"){
        $(".submitSurvey").css({"background-position": "0 -320px"});
        $(".submitSurvey").text("正在提交");
        setTimeout(function(){
          $(".submitSurvey").css({"background-position": "0 -200px"});
          $(".submitSurvey").text("提 交");
        },5000);
        $("#surveyForm").submit();
      }else{
        alert("5秒内禁止重复提交！");
      }
    }
    return false;
  });

  //评分题
  $(".scoreNumTable tr td").click(function(){
    //scoreNumInput
    var quScoreOptionTr=$(this).parents(".quScoreOptionTr");
    var tdText=$(this).text();
    quScoreOptionTr.find(".scoreNumTable tr td").css({"background":"white"});
    quScoreOptionTr.find(".scoreNumText").html($(this).text()+"&nbsp;分");

    $(this).prevAll().css({"background":""});
    $(this).css({"background":""});

    quScoreOptionTr.find(".scoreNumInput").val(tdText);
    quScoreOptionTr.find(".scoreNumText").html(tdText+"&nbsp;分");

    runlogic($(this));
    answerProgressbar($(this));
    validateCheck($(this).parents(".li_surveyQuItemBody"),false);
  });

  bindScoreNumTdHover();
  function bindScoreNumTdHover(){
    $(".scoreNumTable tr td").hover(function(){
      var quScoreOptionTr = $(this).parents(".quScoreOptionTr");
      var scoreNumInput=quScoreOptionTr.find(".scoreNumInput").val();
      if(scoreNumInput==""){
        $(this).prevAll().css({"background":""});
        $(this).css({"background":""});
        quScoreOptionTr.find(".scoreNumText").html($(this).text()+"&nbsp;分");
      }
    },function(){
      var quScoreOptionTr = $(this).parents(".quScoreOptionTr");
      var scoreNumInput=quScoreOptionTr.find(".scoreNumInput").val();
      if(scoreNumInput==""){
        $(this).prevAll().css({"background":"white"});
        $(this).css({"background":"white"});
        quScoreOptionTr.find(".scoreNumText").html("分");
      }
    });
  }

  //排序题
  //quOrderByCoItem
  bindQuOrderBySorts();
  function bindQuOrderBySorts() {
    var quOrderByCoItems=$(".quOrderByCoItem");
    $.each(quOrderByCoItems,function(){

      /* $(this).find( ".quOrderByLeftUl li" ).draggable({
          connectToSortable: $(this).find(".quOrderByTable td"),
          helper: "clone",
          zIndex:2000,
          //revert :true,
          start: function(event, ui) {
              var quOrderByCoItem=$(this).parents(".quOrderByCoItem");
              quOrderByCoItem.find( ".quOrderTabConnect" ).css({"background":"","border":"1px dotted red"});
          },
          drag: function(event, ui) {

          },
          stop: function(event,ui){
              var quOrderByCoItem=$(this).parents(".quOrderByCoItem");
              quOrderByCoItem.find( ".quOrderTabConnect" ).css({"background":"","border":"1px solid #dbdbdb"});
          }
      }); */

      $(this).find( ".quOrderByLeftUl li" ).sortable({
        zIndex:1000,
        scroll :false,
        opacity : 0.8,
        placeholderType:false,
        connectWith:$(this).find( ".quOrderByTable td" ),
        helper: function(event,ui){
          return "<label class='quOrderItemLabel'>"+$(this).text()+"</label>";
        },
        over:function(event,ui){

        },
        start: function(event, ui) {

        },
        drag: function(event, ui) {

        },
        stop: function(event,ui){
          $(".quOrderByTable td").css({"background":""});
          //ui.item.html("<label class='quOrderItemLabel'>"+ui.item.text()+"</label>");
          answerProgressbar($(this));
          validateCheck($(this).parents(".li_surveyQuItemBody"),false);
        }
      });
      var sortObjTempHtml=null;
      $(this).find( ".quOrderByTable td" ).sortable({
        //revert: true
        //dropOnEmpty:false,
        zIndex:1000,
        scroll :false,
        opacity : 0.9,
        placeholderType:true,
        placeholder:"qu-order-highlight",
        connectWith:$(this).find( ".quOrderByTable td" ),
        over:function(event,ui){
          $(".quOrderByTable td").css({"background":""});
          $(this).css({"background":"#FAEDC0"});
          var quOrderItemLabel=$(this).find("label.quOrderItemLabel");
          sortObjTempHtml="";
          if(quOrderItemLabel[0]){
            sortObjTempHtml=quOrderItemLabel.html();
          }
          /*sortObjTempHtml="";
           if(lastLabelHtml!=""){
              sortObjTempHtml="<label class='quOrderItemLabel'>"+$(this).find("label:last-child").html()+"</label>";
          } */
          //console.debug($(ui.helper).css("zIndex")+$(ui.helper).css("position"));
        },
        receive:function(event, ui){//当一个已连接的sortable对象接收到另一个sortable对象的元素后触发此事件。
          //判断如果是从右边新移入的，但当前td中已经有了，就交换到右边去

          var uiSenderClass=ui.sender.attr("class");
          ui.sender.empty();
          /* if(uiSenderClass.indexOf("quCoItemUlLi")<0){
              ui.sender.append(sortObjTempHtml);
          } */
          if(uiSenderClass.indexOf("quCoItemUlLi")<0){
            if(sortObjTempHtml!=""){
              ui.sender.append("<label class='quOrderItemLabel'>"+sortObjTempHtml+"</label>");
            }
          }else{
            if(sortObjTempHtml!=""){
              ui.sender.append("<label class='editAble quCoOptionEdit'>"+sortObjTempHtml+"</label>");
            }
          }

          $(this).empty();
          ui.item.clone().appendTo($(this));
          var quCoOptionEdit=$(this).find(".quCoOptionEdit");
          if(quCoOptionEdit[0]){
            quCoOptionEdit.removeClass();
            quCoOptionEdit.addClass("quOrderItemLabel");
          }
          $(".quOrderByTable td").css({"background":""});
          //更新排序ID  quCoItem quOrderByTableTr
          //bindQuOrderBySorts();
          var quColItem = $(".li_surveyQuItemBody .quCoItem");
          $.each(quColItem,function(){
            var quOrderyByTrs=$(this).find(".quOrderByTableTr");
            $.each(quOrderyByTrs,function(i){
              var quOrderItemHidInput=$(this).find(".quOrderItemHidInput");
              quOrderItemHidInput.val(i+1);
            });
          });
          /*var quOrderyByTrs=$(".quCoItem .quOrderByTableTr");
          $.each(quOrderyByTrs,function(i){
              var quOrderItemHidInput=$(this).find(".quOrderItemHidInput");
              quOrderItemHidInput.val(i+1);
          });*/
        },
        start: function(event, ui) {
          $(".quOrderByTable td").css({"background":""});
          $(this).css({"background":"#FAEDC0"});
        },
        drag: function(event, ui) {
          $(".quOrderByTable td").css({"background":""});
          $(this).css({"background":"#FAEDC0"});
        },
        stop: function(event,ui){
          $(".quOrderByTable td").css({"background":""});
        },
        out: function(event,ui){
          //$(".quOrderByTable td").css({"background":""});
        },
        activate: function( event, ui ) {
          //$(".quOrderByTable td").css({"background":""});
          //$(this).css({"background":"#FAEDC0"});
        }
      });
    });
  }

  /**初始化表单骓证配置**/
  function validateForms(){
    var result=true;
    var surveyQuItemBodys=$(".li_surveyQuItemBody");
    var firstError=null;
    $.each(surveyQuItemBodys,function(){
      var quItemBody=$(this);
      if(!validateCheck(quItemBody,true)){
        //定位到这题
        if(firstError==null){
          firstError=quItemBody;
        }
        result=false;
      }
    });
    if(firstError!=null){
      $(window).scrollTop(firstError.offset().top);
    }
    //
    if($("#jcaptchaImgBody").is(":visible")){
      var jcaptchaInput = $("input[name='jcaptchaInput']").val();
      if(jcaptchaInput===""){
        $("#jcaptchaImgBody .errorItem").show();
        result = false;
      }else{
        $("#jcaptchaImgBody .errorItem").hide();
      }
    }
    return result;
  }


  function checkoutData(checkType, value) {
    var validateStatus = true;
    if(value.length<=0){
      validateStatus = false;
    }
    if (checkType == "NO") {
      if(value.length<=0){
        validateStatus = false;
      }
    }else if (checkType == "EMAIL") {
      var __email = /^[a-zA-Z0-9.!#$%&'*+\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/;
      if (!__email.test(value)) {
        validateStatus = false;
      }
    } else if (checkType == "UNSTRCN") {
      var __cn = /^[\u3220-\uFA29]+$/;
      if (__cn.test(value)) {
        validateStatus = false;
      }
    } else if (checkType == "STRCN") {
      var __cn = /^[\u3220-\uFA29]+$/;
      if (!__cn.test(value)) {
        validateStatus = false;
      }
    } else if (checkType == "NUM") {

      // "number[/[+|-][int|integer|float|double|money|{位数正则}.{位数正则}]]"
//					var __regex = /^\d+(\.\d+)?$/;
      var __regex = /^(?:-?\d+|-?\d{1,3}(?:,\d{3})+)?(?:\.\d+)?$/;
      if (!__regex.test(value)) {
        validateStatus = false;
      }
    } else if (checkType == "DIGITS") {
      var __regex = /^\d+$/;
      if (!__regex.test(value)) {
        validateStatus = false;
      }

    } else if (checkType == "TELENUM") {
      var tel = /^\d{3,4}-{1}\d{7,9}-?\d{1,6}$/;
      var tel400800 = /^(400{1}-?[0-9]{3}-?[0-9]{4})|(800{1}-?[0-9]{3}-?[0-9]{4})$/
      if (!(tel.test(value) || tel400800.test(value))) {
        validateStatus = false;
      }
    } else if (checkType == "PHONENUM") {
      var length = value.length;
      var mobile = /^(((13[0-9]{1})|(15[0-9]{1})|(17[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
      if (!(length == 11 && mobile.test(value))) {
        validateStatus = false;
      }
    } else if (checkType == "TELE_PHONE_NUM") {

      var mobile = /^(((13[0-9]{1})|(15[0-9]{1})|(17[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
      var tel = /^\d{3,4}-{1}\d{7,9}-?\d{1,6}$/;
      var tel400800 = /^(400{1}-?[0-9]{3}-?[0-9]{4})|(800{1}-?[0-9]{3}-?[0-9]{4})$/
      if (!(tel400800.test(value) || tel.test(value) || mobile.test(value))) {
        validateStatus = false;
      }

    } else if (checkType == "DATE") {

      //日期的正则表达式
      /*var reg1 = /^[1-9]\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$/;//2014-01-01
                   var regExp1 = new RegExp(reg1);
                   var reg2 = /^(20|21|22|23|[0-1]\d):[0-5]\d:[0-5]\d$/;//12:00:00
                   var regExp2 = new RegExp(reg2);
                   var reg3 = /^[1-9]\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])\s+(20|21|22|23|[0-1]\d):[0-5]\d:[0-5]\d$/;//2014-01-01 12:00:00
                   var regExp3 = new RegExp(reg3);
                   if(!(regExp1.test(value) || regExp3.test(value))){
                   //						alert("日期格式不正确，正确格式为：2014-01-01");
                   errorType = "DATE";
                   validateStatus = false;
                   }*/
      if (/Invalid|NaN/.test(new Date(value).toString())) {
        validateStatus = false;
      }


    } else if (checkType == "IDENTCODE") {
      var check = /^\d{15}|(\d{17}(\d|x|X))$/.test(value);
      if (!(check && (value.length == 15 || value.length == 18))) {
        validateStatus = false;
      }

    } else if (checkType == "ZIPCODE") {

      var tel = /^[0-9]{6}$/;
      if (!tel.test(value)) {
        validateStatus = false;
      }

    } else if (checkType == "URL"){
      var strRegex = /^(?:(?:(?:https?|ftp):)?\/\/)(?:\S+(?::\S*)?@)?(?:(?!(?:10|127)(?:\.\d{1,3}){3})(?!(?:169\.254|192\.168)(?:\.\d{1,3}){2})(?!172\.(?:1[6-9]|2\d|3[0-1])(?:\.\d{1,3}){2})(?:[1-9]\d?|1\d\d|2[01]\d|22[0-3])(?:\.(?:1?\d{1,2}|2[0-4]\d|25[0-5])){2}(?:\.(?:[1-9]\d?|1\d\d|2[0-4]\d|25[0-4]))|(?:(?:[a-z\u00a1-\uffff0-9]-*)*[a-z\u00a1-\uffff0-9]+)(?:\.(?:[a-z\u00a1-\uffff0-9]-*)*[a-z\u00a1-\uffff0-9]+)*(?:\.(?:[a-z\u00a1-\uffff]{2,})).?)(?::\d{2,5})?(?:[/?#]\S*)?$/i;
      if (!strRegex.test(value)) {
        validateStatus = false;
      }
    }
    return validateStatus;
  }

  /******************************处理题目逻辑设置 **************************************/
  //处理题目逻辑设置


  /** 答题触发事件 **/

    //初始化 处理默认逻辑跳转为显示，则先隐藏元素
  var quLogics=$("#dwSurveyQuContent .quLogicItem");
  $.each(quLogics,function(){
    var loginItem=$(this);
    var cgQuItemId=loginItem.find(".cgQuItemId").val();
    var skQuId=loginItem.find(".skQuId").val();
    var logicId=loginItem.find(".logicId").val();
    var logicType=loginItem.find(".logicType").val();

    if(logicType==="2"){
      //逻辑类型为“显示”2  则初始化为隐藏
      var hidQuItemBody=$(".quId[value='"+skQuId+"']").parents(".li_surveyQuItemBody");
      hidQuItemBody.hide();
      hidQuItemBody.addClass("hidFor"+logicId);
      hidQuItemBody.find(".answerTag").attr("disabled",true);
    }
  });

  /** 单选与多选条件触发 自定义单选多选效果 操作结束后得调用逻辑判断 **/
  $(".dwQuOptionItemContent").click(function(){
    var thObj=$(this);
    var quItemBody=thObj.parents(".li_surveyQuItemBody");
    var quType=quItemBody.find(".quType").val();
    var dwQuInputLabel=thObj.find(".dwQuInputLabel");
    if("RADIO"===quType){
      //单选题
      quItemBody.find(".dwQuInputLabel").removeClass("checked");
      quItemBody.find("input[type='radio']").prop("checked",false);
      dwQuInputLabel.addClass("checked");
      thObj.find("input[type='radio']").prop("checked",true);

      runlogic(thObj.find("input[type='radio']"));
    }else if("CHECKBOX"===quType){
      //多选题
      //quItemBody.find(".dwQuInputLabel").removeClass("checked");
      var quInputLabelClass=dwQuInputLabel.attr("class");
      if(quInputLabelClass.indexOf("checked")>0){
        dwQuInputLabel.removeClass("checked");
        thObj.find("input[type='checkbox']").prop("checked",false);
      }else{
        dwQuInputLabel.addClass("checked");
        thObj.find("input[type='checkbox']").prop("checked",true);
      }

      var checkeds = quItemBody.find("input[type='checkbox']:checked");
      var paramInt02 = quItemBody.find(".paramInt02").val();
      if(paramInt02>0){
        if(checkeds[0]&&checkeds.size()>paramInt02){
          dwQuInputLabel.removeClass("checked");
          thObj.find("input[type='checkbox']").prop("checked",false);
          alert("最多只支持选择"+paramInt02+"项内容！");
          return false;
        }
      }

      runlogic(thObj.find("input[type='checkbox']"));
    }

    answerProgressbar(thObj);
    validateCheck(quItemBody,false);
  });

  $(".dwQuOptionItemNote").click(function(){
    return false;
  });

  //填空题
  $(".fillblankInput,.dwMFillblankInput").blur(function(){
    //$(this).css("borderColor","#D6D6FF");
    var thVal=$(this).val();
    runlogic($(this));
    answerProgressbar($(this));
    if(!$(this).hasClass("Wdate") || thVal!=""){
      validateCheck($(this).parents(".li_surveyQuItemBody"),true);
    }
  });



  $(".radioSelect").change(function(){
    var thVal = $(this).val();
    var thName = $(this).attr("name");
    var quCoItemUlLi = $(this).parents("li.quCoItemUlLi");
    var quItemInputCase= quCoItemUlLi.find(".quItemInputCase[itemid='"+thVal+"']");
    var isNote = quItemInputCase.find(".isNote").val();
    if(isNote=="1"){
      quCoItemUlLi.find(".dwQuOptionItemNote").hide();
      var dwQuOptionItemNote = quCoItemUlLi.find(".dwQuOptionItemNote[name='text_"+thName+"_"+thVal+"']");
      dwQuOptionItemNote.show();
    }else{
      quCoItemUlLi.find(".dwQuOptionItemNote").hide();
    }
    var thObj = $(this);
    var quItemBody = $(this).parents(".li_surveyQuItemBody");
    answerProgressbar(thObj);
    validateCheck(quItemBody,false);
    return false;
  });


  //只要触发事件
  function runlogic(thFormElementObj){
    //thFormElementObj 当前关联的form表单元素
    var quItemBody=thFormElementObj.parents(".li_surveyQuItemBody");

    var quLogicItems=quItemBody.find(".quLogicItem");

    if(quLogicItems[0]){

      var quInputCase=quItemBody.find(".quInputCase");
      var quId=quInputCase.find(".quId").val();
      var quType=quInputCase.find(".quType").val();

      //$("input[name='qu_"+quType+"_"+quId+"']").change(function(){});

      if(quType==="RADIO" || quType==="CHECKBOX" ){


        //判断是否选中
        var quLgoicItem=null;
        //var thVal=thFormElementObj.val();

        var quOptionItems=null;
        quOptionItems=quItemBody.find(".dwQuOptionItemContent");

        $.each(quLogicItems,function(){
          var loginItem=$(this);
          var cgQuItemId=loginItem.find(".cgQuItemId").val();
          var skQuId=loginItem.find(".skQuId").val();
          var logicId=loginItem.find(".logicId").val();
          var logicType=loginItem.find(".logicType").val();

          var isbreak=false;
          $.each(quOptionItems,function(){
            var quCoItem=$(this);
            var quInput=null;
            var logicStatus=false;
            var curQuItemId=null;
            if(quType==="RADIO"){
              quInput=quCoItem.find("input[type='radio']");
              logicStatus=quInput.prop("checked");
              curQuItemId=quInput.val();
            }else if(quType==="CHECKBOX"){
              quInput=quCoItem.find("input[type='checkbox']");
              logicStatus=quInput.prop("checked");
              curQuItemId=quInput.val();
            }

            if(curQuItemId===cgQuItemId){

              if(logicType==="1"){
                if(logicStatus){
                  skQuestion(quItemBody.next(),skQuId,logicId,function(){
                    //重新编题号

                  });
                  if(skQuId==="1" || skQuId==="2" ){
                    isbreak=true;
                  }
                }else{
                  /*
                  //$(".hidFor"+loginId).slideDown("slow");
                  $(".hidFor"+loginId).show();
                  //$(".hidFor"+loginId).fadeIn();
                  $(".hidFor"+loginId).removeClass("hidFor"+loginId);
                  $(".hidFor"+loginId).find(".answerTag").attr("disabled",false);
                  */

                  var hidQuItemBodys=$(".hidFor"+logicId);
                  $(".hidFor"+logicId).removeClass("hidFor"+logicId);
                  var curPageNo = quItemBody.find(".pageNo").val();
                  $.each(hidQuItemBodys,function(){
                    var thQuItemBodyClass=$(this).attr("class");
                    var  thPageNo = $(this).find(".pageNo").val();
                    if(thQuItemBodyClass.indexOf("hidFor")<0 && thPageNo==curPageNo){
                      $(this).show();
                      //$(".hidFor"+loginId).fadeIn();
                      $(this).find(".answerTag").attr("disabled",false);
                    }
                  });
                }
              }else{
                if(logicStatus){

                  var hidQuItemBodys = $(".hidFor" + logicId);
                  $(".hidFor" + logicId).removeClass("hidFor" + logicId);
                  var curPageNo = quItemBody.find(".pageNo").val();
                  $.each(hidQuItemBodys, function () {

                    var hidQuItemBody = $(this);
                    var hidQuId = $(this).find(".quId").val();
                    if (quType == "CHECKBOX" || quType == "RADIO") {
                      $.each(quLogicItems, function () {
                        var thLoginItem = $(this);
                        var thSkQuId = thLoginItem.find(".skQuId").val();
                        var thLogicType = thLoginItem.find(".logicType").val();
                        var thLogicId = thLoginItem.find(".logicId").val();
                        var logicHidForClass = "hidFor" + thLogicId;
//												console.debug(logicHidForClass);
                        if (thLogicType === "2" && thSkQuId === hidQuId) {
                          hidQuItemBody.removeClass(logicHidForClass);
                        }
                      });
                    }

                    var thQuItemBodyClass = $(this).attr("class");
                    var thPageNo = $(this).find(".pageNo").val();
                    if (thQuItemBodyClass.indexOf("hidFor") < 0 && thPageNo == curPageNo) {
                      $(this).show();
                      $(this).find(".answerTag").attr("disabled", false);
                    }
                  });


                }else{
                  /*  隐藏题
                   */

                  var isHide = true;
                  if(quType=="CHECKBOX" || quType=="RADIO"){
                    //先判断有没有逻辑已经触发了
                    $.each(quLogicItems,function() {
                      var thLoginItem = $(this);
                      var thCgQuItemId=thLoginItem.find(".cgQuItemId").val();
                      var thSkQuId = thLoginItem.find(".skQuId").val();
                      var thLogicType = thLoginItem.find(".logicType").val();
                      var thLogicId=thLoginItem.find(".logicId").val();
                      if( thLogicType==="2" && thSkQuId === skQuId ){
                        var thCgQuOptionItem=quItemBody.find(".dwQuOptionItemContent input[type='checkbox'][value='"+thCgQuItemId+"'],.dwQuOptionItemContent input[type='radio'][value='"+thCgQuItemId+"']");
                        var thCgQuOptionStatus=thCgQuOptionItem.prop("checked");
                        if(thCgQuOptionStatus){
                          isHide = false;
                          return false;
                        }
                      }
                    });
                  }

                  if(isHide){
                    var hidQuItemBody=$(".quId[value='"+skQuId+"']").parents(".li_surveyQuItemBody");
                    hidQuItemBody.hide();
                    hidQuItemBody.addClass("hidFor"+logicId);
                    hidQuItemBody.find(".answerTag").attr("disabled",true);
                  }

                }
              }

              return false;
            }
          });

          if(isbreak){
            return false;
          }

        });

      }else if(quType==="FILLBLANK"){


        //遍历每个逻辑设置
        var quOptionItems=quItemBody.find(".dwQuOptionItemContent");
        var thVal=thFormElementObj.val();

        $.each(quLogicItems,function(){
          var loginItem=$(this);
          var cgQuItemId=loginItem.find(".cgQuItemId").val();
          var skQuId=loginItem.find(".skQuId").val();
          var logicId=loginItem.find(".logicId").val();
          var logicType=loginItem.find(".logicType").val();
          if(logicType==="1"){
            //跳转
            if(thVal!=""){
              //逻辑选项被选中状态，激活状态
              skQuestion(quItemBody.next(),skQuId,logicId,function(){
                //重新编题号

              });
              if(skQuId==="1" || skQuId==="2" ){
                isbreak=true;
              }
            }else{
              //逻辑选项未被选中状态，未激活
              //$(".hidFor"+loginId).slideDown("slow");

              var hidQuItemBodys=$(".hidFor"+logicId);
              $(".hidFor"+logicId).removeClass("hidFor"+logicId);
              var curPageNo = quItemBody.find(".pageNo").val();
              $.each(hidQuItemBodys,function(){
                var thQuItemBodyClass=$(this).attr("class");
                var thPageNo = $(this).find(".pageNo").val();
                if(thQuItemBodyClass.indexOf("hidFor")<0 && thPageNo==curPageNo){
                  $(this).show();
                  //$(".hidFor"+loginId).fadeIn();
                  //回答标记与逻辑设置没有关系
                  $(this).find(".answerTag").attr("disabled",false);
                }
              });
            }
          }else{
            //显示
            //逻辑类型为“显示” quType=1
            if(thVal!=""){
              //逻辑选项被选中状态，激活状态  显示题
              var hidQuItemBodys=$(".hidFor"+logicId);
              $(".hidFor"+logicId).removeClass("hidFor"+logicId);
              var curPageNo = quItemBody.find(".pageNo").val();
              $.each(hidQuItemBodys,function(){
                var thQuItemBodyClass=$(this).attr("class");
                var thPageNo = $(this).find(".pageNo").val();
                if(thQuItemBodyClass.indexOf("hidFor")<0  && thPageNo==curPageNo){
                  $(this).show();
                  $(this).find(".answerTag").attr("disabled",false);
                }
              });

            }else{
              /*  隐藏题
               */
              var hidQuItemBody=$(".quId[value='"+skQuId+"']").parents(".li_surveyQuItemBody");
              hidQuItemBody.hide();
              hidQuItemBody.addClass("hidFor"+logicId);
              hidQuItemBody.find(".answerTag").attr("disabled",true);
            }
          }

        });

      }

      resetQuNumLogic();

    }


  }



  function skQuestion(nextQuItemBody,skQuId,logicId,callback){

    if(nextQuItemBody[0]){
      //submitSurveyBtn
      var nextQuType=nextQuItemBody.find(".quType").val();
      var nextQuId=nextQuItemBody.find(".quId").val();
      var nextAnswerTag=nextQuItemBody.find(".answerTag");


      //如果是新一页则显示当前页所有题
      if(nextQuType=="PAGETAG"){
        if(validateForms()) {
          nextQuItemBody.find(".nextPage_a").click();
        }
      }

      //var quType=quItemBody.find(".quType").val();
      //var quId=quItemBody.find(".quId").val();


      //判断跳转类型
      if(skQuId==null){
        //对于逻辑选项未被选中的情况

      }else if(nextQuType=="PAGETAG"){
        skQuestion(nextQuItemBody.next(),skQuId,logicId,function(){

        });
      }else if(nextQuType!="submitSurveyBtn" && nextQuType!="PAGETAG"  && (skQuId==="1" || skQuId==="2" || nextQuId!=skQuId) ){
        //对于逻辑项是被选定的情况下
        nextAnswerTag.attr("disabled",true);
        //nextQuItemBody.slideUp("slow");
        nextQuItemBody.hide();
        //nextQuItemBody.fadeOut();
        nextQuItemBody.addClass("hidFor"+logicId);

        skQuestion(nextQuItemBody.next(),skQuId,logicId,function(){

        });
      }
      /*
      else if(nextQuItemBody.is(":hidden")){
          skQuestion(nextQuItemBody.next(),skQuId,logicId,function(){

          });
      }else if(nextQuType!="submitSurveyBtn" && nextQuType!="PAGETAG" && (skQuId==="1" || skQuId==="2" || nextQuId!=skQuId) ){
          //对于逻辑项是被选定的情况下
          nextAnswerTag.attr("disabled",true);
          //nextQuItemBody.slideUp("slow");
          nextQuItemBody.hide();
          //nextQuItemBody.fadeOut();
          nextQuItemBody.addClass("hidFor"+logicId);

          skQuestion(nextQuItemBody.next(),skQuId,logicId,function(){

          });
      }*/
    }else{
      callback();
    }
  }

//	resetQuNum();
  function resetQuNum(){
    var quCoNums=$(".quCoNum");
    $.each(quCoNums,function(i,item){
      $(this).html((i+1)+"、");
    });

  }

  resetQuNumLogic();
  function resetQuNumLogic(){
    var surveyQuItemBodys = $(".li_surveyQuItemBody");
    var quIndex = 1;
    $.each(surveyQuItemBodys,function(i,item){
      var thClass = $(this).attr("class");
      var quCoNum = $(this).find(".quCoNum");
//			console.debug(thClass+":"+thClass.indexOf("hidFor"));
      if(thClass.indexOf("hidFor")<0 && quCoNum[0]){
        quCoNum.html((quIndex++)+"、");
      }
    });
  }


  /*
  $("input").unbind("click");
  $("input").click(function(){
      var quItemBody=$(this).parents(".li_surveyQuItemBody ");
      var quType=quItemBody.find(".quType").val();
      if(quType=="RADIO"){
          quItemBody.find(".answerTag").val(1);
      }
      var totalQuSize=$(".answerTag:enabled").size();
      var answerTag1=$(".answerTag[value='1']:enabled");
      var answerQuSize=0;
      if(answerTag1[0]){
          answerQuSize=answerTag1.size();
      }
      var newValue = parseInt(answerQuSize/totalQuSize*100);
      $("#resultProgressRoot .progress-label").text(newValue+"%");
      $("#resultProgress").progressbar("option", "value", newValue);
  });
  */

  $("#mobileTdId").click(function(){
    $(this).next().slideToggle();
    return false;
  });

  bindDateEvent();

}

function validateCheck(quItemBody,isSubForm){
  if(quItemBody.is(":visible")){

    var quId=quItemBody.find(".quId").val();
    var quType=quItemBody.find(".quType").val();
    var isRequired=quItemBody.find(".isRequired").val();

    var validateStatus=false;
    var checkType = "";

    if(isRequired==="0"){
      validateStatus = true;
      return true;
    }

    if(quType==="RADIO"){
      var hv = quItemBody.find(".hv").val();
      if(hv=="4" && quType ==="RADIO"){
        var radioSelectVal = quItemBody.find(".radioSelect").val();
        if(radioSelectVal!="" && radioSelectVal!="0"){
          var radioSelect = quItemBody.find(".radioSelect");
          var radioSelectVal = radioSelect.val();
          var radioSelectName = radioSelect.attr("name");
          var quCoItemUlLi = radioSelect.parents("li.quCoItemUlLi");
          var quItemInputCase= quCoItemUlLi.find(".quItemInputCase[itemid='"+radioSelectVal+"']");
          var isNote = quItemInputCase.find(".isNote").val();
          if(isNote=="1"){
            var curText = quCoItemUlLi.find(".dwQuOptionItemNote[name='text_"+radioSelectName+"_"+radioSelectVal+"']");
            var curTextValue = curText.val();
            var checkType = quItemInputCase.find(".checkType").val();
            var isRequiredFill = quItemInputCase.find(".isRequiredFill").val();
            if(isRequiredFill=="1" || curTextValue!="") {
              validateStatus = checkoutData(checkType, curText);
            }else{
              validateStatus = true;
            }
          }else{
            validateStatus = true;
          }
        }
      }else{
        validateStatus=quItemBody.find("input[type='radio']:checked")[0];
        if(validateStatus){
          var curRadio = quItemBody.find("input[type='radio']:checked");
          var curText = curRadio.parent().find("input[type='text']:visible");
          if(curText[0]){
            var curTextValue = curText.val();
            checkType = curRadio.parent().find(".checkType").val();
            var isRequiredFill = curRadio.parent().find(".isRequiredFill").val();
            if(isRequiredFill!="1"){
              validateStatus = checkoutData(checkType, curTextValue);
            }
          }
        }
      }
    }else if(quType==="CHECKBOX"){
      validateStatus=quItemBody.find("input[type='checkbox']:checked")[0];
      var paramInt01 = quItemBody.find(".paramInt01").val();

      if(paramInt01>0){
        /*var checkeds = quItemBody.find("input[type='checkbox']:checked");
        if(checkeds[0]&&checkeds.size()<paramInt01){
          quItemBody.find(".quCoItem").append("<div class=\"errorItem\"><label class=\"error\">请保证最少选择"+paramInt01+"项内容</label></div>");
          validateStatus = false;
        }else{
          quItemBody.find(".quCoItem .errorItem").remove();
        }*/
      }
      if(validateStatus){
//					isNote checkType isRequiredFill
        var curCheckboxs = quItemBody.find("input[type='checkbox']:checked");
        $.each(curCheckboxs,function(){
          var curText = $(this).parent().find("input[type='text']:visible");
          if(curText[0]){
            var curTextValue = curText.val();
            checkType = $(this).parent().find(".checkType").val();
            var isRequiredFill = $(this).parent().find(".isRequiredFill").val();
            if(isRequiredFill!="1"){
              validateStatus = checkoutData(checkType, curTextValue);
            }
          }
          if(!validateStatus){
            return false;
          }
        });
      }
    }else if(quType==="FILLBLANK"){
      var value = quItemBody.find(".fillblankInput").val();
      validateStatus = value!="";
      checkType = quItemBody.find(".checkType").val();
      if(validateStatus){
        validateStatus = checkoutData(checkType, value);
      }

    }else if(quType==="ORDERQU"){
      //quItemBody.find(".quOrderByLeftUl label");
      validateStatus=!quItemBody.find(".quOrderByLeftUl label")[0];
    }else if(quType==="SCORE"){

      validateStatus=true;
      var quScoreOptionTrs=quItemBody.find(".quScoreOptionTr");
      $.each(quScoreOptionTrs,function(){
        var scoreNumInput=$(this).find(".scoreNumInput");
        if(scoreNumInput.val()===""){
          validateStatus=false;
          return false;
        }
      });

    }else if(quType==="MULTIFILLBLANK"){

      validateStatus=true;
      var quScoreOptionTrs=quItemBody.find(".mFillblankTableTr");
      $.each(quScoreOptionTrs,function(){
        var scoreNumInput=$(this).find(".dwMFillblankInput");
        if(scoreNumInput.val()===""){
          validateStatus=false;
          return false;
        }
      });

    }else if(quType==="submitSurveyBtn" || quType==="PARAGRAPH" || quType==="PAGETAG"){
      return true;
    }
  }else{
    validateStatus=true;
  }
  if(validateStatus){
    quItemBody.find(".errorItem").remove();
  }else{
//			isSubForm && !quItemBody.find(".errorItem")[0]
    if(isSubForm){
      if(quItemBody.find(".errorItem")[0]){
        quItemBody.find(".errorItem").remove();
      }
      var errorHtml="<div class=\"errorItem\"><label for=\"\" class=\"error\">请检查题目答案，为必答项！</label></div>";
      if(checkType=="EMAIL"){
        errorHtml="<div class=\"errorItem\"><label for=\"\" class=\"error\">请输入Email，为必答项！</label></div>";
      }else if(checkType=="UNSTRCN"){
        errorHtml="<div class=\"errorItem\"><label for=\"\" class=\"error\">请输入非中文字符，为必答项！</label></div>";
      }else if(checkType=="STRCN"){
        errorHtml="<div class=\"errorItem\"><label for=\"\" class=\"error\">请输入中文字符，为必答项！</label></div>";
      }else if(checkType=="NUM"){
        errorHtml="<div class=\"errorItem\"><label for=\"\" class=\"error\">请输入数字，为必答项！</label></div>";
      }else if(checkType == "DIGITS"){
        errorHtml="<div class=\"errorItem\"><label for=\"\" class=\"error\">请输入整数，为必答项！</label></div>";
      }else if(checkType == "TELENUM"){
        errorHtml="<div class=\"errorItem\"><label for=\"\" class=\"error\">请输入电话，为必答项！</label></div>";
      }else if(checkType == "PHONENUM"){
        errorHtml="<div class=\"errorItem\"><label for=\"\" class=\"error\">请输入手机，为必答项！</label></div>";
      }else if(checkType == "TELE_PHONE_NUM"){
        errorHtml="<div class=\"errorItem\"><label for=\"\" class=\"error\">请输入电话或手机，为必答项！</label></div>";
      }else if(checkType == "DATE"){
        //2014-01-01 12:00:00
        errorHtml="<div class=\"errorItem\"><label for=\"\" class=\"error\">请输入如日期时间，为必答项！</label></div>";
      }else if(checkType == "IDENTCODE"){
        errorHtml="<div class=\"errorItem\"><label for=\"\" class=\"error\">请输入身份证号，为必答项！</label></div>";
      }else if(checkType == "ZIPCODE"){
        errorHtml="<div class=\"errorItem\"><label for=\"\" class=\"error\">请输入邮编，为必答项！</label></div>";
      }else if(checkType == "URL"){
        errorHtml="<div class=\"errorItem\"><label for=\"\" class=\"error\">请输入URL，为必答项！</label></div>";
      }

      quItemBody.find(".quCoItem").append(errorHtml);
    }
  }
  return validateStatus;
}

/**********************处理答题进度条************************/
//$("#resultProgress").progressbar({value: bfbFloat});
function answerProgressbar(thObj){
  var quItemBody=thObj.parents(".li_surveyQuItemBody ");
  var quType=quItemBody.find(".quType").val();

  if(quType==="RADIO"){
    //quItemBody.find(".answerTag").val(1);
    var checks=quItemBody.find("input[type='radio']:checked");
    if(checks[0]){
      quItemBody.find(".answerTag").val(1);
    }else{
      quItemBody.find(".answerTag").val(0);
    }
  }else if(quType=="CHECKBOX"){
    var checks=quItemBody.find("input[type='checkbox']:checked");
    if(checks[0]){
      quItemBody.find(".answerTag").val(1);
    }else{
      quItemBody.find(".answerTag").val(0);
    }
  }else if(quType==="FILLBLANK"){
    var thVal=thObj.val();
    if(thVal!=""){
      quItemBody.find(".answerTag").val(1);
    }else{
      quItemBody.find(".answerTag").val(0);
    }
  }else if(quType==="ORDERQU"){
    //quOrderByLeftUl
    var orderByLabels=quItemBody.find(".quOrderByLeftUl label");
    if(!orderByLabels[0]){
      quItemBody.find(".answerTag").val(1);
    }else{
      quItemBody.find(".answerTag").val(0);
    }
  }else if( quType==="SCORE" ){
    //<input type="hidden" class="answerTag" value="0" >
    var quScoreOptionTr=thObj.parents(".quScoreOptionTr");
    var scoreNumInput=quScoreOptionTr.find(".scoreNumInput");
    if(scoreNumInput.val()!=""){
      quScoreOptionTr.find(".answerTag").val(1);
    }else{
      quScoreOptionTr.find(".answerTag").val(0);
    }
  }else if(quType==="MULTIFILLBLANK"){
    var mFillblankTableTr=thObj.parents(".mFillblankTableTr");
    if(thObj.val()!=""){
      mFillblankTableTr.find(".answerTag").val(1);
    }else{
      mFillblankTableTr.find(".answerTag").val(0);
    }
  }

  var totalQuSize=$("#dwSurveyQuContent .answerTag:enabled").size();
  var answerTag1=$("#dwSurveyQuContent .answerTag[value='1']:enabled");
  var answerQuSize=0;
  if(answerTag1[0]){
    answerQuSize=answerTag1.size();
  }
  var newValue = parseInt(answerQuSize/totalQuSize*100);
  $("#resultProgressRoot .progress-label").text("完成度："+newValue+"%");
  $("#resultProgress").progressbar("option", "value", newValue);
}

