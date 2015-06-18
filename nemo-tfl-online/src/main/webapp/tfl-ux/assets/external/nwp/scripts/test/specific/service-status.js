var tfl = tfl || {};
(function (tfl) {
    "use strict";
    tfl.logs.create("tfl.serviceStatus: loaded");

    var clickLine = function ($row) {
        var $rainbowBoard = $row.closest('.rainbow-list-wrapper, .rainbow-board.roads');
        var $parent = $row.parent('.rainbow-list-item');

        if ($parent.hasClass('expandable')) {
            var lineId = $parent.data('line-id');

            if (!$parent.hasClass('expanded')) {
                $('.rainbow-list-item.expanded', $rainbowBoard).removeClass('expanded');
                $parent.addClass("expanded");
                $rainbowBoard.addClass("fade-to-black");

                if (tfl.hasOwnProperty('tubeMap')) {
                    tfl.tubeMap.zoomToLineId(lineId);
                }

                $parent.trigger('rainbow-list.expanded');

                if (lineId !== undefined && lineId !== null) {
                    window.location.hash = "line-" + lineId;
                }
            } else {
                $parent.removeClass("expanded");
                $rainbowBoard.removeClass("fade-to-black");

                if (tfl.hasOwnProperty('tubeMap')) {
                    tfl.tubeMap.resetToInitialView();
                }

                $parent.trigger('rainbow-list.collapsed');

                if (lineId !== undefined && lineId !== null) {
                    var top = window.scrollY;
                    window.location.hash = "";
                    window.scrollTo(0, top);
                }
            }
        }
    };

    tfl.serviceStatus = {

        initServiceBoardPage: function () {
            tfl.logs.create("tfl.serviceStatus: initialised");
            tfl.serviceStatus.serviceUpdatesBoard();
        },
        pageSetup: function () {
            var $stationsTab = $(".stations-tab");

            $(".lines-stations-tab li").click(function () {
                clickTab($(this));
                //tfl.serviceStatus.appendHashToDatePickerAnchors("");
            });

            $(".lines-stations-tab .lines-tab").click(function () {
                tfl.serviceStatus.appendHashToDatePickerAnchors("");
            });

            $(".lines-stations-tab .stations-tab").click(function () {
                tfl.serviceStatus.appendHashToDatePickerAnchors("#stations-status");
            });

            var clickTab = function ($tab) {
                tfl.logs.create("tfl.serviceStatus: click on tab");

                if ($tab.hasClass("selected")) {
                    return;
                }

                var $linesStatus = $(".lines-status"),
                    $stationsStatus = $(".stations-status"),
                    $linesTab = $(".lines-tab");

                if ($linesStatus.hasClass("visible")) {
                    $linesStatus.removeClass("visible");
                    $stationsStatus.addClass("visible");
                    $linesTab.removeClass("selected");
                    $stationsTab.addClass("selected");
                } else {
                    $stationsStatus.removeClass("visible");
                    $linesStatus.addClass("visible");
                    $stationsTab.removeClass("selected");
                    $linesTab.addClass("selected");
                }
            };

            //on a click through from the home-page check if a specific line has been selected and if so expand it on open.
            if (window.location.hash !== "") {
                var lineHash = window.location.hash;
                tfl.logs.create("tfl.serviceStatus: page hash is " + lineHash);
                var lineVar = lineHash.substr(6);

                if (lineVar === "stations-status") {
                    $(".lines-stations-tab .stations-tab").click();
                } else {
                    var $clickThroughLine = $('[data-line-class=' + lineVar.toLowerCase() + ']');

                    if ($clickThroughLine.length > 0) {
                        clickLine($clickThroughLine);
                    } else {
                        var $clickThroughStation = $('[data-station-class=' + lineVar.toLowerCase() + ']');

                        if ($clickThroughStation.length > 0) {
                            clickTab($stationsTab);
                            clickLine($clickThroughStation);
                        }
                    }
                }
            }
        },
        serviceUpdatesBoard: function () {
            var rainbowBoard = $('.rainbow-list-wrapper, .rainbow-board.roads');

            //close message closes its respective dropdown
            $(".close-disruption-info").click(function () {
                $(this).closest('.info-dropdown').prev().removeClass('selected');
                $(".rainbow-board").removeClass("fade-to-black");
            });
            $('.rainbow-list-content .close-message', rainbowBoard).on('click', function (e) {
                e.preventDefault();
                $(this).closest('.rainbow-list-item').removeClass('expanded');
                rainbowBoard.removeClass("fade-to-black");
            });

            $("[data-line-class], [data-station-class]").click(function (e) {
                clickLine($(this));
                e.preventDefault();
                e.stopPropagation();
            });

        },
        updateServiceBoard: function (boardId, response) {
            var hash = window.location.hash;
            var expandedLineClass;
            var expandedStationId;

            // check if a line status row has been expanded
            var $expandedLineStatus = $(".lines-status .expanded");

            if ($expandedLineStatus.length) {
                expandedLineClass = $expandedLineStatus.find(".rainbow-list-link").data("line-class");
            }

            // check if a station row has been expanded
            var $expandedStation = $(".stations-status .expanded");

            if ($expandedStation.length) {
                expandedStationId = $expandedStation.find(".rainbow-list-link").data("naptan-id");
            }

            $("#" + boardId).parent().html(response);

            var $datePicker = $(".datepicker-dropdown.dropdown-button");

            if ($datePicker.length) {
                tfl.serviceStatus.initServiceBoardPage();
                $datePicker.html(tfl.tools.getTime(new Date()));
            }

            if (hash !== undefined && hash !== null) {
                hash = hash.substr(1);
            }

            if (hash === "stations-status") {
                $(".lines-stations-tab .stations-tab").removeClass("selected");
                $(".lines-stations-tab .stations-tab").click();
            }

            if (expandedLineClass !== undefined && expandedLineClass !== null) {
                clickLine($(".lines-status").find("[data-line-class='" + expandedLineClass + "']"));
            }

            if (expandedStationId !== undefined && expandedStationId !== null) {
                clickLine($(".stations-status").find("[data-naptan-id='" + expandedStationId + "']"));
            }
        },
        ajaxServiceBoard: function (ajaxUrl, boardId) {
            tfl.ajax({
                url: ajaxUrl,
                success: function (response) {
                    tfl.serviceStatus.updateServiceBoard(boardId, response);
                },
                autoRefreshInterval: tfl.autoRefresh.ServiceBoard,
                autoRefreshId: boardId,
                dataType: 'text'
            });
        },
        initServiceBoardAutoRefresh: function (ajaxUrl, boardId) {
            var $expandableBox = $("#" + boardId).parents('.expandable-box');

            if ($expandableBox.length) {

                // status board is contained within an expandable box so we only want to auto refresh when box is expanded
                var $expandableBoxLink = $expandableBox.find(".always-visible");

                $expandableBoxLink.on("expandable-box.expanded", function () {
                    tfl.serviceStatus.ajaxServiceBoard(ajaxUrl, boardId);
                });
                $expandableBoxLink.on("expandable-box.collapsed", function () {
                    tfl.stopAjaxAutoRefresh(boardId);
                });

                // check if box is already expanded
                var $expandedBox = $expandableBox.find(".content.expanded");

                if ($expandedBox.length) {
                    tfl.serviceStatus.ajaxServiceBoard(ajaxUrl, boardId);
                }
            }
            else {
                window.setTimeout(function () {
                    tfl.serviceStatus.ajaxServiceBoard(ajaxUrl, boardId);
                }, tfl.autoRefresh.ServiceBoard);
            }
        },
        appendHashToDatePickerAnchors: function (hashValue) {
            // if there is a hash anchor on the Url, then add it to the end of the dropdown links hrefs
            if (window.location.hash !== "" || hashValue !== null) {
                $(".datepicker-dropdown a").each(function () {
                    var href = $(this).attr("href");
                    href = href.indexOf('#') !== -1 ? href.substr(0, href.indexOf('#')) : href;
                    hashValue = hashValue != null ? hashValue : window.location.hash;
                    $(this).attr("href", href + hashValue);
                });
            }
        }

    };
    tfl.serviceStatus.pageSetup();

})(tfl);