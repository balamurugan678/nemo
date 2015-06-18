(function (o) {
    "use strict";
    tfl.logs.create("tfl.nearby: loaded");

    var $el;
    var nearUrl;
    var mapObject;
    var mapController;

    var placeType = {
        BikePoint: "BikePoint",
        StopPoint: "StopPoint"
    };

    /*
     //NB - Bus stations also exists, as a data-filter-group but items with that group get added to BusStops
     var itemsToFilter = {
     "All": { text: "Show more of all", items: [], icons: '' },
     "Stations": { text: "Stations", items: [], icons: '<span class="dropdown-icon tube-station-icon"></span><span class="dropdown-icon national-rail-station-icon"></span><span class="dropdown-icon tram-station-icon"></span><span class="dropdown-icon coach-station-icon"></span><span class="dropdown-icon river-bus-station-icon"></span>' },
     "BusStops": { text: "Bus stops", items: [], icons: '<span class="dropdown-icon bus-station-icon"></span><span class="dropdown-icon bus-stop-icon"></span>' },
     "CycleHire": { text: "Cycle hire docking stations", items: [], icons: '<span class="dropdown-icon bch-docking-station-icon"></span>' },
     "TaxiRanks": { text: "Taxi and mini cab ranks", items: [], icons: '<span class="dropdown-icon taxi-rank-icon"></span><span class="dropdown-icon minicab-office-icon"></span>' }
     };*/

    // variable declaration
    var mapController,
        mapZoomLevel = 15,
        ajaxCache = { },
        $window = $(window),
        nearbyRadius = 1000,
        bikePointAjaxData;

    function geoCallback() {
        removeHiddenInputs();
        $("#search-filter-form").attr('action', nearUrl);
        $("#search-filter-form").attr('method', 'GET');
        $('.submit-button input[type="submit"]').attr('disabled', 'disabled');
        //setting #input.val fixes a weird race condition where typeahead doesn't set this text fast enough.
        $("#Input").val(tfl.dictionary.CurrentLocationText);
        $("#search-filter-form").submit();
    }

    function removeHiddenInputs() {
        $("input[type=hidden]").each(function () {
            if ($(this).attr("name").toLowerCase() !== "inputgeolocation") {
                $(this).remove();
            }
        });
    }

    function selectNearbyResult(obj) {

        var $obj = $(obj),
            id = $obj.data("id"),
            type = $obj.data("place-type"),
            lat = $obj.data("lat"),
            lon = $obj.data("lon"),
            mode = $obj.data("mode");


        mapController.choosePlace(id, type, { lat: lat, lng: lon });
        if (type === "BikePoint") {
            mapObject.placeChosen({ id: id, placeType: type});
        } else {
            //mapObject.stationStopsChosen([{naptanId: id, modeNames: [mode]}]);
            //       $obj.data('jumpto', $('.map-panel-info').find('.responsive-button-row').find('a:first'));
            //   attr (data-jumpto, ".map-paenel"
            mapObject.loadFromList(id, mode);
        }


        //mapObject.trigger('placeChosen', );
    }

    o.updateBikePointNearbyResult = function (bikePointId, cssDockBarRatio, numBikes, numBikesMessage, numSpaces, numSpacesMessage) {
        var $oldDetails = $(".nearby-list [data-id='" + bikePointId + "']");

        if ($oldDetails.length > 0) {
            var $dockBar = $oldDetails.find(".bch-dock-bar").first();
            var $dockIcon = $oldDetails.find(".bch-docking-station-icon").first();
            var oldClass = $dockBar.attr("class");
            oldClass = oldClass.substring(oldClass.indexOf("full-"));
            var spaceIdx = oldClass.indexOf(" ");
            if (spaceIdx >= 0) {
                oldClass = oldClass.substring(0, spaceIdx);
            }
            $dockBar.removeClass(oldClass).addClass(cssDockBarRatio);
            $dockIcon.removeClass(oldClass).addClass(cssDockBarRatio);
            $oldDetails.find(".bch-bikes-available").text(numBikesMessage);
            $oldDetails.find(".bch-bike-spaces").text(numSpacesMessage);
        }
    };

    //o.updateBikePointFromMapPanel = function () {
    //    var $newDetails = $(".map-panel .availability");
    //    var id = $newDetails.data("id");
    //    var full = $newDetails.data("full");

    //    this.updateBikePointNearbyResult(id, full, $newDetails.find(".bikes").text(), $newDetails.find(".spaces").text());
    //};

    o.updateBikePoints = function (data) {
        if (data === null) {
            return;
        }

        for (var i = 0; i < data.places.length; i++) {
            var bikePoint = data.places[i];

            tfl.mapInteractions.updateBikePoint(bikePoint, o.updateBikePointNearbyResult);
        }
    };

    o.initBikePointAutoRefresh = function (lat, lon) {
        bikePointAjaxData = {
            lat: lat,
            lon: lon,
            radius: nearbyRadius
        };
        tfl.ajax({
            url: tfl.api.BikePoint,
            data: bikePointAjaxData,
            success: o.updateBikePoints,
            autoRefreshInterval: tfl.autoRefresh.BikePoint,
            autoRefreshId: "BikePoints"
        });
    };

    o.initMap = function () {
        tfl.mapInteractions.init();
        $(window).on('map-object-created-' + $el.attr('id').replace('-', ''), function () {
            mapObject = tfl.maps[$el.attr('id').replace('-', '')].googleMap;
            mapController = mapObject.controller;
            if (tfl.tools.getQueryStringParameter("Input") === "Current+location+") {
                mapController.showUserLocation({ lat: mapObject.params.mapCentre.lat, lng: mapObject.params.mapCentre.lng });
            }
            $(".nearby-list-result").click(function (e) {
                if ($(document.body).hasClass("breakpoint-Large")) {
                    e.preventDefault();
                    e.stopPropagation();
                    selectNearbyResult(this);
                }
            });
        });
    };

    o.init = function (selector) {
        $el = $(selector);
        tfl.logs.create("tfl.nearby.init: started");

        nearUrl = window.location.href.replace(window.location.search, "").replace(window.location.origin, "");

        //if we haven't been given a location, try and find our location
        if (tfl.tools.getQueryStringParameter("Input") === "Current+location+"
            && !tfl.tools.getQueryStringParameter("InputGeolocation")
            && tfl.geolocation.isGeolocationSupported()) {
            tfl.geolocation.geolocateMe("#Input", geoCallback);
            return false;
        } else if (tfl.tools.getQueryStringParameter("InputGeolocation") && tfl.geolocation.isGeolocationSupported()) {
            var $searchForm = $("#search-filter-form");
            var submitFunc = function () {
                $searchForm.attr("action", nearUrl);
                $searchForm.find("input[type='hidden']").not("[id='InputGeolocation']").remove();
            };
            $searchForm.bind("submit", submitFunc);

            var unbindSearchFunc = function () {
                $searchForm.unbind("submit", submitFunc);
            };
            var $input = $searchForm.find("input[type='text']");
            $input.one("keydown", unbindSearchFunc).on("geolocation-cleared", unbindSearchFunc);
        }
        if ($el.length) {
            o.initMap();
        }
    };
}(window.tfl.nearby = window.tfl.nearby || {}));
