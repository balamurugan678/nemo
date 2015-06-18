$(document).ready(function () {
	$(".refundCalculationBasis").change(function() {
		var input = $("<input>").attr("type", "hidden").attr("name", "targetAction").val("updateRefundCalculationBasis");
		var refundCalculationBasis = $("<input>").attr("type", "hidden").attr("name", "refundCalculationBasis").val($(this).val());
		var refundCalculationBasisIdValue = $(this).attr('id').replace("refundCalculationBasis", "");
		$("#cartItemId").val(refundCalculationBasisIdValue);
	    var formId = "#" + $(this).closest("form").attr('id');
	    $(formId).append($(input));
	    $(formId).append($(refundCalculationBasis));
	    $(formId).submit();
	});
	
    setLineNoIfDeleteTravelCardClicked();
});

function getCartItemIdIfRefundCalculationBasisChanged(idValue) {
	idValue = 
	$("#cartItemId").val(idValue);
}

function setLineNoIfDeleteTravelCardClicked() {
	$(".deleteTravelCard").each(function(){
		$(this).click(function(){
			setLineNoFromDeleteTravelCardIdValue($(this).attr('id'));
			return true;
		});
	});
}

function setLineNoFromDeleteTravelCardIdValue(idValue) {
	idValue = idValue.replace("deleteTravelCard", "");
	idValue = idValue.replace("-submit", "");
	$("#cartItemId").val(idValue);
}
