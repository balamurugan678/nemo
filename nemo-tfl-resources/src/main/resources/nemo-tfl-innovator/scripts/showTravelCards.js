$(document).ready(function () {
	$(".refundCalculationBasis").change(function() {
		var input = $("<input>").attr("type", "hidden").attr("name", "targetAction").val("updateCartTotal");
	    var formId = "#" + $(this).closest("form").attr('id');
	    $(formId).append($(input));
	    $(formId).submit();
	});
});