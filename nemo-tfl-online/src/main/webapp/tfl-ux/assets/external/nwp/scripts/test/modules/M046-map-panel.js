//(function (o) {

//    var $infoPanel,
//        $mapPanel,
//        loadingId,
//        pageNaptan;

//    o.load = function (ajaxInput, callback) {
//        if (ajaxInput === undefined
//            || ajaxInput.url === undefined
//            || ajaxInput.data === undefined
//            || (ajaxInput.data.naptanId === undefined
//                && ajaxInput.data.placeId === undefined)
//            ) {
//            return false;
//        }
//        if (pageNaptan !== null && pageNaptan === ajaxInput.data.naptanId) {
//            tfl.mapInteractions.hidePanel();
//            return false;
//        }

//        loadingId = ajaxInput.data.naptanId;

//        if ($mapPanel.hasClass('hidden')) $mapPanel.removeClass('hidden');
//        $mapPanel.addClass('loading');

//        tfl.mapInteractions.showPanel();

//        var ajaxSuccess = function(response) {
//            if (loadingId === ajaxInput.data.naptanId) { // if we still want to load this panel
//                $infoPanel.html(response);
//                $mapPanel.removeClass('loading');
//                if (callback) callback();
//            }
//        };

//        var ajaxError = function() {
//            if (loadingId === ajaxInput.data.naptanId) {
//                $infoPanel.html("<h2>Loading information failed</h2>");
//                $mapPanel.removeClass('loading');
//            }
//        };
//        tfl.ajax({
//            url: ajaxInput.url,
//            data: ajaxInput.data,
//            success: ajaxSuccess,
//            error: ajaxError,
//            dataType: 'html'
//        });
//    };

//    o.closePanel = function (e) {
//        if ($mapPanel.hasClass("full-panel")) {
//            $mapPanel.removeClass("full-panel");
//        } else {
//            $mapPanel.addClass("hidden");
//            tfl.mapInteractions.hidePanel();
//        }
//        if (e) {
//            e.stopPropagation();
//        }
//    };

//    o.init = function (sel) {
//        $infoPanel = $(sel);
//        $mapPanel = $infoPanel.parent();
//        $mapPanel.find('a.close3-icon').on('click', o.closePanel);
//        loadingId = "";

//        $mapPanel.click(function () {
//            if ($(document.body).hasClass("stage-active")
//                && !$mapPanel.hasClass("full-panel")
//                && !$mapPanel.hasClass("loading")) {
//                $mapPanel.addClass("full-panel");
//            }
//        });

//        var $ssp =  $('#station-stops-and-piers');
//        if($ssp.length){
//            pageNaptan = $ssp.data('id');
//        }else{
//            pageNaptan = null;
//        }

//        o.initialized = true;
//    };

//}(window.tfl.mapPanel = window.tfl.mapPanel || {}));