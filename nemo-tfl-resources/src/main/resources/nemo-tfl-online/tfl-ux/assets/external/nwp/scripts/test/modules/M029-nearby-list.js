(function (o) {
    tfl.logs.create("tfl.nearbyList: loaded");
    var $resultsToHide;
    var $nearbyList = $(".nearby-list");
    var showMoreTemplate;

    o.maxItemsToShow = 9;
    o.maxItemsOfSingleTypeToShow = Math.max;
    o.maxItemsOfSingleTypeInARow = 2;
    o.prevItems = [];

    tfl.ajax({
        url: '/static/' + tfl.settings.version + '/templates/nearby-list-show-more.html',
        success: function (response) {
            showMoreTemplate = response;
            o.init();
        },
        dataType: "text"
    });

    o.init = function () {
        o.setupNesting();

        $resultsToHide = $(".hidden-results");
        $resultsToHide.each(function () {
            var $this = $(this);
            var $results = $this.find(".nearby-list-result");
            var $collapsed = $this.closest(".nearby-list-result");

            $results.css("visibility", "hidden");
            $collapsed.addClass("hidden-information");
            $this.after(showMoreTemplate);

            var $showAllStopsLink = $this.siblings(".show-more");
            var $showAllStopsText = $showAllStopsLink.find(".nearby-mode-details");
            $showAllStopsLink.css("marginTop", (-1 * $this.height()));

            var listResult = $this.closest("[data-filter-groups]");
            if (listResult && listResult.data("filter-groups") === "BusStations") {
                $showAllStopsText.text("Show all stops at this station");
            }

            $showAllStopsLink.click(function (e) {
                e.stopPropagation();
                e.preventDefault();
                $collapsed.toggleClass("hidden-information");
                $showAllStopsLink.toggleClass("expanded");
                if (!$showAllStopsLink.hasClass("expanded")) {
                    $results.css("visibility", "hidden");
                    $showAllStopsText.text($showAllStopsText.text().replace("Hide", "Show"));
                } else {
                    $results.css("visibility", "visible");
                    $showAllStopsText.text($showAllStopsText.text().replace("Show", "Hide"));
                }


            });
        });

        //wait for the transitions to finish before initialising the pagination
        window.setTimeout(o.initPagination, 500);
        var hidingTimeout = null;
        $(window).resize(function () {
            $resultsToHide.filter(':visible').each(function () {
                var $this = $(this);
                var $showAllStopsLink = $this.siblings(".show-more");
                $showAllStopsLink.css("marginTop", (-1 * $this.height()));
            });
            clearTimeout(hidingTimeout);
            hidingTimeout = setTimeout(
                function () {
                    var w = $nearbyList.width();
                    $resultsToHide.filter(':not(":visible")').each(function () {
                        var $this = $(this);
                        var $showAllStopsLink = $this.siblings(".show-more");
                        var $par = $this.closest('.nearby-list-result');
                        var originalStyle = $par.attr('style');
                        $par.removeAttr('style').css({
                            position: 'fixed',
                            left: '-10000px',
                            display: 'block',
                            width: w
                        });
                        $showAllStopsLink.css("marginTop", (-1 * $this.height()));
                        $par.removeAttr('style').attr('style', originalStyle);
                    });
                }, 300);


        })
    }

    o.initPagination = function () {
        var children = $nearbyList.children(".nearby-list-result:visible").length;
        if (children > tfl.nearbyList.maxItemsToShow) {
            tfl.navigation.pagination.setup($nearbyList.parent(), ".nearby-list", tfl.nearbyList.maxItemsToShow, children);
            $nearbyList.next('.pagination-controls').find('a.next_link').attr('data-jumpto', 'ul.footer-links > li:first a');
        }
    };

    o.setupNesting = function () {
        var itemsInList = 0;
        var collapsed = false;
        for (var i = 0; i < o.maxItemsOfSingleTypeInARow; i++) {
            o.prevItems.push("");
        }

        var $filterList = $('<ul class="links-list" data-selected-item-id="Show me more..." data-dropdown-target="modes-dropdown-placeholder" data-dropdown-option="tab-dropdown"></ul>');
        var $container = null;

        $filterList.append('<li data-item-id="Show me more..."><span>Show me more...</span></li>');

        var $nearbyItems = $nearbyList.children("li");
        var itemCount = 0;
        var $prevItem = null;

        if ($nearbyItems.length < o.maxItemsToShow) {
            // if the total number of items is less than the max page size, don't group items
            return;
        }

        $nearbyItems.each(function () {
            var $this = $(this);
            var groups = $this.data("filter-groups").split(",");
            $(groups).each(function () {
                var groupName = "" + this;
                //groupName should never be empty unless something has broken, but if
                //it is, handle it so that the JS doesn't crash.
                if (groups.length > 1 || (groups.length === 1 && groupName === "")) {
                    o.prevItems[itemCount % o.maxItemsOfSingleTypeInARow] = "Multi";
                    collapsed = false;
                    itemsInList++;
                } else {
                    //check if we need to start collapsing items
                    if (!collapsed) {
                        var sameType = true;
                        for (var i = 0; i < o.maxItemsOfSingleTypeInARow; i++) {
                            if (o.prevItems[i] !== groupName) {
                                sameType = false;
                                break;
                            }
                        }
                        //start collapsing
                        if (sameType) {
                            //build the collapsed container
                            var $containerWrapper = $("<div class='expandable-information'></div>");
                            $container = $("<ul class='hidden-results'></ul>");
                            $containerWrapper.append($container);
                            $prevItem.children(".expanded-result-details").append($containerWrapper);
                            $container.append($this);
                            collapsed = true;
                            $prevItem.addClass("hidden-information");
                        } else {
                            itemsInList++;
                        }
                        //we're already collapsing. do we need to keep adding to the collapsed list?
                    } else if (o.prevItems[0] === groupName) {
                        $container.append($this);
                        //we're going to stop collapsing
                    } else {
                        collapsed = false;
                        itemsInList++;
                    }
                    o.prevItems[itemCount % o.maxItemsOfSingleTypeInARow] = groupName;
                }
            });
            itemCount++;
            $prevItem = $this;
        });
    };

}(window.tfl.nearbyList = window.tfl.nearbyList || {}));