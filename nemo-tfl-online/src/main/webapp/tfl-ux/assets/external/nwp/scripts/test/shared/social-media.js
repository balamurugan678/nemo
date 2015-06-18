(function (o) {
    tfl.logs.create("tfl.socialMedia: loaded");
    o.init = function () {
        var wrapper = $(".share-widget-wrapper");
        if (wrapper.length > 0) {
            //move the share widget up on section pages
            var overview = $(".section-overview");
            if (overview.length > 0 && !overview.hasClass("full-width")) {
                wrapper.css({ marginTop: 0, display: "block", cssFloat: "none" });
            }
            wrapper.append('<a href="javascript:void(0)" class="share-widget clearfix"><div class="twitter-icon icon"></div><div class="facebook-icon icon"></div><div class="share-icon icon"></div><span class="share-text">Share <span class="visually-hidden">on social media</span></span><span class="down-arrow icon"></span></a>');
            var url = window.location.href;
            var title = $("h1").text();
            var list = $('<ul class="share-list"></ul>');

            list.append('<li><a href="http://www.facebook.com/share.php?u=' + url + '&t=' + title + '" class="share-facebook clearfix" tabindex="0"><span class="facebook-icon icon"></span><span class="text">Facebook</span></a></li>');
            list.append('<li><a href="http://twitter.com/home?status=' + title + ' ' + url + '" class="share-twitter clearfix" tabindex="0"><span class="twitter-icon icon"></span><span class="text">Twitter</span></a></li>');
            wrapper.append(list);
            list.find("a").focus(function () {
                list.addClass("visible-for-focus")
            }).blur(function () {
                list.removeClass("visible-for-focus");
            });
            wrapper.removeClass("hidden");
        }
    };
    o.init();
}(window.tfl.socialMedia = window.tfl.socialMedia || {}));