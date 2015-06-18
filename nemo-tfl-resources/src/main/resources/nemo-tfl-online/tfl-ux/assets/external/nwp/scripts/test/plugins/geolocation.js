(function (o) {
    "use strict";
    tfl.logs.create("tfl.geolocation: loaded");

    o.minAccuracy = 1000;

    o.geocoder = null;

    o.geolocationTimeout = 10000;


    /*o.geolocateMe = function (success, failure) {
     if (o.isGeolocationSupported) {
     navigator.geolocation.getCurrentPosition(success, failure);
     }
     };*/

    o.isGeolocationSupported = function () {
        if ("geolocation" in navigator && navigator["geolocation"] !== null) {
            tfl.logs.create("tfl.geolocation: isGeolocationSupported true");
            return true;
        } else {
            tfl.logs.create("tfl.geolocation: isGeolocationSupported false");
            return false;
        }
    };

    o.geolocateMe = function (inputEl, callback) {
        tfl.logs.create("tfl.geolocation: geolocate me");
        o.clearGeolocationError();
        var el = $(inputEl);
        el.typeahead("val", "");
        el.originalPlaceholder = el.attr("placeholder");
        el.attr("placeholder", "Finding address...");
        var success = function (position) {
            if (position.coords && position.coords.accuracy && position.coords.accuracy > tfl.geolocation.minAccuracy) {
                tfl.logs.create("tfl.geolocation.geolocate me: ERROR: inaccuracy too high: " + position.coords.accuracy + "m");
                o.geolocationError("We cannot find your current location. Please try again", el);
                el.parent().siblings(tfl.removableContent.RemoveContentClass).hide();
                return;
            }
            $(inputEl + "Geolocation").val(position.coords.longitude + "," + position.coords.latitude);
            o.setupInputBoxForGeolocation(el);
            if (callback) {
                callback();
            }
        };
        var failure = function (err) {
            el.parent().siblings(tfl.removableContent.RemoveContentClass).hide();
            el.closest('[data-current-location]').removeAttr("data-current-location");
            if (err.code) {
                if (err.code === 1) {
                    tfl.logs.create("tfl.geolocation.geolocateMe: ERROR: permission denied");
                    o.geolocationError("Permission from browser needed before finding your location", el);
                } else if (err.code === 2) {
                    tfl.logs.create("tfl.geolocation.geolocateMe: ERROR: position unavailable");
                    o.geolocationError("We cannot find your current location. Please try again", el);
                } else if (err.code === 3) {
                    tfl.logs.create("tfl.geolocation.geolocateMe: ERROR: timeout");
                    o.geolocationError("We cannot find your current location. Please try again", el);
                }
            } else {
                o.geolocationError("We cannot find your current location. Please try again", el);
            }
        };
        try {
            navigator.geolocation.getCurrentPosition(success, failure, { timeout: o.geolocationTimeout });
        } catch (e) {
            o.geolocationError("Your device does not support the location feature", el);
        }
    };

    o.geolocationError = function (msg, el) {
        $(".geolocation-error").text(msg).removeClass("hidden");
        el.attr("placeholder", el.originalPlaceholder);
        $("[data-current-location]").removeAttr("data-current-location");
        el.focus();
    };

    o.clearGeolocationError = function () {
        $(".geolocation-error").addClass("hidden");
    };

    o.setupInputBoxForGeolocation = function (el) {
        //ignoreKeys variable contains keycodes for keys like tab, ctrl etc which shouldn't clear the input box
        //see http://www.cambiaresearch.com/articles/15/javascript-char-codes-key-codes
        var ignoreKeys = [9, 16, 17, 18, 19, 20, 33, 34, 35, 36, 37, 38, 39, 40, 45, 46, 91, 92, 93, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 144, 145];
        var parent = el.parent();
        parent.attr("data-current-location", "true");
        parent.addClass("geocoded");
        el.typeahead("val", tfl.dictionary.CurrentLocationText);
        //clear the box on input
        el.on("keydown", function (e) {
            if (ignoreKeys.indexOf(e.keyCode) > -1) {
                return;
            }
            if (parent.attr("data-current-location")) {
                parent.removeAttr("data-current-location");
                parent.removeClass("geocoded");
                el.typeahead("val", "");
                $(el.selector + "Geolocation").val("");
            }
        });
    };
}(window.tfl.geolocation = window.tfl.geolocation || {}));