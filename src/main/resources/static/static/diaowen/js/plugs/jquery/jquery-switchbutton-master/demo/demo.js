$(function(){
	
	/* Simple ones */
	
	$(".common:checkbox").switchbutton();
	
	
	
	/* Switch 5: with check/enable actions */
	
	$("#switch5").switchbutton();
	
	$("#uncheck5").click(function(){
		$("#switch5").prop("checked", false).change();
	});
	
	$("#check5").click(function(){
		$("#switch5").prop("checked", true).change();
	});
	
	$("#disable5").click(function(){
		$("#switch5").switchbutton("disable");
	});
	
	$("#enable5").click(function(){
		$("#switch5").switchbutton("enable");
	});
	
	
	/* Switch 6: with custom labels and callbacks */
	
	$("#switch6")
		.switchbutton({
			checkedLabel: 'YES',
			uncheckedLabel: 'NO'
		})
		.change(function(){
			alert("Switch 6 changed to " + ($(this).prop("checked") ? "checked" : "unchecked"));
		});
	
	/* Switch 7: with custom options */
	
	$("#switch7").switchbutton({
		classes: 'ui-switchbutton-thin',
		labels: false
	});
	
	/* Switch 8: ios style */
	
	$("#switch8").switchbutton({
		classes: 'ui-switchbutton-ios5'
	});
	
});

$(function(){
	
	$("#showCode").click(function(){
		$("#pageCode").slideToggle();
	});
	
});
