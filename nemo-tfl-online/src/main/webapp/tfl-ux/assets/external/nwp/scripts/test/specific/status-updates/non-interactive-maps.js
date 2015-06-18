(function (o) {
    "use strict";
    tfl.logs.create("tfl.nonInteractiveMaps: loaded");

    function init() {
        var $map = $('#schematic-map');
        $('.map-sliver').on('click', function () {
            tfl.fullscreen.display($map, true);
        });

        function setup() {
            var map = $map[0];
            tfl.utils.runOnImgComplete(map, function () {
                tfl.zoomableContent.init($map);
            });
            $(window).on('exitBreakpointLarge', function () {
                $map.trigger('destroy-panzoom');
            }).on('enterBreakpointLarge', function () {
                tfl.utils.runOnImgComplete(map, function () {
                    $(window).one('append-around-complete', function () {
                        tfl.zoomableContent.init($map);
                    });
                });
            })
        }

        if ($(document.body).hasClass('breakpoint-Large')) {
            setup();
        } else {
            $(window).one('enterBreakpointLarge', function () {
                setup();
            });
        }
    }

    init();


})(window.tfl.nonInteractiveMaps = window.tfl.nonInteractiveMaps || {});
