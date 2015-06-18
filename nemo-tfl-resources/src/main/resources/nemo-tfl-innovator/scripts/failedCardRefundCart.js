$(document).ready(function () {
    addDatePickerForDateOfRefund(pageName, shortDatePattern);
    dateOfRefundValueChanged();
});

function addDatePickerForDateOfRefund(pageName, shortDatePattern) {
    var date = new Date();
    var elementId = pageName + "\\." + "dateOfRefund";
    $("#"+elementId).datepicker({
        dateFormat: shortDatePattern,
        changeYear: true,
        changeMonth: true,
        maxDate: new Date(date.getFullYear(), date.getMonth(), date.getDate()),
        onSelect: function() {
        	var input = $("<input>").attr("type", "hidden").attr("name", "targetAction").val("updateCartTotal");
            var formId = "#" + $(this).closest("form").attr('id');
            $(formId).append($(input));
            $(formId).submit();
        }
    });
}

function dateOfRefundValueChanged() {
	$(".dateOfRefundValue").change(function() {
		var input = $("<input>").attr("type", "hidden").attr("name", "targetAction").val("updateCartTotal");
	    var formId = "#" + $(this).closest("form").attr('id');
	    $(formId).append($(input));
	    $(formId).submit();
	});
}