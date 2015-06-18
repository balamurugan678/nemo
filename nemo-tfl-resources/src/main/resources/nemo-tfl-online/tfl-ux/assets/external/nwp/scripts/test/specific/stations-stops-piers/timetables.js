(function (o) {
    "use strict";
    tfl.logs.create("tfl.stops.timetables: loaded");

    $("#DestinationId").on("change", function () {
        var $self = $(this);
        var destinationId = $self.val();
        var $firstLastEl = $("#first-last-details");
        var lineName = $firstLastEl.data("linename");
        var selectText = $self.children('[value="' + destinationId + '"]').text();
        if (selectText.indexOf("-") !== -1) {
            lineName = selectText.substring(selectText.indexOf(" - ") + 3);
        }
        var ajaxData = {
            destinationId: destinationId,
            isEndOfLine: $firstLastEl.data("isendofline"),
            lineId: $firstLastEl.data("lineid"),
            lineIds: $firstLastEl.data("lineids"),
            lineName: lineName,
            mode: $firstLastEl.data("mode"),
            naptanId: $firstLastEl.data("naptanid"),
            stopName: $firstLastEl.data("stopname")
        };

        var ajaxSuccess = function (response) {
            $firstLastEl.empty().append(response);
            tfl.tools.setQueryStringParameter("destinationId", destinationId);
        };

        var ajaxError = function () {
            $firstLastEl.append('<div class="r"><span class="field-validation-error">' + tfl.apiErrorMessage + '</span></div>');
        };

        var ajaxComplete = function () {
            $firstLastEl.removeClass("loading");
        };

        // add throbber
        $firstLastEl.addClass("loading");

        // get data
        tfl.ajax({
            url: "/Stops/FirstLastServiceDetails",
            data: ajaxData,
            success: ajaxSuccess,
            error: ajaxError,
            complete: ajaxComplete,
            dataType: 'text'
        });
    });

}(window.tfl.stops.timetables = window.tfl.stops.timetables || {}));
