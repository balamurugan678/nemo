(function (o) {
    var imageAttr = 'data-highlight-image';
    var fileAttr = 'data-highlight-file';

    o.init = function () {
        var noBackgroundImageClass = 'no-background-image';
        var heroObjs = $('[' + imageAttr + ']');
        heroObjs.push($('[' + fileAttr + ']'));

        var containers = [];
        $(window).one("enterBreakpointMedium", function () {
            $.each(heroObjs, function (k, v) {
                var obj = $(v), imgFor = $(obj.attr('data-highlight-for'));
                containers.push(imgFor);

                if (obj.attr(imageAttr)) {
                    imgFor.attr('style', 'background-image: url(' + obj.attr(imageAttr) + ')');
                } else if (obj.attr(fileAttr)) {
                    tfl.ajax({ url: obj.attr(fileAttr), success: setImageFromSource });
                }

                function setImageFromSource(objs) {
                    var activeObjs = $.grep(objs, function (o) {
                        return o.active;
                    });
                    var selectedObj = activeObjs[Math.floor(Math.random() * (activeObjs.length))];
                    imgFor.attr('style', 'background-image: url(' + selectedObj.backgroundImage + ')');
                    var advLinkBox = imgFor.find('.advert-link-box').attr('href', selectedObj.link).show();
                    advLinkBox.find('h2').text(selectedObj.headline);
                    advLinkBox.find('p').text(selectedObj.text);
                    advLinkBox.find('img').attr('src', selectedObj.imageURL);
                }

                var newObj = obj.clone();
                newObj.find('img').each(function () {
                    $(this).remove();
                });
                imgFor.find('.content-area').append(newObj);
                obj.addClass(obj.attr('data-highlight-size'));

                var caption = obj.find(".caption");
                if (caption) {
                    if (caption.parent().hasClass("small")) {
                        var contentArea = imgFor.find(".content-area.medium-large");
                        contentArea.addClass("no-caption");
                        var heroHeight = Math.max(415, imgFor.find(".hero-row").height());
                        contentArea.find(".advert-tile").css({ height: heroHeight + "px" });
                    }
                }

            });
        });
        $(window).bind("exitBreakpointMedium", function () {
            $.each(containers, function (k, v) {
                $(v).addClass(noBackgroundImageClass);
            });
        });
        $(window).bind("enterBreakpointMedium", function () {
            $.each(containers, function (k, v) {
                $(v).removeClass(noBackgroundImageClass);
            });
        });
        if ($("body.breakpoint-Medium").length > 0) {
            $(window).trigger('enterBreakpointMedium');
        }
        if ($("body.breakpoint-Large").length > 0) {
            $(window).trigger('enterBreakpointLarge');
        }
    };

    o.init();

}(window.tfl.heroTakeover = window.tfl.heroTakeover || {}));