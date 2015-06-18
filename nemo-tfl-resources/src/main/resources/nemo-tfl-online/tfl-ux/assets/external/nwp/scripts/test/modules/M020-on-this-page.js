(function (o) {

    var $wrapper,
        $headings;

    function addAnchor($obj, a) {
        $obj.attr('id', 'on-this-page-' + a);
    };

    function generateMarkup() {
        var i,
            markup = '<ul class="on-this-page-list">';

        for (i = 0; i < $headings.length; i += 1) {
            addAnchor($headings.eq(i), i);
            markup += '<li><a href="#on-this-page-' + i + '">' + $headings.eq(i).text() + '</a></li>';
            if (i + 1 === Math.ceil($headings.length / 2)) {
                markup += '</ul><ul class="on-this-page-list">';
            }
        }
        return markup + '</ul><div class="clearfix">&nbsp;</div>';

    }

    function init() {
        $wrapper = $('.on-this-page');
        $headings = $('h2', $wrapper);
        if ($headings.length) {
            $wrapper.prepend(generateMarkup());
        }
        var firstListLength = $("ul.on-this-page-list").first().outerHeight();
        var lastListLength = $("ul.on-this-page-list").last().outerHeight();
        if (firstListLength <= lastListLength || !$("body").hasClass("breakpoint-Medium")) {
            $("ul.on-this-page-list").last().css("margin-bottom", "0");
        }
        else {
            $("ul.on-this-page-list").last().css("margin-bottom", "20px");
        }
    };

    init();

}(window.tfl.onThisPage = window.tfl.onThisPage || {}));