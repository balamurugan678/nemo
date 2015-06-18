(function (o) {

    // No need to include this script with the module, as it is combined into the global set. If you wish to refresh the handles due to a dom change, just call tfl.expandableBox.init.
    function breadcrumb($el) {
        var $breadcrumbs = $el,
            $parent = $breadcrumbs.parent(),
            $items = $breadcrumbs.find(">li"),
            $ellipsis = null,
            ellipsisWidth = 0,
            breakpointArrowWidth = 13,
            measurements = [],
            breakpoints = [],
            totalWidth = 0,
            width = 0,
            currentBreakpoint = 0,
            hiding = false,
            scrolling = false,
            resizeFunc = null;
        return {
            init: function () {
                $ellipsis = $("<li class='ellipsis hidden'><a href='javascript:void(0)'>...</a></li>");
                $items.eq(0).after($ellipsis);
                ellipsisWidth = $ellipsis.width();
                $ellipsis.click(this.makeScrollable);
                $items.each(function () {
                    var w = $(this).width()
                    measurements.push(w);
                    totalWidth += w;
                });
                totalWidth += breakpointArrowWidth;
                breakpoints.push(totalWidth);
                var c = totalWidth;
                for (var i = 1; i < measurements.length; i++) {
                    c -= measurements[i];
                    breakpoints.push(c + ellipsisWidth);
                }
                resizeFunc = $.proxy(this.onResize, this);
                $(window).on("resize", resizeFunc);
                this.onResize();
            },
            onResize: function () {
                width = $breadcrumbs.width();
                if (width < totalWidth) {
                    if (width <= breakpoints[currentBreakpoint]) {
                        hiding = true;
                        $ellipsis.removeClass("hidden");
                        while (width <= breakpoints[currentBreakpoint]) {
                            this.hideItem(currentBreakpoint);
                            currentBreakpoint++;
                        }
                    } else if (width > breakpoints[currentBreakpoint]) {
                        while (width > breakpoints[currentBreakpoint]) {
                            this.showItem(currentBreakpoint);
                            currentBreakpoint--;
                        }
                    }
                } else if (hiding) {
                    $ellipsis.addClass("hidden");
                    hiding = false;
                    while (width >= breakpoints[currentBreakpoint]) {
                        this.showItem(currentBreakpoint);
                        currentBreakpoint--;
                    }
                    if (currentBreakpoint < 0) {
                        currentBreakpoint = 0;
                    }
                }
            },
            hideItem: function (idx) {
                $items.eq(idx + 1).hide();
            },
            showItem: function (idx) {
                $items.eq(idx + 1).show();
            },
            makeScrollable: function () {
                if (tfl.utils.isTouchDevice()) {
                    $parent.addClass("scrolling");
                    $breadcrumbs.css({ width: totalWidth + "px" });
                }
                $ellipsis.addClass("hidden");
                $items.each(function () {
                    $(this).show();
                });
                $(window).off("resize", resizeFunc);
                return false;
            }
        };
    };

    o.init = function () {
        var $breadcrumbs = $("ul.breadcrumbs");
        var $breadcrumbCount = $breadcrumbs.length;

        $("ul.breadcrumbs").each(function () {
            $breadcrumbs = $(this);
            new breadcrumb($breadcrumbs).init();
        })
    }

    o.init();

}(window.tfl.breadcrumb = window.tfl.breadcrumb || {}));