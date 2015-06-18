(function (o) {
    "use strict";
    tfl.logs.create("tfl.journeyPlanner.results: loaded");

    o.init = function (loadedViaAjax) {
        tfl.logs.create("tfl.journeyPlanner.results.init: started (isAjax: " + loadedViaAjax + ")");
        //if we haven't ajax loaded, we'll need to initalise the search form
        if (!loadedViaAjax) {
            o.resultsFormInit();
            o.processResults();
            o.initSteal();
        } else {
            o.initAjaxPage();
        }
        if ($("html").hasClass("lt-ie8")) {
            o.initIE7();
        }
    };

    o.initGlobal = function () {
        tfl.logs.create("tfl.journeyPlanner.initGlobal: started");
        //stop duplication of events by unbinding before binding
        tfl.expandableBox.init();
    };

    o.initAjaxPage = function () {
        tfl.logs.create("tfl.journeyPlanner.results.initAjaxPage: started");
        var loaderType = $('#JpType').val() == '' ? 'publictransport' : $('#JpType').val();
        o.setupLoader(loaderType);
        o.resultsFormInit();
        var url = window.location.href, resultsUrl = tfl.jPResultsPageUrl;
        url = url.substring(url.indexOf('?'), url.length);
        //var dataArray = url.split('&');
        //var data =  {};
        //for (var i = 0; i < dataArray.length; i++) {
        //    var spl = dataArray[i].split('=');
        //    if (!data[spl[0]]) {
        //        data[spl[0]] = [];
        //    }
        //    data[spl[0]].push(spl[1]);
        //}
        //data.ispostback = true;

        var ajaxSuccess = function (response) {
            data = $(response).find('.ajax-response');
            $(".journey-details-ajax").replaceWith($(data));

            // populate walk tab with time
            var journeyTimeWalking = $('.walking-box .journey-time');
            if (journeyTimeWalking.length == 1 && $('[data-jp-tabs="true"] .walking.selected').length < 1) {
                journeyTimeWalking.find('.visually-hidden').remove();
                journeyTimeWalking = $('<span></span>').addClass('tabs-time').text('Walk this in ' + journeyTimeWalking.text());
                $('[data-jp-tabs="true"] [data-jptype="walking"]').addClass('has-time').append(journeyTimeWalking);
            }

            // populate cycle tab with time
            var journeyTimeCycle = $('.cycling-box .journey-time');
            if (journeyTimeCycle.length == 1 && $('[data-jp-tabs="true"] .cycling.selected').length < 1) {
                journeyTimeCycle.find('.visually-hidden').remove();
                journeyTimeCycle = $('<span></span>').addClass('tabs-time').text('Cycle in ' + journeyTimeCycle.text());
                $('[data-jp-tabs="true"] [data-jptype="cycling"]').addClass('has-time').append(journeyTimeCycle);
            }

            o.processResults();
            o.initGlobal();
            if ($(".disambiguation-form").length > 0) {
                tfl.journeyPlanner.disambiguation.init();
            } else {
                $('.summary-results > .expandable-box').css({ opacity: 0 }).each(function (i) {
                    $(this).delay(150 * i).animate({ opacity: 1 }, 100, function () {
                        $(this).removeAttr("style");
                    });
                });
            }
            o.initSteal();
            $("#alternatives").find("[data-pull-content]").click(tfl.navigation.pullContentHandler);
        };
        tfl.ajax({
            url: resultsUrl + url + "&ispostback=true",
            success: ajaxSuccess,
            dataType: 'html'
        });
        //tfl.ajax({
        //    url: resultsUrl,
        //    data: data,
        //    success: ajaxSuccess,
        //    dataType: 'html'
        //});
    };

    o.initSteal = function () {
        // we have to use the steal.js script loader to make sure the mapping code doesn't run until the mapping framework has loaded
        steal(tfl.mapScriptPath)
            .then(function () {
                tfl.logs.create("tfl.journeyPlanner.results.initSteal: started");
                var mapLoaded = false;
                var legMapElement = null;
                var legMap = null;

                function fullScreenLegMap() {
                    tfl.fullscreen.display(legMapElement, false, true);
                    legMapElement.controller().divResized();
                }

                $(".view-on-a-map").click(function (event, data) {
                    var $this = $(this);
                    $(".view-on-a-map").not(this).removeClass("show-all");
                    $this.toggleClass("show-all");
                    var mapLinks;
                    if (data && data.isHideAll) {
                        mapLinks = $(".view-on-a-map.hide-map");
                    } else {
                        mapLinks = $(".view-on-a-map.hide-map").not(this);
                    }
                    mapLinks.text("View on a map").removeClass("hide-map");
                    if ($this.hasClass("hide-map")) {
                        $this.text("View on a map").removeClass("hide-map");
                        legMapElement.hide();
                    } else {
                        if (!mapLoaded) {
                            $this.after("<div id='leg_map' class='leg-map'></div>");
                            $.fixture.on = false; // TODO really need to be able to take this out
                            legMapElement = $('#leg_map').tfl_maps_journey_planner_leg_map({
                                url: $(".journey-results").data("api-uri"),
                                isNationalBounds: tfl.getQueryParam('NationalSearch') === 'true',
                                mapDeactivated: function () {
                                    if (!$(document.body).hasClass("breakpoint-Medium")) {
                                        tfl.fullscreen.hide();
                                    }
                                    legMapElement.siblings(".view-on-a-map").trigger('click');
                                }
                            });
                            legMap = legMapElement.controller();
                            legMap.mapLoading.done(function () {
                                $(window).on("enterBreakpointSmall exitBreakpointMedium", function () {
                                    if (legMapElement.is(":visible")) {
                                        fullScreenLegMap();
                                    }
                                }).on("enterBreakpointMedium", function () {
                                    tfl.fullscreen.hide();
                                });
                                if (!$(document.body).hasClass("breakpoint-Medium")) {
                                    fullScreenLegMap();
                                }
                            });
                            mapLoaded = true;

                        } else {
                            legMapElement.show();
                            legMapElement.insertAfter($(this));
                            if (!$(document.body).hasClass("breakpoint-Medium")) {
                                fullScreenLegMap();
                            }
                        }
                        legMap.chooseLeg($this.data("journeyindex"), $this.data("legindex"));
                        $this.text("Hide map").addClass("hide-map");
                    }
                    event.preventDefault();
                });
            });
    };

    //Initialise for IE7, which has rendering problems when the page
    //height changes (triggered by editing the journey form)
    o.initIE7 = function () {
        $(".cancel-button, .toggle-options, .edit-button").click(function () {
            var row = $(".journey-form").parents(".r").next();
            row.attr("class", row.attr("class"));
        });
    };

    o.maps = function (type) {
        var cyclingType = 'cycling';
        var walkingType = 'walking';
        if (type == cyclingType || type == walkingType) {
            var result = (type == cyclingType) ? $('.summary-results.cycling .cycling-box') : $('.summary-results.walking .walking-box');
            if (result.length == 1) {
                // it's cycling or walking and has only one result, so expand the options and map
                result.find('.always-visible .show-detailed-results').click();
                o.triggerMapLink($('.full-results-container .view-on-a-map'));
            }
        }
    };

    o.triggerMapLink = function (el) {
        tfl.logs.create("tfl.journeyPlanner.results: trigger click on map link");
        el.trigger('click', { isHideAll: true });
    };

    o.setupLoader = function (loaderClass) {
        var bg = $('<div id="loader-background"></div>');
        var bird = $('<div id="loader-birds"></div>');
        var trees = $('<div id="loader-trees"></div>');
        var tSegment = $('<div class="tree-segment"></div>');
        var i;
        for (i = 0; i < 8; i++) {
            trees.append(tSegment.clone());
        }
        var transport = $('<div id="loader-transport-method"></div>');
        var grass = $('<div id="loader-grass"></div>');
        var gSegment = $('<div class="grass-segment"></div>');
        for (i = 0; i < 18; i++) {
            grass.append(gSegment.clone());
        }
        var message = $('<div id="loader-message">Fetching results</div>');
        $("#loader-window").addClass(loaderClass).append(bg).append(trees).append(bird).append(transport).append(grass).append(message);
    };

    o.processResults = function () {
        $(".summary-results > .expandable-box > .content > .always-visible").click(function () {
            if ($(this).parents(".not-selected").length > 0) {
                o.unshowAllDetailedResults();
                o.currentlyShowing = null;
            }
        });
        var summaryResults = $('.summary-results');
        if (summaryResults.hasClass('cycling')) {
            o.maps('cycling');
        } else if (summaryResults.hasClass('walking')) {
            o.maps('walking');
        }

        $(".always-visible .journey-price").after("<button class='primary-button show-detailed-results' data-jumpto=''>" + tfl.dictionary.ShowDetailedView + "</button>");
        $(".back-to-link").click(o.unshowAllDetailedResults);
        var i = 1;
        if ($(".journey-details").length > 0) {
            if (tfl.journeyPlanner.recentJourneys.usingRecentJourneys) {
                tfl.journeyPlanner.recentJourneys.saveJourney($("#From").attr("data-from-id"), $("#From").attr("data-from-modes"), $("#To").attr("data-to-id"), $("#To").attr("data-to-modes"));
                tfl.journeyPlanner.recentSearches.saveSearch($("#From").val(), $("#From").attr("data-from-id"), $("#From").attr("data-from-modes"));
                tfl.journeyPlanner.recentSearches.saveSearch($("#To").val(), $("#To").attr("data-to-id"), $("#To").attr("data-to-modes"));
                if ($("#Via").val() !== "") {
                    tfl.journeyPlanner.recentSearches.saveSearch($("#Via").val(), $("#Via").attr("data-via-id"), $("#Via").attr("data-via-modes"));
                }
            }
        }
        $(".journey-details").each(o.generateJourneySummary);

        //status-disruption click events
        var statusMsgs = $(".journey-steps .message-toggle");
        var statusMsgsDetailedView = $(".journey-detail-step .line-status .message-toggle");
        var el;
        for (var j = 0; j < statusMsgs.length; j++) {
            el = $(statusMsgs[j]);
            (function () {
                var id = j;
                el.click(function () {
                    if (!$(statusMsgsDetailedView[id]).parent().parent().hasClass("expanded")) {
                        $(statusMsgsDetailedView[id]).click();
                    }
                    $(this).parents(".summary").find(".show-detailed-results").click();
                });
            })();
        }
        $(".line-status-info .start-hidden");
        var hideLinks = $(".line-status-info .hide-link");
        for (var k = 0; k < statusMsgs.length; k++) {
            el = $(hideLinks[k]);
            (function () {
                var id = k;
                el.click(function () {
                    $(statusMsgsDetailedView[id]).click();
                });
            })();
        }

        $(".show-detailed-results").click(o.viewDetailsClick);
        o.expandableBoxes = $(".summary-results > .expandable-box");
        $(".json-all-stops").click(o.showAllStops);
        $(".text-instructions-link").click(o.showTextInstructions);
        //add show/hide text to disruptions info
        $(".disruption-heading").append('<span class="link-style show-details">Show details...</span><span class="link-style hide-details">Hide details...</span>');
        $(".accessibility-details").append('<div class="show-hide-links"><a href="javascript:void(0)" class="show-link">Show details...</a><a href="javascript:void(0)" class="hide-link">Hide details...</a></div>');
        $(".show-hide-links a").click(function (e) {
            $(".accessibility-details").toggleClass("expanded");
            e.preventDefault();
        });
        $(".journey-detail-step.terminus").after("<div class='jp-print-button clearfix'><a href='javascript:window.print()' class='secondary-button print-button'>Print this route</a></div>");
    };

    o.generateJourneySummary = function () {
        var summary = $("<table></table>")
            .attr("class", "journey-steps")
            .append("<caption class='visually-hidden'>Steps for journey option " + i + "</caption><thead><tr><th>Time</th><th>Mode of Transport</th><th>Instructions</th></tr></thead>");

        $(this).find(".journey-detail-step:not(.terminus)").each(function (i) {
            var description = "";
            var mode = $(this).find(".step-heading .time-and-mode .centred").html();
            var row = $("<tr></tr>");
            var isDisrupted = false;
            if ($(this).find(".details .instructions .line-status").length > 0) {
                isDisrupted = true;
            }
            var className = "time";
            if ($(this).find(".duration").text().indexOf("hour") > 0) {
                className = className + " wide";
            }
            row.append("<td class='" + className + "'><strong>" + $(this).find(".duration").text().replace("minute", "min").replace("hour", "hr") + "</strong></td>");
            var logo = isDisrupted ? $("<td class='logo disrupted'></td>") : $("<td class='logo'></td>");
            logo.append($(this).find(".time-and-mode .centred").clone());
            row.append(logo);

            if (isDisrupted) {
                row.append("<td class='description disrupted'>" + $(this).find(".step-summary").html() + "</td>");
            } else {
                row.append("<td class='description'>" + $(this).find(".step-summary").html() + "</td>");
            }

            summary.append(row);
            //get status info
            if ($(this).find(".line-status").length > 0) {
                var td = $("<td class='disrupted'></td>").append($(this).find(".disruption-messages .line-status .message-toggle").eq(0).clone());
                summary.append($("<tr><td class='time'>&nbsp;</td><td class='logo disrupted'><div class='disruption-icon centred hide-text'>Disruption</div></td></tr>").append(td));
            }
        });

        summary.insertBefore(this);
        summary.after($(this).parents(".content").find(".price-and-details").clone());
        i++;
    };

    o.viewDetailsClick = function (e) {
        e.stopPropagation();
        var idx = 0;
        idx = $.inArray($(this).parents(".expandable-box").get(0), o.expandableBoxes);
        o.unshowAllDetailedResults();
        tfl.logs.create("tfl.journeyPlanner.results: view details clicked: (index: " + idx + ", currentlyshowing: " + o.currentlyShowing + ")");
        if (idx != o.currentlyShowing) {
            o.showDetailedResults(idx);
        } else {
            o.currentlyShowing = null;
        }
        return false;
    };

    o.showDetailedResults = function (idx) {
        tfl.logs.create("tfl.journeyPlanner.results: Show detailed results. Index: " + idx);
        var expandableBoxes = o.expandableBoxes;
        o.currentlyShowing = idx;
        expandableBoxes.removeClass("selected").addClass("not-selected");
        var parentBox = $(expandableBoxes[idx]);
        var nextParentBox = $(expandableBoxes[idx + 1]);
        var lastParentBox = $(expandableBoxes[expandableBoxes.length - 1]);
        $(lastParentBox).find(".show-detailed-results").attr("data-jumpto", ".earlier:tabbable:first");
        $(lastParentBox).find(".show-detailed-results").addClass("last-show-details");
        parentBox.removeClass("not-selected").addClass("selected");
        var link = parentBox.find(".show-detailed-results:visible");
        $(".show-detailed-results").text(tfl.dictionary.ShowDetailedView);
        $(parentBox).find(".show-detailed-results").text(tfl.dictionary.HideDetailedView);
        $(parentBox).find(".show-detailed-results").addClass("hide-details");
        $(nextParentBox).find(".show-detailed-results").addClass("next-show-details");
        $(parentBox).find(".show-detailed-results").attr("data-jumpto", ".journey-details :tabbable:first:visible");
        $(parentBox).find(".show-detailed-results").removeClass("last-show-details");
        $(expandableBoxes[o.currentlyShowing]).children(".content").children(".start-hidden").appendTo(".full-results-container");
        $(".replan-route").remove();
        $(".full-results-container").append("<div class='replan-route'><a href='javascript:void(0)' class='replan-from-current-location' data-jumpto='.next-show-details:visible'>Replan route from current location</a></div>");
        $(".replan-from-current-location").click(function () {
            tfl.geolocation.geolocateMe("#From", function () {
                var el = $("#From");
                el.removeAttr("data-from-id");
                el.removeAttr("data-from-modes");
                el.removeAttr("data-dataset-name");
                $('#jp-search-form').submit();
            });
            $(".secondary-button.edit-button").click();
            return false;
        });
        $(".full-results-container").removeClass("hidden").addClass($("#JpType").val());
        $(document.body).addClass("showing-full-details");
        var detailsTop = $(".full-results-container").offset().top;
        $("html, body").animate({ scrollTop: detailsTop - 10 });
        //tab focus
        $(".journey-details :tabbable:first").focus();
        //if walking or cycling and there is only one leg, click view map
        var frc = $(".full-results-container");
        var steps = frc.find('.journey-detail-step');
        //if mobile, hijack the back button here
        if (!$("html").hasClass("breakpoint-Medium")) {
            if (history.pushState) {
                var backToLink = $(".back-to-link");
                var backLinkHandler = function () {
                    window.onpopstate = null;
                    history.go(-1);
                    backToLink.off("click", this);
                };
                history.pushState({}, null);
                backToLink.on("click", backLinkHandler);
                window.onpopstate = function () {
                    o.unshowAllDetailedResults();
                    window.onpopstate = null;
                };
            }
        }
        return false;
    };

    o.unshowAllDetailedResults = function () {
        tfl.logs.create("tfl.journeyPlanner.results: Unshow all detailed results.");
        var expandableBoxes = o.expandableBoxes;
        $(".full-results-container").addClass("hidden");
        $(".full-results-container > .start-hidden").appendTo($(expandableBoxes[o.currentlyShowing]).children(".content"));
        $(".show-detailed-results").text(tfl.dictionary.ShowDetailedView);
        $(".show-detailed-results").removeClass("hide-details");
        $(".show-detailed-results").removeClass("next-show-details");
        $(".show-detailed-results").attr("data-jumpto", "");
        expandableBoxes.removeClass("not-selected selected");
        $(document.body).removeClass("showing-full-details");

        $(".json-all-stops").text(tfl.dictionary.ShowAllStops);
        $(".json-all-stops").parent().siblings(".all-stops").hide();

        $(".text-instructions-link").text(tfl.dictionary.ShowTextInstructions).removeClass('hide');
        $('.details .text-instructions-list').hide();

        return false;
    };

    o.showAllStops = function () {
        var linkClicked = this;
        if ($(linkClicked).text() == tfl.dictionary.ShowAllStops) {
            $(linkClicked).text(tfl.dictionary.HideAllStops);
            $(linkClicked).parent().siblings(".all-stops").show();
        } else {
            $(linkClicked).text(tfl.dictionary.ShowAllStops);
            $(linkClicked).parent().siblings(".all-stops").hide();
        }

        $(linkClicked).toggleClass("hide");
        return false;
    };

    o.showTextInstructions = function () {
        var linkClicked = $(this);
        var els = linkClicked.parent().siblings(".text-instructions-list");
        if (linkClicked.text() == tfl.dictionary.ShowTextInstructions) {
            linkClicked.text(tfl.dictionary.HideTextInstructions);
            els.show();
        } else {
            linkClicked.text(tfl.dictionary.ShowTextInstructions);
            els.hide();
        }

        linkClicked.toggleClass("hide");
        return false;
    };

    o.resultsFormInit = function () {
        tfl.logs.create("tfl.journeyPlanner.results: resultsFormInit started");
        $(".plan-journey-button").wrap("<div class='clearfix update-buttons' />").before("<input type='button' class='secondary-button cancel-button' value='Cancel' />");
        $(".plan-journey-button").val("Update journey").submit(function () {
            $(".cancel-button").remove();
        });
        $(".plan-journey-button-second").val("Update journey");
        tfl.journeyPlanner.searchForm.buildSwitchButton();
        $(".switch-button").click(tfl.journeyPlanner.searchForm.switchFromTo);
        var isTouch = tfl.utils.isTouchDevice();
        if ($("#From").val() !== "" && $("#To").val() !== "") {
            var touchClass = "";
            if (isTouch) {
                touchClass = " touch";
            }
            var journeyResultSummary = $("<div class='journey-result-summary" + touchClass + "' ><div class='from-to-wrapper'></div></div>");
            journeyResultSummary.children(".from-to-wrapper").append("<div class='summary-row clearfix'><span class='label'>From:</span><strong>" + $("#From").val() + "</strong></div>");
            journeyResultSummary.children(".from-to-wrapper").append("<div class='summary-row clearfix'><span class='label'>To:</span><strong>" + $("#To").val() + "</strong></div>");
            var leavingOrArriving = $(".leaving-or-arriving li.selected").text().replace(/^\s+|\s+$/g, '');
            var date = $("#Date").val();
            if (date !== null) {
                var month = parseInt(date.substring(4, 6), 10);
                month--;
                date = new Date(parseInt(date.substring(0, 4), 10), month, parseInt(date.substring(6, 8), 10));
            } else {
                date = new Date();
            }
            var months = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
            var days = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];
            var sup = "<sup>th</sup>";

            var day = date.getDate();
            var mod = day % 10;
            if (mod === 1 && day !== 11) {
                sup = "<sup>st</sup>";
            } else if (mod === 2 && day !== 12) {
                sup = "<sup>nd</sup>";
            } else if (mod === 3 && day !== 13) {
                sup = "<sup>rd</sup>";
            }
            //var timeText = days[date.getDay()] + ", " + months[date.getMonth()] + " " + date.getDate() + sup;
            var timeText = days[date.getDay()] + " " + date.getDate() + sup + " " + months[date.getMonth()];
            timeText += ", " + $("#Time option:selected").text();

            var travelPrefsText = "";
            var via = $("#Via").val();
            if (via !== "") {
                travelPrefsText += "<strong class='via-destination'>Via " + via + "</strong> ";
            }

            // cycling
            var ticked = $('.jp-mode-cycling .accessibility-options .ticked label').html();
            travelPrefsText += '<strong class="travelpreferences-cycling">' + ticked + '</strong>';

            // walking
            var optionSelected = $('#WalkingSpeed :selected').text();
            travelPrefsText += '<strong class="travelpreferences-walking">' + optionSelected + ' walking speed</strong>';

            // public transport
            travelPrefsText += '<strong class="travelpreferences-publictransport">Showing ' + $("#JourneyPreference option:selected").text().toLowerCase() + '</strong> ';
            var using = $(".modes-of-transport option:selected");

            travelPrefsText += '<strong class="travelpreferences-publictransport">Using ';
            var i = 0;
            if ($(".modes-of-transport option:not(:selected)").length === 0) {
                travelPrefsText += "all transport modes";
            } else if (using.length <= 4) {
                for (i = 0; i < using.length; i++) {
                    if (i > 0 && i === using.length - 1) {
                        travelPrefsText += " and ";
                    } else if (i > 0) {
                        travelPrefsText += ", ";
                    }
                    travelPrefsText += $(using[i]).text().replace(/^\s+|\s+$/g, '');
                }
            } else {
                for (i = 0; i < 3; i++) {
                    if (i > 0) {
                        travelPrefsText += ", ";
                    }
                    travelPrefsText += $(using[i]).text().replace(/^\s+|\s+$/g, '');
                }
                travelPrefsText += " and " + (using.length - 3) + " others";
            }
            travelPrefsText += "</strong> ";

            var accessibilityPref = $(".accessibility-options li.ticked input").val();
            if (accessibilityPref && accessibilityPref !== "" && accessibilityPref.toLowerCase() !== "norequirements") {
                travelPrefsText += '<strong class="travelpreferences-publictransport">Step free access required</strong> ';
            }
            travelPrefsText += '<strong class="travelpreferences-publictransport">Max walk time ' + $("#MaxWalkingMinutes").val() + " mins</strong>";

            journeyResultSummary.append("<div class='summary-row clearfix'><span class='label'>" + leavingOrArriving + ":</span><strong>" + timeText + "</strong></div>");

            var editButton = $('<button></button>').addClass('secondary-button edit-button').text('Edit').click(function (e) {
                e.preventDefault();
                $("#plan-a-journey").addClass("editing");
                //tabbing focus - #From field
                $("#plan-a-journey :tabbable:first:visible").focus();
                $('#From, #To, #Via').trigger("change");
            });

            journeyResultSummary.append($('<div></div>').addClass('summary-row').append(editButton));

            journeyResultSummary.append("<div class='travel-preferences clearfix'><div class='left-shadow' /><div class='scroller'><div><span>Travel preferences: </span>" + travelPrefsText + "</div></div><div class='right-shadow' /></div>");
            $("#plan-a-journey").append(journeyResultSummary);
        }

        if (isTouch) {
            tfl.journeyPlanner.travelPrefsInnerWidth = $(".scroller > div").get(0).scrollWidth;
            tfl.journeyPlanner.travelPrefsScrollMax = tfl.journeyPlanner.travelPrefsInnerWidth - $(".scroller").width();
            tfl.journeyPlanner.travelPrefsLeftShadow = $(".left-shadow");
            tfl.journeyPlanner.travelPrefsRightShadow = $(".right-shadow");
            $(".scroller").scroll(function () {
                var scrollLeft = $(this).scrollLeft();
                tfl.journeyPlanner.travelPrefsLeftShadow.css({ left: Math.min(0, -40 + scrollLeft) });
                tfl.journeyPlanner.travelPrefsRightShadow.css({ right: Math.min(0, tfl.journeyPlanner.travelPrefsScrollMax - scrollLeft - 40) });
            }).scroll();

            $(window).resize(function () {
                tfl.journeyPlanner.travelPrefsScrollMax = tfl.journeyPlanner.travelPrefsInnerWidth - $(".scroller").width();
                $(".scroller").scroll();
            });
        }
        $(".cancel-button").click(function () {
            //hide more options if it's displayed
            $('.toggle-options.less-options').click();

            $("#plan-a-journey").removeClass("editing").parents(".r").removeClass("expanded");

            //tabbing focus - edit button
            $(".journey-result-summary .secondary-button").focus();
            return false;
        });
    };
})(window.tfl.journeyPlanner.results = window.tfl.journeyPlanner.results || {});