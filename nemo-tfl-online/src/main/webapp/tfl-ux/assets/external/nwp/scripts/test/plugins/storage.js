(function (o) {
    "use strict";
    tfl.logs.create("tfl.storage: loaded");
    var storage = window.localStorage;
    o.isLocalStorageSupported = function () {
        if ("localStorage" in window && window["localStorage"] !== null) {
            tfl.logs.create("tfl.storage: isLocalStorageSupported true");
            return true;
        } else {
            tfl.logs.create("tfl.storage: isLocalStorageSupported false");
            return false;
        }
    };
    o.set = function (key, value) {
        if (storage && key != undefined) {
            try {
                var parseValue = JSON.stringify(value);
                storage.setItem(key, parseValue);
                tfl.logs.create("tfl.storage.set: " + key + ":" + parseValue);
            } catch (e) {
                tfl.logs.create("tfl.storage.set:" + e.message, "error");
            }
        }
    };
    o.get = function (key, defaultValue) {
        if (storage) {
            try {
                var value = storage.getItem(key);
                if (value != null) {
                    //tfl.logs.create("tfl.storage.get: " + key + ":" + value);
                    return JSON.parse(value);
                }
            } catch (e) {
                tfl.logs.create("tfl.storage.get:" + e.message, "error");
            }
        }
        if (defaultValue !== '') {
            tfl.logs.create("tfl.storage.get: " + key + ":" + defaultValue);
            return defaultValue;
        }
        tfl.logs.create("tfl.storage.get: " + key + ": []");
        return [];
    };

    o.showIfNotDisabled = function (key, callbackShowOnEnabledOrFailure) {
        if (storage) {
            try {
                var isHidden = JSON.parse(o.get(key, false));
                if (!isHidden) {
                    callbackShowOnEnabledOrFailure();
                }
            } catch (e) {
                callbackShowOnEnabledOrFailure();
            }
        } else {
            callbackShowOnEnabledOrFailure();
        }
    };
}(window.tfl.storage = window.tfl.storage || {}));