/*
 * MediaQueryListener proof of concept using CSS transitions by Paul Hayes
 * November 5th 2011
 *
 * Based on the excellent matchMedia polyfill
 * https://github.com/paulirish/matchMedia.js
 *
 * matchMedia() polyfill - test whether a CSS media type or media query applies
 * authors: Scott Jehl, Paul Irish, Nicholas Zakas
 * Copyright (c) 2011 Scott, Paul and Nicholas.
 * Dual MIT/BSD license
 */

mql = (function (doc, undefined) {
    "use strict";
    var docElem = doc.documentElement,
        refNode = docElem.firstElementChild || docElem.firstChild,
        idCounter = 0,
        transitionEnd = null;
    if (!doc.getElementById('mq-style')) {
        var style = doc.createElement('style');
        style.id = 'mq-style';
        style.textContent = '.mq { -webkit-transition: width 0.001ms; -moz-transition: width 0.001ms; -o-transition: width 0.001ms; -ms-transition: width 0.001ms; width: 0; position: absolute; top: -100em; }\n';
        docElem.insertBefore(style, refNode);
    }

    var transitionEnds = Array('transitionend', 'webkitTransitionEnd', 'oTransitionEnd', 'msTransitionEnd');

    for (var i in transitionEnds) {
        if ('on' + transitionEnds[i].toLowerCase() in window) transitionEnd = transitionEnds[i];
    }

    if (transitionEnd === null) {
        return;
    }

    return function (size, name, cb) {
        var q = '(min-width: ' + size + 'px)';
        var id = 'mql-' + idCounter++,
            callback = function () {
                // perform test and send results to callback
                cb({
                    matches: (div.offsetWidth == 42),
                    media: q,
                    size: size,
                    name: name
                });
            },
            div = doc.createElement('div');

        div.className = 'mq'; // mq class in CSS declares width: 0 and transition on width of duration 0.001ms
        div.id = id;
        style.textContent += '@media ' + q + ' { #' + div.id + ' { width: 42px; } }\n';

        // add transition event listener
        div.addEventListener(transitionEnd, callback, false);

        docElem.insertBefore(div, refNode);

        // original polyfill removes element, we need to keep element for transitions to continue and events to fire.

        return {
            matches: div.offsetWidth == 42,
            media: q
        };
    };

})(document);
