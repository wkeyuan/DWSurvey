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

    $(".m_clickQuOrderItem").click(function(){
        var visibleOrderbyNum=$(this).parents().find(".m_orderby_num:visible");
        var thOrderbyNum=$(this).find(".m_orderby_num");
        if(!$(this).find(".m_orderby_num:visible")[0]){
            var thNum=0;
            $(this).append("<select class='m_orderby_sel' > </select>");
            var mOrderbySel=$(this).find(".m_orderby_sel");
            var quOrderItems=$(this).parents().find(".m_clickQuOrderItem");
            $.each(quOrderItems,function(i,item){
                mOrderbySel.append("<option value='"+(i+1)+"'>移至排序"+(i+1)+"</option>");
                var targetHid=$(this).parents(".ui-controlgroup-controls ").find(".quOrderItemHidInput[value='"+(i+1)+"']");
                if(!targetHid[0] && thNum===0){
                    thNum=(i+1);
                }
            });
            thOrderbyNum.text(thNum);
            thOrderbyNum.show();
            mOrderbySel.val(thNum)
            $(this).find(".quOrderItemHidInput").val(thNum);
        }
        bindEvent();
        lgcommon($(this));
        validateCheck($(this).parents(".li_surveyQuItemBody"),false);
        return false;
    });
    function bindEvent(){
        $(".m_orderby_sel").unbind();
        $(".m_orderby_sel").change(function(){
            var thOrderbyItem=$(this).parents(".m_clickQuOrderItem");
            var thOrderByNum=thOrderbyItem.find(".quOrderItemHidInput").val();
            var thChangeNum=$(this).val();
            var targetHid=$(this).parents(".ui-controlgroup-controls ").find(".quOrderItemHidInput[value='"+thChangeNum+"']");
            if(targetHid[0]){
                var targetOrderbyItem=targetHid.parents(".m_clickQuOrderItem");
                targetOrderbyItem.find(".m_orderby_num").text(thOrderByNum);
                targetOrderbyItem.find(".quOrderItemHidInput").val(thOrderByNum);
                targetOrderbyItem.find(".m_orderby_sel").val(thOrderByNum);
            }
            thOrderbyItem.find(".m_orderby_num").text(thChangeNum);
            thOrderbyItem.find(".quOrderItemHidInput").val(thChangeNum);
            thOrderbyItem.find(".m_orderby_sel").val(thChangeNum);
        });
    }

    /**评分题*/
    $(".starRating  .fa").hover(function(){
        var prevAll=$(this).prevAll();
        prevAll.removeClass("fa-star-o");
        prevAll.addClass("fa-star");
        $(this).removeClass("fa-star-o");
        $(this).addClass("fa-star");
    },function(){
        var scoreRow=$(this).parents(".scoreRow");
        var starNum=scoreRow.find(".scoreNumInput").val();
        if(starNum==""&&starNum<=0){
            var thParent=$(this).parent();
            var fas=thParent.find(".fa");
            fas.removeClass("fa-star");
            fas.addClass("fa-star-o");
        }
    });

    $(".starRating  .fa").click(function(){
        var thParent=$(this).parent();
        var fas=thParent.find(".fa");
        fas.removeClass("fa-star");
        fas.addClass("fa-star-o");

        var prevAll=$(this).prevAll();
        prevAll.removeClass("fa-star-o");
        prevAll.addClass("fa-star");
        $(this).removeClass("fa-star-o");
        $(this).addClass("fa-star");

        var scoreRow=$(this).parents(".scoreRow");
        var starNum=0;
        if(prevAll[0]){
            starNum=prevAll.size();
        }
        scoreRow.find(".scoreNumInput").val(starNum+1);

        validateCheck($(this).parents(".li_surveyQuItemBody"),false);
        return false;
    });

    //表单验证
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
            var checkType = "";
            if(isRequired==="0"){
                validateStatus = true;
                return true;
            }
            if(quType==="RADIO"){
                var hv = quItemBody.find(".hv").val();
                if(hv=="4"){
                    var radioSelectVal = quItemBody.find(".radioSelect").val();
                    if(radioSelectVal!="" && radioSelectVal!="0"){
                        validateStatus = true;
                    }else{
                        validateStatus = false;
                    }
                }else{
                    validateStatus=quItemBody.find("input[type='radio']:checked")[0];
                }
            }else if(quType==="CHECKBOX"){
                validateStatus=quItemBody.find("input[type='checkbox']:checked")[0];
            }else if(quType==="FILLBLANK"){
                var value = quItemBody.find(".fillblankInput").val();
                validateStatus=value!="";
                checkType = quItemBody.find(".checkType").val();
                if(validateStatus){
                    validateStatus = checkoutData(checkType, value);
                }
            }else if(quType==="ORDERQU"){
                //quItemBody.find(".quOrderByLeftUl label");
                validateStatus=!quItemBody.find(".quOrderItemHidInput[value=0]")[0];
            }else if(quType==="MULTIFILLBLANK"){

                validateStatus=true;
                var quScoreOptionTrs=quItemBody.find(".mFillblankTableTr");
                var paramInt01=quItemBody.find(".paramInt01");
                var anNum = 0;
                $.each(quScoreOptionTrs,function(){
                    var scoreNumInput=$(this).find(".dwMFillblankInput");
                    if(scoreNumInput.val()!=""){
                        anNum++;
                    }
                });

                if(paramInt01[0]){
                    if(paramInt01.val()=='0'){
                        if(anNum!=quScoreOptionTrs.size()) {
                            validateStatus = false;
                        }
                    }else{
                        if(anNum<parseInt(paramInt01.val())) {
                            validateStatus = false;
                        }
                    }
                }else{
                    if(anNum!=quScoreOptionTrs.size()) {
                        validateStatus = false;
                    }
                }

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

            }else if(quType==="submitSurveyBtn" || quType==="PARAGRAPH" || quType==="PAGETAG"){
                return true;
            }

        }else{
            validateStatus=true;
        }

        if(validateStatus){
            quItemBody.find(".errorItem").remove();
        }else{
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
                    errorHtml="<div class=\"errorItem\"><label for=\"\" class=\"error\">请输入如：2014-01-01，为必答项！</label></div>";
                }else if(checkType == "IDENTCODE"){
                    errorHtml="<div class=\"errorItem\"><label for=\"\" class=\"error\">请输入身份证号，为必答项！</label></div>";
                }else if(checkType == "ZIPCODE"){
                    errorHtml="<div class=\"errorItem\"><label for=\"\" class=\"error\">请输入邮编，为必答项！</label></div>";
                }else if(checkType == "URL"){
                    errorHtml="<div class=\"errorItem\"><label for=\"\" class=\"error\">请输入URL，为必答项！</label></div>";
                }
                quItemBody.find(".surveyQuItemContent").append(errorHtml);
            }
        }
        return validateStatus;
    }

    $(".submitSurvey").click(function(){
        if(validateForms()){
            $("#surveyForm").submit();
        }
        return false;
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
        validateCheck(quItemBody,false);
        return false;
    });

    $("#dwSurveyQuContent .dwQuOptionItemContent").unbind();
    $("#dwSurveyQuContent .dwQuOptionItemContent").click(function(){
        clickItem($(this));
        lgcommon($(this));
        validateCheck($(this).parents(".li_surveyQuItemBody"),false);
        $(".fillblankInput,.dwMFillblankInput,.dwChenMFillblankInput").blur();
    });

    function clickItem(thObj){
        var quItemBody=thObj.parents(".li_surveyQuItemBody");
        var quType=quItemBody.find(".quType").val();
        var dwQuInputLabel=thObj.find(".dwQuInputLabel");
        if("RADIO"===quType){

            quItemBody.find(".dwQuInputLabel").removeClass("checked");
            quItemBody.find("input[type='radio']").prop("checked",false);
            thObj.find("input[type='radio']").prop("checked",true);
            dwQuInputLabel.addClass("checked");
            quItemBody.find(".dwQuOptionItemContent").removeClass("active");
            thObj.addClass("active");

        }else if("CHECKBOX"===quType){
            var thCheckbox = thObj.find("input[type='checkbox']");
            if(thCheckbox.prop("checked")){
                thObj.find("input[type='checkbox']").prop("checked",false);
                dwQuInputLabel.removeClass("checked");
                thObj.removeClass("active");
            }else{
                thObj.find("input[type='checkbox']").prop("checked",true);
                dwQuInputLabel.addClass("checked");
                thObj.addClass("active");
            }
        }else if("MULTIFILLBLANK"===quType){

            var chenRow=thObj.parents(".mFillblankTableTr");
            chenRow.find(".dwQuInputLabel").removeClass("checked");
            chenRow.find("input[type='radio']").prop("checked",false);
            thObj.find("input[type='radio']").prop("checked",true);
            dwQuInputLabel.addClass("checked");

        }
    }

    //填空题
    $(".fillblankInput,.dwMFillblankInput,.dwChenMFillblankInput").blur(function(){
        lgcommon($(this));
        validateCheck($(this).parents(".li_surveyQuItemBody"),true);
    });

    function resetQuNum(){
        var quTitleNums=$(".quTitleNum");
        $.each(quTitleNums,function(i,item){
            $(this).text((i+1)+"、")
        });
    }

    resetQuNum();
    var quTitleNums=$(".quTitleNum");
    $.each(quTitleNums,function(){
        var nextObj=$(this).next();
        if(nextObj[0]){
            var childrens=nextObj.children();
            if(childrens[0]){
                var firstChildren=childrens.first();
                if(firstChildren.is("p")){
                    $(this).prependTo(firstChildren);
                }
            }
        }
    });


    var ctx = $("#ctx").val();
    function refreshAutoCode(codeImgId){
        $("#"+codeImgId).attr("src",ctx+"/jcaptcha.action");
    }

//判则判断
    var url= ctx + "/dws-answer!ajaxCheckSurvey.action";
    var surveyId = $("#surveyId").val();
    var data="surveyId="+surveyId;
    $.ajax({
        url:url,
        data:data,
        type:"post",
        success:function(msg){
            var json=eval("("+msg+")");
            if(json.isCheckCode==="3"){
                $("#jcaptchaImgBody").show();
            }

            if(json.surveyStatus!="0"){
                $("#fixedMsg").show();
                $("#fixedMsg").html("您已经回答过此问卷！");
                $("#submitSurvey").remove();
                $("form").attr("action","#");
            }
        }
    });

    var errorcode="${param['errorcode']}";
    if(errorcode=="3"){
        var errorHtml="<div class=\"errorItem\" style=\"padding-left:30px;padding-top:10px;\" ><label for=\"\" class=\"error\">验证码不正确，请重新回答！</label></div>";
        $("#m-errorMsg").append(errorHtml);
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

    bindDateEvent();
});


