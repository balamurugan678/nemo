(function (o) {
    "use strict";
    tfl.logs.create("tfl.autocomplete: loaded");
    o.ClearSearchBox = 'clear-search-box';

    var defaultTypeaheadSettings = {
        highlight: true,
        hint: false,
        minLength: 0
    };

    o.setup = function (inputEl, dataSources, typeaheadSettings, binding) {
        //no autocomplete for IE7 - it's too buggy
        if ($("html").hasClass("lt-ie8")) {
            return;
        }

        tfl.logs.create("tfl.autocomplete.setup: started");

        var $el = $(inputEl);

        if (typeaheadSettings === null) {
            typeaheadSettings = defaultTypeaheadSettings;
        }

        var setupSources = [];

        //for (var a = 0; a < dataSources.length; a++) {
        $(dataSources).each(function () {
            if (typeof this === "function") return;
            var typeaheadSource = this.typeaheadSource;
            var dataEngine = this.dataEngine;

            // modify replace and filter to add/remove throbber to indication data retrieval.
            if (dataEngine.remote && dataEngine.remote["filter"] && dataEngine.remote["replace"]) {
                var originalReplace = dataEngine.remote["replace"];
                var replace = function (url, uriEncodedQuery) {
                    $el.parent(".twitter-typeahead").addClass("downloading");
                    return originalReplace(url, uriEncodedQuery);
                };
                dataEngine.remote["replace"] = replace;
                var originalFilter = dataEngine.remote["filter"];
                var filter = function (response) {
                    var origResponse = originalFilter(response);
                    $el.parent(".twitter-typeahead").removeClass("downloading");
                    return origResponse;
                };
                dataEngine.remote["filter"] = filter;
            }

            // initialize each bloodhound source and add to source
            dataEngine.initialize();
            typeaheadSource.source = dataEngine.ttAdapter();
            setupSources.push(typeaheadSource);
        });

        $el.typeahead(typeaheadSettings, setupSources)
            .on("keydown.autocomplete", function (e) { //only relevant to where item from a dataset has already been selected
                var dataset = $el.attr("data-dataset-name");
                if (dataset) {
                    for (var i = 0; i < setupSources.length; i++) {
                        var dataSource = setupSources[i];
                        if (dataset === dataSource.name) {
                            if (dataSource.keydown) {
                                dataSource.keydown(e, inputEl);
                            }
                            break;
                        }
                    }
                }
            }).on("typeahead:selected typeahead:autocompleted", function (e, datum, dataset) {
                $el.trigger("focus"); // to add remove content cross to text box
                if (dataset) {
                    for (var i = 0; i < setupSources.length; i++) {
                        var dataSource = setupSources[i];
                        if (dataset === dataSource.name) {
                            if (dataSource.callback) {
                                dataSource.callback(inputEl, datum, dataset);
                            }
                            if (dataSource.contextCallback) {
                                dataSource.contextCallback(inputEl, datum, dataset);
                            }
                            break;
                        }
                    }
                    $el.attr("data-dataset-name", dataset);
                }
            }).parent().siblings(tfl.dictionary.RemoveContentClass).bind(o.ClearSearchBox, function () {
                $(this).click();
            });

        //bind 'remove-content' click to input element.
        if (binding) {
            $el.parent().siblings(tfl.dictionary.RemoveContentClass).click(function () {
                var dataset = $el.attr("data-dataset-name");
                if (dataset) {
                    for (var i = 0; i < setupSources.length; i++) {
                        var dataSource = setupSources[i];
                        if (dataset === dataSource.name) {
                            if (dataSource.removeContent) {
                                dataSource.removeContent($el);
                            }
                            break;
                        }
                    }
                    $el.removeAttr("data-dataset-name");
                }
                $el.typeahead("val", "");
            });
        }
        ;
    };// end of setup function

    o.tokenize = function (datum, name) {
        var charsThatNeedTokens = ["'", "(", "&", "-", ")"];
        var datumNameSplit = name != null ? datum[name].trim().split(" ") : datum.trim().split(" ");
        var datumTokens = name != null ? datum[name].trim().split(" ") : datum.trim().split(" ");
        var needsTokens = false;
        for (var b = 0; b < datumNameSplit.length; b++) {
            var datumNamePiece = datumNameSplit[b];
            // add extra token for e.g. "st" and "&"
            switch (datumNamePiece.toLowerCase()) {
                case "st":
                    datumTokens.push("st.");
                    needsTokens = true;
                    break;
                case "st.":
                    datumTokens.push("st");
                    needsTokens = true;
                    break;
                case "&":
                    datumTokens.push("and");
                    needsTokens = true;
                    break;
                case "and":
                    datumTokens.push("&");
                    needsTokens = true;
                    break;
            }
            for (var c = 0; c < charsThatNeedTokens.length; c++) {
                var tokenChar = charsThatNeedTokens[c];
                if (datumNamePiece.indexOf(tokenChar) !== -1) {
                    datumTokens.push.apply(datumTokens, datumNamePiece.trim().split(tokenChar));
                    needsTokens = true;
                }
            }
        }
        if (needsTokens && datumTokens.length > 0) {
            // clean up datumTokens
            var cleanTokens = [];
            for (var d = 0; d < datumTokens.length; d++) {
                var addToken = true;
                var datumToken = datumTokens[d];
                // join "s" onto datumToken if next datumToken is "s" (e.g. king's > ["king","s"]
                if (datumTokens.length >= d + 1 && datumTokens[d + 1] === "s") {
                    datumToken += "s";
                }
                // check dataToken has not already been added
                for (var e = 0; e < cleanTokens.length; e++) {
                    if (datumToken === cleanTokens[e]) {
                        addToken = false;
                        break;
                    }
                }
                if (datumToken !== "s" && datumToken !== "" && addToken) {
                    cleanTokens.push(datumToken);
                }
            }
            if (cleanTokens.length > 0) {
                datum.tokens = cleanTokens;
            }
        }
        return datum;
    };

})(window.tfl.autocomplete = window.tfl.autocomplete || {});