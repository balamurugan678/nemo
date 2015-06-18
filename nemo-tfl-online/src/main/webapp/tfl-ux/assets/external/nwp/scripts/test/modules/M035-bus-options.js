(function (o) {

    function busOptions($parent, scope) {
        if (!$parent) {
            return false;
        }

        // instance variables
        var $options = $parent.find('.bus-option'),
            $disruptionMessages = $parent.parent().siblings('.disruption-message');


        // change to new filter. input the new filter LI element.
        function newFilter($filt, force) {
            if (!force && $filt.hasClass('active')) {
                return false;
            }
            $options.filter('.active').removeClass('active');
            $filt.addClass('active');

            var lineName = ("" + $filt.data("line-name")).toLowerCase();

            var $visibleBoard = $(scope).find(".live-board:not(.hidden)");
            var $departures = $visibleBoard.find(".live-board-feed-item");
            $departures.addClass("hidden");

            var numToShow = Number.MAX_VALUE;
            var $departuresLink = $visibleBoard.find(".live-board-link");
            var hasDeparturesLink = $departuresLink.length !== 0;
            if (hasDeparturesLink) {
                if (tfl.liveBoards && tfl.liveBoards.numInitiallyShown) {
                    numToShow = tfl.liveBoards.numInitiallyShown;
                } else {
                    numToShow = 3;
                }
            }

            $departures.slice(numToShow).addClass("paginate-hidden");
            var $chosen;
            //If we've clicked "All"
            if (lineName === "undefined") {
                $chosen = $departures;
            } else {
                $chosen = $departures.filter("[data-line-name='" + lineName + "']");
            }

            $chosen.slice(0, numToShow).removeClass("hidden").removeClass("paginate-hidden");
            $chosen.filter(".hidden.paginate-hidden").removeClass("hidden");

            if (hasDeparturesLink && $chosen.length > numToShow) {
                $departuresLink.removeClass("hidden");
            } else if (hasDeparturesLink) {
                $departuresLink.addClass("hidden");
            }

            $disruptionMessages.not('.hidden').addClass('hidden');
            $disruptionMessages.filter(function () {
                return ("" + $(this).data('line-name')).toLowerCase() === lineName;
            }).removeClass('hidden');
        }

        var $map = $('.map-sliver');
        var mapId;
        var googleMapObject;
        if ($map.length) {
            mapId = $map.attr('id');
            if (mapId !== undefined) {
                mapId = mapId.replace('-', '');
                if (tfl.maps[mapId] !== undefined) {
                    googleMapObject = tfl.maps[mapId].googleMap;
                } else {
                    $(window).on('map-object-created-' + mapId, function () {
                        googleMapObject = tfl.maps[mapId].googleMap;
                    });
                }
            }

        }

        var firstLastServiceLine = null;
        // click handlers
        $options.unbind().on('click.bus-options', function (e) {

            var $self = $(this);
            newFilter($self);
            // first/last service filter
            var lineId = $self.data("lineid");
            var $firstLastEl = $("#first-last-details");
            var $busRouteSelector = $("#bus-route-selector");
            var isEndOfLine = $firstLastEl.data("isendofline");
            $firstLastEl.empty();
            // if not clicked "All"
            if (lineId || isEndOfLine) {
                firstLastServiceLine = lineId;
                var ajaxData = {
                    url: "/Stops/FirstLastServiceDetails",
                    data: {
                        lineId: lineId,
                        lineName: $self.data("line-name"),
                        mode: tfl.modeNameBus,
                        naptanId: $firstLastEl.data("naptanid"),
                        stopName: $firstLastEl.data("stopname"),
                        isEndOfLine: isEndOfLine
                    }
                };

                // add throbber
                if (lineId) {
                    tfl.tools.setQueryStringParameter("lineId", lineId);
                    if (!$busRouteSelector.hasClass("hidden")) $busRouteSelector.addClass("hidden");
                } else {
                    tfl.tools.removeQueryStringParameter("lineId");
                    if ($busRouteSelector.hasClass("hidden")) $busRouteSelector.removeClass("hidden");
                }
                $firstLastEl.addClass("loading");

                var ajaxSuccess = function (response) {
                    if (lineId === firstLastServiceLine) {
                        $firstLastEl.append(response);
                    }
                };

                var ajaxError = function () {
                    $firstLastEl.append('<div class="r"><span class="field-validation-error">' + tfl.apiErrorMessage + '</span></div>');
                };

                var ajaxComplete = function () {
                    $firstLastEl.removeClass("loading");
                };

                // get first/last data
                tfl.ajax({
                    url: ajaxData.url,
                    data: ajaxData.data,
                    success: ajaxSuccess,
                    error: ajaxError,
                    complete: ajaxComplete,
                    dataType: 'html'
                });

                if (googleMapObject !== undefined) {
                    if (googleMapObject.naptanId !== undefined) {
                        tfl.logs.create('TfL.busOptions: choose bus route - ' + ' ' + lineId + googleMapObject.naptanId);
                        googleMapObject.controller.chooseBusRouteForStop(googleMapObject.naptanId, lineId.toString());
                        if (googleMapObject.parent.mapPanel !== undefined && scope === ".station-details") {
                            googleMapObject.parent.mapPanel.hide();
                        }
                    }
                }
            } else {
                firstLastServiceLine = null;
                tfl.tools.removeQueryStringParameter("lineId");
                if ($busRouteSelector.hasClass("hidden")) $busRouteSelector.removeClass("hidden");
                if (googleMapObject !== undefined) {
                    if (googleMapObject.naptanId !== undefined) {
                        tfl.logs.create('TfL.busOptions: choose bus route - ' + ' ' + undefined + googleMapObject.naptanId);
                        if (googleMapObject.parent.mapPanel !== undefined && scope === ".station-details") {
                            googleMapObject.parent.mapPanel.hide();
                            googleMapObject.controller.choosePlace(googleMapObject.naptanId, "StopPoint", { lat: $map.attr("data-lat"), lng: $map.attr("data-lon") }, $map.attr("data-hubnaptancode"));
                        }
                        googleMapObject.controller.chooseBusRouteForStop(googleMapObject.naptanId, null);
                    }
                }
            }


            e.stopPropagation();
        });

        newFilter($parent.find(".bus-option.active"), true);

        // using AJAX when js is enabled.
        $options.find('>a').on('click', function (e) {
            e.preventDefault();
            e.stopPropagation();
        });

        $parent.addClass('active');

    }

    o.init = function (scope, force) {
        tfl.logs.create("tfl.busOptions.init: (scope " + scope + ")");
        var $busOptions = $(scope).find('.bus-options.filter');
        if (!force) {
            $busOptions = $busOptions.not(".active");
        }

        for (var i = 0; i < $busOptions.length; i += 1) {
            busOptions($busOptions.eq(i), scope);
            //$busOptions.eq(i).attr("class");
            //if ($busOptions.eq(i).hasClass("active")) {
            //$busOptions.eq(i).click();
            //}
        }

    };

}(window.tfl.busOptions = window.tfl.busOptions || {}));