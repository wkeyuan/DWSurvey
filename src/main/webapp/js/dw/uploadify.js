function binduploadImg(tImageId,fileQueueid,hiddenpath,buttonText){
	var ctx=$("input[name='ctx']").val();
	$("#"+tImageId).unbind();
	$("#"+tImageId).uploadify({
		swf: ctx+'/js/plugs/uploadify-v3.1/uploadify.swf',//[必须设置]swf的路径
		uploader: ctx+'/up/upload!saveimage.action;',//[必须设置]上传文件触发的url
		queueID: fileQueueid,
		method: 'post',//和后台交互的方式：post/get
		formData: {"basepath":"edu"},//和后台交互时，附加的参数
		progressData:'speed',// 'percentage''speed''all'//队列中显示文件上传进度的方式：all-上传速度+百分比，percentage-百分比，speed-上传速度
		auto:true,//文件选择完成后，是否自动上传
		multi: true,//是否能选择多个文件
		uploadLimit: 0,//上传个数限制
		fileObjName:'uploadify',
	    fileSizeLimit : 0,//文件的极限大小，以字节为单位，0为不限制。1MB:1*1024*1024
	    fileTypeDesc: 'Bild JPG',//允许上传的文件类型的描述，在弹出的文件选择框里会显示
	    fileTypeExts: '*.jpg;*.gif;*.jpeg;*.png;*.bmp;',//允许上传的文件类型，限制弹出文件选择框里能选择的文件
		debug: false,//debug模式开/关，打开后会显示debug时的信息
		height:26,
		width:66,
		buttonText: buttonText,//上传按钮的文字
		//buttonImage:ctx+'/images/uploadpic.jpg',
		queueSizeLimit : 999,//队列中允许的最大文件数目
		timeoutuploadLimit:999,//能同时上传的文件数目
		removeTimeout: 0,//已完成文件的移除延迟时间，默认3；
		onUploadSuccess: function (file,data,response) {                  //上传完成时事件 
           /* alert('The file ' + file.name + ' finished processing.');$('#file_upload').uploadify('disable', true);       //设置上传按钮不可用*/
				//alert("成功"+ctx+ datatemp.location);
		  //处理回显
		 // var datatemp={"success":"true","filename":"Tulips.jpg  ","location":"/file/images/8o70tffwwhbhzjv.jpg"};
			alert(data);
		  var datatemp=eval("("+data+")");
		  if(datatemp.success=="true"){
			var themeParam=$("#"+tImageId).parents(".theme_param");
			//var prevHost="http://file.diaowen.net/";
			  var prevHost=ctx;
			themeParam.find(".previewImage").attr("src",prevHost+ datatemp.location);
			themeParam.find(".paramtag").val(datatemp.location);
			//设置显示
			if(tImageId=="bodyBgImageFile"){
				//$("#wrap").css({"background-image":"url("+ctx+ datatemp.location+")"});
				$("body").css({"background-image":"url("+prevHost+ datatemp.location+")"});
			}else if(tImageId=="surveyHeadBgImageFile"){
				$("#dwSurveyHeader").css({"background-image":"url("+prevHost+ datatemp.location+")"});
			}else if(tImageId=="surveyContentBgImageMiddleFile"){
				$("#dwSurveyQuContentBg").css({"background-image":"url("+prevHost+ datatemp.location+")"});
			}else if(tImageId=="surveyLogoImageFile"){
				$("#dwSurveyLogo").find("img").attr("src",prevHost+ datatemp.location);
				$("#dwSurveyLogo").show();
				$("#dwSurveyTitle").removeClass("noLogoImg");
			}else if(tImageId=="loginBgfile"){
				//alert(prevHost+ datatemp.location);
				$("#loginBgImgSrc").attr("src",prevHost+ datatemp.location);
				$("#loginBgImgSrc").show();
				$("#loginBgfileImgPath").val(datatemp.location);
			}
			themeParam.find(".upUseImgCheck").prop("checked",true);
		  }else{
			//resImgfilename.text("上传封面失败。");
		  }
        },
        onUploadError: function (file, errorCode, errorMsg, errorString) {    //错误提示 
            alert('The file ' + file.name + ' could not be uploaded: ' + errorString);
        }
	});
	
	$(".upload_event").unbind();
	$(".upload_event").click(function(){
		//上传事件，调用上传控件上传方法
		var curFile=$("#"+$(this).attr("lang"));
		$(curFile).uploadifyUpload();
		
		//v3.1
       //$('#file_upload').uploadify('settings', 'formData', { 'id': 123, 'sid': 22, 'pid': 333 });   //设置表单数据 
       //$('#file_upload').uploadify('upload');                                              //开始上传 
	});
	$(".upload_clear").unbind();
	$(".upload_clear").click(function(){
		//取消上传事件，调用取消上传方法
		var curFile=$("#"+$(this).attr("lang"));
		$(curFile).uploadifyClearQueue();
	});
}

