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

    $(".nextPage_a").click(function(){
        if(validateForms()){
            var thParent=$(this).parent();
            var nextPageNo=thParent.find("input[name='nextPageNo']").val();
            $(".li_surveyQuItemBody").hide();
            $(".surveyQu_"+nextPageNo).fadeIn("slow");
            $("html,body").animate({scrollTop:10},500);
        }
        return false;
    });
    $(".prevPage_a").click(function(){
        var thParent=$(this).parent();
        var prevPageNo=thParent.find("input[name='prevPageNo']").val();
        $(".li_surveyQuItemBody").hide();
        $(".surveyQu_"+prevPageNo).fadeIn("slow");
        $(window).scrollTop(10);
        return false;
    });

    $(".submitSurvey").click(function(){
        if(validateForms()){
            $("#surveyForm").submit();
        }
        return false;
    });

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
                    var quOrderyByTrs=$(".quCoItem .quOrderByTableTr");
                    $.each(quOrderyByTrs,function(i){
                        var quOrderItemHidInput=$(this).find(".quOrderItemHidInput");
                        quOrderItemHidInput.val(i+1);
                    });
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


    function validateForms(){
        var result=true;
        var surveyQuItemBodys=$(".li_surveyQuItemBody");
        var firstError=null;
        $.each(surveyQuItemBodys,function(){
            var quItemBody=$(this);
            if(!validateCheck(quItemBody,true)){
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


    function validateCheck(quItemBody,isSubForm){
        if(quItemBody.is(":visible")){

            var quId=quItemBody.find(".quId").val();
            var quType=quItemBody.find(".quType").val();
            var isRequired=quItemBody.find(".isRequired").val();

            var validateStatus=false;

            if(isRequired==="0"){
                validateStatus = true;
                return true;
            }

            if(quType==="RADIO"){
                validateStatus=quItemBody.find("input[type='radio']:checked")[0];
            }else if(quType==="CHECKBOX"){
                validateStatus=quItemBody.find("input[type='checkbox']:checked")[0];
            }else if(quType==="FILLBLANK"){
                validateStatus=quItemBody.find(".fillblankInput").val()!="";
            }else if(quType==="ORDERQU"){
                //quItemBody.find(".quOrderByLeftUl label");
                validateStatus=!quItemBody.find(".quOrderByLeftUl label")[0];
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
            if(isSubForm && !quItemBody.find(".errorItem")[0]){
                var errorHtml="<div class=\"errorItem\"><label for=\"\" class=\"error\">请检查题目答案，为必答项！</label></div>";
                quItemBody.find(".quCoItem").append(errorHtml);
            }
        }
        return validateStatus;
    }

    var quLogics=$("#dwSurveyQuContent .quLogicItem");
    $.each(quLogics,function(){
        var loginItem=$(this);
        var cgQuItemId=loginItem.find(".cgQuItemId").val();
        var skQuId=loginItem.find(".skQuId").val();
        var logicId=loginItem.find(".logicId").val();
        var logicType=loginItem.find(".logicType").val();
        var hidQuItemBody=$(".quId[value='"+skQuId+"']").parents(".li_surveyQuItemBody");
        hidQuItemBody.hide();
        hidQuItemBody.addClass("hidFor"+logicId);
        hidQuItemBody.find(".answerTag").attr("disabled",true);
    });


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

            lgcommon(thObj.find("input[type='radio']"));
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

            lgcommon(thObj.find("input[type='checkbox']"));
        }
        answerProgressbar(thObj);
        validateCheck(quItemBody,false);
    });

    //填空题
    $(".fillblankInput,.dwMFillblankInput").blur(function(){
        lgcommon($(this));
        answerProgressbar($(this));
        validateCheck($(this).parents(".li_surveyQuItemBody"),false);
    });



    resetQuNum();
    function resetQuNum(){
        var quCoNums=$(".quCoNum");
        $.each(quCoNums,function(i,item){
            $(this).html((i+1)+"、");
        });

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
        }else if(quType==="MULTIFILLBLANK"){
            var mFillblankTableTr=thObj.parents(".mFillblankTableTr");
            if(thObj.val()!=""){
                mFillblankTableTr.find(".answerTag").val(1);
            }else{
                mFillblankTableTr.find(".answerTag").val(0);
            }
        }

        var totalQuSize=$(".answerTag:enabled").size();
        var answerTag1=$(".answerTag[value='1']:enabled");
        var answerQuSize=0;
        if(answerTag1[0]){
            answerQuSize=answerTag1.size();
        }
        var newValue = parseInt(answerQuSize/totalQuSize*100);
        $("#resultProgressRoot .progress-label").text("完成度："+newValue+"%");
        $("#resultProgress").progressbar("option", "value", newValue);
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

});