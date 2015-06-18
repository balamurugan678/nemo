(function (o) {
    tfl.logs.create("tfl.flipTable: loaded");
    function getBreakpointResolution(bpName) {
        var d = tfl.settings.devices;
        for (var i = 0; i < d.length; i++) {
            if (d[i].name === bpName) {
                return d[i].resolution;
            }
        }
        return null;
    }

    o.init = function () {
        tfl.logs.create("tfl.flipTable: started");
        var css = "";
        var tableId = 0;
        $('.flip-table').each(function () {
            tableId++;
            var $table = $(this);
            var tClass = "flip-table-" + tableId;
            $table.addClass(tClass);
            var $cols = $table.find("thead td");
            var bpName = $table.data("flip-breakpoint");
            var bp = getBreakpointResolution(bpName);
            $(window).on("enterBreakpoint" + bpName, function () {
                $table.removeClass("flipped");
            });
            $(window).on("exitBreakpoint" + bpName, function () {
                $table.addClass("flipped");
            });

            if (!$(document.body).hasClass("breakpoint-" + bpName)) {
                $(window).trigger("exitBreakpoint" + bpName);
            }
            css += "@media screen and (max-width: " + bp + "px) {";
            for (var i = 0; i < $cols.length; i++) {
                var colName = $cols.eq(i).text();
                css += "." + tClass + " td:nth-of-type(" + (i + 1) + "):before { content: \"" + colName + "\" }";
            }

            css += "}";
            $(document.body).append("<style>" + css + "</style");
        });
    }
    o.init();
}(window.tfl.flipTable = window.tfl.flipTable || {}));