function getJourneyHistoryDatePickerSettings() {
    var settings = returnDatePickerSettings();
    settings.changeYear = true;
    settings.changeMonth = true;
    settings.minDate = "-56d";
    settings.maxDate = "-1d";
    return settings;
}

function getJourneyHistoryDataTableSettings() {
    return {bSort: false, bFilter: false, bInfo: false, iDisplayLength: 20};
}

$(document).ready(function () {

    var startDate = $("#" + startDateElementId).datepicker(getJourneyHistoryDatePickerSettings());
    var endDate = $("#" + endDateElementId).datepicker(getJourneyHistoryDatePickerSettings());

    startDate.datepicker("option", "onSelect", function (date) {
        var targetDate = startDate.datepicker("getDate");
        endDate.datepicker("option", "minDate", startDate.datepicker('getDate'));
        if (endDate.datepicker("getDate") === null) {
            targetDate.setDate(targetDate.getDate() + 7);
            endDate.datepicker("setDate", targetDate);
        }
    });

    $("#cancel").click(function () {
        history.back();
    });

    $("#getDetails-submit").click(function () {
        toggleLoading();
    });

    $('#JourneyHistoryTable').dataTable(getJourneyHistoryDataTableSettings());
});
