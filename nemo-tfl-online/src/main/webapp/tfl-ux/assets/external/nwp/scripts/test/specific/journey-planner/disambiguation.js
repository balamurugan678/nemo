window.tfl.journeyPlanner = window.tfl.journeyPlanner || {};
(function (o) {
    "use strict";
    tfl.logs.create("tfl.journeyPlanner.disambiguation: loaded");

    o.init = function () {
        tfl.logs.create("tfl.journeyPlanner.disambiguation: initialised");
        var disambiguations = $(".disambiguation-wrapper");
        var numDisambiguations = disambiguations.length;
        var i = 0;
        if (numDisambiguations > 0) {
            $(".disambiguation-extras").prepend("<div class='pagination' />");
            $(".disambiguation-box").each(function () {
                var numItems = $(this).find(".disambiguation-option").length;
                if (numItems > tfl.journeyPlanner.settings.disambiguationItemsPerPage) {
                    tfl.navigation.pagination.setup($(this), ".disambiguation-items", tfl.journeyPlanner.settings.disambiguationItemsPerPage, numItems, true);
                }
                var wrapper = $(this).parents(".disambiguation-wrapper");
                var isFrom = wrapper.hasClass("from-results");
                if (isFrom) {
                    $(".summary-row").eq(0).addClass("disambiguating");
                } else {
                    var isTo = wrapper.hasClass("to-results");
                    if (isTo) {
                        $(".summary-row").eq(1).addClass("disambiguating");
                    } else {
                        $(".via-destination").addClass("disambiguating");
                    }
                }
            });
        }
        $(".disambiguation-link").click(function () {
            var row = $(this).parents(".disambiguation-wrapper");
            if (row.hasClass("from-results")) {
                $("#From").val($(this).find(".place-name").text()).after("<input type='hidden' name='FromId' value='" + $(this).attr("data-param") + "' />");
                $("#disambiguation-map-from").html("");
            } else if (row.hasClass("to-results")) {
                $("#To").val($(this).find(".place-name").text()).after("<input type='hidden' name='ToId' value='" + $(this).attr("data-param") + "' />");
                $("#disambiguation-map-to").html("");
            } else if (row.hasClass("via-results")) {
                $("#Via").val($(this).find(".place-name").text()).after("<input type='hidden' name='ViaId' value='" + $(this).attr("data-param") + "' />");
                $("#disambiguation-map-via").html("");
            }

            var next = row.next(".disambiguation-wrapper");
            if (next.length >= 1) {
                row.hide();
                next.show();
                next.find(".disambiguation-map").controller().divResized();
            } else {
                $("#jp-search-form").submit();
            }
            return false;
        });
        disambiguations.each(function () {
            if (i > 0) {
                $(this).hide();
            }
            i++;
        });

        tfl.mapInteractions.init();

        function setupMapInteractions(id, $options) {
            var mapObject = tfl.maps[id].googleMap;
            mapObject.on('optionChosen', function (option) {
                $options.find(".disambiguation-option[data-id='" + option.id + "'] .disambiguation-link")[0].click();
            });

            var $pagination = $options.parent().find(".pagination");
            if ($pagination.length) {
                tfl.navigation.pagination.addMapControls($pagination, mapObject.controller);
            }
        }

        $("#disambiguation-options-from, #disambiguation-options-to, #disambiguation-options-via").each(function () {
            var $this = $(this);
            if ($this.find(".disambiguation-option").length > 0) {
                var id = $(this).attr('id').replace('-options-', '-map-').replace('-', '');
                if (tfl.maps[id] !== undefined) {
                    setupMapInteractions(id, $this);
                } else {
                    $(window).on('map-object-created-' + id, function () {
                        setupMapInteractions(id, $this);
                    });
                }
            }
        });

    };

})(window.tfl.journeyPlanner.disambiguation = window.tfl.disambiguation || {});