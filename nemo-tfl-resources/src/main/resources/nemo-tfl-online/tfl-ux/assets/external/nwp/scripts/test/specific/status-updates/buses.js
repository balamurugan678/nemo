window.tfl.statusUpdates = window.tfl.statusUpdates || {};
(function (o) {
    "use strict";
    tfl.logs.create("tfl.serviceStatus.buses: loaded");
    //o.favouriteLimit = 10;
    //o.recentSearchLimit = 10;
    o.recentSearchTimeLimit = 2629740000; //1 month in milliseconds
    o.noFavouritesMsg = "No favourite buses";//could just not display anything
    o.recentSearchesMsg = "No recently viewed buses";
    o.busStopsPerPage = 5;


    o.init = function () {
        /*ToDo:
         1) Build favouriates HTML as required (see partial view)
         3) Create CSS for other device sizes. Only mobile complete so far.
         4) Search functionality needs to call API.
         5) Look up dirsuptions with AJAX API call when user clicks favourites or recently viewed.
         6) Disruption info for searched buses in API.
         */

        tfl.logs.create("tfl.serviceStatus.buses: initialised");

        //favourites and recent searches
        //o.getRecentSearches();
        $('.bus-status-results').after('<div class="bus-favourites-recent" />');
        tfl.favourites.setupFavouritesAndRecent({
            selector: ".bus-favourites-recent",
            markup: "bus-stop-disruption"
        });

        o.applyExpandCollapseFunctionality(".bus-status-results");
        o.applyPagination(".bus-status-results .bus-stops");
        //if (tfl.autocomplete.recentMagicSearches.usingRecentSearches) tfl.autocomplete.recentMagicSearches.loadSearches();
        //if ($(".bus-stops-container").length > 0 && tfl.autocomplete.recentMagicSearches.usingRecentSearches) o.saveSearch();
    };

    //o.saveSearch = function () {
    //    var $el = $(".bus-stops-container");
    //    tfl.autocomplete.recentMagicSearches.saveSearch($el.attr("data-route-name"), $el.attr("data-route-id"), [$el.attr("data-route-mode")], "lines");
    //};

    o.applyPagination = function (containerClass) {
        if ($(".bus-stop").length > o.busStopsPerPage) {
            tfl.navigation.pagination.setup($(containerClass), ".bus-stop", o.busStopsPerPage, $(".bus-stop").length);
            $(".pagination a").click(function () {
                collapseAndLighten(containerClass);
            });
        }
    };

    var collapseAndLighten = function (busStopsClass) {
        $(busStopsClass + " .content").removeClass("expanded");
        $(busStopsClass + " .always-visible").children("a").removeClass("expanded");
        $(busStopsClass + " .always-visible").parents(".bus-stop").removeClass("darken");
    };

    o.applyExpandCollapseFunctionality = function (busStopsClass) {
        //function for expanding box and resizing favourites button
        $(busStopsClass + " .bus-stop .always-visible").on("expandable-box.expanded", function () {
            var thisBusStop = $(this).parents(".bus-stop");
            $(this).parents(".bus-stops").find(".bus-stop.expandable-box").not(thisBusStop).addClass("darken").children('.expanded').removeClass('expanded');
            thisBusStop.removeClass("darken");
        });
        $(busStopsClass + " .bus-stop .always-visible").on("expandable-box.collapsed", function () {
            $(".bus-stop.expandable-box.darken").removeClass("darken");
        });

        $(busStopsClass + " .bus-stop .close-status").click(function () {
            $(this).parents().siblings(".always-visible").click();
        });
    };

    //o.buildRecentSearchesList = function () {
    //    var busStopType = ".bus-stop-recent-searches";
    //    //clear existing list
    //    $(busStopType + " .bus-stop").remove();

    //    if (o.recentBuses.length > 0) {
    //        $("#no-recent-buses-msg").hide();
    //        var accordionHtml = '';
    //        for (var i = 0; i < o.recentBuses.length; i++) {
    //            //ToDo run API call to see whether this bus stop has a disruption or not (can we get this from LocalStorage?)
    //            var disrupted = "disrupted";
    //            var busStop = o.recentBuses[i];

    //            accordionHtml = '<div class="bus-stop expandable-box"><div class="content"><div class="always-visible">';
    //            accordionHtml += '<a href="javascript:void(0)" class="fav-button">';
    //            accordionHtml += '<span class="fav-icon unticked" data-number="' + busStop.number + '" data-name="' + busStop.name + '" data-destination="' + busStop.destination + '">&nbsp;</span></a>';
    //            accordionHtml += '<div class="title-row"><span class="visually-hidden">Stop name:</span><span class="stop-name">' + busStop.name + '</span></div>';
    //            accordionHtml += '<a href="javascript:void(0)" class="plain-button ' + disrupted + '"><div class="stop-symbol">';
    //            accordionHtml += '<span class="visually-hidden">Stop number:</span><span class="stop-letter ' + disrupted + '">' + busStop.number + '</span></div>';
    //            accordionHtml += '<div class="stop-destination"><span class="destination-name">Towards ' + busStop.destination + '</span></div></a>';

    //            accordionHtml += '</div><div class="start-hidden">';
    //            accordionHtml += '<div class="routes-list"><p>Routes: <strong>343, 168, N2</strong></p></div><div class="disruptions"><div class="disruption-info">';
    //            accordionHtml += '<h4>343</h4><p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut</p><div class="vertical-button-container">';
    //            accordionHtml += '<a href="javascript:void(0)" class="plain-button">View more details</a></div></div></div><div class="buttons clearfix"><div class="vertical-button-container">';
    //            accordionHtml += '<a href="javascript:void(0)" class="plain-button">View stop on map</a><a href="javascript:void(0)" class="plain-button">Replan your journey</a></div>';
    //            accordionHtml += '<a href="javascript:void(0)" class="close-status">Close status</a></div>';
    //            accordionHtml += '</div></div></div>';

    //            $(busStopType + " .bus-stops-container").append(accordionHtml);
    //        }

    //        o.applyExpandCollapseFunctionality(busStopType);
    //        o.applyPagination(busStopType);
    //        o.hideFavManagingButtons();
    //        $(busStopType + " .fav-button").click(function () {
    //            o.addRemoveFavRecentSearches($(this).children(".fav-icon"), false, true);
    //        });

    //    } else {
    //        $("#no-recent-buses-msg").show();
    //    }

    //};

})(window.tfl.statusUpdates.buses = window.tfl.statusUpdates.buses || {});
