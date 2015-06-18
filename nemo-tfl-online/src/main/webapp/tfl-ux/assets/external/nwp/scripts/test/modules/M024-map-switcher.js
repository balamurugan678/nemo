(function (o) {


    function mapSwitcher($parent) {
        if (!$parent) {
            return false;
        }

        var $maps = $('.map-switcher-container', $parent);

        $maps.on('click.mapSwitcher', function (e) {
            var $this = $(this),
                $other;
            if ($this.hasClass('selected')) {
                return true;
            }
            e.stopPropagation();

            $maps.toggleClass('selected');

            window.getSelection().empty();
        });


    }

    function init() {
        var $mapSwitchers = $('.map-switcher'),
            i;
        for (i = 0; i < $mapSwitchers.length; i += 1) {
            mapSwitcher($mapSwitchers.eq(i));
        }
    }

    init();

}(window.tfl.mapSwitcher = window.tfl.mapSwitcher || {}));