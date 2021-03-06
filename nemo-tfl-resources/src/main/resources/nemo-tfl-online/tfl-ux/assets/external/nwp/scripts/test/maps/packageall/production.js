steal.loading('packageall/packageall.js', 'disambiguation/disambiguation.js', 'road/roadmap.js', 'nearby/nearby_map.js', 'embedded/embedded_map.js', 'journeyplanner/journeyplanner.js', 'disambiguation/models/models.js', 'disambiguation/fixtures/fixtures.js', 'framework/framework.js', 'framework/models/settings_helper.js', 'disambiguation/disambiguation_map/disambiguation_map.js', 'disambiguation/models/disambiguation_option.js', 'disambiguation/models/disambiguation.js', 'jquery/model/model.js', 'jquery/class/class.js', 'jquery/lang/string/string.js', 'jquery/dom/fixture/fixture.js', 'jquery/dom/dom.js', 'jquery/lang/object/object.js', 'framework/models/models.js', 'framework/map/map.js', 'framework/fixtures/fixtures.js', 'jquery/controller/controller.js', 'jquery/lang/openajax/openajax.js', 'framework/map/map_utils.js', 'framework/map/google_script_loader.js', 'framework/map/layers/layer_manager.js', 'framework/map/layers/userlocation_layer.js', 'framework/map/options/map_options_manager.js', 'framework/map/controls/custom_controls_manager.js', 'framework/map/style_manager.js', 'framework/map/full_window_manager.js', 'jquery/event/destroyed/destroyed.js', 'jquery/event/event.js', 'framework/map/map_manager_base.js', 'framework/map/layers/static_layer_base.js', 'framework/map/layers/layer_base.js', 'framework/map/overlays/pin.js', 'framework/map/overlays/html_pin.js', 'framework/map/overlays/circle.js', 'framework/map/overlays/polyline.js', 'framework/map/overlays/polygon.js', 'framework/map/overlays/polygonmask.js', 'framework/map/overlays/overlay_base.js', 'framework/map/overlays/html_marker.js', 'jquery/view/ejs/ejs.js', 'lib/jquery.hammer.js', 'jquery/view/view.js', 'jquery/lang/string/rsplit/rsplit.js', 'framework/map/options/options_dialog.ejs', 'lib/jquery.ie.cors.js', 'disambiguation/disambiguation_map/disambiguation_layer.js', 'framework/map/layers/dynamic_layer_base.js', 'disambiguation/models/disambiguation_option_list.js', 'disambiguation/disambiguation_map/disambiguation_pin_composer.js', 'jquery/model/list/list.js', 'framework/map/layers/geolocation_layer.js', 'framework/map/layers/congestion_charging_zone_layer.js', 'framework/map/layers/low_emission_zone_layer.js', 'framework/map/layers/coach_ban_areas_layer.js', 'framework/map/layers/gla_boundary_layer.js', 'framework/map/layers/traffic_layer.js', 'framework/map/layers/places_layer.js', 'framework/map/layers/cycling_layer.js', 'framework/map/layers/virtual_layer.js', 'road/layers/road_disruptions_layer.js', 'road/layers/road_corridors_layer.js', 'framework/geolocation_provider.js', 'framework/geolocation_unavailable_error.js', 'framework/map/layers/fat_roads_layer_base.js', 'framework/map/layers/static_polygon_layer_base.js', 'framework/models/place.js', 'road/severity_layer_filter.js', 'road/models/road_disruption.js', 'framework/map/layers/layer_filter_base.js', 'framework/helpers/api.js', 'road/models/road_corridor.js', 'framework/map/layers/station_stop_layer.js', 'framework/map/layers/cycle_hire_layer.js', 'framework/map/layers/bus_route_layer.js', 'framework/models/station_stop.js', 'framework/models/route.js', 'framework/classes/bus_routes_manager.js', 'framework/map/layers/station_stop_layer_state.js', 'framework/models/bikepoint.js', 'journeyplanner/journey_map/journey_polyline_composer.js', 'framework/map/layers/static_kml_layer.js', 'framework/map/layers/coach_parking_layer.js', 'lib/togeojson.js', 'journeyplanner/models/models.js', 'journeyplanner/fixtures/fixtures.js', 'journeyplanner/leg_map/leg_map.js', 'journeyplanner/models/leg.js', 'journeyplanner/models/journey.js', 'journeyplanner/models/route.js', 'journeyplanner/leg_map/leg_layer.js', 'journeyplanner/journey_map/journey_pin_composer.js');
steal({src: 'packageall/production.css', has: ['disambiguation/disambiguation.css', 'framework/map/css/map_reset.css', 'framework/map/css/map.css', 'framework/map/overlays/html_pins.css', 'framework/map/controls/custom_controls.css', 'framework/map/layers/labelled_pin.css', 'disambiguation/disambiguation_map/disambiguation_pin.css', 'journeyplanner/journeyplanner.css']});
steal.loaded("jquery/jquery.js");
steal("disambiguation/disambiguation.js", "road/roadmap.js", "nearby/nearby_map.js", "embedded/embedded_map.js", "journeyplanner/journeyplanner.js");
steal.loaded("packageall/packageall.js");
steal.loaded("jquery/jquery.js");
steal("./disambiguation.css", "./models/models.js", "./fixtures/fixtures.js", "//framework/framework.js", "//framework/models/settings_helper.js", "./disambiguation_map/disambiguation_map", function () {
});
steal.loaded("disambiguation/disambiguation.js");
steal("framework/map/map.js", "framework/map/layers/geolocation_layer.js", "framework/map/layers/congestion_charging_zone_layer.js", "framework/map/layers/low_emission_zone_layer.js", "framework/map/layers/coach_ban_areas_layer.js", "framework/map/layers/gla_boundary_layer.js", "framework/map/layers/traffic_layer.js", "framework/map/layers/places_layer.js", "framework/map/layers/cycling_layer.js", "framework/map/layers/virtual_layer.js", "./layers/road_disruptions_layer.js", "./layers/road_corridors_layer.js",
    "jquery/lang/openajax", function (d) {
        Tfl.Maps.Framework.Map("Tfl.Maps.Road.Roadmap", {defaults: {channel: "roadmap", minZoom: 1}}, {init: function () {
            this.mapContainer = d("<div class='map map-container roadmap-map-container'></div>");
            this.element.append(this.mapContainer);
            this.corridorsReady = false;
            this._super()
        }, chooseCorridor: function (b) {
            this.selectedCorridorId = b;
            this.corridorsReady && this.roadCorridorsLayer.chooseCorridor(this.selectedCorridorId)
        }, chooseDisruption: function (b, a) {
            this.incidentsLayer.chooseDisruption(b,
                a);
            a && this.jamCamsLayer.show()
        }, showDisruptions: function () {
            this.incidentsLayer.show()
        }, hideDisruptions: function () {
            this.incidentsLayer.hide()
        }, addLayers: function () {
            var b = this.layerManager;
            b.addLayer(Tfl.Maps.Framework.CongestionChargingZoneLayer);
            b.addLayer(Tfl.Maps.Framework.LowEmissionZoneLayer);
            b.addLayer(Tfl.Maps.Framework.GLABoundaryLayer);
            this.roadCorridorsLayer = b.addLayer(Tfl.Maps.Road.RoadCorridorsLayer, {initialVisibility: true, layerOptions: {whenLayerReady: this.proxy(function () {
                this.corridorsReady =
                    true;
                this.selectedCorridorId !== undefined && this.chooseCorridor(this.selectedCorridorId)
            })}});
            b.addLayer(Tfl.Maps.Framework.TrafficLayer);
            this.jamCamsLayer = b.addLayer(Tfl.Maps.Framework.PlacesLayer, {iconPath: "framework/map/layers/icons/JamCam.png", name: "Jam Cams", classSuffix: "jam-cam", layerOptions: {placeTypes: ["JamCam"], displayByZoom: 10, placeChosen: this.proxy(this.placeChosen)}});
            b.addLayer(Tfl.Maps.Framework.PlacesLayer, {iconPath: "framework/map/layers/icons/RedLightCam.png", name: "Red light cameras",
                classSuffix: "red-light-cam", layerOptions: {placeTypes: ["RedLightCam", "RedLightAndSpeedCam"], displayByZoom: 10, placeChosen: this.proxy(this.placeChosen)}});
            b.addLayer(Tfl.Maps.Framework.PlacesLayer, {iconPath: "framework/map/layers/icons/SpeedCam.png", name: "Speed cameras", classSuffix: "speed-cam", layerOptions: {placeTypes: ["SpeedCam", "RedLightAndSpeedCam"], displayByZoom: 10, placeChosen: this.proxy(this.placeChosen)}});
            this.vmsLayer = b.addLayer(Tfl.Maps.Framework.PlacesLayer, {iconPath: "framework/map/layers/icons/VariableMessageSign.png",
                name: "Variable Message Signs", classSuffix: "variable-message-sign", layerOptions: {placeTypes: ["VariableMessageSign"], displayByZoom: 10, placeChosen: this.proxy(this.placeChosen)}});
            b.addLayer(Tfl.Maps.Framework.VirtualLayer, {initialVisibility: false, name: "Road works", classSuffix: "works", layerOptions: {showing: this.proxy(function () {
                this.incidentsLayer.showWorks();
                this.options.layerStateChanged && this.options.layerStateChanged({worksVisible: true, severeVisible: this.incidentsLayer.filters.RoadIncidentSeverity.isVisible("Severe"),
                    minimalVisible: this.incidentsLayer.filters.RoadIncidentSeverity.isVisible("Minimal"), moderateVisible: this.incidentsLayer.filters.RoadIncidentSeverity.isVisible("Moderate"), seriousVisible: this.incidentsLayer.filters.RoadIncidentSeverity.isVisible("Serious")})
            }), hiding: this.proxy(function () {
                this.incidentsLayer.hideWorks();
                this.options.layerStateChanged && this.options.layerStateChanged({worksVisible: false, severeVisible: this.incidentsLayer.filters.RoadIncidentSeverity.isVisible("Severe"), minimalVisible: this.incidentsLayer.filters.RoadIncidentSeverity.isVisible("Minimal"),
                    moderateVisible: this.incidentsLayer.filters.RoadIncidentSeverity.isVisible("Moderate"), seriousVisible: this.incidentsLayer.filters.RoadIncidentSeverity.isVisible("Serious")})
            })}});
            this.incidentsLayer = b.addLayer(Tfl.Maps.Road.RoadDisruptionsLayer, {initialVisibility: true, layerOptions: {startDate: this.options.startDate || null, endDate: this.options.endDate || null, stateChanged: this.proxy(function (a) {
                this.options.layerStateChanged && this.options.layerStateChanged(a)
            })}})
        }, placeChosen: function (b) {
            this.options.placeChosen &&
            this.options.placeChosen(b)
        }})
    });
steal.loaded("road/roadmap.js");
steal("framework/map/map.js", "framework/map/layers/places_layer.js", "framework/map/layers/station_stop_layer.js", "framework/map/layers/cycle_hire_layer.js", "framework/map/layers/bus_route_layer.js", function (d) {
    Tfl.Maps.Framework.Map("Tfl.Maps.Nearby.Map", {defaults: {channel: "nearby"}}, {init: function () {
        this.mapContainer = d("<div class='map map-container nearby-map-container'></div>");
        this.element.append(this.mapContainer);
        this.zoomThresholds = this.options.zoomThresholds || {};
        if (this.zoomThresholds.cycleHirePinsBy ===
            undefined)this.zoomThresholds.cycleHirePinsBy = 15;
        this.enableShortCommonNames = this.options.enableShortCommonNames;
        this._super();
        this.placeChosen = this.options.placeChosen;
        this.modes = this.options.modes
    }, choosePlace: function (b, a, c, e, h) {
        this.chosenPlace = {id: b, placeType: a, location: c, activateMap: e, dontAddBusRoutesToChosenPlace: h};
        if (this.placesLayers) {
            d.each(this.placesLayers, this.proxy(function (j, f) {
                f.choosePlace(this.chosenPlace.id, this.chosenPlace.placeType, this.chosenPlace.location, e, h)
            }));
            this.setZoom(17)
        }
    },
        chooseBusRouteForStop: function (b, a) {
            if (this.busRouteLayer)this.addRouteForStop(b, a, false); else this.chosenBusRoute = {stopId: b, lineId: a, zoomToFit: false, direction: null}
        }, chooseBusRouteForLine: function (b, a) {
            if (this.busRouteLayer)this.addRouteForLine(b, a, true); else this.chosenBusRoute = {stopId: null, lineId: b, zoomToFit: true, direction: a}
        }, addRouteForStop: function (b, a, c) {
            this.stationStopLayer.dontAddBusRoutesToChosenPlace = true;
            this.busRouteLayer.addRouteForStop(b, a, c).then(this.proxy(function (e) {
                this.stationStopLayer.routeAdded(e)
            }))
        },
        addRouteForLine: function (b, a, c) {
            this.stationStopLayer.dontAddBusRoutesToChosenPlace = true;
            this.busRouteLayer.addRouteForLine(b, a, c).then(this.proxy(function (e) {
                this.stationStopLayer.routeAdded(e)
            }))
        }, addLayers: function () {
            var b = this.layerManager;
            this.busRouteLayer = b.addLayer(Tfl.Maps.Framework.BusRouteLayer, {layerOptions: {}});
            this.placesLayers = [];
            this.placesLayers.push(b.addLayer(Tfl.Maps.Framework.CycleHireLayer, {isOptional: false, layerOptions: {placeChosen: this.placeChosen, zoomThresholds: this.zoomThresholds},
                initialVisibility: true}));
            this.stationStopLayer = b.addLayer(Tfl.Maps.Framework.StationStopLayer, {isOptional: false, layerOptions: {stationStopsChosen: this.options.stationStopsChosen, modes: this.modes, zoomThresholds: this.zoomThresholds, enableShortCommonNames: this.enableShortCommonNames, busRouteLayer: this.busRouteLayer}});
            this.placesLayers.push(this.stationStopLayer);
            this.chooseInitiallyChosenPlace()
        }, chooseInitiallyChosenPlace: function () {
            if (this.chosenPlace) {
                this.choosePlace(this.chosenPlace.id, this.chosenPlace.placeType,
                    this.chosenPlace.location, this.chosenPlace.activateMap, this.chosenPlace.dontAddBusRoutesToChosenPlace);
                this.chosenPlace = null
            }
            if (this.chosenBusRoute) {
                this.addRoute(this.chosenBusRoute.stopId, this.chosenBusRoute.lineId, this.chosenBusRoute.zoomToFit, this.chosenBusRoute.direction);
                this.chosenBusRoute = null
            }
        }})
});
steal.loaded("nearby/nearby_map.js");
steal("framework/map/map.js", "framework/map/layers/places_layer.js", "framework/map/layers/station_stop_layer.js", "framework/map/layers/cycle_hire_layer.js", "framework/map/layers/geolocation_layer.js", "framework/map/layers/congestion_charging_zone_layer.js", "framework/map/layers/low_emission_zone_layer.js", "framework/map/layers/coach_ban_areas_layer.js", "framework/map/layers/gla_boundary_layer.js", "framework/map/layers/traffic_layer.js", "framework/map/layers/cycling_layer.js", "framework/map/layers/static_kml_layer.js",
    "framework/map/layers/coach_parking_layer.js", "road/layers/road_corridors_layer.js", function (d) {
        Tfl.Maps.Framework.Map("Tfl.Maps.Embedded.Map", {defaults: {channel: "embedded", initiallyDeactivated: true, layerMappings: {"congestion-charging-zone": {klass: Tfl.Maps.Framework.CongestionChargingZoneLayer}, "low-emissions-zone": {klass: Tfl.Maps.Framework.LowEmissionZoneLayer}, "gla-boundary": {klass: Tfl.Maps.Framework.GLABoundaryLayer}, "road-corridors": {klass: Tfl.Maps.Road.RoadCorridorsLayer}, "station-stop": {klass: Tfl.Maps.Framework.StationStopLayer,
            name: "Stations stops and piers"}, "cycle-hire": {klass: Tfl.Maps.Framework.CycleHireLayer, name: "Cycle docking stations", layerOptions: {smallPinsClickAble: true}}, "coach-ban-areas": {klass: Tfl.Maps.Framework.CoachBanAreasLayer, name: "Coach ban areas"}, "jam-cam": {klass: Tfl.Maps.Framework.PlacesLayer, name: "Jam Cams", iconPath: "//framework/map/layers/icons/JamCam.png", classSuffix: "jam-cam", layerOptions: {placeTypes: ["JamCam"], displayByZoom: 10}}, "red-light-cam": {klass: Tfl.Maps.Framework.PlacesLayer, name: "Red light cameras",
            iconPath: "framework/map/layers/icons/RedLightCam.png", classSuffix: "red-light-cam", layerOptions: {placeTypes: ["RedLightCam", "RedLightAndSpeedCam"], displayByZoom: 10}}, "speed-cam": {klass: Tfl.Maps.Framework.PlacesLayer, name: "Speed cameras", iconPath: "framework/map/layers/icons/SpeedCam.png", classSuffix: "speed-cam", layerOptions: {placeTypes: ["SpeedCam", "RedLightAndSpeedCam"], displayByZoom: 10}}, "variable-message-sign": {klass: Tfl.Maps.Framework.PlacesLayer, name: "Variable Message Signs", iconPath: "framework/map/layers/icons/VariableMessageSign.png",
            classSuffix: "variable-message-sign", layerOptions: {placeTypes: ["VariableMessageSign"], displayByZoom: 10}}, "coach-parking": {klass: Tfl.Maps.Framework.CoachParkingLayer, name: "Coach parking", iconPath: "framework/map/layers/icons/coachparking.png", layerOptions: {placeTypes: ["CoachPark", "OnStreetMeteredBay", "CoachBay", "OtherCoachParking"], displayByZoom: 10}}}}}, {init: function () {
            d.fixture.on = false;
            this.placeChosen = this.options.placeChosen;
            this.stationStopsChosen = this.options.stationStopsChosen;
            this.mapContainer =
                d("<div class='map map-container embedded-map-container'></div>");
            this.element.append(this.mapContainer);
            this.readSettings();
            this._super()
        }, readSettings: function () {
            var b = this.element.data();
            try {
                if (b.centrelat !== undefined && b.centrelng !== undefined)this.options.mapCentre = {lat: parseFloat(b.centrelat), lng: parseFloat(b.centrelng)};
                if (b.centrelat !== undefined && b.centrelon !== undefined)this.options.mapCentre = {lat: parseFloat(b.centrelat), lng: parseFloat(b.centrelon)}
            } catch (a) {
                dev.steal.log("Failed to read centre")
            }
            try {
                if (b.zoom !==
                    undefined)this.options.initialZoom = parseInt(b.zoom)
            } catch (c) {
                dev.steal.log("Failed to read zoom")
            }
            try {
                this.zoomThresholds = undefined;
                if (b.zoomthresholds !== undefined)this.zoomThresholds = b.zoomthresholds
            } catch (e) {
                dev.steal.log("Failed to parse zoom thresholds")
            }
        }, addLayers: function () {
            var b = this.element.data();
            if (b.layers !== undefined && b.layers !== "") {
                this.layerKeys = b.layers.split(",");
                if (b.layersvisibleatload !== undefined)this.optionalVisibleLayerNames = b.layersvisibleatload.split(",");
                d.each(this.layerKeys,
                    this.proxy(function (a, c) {
                        (a = this.options.layerMappings[c]) && this.addLayer(a, c)
                    }))
            }
            b.kmlurl !== undefined && b.kmlurl !== "" && this.layerManager.addLayer(Tfl.Maps.Framework.StaticKmlLayer, {layerOptions: {url: b.kmlurl}});
            b.showuserlocation !== undefined && b.showuserlocation === 1 && this.layerManager.addLayer(Tfl.Maps.Framework.GeolocationLayer, {});
            b.showsatellitemap !== undefined && b.showsatellitemap === 1 && this.styleManager.selectMapType(google.maps.MapTypeId.HYBRID)
        }, addLayer: function (b, a) {
            var c = b.layerOptions || {};
            d.extend(c, {placeChosen: this.placeChosen, zoomThresholds: this.zoomThresholds});
            a = {name: b.name, iconPath: b.iconPath, layerOptions: c, isOptional: true, initialVisibility: d.inArray(a, this.optionalVisibleLayerNames) != -1, stationStopsChosen: this.stationStopsChosen};
            if (b.classSuffix)a.classSuffix = b.classSuffix;
            this.layerManager.addLayer(b.klass, a)
        }})
    });
