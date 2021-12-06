function querySurveyAll(callback) {
  var ctx = $("#ctx").val();
  var surveyId = $("#id").val();
  var sid = $("#sid").val();
  var tag = $("#tag").val();
  var url=ctx+"/response/survey.do";
  if(tag==="p" || tag==="s"){
    var ctxApp = $("#ctxApp").val();
    url = ctxApp+"/design/survey-design/surveyAll.do";
  }
  var data = "surveyId="+surveyId+"&sid="+sid;
  $.ajax({
    url:url,
    data:data,
    // type:"post",
    cache: false,
    success:function(httpResult){
      console.debug(httpResult);
      if(httpResult.resultCode==200){
        var resultData = httpResult.data;
        parseSurvey(resultData,tag);
        var questions = resultData.questions;
        if(questions!=null){

          var pageNo = 1;
          $.each(questions,function(i,item){
            var quType = item.quType;
            if(quType==="RADIO"){
              parseRadio(item,pageNo);
            }else if(quType==="CHECKBOX"){
              parseCheckbox(item,pageNo);
            }else if(quType==="FILLBLANK"){
              parseFbk(item,pageNo);
            }else if(quType==="SCORE"){
              parseScore(item,pageNo);
            }else if(quType==="ORDERQU"){
              parseOrder(item,pageNo);
            }else if(quType==="MULTIFILLBLANK"){
              parseMfbk(item,pageNo);
            }else if(quType==="PAGETAG"){
              parsePage(item,pageNo);
              pageNo+=1;
            }else if(quType==="PARAGRAPH"){
              parseParagraph(item,pageNo);
            }
          });
          parseSubmit(pageNo);
        }
        if(callback!=null){
          callback();
        }
      }else{
        alert("数据获取失败！");
        // notify("数据获取失败...",10000);
      }
    },
    error: function(xmlHttpRequest,error){
      if(xmlHttpRequest.status===404){
        // alert("未找到发布的问卷或问卷未发布，请重新发布"+sid);
        // window.location.href="/#/diaowen-msg/"+sid+"/1";
        window.location.href="/static/diaowen/diaowen-message.html?sid="+sid+"&respType=1";
      }else if(xmlHttpRequest.status===403){
        $("#dwSurveyName").html("<span style='color: red;'>没有权限，无法加载数据！</span>");
        $("#dwSurveyNoteEdit").html("加载失败");
      }
    }
  });
}

function parseSurvey(data){
  var tag = $("#tag").val();
  if(!(tag==="p" || tag==="s")){
    if(data.surveyState!=1){
      window.location.href="/static/diaowen/diaowen-message.html?sid="+data.sid+"&respType=1";
    }
  }
  $("#id").val(data.id);
  $("#surveyId").val(data.id);
  $("#dwSurveyName").html(data.surveyName);
  $("title").text($("#dwSurveyName").text());
  // $(document).prop('title','val11ue');
  $("#dwSurveyNoteEdit").html(data.surveyDetail.surveyNote);
  $("#breakpoint1").val(data.surveyDetail.breakpoint1);
}

function parseExtracted(quInputCase, item) {
  quInputCase.find(".quId").val(item.id);
  quInputCase.find(".quType").val(item.quType);
  quInputCase.find(".orderById").val(item.orderById);
  quInputCase.find(".isRequired").val(item.isRequired);
  quInputCase.find(".paramInt01").val(item.paramInt01);
  quInputCase.find(".paramInt02").val(item.paramInt02);
  quInputCase.find(".checkType").val(item.checkType);

  var paramInt03 = quInputCase.find(".paramInt03");
  var param03 = quInputCase.find(".param03");
  var cellCount = quInputCase.find(".cellCount");
  var paramStr01 = quInputCase.find(".paramStr01");
  var paramStr02 = quInputCase.find(".paramStr02");
  var paramInt01 = quInputCase.find(".paramInt01");
  var paramInt02 = quInputCase.find(".paramInt02");
  var hv = quInputCase.find(".hv");

  if (!paramInt01[0]) {
    quInputCase.append("<input type='hidden' class='paramInt01' value='"+item.paramInt01+"' />");
    paramInt01 = quInputCase.find(".paramInt01");
  }

  if(paramInt03[0]){paramInt03.val(item.paramInt03);}
  if(param03[0]){param03.val(item.param03);}
  if(cellCount[0]){cellCount.val(item.cellCount);}
  if(paramStr01[0]){paramStr01.val(item.paramStr01);}
  if(paramInt01[0]){paramInt01.val(item.paramInt01);}
  if(paramInt02[0]){paramInt02.val(item.paramInt02);}
  if(hv[0]){hv.val(item.hv);}
}

