window.tfl.navigation = window.tfl.navigation || {};
(function (o) {
    "use strict";
    tfl.logs.create("tfl.navigation.pagination: loaded");


    o.setup = function (containerEl, itemClass, itemsPerPage, totNumItems, dontAddPaginationEl, mapController) {
        tfl.logs.create("tfl.navigation.pagination: setup");
        var numPages = totNumItems % itemsPerPage > 0
            ? Math.floor(totNumItems / itemsPerPage) + 1
            : Math.floor(totNumItems / itemsPerPage);
        var numPagesToDisplay = numPages > 5 ? 3 : numPages;
        if (!dontAddPaginationEl) {
            containerEl.append("<div class='pagination' />");
        }
        containerEl.pajinate({
            items_per_page: itemsPerPage,
            item_container_id: itemClass,
            nav_panel_id: '.pagination',
            num_page_links_to_display: numPagesToDisplay,
            nav_label_first: '1',
            nav_label_prev: '',
            nav_label_next: '',
            nav_label_last: numPages,
            show_first_last: true,
            show_prev_next: false,
            abort_on_small_lists: true
        });
        //customize pagination controls
        containerEl.find(".pagination").wrap("<div class='pagination-controls' />");
        containerEl.find(".previous_link").prependTo(containerEl.find(".pagination"));
        containerEl.find(".next_link").appendTo(containerEl.find(".pagination"));

        //containerEl.find(".first_link").after("<span class='separator'></span>");
        //containerEl.find(".last_link").before("<span class='separator'></span>");
        o.checkPageLinks(containerEl, numPages);
        containerEl.find(".pagination a").click(function () {
            o.checkPageLinks(containerEl, numPages);
            //scroll to top of list
            if ($(window).scrollTop() > containerEl.offset().top) {
                window.scrollTo(0, containerEl.offset().top);
            }
        });

    };

    o.addMapControls = function ($pagination, mapController) {
        var page = 1;
        if (mapController) {
            $pagination.find(".page_link").click(
                function () {
                    page = parseInt($(this).attr("data-longdesc"), 10);
                    mapController.choosePage(page);
                }
            );
            $pagination.find(".previous_link").click(
                function () {
                    mapController.choosePage(--page);
                }
            );
            $pagination.find(".next_link").click(
                function () {
                    mapController.choosePage(++page);
                }
            );
            $pagination.find(".first_link").click(
                function () {
                    page = 1;
                    mapController.choosePage(1);
                }
            );
            $pagination.find(".last_link").click(
                function () {
                    $pagination.find(".page_link.last").trigger('click');
                }
            );
        }
    }
    o.checkPageLinks = function (containerEl, numPages) {
        if (numPages < 6) {

            containerEl.find(".first_link,.last_link").hide();
        } else {

            var pageLinks = containerEl.find(".page_link");
            //first page is select - hide first page selector
            if ($(pageLinks[0]).hasClass("active_page")) {
                $(pageLinks[0]).hide();
                $(pageLinks[3]).show();
                containerEl.find(".next_link").prepend("<span>next-page</span>");
                containerEl.find(".previous_link").prepend("<span>previous-page</span>");
                containerEl.find(".first_link").addClass("active_page");
            } else if ($(pageLinks[pageLinks.length - 1]).hasClass("active_page")) {
                $(pageLinks[pageLinks.length - 1]).hide();
                $(pageLinks[pageLinks.length - 4]).show();

                containerEl.find(".last_link").addClass("active_page");
            } else {
                for (var i = 2; i < pageLinks.length - 2; i++) {
                    if ($(pageLinks[i]).hasClass("active_page")) {
                        $(pageLinks[i + 2]).hide();
                        $(pageLinks[i + 1]).show();
                        $(pageLinks[i - 1]).show();
                        $(pageLinks[i - 2]).hide();
                        break;
                    }
                }
            }
        }
    };

}(window.tfl.navigation.pagination = window.tfl.navigation.pagination || {}));