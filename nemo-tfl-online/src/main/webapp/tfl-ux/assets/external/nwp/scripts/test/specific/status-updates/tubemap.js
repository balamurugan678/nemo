(function (o) {
    "use strict";
    tfl.logs.create("tfl.tubeMap: loaded");

    var $statusMapContainer = $("#tubemap");
    var $map;
    var $panzoom;
    var zoomableContent;
    var $openMapButton;
    var $showPanelButton;
    var $mapPanel;
    var $rainbowList = $('.rainbow-list.interactive');

    var disruptionData;
    var stationDisruptionData;
    var boundingBoxes = {};
    var messageClosed = false;
    var messageOpened = false;

    //var movedSinceZoom = true;
    o.zoomToHideStationNames = 0.8;

    var $extras = $("#station-names, #river");
    $("#cab-emirates-air-line").attr("class", "hidden");

    o.turnOffDisruptions = function () {
        $("#status-map .disrupted").attr("class", "");
    };

    o.linesLoaded = function () {
        o.turnOffDisruptions();
        $statusMapContainer.removeClass("loading");
    };

    o.ajaxLinesSuccessCallback = function (response) {
        tfl.logs.create("tfl.tubemap: API point-to-point response returned.");
        disruptionData = response;
        o.initDisruptions();
        o.linesLoaded();
    };

    o.ajaxLinesErrorCallback = function () {
        o.loadingFailed();
    };

    o.ajaxStopPointsSuccessCallback = function (response) {
        tfl.logs.create("tfl.tubemap: API stations response returned.");
        stationDisruptionData = response;
        o.initStationDisruptions();
    };

    o.ajaxDisruptions = function () {
        var startDate = tfl.tools.getQueryStringParameter("startDate");
        var endDate = tfl.tools.getQueryStringParameter("endDate");
        var lineAjaxUrl;
        var modes = "tube,dlr,overground";
        var data = {
            includeAffectedStopsAndRoutes: true
        };

        if (startDate && endDate) {
            data.startDate = startDate;
            data.endDate = endDate;
            lineAjaxUrl = tfl.api.LinePlannedWorks.format(modes, startDate, endDate);
        } else {
            var date = new Date();
            date.setHours(0);
            date.setMinutes(0);
            date.setSeconds(0);
            data.startDate = tfl.tools.makeMVCDate(date);
            date.setHours(23);
            date.setMinutes(59);
            date.setSeconds(59);
            data.endDate = tfl.tools.makeMVCDate(date);
            lineAjaxUrl = tfl.api.LineStatus.format(modes);
        }
        o.turnOffDisruptions();

        tfl.ajax({
            url: lineAjaxUrl,
            success: o.ajaxLinesSuccessCallback,
            error: o.ajaxLinesErrorCallback,
            autoRefreshInterval: tfl.autoRefresh.ServiceBoard,
            autoRefreshId: "lines"
        });

        tfl.ajax({
            url: tfl.api.StopPointsDisruptionsByModeAndTimePeriod.format(modes, data.startDate, data.endDate),
            success: o.ajaxStopPointsSuccessCallback,
            autroRefreshInterval: tfl.autoRefresh.ServiceBoard,
            autoRefreshId: "stations"
        });
    };

    o.loadingFailed = function () {
        o.linesLoaded();
        o.setupTubeMapMessage("Our data service is currently unavailable. We are trying to fix this. Please come back later.", false);
    };

    function disruptLineSection(lineId, currentStationId, prevStationId) {
        var $lineSectionElement = $("#" + lineId + "_" + currentStationId + "_" + prevStationId);
        if ($lineSectionElement.length === 0) {
            $lineSectionElement = $("#" + lineId + "_" + prevStationId + "_" + currentStationId);
        }
        if ($lineSectionElement.length !== 0) {
            if (lineId === "raillo-overground") {
            }
            $lineSectionElement.attr("class", "disrupted");
            return $lineSectionElement.get(0);
        } else {
            return null;
        }
    }

    function disruptStationElement($stationElement) {
        var parents = $stationElement.parentsUntil("#interchange-circles");

        if (parents.length === 0 || parents[parents.length - 1] === document.documentElement) {
            $stationElement.attr("class", "disrupted");
        } else {
            $(parents[parents.length - 1]).attr("class", "disrupted");
        }
    }

    function disruptStation(lineId, currentStationId, otherLineIds) {
        var $stationElement = $("#" + lineId + "_" + currentStationId);
        if ($stationElement.length > 0) {
            disruptStationElement($stationElement);
        } else {
            $stationElement = $("#interchange-circles").find('[id*=' + currentStationId + ']');
            if ($stationElement.length > 0) {
                disruptStationElement($stationElement);
            }
        }
    }

    function disruptWholeLine($line) {
        $line.find("> [id]").attr("class", "disrupted");
        return $line.get(0).getBBox();
    }

    o.initStationDisruptions = function () {
        for (var i = 0; i < stationDisruptionData.length; i++) {
            var station = stationDisruptionData[i];
            if ((station.type.toLowerCase() === "stopblocking" || station.type.toLowerCase() === "part closure") && station.stationAtcoCode) {
                var $stationElement = $('#' + station.stationAtcoCode.toLowerCase());
                if ($stationElement.length > 0) {
                    $stationElement.attr("class", "disrupted closed");
                } else {
                    $stationElement = $("#interchange-circles").find('[id*=' + station.stationAtcoCode.toLowerCase() + ']');
                    if ($stationElement.length > 0) {
                        $stationElement.attr("class", "disrupted closed");
                    } else {
                        $stationElement = $(".line").find('rect[id*=' + station.stationAtcoCode.toLowerCase() + ']');
                        if ($stationElement.length > 0) {
                            $stationElement.attr("class", "disrupted closed");
                        }
                    }
                }
            }
        }

        $('[data-naptan-id]').each(function () {
            var $this = $(this);
            var $par = $this.closest(".rainbow-list-item");
            var naptanId = $this.data("naptan-id").toLowerCase();
            if (naptanId && naptanId.length > 0) {
                var stationComponents = $("#interchange-circles [id*='" + naptanId + "'], .line > rect[id*='" + naptanId + "'], .line > polyline[id*='" + naptanId + "'], .line > g[id*='" + naptanId + "']");
                if (stationComponents.length > 0) {
                    var bbox = { x: Number.MAX_VALUE, y: Number.MAX_VALUE, x2: Number.MIN_VALUE, y2: Number.MIN_VALUE, width: 0, height: 0 };

                    for (var i = 0; i < stationComponents.length; i++) {
                        o.addToBoundingBox(bbox, stationComponents[i].getBBox());
                    }

                    if (bbox.width > 0 && bbox.height > 0) {
                        $par.on("rainbow-list.expanded", function () {

                            // zoom in
                            if ($(document.body).hasClass('breakpoint-Large')) {
                                zoomableContent.zoomToBoundingBox(bbox);
                            }
                        });
                        $par.on("rainbow-list.collapsed", function () {
                            if ($(document.body).hasClass('breakpoint-Large')) {
                                // zoom out only if we haven't moved
                                if (!zoomableContent.movedSinceZoom) {
                                    zoomableContent.resetToInitialView();
                                }
                            }
                        });
                    }
                }
            }
        });
    };

    function setupLine(line) {
        var hasDisruptions = false;
        var lineId = line.id;
        var bbox = { x: Number.MAX_VALUE, y: Number.MAX_VALUE, x2: Number.MIN_VALUE, y2: Number.MIN_VALUE, width: 0, height: 0 };

        for (var j = 0; j < line.lineStatuses.length; j++) {
            var status = line.lineStatuses[j];
            if (!("disruption" in status)
                || !("affectedRoutes" in status.disruption)
                || !("statusSeverityDescription" in status)
                || status.statusSeverityDescription.toLowerCase() === "special service"
                || status.statusSeverityDescription.toLowerCase() === "minor delays"
                ) {
                continue;
            }
            if (!status.disruption.affectedRoutes.length) {
                bbox = o.addToBoundingBox(bbox, disruptWholeLine($("#" + lineId)));
                hasDisruptions = true;
            }
            var wholeLineCandidate = 0;
            for (var k = 0; k < status.disruption.affectedRoutes.length; k++) {
                var routeSections = status.disruption.affectedRoutes[k].routeSectionNaptanEntrySequence;
                if (routeSections.length < 2) {
                    //we don't have enough stops so we assume it's the whole line that needs disrupting
                    wholeLineCandidate += 1;
                    if (wholeLineCandidate === status.disruption.affectedRoutes.length - 1) {
                        bbox = o.addToBoundingBox(bbox, disruptWholeLine($("#" + lineId)));
                        hasDisruptions = true;
                    }
                    continue;
                }
                var prevStationId = null;
                for (var l = 0; l < routeSections.length; l++) {
                    var stopPoint = routeSections[l].stopPoint;
                    var currentStationId = stopPoint.id.toLowerCase();


                    disruptStation(lineId, currentStationId);

                    if (prevStationId !== null && prevStationId !== currentStationId) {
                        var lineSegment = disruptLineSection(lineId, currentStationId, prevStationId);
                        if (lineSegment) {
                            hasDisruptions = true;
                            bbox = o.addToBoundingBox(bbox, lineSegment.getBBox());
                        }
                    }
                    prevStationId = currentStationId;
                }
            }
        }
        boundingBoxes[lineId] = bbox;
        return hasDisruptions;
    }

    o.zoomToLineId = function (lineId) {
        if (lineId in boundingBoxes) {
            var bbox = boundingBoxes[lineId];
            if (bbox.width > 0 && bbox.height > 0) {
                zoomableContent.zoomToBoundingBox(bbox);
            }
        }
    };

    o.resetToInitialView = function () {
        if (!zoomableContent.movedSinceZoom) {
            zoomableContent.resetToInitialView();
        }
    };

    o.setupTubeMapMessage = function (msg, canClose) {
        tfl.logs.create("tfl.tubemap: showing no disruptions message");
        messageOpened = true;
        var message;
        if (msg) {
            message = msg;
        } else {
            message = "There are currently no major line disruptions reported on the network";
            var future = "";
            if (tfl.tools.getQueryStringParameter("startDate")) {
                future = " future ";
                message = "There are no closures scheduled for this period";
            }
        }

        zoomableContent.disablePanZoom();

        var $message = $("<span class='no-disruptions-panel-message'>" + message + "</span>");
        var $noDisruptionsPanel = $("<div class='no-disruptions-panel " + future + "'></div>");
        if (canClose) {
            var $closePanelButton = $("<span class='icon close3-icon' tabindex='0'></span>");
            $noDisruptionsPanel.append($closePanelButton)

            $closePanelButton.click(function () {
                $noDisruptionsPanel.remove();
                zoomableContent.enablePanZoom();
                $statusMapContainer.removeClass("no-disruptions-on-map");
                messageClosed = true;
            });
        }
        $noDisruptionsPanel.append($message);
        $statusMapContainer.append($noDisruptionsPanel).addClass("no-disruptions-on-map");
    };

    o.initDisruptions = function () {
        var hasDisruptions = false;

        for (var i = 0; i < disruptionData.length; i++) {
            var line = disruptionData[i];

            if (!("lineStatuses" in line)) {
                continue;
            }
            hasDisruptions = setupLine(line) || hasDisruptions;
        }

        if (!hasDisruptions && !messageClosed && !messageOpened) {
            o.setupTubeMapMessage(null, true);
        }
    };

    o.addToBoundingBox = function (bbox, bboxToAdd) {
        bbox.x = Math.min(bbox.x, bboxToAdd.x);
        bbox.y = Math.min(bbox.y, bboxToAdd.y);
        bbox.x2 = Math.max(bbox.x2, bboxToAdd.x + bboxToAdd.width);
        bbox.y2 = Math.max(bbox.y2, bboxToAdd.y + bboxToAdd.height);
        bbox.width = bbox.x2 - bbox.x;
        bbox.height = bbox.y2 - bbox.y;
        return bbox;
    };

    o.enablePanZoom = function () {
        tfl.logs.create("tfl.tubemap: map activated");
        zoomableContent.enablePanZoom();
        zoomableContent.resize();
        zoomableContent.zoomToMinScale();
        zoomableContent.panToCentre();
    };

    o.disablePanZoom = function () {
        tfl.logs.create("tfl.tubemap: map deactivated");
        zoomableContent.resize();
        zoomableContent.zoomToMinScale();
        zoomableContent.panToCentre();
        zoomableContent.disablePanZoom();
    };

    o.showHideExtras = function (hide) {
        if (hide) {
            $extras.attr("class", "hidden");
        } else {
            $extras.removeAttr("class");
        }
    };

    o.loadMapCallback = function (response) {
        tfl.logs.create("tfl.tubemap: tubemap ajax loaded");
        $statusMapContainer.prepend(response);
        $map = $("#status-map");
        if (!$statusMapContainer.data('no-disruptions')) {

            $map.attr("class", "coloured-disruptions");

            var $lines = $("[data-line-id]");
            $lines.each(function () {
                $map.find("#" + $(this).data("line-id")).attr("class", "line");
            });

            o.ajaxDisruptions();
        } else {
            o.linesLoaded();
        }

        //TODO - use a more generic approach, rather than one that relies on a specific ID being present.    
        var svgScale = $("#waterloo-city").get(0).getBoundingClientRect().height / $("#waterloo-city").get(0).getBBox().height;

        zoomableContent = tfl.zoomableContent.init($map, { isSVG: true, svgScale: svgScale });

        if ($(document.body).hasClass('breakpoint-Large')) {
            mapObject.activateMap();
        }
    };

    o.loadMap = function () {
        tfl.ajax({
            url: "/Modules/TubeMap",
            success: o.loadMapCallback,
            dataType: 'html'
        });

        mapObject.on('mapActivated', function () {
            o.enablePanZoom();
        });
        mapObject.on('mapDeactivated', function () {
            o.disablePanZoom();
        });

        o.setupMapButtons();
        if (!$statusMapContainer.data('no-disruptions')) {
            o.setupMapPanel();
        }
    };

    o.setupMapPanelCallback = function (response) {
        $mapPanel = $(response);
        $statusMapContainer.append($mapPanel);
        $mapPanel.find(".close-map-panel-button").click(o.hideMapPanel);
        $mapPanel.find("input[name='disruption-colour']").change(function () {
            $map.attr("class", $("input[name='disruption-colour']:checked").attr("id"));
            o.hideMapPanel();
        });
        tfl.forms.init();
    };

    o.setupMapPanel = function () {
        $showPanelButton = $('<button title="Toggle panel" id="toggle-panel-status-map" class="map-button"><span class="icon menu-icon-blue"></span></button>');
        $statusMapContainer.append($showPanelButton);
        $showPanelButton.click(o.showMapPanel);
        tfl.ajax({
            url: '/static/' + tfl.settings.version + '/templates/schematic-map-options-panel.html',
            success: o.setupMapPanelCallback,
            dataType: 'text'
        });
    };

    o.showMapPanel = function () {
        $(document.body).addClass("showing-map-panel");
    };

    o.hideMapPanel = function () {
        $(document.body).removeClass("showing-map-panel");
    };

    o.setupMapButtons = function () {
        //$openMapButton = $('<button title="Full screen" id="open-fullscreen-stage" class="fullscreen-stage-button"><span class="icon expand-map-icon"></span></button>');
        $statusMapContainer.append($openMapButton);
        $statusMapContainer.append('<span class="throbber"></span>');
    };

    var mapObject;

    o.init = function () {
        tfl.logs.create("tfl.tubeMap.init: started");

        function setupMapObject() {
            mapObject = tfl.maps.tubemap;
            o.loadMap();
        }

        tfl.mapInteractions.init();
        if (tfl.maps && tfl.maps.tubemap !== undefined) {
            setupMapObject();
        } else {
            $(window).one('map-object-created-tubemap', function () {
                setupMapObject();
            });
        }
    };

    o.init();
})(window.tfl.tubeMap = window.tfl.tubeMap || {});
