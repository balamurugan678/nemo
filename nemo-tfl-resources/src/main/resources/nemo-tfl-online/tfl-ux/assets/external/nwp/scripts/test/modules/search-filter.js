// file: search-filter.js
// namespace: tfl.searchFilter
// author: Steven Swinbank
// created: 03/01/2014
// description: functions to set-up searchFilter instances and attach autocomplete datasets as appropriate

(function (o) {
    "use strict";
    tfl.logs.create("tfl.searchFilter: loaded");

    var nearUrl = "/maps/";

    // function to remove hidden inputs so that they aren't concatenated to the Url query string unnescesarily
    function removeHiddenInputs($searchForm, except) {
        $searchForm.find("input[type='hidden']").not("[id='" + except + "']").remove();
    };

    o.setNearUrl = function (url) {
        nearUrl = url;
    };

    o.init = function () {
        tfl.logs.create("tfl.searchFilter.init: started");

        var searchFilter = "search-filter-form";
        var $searchFilter = $("#" + searchFilter);
        var $searchFilterInput = $("#" + searchFilter + " #Input");

        // to add styling to 'Current location' in text input
        if ($.trim($searchFilterInput.val().toLowerCase()) === $.trim(tfl.dictionary.CurrentLocationText).toLowerCase()) {
            tfl.geolocation.setupInputBoxForGeolocation($searchFilterInput);
        }

        // load recent searches
        if (tfl.autocomplete.recentMagicSearches.usingRecentSearches) {
            tfl.autocomplete.recentMagicSearches.loadSearches();
        }

        // collate all sources
        var dataSets = $searchFilter.data("datasets");
        var modesArr = $searchFilter.data("modes").split(",");
        var busStopsOnly = $searchFilter.data("busstopsonly");
        var autocompleteSources = [];
        var recentSearchesSources = [];
        //for (var i = 0; i < dataSets.length; i++) {
        $(dataSets).each(function () {
            var dataSet = this[0];//dataSets[i][0].slice(0);
            var returnUrl = this[1];//dataSets[i][1].slice(0);

            if (dataSet == tfl.disambiguationDataSets.stopPoints) {
                // Stops
                var stopsCallback = function (inputEl, datum) {

                    var a = datum.a || datum.name;
                    var b = datum.b || datum.id;
                    var c = datum.c || datum.modes;
                    var cName = c;
                    if (typeof c == "object" && c.length > 0) {
                        cName = c.length > 1 ? tfl.modeNameMultiModal : c[0];
                    }
                    var d = datum.d || datum.lat;
                    var e = datum.e || datum.lon;
                    // save search
                    if (tfl.autocomplete.recentMagicSearches.usingRecentSearches) {
                        tfl.autocomplete.recentMagicSearches.saveSearch(a, b, c, "stops", d, e);
                    }
                    removeHiddenInputs($searchFilter);

                    if (returnUrl != null) {
                        // urlParameters
                        if (returnUrl.indexOf("?") !== -1) {
                            var urlParameters = returnUrl.substring(returnUrl.indexOf("?"));
                            if (urlParameters.toLowerCase().indexOf("inputgeolocation") !== -1) {
                                $searchFilter.append('<input type="hidden" id ="InputGeolocation" name="InputGeolocation" value="' + encodeURI(e + ',' + d) + '" />');
                            }
                            if (urlParameters.toLowerCase().indexOf("startdate") !== -1 && location.search.toLowerCase().indexOf("startdate") !== -1) {
                                var restOfUrlStartDate = decodeURIComponent(location.search.substring(location.search.toLowerCase().indexOf("startdate")));
                                var startDate = restOfUrlStartDate.substring(restOfUrlStartDate.indexOf("=") + 1, restOfUrlStartDate.indexOf("&") !== -1 ? restOfUrlStartDate.indexOf("&") : null);
                                $searchFilter.append("<input type='hidden' id='startDate' name='startDate' value='" + startDate + "' />");
                            }
                            if (urlParameters.toLowerCase().indexOf("enddate") !== -1 && location.search.toLowerCase().indexOf("enddate") !== -1) {
                                var restOfUrlEndDate = decodeURIComponent(location.search.substring(location.search.toLowerCase().indexOf("enddate")));
                                var endDate = restOfUrlEndDate.substring(restOfUrlEndDate.indexOf("=") + 1, restOfUrlEndDate.indexOf("&") !== -1 ? restOfUrlEndDate.indexOf("&") : restOfUrlEndDate.length);
                                $searchFilter.append("<input type='hidden' id='endDate' name='endDate' value='" + endDate + "' />");
                            }
                            if (urlParameters.toLowerCase().indexOf("stopid") !== -1) {
                                $searchFilter.append("<input type='hidden' id='stopId' name='stopId' value='" + b + "' />");
                            }
                            returnUrl = returnUrl.substring(0, returnUrl.indexOf("?"));
                        }

                        // url routing
                        var actionUrl = returnUrl
                            .replace("{{lat}}", encodeURI(d))
                            .replace("{{lon}}", encodeURI(e))
                            .replace("{{mode}}", encodeURI(cName))
                            .replace("{{naptanId}}", encodeURI(b))
                            .replace("{{stopName}}", encodeURI(a.toLowerCase().replace(/'/g, '').replace(/[^a-z0-9]/g, '-').replace(/-+/g, '-').replace(/-$/g, '').replace(/^-/g, '')));
                        $searchFilter.attr('action', actionUrl);
                    }
                    $searchFilter.submit();
                };

                // search suggestions
                autocompleteSources.push(tfl.autocomplete.sources.stopPointsSearchFactory(modesArr.toString(), stopsCallback, null, null, null, busStopsOnly));

                // recent search suggestions
                var stopsRecentSearches = [];
                if (tfl.autocomplete.recentMagicSearches.searches.stops) {
                    for (var a = 0; a < tfl.autocomplete.recentMagicSearches.searches.stops.length; a++) {
                        var stop = tfl.autocomplete.recentMagicSearches.searches.stops[a];
                        for (var b = 0; b < stop.modes.length; b++) {
                            var stopAdded = false;
                            var stopMode = stop.modes[b];
                            for (var c = 0; c < modesArr.length; c++) {
                                if (stopMode === modesArr[c]) {
                                    stopsRecentSearches.push(stop);
                                    stopAdded = true;
                                    break;
                                }
                            }
                            if (stopAdded) break;
                        }
                    }
                }
                if (stopsRecentSearches != null && stopsRecentSearches.length > 0) {
                    var stopsSourceRecentSearches = tfl.autocomplete.sources.getRecentSearches(
                        "stops-recent-magic-searches",
                        stopsRecentSearches,
                        stopsCallback,
                        "<div class='mode-icons'>{{#modes}}<span class='mode-icon {{.}}-icon'>&nbsp;</span>{{/modes}}</div><span class='stop-name' data-id='{{id}}'>{{name}}</span>",
                        "name");
                    recentSearchesSources.push(stopsSourceRecentSearches);
                }
            } else if (dataSet == tfl.disambiguationDataSets.routes) {
                // Routes
                var routesCallback = function (inputEl, datum) {
                    var a = datum.a || datum.name;
                    var b = datum.b || datum.id;
                    var c = datum.c || datum.mode;
                    var f = datum.f || datum.cssClass;
                    var g = datum.g || datum.direction;
                    var h = datum.h || datum.destination;
                    var cName = c;
                    if (typeof c == "object" && c.length > 0) {
                        cName = c.length > 1 ? tfl.modeNameMultiModal : c[0];
                    }

                    // save search
                    if (tfl.autocomplete.recentMagicSearches.usingRecentSearches) {
                        tfl.autocomplete.recentMagicSearches.saveSearch(a, b, [c], "lines", null, null, f, g, h);
                    }

                    removeHiddenInputs($searchFilter);
                    if (returnUrl != null) {
                        // urlParameters
                        if (returnUrl.indexOf("?") !== -1) {
                            var urlParameters = returnUrl.substring(returnUrl.indexOf("?"));
                            if (urlParameters.toLowerCase().indexOf("lineids") !== -1) {
                                $searchFilter.append("<input type='hidden' id='lineIds' name='lineIds' value='" + b + "' />");
                                //// bus status recent search saving
                                //$(inputEl).attr("data-route-name");
                                //$(inputEl).attr("data-route-id", b);
                                //$(inputEl).attr("data-route-mode", c);
                            }
                            if (urlParameters.toLowerCase().indexOf("startdate") !== -1 && location.search.toLowerCase().indexOf("startdate") !== -1) {
                                var restOfUrlStartDate = decodeURIComponent(location.search.substring(location.search.toLowerCase().indexOf("startdate")));
                                var startDate = restOfUrlStartDate.substring(restOfUrlStartDate.indexOf("=") + 1, restOfUrlStartDate.indexOf("&") !== -1 ? restOfUrlStartDate.indexOf("&") : null);
                                $searchFilter.append("<input type='hidden' id='startDate' name='startDate' value='" + startDate + "' />");
                            }
                            if (urlParameters.toLowerCase().indexOf("enddate") !== -1 && location.search.toLowerCase().indexOf("enddate") !== -1) {
                                var restOfUrlEndDate = decodeURIComponent(location.search.substring(location.search.toLowerCase().indexOf("enddate")));
                                var endDate = restOfUrlEndDate.substring(restOfUrlEndDate.indexOf("=") + 1, restOfUrlEndDate.indexOf("&") !== -1 ? restOfUrlEndDate.indexOf("&") : restOfUrlEndDate.length);
                                $searchFilter.append("<input type='hidden' id='endDate' name='endDate' value='" + endDate + "' />");
                            }
                            returnUrl = returnUrl.substring(0, returnUrl.indexOf("?"));
                        }

                        // url routing
                        var actionUrl = returnUrl
                            .replace("{{stopName}}", encodeURI(a.toLowerCase().replace(/[^a-z0-9]/g, '-').replace(/-+/g, '-').replace(/-$/g, '').replace(/^-/g, '')))
                            .replace("{{lineIds}}", encodeURI(b))
                            .replace("{{mode}}", encodeURI(cName));

                        // add direction for bus routes
                        if (cName === tfl.modeNameBus && g !== null) {
                            $searchFilter.append("<input type='hidden' name='direction' value='" + g + "'/>");
                        }

                        $searchFilter.attr('action', actionUrl);
                    }
                    $searchFilter.submit();
                };

                // search suggestions
                var regexCharacterString = modesArr.length === 1 && modesArr[0].toString() === tfl.modeNameBus ? "[^a-zA-Z0-9]" : "[^a-zA-Z0-9 &-/().']";
                autocompleteSources.push(tfl.autocomplete.sources.routesSearchFactory(modesArr.toString(), routesCallback, regexCharacterString));

                // recent search suggestions
                for (var i = 0; i < modesArr.length; i++) {
                    var routeMode = modesArr[i];
                    if (tfl.autocomplete.recentMagicSearches.searches.lines != null && tfl.autocomplete.recentMagicSearches.searches.lines[routeMode] != null) {
                        var recentSearches = tfl.autocomplete.recentMagicSearches.searches.lines[routeMode];
                        var routesSourceRecentSearches = tfl.autocomplete.sources.getRecentSearches(
                                routeMode + "-recent-magic-searches",
                            recentSearches,
                            routesCallback,
                            "<span class='mode-icon {{mode}}-icon'>&nbsp;<span class='{{cssClass}}'>&nbsp;</span></span><span class='stop-name' data-id='{{id}}'>{{name}}{{#destination.length}} (towards {{destination}}){{/destination.length}}</span>",
                            "name");
                        recentSearchesSources.push(routesSourceRecentSearches);
                    }
                }
            }
        });

        // create 'turn-off' recent searches
        var sourceTurnOffRecentSearches = tfl.autocomplete.sources.getTurnOnOffRecentSearches(false, [
            { linkText: "Turn off recent searches" }
        ]);
        var sourcesTurnRecentSearchesOff = autocompleteSources.slice(0);

        // create 'turn-on' recent searches
        var sourceTurnOnRecentSearches = tfl.autocomplete.sources.getTurnOnOffRecentSearches(true, [
            { linkText: "Turn on recent searches" }
        ]);
        var sourcesTurnRecentSearchesOn = autocompleteSources.slice(0);

        //add callback functions
        sourceTurnOffRecentSearches.typeaheadSource.callback = function (inputEl) {
            tfl.autocomplete.recentMagicSearches.useRecentSearches(false);
            $(inputEl).parent().siblings(tfl.dictionary.RemoveContentClass).trigger("clear-search-box");
            destroyAutocomplete();
            tfl.autocomplete.setup("#" + $(inputEl).attr("id"), sourcesTurnRecentSearchesOn, null, false);
            $(inputEl).attr("placeholder", $searchFilter.attr("data-placeholder"));
        };
        sourceTurnOnRecentSearches.typeaheadSource.callback = function (inputEl) {
            tfl.autocomplete.recentMagicSearches.useRecentSearches(true);
            $(inputEl).parent().siblings(tfl.dictionary.RemoveContentClass).trigger("clear-search-box");
            destroyAutocomplete();
            tfl.autocomplete.setup("#" + $(inputEl).attr("id"), sourcesTurnRecentSearchesOff, null, false);
            $(inputEl).attr("placeholder", $searchFilter.attr("data-placeholder"));
        };

        //setup sources
        autocompleteAddRecentSearchesSources(false);
        autocompleteAddGeolocation();

        //set up autcomplete
        if (tfl.autocomplete.recentMagicSearches.usingRecentSearches) {
            tfl.autocomplete.setup("#" + searchFilter + " #Input", sourcesTurnRecentSearchesOff, null, true);
        } else {
            tfl.autocomplete.setup("#" + searchFilter + " #Input", sourcesTurnRecentSearchesOn, null, true);
        }

        function destroyAutocomplete() {
            $searchFilterInput.parent().siblings(tfl.dictionary.RemoveContentClass).unbind("clear-search-box");
            $searchFilterInput.typeahead("destroy");
            $searchFilterInput.off("typeahead:selected typeahead:autocompleted keydown.autocomplete");
            $searchFilterInput.attr("value", "");
            sourcesTurnRecentSearchesOff = autocompleteSources.slice(0);
            sourcesTurnRecentSearchesOn = autocompleteSources.slice(0);
            autocompleteAddRecentSearchesSources(true);
            autocompleteAddGeolocation();
        };

        function autocompleteAddRecentSearchesSources(excludeRecentSearches) {
            if (tfl.storage.isLocalStorageSupported()) {
                sourcesTurnRecentSearchesOff.splice(0, 0, sourceTurnOffRecentSearches);
                if (tfl.autocomplete.recentMagicSearches.usingRecentSearches && !excludeRecentSearches) {
                    // insert recent searches at start of autocompleteSources array
                    if (recentSearchesSources.length > 0) {
                        Array.prototype.splice.apply(sourcesTurnRecentSearchesOff, [0, 0].concat(limitNumberRecentSearches(recentSearchesSources)));
                    }
                }
                sourcesTurnRecentSearchesOn.splice(0, 0, sourceTurnOnRecentSearches);
            }
        };

        function limitNumberRecentSearches(sources) {
            // create array of items
            var topRecentSearches = [];
            $(sources).each(function () {
                if (typeof this === "function") return;
                var source = this;
                for (var j = 0; j < source.dataEngine.local.length; j++) {
                    var item = source.dataEngine.local[j];
                    topRecentSearches.push([item.type + "_" + item.id, item.lastSearchDate]);
                }
            });
            // reduce number of items to top 5 most recent
            if (topRecentSearches.length > tfl.autocomplete.recentMagicSearches.recentSearchNumberLimit) {
                topRecentSearches.sort(function (a, b) {
                    if (a[1] < b[1]) return 1;
                    if (a[1] > b[1]) return -1;
                    return 0;
                });
                topRecentSearches.length = tfl.autocomplete.recentMagicSearches.recentSearchNumberLimit;
                // go through each item and remove if not in topRecentSearches
                for (var k = 0; k < sources.length; k++) {
                    var recentSearchSource = sources[k];
                    var originalLength = recentSearchSource.dataEngine.local.length;
                    var deleteCounter = 0;
                    for (var l = 0; l < originalLength; l++) {
                        var removeItem = true;
                        var recentSearchItem = recentSearchSource.dataEngine.local[l - deleteCounter];
                        for (var m = 0; m < topRecentSearches.length; m++) {
                            if (recentSearchItem.type + "_" + recentSearchItem.id === topRecentSearches[m][0]) {
                                removeItem = false;
                                break;
                            }
                        }
                        if (removeItem) {
                            var index = recentSearchSource.dataEngine.local.indexOf(recentSearchItem);
                            if (index > -1) {
                                deleteCounter++;
                                recentSearchSource.dataEngine.local.splice(index, 1);
                            }
                        }
                    }
                }
            }
            // sort recent searches sources with most recently searched at top
            sources.sort(function (a, b) {
                var maxProp = function (array, prop) {
                    var values = array.map(function (el) {
                        return el[prop];
                    });
                    return Math.max.apply(Math, values);
                };
                if (maxProp(a.dataEngine.local, 'lastSearchDate') < maxProp(b.dataEngine.local, 'lastSearchDate')) return 1;
                if (maxProp(a.dataEngine.local, 'lastSearchDate') > maxProp(b.dataEngine.local, 'lastSearchDate')) return -1;
                return 0;
            });
            // remove footer from all but last source
            for (var n = 0; n < sources.length - 1; n++) {
                sources[n].typeaheadSource.templates.footer = "<span class='source-footer collapsed'>&nbsp</span>";
            }
            return sources;
        };

        function autocompleteAddGeolocation() {
            if ($searchFilter.data("include-geolocation").toLowerCase() === "true" && tfl.geolocation.isGeolocationSupported()) {
                var geoCallback = function () {
                    removeHiddenInputs($searchFilter);
                    $searchFilter.attr('action', nearUrl);
                    $searchFilter.submit();
                };
                var sourceGeolocation = tfl.autocomplete.sources.getGeolocation(geoCallback);
                sourcesTurnRecentSearchesOff.splice(0, 0, sourceGeolocation);
                sourcesTurnRecentSearchesOn.splice(0, 0, sourceGeolocation);
            }
        };
    };

    o.init();

}(window.tfl.searchFilter = window.tfl.searchFilter || {}));
