function querySurveyAll(){
  showUIDialog(null,'dwSurveyLoadDialog');
  var ctx = $("#ctx").val();
  var url = ctx+"/design/survey-design/surveyAll.do";
  var surveyId = $("#id").val();
  var data = "surveyId="+surveyId+"&sid=";
  console.debug(data);
  $.ajax({
    url:url,
    data:data,
    type:"post",
    success:function(httpResult){
      console.debug(httpResult);
      if(httpResult.resultCode==200){
         var resultData = httpResult.data;
         parseSurvey(resultData);
         var questions = resultData.questions;
         if(questions!=null){
           $.each(questions,function(i,item){
             var quType = item.quType;
             if(quType==="RADIO"){
               parseRadio(item);
             }else if(quType==="CHECKBOX"){
               parseCheckbox(item);
             }else if(quType==="FILLBLANK"){
               parseFbk(item);
             }else if(quType==="SCORE"){
               parseScore(item);
             }else if(quType==="ORDERQU"){
               parseOrder(item);
             }else if(quType==="MULTIFILLBLANK"){
               parseMfbk(item);
             }else if(quType==="PAGETAG"){
               parsePage(item);
             }else if(quType==="PARAGRAPH"){
               parseParagraph(item);
             }else if(quType == "UPLOADFILE"){
               parseUploadfile(item);
             }
           });
           resetQuItem();
           bindQuHoverItem();
           loadReset();
         }
        $("#modelUIDialog").dialog("close");
        dwCommonDialogHide();
      }else{
        // alert("数据获取失败！");
        notify(httpResult.resultMsg,10000);
        if(httpResult.resultCode===401){
          window.location.href="/#/login";
        }else if(httpResult.resultCode===403){
          window.location.href="/#/403";
        }
      }
    },
    error: function(xmlHttpRequest, textStatus, errorThrown){
      if(xmlHttpRequest.status===401){
        window.location.href="/#/login";
      }else if(xmlHttpRequest.status===403){
        window.location.href="/#/403";
      }
    }
  });
}

function parseSurvey(data){
  $("#dwSurveyName").html(data.surveyName);
  $("#dwSurveyNoteEdit").html(data.surveyDetail.surveyNote);
  if(data.surveyDetail.effective>1){
    $("input[name='effective']").prop("checked",true);
  }else{
    $("input[name='effective']").prop("checked",false);
  }
  $("input[name='effectiveIp'][value='"+data.surveyDetail.effectiveIp+"']").attr("checked",true);
  $("input[name='rule'][value='"+data.surveyDetail.rule+"']").attr("checked",true);
  $("input[name='ruleCode']").val(data.surveyDetail.ruleCode);
  $("input[name='refresh'][value='"+data.surveyDetail.refresh+"']").attr("checked",true);
  $("input[name='mailOnly'][value='"+data.surveyDetail.mailOnly+"']").attr("checked",true);
  $("input[name='ynEndNum'][value='"+data.surveyDetail.ynEndNum+"']").attr("checked",true);
  $("input[name='endNum']").val(data.surveyDetail.endNum);
  $("input[name='ynEndTime'][value='"+data.surveyDetail.ynEndTime+"']").attr("checked",true);
  $("input[name='ynStartTime'][value='"+data.surveyDetail.ynStartTime+"']").attr("checked",true);
  $("input[name='showShareSurvey'][value='"+data.surveyDetail.showShareSurvey+"']").attr("checked",true);
  $("input[name='showAnswerDa'][value='"+data.surveyDetail.showAnswerDa+"']").attr("checked",true);
  $("#startTime").val(data.surveyDetail.startTime);
  $("#endTime").val(data.surveyDetail.endTime);
  $("input[name='calSumScore'][value='"+data.surveyDetail.calSumScore+"']").attr("checked",true);
  $("input[name='onlyIps'][value='"+data.surveyDetail.onlyIps+"']").attr("checked",true);
  $("input[name='ruleCodeNum']").val(data.surveyDetail.ruleCodeNum);

  $("input[name='onlyWxAnswer'][value='"+data.surveyDetail.onlyWxAnswer+"']").attr("checked",true);
  $("input[name='wxUserinfo'][value='"+data.surveyDetail.wxUserinfo+"']").attr("checked",true);
  $("input[name='wxAnswerNum']").val(data.surveyDetail.wxAnswerNum);
  var wxAnswerNum = data.surveyDetail.wxAnswerNum;
  if(wxAnswerNum!="" && wxAnswerNum!=0 && wxAnswerNum!=null){
    $("input[name='yn_wxAnswerNum']").attr("checked",true);
    $("input[name='wxAnswerNum']").val(wxAnswerNum);
  }
  $("input[name='breakpoint1'][value='"+data.surveyDetail.breakpoint1+"']").attr("checked",true);

}

