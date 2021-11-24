
var bfbFloat=0;

$(document).ready(function(){

    $("#resultProgress").progressbar({value: bfbFloat,orientation: "vertical"});

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
        }
        return false;
    });
    $(".prevPage_a").click(function(){
        var thParent=$(this).parent();
        var prevPageNo=thParent.find("input[name='prevPageNo']").val();
//		$(".li_surveyQuItemBody").hide();
//		$(".surveyQu_"+prevPageNo).fadeIn("slow");
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

    /**排序题*/
    $(".m_clickQuOrderItem").click(function(){
        var visibleOrderbyNum=$(this).parents(".surveyQuItem").find(".m_orderby_num:visible");
        var thOrderbyNum=$(this).find(".m_orderby_num");
        if($(this).find(".m_orderby_num:visible")[0]){

        }else{

            var thNum=0;
            $(this).append("<select class='m_orderby_sel' > </select>");
            var mOrderbySel=$(this).find(".m_orderby_sel");
            var quOrderItems=$(this).parents(".surveyQuItem").find(".m_clickQuOrderItem");
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

        runlogic($(this));
        validateCheck($(this).parents(".li_surveyQuItemBody"),false);
        answerProgressbar($(this));
        return false;
    });
    function bindEvent(){
        $(".m_orderby_sel").unbind();
        $(".m_orderby_sel").change(function(){
            //交换个排
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

        runlogic($(this));

        validateCheck($(this).parents(".li_surveyQuItemBody"),false);
        answerProgressbar($(this));
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

    $(".submitSurvey").click(function(){
        var btnId = $(this).attr("id");
        if(validateForms()){
            var thTxt = $(this).val();
            if(thTxt==="提交"){
                //$(".submitSurvey").parent("div").css({"background-color": "red"});
                $('.submitSurvey').attr('disabled',"");
                $(".submitSurvey").val("正在提交");
                setTimeout(function(){
                    //$(".submitSurvey").parent("div").css({"background-color": "#373737"});
                    $('.submitSurvey').removeAttr("disabled");
                    $('.submitSurvey').val("提交");
                },5000);
                if(btnId=="previewSubmitSurvey"){
                    alert("当前为预览模式，不能提交答卷！");
                }else{
                    $("#surveyForm").submit();
                }
            }else{
                alert("5秒内禁止重复提交！");
            }
        }
        return false;
    });

    var focusFbk=null;
    //绑定操作事件
    //绑定操作事件
    $("#dwSurveyQuContent .dwQuOptionItemContent").unbind();
    $("#dwSurveyQuContent .dwQuOptionItemContent").click(function(){

        if(focusFbk!=null){
            focusFbk.blur();
        }
        clickItem($(this));
//			runlogic($(this).parents(".li_surveyQuItemBody"),$(this));
        answerProgressbar($(this));
        validateCheck($(this).parents(".li_surveyQuItemBody"),false);
        return false;
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

            runlogic(thObj.find("input[type='radio']"));
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
            runlogic(thObj.find("input[type='checkbox']"));
        }else if("MULTIFILLBLANK"===quType){

            var chenRow=thObj.parents(".mFillblankTableTr");
            chenRow.find(".dwQuInputLabel").removeClass("checked");
            chenRow.find("input[type='radio']").prop("checked",false);
            thObj.find("input[type='radio']").prop("checked",true);
            dwQuInputLabel.addClass("checked");

        }else if("SCALE"===quType){
            //单选题
            quItemBody.find(".inputchecked").removeClass("inputchecked");
            quItemBody.find("input[type='radio']").prop("checked",false);
            thObj.find("input[type='radio']").prop("checked",true);
            thObj.addClass("inputchecked");
            runlogic(thObj.find("input[type='radio']"));
        }
    }

    $(".radioSelect").change(function(){
        if(focusFbk!=null){
            focusFbk.blur();
        }
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

    $(".fillblankInput,.dwMFillblankInput").focus(function(){
        focusFbk = $(this);
        return false;
    });

    //填空题
    $(".fillblankInput,.dwMFillblankInput").blur(function(){
        //$(this).css("borderColor","#D6D6FF");
        runlogic($(this));
        answerProgressbar($(this));
//		validateCheck($(this).parents(".li_surveyQuItemBody"),true);
        var thVal=$(this).val();
        if(!$(this).hasClass("dateSelector") || thVal!=""){
            validateCheck($(this).parents(".li_surveyQuItemBody"),true);
        }
        focusFbk = null;
    });


    function resetQuNum(){
        var quTitleNums=$(".quTitleNum");
        $.each(quTitleNums,function(i,item){
            $(this).text((i+1)+"、")
        });
    }

//	resetQuNum();
    resetQuNumLogic();

    function resetQuNumLogic(){
        var surveyQuItemBodys = $(".li_surveyQuItemBody");
        var quIndex = 1;
        $.each(surveyQuItemBodys,function(i,item){
            var thClass = $(this).attr("class");
//				var quCoNum = $(this).find(".quCoNum");
            var quCoNum = $(this).find(".quTitleNum");
//			console.debug(thClass+":"+thClass.indexOf("hidFor"));
            if(thClass.indexOf("hidFor")<0 && quCoNum[0]){
                quCoNum.html((quIndex++)+"、");
            }
        });
    }



    //处理标题因有p标签影响题目序号换行的问题
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

    function runlogic(thFormElementObj){
        var quItemBody=thFormElementObj.parents(".li_surveyQuItemBody");
        var quLogicItems=quItemBody.find(".quLogicItem");
        if(quLogicItems[0]){
            var quInputCase=quItemBody.find(".quInputCase");
            var quId=quInputCase.find(".quId").val();
            var quType=quInputCase.find(".quType").val();
            if(quType==="RADIO" || quType==="CHECKBOX"){
                var quLgoicItem=null;
                var quOptionItems=quItemBody.find(".dwQuOptionItemContent");
                $.each(quLogicItems,function(){
                    var loginItem=$(this);
                    var cgQuItemId=loginItem.find(".cgQuItemId").val();
                    var skQuId=loginItem.find(".skQuId").val();
                    var logicId=loginItem.find(".logicId").val();
                    var logicType=loginItem.find(".logicType").val();

                    //过滤优先级
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
                                    var hidQuItemBodys=$(".hidFor"+logicId);
                                    $(".hidFor"+logicId).removeClass("hidFor"+logicId);
                                    var curPageNo = quItemBody.find(".pageNo").val();
                                    $.each(hidQuItemBodys,function(){
                                        var thQuItemBodyClass=$(this).attr("class");
                                        var  thPageNo = $(this).find(".pageNo").val();
                                        if(thQuItemBodyClass.indexOf("hidFor")<0 && thPageNo==curPageNo){
                                            $(this).show();
                                            //$(".hidFor"+loginId).fadeIn();
                                            //回答标记与逻辑设置没有关系
                                            $(this).find(".answerTag").attr("disabled",false);
                                        }
                                    });

                                }
                            }else{
                                if(logicStatus){

                                    var hidQuItemBodys=$(".hidFor"+logicId);
                                    $(".hidFor"+logicId).removeClass("hidFor"+logicId);
                                    var curPageNo = quItemBody.find(".pageNo").val();
                                    $.each(hidQuItemBodys,function(){

                                        var hidQuItemBody = $(this);
                                        var hidQuId = $(this).find(".quId").val();
                                        if(quType=="CHECKBOX" || quType=="RADIO"){
                                            $.each(quLogicItems,function() {
                                                var thLoginItem = $(this);
                                                var thSkQuId = thLoginItem.find(".skQuId").val();
                                                var thLogicType = thLoginItem.find(".logicType").val();
                                                var thLogicId=thLoginItem.find(".logicId").val();
                                                var logicHidForClass = "hidFor"+thLogicId;
//												console.debug(logicHidForClass);
                                                if(thLogicType==="2" && thSkQuId===hidQuId){
                                                    hidQuItemBody.removeClass(logicHidForClass);
                                                }
                                            });
                                        }

                                        var thQuItemBodyClass=$(this).attr("class");
                                        var thPageNo = $(this).find(".pageNo").val();
                                        if(thQuItemBodyClass.indexOf("hidFor")<0 && thPageNo==curPageNo){
                                            $(this).show();
                                            $(this).find(".answerTag").attr("disabled",false);
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

    resetQuNumLogic();

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

            }else if(nextQuType=="PAGETAG" ){
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
            }
            */
        }else{
            callback();
        }
    }
/*
    $("#dwSurveyQuContent .dwQuOptionItemContent").hover(function(){
        $(this).css({"border-color":"#1e94fc"});
        $(this).css({"background":"rgb(227, 237, 249)"});
    },function(){
        if(!($(this).hasClass("active") || $(this).hasClass("inputchecked"))){
            $(this).css({"border-color":"rgb(199, 225, 247)"});
            $(this).css({"background":"rgb(255, 255, 255)"});
        }
    });
    */

    bindDateEvent();

});

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

function validateCheck(quItemBody,isSubForm){
    if(quItemBody.is(":visible")){

        var quId=quItemBody.find(".quId").val();
        var quType=quItemBody.find(".quType").val();
        var isRequired=quItemBody.find(".isRequired").val();

        var validateStatus=false;
        var checkType = "";
        var errorMsg = null;

        if(isRequired==="0"){
            validateStatus = true;
            return true;
        }

        if(quType==="RADIO"){
            var hv = quItemBody.find(".hv").val();
            if(hv=="4"){
                var radioSelectVal = quItemBody.find("select.radioSelect").val();
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
                    var curText = curRadio.parents(".dwQuOptionItemContent").find("input[type='text']:visible");
                    if(curText[0]){
                        var curTextValue = curText.val();
                        checkType = curRadio.parents(".dwQuOptionItemContent").find(".checkType").val();
                        var isRequiredFill = curRadio.parents(".dwQuOptionItemContent").find(".isRequiredFill").val();
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
                var checkeds = quItemBody.find("input[type='checkbox']:checked");
                if(checkeds[0]&&checkeds.size()<paramInt01){
                    errorMsg = "请保证最少选择"+paramInt01+"项内容";
                    validateStatus = false;
                }
            }
            if(validateStatus){
//					isNote checkType isRequiredFill
                var curCheckboxs = quItemBody.find("input[type='checkbox']:checked");
                $.each(curCheckboxs,function(){
                    var curText = $(this).parents(".dwQuOptionItemContent").find("input[type='text']:visible");
                    if(curText[0]){
                        var curTextValue = curText.val();
                        checkType = $(this).parents(".dwQuOptionItemContent").find(".checkType").val();
                        var isRequiredFill = $(this).parents(".dwQuOptionItemContent").find(".isRequiredFill").val();
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
            validateStatus=!quItemBody.find(".quOrderItemHidInput[value='']")[0];
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
                }
                if(validateStatus){
                    var curTextValue = scoreNumInput.val();
                    checkType = $(this).parents(".mFillblankTableTr").find(".checkType").val();
                    validateStatus = checkoutData(checkType, curTextValue);
                }
                if(!validateStatus){
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
        /*if(isSubForm && !quItemBody.find(".errorItem")[0]){
         var errorHtml="<div class=\"errorItem\"><label for=\"\" class=\"error\">请检查题目答案，为必答项！</label></div>";
         quItemBody.find(".surveyQuItemContent").append(errorHtml);
         }*/
        if(isSubForm){
            if(quItemBody.find(".errorItem")[0]){
                quItemBody.find(".errorItem").remove();
            }

            var errorHtml="<div class=\"errorItem\"><label for=\"\" class=\"error\">请检查题目答案，为必答项！</label></div>";
            if(errorMsg!=null){
                errorHtml="<div class=\"errorItem\"><label for=\"\" class=\"error\">"+errorMsg+"</label></div>";
            }else{
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
                    errorHtml="<div class=\"errorItem\"><label for=\"\" class=\"error\">请输入日期时间类型，为必答项！</label></div>";
                }else if(checkType == "IDENTCODE"){
                    errorHtml="<div class=\"errorItem\"><label for=\"\" class=\"error\">请输入身份证号，为必答项！</label></div>";
                }else if(checkType == "ZIPCODE"){
                    errorHtml="<div class=\"errorItem\"><label for=\"\" class=\"error\">请输入邮编，为必答项！</label></div>";
                }else if(checkType == "URL"){
                    errorHtml="<div class=\"errorItem\"><label for=\"\" class=\"error\">请输入URL，为必答项！</label></div>";
                }
            }
            quItemBody.find(".surveyQuItemContent").append(errorHtml);
        }
    }
    return validateStatus;
}