function quLogicExtracted(lastQuItemBody, item, pageNo) {
  var quType = item.quType;
  var quLogicInputCase = lastQuItemBody.find(".quLogicInputCase");
  var quLogicItemHtml = $("#quLogicItemModel").html();

  // quLogicItem_${logicSts.count }
  var quLogics = item.questionLogics;
  $.each(quLogics, function (i, item_1) {
    quLogicInputCase.append(quLogicItemHtml);
    var quLogicItem = quLogicInputCase.find(".quLogicItem").last();
    quLogicItem.addClass("quLogicItem_"+i);
    // 修改值
    quLogicItem.find(".logicId").val(item_1.id);
    quLogicItem.find(".skQuId").val(item_1.skQuId);
    quLogicItem.find(".cgQuItemId").val(item_1.cgQuItemId);
    quLogicItem.find(".geLe").val(item_1.geLe);
    quLogicItem.find(".scoreNum").val(item_1.scoreNum);
    quLogicItem.find(".logicType").val(item_1.logicType);

  });
  lastQuItemBody.find(".quTitleText").html(item.quTitle);
  lastQuItemBody.addClass("surveyQu_"+pageNo);

  var quInputCase = lastQuItemBody.find(".quInputCase");
  var pageNoObj = quInputCase.find(".pageNo");
  if(!pageNoObj[0]){
    quInputCase.append("<input type=\"hidden\" class=\"pageNo\" value=\""+pageNo+"\">");
    pageNoObj = quInputCase.find(".pageNo");
  }
  pageNoObj.val(pageNo);

  if(pageNo>1){
    lastQuItemBody.hide();
  }
}


function parseRadio(item,pageNo){

  var radioQuModel = $("#radioQuModel").html();
  $("#dwSurveyQuContentAppUl").append(radioQuModel);
  var lastQuItemBody = $("#dwSurveyQuContentAppUl .li_surveyQuItemBody").last();
  lastQuItemBody.find(".quDragBody").removeClass("quDragBody");
  var quInputCase = lastQuItemBody.find(".quInputCase");
  parseExtracted(quInputCase, item);
  quLogicExtracted(lastQuItemBody, item,pageNo);
  var inputName = "qu_"+item.quType+"_"+item.id;
  var quRadios = item.quRadios;
  var quCoItem = lastQuItemBody.find(".surveyQuItemContent .quCoItem");
  /*
  if(item.hv==3){
    //表格
    quCoItem.append("<table class='tableQuColItem'></table>");
  }else if(item.hv==1){
    //一排
  }*/
  if(item.hv===4){
    var radioSelectContent=$("#radioSelect_content").html();
    var radioSelectOption = $("#radioSelectOption").html();
    quCoItem.append(radioSelectContent);
    var quCoItemUl = quCoItem.find("ul");
    var quCoItemUlLi = quCoItemUl.find(".quCoItemUlLi");
    quCoItemUlLi.addClass("radioSelectLi");
    var radioSelect = quCoItemUl.find(".radioSelect");
    radioSelect.attr("name",inputName);
    radioSelect.empty();
    radioSelect.append("<option value=\"0\">--请选择--</option>");
    $.each(quRadios,function(i,item_2){
      radioSelect.append("<option value='"+item_2.id+"'>"+item_2.optionName+"</option>");
      quCoItemUlLi.append(radioSelectOption);
      var quItemInputCaseLast = quCoItemUlLi.find(".quItemInputCase").last();
      quItemInputCaseLast.attr("itemid",item_2.id);
      quItemInputCaseLast.find(".isNote").val(item_2.id);
      quItemInputCaseLast.find(".checkType").val(item_2.checkType);
      quItemInputCaseLast.find(".isRequiredFill").val(item_2.isRequiredFill);
      var dwQuOptionItemNoteLast = quCoItemUlLi.find(".dwQuOptionItemNote").last();
      dwQuOptionItemNoteLast.attr("text_"+inputName+"_"+item_2.id);
    });
  }else{
    var quRadioItemHtml=$("#quRadioItem_default").html();
    quCoItem.append("<ul></ul>");
    var quCoItemUl = quCoItem.find("ul");
    $.each(quRadios,function(i,item_2){
      quCoItemUl.append(quRadioItemHtml);
      var itemLast = quCoItemUl.find("li:last");
      var itemLabel = itemLast.find(".editAble");
      itemLabel.html(item_2.optionName);
      if(item_2.isNote == 1) itemLast.find(".optionInpText").show();
      var inputRadio = itemLast.find("input[type='radio']");
      var inputId = "qu_"+inputName+"_"+item_2.id;
      inputRadio.prop("id",inputId);
      inputRadio.prop("name",inputName);
      inputRadio.val(item_2.id);
      itemLabel.prop("for",inputId);
      var inputTextName = "text_"+inputName+"_"+item_2.id;
      itemLast.find(".dwQuOptionItemNote").prop("name",inputTextName);
      var quItemInputCase = itemLast.find(".quItemInputCase");
      quItemInputCase.find(".isNote").val(item_2.isNote);
      quItemInputCase.find(".checkType").val(item_2.checkType);
      quItemInputCase.find(".isRequiredFill").val(item_2.isRequiredFill);
      if(item_2.isNote===1){
        itemLast.find(".dwQuOptionItemNote").show();
      }
    });
  }
}

