(function (o) {
    "use strict";
    tfl.logs.create("tfl.datePicker: loaded");

    o.init = function () {
        tfl.logs.create("tfl.datePicker: initialised");
        o.highlightCalendarDays();
        $(window).on("calendar-next-month calendar-previous-month", o.highlightCalendarDays);
    };

    function makeDate(day, month, year) {
        var date = new Date();
        if (day) {
            date.setDate(day);
        }
        if (month) {
            date.setMonth(month - 1);
        }
        if (year) {
            date.setYear(year);
        }
        return date;
    }

    function addLeadingZero(numStr) {
        return numStr.length == 1 ? "0" + numStr : numStr;
    }

    o.highlightCalendarDays = function () {
        //highlighting every day
        var $calendarContainer = $(".fc-calendar-container");

        var month = $calendarContainer.attr("data-month");
        var year = $calendarContainer.attr("data-year");
        var displayedCalendarMonth = makeDate(1, month, year);

        var $dateLinkContainer = $(".date-link-container");
        var currentDate = makeDate($dateLinkContainer.attr("data-current-day"), $dateLinkContainer.attr("data-current-month"), $dateLinkContainer.attr("data-current-year"));

        var $selectedDate = $(".date-link .selected-date");
        var selectedDay = $selectedDate.attr("data-selected-day");
        var selectedMonth = $selectedDate.attr("data-selected-month");
        var selectedYear = $selectedDate.attr("data-selected-year");

        if (displayedCalendarMonth <= currentDate) {
            $(".calendar-previous-month").addClass("disabled");
        } else {
            $(".calendar-previous-month").removeClass("disabled");
        }

        var calendarioDays = $calendarContainer.find(".fc-date");
        var iDate = makeDate(null, month, year);

        selectedMonth = addLeadingZero(selectedMonth);
        month = addLeadingZero(month);
        var href = $dateLinkContainer.attr("data-href");
        if (href.indexOf("?") < 0) {
            href += "?";
        }
        else {
            href += "&";
        }

        for (var i = 0; i < calendarioDays.length; i++) {
            var $calendarioDay = $(calendarioDays[i]);
            iDate.setDate(i + 1);
            var dayText = $calendarioDay.text();
            if (selectedYear == year && selectedMonth == month && selectedDay == dayText) {
                $calendarioDay.parent().addClass("fc-today");
            } else if (iDate > currentDate) {
                var day = addLeadingZero(dayText);
                var ymd = year + "-" + month + "-" + day;
                $calendarioDay.wrap("<a href='" + href + "startDate=" + ymd + "T00%3a00%3a00&endDate=" + ymd + "T23%3a59%3a59' />").parent().parent().addClass("highlighted-day");
            }
        }
    };

})(window.tfl.datePicker = window.tfl.datePicker || {});