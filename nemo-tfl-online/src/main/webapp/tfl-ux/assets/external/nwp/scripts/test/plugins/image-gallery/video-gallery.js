// Author: Steven Swinbank
// Date: 23/04/2013
// Modified by:
// Modified date:
// Description:

(function (o) {
    "use strict";
    tfl.logs.create("tfl.videoGallery: loaded");
    o.id;
    var data = [];
    var transitionSpeed = 300;

    function insertMarkup() {
        var videoGalleries = $(".video-gallery-wrapper");
        $(".video-gallery-wrapper").each(function () {
            $(this).children(".video-gallery").append("<div id='galleria-video-" + $(this).index() + "'></div>");
        })
    };

    function createVideoJSON() {
        $(".video-gallery-wrapper").each(function () {
            //var noScriptEls = $(".gallery-video");
            var noScriptEls = $(this).children('.gallery-video');
            for (var i = 0; i < noScriptEls.length; i++) {
                data.push({ video: $(noScriptEls[i]).attr("data-video") });
            }
        })

    };
    function getAltValue(vidNum) {
        var noScriptEls = $(".gallery-video");
        return $(noScriptEls[vidNum]).attr("data-alt");
    };
    function getCaption(vidNum) {
        var noScriptEls = $(".gallery-video");
        return $(noScriptEls[vidNum]).attr("data-caption");
    };

    function prependTitle(title, $galleryWrapper) {
        var videoGalleryHolder = $galleryWrapper.find(".video-gallery");
        var vidGalTitle = $galleryWrapper.find(".video-gallery-title");
        if (!vidGalTitle.length) {
            videoGalleryHolder.prepend("<div class='video-gallery-title'>" + title + "</div>")
        }
        else {
            vidGalTitle.text(title);
        }

    };
    function reScaleArrows() { // document ready
        var imageHeight = $(".video-thumbnail-image").first().height();
        var arrowRight = $(".nbs-flexisel-nav-right");
        var arrowLeft = $(".nbs-flexisel-nav-left");
        var arrowRightHeight = arrowRight.height();
        var halfArrowHeight = arrowRightHeight / 2;
        var newTop = (imageHeight / 2) - halfArrowHeight;
        arrowRight.css("top", newTop);
        arrowLeft.css("top", newTop);

    };


    function slidingFunction($galleryWrapper) {

        $galleryWrapper.find(".video-thumbs").flexisel({
            enableResponsiveBreakpoints: true,
            clone: false,
            //visibleItems: 5,
            responsiveBreakpoints: {
                mobile: {
                    changePoint: 579,
                    visibleItems: 2
                },
                tablet: {
                    changePoint: 580,
                    visibleItems: 3
                },
                desktop: {
                    changePoint: 900,
                    visibleItems: 4
                }
            }
        });
        $(".video-thumbnail-image").first().load(function () {
            reScaleArrows();
        });


    };

    function createThumbs(allowReload, jsonData, galleryElementId, $galleryWrapper, galleriaId) {
        var noScriptEls = $galleryWrapper.find(".gallery-video");
        for (var i = 0; i < noScriptEls.length; i++) {
            if (noScriptEls.length > 1) {
                var html = '<li class="video-thumb video-' + i + '"><a class="video-' + i + '" href="javascript:void(0)"><img class="video-thumbnail-image" src="' + $(noScriptEls[i]).attr("data-thumb") + '" alt="' + $(noScriptEls[i]).attr("data-alt") + '"/><span class="video-caption">' + $(noScriptEls[i]).attr("data-caption") + '</span></a></li>'
                $galleryWrapper.find(".video-thumbs").append(html);
            }

            var el = $galleryWrapper.find("a.video-" + i);
            (function () {
                var id = i;
                var el2 = $galleryWrapper.find(".video-thumb.video-" + i);
                var galleries = Galleria.get();
                var gallery;
                for (var j = 0; j < galleries.length; j++) {
                    if (galleries[j]._id == galleriaId) {
                        gallery = galleries[j];
                    }
                }
                //gallery.show(0);
                el.click(function () {
                    if (gallery.getIndex() !== (id)) {
                        var shown = gallery.show(id);
                        if (shown) {
                            $galleryWrapper.find(".video-thumb.selected").removeClass("selected");
                            el2.addClass("selected");
                            prependTitle($(this).find('.video-caption').text(), $galleryWrapper);
                        }
                    }
                });
            })();
        }

        slidingFunction($galleryWrapper);
        if (allowReload) {
            //$galleryWrapper.find(".video-thumb a").bind("click.reloadGalleria", function () { reloadGalleriaEventHandler(galleriaId, $galleryWrapper, jsonData, galleryElementId) });
            var numVidThumbs = $galleryWrapper.find("li.video-thumb").length;
            if (numVidThumbs == 0) {
                $galleryWrapper.find(".nbs-flexisel-nav-right").hide();
                $galleryWrapper.find(".nbs-flexisel-nav-left").hide();
            }
        }
        ;
    };

    function reloadGalleriaEventHandler(galleriaId, $galleryWrapper, jsonData, galleryElementId) {
        var galleries = Galleria.get();
        var gallery;
        for (var i = 0; i < galleries.length; i++) {
            if (galleries[i]._id == galleriaId) {
                gallery = galleries[i];
            }
        }
        var videoId = gallery.getIndex();
        gallery.destroy();
        $galleryWrapper.find(".video-thumb").remove();
        setupGalleria(true, jsonData, galleryElementId, $galleryWrapper, videoId);

    };
    function setupGalleria(autoplayVideo, jsonData, galleryElementId, $galleryWrapper, videoId) {
        var autoplayOnOff = autoplayVideo ? 1 : 0;

        if (!videoId) videoId = 0;
        Galleria.run(galleryElementId, {
            youtube: {
                //autohide: 2,
                autoplay: 0
            },
            dataSource: jsonData,
            debug: false,
            fullscreenDoubleTap: true,
            height: 0.5625,
            imageCrop: 'landscape', //true,false,'height','width','landscape','portrait'
            preload: 2, //number of imgs to preload
            queue: false, //queues user 'next' and 'prev' clicks
            showCounter: false, //built in counter
            showInfo: false, //built in caption
            showImagenav: true, //built in 'next' and 'prev' buttons
            swipe: true, //swipe on touch devies
            thumbnails: true,
            transition: 'fade', //'fade','flash','pulse','slide','fadeslide'
            responsive: true,
            transitionSpeed: transitionSpeed,
            extend: function () {
                var galleriaId = this._id;
                var self = this;
                self.bind('image', function (e) {
                    var altValue = getAltValue(self.getIndex());
                    $(galleryElementId + " iframe").attr("alt", altValue).attr("title", altValue);
                })
                createThumbs(!autoplayVideo, jsonData, galleryElementId, $galleryWrapper, galleriaId);
                $galleryWrapper.find(".video-thumb.video-" + videoId).addClass("selected");
                var videoTitle = $galleryWrapper.find(".video-thumb.selected").text();
                prependTitle(videoTitle, $galleryWrapper);
                //var galleries = Galleria.get();
                //var gallery;
                //for (var i = 0; i < galleries.length; i++) {
                //    if (galleries[i]._id == galleriaId) {
                //        gallery = galleries[i];
                //    }
                //}
                //if (autoplayVideo) gallery.show(videoId);
            }
        });
    };

    o.init = function () {
        tfl.logs.create("tfl.videoGallery.init: started");
        //loop through each gallery wrapper
        $(".video-gallery-wrapper").each(function () {
            //adding markup
            $(this).children(".video-gallery").append("<div id='galleria-video-" + $(this).index() + "'></div>");
            var jsonData = [];
            var noScriptEls = $(this).children('.gallery-video');
            for (var i = 0; i < noScriptEls.length; i++) {
                jsonData.push({ video: $(noScriptEls[i]).attr("data-video") });
            }
            setupGalleria(false, jsonData, '#' + $(this).children().children().attr("id"), $(this));
        });
        //$('.video-thumbnail-image').first().bind('load', reScaleArrows);
    };

    $(window).on('resize', function () {
        tfl.utils.requestAnimFrame(reScaleArrows);

    })

    o.init();
})(window.tfl.videoGallery = window.tfl.videoGallery || {});