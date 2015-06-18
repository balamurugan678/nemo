(function (o) {
    o.tabs = function (elm) {
        tfl.logs.create("tfl.navigation.tabs: loaded");
        var tabs = $(elm).children();
        tabs.click(function (event) {
            event.preventDefault();
            tabs.removeClass('selected');
            $(this).addClass('selected');
            tfl.logs.create("tfl.navigation.tabs: " + $(this).find('a').attr('href'));
        });
    };
    o.tabs("[data-tabs='true']");
}(window.tfl.navigation = window.tfl.navigation || {}));