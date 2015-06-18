window.tfl.autocomplete = window.tfl.autocomplete || {};
(function (o) {
    "use strict";
    tfl.logs.create("tfl.autocomplete.sources: loaded");
    o.autocompleteDisplayNumberLimit = 10;

    //stations stops functions
    o.stationsStopsSelectionCallback = function (inputEl, datum) {
        var el = $(inputEl);
        var a = datum.a || datum.placeId;
        el.attr("data-" + el.attr("id").toLowerCase() + "-id", a);
        var c = JSON.stringify(datum.c ? datum.c : datum.placeModes);
        el.attr("data-" + el.attr("id").toLowerCase() + "-modes", c);
    };
    o.stationsStopsRemoveContent = function (inputEl) {
        var el = $(inputEl);
        el.removeAttr("data-" + el.attr("id").toLowerCase() + "-id");
        el.removeAttr("data-" + el.attr("id").toLowerCase() + "-modes");
    };
    function compareStringLengths(a, b) {
        if (a.a.length < b.a.length) return -1;
        if (a.a.length > b.a.length) return 1;
        return 0; // a and b are the same length
    }

    // GeoLocation (used everywhere other than JP)
    o.getGeolocation = function (geoCallback) {
        var callback = function (inputEl) {
            $(inputEl).removeAttr("data-dataset-name");
            o.stationsStopsRemoveContent(inputEl);
            tfl.geolocation.geolocateMe(inputEl, geoCallback);
        };

        var compiledTemplate = Hogan.compile("<a class='geolocation-link' href='javascript:void(0)'><span class='geolocation-icon'>&nbsp</span>{{value}}</a>");
        return {
            typeaheadSource: {
                callback: callback,
                displayKey: "value",
                name: "geolocation",
                templates: {
                    suggestion: compiledTemplate.render.bind(compiledTemplate),
                    footer: "<span class='source-footer'>&nbsp</span>"
                }
            },
            dataEngine: new Bloodhound({
                datumTokenizer: function () {
                    return [""];
                },
                queryTokenizer: function () {
                    return [""];
                },
                limit: 1,
                minLength: 0,
                local: [
                    { value: "Use my location"}
                ]
            })
        };
    };

    //recent searches
    o.getRecentSearches = function (name, local, callback, template, displayKey, limit) {
        if (limit == null) limit = 5;
        var compiledTemplate = Hogan.compile(template);
        return {
            typeaheadSource: {
                callback: callback,
                displayKey: displayKey,
                name: name,
                templates: {
                    suggestion: compiledTemplate.render.bind(compiledTemplate),
                    footer: "<span class='source-footer'>&nbsp</span>",
                    header: "<span class='source-header'>Recent searches</span>"
                }
            },
            dataEngine: new Bloodhound({
                datumTokenizer: function () {
                    return [""];
                },
                queryTokenizer: function () {
                    return [""];
                },
                limit: limit,
                minLength: 0,
                local: local
            })
        };
    };

    //turn on recent searches
    o.getTurnOnOffRecentSearches = function (turnOnNotTurnOff, local) {
        var compiledTemplate = Hogan.compile("<span class='recent-searches-on-off-footer'><a href='javascript:void(0)'>{{linkText}}</a></span>");
        local[0].dataset = turnOnNotTurnOff ? "recent-searches-footer-on" : "recent-searches-footer-off";
        return {
            typeaheadSource: {
                displayKey: "linkText",
                name: turnOnNotTurnOff ? "recent-searches-footer-on" : "recent-searches-footer-off",
                templates: {
                    suggestion: compiledTemplate.render.bind(compiledTemplate),
                    footer: "<span class='source-footer'>&nbsp</span>"
                }
            },
            dataEngine: new Bloodhound({
                datumTokenizer: function () {
                    return [""];
                },
                queryTokenizer: function () {
                    return [""];
                },
                limit: 1,
                minLength: 0,
                local: local
            })
        };
    };

    //JP Suggestions
    o.getJourneyPlannerSuggestions = function () {
        var compiledTemplate = Hogan.compile("<div class='mode-icons'>{{#c}}<span class='mode-icon {{.}}-icon'>&nbsp;</span>{{/c}}</div><span class='stop-name'>{{b}}</span>");
        return {
            typeaheadSource: {
                displayKey: "b",
                name: "journey-planner-suggestions",
                templates: {
                    suggestion: compiledTemplate.render.bind(compiledTemplate)
                },
                callback: o.stationsStopsSelectionCallback,
                removeContent: o.stationsStopsRemoveContent
            },
            dataEngine: new Bloodhound({
                datumTokenizer: function (d) {
                    var datum = tfl.autocomplete.tokenize(d, "b");
                    return datum.tokens != null ? datum.tokens : Bloodhound.tokenizers.whitespace(d.b);
                },
                queryTokenizer: Bloodhound.tokenizers.whitespace,
                limit: o.autocompleteDisplayNumberLimit,
                minLength: 1,
                prefetch: {
                    url: "/cdn/static/feed/stations-list_typeahead.json",
                    ttl: 2629740000 //1 month in milliseconds
                }
            })
        };
    };

    o.routesSearchFactory = function (modes, callback, regexCharacterString) {
        var compiledTemplate = Hogan.compile("<span class='mode-icon {{c}}-icon'>&nbsp;<span class='{{f}}'>&nbsp;</span></span><span class='stop-name' data-id='{{b}}'>{{a}}{{#h.length}} (towards {{h}}){{/h.length}}</span>");
        return {
            typeaheadSource: {
                displayKey: "a",
                minLength: 1,
                name: "routes-search",
                templates: {
                    suggestion: compiledTemplate.render.bind(compiledTemplate)
                },
                callback: callback
            },
            dataEngine: new Bloodhound({
                datumTokenizer: function (d) {
                    return Bloodhound.tokenizers.whitespace(d.a);
                },
                queryTokenizer: Bloodhound.tokenizers.whitespace,
                limit: o.autocompleteDisplayNumberLimit,
                minLength: 1,
                remote: {
                    url: tfl.addAppIdAndKey(tfl.api.RoutesSearch.format(modes)),
                    cache: true,
                    replace: function (url, uriEncodedQuery) { /* remove url-encoded spaces from input */
                        var returnUrl = "";
                        if (uriEncodedQuery !== null && uriEncodedQuery.length > 0) {
                            var rgx = new RegExp(regexCharacterString, "g");
                            returnUrl = url.replace("%QUERY", decodeURIComponent(uriEncodedQuery).replace(rgx, ""));
                        }

                        return returnUrl;
                    },
                    filter: function (response) { /* parse suggestions out of response  */
                        var results = [];
                        var searchMatches = response.searchMatches;
                        if (searchMatches != null) {
                            for (var i = 0; i < searchMatches.length; i++) {
                                var searchMatch = searchMatches[i];
                                // add 'lineCssClass' for tube
                                if (searchMatch.mode === tfl.modeNameTube) {
                                    searchMatch.cssClass = searchMatch.lineName.indexOf(" ") != -1 ? searchMatch.lineName.substring(0, searchMatch.lineName.indexOf(" ")).toLowerCase() : searchMatch.lineName.toLowerCase();
                                } else {
                                    searchMatch.cssClass = "";
                                }
                                // adding direction and towards information for bus routes
                                if (searchMatch.mode === tfl.modeNameBus) {
                                    for (var j = 0; j < searchMatch.lineRouteSection.length; j++) {
                                        var addSearchToResults = true;
                                        var lineRouteSection = searchMatch.lineRouteSection[j];
                                        for (var k = 0; k < results.length; k++) {
                                            var result = results[k];
                                            if (result.b == searchMatch.lineId && result.g == lineRouteSection.direction) {
                                                addSearchToResults = false;
                                                break;
                                            }
                                        }
                                        // only show results that match input (e.g. "23", show 23 bus, not 123, 223, 234 etc.)
                                        if (searchMatch.lineName !== response.input) {
                                            addSearchToResults = false;
                                        }

                                        if (addSearchToResults) {
                                            results.push(
                                                {
                                                    "a": searchMatch.lineName,
                                                    "b": searchMatch.lineId,
                                                    "c": searchMatch.mode,
                                                    "f": searchMatch.cssClass,
                                                    "g": lineRouteSection.direction,
                                                    "h": [lineRouteSection.destination] // as an array so as to display in autocomplete template ({{#h.length}} - towards {{h}}{{/h.length}})
                                                });
                                        }
                                    }
                                } else {
                                    results.push(
                                        {
                                            "a": searchMatch.lineName + (searchMatch.mode == tfl.modeNameTube ? " line" : ""),
                                            "b": searchMatch.lineId,
                                            "c": searchMatch.mode,
                                            "f": searchMatch.cssClass
                                        });
                                }

                                results.sort(compareStringLengths);
                            }
                        }
                        return results;
                    }
                }
            })
        };
    };

    o.stopPointsSearchFactory = function (modes, callback, excludeHubsAndPlatforms, oysterOnly, name, busStopsOnly) {
        oysterOnly = oysterOnly != true ? false : true;
        if (excludeHubsAndPlatforms == null) excludeHubsAndPlatforms = false;
        if (!busStopsOnly) busStopsOnly = false;
        if (oysterOnly == null) oysterOnly = false;
        if (name == null) name = "stop-points-search";
        var compiledTemplate = Hogan.compile("<div class='mode-icons'>{{#c}}<span class='mode-icon {{.}}-icon'>&nbsp;</span>{{/c}}</div><span class='stop-name' data-id='{{b}}'>{{a}}</span>");
        return {
            typeaheadSource: {
                displayKey: "a",
                minLength: 1,
                name: name,
                templates: {
                    suggestion: compiledTemplate.render.bind(compiledTemplate)
                },
                callback: callback
            },
            dataEngine: new Bloodhound({
                datumTokenizer: function (d) {
                    return Bloodhound.tokenizers.whitespace(d.value);
                },
                queryTokenizer: Bloodhound.tokenizers.whitespace,
                limit: o.autocompleteDisplayNumberLimit,
                minLength: 1,
                remote: {
                    url: tfl.addAppIdAndKey(tfl.api.StopPointsSearch.format(modes, oysterOnly)),
                    cache: true,
                    replace: function (url, uriEncodedQuery) { /* remove url-encoded spaces from input */
                        var returnUrl = "";
                        if (uriEncodedQuery !== null && uriEncodedQuery.length > 0) {
                            var rgx = new RegExp("[^a-zA-Z0-9 &-/().']", "g");
                            return url.replace("%QUERY", decodeURIComponent(uriEncodedQuery).replace(rgx, ""));
                        }

                        return returnUrl;
                    },
                    filter: function (response) { /* parse suggestions out of response  */
                        function mostModesComparator(a, b) {
                            var modesDiff = b["c"].length - a["c"].length;
                            if (modesDiff === 0) {
                                return a["a"] > b["a"] ? 1 : -1;
                            } else {
                                return modesDiff;
                            }
                        }

                        function isSearchMatchTopLevel(matchedStop, matchedStops) {
                            for (var j = 0; j < matchedStops.length; j++) {
                                if (matchedStops[j].id == matchedStop.parentId) {
                                    return false;
                                }
                            }
                            return true;
                        }

                        //exclude hubs and platforms
                        function searchStationsOnly(matchedStop) {
                            //Hubs are defined as having 3 or more modes.
                            if (matchedStop.modes && matchedStop.modes.length > 2) {
                                return false;
                            } else {
                                return true;
                            }
                        }

                        var searchMatches = response.matches;
                        var results = [];
                        if (searchMatches != null) {
                            for (var i = 0; i < searchMatches.length; i++) {
                                var searchMatch = searchMatches[i];
                                //var isHub = searchMatch.id == searchMatch.topMostParentId;
                                if (excludeHubsAndPlatforms) {
                                    if (searchStationsOnly(searchMatch)) {
                                        results.push({
                                            "a": searchMatch.name,
                                            "b": searchMatch.id,
                                            "c": searchMatch.modes,
                                            "d": searchMatch.lat,
                                            "e": searchMatch.lon
                                        });
                                    }
                                } else if (busStopsOnly) {
                                    if (searchMatch.stopType == tfl.naptanPublicBusCoachTram) {
                                        results.push({
                                            "a": searchMatch.name,
                                            "b": searchMatch.id,
                                            "c": searchMatch.modes,
                                            "d": searchMatch.lat,
                                            "e": searchMatch.lon
                                        });
                                    }
                                } else {
                                    if (isSearchMatchTopLevel(searchMatch, searchMatches)) {
                                        results.push({
                                            "a": searchMatch.name,
                                            "b": searchMatch.id,
                                            "c": searchMatch.modes,
                                            "d": searchMatch.lat,
                                            "e": searchMatch.lon
                                        });
                                    }
                                }
                            }
                            results.sort(mostModesComparator);
                        }
                        return results;
                    }
                }
            })
        };
    };

})(window.tfl.autocomplete.sources = window.tfl.autocomplete.sources || {});