function parseCheckbox(item,pageNo){

  var quModel = $("#checkboxQuModel").html();
  $("#dwSurveyQuContentAppUl").append(quModel);
  var lastQuItemBody = $("#dwSurveyQuContentAppUl .li_surveyQuItemBody").last()
  lastQuItemBody.find(".quDragBody").removeClass("quDragBody");
  var quInputCase = lastQuItemBody.find(".quInputCase");
  parseExtracted(quInputCase, item);
  quLogicExtracted(lastQuItemBody, item,pageNo);

  var inputTag = lastQuItemBody.find(".hidden_input_tag");
  var inputTagName = "qu_"+item.quType+"_"+item.id;
  inputTag.prop("name",inputTagName);
  inputTag.val("tag_"+inputTagName);

  var inputName = "qu_"+item.quType+"_"+item.id;
  var quCheckboxs = item.quCheckboxs;
  var quCoItem = lastQuItemBody.find(".surveyQuItemContent .quCoItem");
  if(item.hv==3){
    //表格

    quCoItem.append("<table class='tableQuColItem'></table>");
  }else if(item.hv==1){
    //一排

  }else{
    var quCheckboxItemHtml=$("#quCheckboxItem_default").html();
    quCoItem.append("<ul></ul>");
    var quCoItemUl = quCoItem.find("ul");
    $.each(quCheckboxs,function(i,item_2){
      quCoItemUl.append(quCheckboxItemHtml);
      var itemLast = quCoItemUl.find("li:last");
      var itemLable = itemLast.find(".editAble");
      itemLable.html(item_2.optionName);
      if(item_2.isNote == 1) itemLast.find(".optionInpText").show();
      var checkboxName = "tag_"+inputName+"_"+item_2.id;
      var inputCheckbox = itemLast.find("input[type='checkbox']");
      var inputId = "qu_"+checkboxName+"_"+item_2.id;
      inputCheckbox.prop("id",inputId);
      inputCheckbox.prop("name",checkboxName);
      inputCheckbox.val(item_2.id);
      itemLable.prop("for",inputId);
      var inputTextName = "text_"+checkboxName;
      itemLast.find(".dwQuOptionItemNote").prop("name",inputTextName);
      var quItemInputCase = itemLast.find(".quItemInputCase");
      quItemInputCase.find(".isNote").val(item_2.isNote);
      quItemInputCase.find(".checkType").val(item_2.checkType);
      quItemInputCase.find(".isRequiredFill").val(item_2.isRequiredFill);
      if(item_2.isNote===1){
        itemLast.find(".dwQuOptionItemNote").show();
      }
    });
  }

}

