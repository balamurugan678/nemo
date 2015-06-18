(function (o) {
    "use strict";
    tfl.logs.create("tfl.journeyPlanner: loaded");

    tfl.cachingSettings = { daily: getCachingKey() };

    function getCachingKey() {
        var d = new Date();
        var n = String(d.getFullYear()) + String(d.getMonth()) + String(d.getDay()) + String(d.getHours());
        return n;
    }

    o.settings = {
        disambiguationItemsPerPage: 9
    };

    o.init = function () {
        tfl.logs.create("tfl.journeyPlanner.init: started");
        o.createJourneys();
    };


    o.createJourneys = function () {
        if (!tfl.storage.isLocalStorageSupported()) {
            tfl.logs.create("tfl.journeyplanner: Not creating recent journeys box - localStorage not present.");
            return;
        }
        tfl.logs.create("tfl.journeyplanner: Creating recent journeys box");
        $("#recent-journeys").empty();
        if ($("div[data-set=recent-journeys]").length === 0) {
            $("#more-journey-options").after("<div class='small' data-set='recent-journeys' />");
            $("#plan-a-journey").after("<div class='medium-large' data-set='recent-journeys'><div id='recent-journeys' class='moving-source-order' /></div>");
        }
        var recentJourneysBox = $("#recent-journeys");
        recentJourneysBox.empty();
        recentJourneysBox.append("<h3>Recent journeys</h3>");
        if (!tfl.journeyPlanner.recentJourneys.usingRecentJourneys) {
            recentJourneysBox.append("<p>Turn on recent searches so you can access them easily again<br/><a href='javascript:void(0)' class='turn-on-recent-journeys' data-jumpback='#SavePreferences:visible' data-jumpto='#footer a:first'>Turn on recent journeys</a></p>");
        } else {
            tfl.journeyPlanner.recentJourneys.loadJourneys();
            if (tfl.journeyPlanner.recentJourneys.journeys.length > 0) {
                var journeyList = $("<div class='vertical-button-container' />");

                $(tfl.journeyPlanner.recentJourneys.journeys).each(function (i) {
                    var text = "<strong>" + this.from + "</strong> to <strong>" + this.to + "</strong>";
                    if (this.via !== "") {
                        text += " via <strong>" + this.via + "</strong>";
                    }
                    //tab back
                    var link;
                    if (i === 0) {
                        link = $("<a href='javscript:void(0)' class='plain-button' data-jumpback='#SavePreferences:visible'>" + text + "</a>");
                    } else {
                        link = $("<a href='javscript:void(0)' class='plain-button'>" + text + "</a>");
                    }
                    link.click(function () {
                        o.redoJourney(i);
                        return false;
                    });
                    journeyList.append(link);
                });
                recentJourneysBox.append(journeyList);
                recentJourneysBox.append("<p><a href='javascript:void(0)' class='turn-off-recent-journeys' data-jumpto='#footer a:first' data-jumpback='#SavePreferences:visible'>Turn off and clear recent journeys</a></p>");
            } else {
                recentJourneysBox.append("<p>You currently have no saved journeys<br/><a href='javascript:void(0)' class='turn-off-recent-journeys' data-jumpto='#footer a:first' data-jumpback='#SavePreferences:visible'>Turn off recent journeys</a></p>");
            }
        }

        $("div.medium-large[data-set=recent-journeys]").append(recentJourneysBox);

        $(".moving-source-order").appendAround();
        $(".turn-on-recent-journeys").click(function (event) {
            event.preventDefault();
            tfl.journeyPlanner.recentJourneys.useJourneys(true);
            tfl.journeyPlanner.searchForm.destroyAutocomplete();
            tfl.journeyPlanner.searchForm.setupAutocomplete(false);
            if ($("#recent-journeys").length != 0) {
                o.createJourneys();
            }
        });
        $(".turn-off-recent-journeys").click(function (event) {
            event.preventDefault();
            tfl.journeyPlanner.recentJourneys.useJourneys(false);
            tfl.journeyPlanner.searchForm.destroyAutocomplete();
            tfl.journeyPlanner.searchForm.setupAutocomplete(false);
            if ($("#recent-journeys").length != 0) {
                o.createJourneys();
            }
        });
    };

    o.redoJourney = function (idx) {
        var journey = tfl.journeyPlanner.recentJourneys.journeys[idx];
        var async = false;
        var doJourney = function () {
            $("#jp-search-form").submit();
        }
        if (journey.from === tfl.dictionary.CurrentLocationText) {
            async = true;
            tfl.journeyPlanner.searchForm.geolocateMe("#From", doJourney);
        } else {
            $("#From").val(journey.from);
            $("#From").attr("data-from-id", journey.fromId);
            $("#From").attr("data-from-modes", JSON.stringify(journey.fromModes));
        }
        if (journey.to === tfl.dictionary.CurrentLocationText) {
            async = true;
            tfl.journeyPlanner.searchForm.geolocateMe("#To", doJourney);
        } else {
            $("#To").val(journey.to);
            $("#To").attr("data-to-id", journey.toId);
            $("#To").attr("data-to-modes", JSON.stringify(journey.toModes));
        }
        if (journey.via !== null) {
            if (journey.via === tfl.dictionary.CurrentLocationText) {
                async = true;
                tfl.journeyPlanner.searchForm.geolocateMe("#Via", doJourney);
            } else {
                $("#Via").val(journey.via);
                $("#Via").attr("data-via-id", journey.viaId);
                $("#Via").attr("data-via-modes", JSON.stringify(journey.viaModes))
            }
        } else {
            $("#Via").val("");
        }
        if (!async) {
            doJourney();
        }
    };


})(window.tfl.journeyPlanner = window.tfl.journeyPlanner || {});