function parseExtracted(quInputCase, item) {
  quInputCase.find("input[name='quType']").val(item.quType);
  quInputCase.find("input[name='quId']").val(item.id);
  quInputCase.find("input[name='orderById']").val(item.orderById);
  quInputCase.find("input[name='saveTag']").val(1);
  quInputCase.find("input[name='hoverTag']").val(0);
  quInputCase.find("input[name='isRequired']").val(item.isRequired);
  quInputCase.find("input[name='hv']").val(item.hv);
  quInputCase.find("input[name='randOrder']").val(item.randOrder);
  quInputCase.find("input[name='cellCount']").val(item.cellCount);
  quInputCase.find("input[name='paramInt01']").val(item.paramInt01);
  quInputCase.find("input[name='paramInt02']").val(item.paramInt02);
  quInputCase.find("input[name='paramInt03']").val(item.paramInt03);

  quInputCase.find("input[name='paramStr01']").val(item.paramStr01);
  quInputCase.find("input[name='paramStr02']").val(item.paramStr02);

}

function quLogicExtracted(lastQuItemBody, item) {
  var quType = item.quType;
  var quLogicInputCase = lastQuItemBody.find(".quLogicInputCase");
  var quLogicItemHtml = $("#quLogicItemModel").html();

  var quLogics = item.questionLogics;
  $.each(quLogics, function (i, item_1) {
    quLogicInputCase.append(quLogicItemHtml);
    var quLogicItem = quLogicInputCase.find(".quLogicItem").last();
    quLogicItem.addClass("quLogicItem_"+(i+1));
    //修改值
    quLogicItem.find("input[name='quLogicId']").val(item_1.id);
    quLogicItem.find("input[name='skQuId']").val(item_1.skQuId);
    quLogicItem.find("input[name='cgQuItemId']").val(item_1.cgQuItemId);
    quLogicItem.find("input[name='visibility']").val("1");
    quLogicItem.find("input[name='logicType']").val(item_1.logicType);

    //如果是评分题
    if (quType === "SCORE" || quType === "SCALE" || quType === "CHENSCALE" || quType === "MULTISLIDER" || quType==="ORDERQU" || quType==="CHENSCORE") {
      quLogicItem.find("input[name='geLe']").val(item_1.geLe);
      quLogicItem.find("input[name='scoreNum']").val(item_1.scoreNum);
      quLogicItem.find("input[name='logicType']").val(item_1.logicType);
    }

  });
  if(quLogics!=null){
    quLogicInputCase.find("input[name='quLogicItemNum']").val(quLogics.length);
  }
  lastQuItemBody.find(".quCoTitle .quCoTitleEdit").html(item.quTitle);
  refreshQuLogicInfo(lastQuItemBody);
}


function parseRadio(item){

  var radioQuModel = $("#radioQuModel .dwQuTypeModel").html();
  $("#dwSurveyQuContentAppUl").append("<li class=\"li_surveyQuItemBody\">"+radioQuModel+"</li>");
  var lastQuItemBody = $("#dwSurveyQuContentAppUl .li_surveyQuItemBody").last()
  lastQuItemBody.find(".quDragBody").removeClass("quDragBody");
  var quInputCase = lastQuItemBody.find(".quInputCase");
  parseExtracted(quInputCase, item);
  quInputCase.find("input[name='contactsAttr']").val(item.contactsAttr);
  quInputCase.find("input[name='contactsField']").val(item.contactsField);

  quLogicExtracted(lastQuItemBody, item);

  var quCoItemUl = lastQuItemBody.find(".quCoItem ul");
  quCoItemUl.empty();
  var quRadioItemHtml=$("#quRadioItem").html();
  var quRadios = item.quRadios;
  $.each(quRadios,function(i,item_2){
    quCoItemUl.append("<li class='quCoItemUlLi'>"+quRadioItemHtml+"</li>");
    var itemLast = quCoItemUl.find("li:last");
    itemLast.find(".editAble").html(item_2.optionName);
    if(item_2.isNote == 1) itemLast.find(".optionInpText").show();
    var quItemInputCase = itemLast.find(".quItemInputCase");
    quItemInputCase.find("input[name='quItemId']").val(item_2.id);
    quItemInputCase.find("input[name='isNote']").val(item_2.isNote);
    quItemInputCase.find("input[name='checkType']").val(item_2.checkType);
    quItemInputCase.find("input[name='isRequiredFill']").val(item_2.isRequiredFill);
    quItemInputCase.find("input[name='scoreNum']").val(item_2.scoreNum);
    var answerTag = itemLast.find(".answerTag");
    if(item_2.scoreNum>0){
      answerTag.text("("+item_2.scoreNum+"分)");
      answerTag.show();
    }
  });
  if(item.hv===4){
    lastQuItemBody.find(".quCoItem").prepend("<div class=\"selectItems\" style=\"margin-top: 6px;\" ><select style=\"padding: 5px;\" class=\"radioSelect\" name=\"qu_RADIO_"+item.id+"\"><option value=\"0\">--下拉效果，请前往预览中查看--</option></select></div>");
  }

}