function parseFbk(item,pageNo){
  var quModel = $("#fillblankQuModel").html();
  $("#dwSurveyQuContentAppUl").append(quModel);
  var lastQuItemBody = $("#dwSurveyQuContentAppUl .li_surveyQuItemBody").last()
  lastQuItemBody.find(".quDragBody").removeClass("quDragBody");
  var quInputCase = lastQuItemBody.find(".quInputCase");
  parseExtracted(quInputCase, item);
  quLogicExtracted(lastQuItemBody, item, pageNo);
  //lastQuItemBody.find(".quCoTitle .quCoNum").text();
  lastQuItemBody.find(".quCoTitle .quCoTitleEdit").html(item.quTitle);
  //qu_${en.quType }_${en.id }
  var inputName = "qu_"+item.quType+"_"+item.id;
  var answerInputWidth = item.answerInputWidth;
  if(answerInputWidth==null||answerInputWidth==""){
    answerInputWidth=300;
  }
  var quFillblankItem = lastQuItemBody.find(".quCoItem");
  if(item.answerInputRow>1){
    quFillblankItem.append('<textarea name="'+inputName+'" rows="'+item.answerInputRow+'" style="width:100%;" class="inputSytle_2 fillblankInput"></textarea>');
  }else{
    quFillblankItem.append('<input type="text" name="'+inputName+'" class="inputSytle_1 fillblankInput">');
  }

}

function parseScore(item,pageNo){
  var quModel = $("#scoreQuModel").html();
  $("#dwSurveyQuContentAppUl").append(quModel);
  var lastQuItemBody = $("#dwSurveyQuContentAppUl .li_surveyQuItemBody").last()
  lastQuItemBody.find(".quDragBody").removeClass("quDragBody");
  var quInputCase = lastQuItemBody.find(".quInputCase");
  parseExtracted(quInputCase, item);
  quLogicExtracted(lastQuItemBody, item, pageNo);

  var dwScoreInputName = "item_qu_"+item.quType+"_"+item.id+"_";
  var hiddenInputTag = lastQuItemBody.find(".hidden_input_tag");
  hiddenInputTag.prop("name","qu_"+item.quType+"_"+item.id);
  hiddenInputTag.val(dwScoreInputName);

  var quCoItemTable = lastQuItemBody.find(".quCoItem");
  quCoItemTable.empty();
  var quItemHtml=$("#quScoreItemModel").html();
  var quScores = item.quScores;
  $.each(quScores,function(i,item_2){
    quCoItemTable.append(quItemHtml);
    var itemLast = quCoItemTable.find(".quScoreOptionTr:last");
    itemLast.find(".editAble").html(item_2.optionName);
    var dwScoreOptionId= itemLast.find(".dwScoreOptionId");
    dwScoreOptionId.val(item_2.id);
    var scoreNumInput= itemLast.find(".scoreNumInput");
    scoreNumInput.prop("name",dwScoreInputName+item_2.id);
    if(item_2.isNote == 1) itemLast.find(".optionInpText").show();
    var quItemInputCase = itemLast.find(".quItemInputCase");
    var scoreNumTableTr=itemLast.find(".starOptionContent");
    var paramInt02=item.paramInt02;
    scoreNumTableTr.empty();
    for(var i=1;i<=paramInt02;i++){
      scoreNumTableTr.append("<i class=\"fa fa-star-o\"></i>");
    }
  });
}

function parseOrder(item,pageNo){
  var quModel = $("#orderQuModel").html();
  $("#dwSurveyQuContentAppUl").append(quModel);
  var quItemBody = $("#dwSurveyQuContentAppUl .li_surveyQuItemBody").last()
  quItemBody.find(".quDragBody").removeClass("quDragBody");
  var quInputCase = quItemBody.find(".quInputCase");
  parseExtracted(quInputCase, item);
  quLogicExtracted(quItemBody, item, pageNo);

  var dwOrderbyInputName = "item_qu_"+item.quType+"_"+item.id+"_";
  var hiddenInputTag = quItemBody.find(".hidden_input_tag");
  hiddenInputTag.prop("name","qu_"+item.quType+"_"+item.id);
  hiddenInputTag.val(dwOrderbyInputName);

  var quOrderItemLeftUl=quItemBody.find(".ui-controlgroup-controls");
  quOrderItemLeftUl.empty();
  var quOrderItemLeftHtml=$("#quOrderItemLeftModel").html();

  var quScores = item.quOrderbys;
  $.each(quScores,function(i,item_2){
    quOrderItemLeftUl.append(quOrderItemLeftHtml);
    var itemLast = quOrderItemLeftUl.find(".m_clickQuOrderItem:last");
    var newEditObj=itemLast.find(".editAble");
    newEditObj.text(item_2.optionName);

    var quOrderItemHidInput = itemLast.find(".quOrderItemHidInput");
    quOrderItemHidInput.prop("name",dwOrderbyInputName+item_2.id);
    var quItemInputCase = itemLast.find(".quItemInputCase");
    quItemInputCase.find("input[name='quItemId']").val(item_2.id);

  });
}

