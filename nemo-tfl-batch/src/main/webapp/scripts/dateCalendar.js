

$(document).ready(function () {
	var shortDatePattern ="dd/mm/yy";
    addDatePickerForDateOfRefund(shortDatePattern);
});

function addDatePickerForDateOfRefund(shortDatePattern) {
    var elementId = "priceEffectiveDate";
    $("#"+elementId).datepicker({
        dateFormat: shortDatePattern,
        changeYear: true,
        changeMonth: true,
    });
}