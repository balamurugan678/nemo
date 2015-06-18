(function (o) {

    // No need to include this script with the module, as it is combined into the global set. If you wish to refresh the handles due to a dom change, just call tfl.expandableBox.init.

    o.init = function () {
        $(".always-visible").off("click.expandableBox").on("click.expandableBox", function () {
            var $par = $(this).parent();
            if (!$par.hasClass("expanded")) {
                $par.addClass("expanded");
                $(this).attr('aria-expanded', 'true')
                    .trigger("expandable-box.expanded");
            } else {
                $par.removeClass("expanded");
                $(this).attr('aria-expanded', 'false')
                    .trigger("expandable-box.collapsed");
            }
            return false;
        })
            .keypress(function (e) {
                if (e.which == 13) {
                    $(this).click();
                }
            })
    }

    o.init();

}(window.tfl.expandableBox = window.tfl.expandableBox || {}));