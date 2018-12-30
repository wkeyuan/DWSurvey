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
                validateStatus=!quItemBody.find(".quOrderItemHidInput[value=0]")[0];
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

    $("#dwSurveyQuContent input[type='radio'],#dwSurveyQuContent input[type='checkbox']").change(function(){
        lgcommon($(this));
        validateCheck($(this).parents(".li_surveyQuItemBody"),false);
        $(".fillblankInput,.dwMFillblankInput,.dwChenMFillblankInput").blur();
    });

    //填空题
    $(".fillblankInput,.dwMFillblankInput,.dwChenMFillblankInput").blur(function(){
        lgcommon($(this));
        validateCheck($(this).parents(".li_surveyQuItemBody"),false);
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
    var url= ctx + "/response!ajaxCheckSurvey.action";
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
});


