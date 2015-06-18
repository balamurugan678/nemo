// Author: Steven Swinbank
// Date: 12/03/2013
// Modified by: Steven Swinbank (added accessibility functionality)
// Modified date: 20/03/2013
// Description: Javscript functions to retrieve image src's, titles and caption text from the <no script> elements of the ImageGallery view.

(function (o) {
    "use strict";
    tfl.logs.create("tfl.imageGallery: loaded");
    o.id;
    var data = [];

    function insertMarkup() {
        var imageGalleries = $(".image-gallery");
        var html = "<div id='galleria-image'></div><div class='caption clearfix'><div class='first-line'><span class='title'></span><span class='counter'></span></div><span class='content'></span></div>";
        $(".image-gallery").append(html);
    };
    function createImageJSON() {
        var noScriptEls = $('.gallery-image:not([data-imgsrc=""])');
        for (var i = 0; i < noScriptEls.length; i++) {
            if ($(noScriptEls[i]).attr("data-imgsrc").length > 0) {
                data.push({ image: $(noScriptEls[i]).attr("data-imgsrc"), title: $(noScriptEls[i]).attr("data-captitle"), description: $(noScriptEls[i]).attr("data-captitle") });
            }
        }
    };
    function createCaption(picNum) {
        var noScriptEls = $('.gallery-image:not([data-imgsrc=""])');
        $(".caption .title").text($(noScriptEls[picNum]).attr("data-captitle"));
        $(".caption .counter").text("");//clear counter
        $(".caption .counter").append("<strong>" + (picNum + 1) + "</strong> of " + noScriptEls.length);
        $(".caption .content").text($(noScriptEls[picNum]).attr("data-capcontent"));
    };
    function getAltValue(picNum) {
        var noScriptEls = $('.gallery-image:not([data-imgsrc=""])');
        return $(noScriptEls[picNum]).attr("data-captitle");
    };
    o.init = function () {
        tfl.logs.create("tfl.imageGallery.init: started");
        $(".image-gallery").show();
        insertMarkup();
        createImageJSON();
        createCaption(0);
        //Galleria.loadTheme('/cdn/static/scripts/plugins/image-gallery/galleria.classic.min.js');//loaded now explicitly in js queue.
        Galleria.run('#galleria-image', {
            dataSource: data,
            debug: false,
            fullscreenDoubleTap: false,
            height: 0.625,
            imageCrop: 'landscape', //true,false,'height','width','landscape','portrait'
            preload: 2, //number of imgs to preload
            queue: false, //queues user 'next' and 'prev' clicks
            showCounter: false, //built in counter
            showInfo: false, //built in caption
            showImagenav: true, //built in 'next' and 'prev' buttons
            swipe: true, //swipe on touch devies
            thumbnails: false,
            transition: 'fadeslide', //'fade','flash','pulse','slide','fadeslide'
            responsive: true,
            extend: function () {
                o.id = this._id;
                var self = this;
                this.bind('image', function (e) {
                    createCaption(self.getIndex());
                    var altValue = getAltValue(self.getIndex());
                    $("#galleria img").attr("alt", altValue).attr("title", altValue);
                })
                this.$('image-nav-left, image-nav-right').unbind('hover');
                this.$('image-nav-left, image-nav-right').animate({ opacity: 1 }, 100);
                //accessibility
                this.attachKeyboard({
                    left: this.prev,
                    right: this.next
                });
                this.$('image-nav-left, image-nav-right').append("<a href='javascript:void(0)' onclick='return false'>&nbsp;</a>");
                this.$('image-nav-right').attr("title", "click to go to next image").attr("alt", "click to go to next image");
                this.$('image-nav-left').attr("title", "click to go to previous image").attr("alt", "click to go to previous image");
                var defaultWidth = "100%";
                $(".galleria-container").width(defaultWidth);
                var currentWidth = $(".galleria-container").width();
                var defaultHeight = currentWidth * 0.625;
                $(".galleria-container").height(defaultHeight);

            }
        });
    }
    $(document).ready(function () {
        o.init();
    });
})(window.tfl.imageGallery = window.tfl.imageGallery || {});