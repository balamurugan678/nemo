window.tfl.journeyPlanner = window.tfl.journeyPlanner || {};
(function (o) {
    "use strict";
    tfl.logs.create("tfl.journeyPlanner.recentJourneys: loaded");
    o.journeys = [];
    o.usingRecentJourneys = tfl.storage.get('usingRecentJourneys', true);
    o.isLoaded = false;
    o.recentJourneyNumberLimit = 5;

    o.loadJourneys = function () {
        if (!o.isLoaded) {
            if (o.usingRecentJourneys) {
                o.journeys = tfl.storage.get('recentJourneys', []);
            }
            o.isLoaded = true;
        }
    };

    o.useJourneys = function (on) {
        tfl.journeyPlanner.recentJourneys.usingRecentJourneys = on;
        //if (on) {
        //tfl.journeyPlanner.autocomplete.setupAutocomplete(); //clear autocomplete recent search options
        //}
        tfl.storage.set("usingRecentJourneys", o.usingRecentJourneys);
        o.journeys = [];
        tfl.storage.set("recentJourneys", o.journeys);
        tfl.journeyPlanner.recentSearches.searches = [];
        tfl.storage.set("recentSearches", tfl.journeyPlanner.recentSearches.searches);
        return false;
    };

    o.saveJourney = function (fromId, fromModes, toId, toModes, viaId, viaModes) {
        if ($("#From").val() === tfl.dictionary.CurrentLocationText ||
            $("#From").val() === tfl.dictionary.CurrentLocationText ||
            $("#From").val() === tfl.dictionary.CurrentLocationText) {
            return true;
        }
        //load journeys
        o.loadJourneys();
        var newItem = {
            //from: $("#From").val().replace(/^\s+|\s+$/g, '').replace(/\w\S*/g, function (txt) { return txt.charAt(0).toUpperCase() + txt.substr(1).toLowerCase(); }),
            from: $("#From").val().replace(/^\s+|\s+$/g, ''),
            fromId: fromId,
            fromModes: fromModes ? jQuery.parseJSON(fromModes) : "",
            to: $("#To").val().replace(/^\s+|\s+$/g, ''),
            toId: toId,
            toModes: toModes ? jQuery.parseJSON(toModes) : "",
            via: $("#Via").val().replace(/^\s+|\s+$/g, ''),
            viaId: viaId,
            viaModes: viaModes ? jQuery.parseJSON(viaModes) : ""
        };
        //blank from and to aren't allowed, but these will be picked up by validation,
        //so we'll just return true and let the validation pick up the errors.
        if (newItem.from === "" || newItem.to === "") {
            return true;
        }
        for (var i = 0; i < tfl.journeyPlanner.recentJourneys.journeys.length; i++) {
            var j = tfl.journeyPlanner.recentJourneys.journeys[i];
            if (j.from.toLowerCase().replace(/ /g, "") === newItem.from.toLowerCase().replace(/ /g, "") && j.to.toLowerCase().replace(/ /g, "") === newItem.to.toLowerCase().replace(/ /g, "") && j.via.toLowerCase().replace(/ /g, "") === newItem.via.toLowerCase().replace(/ /g, "")) {
                //check modes and ids and keep if already exist
                if (newItem.fromId == "" && j.fromId !== "") {
                    newItem.fromId = j.fromId;
                }
                if (newItem.fromModes == "" && j.fromModes !== "") {
                    newItem.fromModes = j.fromModes;
                }
                if (newItem.toId == "" && j.toId !== "") {
                    newItem.toId = j.toId;
                }
                if (newItem.toModes == "" && j.toModes !== "") {
                    newItem.toModes = j.toModes;
                }
                if (newItem.viaId == "" && j.viaId !== "") {
                    newItem.viaId = j.viaId;
                }
                if (newItem.viaModes == "" && j.viaModes !== "") {
                    newItem.viaModes = j.viaModes;
                }
                tfl.journeyPlanner.recentJourneys.journeys.splice(i, 1);
                break;
            }
        }
        tfl.journeyPlanner.recentJourneys.journeys.splice(0, 0, newItem);
        if (tfl.journeyPlanner.recentJourneys.journeys.length > o.recentJourneyNumberLimit) {
            tfl.journeyPlanner.recentJourneys.journeys.length = o.recentJourneyNumberLimit;
        }
        tfl.storage.set("recentJourneys", tfl.journeyPlanner.recentJourneys.journeys);
        return true;
    };

})(window.tfl.journeyPlanner.recentJourneys = window.tfl.journeyPlanner.recentJourneys || {});