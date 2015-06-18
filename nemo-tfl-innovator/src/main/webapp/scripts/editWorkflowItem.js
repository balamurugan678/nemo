$(document).ready(function () {
	addDatePickerForRefundablePeriod(pageName, shortDatePattern);

});

function addDatePickerForRefundablePeriod(pageName, shortDatePattern) {
    var refundablePeriodId = pageName + "\\." + "refundablePeriodStartDate";
    $("#" + refundablePeriodId).datepicker({
        dateFormat: shortDatePattern,
        changeYear: true,
        changeMonth: true,
    });
}
