(function (o) {
    tfl.logs.create("tfl.fullscreen stage: loaded");

    var $stage,
        $panZoomStage,
        $closeButton,
        elements = [],
        initialized = false,
        panZoomInitialized = false,
        originalPositions = [],
        originalPositionsAreParents = [],
        withPanZoom = false,
        previousScrollTop = 0,
        options = {
            id: "fullscreen-stage",
            stageActiveClass: "stage-active"
        };

    // private setup function on first fullscreen
    function setupStage(noButton) {
        var $stageElement = $('<div id="' + options.id + '"></div>');
        if (!noButton) {
            $closeButton = $('<button id="close-fullscreen-stage" class="fullscreen-stage-button"><span class="icon close-icon"></span></button>');
            $stageElement.append($closeButton);
            $closeButton.on("click", removeFromStage);
        }
        $(document.body).append($stageElement);
        initialized = true;
        return $stageElement;
    }

    function addToStage($el) {
        tfl.logs.create("tfl.fullscreen: enter fullscreen stage");
        o.addItemToStage($el);

        $(document.body).addClass(options.stageActiveClass);
        $(window).trigger("enter-fullscreen-stage");

        //only set up panzoom on the first element if we have to set it up
        if (withPanZoom && elements.length === 1) {
            $(document.body).addClass("panzoom");
            tfl.zoomableContent.init($el);
        }

    }

    function removeFromStage() {
        tfl.logs.create("tfl.fullscreen: exit fullscreen stage");
        var $element;
        for (var i = elements.length - 1; i >= 0; i--) {
            $element = elements[i];
            if (originalPositionsAreParents[i]) {
                originalPositions[i].append($element);
            } else {
                originalPositions[i].before($element);
            }
        }
        elements = [];
        originalPositions = [];
        originalPositionsAreParents = [];
        if (withPanZoom) {
            $(document.body).removeClass("panzoom");
            $element.trigger("destroy-panzoom");
        }
        $(document.body).removeClass(options.stageActiveClass);
        $(window).trigger("exit-fullscreen-stage");
    }

    o.addItemToStage = function ($element) {
        if (!$stage) {
            tfl.logs.create("tfl.fullscreen: Tried to add item to stage before stage was initialised");
            return;
        }

        var $next = $element.next();
        if ($next.length === 0) {
            originalPositions[originalPositions.length] = $element.parent();
            originalPositionsAreParents[originalPositionsAreParents.length] = true;
        } else {
            originalPositions[originalPositions.length] = $next;
            originalPositionsAreParents[originalPositionsAreParents.length] = false;
        }
        elements.push($element);
        $stage.append($element);
    };

    o.hide = function () {
        if (elements.length !== 0) {
            removeFromStage();
        }
        document.body.scrollTop = previousScrollTop;
    }

    o.display = function ($el, wPZ, noButton) {
        if (!initialized) {
            $stage = setupStage(noButton || false);
        }

        withPanZoom = wPZ;

        if (elements.length !== 0) {
            removeFromStage();
        }

        previousScrollTop = document.body.scrollTop;
        addToStage($el);

    }

})(window.tfl.fullscreen = window.tfl.fullscreen || {});