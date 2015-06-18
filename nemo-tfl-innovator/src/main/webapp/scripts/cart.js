$(document).ready(function () {
	$(".removeButton").click(function(){
        $("#lineNo").val($(this).prev().val());
    });
});