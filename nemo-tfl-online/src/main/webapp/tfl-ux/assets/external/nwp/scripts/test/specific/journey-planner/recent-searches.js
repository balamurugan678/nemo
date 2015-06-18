window.tfl.journeyPlanner = window.tfl.journeyPlanner || {};
(function (o) {
    "use strict";
    tfl.logs.create("tfl.journeyPlanner.recentSearches: loaded");
    o.searches = [];
    o.isLoaded = false;
    o.recentSearchNumberLimit = 5;
    o.recentSearchTimeLimit = 2629740000; //1 month in milliseconds

    o.loadSearches = function () {
        if (!o.isLoaded) {
            if (tfl.journeyPlanner.recentJourneys.usingRecentJourneys) {
                o.searches = tfl.storage.get("recentSearches", []);
                if (o.searches.length > 0) {
                    // remove old searches
                    var timeNow = new Date().getTime();
                    for (var i = 0; i < o.searches.length; i++) {
                        var j = o.searches[i];
                        if (j.lastSearchDate < (timeNow - o.recentSearchTimeLimit)) {
                            o.searches.splice(i, 1); //remove from local memory
                        }
                    }
                }
            }
            o.isLoaded = true;
        }
    };

    o.saveSearch = function (newPlaceName, newPlaceId, newPlaceModes) {
        if (newPlaceName === "" || newPlaceName === tfl.dictionary.CurrentLocationText) return true;
        newPlaceName = newPlaceName.replace(/^\s+|\s+$/g, '');

        if (newPlaceId === null) newPlaceId = "";
        if (typeof newPlaceModes == "string" && newPlaceModes !== "") {
            newPlaceModes = jQuery.parseJSON(newPlaceModes);
        }
        var timeNow = new Date().getTime();
        // is search aleardy in recent searches?
        for (var i = 0; i < tfl.journeyPlanner.recentSearches.searches.length; i++) {
            var j = tfl.journeyPlanner.recentSearches.searches[i];
            if (j.placeName.toLowerCase() === newPlaceName.toLowerCase()) {
                if (newPlaceId == "" && j.placeId !== "") {
                    newPlaceId = j.placeId;
                }
                if (newPlaceModes == "" && j.placeModes !== "") {
                    newPlaceModes = j.placeModes;
                }
                tfl.journeyPlanner.recentSearches.searches.splice(i, 1);//remove from local memory
                break;
            }
        }
        //new search item
        var newSearch = {
            placeName: newPlaceName,
            placeId: newPlaceId,
            placeModes: newPlaceModes,
            lastSearchDate: timeNow
        };

        //insert into array and into local memory
        tfl.journeyPlanner.recentSearches.searches.splice(0, 0, newSearch);
        //limit number of recent searches that are stored in the autocomplete list
        if (tfl.journeyPlanner.recentSearches.searches.length > o.recentSearchNumberLimit) {
            tfl.journeyPlanner.recentSearches.searches.length = o.recentSearchNumberLimit;
        }
        tfl.storage.set("recentSearches", tfl.journeyPlanner.recentSearches.searches);

        return true;
    };

})(window.tfl.journeyPlanner.recentSearches = window.tfl.journeyPlanner.recentSearches || {});