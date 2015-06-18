(function (o) {
    var $body,
        mapId = "disambiguation-map",
        $map,
        mapObject;

    o.init = function () {
        var $options = $('#disambiguation-options'),
            $map = $('#' + mapId),
            mapController,
            page = 1;
        mapId = mapId.replace('-', '');

        $options.each(function () {
            var $this = $(this),
                $children = $(this).children();
            if ($children.length > tfl.journeyPlanner.settings.disambiguationItemsPerPage) {
                tfl.navigation.pagination.setup($this.parent(), ".disambiguation-items", tfl.journeyPlanner.settings.disambiguationItemsPerPage, $children.length);
            }
        });

        if ($map.length) {

            $(window).on('map-object-created-' + mapId, function () {
                mapObject = tfl.maps[mapId].googleMap;

                mapObject.on('optionChosen', function (option) {
                    $options.find(".disambiguation-option[data-id='" + option.id + "'] .disambiguation-link")[0].click();
                });

                var $pagination = $options.parent().find(".pagination");
                tfl.navigation.pagination.addMapControls($pagination, mapObject.controller);
            });
        }


    };
}(window.tfl.disambiguation = window.tfl.disambiguation || {}));