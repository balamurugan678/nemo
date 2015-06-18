(function (o) {
    $("ol.formatted-list h2,ul.formatted-list h2").each(function () {
        var h2Item = $(this);
        h2Item.parent().addClass("header-two");
        h2Item.siblings().addClass("list-standard");
    })

    $("ol.formatted-list h3,ul.formatted-list h3").each(function () {
        var h2Item = $(this);
        h2Item.parent().addClass("header-three");
        h2Item.siblings().addClass("list-standard");
    })


}(window.tfl.nestedLists = window.tfl.nestedLists || {}));