function parseCheckbox(item){

  var quModel = $("#checkboxQuModel .dwQuTypeModel").html();
  $("#dwSurveyQuContentAppUl").append("<li class=\"li_surveyQuItemBody\">"+quModel+"</li>");
  var lastQuItemBody = $("#dwSurveyQuContentAppUl .li_surveyQuItemBody").last()
  lastQuItemBody.find(".quDragBody").removeClass("quDragBody");
  var quInputCase = lastQuItemBody.find(".quInputCase");
  parseExtracted(quInputCase, item);
  quInputCase.find("input[name='contactsAttr']").val(item.contactsAttr);
  quInputCase.find("input[name='contactsField']").val(item.contactsField);
  quInputCase.find("input[name='paramInt01']").val(item.paramInt01);
  quInputCase.find("input[name='paramInt02']").val(item.paramInt02);

  quLogicExtracted(lastQuItemBody, item);

  var quCoItemUl = lastQuItemBody.find(".quCoItem ul");
  quCoItemUl.empty();
  var quItemHtml=$("#quCheckboxItem").html();
  var quCheckboxs = item.quCheckboxs;
  $.each(quCheckboxs,function(i,item_2){
    quCoItemUl.append("<li class='quCoItemUlLi'>"+quItemHtml+"</li>");
    var itemLast = quCoItemUl.find("li:last");
    itemLast.find(".editAble").html(item_2.optionName);
    if(item_2.isNote === 1) itemLast.find(".optionInpText").show();
    var quItemInputCase = itemLast.find(".quItemInputCase");
    quItemInputCase.find("input[name='quItemId']").val(item_2.id);
    quItemInputCase.find("input[name='isNote']").val(item_2.isNote);
    quItemInputCase.find("input[name='checkType']").val(item_2.checkType);
    quItemInputCase.find("input[name='isRequiredFill']").val(item_2.isRequiredFill);
    quItemInputCase.find("input[name='scoreNum']").val(item_2.scoreNum);
    var answerTag = itemLast.find(".answerTag");
    if(item_2.scoreNum>0){
      answerTag.text("("+item_2.scoreNum+"分)");
      answerTag.show();
    }
  });
}

function parseFbk(item){
  var quModel = $("#fillblankQuModel .dwQuTypeModel").html();
  $("#dwSurveyQuContentAppUl").append("<li class=\"li_surveyQuItemBody\">"+quModel+"</li>");
  var lastQuItemBody = $("#dwSurveyQuContentAppUl .li_surveyQuItemBody").last()
  lastQuItemBody.find(".quDragBody").removeClass("quDragBody");
  var quInputCase = lastQuItemBody.find(".quInputCase");
  parseExtracted(quInputCase, item);
  quInputCase.find("input[name='contactsAttr']").val(item.contactsAttr);
  quInputCase.find("input[name='contactsField']").val(item.contactsField);
  quInputCase.find("input[name='checkType']").val(item.checkType);
  quInputCase.find("input[name='answerInputWidth']").val(item.answerInputWidth);
  quInputCase.find("input[name='answerInputRow']").val(item.answerInputRow);
  quInputCase.find("input[name='paramStr01']").val(item.paramStr01);
  quInputCase.find("input[name='paramInt01']").val(item.paramInt01);
  quLogicExtracted(lastQuItemBody, item);
  //lastQuItemBody.find(".quCoTitle .quCoNum").text();
  lastQuItemBody.find(".quCoTitle .quCoTitleEdit").html(item.quTitle);

  if(item.answerInputRow>1){
    var textarea = lastQuItemBody.find("textarea");
    textarea.show();
    textarea.attr("rows",item.answerInputRow);
    lastQuItemBody.find("input[type='text']").hide();
  }
}


