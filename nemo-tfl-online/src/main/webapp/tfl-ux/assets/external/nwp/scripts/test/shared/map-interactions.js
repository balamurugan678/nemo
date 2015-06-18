(function (o) {
    tfl.logs.create("tfl.mapInteractions: loaded");

    var bikePointAutoRefreshId = "MapPanelBikePoint";
    var nearbyMapZoomLevel = 17;
    var nearbyRoutesMapZoomLevel = 12;
    var $body = $('body');

    var options = {
        activeMapClass: "active-map",
        mapContainerSelector: ".map-wrapper",
        closePanelClass: "close-map-panel",
        closePanelWrapperClass: "close-panel-wrapper",
        mapScrollTimeMS: 500,
        mapSelector: "#map",
        showingPanelClass: "showing-map-panel"
    };

    o.init = function () {
        tfl.maps = tfl.maps || {};
        $('.sliver-overlay').off('click').on('click', function () {
            $(this).siblings().each(function () {
                var id = $(this).attr('id');
                if (id !== undefined && tfl.maps[id.replace('-', '')] !== undefined) {
                    tfl.maps[id.replace('-', '')].activateMap();
                }
            });
        });
        tfl.logs.create("tfl.mapInteractions: initialising");
        $('.map-sliver').each(function () {
            var $this = $(this);
            if (!$this.hasClass('placeholder')) {
                if ($this.data('sliver-target') !== undefined) {
                    $this = $this.data('sliver-target');
                }
                new MapObject($this);
            }
        });
        tfl.logs.create("tfl.mapInteractions: initialised");
    };

    function findFirstCommonAncestor(nodeA, nodeB, ancestorsB) {
        var ancestorsB = ancestorsB || getAncestors(nodeB);
        if (ancestorsB.length == 0) return null;
        else if (ancestorsB.indexOf(nodeA) > -1) return nodeA;
        else if (nodeA == document) return null;
        else return findFirstCommonAncestor(nodeA.parentNode, nodeB, ancestorsB);
    }

    function getAncestors(node) {
        if (node != document) return [node].concat(getAncestors(node.parentNode));
        else return [node];
    }

    function MapObject($element, preferences) {

        var t = this;
        if (typeof $element === "string") {
            t.target = $element;
            $element = $(t.target);
        }
        t.$element = $element;
        if (t.$element.data('initialised')) {
            return false;
        }

        t.fullscreenZoomable = false;
        t.settings = $.extend(options, preferences || {});
        t.callbacks = {};
        t.mapType = t.$element.data('map-type');
        t.panelTarget = t.$element.data('panelTarget');
        t.startDate = tfl.tools.getQueryStringParameter("startDate");
        t.endDate = tfl.tools.getQueryStringParameter("endDate");
        t.active = false;
        t.id = t.$element.attr('id');
        if (t.id !== undefined) {
            t.concatId = t.id.replace('-', '');
        }
        t.$element.data('initialised', true);

        // panel
        if (t.panelTarget !== undefined) {
            t.mapPanel = new MapPanel(t, $(t.panelTarget));
            t.$commonParent = $(findFirstCommonAncestor(t.mapPanel.$mapPanel[0], t.$element[0]));
        } else {
            t.$commonParent = $(document.body);
        }

        // If google map - init
        if (t.mapType === "google") {
            t.googleMap = new GoogleMap(t);
        } else if (t.mapType === "interactive-schematic") {
            ///t.interactiveMap = new InteractiveMap(t);
            t.setupSchematicInteractions();
        } else if (t.target !== undefined) {
            t.setupSchematicInteractions();
            // non interactive - image probably 
            t.fullscreenZoomable = true;
            if ($(document.body).hasClass('breakpoint-Large')) {
                tfl.utils.runOnImgComplete($(t.target).get(0), function () {
                    tfl.zoomableContent.init($(t.target));
                });
            } else {
                $(window).one('enterBreakpointLarge', function () {
                    tfl.utils.runOnImgComplete($(t.target).get(0), function () {
                        tfl.zoomableContent.init($(t.target));
                    });
                });
            }

            t.setupZoomableInteractions();
        }

        t.initWindowInteractions();

        // store it in accessible place
        if (t.id !== undefined) {
            if (t.mapType !== "google") {
                tfl.maps[t.concatId] = t;
                $(window).trigger('map-object-created-' + t.concatId);
                tfl.logs.create('tfl.mapInteractions: map object created - ' + t.concatId);
            }
        }
    }

    MapObject.prototype.setupZoomableInteractions = function () {
        var t = this;
        var $targ = $(t.target);

        $(window).on('exitBreakpointLarge', function () {
            $targ.parent().parent().prepend($targ);
            $targ.trigger('destroy-panzoom');
        }).on('enterBreakpointLarge', function () {
            $(window).one('append-around-complete', function () {
                tfl.zoomableContent.init($targ);
            });
        });
    };

    MapObject.prototype.setupSchematicInteractions = function () {
        var t = this;
        $(window).on("exit-fullscreen-stage", function () {
            if (!$body.hasClass('breakpoint-Large')) {
                t.deactivateMap();
            }
        });
        $(window).on('append-around-complete', function () {
            if ($body.hasClass('breakpoint-Large')) {
                t.activateMap();
            }
        });
    };

    MapObject.prototype.initWindowInteractions = function () {
        var t = this;
        $(window).on("enterBreakpointLarge", function () {
            if (t.fullscreen) {
                t.closeFullscreen(true);
            } else {
                t.activateMap();
            }
            if (t.mapType === "interactive-schematic") {
                t.$commonParent.addClass(t.settings.activeMapClass); ///////////////// 
            }
        }).on("exitBreakpointLarge", function () {
            t.deactivateMap();
        });

    };

    MapObject.prototype.activateMap = function () {
        var t = this;
        if (!t.active) {
            t.active = true;
            tfl.logs.create("tfl.mapInteractions: map activated");
            t.$commonParent.addClass(t.settings.activeMapClass); //////////////////////////////////////////
            //only full screen on large breakpoint if we've clicked the button, not if we're just changing breakpoints
            if (!$(document.body).hasClass("breakpoint-Large")) {
                if (t.fullscreenZoomable) {
                    tfl.fullscreen.display($(t.target), t.fullscreenZoomable);
                } else {
                    tfl.fullscreen.display(t.$element, t.fullscreenZoomable);
                }
                t.fullscreen = true;
            }

            if (t.googleMap !== undefined) {
                t.googleMap.controller.activateMap();
            }

            t.trigger('mapActivated');
        }
    };


    MapObject.prototype.deactivateMap = function () {
        var t = this;
        t.$commonParent.removeClass(t.settings.activeMapClass);
        if (t.active) {
            t.active = false;
            tfl.logs.create("tfl.mapInteractions: map deactivated");
            if (t.mapPanel !== undefined) {
                t.mapPanel.hide();
            }
            if (t.googleMap !== undefined) {
                t.googleMap.controller.deactivateMap();
            }
            t.trigger('mapDeactivated');
        }
    };

    MapObject.prototype.closeFullscreen = function (remainActive) {
        var t = this;
        if (t.fullscreen) {
            tfl.fullscreen.hide();
            t.fullscreen = false;
        }
        if (!remainActive) {
            t.active = false;
        }
        // $(".moving-source-order").appendAround();
    };

    MapObject.prototype.scrollToMap = function () {
        var t = this;
        window.scrollTo(0, (t.$element.offset().top - 10)); //////test
    };

    // listener handler
    MapObject.prototype.on = function (event, callback) {
        var t = this;
        if (t.callbacks.hasOwnProperty(event)) {
            t.callbacks[event].push(callback);
        } else {
            t.callbacks[event] = [callback];
        }
    };

    MapObject.prototype.trigger = function (event, data) {
        var t = this;
        if (t.callbacks.hasOwnProperty(event)) {
            var calls = t.callbacks[event];
            for (var i = 0; i < calls.length; i += 1) {
                calls[i](data);
            }
        }
    };


    function MapPanel(parent, $target) {
        var t = this;
        t.parent = parent;
        t.$mapPanel = $target;
        t.$infoPanel = t.$mapPanel.find('.map-panel-info');
        t.$closeButton = t.$mapPanel.find('a.close3-icon').on('click', function (e) {
            t.hide(e);
        });
        t.$mapPanel.on('click', function () {
            if (t.parent.fullscreen && !t.$mapPanel.hasClass('loading') && !t.$mapPanel.hasClass('full-panel')) {
                t.$mapPanel.addClass('full-panel');
            }
        });

        // if SSP cant have the same panel as page.
        var $ssp = $('#station-stops-and-piers');
        if ($ssp.length) {
            t.pageNaptan = $ssp.data('id');
        } else {
            t.pageNaptan = null;
        }
    }

    MapPanel.prototype.displayUrl = function (ajaxUrl, data, callback) {
        var t = this;

        tfl.stopAjaxAutoRefresh(bikePointAutoRefreshId);
        if (ajaxUrl === undefined
            || data === undefined
            || (data.naptanId === undefined
                && data.placeId === undefined)) {
            return false;
        }
        if (t.pageNaptan !== null && t.pageNaptan === data.naptanId) {
            t.hide();
            return false;
        }

        t.loadingId = data.naptanId || data.placeId;

        t.$mapPanel.addClass('loading');
        if (t.$mapPanel.hasClass('hidden')) {
            t.$mapPanel.removeClass('hidden');
        }

        var ajaxSuccess = function (response) {
            if (t.loadingId === data.naptanId || t.loadingId === data.placeId) { // if we still want to load this panel
                t.show($(response));
                if (callback) callback();
            }
        };

        var ajaxError = function () {
            if (t.loadingId === data.naptanId || t.loadingId === data.placeId) {
                t.show($("<h2>Loading information failed</h2>"));
            }
        };

        tfl.ajax({
            url: ajaxUrl,
            data: data,
            success: ajaxSuccess,
            error: ajaxError,
            dataType: 'text'
        });
    };


    MapPanel.prototype.show = function ($el) {
        var t = this;
        t.$infoPanel.empty().append($el);
        if (t.$mapPanel.hasClass('hidden')) {
            t.$mapPanel.removeClass('hidden');
        }
        if (t.$mapPanel.hasClass('loading')) {
            t.$mapPanel.removeClass('loading');
        }
    };

    MapPanel.prototype.hide = function (e) {
        var t = this;
        t.loadingId = null;
        tfl.stopAjaxAutoRefresh(bikePointAutoRefreshId);

        if (t.$mapPanel.hasClass("full-panel")) {
            t.$mapPanel.removeClass("full-panel");
        } else {
            t.$mapPanel.addClass("hidden");
        }
        if (e) {
            e.stopPropagation();
        }
    };

    function GoogleMap(parent) {

        var t = this;
        t.parent = parent;
        t.purpose = t.parent.$element.data('map-purpose');
        t.callbacks = {};
        t.params = {};
        t.initialized = false;
        t.initializeParams();

        t.initMap();

    }

    GoogleMap.prototype.initMap = function () {
        var t = this;
        steal(tfl.mapScriptPath).then(function () {
            $.fixture.on = false; // TODO really need to be able to take this out, an issue to fix within the mapping code
            if (t.initialized === false) {
                t.initialized = true;
                var mapCall = t.purpose === "road" ? "roadmap" : "map"; /// be nice to be able to remove this. -----------------------
                t.controller = t.parent.$element["tfl_maps_" + t.purpose + "_" + mapCall](t.params).controller();

                t.controller.mapLoading.done(function () {
                    tfl.maps[t.parent.concatId] = t.parent;
                    $(window).trigger('map-object-created-' + t.parent.concatId);
                    tfl.logs.create('tfl.map-interactions: map object created - ' + t.parent.concatId);
                });
            }
        });
    };

    // listener handler
    GoogleMap.prototype.on = function (event, callback) {
        var t = this;
        if (t.callbacks.hasOwnProperty(event)) {
            t.callbacks[event].push(callback);
        } else {
            t.callbacks[event] = [callback];
        }
    };

    GoogleMap.prototype.trigger = function (event, data) {
        var t = this;
        if (t.callbacks.hasOwnProperty(event)) {
            var calls = t.callbacks[event];
            for (var i = 0; i < calls.length; i += 1) {
                calls[i](data);
            }
        }
    };

    GoogleMap.prototype.initializeParams = function () {
        var t = this;

        // global params
        if (!$body.hasClass('breakpoint-Large')) {
            t.params.initiallyDeactivated = true;
            t.active = false;
            t.parent.active = false;
        } else {
            t.params.initiallyDeactivated = false;
            t.active = true;
            t.parent.active = true;
        }
        if (t.parent.startDate !== null && t.parent.startDate !== "") {
            t.params.startDate = tfl.tools.makeDateFromQueryStringDate(t.parent.startDate);
            t.params.endDate = tfl.tools.makeDateFromQueryStringDate(t.parent.endDate);
        }
        var $nearList = $('.nearby-list'); // needs scoping 
        if ($nearList.length) {
            var searchLat = $nearList.data("search-lat");
            var searchLon = $nearList.data("search-lon");
            if (searchLat != null && searchLon != null) {
                t.params.mapCentre = {
                    lat: searchLat,
                    lng: searchLon
                };
            }
        }

        // global callbacks 
        t.params.mapActivated = function () {
            t.mapActivated();
        };
        t.params.mapDeactivated = function () {
            t.mapDeactivated();
        };

        // purpose specific params

        var subPurpose = t.parent.$element.data('map-purpose-subtype');
        switch (t.purpose) {
            case "road":
                t.params.layerStateChanged = function (data) {
                    t.trigger('layerStateChanged', data)
                };
                t.params.disruptionChosen = function (data) {
                    t.disruptionChosen(data)
                };
                //t.params.jamCamChosen = function (data) { t.jamCamChosen(data) };
                //t.params.variableMessageSignChosen = function(data){t.variableMessageSignChosen(data)};
                t.params.placeChosen = function (data) {
                    t.placeChosen(data);
                };
                break;
            case "nearby":
                if (subPurpose === "routes") {
                    // we want custom zoom thresholds for the bus routes map to stop the map being overwhelming
                    t.params.zoomThresholds = {
                        displayHubsBy: 14,
                        displayStationsBy: 14
                    };
                    t.params.initialZoom = nearbyRoutesMapZoomLevel;
                } else {
                    t.params.initialZoom = nearbyMapZoomLevel;
                }
                break;
            case "disambiguation":
                t.params.optionsElement = $("#disambiguation-options, #" + t.parent.id.replace("-map-", "-options-"));
                t.params.pageSize = tfl.journeyPlanner.settings.disambiguationItemsPerPage;
                t.params.isNationalBounds = tfl.getQueryParam('NationalSearch') === 'true';
                t.params.optionChosen = function (data) {
                    t.trigger('optionChosen', data)
                };
                break;
        }

        // layer specific params for each layer
        var layers = t.parent.$element.data('layers');
        if (layers !== undefined) {
            layers = layers.split(',');
            for (var i = 0; i < layers.length; i += 1) {
                // add to map Params
                // add default callbacks
                switch (layers[i]) {
                    case "road-corridors":
                        break;
                    case "station-stop":
                        t.params.stationStopsChosen = function (data) {
                            t.stationStopsChosen(data)
                        };
                        break;
                    case "cycle-hire":
                    case "coach-parking":
                        t.params.placeChosen = function (data) {
                            t.placeChosen(data)
                        };
                        break;
                    default:
                        // unhandled layers
                        break;
                }
            }
        }
    };

    // global
    GoogleMap.prototype.mapActivated = function () {
        var t = this;
        if (!t.active) {
            t.active = true;
            t.parent.active = true;
            if (!$(document.body).hasClass("breakpoint-Large")) {
                tfl.fullscreen.display(this.parent.$element, false, true);
                this.parent.fullscreen = true;
                if (this.parent.mapPanel !== undefined) {
                    tfl.fullscreen.addItemToStage(this.parent.mapPanel.$mapPanel);
                }
            }
            this.controller.divResized();
            this.trigger('mapActivated');
        }
    };

    GoogleMap.prototype.mapDeactivated = function () {
        var t = this;
        if (t.active) {
            t.active = false;
            //only full screen on large breakpoint if we've clicked the button, not if we're just changing breakpoints
            this.parent.closeFullscreen();
            if (this.parent.mapPanel !== undefined) {
                this.parent.mapPanel.hide();
            }
            this.trigger('mapDeactivated');
        }
    };

    // roads
    GoogleMap.prototype.disruptionChosen = function (data) {
        this.trigger('disruptionChosen', data);
    };

    function mapPanelContentSetupCallback() {

        tfl.favourites.setupButtons();

        //setup the close panel button with meaningful text for accessibility
        var $mp = $(".map-panel");
        $mp.find(".close3-icon").text("Close " + $mp.find(".nearby-list-heading").text());

        if (tfl.responsiveButtonRow !== undefined) {
            tfl.responsiveButtonRow.init();
        }
        if (tfl.predictions !== undefined) {
            tfl.logs.create("tfl.stops: initialising predictions");

            tfl.busOptions.init(".map-panel");
            tfl.predictions.init(".map-panel");
        }
    }

    // stations stops
    GoogleMap.prototype.stationStopsChosen = function (data) {
        var t = this;
        var datum;
        var mode;
        if (data.length > 0) {
            datum = data[0];
            mode = "";

            if (datum.modeNames.length == 1) {
                mode = datum.modeNames[0];
            } else if (datum.modeNames.length > 1) {
                mode = tfl.modeNameMultiModal;
            }
            t.mode = mode;
            t.naptanId = datum.naptanId;

            var ajaxInput = {
                fullContent: false,
                mode: mode,
                naptanId: datum.naptanId
            };

            t.parent.mapPanel.displayUrl("/Stops/Content", ajaxInput, function () {
                mapPanelContentSetupCallback();
                t.trigger('stationStopsChosen', data);
            });
        }
    };

    GoogleMap.prototype.coachChosen = function (data) {
        var t = this;
        if (data.hasOwnProperty('additionalProperties')) {
            var $wrapper = $('<div></div>');
            var $info = $('<div class="map-panel-content"></div>');
            for (var prop in data.additionalProperties) {
                var contact;
                var title;
                var subtitle;
                var cost;
                if (data.additionalProperties.hasOwnProperty(prop)) {
                    var p = data.additionalProperties[prop];
                    var key = p.key.toLowerCase();
                    var category = p.category.toLowerCase();
                    if (category === "address") {
                        if (key === "address") {
                            title = p.value;
                        } else if (key === "contact") {
                            contact = '<p class="contact">' + p.value + '</p>';
                        }
                    } else if (category === "description") {
                        if (key === "detail") {
                            subtitle = p.value;
                        } else if (key === "cost") {
                            cost = '<p class="cost">' + p.value + '</p>';
                        } else if (key === "coachpark" || key === "noasonmap" || key === "bay") {
                        } else {
                            $info.append('<p class="description">' + p.value + '</p>');
                        }
                    }
                }
            }
            if (subtitle !== undefined) {
                $wrapper.prepend('<h4 class="map-panel-subheading coach">' + subtitle + '</h4>');
            }
            if (title !== undefined) {
                $wrapper.prepend('<span class="map-panel-heading coach">' + title + '</span>');
            }
            if (contact !== undefined) {
                $wrapper.append(contact);
            }
            if (cost !== undefined) {
                $info.append(cost);
            }
            $wrapper.append($info);
            t.parent.mapPanel.show($wrapper);
            t.trigger('coachChosen', data);
        }

    };

    GoogleMap.prototype.loadFromList = function (id, mode) {
        var t = this;

        t.naptanId = id;
        var ajaxInput = {
            fullContent: false,
            mode: mode,
            naptanId: id
        };
        var data = [
            {
                modeNames: [mode],
                naptanId: id
            }
        ];
        t.parent.mapPanel.displayUrl("/Stops/Content", ajaxInput, function () {

            mapPanelContentSetupCallback();
            t.trigger('stationStopsChosen', data);

            // sort out tabbing order        

            var mapPanel = $('.map-panel'),
                $nearbyList = $('.nearby-list'),
                nextLink, firstLink, originatorLink,
                originator = $nearbyList.find('li').filter(function (i, el) {
                    return $(this).data('id') == id;
                });
            hasShowMore = (originator.find('.expandable-information'));

            //remove old jump-to actions
            $nearbyList.find('.expanded-result-details > a:first-child').removeData("jumpto");

            if ($('.nearby-list li:visible:last').data('id') == id) {
                if ($('.pagination-controls').length) {
                    if (!hasShowMore.length) {
                        nextLink = $('.pagination-controls').find('a.first_link');
                    }
                    else {
                        nextLink = hasShowMore.find('a.show-more');
                    }
                } else {
                    nextLink = $('ul.footer-links > li:first a');
                }
            } else {

                // does result have a show more link
                if (hasShowMore.length) {
                    nextLink = hasShowMore.find('a.show-more');
                } else {
                    nextLink = originator.next().find('a:first');
                }
            }

            firstLink = mapPanel.find('a:first');
            originatorLink = originator.find('.expanded-result-details > a:first');
            originatorLink.data('jumpto', firstLink);
            firstLink.data('jumpback', originatorLink);
            mapPanel.find('a:last').data('jumpto', nextLink);

            // remove jump-tos when map is closed
            $('.map-panel').find('a.close3-icon').on('click.removeTabs', function () {
                $nearbyList.find('.expanded-result-details > a:first-child').removeData("jumpto");
            });

        });
    };

    o.updateBikePoint = function (data, renderCallback) {
        if (data === null || renderCallback === null) {
            return;
        }

        var bikePoint = data;
        var numBikes = 0, numSpaces = 0, numDocks;

        for (var j = 0; j < bikePoint.additionalProperties.length; j++) {
            var property = bikePoint.additionalProperties[j];

            if (property.key == "NbBikes") {
                numBikes = parseInt(property.value);
            } else if (property.key == "NbEmptyDocks") {
                numSpaces = parseInt(property.value);
            }
        }

        numDocks = numBikes + numSpaces;

        var cssDockBarRatio = Math.round(((numBikes / numDocks) * 1000) / 125) * 125;

        if (cssDockBarRatio === 0 && numBikes > 0) {
            // if the station has at least one bike, display at least one block on the indicator bar
            cssDockBarRatio = "125";
        }
        else if (cssDockBarRatio === 1000 && numSpaces > 0) {
            // if the station has at least one space, display at least one space on the indicator bar
            cssDockBarRatio = "875";
        }
        else if (cssDockBarRatio === 0) {
            cssDockBarRatio = "000";
        }

        cssDockBarRatio = "full-" + cssDockBarRatio;

        var numBikesMessage, numSpacesMessage;

        if (numBikes === 1) {
            numBikesMessage = "1 bike available";
        } else {
            numBikesMessage = numBikes + " bikes available";
        }

        if (numSpaces === 1) {
            numSpacesMessage = "1 space";
        } else {
            numSpacesMessage = numSpaces + " spaces";
        }

        renderCallback(bikePoint.id, cssDockBarRatio, numBikes, numBikesMessage, numSpaces, numSpacesMessage);
    };

    function updateBikePointInMapPanel(bikePointId, cssDockBarRatio, numBikes, numBikesMessage, numSpaces, numSpacesMessage) {
        var $mapPanel = $(".map-panel-info");
        var $dockBar = $mapPanel.find(".cycle-hire-block");
        var $docks = $dockBar.find(".docks");
        var dockBarFull = "<div class='dock full'></div>";
        var dockBarEmpty = "<div class='dock'></div>";

        $docks.hide();

        if (numBikes + numSpaces > 80) {
            $docks.addClass("borderless");
        }

        $docks.empty();
        for (var i = 0; i < numBikes; i++) {
            $docks.append(dockBarFull);
        }
        for (i = 0; i < numSpaces; i++) {
            $docks.append(dockBarEmpty);
        }
        $docks.show();

        var $dockIcon = $mapPanel.find(".bch-docking-station-icon");
        var oldClass = $dockIcon.attr("class");
        oldClass = oldClass.substring(oldClass.indexOf("full-"));
        var spaceIdx = oldClass.indexOf(" ");
        if (spaceIdx >= 0) {
            oldClass = oldClass.substring(0, spaceIdx);
        }
        $dockIcon.removeClass(oldClass).addClass(cssDockBarRatio);
        $dockBar.find(".bikes").text(numBikesMessage);
        $dockBar.find(".spaces").text(numSpacesMessage);
    }

    GoogleMap.prototype.placeChosen = function (data) {
        var t = this;

        switch (data.placeType) {
            case "BikePoint":
                var ajaxInput = {
                    placeId: data.id
                };
                t.parent.mapPanel.displayUrl(
                    "/Near/BikePoint",
                    ajaxInput,
                    function () {
                        t.trigger('placeChosen', data);
                        tfl.ajax({
                            url: tfl.api.BikePointId.format(data.id),
                            success: function (response) {
                                o.updateBikePoint(response, updateBikePointInMapPanel);
                            },
                            autoRefreshInterval: tfl.autoRefresh.BikePoint,
                            autoRefreshId: bikePointAutoRefreshId
                        });
                    }
                );
                break;
            case "CoachPark":
            case "CoachBay":
            case "OtherCoachParking":
            case "OnStreetMeteredBay":
                t.coachChosen(data);
                break;
            case "JamCam":
                this.trigger('jamCamChosen', data);
                break;
            case "VariableMessageSign":
                this.trigger('variableMessageSignChosen', data);
                break;
        }
    };
}(window.tfl.mapInteractions = window.tfl.mapInteractions || {}));