function bindDateEvent(){
    var checkTypeDates = $("#dwSurveyQuContent .checkType[value='DATE']");
    $.each(checkTypeDates,function(){
        var surveyQuItemBody = $(this).parents(".surveyQuItemBody");
        var paramInt01 = surveyQuItemBody.find(".paramInt01");
        var dateFmt = "yyyy-MM-dd";
        var dateType = "date";
        if(paramInt01[0]){
            var paramInt01Val = paramInt01.val();
            if(paramInt01Val==="1"){
                dateFmt="yyyy";
                dateType="year";
            }else if(paramInt01Val==="2"){
                dateFmt="yyyy-MM";
                dateType="month";
            }else if(paramInt01Val==="3"){
                dateFmt="yyyy-MM-dd";
                dateType="date";
            }else if(paramInt01Val==="4"){
                dateFmt="yyyy-MM-dd HH:mm:ss";
                dateType="datetime";
            }else if(paramInt01Val==="5"){
                dateFmt="HH:mm:ss";
                dateType="time";
            }else if(paramInt01Val==="6"){
                dateFmt="HH:mm";
                dateType="time";
            }
        }
       // console.debug("dateType:"+dateType);
       //  validateCheck($(this).parents(".li_surveyQuItemBody"),true);
       laydate.render({
            elem: surveyQuItemBody.find("input.fillblankInput")[0] //指定元素
            ,type: 'datetime'
            ,format: dateFmt
            ,type: dateType
             ,done: function(value, date, endDate){
                $(this.elem).val(value);
                var quItemBody = $(this.elem).parents(".li_surveyQuItemBody");
                answerProgressbar($(this.elem));
                validateCheck($(this.elem).parents(".li_surveyQuItemBody"),false);
              }
        });
    });
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
        /*
        var reg1 = /^[1-9]\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$/;//2014-01-01
                     var regExp1 = new RegExp(reg1);
                     var reg2 = /^(20|21|22|23|[0-1]\d):[0-5]\d:[0-5]\d$/;//12:00:00
                     var regExp2 = new RegExp(reg2);
                     var reg3 = /^[1-9]\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])\s+(20|21|22|23|[0-1]\d):[0-5]\d:[0-5]\d$/;//2014-01-01 12:00:00
                     var regExp3 = new RegExp(reg3);
                     if(!(regExp1.test(value) || regExp2.test(value) || regExp3.test(value) )){
                     //						alert("日期格式不正确，正确格式为：2014-01-01");
                     errorType = "DATE";
                     validateStatus = false;
                     }
         */
        /*if (/Invalid|NaN/.test(new Date(value).toString())) {
            validateStatus = false;
        }*/
        validateStatus = true;


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


var upEventIndex = 0;
function wwwUpload(){
  var ctx = $("#ctx").val();
  var url = '/api/dwsurvey/up/up-file-wb.do';
  //alert('data');
  $("#dwSurveyQuContentAppUl .fileupload").unbind();
  $("#dwSurveyQuContentAppUl .fileupload").fileupload({
    url: url,
    // maxFileCount:1,
    dataType: 'json',
    // forceIframeTransport: true,
    // autoUpload:true,
//  formData: {"safe":"webInf"},
    add: function(e, data) {

      var thObj = $(this);
      showUpFileMsg(thObj,"准备上传...");

      var surveyQuItemBody = $(this).parents(".surveyQuItemBody");
      var paramInt01 = surveyQuItemBody.find(".paramInt01").val();
      var paramInt02 = surveyQuItemBody.find(".paramInt02").val();
      var randOrder = surveyQuItemBody.find(".randOrder").val();

      var upAppend=surveyQuItemBody.find(".upFileDiv .upAppend");
      if(upAppend[0] && randOrder>0 && upAppend.size()>=randOrder ){
        // alert("最多只能上传"+randOrder+"个附件");
        showUpFileMsg(thObj,"最多只能上传"+randOrder+"个附件");
        return ;
      }

      if(paramInt01==1){
        /*
        var acceptFileTypes = /^image\/(gif|jpe?g|png)$/i;
        //文件类型判断
        if(data.originalFiles[0]['type'].length && !acceptFileTypes.test(data.originalFiles[0]['type'])) {
            alert("上传文件类型不对，请上传图片文件！");
            return ;
        }
         */
        var acceptFileTypes = /(\.|\/)(gif|jpg|jpeg|png|bmp)$/i;
        //文件类型判断
        if(!acceptFileTypes.test(data.originalFiles[0].name)) {
          // alert("上传文件类型不对，请上传图片文件！");
          showUpFileMsg(thObj,"上传文件类型不对，请上传图片文件！");
          return ;
        }
      }

      if(paramInt01==2){
//      var acceptFileTypes = /\/(txt|doc?x|xls?x|ppt?x|pdf|xml)$/i;
        var acceptFileTypes = /(\.|\/)(txt|doc|docx|xls|xlsx|ppt|pptx|pdf|xml)$/i;
        //文件类型判断
        if(!acceptFileTypes.test(data.originalFiles[0].name)) {
          // alert("上传文件类型不对,请上传文本文件！");
          showUpFileMsg(thObj,"上传文件类型不对,请上传文本文件！");
          return ;
        }
      }

      //文件大小判断
      if(paramInt02!=null && paramInt02!=""){
        var maxSize = 1024*1024*paramInt02;
        if(data.originalFiles[0]['size'] > maxSize) {
          // alert("文件大小超出限制,最大支持"+paramInt02+"M");
          showUpFileMsg(thObj,"文件大小超出限制,最大支持"+paramInt02+"M");
          return;
        }
      }
      /*
      var url = getUrl(data.files[0]);
      surveyQuItemBody.find(".quFillblankItem").prepend("<label class='upImgLabel' ><img class='upAppend' src='${ctx}"+url+"' height='150' >&nbsp;<a href='' ><i class='fa fa-times' aria-hidden='true'></i></a>" +
              "<input type='hidden' class='upFileName' value='"+data.files[0].name+"' />"+
              "<input type='hidden' class='upFilePath' value='' />"+
              "</label>");
      */
      data.submit();

      showUpFileMsg(thObj,"上传中...");
    },
    change: function (e, data) {
      //alert("change:"+data.files.length);
      /*
       $.each(data.files, function (index, file) {
       alert('Selected file: ' + file.name);
       console.log(file);
       });
       */
    },
    done: function (e, data) {
      // alert(JSON.stringify(data));
//        "{\"success\":\"true\",\"filename\":\"粉刷.jpeg  \",\"location\":\"/file/images/粉刷.jpeg\"}"
//             var result = data.result[0].body ? data.result[0].body.innerHTML : data.result;
//             result = JSON.parse(result);
//             var result = eval("("+data.result+")");
//             var location = result.location;
//             var fileName = result.filename;

      var location = null;
      var fileName = null;
      var httpResult = data.result;
      var resultCode = httpResult.resultCode;
      var resultData = httpResult.data;
      if(resultCode==200){
        //实际只会有一条
        $.each(resultData,function (i,item){
          location = item.location;
          fileName = item.filename;
        });
        //如果是图片，则取回进行显示
        var surveyQuItemBody = $(this).parents(".surveyQuItemBody");
        var paramInt01 = surveyQuItemBody.find(".paramInt01").val();
        if(false){
//      surveyQuItemBody.find(".quFillblankItem .upAppend").remove();
//      <label class='upImgLabel' ><img class='upAppend' src='' height='150' ><a href='' ><i class='fa fa-times' aria-hidden='true'></i></a></label>
//             surveyQuItemBody.find(".quFillblankItem").prepend("<img class='upAppend' src='${ctx}"+location+"' height='150' style='margin-bottom:8px;border:1px solid grey;padding:6px;' />");
          surveyQuItemBody.find(".quFillblankItem").prepend("<label class='upImgLabel' ><img class='upAppend' src='"+ctx+location+"' height='150' >&nbsp;<a href='' ><i class='fa fa-times' aria-hidden='true'></i></a></label>");
          surveyQuItemBody.find("span.fileinput-button span").text("继续上传");
          surveyQuItemBody.find(".fileuploadPath").val(location+"___"+fileName);
        }else{
          var fileuploadPathName = surveyQuItemBody.find(".fileuploadPath").attr("uphidinputname")+"_"+(upEventIndex++);
          var fileSaveValue = location+"___"+fileName;
//      surveyQuItemBody.find(".quFillblankItem .upAppend").remove();
//      surveyQuItemBody.find(".quFillblankItem").prepend("<div class='upAppend' style='margin-bottom:8px;border:1px solid grey;padding:6px;' > 文件名称："+fileName+" </div>");
          surveyQuItemBody.find(".quFillblankItem .upFileDiv").prepend('<div class="upAppend" ><label style="overflow: auto;width: 95%;">文件名称：'+fileName+'</label><a href="#" class="removeUpFile" ><i class="fa fa-times" aria-hidden="true"></i></a>' +
            '<input type="hidden" class="fileuploadSaveValue" name="'+fileuploadPathName+'" value="'+fileSaveValue+'" />' +
            '</div>');
          surveyQuItemBody.find("span.fileinput-button span").text("继续上传");
//      surveyQuItemBody.find(".fileuploadPath").val(fileSaveValue);
        }
        bindUpEvent();
        surveyQuItemBody.find(".answerTag").val(1);
        var quCoItemUlLi = $(this).parents(".quCoItemUlLi");
        quCoItemUlLi.find('.progress').fadeOut(3000);
        //保存路径
        var li_surveyQuItemBody = $(this).parents(".li_surveyQuItemBody");
        runlogic(li_surveyQuItemBody,$(this));
        validateCheck(surveyQuItemBody,false);
        answerProgressbar(surveyQuItemBody);
      }else{
        var thObj = $(this);
        //显示错误信息
        showUpFileMsg(thObj,httpResult.resultMsg);
      }
    },
    progressall: function (e, data) {
      var quCoItemUlLi = $(this).parents(".quCoItemUlLi");
      var progress = parseInt(data.loaded / data.total * 100, 10);
      quCoItemUlLi.find('.progress').show();
      quCoItemUlLi.find('.progress .progress-bar').css(
        'width',
        progress + '%'
      );
      quCoItemUlLi.find('.progressAfter').remove();
      quCoItemUlLi.find('.progress').after("<div class='progressAfter' style='font-size: 14px;color: dodgerblue;margin-top: 10px;'>上传进度："+progress+"%</div>");
    }
  }).prop('disabled', !$.support.fileInput).parent().addClass($.support.fileInput ? undefined : 'disabled');
}

function showUpFileMsg(thObj,upMessage) {
  var quCoItemUlLi = $(thObj).parents(".quCoItemUlLi");
  var progress = quCoItemUlLi.find('.progress');
  if(progress[0]){
    quCoItemUlLi.find('.progressAfter').remove();
    progress.show();
    progress.after("<div class='progressAfter' style='font-size: 14px;color: dodgerblue;margin-top: 10px;'>"+upMessage+"</div>");
  }
}
function bindUpEvent(){
  $(".removeUpFile").unbind();
  $(".removeUpFile").click(function(){
    var surveyQuItemBody = $(this).parents(".li_surveyQuItemBody");
    $(this).parents(".upAppend").remove();
    runlogic(surveyQuItemBody,null);
    validateCheck(surveyQuItemBody,false);
    answerProgressbar(surveyQuItemBody);
    var quId = surveyQuItemBody.find(".quId").val();
    // removeLocalStorage("qu_UPLOADFILE_"+quId);
    localStorageQu(surveyQuItemBody);
    return false;
  });
}