function parseScore(item){
  var quModel = $("#scoreQuModel .dwQuTypeModel").html();
  $("#dwSurveyQuContentAppUl").append("<li class=\"li_surveyQuItemBody\">"+quModel+"</li>");
  var lastQuItemBody = $("#dwSurveyQuContentAppUl .li_surveyQuItemBody").last()
  lastQuItemBody.find(".quDragBody").removeClass("quDragBody");
  var quInputCase = lastQuItemBody.find(".quInputCase");
  parseExtracted(quInputCase, item);
  quInputCase.find("input[name='contactsAttr']").val(item.contactsAttr);
  quInputCase.find("input[name='contactsField']").val(item.contactsField);
  quInputCase.find("input[name='paramInt01']").val(item.paramInt01);
  quInputCase.find("input[name='paramInt02']").val(item.paramInt02);
  quLogicExtracted(lastQuItemBody, item);
  var quCoItemTable=lastQuItemBody.find("table.quCoItemTable");
  quCoItemTable.empty();
  var quItemHtml=$("#quScoreItemModel").html();
  var quScores = item.quScores;
  $.each(quScores,function(i,item_2){
    quCoItemTable.append("<tr class='quScoreOptionTr'>"+quItemHtml+"</tr>");
    var itemLast = quCoItemTable.find("tr.quScoreOptionTr:last");
    itemLast.find(".editAble").html(item_2.optionName);
    if(item_2.isNote == 1) itemLast.find(".optionInpText").show();
    var quItemInputCase = itemLast.find(".quItemInputCase");
    quItemInputCase.find("input[name='quItemId']").val(item_2.id);
    quItemInputCase.find("input[name='isNote']").val(item_2.isNote);
    quItemInputCase.find("input[name='checkType']").val(item_2.checkType);
    quItemInputCase.find("input[name='isRequiredFill']").val(item_2.isRequiredFill);
    var scoreNumTableTr=quCoItemTable.find("tr.quScoreOptionTr:last .scoreNumTable tr");
    var paramInt02=item.paramInt02;
    scoreNumTableTr.empty();
    for(var i=1;i<=paramInt02;i++){
      scoreNumTableTr.append("<td>"+i+"</td>");
    }
  });
}


function parseOrder(item){
  var quModel = $("#orderQuModel .dwQuTypeModel").html();
  $("#dwSurveyQuContentAppUl").append("<li class=\"li_surveyQuItemBody\">"+quModel+"</li>");
  var quItemBody = $("#dwSurveyQuContentAppUl .li_surveyQuItemBody").last()
  quItemBody.find(".quDragBody").removeClass("quDragBody");
  var quInputCase = quItemBody.find(".quInputCase");
  parseExtracted(quInputCase, item);
  quInputCase.find("input[name='contactsAttr']").val(item.contactsAttr);
  quInputCase.find("input[name='contactsField']").val(item.contactsField);
  quInputCase.find("input[name='paramInt01']").val(item.paramInt01);
  quInputCase.find("input[name='paramInt02']").val(item.paramInt02);
  quLogicExtracted(quItemBody, item);

  var quOrderItemLeftUl=quItemBody.find(".quOrderByLeft ul");
  var quOrderByRightTable=quItemBody.find(".quOrderByRight table.quOrderByTable");
  quOrderItemLeftUl.empty();
  quOrderByRightTable.empty();
  var quOrderItemLeftHtml=$("#quOrderItemLeftModel").html();
  var quOrderItemRightHtml=$("#quOrderItemRightModel").html();

  var quScores = item.quOrderbys;
  $.each(quScores,function(i,item_2){
    quOrderItemLeftUl.append("<li class='quCoItemUlLi'>"+quOrderItemLeftHtml+"</li>");
    quOrderByRightTable.append("<tr>"+quOrderItemRightHtml+"</tr>");

    var itemLast = quOrderItemLeftUl.find("li:last");
    var newEditObj=itemLast.find(".editAble");
    newEditObj.text(item_2.optionName);
    var quItemInputCase = itemLast.find(".quItemInputCase");
    quItemInputCase.find("input[name='quItemId']").val(item_2.id);
    refquOrderTableTdNum(quOrderByRightTable);
  });
}

