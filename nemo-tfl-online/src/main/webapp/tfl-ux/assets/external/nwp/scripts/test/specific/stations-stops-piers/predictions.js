(function (o, $) {
    "use strict";
    tfl.logs.create("tfl.predictions: loaded");

    // variable declaration
    var newItemsToShow,
        conn,
        timeoutLength,
        connectionError,
        noUpdatesError,
        initialized,
        started,
        liveBoards;

    function prediction(currentLocation, destinationName, destinationNaptanId, expectedArrival, lineId, lineName, platformName, towards, vehicleId) {
        this.currentLocation = currentLocation;
        this.destinationName = destinationName;
        this.destinationNaptanId = destinationNaptanId;
        this.expectedArrival = expectedArrival;
        this.lineId = lineId;
        this.lineName = lineName;
        this.platformName = platformName;
        this.towards = towards;
        this.vehicleId = vehicleId;
    }

    function departure(mode, vehicleId, lineName, destinationName, destinationNaptanId, expectedArrival, currentLocation, nowTimeStamp) {
        var t = this;

        t.id = ko.computed(function () {
            return vehicleId + destinationNaptanId + lineName;
        });
        t.lineName = ko.observable(lineName);
        t.vehicleId = ko.observable(vehicleId);
        t.lineClassName = ko.computed(function () {
            var lineClassName;

            if (mode === tfl.modeNameOverground) {
                lineClassName = tfl.modeNameOverground;
            } else {
                lineClassName = lineName.indexOf(" ") !== -1 ? lineName.substring(0, lineName.indexOf(" ")).toLowerCase() : lineName.toLowerCase();
            }

            return lineClassName + " live-board-route line-text";
        });
        t.destinationName = ko.observable(destinationName);
        t.destinationId = ko.observable(destinationNaptanId);
        t.timeToStationSecs = Math.round((new Date(expectedArrival) - nowTimeStamp) / 1000);
        t.timeToStation = ko.computed(function () {

            if (t.timeToStationSecs <= 30) {
                return "Due";
            }

            var mins = Math.ceil(t.timeToStationSecs / 60);

            if (mins > 1) {
                return "<span class='visually-hidden'>departing in </span>" + mins + " mins";
            }

            return "<span class='visually-hidden'>departing in </span>" + mins + " min";
        });
        t.currentLocation = ko.observable(currentLocation);
    }

    function platform(name, data, mode, lineIds, nowTimeStamp) {
        var t = this;

        t.name = ko.observable(name);
        t.departures = ko.computed(function () {
            var departures = new Array();
            var filteredPredictions = ko.utils.arrayFilter(data, function (prediction) {
                return prediction.platformName === name && lineIds.indexOf(prediction.lineId) !== -1;
            });

            ko.utils.arrayForEach(filteredPredictions, function (filteredPrediction) {
                var destinationName = mode === tfl.modeNameTube ? filteredPrediction.towards : filteredPrediction.destinationName;

                departures.push(
                    new departure(
                        mode,
                        filteredPrediction.vehicleId,
                        filteredPrediction.lineName,
                        destinationName,
                        filteredPrediction.destinationNaptanId,
                        filteredPrediction.expectedArrival,
                        filteredPrediction.currentLocation,
                        nowTimeStamp
                    )
                );
            });

            departures.sort(function (left, right) {
                return left.timeToStationSecs === right.timeToStationSecs ? 0 : (left.timeToStationSecs < right.timeToStationSecs ? -1 : 1);
            });

            return departures;
        });
    }

    function viewModel(mode, lineIds, nowTimeStamp, data) {
        var t = this;

        t.now = nowTimeStamp;
        t.predictions = ko.observableArray(data);
        t.platformNames = ko.computed(function () {
            var platforms = ko.utils.arrayMap(t.predictions(), function (prediction) {
                if (lineIds.indexOf(prediction.lineId) !== -1) {
                    return prediction.platformName;
                }
                return null;
            });
            return platforms.sort();
        });

        t.platforms = ko.computed(function () {
            var platformNames = ko.utils.arrayGetDistinctValues(t.platformNames());
            var platforms = new Array();

            ko.utils.arrayForEach(platformNames, function (platformName) {
                platforms.push(new platform(platformName, t.predictions(), mode, lineIds, t.now));
            });

            return platforms;
        });
    }

    function LiveBoard($wrapper, scope) {
        var t = this;
        t.$wrapper = $wrapper;
        t.scope = scope;
        t.naptanId = t.$wrapper.data('naptan-id');
        t.lineId = t.$wrapper.data('line-id');
        t.mode = t.$wrapper.data('mode');
        t.lineIds = (t.lineId + "").split(',');

        if (t.mode !== tfl.modeNameBus && t.lineIds.length === 1) {
            t.lineRoom = { "LineId": t.lineId, "NaptanId": t.naptanId };
        }
        else {
            t.lineRoom = { "NaptanId": t.naptanId };
        }

        t.initialSetLoaded = false;
        t.listening = false;
        t.updateTimeout = 0;
        t.predictionsViewModel;
        t.isLinkedToPlatformSelect = $wrapper.closest('.station-details').length;
        t.expandedBoards = [];

        t.updateTimeout = setTimeout(function () {
            t.displayError(noUpdatesError);
        }, timeoutLength);
        t.setupLoadMoreButton();

        // load initial set of predictions from API
        t.updateBoard(JSON.parse(t.$wrapper.find("#json-response").val()));
    }

    LiveBoard.prototype.displayError = function (error) {
        var $errors = this.$wrapper.find('.field-validation-errors');

        if (!$errors.length) {
            $errors.empty();
            this.$wrapper.find('.widget-heading').after('<div class="widget-content"><ul class="field-validation-errors"></ul></div>');
            $errors = this.$wrapper.find('.field-validation-errors');
        }

        $errors.append('<li class = "field-validation-suggestion">' + error + '</li>');
    };

    LiveBoard.prototype.removeError = function () {
        this.$wrapper.find(".field-validation-errors").parent().remove();
    };

    LiveBoard.prototype.startListening = function () {
        var t = this;

        if (!t.listening) {
            // add rooms to open connection
            t.listening = true;
            conn.server
                .addLineRooms([t.lineRoom])
                .done(function () {
                    tfl.logs.create("tfl.predictions: Invocation of addLineRooms succeeded");
                })
                .fail(function (error) {
                    // display error failure to add room
                    tfl.logs.create("tfl.predictions: Invocation of addLineRooms failed. Error: " + error);
                    t.displayError(connectionError);
                    t.listening = false;
                });
        }
    };

    LiveBoard.prototype.stopListening = function () {
        var t = this;

        if (t.listening) {
            t.listening = false;
            // clear timeout
            if (t.updateTimeout) {
                clearTimeout(t.updateTimeout);
                t.updateTimeout = 0;
            }
            // remove line rooms from open connection
            conn.server
                .removeLineRooms([t.lineRoom])
                .done(function () {
                    tfl.logs.create("tfl.predictions: Invocation of removeLineRooms succeeded");
                    t.listening = true;
                })
                .fail(function (error) {
                    tfl.logs.create("tfl.predictions: Invocation of removeLineRooms failed. Error: " + error);
                });
        }
    };

    LiveBoard.prototype.updateBoard = function (data) {
        var t = this;

        // clean + reset
        t.removeError();
        clearTimeout(t.updateTimeout);
        t.updateTimeout = 0;
        t.updateTimeout = setTimeout(function () {
            t.displayError(noUpdatesError);
        }, timeoutLength);

        // set date on board
        var now = new Date();
        t.$wrapper.find('.live-board-last-updated').html(tfl.tools.getTime(now));

        // update board with new view model
        if (data !== null || data !== undefined) {

            // find expanded boards
            t.$wrapper.find('.live-board-subboard.expanded').each(function () {
                t.expandedBoards.push($(this).data('platform-id'));
            });

            if (!t.initialSetLoaded) {
                var initialSet = [];
                for (var i = 0; i < data.length; i++) {
                    var newPrediction = data[i];
                    initialSet.push(
                        new prediction(
                            newPrediction.currentLocation,
                            newPrediction.destinationName,
                            newPrediction.destinationNaptanId,
                            newPrediction.expectedArrival,
                            newPrediction.lineId,
                            newPrediction.lineName,
                            newPrediction.platformName,
                            newPrediction.towards,
                            newPrediction.vehicleId));
                }

                t.predictionsViewModel = new viewModel(t.mode, t.lineIds, now, initialSet);

                // Bind
                ko.applyBindings(t.predictionsViewModel, t.$wrapper[0]);

                // switch out the initial board for live board
                t.$wrapper.find('.live-board-container').removeClass('hidden');

                // Show as bound
                t.initialSetLoaded = true;

            } else {
                // Just update the view model
                t.predictionsViewModel.now = now;

                if (t.mode === tfl.modeNameTube || t.mode === tfl.modeNameDlr || t.mode === tfl.modeNameOverground) {
                    // for tube, DLR and overground, each update contains the full current snapshot of departures,
                    // so clobber the predictions array
                    var firstUpdateIndex = -1;

                    // check received dataset actually contains upserts
                    for (var i = 0; i < data.length; i++) {
                        var newPrediction = data[i];

                        if (newPrediction.OperationType === 1) {
                            firstUpdateIndex = i;
                            break;
                        }
                    }

                    if (firstUpdateIndex !== -1) {
                        t.predictionsViewModel.predictions().length = 0;

                        for (var i = firstUpdateIndex; i < data.length; i++) {
                            var newPrediction = data[i];

                            if (newPrediction.OperationType === 1) {
                                // add prediction
                                t.predictionsViewModel.predictions.push(
                                    new prediction(
                                        newPrediction.CurrentLocation,
                                        newPrediction.DestinationName,
                                        newPrediction.DestinationNaptanId,
                                        newPrediction.ExpectedArrival,
                                        newPrediction.LineId,
                                        newPrediction.LineName,
                                        newPrediction.PlatformName,
                                        newPrediction.Towards,
                                        newPrediction.VehicleId));
                            }
                        }
                    }
                } else {
                    // for bus and river, treat it as a stream of individual updates, so maintain
                    // the predictions array
                    for (var i = 0; i < data.length; i++) {
                        var newPrediction = data[i];
                        var matchedPredictionIndex = -1;
                        for (var j = 0; j < t.predictionsViewModel.predictions().length; j++) {
                            var currPrediction = t.predictionsViewModel.predictions()[j];

                            if (currPrediction.vehicleId === newPrediction.VehicleId
                                && currPrediction.destinationNaptanId === newPrediction.DestinationNaptanId
                                && currPrediction.lineId === newPrediction.LineId) {
                                matchedPredictionIndex = j;
                                break;
                            }
                        }

                        if (matchedPredictionIndex > -1) {
                            // existing matching prediction found, so remove it
                            t.predictionsViewModel.predictions.splice(matchedPredictionIndex, 1);
                        }

                        if (newPrediction.OperationType === 1) {
                            // add prediction
                            t.predictionsViewModel.predictions.push(
                                new prediction(
                                    newPrediction.CurrentLocation,
                                    newPrediction.DestinationName,
                                    newPrediction.DestinationNaptanId,
                                    newPrediction.ExpectedArrival,
                                    newPrediction.LineId,
                                    newPrediction.LineName,
                                    newPrediction.PlatformName,
                                    newPrediction.Towards,
                                    newPrediction.VehicleId));
                        }
                    }

                    // remove stale predictions
                    i = t.predictionsViewModel.predictions().length;
                    while (i--) {
                        currPrediction = t.predictionsViewModel.predictions()[i];

                        if (now.getTime() - new Date(currPrediction.expectedArrival).getTime() > 60000) {
                            t.predictionsViewModel.predictions.splice(i, 1);
                        }
                    }
                }
            }

            if (t.mode === tfl.modeNameBus && tfl.busOptions) {
                tfl.busOptions.init(t.scope, true);
            }

            // if it's in the main content it can be filtered by the platform select dropdown, this re-filters the newly added board to match the selector
            if (t.isLinkedToPlatformSelect) {
                $('#route-selector .routes-list:not(".current-platform") .selected a').trigger('click');
            }

            // set up the pagination button
            t.setupLoadMoreButton();
        }
    };

    LiveBoard.prototype.setupLoadMoreButton = function () {
        var t = this;

        t.$wrapper.find('.live-board-subboard').each(function () {
            var $this = $(this);

            // if board was previously expanded expand and remove the button
            if (t.expandedBoards.indexOf($this.data('platform-id')) > -1) {
                $this.addClass('expanded');
                $this.find('.live-board-link').remove();
            } else {
                // find all the showable things + show up to 3
                var $shown = $this.find('.live-board-feed-item:not(".hidden")');
                if ($shown.length > 3) {
                    $shown.eq(2).nextAll().addClass('paginate-hidden');
                    $this.find('.live-board-link').removeClass('hidden');
                } else {
                    $this.find('.live-board-link').addClass('hidden');
                }
            }
        });

        var tabbables = $(":tabbable");
        var numMoreBtns = t.$wrapper.find('.live-board-link').length;
        var moreBtnsIndex = tabbables.index(t.$wrapper.find('.live-board-link').first());

        // click handler to show all predictions available
        t.$wrapper.find('.live-board-link').off('click').on('click', function (e) {
            e.preventDefault();
            var $this = $(this);
            $this.closest('.live-board-subboard').addClass('expanded');
            tabbables[moreBtnsIndex + numMoreBtns].focus();
            if (numMoreBtns > 1) {
                moreBtnsIndex++;
            }
            $this.remove();
        });
    };

    function start() {

        // start the connection
        $.connection.hub
            .start()
            .done(function () {
                if (conn === null) {
                    started = false;
                    // connection not successful show error on all boards. 
                    broadcastToBoards('displayError', connectionError);
                    tfl.logs.create('tfl.predictions: predictions signalr connection failed to start');
                    return;
                } else {
                    started = true;

                    // add rooms for all active live boards.
                    broadcastToBoards('startListening');
                }
            });
    }

    function init() {

        // connection initialization
        $.connection.hub.logging = true;
        //$.support.cors = true; //force cross-site scripting
        $.connection.hub.url = tfl.apiUrl + 'signalr';
        conn = $.connection.predictionsRoomHub;
        conn.client.OnConnectError = function (errMsg) {
            tfl.logs.create("tfl.predictions: Connection failed. Error: " + errMsg);
            broadcastToBoards('displayError', connectionError);
            broadcastToBoards('stopListening');
        };
        conn.client.showPredictions = function (data) {
            // if no data in array returned
            if (!data.length) {
                tfl.logs.create('tfl.predictions: predictions returned from signalr with no data');
                return false;
            }
            var naptanReturned = data[0].NaptanId;
            // if the naptan returned is not populated
            if (naptanReturned === null || naptanReturned === undefined || naptanReturned === "") {
                tfl.logs.create('tfl.predictions: predictions returned from signalr with no naptanId');
                return false;
            }
            // if we dont have a board matching the naptan returned
            if (!liveBoards.hasOwnProperty(naptanReturned)) {
                tfl.logs.create('tfl.predictions: predictions returned from signalr matching no live board');
                return false;
            }

            // Else, is okay. 
            liveBoards[naptanReturned].updateBoard(data);
        };

        // content properties
        newItemsToShow = 10;
        timeoutLength = 120000;
        connectionError = "Automatic departure updates are not currently available. Please reload the page for further departure updates.";
        noUpdatesError = "This departure information is out of date. Please try reloading the page.";

        // live board init
        liveBoards = {};

        started = false;
        initialized = true;
    }

    function broadcastToBoards(action, options) {
        for (var board in liveBoards) {
            if (liveBoards.hasOwnProperty(board)) {
                liveBoards[board][action](options);
            }
        }
    }

    o.init = function (scope) {
        tfl.logs.create("tfl.predictions.init: init called");

        if (!initialized) {
            init();
        }

        var activeNaptans = [];
        $('.live-box').each(function () {
            var $this = $(this),
                naptanId = $this.data('naptan-id');

            if (naptanId === "" || naptanId === undefined) {

                // initial predictions set not received so do not register for updates
                return;
            }

            activeNaptans.push(naptanId);

            if (!liveBoards.hasOwnProperty(naptanId) || liveBoards[naptanId].scope === scope) {

                // new registration
                if ($this.find('.live-board-container').length) {
                    if (naptanId !== "" && naptanId !== null && naptanId !== undefined) {
                        liveBoards[naptanId] = new LiveBoard($this, scope);

                        if (started) {
                            liveBoards[naptanId].startListening();
                        }
                    }
                }
            }
        });

        if (!started && activeNaptans.length) {
            start();
        }

        if (started) {
            for (var board in liveBoards) {
                if (liveBoards.hasOwnProperty(board)) {
                    // any boards that are no longer active - stop listening.
                    if (activeNaptans.indexOf(liveBoards[board].naptanId) < 0) {
                        liveBoards[board].stopListening();
                        delete liveBoards[board];
                    }
                }
            }
        }
    };

    // TODO: Remove AJAX polling code when no longer required.
    //o.boardRefreshTime = 10000;
    //o.initiallyShown = tfl.liveBoards ? tfl.liveBoards.initiallyShown : 3;

    //function predictionsBoard($el, scope) {
    //    var platforms = [];
    //    var $container = $el.parent();
    //    var $liveBox = $el;
    //    var $liveBoard = $liveBox.find(".live-board");
    //    var $platforms = $liveBoard.find(".live-board-subboard");
    //    var mode = $liveBox.data("mode");
    //    var naptanId = $liveBox.data("naptan-id");
    //    var lineId = $liveBox.data("line-id");

    //    var setupPlatforms = function () {
    //        $platforms.each(function () {
    //            platforms[$(this).data("platform-id")] = false;
    //        });

    //        setupMoreDepartureLinks()
    //    };

    //    var setupMoreDepartureLinks = function () {
    //        $platforms.each(function () {
    //            var $platform = $(this);
    //            if (platforms[$platform.data("platform-id")]) {
    //                $platform.find(".live-board-link").remove();
    //            } else {
    //                $(this).find(".live-board-feed-item").slice(o.initiallyShown).addClass("paginate-hidden");
    //                $(this).find(".live-board-link").click(function (e) {
    //                    e.preventDefault();
    //                    $platform.find(".paginate-hidden").removeClass("paginate-hidden");
    //                    platforms[$platform.data("platform-id")] = true;
    //                    $(this).remove();
    //                });
    //            }
    //        });
    //    };

    //    var refreshBoard = function () {
    //        if (mode && naptanId && lineId) {
    //            var callback = function () {
    //                $.ajax({
    //                    url: "/stops/departuresnaptanid",
    //                    data: {
    //                        mode: mode,
    //                        naptanId: naptanId,
    //                        lineIds: lineId
    //                    }
    //                }).done(function (response) {
    //                    tfl.logs.create("tfl.predictions: Ajax board response returned for " + naptanId + ", " + lineId + ", " + mode);
    //                    $container.html(response);
    //                    $liveBox = $container.find(".live-box");
    //                    $liveBoard = $liveBox.find(".live-board");
    //                    $platforms = $liveBoard.find(".live-board-subboard");
    //                    setupMoreDepartureLinks();
    //                    if (mode === tfl.modeNameBus && tfl.busOptions) {
    //                        tfl.busOptions.init(scope, true);
    //                    }

    //                    window.setTimeout(refreshBoard, o.boardRefreshTime);
    //                });
    //            };

    //            tfl.utils.requestAnimFrame(callback);
    //        }
    //    };

    //    setupPlatforms();
    //    window.setTimeout(refreshBoard, o.boardRefreshTime);
    //}

    //o.init = function (scope) {
    //    var $lb;
    //    if (scope) {
    //        $lb = $(scope).find(".live-box");
    //    } else {
    //        $lb = $(".live-box");
    //    }
    //    predictionsBoard($lb, scope);
    //};

}(window.tfl.predictions = window.tfl.predictions || {}, $));