function binduploadContactsFile(tImageId,fileQueueid,hiddenpath,buttonText){
	var ctx=$("input[name='ctx']").val();
	$("#"+tImageId).unbind();
	$("#"+tImageId).uploadify({
		swf: ctx+'/js/plugs/uploadify-v3.1/uploadify.swf',//[必须设置]swf的路径
		uploader: ctx+'/up/upload!saveUpFile.action;',//[必须设置]上传文件触发的url
		queueID: fileQueueid,
		method: 'post',//和后台交互的方式：post/get
		formData: {"basepath":"edu"},//和后台交互时，附加的参数
		progressData:'speed',// 'percentage''speed''all'//队列中显示文件上传进度的方式：all-上传速度+百分比，percentage-百分比，speed-上传速度
		auto:true,//文件选择完成后，是否自动上传
		multi: true,//是否能选择多个文件
		uploadLimit: 0,//上传个数限制
		fileObjName:'uploadify',
	    fileSizeLimit : 0,//文件的极限大小，以字节为单位，0为不限制。1MB:1*1024*1024
	    fileTypeDesc: 'Bild JPG',//允许上传的文件类型的描述，在弹出的文件选择框里会显示
	    fileTypeExts: '*.xls;',//允许上传的文件类型，限制弹出文件选择框里能选择的文件
		debug: false,//debug模式开/关，打开后会显示debug时的信息
		height:26,
		width:66,
		buttonText: buttonText,//上传按钮的文字
		//buttonImage:ctx+'/images/uploadpic.jpg',
		queueSizeLimit : 999,//队列中允许的最大文件数目
		timeoutuploadLimit:999,//能同时上传的文件数目
		removeTimeout: 0,//已完成文件的移除延迟时间，默认3；
		onUploadSuccess: function (file,data,response) {                  //上传完成时事件 
           /* alert('The file ' + file.name + ' finished processing.');$('#file_upload').uploadify('disable', true);       //设置上传按钮不可用*/
				//alert("成功"+ctx+ datatemp.location);
		  //处理回显
		 // var datatemp={"success":"true","filename":"Tulips.jpg  ","location":"/file/images/8o70tffwwhbhzjv.jpg"};
		//	alert(data);
		  var datatemp=eval("("+data+")");
		  if(datatemp.success=="true"){
			$("#"+hiddenpath).val(datatemp.location);
			$("#upContactsFileShow").text(datatemp.filename+"上传成功");
		  }else{
			//resImgfilename.text("上传封面失败。");
		  }
        },
        onUploadError: function (file, errorCode, errorMsg, errorString) {    //错误提示 
            alert('The file ' + file.name + ' could not be uploaded: ' + errorString);
        }
	});
	
	$(".upload_event").unbind();
	$(".upload_event").click(function(){
		//上传事件，调用上传控件上传方法
		var curFile=$("#"+$(this).attr("lang"));
		$(curFile).uploadifyUpload();
		
		//v3.1
       //$('#file_upload').uploadify('settings', 'formData', { 'id': 123, 'sid': 22, 'pid': 333 });   //设置表单数据 
       //$('#file_upload').uploadify('upload');                                              //开始上传 
	});
	$(".upload_clear").unbind();
	$(".upload_clear").click(function(){
		//取消上传事件，调用取消上传方法
		var curFile=$("#"+$(this).attr("lang"));
		$(curFile).uploadifyClearQueue();
	});
}