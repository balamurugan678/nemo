(function (o) {
    "use strict";

    tfl.logs.create("tfl.favourites: loaded");

    var buttons = {};

    // helper method 
    function toCamelCase(myString) {
        return myString.replace(/-([a-z])/g, function (g) {
            return g[1] !== undefined ? g[1].toUpperCase() : g[1];
        });
    }

    // favourites structure
    function initFavourites() {
        var k;
        o.values = {};
        for (k = 0; k < tfl.objects.modes.length; k += 1) {
            o.values[toCamelCase(tfl.objects.modes[k])] = {};
        }
    };

    // get favourites from storage
    function retrieveFavourites() {
        var fav = tfl.storage.get("favourites");
        if (fav != null) {
            for (var prop in fav) {
                if (fav.hasOwnProperty(prop)) {
                    if (!o.values.hasOwnProperty(prop)) {
                        delete fav[prop];
                    }
                }
            }
            o.values = $.extend(o.values, fav);
        }
    };

    // save favourites in memory to storage
    o.saveFavourites = function () {
        tfl.storage.set("favourites", o.values);
    };

    // add or update a favourite with new data
    o.addFavourite = function (obj) {
        if (typeof(obj) !== "object"
            || !obj.hasOwnProperty('mode')
            || obj.mode === ""
            || !obj.hasOwnProperty('naptanId')
            || obj.naptanId === "") {
            tfl.logs.create('Add Favourite expects object defined in tfl.objects');
            return false;
        } else {
            o.values[toCamelCase(obj.mode)][obj.naptanId] = obj;
            o.saveFavourites();
            // style all buttons relating to that naptan
            if (buttons.hasOwnProperty(obj.naptanId)) {
                for (var i = 0; i < buttons[obj.naptanId].dom.length; i += 1) {
                    buttons[obj.naptanId].dom[i].addClass('active');
                }
            }
            return true;
        }
    };

    // get favourite by naptan id
    o.getFavourite = function (naptanId) {
        if (typeof (naptanId) === "string") {
            for (mode in o.values) {
                if (o.values.hasOwnProperty(mode)) {
                    if (o.values[mode][naptanId] !== undefined) return o.values[mode][naptanId];
                }
            }
        } else {
            tfl.logs.create('Error in tfl.favourites.getFavourite - Expected naptanId');
            return false;
        }
        return false;
    };

    // get favourites as array, mode is optional to get one specitic mode
    o.getFavouritesArray = function (mode) {
        var returnArray = [];
        if (o.values.hasOwnProperty(mode)) {
            for (var val in o.values[mode]) {
                if (o.values[mode].hasOwnProperty(val)) {
                    returnArray.push(o.values[mode][val]);
                }
            }
        } else {
            for (var mode in o.values) {
                if (o.values.hasOwnProperty(mode)) {
                    for (var val in o.values[mode]) {
                        if (tfl.favourites.values[mode].hasOwnProperty(val)) {
                            returnArray.push(tfl.favourites.values[mode][val]);
                        }
                    }
                }
            }
        }
        return returnArray;
    };

    //remove favourite by naptan id
    o.removeFavourite = function (naptanId) {
        if (typeof (naptanId) === "string" || typeof (naptanId) === "number") {
            for (var mode in o.values) {
                if (o.values.hasOwnProperty(mode)) {
                    delete o.values[mode][naptanId];
                }
            }
        } else {
            tfl.logs.create('Error in tfl.favourites.removeFavourite - Expected naptanId');
            return false;
        }
        o.saveFavourites();
        // style all buttons relating to that naptan
        if (buttons.hasOwnProperty(naptanId)) {
            for (var i = 0; i < buttons[naptanId].dom.length; i += 1) {
                buttons[naptanId].dom[i].removeClass('active');
            }
        }
        return true;
    };

    // clear all favourites
    o.clearFavourites = function () {
        initFavourites();
        o.saveFavourites();
        for (var naptanId in buttons) {
            if (buttons.hasOwnProperty(naptanId)) {
                for (var i = 0; i < buttons[naptanId].dom.length; i += 1) {
                    buttons[naptanId].dom[i].removeClass('active');
                }
            }
        }

    };

    function appendButton($placeholder, active) {
        var classes = active ? 'active' : '';

        // inject a button
        $placeholder.append('<a href="javascript:void(0)" class="plain-link with-icon favourite ' + classes + '">' +
            '<span class="i favourite-icon"></span>' +
            '<span class="inactive">Add Favourite</span>' +
            '<span class="active">Favourited <span class="visually-hidden">Click to remove from favourites</span></span>' +
            '</a>');
        return $placeholder.find('>.favourite');
    }

    // click handlers for favourite buttons
    o.setupButtons = function () {
        var $placeholders = $('[data-favourite]'),
            i;
        $placeholders.each(function () {

            var $this = $(this),
                data = $this.data('favourite'),
                fromExisting = false,
                obj, $button, favClass;

            if (typeof(data) === "string") {
                var matchedFavourite = o.getFavourite(data);
                if (!matchedFavourite) {
                    $this.removeAttr('data-favourite');
                    tfl.logs.create('tfl.favourites: fail to set up button, string provided - doesnt match existing favourite');
                    return true;
                }
                fromExisting = true;
                obj = matchedFavourite;
            } else if (typeof (data) !== "object") {
                $this.removeAttr('data-favourite');
                tfl.logs.create('tfl.favourites: neither string nor object passed to setupButtons');
                return true;
            }

            if (!fromExisting) {
                // check this object has a naptan id and a mode associated (both mandatory);
                if (!data.hasOwnProperty('naptanId')
                    || !data.hasOwnProperty('mode')) {
                    $this.removeAttr('data-favourite');
                    tfl.logs.create('tfl.favourites: object passed to setup button has no mode or naptanId');
                    return true;
                }

                // make it into a new SSP object
                obj = new tfl.objects.SSPObject(data.naptanId, data.mode, data.props);

                if (obj.hasOwnProperty('error')) {
                    $this.removeAttr('data-favourite');
                    tfl.logs.create('tfl.favourites: properties passed to setup button generate invalid SSPObject');
                    return true;
                }
            }

            $button = appendButton($this, o.values[toCamelCase(obj.mode)].hasOwnProperty(obj.naptanId));

            if (!buttons.hasOwnProperty(obj.naptanId)) {
                // fresh registration
                buttons[obj.naptanId] = {
                    data: obj,
                    dom: [$button]
                }
            } else {
                // already registered
                if (fromExisting) {
                    buttons[obj.naptanId].data = $.extend(obj, buttons[obj.naptanId].data); // just in case more properties in second
                } else {
                    buttons[obj.naptanId].data = $.extend(buttons[obj.naptanId].data, obj); // just in case more properties in second
                }
                buttons[obj.naptanId].dom.push($button); // add button
            }

            // add click to newly created child
            $button.on('click', function (e) {
                e.preventDefault();
                if ($(this).hasClass('active')) {
                    o.removeFavourite(obj.naptanId);
                } else {
                    o.addFavourite(buttons[obj.naptanId].data);
                }
            });

            // specific classes for fav button
            favClass = $this.data('favourite-class');
            if (favClass !== undefined) $button.addClass(favClass);

            // condtional styles should be done with with-favourite
            $this.parent().addClass('with-favourite');

            // clean up
            $this.removeAttr('data-favourite');

            // add to recent (all favouritable things count towards recent)
            tfl.recent.addRecent(obj);

        });

    };


    function injectFavouriteAndRecent($favs, markup) {
        var template = Hogan.compile(markup);
        var busFavsData = o.getFavouritesArray(tfl.modeNameBus);
        var recentBusData = tfl.recent.getRecentArray(tfl.modeNameBus);

        function renderFavouriteAndRecent() {
            var favresult = template.render({ items: busFavsData, name: "favourite", hidden: "", itemsHeading: "favourited bus stops", noItemsMessage: "You currently don't have an favourite bus stops saved, save some bus stops as your favourites and they shall appear here in future!" });
            var recentresult = template.render({ items: recentBusData, name: "recent", hidden: "hidden", itemsHeading: "recently searched bus stops", noItemsMessage: "No bus stops in your recent search history." });
            var $tabs = $('<div class="tab-wrapper"><ul class="tabs-style-2"><li class="selected" data-target="favourite"><a href="#">Favourites</a></li><li data-target="recent"><a href="#">Recent</a></li></ul></div>');
            $tabs.append(favresult);
            $tabs.append(recentresult);
            $favs.append($tabs);
            o.setupButtons();
            if (busFavsData.length > 5) {
                tfl.navigation.pagination.setup($favs.find('.favourite .bus-stops').parent(), ".bus-stops", 5, busFavsData.length);
            }
            if (recentBusData.length > 5) {
                tfl.navigation.pagination.setup($favs.find('.recent .bus-stops').parent(), ".bus-stops", 5, recentBusData.length);
            }
            var $tabs = $favs.find('.tabs-style-2 > li:not(.not-for-beta)');
            $tabs.on('click', function (e) {
                e.preventDefault();
                e.stopPropagation();
                var $this = $(this);
                if (!$this.hasClass('selected')) {
                    var $sibs = $this.parent().siblings();
                    $sibs.addClass('hidden');
                    $sibs.filter('.' + $this.data('target')).removeClass('hidden');
                    $this.addClass('selected').siblings('.selected').removeClass('selected');
                }
            });
            if (!$(markup).find('.links-only').length) {
                tfl.expandableBox.init();
            }
        }

        var busData = busFavsData.concat(recentBusData);

        if (busData.length) {
            var max = busData.length;
            var count = 0;
            var ajaxData = {};

            var ajaxStopPoints = [];
            for (var i = 0; i < max; i += 1) {
                if (ajaxStopPoints.indexOf(busData[i].naptanId) < 0) {
                    ajaxStopPoints.push(busData[i].naptanId);
                }
            }
            ;

            var startDate = tfl.tools.getQueryStringParameter('startDate');

            if (startDate !== null) {
                ajaxData.url = tfl.api.StopPointsDisruptionsTimePeriod.format(ajaxStopPoints.join(","), startDate, tfl.tools.getQueryStringParameter('endDate'));
                //ajaxData.url = "/PlannedWorks";
                //ajaxData.data.startDate = startDate;
                //ajaxData.data.endDate = tfl.tools.getQueryStringParameter('endDate');
                //ajaxData.data.naptanCodes = ajaxData.data.stopPointIds.join(",");

            } else {
                ajaxData.url = tfl.api.StopPointsDisruptions.format(ajaxStopPoints.join(","));
            }

            ajaxData.success = function (response) {
                for (var a = 0; a < response.length; a += 1) {
                    var ajaxStop = response[a];
                    favs: for (var j = 0; j < busFavsData.length; j += 1) {
                        if (busFavsData[j].naptanId === ajaxStop.stationAtcoCode) {
                            console.log("FOUND " + ajaxStop.stationAtcoCode);
                            busFavsData[j].props.disrupted = "disrupted";

                            if (!busFavsData[j].props.hasOwnProperty('disruptionInfo')) {
                                busFavsData[j].props.disruptionInfo = "";
                            }

                            busFavsData[j].props.disruptionInfo += "<p>" + response[a].description + "</p>";

                            break favs;
                        }
                    }
                    rec: for (var j = 0; j < recentBusData.length; j += 1) {
                        if (recentBusData[j].naptanId === ajaxStop.stationAtcoCode) {
                            console.log("FOUND " + ajaxStop.stationAtcoCode);
                            recentBusData[j].props.disrupted = "disrupted";

                            if (!recentBusData[j].props.hasOwnProperty('disruptionInfo')) {
                                recentBusData[j].props.disruptionInfo = "";
                            }

                            recentBusData[j].props.disruptionInfo += "<p>" + response[a].description + "</p>";

                            break rec;
                        }
                    }
                    /*if (response[a].disruptedPoint != null) {
                     for (var i = 0; i < response[a].affectedStops.length; i += 1) {
                     favs: for (var j = 0; j < busFavsData.length; j += 1) {
                     if (busFavsData[j].naptanId === response[a].affectedStops[i].id) {

                     busFavsData[j].props.disrupted = "disrupted";

                     if (!busFavsData[j].props.hasOwnProperty('disruptionInfo')) {
                     busFavsData[j].props.disruptionInfo = "";
                     }

                     busFavsData[j].props.disruptionInfo += "<p>" + response[a].description + "</p>";

                     break favs;
                     }
                     }
                     rec: for (var j = 0; j < recentBusData.length; j += 1) {
                     if (recentBusData[j].naptanId === response[a].affectedStops[i].id) {

                     recentBusData[j].props.disrupted = "disrupted";

                     if (!recentBusData[j].props.hasOwnProperty('disruptionInfo')) {
                     recentBusData[j].props.disruptionInfo = "";
                     }

                     recentBusData[j].props.disruptionInfo += "<p>" + response[a].description + "</p>";

                     break rec;
                     }
                     }
                     }
                     }*/
                }
                renderFavouriteAndRecent();
            };

            tfl.ajax(ajaxData);
        }
    }

    o.setupFavouritesAndRecent = function (opts) {
        var $target = $(opts.selector);
        if ($target.length) {
            tfl.ajax({
                url: '/static/' + tfl.settings.version + '/templates/' + opts.markup + '.html',
                success: function (response) {
                    injectFavouriteAndRecent($target, response)
                },
                dataType: 'html'
            });
        }
    };

    function init() {
        if (tfl.storage.isLocalStorageSupported()) {
            initFavourites();
            retrieveFavourites();
            o.setupButtons();
            o.setupFavouritesAndRecent({
                selector: ".favourite-and-recent",
                markup: "bus-stop-disruption"
            });
        }
        tfl.logs.create("tfl.favourites: setup");
    }

    init();

})(window.tfl.favourites = window.tfl.favourites || {});