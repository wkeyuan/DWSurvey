function bindDateEvent(){
    var checkTypeDates = $("#dwSurveyQuContent input[name='checkType'][value='DATE']");
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
        console.debug("dateType:"+dateType);
        laydate.render({
            elem: surveyQuItemBody.find("input[type='text']")[0] //指定元素
            ,type: 'datetime'
            ,format: dateFmt
            ,type: dateType
        });
    });
}
