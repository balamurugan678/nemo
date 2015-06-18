$(document).ready(function () {
    addDatePickerForDateOfRefund(pageName, shortDatePattern);
});

function addDatePickerForDateOfRefund(pageName, shortDatePattern) {
    var date = new Date();
    var elementId = pageName + "\\." + "dateOfRefund";
    $("#"+elementId).datepicker({
        dateFormat: shortDatePattern,
        changeYear: true,
        changeMonth: true,
        maxDate: new Date(date.getFullYear(), date.getMonth(), date.getDate()),
    });
}

