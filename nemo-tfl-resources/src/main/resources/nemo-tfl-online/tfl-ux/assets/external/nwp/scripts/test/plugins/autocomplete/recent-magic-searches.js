window.tfl.autocomplete = window.tfl.autocomplete || {};
(function (o) {
    "use strict";
    tfl.logs.create("tfl.autocomplete.recentMagicSearches: loaded");
    o.searches = {};
    o.usingRecentSearches = tfl.storage.get('usingRecentMagicSearches', true);
    o.isLoaded = false;
    o.recentSearchNumberLimit = 5;
    o.recentSearchTimeLimit = 2629740000; //1 month in milliseconds

    o.useRecentSearches = function (on) {
        o.usingRecentSearches = on;
        tfl.storage.set("usingRecentMagicSearches", o.usingRecentSearches);
        o.searches = {};
        tfl.storage.set("recentMagicSearches", o.searches);
        return false;
    };

    o.loadSearches = function () {
        tfl.logs.create("tfl.autocomplete.recentMagicSearches.loadSearches: initialised");
        if (!o.isLoaded) {
            if (o.usingRecentSearches) {
                o.searches = tfl.storage.get("recentMagicSearches", {});
                // remove old searches
                var timeNow = new Date().getTime();
                var itemRemoved;
                for (var type in o.searches) {
                    if (o.searches.hasOwnProperty(type)) {
                        for (var mode in o.searches[type]) {
                            if (o.searches[type].hasOwnProperty(mode)) {
                                for (var i = 0; i < o.searches[type][mode].length; i++) {
                                    if (o.searches[type][mode][i] !== undefined) {
                                        var item = o.searches[type][mode][i];

                                        if (!item.hasOwnProperty('lastSearchDate') || item.lastSearchDate < (timeNow - o.recentSearchTimeLimit)) {
                                            tfl.logs.create("tfl.autocomplete.recentMagicSearches: removing old searches");
                                            o.searches[type][mode].splice(i, 1); //remove from local memory
                                            itemRemoved = true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                if (itemRemoved) tfl.storage.set("recentMagicSearches", o.searches);
            }
            o.isLoaded = true;
        }
    };

    o.saveSearch = function (newName, newId, newModes, newType, lat, lon, cssClass, direction, destination) {
        tfl.logs.create("tfl.autocomplete.recentMagicSearches.saveSearch: initialised");

        // load recent searches
        o.loadSearches();

        if (newName === "") return true;
        if (newId === null) newId = "";
        if (cssClass === null) cssClass = "";
        if (direction === null) direction = "";
        if (destination === null) destination = "";
        if (typeof newModes == "string" && newModes !== "") {
            newModes = jQuery.parseJSON(newModes);
        }
        if (newModes === null) newModes = [];
        var timeNow = new Date().getTime();
        if (newType == "lines") {
            // mode part of object properties (e.g. type: Lines)
            var newMode = newModes[0].toString();
            // check whether object exists within o.searches, and if not then add it
            if (o.searches[newType]) {
                if (o.searches[newType][newMode]) {
                    // is search aleardy in recent searches? if so, remove (it will be added back in at top of list later)
                    for (var b = 0; b < o.searches[newType][newMode].length; b++) {
                        var line = o.searches[newType][newMode][b];
                        if (line.id.toLowerCase() == newId.toLowerCase()) {
                            o.searches[newType][newMode].splice(b, 1); //remove from array
                            break;
                        }
                    }
                } else {
                    o.searches[newType][newMode] = [];
                }
            } else {
                o.searches[newType] = {};
                o.searches[newType][newMode] = [];
            }

            //new search line
            var newLine = {
                lastSearchDate: timeNow,
                name: newName,
                id: newId,
                mode: newMode,
                cssClass: cssClass,
                direction: direction,
                destination: destination,
                type: newType
            };

            //insert into array and into local memory
            o.searches[newType][newMode].splice(0, 0, newLine);

            //limit number of recent searches that are stored in the autocomplete list
            if (o.searches[newType][newMode].length > o.recentSearchNumberLimit) {
                o.searches[newType][newMode].length = o.recentSearchNumberLimit;
            }

        } else if (newType == "stops") {
            // modes stored as array (e.g. type: Stops)
            // check whether object exists within o.searches, and if not then add it
            if (o.searches[newType]) {
                // is search aleardy in recent searches? if so, remove (it will be added back in at top of list later)
                for (var c = 0; c < o.searches[newType].length; c++) {
                    var stopPoint = o.searches[newType][c];
                    if (stopPoint.id.toLowerCase() == newId.toLowerCase()) {
                        o.searches[newType].splice(c, 1); //remove from array
                        break;
                    }
                }
            } else {
                o.searches[newType] = [];
            }

            //new search item
            var newStop = {
                lastSearchDate: timeNow,
                name: newName,
                id: newId,
                modes: newModes,
                lat: lat,
                lon: lon,
                type: newType
            };

            //insert into array and into local memory
            o.searches[newType].splice(0, 0, newStop);

            //limit number of recent searches that are stored in the autocomplete list
            if (o.searches[newType].length > o.recentSearchNumberLimit) {
                o.searches[newType].length = o.recentSearchNumberLimit;
            }
        }

        tfl.storage.set("recentMagicSearches", o.searches);
        return true;
    };

    o.geolocateMe = function (inputEl, callback) {
        var geolocationError = function (msg) {
            $(".geolocation-error").text(msg).removeClass("hidden");
        };

        tfl.logs.create("tfl.autocomplete.recentMagicSearches.geolocateMe: initialised");
        var el = $(inputEl);
        el.typeahead("val", "");
        el.attr("placeholder", "Finding address...");
        var success = function (position) {
            //if (position.coords && position.coords.accuracy && position.coords.accuracy > tfl.geolocation.minAccuracy) {
            if (position.coords && position.coords.accuracy && position.coords.accuracy > 1000000) {
                tfl.logs.create("tfl.journeyPlanner.searchForm.geolocate me: ERROR: inaccuracy too high: " + position.coords.accuracy + "m");
                geolocationError("We cannot find your current location. Please try again");

                return;
            }
            //el.typeahead("val", position.coords.longitude + "," + position.coords.latitude);
            el.val(position.coords.longitude + "," + position.coords.latitude);
            $(inputEl).append("<input type='hidden' id='lat' name='lat' value='" + position.coords.latitude + "' />");
            $(inputEl).append("<input type='hidden' id='lon' name='lon' value='" + position.coords.longitude + "' />");
            //o.setupInputBoxForGeolocation(el);
            if (callback) {
                callback();
            }
        };

        var failure = function (err) {
            if (err.code) {
                if (err.code === 1) {
                    tfl.logs.create("tfl.journeyPlanner.searchForm.geolocateMe: ERROR: permission denied");
                    geolocationError("Permission from browser needed before finding your location");
                } else if (err.code === 2) {
                    tfl.logs.create("tfl.journeyPlanner.searchForm.geolocateMe: ERROR: position unavailable");
                    geolocationError("We cannot find your current location. Please try again");
                } else if (err.code === 3) {
                    tfl.logs.create("tfl.journeyPlanner.searchForm.geolocateMe: ERROR: timeout");
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

})(window.tfl.autocomplete.recentMagicSearches = window.tfl.autocomplete.recentMagicSearches || {});