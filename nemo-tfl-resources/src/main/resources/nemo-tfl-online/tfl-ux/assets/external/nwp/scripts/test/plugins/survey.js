(function (o) {
    tfl.logs.create("tfl.survey: started");

    o.success = function () {
        $('#survey').removeAttr("src");
        toggleSurvey(true);
    };
    $.ajax({
        url: '/corporate/feedback/'
    }).done(function (response) {
        $('.cookie-policy-notice').after(response);
        //setSrc();
        $('.beta-banner-link').click(function () {
            setSrc();
            toggleSurvey(false);
        });
    });

    function toggleSurvey(isSuccess) {
        if ($("html.lt-ie8").length > 0) {
            $(".beta-banner-form").toggle();
            toggleLinkIcon();
        } else {
            $(".beta-banner-form").slideToggle("slow", function () {
                if (isSuccess) {
                    $('.beta-banner-submit').slideDown("slow", function () {
                        toggleLinkIcon();
                    }).delay(1000).slideUp("slow", function () {
                        toggleLinkIcon();
                    });
                }
                toggleLinkIcon();
            });
        }
    }

    function setSrc() {
        var body = $('body');
        var size = 'small';
        if (body.hasClass('breakpoint-Large')) {
            size = 'large';
        } else if (body.hasClass('breakpoint-Medium')) {
            size = 'medium';
        }
        var surveyIframe = $('#survey');
        if (surveyIframe.attr('src') === null || surveyIframe.attr('src') === undefined || surveyIframe.attr('src') === "") {
            var surveySrc = 'https://www.research.net/s/tfl_beta?layout=' + size + '&page=' + encodeURIComponent(location.href) + '&device=' + encodeURIComponent(navigator.userAgent);
            surveyIframe.attr('src', surveySrc);
        }
    }

    function toggleLinkIcon() {
        var link = $('.beta-banner-link');
        var expandedClass = 'link-accordion-expanded';
        link.toggleClass(expandedClass);
        if ($("html.lt-ie8").length > 0) {
            var ieFixEls = $(".top-row, #full-width-content");
            ieFixEls.addClass("ie7-fix");
            window.setTimeout(function () {
                ieFixEls.removeClass("ie7-fix");
            }, 5);
        }
    }
})(tfl.survey = tfl.survey || {});