function parseMfbk(item){
  var quModel = $("#mfillblankQuModel .dwQuTypeModel").html();
  $("#dwSurveyQuContentAppUl").append("<li class=\"li_surveyQuItemBody\">"+quModel+"</li>");
  var lastQuItemBody = $("#dwSurveyQuContentAppUl .li_surveyQuItemBody").last()
  lastQuItemBody.find(".quDragBody").removeClass("quDragBody");
  var quInputCase = lastQuItemBody.find(".quInputCase");
  parseExtracted(quInputCase, item);
  quInputCase.find("input[name='contactsAttr']").val(item.contactsAttr);
  quInputCase.find("input[name='contactsField']").val(item.contactsField);
  quInputCase.find("input[name='paramInt01']").val(item.paramInt01);
  quInputCase.find("input[name='paramInt02']").val(item.paramInt02);
  quInputCase.find("input[name='paramStr01']").val(item.paramStr01);
  quLogicExtracted(lastQuItemBody, item);
  var quCoItemTable=lastQuItemBody.find("table.mFillblankTable");
  quCoItemTable.empty();
  var quItemHtml=$("#mFillblankTableModel").html();
  var quMultiFillblanks = item.quMultiFillblanks;
  $.each(quMultiFillblanks,function(i,item_2){
    quCoItemTable.append("<tr class='mFillblankTableTr'>"+quItemHtml+"</tr>");
    var itemLast=quCoItemTable.find("tr.mFillblankTableTr:last");
    itemLast.find(".editAble").text(item_2.optionName);
    var quItemInputCase = itemLast.find(".quItemInputCase");
    quItemInputCase.find("input[name='quItemId']").val(item_2.id);
  });
}

function parsePage(item){
  var quModel = $("#pageQuModel .dwQuTypeModel").html();
  $("#dwSurveyQuContentAppUl").append("<li class=\"li_surveyQuItemBody\">"+quModel+"</li>");
  var lastQuItemBody = $("#dwSurveyQuContentAppUl .li_surveyQuItemBody").last()
  lastQuItemBody.find(".quDragBody").removeClass("quDragBody");
  var quInputCase = lastQuItemBody.find(".quInputCase");
  parseExtracted(quInputCase, item);
  quInputCase.find("input[name='contactsAttr']").val(item.contactsAttr);
  quInputCase.find("input[name='contactsField']").val(item.contactsField);
  quInputCase.find("input[name='checkType']").val(item.checkType);
  quInputCase.find("input[name='answerInputWidth']").val(item.answerInputWidth);
  quInputCase.find("input[name='answerInputRow']").val(item.answerInputRow);
  quInputCase.find("input[name='paramStr01']").val(item.paramStr01);
  quLogicExtracted(lastQuItemBody, item);

}


function parseParagraph(item){
  var quModel = $("#paragraphQuModel .dwQuTypeModel").html();
  $("#dwSurveyQuContentAppUl").append("<li class=\"li_surveyQuItemBody\">"+quModel+"</li>");
  var lastQuItemBody = $("#dwSurveyQuContentAppUl .li_surveyQuItemBody").last()
  lastQuItemBody.find(".quDragBody").removeClass("quDragBody");
  var quInputCase = lastQuItemBody.find(".quInputCase");
  parseExtracted(quInputCase, item);
  quInputCase.find("input[name='contactsAttr']").val(item.contactsAttr);
  quInputCase.find("input[name='contactsField']").val(item.contactsField);
  quInputCase.find("input[name='checkType']").val(item.checkType);
  quInputCase.find("input[name='answerInputWidth']").val(item.answerInputWidth);
  quInputCase.find("input[name='answerInputRow']").val(item.answerInputRow);
  quInputCase.find("input[name='paramStr01']").val(item.paramStr01);
  quLogicExtracted(lastQuItemBody, item);

}

function parseUploadfile(item){
  var quModel = $("#uploadFileQuModel .dwQuTypeModel").html();
  $("#dwSurveyQuContentAppUl").append("<li class=\"li_surveyQuItemBody\">"+quModel+"</li>");
  var lastQuItemBody = $("#dwSurveyQuContentAppUl .li_surveyQuItemBody").last()
  lastQuItemBody.find(".quDragBody").removeClass("quDragBody");
  var quInputCase = lastQuItemBody.find(".quInputCase");
  parseExtracted(quInputCase, item);
  quInputCase.find("input[name='contactsAttr']").val(item.contactsAttr);
  quInputCase.find("input[name='contactsField']").val(item.contactsField);
  quInputCase.find("input[name='checkType']").val(item.checkType);
  quInputCase.find("input[name='answerInputWidth']").val(item.answerInputWidth);
  quInputCase.find("input[name='answerInputRow']").val(item.answerInputRow);
  quInputCase.find("input[name='paramStr01']").val(item.paramStr01);
  quLogicExtracted(lastQuItemBody, item);

}
