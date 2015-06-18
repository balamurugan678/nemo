(function (o) {
    "use strict";
    tfl.logs.create("tfl.search.search-results-pagination: loaded");
    var numLinks = $("a.page_link");
    if (numLinks.length < 6) {
        $("a.previous_link,a.next_link,span.separator").hide();
    }
    else {
        $("a.page_link").hide();
        if ($("a.page_link").first().hasClass("selected") || $(".first_link").hasClass("selected")) {
            $(".first-link").hide();
            $(".last-link").show();
            for (var x = 0; x <= 2; x++) {
                numLinks.eq(x).show();
            }

        }
        else if ($("a.page_link").last().hasClass("selected") || $(".last_link").hasClass("selected")) {
            $(".first-link").show();
            $(".last-link").hide();
            for (var x = numLinks.length - 3; x <= numLinks.length; x++) {
                numLinks.eq(x).show();
            }
        }
        else {
            $(".first-link").show();
            $(".last-link").show();
            for (var i = 1; i < numLinks.length - 1; i++) {
                if ($(numLinks[i]).hasClass("selected")) {
                    $(numLinks[i]).show();
                    $(numLinks[i + 2]).hide();
                    $(numLinks[i + 1]).show();
                    $(numLinks[i - 1]).show();
                    $(numLinks[i - 2]).hide();
                    break;
                }
            }
        }
    }


}(window.tfl.search = window.tfl.search || {}));