function parseMfbk(item,pageNo){
  var quModel = $("#mfillblankQuModel").html();
  $("#dwSurveyQuContentAppUl").append(quModel);
  var lastQuItemBody = $("#dwSurveyQuContentAppUl .li_surveyQuItemBody").last()
  lastQuItemBody.find(".quDragBody").removeClass("quDragBody");
  var quInputCase = lastQuItemBody.find(".quInputCase");
  parseExtracted(quInputCase, item);
  quLogicExtracted(lastQuItemBody, item, pageNo);
  var dwMFillblankInputName = "text_qu_"+item.quType+"_"+item.id+"_";
  var hiddenInputTag = lastQuItemBody.find(".hidden_input_tag");
  hiddenInputTag.prop("name","qu_"+item.quType+"_"+item.id);
  hiddenInputTag.val(dwMFillblankInputName);
  var quCoItemTable=lastQuItemBody.find(".quCoItem");
  quCoItemTable.empty();
  var quItemHtml=$("#mFillblankTableModel").html();
  var quMultiFillblanks = item.quMultiFillblanks;
  $.each(quMultiFillblanks,function(i,item_2){
    quCoItemTable.append(quItemHtml);
    var itemLast=quCoItemTable.find(".mFillblankTableTr:last");
    var editAble = itemLast.find(".editAble")
    editAble.text(item_2.optionName);
    editAble.prop("for",dwMFillblankInputName+item_2.id);
    var dwMFillblankInput = itemLast.find(".dwMFillblankOptionId");
    dwMFillblankInput.val(item_2.id);
    var dwMFillblankInput = itemLast.find(".dwMFillblankInput");
    dwMFillblankInput.prop("name",dwMFillblankInputName+item_2.id);
    dwMFillblankInput.prop("id",dwMFillblankInputName+item_2.id);
    var quItemInputCase = itemLast.find(".quItemInputCase");
    quItemInputCase.find("input[name='quItemId']").val(item_2.id);
  });
}

function refquOrderTableTdNum(quOrderByRightTable){
  var quOrderyTableTds=quOrderByRightTable.find(".quOrderyTableTd");
  $.each(quOrderyTableTds,function(i){
    $(this).text(i+1);
  });
}

function parsePage(item,pageNo){
  var quModel = $("#pageQuModel").html();
  $("#dwSurveyQuContentAppUl").append(quModel);
  var lastQuItemBody = $("#dwSurveyQuContentAppUl .li_surveyQuItemBody").last()
  lastQuItemBody.find(".quDragBody").removeClass("quDragBody");
  var quInputCase = lastQuItemBody.find(".quInputCase");
  parseExtracted(quInputCase, item);
  quLogicExtracted(lastQuItemBody, item, pageNo);
  // nextPageNo
  lastQuItemBody.find("input[name='nextPageNo']").val(pageNo+1);
}

function parseParagraph(item,pageNo){
  var quModel = $("#paragraphQuModel").html();
  $("#dwSurveyQuContentAppUl").append(quModel);
  var lastQuItemBody = $("#dwSurveyQuContentAppUl .li_surveyQuItemBody").last()
  lastQuItemBody.find(".quDragBody").removeClass("quDragBody");
  var quInputCase = lastQuItemBody.find(".quInputCase");
  parseExtracted(quInputCase, item);
  quLogicExtracted(lastQuItemBody, item,pageNo);

}

function parseSubmit(pageNo){
  var quModel = $("#dwSubmitModel").html();
  $("#dwSurveyQuContentAppUl").append(quModel);
  var lastQuItemBody = $("#dwSurveyQuContentAppUl .li_surveyQuItemBody").last()
  lastQuItemBody.addClass("surveyQu_"+pageNo);
  if(pageNo>1){
    lastQuItemBody.hide();
  }
  lastQuItemBody.find("input[name='nextPageNo']").val(pageNo+1);

  var pageNoObj = lastQuItemBody.find(".pageNo");
  if(!pageNoObj[0]){
    lastQuItemBody.append("<input type=\"hidden\" class=\"pageNo\" value=\""+pageNo+"\">");
    pageNoObj = lastQuItemBody.find(".pageNo");
  }
  pageNoObj.val(pageNo);

}


