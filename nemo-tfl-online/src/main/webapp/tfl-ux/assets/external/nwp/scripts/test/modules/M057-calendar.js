(function (o) {
    o.init = function (selector) {
        $(selector).each(function () {
            var $this = $(this);
            $this.wrap('<div class="fc-calendar-wrapper clearfix" />');
            var $wrap = $this.parent();
            var $cal = $this.calendario({
                weekabbrs: ["S", "M", "T", "W", "T", "F", "S"],
                displayWeekAbbr: true,
                month: $this.attr("data-month"),
                year: $this.attr("data-year")
            });
            $wrap.prepend("<div class='current-month'><a href='javascript:void(0);' class='calendar-previous-month hide-text'>Previous month</a><span class='calendar-month'>" + $cal.getMonthName() + "</span> <span class='calendar-year'>" + $cal.getYear() + "</span><a href='javascript:void(0);' class='calendar-next-month hide-text'>Next month</a></div>");
            $wrap.find('.calendar-next-month').on('click', function () {
                if (!$(this).hasClass('disabled')) {
                    $cal.gotoNextMonth(function () {
                        updateMonthYear();
                        $this.attr("data-month", $cal.getMonth());
                        $this.attr("data-year", $cal.getYear());
                        $(window).trigger('calendar-next-month');
                    });
                }
            });
            $wrap.find('.calendar-previous-month').on('click', function () {
                if (!$(this).hasClass('disabled')) {
                    $cal.gotoPreviousMonth(function () {
                        updateMonthYear();
                        $this.attr("data-month", $cal.getMonth());
                        $this.attr("data-year", $cal.getYear());
                        $(window).trigger('calendar-previous-month');
                    });
                }
            });
            var $month = $wrap.find('.calendar-month');
            var $year = $wrap.find('.calendar-year');
            var updateMonthYear = function () {
                $month.html($cal.getMonthName());
                $year.html($cal.getYear());
            }
        });
    }

    o.init(".fc-calendar-container");

}(window.tfl.calendar = window.tfl.calendar || {}));