steal.loaded("embedded/embedded_map.js");
steal.loaded("jquery/jquery.js");
steal("./journeyplanner.css", "./models/models.js", "./fixtures/fixtures.js", "//framework/framework.js", "//framework/models/settings_helper.js", "./leg_map/leg_map", function () {
});
steal.loaded("journeyplanner/journeyplanner.js");
steal("./disambiguation_option.js", "./disambiguation.js", "jquery/model");
steal.loaded("disambiguation/models/models.js");
steal("jquery/dom/fixture", function () {
});
steal.loaded("disambiguation/fixtures/fixtures.js");
steal("./models/models.js", "./map/map", "./fixtures/fixtures.js", function () {
});
steal.loaded("framework/framework.js");
steal("jquery/model", function () {
    $.Model("Tfl.Maps.Framework.Models.SettingsHelper", {googleApiKey: "mykey", contentBaseUrl: null, isInTest: false, tileServerUrl: null, apiProxyRoot: null, apiQuerySuffix: "", init: function () {
        Tfl.Maps.RELEASE = "beta4.1";
        Tfl.Maps.GITCOMMITHASH = "GIT_COMMIT_HASH";
        Tfl.Maps.GITBRANCH = "GIT_BRANCH";
        Tfl.Maps.BUILDDATE = "BUILD_DATE";
        var d = $("script[src*='steal.production.js']").attr("src");
        this.contentBaseUrl = typeof navigator != "undefined" && navigator.userAgent && /BlackBerry/.test(navigator.userAgent) ?
            location.protocol + "//" + location.host + "/maps/" : d ? this.findBaseUrl(d) : this.findBaseUrl($("script[src*='steal.js']").attr("src"));
        if (steal.options.env === "development")this.inDevelopmentMode = true; else {
            this.inDevelopmentMode = false;
            this.apiProxyRoot = tfl.apiUrl + "/";
            this.apiQuerySuffix = "&app_id=" + tfl.appId + "&app_key=" + tfl.appKey;
            this.tileServerUrl = "/maptiles/"
        }
    }, findBaseUrl: function (d) {
        var b = d.indexOf("steal/steal.");
        if (b === 0) {
            d = window.location.href.lastIndexOf("/");
            return window.location.href.substr(0, d +
                1)
        } else return d.substr(0, b)
    }}, {})
});
steal.loaded("framework/models/settings_helper.js");
steal("framework/map/map.js", "./disambiguation_layer", function (d) {
    Tfl.Maps.Framework.Map("Tfl.Maps.DisambiguationMap", {defaults: {channel: "disambiguation", pageSize: 5, initiallyDeactivated: false}}, {init: function () {
        this.mapContainer = d("<div class='map map-container disambiguation-map-container'></div>");
        this.element.append(this.mapContainer);
        this._super()
    }, addLayers: function () {
        this.disambiguationLayer = this.layerManager.addLayer(Tfl.Maps.Disambiguation.DisambiguationLayer, {layerOptions: {pageSize: this.options.pageSize,
            optionsElement: this.options.optionsElement}})
    }, divResized: function () {
        this._super();
        this.disambiguationLayer.resetToInitialPosition()
    }, chooseOption: function (b) {
        this.disambiguationLayer.chooseOption(b)
    }, choosePage: function (b) {
        this.disambiguationLayer.choosePage(b)
    }})
});
steal.loaded("disambiguation/disambiguation_map/disambiguation_map.js");
steal("jquery/model", function () {
    $.Model("Tfl.Maps.Disambiguation.Models.DisambiguationOption", {}, {})
});
steal.loaded("disambiguation/models/disambiguation_option.js");
steal("jquery/model", function () {
    $.Model("Tfl.Maps.Disambiguation.Models.Disambiguation", {attributes: {disambiguationOptions: "Disambiguation.Models.DisambiguationOption.models"}, findOne: "/disambiguations/blah"}, {})
});
steal.loaded("disambiguation/models/disambiguation.js");
steal("jquery/class", "jquery/lang/string", function () {
    var d = $.String, b = d.getObject, a = d.underscore, c = d.classize, e = $.isArray, h = $.makeArray, j = $.extend, f = $.each, g = function (i, l, r) {
            $.event.trigger(l, r, i, true)
        }, k = function (i, l, r, v, p, q, o) {
            if (typeof i == "string") {
                var u = i.indexOf(" ");
                i = u > -1 ? {url: i.substr(u + 1), type: i.substr(0, u)} : {url: i}
            }
            i.data = typeof l == "object" && !e(l) ? j(i.data || {}, l) : l;
            i.url = d.sub(i.url, i.data, true);
            return $.ajax(j({type: q || "post", dataType: o || "json", fixture: p, success: r, error: v}, i))
        }, m = function (i, l, r) {
            var v = a(i.shortName), p = "-" + v + (l || "");
            return $.fixture && $.fixture[p] ? p : r || "//" + a(i.fullName).replace(/\.models\..*/, "").replace(/\./g, "/") + "/fixtures/" + v + (l || "") + ".json"
        }, t = function (i, l, r) {
            l = l || {};
            i = i.id;
            if (l[i] && l[i] !== r) {
                l["new" + d.capitalize(r)] = l[i];
                delete l[i]
            }
            l[i] = r;
            return l
        }, z = function (i) {
            return new (i || $.Model.List || Array)
        }, y = function (i) {
            return i[i.constructor.id]
        }, D = function (i) {
            var l = [];
            f(i, function (r, v) {
                if (!v["__u Nique"]) {
                    l.push(v);
                    v["__u Nique"] = 1
                }
            });
            return f(l, function (r, v) {
                delete v["__u Nique"]
            })
        },
        s = function (i, l, r, v, p) {
            var q = $.Deferred(), o = [i.serialize(), function (B) {
                i[p || l + "d"](B);
                q.resolveWith(i, [i, B, l])
            }, function (B) {
                q.rejectWith(i, [B])
            }], u = i.constructor, w, C = q.promise();
            l == "destroy" && o.shift();
            l !== "create" && o.unshift(y(i));
            q.then(r);
            q.fail(v);
            if ((w = u[l].apply(u, o)) && w.abort)C.abort = function () {
                w.abort()
            };
            return C
        }, A = function (i) {
            return typeof i === "object" && i !== null && i
        }, x = function (i) {
            return function () {
                return $.fn[i].apply($([this]), arguments)
            }
        }, n = x("bind");
    x = x("unbind");
    ajaxMethods = {create: function (i) {
        return function (l, r, v) {
            return k(i || this._shortName, l, r, v, m(this, "Create", "-restCreate"))
        }
    }, update: function (i) {
        return function (l, r, v, p) {
            return k(i || this._shortName + "/{" + this.id + "}", t(this, r, l), v, p, m(this, "Update", "-restUpdate"), "put")
        }
    }, destroy: function (i) {
        return function (l, r, v) {
            var p = {};
            p[this.id] = l;
            return k(i || this._shortName + "/{" + this.id + "}", p, r, v, m(this, "Destroy", "-restDestroy"), "delete")
        }
    }, findAll: function (i) {
        return function (l, r, v) {
            return k(i || this._shortName, l, r, v, m(this, "s"), "get", "json " + this._shortName + ".models")
        }
    },
        findOne: function (i) {
            return function (l, r, v) {
                return k(i || this._shortName + "/{" + this.id + "}", l, r, v, m(this), "get", "json " + this._shortName + ".model")
            }
        }};
    jQuery.Class("jQuery.Model", {setup: function (i) {
        var l = this, r = this.fullName;
        f(["attributes", "validations"], function (p, q) {
            if (!l[q] || i[q] === l[q])l[q] = {}
        });
        f(["convert", "serialize"], function (p, q) {
            if (i[q] != l[q])l[q] = j({}, i[q], l[q])
        });
        this._fullName = a(r.replace(/\./g, "_"));
        this._shortName = a(this.shortName);
        if (r.indexOf("jQuery") != 0) {
            if (this.listType)this.list = new this.listType([]);
            f(ajaxMethods, function (p, q) {
                var o = l[p];
                if (typeof o !== "function")l[p] = q(o)
            });
            r = {};
            var v = "* " + this._shortName + ".model";
            r[v + "s"] = this.proxy("models");
            r[v] = this.proxy("model");
            $.ajaxSetup({converters: r})
        }
    }, attributes: {}, model: function (i) {
        if (!i)return null;
        if (i instanceof this)i = i.serialize();
        return new this(A(i[this._shortName]) || A(i.data) || A(i.attributes) || i)
    }, models: function (i) {
        if (!i)return null;
        var l = z(this.List), r = e(i), v = $.Model.List;
        v = v && i instanceof v;
        for (var p = (v = r ? i : v ? i.serialize() : i.data) ? v.length :
            null, q = 0; q < p; q++)l.push(this.model(v[q]));
        r || f(i, function (o, u) {
            if (o !== "data")l[o] = u
        });
        return l
    }, id: "id", addAttr: function (i, l) {
        var r = this.attributes;
        r[i] || (r[i] = l);
        return l
    }, convert: {date: function (i) {
        var l = typeof i;
        return l === "string" ? isNaN(Date.parse(i)) ? null : Date.parse(i) : l === "number" ? new Date(i) : i
    }, number: function (i) {
        return parseFloat(i)
    }, "boolean": function (i) {
        return Boolean(i === "false" ? 0 : i)
    }, "default": function (i, l, r) {
        l = b(r);
        var v = window;
        if (r.indexOf(".") >= 0) {
            r = r.substring(0, r.lastIndexOf("."));
            v = b(r)
        }
        return typeof l == "function" ? l.call(v, i) : i
    }}, serialize: {"default": function (i) {
        return A(i) && i.serialize ? i.serialize() : i
    }, date: function (i) {
        return i && i.getTime()
    }}, bind: n, unbind: x, _ajax: k}, {setup: function (i) {
        this._init = true;
        this.attrs(j({}, this.constructor.defaults, i));
        delete this._init
    }, update: function (i, l, r) {
        this.attrs(i);
        return this.save(l, r)
    }, errors: function (i) {
        if (i)i = e(i) ? i : h(arguments);
        var l = {}, r = this, v = function (q, o) {
            f(o, function (u, w) {
                if (u = w.call(r)) {
                    l[q] || (l[q] = []);
                    l[q].push(u)
                }
            })
        }, p = this.constructor.validations;
        f(i || p || {}, function (q, o) {
            if (typeof q == "number") {
                q = o;
                o = p[q]
            }
            v(q, o || [])
        });
        return $.isEmptyObject(l) ? null : l
    }, attr: function (i, l, r, v) {
        var p = c(i), q = "get" + p;
        if (l !== undefined) {
            p = "set" + p;
            q = this[i];
            var o = this, u = function (w) {
                v && v.call(o, w);
                g(o, "error." + i, w)
            };
            if (this[p] && (l = this[p](l, this.proxy("_updateProperty", i, l, q, r, u), u)) === undefined)return;
            this._updateProperty(i, l, q, r, u);
            return this
        }
        return this[q] ? this[q]() : this[i]
    }, bind: n, unbind: x, _updateProperty: function (i, l, r, v, p) {
        var q = this.constructor, o = q.attributes[i] ||
            q.addAttr(i, "string"), u = q.convert[o] || q.convert["default"], w = null, C = "", B = "updated.";
        v = v;
        var K = q.list;
        l = this[i] = l === null ? null : u.call(q, l, function () {
        }, o);
        this._init || (w = this.errors(i));
        o = [l];
        u = [i, l, r];
        if (w) {
            C = B = "error.";
            v = p;
            u.splice(1, 0, w);
            o.unshift(w)
        }
        if (r !== l && !this._init) {
            !w && g(this, C + i, o);
            g(this, B + "attr", u)
        }
        v && v.apply(this, o);
        if (i === q.id && l !== null && K)if (r) {
            if (r != l) {
                K.remove(r);
                K.push(this)
            }
        } else K.push(this)
    }, removeAttr: function (i) {
        var l = this[i], r = false, v = this.constructor.attributes;
        this[i] && delete this[i];
        if (v[i]) {
            delete v[i];
            r = true
        }
        !this._init && r && l && g(this, "updated.attr", [i, null, l])
    }, attrs: function (i) {
        var l, r = this.constructor, v = r.attributes;
        if (i) {
            r = r.id;
            for (l in i)l != r && this.attr(l, i[l]);
            r in i && this.attr(r, i[r])
        } else {
            i = {};
            for (l in v)if (v.hasOwnProperty(l))i[l] = this.attr(l)
        }
        return i
    }, serialize: function () {
        var i = this.constructor, l = i.attributes, r, v, p = {}, q;
        attributes = {};
        for (q in l)if (l.hasOwnProperty(q)) {
            r = l[q];
            v = i.serialize[r] || i.serialize["default"];
            p[q] = v.call(i, this[q], r)
        }
        return p
    }, isNew: function () {
        var i =
            y(this);
        return i === undefined || i === null || i === ""
    }, save: function (i, l) {
        return s(this, this.isNew() ? "create" : "update", i, l)
    }, destroy: function (i, l) {
        return s(this, "destroy", i, l, "destroyed")
    }, identity: function () {
        var i = y(this), l = this.constructor;
        return(l._fullName + "_" + (l.escapeIdentity ? encodeURIComponent(i) : i)).replace(/ /g, "_")
    }, elements: function (i) {
        var l = this.identity();
        if (this.constructor.escapeIdentity)l = l.replace(/([ #;&,.+*~\'%:"!^$[\]()=>|\/])/g, "\\$1");
        return $("." + l, i)
    }, hookup: function (i) {
        var l = this.constructor._shortName,
            r = $.data(i, "models") || $.data(i, "models", {});
        $(i).addClass(l + " " + this.identity());
        r[l] = this
    }});
    f(["created", "updated", "destroyed"], function (i, l) {
        $.Model.prototype[l] = function (r) {
            var v = this.constructor;
            l === "destroyed" && v.list && v.list.remove(y(this));
            r && typeof r == "object" && this.attrs(r.attrs ? r.attrs() : r);
            g(this, l);
            g(v, l, this);
            return[this].concat(h(arguments))
        }
    });
    $.fn.models = function () {
        var i = [], l, r;
        this.each(function () {
            f($.data(this, "models") || {}, function (v, p) {
                l = l === undefined ? p.constructor.List || null :
                        p.constructor.List === l ? l : null;
                i.push(p)
            })
        });
        r = z(l);
        r.push.apply(r, D(i));
        return r
    };
    $.fn.model = function (i) {
        if (i && i instanceof $.Model) {
            i.hookup(this[0]);
            return this
        } else return this.models.apply(this, arguments)[0]
    }
});
steal.loaded("jquery/model/model.js");
steal("jquery", "jquery/lang/string", function (d) {
    var b = false, a = d.makeArray, c = d.isFunction, e = d.isArray, h = d.extend, j = d.String.getObject, f = function (m, t) {
        return m.concat(a(t))
    }, g = /xyz/.test(function () {
    }) ? /\b_super\b/ : /.*/, k = function (m, t, z) {
        z = z || m;
        for (var y in m)z[y] = c(m[y]) && c(t[y]) && g.test(m[y]) ? function (D, s) {
            return function () {
                var A = this._super, x;
                this._super = t[D];
                x = s.apply(this, arguments);
                this._super = A;
                return x
            }
        }(y, m[y]) : m[y]
    };
    clss = d.Class = function () {
        arguments.length && clss.extend.apply(clss, arguments)
    };
    h(clss, {proxy: function (m) {
        var t = a(arguments), z;
        m = t.shift();
        e(m) || (m = [m]);
        z = this;
        return function () {
            for (var y = f(t, arguments), D, s = m.length, A = 0, x; A < s; A++)if (x = m[A]) {
                if ((D = typeof x == "string") && z._set_called)z.called = x;
                y = (D ? z[x] : x).apply(z, y || []);
                if (A < s - 1)y = !e(y) || y._use_call ? [y] : y
            }
            return y
        }
    }, newInstance: function () {
        var m = this.rawInstance(), t;
        if (m.setup)t = m.setup.apply(m, arguments);
        if (m.init)m.init.apply(m, e(t) ? t : arguments);
        return m
    }, setup: function (m) {
        this.defaults = h(true, {}, m.defaults, this.defaults);
        return arguments
    },
        rawInstance: function () {
            b = true;
            var m = new this;
            b = false;
            return m
        }, extend: function (m, t, z) {
            function y() {
                if (!b)return this.constructor !== y && arguments.length ? arguments.callee.extend.apply(arguments.callee, arguments) : this.Class.newInstance.apply(this.Class, arguments)
            }

            if (typeof m != "string") {
                z = t;
                t = m;
                m = null
            }
            if (!z) {
                z = t;
                t = null
            }
            z = z || {};
            var D = this, s = this.prototype, A, x, n, i;
            b = true;
            i = new this;
            b = false;
            k(z, s, i);
            for (A in this)if (this.hasOwnProperty(A))y[A] = this[A];
            k(t, this, y);
            if (m) {
                n = m.split(/\./);
                x = n.pop();
                n = s = j(n.join("."),
                    window, true);
                s[x] = y
            }
            h(y, {prototype: i, namespace: n, shortName: x, constructor: y, fullName: m});
            y.prototype.Class = y.prototype.constructor = y;
            x = y.setup.apply(y, f([D], arguments));
            if (y.init)y.init.apply(y, x || f([D], arguments));
            return y
        }});
    clss.callback = clss.prototype.callback = clss.prototype.proxy = clss.proxy
})();
steal.loaded("jquery/class/class.js");
steal("jquery").then(function (d) {
    var b = {undHash: /_|-/, colons: /::/, words: /([A-Z]+)([A-Z][a-z])/g, lowUp: /([a-z\d])([A-Z])/g, dash: /([a-z\d])([A-Z])/g, replacer: /\{([^\}]+)\}/g, dot: /\./}, a = function (j, f, g) {
        return j[f] !== undefined ? j[f] : g && (j[f] = {})
    }, c = function (j) {
        var f = typeof j;
        return j && (f == "function" || f == "object")
    }, e, h = d.String = d.extend(d.String || {}, {getObject: e = function (j, f, g) {
        j = j ? j.split(b.dot) : [];
        var k = j.length, m, t, z, y = 0;
        f = d.isArray(f) ? f : [f || window];
        if (k == 0)return f[0];
        for (; m = f[y++];) {
            for (z = 0; z < k - 1 &&
                c(m); z++)m = a(m, j[z], g);
            if (c(m)) {
                t = a(m, j[z], g);
                if (t !== undefined) {
                    g === false && delete m[j[z]];
                    return t
                }
            }
        }
    }, capitalize: function (j) {
        return j.charAt(0).toUpperCase() + j.substr(1)
    }, camelize: function (j) {
        j = h.classize(j);
        return j.charAt(0).toLowerCase() + j.substr(1)
    }, classize: function (j, f) {
        j = j.split(b.undHash);
        for (var g = 0; g < j.length; g++)j[g] = h.capitalize(j[g]);
        return j.join(f || "")
    }, niceName: function (j) {
        return h.classize(j, " ")
    }, underscore: function (j) {
        return j.replace(b.colons, "/").replace(b.words, "$1_$2").replace(b.lowUp,
            "$1_$2").replace(b.dash, "_").toLowerCase()
    }, sub: function (j, f, g) {
        var k = [];
        g = typeof g == "boolean" ? !g : g;
        k.push(j.replace(b.replacer, function (m, t) {
            m = e(t, f, g);
            if (c(m)) {
                k.push(m);
                return""
            } else return"" + m
        }));
        return k.length <= 1 ? k[0] : k
    }, _regs: b})
});
steal.loaded("jquery/lang/string/string.js");
steal("jquery/dom", "jquery/lang/object", "jquery/lang/string", function (d) {
    d.ajaxPrefilter(function (f, g) {
        if (d.fixture.on) {
            var k = c(f);
            if (f.fixture) {
                if (typeof f.fixture === "string" && d.fixture[f.fixture])f.fixture = d.fixture[f.fixture];
                if (typeof f.fixture == "string") {
                    g = f.fixture;
                    if (/^\/\//.test(g))g = steal.root.mapJoin(f.fixture.substr(2)) + "";
                    f.url = g;
                    f.data = null;
                    f.type = "GET";
                    if (!f.error)f.error = function (m, t, z) {
                        throw"fixtures.js Error " + t + " " + z;
                    }
                } else {
                    f.dataTypes.splice(0, 0, "fixture");
                    k && d.extend(g.data, k)
                }
            }
        }
    });
    d.ajaxTransport("fixture", function (f, g) {
        f.dataTypes.shift();
        var k = f.dataTypes[0], m;
        return{send: function (t, z) {
            m = setTimeout(function () {
                var y = f.fixture(g, f, t);
                if (!d.isArray(y)) {
                    var D = [
                        {}
                    ];
                    D[0][k] = y;
                    y = D
                }
                typeof y[0] != "number" && y.unshift(200, "success");
                if (!y[2] || !y[2][k]) {
                    D = {};
                    D[k] = y[2];
                    y[2] = D
                }
                z.apply(null, y)
            }, d.fixture.delay)
        }, abort: function () {
            clearTimeout(m)
        }}
    });
    var b = [], a = function (f, g) {
        for (var k = 0; k < b.length; k++)if (h._similar(f, b[k], g))return k;
        return-1
    }, c = function (f) {
        var g = a(f);
        if (g > -1) {
            f.fixture = b[g].fixture;
            return h._getData(b[g].url, f.url)
        }
    }, e = function (f) {
        var g = f.data.id;
        if (g === undefined && typeof f.data === "number")g = f.data;
        g === undefined && f.url.replace(/\/(\d+)(\/|$|\.)/g, function (k, m) {
            g = m
        });
        if (g === undefined)g = f.url.replace(/\/(\w+)(\/|$|\.)/g, function (k, m) {
            if (m != "update")g = m
        });
        if (g === undefined)g = Math.round(Math.random() * 1E3);
        return g
    }, h = d.fixture = function (f, g) {
        if (g !== undefined) {
            if (typeof f == "string") {
                var k = f.match(/(GET|POST|PUT|DELETE) (.+)/i);
                f = k ? {url: k[2], type: k[1]} : {url: f}
            }
            k = a(f, !!g);
            k > -1 && b.splice(k,
                1);
            if (g != null) {
                f.fixture = g;
                b.push(f)
            }
        }
    }, j = d.String._regs.replacer;
    d.extend(d.fixture, {_similar: function (f, g, k) {
        return k ? d.Object.same(f, g, {fixture: null}) : d.Object.subset(f, g, d.fixture._compare)
    }, _compare: {url: function (f, g) {
        return!!h._getData(g, f)
    }, fixture: null, type: "i"}, _getData: function (f, g) {
        var k = [];
        f = f.replace(".", "\\.").replace("?", "\\?");
        var m = (new RegExp(f.replace(j, function (z, y) {
            k.push(y);
            return"([^/]+)"
        }) + "$")).exec(g), t = {};
        if (!m)return null;
        m.shift();
        d.each(k, function (z, y) {
            t[y] = m.shift()
        });
        return t
    }, "-restUpdate": function (f) {
        return[200, "succes", {id: e(f)}, {location: f.url + "/" + e(f)}]
    }, "-restDestroy": function () {
        return{}
    }, "-restCreate": function (f, g, k, m) {
        m = m || parseInt(Math.random() * 1E5, 10);
        return[200, "succes", {id: m}, {location: f.url + "/" + m}]
    }, make: function (f, g, k, m) {
        if (typeof f === "string")f = [f + "s", f];
        for (var t = d.fixture["~" + f[0]] = [], z = function (s) {
            for (var A = 0; A < t.length; A++)if (s == t[A].id)return t[A]
        }, y = 0; y < g; y++) {
            var D = k(y, t);
            if (!D.id)D.id = y;
            t.push(D)
        }
        d.fixture["-" + f[0]] = function (s) {
            var A = t.slice(0);
            s.data = s.data || {};
            d.each((s.data.order || []).slice(0).reverse(), function (r, v) {
                var p = v.split(" ");
                A = A.sort(function (q, o) {
                    return p[1].toUpperCase() !== "ASC" ? q[p[0]] < o[p[0]] ? 1 : q[p[0]] == o[p[0]] ? 0 : -1 : q[p[0]] < o[p[0]] ? -1 : q[p[0]] == o[p[0]] ? 0 : 1
                })
            });
            d.each((s.data.group || []).slice(0).reverse(), function (r, v) {
                var p = v.split(" ");
                A = A.sort(function (q, o) {
                    return q[p[0]] > o[p[0]]
                })
            });
            var x = parseInt(s.data.offset, 10) || 0, n = parseInt(s.data.limit, 10) || t.length - x, i = 0;
            for (var l in s.data) {
                i = 0;
                if (s.data[l] !== undefined && (l.indexOf("Id") != -1 || l.indexOf("_id") != -1))for (; i < A.length;)if (s.data[l] != A[i][l])A.splice(i, 1); else i++
            }
            if (m)for (i = 0; i < A.length;)if (m(A[i], s))i++; else A.splice(i, 1);
            return[
                {count: A.length, limit: s.data.limit, offset: s.data.offset, data: A.slice(x, x + n)}
            ]
        };
        d.fixture["-" + f[1]] = function (s) {
            return(s = z(e(s))) ? [s] : []
        };
        d.fixture["-" + f[1] + "Update"] = function (s, A) {
            var x = e(s);
            d.extend(z(x), s.data);
            return d.fixture["-restUpdate"](s, A)
        };
        d.fixture["-" + f[1] + "Destroy"] = function (s, A) {
            for (var x = e(s), n = 0; n < t.length; n++)if (t[n].id == x) {
                t.splice(n,
                    1);
                break
            }
            d.extend(z(x), s.data);
            return d.fixture["-restDestroy"](s, A)
        };
        d.fixture["-" + f[1] + "Create"] = function (s, A) {
            var x = k(t.length, t);
            d.extend(x, s.data);
            if (!x.id)x.id = t.length;
            t.push(x);
            return d.fixture["-restCreate"](s, A, undefined, x.id)
        };
        return{getId: e, findOne: z, find: function (s) {
            return z(e(s))
        }}
    }, rand: function (f, g, k) {
        if (typeof f == "number")return typeof g == "number" ? f + Math.floor(Math.random() * (g - f)) : Math.floor(Math.random() * f);
        var m = arguments.callee;
        if (g === undefined)return m(f, m(f.length + 1));
        var t =
            [];
        f = f.slice(0);
        k || (k = g);
        k = g + Math.round(m(k - g));
        for (var z = 0; z < k; z++)t.push(f.splice(m(f.length), 1)[0]);
        return t
    }, xhr: function (f) {
        return d.extend({}, {abort: d.noop, getAllResponseHeaders: function () {
            return""
        }, getResponseHeader: function () {
            return""
        }, open: d.noop, overrideMimeType: d.noop, readyState: 4, responseText: "", responseXML: null, send: d.noop, setRequestHeader: d.noop, status: 200, statusText: "OK"}, f)
    }, on: true});
    d.fixture.delay = 200;
    d.fixture["-handleFunction"] = function (f) {
        if (typeof f.fixture === "string" && d.fixture[f.fixture])f.fixture =
            d.fixture[f.fixture];
        if (typeof f.fixture == "function") {
            setTimeout(function () {
                f.success && f.success.apply(null, f.fixture(f, "success"));
                f.complete && f.complete.apply(null, f.fixture(f, "complete"))
            }, d.fixture.delay);
            return true
        }
        return false
    };
    d.fixture.overwrites = b
});
steal.loaded("jquery/dom/fixture/fixture.js");
steal("jquery");
steal.loaded("jquery/dom/dom.js");
steal("jquery", function (d) {
    var b = d.isArray, a = function (h) {
        var j = 0;
        for (var f in h)j++;
        return j
    };
    d.Object = {};
    var c = d.Object.same = function (h, j, f, g, k, m) {
        var t = typeof h, z = b(h), y = typeof f;
        if (y == "string" || f === null) {
            f = e[f];
            y = "function"
        }
        if (y == "function")return f(h, j, g, k);
        f = f || {};
        if (m === -1)return t === "object" || h === j;
        if (t !== typeof j || z !== b(j))return false;
        if (h === j)return true;
        if (z) {
            if (h.length !== j.length)return false;
            for (m = 0; m < h.length; m++) {
                g = f[m] === undefined ? f["*"] : f[m];
                if (!c(h[m], j[m], h, j, g))return false
            }
            return true
        } else if (t ===
            "object" || t === "function") {
            k = d.extend({}, j);
            for (var D in h) {
                g = f[D] === undefined ? f["*"] : f[D];
                if (!c(h[D], j[D], g, h, j, m === false ? -1 : undefined))return false;
                delete k[D]
            }
            for (D in k)if (f[D] === undefined || !c(undefined, j[D], f[D], h, j, m === false ? -1 : undefined))return false;
            return true
        }
        return false
    };
    d.Object.subsets = function (h, j, f) {
        var g = j.length, k = [];
        a(h);
        for (var m = 0; m < g; m++) {
            var t = j[m];
            d.Object.subset(h, t, f) && k.push(t)
        }
        return k
    };
    d.Object.subset = function (h, j, f) {
        f = f || {};
        for (var g in j)if (!c(h[g], j[g], f[g], h, j))return false;
        return true
    };
    var e = {"null": function () {
        return true
    }, i: function (h, j) {
        return("" + h).toLowerCase() == ("" + j).toLowerCase()
    }}
});
steal.loaded("jquery/lang/object/object.js");
steal("./settings_helper.js", "jquery/model");
steal.loaded("framework/models/models.js");
steal("./css/map_reset.css").then("jquery/controller", "jquery/lang/openajax", "framework/map/map_utils.js", "./css/map.css", "./google_script_loader", "./layers/layer_manager.js", "./layers/userlocation_layer.js", "./options/map_options_manager.js", "./controls/custom_controls_manager.js", "./style_manager.js", "./full_window_manager.js").then("lib/jquery.ie.cors.js", function (d) {
    var b = Tfl.Maps.Framework.MapUtils;
    d.Controller("Tfl.Maps.Framework.Map", {defaults: {channel: "none", initiallyDeactivated: true, maxZoom: 19,
        initialZoom: 11, mapCentre: {lat: 51.507, lng: -0.128}, fallbackMapCentre: {lat: 51.507, lng: -0.128}, panLimit: {sw: {lat: 51.1, lng: -0.6}, ne: {lat: 51.9, lng: 0.4}}, nationalPanLimit: {sw: {lat: 50, lng: -10.5}, ne: {lat: 61, lng: 2.1}}, isNationalBounds: false, isAllowFullWindow: false, geolocationAccuracyThreshold: 8E3, automaticallyRemoveMapLoadingOverlay: true}, nextMapId: 0, getNextMapId: function () {
        return this.nextMapId++
    }}, {init: function () {
        this.scriptLoading = d.Deferred();
        this.mapLoading = d.Deferred();
        this.mapId = Tfl.Maps.Framework.Map.getNextMapId();
        this.firstActivateDone = this.isMapReady = false;
        this.element.addClass("map-reset");
        if (!this.mapContainer) {
            this.mapContainer = d("<div class='map map-container'></div>");
            d(this.element).append(this.mapContainer)
        }
        this.mapElement = d("<div class='map'></div>");
        this.mapContainer.append(this.mapElement);
        this.addMapLoadingOverlay();
        Tfl.Maps.Framework.GoogleScriptLoader.loadGoogleScript(this.options.channel, this.proxy("mapScriptLoaded"));
        b.sanitizeLatLng(this.options.mapCentre)
    }, mapScriptLoaded: function () {
        google.maps.visualRefresh =
            true;
        this.scriptLoading.resolve();
        this.allowedBounds = b.getGoogleBounds(this.options.isNationalBounds ? this.options.nationalPanLimit : this.options.panLimit);
        this.initialCenter = new google.maps.LatLng(this.options.mapCentre.lat, this.options.mapCentre.lng);
        if (!this.allowedBounds.contains(this.initialCenter))this.initialCenter = new google.maps.LatLng(this.options.fallbackMapCentre.lat, this.options.fallbackMapCentre.lng);
        this.lastValidCenter = this.initialCenter;
        this.options.minZoom = this.options.minZoom || this.getMinZoomFromMapWidth();
        this.googleMap = new google.maps.Map(this.mapElement[0], {zoom: this.options.initialZoom, center: this.lastValidCenter, panControl: false, streetViewControl: false, zoomControl: false, mapTypeControl: false, scrollwheel: false, minZoom: this.options.minZoom, maxZoom: this.options.maxZoom, disableDefaultUI: false, noClear: true});
        this.styleManager = new Tfl.Maps.Framework.StyleManager(this);
        this.rightTopCustomControlManager = new Tfl.Maps.Framework.CustomControlsManager(this, google.maps.ControlPosition.RIGHT_TOP);
        this.rightBottomCustomControlManager =
            new Tfl.Maps.Framework.CustomControlsManager(this, google.maps.ControlPosition.RIGHT_BOTTOM);
        this.deactivateButton = d("<div class='map-collapse-button'></div>");
        this.deactivateButton.click(this.proxy(this.deactivateMap));
        this.rightTopCustomControlManager.addControl(this.deactivateButton, true);
        this.optionsManager = new Tfl.Maps.Framework.MapOptionsManager(this);
        if (this.options.isAllowFullWindow)this.fullWindowManager = new Tfl.Maps.Framework.FullWindowManager(this);
        this.layerManager = new Tfl.Maps.Framework.LayerManager(this);
        this.userLocationLayer = this.layerManager.addLayer(Tfl.Maps.Framework.UserLocationLayer, {});
        this.addLayers && this.addLayers();
        this.changeBounds(this.boundsChanged);
        this.endDrag(this.dragEnd);
        this.changeZoom(this.zoomChanged);
        this.changeCenter(this.centerChanged);
        this.clickMap(function () {
            d(this.element.focus())
        });
        if (this.options.mapContainerElement)this.mapContainer = d(this.options.mapContainerElement); else {
            this.mapContainer = d(this.element);
            this.mapContainer.attr("tabindex", 1)
        }
        this.mapContainer.keyup(this.proxy(this.keyUp));
        this.updateDivSize();
        this.updateBounds();
        d(window).resize(this.proxy(this.windowResized));
        this.options.automaticallyRemoveMapLoadingOverlay && this.removeMapLoadingOverlay();
        setTimeout(this.proxy(this.addKeyboardAccessibleOverlays), 1E3);
        this.addDevelopmentModeLabel(this.mapElement);
        this.addStreetViewVisbilityChangedEvent()
    }, positionMap: function (a, c) {
        b.sanitizeLatLng(a);
        if (this.isMapReady) {
            this.activateIfDeactivated();
            this.displayMapByCenter(a);
            this.setZoom(c)
        } else this.mapLoading.done(this.proxy(function () {
            this.positionMap(a,
                c)
        }))
    }, displayMapByBoundingBox: function (a) {
        b.sanitizeLatLng(a.sw);
        b.sanitizeLatLng(a.ne);
        var c = new google.maps.LatLng(a.sw.lat, a.sw.lng);
        a = new google.maps.LatLng(a.ne.lat, a.ne.lng);
        this.googleMap.fitBounds(new google.maps.LatLngBounds(c, a))
    }, displayMapByCenter: function (a) {
        b.sanitizeLatLng(a);
        this.googleMap.setCenter(new google.maps.LatLng(a.lat, a.lng))
    }, displayMapByPositions: function (a, c) {
        if (a && a.length != 0) {
            b.sanitizeLatLng(a[0]);
            var e = new google.maps.LatLng(a[0].lat, a[0].lng), h = new google.maps.LatLngBounds(e,
                e);
            for (e = 1; e < a.length; e++) {
                b.sanitizeLatLng(a[e]);
                h = h.extend(new google.maps.LatLng(a[e].lat, a[e].lng))
            }
            this.googleMap.fitBounds(h);
            if (c)this.reApplySelectedBounds = this.proxy(function () {
                this.googleMap.fitBounds(h)
            })
        }
    }, resetToInitialPositionAndZoom: function () {
        this.displayMapByCenter(this.options.mapCentre);
        this.setZoom(this.options.initialZoom)
    }, getZoom: function () {
        return this.googleMap.getZoom()
    }, setZoom: function (a) {
        this.googleMap.setZoom(a)
    }, divResized: function () {
        this.optionsManager.closeOptionsDialogIfOpen();
        this.updateDivSize();
        var a = this.lastValidCenter;
        google.maps.event.trigger(this.googleMap, "resize");
        this.googleMap.setCenter(a)
    }, removeMapLoadingOverlay: function () {
        if (this.overlayElement) {
            this.overlayElement.remove();
            this.overlayElement = null
        }
        if (this.options.initiallyDeactivated) {
            this.hideMapControls();
            this.addMapDeactivatedOverlay()
        } else setTimeout(this.proxy(this.showMapControls()), 1E3)
    }, getBounds: function () {
        return this.isMapReady ? b.getOurBoundsFromGoogleBounds(this.googleMap.getBounds()) : {sw: {lat: 51.267071,
            lng: -0.503998}, ne: {lat: 51.692139, lng: 0.266418}}
    }, getCenter: function () {
        return b.getOurLatLngFromGoogleLatLng(this.googleMap.getCenter())
    }, activateIfDeactivated: function () {
        this.mapDeactivatedOverlay && this.activateMap(false)
    }, showUserLocation: function (a) {
        this.userLocationLayer.showUserLocation(a)
    }, hideUserLocation: function () {
        this.userLocationLayer.hideUserLocation()
    }, changeBounds: function (a) {
        this.registerListener("bounds_changed", a)
    }, endDrag: function (a) {
        this.registerListener("dragend", a)
    }, changeZoom: function (a) {
        this.registerListener("zoom_changed",
            a)
    }, changeCenter: function (a) {
        this.registerListener("center_changed", a)
    }, changeType: function (a) {
        this.registerListener("maptypeid_changed", a)
    }, clickMap: function (a) {
        this.registerListener("click", a)
    }, idle: function (a) {
        this.registerListener("idle", a)
    }, hideStreetView: function () {
        var a = this.googleMap.getStreetView();
        a && a.setVisible(false)
    }, setHeight: function (a) {
        var c = this.getCenter();
        this.setHeightInternal(a);
        this.triggerResize();
        this.displayMapByCenter(c);
        this.updateDivSize()
    }, triggerResize: function () {
        google.maps.event.trigger(this.googleMap,
            "resize")
    }, keyUp: function () {
        switch (event.which) {
            case 37:
                this.googleMap.panBy(-64, 0);
                break;
            case 38:
                this.googleMap.panBy(0, -64);
                break;
            case 39:
                this.googleMap.panBy(64, 0);
                break;
            case 40:
                this.googleMap.panBy(0, 64);
                break;
            case 109:
            case 189:
                this.googleMap.setZoom(this.googleMap.getZoom() - 1);
                break;
            case 107:
            case 187:
                this.googleMap.setZoom(this.googleMap.getZoom() + 1);
                break
        }
    }, mapReady: function () {
        this.isMapReady = true;
        this.mapLoading.resolve();
        OpenAjax.hub.publish("tfl.map." + this.mapId + ".ready")
    }, clicked: function () {
    },
        boundsChanged: function () {
            this.isMapReady || this.mapReady();
            this.updateBounds()
        }, dragEnd: function () {
        }, zoomChanged: function () {
        }, centerChanged: function () {
            if (this.isWithinAllowedBounds())this.lastValidCenter = this.googleMap.getCenter(); else this.panToLastValidCenter()
        }, streetViewVisibilityChanged: function (a) {
            if (a) {
                if (!this.streetViewCloseLink) {
                    var c = this;
                    this.streetViewCloseLink = d("<div></div>", {"class": "map-close-streetview-button-container", html: "<div class='map-close-streetview-button'></div>", click: function () {
                        c.googleMap.getStreetView().setVisible(false)
                    }}).appendTo(this.mapElement)
                }
            } else if (this.streetViewCloseLink) {
                this.streetViewCloseLink.remove();
                this.streetViewCloseLink = null
            }
        }, windowResized: function () {
            var a = this.element.width(), c = this.element.height();
            if (a !== this.divSize.width || c !== this.divSize.height)this.divResized()
        }, addMapLoadingOverlay: function () {
            if (d(this.mapElement).is(":visible")) {
                var a = this.element, c = a.height(), e = d("<div class='map-loading-overlay' style='top:-" + c + "px;'></div>");
                e.width(a.width());
                e.height(c);
                a.append(e);
                this.overlayElement = e
            }
        }, deactivateMap: function () {
            this.options.mapDeactivated && this.options.mapDeactivated();
            this.hideMapControls();
            this.addMapDeactivatedOverlay()
        }, addMapDeactivatedOverlay: function () {
            this.mapDeactivatedOverlay = d("<div class='map-collapsed-overlay'><div class='map-expand-button'><div class='map-expand-button-icon'></div></div></div>").prependTo(this.element);
            this.mapDeactivatedOverlay.click(this.proxy(function () {
                this.activateMap(true)
            }))
        }, hideMapControls: function () {
            this.googleMap.setOptions({panControl: false, streetViewControl: false, zoomControl: false});
            this.rightTopCustomControlManager.hide();
            this.rightBottomCustomControlManager.hide()
        },
        activateMap: function () {
            this.options.mapActivated && this.options.mapActivated();
            if (this.mapDeactivatedOverlay) {
                this.mapDeactivatedOverlay.remove();
                this.mapDeactivatedOverlay = null
            }
            this.showMapControls();
            if (!this.firstActivateDone) {
                this.firstActivateDone = true;
                this.reApplySelectedBounds && this.reApplySelectedBounds()
            }
        }, showMapControls: function () {
            this.googleMap.setOptions({panControl: true, streetViewControl: true, zoomControl: true});
            this.rightTopCustomControlManager.show();
            this.rightBottomCustomControlManager.show()
        },
        setHeightInternal: function (a) {
            this.mapElement.height(a);
            this.mapContainer.height(a);
            this.element.height(a)
        }, addKeyboardAccessibleOverlays: function () {
            var a = d(this.mapElement);
            this.addKeyboardAccessibleOverlay(a.find("[title='Show street map']"));
            this.addKeyboardAccessibleOverlay(a.find("[title='Show satellite imagery']"))
        }, addKeyboardAccessibleOverlay: function (a) {
            a.append("<button type='button' style='                 width:100%;height:100%;padding:2px;margin:2px;                 background:transparent ;border-width:0px;border-style:solid;                 cursor: pointer;overflow:hidden ;text-indent:-100em;                 position:absolute ;top:-2px;left:-2px;' value='" +
                a.attr("title") + "'></button>")
        }, panToLastValidCenter: function () {
            this.googleMap.panTo(this.lastValidCenter)
        }, isWithinAllowedBounds: function () {
            return this.allowedBounds.contains(this.googleMap.getCenter())
        }, getMinZoomFromMapWidth: function () {
            var a = this.getBoundsZoomLevel(this.allowedBounds, this.element.width(), this.element.height());
            return Math.max(0, a)
        }, updateDivSize: function () {
            this.divSize = {width: this.element.width(), height: this.element.height()}
        }, updateBounds: function () {
            this.lastValidBounds = this.googleMap.getBounds()
        },
        getBoundsZoomLevel: function (a, c, e) {
            var h = a.getNorthEast(), j = a.getSouthWest();
            a = h.lat() - j.lat();
            if (a < 0)a += 360;
            h = h.lng() - j.lng();
            e = Math.floor(Math.log(e * 360 / a / 256) / Math.LN2);
            c = Math.floor(Math.log(c * 360 / h / 256) / Math.LN2);
            return e < c ? e : c
        }, registerListener: function (a, c) {
            google.maps.event.addListener(this.googleMap, a, this.proxy(c))
        }, addDevelopmentModeLabel: function (a) {
            Tfl.Maps.Framework.Models.SettingsHelper.inDevelopmentMode && d("<div></div>", {text: "In development mode", css: {padding: "3px", "background-color": "#ffffff",
                color: "#ff0000", "-moz-opacity": "0.60", opacity: "0.60", "font-size": "11px", "z-index": "1000001", position: "absolute", top: "0", left: "0"}}).hide().appendTo(a).fadeIn()
        }, addStreetViewVisbilityChangedEvent: function () {
            var a = this, c = this.googleMap.getStreetView();
            google.maps.event.addListener(c, "visible_changed", function () {
                a.streetViewVisibilityChanged(c.getVisible())
            })
        }})
});
steal.loaded("framework/map/map.js");
steal("jquery/dom/fixture", function () {
});
steal.loaded("framework/fixtures/fixtures.js");
steal("jquery/class", "jquery/lang/string", "jquery/event/destroyed", function (d) {
    var b = function (p, q, o) {
        var u, w = p.bind && p.unbind ? p : d(e(p) ? [p] : p);
        if (q.indexOf(">") === 0) {
            q = q.substr(1);
            u = function (C) {
                C.target === p && o.apply(this, arguments)
            }
        }
        w.bind(q, u || o);
        return function () {
            w.unbind(q, u || o);
            p = q = o = u = null
        }
    }, a = d.makeArray, c = d.isArray, e = d.isFunction, h = d.extend, j = d.String, f = d.each, g = Array.prototype.slice, k = function (p, q, o, u) {
        var w = p.delegate && p.undelegate ? p : d(e(p) ? [p] : p);
        w.delegate(q, o, u);
        return function () {
            w.undelegate(q,
                o, u);
            w = p = o = u = q = null
        }
    }, m = function (p, q, o, u) {
        return u ? k(p, u, q, o) : b(p, q, o)
    }, t = function (p, q) {
        var o = typeof q == "string" ? p[q] : q;
        return function () {
            p.called = q;
            return o.apply(p, [this.nodeName ? d(this) : this].concat(g.call(arguments, 0)))
        }
    }, z = /\./g, y = /_?controllers?/ig, D = function (p) {
        return j.underscore(p.replace("jQuery.", "").replace(z, "_").replace(y, ""))
    }, s = /[^\w]/, A = /\{([^\}]+)\}/g, x = /^(?:(.*?)\s)?([\w\.\:>]+)$/, n, i = function (p, q) {
        return d.data(p, "controllers", q)
    };
    d.Class("jQuery.Controller", {setup: function () {
        this._super.apply(this,
            arguments);
        if (!(!this.shortName || this.fullName == "jQuery.Controller")) {
            this._fullName = D(this.fullName);
            this._shortName = D(this.shortName);
            var p = this, q = this.pluginName || this._fullName, o;
            d.fn[q] || (d.fn[q] = function (u) {
                var w = a(arguments), C = typeof u == "string" && e(p.prototype[u]), B = w[0];
                return this.each(function () {
                    var K = i(this);
                    if (K = K && K[q])C ? K[B].apply(K, w.slice(1)) : K.update.apply(K, w); else p.newInstance.apply(p, [this].concat(w))
                })
            });
            this.actions = {};
            for (o in this.prototype)if (!(o == "constructor" || !e(this.prototype[o])))if (this._isAction(o))this.actions[o] =
                this._action(o)
        }
    }, hookup: function (p) {
        return new this(p)
    }, _isAction: function (p) {
        return s.test(p) ? true : d.inArray(p, this.listensTo) > -1 || d.event.special[p] || l[p]
    }, _action: function (p, q) {
        A.lastIndex = 0;
        if (!q && A.test(p))return null;
        p = q ? j.sub(p, [q, window]) : p;
        q = c(p);
        var o = (q ? p[1] : p).match(x);
        return{processor: l[o[2]] || n, parts: o, delegate: q ? p[0] : undefined}
    }, processors: {}, listensTo: [], defaults: {}}, {setup: function (p, q) {
        var o = this.constructor;
        p = (typeof p == "string" ? d(p) : p.jquery ? p : [p])[0];
        var u = o.pluginName || o._fullName;
        this.element = d(p).addClass(u);
        (i(p) || i(p, {}))[u] = this;
        this.options = h(h(true, {}, o.defaults), q);
        this.called = "init";
        this.bind();
        return[this.element, this.options].concat(a(arguments).slice(2))
    }, bind: function (p, q, o) {
        if (p === undefined) {
            this._bindings = [];
            p = this.constructor;
            q = this._bindings;
            o = p.actions;
            var u = this.element;
            for (funcName in o)if (o.hasOwnProperty(funcName)) {
                ready = o[funcName] || p._action(funcName, this.options);
                q.push(ready.processor(ready.delegate || u, ready.parts[2], ready.parts[1], funcName, this))
            }
            var w =
                t(this, "destroy");
            u.bind("destroyed", w);
            q.push(function (C) {
                d(C).unbind("destroyed", w)
            });
            return q.length
        }
        if (typeof p == "string") {
            o = q;
            q = p;
            p = this.element
        }
        return this._binder(p, q, o)
    }, _binder: function (p, q, o, u) {
        if (typeof o == "string")o = t(this, o);
        this._bindings.push(m(p, q, o, u));
        return this._bindings.length
    }, _unbind: function () {
        var p = this.element[0];
        f(this._bindings, function (q, o) {
            o(p)
        });
        this._bindings = []
    }, delegate: function (p, q, o, u) {
        if (typeof p == "string") {
            u = o;
            o = q;
            q = p;
            p = this.element
        }
        return this._binder(p, o, u,
            q)
    }, update: function (p) {
        h(this.options, p);
        this._unbind();
        this.bind()
    }, destroy: function () {
        if (this._destroyed)throw this.constructor.shortName + " controller already deleted";
        var p = this.constructor.pluginName || this.constructor._fullName;
        this._destroyed = true;
        this.element.removeClass(p);
        this._unbind();
        delete this._actions;
        delete this.element.data("controllers")[p];
        d(this).triggerHandler("destroyed");
        this.element = null
    }, find: function (p) {
        return this.element.find(p)
    }, _set_called: true});
    var l = d.Controller.processors;
    n = function (p, q, o, u, w) {
        return m(p, q, t(w, u), o)
    };
    f("change click contextmenu dblclick keydown keyup keypress mousedown mousemove mouseout mouseover mouseup reset resize scroll select submit focusin focusout mouseenter mouseleave".split(" "), function (p, q) {
        l[q] = n
    });
    var r, v = function (p, q) {
        for (r = 0; r < q.length; r++)if (typeof q[r] == "string" ? p.constructor._shortName == q[r] : p instanceof q[r])return true;
        return false
    };
    d.fn.extend({controllers: function () {
        var p = a(arguments), q = [], o, u, w;
        this.each(function () {
            o = d.data(this,
                "controllers");
            for (w in o)if (o.hasOwnProperty(w)) {
                u = o[w];
                if (!p.length || v(u, p))q.push(u)
            }
        });
        return q
    }, controller: function () {
        return this.controllers.apply(this, arguments)[0]
    }})
});
steal.loaded("jquery/controller/controller.js");
steal.then(function () {
    if (!window.OpenAjax) {
        OpenAjax = new (function () {
            var d = {};
            this.hub = d;
            d.implementer = "http://openajax.org";
            d.implVersion = "2.0";
            d.specVersion = "2.0";
            d.implExtraData = {};
            var b = {};
            d.libraries = b;
            d.registerLibrary = function (a, c, e, h) {
                b[a] = {prefix: a, namespaceURI: c, version: e, extraData: h};
                this.publish("org.openajax.hub.registerLibrary", b[a])
            };
            d.unregisterLibrary = function (a) {
                this.publish("org.openajax.hub.unregisterLibrary", b[a]);
                delete b[a]
            };
            d._subscriptions = {c: {}, s: []};
            d._cleanup = [];
            d._subIndex =
                0;
            d._pubDepth = 0;
            d.subscribe = function (a, c, e, h, j) {
                e || (e = window);
                var f = a + "." + this._subIndex;
                c = {scope: e, cb: c, fcb: j, data: h, sid: this._subIndex++, hdl: f};
                this._subscribe(this._subscriptions, a.split("."), 0, c);
                return f
            };
            d.publish = function (a, c) {
                var e = a.split(".");
                this._pubDepth++;
                this._publish(this._subscriptions, e, 0, a, c);
                this._pubDepth--;
                if (this._cleanup.length > 0 && this._pubDepth == 0) {
                    for (a = 0; a < this._cleanup.length; a++)this.unsubscribe(this._cleanup[a].hdl);
                    delete this._cleanup;
                    this._cleanup = []
                }
            };
            d.unsubscribe =
                function (a) {
                    a = a.split(".");
                    var c = a.pop();
                    this._unsubscribe(this._subscriptions, a, 0, c)
                };
            d._subscribe = function (a, c, e, h) {
                var j = c[e];
                if (e == c.length)a.s.push(h); else {
                    if (typeof a.c == "undefined")a.c = {};
                    if (typeof a.c[j] == "undefined")a.c[j] = {c: {}, s: []};
                    this._subscribe(a.c[j], c, e + 1, h)
                }
            };
            d._publish = function (a, c, e, h, j, f) {
                if (typeof a != "undefined") {
                    if (e == c.length)a = a; else {
                        this._publish(a.c[c[e]], c, e + 1, h, j, f);
                        this._publish(a.c["*"], c, e + 1, h, j, f);
                        a = a.c["**"]
                    }
                    if (typeof a != "undefined") {
                        a = a.s;
                        c = a.length;
                        for (e = 0; e < c; e++)if (a[e].cb) {
                            var g =
                                a[e].scope, k = a[e].cb, m = a[e].fcb, t = a[e].data;
                            if (typeof k == "string")k = g[k];
                            if (typeof m == "string")m = g[m];
                            if (!m || m.call(g, h, j, t))k.call(g, h, j, t, f)
                        }
                    }
                }
            };
            d._unsubscribe = function (a, c, e, h) {
                if (typeof a != "undefined")if (e < c.length) {
                    var j = a.c[c[e]];
                    this._unsubscribe(j, c, e + 1, h);
                    if (j.s.length == 0) {
                        for (var f in j.c)return;
                        delete a.c[c[e]]
                    }
                } else {
                    a = a.s;
                    c = a.length;
                    for (e = 0; e < c; e++)if (h == a[e].sid) {
                        if (this._pubDepth > 0) {
                            a[e].cb = null;
                            this._cleanup.push(a[e])
                        } else a.splice(e, 1);
                        return
                    }
                }
            };
            d.reinit = function () {
                for (var a in OpenAjax.hub.libraries)delete OpenAjax.hub.libraries[a];
                OpenAjax.hub.registerLibrary("OpenAjax", "http://openajax.org/hub", "1.0", {});
                delete OpenAjax._subscriptions;
                OpenAjax._subscriptions = {c: {}, s: []};
                delete OpenAjax._cleanup;
                OpenAjax._cleanup = [];
                OpenAjax._subIndex = 0;
                OpenAjax._pubDepth = 0
            }
        });
        OpenAjax.hub.registerLibrary("OpenAjax", "http://openajax.org/hub", "1.0", {})
    }
    OpenAjax.hub.registerLibrary("JavaScriptMVC", "http://JavaScriptMVC.com", "3.1", {})
});
steal.loaded("jquery/lang/openajax/openajax.js");
steal("jquery/class", function (d) {
    d.Class("Tfl.Maps.Framework.MapUtils", {sanitizeLatLng: function (b) {
        if (b.lon)b.lng = b.lon
    }, getBoundsFromPositions: function (b) {
        if (!b || b.length == 0)return null;
        return this.extendBoundsWithPositions({sw: b[0], ne: b[0]}, b.slice(1))
    }, extendBoundsWithPositions: function (b, a) {
        var c = this;
        if (!a || a.length == 0)return b;
        var e = this.getGoogleBounds(b);
        d.each(a, function (h, j) {
            e = e.extend(c.getGoogleLatLng(j))
        });
        return this.getOurBoundsFromGoogleBounds(e)
    }, getOurBoundsFromGoogleBounds: function (b) {
        return{sw: {lat: b.getSouthWest().lat(),
            lng: b.getSouthWest().lng()}, ne: {lat: b.getNorthEast().lat(), lng: b.getNorthEast().lng()}}
    }, getBoundsFromPositionAndRadius: function (b, a) {
        b = this.getGoogleLatLng(b);
        var c = Math.sqrt(Math.pow(a, 2) * 2);
        a = google.maps.geometry.spherical.computeOffset(b, c, 225);
        b = google.maps.geometry.spherical.computeOffset(b, c, 45);
        return{sw: {lat: a.lat(), lng: a.lng()}, ne: {lat: b.lat(), lng: b.lng()}}
    }, boundsAContainsBoundsB: function (b, a) {
        b = this.getGoogleBounds(b);
        a = this.getGoogleBounds(a);
        return b.contains(a.getSouthWest()) && b.contains(a.getNorthEast())
    },
        getOurLatLngFromGoogleLatLng: function (b) {
            return{lat: b.lat(), lng: b.lng()}
        }, getOurPathFromGooglePath: function (b) {
            return d.map(b, this.getOurLatLngFromGoogleLatLng)
        }, getOurPathFromTflPath: function (b) {
            b = typeof b === "string" ? d.parseJSON(b) : b;
            return d.map(b, function (a) {
                return a[0] > a[1] ? {lat: a[0], lng: a[1]} : {lat: a[1], lng: a[0]}
            })
        }, getOurLatLngFromTflPoint: function (b) {
            b = typeof b === "string" ? d.parseJSON(b) : b;
            return b[0] > b[1] ? {lat: b[0], lng: b[1]} : {lat: b[1], lng: b[0]}
        }, getGoogleBounds: function (b) {
            return new google.maps.LatLngBounds(this.getGoogleLatLng(b.sw),
                this.getGoogleLatLng(b.ne))
        }, getGoogleLatLng: function (b) {
            return new google.maps.LatLng(b.lat, b.lng)
        }, getGooglePath: function (b) {
            return d.map(b, this.getGoogleLatLng)
        }, filterArrayByBounds: function (b, a, c, e) {
            var h = this;
            return d.map(b, function (j) {
                var f = c(j), g = e(j);
                return h.boundsContainPosition(a, f, g) ? j : null
            })
        }, boundsContainPosition: function (b, a, c) {
            return a < b.ne.lat && a > b.sw.lat && c < b.ne.lng && c > b.sw.lng
        }, ourPixelBoundsOverlap: function (b, a) {
            return!(b.sw.x < a.ne.x || b.ne.x > a.sw.x || b.sw.y < a.ne.y || b.ne.y > a.sw.y)
        },
        getNearestPoints: function (b, a, c) {
            d.each(a, function (e, h) {
                h.distance = Tfl.Maps.Framework.MapUtils.distanceBetweenPoints(b, h)
            });
            return a.sort(function (e, h) {
                return e.distance - h.distance
            }).slice(0, c)
        }, distanceBetweenPoints: function (b, a) {
            var c = Tfl.Maps.Framework.MapUtils.radians(a.lat - b.lat), e = Tfl.Maps.Framework.MapUtils.radians(a.lng - b.lng);
            b = Math.sin(c / 2) * Math.sin(c / 2) + Math.cos(Tfl.Maps.Framework.MapUtils.radians(b.lat)) * Math.cos(Tfl.Maps.Framework.MapUtils.radians(a.lat)) * Math.sin(e / 2) * Math.sin(e / 2);
            return 6371 * 2 * Math.atan2(Math.sqrt(b), Math.sqrt(1 - b))
        }, radians: function (b) {
            return b * Math.PI / 180
        }, debugBounds: function (b, a) {
            var c = null;
            c = a.ne ? this.getPathFromBounds(a) : this.getPathFromBounds(this.getBoundsFromPositions(a));
            b = {map: b.googleMap, path: this.getGooglePath(c), clickable: false, strokeColor: "#000000", strokeOpacity: 1, strokeWeight: 1};
            new google.maps.Polygon(b)
        }, debugPoint: function (b, a) {
            new google.maps.Circle({map: b.googleMap, center: new google.maps.LatLng(a.lat, a.lng), radius: Math.round(10), strokeColor: "#000000",
                strokeOpacity: 1, strokeWeight: 2, fillColor: "#000000", fillOpacity: 0});
            new google.maps.Circle({map: b.googleMap, center: new google.maps.LatLng(a.lat, a.lng), radius: 10, strokeColor: "#000000", strokeOpacity: 1, strokeWeight: 2, fillColor: "#000000", fillOpacity: 0})
        }, getPathFromBounds: function (b) {
            return[
                {lat: b.ne.lat, lng: b.sw.lng},
                {lat: b.ne.lat, lng: b.ne.lng},
                {lat: b.sw.lat, lng: b.ne.lng},
                {lat: b.sw.lat, lng: b.sw.lng},
                {lat: b.ne.lat, lng: b.sw.lng}
            ]
        }}, {})
});
steal.loaded("framework/map/map_utils.js");
steal("jquery/class", function () {
    $.Class("Tfl.Maps.Framework.GoogleScriptLoader", {googleScriptLoaded: false, googleScriptLoading: false, googleScriptCallbacks: [], loadGoogleScript: function (d, b) {
        if (this.googleScriptLoaded)b(); else if (this.googleScriptLoading)this.googleScriptCallbacks.push(b); else {
            this.googleScriptLoading = true;
            this.googleScriptCallbacks.push(b);
            d = "&client=gme-transportforlondon&channel=" + d;
            var a = "sensor=false&libraries=geometry" + (this.isDebug() ? "" : d);
            $.getScript("https://www.google.com/jsapi",
                this.proxy(function () {
                    google.load("maps", "3", {other_params: a, callback: this.proxy(this.runGoogleScriptCallbacks)})
                }))
        }
    }, isDebug: function () {
        if (window.tfl)if (tfl.isGoogleMapsDebug)return true;
        return false
    }, runGoogleScriptCallbacks: function () {
        this.googleScriptLoaded = true;
        this.setupGooglePrototypes();
        $.each(this.googleScriptCallbacks, function (d, b) {
            b()
        })
    }, setupGooglePrototypes: function () {
        google.maps.Point.prototype.distanceTo = function (d) {
            var b = d.x - this.x;
            d = d.y - this.y;
            return Math.sqrt(b * b + d * d)
        };
        google.maps.Point.prototype.pixelX =
            function () {
                return this.x
            };
        google.maps.Point.prototype.pixelY = function () {
            return this.y
        }
    }}, {})
});
steal.loaded("framework/map/google_script_loader.js");
steal("jquery/class", "jquery/lang/openajax", "framework/map/map_manager_base.js", "framework/models/settings_helper.js", function (d) {
    var b = Tfl.Maps.Framework.Models.SettingsHelper;
    Tfl.Maps.Framework.MapManagerBase("Tfl.Maps.Framework.LayerManager", {}, {init: function (a) {
        this.map = a;
        this.layerHolders = {};
        this.nextZIndex = 0
    }, addLayer: function (a, c) {
        c = c || {};
        var e = c.name || a.LAYER_NAME, h = c.isOptional;
        if (typeof h === "undefined")h = e ? true : false;
        var j = c.initialVisibility;
        if (typeof j === "undefined")j = !h;
        e = e || "layer" +
            this.nextZIndex;
        a = new a(this.map, this.nextZIndex, c.layerOptions || {});
        this.layerHolders[e] = {layer: a, isOptional: h};
        if (h && c.iconPath)this.layerHolders[e].iconPath = c.iconPath;
        if (c.classSuffix)this.layerHolders[e].classSuffix = c.classSuffix;
        j && a.show();
        this.nextZIndex++;
        return a
    }, getLayerTogglesData: function () {
        return d.map(this.layerHolders, function (a, c) {
            if (a.isOptional) {
                var e = a.layer, h = b.contentBaseUrl + (a.iconPath || e.constructor.LAYER_ICON_PATH);
                a = a.classSuffix || e.constructor.LAYER_CLASS_SUFFIX || "unknown";
                return e.isFilterable() ? {name: c, iconUrl: h, filters: e.filters} : {name: c, iconUrl: h, classSuffix: a}
            } else return null
        })
    }, wireUpLayerToggles: function (a) {
        d.each(a, this.proxy(function (c, e) {
            var h = d(e);
            c = h.data("name");
            var j = this.layerHolders[c].layer;
            this.setToggleState(h, j.isVisible());
            h.click(this.proxy(function () {
                this.setToggleState(h, !j.isVisible());
                this.toggleLayer(j);
                this.map.optionsManager.closeMapOptionsDialog()
            }));
            j.isFilterable() && this.wireUpFilterToggles(h.parent().find(".map-options-layer-filter[data-name='" +
                c + "']"), j)
        }))
    }, wireUpFilterToggles: function (a, c) {
        d.each(a, this.proxy(function (e, h) {
            var j = d(h);
            e = j.data("filtername");
            var f = c.filters[e], g = f.filterValues[j.data("filtervaluename")];
            this.setLayerFilterToggleState(j, g.isVisible);
            j.click(this.proxy(function () {
                this.setLayerFilterToggleState(j, !g.isVisible);
                this.toggleLayerFilter(c, f, g.name);
                c.refresh();
                this.map.optionsManager.closeMapOptionsDialog()
            }))
        }))
    }, getLayerKeyData: function () {
        return d.map(this.layerHolders, function (a) {
            if (a = a.layer.constructor.LAYER_KEY)return d.map(a,
                function (c, e) {
                    e = {name: e};
                    if (c.slice(0, 1) === "#")e.color = c; else e.iconUrl = b.contentBaseUrl + c;
                    return e
                })
        })
    }, toggleLayer: function (a) {
        a.isVisible() ? a.hide() : a.show()
    }, toggleLayerFilter: function (a, c, e) {
        a.isVisible() ? a.hide() : a.show();
        c.isVisible(e) ? c.hide(e) : c.show(e)
    }, setToggleState: function (a, c) {
        a.toggleClass("map-options-layer-toggle-checked", c)
    }, setLayerFilterToggleState: function (a, c) {
        a.toggleClass("map-options-layer-filter-toggle-checked", c)
    }})
});
steal.loaded("framework/map/layers/layer_manager.js");
steal("./static_layer_base.js", function () {
    Tfl.Maps.Framework.StaticLayerBase("Tfl.Maps.Framework.UserLocationLayer", {}, {init: function (d, b) {
        this._super(d, b)
    }, showUserLocation: function (d) {
        this.userLocation = d;
        this.pin = this.addHtmlPin({position: this.userLocation, iconAnchor: {x: 50, y: 50}, html: "<div class='userlocation-dot-holder' style='height: 100px; width: 100px;'><div class='userlocation-pulse'></div><div class='userlocation-dot'></div></div>"})
    }, hideUserLocation: function () {
        this.clear();
        this.pin = this.userLocation =
            null
    }, render: function () {
    }})
});
steal.loaded("framework/map/layers/userlocation_layer.js");
steal("jquery/class", "jquery/view/ejs", "framework/map/map_manager_base.js", "lib/jquery.hammer.js").then("./options_dialog.ejs", function (d) {
    Tfl.Maps.Framework.MapManagerBase("Tfl.Maps.Framework.MapOptionsManager", {init: function () {
        this.panelResizesMap = true
    }}, {init: function (b) {
        this._super(b);
        this.addMapOptionsControl()
    }, closeOptionsDialogIfOpen: function () {
        this.dialogDiv && this.removeMapOptionsDialog()
    }, addMapOptionsControl: function () {
        this.mapOptionsButton = d("<div class='map-options-control-button'></div>");
        this.map.rightTopCustomControlManager.addControl(this.mapOptionsButton);
        this.mapOptionsButton.click(this.proxy(this.openMapOptionsDialog))
    }, openMapOptionsDialog: function () {
        this.map.hideMapControls();
        var b = !d("body").hasClass("breakpoint-Medium") ? 1 : 2, a = this.map.element, c = this.map.styleManager, e = this.map.layerManager;
        a.prepend("//framework/map/options/options_dialog.ejs", {mapTypeOptions: c.getMapTypeSelectorData(), layerKeyColumns: this.arrayToColumns(e.getLayerKeyData(), b), optionalLayerColumns: this.arrayToColumns(e.getLayerTogglesData().reverse(),
            b)});
        b = a.find(".map-options-dialog");
        c.wireUpMapSelector(b.find(".map-options-map-type-selector"));
        e.wireUpLayerToggles(b.find(".map-options-layer-container"));
        b.find(".map-options-close-button, .map-options-dialog-background").click(this.proxy(this.closeMapOptionsDialog));
        c = b.find(".map-options-dialog-inner");
        c.hammer && c.hammer({drag_lock_to_axis: true}).on("release dragright swiperight", this.proxy(this.handleHammer));
        b.width(a.width());
        b.height(a.height());
        e = a.height();
        this.unexpandedMapHeight = a.height();
        if (c.outerHeight() > e)if (this.constructor.panelResizesMap) {
            this.map.setHeight(c.outerHeight());
            b.height(a.height());
            b.width(a.width());
            this.map.divResized()
        } else {
            c.outerHeight(e);
            a = b.find(".map-options-dialog-scroll");
            a.height(c.height() - a.position().top)
        }
        c.animate({left: "10%"}, {complete: this.proxy(function () {
            this.dialogInnerOriginLeft = this.dialogInner.position().left
        })});
        this.dialogDiv = b;
        this.dialogInner = c
    }, arrayToColumns: function (b, a) {
        for (var c = (b.length / a).toFixed(), e = [], h = 0; h < a; h++)e.push(b.slice(h *
            c, h * c + c));
        return e
    }, handleHammer: function (b) {
        b.gesture.preventDefault();
        switch (b.type) {
            case "dragright":
            case "dragleft":
                this.dialogInner.css("left", this.dialogInnerOriginLeft + b.gesture.deltaX);
                break;
            case "swiperight":
                this.closeMapOptionsDialog();
                b.gesture.stopDetect();
                break;
            case "release":
                b.gesture.deltaX > this.dialogInner.width() / 3 ? this.closeMapOptionsDialog() : this.dialogInner.animate({left: "10%"});
                break
        }
    }, closeMapOptionsDialog: function () {
        this.dialogInner.animate({left: "100%"}, {complete: this.proxy(this.removeMapOptionsDialog)})
    },
        removeMapOptionsDialog: function () {
            this.dialogDiv.remove();
            this.dialogInner = this.dialogDiv = null;
            this.map.setHeight(this.unexpandedMapHeight);
            this.map.showMapControls()
        }})
});
steal.loaded("framework/map/options/map_options_manager.js");
steal("jquery/class", "jquery/view/ejs", "framework/map/map_manager_base.js", "./custom_controls.css").then(function (d) {
    Tfl.Maps.Framework.MapManagerBase("Tfl.Maps.Framework.CustomControlsManager", {init: function () {
    }}, {init: function (b, a) {
        this._super(b);
        this.controlPosition = a;
        this.controlGroupElement = d("<ul class='map-custom-control-group'></ul>");
        b.googleMap.controls[a].push(this.controlGroupElement[0])
    }, addControl: function (b, a) {
        b = d("<li>").append(b);
        a && b.addClass("hidden-on-tablet-and-above");
        this.controlGroupElement.append(b)
    },
        hide: function () {
            this.controlGroupElement.hide()
        }, show: function () {
            this.controlGroupElement.show()
        }})
});
steal.loaded("framework/map/controls/custom_controls_manager.js");
steal("./map_manager_base.js", function (d) {
    Tfl.Maps.Framework.MapManagerBase("Tfl.Maps.Framework.StyleManager", {COMMON_STYLES: [
        {featureType: "transit.station", stylers: [
            {visibility: "off"}
        ]}
    ], HYBRID_STYLES: [
        {featureType: "transit.line", stylers: [
            {visibility: "off"}
        ]}
    ], ROADMAP_STYLES: [
        {featureType: "transit.line", stylers: [
            {hue: "#00a8ff"},
            {visibility: "simplified"}
        ]}
    ], FAT_ROAD_WEIGHTS_BY_ZOOM: {"default": [0, 1, 3], "15": [4, 4, 6], "16": [8, 8, 10], "17": [18, 18, 20], "18": [24, 42, 42], "19": [48, 84, 84], "20": [48, 84, 84], "21": [48,
        84, 84]}, PLAINMAP_STYLES: [
        {featureType: "transit.line", stylers: [
            {hue: "#00a8ff"},
            {visibility: "simplified"}
        ]},
        {featureType: "road", elementType: "geometry", stylers: [
            {color: "#ffffff"},
            {visibility: "simplified"}
        ]},
        {featureType: "road", elementType: "labels.text.stroke", stylers: [
            {color: "#ffffff"},
            {weight: 2.5}
        ]},
        {featureType: "road", elementType: "labels.text.fill", stylers: [
            {color: "#6c6c6c"}
        ]},
        {featureType: "road.local", elementType: "geometry", stylers: [
            {weight: 1}
        ]},
        {featureType: "road", elementType: "labels.icon", stylers: [
            {hue: "#0000ff"},
            {visibility: "off"}
        ]},
        {featureType: "landscape", stylers: [
            {visibility: "off"}
        ]},
        {featureType: "transit.station", stylers: [
            {visibility: "off"}
        ]}
    ]}, {init: function (b) {
        this._super(b);
        this.selectMapType(google.maps.MapTypeId.ROADMAP);
        this.map.idle(this.proxy(this.onMapIdle));
        this.map.changeZoom(this.proxy(this.onZoomChanged));
        this.plainMapRequestScore = this.fatRoadsRequestScore = 0;
        this.linkRemoved = false
    }, getMapTypeSelectorData: function () {
        return[
            {value: google.maps.MapTypeId.ROADMAP, name: "Road"},
            {value: google.maps.MapTypeId.HYBRID,
                name: "Satellite"}
        ]
    }, wireUpMapSelector: function (b) {
        var a = this;
        b.find(".map-options-map-type-selector-option").click(function () {
            var c = d(this).data("maptype");
            a.selectMapType(c);
            a.map.optionsManager.closeMapOptionsDialog()
        }).removeClass("map-options-map-type-selector-option-selected");
        b.find(".map-options-map-type-selector-option[data-maptype='" + this.map.googleMap.getMapTypeId() + "']").addClass("map-options-map-type-selector-option-selected")
    }, selectMapType: function (b) {
        this.map.googleMap.setMapTypeId(b);
        this.applyMapStyles(b)
    }, applyMapStyles: function (b) {
        b = this.getMapTypeStyles(b);
        if (this.showPlainMap)b = b.concat(this.constructor.PLAINMAP_STYLES);
        if (this.showFatRoads)b = b.concat(this.getFatRoadStyles());
        this.map.googleMap.setOptions({styles: b})
    }, requestShowPlainMap: function () {
        if (!this.showPlainMap) {
            this.showPlainMap = true;
            this.selectMapType(this.map.googleMap.getMapTypeId())
        }
        this.plainMapRequestScore++
    }, requestHidePlainMap: function () {
        if (this.showPlainMap) {
            this.plainMapRequestScore--;
            if (this.plainMapRequestScore <
                1) {
                this.plainMapRequestScore = 0;
                this.showPlainMap = false;
                this.selectMapType(this.map.googleMap.getMapTypeId())
            }
        }
    }, requestShowFatRoads: function () {
        if (!this.showFatRoads) {
            this.showFatRoads = true;
            this.selectMapType(this.map.googleMap.getMapTypeId())
        }
        this.fatRoadsRequestScore++
    }, requestHideFatRoads: function () {
        if (this.showFatRoads) {
            this.fatRoadsRequestScore--;
            if (this.fatRoadsRequestScore < 1) {
                this.fatRoadsRequestScore = 0;
                this.showFatRoads = false;
                this.selectMapType(this.map.googleMap.getMapTypeId())
            }
        }
    }, onMapIdle: function () {
        if (!this.linkRemoved) {
            if (this.linkRemoveAttempts ===
                undefined)this.linkRemoveAttempts = 0;
            if (!(this.linkRemoveAttempts > 20)) {
                var b = this.getReportErrorEle();
                if (b.length) {
                    b.remove();
                    this.linkRemoved = true
                } else window.setTimeout(this.proxy(this.onMapIdle), 100);
                this.linkRemoveAttempts++
            }
        }
    }, onZoomChanged: function () {
        this.selectMapType(this.map.googleMap.getMapTypeId())
    }, getMapTypeStyles: function (b) {
        var a = this.constructor;
        b = b === google.maps.MapTypeId.ROADMAP ? a.ROADMAP_STYLES : a.HYBRID_STYLES;
        return b = b.concat(a.COMMON_STYLES)
    }, getFatRoadStyles: function () {
        var b =
            function (c, e) {
                return{featureType: c, elementType: "geometry.fill", stylers: [
                    {weight: e}
                ]}
            }, a = this.constructor.FAT_ROAD_WEIGHTS_BY_ZOOM["" + this.map.googleMap.getZoom()] || this.constructor.FAT_ROAD_WEIGHTS_BY_ZOOM["default"];
        return[b("road.local", a[0]), b("road.arterial", a[1]), b("road.highway", a[2])]
    }, getReportErrorEle: function () {
        return d("div.gm-style-cc:has(a[title='Report errors in the road map or imagery to Google'])")
    }})
});
steal.loaded("framework/map/style_manager.js");
steal("framework/models/settings_helper.js", "./map_manager_base.js", function (d) {
    Tfl.Maps.Framework.MapManagerBase("Tfl.Maps.Framework.FullWindowManager", {SWITCH_TO_FULL_HTML: "<img src='" + Tfl.Maps.Framework.Models.SettingsHelper.contentBaseUrl + "framework/map/icons/expand_icon.png' />", SWITCH_TO_NORMAL_HTML: "<img src='" + Tfl.Maps.Framework.Models.SettingsHelper.contentBaseUrl + "framework/map/icons/collapse_icon.png' />"}, {init: function (b) {
        this._super(b);
        this.fullWindowControl = d("<div class='map-full-window-control'><div class='map-full-window-control-inner'>" +
            this.constructor.SWITCH_TO_FULL_HTML + "</div></div>");
        this.fullWindowControl.click(this.proxy(this.controlClicked));
        this.map.rightBottomCustomControlManager.addControl(this.fullWindowControl);
        d(document).keyup(this.proxy(this.keyReleased));
        this.map.addKeyboardAccessibleOverlay(this.fullWindowControl)
    }, fullWindowControl: null, isCurrentlyFullWindow: false, scrollTop: null, controlClicked: function () {
        var b = d(this.map.mapElement);
        if (this.isCurrentlyFullWindow) {
            b.parents().removeClass("map-full-window");
            var a =
                d("#tflmapfullwindowplaceholder");
            b.parent().insertAfter(a);
            a.remove();
            d(window).scrollTop(this.scrollTop);
            this.isCurrentlyFullWindow = false
        } else {
            this.scrollTop = d(window).scrollTop();
            d("<div />").attr("id", "tflmapfullwindowplaceholder").insertBefore(b.parent());
            b.parent().prependTo(d(document.body));
            b.parents().addClass("map-full-window");
            d(window).scrollTop(0);
            this.isCurrentlyFullWindow = true
        }
        this.refreshControlHtml();
        this.map.divResized()
    }, keyReleased: function (b) {
        this.isCurrentlyFullWindow && b.which ===
            27 && this.controlClicked()
    }, refreshControlHtml: function () {
        this.fullWindowControl.find(".map-full-window-control-inner").html(this.isCurrentlyFullWindow ? this.constructor.SWITCH_TO_NORMAL_HTML : this.constructor.SWITCH_TO_FULL_HTML)
    }})
});
steal.loaded("framework/map/full_window_manager.js");
steal("jquery/event").then(function (d) {
    var b = jQuery.cleanData;
    d.cleanData = function (a) {
        for (var c = 0, e; (e = a[c]) !== undefined; c++)d(e).triggerHandler("destroyed");
        b(a)
    }
});
steal.loaded("jquery/event/destroyed/destroyed.js");
steal("jquery");
steal.loaded("jquery/event/event.js");
steal("jquery/class", function () {
    $.Class("Tfl.Maps.Framework.MapManagerBase", {}, {init: function (d) {
        this.map = d
    }})
});
steal.loaded("framework/map/map_manager_base.js");
steal("jquery/class", "./layer_base", function (d) {
    Tfl.Maps.Framework.LayerBase("Tfl.Maps.Framework.StaticLayerBase", {}, {renderLayer: function (b) {
        b = this.render(b);
        d.when(b).done(this.proxy(function () {
            this.isRendered = true
        }))
    }})
});
steal.loaded("framework/map/layers/static_layer_base.js");
steal("jquery/class", "jquery/lang/openajax", "jquery/lang/object", "framework/map/overlays/pin.js", "framework/map/overlays/html_pin.js", "framework/map/overlays/circle.js", "framework/map/overlays/polyline.js", "framework/map/overlays/polygon.js", "framework/map/overlays/polygonmask.js", function (d) {
    d.Class("Tfl.Maps.Framework.LayerBase", {init: function () {
        this.layerInstances = []
    }, selectItem: function (b, a, c) {
        for (var e = 0; e < Tfl.Maps.Framework.LayerBase.layerInstances.length; e++)Tfl.Maps.Framework.LayerBase.layerInstances[e] !==
        c && Tfl.Maps.Framework.LayerBase.layerInstances[e].itemSelectedInOtherLayer(b, a)
    }, LAYER_ZINDEX_SPACE: 1E4}, {init: function (b, a) {
        Tfl.Maps.Framework.LayerBase.layerInstances.push(this);
        this.map = b;
        this.zIndex = a;
        this.isFirstRender = true;
        this.isRendered = false;
        this.overlays = []
    }, hide: function () {
        this.prepareClear(true);
        this.clear(true)
    }, show: function () {
        this.isRendered || this.map.mapLoading.done(this.proxy(function () {
            this.renderLayer(this.isFirstRender);
            this.isFirstRender = false
        }))
    }, prepareClear: function () {
        if (this.isRendered) {
            var b =
                this.overlaysToClear = [];
            d.each(this.overlays, function (a, c) {
                c && !c.isCleared && b.push(c)
            });
            this.overlays = [];
            this.isRendered = false
        }
    }, clear: function () {
        if (this.overlaysToClear) {
            d.each(this.overlaysToClear, function (b, a) {
                a && !a.isCleared && a.clear()
            });
            this.overlaysToClear = []
        }
    }, refresh: function () {
        this.prepareClear();
        this.clear();
        this.show()
    }, isVisible: function () {
        return this.isRendered
    }, isFilterable: function () {
        return false
    }, focus: function () {
        this.map.activateIfDeactivated();
        this.map.optionsManager.closeOptionsDialogIfOpen();
        this.isVisible() || this.show()
    }, selectItem: function (b, a, c) {
        Tfl.Maps.Framework.LayerBase.selectItem(b, a, c)
    }, itemSelectedInOtherLayer: function () {
    }, addPin: function (b, a) {
        return this.addOverlay(Tfl.Maps.Framework.Pin, b, a)
    }, addHtmlPin: function (b, a) {
        return this.addOverlay(Tfl.Maps.Framework.HtmlPin, b, a)
    }, addCircle: function (b, a) {
        return this.addOverlay(Tfl.Maps.Framework.Circle, b, a)
    }, addPolyline: function (b, a) {
        return this.addOverlay(Tfl.Maps.Framework.Polyline, b, a)
    }, addPolygon: function (b, a) {
        return this.addOverlay(Tfl.Maps.Framework.Polygon,
            b, a)
    }, addPolygonMask: function (b, a) {
        return this.addOverlay(Tfl.Maps.Framework.PolygonMask, b, a)
    }, addOverlay: function (b, a, c) {
        c = c || 0;
        a.zIndex = this.zIndex * this.constructor.LAYER_ZINDEX_SPACE + c;
        b = new b(this.map, a);
        this.overlays.push(b);
        return b
    }})
});
steal.loaded("framework/map/layers/layer_base.js");
steal("jquery/class", "framework/models/settings_helper.js", "./overlay_base.js", function () {
    Tfl.Maps.Framework.OverlayBase("Tfl.Maps.Framework.Pin", {}, {setPosition: function (d) {
        this.googleOverlay.setPosition(new google.maps.LatLng(d.lat, d.lng))
    }, getGoogleOverlay: function () {
        var d = this.spec, b = {map: this.map.googleMap, title: d.tooltip, position: new google.maps.LatLng(d.position.lat, d.position.lng), zIndex: d.zIndex, optimized: !Tfl.Maps.Framework.Models.SettingsHelper.isInTest, cursor: d.cursor || ""};
        if (d.iconUrl) {
            var a =
                window.devicePixelRatio > 1;
            b.icon = d.retinaIconUrl && a ? {url: d.retinaIconUrl, scaledSize: d.size} : {url: d.iconUrl};
            if (d.iconAnchor)b.icon.anchor = d.iconAnchor
        }
        return new google.maps.Marker(b)
    }})
});
steal.loaded("framework/map/overlays/pin.js");
steal("jquery/class", "./overlay_base.js", "./html_marker.js", "./html_pins.css", function () {
    Tfl.Maps.Framework.OverlayBase("Tfl.Maps.Framework.HtmlPin", {}, {getGoogleOverlay: function () {
        var d = this.spec;
        d = {map: this.map.googleMap, title: d.tooltip, position: new google.maps.LatLng(d.position.lat, d.position.lng), zIndex: d.zIndex, html: d.html, iconAnchor: d.iconAnchor};
        Tfl.Maps.Framework.HtmlMarkerLoader.load();
        return new Tfl.Maps.Framework.HtmlMarker(d)
    }})
});
steal.loaded("framework/map/overlays/html_pin.js");
steal("jquery/class", "./overlay_base.js", function () {
    Tfl.Maps.Framework.OverlayBase("Tfl.Maps.Framework.Circle", {DEFAULT_STROKE_WEIGHT: 1, DEFAULT_STROKE_COLOR: "#2070b6", DEFAULT_FILL_COLOR: "#bde5ff", DEFAULT_STROKE_OPACITY: 1, DEFAULT_FILL_OPACITY: 0.35}, {setPosition: function (d) {
        this.googleOverlay.setCenter(new google.maps.LatLng(d.lat, d.lng))
    }, setRadius: function (d) {
        this.googleOverlay.setRadius(Math.round(d))
    }, setStrokeColor: function (d) {
        this.googleOverlay.setOptions({strokeColor: d})
    }, getGoogleOverlay: function () {
        var d =
            this.spec;
        return new google.maps.Circle({map: this.map.googleMap, center: new google.maps.LatLng(d.position.lat, d.position.lng), radius: Math.round(d.radius), strokeColor: this.constructor.DEFAULT_STROKE_COLOR, strokeOpacity: this.constructor.DEFAULT_STROKE_OPACITY, fillColor: this.constructor.DEFAULT_FILL_COLOR, fillOpacity: this.constructor.DEFAULT_FILL_OPACITY, strokeWeight: this.constructor.DEFAULT_STROKE_WEIGHT})
    }})
});
steal.loaded("framework/map/overlays/circle.js");
steal("jquery/class", "./overlay_base.js", "framework/map/map_utils.js", function (d) {
    var b = Tfl.Maps.Framework.MapUtils;
    Tfl.Maps.Framework.OverlayBase("Tfl.Maps.Framework.Polyline", {}, {getGoogleOverlay: function () {
        var a = this.spec.colors, c = this.spec.weight, e = {map: this.map.googleMap, path: b.getGooglePath(this.spec.path), clickable: false};
        a = [].concat(a);
        var h, j = [];
        if (a.length === 1) {
            e.strokeColor = a[0];
            e.strokeWeight = c;
            e.strokeOpacity = this.spec.opacity
        } else {
            switch (a.length) {
                case 2:
                    h = ["M -1.25,0 -0.25,0", "M 0.25,0 1.25,0"];
                    break;
                case 3:
                    h = c === 1 ? ["M -1.25,0 -0.75,0", "M -0.25,0 0.25,0", "M 0.75,0 1.25,0"] : ["M -2,0 -1,0", "M -0.5,0 0.5,0", "M 1,0 2,0"];
                    break;
                default:
                    throw new Error("Cannot paint polyline with more than three colors");
            }
            d.each(a, function (f, g) {
                j.push({icon: {path: h[f], strokeWeight: c > 3 ? 2 : 1, strokeColor: g}, repeat: "1px"})
            });
            e.strokeColor = "#ffffff"
        }
        if (this.spec.showChevrons) {
            e.strokeWeight = 4;
            j.push({icon: {path: "M -2,0 0,-2 2,0 2,3 0,1 -2,3 z", strokeWeight: 0, strokeColor: "#ffffff", fillColor: "#ffffff", fillOpacity: 1, scale: 1.2},
                repeat: "60px", offset: "13px"});
            j.push({icon: {path: "M -2,0 0,-2 2,0 2,3 0,1 -2,3 z", strokeWeight: 0, strokeColor: "#ffffff", fillColor: "#ffffff", fillOpacity: 1, scale: 1.2}, repeat: "60px", offset: "20px"});
            j.push({icon: {path: "M -2,0 0,-2 2,0 2,3 0,1 -2,3 z", strokeWeight: 0, strokeColor: "#ffffff", fillColor: "#ffffff", fillOpacity: 1, scale: 1.2}, repeat: "60px", offset: "27px"})
        } else this.spec.showArrows && j.push({icon: {path: "M -1,1 0,0 1,1 1,1.5 0,0.5 -1,1.5 -1,1", strokeWeight: 0.4, strokeColor: "#2D3039", fillColor: "#ffffff",
            fillOpacity: 1, scale: 7}, repeat: "80px", offset: "40px"});
        e.icons = j;
        return new google.maps.Polyline(e)
    }})
});
steal.loaded("framework/map/overlays/polyline.js");
steal("jquery/class", "./overlay_base.js", "framework/map/map_utils.js", function () {
    var d = Tfl.Maps.Framework.MapUtils;
    Tfl.Maps.Framework.OverlayBase("Tfl.Maps.Framework.Polygon", {DEFAULT_STROKE_WEIGHT: 1, DEFAULT_STROKE_COLOR: "#2070b6", DEFAULT_FILL_COLOR: "#bde5ff", DEFAULT_STROKE_OPACITY: 1, DEFAULT_FILL_OPACITY: 0.35}, {getGoogleOverlay: function () {
        var b = {map: this.map.googleMap, path: d.getGooglePath(this.spec.path), clickable: false, strokeColor: this.spec.strokeColor || this.constructor.DEFAULT_STROKE_COLOR, strokeOpacity: this.constructor.DEFAULT_STROKE_OPACITY,
            fillColor: this.spec.fillColor || this.constructor.DEFAULT_FILL_COLOR, fillOpacity: this.constructor.DEFAULT_FILL_OPACITY, strokeWeight: this.constructor.DEFAULT_STROKE_WEIGHT};
        return new google.maps.Polygon(b)
    }})
});
steal.loaded("framework/map/overlays/polygon.js");
steal("jquery/class", "./overlay_base.js", "framework/map/map_utils.js", function () {
    var d = Tfl.Maps.Framework.MapUtils;
    Tfl.Maps.Framework.OverlayBase("Tfl.Maps.Framework.PolygonMask", {DEFAULT_STROKE_WEIGHT: 1, DEFAULT_STROKE_COLOR: "#2070b6", DEFAULT_FILL_COLOR: "#bde5ff", DEFAULT_STROKE_OPACITY: 1, DEFAULT_FILL_OPACITY: 0.35, DEFAULT_OUTER_RING: [
        {lat: 63.074866, lng: -14.941406},
        {lat: 63.273182, lng: 10.195313},
        {lat: 48.283193, lng: 10.634766},
        {lat: 47.279229, lng: -14.238281},
        {lat: 63.074866, lng: -14.941406}
    ]}, {getGoogleOverlay: function () {
        var b =
        {map: this.map.googleMap, paths: [d.getGooglePath(this.spec.outerRing || this.constructor.DEFAULT_OUTER_RING), d.getGooglePath(this.spec.path.reverse())], clickable: false, strokeColor: this.spec.strokeColor || this.constructor.DEFAULT_STROKE_COLOR, strokeOpacity: this.constructor.DEFAULT_STROKE_OPACITY, fillColor: this.spec.fillColor || this.constructor.DEFAULT_FILL_COLOR, fillOpacity: this.constructor.DEFAULT_FILL_OPACITY, strokeWeight: this.constructor.DEFAULT_STROKE_WEIGHT};
        return new google.maps.Polygon(b)
    }})
});
steal.loaded("framework/map/overlays/polygonmask.js");
steal("jquery/class", "jquery/lang/openajax", function (d) {
    d.Class("Tfl.Maps.Framework.OverlayBase", {}, {init: function (b, a) {
        this.map = b;
        this.spec = a;
        this.listeners = {};
        this.googleOverlay = this.getGoogleOverlay()
    }, clear: function () {
        this.googleOverlay && this.googleOverlay.setMap(null);
        this.googleOverlay = null;
        this.isCleared = true
    }, show: function () {
        this.googleOverlay.setVisible(true)
    }, hide: function () {
        this.googleOverlay.setVisible(false)
    }, click: function (b) {
        this.registerListener("click", b)
    }, hover: function (b, a) {
        this.registerListener("mouseover",
            callback);
        a && this.registerListener("mouseout", callback)
    }, registerListener: function (b, a) {
        google.maps.event.addListener(this.googleOverlay, b, this.proxy(function () {
            a.call(this, this)
        }))
    }})
});
steal.loaded("framework/map/overlays/overlay_base.js");
var Tfl = Tfl || {};
Tfl.Maps = Tfl.Maps || {};
Tfl.Maps.Framework = Tfl.Maps.Framework || {};
steal(function () {
    Tfl.Maps.Framework.HtmlMarkerLoader = {};
    Tfl.Maps.Framework.HtmlMarkerLoader.load = function () {
        if (!Tfl.Maps.Framework.HtmlMarker) {
            Tfl.Maps.Framework.HtmlMarker = function (d) {
                this.setValues(d);
                var b = d.classes;
                b = b ? " " + b.join(" ") : "";
                var a = this.div_ = document.createElement("div");
                a.className = "map-html-marker" + b;
                if (d.icon) {
                    $(a).css("background-image", "url(" + d.icon + ")");
                    $(a).css("background-repeat", "no-repeat")
                }
                d.title && $(a).attr("title", d.title);
                this.iconAnchor = d.iconAnchor;
                this.LatLng = this.getPosition()
            };
            Tfl.Maps.Framework.HtmlMarker.measurements = {};
            Tfl.Maps.Framework.HtmlMarker.prototype = new google.maps.OverlayView;
            Tfl.Maps.Framework.HtmlMarker.prototype.onAdd = function () {
                this.getPanes().overlayMouseTarget.appendChild(this.div_);
                var d = this;
                this.listeners_ = [google.maps.event.addListener(this, "position_changed", function () {
                    d.draw()
                }), google.maps.event.addListener(this, "html_changed", function () {
                    d.draw()
                }), google.maps.event.addDomListener(this.div_, "click", function () {
                    google.maps.event.trigger(d, "click")
                }),
                    google.maps.event.addDomListener(this.div_, "mouseover", function () {
                        google.maps.event.trigger(d, "mouseover")
                    }), google.maps.event.addDomListener(this.div_, "mouseout", function () {
                        google.maps.event.trigger(d, "mouseout")
                    })]
            };
            Tfl.Maps.Framework.HtmlMarker.prototype.onRemove = function () {
                this.div_.parentNode.removeChild(this.div_);
                for (var d = 0, b = this.listeners_.length; d < b; ++d)google.maps.event.removeListener(this.listeners_[d])
            };
            Tfl.Maps.Framework.HtmlMarker.prototype.wiredup = function (d) {
                var b = this;
                b.getProjection() ===
                undefined ? window.setTimeout(function () {
                    b.wiredup(d)
                }, 10) : d()
            };
            Tfl.Maps.Framework.HtmlMarker.prototype.measure = function () {
                this.renderForDraw(true);
                return this.pixelBoundingBox
            };
            Tfl.Maps.Framework.HtmlMarker.prototype.renderForDraw = function (d) {
                var b = this.getXYPosition(), a = this.div_, c = 0, e = 0, h = this.get("html").toString();
                if (d) {
                    if (c = Tfl.Maps.Framework.HtmlMarker.measurements[h]) {
                        this.pixelBoundingBox = {ne: {x: b.x, y: b.y}, sw: {x: b.x + c.width, y: b.y + c.height}};
                        return
                    }
                    $(a).appendTo($(".map")[0])
                }
                $(a).html(h);
                $(a).addClass("html-pin");
                e = $(a).width();
                c = $(a).height();
                var j = e / 2, f = c;
                if (this.iconAnchor) {
                    if (this.iconAnchor.x)j = this.iconAnchor.x;
                    if (this.iconAnchor.y)if (this.iconAnchor.offsetFromBottom)f += this.iconAnchor.y; else f = this.iconAnchor.y
                }
                a.style.top = b.y - f + "px";
                a.style.left = b.x - j + "px";
                a.style.display = "block";
                a.style.zIndex = this.get("zIndex").toString();
                if (d) {
                    this.pixelBoundingBox = {ne: {x: b.x, y: b.y}, sw: {x: b.x + e, y: b.y + c}};
                    $(a).html("");
                    Tfl.Maps.Framework.HtmlMarker.measurements[h] = {height: c, width: e, xOffset: j, yOffset: f}
                }
            };
            Tfl.Maps.Framework.HtmlMarker.prototype.draw =
                function () {
                    this.renderForDraw(false)
                };
            Tfl.Maps.Framework.HtmlMarker.prototype.getXYPosition = function () {
                return this.getProjection().fromLatLngToDivPixel(this.getPosition())
            };
            Tfl.Maps.Framework.HtmlMarker.prototype.getPosition = function () {
                return this.get("position")
            };
            Tfl.Maps.Framework.HtmlMarker.prototype.setVisible = function (d) {
                d ? $(this.div_).show() : $(this.div_).hide()
            }
        }
    }
});
steal.loaded("framework/map/overlays/html_marker.js");
steal("jquery/view", "jquery/lang/string/rsplit").then(function (d) {
    var b = function (o) {
        eval(o)
    }, a = d.String.rsplit, c = d.extend, e = d.isArray, h = /\r\n/g, j = /\r/g, f = /\n/g, g = /\n/, k = /\\/g, m = /"/g, t = /'/g, z = /\t/g, y = /\{/g, D = /\}/g, s = /\s*\(([\$\w]+)\)\s*->([^\n]*)/, A = function (o) {
        return o.replace(k, "\\\\").replace(f, "\\n").replace(m, '\\"').replace(z, "\\t")
    }, x = function (o) {
        return o.replace(/&/g, "&amp;").replace(/</g, "&lt;").replace(/>/g, "&gt;").replace(m, "&#34;").replace(t, "&#39;")
    }, n = d.View, i = function (o) {
        var u = o.match(y);
        o = o.match(D);
        return(u ? u.length : 0) - (o ? o.length : 0)
    }, l = function (o) {
        if (this.constructor != l) {
            var u = new l(o);
            return function (w, C) {
                return u.render(w, C)
            }
        }
        if (typeof o == "function")this.template = {fn: o}; else {
            c(this, l.options, o);
            this.template = q(this.text, this.type, this.name)
        }
    };
    window.jQuery && (jQuery.EJS = l);
    l.prototype.render = function (o, u) {
        o = o || {};
        this._extra_helpers = u;
        u = new l.Helpers(o, u || {});
        return this.template.fn.call(o, o, u)
    };
    c(l, {text: function (o) {
        if (typeof o == "string")return o;
        if (o === null || o === undefined)return"";
        var u = o.hookup && function (w, C) {
            o.hookup.call(o, w, C)
        } || typeof o == "function" && o || e(o) && function (w, C) {
            for (var B = 0; B < o.length; B++)o[B].hookup ? o[B].hookup(w, C) : o[B](w, C)
        };
        if (u)return"data-view-id='" + n.hookup(u) + "'";
        return o.toString ? o.toString() : ""
    }, clean: function (o) {
        return typeof o == "string" ? x(o) : typeof o == "number" ? o : l.text(o)
    }, options: {type: "<", ext: ".ejs"}});
    var r = function (o, u, w) {
        u = a(u, g);
        for (var C = 0; C < u.length; C++)v(o, u[C], w)
    }, v = function (o, u, w) {
        o.lines++;
        u = a(u, o.splitter);
        for (var C, B = 0; B < u.length; B++) {
            C =
                u[B];
            C !== null && w(C, o)
        }
    }, p = function (o, u) {
        var w = {};
        c(w, {left: o + "%", right: "%" + u, dLeft: o + "%%", dRight: "%%" + u, eeLeft: o + "%==", eLeft: o + "%=", cmnt: o + "%#", scan: r, lines: 0});
        w.splitter = new RegExp("(" + [w.dLeft, w.dRight, w.eeLeft, w.eLeft, w.cmnt, w.left, w.right + "\n", w.right, "\n"].join(")|(").replace(/\[/g, "\\[").replace(/\]/g, "\\]") + ")");
        return w
    }, q = function (o, u, w) {
        o = o.replace(h, "\n").replace(j, "\n");
        u = u || "<";
        var C = new l.Buffer(["var ___v1ew = [];"], []), B = "", K = function (J) {
            C.push("___v1ew.push(", '"', A(J), '");')
        }, M =
            null, O = function () {
            B = ""
        }, P = [];
        r(p(u, u === "[" ? "]" : ">"), o || "", function (J, H) {
            if (M === null)switch (J) {
                case "\n":
                    B += "\n";
                    K(B);
                    C.cr();
                    O();
                    break;
                case H.left:
                case H.eLeft:
                case H.eeLeft:
                case H.cmnt:
                    M = J;
                    B.length > 0 && K(B);
                    O();
                    break;
                case H.dLeft:
                    B += H.left;
                    break;
                default:
                    B += J;
                    break
            } else switch (J) {
                case H.right:
                    switch (M) {
                        case H.left:
                            J = i(B);
                            H = P.length && J == -1 ? P.pop() : ";";
                            H === "));" && C.push("return ___v1ew.join('')");
                            C.push(B, H);
                            J === 1 && P.push(";");
                            break;
                        case H.eLeft:
                            (J = i(B)) && P.push("));");
                            if (s.test(B)) {
                                H = B.match(s);
                                B = "function(__){var " +
                                    H[1] + "=$(__);" + H[2] + "}"
                            }
                            C.push("___v1ew.push(", "jQuery.EJS.clean(", B, J ? "var ___v1ew = [];" : "));");
                            break;
                        case H.eeLeft:
                            (J = i(B)) && P.push("));");
                            C.push("___v1ew.push(", "jQuery.EJS.text(", B, J ? "var ___v1ew = [];" : "));");
                            break
                    }
                    M = null;
                    O();
                    break;
                case H.dRight:
                    B += H.right;
                    break;
                default:
                    B += J;
                    break
            }
        });
        B.length > 0 && C.push("___v1ew.push(", '"', A(B) + '");');
        o = {out: "try { with(_VIEW) { with (_CONTEXT) {" + C.close() + " return ___v1ew.join('')}}}catch(e){e.lineNumber=null;throw e;}"};
        b.call(o, "this.fn = (function(_CONTEXT,_VIEW){" +
            o.out + "});\r\n//@ sourceURL=" + w + ".js");
        return o
    };
    l.Buffer = function (o, u) {
        this.line = [];
        this.script = [];
        this.post = u;
        this.push.apply(this, o)
    };
    l.Buffer.prototype = {push: function () {
        this.line.push.apply(this.line, arguments)
    }, cr: function () {
        this.script.push(this.line.join(""), "\n");
        this.line = []
    }, close: function () {
        if (this.line.length > 0) {
            this.script.push(this.line.join(""));
            this.line = []
        }
        this.post.length && this.push.apply(this, this.post);
        this.script.push(";");
        return this.script.join("")
    }};
    l.Helpers = function (o, u) {
        this._data =
            o;
        this._extras = u;
        c(this, u)
    };
    l.Helpers.prototype = {plugin: function () {
        var o = d.makeArray(arguments), u = o.shift();
        return function (w) {
            w = d(w);
            w[u].apply(w, o)
        }
    }, view: function (o, u, w) {
        w = w || this._extras;
        u = u || this._data;
        return n(o, u, w)
    }};
    n.register({suffix: "ejs", script: function (o, u) {
        return"jQuery.EJS(function(_CONTEXT,_VIEW) { " + (new l({text: u, name: o})).template.out + " })"
    }, renderer: function (o, u) {
        return l({text: u, name: o})
    }})
});
steal.loaded("jquery/view/ejs/ejs.js");
(function (d, b) {
    function a() {
        if (!c.READY) {
            c.event.determineEventTypes();
            for (var f in c.gestures)c.gestures.hasOwnProperty(f) && c.detection.register(c.gestures[f]);
            c.event.onTouch(c.DOCUMENT, c.EVENT_MOVE, c.detection.detect);
            c.event.onTouch(c.DOCUMENT, c.EVENT_END, c.detection.detect);
            c.READY = true
        }
    }

    var c = function (f, g) {
        return new c.Instance(f, g || {})
    };
    c.defaults = {stop_browser_behavior: {userSelect: "none", touchAction: "none", touchCallout: "none", contentZooming: "none", userDrag: "none", tapHighlightColor: "rgba(0,0,0,0)"}};
    c.HAS_POINTEREVENTS = navigator.pointerEnabled || navigator.msPointerEnabled;
    c.HAS_TOUCHEVENTS = "ontouchstart"in d;
    c.MOBILE_REGEX = /mobile|tablet|ip(ad|hone|od)|android/i;
    c.NO_MOUSEEVENTS = c.HAS_TOUCHEVENTS && navigator.userAgent.match(c.MOBILE_REGEX);
    c.EVENT_TYPES = {};
    c.DIRECTION_DOWN = "down";
    c.DIRECTION_LEFT = "left";
    c.DIRECTION_UP = "up";
    c.DIRECTION_RIGHT = "right";
    c.POINTER_MOUSE = "mouse";
    c.POINTER_TOUCH = "touch";
    c.POINTER_PEN = "pen";
    c.EVENT_START = "start";
    c.EVENT_MOVE = "move";
    c.EVENT_END = "end";
    c.DOCUMENT = document;
    c.plugins = {};
    c.READY = false;
    c.Instance = function (f, g) {
        var k = this;
        a();
        this.element = f;
        this.enabled = true;
        this.options = c.utils.extend(c.utils.extend({}, c.defaults), g || {});
        this.options.stop_browser_behavior && c.utils.stopDefaultBrowserBehavior(this.element, this.options.stop_browser_behavior);
        c.event.onTouch(f, c.EVENT_START, function (m) {
            k.enabled && c.detection.startDetect(k, m)
        });
        return this
    };
    c.Instance.prototype = {on: function (f, g) {
        f = f.split(" ");
        for (var k = 0; k < f.length; k++)this.element.addEventListener(f[k], g,
            false);
        return this
    }, off: function (f, g) {
        f = f.split(" ");
        for (var k = 0; k < f.length; k++)this.element.removeEventListener(f[k], g, false);
        return this
    }, trigger: function (f, g) {
        var k = c.DOCUMENT.createEvent("Event");
        k.initEvent(f, true, true);
        k.gesture = g;
        f = this.element;
        if (c.utils.hasParent(g.target, f))f = g.target;
        f.dispatchEvent(k);
        return this
    }, enable: function (f) {
        this.enabled = f;
        return this
    }};
    var e = null, h = false, j = false;
    c.event = {bindDom: function (f, g, k) {
        g = g.split(" ");
        for (var m = 0; m < g.length; m++)f.addEventListener(g[m],
            k, false)
    }, onTouch: function (f, g, k) {
        var m = this;
        this.bindDom(f, c.EVENT_TYPES[g], function (t) {
            var z = t.type.toLowerCase();
            if (!(z.match(/mouse/) && j)) {
                if (z.match(/touch/) || z.match(/pointerdown/) || z.match(/mouse/) && t.which === 1)h = true;
                if (z.match(/touch|pointer/))j = true;
                var y = 0;
                if (h) {
                    if (c.HAS_POINTEREVENTS && g != c.EVENT_END)y = c.PointerEvent.updatePointer(g, t); else if (z.match(/touch/))y = t.touches.length; else j || (y = z.match(/up/) ? 0 : 1);
                    if (y > 0 && g == c.EVENT_END)g = c.EVENT_MOVE; else if (!y)g = c.EVENT_END;
                    if (!y && e !== null)t =
                        e; else e = t;
                    k.call(c.detection, m.collectEventData(f, g, t));
                    if (c.HAS_POINTEREVENTS && g == c.EVENT_END)y = c.PointerEvent.updatePointer(g, t)
                }
                if (!y) {
                    e = null;
                    j = h = false;
                    c.PointerEvent.reset()
                }
            }
        })
    }, determineEventTypes: function () {
        var f;
        f = c.HAS_POINTEREVENTS ? c.PointerEvent.getEvents() : c.NO_MOUSEEVENTS ? ["touchstart", "touchmove", "touchend touchcancel"] : ["touchstart mousedown", "touchmove mousemove", "touchend touchcancel mouseup"];
        c.EVENT_TYPES[c.EVENT_START] = f[0];
        c.EVENT_TYPES[c.EVENT_MOVE] = f[1];
        c.EVENT_TYPES[c.EVENT_END] =
            f[2]
    }, getTouchList: function (f) {
        return c.HAS_POINTEREVENTS ? c.PointerEvent.getTouchList() : f.touches ? f.touches : [
            {identifier: 1, pageX: f.pageX, pageY: f.pageY, target: f.target}
        ]
    }, collectEventData: function (f, g, k) {
        f = this.getTouchList(k, g);
        var m = c.POINTER_TOUCH;
        if (k.type.match(/mouse/) || c.PointerEvent.matchType(c.POINTER_MOUSE, k))m = c.POINTER_MOUSE;
        return{center: c.utils.getCenter(f), timeStamp: (new Date).getTime(), target: k.target, touches: f, eventType: g, pointerType: m, srcEvent: k, preventDefault: function () {
            this.srcEvent.preventManipulation &&
            this.srcEvent.preventManipulation();
            this.srcEvent.preventDefault && this.srcEvent.preventDefault()
        }, stopPropagation: function () {
            this.srcEvent.stopPropagation()
        }, stopDetect: function () {
            return c.detection.stopDetect()
        }}
    }};
    c.PointerEvent = {pointers: {}, getTouchList: function () {
        var f = this, g = [];
        Object.keys(f.pointers).sort().forEach(function (k) {
            g.push(f.pointers[k])
        });
        return g
    }, updatePointer: function (f, g) {
        if (f == c.EVENT_END)this.pointers = {}; else {
            g.identifier = g.pointerId;
            this.pointers[g.pointerId] = g
        }
        return Object.keys(this.pointers).length
    },
        matchType: function (f, g) {
            if (!g.pointerType)return false;
            var k = {};
            k[c.POINTER_MOUSE] = g.pointerType == g.MSPOINTER_TYPE_MOUSE || g.pointerType == c.POINTER_MOUSE;
            k[c.POINTER_TOUCH] = g.pointerType == g.MSPOINTER_TYPE_TOUCH || g.pointerType == c.POINTER_TOUCH;
            k[c.POINTER_PEN] = g.pointerType == g.MSPOINTER_TYPE_PEN || g.pointerType == c.POINTER_PEN;
            return k[f]
        }, getEvents: function () {
            return["pointerdown MSPointerDown", "pointermove MSPointerMove", "pointerup pointercancel MSPointerUp MSPointerCancel"]
        }, reset: function () {
            this.pointers =
            {}
        }};
    c.utils = {extend: function (f, g, k) {
        for (var m in g)f[m] !== b && k || (f[m] = g[m]);
        return f
    }, hasParent: function (f, g) {
        for (; f;) {
            if (f == g)return true;
            f = f.parentNode
        }
        return false
    }, getCenter: function (f) {
        for (var g = [], k = [], m = 0, t = f.length; m < t; m++) {
            g.push(f[m].pageX);
            k.push(f[m].pageY)
        }
        return{pageX: (Math.min.apply(Math, g) + Math.max.apply(Math, g)) / 2, pageY: (Math.min.apply(Math, k) + Math.max.apply(Math, k)) / 2}
    }, getVelocity: function (f, g, k) {
        return{x: Math.abs(g / f) || 0, y: Math.abs(k / f) || 0}
    }, getAngle: function (f, g) {
        return Math.atan2(g.pageY -
            f.pageY, g.pageX - f.pageX) * 180 / Math.PI
    }, getDirection: function (f, g) {
        var k = Math.abs(f.pageX - g.pageX), m = Math.abs(f.pageY - g.pageY);
        return k >= m ? f.pageX - g.pageX > 0 ? c.DIRECTION_LEFT : c.DIRECTION_RIGHT : f.pageY - g.pageY > 0 ? c.DIRECTION_UP : c.DIRECTION_DOWN
    }, getDistance: function (f, g) {
        var k = g.pageX - f.pageX;
        f = g.pageY - f.pageY;
        return Math.sqrt(k * k + f * f)
    }, getScale: function (f, g) {
        if (f.length >= 2 && g.length >= 2)return this.getDistance(g[0], g[1]) / this.getDistance(f[0], f[1]);
        return 1
    }, getRotation: function (f, g) {
        if (f.length >= 2 && g.length >=
            2)return this.getAngle(g[1], g[0]) - this.getAngle(f[1], f[0]);
        return 0
    }, isVertical: function (f) {
        return f == c.DIRECTION_UP || f == c.DIRECTION_DOWN
    }, stopDefaultBrowserBehavior: function (f, g) {
        var k, m = ["webkit", "khtml", "moz", "ms", "o", ""];
        if (g && f.style) {
            for (var t = 0; t < m.length; t++)for (var z in g)if (g.hasOwnProperty(z)) {
                k = z;
                if (m[t])k = m[t] + k.substring(0, 1).toUpperCase() + k.substring(1);
                f.style[k] = g[z]
            }
            if (g.userSelect == "none")f.onselectstart = function () {
                return false
            }
        }
    }};
    c.detection = {gestures: [], current: null, previous: null,
        stopped: false, startDetect: function (f, g) {
            if (!this.current) {
                this.stopped = false;
                this.current = {inst: f, startEvent: c.utils.extend({}, g), lastEvent: false, name: ""};
                this.detect(g)
            }
        }, detect: function (f) {
            if (!(!this.current || this.stopped)) {
                f = this.extendEventData(f);
                for (var g = this.current.inst.options, k = 0, m = this.gestures.length; k < m; k++) {
                    var t = this.gestures[k];
                    if (!this.stopped && g[t.name] !== false)if (t.handler.call(t, f, this.current.inst) === false) {
                        this.stopDetect();
                        break
                    }
                }
                if (this.current)this.current.lastEvent = f;
                f.eventType ==
                c.EVENT_END && !f.touches.length - 1 && this.stopDetect();
                return f
            }
        }, stopDetect: function () {
            this.previous = c.utils.extend({}, this.current);
            this.current = null;
            this.stopped = true
        }, extendEventData: function (f) {
            var g = this.current.startEvent;
            if (g && (f.touches.length != g.touches.length || f.touches === g.touches)) {
                g.touches = [];
                for (var k = 0, m = f.touches.length; k < m; k++)g.touches.push(c.utils.extend({}, f.touches[k]))
            }
            k = f.timeStamp - g.timeStamp;
            m = f.center.pageX - g.center.pageX;
            var t = f.center.pageY - g.center.pageY, z = c.utils.getVelocity(k,
                m, t);
            c.utils.extend(f, {deltaTime: k, deltaX: m, deltaY: t, velocityX: z.x, velocityY: z.y, distance: c.utils.getDistance(g.center, f.center), angle: c.utils.getAngle(g.center, f.center), direction: c.utils.getDirection(g.center, f.center), scale: c.utils.getScale(g.touches, f.touches), rotation: c.utils.getRotation(g.touches, f.touches), startEvent: g});
            return f
        }, register: function (f) {
            var g = f.defaults || {};
            if (g[f.name] === b)g[f.name] = true;
            c.utils.extend(c.defaults, g, true);
            f.index = f.index || 1E3;
            this.gestures.push(f);
            this.gestures.sort(function (k, m) {
                if (k.index < m.index)return-1;
                if (k.index > m.index)return 1;
                return 0
            });
            return this.gestures
        }};
    c.gestures = c.gestures || {};
    c.gestures.Hold = {name: "hold", index: 10, defaults: {hold_timeout: 500, hold_threshold: 1}, timer: null, handler: function (f, g) {
        switch (f.eventType) {
            case c.EVENT_START:
                clearTimeout(this.timer);
                c.detection.current.name = this.name;
                this.timer = setTimeout(function () {
                    c.detection.current.name == "hold" && g.trigger("hold", f)
                }, g.options.hold_timeout);
                break;
            case c.EVENT_MOVE:
                f.distance > g.options.hold_threshold &&
                clearTimeout(this.timer);
                break;
            case c.EVENT_END:
                clearTimeout(this.timer);
                break
        }
    }};
    c.gestures.Tap = {name: "tap", index: 100, defaults: {tap_max_touchtime: 250, tap_max_distance: 10, tap_always: true, doubletap_distance: 20, doubletap_interval: 300}, handler: function (f, g) {
        if (f.eventType == c.EVENT_END) {
            var k = c.detection.previous, m = false;
            if (!(f.deltaTime > g.options.tap_max_touchtime || f.distance > g.options.tap_max_distance)) {
                if (k && k.name == "tap" && f.timeStamp - k.lastEvent.timeStamp < g.options.doubletap_interval && f.distance <
                    g.options.doubletap_distance) {
                    g.trigger("doubletap", f);
                    m = true
                }
                if (!m || g.options.tap_always) {
                    c.detection.current.name = "tap";
                    g.trigger(c.detection.current.name, f)
                }
            }
        }
    }};
    c.gestures.Swipe = {name: "swipe", index: 40, defaults: {swipe_max_touches: 1, swipe_velocity: 0.7}, handler: function (f, g) {
        if (f.eventType == c.EVENT_END)if (!(g.options.swipe_max_touches > 0 && f.touches.length > g.options.swipe_max_touches))if (f.velocityX > g.options.swipe_velocity || f.velocityY > g.options.swipe_velocity) {
            g.trigger(this.name, f);
            g.trigger(this.name +
                f.direction, f)
        }
    }};
    c.gestures.Drag = {name: "drag", index: 50, defaults: {drag_min_distance: 10, drag_max_touches: 1, drag_block_horizontal: false, drag_block_vertical: false, drag_lock_to_axis: false, drag_lock_min_distance: 25}, triggered: false, handler: function (f, g) {
        if (c.detection.current.name != this.name && this.triggered) {
            g.trigger(this.name + "end", f);
            this.triggered = false
        } else if (!(g.options.drag_max_touches > 0 && f.touches.length > g.options.drag_max_touches))switch (f.eventType) {
            case c.EVENT_START:
                this.triggered = false;
                break;
            case c.EVENT_MOVE:
                if (f.distance < g.options.drag_min_distance && c.detection.current.name != this.name)return;
                c.detection.current.name = this.name;
                if (c.detection.current.lastEvent.drag_locked_to_axis || g.options.drag_lock_to_axis && g.options.drag_lock_min_distance <= f.distance)f.drag_locked_to_axis = true;
                var k = c.detection.current.lastEvent.direction;
                if (f.drag_locked_to_axis && k !== f.direction)f.direction = c.utils.isVertical(k) ? f.deltaY < 0 ? c.DIRECTION_UP : c.DIRECTION_DOWN : f.deltaX < 0 ? c.DIRECTION_LEFT : c.DIRECTION_RIGHT;
                if (!this.triggered) {
                    g.trigger(this.name + "start", f);
                    this.triggered = true
                }
                g.trigger(this.name, f);
                g.trigger(this.name + f.direction, f);
                if (g.options.drag_block_vertical && c.utils.isVertical(f.direction) || g.options.drag_block_horizontal && !c.utils.isVertical(f.direction))f.preventDefault();
                break;
            case c.EVENT_END:
                this.triggered && g.trigger(this.name + "end", f);
                this.triggered = false;
                break
        }
    }};
    c.gestures.Transform = {name: "transform", index: 45, defaults: {transform_min_scale: 0.01, transform_min_rotation: 1, transform_always_block: false},
        triggered: false, handler: function (f, g) {
            if (c.detection.current.name != this.name && this.triggered) {
                g.trigger(this.name + "end", f);
                this.triggered = false
            } else if (!(f.touches.length < 2)) {
                g.options.transform_always_block && f.preventDefault();
                switch (f.eventType) {
                    case c.EVENT_START:
                        this.triggered = false;
                        break;
                    case c.EVENT_MOVE:
                        var k = Math.abs(1 - f.scale), m = Math.abs(f.rotation);
                        if (k < g.options.transform_min_scale && m < g.options.transform_min_rotation)return;
                        c.detection.current.name = this.name;
                        if (!this.triggered) {
                            g.trigger(this.name +
                                "start", f);
                            this.triggered = true
                        }
                        g.trigger(this.name, f);
                        m > g.options.transform_min_rotation && g.trigger("rotate", f);
                        if (k > g.options.transform_min_scale) {
                            g.trigger("pinch", f);
                            g.trigger("pinch" + (f.scale < 1 ? "in" : "out"), f)
                        }
                        break;
                    case c.EVENT_END:
                        this.triggered && g.trigger(this.name + "end", f);
                        this.triggered = false;
                        break
                }
            }
        }};
    c.gestures.Touch = {name: "touch", index: -Infinity, defaults: {prevent_default: false, prevent_mouseevents: false}, handler: function (f, g) {
        if (g.options.prevent_mouseevents && f.pointerType == c.POINTER_MOUSE)f.stopDetect();
        else {
            g.options.prevent_default && f.preventDefault();
            f.eventType == c.EVENT_START && g.trigger(this.name, f)
        }
    }};
    c.gestures.Release = {name: "release", index: Infinity, handler: function (f, g) {
        f.eventType == c.EVENT_END && g.trigger(this.name, f)
    }};
    if (typeof module === "object" && typeof module.exports === "object")module.exports = c; else {
        d.Hammer = c;
        typeof d.define === "function" && d.define.amd && d.define("hammer", [], function () {
            return c
        })
    }
})(this);
(function (d, b) {
    if (d !== b) {
        Hammer.event.bindDom = function (a, c, e) {
            d(a).on(c, function (h) {
                var j = h.originalEvent || h;
                if (j.pageX === b) {
                    j.pageX = h.pageX;
                    j.pageY = h.pageY
                }
                if (!j.target)j.target = h.target;
                if (j.which === b)j.which = j.button;
                if (!j.preventDefault)j.preventDefault = h.preventDefault;
                if (!j.stopPropagation)j.stopPropagation = h.stopPropagation;
                e.call(this, j)
            })
        };
        Hammer.Instance.prototype.on = function (a, c) {
            return d(this.element).on(a, c)
        };
        Hammer.Instance.prototype.off = function (a, c) {
            return d(this.element).off(a, c)
        };
        Hammer.Instance.prototype.trigger = function (a, c) {
            var e = d(this.element);
            if (e.has(c.target).length)e = d(c.target);
            return e.trigger({type: a, gesture: c})
        };
        d.fn.hammer = function (a) {
            return this.each(function () {
                var c = d(this), e = c.data("hammer");
                if (e)e && a && Hammer.utils.extend(e.options, a); else c.data("hammer", new Hammer(this, a || {}))
            })
        }
    }
})(window.jQuery || window.Zepto);
steal.loaded("lib/jquery.hammer.js");
steal("jquery").then(function (d) {
    var b = function (n) {
        return n.replace(/^\/\//, "").replace(/[\/\.]/g, "_")
    }, a = d.makeArray, c = 1, e = d.View = function (n, i, l, r) {
        if (typeof l === "function") {
            r = l;
            l = undefined
        }
        var v = g(i);
        if (v.length) {
            var p = d.Deferred();
            v.push(j(n, true));
            d.when.apply(d, v).then(function (o) {
                var u = a(arguments), w = u.pop()[0];
                if (f(i))i = k(o); else for (var C in i)if (f(i[C]))i[C] = k(u.shift());
                u = w(i, l);
                p.resolve(u);
                r && r(u)
            });
            return p.promise()
        } else {
            var q;
            v = typeof r === "function";
            p = j(n, v);
            if (v) {
                q = p;
                p.done(function (o) {
                    r(o(i,
                        l))
                })
            } else p.done(function (o) {
                q = o(i, l)
            });
            return q
        }
    }, h = function (n, i) {
        if (!n.match(/[^\s]/))throw"$.View ERROR: There is no template or an empty template at " + i;
    }, j = function (n, i) {
        return d.ajax({url: n, dataType: "view", async: i})
    }, f = function (n) {
        return n && d.isFunction(n.always)
    }, g = function (n) {
        var i = [];
        if (f(n))return[n]; else for (var l in n)f(n[l]) && i.push(n[l]);
        return i
    }, k = function (n) {
        return d.isArray(n) && n.length === 3 && n[1] === "success" ? n[0] : n
    };
    d.ajaxTransport("view", function (n, i) {
        var l = i.url;
        n = l.match(/\.[\w\d]+$/);
        var r, v, p, q, o = function (w) {
            w = r.renderer(p, w);
            if (e.cache)e.cached[p] = w;
            return{view: w}
        };
        if (v = document.getElementById(l))n = "." + v.type.match(/\/(x\-)?(.+)/)[2];
        if (!n) {
            n = e.ext;
            l += e.ext
        }
        p = b(l);
        if (l.match(/^\/\//)) {
            var u = l.substr(2);
            l = typeof steal === "undefined" ? (l = "/" + u) : steal.root.mapJoin(u) + ""
        }
        r = e.types[n];
        return{send: function (w, C) {
            if (e.cached[p])return C(200, "success", {view: e.cached[p]}); else if (v)C(200, "success", o(v.innerHTML)); else q = d.ajax({async: i.async, url: l, dataType: "text", error: function () {
                h("",
                    l);
                C(404)
            }, success: function (B) {
                h(B, l);
                C(200, "success", o(B))
            }})
        }, abort: function () {
            q && q.abort()
        }}
    });
    d.extend(e, {hookups: {}, hookup: function (n) {
        var i = ++c;
        e.hookups[i] = n;
        return i
    }, cached: {}, cache: true, register: function (n) {
        this.types["." + n.suffix] = n;
        window.steal && steal.type(n.suffix + " view js", function (i, l) {
            var r = e.types["." + i.type], v = b(i.rootSrc + "");
            i.text = r.script(v, i.text);
            l()
        })
    }, types: {}, ext: ".ejs", registerScript: function (n, i, l) {
        return"$.View.preload('" + i + "'," + e.types["." + n].script(i, l) + ");"
    }, preload: function (n, i) {
        e.cached[n] = function (l, r) {
            return i.call(l, l, r)
        }
    }});
    window.steal && steal.type("view js", function (n, i) {
        var l = e.types["." + n.type], r = b(n.rootSrc + "");
        n.text = "steal('" + (l.plugin || "jquery/view/" + n.type) + "').then(function($){$.View.preload('" + r + "'," + n.text + ");\n})";
        i()
    });
    var m, t, z, y, D, s, A, x = {val: true, text: true};
    m = function (n) {
        var i = d.fn[n];
        d.fn[n] = function () {
            var l = a(arguments), r, v, p = this;
            if (f(l[0])) {
                l[0].done(function (q) {
                    t.call(p, [q], i)
                });
                return this
            } else if (z(l)) {
                if (r = s(l)) {
                    v = l[r];
                    l[r] = function (q) {
                        t.call(p,
                            [q], i);
                        v.call(p, q)
                    };
                    e.apply(e, l);
                    return this
                }
                l = e.apply(e, l);
                if (f(l)) {
                    l.done(function (q) {
                        t.call(p, [q], i)
                    });
                    return this
                } else l = [l]
            }
            return x[n] ? i.apply(this, l) : t.call(this, l, i)
        }
    };
    t = function (n, i) {
        var l;
        for (var r in e.hookups)break;
        if (r && n[0] && y(n[0])) {
            l = e.hookups;
            e.hookups = {};
            n[0] = d(n[0])
        }
        i = i.apply(this, n);
        l && A(n[0], l);
        return i
    };
    z = function (n) {
        var i = typeof n[1];
        return typeof n[0] == "string" && (i == "object" || i == "function") && !D(n[1])
    };
    D = function (n) {
        return n.nodeType || n.jquery
    };
    y = function (n) {
        if (D(n))return true;
        else if (typeof n === "string") {
            n = d.trim(n);
            return n.substr(0, 1) === "<" && n.substr(n.length - 1, 1) === ">" && n.length >= 3
        } else return false
    };
    s = function (n) {
        return typeof n[3] === "function" ? 3 : typeof n[2] === "function" && 2
    };
    A = function (n, i) {
        var l, r = 0, v, p;
        n = n.filter(function () {
            return this.nodeType != 3
        });
        n = n.add("[data-view-id]", n);
        for (l = n.length; r < l; r++)if (n[r].getAttribute && (v = n[r].getAttribute("data-view-id")) && (p = i[v])) {
            p(n[r], v);
            delete i[v];
            n[r].removeAttribute("data-view-id")
        }
        d.extend(e.hookups, i)
    };
    d.fn.hookup =
        function () {
            var n = e.hookups;
            e.hookups = {};
            A(this, n);
            return this
        };
    d.each(["prepend", "append", "after", "before", "text", "html", "replaceWith", "val"], function (n, i) {
        m(i)
    })
});
steal.loaded("jquery/view/view.js");
steal("jquery/lang/string", function (d) {
    d.String.rsplit = function (b, a) {
        for (var c = a.exec(b), e = [], h; c !== null;) {
            h = c.index;
            if (h !== 0) {
                e.push(b.substring(0, h));
                b = b.slice(h)
            }
            e.push(c[0]);
            b = b.slice(c[0].length);
            c = a.exec(b)
        }
        b !== "" && e.push(b);
        return e
    }
});
steal.loaded("jquery/lang/string/rsplit/rsplit.js");
steal("jquery/view/ejs").then(function (d) {
    d.View.preload("framework_map_options_options_dialog_ejs", jQuery.EJS(function (b, a) {
        try {
            with (a)with (b) {
                var c = [];
                c.push("<div class='map-options-dialog'>\n");
                c.push("\t<div class='map-options-dialog-container'>\n");
                c.push("\t\t<div class='map-options-dialog-background'></div>\n");
                c.push("\t\t<div class='map-options-dialog-inner'>\n");
                c.push("      <div class='map-options-title'>\n");
                c.push("        <div class='map-options-hamburger'></div>\n");
                c.push("        <h1 class='map-options-title-text'>Map options</h1>\n");
                c.push("        <div class='map-options-close-button'></div>\n");
                c.push("      </div>\n");
                c.push("      <div class='map-options-dialog-scroll'>\n");
                c.push("        <div class='map-options-dialog-content'>\n");
                c.push("          \n");
                c.push("          <div class='map-options-map-type-selector-container'>\n");
                c.push("            <div class='map-options-map-type-selector'>\n");
                c.push("              ");
                d.each(mapTypeOptions, function (h, j) {
                    c.push("\n");
                    c.push("              <div class='map-options-map-type-selector-option' data-maptype='");
                    c.push(jQuery.EJS.clean(j.value));
                    c.push("'>\n");
                    c.push("                <div class='map-options-map-type-selector-option-");
                    c.push(jQuery.EJS.clean(j.value));
                    c.push("'></div>\n");
                    c.push("                <div class='map-options-map-type-selector-name'>\n");
                    c.push("                  ");
                    c.push(jQuery.EJS.clean(j.name));
                    c.push("\n");
                    c.push("                </div>\n");
                    c.push("                <div class='map-options-map-type-selector-toggle'></div>\n");
                    c.push("              </div>\n");
                    c.push("              ")
                });
                c.push("\n");
                c.push("            </div>\n");
                c.push("          </div>\n");
                c.push("\n");
                c.push("          ");
                if (optionalLayerColumns.length > 0) {
                    c.push("            \n");
                    c.push("            <div class='map-options-layers'>\n");
                    c.push("              ");
                    d.each(optionalLayerColumns, function (h, j) {
                        c.push("\n");
                        c.push("                <div class='map-options-column-");
                        c.push(jQuery.EJS.clean(h + 1));
                        c.push("-of-");
                        c.push(jQuery.EJS.clean(optionalLayerColumns.length));
                        c.push("'>\n");
                        c.push("                  ");
                        d.each(j, function (f, g) {
                            c.push("\n");
                            c.push("                    <div class='map-options-layer-container' data-name='");
                            c.push(jQuery.EJS.clean(g.name));
                            c.push("'>\n");
                            c.push("                    ");
                            if (g.filters) {
                                c.push("\n");
                                c.push("                      ");
                                d.each(g.filters, function (k, m) {
                                    c.push("\n");
                                    c.push("                        ");
                                    d.each(m.filterValues, function (t, z) {
                                        c.push("\n");
                                        c.push("                        <div  class='map-options-layer-filter' data-name='");
                                        c.push(jQuery.EJS.clean(g.name));
                                        c.push("' data-filtername='");
                                        c.push(jQuery.EJS.clean(m.name));
                                        c.push("' data-filtervaluename='");
                                        c.push(jQuery.EJS.clean(z.name));
                                        c.push("'>\n");
                                        c.push("                          <div class='map-options-layer-icon map-options-layer-icon-");
                                        c.push(jQuery.EJS.clean(z.name.toLowerCase()));
                                        c.push("'></div>\n");
                                        c.push("                          ");
                                        c.push(jQuery.EJS.clean(z.description));
                                        c.push("\n");
                                        c.push("                          <div class='map-options-layer-filter-toggle'></div>\n");
                                        c.push("                        </div>\n");
                                        c.push("                        ")
                                    });
                                    c.push("\n");
                                    c.push("                      ")
                                });
                                c.push("\n")
                            } else {
                                c.push("\n");
                                c.push("                      <div class='map-options-layer' data-name='");
                                c.push(jQuery.EJS.clean(g.name));
                                c.push("'>\n");
                                c.push("                        <div class='map-options-layer-inner'>\n");
                                c.push("                          <div class='map-options-layer-icon map-options-layer-icon-");
                                c.push(jQuery.EJS.clean(g.classSuffix));
                                c.push("'>\n");
                                c.push("                          </div>\n");
                                c.push("                          ");
                                c.push(jQuery.EJS.clean(g.name));
                                c.push("\n");
                                c.push("                          <div class='map-options-layer-toggle'></div>\n");
                                c.push("                        </div>\n");
                                c.push("                      </div>\n")
                            }
                            c.push("                    ");
                            c.push("\n");
                            c.push("                    </div>\n");
                            c.push("                  ")
                        });
                        c.push("\n");
                        c.push("                </div>\n");
                        c.push("              ")
                    });
                    c.push("\n");
                    c.push("            </div>\n");
                    c.push("          ")
                }
                c.push("\n");
                c.push("          \n");
                c.push("          ");
                if (layerKeyColumns[0].length > 0) {
                    c.push("\n");
                    c.push("            <div class='map-options-key'>\n");
                    c.push("              <h1>Map Key</h1>\n");
                    c.push("                ");
                    d.each(layerKeyColumns, function (h, j) {
                        c.push("\n");
                        c.push("                <div class='map-options-column-");
                        c.push(jQuery.EJS.clean(h + 1));
                        c.push("-of-");
                        c.push(jQuery.EJS.clean(layerKeyColumns.length));
                        c.push("'>\n");
                        c.push("                  ");
                        d.each(j, function (f, g) {
                            c.push("\n");
                            c.push("                    <div class='map-options-key-item'>\n");
                            c.push("                      ");
                            if (g.iconUrl) {
                                c.push("\n");
                                c.push("                        <img src='");
                                c.push(jQuery.EJS.clean(g.iconUrl));
                                c.push("' />\n")
                            } else {
                                c.push("\n");
                                c.push("                        <div class='map-options-key-square' style='background-color:");
                                c.push(jQuery.EJS.clean(g.color));
                                c.push(";'></div>\n")
                            }
                            c.push("                      ");
                            c.push("\n");
                            c.push("                      ");
                            c.push(jQuery.EJS.clean(g.name));
                            c.push("\n");
                            c.push("                    </div>\n");
                            c.push("                  ")
                        });
                        c.push("\n");
                        c.push("                  </div>\n");
                        c.push("                ")
                    });
                    c.push("\n");
                    c.push("              \n");
                    c.push("            </div>\n");
                    c.push("          ")
                }
                c.push("\n");
                c.push("            \n");
                c.push("        </div>\n");
                c.push("      </div>\n");
                c.push("    </div>\n");
                c.push("\t</div>\n");
                c.push("</div>");
                return c.join("")
            }
        } catch (e) {
            e.lineNumber = null;
            throw e;
        }
    }))
});
steal.loaded("framework/map/options/options_dialog.ejs");
(function (d) {
    if (!("__jquery_xdomain__"in d) && /msie/.test(navigator.userAgent.toLowerCase()) && "XDomainRequest"in window && !(window.XMLHttpRequest && "withCredentials"in new XMLHttpRequest) && document.location.href.indexOf("file:///") == -1) {
        d.__jquery_xdomain__ = d.support.cors = true;
        var b = /^(((([^:\/#\?]+:)?(?:\/\/((?:(([^:@\/#\?]+)(?:\:([^:@\/#\?]+))?)@)?(([^:\/#\?]+)(?:\:([0-9]+))?))?)?)?((\/?(?:[^\/\?#]+\/+)*)([^\?#]*)))?(\?[^#]+)?)(#.*)?/, a = d.ajaxSettings.xhr, c = "XDR_SESSION_COOKIE_NAME"in window ? window.XDR_SESSION_COOKIE_NAME :
            "jsessionid", e = "XDR_COOKIE_HEADERS"in window ? window.XDR_COOKIE_HEADERS : [], h = "XDR_HEADERS"in window ? window.XDR_HEADERS : ["Content-Type"], j = {UNSENT: 0, OPENED: 1, LOADING: 3, DONE: 4}, f = window.XDR_DEBUG && "console"in window, g, k, m = 0;

        function t(s, A) {
            if (typeof s == "string")s = [s];
            var x, n;
            for (x = 0; x < s.length; x++)(n = (n = (new RegExp("(?:^|; )" + s[x] + "=([^;]*)", "i")).exec(document.cookie)) && n[1]) && A.call(null, s[x], n)
        }

        function z(s) {
            if (s.length >= 5) {
                var A = s.substring(s.length <= 20 ? 0 : s.length - 20), x = A.length - 1, n, i, l;
                if (A.charAt(x) ===
                    "~") {
                    for (n = x--; x >= 0 && A.charAt(x) !== "~"; x--);
                    i = parseInt(A.substring(x + 1, n));
                    if (!isNaN(i) && i >= 0 && x >= 2 && A.charAt(x) === "~") {
                        for (n = x--; x >= 0 && A.charAt(x) !== "~"; x--);
                        l = parseInt(A.substring(x + 1, n));
                        if (!isNaN(l) && x >= 0 && A.charAt(x) === "~") {
                            n = s.length - i - A.length + x;
                            return[l, s.substring(0, n), s.substr(n, i)]
                        }
                    }
                }
            }
            return[200, s, ""]
        }

        function y(s) {
            if (typeof s === "object")return s;
            return(s = b.exec(s)) ? {href: s[0] || "", hrefNoHash: s[1] || "", hrefNoSearch: s[2] || "", domain: s[3] || "", protocol: s[4] || "", authority: s[5] || "", username: s[7] ||
                "", password: s[8] || "", host: s[9] || "", hostname: s[10] || "", port: s[11] || "", pathname: s[12] || "", directory: s[13] || "", filename: s[14] || "", search: s[15] || "", hash: s[16] || ""} : {}
        }

        function D(s) {
            if (s.length == 0)return[];
            var A = [], x = 0, n = 0, i;
            do {
                i = s.indexOf(",", n);
                A[x] = (A[x] || "") + s.substring(n, i == -1 ? s.length : i);
                n = i + 1;
                if (A[x].indexOf("Expires=") == -1 || A[x].indexOf(",") != -1)x++; else A[x] += ","
            } while (i > 0);
            for (x = 0; x < A.length; x++) {
                s = A[x].indexOf("Domain=");
                if (s != -1)A[x] = A[x].substring(0, s) + A[x].substring(A[x].indexOf(";", s) + 1)
            }
            return A
        }

        k = y(document.location.href).domain;
        g = function () {
            var s = this, A = new XDomainRequest, x, n = [], i, l, r = m++, v = function (q) {
                s.readyState = q;
                typeof s.onreadystatechange === "function" && s.onreadystatechange.call(s)
            }, p = function (q, o) {
                if (!s.responseText)s.responseText = "";
                f && console.log("[XDR-" + r + "] request end with state " + q + " and code " + o + " and data length " + s.responseText.length);
                s.status = o;
                if (!s.responseType) {
                    x = x || A.contentType;
                    if (x.match(/\/json/)) {
                        s.responseType = "json";
                        s.response = s.responseText
                    } else if (x.match(/\/xml/)) {
                        s.responseType =
                            "document";
                        o = new ActiveXObject("Microsoft.XMLDOM");
                        o.async = false;
                        o.loadXML(s.responseText);
                        s.responseXML = s.response = o;
                        if (d(o).children("error").length != 0) {
                            o = d(o).find("error");
                            s.status = parseInt(o.attr("response_code"))
                        }
                    } else {
                        s.responseType = "text";
                        s.response = s.responseText
                    }
                }
                v(q);
                l = n = A = null
            };
            A.onprogress = function () {
                v(j.LOADING)
            };
            A.ontimeout = function () {
                p(j.DONE, 408)
            };
            A.onerror = function () {
                p(j.DONE, 500)
            };
            A.onload = function () {
                var q, o, u = z(A.responseText || "");
                f && console.log("[XDR-" + m + "] parsing cookies for header " +
                    u[2]);
                q = D(u[2]);
                s.responseText = u[1] || "";
                f && console.log("[XDR-" + r + "] raw data:\n" + A.responseText + "\n parsed response: status=" + u[0] + ", header=" + u[2] + ", data=\n" + u[1]);
                for (o = 0; o < q.length; o++) {
                    f && console.log("[XDR-" + r + "] installing cookie " + q[o]);
                    document.cookie = q[o] + ";Domain=" + document.domain
                }
                p(j.DONE, u[0])
            };
            this.readyState = j.UNSENT;
            this.status = 0;
            this.responseType = this.statusText = "";
            this.timeout = 0;
            this.withCredentials = false;
            this.overrideMimeType = function (q) {
                x = q
            };
            this.abort = function () {
                A.abort()
            };
            this.setRequestHeader =
                function (q, o) {
                    d.inArray(q, h) >= 0 && n.push({k: q, v: o})
                };
            this.open = function (q, o) {
                l = o;
                i = q;
                v(j.OPENED)
            };
            this.send = function (q) {
                A.timeout = this.timeout;
                if (c || e || n.length) {
                    var o, u = function (w, C) {
                        var B = l.indexOf("?");
                        l += (B == -1 ? "?" : "&") + w + "=" + encodeURIComponent(C);
                        f && console.log("[XDR-" + r + "] added parameter " + w + "=" + C + " => " + l)
                    };
                    for (o = 0; o < n.length; o++)u(n[o].k, n[o].v);
                    t(c, function (w, C) {
                        var B = l.indexOf("?");
                        if (B == -1)l += ";" + w + "=" + C; else l = l.substring(0, B) + ";" + w + "=" + C + l.substring(B);
                        f && console.log("[XDR-" + r + "] added cookie " +
                            l)
                    });
                    t(e, u);
                    u("_xdr", "" + r)
                }
                f && console.log("[XDR-" + r + "] opening " + l);
                A.open(i, l);
                f && console.log("[XDR-" + r + "] send, timeout=" + A.timeout);
                A.send(q)
            };
            this.getAllResponseHeaders = function () {
                return""
            };
            this.getResponseHeader = function () {
                return null
            }
        };
        d.ajaxSettings.xhr = function () {
            var s = y(this.url).domain;
            if (s === "" || s === k)return a.call(d.ajaxSettings); else try {
                return new g
            } catch (A) {
            }
        }
    }
})(jQuery);
steal.loaded("lib/jquery.ie.cors.js");
steal("jquery/class", "framework/map/layers/dynamic_layer_base.js", "framework/map/layers/static_layer_base.js", "disambiguation/models/disambiguation.js", "disambiguation/models/disambiguation_option.js", "disambiguation/models/disambiguation_option_list.js", "./disambiguation_pin_composer.js", "framework/map/layers/labelled_pin.css", function (d) {
    var b = Tfl.Maps.Disambiguation.DisambiguationPinComposer;
    Tfl.Maps.Framework.DynamicLayerBase("Tfl.Maps.Disambiguation.DisambiguationLayer", {DISPLAY_LARGE_BUS_STOPS_FROM_ZOOM: 14,
        DEFAULT_PAGE_SIZE: 5, MODE_NAMES_TO_REMOVE: {plane: {}, "river-tour": {}, "international-rail": {}}, BUS_STOP_MODE_NAME: "bus"}, {init: function (a, c, e) {
        this.pageSize = e.pageSize || this.constructor.DEFAULT_PAGE_SIZE;
        this.optionsElement = e.optionsElement;
        this.currentPage = 0;
        this.fetchOptions();
        this.previousZoom = null;
        this._super(a, c)
    }, chooseOption: function (a) {
        a = parseInt(a);
        this.map.mapLoading.done(this.proxy(function () {
            this.map.activateIfDeactivated();
            var c = this.getOptionFromId(a);
            c && this.selectOption(c)
        }))
    }, choosePage: function (a) {
        this.map.mapLoading.done(this.proxy(function () {
            this.map.activateIfDeactivated();
            this.changePage(a)
        }))
    }, layerNeedsToRender: function () {
        var a = this, c = d.grep(this.pages, function (j) {
            return d.grep(j, function (f) {
                return d.inArray(a.constructor.BUS_STOP_MODE_NAME, f.modeNames) != -1
            }).length > 0
        }).length > 0, e = true, h = this.map.getZoom();
        if (this.previousZoom)e = h < this.constructor.DISPLAY_LARGE_BUS_STOPS_FROM_ZOOM && this.constructor.DISPLAY_LARGE_BUS_STOPS_FROM_ZOOM <= this.previousZoom || this.previousZoom < this.constructor.DISPLAY_LARGE_BUS_STOPS_FROM_ZOOM && this.constructor.DISPLAY_LARGE_BUS_STOPS_FROM_ZOOM <=
            h;
        this.previousZoom = h;
        return c && e
    }, fetchOptions: function () {
        var a = d(this.optionsElement).find(".disambiguation-option"), c = new Tfl.Maps.Disambiguation.Models.DisambiguationOption.List, e = this;
        a.each(function (f, g) {
            f = d(g);
            f = new Tfl.Maps.Disambiguation.Models.DisambiguationOption({id: f.data("id"), lat: f.data("lat"), lng: f.data("lng"), name: f.data("name"), placeType: f.data("placetype"), modeNames: f.data("modenames").split(","), busStopLetter: f.data("stopletter")});
            f.modeNames = d.map(f.modeNames, function (k) {
                return e.constructor.MODE_NAMES_TO_REMOVE[k] ?
                    null : k
            });
            if (f.modeNames.length === 1 && f.modeNames[0] === "")f.modeNames[0] = "poi";
            c.push(f)
        });
        this.pages = [];
        this.positions = [];
        for (a = 0; a < c.length; a++) {
            var h = c[a], j = Math.floor(a / this.pageSize, 0);
            this.pages.length < j + 1 && this.pages.push([]);
            this.pages[j].push(h);
            this.positions.push({lat: h.lat, lng: h.lng})
        }
    }, render: function (a, c) {
        this.drawPins();
        c && this.map.displayMapByPositions(this.positions)
    }, drawPins: function () {
        var a = this.map.getZoom() >= this.constructor.DISPLAY_LARGE_BUS_STOPS_FROM_ZOOM;
        d.each(this.pages,
            this.proxy(function (c, e) {
                var h = c === this.currentPage ? "normal" : "inactive";
                d.each(e, this.proxy(function (j, f) {
                    var g = h === "normal" ? j + 1 + "" : null;
                    j = this.getZIndex(c, j);
                    g = {position: {lat: f.lat, lng: f.lng}, modeNames: f.modeNames.length == 0 ? ["poi"] : f.modeNames, placeType: f.placeType, state: h, title: f.name, number: g, busStopLetter: f.busStopLetter, showLargeBusStops: a};
                    f.pin = this.addAndWireUpDisambiguationPin(g, j, f);
                    f.pinSpec = g;
                    f.pinZIndex = j
                }))
            }))
    }, addAndWireUpDisambiguationPin: function (a, c, e) {
        a = this.addHtmlPin(b.getHtmlPinSpec(a),
            c);
        a.click(this.proxy(function () {
            this.selectOption(e);
            this.map.options.optionChosen && this.map.options.optionChosen({id: e.id, name: e.name})
        }));
        return a
    }, getZIndex: function (a, c) {
        c = this.positions.length - (a * this.pageSize + c);
        if (a === this.currentPage)c += 1E3;
        return c
    }, resetToInitialPosition: function () {
        this.map.displayMapByPositions(this.positions)
    }, changePage: function (a) {
        if (this.currentPage !== a) {
            this.targetedOption = this.chosenOption = null;
            this.currentPage = a;
            this.refresh()
        }
    }, selectOption: function (a) {
        if (this.chosenOption !==
            a) {
            this.chosenOption && this.unhighlightPin(this.chosenOption);
            this.highlightPin(a);
            this.chosenOption = a
        }
    }, targetPin: function (a) {
        if (this.targetedPinOption != a && this.chosenOption != a) {
            this.highlightPin(a);
            this.targetedPinOption = a
        }
    }, untargetPin: function (a) {
        if (this.chosenOption != a) {
            this.unhighlightPin(a);
            this.targetedPinOption = null
        }
    }, highlightPin: function (a) {
        this.setPinState(a, "highlighted")
    }, unhighlightPin: function (a) {
        this.setPinState(a, "normal")
    }, setPinState: function (a, c) {
        a.pin.clear();
        a.pin = null;
        a.pinSpec.state =
            c;
        a.pin = this.addAndWireUpDisambiguationPin(a.pinSpec, a.pinZIndex, a)
    }, getOptionFromId: function (a) {
        return d.grep(this.pages[this.currentPage], function (c) {
            return c.id === a
        })[0]
    }})
});
steal.loaded("disambiguation/disambiguation_map/disambiguation_layer.js");
steal("jquery/class", "framework/map/map_utils.js", "./layer_base", function (d) {
    var b = Tfl.Maps.Framework.MapUtils;
    Tfl.Maps.Framework.LayerBase("Tfl.Maps.Framework.DynamicLayerBase", {GUTTER_RATIO: 0.25}, {renderLayer: function (a) {
        a && this.map.idle(this.proxy(this.renderLayerIfOutOfBounds));
        this.prepareClear();
        var c = this.addGutteringToBounds(this.map.getBounds());
        this.boundsOfLastRender = c;
        a = this.render(c, a);
        d.when(a).done(this.proxy(function () {
            this.clear();
            this.isRendered = true
        }))
    }, renderLayerIfOutOfBounds: function () {
        var a =
            this.map.getBounds();
        if (this.layerNeedsToRender() || !b.boundsAContainsBoundsB(this.boundsOfLastRender, a))this.renderLayer()
    }, layerNeedsToRender: function () {
        return false
    }, addGutteringToBounds: function (a) {
        var c = (a.ne.lng - a.sw.lng) * this.constructor.GUTTER_RATIO, e = (a.ne.lat - a.sw.lat) * this.constructor.GUTTER_RATIO;
        return{sw: {lat: a.sw.lat - e, lng: a.sw.lng - c}, ne: {lat: a.ne.lat + e, lng: a.ne.lng + c}}
    }, getZoomThreshold: function (a, c, e) {
        if (!c.zoomThresholds)return e;
        if (!c.zoomThresholds[a])return e;
        return c.zoomThresholds[a]
    }})
});
steal.loaded("framework/map/layers/dynamic_layer_base.js");
steal("jquery/model", "jquery/model/list", function () {
    $.Model.List("Tfl.Maps.Disambiguation.Models.DisambiguationOption.List", {}, {})
});
steal.loaded("disambiguation/models/disambiguation_option_list.js");
steal("jquery/class", "framework/models/settings_helper.js", "./disambiguation_pin.css", function (d) {
    d.Class("Tfl.Maps.Disambiguation.DisambiguationPinComposer", {getHtmlPinSpec: function (b) {
        var a = this.getHtml(b), c;
        if (this.isPinABusStop(b))c = {offsetFromBottom: true, y: -9};
        return{position: b.position, tooltip: b.title, html: a, iconAnchor: c}
    }, getHtml: function (b) {
        return d.View("//framework/map/layers/disambiguation_pin.ejs", {label: b.title, modeNames: b.modeNames, number: b.number, state: b.state, rootUrl: Tfl.Maps.Framework.Models.SettingsHelper.contentBaseUrl +
            "framework/map/layers/icons/", isABusStop: this.isPinABusStop(b), busStopLetter: b.busStopLetter, largeBusStopCssClass: this.getLargeBusStopCssClass(b.busStopLetter), showLargeBusStops: b.showLargeBusStops})
    }, isPinABusStop: function (b) {
        b = b.modeNames;
        return b.length == 1 && b[0] === "bus"
    }, getLargeBusStopCssClass: function (b) {
        b = d.trim(b).length;
        return"bus-marker-" + (b == 1 ? "single" : b.length == 2 ? "double" : "triple") + "-letter"
    }}, {})
});
steal.loaded("disambiguation/disambiguation_map/disambiguation_pin_composer.js");
steal("jquery/model").then(function (d) {
    var b = function (k) {
        return k[0] && d.isArray(k[0]) ? k[0] : k[0]instanceof d.Model.List ? d.makeArray(k[0]) : d.makeArray(k)
    }, a = 0, c = function (k) {
        return k[k.constructor.id]
    }, e = jQuery.expando, h = d.each, j = d.Model._ajax, f = {update: function (k) {
        return function (m, t, z, y) {
            return j(k, {ids: m, attrs: t}, z, y, "-updateAll", "put")
        }
    }, destroy: function (k) {
        return function (m, t, z) {
            return j(k, m, t, z, "-destroyAll", "post")
        }
    }};
    d.Class("jQuery.Model.List", {setup: function () {
        for (var k in f)if (typeof this[k] !==
            "function")this[k] = f[k](this[k])
    }}, {init: function (k) {
        this.length = 0;
        this._data = {};
        this._namespace = ".list" + ++a;
        this.push.apply(this, d.makeArray(k || []))
    }, slice: function () {
        return new this.Class(Array.prototype.slice.apply(this, arguments))
    }, match: function (k, m) {
        return this.grep(function (t) {
            return t[k] == m
        })
    }, grep: function (k, m) {
        return new this.Class(d.grep(this, k, m))
    }, _makeData: function () {
        var k = this._data = {};
        this.each(function (m, t) {
            k[t[t.constructor.id]] = t
        })
    }, get: function () {
        if (!this.length)return new this.Class([]);
        this._changed && this._makeData();
        var k = [], m = this[0].constructor, t = m.id;
        m = new RegExp(m._fullName + "_([^ ]+)");
        for (var z, y, D = b(arguments), s = 0; s < D.length; s++)(y = D[s].nodeName && (z = D[s].className.match(m)) ? this._data[z[1]] : this._data[typeof D[s] == "string" || typeof D[s] == "number" ? D[s] : D[s][t]]) && k.push(y);
        return new this.Class(k)
    }, remove: function (k) {
        if (!this.length)return[];
        var m = [], t = this[0].constructor, z = t.id;
        t = new RegExp(t._fullName + "_([^ ]+)");
        var y;
        k = b(arguments);
        for (var D = 0; D < this.length;) {
            for (var s =
                this[D], A = false, x = 0; x < k.length; x++) {
                var n = k[x].nodeName && (y = k[x].className.match(t)) && y[1] || (typeof k[x] == "string" || typeof k[x] == "number" ? k[x] : k[x][z]);
                if (s[z] == n) {
                    m.push.apply(m, this.splice(D, 1));
                    k.splice(x, 1);
                    A = true;
                    break
                }
            }
            A || D++
        }
        m = new this.Class(m);
        m.length && d([this]).trigger("remove", [m]);
        return m
    }, elements: function (k) {
        return d(this.map(function (m) {
            return"." + m.identity()
        }).join(","), k)
    }, model: function () {
        return this.constructor.namespace
    }, findAll: function (k, m, t) {
        var z = this;
        this.model().findAll(k,
            function (y) {
                z.push(y);
                m && m(z)
            }, t)
    }, destroy: function (k, m) {
        var t = this.map(c), z = this.slice(0, this.length);
        if (t.length)this.constructor.destroy(t, function () {
            h(z, function () {
                this.destroyed()
            });
            k && k(z)
        }, m); else k && k(this);
        return this
    }, update: function (k, m, t) {
        var z = this.map(c), y = this.slice(0, this.length);
        if (z.length)this.constructor.update(z, k, function (D) {
            var s = d.extend(k, D || {});
            h(y, function () {
                this.updated(s)
            });
            m && m(y)
        }, t); else m && m(this);
        return this
    }, bind: function () {
        this[e] === undefined && this.bindings(this);
        d.fn.bind.apply(d([this]), arguments);
        return this
    }, unbind: function () {
        d.fn.unbind.apply(d([this]), arguments);
        this[e] === undefined && d(this).unbind(this._namespace);
        return this
    }, bindings: function (k) {
        var m = this;
        d(k).bind("destroyed" + this._namespace, function () {
            m.remove(this)
        }).bind("updated" + this._namespace, function () {
            d([m]).trigger("updated", this)
        })
    }, push: function () {
        var k = b(arguments);
        this[e] !== undefined && this.bindings(k);
        this._changed = true;
        var m = g.apply(this, k);
        this[e] && k.length && d([this]).trigger("add",
            [k]);
        return m
    }, serialize: function () {
        return this.map(function (k) {
            return k.serialize()
        })
    }});
    var g = [].push;
    h({pop: [].pop, shift: [].shift, unshift: [].unshift, splice: [].splice, sort: [].sort, reverse: [].reverse}, function (k, m) {
        d.Model.List.prototype[k] = function () {
            this._changed = true;
            return m.apply(this, arguments)
        }
    });
    h(["each", "map"], function (k, m) {
        d.Model.List.prototype[m] = function (t, z) {
            return d[m](this, t, z)
        }
    })
});
steal.loaded("jquery/model/list/list.js");
steal("./static_layer_base.js", "framework/geolocation_provider.js", function () {
    Tfl.Maps.Framework.StaticLayerBase("Tfl.Maps.Framework.GeolocationLayer", {}, {render: function () {
        this.googleMap = this.map.googleMap;
        Tfl.Maps.Framework.GeolocationProvider.registerListener(this.proxy(this.updatePin))
    }, updatePin: function (d) {
        if (this.pin)this.pin.setPosition(d); else this.pin = this.addHtmlPin({position: d, iconAnchor: {x: 50, y: 50}, html: "<div class='userlocation-dot-holder' style='height: 100px; width: 100px;'><div class='userlocation-pulse'></div><div class='userlocation-dot'></div></div>"})
    }})
});
steal.loaded("framework/map/layers/geolocation_layer.js");
steal("jquery/class", "./fat_roads_layer_base", function () {
    Tfl.Maps.Framework.FatRoadsLayerBase("Tfl.Maps.Framework.CongestionChargingZoneLayer", {LAYER_NAME: "Congestion charge", LAYER_ICON_PATH: "framework/map/layers/icons/ccz_layer.png", LAYER_CLASS_SUFFIX: "ccz", POLYGON_JSON_FILENAME: "ccz.json", POLYGON_FILL_COLOUR: "#ff0000", POLYGON_STROKE_COLOUR: "#ff0000"}, {})
});
steal.loaded("framework/map/layers/congestion_charging_zone_layer.js");
steal("jquery/class", "./fat_roads_layer_base", function () {
    Tfl.Maps.Framework.FatRoadsLayerBase("Tfl.Maps.Framework.LowEmissionZoneLayer", {LAYER_NAME: "Low emission zone", LAYER_ICON_PATH: "framework/map/layers/icons/lez_layer.png", LAYER_CLASS_SUFFIX: "lez", POLYGON_JSON_FILENAME: "lez.json", POLYGON_FILL_COLOUR: "#00ff00", POLYGON_STROKE_COLOUR: "#000000"}, {})
});
steal.loaded("framework/map/layers/low_emission_zone_layer.js");
steal("jquery/class", "./fat_roads_layer_base", function () {
    Tfl.Maps.Framework.FatRoadsLayerBase("Tfl.Maps.Framework.CoachBanAreasLayer", {LAYER_NAME: "Coach ban areas", LAYER_ICON_PATH: "framework/map/layers/icons/coach-ban_layer.png", LAYER_CLASS_SUFFIX: "coach-ban", POLYGON_JSON_FILENAME: "cba.json", POLYGON_FILL_COLOUR: "#ff0000", POLYGON_STROKE_COLOUR: "#000000"}, {})
});
steal.loaded("framework/map/layers/coach_ban_areas_layer.js");
steal("jquery/class", "./fat_roads_layer_base", "framework/map/map_utils.js", "framework/models/settings_helper.js", function (d) {
    var b = Tfl.Maps.Framework.MapUtils;
    Tfl.Maps.Framework.FatRoadsLayerBase("Tfl.Maps.Framework.GLABoundaryLayer", {LAYER_NAME: "GLA Boundary", LAYER_ICON_PATH: "framework/map/layers/icons/gla_layer.png", LAYER_CLASS_SUFFIX: "gla", POLYGON_JSON_FILENAME: "gla.json"}, {renderPolygons: function (a) {
        var c, e, h, j = this;
        d.each(a.encodedPaths, function (f, g) {
            c = google.maps.geometry.encoding.decodePath(g);
            e = b.getOurPathFromGooglePath(c);
            h = {path: e, strokeColor: "#174fc2", strokeOpacity: 1, fillColor: "#174fc2"};
            j.addPolygonMask(h)
        })
    }})
});
steal.loaded("framework/map/layers/gla_boundary_layer.js");
steal("jquery/class", "./static_layer_base", function () {
    Tfl.Maps.Framework.StaticLayerBase("Tfl.Maps.Framework.TrafficLayer", {LAYER_NAME: "Traffic conditions", LAYER_ICON_PATH: "framework/map/layers/icons/traffic_layer.png", LAYER_CLASS_SUFFIX: "traffic"}, {render: function () {
        this.trafficLayer = new google.maps.TrafficLayer;
        this.trafficLayer.setMap(this.map.googleMap)
    }, clear: function () {
        if (this.trafficLayer) {
            this.trafficLayer.setMap(null);
            this.trafficLayer = null
        }
        this._super()
    }})
});
steal.loaded("framework/map/layers/traffic_layer.js");
steal("jquery/class", "./dynamic_layer_base.js", "framework/models/place.js", "framework/models/settings_helper.js", function (d) {
    Tfl.Maps.Framework.DynamicLayerBase("Tfl.Maps.Framework.PlacesLayer", {PLACETYPES_WITH_ICONS: ["CoachPark", "CoachBay", "OnStreetMeteredBay", "OtherCoachParking", "Theatre", "PublicToilet", "JamCam", "RedLightCam", "SpeedCam", "RedLightAndSpeedCam", "VariableMessageSign"], PLACETYPES_WITH_RETINA_ICONS: ["JamCam", "RedLightCam", "SpeedCam", "RedLightAndSpeedCam", "VariableMessageSign"], PLACETYPES_WITH_CLASSSUFFIXES: {CoachPark: "coach-park",
        CoachBay: "coach-bay", OnStreetMeteredBay: "metered-bay", OtherCoachParking: "other-coach-parking"}, ICON_SIZE: {width: 22, height: 23}, DISPLAY_BY_ZOOM: 14}, {init: function (b, a, c) {
        this.placeTypes = c.placeTypes;
        this.categories = c.categories ? c.categories : [];
        if (c.placeChosen)this.placeChosen = c.placeChosen;
        this.selectedPlace = {id: "", placeType: ""};
        this.places = {};
        this.displayByZoom = c.displayByZoom ? c.displayByZoom : this.constructor.DISPLAY_BY_ZOOM;
        this.previousZoomLevel = null;
        this.renderedPlaces = [];
        this._super(b, a)
    }, choosePlace: function (b, a, c) {
        if (d.inArray(a, this.placeTypes) === -1)this.selectedPlace = {id: "", placeType: ""}; else {
            this.selectItem(b, "place", this);
            this.selectedPlace = {id: b, placeType: a};
            this.focus();
            this.places[b + "_" + a] && this.addPlace(this.places[b + "_" + a]);
            this.previousZoomLevel = 0;
            this.map.displayMapByCenter(c)
        }
    }, prepareClear: function (b) {
        if (this.renderStateChanged || b) {
            this._super();
            this.renderedPlaces = []
        }
    }, clear: function (b) {
        if (this.renderStateChanged || b) {
            this.prepareClear();
            this._super()
        }
    }, render: function (b) {
        var a = this.placeTypes,
            c = this.categories;
        if (this.map.getZoom() < this.displayByZoom)return null;
        return this.fetchData(b, a, c).then(this.proxy(function (e) {
            this.prepareClear();
            this.clear();
            var h = this;
            h.places = {};
            d.each(e, function (j, f) {
                h.places[f.id + "_" + f.placeType] = f;
                if (h.selectedPlace.id === f.id)h.selectedPlace = f
            });
            d.each(this.sortPlacesByLatLon(e), function (j, f) {
                if (d.inArray(f.id, h.renderedPlaces) == -1) {
                    h.addPlace(f);
                    h.renderedPlaces.push(f.id)
                }
            })
        }))
    }, fetchData: function (b, a, c) {
        return Tfl.Maps.Framework.Models.Place.findAll({bounds: b,
            placeTypes: a, categories: c})
    }, layerNeedsToRender: function () {
        var b = this.map.getZoom(), a = this.previousZoomLevel, c = this.baseLayerNeedsToRender();
        a = !this.previousZoomLevel || b < this.displayByZoom && this.displayByZoom <= a || a < this.displayByZoom && this.displayByZoom <= b || c;
        this.previousZoomLevel = b;
        (this.renderStateChanged = a) && this.clear();
        return a
    }, baseLayerNeedsToRender: function () {
        return false
    }, itemSelectedInOtherLayer: function () {
        if (this.selectedPlace) {
            var b = this.selectedPlace;
            this.selectedPlace = {id: "", placeType: ""};
            this.unchoosePlace(b)
        }
    }, sortPlacesByLatLon: function (b) {
        return b.sort(function (a, c) {
            return c.lat - a.lat
        })
    }, addPlace: function (b) {
        var a = this.getClassSuffixForPlace(b), c = {lat: b.lat, lng: b.lon}, e = null;
        if (a === "") {
            a = this.getIconUrlForPlace(b);
            e = this.getRetinaIconUrlForPlace(b);
            e = this.addPin({position: c, iconUrl: a, retinaIconUrl: e, tooltip: b.commonName, size: this.constructor.ICON_SIZE, cursor: this.allowPinClick() ? "" : "grab"})
        } else {
            e = b.id === this.selectedPlace.id;
            c = {position: c, html: "<div id='" + b.id + "' class='map-pin-32x32 map-layer-icon-places-" +
                (e ? "selected-" : "") + a + "'></div>", tooltip: b.commonName};
            c.iconAnchor = e ? {x: 16, y: 48} : {x: 16, y: 16};
            e = this.addHtmlPin(c)
        }
        b.pin = e;
        this.allowPinClick() && e.click(this.proxy(function () {
            this.placeClicked(b)
        }))
    }, getIconUrlForPlace: function (b) {
        b = b.placeType;
        b = d.inArray(b, this.constructor.PLACETYPES_WITH_ICONS) > -1 ? b + ".png" : "default.png";
        return Tfl.Maps.Framework.Models.SettingsHelper.contentBaseUrl + "framework/map/layers/icons/" + b
    }, getRetinaIconUrlForPlace: function (b) {
        b = b.placeType;
        b = d.inArray(b, this.constructor.PLACETYPES_WITH_RETINA_ICONS) > -1 ? b + "_x2.png" : "default_x2.png";
        return Tfl.Maps.Framework.Models.SettingsHelper.contentBaseUrl + "framework/map/layers/icons/" + b
    }, getClassSuffixForPlace: function (b) {
        b = b.placeType;
        return this.constructor.PLACETYPES_WITH_CLASSSUFFIXES[b] ? this.constructor.PLACETYPES_WITH_CLASSSUFFIXES[b] : ""
    }, allowPinClick: function () {
        return true
    }, placeClicked: function (b) {
        this.selectItem(b.id, "place", this);
        this.placeChosen && this.placeChosen({id: b.id, placeType: b.placeType, commonName: b.commonName, additionalProperties: b.additionalProperties});
        var a = this.selectedPlace;
        this.selectedPlace = b;
        this.unchoosePlace(a);
        b.pin && this.clearPlace(b);
        this.addPlace(b)
    }, unchoosePlace: function (b) {
        this.clearPlace(b) && this.addPlace(b)
    }, clearPlace: function (b) {
        var a = b.id || b.pin;
        b.id && d("#" + b.id).remove();
        b.pin && b.pin.clear();
        return a
    }})
});
steal.loaded("framework/map/layers/places_layer.js");
steal("jquery/class", "./static_layer_base", function () {
    Tfl.Maps.Framework.StaticLayerBase("Tfl.Maps.Framework.CyclingLayer", {LAYER_NAME: "Cycling routes", LAYER_ICON_PATH: "framework/map/layers/icons/cycling_layer.png"}, {render: function () {
        this.cyclingLayer = new google.maps.BicyclingLayer;
        this.cyclingLayer.setMap(this.map.googleMap)
    }, clear: function () {
        if (this.cyclingLayer) {
            this.cyclingLayer.setMap(null);
            this.cyclingLayer = null
        }
        this._super()
    }})
});
steal.loaded("framework/map/layers/cycling_layer.js");
steal("jquery/class", "./static_layer_base", "framework/models/settings_helper.js", function () {
    Tfl.Maps.Framework.StaticLayerBase("Tfl.Maps.Framework.VirtualLayer", {LAYER_NAME: "Virtual", LAYER_ICON_PATH: null, LAYER_CLASS_SUFFIX: null}, {init: function (d, b, a) {
        this.options = a;
        this._super(d, b)
    }, show: function () {
        this.options.showing && this.options.showing();
        this._super()
    }, hide: function () {
        this.options.hiding && this.options.hiding();
        this._super()
    }, render: function () {
    }, clear: function () {
        this._super()
    }})
});
steal.loaded("framework/map/layers/virtual_layer.js");
steal("jquery/class", "framework/map/layers/static_layer_base.js", "framework/map/map_utils.js", "../severity_layer_filter.js", "../models/road_disruption.js", "framework/models/settings_helper.js", function (d) {
    var b = Tfl.Maps.Framework.MapUtils;
    Tfl.Maps.Framework.StaticLayerBase("Tfl.Maps.Road.RoadDisruptionsLayer", {LAYER_NAME: "Road incidents", LAYER_ICON_PATH: "road/icons/disruption_layer.png", DISRUPTION_ICON_URL: {MinimalWorks: "road/icons/works", ModerateWorks: "road/icons/works", SeriousWorks: "road/icons/works",
        SevereWorks: "road/icons/works", Minimal: "road/icons/minimal_impact", Moderate: "road/icons/moderate_impact", Serious: "road/icons/serious_impact", Severe: "road/icons/severe_impact"}, DISRUPTION_POLYGON_COLOUR: {Minimal: "#ffde8e", Moderate: "#fdb813", Serious: "#ff0000", Severe: "#ed3325"}, LAYER_KEY: {}}, {init: function (a, c, e) {
        this.options = e;
        this.filters = {};
        this.filters.RoadIncidentSeverity = new Tfl.Maps.Road.SeverityLayerFilter(this, {hiding: this.proxy(function () {
            this.stateChanged()
        }), showing: this.proxy(function () {
            this.stateChanged()
        })});
        this.areWorksVisible = false;
        this._super(a, c)
    }, stateChanged: function () {
        this.options.stateChanged && this.options.stateChanged({worksVisible: this.areWorksVisible, severeVisible: this.filters.RoadIncidentSeverity.isVisible("Severe"), minimalVisible: this.filters.RoadIncidentSeverity.isVisible("Minimal"), moderateVisible: this.filters.RoadIncidentSeverity.isVisible("Moderate"), seriousVisible: this.filters.RoadIncidentSeverity.isVisible("Serious")})
    }, chooseDisruption: function (a, c) {
        this.focus();
        this.disruptionSelected(a,
            false, c)
    }, showWorks: function () {
        this.areWorksVisible = true;
        this.worksPins && d.each(this.worksPins, function (a, c) {
            c.show()
        })
    }, hideWorks: function () {
        this.areWorksVisible = false;
        this.worksPins && d.each(this.worksPins, function (a, c) {
            c.hide()
        })
    }, render: function () {
        return Tfl.Maps.Road.Models.RoadDisruption.findAll({startDate: this.options.startDate, endDate: this.options.endDate, bounds: this.map.options.panLimit, severityFilter: this.filters.RoadIncidentSeverity}).done(this.proxy(function (a) {
            this.worksPins = [];
            this.nonWorksPins =
                [];
            d.each(this.sortDisruptionsByLatLon(a), this.proxy(function (c, e) {
                this.addDisruptionPin(e)
            }));
            d(".road-disruption")
        }))
    }, sortDisruptionsByLatLon: function (a) {
        d.each(a, this.proxy(function (c, e) {
            c = d.parseJSON(e.point);
            e.lat = c[1];
            e.lng = c[0]
        }));
        return a.sort(function (c, e) {
            return e.lat - c.lat
        })
    }, addDisruptionPin: function (a) {
        var c = {lat: a.lat, lng: a.lng}, e = this.getIconUrl(a), h = this.getRetinaIconUrl(a);
        c = this.addPin({position: c, iconUrl: e, retinaIconUrl: h, size: {width: 22, height: 23}, tooltip: a.comments});
        c.click(this.proxy(function () {
            this.disruptionSelected(a.id,
                true)
        }));
        this.addDisruptionPinToArray(a, c)
    }, getIconUrl: function (a) {
        return a.category === "Works" ? Tfl.Maps.Framework.Models.SettingsHelper.contentBaseUrl + this.constructor.DISRUPTION_ICON_URL[a.severity + "Works"] + ".png" : Tfl.Maps.Framework.Models.SettingsHelper.contentBaseUrl + this.constructor.DISRUPTION_ICON_URL[a.severity] + ".png"
    }, getRetinaIconUrl: function (a) {
        return a.category === "Works" ? Tfl.Maps.Framework.Models.SettingsHelper.contentBaseUrl + this.constructor.DISRUPTION_ICON_URL[a.severity + "Works"] + "_x2.png" :
            Tfl.Maps.Framework.Models.SettingsHelper.contentBaseUrl + this.constructor.DISRUPTION_ICON_URL[a.severity] + "_x2.png"
    }, addDisruptionPinToArray: function (a, c) {
        if (a.category === "Works") {
            this.worksPins.push(c);
            this.areWorksVisible || c.hide()
        } else this.nonWorksPins.push(c)
    }, isFilterable: function () {
        return true
    }, disruptionSelected: function (a, c, e) {
        Tfl.Maps.Road.Models.RoadDisruption.findOne({id: a}).done(this.proxy(function (h) {
            if (h != undefined) {
                c && this.map.options.disruptionChosen && this.map.options.disruptionChosen({id: h.id,
                    locationDescription: h.location, severityDescription: h.severity});
                this.clearDisruptionOverlays();
                if (h.polygon)this.renderDisruptionPolygon(h); else if (h.streets)this.renderDisruptionStreets(h); else {
                    var j = d.parseJSON(h.point);
                    this.disruptionPoints.push({lat: j[1], lng: j[0]})
                }
                if (!c)if (e) {
                    var f = h.getOurLatLng();
                    Tfl.Maps.Framework.Models.Place.findAllByRadius({lat: f.lat, lng: f.lng, radius: 3200, placeTypes: ["JamCam"]}).then(this.proxy(function (g) {
                        var k = [];
                        d.each(g.places, function (m, t) {
                            k.push({lat: t.lat, lng: t.lon})
                        });
                        this.map.displayMapByPositions(Tfl.Maps.Framework.MapUtils.getNearestPoints(f, k, 2).concat(this.disruptionPoints))
                    }))
                } else this.map.displayMapByPositions(this.disruptionPoints)
            }
        }))
    }, clearDisruptionOverlays: function () {
        this.disruptionOverlays && d.each(this.disruptionOverlays, function (a, c) {
            c.clear()
        });
        this.disruptionOverlays = [];
        this.disruptionPoints = []
    }, renderDisruptionPolygon: function (a) {
        var c = this.constructor.DISRUPTION_POLYGON_COLOUR[a.severity];
        a = b.getOurPathFromTflPath(a.polygon);
        this.disruptionOverlays.push(this.addPolygon({path: a,
            strokeColor: c, fillColor: c}));
        this.disruptionPoints = a
    }, renderDisruptionStreets: function (a) {
        var c = this;
        d.each(a.streets, function (e, h) {
            d.each(h.segments, function (j, f) {
                j = b.getOurPathFromTflPath(f.lineString);
                f = c.addPolyline({path: j, weight: 6, opacity: 0.5, colors: [c.constructor.DISRUPTION_POLYGON_COLOUR[a.severity]]});
                c.disruptionOverlays.push(f);
                c.disruptionPoints = c.disruptionPoints.concat(j)
            })
        })
    }})
});
steal.loaded("road/layers/road_disruptions_layer.js");
steal("jquery/class", "framework/map/layers/static_layer_base.js", "../models/road_corridor.js", "framework/models/settings_helper.js", "framework/map/map_utils.js", function (d) {
    var b = Tfl.Maps.Framework.Models.SettingsHelper;
    Tfl.Maps.Framework.StaticLayerBase("Tfl.Maps.Road.RoadCorridorsLayer", {LAYER_NAME: "Road Corridors", LAYER_ICON_PATH: "framework/map/layers/icons/road-corridors_layer.png", LAYER_CLASS_SUFFIX: "road-corridors", LAYER_KEY: {"Central London": "#99F37E", "North Circular (A406)": "#A32FD0", "South Circular (A205)": "#6D1F38",
        "Blackwall Tunnel": "#EB9C58", A1: "#3B8B2D", A10: "#5C4DC4", A12: "#E8624C", A13: "#56DDF7", A2: "#EF3CBc", A20: "#F1E78C", A21: "#44607E", A23: "#55FFAC", A24: "#6E5641", A3: "#599267", A316: "#EC82B7", A4: "#57F14E", A40: "#CDFE50", A41: "#8B4084"}, getNormalizedCoord: function (a, c) {
        var e = a.y;
        a = a.x;
        c = 1 << c;
        if (e < 0 || e >= c)return null;
        if (a < 0 || a >= c)a = (a % c + c) % c;
        return{x: a, y: e}
    }}, {init: function (a, c, e) {
        this.whenLayerReady = e.whenLayerReady;
        this._super(a, c)
    }, chooseCorridor: function (a) {
        if (a === "")a = "all";
        if (a === "Central London Red Routes")a =
            "Central London";
        this.focus();
        this.map.displayMapByBoundingBox(this.corridors[a].bounds);
        if (a !== this.selectedCorridorId) {
            if (this.selectedCorridorId !== "" && this.corridors[this.selectedCorridorId].group === "Central London Red Routes")a = "Central London";
            this.hideRoadCorridorTileLayer(this.selectedCorridorId);
            this.selectedCorridorId = a;
            this.showRoadCorridorTileLayer(this.selectedCorridorId)
        }
    }, render: function (a) {
        if (a) {
            this.corridors = {};
            this.selectedCorridorId = "all";
            this.corridorOverlays = {};
            this.showRoadCorridorTileLayer("all");
            return Tfl.Maps.Road.Models.RoadCorridor.findAll().done(this.proxy(this.processCorridors))
        } else {
            this.hideRoadCorridorTileLayer(this.selectedCorridorId);
            this.showRoadCorridorTileLayer(this.selectedCorridorId);
            return null
        }
    }, clear: function () {
        this.map.googleMap.overlayMapTypes.removeAt(0);
        this._super()
    }, hide: function () {
        this.isRendered && this.map.styleManager.requestHidePlainMap();
        this._super()
    }, show: function () {
        if (this.map.isMapReady)this.isRendered || this.map.styleManager.requestShowPlainMap(); else {
            var a =
                this;
            this.map.mapLoading.done(function () {
                this.isRendered || a.map.styleManager.requestShowPlainMap()
            })
        }
        this._super()
    }, processCorridors: function (a) {
        var c = this, e = {};
        d.each(a, function (h, j) {
            h = d.parseJSON(j.bounds);
            h = {sw: {lat: h[0][1], lng: h[0][0]}, ne: {lat: h[1][1], lng: h[1][0]}};
            if (j.group === "Central London Red Routes")if (e.bounds)Tfl.Maps.Framework.MapUtils.extendBoundsWithPositions(e.bounds, [h.sw, h.ne]); else e.bounds = h;
            j.bounds = h;
            c.corridors[j.id] = j
        });
        c.corridors["Central London"] = e;
        c.corridors.all = {bounds: this.map.options.panLimit};
        this.whenLayerReady && this.whenLayerReady()
    }, hideRoadCorridorTileLayer: function (a) {
        if (a = this.corridorOverlays["corridor_" + a])for (var c = this.map.googleMap.overlayMapTypes.getArray(), e = 0; e < c.length; e++)if (c[e] === a) {
            this.map.googleMap.overlayMapTypes.removeAt(e);
            return
        }
    }, showRoadCorridorTileLayer: function (a) {
        var c = this.corridorOverlays["corridor_" + a];
        if (!c) {
            c = this.createRoadCorridorTileLayer(a);
            this.corridorOverlays["corridor_" + a] = c
        }
        this.map.googleMap.overlayMapTypes.insertAt(0, c)
    }, createRoadCorridorTileLayer: function (a) {
        a =
                a == "" ? "all" : a;
        var c = this, e = {getTileUrl: function (h, j) {
            h = c.constructor.getNormalizedCoord(h, j);
            if (!h)return null;
            var f = Math.pow(2, j);
            return b.tileServerUrl + "tflroads_" + a.replace(" ", "_") + "/" + j + "/" + h.x + "/" + (f - h.y - 1)
        }, tileSize: new google.maps.Size(256, 256), name: "RoadCorridors" + a};
        return new google.maps.ImageMapType(e)
    }})
});
steal.loaded("road/layers/road_corridors_layer.js");
steal("jquery/class").then("./geolocation_unavailable_error.js", function () {
    $.Class("Tfl.Maps.Framework.GeolocationProvider", {ACCURACY_THRESHOLD: 8E3, successListeners: [], failureListeners: [], geolocationStarted: false, isGeolocationAvailable: function () {
        return navigator.geolocation && true
    }, registerListener: function (d, b) {
        if (!this.isGeolocationAvailable())throw new Tfl.Maps.Framework.GeolocationUnavailableError("Cannot register a geolocation listener when geolocation is unavailable!");
        if (d) {
            this.successListeners.push(d);
            this.lastResult && this.lastResult.success && d(this.lastResult.position, this.lastResult.accuracy)
        }
        if (b) {
            this.failureListeners.push(b);
            this.lastResult && !this.lastResult.success && b(this.lastResult.failureType, this.lastResult.message)
        }
        if (!this.geolocationStarted) {
            this.startGeolocating();
            this.geolocationStarted = true
        }
    }, startGeolocating: function () {
        this.watchId = navigator.geolocation.watchPosition(this.proxy(this.geolocationSucceeded), this.proxy(this.geolocationFailed), {maximumAge: 6E5})
    }, geolocationSucceeded: function (d) {
        var b =
            d.coords.accuracy, a = this.ACCURACY_THRESHOLD;
        if (b > a)this.geolocationFailed({code: -1, message: "The accuracy returned by the geolocator was " + b + " metres, which is greater than the maximum allowed value of " + a + " metres"}); else {
            var c = {lat: d.coords.latitude, lng: d.coords.longitude};
            $.each(this.successListeners, function (e, h) {
                h(c, b)
            });
            this.lastResult = {success: true, accuracy: b, position: c}
        }
    }, geolocationFailed: function (d) {
        var b = this.getFailureType(d.code), a = d.message;
        $.each(this.failureListeners, function (c, e) {
            e(b,
                a)
        });
        this.lastResult = {success: false, failureType: b, message: a}
    }, getFailureType: function (d) {
        switch (d) {
            case -1:
                return"INACCURACY_TOO_HIGH";
            case 1:
                return"PERMISSION_DENIED";
            case 2:
                return"LOCATION_UNAVAILABLE";
            case 3:
                return"TIMEOUT"
        }
    }}, {})
});
steal.loaded("framework/geolocation_provider.js");
steal(function () {
    Tfl.Maps.Framework.GeolocationUnavailableError = function (d) {
        this.name = "Geolocation unavailable";
        this.message = d
    };
    Tfl.Maps.Framework.GeolocationUnavailableError.prototype = new Error
});
steal.loaded("framework/geolocation_unavailable_error.js");
steal("jquery/class", "./static_polygon_layer_base", function () {
    Tfl.Maps.Framework.StaticPolygonLayerBase("Tfl.Maps.Framework.FatRoadsLayerBase", {}, {hide: function () {
        this.isRendered && this.map.styleManager.requestHideFatRoads();
        this._super()
    }, show: function () {
        this.isRendered || this.map.styleManager.requestShowFatRoads();
        this._super()
    }})
});
steal.loaded("framework/map/layers/fat_roads_layer_base.js");
steal("jquery/class", "./static_layer_base", "framework/map/map_utils.js", "framework/models/settings_helper.js", function (d) {
    var b = Tfl.Maps.Framework.MapUtils, a = Tfl.Maps.Framework.Models.SettingsHelper;
    Tfl.Maps.Framework.StaticLayerBase("Tfl.Maps.Framework.StaticPolygonLayerBase", {}, {render: function () {
        return d.getJSON(a.contentBaseUrl + "framework/map/layers/static_polygon_data/" + this.constructor.POLYGON_JSON_FILENAME, this.proxy(this.renderPolygons))
    }, renderPolygons: function (c) {
        var e, h, j, f = this;
        d.each(c.encodedPaths,
            function (g, k) {
                e = google.maps.geometry.encoding.decodePath(k);
                h = b.getOurPathFromGooglePath(e);
                j = {path: h, strokeColor: f.constructor.POLYGON_STROKE_COLOUR || null, fillColor: f.constructor.POLYGON_FILL_COLOUR || null};
                f.addPolygon(j)
            })
    }})
});
steal.loaded("framework/map/layers/static_polygon_layer_base.js");
steal("jquery/model", "framework/fixtures", "framework/map/map_utils.js", "framework/models/settings_helper.js", function () {
    $.Model("Tfl.Maps.Framework.Models.Place", {findAll: function (d, b, a) {
        var c = Tfl.Maps.Framework.MapUtils, e = $.Deferred(), h = d.bounds, j = d.placeTypes, f = c.distanceBetweenPoints(h.sw, h.ne).toFixed(1), g = Tfl.Maps.Framework.Models.SettingsHelper.apiProxyRoot + "Place?" + Tfl.Maps.Framework.Models.SettingsHelper.apiQuerySuffix;
        g += f < 121 ? "&type=" + j.join(",") + "&swLat=" + this.roundCoordinate(h.sw.lat) +
            "&swLon=" + this.roundCoordinate(h.sw.lng) + "&neLat=" + this.roundCoordinate(h.ne.lat) + "&neLon=" + this.roundCoordinate(h.ne.lng) + "&categories=" + d.categories.join(",") + "&includeChildren=false" : "&types=" + j.join(",");
        d = {url: g, type: "get", dataType: "json place.models", error: a, fixture: this.mapFixture(j)};
        $.ajax(d).done(this.proxy(function (k) {
            if ($.fixture.on) {
                k = c.filterArrayByBounds(k, h, function (m) {
                    return m.lat
                }, function (m) {
                    return m.lon
                });
                k = $.grep(k, function (m) {
                    return $.inArray(m.placeType, j) > -1
                })
            } else k = c.filterArrayByBounds(k,
                h, function (m) {
                    return m.lat
                }, function (m) {
                    return m.lon
                });
            e.resolve(k);
            b && b(k)
        }));
        return e
    }, findAllByRadius: function (d, b, a) {
        var c = $.Deferred(), e = d.placeTypes;
        d = {url: Tfl.Maps.Framework.Models.SettingsHelper.apiProxyRoot + "Place?type=" + e.join(",") + "&lat=" + d.lat + "&lon=" + d.lng + "&radius=" + d.radius + "&categories=&includeChildren=false" + Tfl.Maps.Framework.Models.SettingsHelper.apiQuerySuffix, type: "get", dataType: "json place.models", error: a, fixture: this.mapFixture(e)};
        $.ajax(d).done(this.proxy(function (h) {
            c.resolve(h);
            b && b(h)
        }));
        return c
    }, roundCoordinate: function (d) {
        return d < 0 ? Math.floor(d * 1E4) / 1E4 : Math.ceil(d * 1E4) / 1E4
    }, mapFixture: function (d) {
        d = d.join(",") + ".json";
        var b = Tfl.Maps.Framework.Models.SettingsHelper.contentBaseUrl + "framework/fixtures/places/" + d;
        return function () {
            var a;
            $.ajax({url: b, type: "get", dataType: "json", async: false}).done(function (c) {
                a = c
            });
            return[200, "success", a, {}]
        }
    }}, {getOurLatLng: function () {
        return Tfl.Maps.Framework.MapUtils.getOurLatLngFromTflPoint(this.point)
    }})
});
steal.loaded("framework/models/place.js");
steal("jquery/class", "jquery/lang/object", "//framework/map/layers/layer_filter_base.js", function () {
    Tfl.Maps.Framework.LayerFilterBase("Tfl.Maps.Road.SeverityLayerFilter", {}, {init: function (d, b) {
        this.name = "RoadIncidentSeverity";
        this._super(d, b);
        this.addFilterValue("Severe", "Severe road incidents", true);
        this.addFilterValue("Serious", "Serious road incidents", true);
        this.addFilterValue("Moderate", "Moderate road incidents", true);
        this.addFilterValue("Minimal", "Minimal road incidents", true)
    }})
});
steal.loaded("road/severity_layer_filter.js");
steal("jquery/model", "jquery/dom/fixture", "framework/helpers/api.js", "framework/map/map_utils.js", "framework/models/settings_helper.js", function () {
    $.Model("Tfl.Maps.Road.Models.RoadDisruption", {findAll: function (d, b, a) {
        return this.buildFindAll(d, b, a)
    }, findOne: function (d, b, a) {
        return this.buildFindOne(d, b, a)
    }, buildFindAll: function (d, b, a) {
        var c = Tfl.Maps.Framework.Models.SettingsHelper.apiProxyRoot;
        if (d.startDate !== null || d.endDate !== null) {
            c += "Road/all/Disruption/";
            if (d.startDate !== null)c += d.startDate.toTflApiString();
            c += "/to/";
            if (d.endDate !== null)c += d.endDate.toTflApiString();
            c += "?"
        } else c += "Road/all/Disruption?";
        c += "swLat=" + d.bounds.sw.lat + "&";
        c += "swLon=" + d.bounds.sw.lng + "&";
        c += "neLat=" + d.bounds.ne.lat + "&";
        c += "neLon=" + d.bounds.ne.lng + "&";
        c += "stripContent=true&";
        d = d.severityFilter.getVisibleFilterValues();
        if (d.length !== 0) {
            c += "severities=" + $.map(d, function (e) {
                return e.name
            }).join(",");
            c += Tfl.Maps.Framework.Models.SettingsHelper.apiQuerySuffix;
            return $.ajax({url: c, type: "get", dataType: "json road_disruption.models",
                success: b, error: a, fixture: "//road/fixtures/example_road_disruptions.json"})
        } else {
            b = $.Deferred();
            b.resolve([]);
            return b
        }
    }, buildFindOne: function (d, b, a) {
        var c = $.Deferred(), e = Tfl.Maps.Framework.Models.SettingsHelper.apiProxyRoot + "Road/all/Disruption/" + d.id + "?";
        e += Tfl.Maps.Framework.Models.SettingsHelper.apiQuerySuffix;
        $.ajax({url: e, type: "get", dataType: "json road_disruption.models", success: b, error: a, fixture: "//road/fixtures/example_road_disruption.json"}).done(function (h) {
            h = $.grep(h, function (j) {
                return j.id.toString() ===
                    d.id.toString()
            })[0];
            c.resolve(h);
            b && b(h)
        });
        return c
    }}, {getOurLatLng: function () {
        return Tfl.Maps.Framework.MapUtils.getOurLatLngFromTflPoint(this.point)
    }})
});
steal.loaded("road/models/road_disruption.js");
steal("jquery/class", "jquery/lang/object", function (d) {
    d.Class("Tfl.Maps.Framework.LayerFilterBase", {}, {init: function (b, a) {
        this.layer = b;
        this.filterValues = {};
        this.options = a
    }, hide: function (b) {
        this.filterValues[b].isVisible = false;
        this.options.hiding && this.options.hiding()
    }, show: function (b) {
        this.layer.show();
        this.filterValues[b].isVisible = true;
        this.options.showing && this.options.showing()
    }, isVisible: function (b) {
        return this.filterValues[b].isVisible
    }, addFilterValue: function (b, a, c, e) {
        this.filterValues[b] =
        {name: b, description: a, isVisible: c, iconUrl: e}
    }, getVisibleFilterValues: function () {
        return d.map(this.filterValues, function (b) {
            if (b.isVisible)return b
        })
    }})
});
steal.loaded("framework/map/layers/layer_filter_base.js");
steal(function () {
    Date.prototype.toTflApiString || function () {
        function d(b) {
            b = String(b);
            if (b.length === 1)b = "0" + b;
            return b
        }

        Date.prototype.toTflApiString = function () {
            return this.getUTCFullYear() + "-" + d(this.getUTCMonth() + 1) + "-" + d(this.getUTCDate()) + "T" + d(this.getUTCHours()) + ":" + d(this.getUTCMinutes())
        }
    }()
});
steal.loaded("framework/helpers/api.js");
steal("jquery/model", "jquery/dom/fixture", "framework/models/settings_helper.js", function () {
    $.Model("Tfl.Maps.Road.Models.RoadCorridor", {findAll: function (d, b) {
        return this.buildFindAll(d, b)
    }, buildFindAll: function (d, b) {
        return $.ajax({url: Tfl.Maps.Framework.Models.SettingsHelper.apiProxyRoot + "road/all/status?" + Tfl.Maps.Framework.Models.SettingsHelper.apiQuerySuffix, type: "get", dataType: "json road_corridor.models", success: d, error: b, fixture: "//road/fixtures/example_road_corridors.json"})
    }}, {})
});
steal.loaded("road/models/road_corridor.js");
steal("jquery/class", "./dynamic_layer_base.js", "framework/models/station_stop.js", "framework/models/settings_helper.js", "framework/models/route.js", "framework/classes/bus_routes_manager.js", "./station_stop_layer_state.js", "./labelled_pin.css", function (d) {
    Tfl.Maps.Framework.DynamicLayerBase("Tfl.Maps.Framework.StationStopLayer", {LAYER_NAME: "Stations, stops and piers", DISPLAY_INTERCHANGES_FROM_ZOOM: 14, DISPLAY_INTERCHANGES_TO_ZOOM: 16, DISPLAY_INTERCHANGE_LABELS_FROM_ZOOM: 15, DISPLAY_STATION_STOPS_FROM_ZOOM: 14,
        DISPLAY_STATION_LABELS_FROM: 15, DISPLAY_BUS_ROUTE_STOPS_FROM_ZOOM: 13, DISPLAY_BUS_STOPS_FROM_ZOOM: 15, DISPLAY_LARGE_BUS_STOPS_FROM_ZOOM: 17, MODE_NAMES_SORT_ORDER: ["tube", "bus", "overground", "dlr", "national-rail", "river-bus"], MODE_NAMES_TO_REMOVE: {plane: {}, "river-tour": {}, "international-rail": {}}, DEFAULT_MODES: ["tube", "bus", "coach", "overground", "dlr", "cable-car", "national-rail", "river-bus", "tram"]}, {init: function (b, a, c) {
        Date.now = Date.now || function () {
            return+new Date
        };
        if (!Object.keys)Object.keys = function (e) {
            var h =
                [], j;
            for (j in e)e.hasOwnProperty(j) && h.push(j);
            return h
        };
        this._super(b, a);
        this.chosenPlaceId = "";
        this.highlightedNaptanIds = {};
        this.latestCallNumber = 0;
        this.lastState = new Tfl.Maps.Framework.StationStopLayerState;
        this.modes = this.getModes(c.modes);
        this.renderStateChanged = true;
        this.busRouteLayer = c.busRouteLayer;
        this.dontAddBusRoutesToChosenPlace = false;
        this.stationStops = {};
        this.nonHubs = {};
        this.hubs = {};
        this.modeHubs = {};
        this.visibleStationStops = {};
        this.renderedHubs = [];
        this.renderedNonHubs = [];
        this.enableShortCommonNames =
            c.enableShortCommonNames;
        this.displayHubsBy = this.getZoomThreshold("displayHubsBy", c, this.constructor.DISPLAY_INTERCHANGES_FROM_ZOOM);
        this.displayHubsTo = this.getZoomThreshold("displayHubsTo", c, this.constructor.DISPLAY_INTERCHANGES_TO_ZOOM);
        this.displayHubLabelsBy = this.getZoomThreshold("displayHubLabelsBy", c, this.constructor.DISPLAY_INTERCHANGE_LABELS_FROM_ZOOM);
        this.displayStationsBy = this.getZoomThreshold("displayStationsBy", c, this.constructor.DISPLAY_STATION_STOPS_FROM_ZOOM);
        this.displayBusStopsBy =
            this.getZoomThreshold("displayBusStopsBy", c, this.constructor.DISPLAY_BUS_STOPS_FROM_ZOOM);
        this.displayLargeBusStopsBy = this.getZoomThreshold("displayLargeBusStopsBy", c, this.constructor.DISPLAY_LARGE_BUS_STOPS_FROM_ZOOM);
        this.displayLabelsBy = this.getZoomThreshold("displayLabelsBy", c, this.constructor.DISPLAY_STATION_LABELS_FROM);
        this.displayBusRouteStopsFrom = this.getZoomThreshold("displayBusRouteStopsFrom", c, this.constructor.DISPLAY_BUS_ROUTE_STOPS_FROM_ZOOM)
    }, choosePlace: function (b, a, c, e, h) {
        if (a.toLowerCase() ===
            "stoppoint") {
            this.selectItem(b, "stationstop", this);
            this.chosenPlaceId = b;
            this.lastState = new Tfl.Maps.Framework.StationStopLayerState;
            this.dontAddBusRoutesToChosenPlace = h;
            this.map.activateIfDeactivated();
            this.map.displayMapByCenter(c)
        }
    }, prepareClear: function (b) {
        if (this.renderStateChanged || !this.currentState.isAnythingToRender() || b) {
            this._super();
            this.renderedHubs = [];
            this.renderedNonHubs = []
        }
    }, clear: function (b) {
        if (b)this.isRendered = true;
        if (this.renderStateChanged || !this.currentState.isAnythingToRender() ||
            b) {
            this.prepareClear(b);
            this._super()
        }
    }, itemSelectedInOtherLayer: function () {
        var b = this.chosenStationStop && this.currentState && (this.currentState.hubsVisible && this.chosenStationStop.inHub || this.currentState.busStopsVisible);
        if (this.chosenStationStop) {
            if (this.chosenStationStop.pin) {
                this.chosenStationStop.pin.clear();
                this.chosenStationStop.pin = null
            }
            this.chosenPlaceId = "";
            this.dontAddBusRoutesToChosenPlace = false;
            b && this.addStationStop(this.chosenStationStop)
        }
    }, layerNeedsToRender: function () {
        var b = this.lastState;
        this.currentState = new Tfl.Maps.Framework.StationStopLayerState;
        this.setCurrentState();
        this.lastState = this.currentState;
        (this.renderStateChanged = !this.currentState.isSame(b)) && this.clear();
        return this.renderStateChanged
    }, setCurrentState: function () {
        var b = this.map.getZoom();
        if (b >= this.displayHubsBy) {
            if (b <= this.displayHubsTo) {
                this.currentState.hubsVisible = true;
                if (this.displayHubLabelsBy <= b)this.currentState.hubLabelsVisible = true
            }
            if (b >= this.displayStationsBy) {
                this.currentState.stationsVisible = true;
                if (this.displayLabelsBy <=
                    b)this.currentState.stationLabelsVisible = true
            }
        }
        if (b >= this.displayBusRouteStopsFrom)this.currentState.busRouteStopsVisible = true;
        if (b >= this.displayBusStopsBy) {
            this.currentState.busStopsVisible = true;
            if (b >= this.displayLargeBusStopsBy)this.currentState.largeBusStopsVisible = true
        }
    }, render: function (b) {
        this.currentState == undefined && this.layerNeedsToRender();
        if (!this.currentState.isAnythingToRender()) {
            this.clear();
            if (this.departurePoint || this.arrivalPoint) {
                this.addStationStop(this.departurePoint);
                this.addStationStop(this.arrivalPoint)
            }
            if (this.routeTermini)for (b =
                                           0; b < this.routeTermini.length; b++)this.addStationStop(this.routeTermini[b]);
            return null
        }
        var a = this.getFilters();
        if (a.stopTypes.length == 0)return null; else {
            this.latestCallNumber++;
            var c = this.latestCallNumber;
            return Tfl.Maps.Framework.Models.StationStop.findAll({bounds: b, stopTypes: a.stopTypes, modes: this.modes}).done(this.proxy(function (e) {
                c === this.latestCallNumber && this.handleDelayedGoogleLoad(e)
            })).fail(function () {
            })
        }
    }, getFilters: function () {
        var b = {stopTypes: [], modes: []};
        this.currentState.hubsVisible &&
        b.stopTypes.push("TransportInterchange");
        if (this.currentState.stationsVisible) {
            b.stopTypes.push("NaptanMetroStation");
            b.stopTypes.push("NaptanRailStation");
            b.stopTypes.push("NaptanBusCoachStation");
            b.stopTypes.push("NaptanFerryPort")
        }
        this.currentState.busStopsVisible && b.stopTypes.push("NaptanPublicBusCoachTram");
        return b
    }, handleDelayedGoogleLoad: function (b) {
        this.addHtmlPin({position: {lat: 0, lng: 0}, html: ""}).googleOverlay.wiredup(this.proxy(function () {
            this.handleDelayedGoogleLoad = this.proxy(function (a) {
                Date.now();
                this.processData(a)
            });
            this.handleDelayedGoogleLoad(b)
        }))
    }, processData: function (b) {
        var a = this;
        this.stationStops = {};
        this.nonHubs = {};
        this.hubs = {};
        this.modeHubs = {};
        this.visibleStationStops = {};
        d.each(b, function (c, e) {
            a.stationStops[e.naptanId] = e
        });
        this.chosenBusStops && d.each(this.chosenBusStops, function (c, e) {
            c = e.id;
            e.naptanId = c;
            if (a.stationStops[c] === undefined)a.stationStops[c] = e; else a.stationStops[c].linesStartingOrStopping = e.linesStartingOrStopping
        });
        if (this.departurePoint)if (a.stationStops[this.departurePoint.naptanId] ===
            undefined)a.stationStops[this.departurePoint.naptanId] = this.departurePoint;
        if (this.arrivalPoint)if (a.stationStops[this.arrivalPoint.naptanId] === undefined)a.stationStops[this.arrivalPoint.naptanId] = this.arrivalPoint;
        d.each(a.stationStops, function (c, e) {
            a.processStationStop(e)
        });
        d.each(a.nonHubs, function (c, e) {
            c = e.modes.length == 1 && e.modes[0] == "bus";
            e.inHub = e.hubNaptanCode !== undefined && !c
        });
        d.each(a.hubs, function (c, e) {
            if (a.modes && a.modes.length > 0)e.modeNames = a.getFormattedListOfModeNames(e.modeNames);
            d.each(e.modeNames, function (h, j) {
                a.modeHubs[e.id + "_" + j] = {stationStops: [], hub: e, modeName: j}
            })
        });
        if (this.currentState.hubsVisible) {
            a.nonHubs = d.map(a.nonHubs, function (c) {
                return c.inHub ? null : c
            });
            d.each(a.sortStationStopsByLatLon(a.hubs), function (c, e) {
                if (d.inArray(e.id, a.renderedHubs) == -1) {
                    a.addStationStop(e);
                    a.renderedHubs.push(e.id)
                }
            })
        }
        d.each(a.sortStationStopsByLatLon(a.nonHubs), function (c, e) {
            if (d.inArray(e.naptanId, a.renderedNonHubs) == -1) {
                a.addStationStop(e);
                a.renderedNonHubs.push(e.naptanId)
            }
        })
    },
        getFormattedListOfModeNames: function (b) {
            var a = this.modes;
            return d.grep(b, function (c) {
                return d.inArray(c, a) > -1
            })
        }, processStationStop: function (b) {
            var a = this;
            b.modeNames = d.map(b.modes, function (c) {
                return a.constructor.MODE_NAMES_TO_REMOVE[c] ? null : b.modes.length != 1 && c === "bus" ? null : c
            });
            b.shortCommonName = b.commonName;
            if (b.stopType === "TransportInterchange") {
                this.hubs[b.id] = b;
                this.sortModeNames(b.modeNames)
            } else this.nonHubs[b.id] = b;
            b.modeNames = this.sortModeNames(b.modeNames);
            if (b.linesStartingOrStopping ==
                undefined)b.linesStartingOrStopping = {}
        }, sortStationStopsByLatLon: function (b) {
            return d.map(b, function (a) {
                return a
            }).sort(function (a, c) {
                return c.lat - a.lat
            })
        }, sortModeNames: function (b) {
            if (b.length <= 1)return b;
            var a = this;
            return b.sort(function (c, e) {
                c = d.inArray(c, a.constructor.MODE_NAMES_SORT_ORDER);
                e = d.inArray(e, a.constructor.MODE_NAMES_SORT_ORDER);
                c = c == -1 ? 10 : c;
                e = e == -1 ? 10 : e;
                return c - e
            })
        }, addStationStop: function (b) {
            var a = this, c = {lat: b.lat, lng: b.lon};
            this.visibleStationStops[b.naptanId] = b;
            if (b.naptanId &&
                b.naptanId === this.chosenPlaceId) {
                b.chosen = true;
                this.isStationStopABusStop(b) && !this.dontAddBusRoutesToChosenPlace && this.addBusRoutes(b, false)
            } else b.chosen = false;
            b.highlighted = this.highlightedNaptanIds[b.naptanId] ? true : false;
            if (this.shouldShowSmallBusPin(b)) {
                if (this.currentState.busStopsVisible || this.currentState.busRouteStopsVisible) {
                    c = this.addPin({position: c, iconUrl: Tfl.Maps.Framework.Models.SettingsHelper.contentBaseUrl + "framework/map/layers/icons/bus-extra-small.png", retinaIconUrl: Tfl.Maps.Framework.Models.SettingsHelper.contentBaseUrl +
                        "framework/map/layers/icons/bus-extra-small_x2.png", size: {width: 8, height: 9}, tooltip: b.shortCommonName, highlighted: b.highlighted, cursor: "grab"});
                    b.pin = c
                }
            } else {
                var e = this.isStationOnABusRoute(b);
                e = this.getHtmlForStationStop(b, e);
                c = {position: c, html: e, tooltip: b.shortCommonName};
                c.iconAnchor = {y: b.highlighted ? -12 : 0, offsetFromBottom: b.highlighted};
                if (b.modeNames.length == 1 && b.modeNames[0] === "bus")c.iconAnchor = b.chosen ? {x: 16, y: 48} : {y: 16, x: 16};
                b.pin = this.addHtmlPin(c);
                b.pin.click(function () {
                    a.stationStopClicked(b)
                })
            }
            if (b.chosen)this.chosenStationStop =
                b
        }, shouldShowSmallBusPin: function (b) {
            var a = this.routeTermini && d.grep(this.routeTermini, function (e) {
                return e.naptanId == b.naptanId
            }).length > 0;
            if (this.currentState.busRouteStopsVisible && !this.currentState.largeBusStopsVisible && this.isStationOnABusRoute(b) && !a)return true;
            a = this.isStationStopABusStop(b) && (!this.currentState || !this.currentState.largeBusStopsVisible) && !b.chosen;
            var c = this.chosenBusStops && d.grep(this.chosenBusStops, function (e) {
                return e.id == b.id
            }).length > 0;
            if (this.departurePoint && this.departurePoint.naptanId ===
                b.id || this.arrivalPoint && this.arrivalPoint.naptanId === b.id || c && !a || c && Object.keys(b.linesStartingOrStopping).length != 0)a = false;
            return a
        }, isStationOnABusRoute: function (b) {
            return this.chosenBusStops && d.grep(this.chosenBusStops, function (a) {
                return a.modes.length == 1 && a.modes[0] === "bus" && a.id == b.id
            }).length > 0
        }, getHtmlForStationStop: function (b, a) {
            var c = this.showStationStopLabelText(b) ? this.getFormattedStationStopLabelText(b) : "", e = d.trim(c).length > 0;
            if (b.linesStartingOrStopping && Object.keys(b.linesStartingOrStopping).length !=
                0)a = false;
            if (b.chosen)a = false;
            return d.View("//framework/map/layers/station_stop_pin.ejs", {label: c, hasLabel: e, modeNames: b.modeNames, rootUrl: Tfl.Maps.Framework.Models.SettingsHelper.contentBaseUrl + "framework/map/layers/icons/", stopLetter: b.stopLetter ? b.stopLetter : "", highlighted: b.highlighted || a, chosen: b.chosen, compassPoint: this.getCompassPointForStationStop(b), linesStartingOrStopping: b.linesStartingOrStopping || {}, onBusRoute: a})
        }, showStationStopLabelText: function (b) {
            b = b.id && b.id.toLowerCase().substr(0,
                3) == "hub";
            var a = this.map.getZoom();
            return b ? this.displayHubLabelsBy <= a : this.displayLabelsBy <= a
        }, getFormattedStationStopLabelText: function (b) {
            b = b.shortCommonName;
            if (!this.enableShortCommonNames)return b;
            return b.replace("Underground Station", "").replace("Rail Station", "").replace("DLR Station", "").replace("Station", "").replace("Pier", "")
        }, getCompassPointForStationStop: function (b) {
            if (b.additionalProperties)for (var a = 0; a < b.additionalProperties.length; a++)if (b.additionalProperties[a].key == "CompassPoint")return b.additionalProperties[a].value.toLowerCase();
            return null
        }, uniqueArray: function (b) {
            return d.grep(b, function (a, c) {
                return c == d.inArray(a, b)
            })
        }, stationStopClicked: function (b) {
            this.selectItem(b.id, "stationstop", this);
            this.dontAddBusRoutesToChosenPlace = false;
            b.hasRoutesAdded = false;
            var a = {lat: b.lat, lng: b.lon};
            if (this.isStationStopABusStop(b)) {
                this.map.displayMapByCenter(a);
                this.map.setZoom(17);
                this.addBusRoutes(b, true)
            } else {
                this.map.displayMapByCenter(a);
                this.map.setZoom(17)
            }
            if (this.chosenStationStop) {
                if (this.chosenStationStop.pin) {
                    this.chosenStationStop.pin.clear();
                    this.chosenStationStop.pin = null
                }
                this.chosenPlaceId = "";
                this.addStationStop(this.chosenStationStop)
            }
            this.chosenPlaceId = this.chosenPlaceId == b.id ? "" : b.id;
            b.pin.clear();
            this.addStationStop(b);
            this.map.options.placeChosen && this.map.options.placeChosen({id: b.id, placeType: "stationstop", commonName: b.commonName});
            this.map.options.stationStopsChosen && this.map.options.stationStopsChosen([
                {naptanId: b.id, stopType: b.stopType, modeNames: b.modeNames, commonName: b.commonName}
            ])
        }, setHighlightedStationStops: function (b, a, c) {
            this.currentState = new Tfl.Maps.Framework.StationStopLayerState;
            this.setCurrentState();
            this.lastState = this.currentState;
            this.markNaptanIdsAsHighlighted(b);
            this.departurePoint && this.departurePoint.pin && this.departurePoint.pin.clear();
            this.arrivalPoint && this.arrivalPoint.pin && this.arrivalPoint.pin.clear();
            if (a) {
                this.departurePoint = a;
                this.departurePoint.id = this.departurePoint.naptanId;
                this.processStationStop(this.departurePoint);
                this.addStationStop(this.departurePoint)
            }
            if (c) {
                this.arrivalPoint = c;
                this.arrivalPoint.id = this.arrivalPoint.naptanId;
                this.processStationStop(this.arrivalPoint);
                this.addStationStop(this.arrivalPoint)
            }
        }, markNaptanIdsAsHighlighted: function (b) {
            var a = this;
            this.highlightedNaptanIds && d.each(this.highlightedNaptanIds, function (c, e) {
                if (this.stationStops)this.stationStops[e].isHighlighted = true
            });
            this.highlightedNaptanIds = {};
            d.each(b, function (c, e) {
                a.highlightedNaptanIds[e] = true
            })
        }, getModes: function (b) {
            if (b !== undefined && b !== null && b.length > 0)return b;
            return this.constructor.DEFAULT_MODES
        },
        isStationStopABusStop: function (b) {
            return b.modeNames && b.modeNames.length == 1 && b.modeNames[0] === "bus"
        }, addBusRoutes: function (b) {
            if (!b.hasRoutesAdded) {
                b.hasRoutesAdded = true;
                this.busRouteLayer && this.busRouteLayer.addRouteForStop(b.id, null, false).then(this.proxy(this.routeAdded))
            }
        }, routeAdded: function (b) {
            var a = this, c = 0, e = function () {
                a.clear(true);
                a.processData(a.stationStops)
            };
            this.chosenBusStops = [];
            this.routeTermini = [];
            b.length == 0 && e();
            d.each(a.stationStops, function (h, j) {
                j.linesStartingOrStopping = undefined
            });
            d.each(b, function (h, j) {
                Tfl.Maps.Framework.Models.StationStop.findStopsAlongLine({lineId: j.lineId, direction: j.direction}).then(function (f) {
                    d.merge(a.chosenBusStops, f);
                    d.each(a.chosenBusStops, function (g, k) {
                        k.highlighted = true;
                        k.linesStartingOrStopping = {}
                    });
                    c++;
                    c == b.length && Tfl.Maps.Framework.BusRoutesManager.getStartEndPointsForLine(j.lineId, j.direction).then(function (g) {
                        d.each(a.chosenBusStops, function (k, m) {
                            if (g.startPoints[m.id] !== undefined || g.endPoints[m.id] !== undefined) {
                                a.processStationStop(m);
                                m.linesStartingOrStopping[j.lineId] =
                                {lineId: j.lineId, lineName: a.mapLineIdToName(g, j.lineId)};
                                a.routeTermini.push(m)
                            }
                        });
                        e()
                    })
                })
            })
        }, mapLineIdToName: function (b, a) {
            if (this.lineToNameMapping === undefined)this.lineToNameMapping = {};
            if (this.lineToNameMapping[a] !== undefined)return this.lineToNameMapping[a];
            for (var c = Object.keys(b.startPoints), e = 0; e < c.length; e++)for (var h = b.startPoints[c[e]], j = 0; j < h.lines.length; j++)if (h.lines[j].id === a) {
                this.lineToNameMapping[a] = h.lines[j].name;
                return h.lines[j].name
            }
            e = Object.keys(b.endPoints);
            for (h = 0; h < c.length; h++) {
                j =
                    b.endPoints[e[h]];
                for (var f = 0; f < j.lines.length; f++)if (j.lines[f].id === a) {
                    this.lineToNameMapping[a] = j.lines[f].name;
                    return j.lines[f].name
                }
            }
            return a
        }})
});
steal.loaded("framework/map/layers/station_stop_layer.js");
steal("./places_layer.js", "framework/models/bikepoint.js", "framework/models/settings_helper.js", function (d) {
    Tfl.Maps.Framework.PlacesLayer("Tfl.Maps.Framework.CycleHireLayer", {LAYER_NAME: "Docking stations", LAYER_ICON_PATH: "framework/map/layers/icons/cycle-hire_layer.png", DISPLAY_BY_ZOOM: 15, SHOW_DETAILED_PINS_BY: 17, ICON_SIZE: {width: 8, height: 9}, LAYER_CLASS_SUFFIX: "cycle-hire"}, {init: function (b, a, c) {
        c.placeTypes = ["BikePoint"];
        c.categories = ["Description"];
        if (c.displayByZoom === undefined)c.displayByZoom =
            this.constructor.DISPLAY_BY_ZOOM;
        c.displayByZoom = this.getZoomThreshold("cycleHirePinsBy", c, c.displayByZoom);
        this.showDetailsPinsBy = this.getZoomThreshold("cycleHireDetailedPinsBy", c, this.constructor.SHOW_DETAILED_PINS_BY);
        this.smallPinsClickAble = c.smallPinsClickAble || false;
        this._super(b, a, c)
    }, fetchData: function (b) {
        return Tfl.Maps.Framework.Models.BikePoint.findAll({bounds: b})
    }, shouldUseHtmlPin: function () {
        return this.map.getZoom() >= this.showDetailsPinsBy ? true : false
    }, getIconUrlForPlace: function () {
        return Tfl.Maps.Framework.Models.SettingsHelper.contentBaseUrl +
            "framework/map/layers/icons/cycle-hire-small.png"
    }, getRetinaIconUrlForPlace: function () {
        return Tfl.Maps.Framework.Models.SettingsHelper.contentBaseUrl + "framework/map/layers/icons/cycle-hire-small_x2.png"
    }, getClassSuffixForPlace: function (b) {
        var a = this.selectedPlace.id == b.id, c = this.map.getZoom() >= this.showDetailsPinsBy;
        if (a || c) {
            a = parseInt(d.grep(b.additionalProperties, function (e) {
                return e.key === "NbBikes"
            })[0].value);
            b = parseInt(d.grep(b.additionalProperties, function (e) {
                return e.key === "NbEmptyDocks"
            })[0].value);
            b = Math.floor(a / (a + b) * 1E3 / 125) * 125;
            if (b == "0" && a > 0)b = "125";
            if (b == "0")b = "000";
            return"cycle-hire-" + b
        } else return""
    }, allowPinClick: function () {
        return this.smallPinsClickAble || this.shouldUseHtmlPin()
    }, baseLayerNeedsToRender: function () {
        var b = this.map.getZoom(), a = this.previousZoomLevel;
        if (!a)return false;
        return b < this.showDetailsPinsBy && this.showDetailsPinsBy <= a || a < this.showDetailsPinsBy && this.showDetailsPinsBy <= b
    }})
});
steal.loaded("framework/map/layers/cycle_hire_layer.js");
steal("jquery/class", "./static_layer_base.js", "framework/map/map_utils.js", "framework/models/route.js", "framework/classes/bus_routes_manager.js", "journeyplanner/journey_map/journey_polyline_composer.js", function (d) {
    Tfl.Maps.Framework.StaticLayerBase("Tfl.Maps.Framework.BusRouteLayer", {}, {init: function (b, a) {
        this._super(b, a);
        this.routesForStop = {};
        this.routesForLine = {};
        this.previousLineId = this.previousStopId = ""
    }, addRouteForStop: function (b, a, c) {
        var e = d.Deferred(), h = {};
        if (b != this.previousStopId || a != this.previousLineId)this.hide();
        if (this.routesForStop[b]) {
            h = a === undefined || a === null ? this.routesForStop[b] : this.findRoutesForLine(this.routesForStop[b], a);
            this.renderRoutes(h, c);
            e.resolve(h)
        } else Tfl.Maps.Framework.Models.BusRoute.findAll({stopId: b}).then(this.proxy(function (j) {
            h = j;
            this.routesForStop[b] = h;
            if (a !== undefined && a !== null)h = this.findRoutesForLine(h, a);
            this.renderRoutes(h, c);
            e.resolve(h)
        })).fail(function () {
        });
        this.previousStopId = b;
        this.previousLineId = a;
        return e
    }, addRouteForLine: function (b, a, c) {
        var e = d.Deferred(), h = {};
        if (b !=
            this.previousLineId || a != this.previousDiectionId)this.hide();
        if (this.routesForLine[b + a]) {
            h = this.routesForLine[b + a];
            this.renderRoutes(h, c);
            e.resolve(h)
        } else Tfl.Maps.Framework.Models.BusRoute.findAll({lineId: b, direction: a}).then(this.proxy(function (j) {
            h = j;
            this.routesForLine[b + a] = h;
            this.renderRoutes(h, c);
            e.resolve(h)
        })).fail(function () {
        });
        this.previousLineId = b;
        this.previousDiectionId = a;
        return e
    }, render: function () {
    }, findRoutesForLine: function (b, a) {
        return d.grep(b, function (c) {
            return c.lineId === a
        })
    }, renderRoutes: function (b, a) {
        d.each(b, this.proxy(function (c, e) {
            this.renderRoute(e)
        }));
        a && this.zoomToFit(b);
        this.isRendered = true
    }, renderRoute: function (b) {
        var a = this;
        b = Tfl.Maps.JourneyPlanner.JourneyPolylineComposer.getPolylineSpecs({path: b.path, mode: "bus", showArrows: false, showChevrons: true});
        d.each(b, function (c, e) {
            a.addPolyline(e)
        })
    }, zoomToFit: function (b) {
        var a = [];
        d.each(b, this.proxy(function (c, e) {
            a = a.concat(e.path)
        }));
        this.map.displayMapByPositions(a, true)
    }})
});
steal.loaded("framework/map/layers/bus_route_layer.js");
steal("jquery/model", "framework/fixtures", "framework/map/map_utils.js", "framework/models/settings_helper.js", function () {
    $.Model("Tfl.Maps.Framework.Models.StationStop", {findAll: function (d, b, a) {
        return this.buildFindAll(d, b, a)
    }, buildFindAll: function (d, b, a) {
        var c = $.Deferred(), e = d.bounds, h = d.modes != null && d.modes != undefined ? d.modes : [], j = d.stopTypes != null && d.stopTypes != undefined ? d.stopTypes : [];
        j = "swLat=" + this.roundCoordinate(e.sw.lat) + "&swLon=" + this.roundCoordinate(e.sw.lng) + "&neLat=" + this.roundCoordinate(e.ne.lat) +
            "&neLon=" + this.roundCoordinate(e.ne.lng) + "&stopTypes=" + j.join(",") + "&modes=" + h.join(",") + "&includeChildren=false&returnLines=false&additionalPropertiesCategories=Direction";
        j = Tfl.Maps.Framework.Models.SettingsHelper.apiProxyRoot + "StopPoint?" + j + Tfl.Maps.Framework.Models.SettingsHelper.apiQuerySuffix;
        d = {url: j, type: "get", dataType: "json station_stop.models", error: a, fixture: this.mapFixture(e, h, d.stopTypes, j)};
        $.ajax(d).done(this.proxy(function (f) {
            c.resolve(f);
            b && b(f)
        }));
        return c
    }, roundCoordinate: function (d) {
        return d <
            0 ? Math.floor(d * 1E4) / 1E4 : Math.ceil(d * 1E4) / 1E4
    }, mapFixture: function (d, b, a) {
        d = ("" + this.roundCoordinate(d.sw.lat) + this.roundCoordinate(d.sw.lng) + this.roundCoordinate(d.ne.lat) + this.roundCoordinate(d.ne.lng)).replace(/[-|,|.]/g, "");
        b = b != null && b != undefined ? b.join("").replace(/-/g, "") : "";
        a = a != null && a != undefined ? a.join("").replace(/Naptan/g, "") : "";
        var c = "framework/fixtures/ssp_" + d + b + "_" + a + ".json";
        c = c.replace("TransportInterchangeMetroStationRailStationBusCoachStationFerryPortPublicBusCoachTram", "allstoptypes");
        return function () {
            var e;
            $.ajax({url: Tfl.Maps.Framework.Models.SettingsHelper.contentBaseUrl + c, type: "get", dataType: "json", async: false}).done(function (h) {
                e = h
            });
            return[200, "success", e, {}]
        }
    }, findStopsAlongLine: function (d, b, a) {
        var c = $.Deferred(), e = Tfl.Maps.Framework.Models.SettingsHelper.apiProxyRoot;
        e += "Line/" + d.lineId + "/Route/sequence/" + (d.direction || "outbound") + "?";
        e += Tfl.Maps.Framework.Models.SettingsHelper.apiQuerySuffix;
        $.ajax({url: e, type: "get", dataType: "json station_stop.models", error: a}).done(this.proxy(function (h) {
            var j =
                [];
            $.each(h.stopPointSequences, function (f, g) {
                $.each(g.stopPoint, function (k, m) {
                    j.push(m)
                })
            });
            c.resolve(j);
            b && b(j)
        }));
        return c
    }}, {})
});
steal.loaded("framework/models/station_stop.js");
steal("jquery/model", "framework/fixtures", "framework/map/map_utils.js", "framework/models/settings_helper.js", function () {
    $.Model("Tfl.Maps.Framework.Models.BusRoute", {findAll: function (d, b, a) {
        return this.buildFindAll(d, b, a)
    }, buildFindAll: function (d, b, a) {
        b = Tfl.Maps.Framework.Models.SettingsHelper.apiProxyRoot;
        b += d.stopId !== undefined && d.stopId !== null ? "StopPoint/" + d.stopId + "/Route?" : "Line/" + d.lineId + "/Route/Sequence/" + d.direction + "?";
        b += Tfl.Maps.Framework.Models.SettingsHelper.apiQuerySuffix;
        return $.ajax({url: b,
            type: "get", dataType: "json bus_route.models", error: a, fixture: "//framework/fixtures/route.json"})
    }, models: function (d) {
        var b = this;
        if (d.lineId !== undefined) {
            var a = [];
            $.each(d.lineStrings, function (c, e) {
                a.push({naptanId: "wholeroute", lineId: d.lineId, direction: d.direction, path: b.parseLineString(e)})
            });
            return a
        } else {
            $.each(d, function (c, e) {
                e.path = b.parseLineString(e.lineString);
                e.lineString = null
            });
            return d
        }
    }, parseLineString: function (d) {
        d = $.parseJSON(d);
        return $.map(d, function (b) {
            return b[0] < b[1] ? {lat: b[1], lng: b[0]} :
            {lat: b[0], lng: b[1]}
        })
    }}, {})
});
steal.loaded("framework/models/route.js");
steal("jquery/class", "framework/models/route.js", "framework/models/settings_helper.js", function (d) {
    d.Class("Tfl.Maps.Framework.BusRoutesManager", {init: function () {
        this.lines = {};
        this.stops = {}
    }, getStartEndPointsForLine: function (b, a) {
        var c = d.Deferred(), e = this;
        this.lines[b + "_" + a] === undefined ? this.fetchLineData(b, a).then(function () {
            c.resolve(e.lines[b + "_" + a])
        }) : c.resolve(e.lines[b + "_" + a]);
        return c
    }, getEndPointsForStop: function () {
        this.lines[lineId + "_" + direction] = {}
    }, fetchLineData: function (b, a) {
        var c = d.Deferred(),
            e = this, h = Tfl.Maps.Framework.Models.SettingsHelper.apiProxyRoot;
        h += "Line/" + b + "/Route/sequence/" + a + "?";
        h += Tfl.Maps.Framework.Models.SettingsHelper.apiQuerySuffix;
        d.ajax(h).then(function (j) {
            var f = d.grep(j.stopPointSequences, function (k) {
                return k.prevBranchIds.length == 0
            });
            j = d.grep(j.stopPointSequences, function (k) {
                return k.nextBranchIds.length == 0
            });
            var g = {startPoints: [], endPoints: []};
            if (f.length !== 0 || j.length !== 0) {
                d.each(f, function (k, m) {
                    if (m.stopPoint.length !== 0) {
                        k = m.stopPoint[0];
                        g.startPoints[k.id] = k
                    }
                });
                d.each(j, function (k, m) {
                    if (m.stopPoint.length !== 0) {
                        k = m.stopPoint[m.stopPoint.length - 1];
                        g.endPoints[k.id] = k
                    }
                })
            }
            e.lines[b + "_" + a] = g;
            c.resolve()
        });
        return c
    }}, {})
});
steal.loaded("framework/classes/bus_routes_manager.js");
steal("jquery/class", "jquery/lang/object", function (d) {
    d.Class("Tfl.Maps.Framework.StationStopLayerState", {}, {init: function () {
        this.busRouteStopsVisible = this.stationLabelsVisible = this.hubLabelsVisible = this.largeBusStopsVisible = this.busStopsVisible = this.stationsVisible = this.hubsVisible = false
    }, isSame: function (b) {
        return this.hubsVisible == b.hubsVisible && this.stationsVisible == b.stationsVisible && this.busStopsVisible == b.busStopsVisible && this.largeBusStopsVisible == b.largeBusStopsVisible && this.hubLabelsVisible ==
            b.hubLabelsVisible && this.stationLabelsVisible == b.stationLabelsVisible && this.busRouteStopsVisible == b.busRouteStopsVisible
    }, isAnythingToRender: function () {
        return this.hubsVisible || this.stationsVisible || this.busStopsVisible || this.busRouteStopsVisible
    }})
});
steal.loaded("framework/map/layers/station_stop_layer_state.js");
steal("jquery/model", "framework/fixtures", "framework/map/map_utils.js", "framework/models/settings_helper.js", function () {
    $.Model("Tfl.Maps.Framework.Models.BikePoint", {findAll: function (d, b, a) {
        var c = Tfl.Maps.Framework.MapUtils, e = $.Deferred(), h = d.bounds;
        d = c.distanceBetweenPoints(h.sw, h.ne).toFixed(1);
        var j = Tfl.Maps.Framework.Models.SettingsHelper.apiProxyRoot + "BikePoint?" + Tfl.Maps.Framework.Models.SettingsHelper.apiQuerySuffix;
        if (d < 121)j += "&swLat=" + this.roundCoordinate(h.sw.lat) + "&swLon=" + this.roundCoordinate(h.sw.lng) +
            "&neLat=" + this.roundCoordinate(h.ne.lat) + "&neLon=" + this.roundCoordinate(h.ne.lng);
        a = {url: j, type: "get", dataType: "json bike_point.models", error: a, fixture: this.mapFixture()};
        $.ajax(a).done(this.proxy(function (f) {
            f = $.fixture.on ? c.filterArrayByBounds(f, h, function (g) {
                return g.lat
            }, function (g) {
                return g.lon
            }) : c.filterArrayByBounds(f, h, function (g) {
                return g.lat
            }, function (g) {
                return g.lon
            });
            e.resolve(f);
            b && b(f)
        }));
        return e
    }, roundCoordinate: function (d) {
        return d < 0 ? Math.floor(d * 1E4) / 1E4 : Math.ceil(d * 1E4) / 1E4
    }, mapFixture: function () {
        var d =
            Tfl.Maps.Framework.Models.SettingsHelper.contentBaseUrl + "framework/fixtures/places/bikepoint.json";
        return function () {
            var b;
            $.ajax({url: d, type: "get", dataType: "json", async: false}).done(function (a) {
                b = a
            });
            return[200, "success", b, {}]
        }
    }}, {getOurLatLng: function () {
        return Tfl.Maps.Framework.MapUtils.getOurLatLngFromTflPoint(this.point)
    }})
});
steal.loaded("framework/models/bikepoint.js");
steal("jquery/class", "framework/models/settings_helper.js", function (d) {
    d.Class("Tfl.Maps.JourneyPlanner.JourneyPolylineComposer", {TUBE_LINE_COLORS: {bakerloo: "#b36408", central: "#e32219", circle: "#fcca00", district: "#006633", "hammersmith & city": "#f3a9bb", jubilee: "#a0a5a9", metropolitan: "#9b0057", northern: "#000000", piccadilly: "#003888", victoria: "#0098d4", "waterloo & city": "#95cdba"}, OTHER_MODE_COLORS: {overground: "#f87014", bus: "#dc241f", dlr: "#00afad", tram: "#00bd19", walking: "#97a69b", "river-bus": "#00a0e2",
        "cable-car": "#e21836", "national-rail": "#ff0000", coach: "#ff9900"}, GENERIC_COLOR: "#0000ff", OUTLINE_WEIGHTS: [, 6, 11, 16], getPolylineSpecs: function (b) {
        var a, c, e = b.mode;
        c = b.routes;
        var h = [], j = b.showArrows != null && b.showArrows != undefined ? b.showArrows : true, f = b.showChevrons != null && b.showChevrons != undefined ? b.showChevrons : false, g = function (k, m) {
            h.push({path: b.path, colors: k, weight: m, showArrows: j, showChevrons: f})
        };
        if (e === "tube") {
            a = this;
            c = d.map(c, function (k) {
                return a.TUBE_LINE_COLORS[k]
            })
        } else c = [this.OTHER_MODE_COLORS[e] ||
            this.GENERIC_COLOR];
        g("#eeeeee", this.OUTLINE_WEIGHTS[c.length]);
        switch (e) {
            case "cable-car":
                e = c[0];
                g([e, e, e], 1);
                break;
            case "national-rail":
                g(c, 4);
                g("#ffffff", 3);
                break;
            case "overground":
                g(c, 4);
                g("#ffffff", 2);
                break;
            case "coach":
                g(c, 4);
                g("#ffffff", 2);
                break;
            default:
                g(c, 4);
                break
        }
        return h
    }}, {})
});
steal.loaded("journeyplanner/journey_map/journey_polyline_composer.js");
steal("jquery/class", "./static_layer_base", "lib/togeojson.js", "framework/map/map_utils.js", "framework/models/settings_helper.js", "./labelled_pin.css", function (d) {
    var b = Tfl.Maps.Framework.MapUtils;
    Tfl.Maps.Framework.StaticLayerBase("Tfl.Maps.Framework.StaticKmlLayer", {}, {init: function (a, c, e) {
        this._super(a, c);
        this.url = e.url
    }, render: function () {
        this.bounds = {ne: this.map.getCenter(), sw: this.map.getCenter()};
        return d.ajax({url: this.url, dataType: "text", error: function () {
        }, dataFilter: this.cleanXml}).then(this.proxy(this.processData)).fail(function () {
        })
    },
        cleanXml: function (a) {
            return Object.prototype.toString.call(a) === "[object Document]" || Object.prototype.toString.call(a) === "[object XMLDocument]" ? a : a.replace('xsi:schemaLocation="http://www.opengis.net/kml/2.2 http://schemas.opengis.net/kml/2.2.0/ogckml22.xsd"', "")
        }, processData: function (a) {
            this.renderGeoJson(this.parseKmlToGeoJson(Object.prototype.toString.call(a) === "[object Document]" || Object.prototype.toString.call(a) === "[object XMLDocument]" ? a : d.parseXML(a)));
            this.map.displayMapByBoundingBox(this.bounds)
        },
        parseKmlToGeoJson: function (a) {
            return window.toGeoJSON.kml(a, {styles: true})
        }, renderGeoJson: function (a) {
            var c = [];
            d.each(a.features, this.proxy(function (e, h) {
                switch (h.geometry.type) {
                    case "Point":
                        c.push(h);
                        break;
                    case "LineString":
                        this.addLineStringFeature(h);
                        break;
                    case "Polygon":
                        this.addPolygonFeature(h);
                        break;
                    case "GeometryCollection":
                        break
                }
            }));
            c = c.sort(function (e, h) {
                return h.geometry.coordinates[1] - e.geometry.coordinates[1]
            });
            d.each(c, this.proxy(function (e, h) {
                this.addPointFeature(h)
            }))
        }, addPointFeature: function (a) {
            var c =
            {position: {lat: a.geometry.coordinates[1], lng: a.geometry.coordinates[0]}, tooltip: a.properties.name || ""};
            if (a.properties.style)if (a.properties.style.iconUrl)if (a.properties.style.iconUrl.indexOf("maps.google.com") == -1)c.iconUrl = a.properties.style.iconUrl;
            c.html = d.View("//framework/map/layers/kml_pin.ejs", {label: a.properties.name || "", iconUrl: c.iconUrl ? c.iconUrl : null, chosen: false});
            var e = this.addHtmlPin(c);
            e.click(this.proxy(function () {
                this.pinClicked(e, a)
            }));
            this.addPointsToExtent([c.position])
        }, addPolygonFeature: function (a) {
            if (a.geometry.coordinates.length ==
                1) {
                var c = {path: d.map(a.geometry.coordinates[0], function (h) {
                    return{lat: h[1], lng: h[0]}
                })};
                if (a.properties.style) {
                    if (a.properties.style.strokeColor)c.strokeColor = a.properties.style.strokeColor;
                    if (a.properties.style.fillColor)c.fillColor = a.properties.style.fillColor;
                    if (a.properties.style.weight)c.fillColor = a.properties.style.weight
                }
                this.addPolygon(c);
                this.addPointsToExtent(c.path)
            } else {
                c = d.map(a.geometry.coordinates[1], function (h) {
                    return{lat: h[1], lng: h[0]}
                });
                var e = d.map(a.geometry.coordinates[0], function (h) {
                    return{lat: h[1],
                        lng: h[0]}
                });
                c = {path: c, outerRing: e};
                if (a.properties.style) {
                    if (a.properties.style.strokeColor)c.strokeColor = a.properties.style.strokeColor;
                    if (a.properties.style.fillColor)c.fillColor = a.properties.style.fillColor;
                    if (a.properties.style.weight)c.fillColor = a.properties.style.weight
                }
                this.addPolygonMask(c);
                this.addPointsToExtent(c.path);
                this.addPointsToExtent(c.outerRing)
            }
        }, addLineStringFeature: function (a) {
            var c = {path: d.map(a.geometry.coordinates, function (e) {
                return{lat: e[1], lng: e[0]}
            }), opacity: null};
            if (a.properties.style) {
                if (a.properties.style.strokeColor) {
                    c.colors =
                        [a.properties.style.strokeColor];
                    c.opacity = a.properties.style.strokeOpacity
                }
                c.weight = a.properties.style.strokeWeight ? [a.properties.style.strokeWeight] : 2
            }
            this.addPolyline(c)
        }, addPointsToExtent: function (a) {
            this.bounds = b.extendBoundsWithPositions(this.bounds, a)
        }, pinClicked: function () {
        }})
});
steal.loaded("framework/map/layers/static_kml_layer.js");
steal("jquery/class", "./places_layer.js", function () {
    Tfl.Maps.Framework.PlacesLayer("Tfl.Maps.Framework.CoachParkingLayer", {LAYER_KEY: {"Coach parks": "framework/map/layers/icons/coachpark.png", "On-street coach meters": "framework/map/layers/icons/OnStreetMeteredBay.png", "Coach bays": "framework/map/layers/icons/CoachMeter.png", "Other coach parking": "framework/map/layers/icons/OtherCoachParking.png", "Coach ban areas": "#faa1a1"}}, {})
});
steal.loaded("framework/map/layers/coach_parking_layer.js");
toGeoJSON = function () {
    function d(n) {
        if (!n || !n.length)return 0;
        for (var i = 0, l = 0; i < n.length; i++)l = (l << 5) - l + n.charCodeAt(i) | 0;
        return l
    }

    function b(n, i) {
        return n.getElementsByTagName(i)
    }

    function a(n, i) {
        return n.getAttribute(i)
    }

    function c(n, i) {
        return parseFloat(a(n, i))
    }

    function e(n, i) {
        n = b(n, i);
        return n.length ? n[0] : null
    }

    function h(n) {
        n.normalize && n.normalize();
        return n
    }

    function j(n) {
        for (var i = 0, l = []; i < n.length; i++)l[i] = parseFloat(n[i]);
        return l
    }

    function f(n) {
        var i = {};
        for (var l in n)if (n[l])i[l] = n[l];
        return i
    }

    function g(n) {
        n && h(n);
        return n && n.firstChild && n.firstChild.nodeValue
    }

    function k(n) {
        return j(n.replace(D, "").split(","))
    }

    function m(n) {
        n = n.replace(s, "").split(A);
        for (var i = [], l = 0; l < n.length; l++)i.push(k(n[l]));
        return i
    }

    function t(n) {
        return[c(n, "lon"), c(n, "lat")]
    }

    function z() {
        return{type: "FeatureCollection", features: []}
    }

    function y(n) {
        return x.serializeToString(n)
    }

    var D = /\s*/g, s = /^\s*|\s*$/g, A = /\s+/, x;
    if (typeof XMLSerializer !== "undefined")x = new XMLSerializer; else if (typeof require !== "undefined")x = new (require("xmldom").XMLSerializer);
    return{kml: function (n, i) {
        function l(E) {
            var F = {}, G = e(E, "IconStyle");
            G && v(G, F);
            (G = e(E, "LineStyle")) && p(G, F);
            (E = e(E, "PolyStyle")) && q(E, F);
            return F
        }

        function r(E) {
            var F = b(E, "Pair");
            if (F)for (var G = 0; G < F.length; G++) {
                var I = e(F[G], "key"), L = e(F[G], "styleUrl");
                if (I && L)if (L = M[g(L)])M["#" + a(E, "id") + "_" + g(I)] = L
            }
        }

        function v(E, F) {
            if (e(E, "Icon"))if (E = e(E, "href"))F.iconUrl = g(E)
        }

        function p(E, F) {
            var G = e(E, "color");
            if (G)if (G = o(g(G))) {
                F.strokeColor = G.rgb;
                F.strokeOpacity = G.opacity
            }
            if (E = e(E, "width"))F.strokeWeight = g(E)
        }

        function q(E, F) {
            if (E = e(E, "color"))if (E = o(g(E))) {
                F.fillColor = E.rgb;
                F.fillOpacity = E.opacity
            }
        }

        function o(E) {
            var F = E.match(/([0-9a-f]{2})([0-9a-f]{2})([0-9a-f]{2})([0-9a-f]{2})/i);
            if (F && F.length == 5)return{rgb: "#" + F[4] + F[3] + F[2], opacity: parseInt(F[1], 16) / 255 * 1}; else return(F = E.match(/([0-9a-f]{2})([0-9a-f]{2})([0-9a-f]{2})/i)) && F.length == 4 ? {rgb: "#" + F[3] + F[2] + F[1], opacity: 1} : null
        }

        function u(E) {
            return j(E.split(" "))
        }

        function w(E) {
            E = b(E, "coord", "gx");
            for (var F = [], G = 0; G < E.length; G++)F.push(u(g(E[G])));
            return F
        }

        function C(E) {
            var F, G, I, L, N = [];
            if (e(E, "MultiGeometry"))return C(e(E, "MultiGeometry"));
            if (e(E, "MultiTrack"))return C(e(E, "MultiTrack"));
            for (I = 0; I < O.length; I++)if (G = b(E, O[I]))for (L = 0; L < G.length; L++) {
                F = G[L];
                if (O[I] == "Point")N.push({type: "Point", coordinates: k(g(e(F, "coordinates")))}); else if (O[I] == "LineString")N.push({type: "LineString", coordinates: m(g(e(F, "coordinates")))}); else if (O[I] == "Polygon") {
                    var Q = b(F, "LinearRing"), R = [];
                    for (F = 0; F < Q.length; F++)R.push(m(g(e(Q[F], "coordinates"))));
                    N.push({type: "Polygon",
                        coordinates: R})
                } else O[I] == "Track" && N.push({type: "LineString", coordinates: w(F)})
            }
            return N
        }

        function B(E) {
            var F = C(E), G, I = {}, L = g(e(E, "name"));
            G = g(e(E, "styleUrl"));
            var N = g(e(E, "description"));
            E = e(E, "ExtendedData");
            if (!F.length)return[];
            if (L)I.name = L;
            if (G && K[G]) {
                I.styleUrl = G;
                I.styleHash = K[G];
                if (M[G])I.style = M[G]
            } else if (G && M[G + "_normal"]) {
                I.styleUrl = G + "_normal";
                if (M[G + "_normal"])I.style = M[G + "_normal"]
            }
            if (N)I.description = N;
            if (E) {
                L = b(E, "Data");
                N = b(E, "SimpleData");
                for (G = 0; G < L.length; G++)I[L[G].getAttribute("name")] =
                    g(e(L[G], "value"));
                for (G = 0; G < N.length; G++)I[N[G].getAttribute("name")] = g(N[G])
            }
            return[
                {type: "Feature", geometry: F.length === 1 ? F[0] : {type: "GeometryCollection", geometries: F}, properties: I}
            ]
        }

        i = i || {};
        i = z();
        var K = {}, M = {}, O = ["Polygon", "LineString", "Point", "Track"], P = b(n, "Placemark"), J = b(n, "StyleMap");
        n = b(n, "Style");
        for (var H = 0; H < n.length; H++) {
            K["#" + a(n[H], "id")] = d(y(n[H])).toString(16);
            M["#" + a(n[H], "id")] = l(n[H])
        }
        for (n = 0; n < J.length; n++)r(J[n], M);
        for (J = 0; J < P.length; J++)i.features = i.features.concat(B(P[J]));
        return i
    }, gpx: function (n) {
        function i(u, w) {
            var C = b(u, w), B = [];
            for (w = 0; w < C.length; w++)B.push(t(C[w]));
            return{type: "Feature", properties: r(u), geometry: {type: "LineString", coordinates: B}}
        }

        function l(u) {
            var w = r(u);
            w.ele = g(e(u, "ele"));
            w.sym = g(e(u, "sym"));
            return{type: "Feature", properties: w, geometry: {type: "Point", coordinates: t(u)}}
        }

        function r(u) {
            var w = ["name", "desc", "author", "copyright", "link", "time", "keywords"], C = {}, B;
            for (B = 0; B < w.length; B++)C[w[B]] = g(e(u, w[B]));
            return f(C)
        }

        var v = b(n, "trk"), p = b(n, "rte"), q = b(n,
            "wpt"), o = z();
        for (n = 0; n < v.length; n++)o.features.push(i(v[n], "trkpt"));
        for (n = 0; n < p.length; n++)o.features.push(i(p[n], "rtept"));
        for (n = 0; n < q.length; n++)o.features.push(l(q[n]));
        return o
    }}
}();
if (typeof module !== "undefined")module.exports = toGeoJSON;
steal.loaded("lib/togeojson.js");
steal("jquery/model", "./leg.js", "./journey.js", "./route.js");
steal.loaded("journeyplanner/models/models.js");
steal("jquery/dom/fixture", function () {
    $.fixture("http://217.28.133.210:8002/Journeys/JourneyResults/bank/to/victoria", "//journeyplanner/fixtures/example_journeys_0.json");
    $.fixture("/placeholder/route/url/0", "//journeyplanner/fixtures/example_route_0.json");
    $.fixture("/placeholder/route/url/1", "//journeyplanner/fixtures/example_route_1.json");
    $.fixture("/placeholder/route/url/2", "//journeyplanner/fixtures/example_route_2.json");
    $.fixture("/placeholder/route/url/3", "//journeyplanner/fixtures/example_route_3.json")
});
steal.loaded("journeyplanner/fixtures/fixtures.js");
steal("framework/map/map.js", "./leg_layer.js", "framework/map/layers/station_stop_layer.js", "framework/map/layers/cycle_hire_layer.js", function (d) {
    Tfl.Maps.Framework.Map("Tfl.Maps.JourneyPlanner.LegMap", {defaults: {channel: "journeyplannerleg", initiallyDeactivated: false}}, {init: function () {
        this.mapContainer = d("<div class='map map-container leg-map-container'></div>");
        this.element.append(this.mapContainer);
        this.legIndex = this.journeyIndex = -1;
        this._super()
    }, addLayers: function () {
        this.stationStopLayer = this.layerManager.addLayer(Tfl.Maps.Framework.StationStopLayer,
            {layerOptions: {zoomThresholds: {displayLabelsBy: 1}}});
        this.legLayer = this.layerManager.addLayer(Tfl.Maps.JourneyPlanner.LegLayer, {initialVisibility: false, layerOptions: {journeysUrl: this.options.url, stationStopLayer: this.stationStopLayer}});
        this.layerManager.addLayer(Tfl.Maps.Framework.CycleHireLayer, {initialVisibility: true, layerOptions: {zoomThresholds: {cycleHirePinsBy: 15}}});
        this.chooseInitiallyChosenLeg()
    }, chooseLeg: function (b, a) {
        this.journeyIndex = b;
        this.legIndex = a;
        this.legLayer && this.legLayer.chooseLeg(b,
            a)
    }, chooseInitiallyChosenLeg: function () {
        this.journeyIndex != -1 && this.chooseLeg(this.journeyIndex, this.legIndex)
    }})
});
steal.loaded("journeyplanner/leg_map/leg_map.js");
steal("jquery/model", function () {
    $.Model("Tfl.Maps.JourneyPlanner.Models.Leg", {}, {})
});
steal.loaded("journeyplanner/models/leg.js");
steal("jquery/model", "./leg.js", function () {
    $.Model("Tfl.Maps.JourneyPlanner.Models.Journey", {attributes: {}, findAll: function (d, b, a) {
        return this.buildFindAll(d, b, a)
    }, buildFindAll: function (d, b, a) {
        return $.ajax({url: d.url, type: "get", dataType: "json journey.models", success: b, error: a, fixture: "//journeyplanner/fixtures/example_journeys_0.json"})
    }, models: function (d) {
        var b = [];
        $.each(d.journeys, function (a, c) {
            c.newLegs = [];
            $.each(c.legs, function (e, h) {
                c.newLegs.push(new Tfl.Maps.JourneyPlanner.Models.Leg(h))
            });
            c.legs = c.newLegs;
            b.push(c)
        });
        return b
    }}, {})
});
steal.loaded("journeyplanner/models/journey.js");
steal("jquery/model", function () {
    $.Model("Tfl.Maps.JourneyPlanner.Models.Route", {findOne: function (d, b, a) {
        var c = $.Deferred();
        c.done(b);
        c.fail(a);
        d = $.map(d.fakePath, function (e) {
            return{lat: e.lat, lng: e.lon}
        });
        c.resolve({path: d});
        return c
    }}, {})
});
steal.loaded("journeyplanner/models/route.js");
steal("jquery/class", "framework/map/layers/static_layer_base.js", "framework/map/map_utils.js", "journeyplanner/fixtures", "journeyplanner/models/journey.js", "../journey_map/journey_pin_composer.js", "../journey_map/journey_polyline_composer.js", function (d) {
    var b = Tfl.Maps.Framework.MapUtils;
    Tfl.Maps.Framework.StaticLayerBase("Tfl.Maps.JourneyPlanner.LegLayer", {}, {init: function (a, c, e) {
        this.journeysUrl = e.journeysUrl;
        this.stationStopLayer = e.stationStopLayer;
        this.fullJourney = e.fullJourney;
        this.journeysUrl &&
        this.fetchJourneys();
        this._super(a, c)
    }, chooseLeg: function (a, c) {
        this.fullJourney || this.clear();
        this.map.mapLoading.done(this.proxy(function () {
            this.map.hideStreetView();
            this.map.activateIfDeactivated();
            this.selectedJourneyLeg = {journeyIndex: a, legIndex: c};
            this.refresh()
        }))
    }, clear: function () {
        if (this.stationStopLayer) {
            this.stationStopLayer.isRendered = true;
            this.stationStopLayer.renderStateChanged = true;
            this.stationStopLayer.clear()
        }
        this.isRendered = true;
        this.prepareClear();
        this._super()
    }, render: function () {
        this.fetchingJourneys.done(this.proxy(function () {
            this.displaySelectedJourneyLeg(this.selectedJourneyLeg)
        }))
    },
        fetchJourneys: function () {
            this.fetchingJourneys = Tfl.Maps.JourneyPlanner.Models.Journey.findAll({url: this.journeysUrl}, this.proxy(function (a) {
                this.journeys = a
            }))
        }, displaySelectedJourneyLeg: function (a) {
            this.renderLeg(this.journeys[a.journeyIndex].legs[a.legIndex], true)
        }, renderLeg: function (a, c, e) {
            var h, j, f = {lat: a.departurePoint.lat, lng: a.departurePoint.lon}, g = a.mode.name.toLowerCase(), k = d.map(a.routeOptions, function (m) {
                return m.name ? m.name.toLowerCase() : ""
            });
            if (a.path.lineString) {
                h = d.parseJSON(a.path.lineString);
                h = d.map(h, function (m) {
                    return m[0] < m[1] ? {lat: m[1], lng: m[0]} : {lat: m[0], lng: m[1]}
                });
                j = b.getBoundsFromPositions(h);
                this.addRoute({path: h, mode: g, routes: k})
            } else j = b.getBoundsFromPositions([f, {lat: a.arrivalPoint.lat, lng: a.arrivalPoint.lon}]);
            if (a.path.stopPoints) {
                a.naptanIds = [];
                d.each(a.path.stopPoints, function (m, t) {
                    a.naptanIds.push(t.id);
                    t.hubNaptanCode !== undefined && a.naptanIds.push(t.hubNaptanCode)
                });
                a.naptanIds.push(a.departurePoint.naptanId)
            }
            this.fullJourney || this.map.displayMapByBoundingBox(j);
            if (this.stationStopLayer &&
                a.naptanIds) {
                a.departurePoint.modes = [a.mode.name];
                a.arrivalPoint.modes = [a.mode.name];
                g = a.naptanIds;
                f = a.departurePoint;
                k = a.arrivalPoint;
                if (this.fullJourney) {
                    if (this.onlyShowStartAndEndOfJourney) {
                        e || (f = null);
                        c || (k = null)
                    }
                } else {
                    this.stationStopLayer.isRendered = true;
                    this.stationStopLayer.renderStateChanged = true;
                    this.stationStopLayer.clear()
                }
                this.stationStopLayer.setHighlightedStationStops(g, f, k, this.fullJourney)
            } else {
                this.addPin(f, g, a.departurePoint.commonName);
                if (c) {
                    c = {lat: a.arrivalPoint.lat, lng: a.arrivalPoint.lon};
                    this.addPin(c, g, a.arrivalPoint.commonName)
                }
            }
        }, getBoundsForLeg: function (a) {
            var c = {lat: a.departurePoint.lat, lng: a.departurePoint.lon};
            if (a.path.lineString) {
                points = d.parseJSON(a.path.lineString);
                path = d.map(points, function (e) {
                    return e[0] < e[1] ? {lat: e[1], lng: e[0]} : {lat: e[0], lng: e[1]}
                });
                return b.getBoundsFromPositions(path)
            } else return b.getBoundsFromPositions([c, {lat: a.arrivalPoint.lat, lng: a.arrivalPoint.lon}])
        }, addPin: function (a, c, e) {
            return this._super(Tfl.Maps.JourneyPlanner.JourneyPinComposer.getPinSpec({position: a,
                mode: c, name: e}))
        }, addRoute: function (a) {
            var c = this;
            a = Tfl.Maps.JourneyPlanner.JourneyPolylineComposer.getPolylineSpecs(a);
            d.each(a, function (e, h) {
                c.addPolyline(h)
            })
        }})
});
steal.loaded("journeyplanner/leg_map/leg_layer.js");
steal("jquery/class", "framework/models/settings_helper.js", function (d) {
    d.Class("Tfl.Maps.JourneyPlanner.JourneyPinComposer", {MODES_WITH_CUSTOM_ICONS: ["bus", "cable-car", "cycle", "dlr", "overground", "river-bus", "national-rail", "tram", "tube", "walking"], GENERIC_LEG_MODE_OFFSET: {x: 8, y: 24}, DEFAULT_LEG_MODE_OFFSET: {x: 12, y: 33}, getPinSpec: function (b) {
        var a = b.mode;
        if (d.inArray(a, this.MODES_WITH_CUSTOM_ICONS) === -1)a = "generic";
        return{position: b.position, iconUrl: Tfl.Maps.Framework.Models.SettingsHelper.contentBaseUrl +
            "journeyplanner/journey_map/icons/" + (a + "_pin.png"), iconAnchor: a === "generic" ? this.constructor.GENERIC_LEG_MODE_OFFSET : this.constructor.DEFAULT_LEG_MODE_OFFSET, tooltip: b.name}
    }}, {})
});
steal.loaded("journeyplanner/journey_map/journey_pin_composer.js");
