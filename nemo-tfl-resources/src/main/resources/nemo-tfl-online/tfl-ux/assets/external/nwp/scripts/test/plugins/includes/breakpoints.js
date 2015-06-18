/*
 Breakpoints.js
 version 1.0

 Creates handy events for your responsive design breakpoints

 Copyright 2011 XOXCO, Inc
 http://xoxco.com/

 Documentation for this plugin lives here:
 http://xoxco.com/projects/code/breakpoints

 Licensed under the MIT license:
 http://www.opensource.org/licenses/mit-license.php

 */
(function ($) {
    "use strict";
    var lastSize = 0;
    var interval = null;
    var sorted = null;

    $.fn.resetBreakpoints = function () {
        $(window).unbind('resize');
        lastSize = 0;
    };

    $.fn.setBreakpoints = function (settings) {
        var options = jQuery.extend({
            distinct: true,
            useInnerWidth: true,
            breakpoints: [
                { name: 'Small', resolution: 10 },
                { name: 'Medium', resolution: 580 },
                { name: 'Large', resolution: 900 }
            ]
        }, settings);

        sorted = options.breakpoints.sort(function (a, b) {
            return (b.resolution - a.resolution);
        });

        $(window).on('resize', function () {
            var w;
            //use innerWidth, so that js breakpoints get triggered at the same time as css breakpoints
            //TODO - Don't use browser detection to fix differences in implementation
            if ("innerWidth" in window) {
                w = window.innerWidth;
            } else {
                w = $(window).width();
            }
            var done = false;
            var changed = false;
            for (var bp in sorted) {
                // fire onEnter when a browser expands into a new breakpoint
                // if in distinct mode, remove all other breakpoints first.

                var currentBP = options.breakpoints[bp].resolution;

                if (!done && w >= currentBP && lastSize < currentBP) {
                    if (options.distinct) {
                        for (var x in sorted) {
                            if ($('body').hasClass('breakpoint-' + options.breakpoints[x].name)) {
                                $('body').removeClass('breakpoint-' + options.breakpoints[x].name);
                                $(window).trigger('exitBreakpoint' + options.breakpoints[x].name);
                            }
                        }
                        done = true;
                    }
                    $('body').addClass('breakpoint-' + options.breakpoints[bp].name);
                    $(window).trigger('enterBreakpoint' + options.breakpoints[bp].name);
                    changed = true;
                }

                // fire onExit when browser contracts out of a larger breakpoint
                if (w < options.breakpoints[bp].resolution && lastSize >= options.breakpoints[bp].resolution) {
                    $('body').removeClass('breakpoint-' + options.breakpoints[bp].name);
                    $(window).trigger('exitBreakpoint' + options.breakpoints[bp].name);
                    changed = true;
                }

                // if in distinct mode, fire onEnter when browser contracts into a smaller breakpoint
                if (
                    options.distinct && // only one breakpoint at a time
                    w >= options.breakpoints[bp].resolution && // and we are in this one
                    w < options.breakpoints[bp - 1].resolution && // and smaller than the bigger one
                    lastSize > w && // and we contracted
                    lastSize > 0 &&  // and this is not the first time
                    !$('body').hasClass('breakpoint-' + options.breakpoints[bp].name) // and we aren't already in this breakpoint
                    ) {
                    $('body').addClass('breakpoint-' + options.breakpoints[bp].name);
                    $(window).trigger('enterBreakpoint' + options.breakpoints[bp].name);
                    changed = true;
                }
            }
            if (changed) {
                $(window).trigger('changeBreakpoint');
            }
            // set up for next call
            if (lastSize != w) {
                lastSize = w;
            }
        });
        $(window).resize();
    };
})(jQuery);