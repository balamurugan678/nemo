(function (o) {
    tfl.logs.create("tfl.navigation.pullContent: started");
    var lightBox,
        contentWrap,
        closeLink,
        obj;

    o.dataAttr = 'data-pull-content';
    var dataStorage = 'data-pull-content-storage';

    o.pullContentHandler = function (event) {
        event.preventDefault();

        obj = $(this);
        var storageKey = obj.attr(dataStorage),
            storage;
        if (storageKey) {
            storage = tfl.storage.get(storageKey);
            if (storage) {
                return false;
            } else {
                tfl.storage.set(storageKey, 'true');
            }
        }

        var content = obj.attr(o.dataAttr).split(','),
            url = obj.attr('href'),
            lightBoxType = obj.attr("data-light-box-type"),
            lightBoxMinWidth = parseInt(obj.attr("data-light-box-min-width"), 10);

        tfl.logs.create("tfl.navigation.pullContent: Clicked a pull link for " + url);


        // if there is a minimum width specified and the window is too small, don't use a lightbox, just display the item in a new window.
        if (!isNaN(lightBoxMinWidth) && lightBoxMinWidth > $(window).width()) {
            window.open(url, '_blank');
            return false;
        }

        lightBoxType = lightBoxType === undefined ? "alert" : lightBoxType; // default light box is  an alert.
        lightBox.attr('data-type', lightBoxType);

        var ajaxSuccess = function (response) {
            var html = $(response),
                hasContent = false,
                tabbables;
            for (var i = 0; i < content.length; i++) {
                $.each(html.find($.trim(content[i])), function (k, v) {
                    hasContent = true;
                    var snipet = $('<p></p>').append($(v)).html();
                    contentWrap.append(snipet);
                    /// ?
                });
            }
            if (!hasContent) {
                document.location.href = url;
                return true;
            }
            closeLink.focus();
            tabbables = contentWrap.find(":tabbable");
            $(tabbables[tabbables.length - 1]).addClass("light-box-last-tab-target").attr("data-jumpto", ".close-light-box");
        };

        if (url.length > 0) {

            if (lightBoxType === "image") {
                var img = new Image();
                img.src = url;
                img.alt = obj.text();
                contentWrap.append(img);

                lightBox.addClass('active');
                closeLink.focus();

            } else {

                tfl.ajax({ url: url, success: ajaxSuccess, dataType: "text" });
                lightBox.addClass("active");

            }
        }
    };
    o.initialised = false;
    o.init = function () {
        var lightBoxes = $('[' + o.dataAttr + ']');

        var ajaxSuccess = function (response) {

            lightBox = $(response);
            closeLink = lightBox.find('.close-light-box');
            contentWrap = lightBox.find('.content');

            lightBox.on('click.lightBox', function (e) {
                closeLink.trigger('click.lightBox');
            });
            lightBox.find('.content-wrap').on('click.lightBox', function (e) {
                e.stopPropagation();
            });
            closeLink.on('click.lightBox', function () {
                contentWrap.empty();
                lightBox.removeClass("active");
                obj.focus();
            });
            $(document.body).append(lightBox);
            lightBoxes.click(o.pullContentHandler);
            o.initialised = true;
            tfl.logs.create("tfl.navigation.pullContent: initialised");
        };

        if (lightBoxes.length && !o.initialised) {
            tfl.ajax({url: '/static/' + tfl.settings.version + '/templates/light-box.html', success: ajaxSuccess, dataType: "text"});
        } else {
            lightBoxes.off('click').click(o.pullContentHandler);
        }
    };

    o.init();
})(window.tfl.navigation = window.tfl.navigation || {});