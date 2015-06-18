(function (o) {
    "use strict";

    tfl.logs.create("tfl.recent: loaded");

    var recentCap = 5;

    // helper method 
    function toCamelCase(myString) {
        return myString.replace(/-([a-z])/g, function (g) {
            return g[1] !== undefined ? g[1].toUpperCase() : g[1];
        });
    }

    // recent structure
    function initRecent() {
        var k;
        o.values = {};
        for (k = 0; k < tfl.objects.modes.length; k += 1) {
            o.values[toCamelCase(tfl.objects.modes[k])] = [];
        }
    };

    // get recent from storage
    function retrieveRecent() {
        var rec = tfl.storage.get("recent");
        if (rec != null) {
            for (var prop in rec) {
                if (rec.hasOwnProperty(prop)) {
                    if (!o.values.hasOwnProperty(prop)) {
                        delete rec[prop];
                    }
                }
            }
            o.values = $.extend(o.values, rec);
        }
    };

    // save recent in memory to storage
    o.saveRecent = function () {
        tfl.storage.set("recent", o.values);
    };

    Array.prototype.remove = function (from, to) {
        var rest = this.slice((to || from) + 1 || this.length);
        this.length = from < 0 ? this.length + from : from;
        return this.push.apply(this, rest);
    };


    // add or update a recent with new data
    o.addRecent = function (obj) {
        if (typeof (obj) !== "object"
            || !obj.hasOwnProperty('mode')
            || obj.mode === ""
            || !obj.hasOwnProperty('naptanId')
            || obj.naptanId === "") {
            tfl.logs.create('Add recent expects object defined in tfl.objects');
            return false;
        } else {
            var alreadyRecent = false;
            for (var i = 0; i < o.values[toCamelCase(obj.mode)].length; i += 1) {
                if (o.values[toCamelCase(obj.mode)][i].naptanId === obj.naptanId) {
                    // already in recent - bump to top.
                    alreadyRecent = true;
                    o.values[toCamelCase(obj.mode)].remove(i);
                    o.values[toCamelCase(obj.mode)].unshift(obj);
                    break;
                }
            }
            if (!alreadyRecent) {
                o.values[toCamelCase(obj.mode)].unshift(obj);
                if (o.values[toCamelCase(obj.mode)].length > recentCap) {
                    o.values[toCamelCase(obj.mode)].remove(recentCap);
                }
            }
            o.saveRecent();
            return true;
        }
    };

    // get recent by naptan id
    o.getRecent = function (naptanId) {
        if (typeof (naptanId) === "string") {
            for (var mode in o.values) {
                if (o.values.hasOwnProperty(mode)) {
                    for (var i = 0; i < o.values[mode].length; i += 1) {
                        if (o.values[mode][i].naptanId === naptanId) return o.values[mode][i];
                    }
                }
            }
        } else {
            tfl.logs.create('Error in tfl.favourites.getRecent - Expected naptanId');
            return false;
        }
        return false;
    };

    // get recent as array, mode is optional to get one specitic mode
    o.getRecentArray = function (mode) {
        var returnArray = [];
        if (o.values.hasOwnProperty(mode)) {
            returnArray = o.values[mode];
        } else {
            for (var mode in o.values) {
                if (o.values.hasOwnProperty(mode)) {
                    returnArray = returnArray.concat(tfl.recent.values[mode]);
                }
            }
        }
        return returnArray;
    };

    //remove recent by naptan id
    o.removeRecent = function (naptanId) {
        if (typeof (naptanId) === "string" || typeof (naptanId) === "number") {
            for (var mode in o.values) {
                if (o.values.hasOwnProperty(mode)) {
                    for (var i = 0; i < o.values[mode].length; i += 1) {
                        if (o.values[mode][i].naptanId === naptanId) delete o.values[mode][i];
                    }
                }
            }
        } else {
            tfl.logs.create('Error in tfl.recent.removeRecent - Expected naptanId');
            return false;
        }
        o.saveRecent();
        return true;
    };

    // clear all recent
    o.clearRecent = function () {
        initRecent();
        o.saveRecent();
    };

    function init() {
        if (tfl.storage.isLocalStorageSupported()) {
            initRecent();
            retrieveRecent();
        }
        tfl.logs.create("tfl.recent: setup");
    }

    init();

})(window.tfl.recent = window.tfl.recent || {});