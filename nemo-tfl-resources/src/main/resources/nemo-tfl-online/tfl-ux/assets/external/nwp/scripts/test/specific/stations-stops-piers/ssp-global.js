(function (o) {
    "use strict";
    tfl.logs.create("tfl.stops: loaded");

    var mapObject;
    var $el = $('.map-sliver');
    var mapController = null;
    var currentPageNaptanId = null;

    var placeType = {
        BikePoint: "BikePoint",
        StopPoint: "StopPoint"
    };

    o.init = function () {
        tfl.logs.create("tfl.stops.init: started");
        if (tfl.autocomplete.recentMagicSearches.usingRecentSearches &&
            document.referrer.toLowerCase().indexOf("/disambiguation") > 0) {
            o.saveSearch();
        }

        if (window.location.hash !== "") {
            var hash = window.location.hash;
            tfl.logs.create("tfl.serviceStatus: page hash is " + hash);
            var $hash = $(hash);
            if ($hash.length && $hash.hasClass('info-widget')) {
                $hash.find('.always-visible').click();
            }
        }

        if ($el.attr('id') !== undefined) {
            tfl.mapInteractions.init();
            $(window).on('map-object-created-' + $el.attr('id').replace('-', ''), function () {
                currentPageNaptanId = $el.attr("data-naptanid");
                var hubNaptanCode = $el.attr("data-hubnaptancode");

                mapObject = tfl.maps[$el.attr('id').replace('-', '')].googleMap;
                mapController = mapObject.controller;

                //mapObject.on('placeChosen', function (data) {
                //    var $nearbyList = $(".nearby-list");
                //    if ($nearbyList.length > 0) {
                //        tfl.logs.create("tfl.stops: Bike point info returned. Refreshing nearby list.");
                //        tfl.nearby.updateBikePointFromMapPanel();
                //    }
                //});

                var direction = tfl.tools.getQueryStringParameter('direction');
                direction = direction === null ? 'outbound' : direction;
                var stopId = $el.data('stopid') || $el.data('naptanid');
                var lineId = $el.data('lineid') || tfl.tools.getQueryStringParameter("lineId");
                var $ssp = $('#station-stops-and-piers');
                if (!$ssp.length || ~$ssp.data('modes').indexOf('bus')) {
                    if (stopId !== undefined && lineId !== undefined && lineId !== null) {
                        tfl.logs.create("tfl.stops: choose bus routes init " + lineId + " " + stopId); // routes page 
                        mapController.chooseBusRouteForStop(stopId, lineId.toString());
                    } else if (lineId !== null) {
                        tfl.logs.create("tfl.stops: choose bus route - " + lineId + " " + currentPageNaptanId);
                        mapController.chooseBusRouteForLine(lineId.toString(), direction, currentPageNaptanId);
                    }
                }

                if ($el.attr('id').replace('-', '') === "map") {
                    mapObject.on('mapActivated', function () {
                        mapObject.naptanId = currentPageNaptanId;
                        mapController.choosePlace(currentPageNaptanId, "StopPoint", { lat: $el.attr("data-lat"), lng: $el.attr("data-lon") }, hubNaptanCode, lineId !== null);
                    });

                    if ($(document.body).hasClass('breakpoint-Large')) {
                        mapObject.naptanId = currentPageNaptanId;
                        mapController.choosePlace(currentPageNaptanId, "StopPoint", { lat: $el.attr("data-lat"), lng: $el.attr("data-lon") }, hubNaptanCode, lineId !== null);
                    }
                }
            });
        }
        if ($el.attr("data-lat") == 0 && $el.attr("data-lon") == 0) {
            // hide map if no lat / long data
            $(".ssp-map-wrapper").hide();
        }

    };


    o.saveSearch = function () {
        var $el = $("#station-stops-and-piers");
        if ($el && $el.attr("data-name")
            && $el.attr("data-id") && $el.attr("data-modes") && $el.attr("data-lat") && $el.attr("data-lon")) {
            tfl.autocomplete.recentMagicSearches.saveSearch($el.attr("data-name"), $el.attr("data-id"), $el.attr("data-modes"), "stops", $el.attr("data-lat"), $el.attr("data-lon"));
        }
    };

}(window.tfl.stops = window.tfl.stops || {}));
