window.tfl.journeyPlanner = window.tfl.journeyPlanner || {};

(function (o) {
    "use strict";
    tfl.logs.create("tfl.journeyPlanner.searchForm: loaded");

    o.isWidget;

    o.widgetInit = function () {
        tfl.logs.create("tfl.journeyPlanner.searchForm: started widgetInit");

        $(".toggle-options, .change-departure-time").click(function (e) {
            var button = $(this);
            var href = button.attr('href');
            var uri = href.substr(0, href.indexOf('#'));
            var hash = href.substr(href.indexOf('#'));
            $.each(['From', 'To', 'FromId', 'ToId', 'FromGeolocation', 'ToGeolocation'], function (k, v) {
                var val = $('#' + v).val();
                if (val !== undefined && val !== '') {
                    if (k == 0) uri += '?';
                    if (k > 0) uri += '&';
                    uri += v + '=' + val;
                }
            });
            button.attr('href', uri + hash);
        });
    };

    o.setupDateOptions = function () {
        //increment the current time at 01,16,31,46 mins past the hour
        var d = new Date();

        var mins = ((15 - d.getMinutes() % 15) * 60000);
        var secs = ((60 - d.getSeconds() % 60) * 1000);
        setTimeout(o.incrementCurrentTime, (mins - secs));
    };

    o.processTempLocalStorage = function () { //el, attr name, attr value
        var tempJPLocalStorage = tfl.storage.get("tempJPLocalStorage", [
            []
        ]);
        if (tempJPLocalStorage.length > 0) {
            for (var i = 0; i < tempJPLocalStorage.length; i++) {
                $(tempJPLocalStorage[i][0]).attr(tempJPLocalStorage[i][1], tempJPLocalStorage[i][2]);
            }
        }
    };

    o.submitSearchForm = function () {
        var fromEl = $("#From");
        var toEl = $("#To");
        var viaEl = $("#Via");
        if (fromEl.attr("data-from-id")) {
            if ($("#FromId").length == 0) {
                $(this).append("<input type='hidden' id='FromId' name='FromId' value='" + fromEl.attr("data-from-id") + "' />");
            } else {
                $("#FromId").val(fromEl.attr("data-from-id"));
            }
        }
        if (toEl.attr("data-to-id")) {
            if ($("#ToId").length == 0) {
                $(this).append("<input type='hidden' id='ToId' name='ToId' value='" + toEl.attr("data-to-id") + "' />");
            }
            else {
                $("#ToId").val(toEl.attr("data-to-id"));
            }
        }
        if (viaEl.attr("data-via-id")) {
            $(this).append("<input type='hidden' id='ViaId' name='ViaId' value='" + viaEl.attr("data-via-id") + "' />");
        }
        var tempJPLocalStorage = [
            []
        ];
        if (fromEl.attr("data-from-modes")) {
            tempJPLocalStorage.push(["#From", "data-from-modes", fromEl.attr("data-from-modes")]);
        }
        if (fromEl.attr("data-dataset-name")) {
            tempJPLocalStorage.push(["#From", "data-dataset-name", fromEl.attr("data-dataset-name")]);
        }
        if (toEl.attr("data-to-modes")) {
            tempJPLocalStorage.push(["#To", "data-to-modes", toEl.attr("data-to-modes")]);
        }
        if (toEl.attr("data-dataset-name")) {
            tempJPLocalStorage.push(["#To", "data-dataset-name", toEl.attr("data-dataset-name")]);
        }
        if (viaEl.attr("data-via-modes")) {
            tempJPLocalStorage.push(["#Via", "data-via-modes", viaEl.attr("data-via-modes")]);
        }
        if (viaEl.attr("data-dataset-name")) {
            tempJPLocalStorage.push(["#Via", "data-dataset-name", viaEl.attr("data-dataset-name")]);
        }
        if (tempJPLocalStorage.length > 0) {
            tfl.storage.set("tempJPLocalStorage", tempJPLocalStorage);
        }

        if (fromEl.val() !== tfl.dictionary.CurrentLocationText) {
            $("#FromGeolocation").val("");
        }
        if (toEl.val() !== tfl.dictionary.CurrentLocationText) {
            $("#ToGeolocation").val("");
        }
        if (viaEl.val() !== tfl.dictionary.CurrentLocationText) {
            $("#ViaGeolocation").val("");
        }

        return true;
    };

    o.changeTabs = function () {
        var obj = $('[data-jp-tabs="true"]');
        var isResultsPage = obj.parents('.journey-planner-results').length > 0;
        obj.children().click(function () {
            var jpType = $("#JpType");
            var oldType = jpType.val();
            var link = $(this).children("a");
            var newType = link.attr("data-jptype");
            obj.removeClass(oldType).addClass(newType);
            $("#jp-search-form").removeClass(oldType).addClass(newType);
            jpType.val(newType);
            if (isResultsPage) {
                var optionsForPt = $('#OptionsForPublictransport');
                if (jpType.val() == tfl.dictionary.JpTypeCycling || jpType.val() == tfl.dictionary.JpTypeWalking) {
                    optionsForPt.hide();
                } else {
                    optionsForPt.show();
                }
                // if it has a time it means it has a result for walking/cycling otherwise submit to search for results
                if (link.hasClass('has-time')) {
                    $('.summary-results').removeClass(oldType).addClass(newType);
                    window.tfl.journeyPlanner.results.unshowAllDetailedResults();
                    window.tfl.journeyPlanner.results.currentlyShowing = null;
                } else {
                    $("#jp-search-form").submit();
                }
                window.tfl.journeyPlanner.results.maps(newType);

                // hide show 'no public transport results found' message
                if (newType === "publictransport") {
                    $(".no-public-transport").show();
                } else {
                    $(".no-public-transport").hide();
                }
            }
        });
    };

    o.incrementCurrentTime = function () {
        if ($(".time-options.change-time").length === 0) {
            o.setCurrentTime();
        }
        setTimeout(o.incrementCurrentTime, 900000);
    };

    o.getCurrentDate = function () {
        var d = new Date();
        var yyyy = d.getFullYear().toString();
        var mm = (d.getMonth() + 1).toString();
        var dd = d.getDate().toString();
        return yyyy + (mm[1] ? mm : "0" + mm[0]) + (dd[1] ? dd : "0" + dd[0])
    };

    o.getCurrentTime = function () {
        var d = new Date();
        var mins = d.getMinutes();
        mins = mins = 0 ? mins : (mins + (15 - (mins % 15)));
        var hrs = d.getHours();

        if (mins === 60) {
            mins = 0;
            hrs++;
            if (hrs === 24) {
                hrs = 0;
            }
        }

        var minStr = "" + mins;
        if (minStr.length === 1) {
            minStr = "0" + minStr;
        }

        var hrStr = "" + hrs;
        if (hrStr.length === 1) {
            hrStr = "0" + hrStr;
        }

        return hrStr + minStr;
    };

    o.setCurrentTime = function () {
        var d = new Date();
        var mins = d.getMinutes();
        mins = (mins + (15 - (mins % 15)));
        var hrs = d.getHours();

        if (mins === 60) {
            mins = 0;
            hrs++;
            if (hrs === 24) {
                hrs = 0;
                $("#Date").val($("#Date option:selected").next().val());
                $(".date-of-departure > span").text("Tomorrow");
            }
        }

        var minStr = "" + mins;
        if (minStr.length === 1) {
            minStr = "0" + minStr;
        }

        var hrStr = "" + hrs;
        if (hrStr.length === 1) {
            hrStr = "0" + hrStr;
        }

        $("#Time").val(hrStr + minStr);
        $(".hours span").text(hrStr + ":" + minStr);
    };

    o.changeDepartureTime = function () {
        $(".time-options").toggleClass("change-time");
        return false;
    };

    o.toggleMoreOptions = function () {
        $(this).parents(".r").toggleClass("expanded");
        var link = $(".toggle-options");
        if (link.hasClass("more-options")) {
            link.removeClass("more-options").addClass("less-options").text(tfl.dictionary.LessOptions);
        } else {
            link.removeClass("less-options").addClass("more-options").text(tfl.dictionary.MoreOptions + tfl.dictionary.CustomisedOptions);
        }
        return false;
    };

    o.toggleMoreOptionsIsWidget = function () {
        var button = $(this);
        var href = button.attr('href');
        var hrefStart = href;
        if (href.indexOf('?') != -1) {
            hrefStart = href.substr(0, href.indexOf('?'))
        } else {
            hrefStart = href.substr(0, href.indexOf('#'))
        }
        //var uri = href.substr(0, href.indexOf('#'));
        var uri = "";
        var hash = href.substr(href.indexOf('#'));
        $.each(['From', 'To', 'FromGeolocation', 'ToGelocation'], function (k, v) {
            var val = $('#' + v).val();
            if (val !== undefined && val !== '') {
                if (k == 0) uri += '?';
                if (k > 0) uri += '&';
                uri += v + '=' + val;
            }
        });
        var data = [];
        var fromEl = $("#From");
        var toEl = $("#To");
        if (fromEl.attr("data-from-id")) data.push(["fromId", fromEl.attr("data-from-id")]);
        if (toEl.attr("data-to-id")) data.push(["toId", toEl.attr("data-to-id")]);
        data.push(["date", $("#Date").find("option:selected").val()]);
        data.push(["timeIs", $(".leaving-or-arriving .selected input").val()]);
        data.push(["time", $("#Time").find("option:selected").val()]);
        if (uri.indexOf("?") == -1) {
            uri += "?";
        }
        for (var i = 0; i < data.length; i++) {
            uri += "&" + data[i][0] + "=" + data[i][1];
        }
        button.attr('href', hrefStart + uri + hash);

        var tempJpLocalStorage = [
            []
        ];
        if (fromEl.attr("data-from-modes")) {
            tempJpLocalStorage.push(["#From", "data-from-modes", fromEl.attr("data-from-modes")]);
        }
        if (fromEl.attr("data-dataset-name")) {
            tempJpLocalStorage.push(["#From", "data-dataset-name", fromEl.attr("data-dataset-name")]);
        }
        if (toEl.attr("data-to-modes")) {
            tempJpLocalStorage.push(["#To", "data-to-modes", toEl.attr("data-to-modes")]);
        }
        if (toEl.attr("data-dataset-name")) {
            tempJpLocalStorage.push(["#To", "data-dataset-name", toEl.attr("data-dataset-name")]);
        }
        if (tempJpLocalStorage.length > 0) {
            tfl.storage.set("tempJPLocalStorage", tempJpLocalStorage);
        }
    };

    o.buildSwitchButton = function () {
        $(".geolocation-box").after("<a href='javascript:void(0)' class='switch-button hide-text'>Switch from and to<a>");
    };

    o.switchFromTo = function () {
        var fromEl = $("#From");
        var fromGeoEl = $("#FromGeolocation");
        var toEl = $("#To");
        var toGeoEl = $("#ToGeolocation");
        var from = fromEl.val();
        var fromId = fromEl.attr("data-from-id") ? fromEl.attr("data-from-id") : "";
        var fromModes = fromEl.attr("data-from-modes") ? fromEl.attr("data-from-modes") : "";
        var fromDatasetName = fromEl.attr("data-dataset-name") ? fromEl.attr("data-dataset-name") : "";
        var fromDataCurrentLocation = fromEl.parent().attr("data-current-location") ? fromEl.parent().attr("data-current-location") : "";
        var fromGeolocation = fromGeoEl.val();
        var to = toEl.val();
        var toId = toEl.attr("data-to-id") ? toEl.attr("data-to-id") : "";
        var toModes = toEl.attr("data-to-modes") ? toEl.attr("data-to-modes") : "";
        var toDatasetName = toEl.attr("data-dataset-name") ? toEl.attr("data-dataset-name") : "";
        var toDataCurrentLocation = toEl.parent().attr("data-current-location") ? toEl.parent().attr("data-current-location") : "";
        var toGeolocation = toGeoEl.val();
        fromEl.typeahead("val", to);
        fromEl.attr("data-from-id", toId);
        fromEl.attr("data-from-modes", toModes);
        fromEl.attr("data-dataset-name", toDatasetName);
        if (toDataCurrentLocation !== "") {
            fromEl.parent().attr("data-current-location", toDataCurrentLocation);
        } else {
            fromEl.parent().removeAttr("data-current-location");
        }
        fromEl.trigger("change");
        toEl.typeahead("val", from);
        toEl.val(from);
        toEl.attr("data-to-id", fromId);
        toEl.attr("data-to-modes", fromModes);
        toEl.attr("data-dataset-name", fromDatasetName);
        if (fromDataCurrentLocation !== "") {
            toEl.parent().attr("data-current-location", fromDataCurrentLocation);
        } else {
            toEl.parent().removeAttr("data-current-location");
        }
        toEl.trigger("change");
        fromGeoEl.val(toGeolocation);
        toGeoEl.val(fromGeolocation);
    };

    // autocomplete variables
    tfl.journeyPlanner.recentSearches.loadSearches();
    var sourceRecentSearches = tfl.autocomplete.sources.getRecentSearches(
        "journey-planner-recent-searches",
        tfl.journeyPlanner.recentSearches.searches,
        tfl.autocomplete.sources.stationsStopsSelectionCallback,
        "<div class='mode-icons'>{{#placeModes}}<span class='mode-icon {{.}}-icon'>&nbsp;</span>{{/placeModes}}</div><span class='stop-name'>{{placeName}}</span>",
        "placeName",
        tfl.journeyPlanner.recentSearches.recentSearchNumberLimit
    );
    sourceRecentSearches.typeaheadSource.removeContent = tfl.autocomplete.sources.stationsStopsRemoveContent;
    var autocompleteSources = [tfl.autocomplete.sources.getJourneyPlannerSuggestions()];
    var sourcesTurnRecentSearchesOn, sourcesTurnRecentSearchesOff;
    var sourceTurnOffRecentSearches = tfl.autocomplete.sources.getTurnOnOffRecentSearches(false, [
        { linkText: "Turn off recent journeys" }
    ]);
    var sourceTurnOnRecentSearches = tfl.autocomplete.sources.getTurnOnOffRecentSearches(true, [
        { linkText: "Turn on recent journeys" }
    ]);

    o.setupAutocomplete = function (freshPageLoad) {
        //setup sources
        sourcesTurnRecentSearchesOn = autocompleteSources.slice(0);
        sourcesTurnRecentSearchesOff = autocompleteSources.slice(0);
        o.autocompleteAddRecentSearches(!freshPageLoad);
        o.autocompleteAddGeolocation();

        sourceTurnOffRecentSearches.typeaheadSource.callback = function (inputEl) {
            tfl.journeyPlanner.recentJourneys.useJourneys(false);
            if ($("#recent-journeys").length != 0) {
                tfl.journeyPlanner.createJourneys();
            }
            $(inputEl).parent().siblings(tfl.dictionary.RemoveContentClass).trigger("clear-search-box");
            o.destroyAutocomplete();
            $("#From, #To, #Via").each(function () {
                tfl.autocomplete.setup("#" + $(this).attr("id"), sourcesTurnRecentSearchesOn, null, true);
                $("#" + $(this).attr("id")).attr("placeholder", $(this).attr("id"));
            });
        };
        sourceTurnOnRecentSearches.typeaheadSource.callback = function (inputEl) {
            tfl.journeyPlanner.recentJourneys.useJourneys(true);
            if ($("#recent-journeys").length != 0) {
                tfl.journeyPlanner.createJourneys();
            }
            $(inputEl).parent().siblings(tfl.dictionary.RemoveContentClass).trigger("clear-search-box");
            o.destroyAutocomplete();
            $("#From, #To, #Via").each(function () {
                tfl.autocomplete.setup("#" + $(this).attr("id"), sourcesTurnRecentSearchesOff, null, true);
                $("#" + $(this).attr("id")).attr("placeholder", $(this).attr("id"));
            });
        };

        if (tfl.journeyPlanner.recentJourneys.usingRecentJourneys) {
            tfl.autocomplete.setup("#From", sourcesTurnRecentSearchesOff, null, freshPageLoad);
            tfl.autocomplete.setup("#To", sourcesTurnRecentSearchesOff, null, freshPageLoad);
            tfl.autocomplete.setup("#Via", sourcesTurnRecentSearchesOff, null, freshPageLoad);
        } else {
            tfl.autocomplete.setup("#From", sourcesTurnRecentSearchesOn, null, freshPageLoad);
            tfl.autocomplete.setup("#To", sourcesTurnRecentSearchesOn, null, freshPageLoad);
            tfl.autocomplete.setup("#Via", sourcesTurnRecentSearchesOn, null, freshPageLoad);
        }
    };

    o.destroyAutocomplete = function () {
        $("#From, #To, #Via").each(function () {
            $(this).parent().siblings(tfl.dictionary.RemoveContentClass).unbind("clear-search-box");
            $(this).typeahead("destroy");
            $(this).off("typeahead:selected typeahead:autocompleted keydown.autocomplete");
        });
        sourcesTurnRecentSearchesOff = autocompleteSources.slice(0);
        sourcesTurnRecentSearchesOn = autocompleteSources.slice(0);
        o.autocompleteAddRecentSearches(true);
        o.autocompleteAddGeolocation();
    };

    o.autocompleteAddRecentSearches = function (excludeRecentSearches) {
        if (tfl.storage.isLocalStorageSupported()) {
            sourcesTurnRecentSearchesOff.splice(0, 0, sourceTurnOffRecentSearches);
            if (tfl.journeyPlanner.recentJourneys.usingRecentJourneys && !excludeRecentSearches) {
                sourcesTurnRecentSearchesOff.splice(0, 0, sourceRecentSearches);
            }
            sourcesTurnRecentSearchesOn.splice(0, 0, sourceTurnOnRecentSearches);
        }
    };

    o.autocompleteAddGeolocation = function () {
        var sourceGeolocation = tfl.autocomplete.sources.getGeolocation(null);
        if (tfl.geolocation.isGeolocationSupported()) {
            sourcesTurnRecentSearchesOff.splice(0, 0, sourceGeolocation);
            sourcesTurnRecentSearchesOn.splice(0, 0, sourceGeolocation);
        }
    };

    function inputKeydownCallbackSetup() {
        $("#From, #To, #Via").each(function () {
            $(this).on("keydown", function (e) {
                //tab (9), enter key (13), cursor keys (37-40), pg up (33), pg dwn (34), end (35), home (36), (shift,ctr,alt,caps lock 16-20), insert (45), win key (91)
                if (e.which !== 9 && e.which !== 13 && (e.which < 33 || e.which > 40) && (e.which < 16 || e.which > 20) && e.which !== 45 && e.which !== 91) {
                    //remove ID if user changes value
                    removeAttributes($(this));
                }
                ;
            });
            $(this).on('paste', function () {
                removeAttributes($(this));
            });
        });

        function removeAttributes($el) {
            $el.removeAttr("data-" + $el.attr("id").toLowerCase() + "-id");
            $el.removeAttr("data-" + $el.attr("id").toLowerCase() + "-modes");
            $el.removeAttr("data-dataset-name");
        }
    };

    o.init = function (isWidget) {
        tfl.logs.create("tfl.journeyPlanner.searchForm.init: started (widget = " + isWidget + ")");
        o.isWidget = isWidget || false;

        o.processTempLocalStorage();

        var arrivingOrLeaving = $(".leaving-or-arriving .selected label").text();

        $(".time-options").prepend("<div class='time-defaults'><p><strong>" + arrivingOrLeaving + ":</strong> <span>now</span></p><a href='javascript:void(0)' class='change-departure-time'>change time</a></div>");
        if (!String.prototype.indexOf || window.location.href.indexOf("&time=") > 0) {
            $(".change-time-options").show();
            $(".time-defaults").hide();
        }

        $(".extra-options").prepend('<a href="' + tfl.jPLandingPageUrl + '#more-options" class="toggle-options more-options">Accessibility &amp; travel options</a>');

        $("#jp-search-form").submit(o.submitSearchForm);

        tfl.journeyPlanner.recentSearches.loadSearches();

        // var jpMap = $('<div class="hidden"><div class="geolocation-map hidden"><div class="image-container"></div></div></div>');
        // $("#FromGeolocation").after(jpMap);

        $(".change-departure-time").click(o.changeDepartureTime);
        o.setupDateOptions();

        if (!o.isWidget) {
            o.changeTabs();
            $(".toggle-options").click(o.toggleMoreOptions);
        } else {
            $(".toggle-options").click(o.toggleMoreOptionsIsWidget);
        }

        if (window.location.hash === "#more-options") {
            $(".toggle-options").click();
        } else if (window.location.hash === "#change-time") {
            o.changeDepartureTime();
        }

        $("#IsAsync").val(true);

        //add autocomplete
        o.setupAutocomplete(true);
        inputKeydownCallbackSetup();

        var fromEl = $("#From"), toEl = $("#To"), viaEl = $("#Via");
        $([fromEl, toEl, viaEl]).each(function () {
            if (this.val() === tfl.dictionary.CurrentLocationText) {
                tfl.geolocation.setupInputBoxForGeolocation(this);
            }
        });

    };

}(window.tfl.journeyPlanner.searchForm = window.tfl.journeyPlanner.searchForm || {}));