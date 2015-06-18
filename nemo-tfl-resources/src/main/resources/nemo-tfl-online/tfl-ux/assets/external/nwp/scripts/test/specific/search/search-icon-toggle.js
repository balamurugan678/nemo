(function (o) {
    tfl.logs.create("tfl.search-icon-toggle: loaded");
    var searchBox = $(".search-tools input:text");
    var searchBoxContent = searchBox.val();
    searchBox.on("keyup", function () {
        o.toggleSearchIcon();
    }),
        $(".search-tools a.remove-content").click(function () {
            o.toggleSearchIcon();
        });
    o.toggleSearchIcon = function () {
        var searchBoxContent = searchBox.val();
        if (searchBoxContent.length == 0) {
            $("#search-button").show();
        }
        else {
            $("#search-button").hide();
        }
    }
    o.h1Fix = function () {
        var searchTitle = $(".search-title");
        var dots = $(".dots").length;
        if (dots == 0) {
            searchTitle.append("<span class='hero-headline dots visually-hidden'>...</span>");
        }
        searchTitle.css("display", "inline-block");
        var searchTitleWidth = searchTitle.outerWidth();
        var pageWidth = searchTitle.parent().outerWidth();
        pageWidth = pageWidth * 0.95;
        pageWidth = pageWidth - 20;
        if (searchTitleWidth > pageWidth) {
            searchTitle.css("display", "block");
            $(".dots").removeClass("visually-hidden");
        }
        else {
            searchTitle.css("display", "inline-block");
            $(".dots").addClass("visually-hidden");
        }
    }
    o.toggleSearchIcon();
    o.h1Fix();

    $(window).on('resize', function () {
        o.h1Fix();

    })


}(window.tfl.searchIcon = window.tfl.searchIcon || {}));