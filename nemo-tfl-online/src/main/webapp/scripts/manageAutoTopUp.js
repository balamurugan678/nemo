var controllerLocation = "ManageAutoTopUp.htm";

$(document).ready(function () {
	var value = $('#autoTopUpOrderStatus').val();
	var autoTopUpStateAmtVal = $('#autoTopUpStateAmount').val();
	if(value == "true" && (autoTopUpStateAmtVal>0)){
	var fieldSetid = $(".autoTopUpStateClass");
	$(fieldSetid).attr('disabled', true);
	}
});


