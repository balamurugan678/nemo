(function (o) {
    "use strict";
    o.initialize = function () {
        o.setUpResultTags();
        //$(".results-list-all").find("data-pull-content").click(tfl.navigation.pullContentHandler);
        //o.setUpSelectBoxes();
        o.populateSearchBoxes();
        o.resetSelectBoxes();
        o.checkQueryString();
        o.selectBoxFiltering();
        o.selectBoxChange();
        // o.PaginateSearchFilters();
    };

    o.resetSelectBoxes = function () {
        $('.reset-filters').click(function () {
            $(".selector").children(".filter-box").each(function () {
                $(this).prop('selectedIndex', 0);
                $(this).siblings("span").text($(this).children("option:first-child").text());
                o.selectBoxFiltering();
            });
        });

    };

    o.checkQueryString = function () {
        //gets the current url
        var url = $(location).attr('href');
        //checks it for ? if there isnt any, just runs as usual if there is...
        if (url.indexOf("?") >= 0) {
            //splits url at ? 
            var urlSplit = url.split("?");
            //Splits that by & 
            var ampSplit = urlSplit[1].split("&");
            //the number of & will tell us how many filters are in the url
            var numFilters = (ampSplit.length);
            var i = 0;
            var preFilterArray = new Array();
            while (i < numFilters) {
                preFilterArray.push(ampSplit[i].replace(/%20/g, ' '));
                i++;
            }
            var filterCount = 0;
            $(".filter-box").each(function () {
                //gets this boxes name
                var filterName = $(this).attr("name");
                //for every item in the array
                for (x = 0; x < preFilterArray.length; x++) {
                    var arrayFilterValue = preFilterArray[x].split("=");
                    if (arrayFilterValue[0] == filterName) {
                        var allOptions = $(this).children().text();
                        if (allOptions.indexOf(arrayFilterValue[1]) <= 0) {
                        }
                        else {
                            $(this).siblings("span").text(arrayFilterValue[1]);
                        }
                    }
                    else {

                    }
                    o.selectBoxFiltering();
                }

            });

        }
        else {

        }
    };

    o.tagSet = function () {
        var selectedTag = $(this).text();
    };

    o.selectBoxChange = function () {
        $("select.filter-box").change(function () {
            o.selectBoxFiltering();
        });
    };

    o.setUpResultTags = function () {
        $(".results-list-all").find("data-pull-content").click(tfl.navigation.pullContentHandler);
        $(".results-list-all").hide();
        $(".results-list-all").children("#results-list").children("li.search-results").each(function () {
            //take the list of tags provided and turn them into tag links
            var tagString = $(this).children(".search-tags");
            var tagStringValue = tagString.text();
            var tagStringSplit = tagStringValue.split(",")
            var numTags = (tagStringSplit.length);
            for (var y = 0; y < numTags; y++) {
                tagString.append("<a class='result-tag'>" + tagStringSplit[y] + "</a>");
            }
            var $tmp = tagString.children().remove();
            tagString.text('').append($tmp);
        });
    };

    o.selectBoxFiltering = function () {
        $("#pagination-items").empty();
        $(".pagination").remove();
        //for each result

        $(".search-results").each(function () {
            var hide = false;
            //create an array
            var ResultTags = new Array();
            //look for all links in the result - these will be the tags - for each of these...
            $(this).find("a.result-tag").each(function () {
                //get its value
                var tag = $(this).text();
                //put it into the array
                ResultTags[ResultTags.length] = tag;
            });

            //for each selector
            $(".selector").children(".filter-box").each(function () {
                //get its selected value
                var selectedValue = $(this).siblings("span").text();
                //if the value is All then go to the next iteration
                if (selectedValue == "All") {
                    return true;
                }
                //Check the array for the value if its not in there break out
                var inArrayCheck = ($.inArray(selectedValue, ResultTags)); //returns -1 if not in array or otherwise returns the index of the element
                if (inArrayCheck == -1) {
                    hide = true;
                    //$(this).addClass("not-here-anymore");
                    return false;
                }
            });

            if (!hide) {
                $(this).clone(true).appendTo("#pagination-items")
            }

        });
        var numItems = $("#pagination-items > li").length;
        if (numItems === 0) {
            //if the field validation text is already there dont add it again
            if ($(".field-validation-information").length < 1) {
                $(".results-wrapper").append("<p class='field-validation-information' id='search-filters-no-results'>There is nothing matching your search.</p>");
            }
        }
        else {
            $("#search-filters-no-results").remove();
        }
        o.PaginateSearchFilters();
        //});
    };

    o.PaginateSearchFilters = function () {
        //check that there are rows to paginate
        var itemsPerPage = 5;
        $(".pagination-controls").remove();
        if ($("#pagination-items .search-results").length > itemsPerPage) {
            $(".results-wrapper ul").after("<div class='pagination' />");
            tfl.navigation.pagination.setup($(".results-wrapper"), "#pagination-items", itemsPerPage, $("#pagination-items .search-results").length, true);
        }
    };

    o.populateSearchBoxes = function () {
        $(".search-filters-wrapper").append("<div class='search-filter filter-by-box'><a class='reset-filters'>Reset</a><p class='heading'>Filter by:</p>");
        for (var i = 0; i <= filters.length; i++) {
            for (var categoryName in filters[i]) {
                $(".filter-by-box").append("<div class='filter-box-wrap'></div");
                $(".filter-box-wrap").eq(i).append("<label for='" + categoryName + "' class='heading'>" + categoryName + ":</label><div class='selector'><select class='filter-box' name=" + categoryName + " class='valid'><option selected='selected'>All</option></select></div>");
                for (var j = 0; j < filters[i][categoryName].length; j++) {
                    $(".filter-box-wrap").eq(i).find(".filter-box").append("<option>" + filters[i][categoryName][j] + "</option>");
                }
            }
        }
        tfl.forms.setupSelectBoxes();
    };

    $(document).ready(function () {
        tfl.logs.create("tfl.searchFilters: looking for search filters");
        var selector = $(".search-filters-wrapper");
        if (selector.length > 0) {
            tfl.logs.create("tfl.searchFilters: loading search filters");
            o.initialize();
        }
    });

})(window.tfl.searchFilters = window.tfl.searchFilters || {});