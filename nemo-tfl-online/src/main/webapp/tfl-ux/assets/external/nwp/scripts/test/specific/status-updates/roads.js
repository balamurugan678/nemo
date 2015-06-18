/*global $, jQuery, tfl, steal*/
window.tfl.serviceStatus = window.tfl.serviceStatus || {};
(function (o) {
    "use strict";
    tfl.logs.create("tfl.serviceStatus.roads: loaded");
    window.corridors = {};
    var disruptionsPerPage = 6;
    var jamCamInterval = 30000;
    var jamCamTimeout = null;
    var jamCamUrl = "";

    var disruptions = [];

    var severitiesVisible = {
        Severe: true,
        Serious: true,
        Moderate: true,
        Minimal: true
    };

    var worksVisible = false;
    var worksCategory = "Works";

    var selectedCorridor = null;

    var queryStringStartDate = tfl.tools.getQueryStringParameter("startDate");
    var queryStringEndDate = tfl.tools.getQueryStringParameter("endDate");
    var queryStringCorridorIds = tfl.tools.getQueryStringParameter("corridorIds");
    var queryStringDisruptionIds = tfl.tools.getQueryStringParameter("disruptionIds");
    var selectedCorridor = null;
    var mapObject = null;
    var roadMapController = null;
    var corridorSelectedClass = "corridor-selected";
    var nearbySelectedClass = "nearby-selected";
    var $showAllDisruptions = $(".show-all-reported");
    var $roadDisruptions = $(".road-disruption");
    var $selectCorridor = $(".select-corridor");
    var $mapPanel = $('.roads-map-panel');
    var $mapDisruptionsPanel = $(".roads-map-disruptions-panel");
    var $closeMapPanelButton = $(".corridor-details .close");
    var $roadMap = $('#roadmap');
    var $visibleRoadDisruptions = $(".road-disruptions-wrapper .road-disruptions");
    var $mapDisruptionsPanelDisruptions = null;
    var $mapDisruptionsPanelNoDisruptions = $mapDisruptionsPanel.find(".no-disruptions").addClass("hidden");
    var $nearbyDisruptionsMapPanel = $(".nearby-disruptions-map-panel");
    var $nearbyDisruptions;
    var $hiddenList;
    var $nearbyButton;
    var isLargeBreakpoint = $(document.body).hasClass(".breakpoint-Large");
    var $infoPanel = $('#map-panel-info');
    var imageUrl = "http://www.tfl.gov.uk/";
    var nearbyResponse = "";

    o.init = function () {
        o.initMap();
        o.addDisruptionMapInteractions();
        o.initWorksContainer();
        o.generateSeveritiesAndCorridors();
        o.initInteractions();
        o.initNearbyIncidents();
        o.addPagination();
    }

    o.initMap = function () {
        tfl.logs.create("tfl.serviceStatus.roads: init map");
        tfl.mapInteractions.init();
        $(window).one('map-object-created-roadmap', function () {
            mapObject = tfl.maps.roadmap;
            roadMapController = mapObject.googleMap.controller;

            mapObject.googleMap.on("disruptionChosen", function (data) {
                o.mapDisruptionChosen(data);
            });
            mapObject.googleMap.on("mapDeactivated", function (data) {
                o.mapDeactivatedCallback(data);
            });
            mapObject.googleMap.on("layerStateChanged", function (data) {
                o.mapLayerStateChanged(data);
            });
            mapObject.googleMap.on("jamCamChosen", function (data) {
                o.jamCamChosen(data);
            });
            mapObject.googleMap.on("variableMessageSignChosen", function (data) {
                o.variableMessageSignChosen(data);
            });

            if (queryStringCorridorIds !== "" && queryStringCorridorIds !== "all" && queryStringCorridorIds !== undefined && queryStringCorridorIds !== null) {
                var $row;
                if (queryStringCorridorIds === "Inner+Ring,Southern+River+Route,Bishopsgate+Cross+Route,City+Route,Farringdon+Cross+Route,Western+Cross+Route") {
                    $row = $('td.roads-central-london-red-routes').parent();
                } else {
                    $row = $('tr[data-corridor-name="' + queryStringCorridorIds + '"]');
                }
                if ($row.length) {
                    selectedCorridor = queryStringCorridorIds;
                    o.selectCorridor();
                }
            }
        });
    };

    function extractAdditionalProperty(data, prop) {
        if (!data.hasOwnProperty("additionalProperties")) {
            return false;
        }
        var val = null;

        // Find the url in the data        
        for (var i = 0; i < data.additionalProperties.length; i += 1) {
            if (data.additionalProperties[i].hasOwnProperty('key')
                && data.additionalProperties[i].key === prop
                && data.additionalProperties[i].hasOwnProperty('value')) {
                val = data.additionalProperties[i].value;
                break;
            }
        }
        return val;
    }


    o.variableMessageSignChosen = function (data) {
        var message = "";
        message = extractAdditionalProperty(data, "signtext");

        if (message === "") {
            message = "Board Inactive";
        }
        var messages = message.split('|');
        var $sign = $('<div class="variable-message-sign"></div>');
        for (var i = 0; i < messages.length; i += 1) {
            $sign.append('<p class="message">' + $.trim(messages[i]) + '</p>');
        }
        $sign.append('<div class="corners">&nbsp;</div>');

        var $wrapper = $('<div></div>');
        $wrapper.append('<span class="map-panel-heading variable-message">Variable Message Sign</span>');
        if (data.hasOwnProperty('commonName')) {
            $wrapper.append('<h4 class="map-panel-subheading">' + data.commonName + '</h4>');
        }
        $wrapper.append($sign);
        o.populateMapPanel($wrapper);
    }


    function loadImage(url, callback) {
        var img = new Image();
        img.onload = function () {
            if (callback) {
                callback(url);
            }
        };
        img.onerror = function () {
            url = null;
            if (callback) {
                callback(url);
            }

        };
        img.src = url;
    }

    function updateJamCam(url) {
        var $panel = $('.map-panel');
        var $img = $panel.find('.jam-cam-wrapper img');
        if (jamCamUrl === null || url === null) {
            $img.addClass('hidden');
            $img.after('<p class="field-validation-error">Jamcam currently out of service</p>');

        } else if (url === jamCamUrl && $img.length) {
            $img.attr('src', url);
            setTimeout(function () {
                loadImage(url, updateJamCam)
            }, jamCamInterval);
            if ($img.hasClass('hidden')) {
                $img.removeClass('hidden');
            }
        }
        if ($panel.hasClass('loading')) {
            $panel.removeClass('loading')
        }

    }

    o.jamCamChosen = function (data) {
        var url = null;

        url = extractAdditionalProperty(data, "imageUrl");

        // no url found
        if (url === null || url === false) {
            url = null;
        } else {
            url = imageUrl + url;                                                                           /// new site??
        }
        jamCamUrl = url;

        // generate Jam cam markup
        var $wrapper = $('<div></div>');
        $wrapper.append('<span class="map-panel-heading jam-cam">Jam Cam</span>');
        var commonName = false;
        var alt = "Jam cam feed";
        if (data.hasOwnProperty("commonName") && data.commonName !== "") {
            commonName = data.commonName;
            $wrapper.append('<h4 class="map-panel-subheading">' + commonName + '</h4>');
            alt += " at " + commonName;
        }
        $wrapper.append('<div class="jam-cam-wrapper"><img alt="' + alt + '"/></div>');
        o.populateMapPanel($wrapper);
        $mapPanel.find('.map-panel').addClass('loading');

        // load image
        loadImage(url, updateJamCam);

    }


    o.mapDisruptionChosen = function (data) {
        roadMapController.chooseDisruption(data.id, false);
        o.selectIndividualDisruption(data.id);
    };

    o.selectIndividualDisruption = function (disruptionId) {
        var $singleDisruption = $mapDisruptionsPanelDisruptions.filter("[data-disruption-id='" + disruptionId + "']").clone(true);
        if ($singleDisruption.hasClass('hidden')) {
            $singleDisruption.removeClass('hidden');
        }
        var severity = $.trim($singleDisruption.find('.severity').text());
        $singleDisruption.prepend('<span class="map-panel-heading ' + severity + '">' + severity + ' Incident</span>');
        o.populateMapPanel($singleDisruption);
    }

    o.populateMapPanel = function ($obj) {
        mapObject.mapPanel.show($obj);
    }

    o.hidePanelDisruptions = function () {
        for (var i = 0; i < disruptions.length; i++) {
            var d = disruptions[i];
            d.addClass("hidden");
        }
    };

    o.mapDeactivatedCallback = function () {
        // mapObject.deactivateMap();
        o.deselectCorridor();
    };

    o.deselectCorridor = function () {
        selectedCorridor = null;
        $(document.body).removeClass(corridorSelectedClass);
        $(document.body).removeClass(nearbySelectedClass);
        $selectCorridor.filter(".selected").removeClass("selected").closest(".fade-to-black").removeClass("fade-to-black");
        roadMapController.chooseCorridor('all');
        roadMapController.resetToInitialPositionAndZoom();
        $mapDisruptionsPanelDisruptions.addClass("hidden");
        $mapDisruptionsPanelNoDisruptions.addClass("hidden");
        tfl.tools.removeQueryStringParameter('corridorIds');
    }

    o.mapLayerStateChanged = function (data) {
        //if we're turning on or off works, everything can change, so try them all
        if (data.worksVisible !== worksVisible) {
            worksVisible = data.worksVisible;
        }

        if (data.severeVisible !== severitiesVisible["Severe"]) {
            severitiesVisible["Severe"] = data.severeVisible;
        }

        if (data.seriousVisible !== severitiesVisible["Serious"]) {
            severitiesVisible["Serious"] = data.seriousVisible;
        }

        if (data.moderateVisible !== severitiesVisible["Moderate"]) {
            severitiesVisible["Moderate"] = data.moderateVisible;
        }

        if (data.minimalVisible !== severitiesVisible["Minimal"]) {
            severitiesVisible["Minimal"] = data.minimalVisible;
        }
        if ($(document.body).hasClass(nearbySelectedClass)) {
            o.nearbyDisruptionsLoaded(nearbyResponse);
        } else {
            o.updateVisibleDisruptions();
            if (selectedCorridor) {
                o.selectCorridor();
            }
        }
    };

    o.updateVisibleDisruptions = function () {
        var anyShown = false;
        for (var i = 0; i < disruptions.length; i++) {
            var d = disruptions[i];
            if (severitiesVisible[d.severity] && (!d.isWorks || (worksVisible && d.isWorks))) {
                //the disruption needs to be made visible
                $visibleRoadDisruptions.append(d.original);
                d.isHidden = false;
                d.original.removeClass("hidden");
                anyShown = true;
            } else if (!d.isHidden) {
                //the disruption needs to be hidden
                $hiddenList.append(d.original);
                d.isHidden = true;
                d.original.addClass("hidden");
            }
        }
        if (anyShown) {
            $mapDisruptionsPanelNoDisruptions.addClass("hidden");
        }
        o.addPagination();
    };

    o.addPagination = function () {
        $(".pagination-controls").remove();
        var numDisruptions = $(".road-disruptions-wrapper .road-disruption").length;
        if (numDisruptions > disruptionsPerPage) {
            tfl.navigation.pagination.setup($(".road-disruptions-wrapper"), ".road-disruptions", disruptionsPerPage, numDisruptions);
        }
    };

    o.selectCorridor = function () {
        $(document.body).removeClass(nearbySelectedClass);
        $(document.body).addClass(corridorSelectedClass);
        var corridorsToSelect = selectedCorridor.split(",");
        var disruptionsShown = 0;
        var $row;
        if (corridorsToSelect.length > 1) {
            roadMapController.chooseCorridor($('td.roads-central-london-red-routes').parent().data("corridor-group"));
            tfl.tools.setQueryStringParameter('corridorIds', $('td.roads-central-london-red-routes').parent().data("corridor-group"));
            $row = $('td.roads-central-london-red-routes').parent();
        } else {
            roadMapController.chooseCorridor(corridorsToSelect[0]);
            tfl.tools.setQueryStringParameter('corridorIds', corridorsToSelect[0]);
            $row = $('tr[data-corridor-name="' + corridorsToSelect + '"]');
        }

        for (var i = 0; i < disruptions.length; i++) {
            var d = disruptions[i];
            if ((worksVisible || !d.isWorks) && severitiesVisible[d.severity] && o.matchCorridors(corridorsToSelect, d.corridors)) {
                d.removeClass("hidden");
                disruptionsShown++;
            } else {
                d.addClass("hidden");
            }
        }

        if (disruptionsShown === 0) {
            $mapDisruptionsPanelNoDisruptions.removeClass("hidden");
        }
        mapObject.scrollToMap();
        mapObject.activateMap();

        $selectCorridor.filter(".selected").removeClass("selected");
        $row.addClass('selected');
        $(".rainbow-board").addClass("fade-to-black");


    };

    o.matchCorridors = function (corA, corB) {
        for (var i = 0; i < corA.length; i++) {
            for (var j = 0; j < corB.length; j++) {
                if (corA[i] === corB[j]) {
                    return true;
                }
            }
        }
        return false;
    }

    o.addDisruptionMapInteractions = function (t) {
        var $target = $roadDisruptions;
        if (t) {
            $target = t;
        }
        $target.each(function () {
            var $viewOnMap = $("<a href='javascript:void(0)' class='plain-button'>View incident on map</a>");
            var $viewJamCams = $("<a href='javascript:void(0)' class='plain-button'>View nearby Jam Cams</a>");
            var $disruptionInfo = $(this).find(".disruption-info");
            $disruptionInfo.append($viewOnMap);
            $disruptionInfo.append($viewJamCams);
            $([$viewOnMap, $viewJamCams]).wrap("<div class='vertical-button-container' />");
            $viewOnMap.click(o.viewIncidentOnMap);
            $viewJamCams.click(o.viewJamCams);
        });
    };

    o.viewIncidentOnMap = function () {
        o.showDisruptionOnMap($(this).closest(".road-disruption").data("disruption-id"), false);      // ToDo
    };

    o.showDisruptionOnMap = function (disruptionId, showJamCams) {
        roadMapController.chooseDisruption(disruptionId, showJamCams);                                 // ToDo
        o.selectIndividualDisruption(disruptionId);
        mapObject.scrollToMap();
    };

    o.viewJamCams = function () {
        o.showDisruptionOnMap($(this).closest(".road-disruption").data("disruption-id"), true);           // ToDo
    };

    o.initWorksContainer = function () {
        $hiddenList = $("<div class='hidden list-of-hidden-disruptions'></div>");
        $mapDisruptionsPanel.after($hiddenList);
    };

    o.generateSeveritiesAndCorridors = function () {
        $roadDisruptions.each(function () {
            var $this = $(this).clone(true).addClass("hidden");
            $this.isHidden = false;
            $this.original = $(this);

            var works = ($this.data("category") === worksCategory);
            $this.isWorks = works;

            $this.corridors = $this.data("corridors").split(",");

            var s = $this.data("severity");
            $this.severity = s;

            $mapDisruptionsPanel.append($this);

            if (!severitiesVisible[s]) {
                $hiddenList.append($this.original);
                $this.isHidden = true;
            }

            if (works && !worksVisible) {
                $hiddenList.append($this.original);
                $this.isHidden = true;
            }

            disruptions.push($this);
        });
        $mapDisruptionsPanelDisruptions = $mapDisruptionsPanel.find(".road-disruption");
    };

    o.initInteractions = function () {
        $selectCorridor.click(o.handleCorridorClick);
        $showAllDisruptions.click(o.resetView);
    };

    o.handleCorridorClick = function () {
        var $this = $(this);
        $mapDisruptionsPanelDisruptions.addClass("hidden");
        $mapDisruptionsPanelNoDisruptions.addClass("hidden");
        mapObject.mapPanel.hide();
        var corridorToSelect = $this.data("corridor-name");
        if (corridorToSelect && corridorToSelect !== selectedCorridor) {
            selectedCorridor = corridorToSelect;
            o.selectCorridor();
        } else {
            o.deselectCorridor();
        }

    };

    o.resetView = function (e) {
        if (e) {
            e.preventDefault();
        }
        o.deselectCorridor();
        mapObject.mapPanel.hide();
        $(document.body).removeClass(nearbySelectedClass);
    };

    o.initNearbyIncidents = function () {
        if (tfl.geolocation.isGeolocationSupported()) {
            $nearbyButton = $("<a href='javascript:void(0);' class='plain-button nearby-roads-button'><span class='nearby-icon'></span>View incidents near me</a>");
            $(".service-status-rainbow-board > .vertical-button-container:first").after($nearbyButton);
            $nearbyButton.wrap("<div class='vertical-button-container view-nearby-incidents' />");
            $nearbyButton.click(o.geolocateMe);
        }
    };

    o.geolocateMe = function () {
        function geolocationError(msg) {
            $(".geolocation-error").text(msg).removeClass("hidden");
        }

        var success = function (position) {
            if (position.coords && position.coords.accuracy && position.coords.accuracy > tfl.geolocation.minAccuracy) {
                tfl.logs.create("tfl.serviceStatus.roads.geolocate me: ERROR: inaccuracy too high: " + position.coords.accuracy + "m");
                geolocationError("We cannot find your current location. Please try again");
                return;
            }
            $(".geolocation-error").addClass("hidden");
            o.resetView();
            o.loadDisruptionsNearby(position.coords.latitude, position.coords.longitude);
            roadMapController.positionMap({ lat: position.coords.latitude, lng: position.coords.longitude }, 15);
            roadMapController.showUserLocation({ lat: position.coords.latitude, lng: position.coords.longitude });
        };

        var failure = function (err) {
            if (err.code) {
                if (err.code === 1) {
                    tfl.logs.create("tfl.serviceStatus.roads.geolocateMe: ERROR: permission denied");
                    geolocationError("Permission from browser needed before finding your location");
                } else if (err.code === 2) {
                    tfl.logs.create("tfl.serviceStatus.roads.geolocateMe: ERROR: position unavailable");
                    geolocationError("We cannot find your current location. Please try again");
                } else if (err.code === 3) {
                    tfl.logs.create("tfl.serviceStatus.roads.geolocateMe: ERROR: timeout");
                    geolocationError("We cannot find your current location. Please try again");
                }
            } else {
                geolocationError("We cannot find your current location. Please try again");
            }
        };

        try {
            navigator.geolocation.getCurrentPosition(success, failure);
        } catch (e) {
            geolocationError("Your device does not support the location feature");
        }
    };

    o.loadDisruptionsNearby = function (latitude, longitude) {
        tfl.logs.create("tfl.serviceStatus.roads: load disruptions nearby");
        //o.disruptionsLoading();
        var data = {
            lat: latitude,
            lon: longitude,
            startDate: queryStringStartDate,
            endDate: queryStringEndDate,
            showWorks: true
        };

        tfl.ajax({
            url: "/StatusUpdates/RoadsDisruptionsNearby",
            data: data,
            success: o.nearbyDisruptionsLoaded,
            dataType: 'html'
        });
    };

    o.nearbyDisruptionsLoaded = function (response) {
        nearbyResponse = response;
        $(document.body).addClass(nearbySelectedClass).removeClass(corridorSelectedClass);
        $nearbyDisruptionsMapPanel.html(response);
        $nearbyDisruptions = $nearbyDisruptionsMapPanel.removeClass('hidden').find('.road-disruption');

        if (!worksVisible) {
            $nearbyDisruptions.filter('[data-category=' + worksCategory + ']').addClass('hidden');
        }

        $nearbyDisruptions.each(function () {
            if (!severitiesVisible[$(this).data('severity')]) {
                $(this).addClass('hidden');
            }
        });

        o.addDisruptionMapInteractions($nearbyDisruptionsMapPanel.find(".road-disruption"));
    };
}(window.tfl.serviceStatus.roads = window.tfl.serviceStatus.roads || {}));
