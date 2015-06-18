(function (o) {
    var defaults = {
        svgScale: 1,                /// this is based off a map element in tube map how do we want to do it?
        maxScale: 4,
        animate: true,
        panZoomIncrement: 0.5,
        focusAreaRatio: 0.8,
        isSVG: false,
        keyboardIncrement: 50
    }


    function ZoomableContent($element, settings) {
        // user defined
        this.$element = $element;
        this.settings = settings;

        // setup panzoom
        this.$panzoom = null;

        // dimensions
        this.elementWidth = null;
        this.elementHeight = null;
        this.halfElementWidth = null;
        this.halfElementHeight = null;
        this.cHeight = null;
        this.cWidth = null;
        this.halfCWidth = null;
        this.halfCHeight = null;
        this.ceDiffx = null;
        this.ceDiffy = null;
        this.minScale = null;
        this.viewBoxWidthDiff = null;
        this.viewBoxHeightDiff = null;

        // view box
        this.viewBoxX = null;
        this.viewBoxY = null;
        this.viewBoxWidth = null;
        this.viewBoxHeight = null;

        // variables used throughout
        this.currentScale = null;
        this.movedSinceZoom = false;

        this.$zoomInButton = null;
        this.$zoomOutButton = null;

        if ($element.length !== 1) {
            tfl.logs.create("tfl.zoomableContent: More than one element matching selector: '" + $element.selector + "' for zoom.");
            return;
        }

        this.calculateDimensions();

        if (this.settings.isSVG) {
            this.calculateViewBox();
        }


        // if on image load the image is smaller than the view port - make it minimum the size of the screen, and then recalculate.
        if (this.ceDiffx < 0 || this.ceDiffy < 0) {
            var oldWidth = this.elementWidth,
                oldHeight = this.elementHeight;
            if (this.ceDiffx < this.ceDiffy) {
                this.$element.addClass("panzoom-element width");
            } else {
                this.$element.addClass("panzoom-element height");
            }

            this.calculateDimensions();
        }
        this.setupPanZoom();
        zoom = this;
        this.resize();

        this.setupControls();
    }

    ZoomableContent.prototype.setupControls = function () {
        var t = this;
        t.$zoomInButton = $('<button  title="Zoom in" class="map-button zoom-in"><span class="icon plus-icon-blue"></span></button>');
        t.$zoomOutButton = $('<button  title="Zoom out" class="map-button zoom-out"><span class="icon minus-icon-blue"></span></button>');
        // this.$fullScreenButton = $('<button class="map-button full-screen"><span class="icon expand-map-icon"></span></button>');
        t.$elementContainer = t.$element.wrap("<div class='panzoom-wrapper' tabindex=0 />").parent();
        t.$elementContainer.append(t.$zoomInButton);
        t.$elementContainer.append(t.$zoomOutButton);
        // this.$elementContainer.append(this.$fullScreenButton);

        var zoomClick = function (zoomOut) {
            var center = t.$element.parent().offset();
            center.top += t.halfCHeight;
            center.left += t.halfCWidth;
            center.top -= document.body.scrollTop;
            center.left -= document.body.scrollLeft;

            t.$panzoom.panzoom("zoom", zoomOut, {
                focal: {
                    clientX: center.left,
                    clientY: center.top
                }
            });
            //o.showHideExtras(currentScale <= o.zoomToHideStationNames);
        }
        t.$zoomInButton.click(function (e) {
            e.stopPropagation();
            zoomClick(false)
        }).on('keydown', function (e) {
            e.stopPropagation();
        });
        ;
        t.$zoomOutButton.click(function (e) {
            e.stopPropagation();
            zoomClick(true)
        }).on('keydown', function (e) {
            e.stopPropagation();
        });
        ;

        var arrowKeys = [37, 38, 39, 40];
        t.$elementContainer.on('dblclick', function (e) {
            t.$panzoom.panzoom("zoom", false, {
                focal: {
                    clientX: e.clientX,
                    clientY: e.clientY
                }
            });
        }).on('keydown', function (e) {
            if ($(e.target).hasClass('map-button') || $(e.target).closest('.map-button').length) {
                return false;
            }
            e.preventDefault();
            var mat = t.$panzoom.panzoom('getMatrix');
            switch (e.keyCode || e.which) {
                case 37:
                    //left
                    mat[4] = "" + (parseInt(mat[4], 10) + t.settings.keyboardIncrement);
                    break;
                case 38:
                    //up
                    mat[5] = "" + (parseInt(mat[5], 10) + t.settings.keyboardIncrement);
                    break;
                case 39:
                    //right
                    mat[4] = "" + (parseInt(mat[4], 10) - t.settings.keyboardIncrement);
                    break;
                case 40:
                    mat[5] = "" + (parseInt(mat[5], 10) - t.settings.keyboardIncrement);
                    //down
                    break;
            }
            ;
            t.$panzoom.panzoom('setMatrix', mat);
        })


    };


    ZoomableContent.prototype.zoomToBoundingBox = function (bbox) {
        var t = this;
        var panX = bbox.x + (bbox.width / 2);
        var panY = bbox.y + (bbox.height / 2);
        var height = bbox.height;
        var width = bbox.width;

        if (t.settings.isSVG) {
            panX -= t.viewBoxX;
            panX -= (t.viewBoxWidthDiff / 2);
            panX *= t.settings.svgScale;

            panY -= t.viewBoxY;
            panY -= (t.viewBoxHeightDiff / 2);
            panY *= t.settings.svgScale;

            height *= t.settings.svgScale;
            width *= t.settings.svgScale;
        }

        t.currentScale = Math.min(t.settings.maxScale, Math.min(t.cHeight * t.settings.focusAreaRatio / height, t.cWidth * t.settings.focusAreaRatio / width));


        t.$panzoom.panzoom("zoom", t.currentScale);

        t.panToPoint(panX, panY);
        t.movedSinceZoom = false;
    };

    ZoomableContent.prototype.panToPoint = function (x, y) {
        var t = this;
        var panX = (x - t.halfElementWidth) * t.currentScale;
        panX *= -1;
        panX -= t.ceDiffx;

        var panY = (y - t.halfElementHeight) * t.currentScale;
        panY *= -1;
        panY -= t.ceDiffy;

        this.$panzoom.panzoom("pan", panX, panY, { animate: t.settings.animate });
    };

    ZoomableContent.prototype.resetToInitialView = function () {
        var t = this;
        t.zoomToMinScale();
        t.panToCentre();
        t.movedSinceZoom = false;
    }


    ZoomableContent.prototype.calculateDimensions = function () {
        var t = this;
        t.elementWidth = t.$element.width();
        t.elementHeight = t.$element.height();
        t.halfElementWidth = t.elementWidth / 2;
        t.halfElementHeight = t.elementHeight / 2;
        t.cWidth = t.$element.parent().width();
        t.cHeight = t.$element.parent().height();
        t.halfCWidth = t.cWidth / 2;
        t.halfCHeight = t.cHeight / 2;
        t.ceDiffx = t.halfElementWidth - t.halfCWidth;
        t.ceDiffy = t.halfElementHeight - t.halfCHeight;
        t.minScale = Math.max(t.cHeight / t.elementHeight, t.cWidth / t.elementWidth);
        if (t.settings.isSVG) {
            t.viewBoxWidthDiff = (t.viewBoxWidth * t.settings.svgScale) - t.elementWidth;
            t.viewBoxHeightDiff = (t.viewBoxHeight * t.settings.svgScale) - t.elementHeight;
        }
    };

    ZoomableContent.prototype.calculateViewBox = function () {
        var t = this;
        if (t.settings.isSVG) {
            var viewBoxAttrs = t.$element.get(0).attributes.getNamedItem("viewBox").value.split(" ");
            t.viewBoxX = parseInt(viewBoxAttrs[0], 10);
            t.viewBoxY = parseInt(viewBoxAttrs[1], 10);
            t.viewBoxWidth = parseInt(viewBoxAttrs[2], 10);
            t.viewBoxHeight = parseInt(viewBoxAttrs[3], 10);
        }
    }

    ZoomableContent.prototype.resize = function () {
        var t = this;
        t.calculateDimensions();
        t.$panzoom.panzoom("resetDimensions");
        t.$panzoom.panzoom("option", {
            minScale: t.minScale
        });
        var disabled = t.$panzoom.panzoom("isDisabled");
        if (disabled) {
            t.enablePanZoom();
        }
        t.$panzoom.panzoom("zoom", t.minScale);
        t.currentScale = t.minScale;
        if (disabled) {
            t.disablePanZoom();
        }
    };

    ZoomableContent.prototype.disablePanZoom = function () {
        this.$panzoom.panzoom("option", {
            cursor: "pointer",
            disablePan: true,
            disableZoom: true
        }).panzoom("disable");
    };

    ZoomableContent.prototype.enablePanZoom = function () {
        this.$panzoom.panzoom("option", {
            cursor: "move",
            disablePan: false,
            disableZoom: false
        }).panzoom("enable");
    };

    ZoomableContent.prototype.zoomToMinScale = function () {
        this.$panzoom.panzoom("zoom", this.minScale);
    };

    ZoomableContent.prototype.panToCentre = function () {
        this.panToPoint(this.halfElementWidth, this.halfElementHeight);
    };

    ZoomableContent.prototype.setupPanZoom = function () {
        var t = this;
        t.$panzoom = t.$element.panzoom({
            maxScale: t.settings.maxScale,
            minScale: t.minScale,
            contain: "invert",
            increment: t.settings.panZoomIncrement
        });

        t.$panzoom.on("panzoomzoom", function (e, panzoom, scale, opts) {
            t.currentScale = scale;
        });

        t.$panzoom.on("panzoomchange", function (e, panzoom, transform) {
            t.movedSinceZoom = true;
        });

        //when panning or touch zooming ends                                                  specific to tubemap
        //t.$panzoom.on("panzoomend", function (e, panzoom, matrix, changed) {
        //    if (changed) {
        //        var s = parseFloat(matrix[0]);
        //        t.showHideExtras(s <= t.zoomToHideStationNames);
        //    }
        //});
        t.currentScale = t.minScale;
        t.$panzoom.panzoom("zoom", t.currentScale);

        t.panToCentre();

        t.$panzoom.parent().on("mousewheel.focal", function (e) {
            if (!t.$panzoom.panzoom("isDisabled")) {
                e.preventDefault();
                var delta = e.delta || e.originalEvent.wheelDelta;
                var zoomOut = delta ? delta < 0 : e.originalEvent.deltaY > 0;

                t.$panzoom.panzoom("zoom", zoomOut, {
                    focal: {
                        clientX: e.originalEvent.clientX,
                        clientY: e.originalEvent.clientY
                    }
                });
                //t.showHideExtras(t.currentScale <= t.zoomToHideStationNames);                 /// specific to tube map
            }
        });


        var restart = function () {
            if (t.$element.closest('.zoomable-content').length) {
                o.init(t.$element, t.settings);
            }
        }

        $(window).resize(
            $.proxy(t.resize, t)
        )

        t.$panzoom.on("destroy-panzoom", function () {
            tfl.logs.create("tfl.zoomableContent: panzoom destroyed");
            t.$panzoom.panzoom("destroy");
            t.$panzoom.removeAttr("style");
            t.$elementContainer.remove();
        });

    };

    o.init = function (el, options) {
        var element = el
        if (typeof (el) === "string") {
            element = $(el);
        }

        // private init method
        var init = function () {
            var settings = $.extend(defaults, (options || {}));
            return new ZoomableContent(element, settings);
        }

        if (element[0].tagName.toLowerCase() === 'img') {
            tfl.utils.runOnImgComplete(element[0], init);
        } else {
            return init();
        }
    }

    $('[data-zoomable=true]').each(function () {
        o.init($(this));
    });

}(window.tfl.zoomableContent = window.tfl.zoomableContent || {}));