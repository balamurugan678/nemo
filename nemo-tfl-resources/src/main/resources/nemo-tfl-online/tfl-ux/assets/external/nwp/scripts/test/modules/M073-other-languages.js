(function (o) {

    hideLanguages = function () {
        var button = $(".more-button");
        var totalLanguages = $(".featured-languages li").length;
        for (var x = 6; x <= totalLanguages; x++) {
            $(".featured-languages li:nth-child(" + x + ")").hide();
        }
    }

    $(".more-button").click(function () {
        var button = $(".more-button");
        var totalLanguages = $(".featured-languages li").length;
        button.toggleClass("hidden-languages")
        for (var x = 6; x <= totalLanguages; x++) {
            $(".featured-languages li:nth-child("
                + x + ")").show();
        }
        if (button.hasClass("hidden-languages")) {
            button.html("Show Less");
        }
        else {
            hideLanguages();
            button.html("Show More");
        }
    })
    hideLanguages();

}(window.tfl.otherLanguages = window.tfl.otherLanguages || {}));

