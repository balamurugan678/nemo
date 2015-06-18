/*! LAB.js (LABjs :: Loading And Blocking JavaScript)
 v2.0.3 (c) Kyle Simpson
 MIT License
 */
(function (o) {
    var K = o.$LAB, y = "UseLocalXHR", z = "AlwaysPreserveOrder", u = "AllowDuplicates", A = "CacheBust", B = "BasePath", C = /^[^?#]*\//.exec(location.href)[0], D = /^\w+\:\/\/\/?[^\/]+/.exec(C)[0], i = document.head || document.getElementsByTagName("head"), L = (o.opera && Object.prototype.toString.call(o.opera) == "[object Opera]") || ("MozAppearance" in document.documentElement.style), q = document.createElement("script"), E = typeof q.preload == "boolean", r = E || (q.readyState && q.readyState == "uninitialized"), F = !r && q.async === true, M = !r && !F && !L;

    function G(a) {
        return Object.prototype.toString.call(a) == "[object Function]"
    }

    function H(a) {
        return Object.prototype.toString.call(a) == "[object Array]"
    }

    function N(a, c) {
        var b = /^\w+\:\/\//;
        if (/^\/\/\/?/.test(a)) {
            a = location.protocol + a
        } else if (!b.test(a) && a.charAt(0) != "/") {
            a = (c || "") + a
        }
        return b.test(a) ? a : ((a.charAt(0) == "/" ? D : C) + a)
    }

    function s(a, c) {
        for (var b in a) {
            if (a.hasOwnProperty(b)) {
                c[b] = a[b]
            }
        }
        return c
    }

    function O(a) {
        var c = false;
        for (var b = 0; b < a.scripts.length; b++) {
            if (a.scripts[b].ready && a.scripts[b].exec_trigger) {
                c = true;
                a.scripts[b].exec_trigger();
                a.scripts[b].exec_trigger = null
            }
        }
        return c
    }

    function t(a, c, b, d) {
        a.onload = a.onreadystatechange = function () {
            if ((a.readyState && a.readyState != "complete" && a.readyState != "loaded") || c[b]) return;
            a.onload = a.onreadystatechange = null;
            d()
        }
    }

    function I(a) {
        a.ready = a.finished = true;
        for (var c = 0; c < a.finished_listeners.length; c++) {
            a.finished_listeners[c]()
        }
        a.ready_listeners = [];
        a.finished_listeners = []
    }

    function P(d, f, e, g, h) {
        setTimeout(function () {
            var a, c = f.real_src, b;
            if ("item" in i) {
                if (!i[0]) {
                    setTimeout(arguments.callee, 25);
                    return
                }
                i = i[0]
            }
            a = document.createElement("script");
            if (f.type) a.type = f.type;
            if (f.charset) a.charset = f.charset;
            if (h) {
                if (r) {
                    e.elem = a;
                    if (E) {
                        a.preload = true;
                        a.onpreload = g
                    } else {
                        a.onreadystatechange = function () {
                            if (a.readyState == "loaded") g()
                        }
                    }
                    a.src = c
                } else if (h && c.indexOf(D) == 0 && d[y]) {
                    b = new XMLHttpRequest();
                    b.onreadystatechange = function () {
                        if (b.readyState == 4) {
                            b.onreadystatechange = function () {
                            };
                            e.text = b.responseText + "\n//@ sourceURL=" + c;
                            g()
                        }
                    };
                    b.open("GET", c);
                    b.send()
                } else {
                    a.type = "text/cache-script";
                    t(a, e, "ready", function () {
                        i.removeChild(a);
                        g()
                    });
                    a.src = c;
                    i.insertBefore(a, i.firstChild)
                }
            } else if (F) {
                a.async = false;
                t(a, e, "finished", g);
                a.src = c;
                i.insertBefore(a, i.firstChild)
            } else {
                t(a, e, "finished", g);
                a.src = c;
                i.insertBefore(a, i.firstChild)
            }
        }, 0)
    }

    function J() {
        var l = {}, Q = r || M, n = [], p = {}, m;
        l[y] = true;
        l[z] = false;
        l[u] = false;
        l[A] = false;
        l[B] = "";
        function R(a, c, b) {
            var d;

            function f() {
                if (d != null) {
                    d = null;
                    I(b)
                }
            }

            if (p[c.src].finished) return;
            if (!a[u]) p[c.src].finished = true;
            d = b.elem || document.createElement("script");
            if (c.type) d.type = c.type;
            if (c.charset) d.charset = c.charset;
            t(d, b, "finished", f);
            if (b.elem) {
                b.elem = null
            } else if (b.text) {
                d.onload = d.onreadystatechange = null;
                d.text = b.text
            } else {
                d.src = c.real_src
            }
            i.insertBefore(d, i.firstChild);
            if (b.text) {
                f()
            }
        }

        function S(c, b, d, f) {
            var e, g, h = function () {
                b.ready_cb(b, function () {
                    R(c, b, e)
                })
            }, j = function () {
                b.finished_cb(b, d)
            };
            b.src = N(b.src, c[B]);
            b.real_src = b.src + (c[A] ? ((/\?.*$/.test(b.src) ? "&_" : "?_") + ~~(Math.random() * 1E9) + "=") : "");
            if (!p[b.src]) p[b.src] = { items: [], finished: false };
            g = p[b.src].items;
            if (c[u] || g.length == 0) {
                e = g[g.length] = { ready: false, finished: false, ready_listeners: [h], finished_listeners: [j] };
                P(c, b, e, ((f) ? function () {
                    e.ready = true;
                    for (var a = 0; a < e.ready_listeners.length; a++) {
                        e.ready_listeners[a]()
                    }
                    e.ready_listeners = []
                } : function () {
                    I(e)
                }), f)
            } else {
                e = g[0];
                if (e.finished) {
                    j()
                } else {
                    e.finished_listeners.push(j)
                }
            }
        }

        function v() {
            var e, g = s(l, {}), h = [], j = 0, w = false, k;

            function T(a, c) {
                a.ready = true;
                a.exec_trigger = c;
                x()
            }

            function U(a, c) {
                a.ready = a.finished = true;
                a.exec_trigger = null;
                for (var b = 0; b < c.scripts.length; b++) {
                    if (!c.scripts[b].finished) return
                }
                c.finished = true;
                x()
            }

            function x() {
                while (j < h.length) {
                    if (G(h[j])) {
                        try {
                            h[j++]()
                        } catch (err) {
                            if (window.console) {
                                console.error(err.message)
                            }
                        }
                        continue
                    } else if (!h[j].finished) {
                        if (O(h[j])) continue;
                        break
                    }
                    j++
                }
                if (j == h.length) {
                    w = false;
                    k = false
                }
            }

            function V() {
                if (!k || !k.scripts) {
                    h.push(k = { scripts: [], finished: true })
                }
            }

            e = { script: function () {
                for (var f = 0; f < arguments.length; f++) {
                    (function (a, c) {
                        var b;
                        if (!H(a)) {
                            c = [a]
                        }
                        for (var d = 0; d < c.length; d++) {
                            V();
                            a = c[d];
                            if (G(a)) a = a();
                            if (!a) continue;
                            if (H(a)) {
                                b = [].slice.call(a);
                                b.unshift(d, 1);
                                [].splice.apply(c, b);
                                d--;
                                continue
                            }
                            if (typeof a == "string") a = { src: a };
                            a = s(a, { ready: false, ready_cb: T, finished: false, finished_cb: U });
                            k.finished = false;
                            k.scripts.push(a);
                            S(g, a, k, (Q && w));
                            w = true;
                            if (g[z]) e.wait()
                        }
                    })(arguments[f], arguments[f])
                }
                return e
            }, wait: function () {
                if (arguments.length > 0) {
                    for (var a = 0; a < arguments.length; a++) {
                        h.push(arguments[a])
                    }
                    k = h[h.length - 1]
                } else k = false;
                x();
                return e
            } };
            return { script: e.script, wait: e.wait, setOptions: function (a) {
                s(a, g);
                return e
            } }
        }

        m = { setGlobalDefaults: function (a) {
            s(a, l);
            return m
        }, setOptions: function () {
            return v().setOptions.apply(null, arguments)
        }, script: function () {
            return v().script.apply(null, arguments)
        }, wait: function () {
            return v().wait.apply(null, arguments)
        }, queueScript: function () {
            n[n.length] = { type: "script", args: [].slice.call(arguments) };
            return m
        }, queueWait: function () {
            n[n.length] = { type: "wait", args: [].slice.call(arguments) };
            return m
        }, runQueue: function () {
            var a = m, c = n.length, b = c, d;
            for (; --b >= 0;) {
                d = n.shift();
                a = a[d.type].apply(null, d.args)
            }
            return a
        }, noConflict: function () {
            o.$LAB = K;
            return m
        }, sandbox: function () {
            return J()
        } };
        return m
    }

    o.$LAB = J();
    (function (a, c, b) {
        if (document.readyState == null && document[a]) {
            document.readyState = "loading";
            document[a](c, b = function () {
                document.removeEventListener(c, b, false);
                document.readyState = "complete"
            }, false)
        }
    })("addEventListener", "DOMContentLoaded")
})(this);
var tfl = {};
(function (o) {
    o.debug = false;
    o.mapScriptPath = '';
}(tfl));

(function (o) {
    o.settings = {
        version: '0.1',
        debug: o.debug,
        googleMapsLoaded: false,
        devices: [
            { name: 'Small', resolution: 10 },
            { name: 'Medium', resolution: 580 },
            { name: 'Large', resolution: 900 }
        ]
    };
    o.logs = {
        create: function (message, type) {
            if (tfl.settings.debug && window.console) {
                switch (type) {
                    case 'info':
                        console.info(message);
                        break;
                    case 'warn':
                        console.warn(message);
                        break;
                    case 'debug':
                        console.debug(message);
                        break;
                    case 'error':
                        console.error(message);
                        break;
                    default:
                        console.log(message);
                        break;
                }
            }
        }
    };
}(window.tfl = window.tfl || {}));

/*jshint browser:true, devel:true, jquery: true, smarttabs: true */
/*global google:false, steal:false */
/*! appendAround markup pattern. [c]2012, @scottjehl, Filament Group, Inc. MIT/GPL
 how-to:
 1. Insert potential element containers throughout the DOM
 2. give each container a data-set attribute with a value that matches all other containers' values
 3. Place your appendAround content in one of the potential containers
 4. Call appendAround() on that element when the DOM is ready
 */
(function ($) {
    $.fn.appendAround = function () {
        return this.each(function () {
            var $self = $(this),
                att = "data-set",
                $set = $("[" + att + "='" + $self.closest("[" + att + "]").attr(att) + "']");

            function appendToVisibleContainer() {
                if ($self.is(":hidden")) {
                    $self.appendTo($set.filter(":visible:eq(0)"));
                }
            }

            appendToVisibleContainer();

            //NOTE - THIS NEXT LINE HAS BEEN MODIFIED TO HOOK INTO OUR MODIFIED BREAKPOINTS.JS
            $(window).on("changeBreakpoint", appendToVisibleContainer);
        });
    };
}(jQuery));
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
            if ("innerWidth" in window && navigator.userAgent.indexOf("Chrome") < 0) {
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
/*! jQuery UI - v1.10.1 - 2013-02-15
 * http://jqueryui.com
 * Includes: jquery.ui.core.js
 * Copyright 2013 jQuery Foundation and other contributors; Licensed MIT */
(function (e, t) {
    function i(t, n) {
        var r, i, o, u = t.nodeName.toLowerCase();
        return"area" === u ? (r = t.parentNode, i = r.name, !t.href || !i || r.nodeName.toLowerCase() !== "map" ? !1 : (o = e("img[usemap=#" + i + "]")[0], !!o && s(o))) : (/input|select|textarea|button|object/.test(u) ? !t.disabled : "a" === u ? t.href || n : n) && s(t)
    }

    function s(t) {
        return e.expr.filters.visible(t) && !e(t).parents().addBack().filter(function () {
            return e.css(this, "visibility") === "hidden"
        }).length
    }

    var n = 0, r = /^ui-id-\d+$/;
    e.ui = e.ui || {};
    if (e.ui.version)return;
    e.extend(e.ui, {version: "1.10.1", keyCode: {BACKSPACE: 8, COMMA: 188, DELETE: 46, DOWN: 40, END: 35, ENTER: 13, ESCAPE: 27, HOME: 36, LEFT: 37, NUMPAD_ADD: 107, NUMPAD_DECIMAL: 110, NUMPAD_DIVIDE: 111, NUMPAD_ENTER: 108, NUMPAD_MULTIPLY: 106, NUMPAD_SUBTRACT: 109, PAGE_DOWN: 34, PAGE_UP: 33, PERIOD: 190, RIGHT: 39, SPACE: 32, TAB: 9, UP: 38}}), e.fn.extend({_focus: e.fn.focus, focus: function (t, n) {
        return typeof t == "number" ? this.each(function () {
            var r = this;
            setTimeout(function () {
                e(r).focus(), n && n.call(r)
            }, t)
        }) : this._focus.apply(this, arguments)
    }, scrollParent: function () {
        var t;
        return e.ui.ie && /(static|relative)/.test(this.css("position")) || /absolute/.test(this.css("position")) ? t = this.parents().filter(function () {
            return/(relative|absolute|fixed)/.test(e.css(this, "position")) && /(auto|scroll)/.test(e.css(this, "overflow") + e.css(this, "overflow-y") + e.css(this, "overflow-x"))
        }).eq(0) : t = this.parents().filter(function () {
            return/(auto|scroll)/.test(e.css(this, "overflow") + e.css(this, "overflow-y") + e.css(this, "overflow-x"))
        }).eq(0), /fixed/.test(this.css("position")) || !t.length ? e(document) : t
    }, zIndex: function (n) {
        if (n !== t)return this.css("zIndex", n);
        if (this.length) {
            var r = e(this[0]), i, s;
            while (r.length && r[0] !== document) {
                i = r.css("position");
                if (i === "absolute" || i === "relative" || i === "fixed") {
                    s = parseInt(r.css("zIndex"), 10);
                    if (!isNaN(s) && s !== 0)return s
                }
                r = r.parent()
            }
        }
        return 0
    }, uniqueId: function () {
        return this.each(function () {
            this.id || (this.id = "ui-id-" + ++n)
        })
    }, removeUniqueId: function () {
        return this.each(function () {
            r.test(this.id) && e(this).removeAttr("id")
        })
    }}), e.extend(e.expr[":"], {data: e.expr.createPseudo ? e.expr.createPseudo(function (t) {
        return function (n) {
            return!!e.data(n, t)
        }
    }) : function (t, n, r) {
        return!!e.data(t, r[3])
    }, focusable: function (t) {
        return i(t, !isNaN(e.attr(t, "tabindex")))
    }, tabbable: function (t) {
        var n = e.attr(t, "tabindex"), r = isNaN(n);
        return(r || n >= 0) && i(t, !r)
    }}), e("<a>").outerWidth(1).jquery || e.each(["Width", "Height"], function (n, r) {
        function u(t, n, r, s) {
            return e.each(i, function () {
                n -= parseFloat(e.css(t, "padding" + this)) || 0, r && (n -= parseFloat(e.css(t, "border" + this + "Width")) || 0), s && (n -= parseFloat(e.css(t, "margin" + this)) || 0)
            }), n
        }

        var i = r === "Width" ? ["Left", "Right"] : ["Top", "Bottom"], s = r.toLowerCase(), o = {innerWidth: e.fn.innerWidth, innerHeight: e.fn.innerHeight, outerWidth: e.fn.outerWidth, outerHeight: e.fn.outerHeight};
        e.fn["inner" + r] = function (n) {
            return n === t ? o["inner" + r].call(this) : this.each(function () {
                e(this).css(s, u(this, n) + "px")
            })
        }, e.fn["outer" + r] = function (t, n) {
            return typeof t != "number" ? o["outer" + r].call(this, t) : this.each(function () {
                e(this).css(s, u(this, t, !0, n) + "px")
            })
        }
    }), e.fn.addBack || (e.fn.addBack = function (e) {
        return this.add(e == null ? this.prevObject : this.prevObject.filter(e))
    }), e("<a>").data("a-b", "a").removeData("a-b").data("a-b") && (e.fn.removeData = function (t) {
        return function (n) {
            return arguments.length ? t.call(this, e.camelCase(n)) : t.call(this)
        }
    }(e.fn.removeData)), e.ui.ie = !!/msie [\w.]+/.exec(navigator.userAgent.toLowerCase()), e.support.selectstart = "onselectstart"in document.createElement("div"), e.fn.extend({disableSelection: function () {
        return this.bind((e.support.selectstart ? "selectstart" : "mousedown") + ".ui-disableSelection", function (e) {
            e.preventDefault()
        })
    }, enableSelection: function () {
        return this.unbind(".ui-disableSelection")
    }}), e.extend(e.ui, {plugin: {add: function (t, n, r) {
        var i, s = e.ui[t].prototype;
        for (i in r)s.plugins[i] = s.plugins[i] || [], s.plugins[i].push([n, r[i]])
    }, call: function (e, t, n) {
        var r, i = e.plugins[t];
        if (!i || !e.element[0].parentNode || e.element[0].parentNode.nodeType === 11)return;
        for (r = 0; r < i.length; r++)e.options[i[r][0]] && i[r][1].apply(e.element, n)
    }}, hasScroll: function (t, n) {
        if (e(t).css("overflow") === "hidden")return!1;
        var r = n && n === "left" ? "scrollLeft" : "scrollTop", i = !1;
        return t[r] > 0 ? !0 : (t[r] = 1, i = t[r] > 0, t[r] = 0, i)
    }})
})(jQuery);
/*!
 * jQuery Cookie Plugin v1.3
 * https://github.com/carhartl/jquery-cookie
 *
 * Copyright 2011, Klaus Hartl
 * Dual licensed under the MIT or GPL Version 2 licenses.
 * http://www.opensource.org/licenses/mit-license.php
 * http://www.opensource.org/licenses/GPL-2.0
 */
(function ($, document, undefined) {
    var pluses = /\+/g;

    function raw(s) {
        return s;
    }

    function decoded(s) {
        return decodeURIComponent(s.replace(pluses, ' '));
    }

    var config = $.cookie = function (key, value, options) {
        // write
        if (value !== undefined) {
            options = $.extend({}, config.defaults, options);

            if (value === null) {
                options.expires = -1;
            }

            if (typeof options.expires === 'number') {
                var days = options.expires, t = options.expires = new Date();
                t.setDate(t.getDate() + days);
            }

            value = config.json ? JSON.stringify(value) : String(value);

            return (document.cookie = [
                encodeURIComponent(key), '=', config.raw ? value : encodeURIComponent(value),
                options.expires ? '; expires=' + options.expires.toUTCString() : '', // use expires attribute, max-age is not supported by IE
                options.path ? '; path=' + options.path : '',
                options.domain ? '; domain=' + options.domain : '',
                options.secure ? '; secure' : ''
            ].join(''));
        }

        // read
        var decode = config.raw ? raw : decoded;
        var cookies = document.cookie.split('; ');
        for (var i = 0, l = cookies.length; i < l; i++) {
            var parts = cookies[i].split('=');
            if (decode(parts.shift()) === key) {
                var cookie = decode(parts.join('='));
                return config.json ? JSON.parse(cookie) : cookie;
            }
        }

        return null;
    };

    config.defaults = {};

    $.removeCookie = function (key, options) {
        if ($.cookie(key) !== null) {
            $.cookie(key, null, options);
            return true;
        }
        return false;
    };
})(jQuery, document);
;
(function ($) { /*******************************************************************************************/
    // jquery.pajinate.js - version 0.4
    // A jQuery plugin for paginating through any number of DOM elements
    // 
    // Copyright (c) 2010, Wes Nolte (http://wesnolte.com)
    // Licensed under the MIT License (MIT-LICENSE.txt)
    // http://www.opensource.org/licenses/mit-license.php
    // Created: 2010-04-16 | Updated: 2010-04-26
    //
    /*******************************************************************************************/

    $.fn.pajinate = function (options) {
        // Set some state information
        var current_page = 'current_page';
        var items_per_page = 'items_per_page';

        var meta;

        // Setup default option values
        var defaults = {
            item_container_id: '.content',
            items_per_page: 10,
            nav_panel_id: '.page_navigation',
            nav_info_id: '.info_text',
            num_page_links_to_display: 20,
            start_page: 0,
            wrap_around: false,
            nav_label_first: 'First',
            nav_label_prev: 'Prev',
            nav_label_next: 'Next',
            nav_label_last: 'Last',
            nav_order: ["first", "prev", "num", "next", "last"],
            nav_label_info: 'Showing {0}-{1} of {2} results',
            show_first_last: true,
            abort_on_small_lists: false,
            jquery_ui: false,
            jquery_ui_active: "ui-state-highlight",
            jquery_ui_default: "ui-state-default",
            jquery_ui_disabled: "ui-state-disabled"
        };

        var options = $.extend(defaults, options);
        var $item_container;
        var $page_container;
        var $items;
        var $nav_panels;
        var total_page_no_links;
        var jquery_ui_default_class = options.jquery_ui ? options.jquery_ui_default : '';
        var jquery_ui_active_class = options.jquery_ui ? options.jquery_ui_active : '';
        var jquery_ui_disabled_class = options.jquery_ui ? options.jquery_ui_disabled : '';

        return this.each(function () {
            $page_container = $(this);
            $item_container = $(this).find(options.item_container_id);
            $items = $page_container.find(options.item_container_id).children();

            if (options.abort_on_small_lists && options.items_per_page >= $items.size()) return $page_container;

            meta = $page_container;

            // Initialize meta data
            meta.data(current_page, 0);
            meta.data(items_per_page, options.items_per_page);

            // Get the total number of items
            var total_items = $item_container.children().size();

            // Calculate the number of pages needed
            var number_of_pages = Math.ceil(total_items / options.items_per_page);

            // Construct the nav bar
            var more = '<span class="ellipse more">...</span>';
            var less = '<span class="ellipse less">...</span>';
            var first = !options.show_first_last ? '' : '<a class="first_link ' + jquery_ui_default_class + '" href="">' + options.nav_label_first + '</a>';
            var last = !options.show_first_last ? '' : '<a class="last_link ' + jquery_ui_default_class + '" href="">' + options.nav_label_last + '</a>';

            var navigation_html = "";

            for (var i = 0; i < options.nav_order.length; i++) {
                switch (options.nav_order[i]) {
                    case "first":
                        navigation_html += first;
                        break;
                    case "last":
                        navigation_html += last;
                        break;
                    case "next":
                        navigation_html += '<a class="next_link ' + jquery_ui_default_class + '" href="">' + options.nav_label_next + '</a>';
                        break;
                    case "prev":
                        navigation_html += '<a class="previous_link ' + jquery_ui_default_class + '" href="">' + options.nav_label_prev + '</a>';
                        break;
                    case "num":
                        navigation_html += less;
                        var current_link = 0;
                        while (number_of_pages > current_link) {
                            navigation_html += '<a class="page_link ' + jquery_ui_default_class + '" href="" longdesc="' + current_link + '">' + (current_link + 1) + '</a>';
                            current_link++;
                        }
                        navigation_html += more;
                        break;
                    default:
                        break;
                }

            }

            // And add it to the appropriate area of the DOM	
            $nav_panels = $page_container.find(options.nav_panel_id);
            $nav_panels.html(navigation_html).each(function () {

                $(this).find('.page_link:first').addClass('first');
                $(this).find('.page_link:last').addClass('last');

            });

            // Hide the more/less indicators
            $nav_panels.children('.ellipse').hide();

            // Set the active page link styling
            $nav_panels.find('.previous_link').next().next().addClass('active_page ' + jquery_ui_active_class);

            /* Setup Page Display */
            // And hide all pages
            $items.hide();
            // Show the first page			
            $items.slice(0, meta.data(items_per_page)).show();

            /* Setup Nav Menu Display */
            // Page number slices
            total_page_no_links = $page_container.find(options.nav_panel_id + ':first').children('.page_link').size();
            options.num_page_links_to_display = Math.min(options.num_page_links_to_display, total_page_no_links);

            $nav_panels.children('.page_link').hide(); // Hide all the page links
            // And only show the number we should be seeing
            $nav_panels.each(function () {
                $(this).children('.page_link').slice(0, options.num_page_links_to_display).show();
            });

            /* Bind the actions to their respective links */

            // Event handler for 'First' link
            $page_container.find('.first_link').click(function (e) {
                e.preventDefault();

                movePageNumbersRight($(this), 0);
                gotopage(0);
            });

            // Event handler for 'Last' link
            $page_container.find('.last_link').click(function (e) {
                e.preventDefault();
                var lastPage = total_page_no_links - 1;
                movePageNumbersLeft($(this), lastPage);
                gotopage(lastPage);
            });

            // Event handler for 'Prev' link
            $page_container.find('.previous_link').click(function (e) {
                e.preventDefault();
                showPrevPage($(this));
            });


            // Event handler for 'Next' link
            $page_container.find('.next_link').click(function (e) {
                e.preventDefault();
                showNextPage($(this));
            });

            // Event handler for each 'Page' link
            $page_container.find('.page_link').click(function (e) {
                e.preventDefault();
                gotopage($(this).attr('longdesc'));
            });

            // Goto the required page
            gotopage(parseInt(options.start_page));
            toggleMoreLess();
            if (!options.wrap_around) tagNextPrev();
        });

        function showPrevPage(e) {
            new_page = parseInt(meta.data(current_page)) - 1;

            // Check that we aren't on a boundary link
            if ($(e).siblings('.active_page').prev('.page_link').length == true) {
                movePageNumbersRight(e, new_page);
                gotopage(new_page);
            }
            else if (options.wrap_around) {
                gotopage(total_page_no_links - 1);
            }

        };

        function showNextPage(e) {
            new_page = parseInt(meta.data(current_page)) + 1;

            // Check that we aren't on a boundary link
            if ($(e).siblings('.active_page').next('.page_link').length == true) {
                movePageNumbersLeft(e, new_page);
                gotopage(new_page);
            }
            else if (options.wrap_around) {
                gotopage(0);
            }

        };

        function gotopage(page_num) {

            page_num = parseInt(page_num, 10)

            var ipp = parseInt(meta.data(items_per_page));

            // Find the start of the next slice
            start_from = page_num * ipp;

            // Find the end of the next slice
            end_on = start_from + ipp;
            // Hide the current page	
            var items = $items.hide().slice(start_from, end_on);

            items.show();

            // Reassign the active class
            $page_container.find(options.nav_panel_id).children('.page_link[longdesc=' + page_num + ']').addClass('active_page ' + jquery_ui_active_class).siblings('.active_page').removeClass('active_page ' + jquery_ui_active_class);

            // Set the current page meta data							
            meta.data(current_page, page_num);
            /*########## Ajout de l'option page courante + nombre de pages*/
            var $current_page = parseInt(meta.data(current_page) + 1);
            // Get the total number of items
            var total_items = $item_container.children().size();
            // Calculate the number of pages needed
            var $number_of_pages = Math.ceil(total_items / options.items_per_page);
            /*##################################################################*/
            $page_container.find(options.nav_info_id).html(options.nav_label_info.replace("{0}", start_from + 1).
                replace("{1}", start_from + items.length).replace("{2}", $items.length).replace("{3}", $current_page).replace("{4}", $number_of_pages));

            // Hide the more and/or less indicators
            toggleMoreLess();

            // Add a class to the next or prev links if there are no more pages next or previous to the active page
            tagNextPrev();

            // check if the onPage callback is available and call it
            if (typeof (options.onPageDisplayed) !== "undefined") {
                options.onPageDisplayed.call(this, page_num + 1)
            }

        }

        // Methods to shift the diplayed index of page numbers to the left or right


        function movePageNumbersLeft(e, new_p) {
            var new_page = new_p;

            var $current_active_link = $(e).siblings('.active_page');

            if ($current_active_link.siblings('.page_link[longdesc=' + new_page + ']').css('display') == 'none') {

                $nav_panels.each(function () {
                    $(this).children('.page_link').hide() // Hide all the page links
                        .slice(parseInt(new_page - options.num_page_links_to_display + 1), new_page + 1).show();
                });
            }

        }

        function movePageNumbersRight(e, new_p) {
            var new_page = new_p;

            var $current_active_link = $(e).siblings('.active_page');

            if ($current_active_link.siblings('.page_link[longdesc=' + new_page + ']').css('display') == 'none') {

                $nav_panels.each(function () {
                    $(this).children('.page_link').hide() // Hide all the page links
                        .slice(new_page, new_page + parseInt(options.num_page_links_to_display)).show();
                });
            }
        }

        // Show or remove the ellipses that indicate that more page numbers exist in the page index than are currently shown


        function toggleMoreLess() {

            if (!$nav_panels.children('.page_link:visible').hasClass('last')) {
                $nav_panels.children('.more').show();
            }
            else {
                $nav_panels.children('.more').hide();
            }

            if (!$nav_panels.children('.page_link:visible').hasClass('first')) {
                $nav_panels.children('.less').show();
            }
            else {
                $nav_panels.children('.less').hide();
            }
        }

        /* Add the style class ".no_more" to the first/prev and last/next links to allow custom styling */

        function tagNextPrev() {
            if ($nav_panels.children('.last').hasClass('active_page')) {
                $nav_panels.children('.next_link').add('.last_link').addClass('no_more ' + jquery_ui_disabled_class);
            }
            else {
                $nav_panels.children('.next_link').add('.last_link').removeClass('no_more ' + jquery_ui_disabled_class);
            }

            if ($nav_panels.children('.first').hasClass('active_page')) {
                $nav_panels.children('.previous_link').add('.first_link').addClass('no_more ' + jquery_ui_disabled_class);
            }
            else {
                $nav_panels.children('.previous_link').add('.first_link').removeClass('no_more ' + jquery_ui_disabled_class);
            }
        }

    };

})(jQuery);

/* ============================================================================
 * jquery.clearsearch.js v1.0.1
 * https://github.com/waslos/jquery-clearsearch
 * ============================================================================
 * Copyright (c) 2012, Was los.de GmbH & Co. KG
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the "Was los.de GmbH & Co. KG" nor the names of its
 *    contributors may be used to endorse or promote products derived from this
 *    software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * ========================================================================= */
(function ($) {
    $.fn.clearSearch = function (options) {
        var settings = $.extend({
            'clearClass': 'clear_input',
            'divClass': this.clearClass + '_div',
            'focusAfterClear': true,
            'linkText': '&times;',
            'distanceFromEdge': 5
        }, options);
        return this.each(function () {
            var $this = $(this), btn;

            if (!$this.parent().hasClass(settings.divClass)) {
                $this.wrap('<div style="position: relative;" class="'
                    + settings.divClass + '">' + $this.html() + '</div>');
                $this.after('<a style="position: absolute; cursor: pointer;" class="'
                    + settings.clearClass + '">' + settings.linkText + '</a>');
            }
            btn = $this.next();

            function clearField() {
                $this.val('').change();
                triggerBtn();
                if (settings.focusAfterClear) {
                    $this.focus();
                }
                if (typeof (settings.callback) === "function") {
                    settings.callback();
                }
            }

            function triggerBtn() {
                if (hasText()) {
                    btn.show();
                } else {
                    btn.hide();
                }
                update();
            }

            function hasText() {
                return $this.val().replace(/^\s+|\s+$/g, '').length > 0;
            }

            function update() {
                var height = $this.outerHeight();
                btn.css({
                    top: height / 2 - btn.height() / 2,
                    right: settings.distanceFromEdge
                });
            }

            btn.on('click', clearField);
            $this.on('keyup keydown change focus', triggerBtn);
            triggerBtn();
        });
    };
})(jQuery);
/*
 *  Copyright 2011 Twitter, Inc.
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */


var Hogan = {};

(function (Hogan, useArrayBuffer) {
    Hogan.Template = function (renderFunc, text, compiler, options) {
        this.r = renderFunc || this.r;
        this.c = compiler;
        this.options = options;
        this.text = text || '';
        this.buf = (useArrayBuffer) ? [] : '';
    }

    Hogan.Template.prototype = {
        // render: replaced by generated code.
        r: function (context, partials, indent) {
            return '';
        },

        // variable escaping
        v: hoganEscape,

        // triple stache
        t: coerceToString,

        render: function render(context, partials, indent) {
            return this.ri([context], partials || {}, indent);
        },

        // render internal -- a hook for overrides that catches partials too
        ri: function (context, partials, indent) {
            return this.r(context, partials, indent);
        },

        // tries to find a partial in the curent scope and render it
        rp: function (name, context, partials, indent) {
            var partial = partials[name];

            if (!partial) {
                return '';
            }

            if (this.c && typeof partial == 'string') {
                partial = this.c.compile(partial, this.options);
            }

            return partial.ri(context, partials, indent);
        },

        // render a section
        rs: function (context, partials, section) {
            var tail = context[context.length - 1];

            if (!isArray(tail)) {
                section(context, partials, this);
                return;
            }

            for (var i = 0; i < tail.length; i++) {
                context.push(tail[i]);
                section(context, partials, this);
                context.pop();
            }
        },

        // maybe start a section
        s: function (val, ctx, partials, inverted, start, end, tags) {
            var pass;

            if (isArray(val) && val.length === 0) {
                return false;
            }

            if (typeof val == 'function') {
                val = this.ls(val, ctx, partials, inverted, start, end, tags);
            }

            pass = (val === '') || !!val;

            if (!inverted && pass && ctx) {
                ctx.push((typeof val == 'object') ? val : ctx[ctx.length - 1]);
            }

            return pass;
        },

        // find values with dotted names
        d: function (key, ctx, partials, returnFound) {
            var names = key.split('.'),
                val = this.f(names[0], ctx, partials, returnFound),
                cx = null;

            if (key === '.' && isArray(ctx[ctx.length - 2])) {
                return ctx[ctx.length - 1];
            }

            for (var i = 1; i < names.length; i++) {
                if (val && typeof val == 'object' && names[i] in val) {
                    cx = val;
                    val = val[names[i]];
                } else {
                    val = '';
                }
            }

            if (returnFound && !val) {
                return false;
            }

            if (!returnFound && typeof val == 'function') {
                ctx.push(cx);
                val = this.lv(val, ctx, partials);
                ctx.pop();
            }

            return val;
        },

        // find values with normal names
        f: function (key, ctx, partials, returnFound) {
            var val = false,
                v = null,
                found = false;

            for (var i = ctx.length - 1; i >= 0; i--) {
                v = ctx[i];
                if (v && typeof v == 'object' && key in v) {
                    val = v[key];
                    found = true;
                    break;
                }
            }

            if (!found) {
                return (returnFound) ? false : "";
            }

            if (!returnFound && typeof val == 'function') {
                val = this.lv(val, ctx, partials);
            }

            return val;
        },

        // higher order templates
        ho: function (val, cx, partials, text, tags) {
            var compiler = this.c;
            var options = this.options;
            options.delimiters = tags;
            var text = val.call(cx, text);
            text = (text == null) ? String(text) : text.toString();
            this.b(compiler.compile(text, options).render(cx, partials));
            return false;
        },

        // template result buffering
        b: (useArrayBuffer) ? function (s) {
            this.buf.push(s);
        } :
            function (s) {
                this.buf += s;
            },
        fl: (useArrayBuffer) ? function () {
            var r = this.buf.join('');
            this.buf = [];
            return r;
        } :
            function () {
                var r = this.buf;
                this.buf = '';
                return r;
            },

        // lambda replace section
        ls: function (val, ctx, partials, inverted, start, end, tags) {
            var cx = ctx[ctx.length - 1],
                t = null;

            if (!inverted && this.c && val.length > 0) {
                return this.ho(val, cx, partials, this.text.substring(start, end), tags);
            }

            t = val.call(cx);

            if (typeof t == 'function') {
                if (inverted) {
                    return true;
                } else if (this.c) {
                    return this.ho(t, cx, partials, this.text.substring(start, end), tags);
                }
            }

            return t;
        },

        // lambda replace variable
        lv: function (val, ctx, partials) {
            var cx = ctx[ctx.length - 1];
            var result = val.call(cx);

            if (typeof result == 'function') {
                result = coerceToString(result.call(cx));
                if (this.c && ~result.indexOf("{\u007B")) {
                    return this.c.compile(result, this.options).render(cx, partials);
                }
            }

            return coerceToString(result);
        }

    };

    var rAmp = /&/g,
        rLt = /</g,
        rGt = />/g,
        rApos = /\'/g,
        rQuot = /\"/g,
        hChars = /[&<>\"\']/;


    function coerceToString(val) {
        return String((val === null || val === undefined) ? '' : val);
    }

    function hoganEscape(str) {
        str = coerceToString(str);
        return hChars.test(str) ?
            str
                .replace(rAmp, '&amp;')
                .replace(rLt, '&lt;')
                .replace(rGt, '&gt;')
                .replace(rApos, '&#39;')
                .replace(rQuot, '&quot;') :
            str;
    }

    var isArray = Array.isArray || function (a) {
        return Object.prototype.toString.call(a) === '[object Array]';
    };

})(typeof exports !== 'undefined' ? exports : Hogan);


(function (Hogan) {
    // Setup regex  assignments
    // remove whitespace according to Mustache spec
    var rIsWhitespace = /\S/,
        rQuot = /\"/g,
        rNewline = /\n/g,
        rCr = /\r/g,
        rSlash = /\\/g,
        tagTypes = {
            '#': 1, '^': 2, '/': 3, '!': 4, '>': 5,
            '<': 6, '=': 7, '_v': 8, '{': 9, '&': 10
        };

    Hogan.scan = function scan(text, delimiters) {
        var len = text.length,
            IN_TEXT = 0,
            IN_TAG_TYPE = 1,
            IN_TAG = 2,
            state = IN_TEXT,
            tagType = null,
            tag = null,
            buf = '',
            tokens = [],
            seenTag = false,
            i = 0,
            lineStart = 0,
            otag = '{{',
            ctag = '}}';

        function addBuf() {
            if (buf.length > 0) {
                tokens.push(new String(buf));
                buf = '';
            }
        }

        function lineIsWhitespace() {
            var isAllWhitespace = true;
            for (var j = lineStart; j < tokens.length; j++) {
                isAllWhitespace =
                    (tokens[j].tag && tagTypes[tokens[j].tag] < tagTypes['_v']) ||
                        (!tokens[j].tag && tokens[j].match(rIsWhitespace) === null);
                if (!isAllWhitespace) {
                    return false;
                }
            }

            return isAllWhitespace;
        }

        function filterLine(haveSeenTag, noNewLine) {
            addBuf();

            if (haveSeenTag && lineIsWhitespace()) {
                for (var j = lineStart, next; j < tokens.length; j++) {
                    if (!tokens[j].tag) {
                        if ((next = tokens[j + 1]) && next.tag == '>') {
                            // set indent to token value
                            next.indent = tokens[j].toString()
                        }
                        tokens.splice(j, 1);
                    }
                }
            } else if (!noNewLine) {
                tokens.push({tag: '\n'});
            }

            seenTag = false;
            lineStart = tokens.length;
        }

        function changeDelimiters(text, index) {
            var close = '=' + ctag,
                closeIndex = text.indexOf(close, index),
                delimiters = trim(
                    text.substring(text.indexOf('=', index) + 1, closeIndex)
                ).split(' ');

            otag = delimiters[0];
            ctag = delimiters[1];

            return closeIndex + close.length - 1;
        }

        if (delimiters) {
            delimiters = delimiters.split(' ');
            otag = delimiters[0];
            ctag = delimiters[1];
        }

        for (i = 0; i < len; i++) {
            if (state == IN_TEXT) {
                if (tagChange(otag, text, i)) {
                    --i;
                    addBuf();
                    state = IN_TAG_TYPE;
                } else {
                    if (text.charAt(i) == '\n') {
                        filterLine(seenTag);
                    } else {
                        buf += text.charAt(i);
                    }
                }
            } else if (state == IN_TAG_TYPE) {
                i += otag.length - 1;
                tag = tagTypes[text.charAt(i + 1)];
                tagType = tag ? text.charAt(i + 1) : '_v';
                if (tagType == '=') {
                    i = changeDelimiters(text, i);
                    state = IN_TEXT;
                } else {
                    if (tag) {
                        i++;
                    }
                    state = IN_TAG;
                }
                seenTag = i;
            } else {
                if (tagChange(ctag, text, i)) {
                    tokens.push({tag: tagType, n: trim(buf), otag: otag, ctag: ctag,
                        i: (tagType == '/') ? seenTag - ctag.length : i + otag.length});
                    buf = '';
                    i += ctag.length - 1;
                    state = IN_TEXT;
                    if (tagType == '{') {
                        if (ctag == '}}') {
                            i++;
                        } else {
                            cleanTripleStache(tokens[tokens.length - 1]);
                        }
                    }
                } else {
                    buf += text.charAt(i);
                }
            }
        }

        filterLine(seenTag, true);

        return tokens;
    }

    function cleanTripleStache(token) {
        if (token.n.substr(token.n.length - 1) === '}') {
            token.n = token.n.substring(0, token.n.length - 1);
        }
    }

    function trim(s) {
        if (s.trim) {
            return s.trim();
        }

        return s.replace(/^\s*|\s*$/g, '');
    }

    function tagChange(tag, text, index) {
        if (text.charAt(index) != tag.charAt(0)) {
            return false;
        }

        for (var i = 1, l = tag.length; i < l; i++) {
            if (text.charAt(index + i) != tag.charAt(i)) {
                return false;
            }
        }

        return true;
    }

    function buildTree(tokens, kind, stack, customTags) {
        var instructions = [],
            opener = null,
            token = null;

        while (tokens.length > 0) {
            token = tokens.shift();
            if (token.tag == '#' || token.tag == '^' || isOpener(token, customTags)) {
                stack.push(token);
                token.nodes = buildTree(tokens, token.tag, stack, customTags);
                instructions.push(token);
            } else if (token.tag == '/') {
                if (stack.length === 0) {
                    throw new Error('Closing tag without opener: /' + token.n);
                }
                opener = stack.pop();
                if (token.n != opener.n && !isCloser(token.n, opener.n, customTags)) {
                    throw new Error('Nesting error: ' + opener.n + ' vs. ' + token.n);
                }
                opener.end = token.i;
                return instructions;
            } else {
                instructions.push(token);
            }
        }

        if (stack.length > 0) {
            throw new Error('missing closing tag: ' + stack.pop().n);
        }

        return instructions;
    }

    function isOpener(token, tags) {
        for (var i = 0, l = tags.length; i < l; i++) {
            if (tags[i].o == token.n) {
                token.tag = '#';
                return true;
            }
        }
    }

    function isCloser(close, open, tags) {
        for (var i = 0, l = tags.length; i < l; i++) {
            if (tags[i].c == close && tags[i].o == open) {
                return true;
            }
        }
    }

    Hogan.generate = function (tree, text, options) {
        var code = 'var _=this;_.b(i=i||"");' + walk(tree) + 'return _.fl();';
        if (options.asString) {
            return 'function(c,p,i){' + code + ';}';
        }

        return new Hogan.Template(new Function('c', 'p', 'i', code), text, Hogan, options);
    }

    function esc(s) {
        return s.replace(rSlash, '\\\\')
            .replace(rQuot, '\\\"')
            .replace(rNewline, '\\n')
            .replace(rCr, '\\r');
    }

    function chooseMethod(s) {
        return (~s.indexOf('.')) ? 'd' : 'f';
    }

    function walk(tree) {
        var code = '';
        for (var i = 0, l = tree.length; i < l; i++) {
            var tag = tree[i].tag;
            if (tag == '#') {
                code += section(tree[i].nodes, tree[i].n, chooseMethod(tree[i].n),
                    tree[i].i, tree[i].end, tree[i].otag + " " + tree[i].ctag);
            } else if (tag == '^') {
                code += invertedSection(tree[i].nodes, tree[i].n,
                    chooseMethod(tree[i].n));
            } else if (tag == '<' || tag == '>') {
                code += partial(tree[i]);
            } else if (tag == '{' || tag == '&') {
                code += tripleStache(tree[i].n, chooseMethod(tree[i].n));
            } else if (tag == '\n') {
                code += text('"\\n"' + (tree.length - 1 == i ? '' : ' + i'));
            } else if (tag == '_v') {
                code += variable(tree[i].n, chooseMethod(tree[i].n));
            } else if (tag === undefined) {
                code += text('"' + esc(tree[i]) + '"');
            }
        }
        return code;
    }

    function section(nodes, id, method, start, end, tags) {
        return 'if(_.s(_.' + method + '("' + esc(id) + '",c,p,1),' +
            'c,p,0,' + start + ',' + end + ',"' + tags + '")){' +
            '_.rs(c,p,' +
            'function(c,p,_){' +
            walk(nodes) +
            '});c.pop();}';
    }

    function invertedSection(nodes, id, method) {
        return 'if(!_.s(_.' + method + '("' + esc(id) + '",c,p,1),c,p,1,0,0,"")){' +
            walk(nodes) +
            '};';
    }

    function partial(tok) {
        return '_.b(_.rp("' + esc(tok.n) + '",c,p,"' + (tok.indent || '') + '"));';
    }

    function tripleStache(id, method) {
        return '_.b(_.t(_.' + method + '("' + esc(id) + '",c,p,0)));';
    }

    function variable(id, method) {
        return '_.b(_.v(_.' + method + '("' + esc(id) + '",c,p,0)));';
    }

    function text(id) {
        return '_.b(' + id + ');';
    }

    Hogan.parse = function (tokens, text, options) {
        options = options || {};
        return buildTree(tokens, '', [], options.sectionTags || []);
    },

        Hogan.cache = {};

    Hogan.compile = function (text, options) {
        // options
        //
        // asString: false (default)
        //
        // sectionTags: [{o: '_foo', c: 'foo'}]
        // An array of object with o and c fields that indicate names for custom
        // section tags. The example above allows parsing of {{_foo}}{{/foo}}.
        //
        // delimiters: A string that overrides the default delimiters.
        // Example: "<% %>"
        //
        options = options || {};

        var key = text + '||' + !!options.asString;

        var t = this.cache[key];

        if (t) {
            return t;
        }

        t = this.generate(this.parse(this.scan(text, options.delimiters), text, options), text, options);
        return this.cache[key] = t;
    };
})(typeof exports !== 'undefined' ? exports : Hogan);


/*!
 * typeahead.js 0.9.2
 * https://github.com/twitter/typeahead
 * Copyright 2013 Twitter, Inc. and other contributors; Licensed MIT
 */

(function ($) {
    var VERSION = "0.9.2";
    var utils = {
        isMsie: function () {
            var match = /(msie) ([\w.]+)/i.exec(navigator.userAgent);
            return match ? parseInt(match[2], 10) : false;
        },
        isBlankString: function (str) {
            return !str || /^\s*$/.test(str);
        },
        escapeRegExChars: function (str) {
            return str.replace(/[\-\[\]\/\{\}\(\)\*\+\?\.\\\^\$\|]/g, "\\$&");
        },
        isString: function (obj) {
            return typeof obj === "string";
        },
        isNumber: function (obj) {
            return typeof obj === "number";
        },
        isArray: $.isArray,
        isFunction: $.isFunction,
        isObject: $.isPlainObject,
        isUndefined: function (obj) {
            return typeof obj === "undefined";
        },
        bind: $.proxy,
        bindAll: function (obj) {
            var val;
            for (var key in obj) {
                $.isFunction(val = obj[key]) && (obj[key] = $.proxy(val, obj));
            }
        },
        indexOf: function (haystack, needle) {
            for (var i = 0; i < haystack.length; i++) {
                if (haystack[i] === needle) {
                    return i;
                }
            }
            return -1;
        },
        each: $.each,
        map: $.map,
        filter: $.grep,
        every: function (obj, test) {
            var result = true;
            if (!obj) {
                return result;
            }
            $.each(obj, function (key, val) {
                if (!(result = test.call(null, val, key, obj))) {
                    return false;
                }
            });
            return !!result;
        },
        some: function (obj, test) {
            var result = false;
            if (!obj) {
                return result;
            }
            $.each(obj, function (key, val) {
                if (result = test.call(null, val, key, obj)) {
                    return false;
                }
            });
            return !!result;
        },
        mixin: $.extend,
        getUniqueId: function () {
            var counter = 0;
            return function () {
                return counter++;
            };
        }(),
        defer: function (fn) {
            setTimeout(fn, 0);
        },
        debounce: function (func, wait, immediate) {
            var timeout, result;
            return function () {
                var context = this, args = arguments, later, callNow;
                later = function () {
                    timeout = null;
                    if (!immediate) {
                        result = func.apply(context, args);
                    }
                };
                callNow = immediate && !timeout;
                clearTimeout(timeout);
                timeout = setTimeout(later, wait);
                if (callNow) {
                    result = func.apply(context, args);
                }
                return result;
            };
        },
        throttle: function (func, wait) {
            var context, args, timeout, result, previous, later;
            previous = 0;
            later = function () {
                previous = new Date();
                timeout = null;
                result = func.apply(context, args);
            };
            return function () {
                var now = new Date(), remaining = wait - (now - previous);
                context = this;
                args = arguments;
                if (remaining <= 0) {
                    clearTimeout(timeout);
                    timeout = null;
                    previous = now;
                    result = func.apply(context, args);
                } else if (!timeout) {
                    timeout = setTimeout(later, remaining);
                }
                return result;
            };
        },
        tokenizeQuery: function (str) {
            return $.trim(str).toLowerCase().split(/[\s]+/);
        },
        tokenizeText: function (str) {
            return $.trim(str).toLowerCase().split(/[\s\-_]+/);
        },
        getProtocol: function () {
            return location.protocol;
        },
        noop: function () {
        }
    };
    var EventTarget = function () {
        var eventSplitter = /\s+/;
        return {
            on: function (events, callback) {
                var event;
                if (!callback) {
                    return this;
                }
                this._callbacks = this._callbacks || {};
                events = events.split(eventSplitter);
                while (event = events.shift()) {
                    this._callbacks[event] = this._callbacks[event] || [];
                    this._callbacks[event].push(callback);
                }
                return this;
            },
            trigger: function (events, data) {
                var event, callbacks;
                if (!this._callbacks) {
                    return this;
                }
                events = events.split(eventSplitter);
                while (event = events.shift()) {
                    if (callbacks = this._callbacks[event]) {
                        for (var i = 0; i < callbacks.length; i += 1) {
                            callbacks[i].call(this, {
                                type: event,
                                data: data
                            });
                        }
                    }
                }
                return this;
            }
        };
    }();
    var EventBus = function () {
        var namespace = "typeahead:";

        function EventBus(o) {
            if (!o || !o.el) {
                $.error("EventBus initialized without el");
            }
            this.$el = $(o.el);
        }

        utils.mixin(EventBus.prototype, {
            trigger: function (type) {
                var args = [].slice.call(arguments, 1);
                this.$el.trigger(namespace + type, args);
            }
        });
        return EventBus;
    }();
    var PersistentStorage = function () {
        var ls, methods;
        try {
            ls = window.localStorage;
        } catch (err) {
            ls = null;
        }
        function PersistentStorage(namespace) {
            this.prefix = ["__", namespace, "__"].join("");
            this.ttlKey = "__ttl__";
            this.keyMatcher = new RegExp("^" + this.prefix);
        }

        if (ls && window.JSON) {
            methods = {
                _prefix: function (key) {
                    return this.prefix + key;
                },
                _ttlKey: function (key) {
                    return this._prefix(key) + this.ttlKey;
                },
                get: function (key) {
                    if (this.isExpired(key)) {
                        this.remove(key);
                    }
                    return decode(ls.getItem(this._prefix(key)));
                },
                set: function (key, val, ttl) {
                    if (utils.isNumber(ttl)) {
                        ls.setItem(this._ttlKey(key), encode(now() + ttl));
                    } else {
                        ls.removeItem(this._ttlKey(key));
                    }
                    return ls.setItem(this._prefix(key), encode(val));
                },
                remove: function (key) {
                    ls.removeItem(this._ttlKey(key));
                    ls.removeItem(this._prefix(key));
                    return this;
                },
                clear: function () {
                    var i, key, keys = [], len = ls.length;
                    for (i = 0; i < len; i++) {
                        if ((key = ls.key(i)).match(this.keyMatcher)) {
                            keys.push(key.replace(this.keyMatcher, ""));
                        }
                    }
                    for (i = keys.length; i--;) {
                        this.remove(keys[i]);
                    }
                    return this;
                },
                isExpired: function (key) {
                    var ttl = decode(ls.getItem(this._ttlKey(key)));
                    return utils.isNumber(ttl) && now() > ttl ? true : false;
                }
            };
        } else {
            methods = {
                get: utils.noop,
                set: utils.noop,
                remove: utils.noop,
                clear: utils.noop,
                isExpired: utils.noop
            };
        }
        utils.mixin(PersistentStorage.prototype, methods);
        return PersistentStorage;
        function now() {
            return new Date().getTime();
        }

        function encode(val) {
            return JSON.stringify(utils.isUndefined(val) ? null : val);
        }

        function decode(val) {
            return JSON.parse(val);
        }
    }();
    var RequestCache = function () {
        function RequestCache(o) {
            utils.bindAll(this);
            o = o || {};
            this.sizeLimit = o.sizeLimit || 10;
            this.cache = {};
            this.cachedKeysByAge = [];
        }

        utils.mixin(RequestCache.prototype, {
            get: function (url) {
                return this.cache[url];
            },
            set: function (url, resp) {
                var requestToEvict;
                if (this.cachedKeysByAge.length === this.sizeLimit) {
                    requestToEvict = this.cachedKeysByAge.shift();
                    delete this.cache[requestToEvict];
                }
                this.cache[url] = resp;
                this.cachedKeysByAge.push(url);
            }
        });
        return RequestCache;
    }();
    var Transport = function () {
        var pendingRequestsCount = 0, pendingRequests = {}, maxPendingRequests, requestCache;

        function Transport(o) {
            utils.bindAll(this);
            o = utils.isString(o) ? {
                url: o
            } : o;
            requestCache = requestCache || new RequestCache();
            maxPendingRequests = utils.isNumber(o.maxParallelRequests) ? o.maxParallelRequests : maxPendingRequests || 6;
            this.url = o.url;
            this.wildcard = o.wildcard || "%QUERY";
            this.filter = o.filter;
            this.replace = o.replace;
            this.ajaxSettings = {
                type: "get",
                cache: o.cache,
                timeout: o.timeout,
                dataType: o.dataType || "json",
                beforeSend: o.beforeSend
            };
            this._get = (/^throttle$/i.test(o.rateLimitFn) ? utils.throttle : utils.debounce)(this._get, o.rateLimitWait || 300);
        }

        utils.mixin(Transport.prototype, {
            _get: function (url, cb) {
                var that = this;
                if (belowPendingRequestsThreshold()) {
                    this._sendRequest(url).done(done);
                } else {
                    this.onDeckRequestArgs = [].slice.call(arguments, 0);
                }
                function done(resp) {
                    var data = that.filter ? that.filter(resp) : resp;
                    cb && cb(data);
                    requestCache.set(url, resp);
                }
            },
            _sendRequest: function (url) {
                var that = this, jqXhr = pendingRequests[url];
                if (!jqXhr) {
                    incrementPendingRequests();
                    jqXhr = pendingRequests[url] = $.ajax(url, this.ajaxSettings).always(always);
                }
                return jqXhr;
                function always() {
                    decrementPendingRequests();
                    pendingRequests[url] = null;
                    if (that.onDeckRequestArgs) {
                        that._get.apply(that, that.onDeckRequestArgs);
                        that.onDeckRequestArgs = null;
                    }
                }
            },
            get: function (query, cb) {
                var that = this, encodedQuery = encodeURIComponent(query || ""), url, resp;
                cb = cb || utils.noop;
                url = this.replace ? this.replace(this.url, encodedQuery) : this.url.replace(this.wildcard, encodedQuery);
                if (resp = requestCache.get(url)) {
                    utils.defer(function () {
                        cb(that.filter ? that.filter(resp) : resp);
                    });
                } else {
                    this._get(url, cb);
                }
                return !!resp;
            }
        });
        return Transport;
        function incrementPendingRequests() {
            pendingRequestsCount++;
        }

        function decrementPendingRequests() {
            pendingRequestsCount--;
        }

        function belowPendingRequestsThreshold() {
            return pendingRequestsCount < maxPendingRequests;
        }
    }();
    var Dataset = function () {
        var keys = {
            thumbprint: "thumbprint",
            protocol: "protocol",
            itemHash: "itemHash",
            adjacencyList: "adjacencyList"
        };

        function Dataset(o) {
            utils.bindAll(this);
            if (utils.isString(o.template) && !o.engine) {
                $.error("no template engine specified");
            }
            if (!o.local && !o.prefetch && !o.remote) {
                $.error("one of local, prefetch, or remote is required");
            }
            this.name = o.name || utils.getUniqueId();
            this.limit = o.limit || 5;
            this.minLength = o.minLength === 0 ? 0 : o.minLength || 1;
            this.header = o.header;
            this.footer = o.footer;
            this.valueKey = o.valueKey || "value";
            this.template = compileTemplate(o.template, o.engine, this.valueKey);
            this.local = o.local;
            this.prefetch = o.prefetch;
            this.remote = o.remote;
            this.itemHash = {};
            this.adjacencyList = {};
            this.storage = o.name ? new PersistentStorage(o.name) : null;
        }

        utils.mixin(Dataset.prototype, {
            _processLocalData: function (data) {
                this._mergeProcessedData(this._processData(data));
            },
            _loadPrefetchData: function (o) {
                var that = this, thumbprint = VERSION + (o.thumbprint || ""), storedThumbprint, storedProtocol, storedItemHash, storedAdjacencyList, isExpired, deferred;
                if (this.storage) {
                    storedThumbprint = this.storage.get(keys.thumbprint);
                    storedProtocol = this.storage.get(keys.protocol);
                    storedItemHash = this.storage.get(keys.itemHash);
                    storedAdjacencyList = this.storage.get(keys.adjacencyList);
                }
                isExpired = storedThumbprint !== thumbprint || storedProtocol !== utils.getProtocol();
                o = utils.isString(o) ? {
                    url: o
                } : o;
                o.ttl = utils.isNumber(o.ttl) ? o.ttl : 24 * 60 * 60 * 1e3;
                if (storedItemHash && storedAdjacencyList && !isExpired) {
                    this._mergeProcessedData({
                        itemHash: storedItemHash,
                        adjacencyList: storedAdjacencyList
                    });
                    deferred = $.Deferred().resolve();
                } else {
                    deferred = $.getJSON(o.url).done(processPrefetchData);
                }
                return deferred;
                function processPrefetchData(data) {
                    var filteredData = o.filter ? o.filter(data) : data, processedData = that._processData(filteredData), itemHash = processedData.itemHash, adjacencyList = processedData.adjacencyList;
                    if (that.storage) {
                        that.storage.set(keys.itemHash, itemHash, o.ttl);
                        that.storage.set(keys.adjacencyList, adjacencyList, o.ttl);
                        that.storage.set(keys.thumbprint, thumbprint, o.ttl);
                        that.storage.set(keys.protocol, utils.getProtocol(), o.ttl);
                    }
                    that._mergeProcessedData(processedData);
                }
            },
            _transformDatum: function (datum) {
                var value = utils.isString(datum) ? datum : datum[this.valueKey], tokens = datum.tokens || utils.tokenizeText(value), item = {
                    value: value,
                    tokens: tokens
                };
                if (utils.isString(datum)) {
                    item.datum = {};
                    item.datum[this.valueKey] = datum;
                } else {
                    item.datum = datum;
                }
                item.tokens = utils.filter(item.tokens, function (token) {
                    return !utils.isBlankString(token);
                });
                item.tokens = utils.map(item.tokens, function (token) {
                    return token.toLowerCase();
                });
                return item;
            },
            _processData: function (data) {
                var that = this, itemHash = {}, adjacencyList = {};
                utils.each(data, function (i, datum) {
                    var item = that._transformDatum(datum), id = utils.getUniqueId(item.value);
                    itemHash[id] = item;
                    utils.each(item.tokens, function (i, token) {
                        var character = token.charAt(0), adjacency = adjacencyList[character] || (adjacencyList[character] = [id]);
                        !~utils.indexOf(adjacency, id) && adjacency.push(id);
                    });
                });
                return {
                    itemHash: itemHash,
                    adjacencyList: adjacencyList
                };
            },
            _mergeProcessedData: function (processedData) {
                var that = this;
                utils.mixin(this.itemHash, processedData.itemHash);
                utils.each(processedData.adjacencyList, function (character, adjacency) {
                    var masterAdjacency = that.adjacencyList[character];
                    that.adjacencyList[character] = masterAdjacency ? masterAdjacency.concat(adjacency) : adjacency;
                });
            },
            _getLocalSuggestions: function (terms) {
                var that = this, firstChars = [], lists = [], shortestList, suggestions = [];
                utils.each(terms, function (i, term) {
                    var firstChar = term.charAt(0);
                    !~utils.indexOf(firstChars, firstChar) && firstChars.push(firstChar);
                });
                utils.each(firstChars, function (i, firstChar) {
                    var list = that.adjacencyList[firstChar];
                    if (!list) {
                        return false;
                    }
                    lists.push(list);
                    if (!shortestList || list.length < shortestList.length) {
                        shortestList = list;
                    }
                });
                if (lists.length < firstChars.length) {
                    return [];
                }
                utils.each(shortestList, function (i, id) {
                    var item = that.itemHash[id], isCandidate, isMatch;
                    isCandidate = utils.every(lists, function (list) {
                        return ~utils.indexOf(list, id);
                    });
                    isMatch = isCandidate && utils.every(terms, function (term) {
                        return utils.some(item.tokens, function (token) {
                            return token.indexOf(term) === 0;
                        });
                    });
                    isMatch && suggestions.push(item);
                });
                return suggestions;
            },
            initialize: function () {
                var deferred;
                this.local && this._processLocalData(this.local);
                this.transport = this.remote ? new Transport(this.remote) : null;
                deferred = this.prefetch ? this._loadPrefetchData(this.prefetch) : $.Deferred().resolve();
                this.local = this.prefetch = this.remote = null;
                this.initialize = function () {
                    return deferred;
                };
                return deferred;
            },
            getSuggestions: function (query, cb) {
                var that = this, terms, suggestions, cacheHit = false;
                if (query.length < this.minLength) {
                    return;
                }
                terms = utils.tokenizeQuery(query);
                suggestions = this._getLocalSuggestions(terms).slice(0, this.limit);
                if (suggestions.length < this.limit && this.transport) {
                    cacheHit = this.transport.get(query, processRemoteData);
                }
                !cacheHit && cb && cb(suggestions);
                function processRemoteData(data) {
                    suggestions = suggestions.slice(0);
                    utils.each(data, function (i, datum) {
                        var item = that._transformDatum(datum), isDuplicate;
                        isDuplicate = utils.some(suggestions, function (suggestion) {
                            return item.value === suggestion.value;
                        });
                        !isDuplicate && suggestions.push(item);
                        return suggestions.length < that.limit;
                    });
                    cb && cb(suggestions);
                }
            }
        });
        return Dataset;
        function compileTemplate(template, engine, valueKey) {
            var renderFn, compiledTemplate;
            if (utils.isFunction(template)) {
                renderFn = template;
            } else if (utils.isString(template)) {
                compiledTemplate = engine.compile(template);
                renderFn = utils.bind(compiledTemplate.render, compiledTemplate);
            } else {
                renderFn = function (context) {
                    return "<p>" + context[valueKey] + "</p>";
                };
            }
            return renderFn;
        }
    }();
    var InputView = function () {
        function InputView(o) {
            var that = this;
            utils.bindAll(this);
            this.specialKeyCodeMap = {
                9: "tab",
                27: "esc",
                37: "left",
                39: "right",
                13: "enter",
                38: "up",
                40: "down"
            };
            this.$hint = $(o.hint);
            this.$input = $(o.input).on("blur.tt", this._handleBlur).on("focus.tt", this._handleFocus).on("keydown.tt", this._handleSpecialKeyEvent);
            if (!utils.isMsie()) {
                this.$input.on("input.tt", this._compareQueryToInputValue);
            } else {
                this.$input.on("keydown.tt keypress.tt cut.tt paste.tt", function ($e) {
                    if (that.specialKeyCodeMap[$e.which || $e.keyCode]) {
                        return;
                    }
                    utils.defer(that._compareQueryToInputValue);
                });
            }
            this.query = this.$input.val();
            this.$overflowHelper = buildOverflowHelper(this.$input);
        }

        utils.mixin(InputView.prototype, EventTarget, {
            _handleFocus: function () {
                this.trigger("focused");
            },
            _handleBlur: function () {
                this.trigger("blured");
            },
            _handleSpecialKeyEvent: function ($e) {
                var keyName = this.specialKeyCodeMap[$e.which || $e.keyCode];
                keyName && this.trigger(keyName + "Keyed", $e);
            },
            _compareQueryToInputValue: function () {
                var inputValue = this.getInputValue(), isSameQuery = compareQueries(this.query, inputValue), isSameQueryExceptWhitespace = isSameQuery ? this.query.length !== inputValue.length : false;
                if (isSameQueryExceptWhitespace) {
                    this.trigger("whitespaceChanged", {
                        value: this.query
                    });
                } else if (!isSameQuery) {
                    this.trigger("queryChanged", {
                        value: this.query = inputValue
                    });
                }
            },
            destroy: function () {
                this.$hint.off(".tt");
                this.$input.off(".tt");
                this.$hint = this.$input = this.$overflowHelper = null;
            },
            focus: function () {
                this.$input.focus();
            },
            blur: function () {
                this.$input.blur();
            },
            getQuery: function () {
                return this.query;
            },
            setQuery: function (query) {
                this.query = query;
            },
            getInputValue: function () {
                return this.$input.val();
            },
            setInputValue: function (value, silent) {
                this.$input.val(value);
                !silent && this._compareQueryToInputValue();
            },
            getHintValue: function () {
                return this.$hint.val();
            },
            setHintValue: function (value) {
                this.$hint.val(value);
            },
            hidePlaceholder: function () {
                if (!this.placeholderText) {
                    this.placeholderText = this.$input.attr("placeholder");
                }
                this.$input.attr("placeholder", "");
            },
            showPlaceholder: function () {
                if (this.placeholderText) {
                    this.$input.attr("placeholder", this.placeholderText);
                }
            },
            getLanguageDirection: function () {
                return (this.$input.css("direction") || "ltr").toLowerCase();
            },
            isOverflow: function () {
                this.$overflowHelper.text(this.getInputValue());
                return this.$overflowHelper.width() > this.$input.width();
            },
            isCursorAtEnd: function () {
                var valueLength = this.$input.val().length, selectionStart = this.$input[0].selectionStart, range;
                if (utils.isNumber(selectionStart)) {
                    return selectionStart === valueLength;
                } else if (document.selection) {
                    range = document.selection.createRange();
                    range.moveStart("character", -valueLength);
                    return valueLength === range.text.length;
                }
                return true;
            },
            isCursorAtBeginning: function () {
                var valueLength = this.$input.val().length, selectionStart = this.$input[0].selectionStart, range;
                if (utils.isNumber(selectionStart)) {
                    return selectionStart === 0;
                } else if (document.selection) {
                    range = document.selection.createRange();
                    range.moveStart("character", -valueLength);
                    return range.text.length === 0;
                }
                return true;
            }
        });
        return InputView;
        function buildOverflowHelper($input) {
            return $("<span></span>").css({
                position: "absolute",
                left: "-9999px",
                visibility: "hidden",
                whiteSpace: "nowrap",
                fontFamily: $input.css("font-family"),
                fontSize: $input.css("font-size"),
                fontStyle: $input.css("font-style"),
                fontVariant: $input.css("font-variant"),
                fontWeight: $input.css("font-weight"),
                wordSpacing: $input.css("word-spacing"),
                letterSpacing: $input.css("letter-spacing"),
                textIndent: $input.css("text-indent"),
                textRendering: $input.css("text-rendering"),
                textTransform: $input.css("text-transform")
            }).insertAfter($input);
        }

        function compareQueries(a, b) {
            a = (a || "").replace(/^\s*/g, "").replace(/\s{2,}/g, " ");
            b = (b || "").replace(/^\s*/g, "").replace(/\s{2,}/g, " ");
            return a === b;
        }
    }();
    var DropdownView = function () {
        var html = {
            suggestionsList: '<span class="tt-suggestions"></span>'
        }, css = {
            suggestionsList: {
                display: "block"
            },
            suggestion: {
                whiteSpace: "nowrap",
                cursor: "pointer"
            },
            suggestionChild: {
                whiteSpace: "normal"
            }
        };

        function DropdownView(o) {
            utils.bindAll(this);
            this.isOpen = false;
            this.isEmpty = true;
            this.isMouseOverDropdown = false;
            this.$menu = $(o.menu).on("mouseenter.tt", this._handleMouseenter).on("mouseleave.tt", this._handleMouseleave).on("click.tt", ".tt-suggestion", this._handleSelection).on("mouseover.tt", ".tt-suggestion", this._handleMouseover);
        }

        utils.mixin(DropdownView.prototype, EventTarget, {
            _handleMouseenter: function () {
                this.isMouseOverDropdown = true;
            },
            _handleMouseleave: function () {
                this.isMouseOverDropdown = false;
            },
            _handleMouseover: function ($e) {
                var $suggestion = $($e.currentTarget);
                this._getSuggestions().removeClass("tt-is-under-cursor");
                $suggestion.addClass("tt-is-under-cursor");
            },
            _handleSelection: function ($e) {
                var $suggestion = $($e.currentTarget);
                this.trigger("suggestionSelected", extractSuggestion($suggestion));
            },
            _show: function () {
                this.$menu.css("display", "block");
            },
            _hide: function () {
                this.$menu.hide();
            },
            _moveCursor: function (increment) {
                var $suggestions, $cur, nextIndex, $underCursor;
                if (!this.isVisible()) {
                    return;
                }
                $suggestions = this._getSuggestions();
                $cur = $suggestions.filter(".tt-is-under-cursor");
                $cur.removeClass("tt-is-under-cursor");
                nextIndex = $suggestions.index($cur) + increment;
                nextIndex = (nextIndex + 1) % ($suggestions.length + 1) - 1;
                if (nextIndex === -1) {
                    this.trigger("cursorRemoved");
                    return;
                } else if (nextIndex < -1) {
                    nextIndex = $suggestions.length - 1;
                }
                $underCursor = $suggestions.eq(nextIndex).addClass("tt-is-under-cursor");
                this.trigger("cursorMoved", extractSuggestion($underCursor));
            },
            _getSuggestions: function () {
                return this.$menu.find(".tt-suggestions > .tt-suggestion");
            },
            destroy: function () {
                this.$menu.off(".tt");
                this.$menu = null;
            },
            isVisible: function () {
                return this.isOpen && !this.isEmpty;
            },
            closeUnlessMouseIsOverDropdown: function () {
                if (!this.isMouseOverDropdown) {
                    this.close();
                }
            },
            close: function () {
                if (this.isOpen) {
                    this.isOpen = false;
                    this._hide();
                    this.isMouseOverDropdown = false;
                    this.$menu.find(".tt-suggestions > .tt-suggestion").removeClass("tt-is-under-cursor");
                    this.trigger("closed");
                }
            },
            open: function () {
                if (!this.isOpen) {
                    this.isOpen = true;
                    !this.isEmpty && this._show();
                    this.trigger("opened");
                }
            },
            setLanguageDirection: function (dir) {
                var ltrCss = {
                    left: "0",
                    right: "auto"
                }, rtlCss = {
                    left: "auto",
                    right: " 0"
                };
                dir === "ltr" ? this.$menu.css(ltrCss) : this.$menu.css(rtlCss);
            },
            moveCursorUp: function () {
                this._moveCursor(-1);
            },
            moveCursorDown: function () {
                this._moveCursor(+1);
            },
            getSuggestionUnderCursor: function () {
                var $suggestion = this._getSuggestions().filter(".tt-is-under-cursor").first();
                return $suggestion.length > 0 ? extractSuggestion($suggestion) : null;
            },
            getFirstSuggestion: function () {
                var $suggestion = this._getSuggestions().first();
                return $suggestion.length > 0 ? extractSuggestion($suggestion) : null;
            },
            renderSuggestions: function (dataset, suggestions) {
                var datasetClassName = "tt-dataset-" + dataset.name, wrapper = '<div class="tt-suggestion">%body</div>', compiledHtml, $suggestionsList, $dataset = this.$menu.find("." + datasetClassName), elBuilder, fragment, $el;
                if ($dataset.length === 0) {
                    $suggestionsList = $(html.suggestionsList).css(css.suggestionsList);
                    $dataset = $("<div></div>").addClass(datasetClassName).append(dataset.header).append($suggestionsList).append(dataset.footer).appendTo(this.$menu);
                }
                if (suggestions.length > 0) {
                    this.isEmpty = false;
                    this.isOpen && this._show();
                    elBuilder = document.createElement("div");
                    fragment = document.createDocumentFragment();
                    utils.each(suggestions, function (i, suggestion) {
                        compiledHtml = dataset.template(suggestion.datum);
                        elBuilder.innerHTML = wrapper.replace("%body", compiledHtml);
                        $el = $(elBuilder.firstChild).css(css.suggestion).data("suggestion", suggestion);
                        $el.children().each(function () {
                            $(this).css(css.suggestionChild);
                        });
                        fragment.appendChild($el[0]);
                    });
                    $dataset.show().find(".tt-suggestions").html(fragment);
                } else {
                    this.clearSuggestions(dataset.name);
                }
                this.trigger("suggestionsRendered");
            },
            clearSuggestions: function (datasetName) {
                var $datasets = datasetName ? this.$menu.find(".tt-dataset-" + datasetName) : this.$menu.find('[class^="tt-dataset-"]'), $suggestions = $datasets.find(".tt-suggestions");
                $datasets.hide();
                $suggestions.empty();
                if (this._getSuggestions().length === 0) {
                    this.isEmpty = true;
                    this._hide();
                }
            }
        });
        return DropdownView;
        function extractSuggestion($el) {
            return $el.data("suggestion");
        }
    }();
    var TypeaheadView = function () {
        var html = {
            wrapper: '<span class="twitter-typeahead"></span>',
            hint: '<input class="tt-hint" type="text" autocomplete="off" spellcheck="off" disabled>',
            dropdown: '<span class="tt-dropdown-menu"></span>'
        }, css = {
            wrapper: {
                position: "relative",
                display: "inline-block"
            },
            hint: {
                position: "absolute",
                top: "0",
                left: "0",
                borderColor: "transparent",
                boxShadow: "none"
            },
            query: {
                position: "relative",
                verticalAlign: "top",
                backgroundColor: "transparent"
            },
            dropdown: {
                position: "absolute",
                top: "100%",
                left: "0",
                zIndex: "100",
                display: "none"
            }
        };
        if (utils.isMsie()) {
            utils.mixin(css.query, {
                backgroundImage: "url(data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7)"
            });
        }
        if (utils.isMsie() && utils.isMsie() <= 7) {
            utils.mixin(css.wrapper, {
                display: "inline",
                zoom: "1"
            });
            utils.mixin(css.query, {
                marginTop: "-1px"
            });
        }
        function TypeaheadView(o) {
            var $menu, $input, $hint;
            utils.bindAll(this);
            this.$node = buildDomStructure(o.input);
            this.datasets = o.datasets;
            this.dir = null;
            this.eventBus = o.eventBus;
            $menu = this.$node.find(".tt-dropdown-menu");
            $input = this.$node.find(".tt-query");
            $hint = this.$node.find(".tt-hint");
            this.dropdownView = new DropdownView({
                menu: $menu
            }).on("suggestionSelected", this._handleSelection).on("cursorMoved", this._clearHint).on("cursorMoved", this._setInputValueToSuggestionUnderCursor).on("cursorRemoved", this._setInputValueToQuery).on("cursorRemoved", this._updateHint).on("suggestionsRendered", this._updateHint).on("opened", this._updateHint).on("closed", this._clearHint).on("opened closed", this._propagateEvent);
            this.inputView = new InputView({
                input: $input,
                hint: $hint
            }).on("focused", this._openDropdown).on("blured", this._closeDropdown).on("blured", this._setInputValueToQuery).on("enterKeyed tabKeyed", this._handleSelection).on("queryChanged", this._clearHint).on("queryChanged", this._clearSuggestions).on("queryChanged", this._getSuggestions).on("whitespaceChanged", this._updateHint).on("queryChanged whitespaceChanged", this._openDropdown).on("queryChanged whitespaceChanged", this._setLanguageDirection).on("escKeyed", this._closeDropdown).on("escKeyed", this._setInputValueToQuery).on("tabKeyed upKeyed downKeyed", this._managePreventDefault).on("upKeyed downKeyed", this._moveDropdownCursor).on("upKeyed downKeyed", this._openDropdown).on("tabKeyed leftKeyed rightKeyed", this._autocomplete);
        }

        utils.mixin(TypeaheadView.prototype, EventTarget, {
            _managePreventDefault: function (e) {
                var $e = e.data, hint, inputValue, preventDefault = false;
                switch (e.type) {
                    case "tabKeyed":
                        hint = this.inputView.getHintValue();
                        inputValue = this.inputView.getInputValue();
                        preventDefault = hint && hint !== inputValue && inputValue !== "";
                        break;

                    case "upKeyed":
                    case "downKeyed":
                        preventDefault = !$e.shiftKey && !$e.ctrlKey && !$e.metaKey;
                        break;
                }
                preventDefault && $e.preventDefault();
            },
            _setLanguageDirection: function () {
                var dir = this.inputView.getLanguageDirection();
                if (dir !== this.dir) {
                    this.dir = dir;
                    this.$node.css("direction", dir);
                    this.dropdownView.setLanguageDirection(dir);
                }
            },
            _updateHint: function () {
                var suggestion = this.dropdownView.getFirstSuggestion(), hint = suggestion ? suggestion.value : null, dropdownIsVisible = this.dropdownView.isVisible(), inputHasOverflow = this.inputView.isOverflow(), inputValue, query, escapedQuery, beginsWithQuery, match;
                if (hint && dropdownIsVisible && !inputHasOverflow) {
                    inputValue = this.inputView.getInputValue();
                    query = inputValue.replace(/\s{2,}/g, " ").replace(/^\s+/g, "");
                    escapedQuery = utils.escapeRegExChars(query);
                    beginsWithQuery = new RegExp("^(?:" + escapedQuery + ")(.*$)", "i");
                    match = beginsWithQuery.exec(hint);
                    if (inputValue === "") {
                        this.inputView.hidePlaceholder();
                    }
                    this.inputView.setHintValue(inputValue + (match ? match[1] : ""));
                }
            },
            _clearHint: function () {
                this.inputView.showPlaceholder();
                this.inputView.setHintValue("");
            },
            _clearSuggestions: function () {
                this.dropdownView.clearSuggestions();
            },
            _setInputValueToQuery: function () {
                this.inputView.setInputValue(this.inputView.getQuery());
            },
            _setInputValueToSuggestionUnderCursor: function (e) {
                var suggestion = e.data;
                this.inputView.setInputValue(suggestion.value, true);
            },
            _openDropdown: function () {
                if (!this.dropdownView.isOpen) {
                    this._getSuggestions();
                }
                this.dropdownView.open();
            },
            _closeDropdown: function (e) {
                this.dropdownView[e.type === "blured" ? "closeUnlessMouseIsOverDropdown" : "close"]();
            },
            _moveDropdownCursor: function (e) {
                var $e = e.data;
                if (!$e.shiftKey && !$e.ctrlKey && !$e.metaKey) {
                    this.dropdownView[e.type === "upKeyed" ? "moveCursorUp" : "moveCursorDown"]();
                }
            },
            _handleSelection: function (e) {
                var byClick = e.type === "suggestionSelected", suggestion = byClick ? e.data : this.dropdownView.getSuggestionUnderCursor();
                if (suggestion) {
                    this.inputView.setInputValue(suggestion.value);
                    byClick ? this.inputView.focus() : e.data.preventDefault();
                    byClick && utils.isMsie() ? utils.defer(this.dropdownView.close) : this.dropdownView.close();
                    this.eventBus.trigger("selected", suggestion.datum);
                }
            },
            _getSuggestions: function () {
                var that = this, query = this.inputView.getQuery(), blank = utils.isBlankString(query);
                utils.each(this.datasets, function (i, dataset) {
                    if (!blank) {
                        dataset.getSuggestions(query, function (suggestions) {
                            if (query === that.inputView.getQuery()) {
                                that.dropdownView.renderSuggestions(dataset, suggestions);
                            }
                        });
                    } else if (dataset.minLength === 0) {
                        var suggestions = [];
                        var i = 0;
                        for (var item in dataset.itemHash) {
                            if (dataset.limit && i >= dataset.limit) {
                                break;
                            }
                            suggestions.push(dataset.itemHash[item]);
                            i++;
                        }
                        that.dropdownView.renderSuggestions(dataset, suggestions);
                    }
                });
            },
            _autocomplete: function (e) {
                var isCursorAtEnd, isCursorAtBeginning, languageDirection, ignoreEvent, query, hint, suggestion;
                if (e.type === "rightKeyed" || e.type === "leftKeyed") {
                    isCursorAtEnd = this.inputView.isCursorAtEnd();
                    ignoreEvent = this.inputView.getLanguageDirection() === "ltr" ? e.type === "leftKeyed" : e.type === "rightKeyed";
                    if (!isCursorAtEnd || ignoreEvent) {
                        return;
                    }
                }
                if (e.type === "tabKeyed") {
                    languageDirection = this.inputView.getLanguageDirection();
                    if ((languageDirection === "ltr" && this.inputView.isCursorAtBeginning()) || (languageDirection !== "ltr" && this.inputView.isCursorAtEnd())) {
                        return;
                    }
                }
                query = this.inputView.getQuery();
                hint = this.inputView.getHintValue();
                if (hint !== "" && query !== hint) {
                    suggestion = this.dropdownView.getFirstSuggestion();
                    this.inputView.setInputValue(suggestion.value);
                    this.eventBus.trigger("autocompleted", suggestion.datum);
                }
            },
            _propagateEvent: function (e) {
                this.eventBus.trigger(e.type);
            },
            destroy: function () {
                this.inputView.destroy();
                this.dropdownView.destroy();
                destroyDomStructure(this.$node);
                this.$node = null;
            },
            setQuery: function (query) {
                this.inputView.setQuery(query);
                this.inputView.setInputValue(query);
                this._clearHint();
                this._clearSuggestions();
                this._getSuggestions();
            }
        });
        return TypeaheadView;
        function buildDomStructure(input) {
            var $wrapper = $(html.wrapper), $dropdown = $(html.dropdown), $input = $(input), $hint = $(html.hint);
            $wrapper = $wrapper.css(css.wrapper);
            $dropdown = $dropdown.css(css.dropdown);
            $hint.css(css.hint).css({
                backgroundAttachment: $input.css("background-attachment"),
                backgroundClip: $input.css("background-clip"),
                backgroundColor: $input.css("background-color"),
                backgroundImage: $input.css("background-image"),
                backgroundOrigin: $input.css("background-origin"),
                backgroundPosition: $input.css("background-position"),
                backgroundRepeat: $input.css("background-repeat"),
                backgroundSize: $input.css("background-size")
            });
            $input.data("ttAttrs", {
                dir: $input.attr("dir"),
                autocomplete: $input.attr("autocomplete"),
                spellcheck: $input.attr("spellcheck"),
                style: $input.attr("style")
            });
            $input.addClass("tt-query").attr({
                autocomplete: "off",
                spellcheck: false
            }).css(css.query);
            try {
                !$input.attr("dir") && $input.attr("dir", "auto");
            } catch (e) {
            }
            return $input.wrap($wrapper).parent().prepend($hint).append($dropdown);
        }

        function destroyDomStructure($node) {
            var $input = $node.find(".tt-query");
            utils.each($input.data("ttAttrs"), function (key, val) {
                utils.isUndefined(val) ? $input.removeAttr(key) : $input.attr(key, val);
            });
            $input.detach().removeData("ttAttrs").removeClass("tt-query").insertAfter($node);
            $node.remove();
        }
    }();
    (function () {
        var cache = {}, viewKey = "ttView", methods;
        methods = {
            initialize: function (datasetDefs) {
                var datasets;
                datasetDefs = utils.isArray(datasetDefs) ? datasetDefs : [datasetDefs];
                if (datasetDefs.length === 0) {
                    $.error("no datasets provided");
                }
                datasets = utils.map(datasetDefs, function (o) {
                    var dataset = cache[o.name] ? cache[o.name] : new Dataset(o);
                    if (o.name) {
                        cache[o.name] = dataset;
                    }
                    return dataset;
                });
                return this.each(initialize);
                function initialize() {
                    var $input = $(this), deferreds, eventBus = new EventBus({
                        el: $input
                    });
                    deferreds = utils.map(datasets, function (dataset) {
                        return dataset.initialize();
                    });
                    $input.data(viewKey, new TypeaheadView({
                        input: $input,
                        eventBus: eventBus = new EventBus({
                            el: $input
                        }),
                        datasets: datasets
                    }));
                    $.when.apply($, deferreds).always(function () {
                        utils.defer(function () {
                            eventBus.trigger("initialized");
                        });
                    });
                }
            },
            destroy: function () {
                return this.each(destroy);
                function destroy() {
                    var $this = $(this), view = $this.data(viewKey);
                    if (view) {
                        view.destroy();
                        $this.removeData(viewKey);
                    }
                }
            },
            setQuery: function (query) {
                return this.each(setQuery);
                function setQuery() {
                    var view = $(this).data(viewKey);
                    view && view.setQuery(query);
                }
            }
        };
        jQuery.fn.typeahead = function (method) {
            if (methods[method]) {
                return methods[method].apply(this, [].slice.call(arguments, 1));
            } else {
                return methods.initialize.apply(this, arguments);
            }
        };
    })();
})(window.jQuery);
(function (o) {
    "use strict";
    tfl.logs.create("tfl.storage: loaded");
    var storage = window.localStorage;
    o.isLocalStorageSupported = function () {
        if ("localStorage" in window && window["localStorage"] !== null) {
            tfl.logs.create("tfl.storage: isLocalStorageSupported true");
            return true;
        } else {
            tfl.logs.create("tfl.storage: isLocalStorageSupported false");
            return false;
        }
    };
    o.set = function (key, value) {
        if (storage && key != undefined) {
            try {
                var parseValue = JSON.stringify(value);
                storage.setItem(key, parseValue);
                tfl.logs.create("tfl.storage.set: " + key + ":" + parseValue);
            } catch (e) {
                tfl.logs.create("tfl.storage.set:" + e.message, "error");
            }
        }
    };
    o.get = function (key, defaultValue) {
        if (storage) {
            try {
                var value = storage.getItem(key);
                if (value != null) {
                    tfl.logs.create("tfl.storage.get: " + key + ":" + value);
                    return JSON.parse(value);
                }
            } catch (e) {
                tfl.logs.create("tfl.storage.get:" + e.message, "error");
            }
        }
        if (defaultValue !== '') {
            tfl.logs.create("tfl.storage.get: " + key + ":" + defaultValue);
            return defaultValue;
        }
        tfl.logs.create("tfl.storage.get: " + key + ": []");
        return [];
    };

    o.showIfNotDisabled = function (key, callbackShowOnEnabledOrFailure) {
        if (storage) {
            try {
                var isHidden = JSON.parse(o.get(key, false));
                if (!isHidden) {
                    callbackShowOnEnabledOrFailure();
                }
            } catch (e) {
                callbackShowOnEnabledOrFailure();
            }
        } else {
            callbackShowOnEnabledOrFailure();
        }
    };
}(window.tfl.storage = window.tfl.storage || {}));
var tfl = tfl || {};
(function (tfl) {
    "use strict";
    tfl.logs.create("tfl.core: loaded");
    tfl.supportsSVG = function () {
        return !!document.createElementNS && !!document.createElementNS('http://www.w3.org/2000/svg', 'svg').createSVGRect;
    };
    tfl.initialize = function () {
        tfl.logs.create("tfl.core: started");
        if (!tfl.supportsSVG()) {
            $(document.body).addClass("nosvg");
        }
        tfl.responsive.initialize();
        tfl.interactions.initialize();
        tfl.lazyLoading.initialize();
        //tfl.socialMedia.init();
    };
    tfl.getQueryParam = function (name) {
        name = name.replace(/[\[]/, "\\\[").replace(/[\]]/, "\\\]").toLowerCase();
        var regexS = "[\\?&]" + name + "=([^&#]*)";
        var regex = new RegExp(regexS);
        var results = regex.exec(window.location.search.toLowerCase());
        if (results == null) return "";
        else return decodeURIComponent(results[1].replace(/\+/g, " "));
    };
    tfl.interactions = {
        initialize: function () {
            tfl.logs.create("tfl.interactions: started");
            $(".top-row .more").click(tfl.interactions.toggleNavigation);
            //REMOVED FOR BETA - RE ADD LATER
            //$(".show-mobile-search").click(tfl.interactions.toggleMobileSearch);
            $("#footer h2.heading").wrap('<a href="#info-for" class="info-for-link" />');
            $("#footer a.info-for-link").click(tfl.interactions.toggleFooter);
            // tfl.interactions.setupSelectBoxes();
            // tfl.interactions.setupCheckboxLists();
            // tfl.interactions.setupHorizontalToggles();
            // tfl.interactions.setupCustomInputs();
            // tfl.interactions.setupCalendars();
            $(".always-visible").unbind("click").click(tfl.interactions.toggleExpandableBox);
            $(".checkbox-list input[type='checkbox'], .radiobutton-list input[type='radio']").click(tfl.interactions.toggleCustomInput);
            $(".styled-checkbox input[type='checkbox']").click(tfl.interactions.toggleCustomInput);
            //new FastClick(document.body);

            //REMOVE AFTER BETA
            $(".search-tools").attr("href", "/error/coming-soon").attr("data-pull-content", "#body");//.click(tfl.navigation.pullContentHandler);

            //cookie policy notice
            $(".cookie-policy-button a").click(tfl.interactions.hideCookiesPolicyNotice);
            tfl.storage.showIfNotDisabled("cookiePolicyNoticeHidden", function () {
                $(".cookie-policy-notice").show();
            });
            //if ("localStorage" in window && window["localStorage"] !== null) {
            //    try {
            //        var noticeHidden = JSON.parse(window.localStorage.getItem("cookiePolicyNoticeHidden"));
            //        if (!noticeHidden.hidden) {
            //            $(".cookie-policy-notice").show();
            //        }
            //    } catch (e) {
            //        $(".cookie-policy-notice").show();
            //    }
            //}
            //key press for tabbing
            $(document).keydown(function (e) {
                var el = [];
                if (e.shiftKey && e.keyCode == 9) { //shift+tab
                    if ($(document.activeElement).attr("data-jumpback")) {
                        el = $($(document.activeElement).attr("data-jumpback"));
                    }
                } else if (e.which == 9) { //tab
                    if ($(document.activeElement).attr("data-jumpto")) {
                        el = $($(document.activeElement).attr("data-jumpto"));
                    }
                }
                if (el.length > 0) {
                    el.focus();
                    $(".ui-menu-item").hide();//hide autocomplete options
                    return false;
                }
            });
            // add "autocomplete = off" to text boxes (browser autocomplete)
            $("input[type='text']").attr("autocomplete", "off");
        },

        hideCookiesPolicyNotice: function () {
            $(".cookie-policy-notice").hide();
            tfl.storage.set("cookiePolicyNoticeHidden", "true");
            return false;
        },
        toggleNavigation: function () {
            if ($(".top-row.show-search").is(":visible")) {
                tfl.interactions.toggleMobileSearch();
            }
            $(".top-row .more").children("a").toggleClass("expanded");
            $(".extra-nav").toggleClass("expanded");
            $("#container").toggleClass("menu-visible");
            return false;
        },
        toggleMobileSearch: function () {
            if ($(".extra-nav.expanded").is(":visible")) {
                tfl.interactions.toggleNavigation();
            }
            var el = $(".show-mobile-search");
            var journeyPlannerButton = ".journey-form #plan-a-journey tabbable:first,#main-hero :tabbable:first";
            //var homePageElement = "#main-hero :tabbable:first";
            var searchBox = "#search-box";
            if (el.attr("data-jumpto") === journeyPlannerButton) {
                el.attr("data-jumpto", searchBox);
            } else if (el.attr("data-jumpto") === searchBox) {
                el.attr("data-jumpto", journeyPlannerButton);
            }
            $(".top-row").toggleClass("show-search");
            return false;
        },
        toggleFooter: function () {
            $(".primary-footer").toggleClass("expanded");
            return false;
        },
        toggleExpandableBox: function () {
            $(this).parent().toggleClass("expanded");
            return false;
        },
        toggleCustomInput: function (e) {
            var radiobuttonList = $(this).parents(".radiobutton-list");
            if (radiobuttonList.length > 0) {
                $(this).parent().parent().children("li").removeClass("ticked");
            }
            $(this).parent().toggleClass("ticked");
            var toCheckboxList = $(this).parent().parent().prev("select.to-checkbox-list");
            if (toCheckboxList.length > 0) {
                var checkbox = $(this);
                var waitToUpdate = function () {
                    var selected = checkbox.attr("checked") === "checked";
                    if (selected) {
                        toCheckboxList.find("option[value='" + checkbox.attr("id") + "']").attr("selected", "selected");
                    } else {
                        toCheckboxList.find("option[value='" + checkbox.attr("id") + "']").removeAttr("selected");
                    }
                };
                setTimeout(waitToUpdate, 10);
            }
        },
        setupSelectBoxes: function () {
            $(".selector select").before("<span />").each(function () {
                tfl.interactions.setSelectBoxSpan(this);
                $(this).focus(function () {
                    $(this).parent().addClass("focus");
                });
                $(this).blur(function () {
                    $(this).parent().removeClass("focus");
                });
            }).change(tfl.interactions.updateSelectBox);
        },
        setSelectBoxSpan: function (selectbox) {
            $(selectbox).siblings("span").text($(selectbox).children("option:selected").text());
        },
        updateSelectBox: function (e) {
            tfl.interactions.setSelectBoxSpan(this);
        },
        setupCheckBoxSelectall: function (el) {
            var div = $("<div class='select-deselect-option'><a href='javascript:void(0);'>Deselect All</a></div>");
            var link = div.children("a");
            $(el).parent().prepend(div);
            var inputs = $(el).find("input");
            var clickHandler = function () {
                var allTicked = true;
                for (var i = 0; i < inputs.length; i++) {
                    if (!$(inputs[i]).is(":checked")) {
                        allTicked = false;
                        break;
                    }
                }
                if (allTicked) {
                    link.text("Deselect all");
                    link.removeClass("select-all");
                } else {
                    link.text("Select all");
                    link.addClass("select-all");
                }
            };
            inputs.click(clickHandler);
            clickHandler();
            tfl.interactions.modeSelectAll(link);
        },
        modeSelectAll: function (el) {
            $(el).click(function () {
                var list = $(this).parent().siblings("ul");
                if (!$(this).hasClass('select-all')) {
                    list.children("li.ticked").each(function () {
                        $(this).children("input").trigger("click");
                    });
                } else {
                    list.children("li").not(".ticked").each(function () {
                        $(this).children("input").trigger("click");
                    });
                    $(this).removeClass("select-all").text("Deselect all");
                }
            });
        },
        setupCheckboxLists: function () {
            tfl.logs.create("tfl.interactions: setup checkbox lists");
            $(".to-checkbox-list").each(function () {
                var list = $("<ul class='clearfix'></ul>");
                var i = 1;
                var numItems = $(this).children("option").length;
                $(this).children().each(function () {
                    var item = $("<li></li>");
                    if (i % 2 === 1) {
                        item.addClass("odd");
                    }
                    var value = $(this).attr("value");
                    var caption = $(this).text();
                    var input = $("<input type='checkbox' />")
                        .attr("id", value);
                    if ($(this).attr("selected") !== undefined) {
                        input.attr("checked", "checked");
                    }
                    item.append(input);

                    var label = $("<label for='" + value + "' class='" + value + "'></label>");
                    label.append(caption);
                    item.append(label);
                    list.append(item);
                    i++;
                });
                $(this).after(list);
                if ($(this).hasClass("select-all")) {
                    list.addClass("select-all");
                    tfl.interactions.setupCheckBoxSelectall(list);
                }

                $(this).hide();
            });
        },
        setupHorizontalToggles: function () {
            tfl.logs.create("tfl.interactions: setup horizontal toggles");
            $(".horizontal-toggle-buttons").each(function () {
                var inputs = $(this).find("input");
                inputs.filter(":checked").parent().addClass("selected");
                inputs.focus(function () {
                    $(this).parent().addClass("focus");
                });
                inputs.blur(function () {
                    $(this).parent().removeClass("focus");
                });
                var parents = inputs.parent();
                inputs.change(function () {
                    parents.removeClass("selected");
                    $(this).parent().addClass("selected");
                });

            });
        },
        setupCustomInputs: function () {
            $(".checkbox-list input[type='checkbox'], .radiobutton-list input[type='radio'], .styled-checkbox input[type='checkbox']").each(function () {
                if ($(this).attr("checked") === "checked") {
                    $(this).parent().addClass("ticked");
                }
                $(this).focus(function () {
                    $(this).parent().addClass("focus");
                });
                $(this).blur(function () {
                    $(this).parent().removeClass("focus");
                });
            });
        },
        setupCalendars: function () {
            $(".fc-calendar-container").each(function () {
                var $this = $(this);
                $this.wrap('<div class="fc-calendar-wrapper clearfix" />');
                var $wrap = $this.parent();
                var $cal = $this.calendario({
                    weekabbrs: ["S", "M", "T", "W", "T", "F", "S"],
                    displayWeekAbbr: true
                });
                $wrap.prepend("<div class='current-month'><a href='javascript:void(0);' class='calendar-previous-month' /><span class='calendar-month'>" + $cal.getMonthName() + "</span> <span class='calendar-year'>" + $cal.getYear() + "</span><a href='javascript:void(0);' class='calendar-next-month' /></div>");
                $wrap.find('.calendar-next-month').on('click', function () {
                    $cal.gotoNextMonth(updateMonthYear);
                });
                $wrap.find('.calendar-previous-month').on('click', function () {
                    $cal.gotoPreviousMonth(updateMonthYear);
                });
                var $month = $wrap.find('.calendar-month');
                var $year = $wrap.find('.calendar-year');
                var updateMonthYear = function () {
                    $month.html($cal.getMonthName());
                    $year.html($cal.getYear());
                }
            });
        },
        isTouchDevice: function () {
            return !!('ontouchstart' in window) || !!('onmsgesturechange' in window);
        }
    };
    tfl.responsive = {
        initialize: function () {
            tfl.logs.create("tfl.responsive: started");
            tfl.responsive.setupBreakpoints();

            $(".top-row .logo").after("<ul class='collapsible-menu clearfix'>&nbsp;</ul>");
            var menu = $(".collapsible-menu");
            menu.append($(".plan-journey").clone());
            menu.append($(".status-update").clone());
            menu.append($(".maps").clone());
            menu.append($(".fares-and-payments").clone());
            $(".collapsible-menu a").removeAttr("data-jumpback");
            menu.append("<li class='more'><a href='javascript:void(0)' data-jumpto='.extra-nav a:visible:first'><span class='text'>More...</span><span class='arrow'>&nbsp;</span></a></li></li>");
            $(".texts").append('<div class="search not-for-beta"><a href="/error/coming-soon" data-pull-content="#body" class="show-mobile-search" data-jumpto=".journey-form #plan-a-journey tabbable:first,#main-hero :tabbable:first"><span class="search-icon hide-text">Search</span><span class="collapse"></span><span class="expand"></span></a></div>');
            $(".top-row-extras").prepend("<div class='more'><a href='javascript:void(0)' data-jumpto='.extra-nav a:first:visible'><span class='text'>Menu</span><span class='arrow'>&nbsp;</span></a></li></div>");
            //make sidenav move to bottom for mobile
            $(".moving-source-order").appendAround();
            tfl.responsive.initTables();
            tfl.responsive.initBreadcrumbs();
            tfl.responsive.initNewsTeasers();
            tfl.responsive.initLazyLoadedImages();
        },
        setupBreakpoints: function () {
            $(window).setBreakpoints({
                distinct: false,
                breakpoints: tfl.settings.devices
            });
            //$(tfl.settings.devices).each(function () {
            //    mql(this.resolution, this.name, tfl.responsive.changeBreakpoint);
            //});
        },
        changeBreakpoint: function (mql) {
            if (mql.matches) {
                $(window).trigger('enterBreakpoint' + mql.name);
                $('body').addClass('breakpoint-' + mql.name);
            } else {
                $(window).trigger('exitBreakpoint' + mql.name);
                $('body').removeClass('breakpoint-' + mql.name);
            }
            $(window).trigger('changeBreakpoint');
        },
        allTables: [],
        tableCellPadding: 5,
        tableShadowWidth: 15,
        ResponsiveTable: function (el) {
            var table = el;
            var parent = $(table).parent();
            var left = null;
            var top = null;
            var width = null;
            var height = null;
            var hiding = false;
            var leftShadow = null;
            var rightShadow = null;
            var parentWidth = null;
            var scrollMax = null;
            var firstRow = null;
            var firstRowHeight = 0;
            var firstRowTop = 0;
            var firstRowLeft = 0;
            var firstRowFixed = false;
            var firstCol = null;
            var firstColWidth = 0;
            var firstColLeft = 0;
            return {
                init: function () {
                    this.onResize();
                    parent.scroll(this.onParentScroll);
                },
                onResize: function () {
                    var offset = $(parent).offset();
                    left = offset.left;
                    top = offset.top;
                    width = $(table).width();
                    height = $(table).height();
                    parentWidth = $(parent).width();
                    if (firstRow) {
                        firstRowHeight = firstRow.height();
                        var toffset = $(table).offset();
                        firstRowTop = toffset.top;
                        firstRowLeft = toffset.left;
                        if (hiding) {
                            firstRow.parent().css({ width: parentWidth, left: left });
                            this.onWindowScroll($(window).scrollTop());
                        }
                    }
                    if (firstCol) {
                        firstColWidth = firstCol.width() - 2 * tfl.responsive.tableCellPadding;
                    }
                    if (width > parentWidth) {
                        if (!hiding) {
                            this.startHiding();
                        } else {
                            scrollMax = width - parentWidth;
                            this.onParentScroll();
                        }
                    } else if (hiding) {
                        this.stopHiding();
                    }
                },
                onWindowScroll: function (scrollTop) {
                    if (hiding && !firstRowFixed && (top < scrollTop && scrollTop <= top + height - firstRowHeight)) {
                        firstRowFixed = true;
                        $(firstRow).css({ marginLeft: -1 * $(parent).scrollLeft() });
                        $(firstRow).parent().removeClass("hidden");
                    } else if (firstRowFixed) {
                        //if we scroll above or below the table, get rid of the fixed heading
                        if (top >= scrollTop || scrollTop > top + height - firstRowHeight) {
                            firstRowFixed = false;
                            $(firstRow).parent().addClass("hidden");
                        }
                    }
                },
                onParentScroll: function () {
                    var pScrollLeft = $(parent).scrollLeft();
                    $(firstRow).css({ marginLeft: (-1 * pScrollLeft) });
                    leftShadow.css({ left: firstColWidth + 2 * tfl.responsive.tableCellPadding - tfl.responsive.tableShadowWidth + Math.min(pScrollLeft, tfl.responsive.tableShadowWidth) });
                    rightShadow.css({ width: Math.min(scrollMax - pScrollLeft, tfl.responsive.tableShadowWidth) });
                },
                startHiding: function () {
                    hiding = true;
                    parent.addClass("hiding");
                    if (parent.children(".hiding-table").length > 0) {
                        firstRow.css({ width: width });
                        parent.children(".hiding-table").show();
                    } else {
                        this.createFixedHeadings();
                    }
                    parent.parent().addClass("hiding");
                },
                stopHiding: function () {
                    hiding = false;
                    parent.removeClass("hiding");
                    parent.parent().removeClass("hiding");
                    parent.parent().find(".hiding-table").hide();
                    firstRowFixed = false;
                    $(firstRow).parent().addClass("hidden");
                },
                createFixedHeadings: function () {
                    firstRow = $("<table class='first-row'></table>");
                    var fr = $(table).find("tr:first-child");
                    firstRowHeight = fr.height();
                    firstRow.append(fr.clone().css({ height: firstRowHeight }));

                    firstRow.find("th").replaceWith(function () {
                        return $("<td />", { html: $(this).html() });
                    });

                    var frCells = fr.find("th, td");
                    $(firstRow).find("th, td").each(function (i) {
                        if (i === 0) {
                            $(this).html("<span>" + $(this).html() + "</span>");
                        }
                        $(this).width($(frCells.get(i)).width());
                    });

                    firstCol = $("<table class='hiding-table first-col'></table>").css({ top: 0 });
                    firstColWidth = 0;
                    firstColLeft = 0;
                    $(table).find("th:first-child,td:first-child").each(function (i) {
                        if (i === 0) {
                            firstColWidth = $(this).width();
                            firstColLeft = $(this).offset().left;
                        }
                        var r = $("<tr></tr>");
                        //$(this).css({height : $(this).height()});
                        r.append($(this).clone().css({ height: $(this).height() }));
                        firstCol.append(r);
                    });

                    parent.wrap("<div class='responsive-table-wrapper'></div>");
                    parent.parent().append(firstCol.css({ width: firstColWidth, position: "absolute" }));

                    firstRow.wrap("<div class='hidden'></div>");
                    firstCol.after(firstRow.css({ width: width }).parent().css({
                        overflow: "hidden",
                        width: parentWidth,
                        position: "fixed",
                        top: 0,
                        boxShadow: "0px 5px 5px -3px rgba(0, 0, 0, 0.3)"
                    }));
                    //parent.append(firstRow.css({ width: width }));

                    firstRowTop = $(firstRow).parent().offset().top;
                    firstRowLeft = $(table).offset().left;
                    leftShadow = $("<div class='responsive-table-shadow left-shadow' />");
                    leftShadow.css({ left: firstColWidth + 2 * tfl.responsive.tableCellPadding - tfl.responsive.tableShadowWidth, height: height });
                    rightShadow = $("<div class='responsive-table-shadow right-shadow' />");
                    rightShadow.css({ right: 0, height: height });
                    firstCol.before(leftShadow);
                    firstCol.before(rightShadow);

                    scrollMax = width - parentWidth;
                    //call the window scroll event in case resizing the window triggered
                    //the creation of the top and left bars and we need to make them fixed
                    this.onWindowScroll($(window).scrollTop());
                }
            };
        },
        ResponsiveBreadcrumbs: function (el) {
            var breadcrumbs = el;
            var parent = breadcrumbs.parent();
            var items = breadcrumbs.find("li");
            var ellipsis = null;
            var ellipsisWidth = 0;
            var breakpointArrowWidth = 10;
            var measurements = [];
            var breakpoints = [];
            var totalWidth = 0;
            var width = 0;
            var currentBreakpoint = 0;
            var hiding = false;
            var scrolling = false;
            var resizeFunc = null;
            return {
                init: function () {
                    //parent.scroll(this.onParentScroll);
                    ellipsis = $("<li class='ellipsis hidden'><a href='javascript:void(0)'>...</a></li>");
                    $(items[0]).after(ellipsis);
                    ellipsisWidth = ellipsis.width();
                    ellipsis.click(this.makeScrollable);
                    items.each(function () {
                        var w = $(this).width()
                        measurements.push(w);
                        totalWidth += w;
                    });
                    totalWidth += breakpointArrowWidth;
                    breakpoints.push(totalWidth);
                    var c = totalWidth;
                    for (var i = 1; i < measurements.length - 1; i++) {
                        c -= measurements[i];
                        breakpoints.push(c + ellipsisWidth);
                    }
                    resizeFunc = $.proxy(this.onResize, this);
                    $(window).on("resize", resizeFunc);
                    this.onResize();
                },
                onResize: function () {
                    width = breadcrumbs.width();
                    if (width < totalWidth) {
                        if (width < breakpoints[currentBreakpoint]) {
                            hiding = true;
                            ellipsis.removeClass("hidden");
                            while (width < breakpoints[currentBreakpoint]) {
                                this.hideItem(currentBreakpoint);
                                currentBreakpoint++;
                            }
                        } else if (width > breakpoints[currentBreakpoint]) {
                            while (width > breakpoints[currentBreakpoint]) {
                                this.showItem(currentBreakpoint);
                                currentBreakpoint--;
                            }
                        }
                    } else if (hiding) {
                        ellipsis.addClass("hidden");
                        hiding = false;
                        while (width > breakpoints[currentBreakpoint]) {
                            this.showItem(currentBreakpoint);
                            currentBreakpoint--;
                        }
                        if (currentBreakpoint < 0) {
                            currentBreakpoint = 0;
                        }
                    }
                },
                hideItem: function (idx) {
                    $(items[idx + 1]).hide();
                },
                showItem: function (idx) {
                    $(items[idx + 1]).show();
                },
                makeScrollable: function () {
                    if (tfl.interactions.isTouchDevice()) {
                        parent.addClass("scrolling");
                        breadcrumbs.css({ width: totalWidth + "px" });
                    }
                    ellipsis.addClass("hidden");
                    items.each(function () {
                        $(this).show();
                    });
                    $(window).off("resize", resizeFunc);
                    return false;
                }
            };
        },
        initNewsTeasers: function () {
            var noBackgroundImageClass = 'no-background-image';
            var heroObjs = $('[data-highlight-image]');
            var containers = [];
            $(window).one("enterBreakpointMedium", function () {
                $.each(heroObjs, function (k, v) {
                    var obj = $(v), imgFor = $(obj.attr('data-highlight-for'));
                    var newObj = obj.find('a').clone();
                    newObj.find('img').each(function () {
                        $(this).remove();
                    });
                    containers.push(imgFor);
                    imgFor.attr('style', 'background-image: url(' + obj.attr('data-highlight-image') + ')');
                    imgFor.find('.teaser-text').show().append(newObj);
                    obj.addClass(obj.attr('data-highlight-size'));
                });
            });
            $(window).bind("exitBreakpointMedium", function () {
                $.each(containers, function (k, v) {
                    $(v).addClass(noBackgroundImageClass);
                });
            });
            $(window).bind("enterBreakpointMedium", function () {
                $.each(containers, function (k, v) {
                    $(v).removeClass(noBackgroundImageClass);
                });
            });
            if ($("body.breakpoint-Medium").length > 0) {
                $(window).trigger('enterBreakpointMedium');
            }
            if ($("body.breakpoint-Large").length > 0) {
                $(window).trigger('enterBreakpointLarge');
            }
        },
        initTables: function () {
            //only tables in a table-container div will be made responsive
            $(".table-container table").each(function (i) {
                var t = new tfl.responsive.ResponsiveTable(this);
                t.init();
                tfl.responsive.allTables.push(t);
            });
            if (tfl.responsive.allTables.length > 0) {
                $(window).resize(function () {
                    for (var i = 0; i < tfl.responsive.allTables.length; i++) {
                        tfl.responsive.allTables[i].onResize();
                    }
                });
                $(window).scroll(function () {
                    var scrollTop = $(this).scrollTop();
                    for (var i = 0; i < tfl.responsive.allTables.length; i++) {
                        tfl.responsive.allTables[i].onWindowScroll(scrollTop);
                    }
                });
            }
        },
        initBreadcrumbs: function () {
            // var breadcrumbs = $("ul.breadcrumbs");
            // if (breadcrumbs) {
            // var b = new tfl.responsive.ResponsiveBreadcrumbs(breadcrumbs);
            // b.init();
            // }
        },
        initLazyLoadedImages: function () {
            $("[data-img]").each(function () {
                var that = this;
                var showImg = function () {
                    $(that).children("img").remove();
                    $(that).prepend("<img width='100%' src='" + $(that).attr("data-img") + "' alt='" + $(that).attr("data-img-alt") + "' />");
                };

                if ($(this).attr("data-img-breakpoint") === "medium") {
                    $(window).one("enterBreakpointMedium", showImg);
                    if ($("body.breakpoint-Medium").length > 0) {
                        $(window).trigger('enterBreakpointMedium');
                    }
                } else {
                    $(window).one("enterBreakpointLarge", showImg);
                    if ($("body.breakpoint-Large").length > 0) {
                        $(window).trigger('enterBreakpointLarge');
                    }
                }
            });
        }
    };
    tfl.lazyLoading = tfl.lazyLoading || {};
    tfl.lazyLoading.initialize = function () {
        tfl.logs.create("tfl.lazyLoading: started");
        if (tfl.lazyLoading.pageVideos) {
            var containers = $(".video-container");
            var videos = tfl.lazyLoading.pageVideos;
            for (var i = 0; i < videos.length; i++) {
                if (i === containers.length) {
                    break;
                }
                $(containers.get(i)).html("<iframe width='560' height='315' src='" + videos[i] + "' frameborder='0' allowfullscreen></iframe>");
            }
        }
    };
    tfl.initialize();
})(tfl);
(function (o) {
    "use strict";
    tfl.logs.create("tfl.geolocation: loaded");

    o.geocoder = null;

    o.geolocateMe = function (success, failure) {
        if (o.isGeolocationSupported) {
            navigator.geolocation.getCurrentPosition(success, failure);
        } else {
            return false;
        }
    };

    o.isGeolocationSupported = function () {
        if ("geolocation" in navigator && navigator["geolocation"] !== null) {
            tfl.logs.create("tfl.geolocation: isGeolocationSupported true");
            return true;
        } else {
            tfl.logs.create("tfl.geolocation: isGeolocationSupported false");
            return false;
        }
    }

    o.reverseGeocode = function (latLng, success, failure) {
        tfl.logs.create("tfl.geolocation: Attempting to reverse geocode position");
        if (!o.geocoder) {
            o.geocoder = new google.maps.Geocoder();
        }
        o.geocoder.geocode({ 'latLng': latLng }, function (results, status) {
            if (status == google.maps.GeocoderStatus.OK) {
                tfl.logs.create("tfl.geolocation: reverse geocode success");
                success(results[0]);
            } else {
                tfl.logs.create("tfl.geolocation: reverse geocode failure");
                failure();
            }
        });
    };

    o.formatAddress = function (result) {
        var addr = "";
        var isEmpty = true;
        for (var i = 0; i < result.address_components.length; i++) {
            var component = result.address_components[i];
            if ($.inArray("route", component.types) > -1) {
                if (!isEmpty) {
                    addr += ", ";
                }
                addr += component.long_name;
                isEmpty = false;
            }
            if ($.inArray("postal_code", component.types) > -1 || $.inArray("postal_code_prefix", component.types) > -1) {
                if (!isEmpty) {
                    addr += ", ";
                }
                addr += component.short_name;
                isEmpty = false;
            }
        }
        return addr;
    }
}(window.tfl.geolocation = window.tfl.geolocation || {}));
(function (o) {
    tfl.logs.create("tfl.navigation.pullContent: started");
    o.dataAttr = 'data-pull-content';
    var dataStorage = 'data-pull-content-storage';
    o.pullContentHandler = function (event) {
        event.preventDefault();
        var obj = $(this);
        var storageKey = obj.attr(dataStorage);
        var storage = tfl.storage.get(storageKey);
        if (storage) {
            return false;
        } else {
            tfl.storage.set(storageKey, 'true');
        }

        var content = obj.attr(o.dataAttr).split(',');
        var url = obj.attr('href');
        tfl.logs.create("tfl.navigation.pullContent: Clicked a pull link for " + url);
        if (url.length > 0) {
            $.ajax({
                url: url
            }).done(function (response) {
                    var html = $(response);
                    var toRender = $('<div></div>').addClass('alert-box');
                    for (var i = 0; i < content.length; i++) {
                        $.each(html.find(content[i].trim()), function (k, v) {
                            var snipet = $('<p></p>').append($(v)).html();
                            toRender.append(snipet);
                        });
                    }
                    toRender.find("#error-window").remove();
                    var closeLink = $("<a href='javascript:void(0)' class='close-alert-box'>close</a>");
                    toRender.prepend(closeLink);
                    $("#full-width-content").after(toRender);
                    closeLink.focus();
                    $("head").append('<style>.pull-content-active:after { height: ' + Math.max($(document.body).height(), toRender.height() + 70) + 'px}</stlye>');

                    var tabbables = toRender.find(":tabbable");
                    var last = $(tabbables[tabbables.length - 1]).addClass("alert-box-last-tab-target");
                    last.attr("data-jumpto", ".close-alert-box");
                    closeLink.attr("data-jumpback", ".alert-box-last-tab-target");
                    toRender.click(function (e) {
                        e.stopPropagation();
                    });
                    $('.close-alert-box-button').click(function () {
                        closeLink.click();
                    });
                    $(document.body).click(function (e) {
                        closeLink.click();
                    });
                    closeLink.click(function () {
                        toRender.remove();
                        $(document.body).removeClass("pull-content-active");
                        obj.focus();
                    });
                });

            $(document.body).addClass("pull-content-active");
        }
    };
    o.init = function () {
        tfl.logs.create("tfl.navigation.pullContent: initialised");
        $('[' + o.dataAttr + ']').click(o.pullContentHandler);
    };
    //$(window).one("enterBreakpointMedium", function () {
    o.init();
    //});
    //if we've already triggered the breakpoint before this has been called, trigger it automatically
    //if ($("body.breakpoint-Medium").length > 0) {
    //    $(window).trigger('enterBreakpointMedium');
    //}
})
    (window.tfl.navigation = window.tfl.navigation || {});
var tfl = tfl || {};
(function (tfl) {
    "use strict";
    tfl.logs.create("tfl.serviceStatus: loaded");
    tfl.serviceStatus = {
        settings: {
            recentlyViewedBusesDisplayLimit: 2,
            recentlyViewedBusesStoreLimit: 2629740000, //1 month in milliseconds
            favouriteBusesLimit: 5,
            noRecentlyViewBusesMsg: "No recently viewed buses",
            noFavouriteBusesMsg: "No favourite buses"
        },
        initWidget: function () {
        },
        initServiceBoardPage: function () {
            tfl.serviceStatus.buildDropDownHtml();
            //from Medium and above media screen size
            $(window).one("enterBreakpointMedium", function () {
                tfl.serviceStatus.dropDownMouseFunctionality();
            });
            if ($("body.breakpoint-Medium").length > 0) {
                $(window).trigger('enterBreakpointMedium');
            }
            //detect mode and run mode specific JS
            var statusMode = $(".service-status-selector").attr("data-mode");
            if (statusMode == "Buses") {
                tfl.serviceStatus.busStatusPage();
            }
            tfl.serviceStatus.ServiceStatusBoard();
            //tfl.serviceStatus.scrollToAnchor();
        },
        buildDropDownHtml: function () {
            // build mode selector button
            tfl.logs.create("tfl.serviceStatus: build drop down");
            var buttonHtml = $("<ul class='tabs-style-1'><li class='drop-down'><a href='/error/coming-soon' data-pull-content='#body' class='primary-dropdown-ico drop-down-button small not-for-beta' alt='Change mode of transport'>&nbsp;</a><a href='/error/coming-soon' data-pull-content='#body' class='primary-dropdown-ico drop-down-button large not-for-beta' alt='Change mode of transport'>Change mode&nbsp;</a></li></ul>");
            $(".selected-mode h1").after(buttonHtml);
            buttonHtml.find("[data-pull-content]").click(tfl.navigation.pullContentHandler);
            // build selectable-modes
            //REMOVED DURING BETA - UNCOMMENT WHEN MORE MODES ARE AVAILABLE
            /*
             var selectorHtml = "<div class='selectable-modes'><ul></ul></div>";
             $(".selected-mode").after(selectorHtml);
             var selectedMode = $(".service-status-selector").attr("data-mode");
             var modeLinks = $(".service-status-link");
             for (var i = 0; i < modeLinks.length; i++) {
             if ($(modeLinks[i]).find(".mode-name").text().toLowerCase() !== selectedMode.toLowerCase()) {
             $(".selectable-modes ul").append(modeLinks[i])
             }
             }
             $(".selectable-modes a").hover(function () {
             $(this).toggleClass("mode-staus-focus");
             });
             //click event
             $(".drop-down-button").click(tfl.serviceStatus.dropDownClick);
             */
            return false;
        },
        dropDownMouseFunctionality: function () {// mouse entering/leaving drop-down hiding
            //hide drop-down if move out of drop-down and drop-down-button
            $(".selectable-modes").on("mouseleave", function (e) {
                elMouseIsOver = document.elementFromPoint(e.clientX, e.clientY);
                if (elMouseIsOver.className.indexOf("drop-down") == -1) {
                    tfl.serviceStatus.hideDropDown();
                }
            });
            //hide drop-down if move out of sides or top of drop-down-button
            $(".drop-down-button").on("mouseleave", function (e) {
                if (!(e.pageY >= ($(".selected-mode .tabs-style-1").offset().top) + $(".drop-down-button").height())) {
                    tfl.serviceStatus.hideDropDown();
                }
            });
            //hide drop-down if click outside drop-down
            $("body > div:not(.drop-down-button.large,.selectable-modes)").click(function (e) {
                tfl.serviceStatus.hideDropDown();
            });
        },
        dropDownClick: function () {
            if ($(".selected-mode li.selected").is(":visible")) {
                tfl.serviceStatus.hideDropDown();
            } else {
                tfl.serviceStatus.showDropDown();
            }
            return false;
        },
        hideDropDown: function () {
            $(".selectable-modes").hide();
            $(".selected-mode li").removeClass("selected");
        },
        showDropDown: function () {
            $(".selectable-modes").show();
            $(".selected-mode li").addClass("selected");
        },
        //Mode specific function
        //busStatusPage: function () {
        //    /*ToDo:
        //        1) Build favouriates HTML as required (see partial view)
        //        2) Build recently viewed HTML as required (see partial view)
        //        3) Create CSS for other device sizes. Only mobile complete so far.
        //        4) Search functionality needs to call API.
        //        5) Look up dirsuptions with AJAX API call when user clicks favourites or recently viewed.
        //        6) Disruption info for searched buses in API.
        //    */

        //    //favourites
        //    tfl.serviceStatus.getFavouriteBuses();
        //    //tfl.serviceStatus.buildFavouriteBusesHtml();
        //    //tfl.serviceStatus.setFavouriteBuses("test");

        //    //recently viewed
        //    tfl.serviceStatus.getRecentlyViewedBuses();
        //    //tfl.serviceStatus.buildRecentlyViewedBusesHtml();

        //    //bus search results
        //    tfl.serviceStatus.buildFavouriteButtons();

        //    //testing fav icon clicl function
        //    $(".fav-button").click(function (e) {
        //        e.stopPropagation();
        //        //$(".always-visible").unbind("click")
        //        //$(".always-visible").click(function () {
        //        //    tfl.interactions.toggleExpandableBox;
        //        //}).children().click(function(e) {
        //        //    return false;
        //        //});
        //    });

        //},
        ////getFavouriteBuses: function () {
        ////    //ToDo need to check API for disruptions and use for loop to add in disruption = true.
        ////    //tfl.serviceStatus.favouriteBuses = [];
        ////    tfl.serviceStatus.favouriteBuses = tfl.storage.get("favouriteBuses", []);

        ////    /*********************************************/
        ////    //testing: add fav bus stop to local storage
        ////    //if (tfl.serviceStatus.favouriteBuses.length == 0) {
        ////    //    var newItem = {
        ////    //        name: "Cromwell Road",
        ////    //        destination: "Clapham Junction",
        ////    //        number: "F",
        ////    //        disruption: false
        ////    //    };
        ////    //    tfl.serviceStatus.favouriteBuses.push(newItem);
        ////    //    tfl.storage.set("favouriteBuses", tfl.serviceStatus.favouriteBuses);
        ////    //}
        ////    /*********************************************/

        ////},
        //setFavouriteBuses: function (newName, newDestination, newNumber, addNew) {
        //    if (newName != null) {
        //        if (newDestination == null) newDestination = "";
        //        if (newNumber == null) newNumber = "";
        //        if (addNew) {
        //            var newItem = {
        //                name: newName,
        //                destination: newDestination,
        //                number: newNumber,
        //                disruption: false
        //            };
        //            // add in new favourite
        //            tfl.serviceStatus.favouriteBuses.splice(0, 0, newItem);
        //        } else {
        //            // remove item
        //            for (var i = 0; i < tfl.serviceStatus.favouriteBuses.length; i++) {
        //                var favouriteBus = tfl.serviceStatus.favouriteBuses[i]
        //                if (favouriteBus.name == newName && favouriteBus.number == newNumber && favouriteBus.destination == newDestination) {
        //                    tfl.serviceStatus.favouriteBuses.splice(i, 1); //remove from array
        //                }
        //            }
        //        }
        //    }
        //    //limit number of recent searches that are stored in the autocomplete list
        //    if (tfl.serviceStatus.favouriteBuses.length > tfl.serviceStatus.settings.favouriteBusesLimit) {
        //        tfl.serviceStatus.favouriteBuses.length = tfl.serviceStatus.settings.favouriteBusesLimit;
        //    }
        //    tfl.storage.set("favouriteBuses", tfl.serviceStatus.favouriteBuses);
        //},
        //getRecentlyViewedBuses: function () {
        //    //ToDo need to check API for disruptions and use for loop to add in disruption = true.
        //    //tfl.serviceStatus.recentlyViewedBuses = [];
        //    tfl.serviceStatus.recentlyViewedBuses = tfl.storage.get("recentlyViewedBuses", []);
        //    if (tfl.serviceStatus.recentlyViewedBuses.length > 0) {
        //        // remove old viewed buses
        //        var timeNow = new Date().getTime();
        //        for (var i = 0; i < tfl.serviceStatus.recentlyViewedBuses.length; i++) {
        //            var j = tfl.serviceStatus.recentlyViewedBuses[i];
        //            if (j.lastSearchDate < (timeNow - tfl.serviceStatus.recentlyViewedBuses.recentlyViewedBusesStoreLimit)) {
        //                tfl.serviceStatus.recentlyViewedBuses.splice(i, 1); //remove from array
        //            }
        //        }
        //    }

        //    /*********************************************/
        //    //testing: add bus stop to local storage
        //    //need to store recent view on successful search result and viewing of bus
        //    //if (tfl.serviceStatus.recentlyViewedBuses.length == 0) {
        //    //    var timeNow = new Date().getTime();
        //    //    var newItem = {
        //    //        name: "Cromwell Road",
        //    //        destination: "Clapham Junction",
        //    //        number: "F",
        //    //        lastSearchDate: timeNow
        //    //    };
        //    //    tfl.serviceStatus.recentlyViewedBuses.push(newItem);
        //    //    tfl.storage.set("recentlyViewedBuses", tfl.serviceStatus.recentlyViewedBuses);
        //    //}
        //    /*********************************************/
        //},
        //setRecentlyViewedBuses: function (newName, newDestination, newNumber, addNew) {
        //    if (newName != null) {
        //        if (newDestination == null) newDestination = "";
        //        if (newNumber == null) newNumber = "";
        //        if (addNew) {
        //            var timeNow = new Date().getTime();
        //            var newItem = {
        //                name: newName,
        //                destination: newDestination,
        //                number: newNumber,
        //                lastSearchDate: timeNow
        //            };
        //            // add in new recently view item
        //            tfl.serviceStatus.recentlyViewedBuses.splice(0, 0, newItem);
        //        } else {
        //            // remove item
        //            for (var i = 0; i < tfl.serviceStatus.recentlyViewedBuses.length; i++) {
        //                var recentlyViewedBus = tfl.serviceStatus.recentlyViewedBuses[i]
        //                if (recentlyViewedBus.name == newName && recentlyViewedBus.number == newNumber && recentlyViewedBus.destination == newDestination) {
        //                    tfl.serviceStatus.recentlyViewedBuses.splice(i, 1); //remove from array
        //                }
        //            }
        //        }
        //    }
        //    // remove old viewed buses
        //    var timeNow = new Date().getTime();
        //    for (var j = 0; j < tfl.serviceStatus.recentlyViewedBuses.length; j++) {
        //        var k = tfl.serviceStatus.recentlyViewedBuses[j];
        //        if (k.lastSearchDate < (timeNow - tfl.serviceStatus.recentlyViewedBuses.recentlyViewedBusesStoreLimit)) {
        //            tfl.serviceStatus.recentlyViewedBuses.splice(j, 1); //remove from array
        //        }
        //    }
        //    tfl.storage.set("recentlyViewedBuses", tfl.serviceStatus.recentlyViewedBuses);
        //},
        //buildFavouriteButtons: function () { //to build fav button on bus search results
        //    tfl.logs.create("adding bus favourite buttons");
        //    var busSearchResults = $(".bus-stop-search-results .bus-stop .plain-button");
        //    var favButtonHtml, busStopName, busStopNumber, busStopDestination, favouriteTicked;
        //    for (i = 0; i < busSearchResults.length; i++) {
        //        busSearchResult = busSearchResults[i];
        //        busStopName = $(busSearchResult).find(".stop-name").attr("data-name");
        //        busStopNumber = $(busSearchResult).find(".stop-number").attr("data-number");
        //        busStopDestination = $(busSearchResult).find(".destination-name").attr("data-destination");
        //        favouriteTicked = "unticked";// default is to be unticked
        //        //is busStop a favourite?
        //        for (j = 0; j < tfl.serviceStatus.favouriteBuses.length; j++) {
        //            favouriteBus = tfl.serviceStatus.favouriteBuses[j];
        //            if (favouriteBus.name == busStopName && favouriteBus.number == busStopNumber && favouriteBus.destination == busStopDestination) {
        //                favouriteTicked = "";
        //            }
        //        }
        //        //create HTML
        //        favButtonHtml = "<a href='javascript:void(0)' class='fav-button'><span class='fav-icon " + favouriteTicked + "' data-number='" + busStopNumber + "' data-name='" + busStopName + "' data-destination='" + busStopDestination + "'>&nbsp;</span></a>";
        //        $(busSearchResult).after(favButtonHtml);
        //    }
        //},
        //buildFavouriteBusesHtml: function () {
        //    var favHtml = "<span class='fav-icon'>&nbsp;</span><h2>Favourites</h2>"
        //    $(".bus-stop-favourites").append(favHtml);

        //    //call shared buildBusStopHtml
        //    tfl.serviceStatus.buildBusStopHtml(tfl.serviceStatus.favouriteBuses, tfl.serviceStatus.settings.favouriteBusesLimit, ".bus-stop-favourites", tfl.serviceStatus.settings.noFavouriteBusesMsg);
        //    //favourite - fav star click
        //    $(".bus-stop-favourites .accordion-heading .fav-icon").click(function () {
        //        //add clicked item from recently viewed
        //        tfl.serviceStatus.setRecentlyViewedBuses($(this).attr('data-name'), $(this).attr('data-destination'), $(this).attr('data-number'), true);
        //        //remove clicked item as favourite
        //        tfl.serviceStatus.setFavouriteBuses($(this).attr('data-name'), $(this).attr('data-destination'), $(this).attr('data-number'));
        //        //clear favs and recently viewed display and re-draw both.
        //        tfl.serviceStatus.favStarClickRebuild();
        //        return false;
        //    });
        //},
        //buildRecentlyViewedBusesHtml: function () {
        //    var ulHtml = "<h2>Recently viewed</h2><ul class='recently-viewed'></ul>"
        //    $(".bus-stop-recently-viewed").append(ulHtml);
        //    tfl.serviceStatus.buildBusStopHtml(tfl.serviceStatus.recentlyViewedBuses, tfl.serviceStatus.settings.recentlyViewedBusesDisplayLimit, ".bus-stop-recently-viewed", tfl.serviceStatus.settings.noRecentlyViewBusesMsg, "unticked");
        //    //recently viewed - fav start click
        //    $(".recently-viewed .fav-icon.unticked").click(function () {
        //        //add clicked item as favourite
        //        tfl.serviceStatus.setFavouriteBuses($(this).attr('data-name'), $(this).attr('data-destination'), $(this).attr('data-number'), true);
        //        //remove clicked item from recently viewed
        //        tfl.serviceStatus.setRecentlyViewedBuses($(this).attr('data-name'), $(this).attr('data-destination'), $(this).attr('data-number'));
        //        //clear favs and recently viewed display and re-draw both.
        //        tfl.serviceStatus.favStarClickRebuild();
        //        return false;
        //    });
        //},
        //buildBusStopHtml: function (array, arrayLimit, appendEl, elseMsg, favStarUnticked) {
        //    var liHtml, busStop, disrupted;
        //    var appendEl2 = appendEl + " .accordion-heading";
        //    var accordionHtml = "<div class='expandable-box'><div class='content'><div class='always-visible'><a href='javascript:void(0);' class='controls'>&nbsp;</a><div class='accordion-heading'></div></div></div></div>";
        //    if (favStarUnticked == null) favStarUnticked = "";
        //    if (array.length > 0) {
        //        for (var i = 0; i < array.length; i++) {
        //            $(appendEl).append(accordionHtml);
        //            //ToDo run API call to see whether this bus stop has a disruption or not
        //            disrupted = "disrupted";
        //            busStop = array[i];
        //            liHtml = "<ul class='bus-stop'><li class='clearfix'><a href='javascript:void(0)' class='plain-button'><span class='visually-hidden'>Stop number:</span><span class='stop-number " + disrupted + "'>" + busStop.number + "</span>";
        //            liHtml += "<div class='stop-and-destination'><span class='visually-hidden'>Stop name:</span><span class='stop-name'>" + busStop.name + "</span><br />";
        //            liHtml += "<span class='destination-name'>Towards " + busStop.destination + "</span></div></a>";
        //            liHtml += "<a href='javascript:void(0)' class='fav-button'><span class='fav-icon " + favStarUnticked + "' data-number='" + busStop.number + "' data-name='" + busStop.name + "' data-destination='" + busStop.destination + "'>&nbsp;</span></a></li></ul>";
        //            $(appendEl2).append(liHtml);
        //            if ((i + 1) == arrayLimit) {
        //                // exit for loop when reach limit
        //                break;
        //            }
        //        }
        //    } else {
        //        liHtml = "<span class='clearfix'>" + elseMsg + "</span>";
        //        $(appendEl).append(liHtml);
        //    }
        //},
        //favStarClickRebuild: function () {
        //    $(".bus-stop-favourites,.bus-stop-recently-viewed").empty();
        //    tfl.serviceStatus.buildFavouriteBusesHtml();
        //    tfl.serviceStatus.buildRecentlyViewedBusesHtml();
        //},
        //srcoll to now function
        //scrollToAnchor: function (event) {
        //    var aTag = $("a[name='now']");
        //    $('html,body').animate({ scrollTop: aTag.offset().top }, 'fast');
        //    return false;
        //},

        //srcoll to function
        /*scrollToAnchor: function (aTag) {
         $('html,body').animate({ scrollTop: $('.main')[0].scrollHeight }, 'fast');

         return false;
         },*/

        ServiceStatusBoard: function () {
            //$(".rainbow-board tr.info-dropdown").addClass("hidden");
            // $(".has-disruption td:first-child").append('<div class="td-line-wrapper" />');
            //$(".has-disruption td:first-child span").wrap('<div class="td-line-wrapper"></div>');
            //$(".has-disruption td:first-child > div").append('<a class="control" href="javascript:void(0);"></a>');

            /*var largeStatusInformation, lineName, disruptionType, disruptionMessage;
             var prevLineClass = "";*/
            var clickLine = function (row) {
                tfl.logs.create("tfl.serviceStatus: click on line");
                var next = row.next();
                var nextVisible = next.hasClass("visible");
                $(".info-dropdown.visible").removeClass("visible");
                $("[data-line-class]").removeClass("selected");
                row.removeClass("selected");
                if (!nextVisible) {
                    row.addClass("selected");
                    next.addClass("visible");
                }
            };

            $("[data-line-class]").click(function () {
                clickLine($(this));
            });

            //on a click through from the home-page check if a specific line has been selected and if so expand it on open.

            if (window.location.hash !== "") {
                var lineHash = window.location.hash;
                tfl.logs.create("tfl.serviceStatus: page hash is " + lineHash);
                var lineVar = lineHash.substr(1);
                var clickThroughLine = $('[data-line-class= ' + lineVar.toLowerCase() + ' ]');
                if (clickThroughLine.length > 0) {
                    clickLine(clickThroughLine);
                }
            }

            //needs re-factoring/simplifying
            /*if (window.location.hash === '#bakerloo' || window.location.hash === '#circle' || window.location.hash === '#hammersmith' || window.location.hash === '#jubilee' || window.location.hash === '#northern' || window.location.hash === '#overground' || window.location.hash === '#waterloo' || window.location.hash === '#metropolitan' || window.location.hash === '#victoria') {
             var lineHash = window.location.hash;
             var lineVar = lineHash.substr(1);
             var clickThroughLine = $('tr[data-line-class= ' + lineVar + ' ]');
             clickThroughLine.addClass("selected");
             clickThroughLine.next('tr').toggleClass("hidden");
             clickThroughLine.find("a.control").toggleClass("expanded");

             if (!$("body").hasClass("breakpoint-Large")) {
             return tfl.serviceStatus.scrollToAnchor(clickThroughLine);
             }

             if (!largeStatusInformation) {
             largeStatusInformation = $('<table class="large hidden large-status-information"></table>');
             lineName = $('<td class="line-text"></td>');
             disruptionType = $('<td class="disruption-type">Severe Delays </td>');
             disruptionMessage = $('<td class="disruption-message"></td>');
             largeStatusInformation.append(lineName).append(disruptionType).append(disruptionMessage);
             $(".status-map").after(largeStatusInformation);

             var lineClass = lineVar;
             lineName.removeClass(prevLineClass).addClass(lineClass).html($(clickThroughLine).find(".line-text").html());
             prevLineClass = lineClass;
             disruptionType.html($(clickThroughLine).find(".service-status").html());
             disruptionMessage.html($(clickThroughLine).next().find(".disruption-message").html());
             return tfl.serviceStatus.scrollToAnchor($("a[name='now']"));

             }
             }*/
        }
    };
})(tfl);
(function (o) {
    tfl.logs.create("tfl.socialMedia: loaded");
    o.init = function () {
        var wrapper = $(".share-widget-wrapper");
        if (wrapper.length > 0) {
            //move the share widget up on section pages
            var overview = $(".section-overview");
            if (overview.length > 0 && !overview.hasClass("full-width")) {
                wrapper.css({ marginTop: 0, display: "block", cssFloat: "none" });
            }
            wrapper.append('<a href="javascript:void(0)" class="share-widget clearfix"><div class="twitter-icon icon"></div><div class="facebook-icon icon"></div><div class="share-icon icon"></div><span class="share-text">Share <span class="visually-hidden">on social media</span></span><span class="down-arrow icon"></span></a>');
            var url = window.location.href;
            var title = $("h1").text();
            var list = $('<ul class="share-list"></ul>');

            list.append('<li><a href="http://www.facebook.com/share.php?u=' + url + '&t=' + title + '" class="share-facebook clearfix" tabindex="0"><span class="facebook-icon icon"></span><span class="text">Facebook</span></a></li>');
            list.append('<li><a href="http://twitter.com/home?status=' + title + ' ' + url + '" class="share-twitter clearfix" tabindex="0"><span class="twitter-icon icon"></span><span class="text">Twitter</span></a></li>');
            wrapper.append(list);
            list.find("a").focus(function () {
                list.addClass("visible-for-focus")
            }).blur(function () {
                list.removeClass("visible-for-focus");
            });
            wrapper.removeClass("hidden");
        }
    };
    o.init();
}(window.tfl.socialMedia = window.tfl.socialMedia || {}));
window.tfl.journeyPlanner = window.tfl.journeyPlanner || {};
(function (o) {
    "use strict";
    tfl.logs.create("tfl.journeyPlanner.recentJourneys: loaded");
    o.journeys = [];
    o.usingRecentJourneys = tfl.storage.get('usingRecentJourneys', true);
    o.isLoaded = false;
    o.recentJourneyNumberLimit = 5;

    o.loadJourneys = function () {
        if (!o.isLoaded) {
            if (o.usingRecentJourneys) {
                o.journeys = tfl.storage.get('recentJourneys', []);
            }
            o.isLoaded = true;
        }
    };

    o.useJourneys = function (on) {
        tfl.journeyPlanner.recentJourneys.usingRecentJourneys = on;
        if (on) {
            //tfl.journeyPlanner.autocomplete.setupAutocomplete(); //clear autocomplete recent search options
        }
        tfl.storage.set("usingRecentJourneys", o.usingRecentJourneys);
        o.journeys = [];
        tfl.storage.set("recentJourneys", o.journeys);
        tfl.journeyPlanner.recentSearches.searches = [];
        tfl.storage.set("recentSearches", tfl.journeyPlanner.recentSearches.searches);
        return false;
    };

    o.saveJourney = function (fromId, fromModes, toId, toModes, viaId, viaModes) {
        //load journeys
        o.loadJourneys();
        var newItem = {
            //from: $("#From").val().replace(/^\s+|\s+$/g, '').replace(/\w\S*/g, function (txt) { return txt.charAt(0).toUpperCase() + txt.substr(1).toLowerCase(); }),
            from: $("#From").val().replace(/^\s+|\s+$/g, ''),
            fromId: fromId,
            fromModes: fromModes ? jQuery.parseJSON(fromModes) : "",
            to: $("#To").val().replace(/^\s+|\s+$/g, ''),
            toId: toId,
            toModes: toModes ? jQuery.parseJSON(toModes) : "",
            via: $("#Via").val().replace(/^\s+|\s+$/g, ''),
            viaId: viaId,
            viaModes: viaModes ? jQuery.parseJSON(viaModes) : ""
        };
        //blank from and to aren't allowed, but these will be picked up by validation,
        //so we'll just return true and let the validation pick up the errors.
        if (newItem.from === "" || newItem.to === "") {
            return true;
        }
        for (var i = 0; i < tfl.journeyPlanner.recentJourneys.journeys.length; i++) {
            var j = tfl.journeyPlanner.recentJourneys.journeys[i];
            if (j.from.toLowerCase().replace(/ /g, "") === newItem.from.toLowerCase().replace(/ /g, "") && j.to.toLowerCase().replace(/ /g, "") === newItem.to.toLowerCase().replace(/ /g, "") && j.via.toLowerCase().replace(/ /g, "") === newItem.via.toLowerCase().replace(/ /g, "")) {
                //check modes and ids and keep if already exist
                if (newItem.fromId == "" && j.fromId !== "") {
                    newItem.fromId = j.fromId;
                }
                if (newItem.fromModes == "" && j.fromModes !== "") {
                    newItem.fromModes = j.fromModes;
                }
                if (newItem.toId == "" && j.toId !== "") {
                    newItem.toId = j.toId;
                }
                if (newItem.toModes == "" && j.toModes !== "") {
                    newItem.toModes = j.toModes;
                }
                if (newItem.viaId == "" && j.viaId !== "") {
                    newItem.viaId = j.viaId;
                }
                if (newItem.viaModes == "" && j.viaModes !== "") {
                    newItem.viaModes = j.viaModes;
                }
                tfl.journeyPlanner.recentJourneys.journeys.splice(i, 1);
                break;
            }
        }
        tfl.journeyPlanner.recentJourneys.journeys.splice(0, 0, newItem);
        if (tfl.journeyPlanner.recentJourneys.journeys.length > o.recentJourneyNumberLimit) {
            tfl.journeyPlanner.recentJourneys.journeys.length = o.recentJourneyNumberLimit;
        }
        tfl.storage.set("recentJourneys", tfl.journeyPlanner.recentJourneys.journeys);
        return true;
    };

})(window.tfl.journeyPlanner.recentJourneys = window.tfl.journeyPlanner.recentJourneys || {});
window.tfl.journeyPlanner = window.tfl.journeyPlanner || {};
(function (o) {
    "use strict";
    tfl.logs.create("tfl.journeyPlanner.recentSearches: loaded");
    o.searches = [];
    o.isLoaded = false;
    o.recentSearchNumberLimit = 5;
    o.recentSearchTimeLimit = 2629740000; //1 month in milliseconds

    o.loadSearches = function () {
        if (!o.isLoaded) {
            if (tfl.journeyPlanner.recentJourneys.usingRecentJourneys) {
                o.searches = tfl.storage.get("recentSearches", []);
                if (o.searches.length > 0) {
                    // remove old searches
                    var timeNow = new Date().getTime();
                    for (var i = 0; i < o.searches.length; i++) {
                        var j = o.searches[i];
                        if (j.lastSearchDate < (timeNow - o.recentSearchTimeLimit)) {
                            o.splice(i, 1); //remove from local memory
                        }
                    }
                }
            }
            o.isLoaded = true;
        }
    };

    o.saveSearch = function (newPlaceName, newPlaceId, newPlaceModes) {
        if (newPlaceName == "") return true;
        if (newPlaceId == null) newPlaceId = "";
        if (typeof newPlaceModes == "string" && newPlaceModes !== "") {
            newPlaceModes = jQuery.parseJSON(newPlaceModes);
        }
        var timeNow = new Date().getTime();
        var newCount = 1;
        // is search aleardy in recent searches?
        for (var i = 0; i < tfl.journeyPlanner.recentSearches.searches.length; i++) {
            var j = tfl.journeyPlanner.recentSearches.searches[i];
            if (j.placeName.toLowerCase() === newPlaceName.toLowerCase()) {
                newCount = j.count + 1;
                if (newPlaceId == "" && j.placeId !== "") {
                    newPlaceId = j.placeId;
                }
                if (newPlaceModes == "" && j.placeModes !== "") {
                    newPlaceModes = j.placeModes;
                }
                tfl.journeyPlanner.recentSearches.searches.splice(i, 1);//remove from local memory
                break;
            }
        }
        //new search item
        var newSearch = {
            placeName: newPlaceName,
            placeId: newPlaceId,
            placeModes: newPlaceModes,
            count: newCount,
            lastSearchDate: timeNow
        };

        //find place to insert newSearch
        var insertAt = tfl.journeyPlanner.recentSearches.searches.length;
        for (var k = 0; k < tfl.journeyPlanner.recentSearches.searches.length; k++) {
            var l = tfl.journeyPlanner.recentSearches.searches[k];
            if (newCount >= l.count) {
                insertAt = k;
                break;
            }
        }
        //insert into array and into local memory
        tfl.journeyPlanner.recentSearches.searches.splice(insertAt, 0, newSearch);
        //limit number of recent searches that are stored in the autocomplete list
        if (tfl.journeyPlanner.recentSearches.searches.length > o.recentSearchNumberLimit) {
            tfl.journeyPlanner.recentSearches.searches.length = o.recentSearchNumberLimit;
        }
        tfl.storage.set("recentSearches", tfl.journeyPlanner.recentSearches.searches);

        return true;
    };

})(window.tfl.journeyPlanner.recentSearches = window.tfl.journeyPlanner.recentSearches || {});
(function (o) {
    "use strict";
    tfl.logs.create("tfl.journeyPlanner: loaded");

    tfl.cachingSettings = { daily: getCachingKey() };

    tfl.dictionary = {
        MoreOptions: 'Accessibility & travel options',
        LessOptions: 'Fewer options',
        CustomisedOptions: $.cookie('jp-pref') !== null ? ' (customised)' : '',
        ShowDetailedView: 'View Details',
        HideDetailedView: 'Hide Details',
        ShowAllStops: 'View all stops',
        HideAllStops: 'Hide all stops',
        NowText: 'Now',
        TodayText: 'Today',
        RemoveContentClass: ".remove-content",
        JpTypeCycling: 'cycling',
        JpTypeWalking: 'walking',
        FirstServiceText: 'First service',
        LastServiceText: 'Last service'
    };

    function getCachingKey() {
        var d = new Date();
        var n = String(d.getFullYear()) + String(d.getMonth()) + String(d.getDay()) + String(d.getHours());
        return n;
    }

    o.settings = {
        disambiguationItemsPerPage: 9
    };

    o.init = function () {
        tfl.logs.create("tfl.journeyPlanner.init: started");
        o.createPromo();
        o.createJourneys();
    };

    o.createPromo = function () {
        $(window).one("enterBreakpointMedium", function () {
            //load the promo json file published from CMS
            $.getJSON('/static/' + tfl.cachingSettings.daily + '/cms/feeds/suggested-destinations.json', function (result) {
                var data = [];
                //add the active locations to the data array
                $.each(result, function (i, e) {
                    if (e.active !== undefined && e.active) data.push(e);
                });
                promoSwapper(data, 0, { time: 30000, enabled: false });
            });
        });
        //if we've already triggered the breakpoint before this has been called, trigger it automatically
        if ($("body.breakpoint-Medium").length > 0) {
            $(window).trigger('enterBreakpointMedium');
        }
        function promoSwapper(promos, current, transition) {
            if (!transition.enabled) current = Math.floor(Math.random() * promos.length); //if transition not enabled pick a random location
            var promo = promos[current];
            var promoBox = $('#promo-box');

            //making sure the backgroundImage is loaded before the swap
            if (!$(document.body).hasClass("showing-map")) {
                var img = $('<img />').attr('src', promo.backgroundImage).load(function () {
                    var bubble = $('<div></div>').attr('id', 'promo-box').addClass('journey-planner-promo').addClass(promo.placement)
                        .append($('<h2 class="visually-hidden">Promotional information</h2>'))
                        .append($('<h3></h3>').html(promo.headline))
                        .append($('<p></p>').html(promo.text));

                    if (promo.imageAttribution) bubble.append($('<p></p>').addClass('image-attribution').html(promo.imageAttribution));

                    $('.journey-planner-start').css('background-image', 'url(' + promo.backgroundImage + ')');
                    if (promoBox.length === 0) {
                        bubble.appendTo('.journey-form');
                    } else {
                        promoBox.replaceWith(bubble);
                    }
                    promoBox = $('#promo-box');
                    promoBox.click(function () {
                        var link = promo.link.toLowerCase();
                        if (link != '') {
                            if (link.indexOf('http://') >= 0) {
                                window.open(promo.link);
                            } else {
                                link = link.replace('index.cshtml', '').replace('.cshtml', '').replace('/views/cms/', '/');
                                location.href = link;
                            }
                        } else {
                            tfl.journeyPlanner.planPromoJourney(promo);
                        }
                    });
                });
                if (img[0].width) img.load();
            }
        }

        function removePromo(hiddenClass) {
            var promoBox = $('#promo-box');

            if (promoBox.length > 0) promoBox.addClass(hiddenClass);
        }
    };

    o.createJourneys = function () {
        if (!tfl.storage.isLocalStorageSupported()) {
            tfl.logs.create("tfl.journeyplanner: Not creating recent journeys box - localStorage not present.");
            return;
        }
        tfl.logs.create("tfl.journeyplanner: Creating recent journeys box");
        $("#recent-journeys").empty();
        if ($("div[data-set=recent-journeys]").length === 0) {
            $("#more-journey-options").after("<div class='small' data-set='recent-journeys' />");
            $("#plan-a-journey").after("<div class='medium-large' data-set='recent-journeys'><div id='recent-journeys' class='moving-source-order' /></div>");
        }
        var recentJourneysBox = $("#recent-journeys");
        recentJourneysBox.empty();
        recentJourneysBox.append("<h3>Recent Journeys</h3>");
        if (!tfl.journeyPlanner.recentJourneys.usingRecentJourneys) {
            recentJourneysBox.append("<p>Turn on recent searches so you can access them easily again<br/><a href='javascript:void(0)' class='turn-on-recent-journeys' data-jumpback='#SavePreferences:visible' data-jumpto='#footer a:first'>Turn on recent journeys</a></p>");
        } else {
            tfl.journeyPlanner.recentJourneys.loadJourneys();
            if (tfl.journeyPlanner.recentJourneys.journeys.length > 0) {
                var journeyList = $("<div class='vertical-button-container' />");

                $(tfl.journeyPlanner.recentJourneys.journeys).each(function (i) {
                    var text = "<strong>" + this.from + "</strong> to <strong>" + this.to + "</strong>";
                    if (this.via !== "") {
                        text += " via <strong>" + this.via + "</strong>";
                    }
                    //tab back
                    var link;
                    if (i === 0) {
                        link = $("<a href='javscript:void(0)' class='plain-button' data-jumpback='#SavePreferences:visible'>" + text + "</a>");
                    } else {
                        link = $("<a href='javscript:void(0)' class='plain-button'>" + text + "</a>");
                    }
                    link.click(function () {
                        o.redoJourney(i);
                        return false;
                    });
                    journeyList.append(link);
                });
                recentJourneysBox.append(journeyList);
                recentJourneysBox.append("<p><a href='javascript:void(0)' class='turn-off-recent-journeys' data-jumpto='#footer a:first' data-jumpback='#SavePreferences:visible'>Turn off and clear recent journeys</a></p>");
            } else {
                recentJourneysBox.append("<p>You currently have no saved journeys<br/><a href='javascript:void(0)' class='turn-off-recent-journeys' data-jumpto='#footer a:first' data-jumpback='#SavePreferences:visible'>Turn off recent journeys</a></p>");
            }
        }

        $("div.medium-large[data-set=recent-journeys]").append(recentJourneysBox);

        $(".moving-source-order").appendAround();
        $(".turn-on-recent-journeys").click(function (event) {
            event.preventDefault();
            tfl.journeyPlanner.recentJourneys.useJourneys(true);
            tfl.journeyPlanner.searchForm.destroyAutocomplete();
            tfl.journeyPlanner.searchForm.setupAutocomplete(false);
            o.createJourneys();
        });
        $(".turn-off-recent-journeys").click(function (event) {
            event.preventDefault();
            tfl.journeyPlanner.recentJourneys.useJourneys(false);
            tfl.journeyPlanner.searchForm.destroyAutocomplete();
            tfl.journeyPlanner.searchForm.setupAutocomplete(false);
            o.createJourneys();
        });
    };

    o.redoJourney = function (idx) {
        var journey = tfl.journeyPlanner.recentJourneys.journeys[idx];
        $("#From").val(journey.from);
        $("#From").attr("data-from-id", journey.fromId);
        $("#From").attr("data-from-modes", JSON.stringify(journey.fromModes));
        $("#To").val(journey.to);
        $("#To").attr("data-to-id", journey.toId);
        $("#To").attr("data-to-modes", JSON.stringify(journey.toModes));
        if (journey.via !== null) {
            $("#Via").val(journey.via);
            $("#Via").attr("data-via-id", journey.viaId);
            $("#Via").attr("data-via-modes", JSON.stringify(journey.viaModes))
        } else {
            $("#Via").val("");
        }
        $("#jp-search-form").submit();
    };

    o.planPromoJourney = function (promo) {
        var fromField = $("#From"), toField = $("#To");
        if (promo) {
            toField.val(promo.to);
            var fromValue = promo.from;
            //if 'current' is on the json file call the use current location function
            if (promo.from === 'current') {
                tfl.journeyPlanner.searchForm.geolocateMe("#From", function () {
                    $('#jp-search-form').submit();
                });
            } else {
                fromField.val(fromValue);
                $('#jp-search-form').submit();
            }
        }
    };
})(window.tfl.journeyPlanner = window.tfl.journeyPlanner || {});

window.tfl.autocomplete = window.tfl.autocomplete || {};
(function (o) {
    "use strict";
    tfl.logs.create("tfl.autocomplete.sources: loaded");
    o.autocompleteDisplayNumberLimit = 10;

    //Geolocation
    var geolocationName = "geolocation";
    var geolocationLinkText = "Use my location";
    o.geolocation = {
        name: geolocationName,
        local: [
            {
                value: geolocationLinkText,
                dataset: geolocationName
            }
        ],
        prefetch: "",
        remote: "",
        valueKey: "value",
        minLength: 0,
        limit: 1,
        template: "<a class='geolocation-link' href='javascript:void(0)'><span class='geolocation-icon'>&nbsp</span>{{value}}</a>",
        engine: Hogan,
        header: "",
        footer: "<span class='source-footer'>&nbsp</span>",
        callback: function (inputEl) {
            $(inputEl).removeAttr("data-dataset-name");
            stationsStopsRemoveContent(inputEl);
            tfl.journeyPlanner.searchForm.geolocateMe(inputEl);
        }
    };

    //recent searches
    //tfl.journeyPlanner.recentSearches.saveSearch("Abbey Road DLR Station", "1003006", ["tube", "dlr", "bus"]);
    o.recentSearches = {
        name: "",
        local: "",
        prefetch: "",
        remote: "",
        valueKey: "placeName",
        minLength: 0,
        limit: tfl.journeyPlanner.recentSearches.recentSearchNumberLimit,
        template: "{{#placeModes}}<span class='mode-icon {{.}}-icon'>&nbsp;</span>{{/placeModes}}<span class='stop-name' data-station-id='{{placeId}}' data-stations-modes='{{placeModes}}'>{{placeName}}</span>",
        engine: Hogan,
        header: "<span class='source-header'>Recent searches</span>",
        footer: "<span class='source-footer'>&nbsp</span>",
        callback: stationsStopsSellectionCallback,
        removeContent: stationsStopsRemoveContent
    };

    //JP recent searches footer
    var recentSearchTurnOffName = "journey-planner-recent-searches-footer-off";
    var recentSearchTurnOffLinkText = "Turn off recent journeys";
    var recentSearchTurnOffExtraText = "";
    var recentSearchTurnOnName = "journey-planner-recent-searches-footer-on";
    var recentSearchTurnOnLinkText = "Turn on recent journeys";
    var recentSearchTurnOnExtraText = " to store your favourite stations & stops";

    o.journeyPlannerRecentSearchesTurnOffFooter = {
        name: recentSearchTurnOffName,
        local: [
            {
                linkText: recentSearchTurnOffLinkText,
                extraText: recentSearchTurnOffExtraText,
                dataset: recentSearchTurnOffName
            }
        ],
        prefetch: "",
        remote: "",
        valueKey: "linkText",
        minLength: 0,
        limit: 1,
        template: "<span class='journey-planner-recent-searches-footer'><a href='javascript:void(0)'>{{linkText}}</a>{{extraText}}</span>",
        engine: Hogan,
        header: "",
        footer: "<span class='source-footer'>&nbsp</span>",
        callback: function () {
            tfl.journeyPlanner.recentJourneys.useJourneys(false);
            if (!tfl.journeyPlanner.searchForm.isWidget) {
                tfl.journeyPlanner.createJourneys();
            }
        }
    };
    o.journeyPlannerRecentSearchesTurnOnFooter = {
        name: recentSearchTurnOnName,
        local: [
            {
                linkText: recentSearchTurnOnLinkText,
                extraText: recentSearchTurnOnExtraText,
                dataset: recentSearchTurnOnName
            }
        ],
        prefetch: "",
        remote: "",
        valueKey: "linkText",
        minLength: 0,
        limit: 1,
        template: "<span class='journey-planner-recent-searches-footer'><a href='javascript:void(0)'>{{linkText}}</a>{{extraText}}</span>",
        engine: Hogan,
        header: "",
        footer: "<span class='source-footer'>&nbsp</span>",
        callback: function () {
            tfl.journeyPlanner.recentJourneys.useJourneys(true);
            if (!tfl.journeyPlanner.searchForm.isWidget) {
                tfl.journeyPlanner.createJourneys();
            }
        }
    };

    //JP Suggestions
    o.journeyPlannerSuggestions = {
        name: "journey-planner-suggestions",
        local: "",
        prefetch: {
            url: "../cdn/static/feed/stations-list_typeahead.json",
            ttl: 2629740000 //1 month in milliseconds
        },
        remote: "",
        valueKey: "b",
        minLength: 1,
        limit: o.autocompleteDisplayNumberLimit,
        template: "{{#c}}<span class='mode-icon {{.}}-icon'>&nbsp;</span>{{/c}}<span class='stop-name' data-station-id='{{a}}' data-stations-modes='{{c}}'>{{b}}</span>",
        engine: Hogan,
        header: "",
        footer: "",
        callback: stationsStopsSellectionCallback,
        removeContent: stationsStopsRemoveContent
    };

    //stations stops functions
    function stationsStopsSellectionCallback(inputEl, datum, dataset) {
        var el = $(inputEl);
        var a = datum.a || datum.placeId;
        el.attr("data-" + el.attr("id").toLowerCase() + "-id", a);
        var c = JSON.stringify(datum.c ? datum.c : datum.placeModes);
        el.attr("data-" + el.attr("id").toLowerCase() + "-modes", c);
    };
    function stationsStopsRemoveContent(inputEl) {
        var el = $(inputEl);
        el.removeAttr("data-" + el.attr("id").toLowerCase() + "-id");
        el.removeAttr("data-" + el.attr("id").toLowerCase() + "-modes");
    };

})(window.tfl.autocomplete.sources = window.tfl.autocomplete.sources || {});
(function (o) {
    "use strict";
    tfl.logs.create("tfl.autocomplete: loaded");

    o.setup = function (inputEl, dataSources, binding) {
        var el = $(inputEl);
        //no autocomplete for IE7 - it's too buggy
        if ($("html").hasClass("lt-ie8")) {
            return;
        }
        tfl.logs.create("tfl.autocomplete.setup: started");
        el.typeahead(dataSources)
            .on("keydown",function (e) { //only relevant to where item from a dataset has already been selected
                var dataset = el.attr("data-dataset-name");
                if (dataset && dataset !== "") {
                    for (var i = 0; i < dataSources.length; i++) {
                        dataSource = dataSources[i];
                        if (dataset === dataSource.name) {
                            if (dataSources[i].keydown) {
                                dataSources[i].keydown(e, inputEl);
                            }
                            break;
                        }
                    }
                }
            }).on("typeahead:selected typeahead:autocompleted",function (e, datum) {
                var dataset = o.getDatasetName(inputEl);
                if (dataset) {
                    for (var i = 0; i < dataSources.length; i++) {
                        var dataSource = dataSources[i];
                        if (dataset === dataSource.name) {
                            if (dataSource.callback) {
                                dataSource.callback(inputEl, datum, dataset);
                            }
                            if (dataSource.contextCallback) {
                                dataSource.contextCallback(inputEl, datum, dataset);
                            }
                            break;
                        }
                    }
                    el.attr("data-dataset-name", dataset);
                }
            }).parent().siblings(tfl.dictionary.RemoveContentClass).bind("clear-search-box", function () {
                $(this).click();
            });

        //bind 'remove-content' click to input element.
        if (binding) {
            el.parent().siblings(tfl.dictionary.RemoveContentClass).click(function () {
                var dataset = el.attr("data-dataset-name");
                if (dataset && dataset !== "") {
                    for (var i = 0; i < dataSources.length; i++) {
                        dataSource = dataSources[i];
                        if (dataset === dataSource.name) {
                            if (dataSources[i].removeContent) {
                                dataSources[i].removeContent(el);
                            }
                            break;
                        }
                    }
                    el.removeAttr("data-dataset-name")
                }
                el.typeahead("setQuery", "");
            })
        }
        ;
    };// end of setup function

    o.getDatasetName = function (inputEl) {
        return $(inputEl).parent().find("div.tt-suggestion").parent().parent().attr("class").substr(11);
    }

})(window.tfl.autocomplete = window.tfl.autocomplete || {});
(function (o) {
    "use strict";
    tfl.logs.create("tfl.journeyPlanner.results: loaded");

    o.init = function (loadedViaAjax) {
        tfl.logs.create("tfl.journeyPlanner.results.init: started (isAjax: " + loadedViaAjax + ")");
        //if we haven't ajax loaded, we'll need to initalise the search form
        if (!loadedViaAjax) {
            o.resultsFormInit();
            o.processResults();
            o.initSteal();
        } else {
            o.initAjaxPage();
        }
        if ($("html").hasClass("lt-ie8")) {
            o.initIE7();
        }
    };

    o.initGlobal = function () {
        tfl.logs.create("tfl.journeyPlanner.initGlobal: started");
        //stop duplication of events by unbinding before binding
        $(".always-visible").unbind("click", tfl.interactions.toggleExpandableBox).click(tfl.interactions.toggleExpandableBox);
    };

    o.initAjaxPage = function () {
        tfl.logs.create("tfl.journeyPlanner.results.initAjaxPage: started");
        var loaderType = $('#JpType').val() == '' ? 'publictransport' : $('#JpType').val();
        o.setupLoader(loaderType);
        o.resultsFormInit();
        var url = window.location.href, resultsUrl = '/plan-a-journey/results';
        url = url.substring(url.indexOf('?'), url.length);
        $.ajax({
            url: resultsUrl + url.toLowerCase() + "&ispostback=true"
        }).done(function (data) {
                data = $(data).find('.ajax-response');
                $(".journey-details-ajax").replaceWith($(data));

                // populate walk tab with time
                var journeyTimeWalking = $('.walking-box .journey-time');
                if (journeyTimeWalking.length == 1 && $('[data-jp-tabs="true"] .walking.selected').length < 1) {
                    journeyTimeWalking.find('.visually-hidden').remove();
                    journeyTimeWalking = $('<span></span>').addClass('tabs-time').text('Walk this in ' + journeyTimeWalking.text());
                    $('[data-jp-tabs="true"] [data-jptype="walking"]').addClass('has-time').append(journeyTimeWalking);
                }

                // populate cycle tab with time
                var journeyTimeCycle = $('.cycling-box .journey-time');
                if (journeyTimeCycle.length == 1 && $('[data-jp-tabs="true"] .cycling.selected').length < 1) {
                    journeyTimeCycle.find('.visually-hidden').remove();
                    journeyTimeCycle = $('<span></span>').addClass('tabs-time').text('Cycle in ' + journeyTimeCycle.text());
                    $('[data-jp-tabs="true"] [data-jptype="cycling"]').addClass('has-time').append(journeyTimeCycle);
                }

                o.processResults();
                o.initGlobal();
                if ($(".disambiguation-form").length > 0) {
                    tfl.journeyPlanner.disambiguation.init();
                } else {
                    $('.summary-results > .expandable-box').css({ opacity: 0 }).each(function (i) {
                        $(this).delay(150 * i).animate({ opacity: 1 }, 100, function () {
                            $(this).removeAttr("style");
                        });
                    });
                }
                o.initSteal();
                $("#alternatives").find("[data-pull-content]").click(tfl.navigation.pullContentHandler);
            });
    };

    o.initSteal = function () {
        // we have to use the steal.js script loader to make sure the mapping code doesn't run until the mapping framework has loaded
        steal(tfl.mapScriptPath)
            .then(function () {
                tfl.logs.create("tfl.journeyPlanner.results.initSteal: started");
                var mapLoaded = false;
                var legMap = null;
                $(".view-on-a-map").click(function (event, data) {
                    $(this).toggleClass("show-all");
                    var mapLinks;
                    if (data && data.isHideAll) {
                        mapLinks = $(".view-on-a-map.hide-map");
                    } else {
                        mapLinks = $(".view-on-a-map.hide-map").not(this);
                    }
                    mapLinks.text("View on a map").removeClass("hide-map");
                    if ($(this).hasClass("hide-map")) {
                        $(this).text("View on a map").removeClass("hide-map");
                        legMap.hide();
                    } else {
                        if (!mapLoaded) {
                            $(this).after("<div id='leg_map' class='leg-map'></div>");
                            $.fixture.on = false; // TODO really need to be able to take this out
                            legMap = $('#leg_map').tfl_maps_journey_planner_leg_map({ url: $(".journey-results").data("api-uri"), isNationalBounds: tfl.getQueryParam('NationalSearch') === 'true' });
                            mapLoaded = true;
                        } else {
                            legMap.show();
                            legMap.insertAfter($(this));
                        }
                        $.publish('tfl.journeyplanner.leg.chosen', { journeyIndex: $(this).data("journeyindex"), legIndex: $(this).data("legindex") });
                        $(this).text("Hide map").addClass("hide-map");
                    }
                    event.preventDefault();
                });
            });
    };

    //Initialise for IE7, which has rendering problems when the page
    //height changes (triggered by editing the journey form)
    o.initIE7 = function () {
        $(".cancel-button, .toggle-options, .edit-button").click(function () {
            var row = $(".journey-form").parents(".r").next();
            row.attr("class", row.attr("class"));
        });
    }

    o.maps = function (type) {
        var cyclingType = 'cycling';
        var walkingType = 'walking';
        if (type == cyclingType || type == walkingType) {
            var result = (type == cyclingType) ? $('.summary-results.cycling .cycling-box') : $('.summary-results.walking .walking-box');
            if (result.length == 1) {
                // it's cycling or walking and has only one result, so expand the options and map
                result.find('.always-visible .show-detailed-results').click();
                o.triggerMapLink($('.full-results-container .view-on-a-map'));
            }
        }
    };

    o.triggerMapLink = function (el) {
        tfl.logs.create("tfl.journeyPlanner.results: trigger click on map link");
        el.trigger('click', { isHideAll: true });
    };

    o.setupLoader = function (loaderClass) {
        var bg = $('<div id="loader-background"></div>');
        var bird = $('<div id="loader-birds"></div>');
        var trees = $('<div id="loader-trees"></div>');
        var tSegment = $('<div class="tree-segment"></div>');
        for (var i = 0; i < 8; i++) {
            trees.append(tSegment.clone());
        }
        var transport = $('<div id="loader-transport-method"></div>');
        var grass = $('<div id="loader-grass"></div>');
        var gSegment = $('<div class="grass-segment"></div>');
        for (var i = 0; i < 18; i++) {
            grass.append(gSegment.clone());
        }
        var message = $('<div id="loader-message">Fetching results</div>');
        $("#loader-window").addClass(loaderClass).append(bg).append(trees).append(bird).append(transport).append(grass).append(message);

    };

    o.processResults = function () {
        $(".summary-results > .expandable-box > .content > .always-visible").click(function () {
            if ($(this).parents(".not-selected").length > 0) {
                o.unshowAllDetailedResults();
                o.currentlyShowing = null;
            }
        });
        var summaryResults = $('.summary-results');
        if (summaryResults.hasClass('cycling')) {
            o.maps('cycling');
        } else if (summaryResults.hasClass('walking')) {
            o.maps('walking');
        }

        $(".always-visible .journey-price").after("<a href='javascript:void(0)' class='primary-button show-detailed-results' data-jumpto=''>" + tfl.dictionary.ShowDetailedView + "</a>");
        //$(".back-to-link").click(function () {
        //    o.currentlyShowing = null;
        //    o.unshowAllDetailedResults();
        //});
        $(".back-to-link").click(o.unshowAllDetailedResults);
        var i = 1;
        if ($(".journey-details").length > 0) {
            if (tfl.journeyPlanner.recentJourneys.usingRecentJourneys) {
                tfl.journeyPlanner.recentJourneys.saveJourney($("#From").attr("data-from-id"), $("#From").attr("data-from-modes"), $("#To").attr("data-to-id"), $("#To").attr("data-to-modes"));
                tfl.journeyPlanner.recentSearches.saveSearch($("#From").val().replace(/^\s+|\s+$/g, ''), $("#From").attr("data-from-id"), $("#From").attr("data-from-modes"));
                tfl.journeyPlanner.recentSearches.saveSearch($("#To").val().replace(/^\s+|\s+$/g, ''), $("#To").attr("data-to-id"), $("#To").attr("data-to-modes"));
                if ($("#Via").val() !== "") {
                    tfl.journeyPlanner.recentSearches.saveSearch($("#Via").val().replace(/^\s+|\s+$/g, ''), $("#Via").attr("data-via-id"), $("#Via").attr("data-via-modes"));
                }
            }
        }
        $(".journey-details").each(o.generateJourneySummary);

        //status-disruption click events
        var statusMsgs = $(".journey-steps .message-toggle");
        var statusMsgsDetailedView = $(".journey-detail-step .line-status .message-toggle");
        for (var j = 0; j < statusMsgs.length; j++) {
            var el = $(statusMsgs[j]);
            (function () {
                var id = j;
                el.click(function () {
                    if (!$(statusMsgsDetailedView[id]).parent().parent().hasClass("expanded")) {
                        $(statusMsgsDetailedView[id]).click();
                    }
                    $(this).parents(".summary").find(".show-detailed-results").click();
                });
            })();
        }
        $(".line-status-info .start-hidden");
        var hideLinks = $(".line-status-info .hide-link");
        for (var k = 0; k < statusMsgs.length; k++) {
            var el = $(hideLinks[k]);
            (function () {
                var id = k;
                el.click(function () {
                    $(statusMsgsDetailedView[id]).click();
                });
            })();
        }

        $(".show-detailed-results").click(o.viewDetailsClick);
        o.expandableBoxes = $(".summary-results > .expandable-box");
        $(".json-all-stops").click(o.showAllStops);

        //add show/hide text to disruptions info
        $(".disruption-heading").append('<span class="link-style show-details">Show details...</span><span class="link-style hide-details">Hide details...</span>');
        $(".accessibility-details").append('<div class="show-hide-links"><a href="javascript:void(0)" class="show-link">Show details...</a><a href="javascript:void(0)" class="hide-link">Hide details...</a></div>');
        $(".show-hide-links a").click(function (e) {
            $(".accessibility-details").toggleClass("expanded");
            e.preventDefault();
        });
    };

    o.generateJourneySummary = function () {
        var summary = $("<table></table>")
            .attr("class", "journey-steps")
            .append("<caption class='visually-hidden'>Steps for journey option " + i + "</caption><thead><tr><th>Time</th><th>Mode of Transport</th><th>Instructions</th></tr></thead>");

        $(this).find(".journey-detail-step:not(.terminus)").each(function (i) {
            var description = "";
            var mode = $(this).find(".step-heading .time-and-mode .centred").html();
            var row = $("<tr></tr>");
            var isDisrupted = false;
            if ($(this).find(".details .instructions .line-status").length > 0) {
                isDisrupted = true;
            }
            var className = "time"
            if ($(this).find(".duration").text().indexOf("hour") > 0) {
                className = className + " wide";
            }
            row.append("<td class='" + className + "'><strong>" + $(this).find(".duration").text().replace("minute", "min").replace("hour", "hr") + "</strong></td>");
            var logo = isDisrupted ? $("<td class='logo disrupted'></td>") : $("<td class='logo'></td>");
            logo.append($(this).find(".time-and-mode .centred").clone());
            row.append(logo);

            if (isDisrupted) {
                row.append("<td class='description disrupted'>" + $(this).find(".step-summary").html() + "</td>");
            } else {
                row.append("<td class='description'>" + $(this).find(".step-summary").html() + "</td>");
            }

            summary.append(row);
            //get status info
            if ($(this).find(".line-status").length > 0) {
                var td = $("<td class='disrupted'></td>").append($(this).find(".disruption-messages .line-status .message-toggle").eq(0).clone());
                summary.append($("<tr><td class='time'>&nbsp;</td><td class='logo disrupted'><div class='disruption-icon centred hide-text'>Disruption</div></td></tr>").append(td));
            }
        });

        summary.insertBefore(this);
        summary.after($(this).parents(".content").find(".price-and-details").clone());
        i++;
    };

    o.viewDetailsClick = function (e) {
        e.stopPropagation();
        var idx = 0;
        idx = $.inArray($(this).parents(".expandable-box").get(0), o.expandableBoxes);
        o.unshowAllDetailedResults();
        tfl.logs.create("tfl.journeyPlanner.results: view details clicked: (index: " + idx + ", currentlyshowing: " + o.currentlyShowing + ")");
        if (idx != o.currentlyShowing) {
            o.showDetailedResults(idx);
        } else {
            o.currentlyShowing = null;
        }
        return false;
    };

    o.showDetailedResults = function (idx) {
        tfl.logs.create("tfl.journeyPlanner.results: Show detailed results. Index: " + idx);
        var expandableBoxes = o.expandableBoxes;
        o.currentlyShowing = idx;
        expandableBoxes.removeClass("selected").addClass("not-selected");
        var parentBox = $(expandableBoxes[idx]);
        var nextParentBox = $(expandableBoxes[idx + 1]);
        var lastParentBox = $(expandableBoxes[expandableBoxes.length - 1]);
        $(lastParentBox).find(".show-detailed-results").attr("data-jumpto", ".earlier:tabbable:first");
        $(lastParentBox).find(".show-detailed-results").addClass("last-show-details");
        parentBox.removeClass("not-selected").addClass("selected");
        var link = parentBox.find(".show-detailed-results:visible");
        $(".show-detailed-results").text(tfl.dictionary.ShowDetailedView);
        $(parentBox).find(".show-detailed-results").text(tfl.dictionary.HideDetailedView);
        $(parentBox).find(".show-detailed-results").addClass("hide-details");
        $(nextParentBox).find(".show-detailed-results").addClass("next-show-details");
        $(parentBox).find(".show-detailed-results").attr("data-jumpto", ".journey-details :tabbable:first:visible");
        $(parentBox).find(".show-detailed-results").removeClass("last-show-details");
        $(expandableBoxes[o.currentlyShowing]).children(".content").children(".start-hidden").appendTo(".full-results-container");
        $(".replan-route").remove();
        $(".full-results-container").append("<div class='replan-route'><a href='javascript:void(0)' class='replan-from-current-location' data-jumpto='.next-show-details:visible'>Replan route from current location</a></div>");
        $(".replan-from-current-location").click(function () {
            tfl.journeyPlanner.searchForm.geolocateMe("#From", function () {
                var el = $("#From");
                el.removeAttr("data-from-id");
                el.removeAttr("data-from-modes");
                el.removeAttr("data-dataset-name");
                $('#jp-search-form').submit();
            });
            $(".secondary-button.edit-button").click();
            return false;
        });
        $(".full-results-container").removeClass("hidden").addClass($("#JpType").val());
        $(document.body).addClass("showing-full-details");
        var detailsTop = $(".full-results-container").offset().top;
        $("html, body").animate({ scrollTop: detailsTop - 10 });
        //tab focus
        $(".journey-details :tabbable:first").focus();
        //if walking or cycling, click view map
        var frc = $(".full-results-container");
        if (frc.hasClass("cycling") || frc.hasClass("walking")) {
            var mapLink = frc.find(".view-on-a-map");
            if (mapLink.length > 0 && !mapLink.hasClass("hide-map")) {
                o.triggerMapLink(mapLink);
            }
        }
        //if mobile, hijack the back button here
        if (!$("html").hasClass("breakpoint-Medium")) {
            if (history.pushState) {
                var backToLink = $(".back-to-link");
                var backLinkHandler = function () {
                    window.onpopstate = null;
                    history.go(-1);
                    backToLink.off("click", this);
                };
                history.pushState({}, null);
                backToLink.on("click", backLinkHandler);
                window.onpopstate = function () {
                    o.unshowAllDetailedResults();
                    window.onpopstate = null;
                }
            }
        }
        return false;
    };

    o.unshowAllDetailedResults = function () {
        tfl.logs.create("tfl.journeyPlanner.results: Unshow all detailed results.");
        var expandableBoxes = o.expandableBoxes;
        $(".full-results-container").addClass("hidden");
        $(".full-results-container > .start-hidden").appendTo($(expandableBoxes[o.currentlyShowing]).children(".content"));
        $(".show-detailed-results").text(tfl.dictionary.ShowDetailedView);
        $(".show-detailed-results").removeClass("hide-details");
        $(".show-detailed-results").removeClass("next-show-details");
        $(".show-detailed-results").attr("data-jumpto", "");
        expandableBoxes.removeClass("not-selected selected");
        $(document.body).removeClass("showing-full-details");
        $(".json-all-stops").text(tfl.dictionary.ShowAllStops);
        $(".json-all-stops").parent().siblings(".all-stops").hide();
        return false;
    };

    o.showAllStops = function () {
        var linkClicked = this;
        if ($(linkClicked).text() == tfl.dictionary.ShowAllStops) {
            $(linkClicked).text(tfl.dictionary.HideAllStops);
            $(linkClicked).parent().siblings(".all-stops").show();
        } else {
            $(linkClicked).text(tfl.dictionary.ShowAllStops);
            $(linkClicked).parent().siblings(".all-stops").hide();
        }

        $(linkClicked).toggleClass("hide");
        return false;
    };

    o.resultsFormInit = function () {
        $(".plan-journey-button").wrap("<div class='clearfix update-buttons' />").before("<a href='javascript:void(0)' class='secondary-button cancel-button'>Cancel</a>");
        $(".plan-journey-button").val("Update journey").submit(function () {
            $(".cancel-button").remove();
        });
        $(".plan-journey-button-second").val("Update journey");
        tfl.journeyPlanner.searchForm.buildSwitchButton();
        $(".switch-button").click(tfl.journeyPlanner.searchForm.switchFromTo);
        var isTouch = tfl.interactions.isTouchDevice();
        if ($("#From").val() !== "" && $("#To").val() !== "") {
            var touchClass = "";
            if (isTouch) {
                touchClass = " touch";
            }
            var journeyResultSummary = $("<div class='journey-result-summary" + touchClass + "' ><div class='from-to-wrapper'></div></div>");
            journeyResultSummary.children(".from-to-wrapper").append("<div class='summary-row clearfix'><span class='label'>From:</span><strong>" + $("#From").val() + "</strong></div>");
            journeyResultSummary.children(".from-to-wrapper").append("<div class='summary-row clearfix'><span class='label'>To:</span><strong>" + $("#To").val() + "</strong></div>");
            var leavingOrArriving = $(".leaving-or-arriving li.selected").text().replace(/^\s+|\s+$/g, '');
            var date = $("#Date").val();
            if (date !== null) {
                var month = parseInt(date.substring(4, 6));
                month--;
                date = new Date(parseInt(date.substring(0, 4)), month, parseInt(date.substring(6, 8)));
            } else {
                date = new Date();
            }
            var months = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
            var days = ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"];
            var sup = "<sup>th</sup>";

            var day = date.getDate();
            var mod = day % 10;
            if (mod === 1 && day !== 11) {
                sup = "<sup>st</sup>";
            } else if (mod === 2 && day !== 12) {
                sup = "<sup>nd</sup>";
            } else if (mod === 3 && day !== 13) {
                sup = "<sup>rd</sup>";
            }
            var timeText = days[date.getDay()] + ", " + months[date.getMonth()] + " " + date.getDate() + sup;
            //if (leavingOrArriving !== tfl.dictionary.FirstServiceText && leavingOrArriving !== tfl.dictionary.LastServiceText) {
            timeText += ", " + $("#Time option:selected").text();
            //}

            var travelPrefsText = "";
            var via = $("#Via").val();
            if (via !== "") {
                travelPrefsText += "<strong class='via-destination'>Via " + via + "</strong> ";
            }

            // cycling
            var ticked = $('.jp-mode-cycling .accessibility-options .ticked label').html();
            travelPrefsText += '<strong class="travelpreferences-cycling">' + ticked + '</strong>';

            // walking
            var optionSelected = $('#WalkingSpeed :selected').text();
            travelPrefsText += '<strong class="travelpreferences-walking">' + optionSelected + ' walking speed</strong>';

            // public transport
            travelPrefsText += '<strong class="travelpreferences-publictransport">Showing ' + $("#JourneyPreference option:selected").text().toLowerCase() + '</strong> ';
            var using = $(".modes-of-transport option:selected");

            travelPrefsText += '<strong class="travelpreferences-publictransport">Using ';
            var i = 0;
            if ($(".modes-of-transport option:not(:selected)").length === 0) {
                travelPrefsText += "all transport modes";
            } else if (using.length <= 4) {
                for (i = 0; i < using.length; i++) {
                    if (i > 0 && i === using.length - 1) {
                        travelPrefsText += " and ";
                    } else if (i > 0) {
                        travelPrefsText += ", ";
                    }
                    travelPrefsText += $(using[i]).text().replace(/^\s+|\s+$/g, '');
                }
            } else {
                for (i = 0; i < 3; i++) {
                    if (i > 0) {
                        travelPrefsText += ", ";
                    }
                    travelPrefsText += $(using[i]).text().replace(/^\s+|\s+$/g, '');
                }
                travelPrefsText += " and " + (using.length - 3) + " others";
            }
            travelPrefsText += "</strong> ";

            var accessibilityPref = $(".accessibility-options li.ticked input").val();
            if (accessibilityPref && accessibilityPref !== "" && accessibilityPref.toLowerCase() !== "norequirements") {
                travelPrefsText += '<strong class="travelpreferences-publictransport">Step free access required</strong> ';
            }
            travelPrefsText += '<strong class="travelpreferences-publictransport">Max walk time ' + $("#MaxWalkingMinutes").val() + " mins</strong>";

            journeyResultSummary.append("<div class='summary-row clearfix'><span class='label'>" + leavingOrArriving + ":</span><strong>" + timeText + "</strong></div>");

            var editButton = $('<a></a>').attr('href', 'javascript:void(0)').addClass('secondary-button edit-button').text('Edit').click(function (e) {
                e.preventDefault();
                $("#plan-a-journey").addClass("editing");
                //tabbing focus - #From field
                $("#plan-a-journey :tabbable:first:visible").focus();
                $('#From, #To, #Via').trigger("change");
            });

            journeyResultSummary.append($('<div></div>').addClass('summary-row').append(editButton));

            journeyResultSummary.append("<div class='travel-preferences clearfix'><div class='left-shadow' /><div class='scroller'><div><span>Travel preferences: </span>" + travelPrefsText + "</div></div><div class='right-shadow' /></div>");
            $("#plan-a-journey").append(journeyResultSummary);
        }

        if (isTouch) {
            tfl.journeyPlanner.travelPrefsInnerWidth = $(".scroller > div").get(0).scrollWidth;
            tfl.journeyPlanner.travelPrefsScrollMax = tfl.journeyPlanner.travelPrefsInnerWidth - $(".scroller").width();
            tfl.journeyPlanner.travelPrefsLeftShadow = $(".left-shadow");
            tfl.journeyPlanner.travelPrefsRightShadow = $(".right-shadow");
            $(".scroller").scroll(function () {
                var scrollLeft = $(this).scrollLeft();
                tfl.journeyPlanner.travelPrefsLeftShadow.css({ left: Math.min(0, -40 + scrollLeft) });
                tfl.journeyPlanner.travelPrefsRightShadow.css({ right: Math.min(0, tfl.journeyPlanner.travelPrefsScrollMax - scrollLeft - 40) });
            }).scroll();

            $(window).resize(function () {
                tfl.journeyPlanner.travelPrefsScrollMax = tfl.journeyPlanner.travelPrefsInnerWidth - $(".scroller").width();
                $(".scroller").scroll();
            });
        }
        $(".cancel-button").click(function () {
            //hide more options if it's displayed
            $('.toggle-options.less-options').click();

            $("#plan-a-journey").removeClass("editing").parents(".r").removeClass("expanded");

            //tabbing focus - edit button
            $(".journey-result-summary .secondary-button").focus();
            return false;
        });

    };

})(window.tfl.journeyPlanner.results = window.tfl.journeyPlanner.results || {});
window.tfl.journeyPlanner = window.tfl.journeyPlanner || {};
(function (o) {
    "use strict";
    tfl.logs.create("tfl.journeyPlanner.disambiguation: started");

    o.init = function () {
        tfl.logs.create("tfl.journeyPlanner.disambiguation: initialised");
        var disambiguations = $(".disambiguation-wrapper");
        var numDisambiguations = disambiguations.length;
        var i = 0;
        if (numDisambiguations > 0) {
            $(".disambiguation-extras").prepend("<div class='pagination' />");
            $(".disambiguation-box").each(function () {
                $(this).pajinate({
                    items_per_page: tfl.journeyPlanner.settings.disambiguationItemsPerPage,
                    item_container_id: '.disambiguation-items',
                    nav_panel_id: '.pagination',
                    num_page_links_to_display: 6,
                    show_first_last: false,
                    show_prev_next: false
                });
                var wrapper = $(this).parents(".disambiguation-wrapper");
                var isFrom = wrapper.hasClass("from-results");
                if (isFrom) {
                    $(".summary-row").eq(0).addClass("disambiguating");
                } else {
                    var isTo = wrapper.hasClass("to-results");
                    if (isTo) {
                        $(".summary-row").eq(1).addClass("disambiguating");
                    } else {
                        $(".via-destination").addClass("disambiguating");
                    }
                }
            });
        }
        $(".disambiguation-link").click(function () {
            var row = $(this).parents(".disambiguation-wrapper");
            if (row.hasClass("from-results")) {
                $("#From").val($(this).find(".place-name").text()).after("<input type='hidden' name='FromId' value='" + $(this).attr("data-param") + "' />");
                $("#disambiguation-map-from").html("");
            } else if (row.hasClass("to-results")) {
                $("#To").val($(this).find(".place-name").text()).after("<input type='hidden' name='ToId' value='" + $(this).attr("data-param") + "' />");
                $("#disambiguation-map-to").html("");
            } else if (row.hasClass("via-results")) {
                $("#Via").val($(this).find(".place-name").text()).after("<input type='hidden' name='ViaId' value='" + $(this).attr("data-param") + "' />");
                $("#disambiguation-map-via").html("");
            }

            var next = row.next(".disambiguation-wrapper");
            if (next.length >= 1) {
                row.hide();
                next.show();
            } else {
                $("#jp-search-form").submit();
            }
            $.publish("tfl.disambiguation.map.resized");
            return false;
        });
        disambiguations.each(function () {
            if (i > 0) {
                $(this).hide();
            }
            i++;
        });

        // we have to use the steal.js script loader to make sure the mapping code doesn't run until the mapping framework has loaded
        steal(tfl.mapScriptPath)
            .then(function () {
                if ($("#disambiguation-options-from .disambiguation-option").length > 1) {
                    $('#disambiguation-map-from').tfl_maps_disambiguation_map(
                        {
                            optionsElement: $("#disambiguation-options-from"),
                            pageSize: tfl.journeyPlanner.settings.disambiguationItemsPerPage,
                            isNationalBounds: tfl.getQueryParam('NationalSearch') === 'true',
                            mapName: "from"
                        });
                    $(".from-results .pagination .page_link").click(
                        function () {
                            $.publish("tfl.disambiguation.page.chosen", { pageNumber: parseInt($(this).attr("longdesc")), mapName: "from" });
                        }
                    );
                }
                if ($("#disambiguation-options-to .disambiguation-option").length > 1) {
                    $('#disambiguation-map-to').tfl_maps_disambiguation_map(
                        {
                            optionsElement: $("#disambiguation-options-to"),
                            pageSize: tfl.journeyPlanner.settings.disambiguationItemsPerPage,
                            isNationalBounds: tfl.getQueryParam('NationalSearch') === 'true',
                            mapName: "to"
                        });
                    $(".to-results .pagination .page_link").click(
                        function () {
                            $.publish("tfl.disambiguation.page.chosen", { pageNumber: parseInt($(this).attr("longdesc")), mapName: "to" });
                        }
                    );
                }
                if ($("#disambiguation-options-via .disambiguation-option").length > 1) {
                    $('#disambiguation-map-via').tfl_maps_disambiguation_map(
                        {
                            optionsElement: $("#disambiguation-options-via"),
                            pageSize: tfl.journeyPlanner.settings.disambiguationItemsPerPage,
                            isNationalBounds: tfl.getQueryParam('NationalSearch') === 'true',
                            mapName: "via"
                        });
                    $(".via-results .pagination .page_link").click(
                        function () {
                            $.publish("tfl.disambiguation.page.chosen", { pageNumber: parseInt($(this).attr("longdesc")), mapName: "via" });
                        }
                    );
                }
                $.subscribe("tfl.maps.disambiguation.option.chosen", function (e, option) {
                    $("#disambiguation-options-" + option.mapName + " .disambiguation-option[data-id='" + option.id + "']")
                        .find(".disambiguation-link")[0].click();
                });
            }
        );
    };

})(window.tfl.journeyPlanner.disambiguation = window.tfl.disambiguation || {});
window.tfl.journeyPlanner = window.tfl.journeyPlanner || {};

(function (o) {
    "use strict";
    tfl.logs.create("tfl.journeyPlanner.searchForm: loaded");

    o.isWidget;

    o.widgetInit = function () {
        tfl.logs.create("tfl.journeyPlanner.searchForm: started widgetInit");

        $(".toggle-options, .change-departure-time").click(function (e) {
            var button = $(this);
            var href = button.attr('href');
            var uri = href.substr(0, href.indexOf('#'));
            var hash = href.substr(href.indexOf('#'));
            $.each(['From', 'To', 'FromId', 'ToId', 'FromGeolocation', 'ToGeolocation'], function (k, v) {
                var val = $('#' + v).val();
                if (val !== undefined && val !== '') {
                    if (k == 0) uri += '?';
                    if (k > 0) uri += '&';
                    uri += v + '=' + val;
                }
            });
            button.attr('href', uri + hash);
        });
    };

    o.setupDateOptions = function () {
        //increment the current time at 01,16,31,46 mins past the hour
        var d = new Date();

        var mins = ((15 - d.getMinutes() % 15) * 60000);
        var secs = ((60 - d.getSeconds() % 60) * 1000);
        setTimeout(o.incrementCurrentTime, (mins - secs));
    };

    o.processTempLocalStorage = function () { //el, attr name, attr value
        var tempJPLocalStorage = tfl.storage.get("tempJPLocalStorage", [
            []
        ]);
        if (tempJPLocalStorage.length > 0) {
            for (var i = 0; i < tempJPLocalStorage.length; i++) {
                $(tempJPLocalStorage[i][0]).attr(tempJPLocalStorage[i][1], tempJPLocalStorage[i][2]);
            }
        }
    };

    o.submitSearchForm = function () {
        var fromEl = $("#From");
        var toEl = $("#To");
        var viaEl = $("#Via");
        if (fromEl.attr("data-from-id")) {
            $(this).append("<input type='hidden' id='FromId' name='FromId' value='" + fromEl.attr("data-from-id") + "' />");
        }
        if (toEl.attr("data-to-id")) {
            $(this).append("<input type='hidden' id='ToId' name='ToId' value='" + toEl.attr("data-to-id") + "' />");
        }
        if (viaEl.attr("data-via-id")) {
            $(this).append("<input type='hidden' id='ViaId' name='ViaId' value='" + viaEl.attr("data-via-id") + "' />");
        }
        var tempJPLocalStorage = [
            []
        ];
        if (fromEl.attr("data-from-modes")) {
            tempJPLocalStorage.push(["#From", "data-from-modes", fromEl.attr("data-from-modes")]);
        }
        if (fromEl.attr("data-dataset-name")) {
            tempJPLocalStorage.push(["#From", "data-dataset-name", fromEl.attr("data-dataset-name")]);
        }
        if (toEl.attr("data-to-modes")) {
            tempJPLocalStorage.push(["#To", "data-to-modes", toEl.attr("data-to-modes")]);
        }
        if (toEl.attr("data-dataset-name")) {
            tempJPLocalStorage.push(["#To", "data-dataset-name", toEl.attr("data-dataset-name")]);
        }
        if (viaEl.attr("data-via-modes")) {
            tempJPLocalStorage.push(["#Via", "data-via-modes", viaEl.attr("data-via-modes")]);
        }
        if (viaEl.attr("data-dataset-name")) {
            tempJPLocalStorage.push(["#Via", "data-dataset-name", viaEl.attr("data-dataset-name")]);
        }
        if (tempJPLocalStorage.length > 0) {
            tfl.storage.set("tempJPLocalStorage", tempJPLocalStorage);
        }
        return true;
    };

    o.changeTabs = function () {
        var obj = $('[data-jp-tabs="true"]');
        var isResultsPage = obj.parents('.journey-planner-results').length > 0;
        obj.children().click(function () {
            var jpType = $("#JpType");
            var oldType = jpType.val();
            var link = $(this).children("a");
            var newType = link.attr("data-jptype");
            obj.removeClass(oldType).addClass(newType);
            $("#jp-search-form").removeClass(oldType).addClass(newType);
            jpType.val(newType);
            if (isResultsPage) {
                var optionsForPt = $('#OptionsForPublictransport');
                if (jpType.val() == tfl.dictionary.JpTypeCycling || jpType.val() == tfl.dictionary.JpTypeWalking) {
                    optionsForPt.hide();
                } else {
                    optionsForPt.show();
                }
                // if it has a time it means it has a result for walking/cycling otherwise submit to search for results
                if (link.hasClass('has-time')) {
                    $('.summary-results').removeClass(oldType).addClass(newType);
                    window.tfl.journeyPlanner.results.unshowAllDetailedResults();
                    window.tfl.journeyPlanner.results.currentlyShowing = null;
                } else {
                    $("#jp-search-form").submit();
                }
                window.tfl.journeyPlanner.results.maps(newType);
            }
        });
    };

    o.incrementCurrentTime = function () {
        if ($(".time-options.change-time").length === 0) {
            o.setCurrentTime();
        }
        setTimeout(o.incrementCurrentTime, 900000);
    };

    o.getCurrentDate = function () {
        var d = new Date();
        var yyyy = d.getFullYear().toString();
        var mm = (d.getMonth() + 1).toString();
        var dd = d.getDate().toString();
        return yyyy + (mm[1] ? mm : "0" + mm[0]) + (dd[1] ? dd : "0" + dd[0])
    };

    o.getCurrentTime = function () {
        var d = new Date();
        var mins = d.getMinutes();
        mins = mins = 0 ? mins : (mins + (15 - (mins % 15)));
        var hrs = d.getHours();

        if (mins === 60) {
            mins = 0;
            hrs++;
            if (hrs === 24) {
                hrs = 0;
            }
        }

        var minStr = "" + mins;
        if (minStr.length === 1) {
            minStr = "0" + minStr;
        }

        var hrStr = "" + hrs;
        if (hrStr.length === 1) {
            hrStr = "0" + hrStr;
        }

        return hrStr + minStr;
    };

    o.setCurrentTime = function () {
        var d = new Date();
        var mins = d.getMinutes();
        mins = (mins + (15 - (mins % 15)));
        var hrs = d.getHours();

        if (mins === 60) {
            mins = 0;
            hrs++;
            if (hrs === 24) {
                hrs = 0;
                $("#Date").val($("#Date option:selected").next().val());
                $(".date-of-departure > span").text("Tomorrow");
            }
        }

        var minStr = "" + mins;
        if (minStr.length === 1) {
            minStr = "0" + minStr;
        }

        var hrStr = "" + hrs;
        if (hrStr.length === 1) {
            hrStr = "0" + hrStr;
        }

        $("#Time").val(hrStr + minStr);
        $(".hours span").text(hrStr + ":" + minStr);
    };

    o.changeDepartureTime = function () {
        $(".time-options").toggleClass("change-time");
        return false;
    };

    o.toggleMoreOptions = function () {
        $(this).parents(".r").toggleClass("expanded");
        var link = $(".toggle-options");
        if (link.hasClass("more-options")) {
            link.removeClass("more-options").addClass("less-options").text(tfl.dictionary.LessOptions);
        } else {
            link.removeClass("less-options").addClass("more-options").text(tfl.dictionary.MoreOptions + tfl.dictionary.CustomisedOptions);
        }
        return false;
    };

    o.toggleMoreOptionsIsWidget = function () {
        var button = $(this);
        var href = button.attr('href');
        var hrefStart = href;
        if (href.indexOf('?') != -1) {
            hrefStart = href.substr(0, href.indexOf('?'))
        } else {
            hrefStart = href.substr(0, href.indexOf('#'))
        }
        //var uri = href.substr(0, href.indexOf('#'));
        var uri = "";
        var hash = href.substr(href.indexOf('#'));
        $.each(['From', 'To', 'FromGeolocation', 'ToGelocation'], function (k, v) {
            var val = $('#' + v).val();
            if (val !== undefined && val !== '') {
                if (k == 0) uri += '?';
                if (k > 0) uri += '&';
                uri += v + '=' + val;
            }
        });
        var data = [];
        var fromEl = $("#From");
        var toEl = $("#To");
        if (fromEl.attr("data-from-id")) data.push(["fromId", fromEl.attr("data-from-id")]);
        if (toEl.attr("data-to-id")) data.push(["toId", toEl.attr("data-to-id")]);
        data.push(["date", $("#Date").find("option:selected").val()]);
        data.push(["timeIs", $(".leaving-or-arriving .selected input").val()]);
        data.push(["time", $("#Time").find("option:selected").val()]);
        if (uri.indexOf("?") == -1) {
            uri += "?"
        }
        for (i = 0; i < data.length; i++) {
            uri += "&" + data[i][0] + "=" + data[i][1];
        }
        button.attr('href', hrefStart + uri + hash);

        var tempJPLocalStorage = [
            []
        ];
        if (fromEl.attr("data-from-modes")) {
            tempJPLocalStorage.push(["#From", "data-from-modes", fromEl.attr("data-from-modes")]);
        }
        if (fromEl.attr("data-dataset-name")) {
            tempJPLocalStorage.push(["#From", "data-dataset-name", fromEl.attr("data-dataset-name")]);
        }
        if (toEl.attr("data-to-modes")) {
            tempJPLocalStorage.push(["#To", "data-to-modes", toEl.attr("data-to-modes")]);
        }
        if (toEl.attr("data-dataset-name")) {
            tempJPLocalStorage.push(["#To", "data-dataset-name", toEl.attr("data-dataset-name")]);
        }
        if (tempJPLocalStorage.length > 0) {
            tfl.storage.set("tempJPLocalStorage", tempJPLocalStorage);
        }
    };

    o.geolocateMe = function (inputEl, callback) {
        steal(tfl.mapScriptPath)
            .then(
            function () {
                tfl.logs.create("tfl.journeyPlanner.searchForm: geolocate me");
                var el = $(inputEl);
                el.typeahead("setQuery", "");
                el.attr("placeholder", "Finding address...");
                $(".geolocation-map").removeClass("hidden").children(".image-container").tfl_maps_my_location_map();
                $(document.body).addClass("showing-map");
                $.subscribe("tfl.maps.mylocation.geolocation.success", function (e, data) {
                    tfl.logs.create("tfl.maps.mylocation.geolocation.success");
                    var latLng = new google.maps.LatLng(data.location.lat, data.location.lng);
                    var geocodeSuccess = function (result) {
                        var geolocated = tfl.geolocation.formatAddress(result);
                        $(inputEl).val(geolocated).typeahead("setQuery", geolocated);
                        $(inputEl + "Geolocation").val(data.location.lng + "," + data.location.lat);
                        el.attr("placeholder", el.attr("id"));
                        if (callback) {
                            callback();
                        }
                    };
                    var failure = function () {
                    };
                    tfl.geolocation.reverseGeocode(latLng, geocodeSuccess, failure);
                });
                $.subscribe("tfl.maps.mylocation.geolocation.failure", function (e, data) {
                    tfl.logs.create("tfl.maps.mylocation.geolocation.failure: failure code " + data.failureType);
                    var msg = "We couldn't get your current location";
                    if (data.message) {
                        msg += " " + data.message;
                    }
                    msg += ".";
                    $(".geolocation-error").text(msg).removeClass("hidden");
                    el.attr("placeholder", el.attr("id"));
                    $(document.body).removeClass("showing-map");
                    $(".geolocation-map").addClass("hidden")
                });
            }
        );
    };

    o.reverseGeocode = function (position, button) {
        var geocoder = new google.maps.Geocoder();
        var latLng = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
        geocoder.geocode({ 'latLng': latLng }, function (results, status) {
            if (status == google.maps.GeocoderStatus.OK) {
                $(button).removeClass("loading").addClass("loaded");
            } else {
                alert('Geocoder failed due to: ' + status);
            }
        });
    };

    o.clearGeolocation = function () {
        $(".geolocation-map").addClass("hidden");
        $("#FromGeolocation").val("");
        $("#ToGeolocation").val("");
        $("#ViaGeolocation").val("");
    };

    o.buildSwitchButton = function () {
        $(".geolocation-box").after("<a href='javascript:void(0)' class='switch-button hide-text'>Switch from and to<a>");
    };

    o.switchFromTo = function () {
        var fromEl = $("#From");
        var fromGeoEl = $("#FromGeolocation");
        var toEl = $("#To");
        var toGeoEl = $("#ToGeolocation");
        var from = fromEl.val();
        var fromId = fromEl.attr("data-from-id") ? fromEl.attr("data-from-id") : "";
        var fromModes = fromEl.attr("data-from-modes") ? fromEl.attr("data-from-modes") : "";
        var fromDatasetName = fromEl.attr("data-dataset-name") ? fromEl.attr("data-dataset-name") : "";
        var fromGeolocation = fromGeoEl.val();
        var to = toEl.val();
        var toId = toEl.attr("data-to-id") ? toEl.attr("data-to-id") : "";
        var toModes = toEl.attr("data-to-modes") ? toEl.attr("data-to-modes") : "";
        var toDatasetName = toEl.attr("data-dataset-name") ? toEl.attr("data-dataset-name") : "";
        var toGeolocation = toGeoEl.val();
        fromEl.typeahead("setQuery", to)
        fromEl.attr("data-from-id", toId);
        fromEl.attr("data-from-modes", toModes);
        fromEl.attr("data-dataset-name", toDatasetName);
        fromEl.trigger("change");
        toEl.typeahead("setQuery", from)
        toEl.val(from);
        toEl.attr("data-to-id", fromId);
        toEl.attr("data-to-modes", fromModes);
        toEl.attr("data-dataset-name", fromDatasetName);
        toEl.trigger("change");
        fromGeoEl.val(toGeolocation);
        toGeoEl.val(fromGeolocation);
    };

    //autocomplete variables
    var sourceRecentSearches = tfl.autocomplete.sources.recentSearches;
    var sourceTurnOffRecentSearches = tfl.autocomplete.sources.journeyPlannerRecentSearchesTurnOffFooter;
    var sourcesTurnRecentSearchesOff = [tfl.autocomplete.sources.journeyPlannerSuggestions];
    var sourceTurnOnRecentSearches = tfl.autocomplete.sources.journeyPlannerRecentSearchesTurnOnFooter;
    var sourcesTurnRecentSearchesOn = [tfl.autocomplete.sources.journeyPlannerSuggestions];

    o.setupAutocomplete = function (freshPageLoad) {
        //setup sources
        o.autocompleteAddRecentSearches(!freshPageLoad);
        o.autocompleteAddGeolocation();

        sourceRecentSearches.name = "journey-planner-recent-searches";
        sourceRecentSearches.local = tfl.journeyPlanner.recentSearches.searches;

        sourceTurnOnRecentSearches.contextCallback = function (inputEl, datum) {
            $(inputEl).parent().siblings(tfl.dictionary.RemoveContentClass).trigger("clear-search-box")
            o.destroyAutocomplete();
            $("#From, #To, #Via").each(function () {
                tfl.autocomplete.setup("#" + $(this).attr("id"), sourcesTurnRecentSearchesOff);
                $("#" + $(this).attr("id")).attr("placeholder", $(this).attr("id"))
            });
        };
        sourceTurnOffRecentSearches.contextCallback = function (inputEl, datum) {
            $(inputEl).parent().siblings(tfl.dictionary.RemoveContentClass).trigger("clear-search-box")
            o.destroyAutocomplete();
            $("#From, #To, #Via").each(function () {
                tfl.autocomplete.setup("#" + $(this).attr("id"), sourcesTurnRecentSearchesOn);
                $("#" + $(this).attr("id")).attr("placeholder", $(this).attr("id"))
            });
        };

        if (tfl.journeyPlanner.recentJourneys.usingRecentJourneys) {
            tfl.autocomplete.setup("#From", sourcesTurnRecentSearchesOff, freshPageLoad);
            //remove geolocation
            //sources.splice(0, 1);
            tfl.autocomplete.setup("#To", sourcesTurnRecentSearchesOff, freshPageLoad);
            tfl.autocomplete.setup("#Via", sourcesTurnRecentSearchesOff, freshPageLoad);
        } else {
            tfl.autocomplete.setup("#From", sourcesTurnRecentSearchesOn, freshPageLoad);
            //remove geolocation
            //sources.splice(0, 1);
            tfl.autocomplete.setup("#To", sourcesTurnRecentSearchesOn, freshPageLoad);
            tfl.autocomplete.setup("#Via", sourcesTurnRecentSearchesOn, freshPageLoad);
        }
    }

    o.destroyAutocomplete = function () {
        $("#From, #To, #Via").each(function () {
            $(this).parent().siblings(tfl.dictionary.RemoveContentClass).unbind("clear-search-box");
            $(this).typeahead("destroy");
            $(this).off("typeahead:selected typeahead:autocompleted keydown");
        });
        sourcesTurnRecentSearchesOff = [tfl.autocomplete.sources.journeyPlannerSuggestions];
        o.autocompleteAddRecentSearches(true);
        o.autocompleteAddGeolocation();
    }

    o.autocompleteAddRecentSearches = function (excludeRecentSearches) {
        if (tfl.storage.isLocalStorageSupported()) {
            sourcesTurnRecentSearchesOff.splice(0, 0, sourceTurnOffRecentSearches);
            if (tfl.journeyPlanner.recentJourneys.usingRecentJourneys && !excludeRecentSearches) {
                sourcesTurnRecentSearchesOff.splice(0, 0, sourceRecentSearches);
            }
            sourcesTurnRecentSearchesOn.splice(0, 0, sourceTurnOnRecentSearches);
        }
    };

    o.autocompleteAddGeolocation = function () {
        if (tfl.geolocation.isGeolocationSupported()) {
            sourcesTurnRecentSearchesOff.splice(0, 0, tfl.autocomplete.sources.geolocation);
            sourcesTurnRecentSearchesOn.splice(0, 0, tfl.autocomplete.sources.geolocation);
        }
    };

    o.inputKeydownCallbackSetup = function () {
        $("#From, #To, #Via").each(function () {
            $(this).on("keydown", function (e) {
                //tab (9), enter key (13), cursor keys (37-40), pg up (33), pg dwn (34), end (35), home (36), (shift,ctr,alt,caps lock 16-20), insert (45), win key (91)
                if (e.which !== 9 && e.which !== 13 && (e.which < 33 || e.which > 40) && (e.which < 16 || e.which > 20) && e.which !== 45 && e.which !== 91) {
                    //remove ID if user changes value
                    $(this).removeAttr("data-" + $(this).attr("id").toLowerCase() + "-id");
                    $(this).removeAttr("data-" + $(this).attr("id").toLowerCase() + "-modes");
                    $(this).removeAttr("data-dataset-name");
                }
                ;
            });
        });
    };

    o.init = function (isWidget) {
        tfl.logs.create("tfl.journeyPlanner.searchForm.init: started (widget = " + isWidget + ")");
        o.isWidget = isWidget || false;

        o.processTempLocalStorage();

        var arrivingOrLeaving = $(".leaving-or-arriving .selected label").text();
        $(".time-options").prepend("<div class='time-defaults'><p><strong>" + arrivingOrLeaving + ":</strong> <span>now</span></p><a href='javascript:void(0)' class='change-departure-time'>change time</a></div>");
        if ((o.getCurrentTime() != $("#Time").find("option:selected").val()) || o.getCurrentDate() != $("#Date").find("option:selected").val()) {
            $(".change-time-options").show();
            $(".time-defaults").hide();
        }

        $(".extra-options").prepend('<a href="/plan-a-journey/#more-options" class="toggle-options more-options">Accessibility &amp; travel options</a>');

        $("#From").on("keydown", function (e) {
            //tab (9), enter key (13), cursor keys (37-40), pg up (33), pg dwn (34), end (35), home (36), (shift,ctr,alt,caps lock 16-20), insert (45), win key (91)
            if (e.which !== 9 && e.which !== 13 && (e.which < 33 || e.which > 40) && (e.which < 16 || e.which > 20) && e.which !== 45 && e.which !== 91) {
                o.clearGeolocation();
            }
        });

        $("#jp-search-form").submit(o.submitSearchForm);

        tfl.journeyPlanner.recentSearches.loadSearches();

        var jpMap = $('<div class="geolocation-map hidden"><div class="image-container"></div></div>');
        $("#FromGeolocation").after(jpMap);

        $(".change-departure-time").click(o.changeDepartureTime);
        o.setupDateOptions();

        if (!o.isWidget) {
            o.changeTabs();
            $(".toggle-options").click(o.toggleMoreOptions);
        } else {
            $(".toggle-options").click(o.toggleMoreOptionsIsWidget);
        }

        if (window.location.hash === "#more-options") {
            $(".toggle-options").click();
        } else if (window.location.hash === "#change-time") {
            o.changeDepartureTime();
        }

        $("#IsAsync").val(true);

        //add autocomplete
        o.setupAutocomplete(true);
        o.inputKeydownCallbackSetup();
    };

}(window.tfl.journeyPlanner.searchForm = window.tfl.journeyPlanner.searchForm || {}));
(function (o) {
    "use strict";
    o.initialize = function () {
        o.setUpResultTags();
        //$(".results-list-all").find("data-pull-content").click(tfl.navigation.pullContentHandler);
        //o.setUpSelectBoxes();
        tfl.interactions.setupSelectBoxes();
        o.resetSelectBoxes();
        o.populateSearchBoxes();
        o.selectBoxFiltering();
        o.selectBoxChange();
        // o.PaginateSearchFilters();
    };

    o.loadViaAjax = function (selector) {
        $.ajax({
            url: "/modules/filterby"
        }).done(function (response) {
                var html = $(response);
                $(selector).prepend(html);
                o.initialize();
            });
    };

    o.resetSelectBoxes = function () {
        $('.reset-filters').click(function () {
            $(".selector").children(".filter-box").each(function () {
                $(this).prop('selectedIndex', 0);
                $(this).siblings("span").text($(this).children("option:first-child").text());
                o.selectBoxFiltering();
            });
        });

    };

    o.tagSet = function () {
        var selectedTag = $(this).text();
    }

    o.selectBoxChange = function () {
        $("select.filter-box").change(function () {
            o.selectBoxFiltering();
        });
    };

    o.setUpResultTags = function () {
        $(".results-list-all").find("data-pull-content").click(tfl.navigation.pullContentHandler);
        $(".results-list-all").hide();
        $(".results-list-all").children("#results-list").children("li.search-results").each(function () {
            //take the list of tags provided and turn them into tag links
            var tagString = $(this).children(".search-tags");
            var tagStringValue = tagString.text();
            var tagStringSplit = tagStringValue.split(",")
            var numTags = (tagStringSplit.length);
            var y = 0;
            for (y = 0; y < numTags; y++) {
                tagString.append("<a class='result-tag'>" + tagStringSplit[y] + "</a>");
            }
            var $tmp = tagString.children().remove();
            tagString.text('').append($tmp);
        });
    };

    o.selectBoxFiltering = function () {
        $("#pagination-items").empty();
        $(".pagination").remove();
        //for each result

        $(".search-results").each(function () {
            var hide = false;
            //create an array
            var ResultTags = new Array();
            //look for all links in the result - these will be the tags - for each of these...
            $(this).find("a.result-tag").each(function () {
                //get its value
                var tag = $(this).text();
                //put it into the array
                ResultTags[ResultTags.length] = tag;
            });

            var selectBox = this;
            //for each selector
            $(".selector").children(".filter-box").each(function () {
                //get its selected value
                var selectedValue = $(this).siblings("span").text();
                //if the value is All then go to the next iteration
                if (selectedValue == "All") {
                    return true;
                }
                //Check the array for the value if its not in there break out
                var inArrayCheck = ($.inArray(selectedValue, ResultTags)); //returns -1 if not in array or otherwise returns the index of the element
                if (inArrayCheck == -1) {
                    hide = true;
                    $(this).addClass("not-here-anymore");
                    return false;
                }
            });

            if (!hide) {
                $(this).clone(true).appendTo("#pagination-items")
            }

        });
        o.PaginateSearchFilters();
        //});
    };

    o.PaginateSearchFilters = function () {
        //check that there are rows to paginate
        var countChecker = $("#pagination-items").children().size();
        if (countChecker > 0) {
            $(".results-wrapper ul").after("<div class='pagination' />");
            $(".results-wrapper").pajinate({
                items_per_page: 5,
                item_container_id: '#pagination-items',
                nav_panel_id: '.pagination',
                num_page_links_to_display: 10,
                show_prev_next: true,
                show_first_last: true

            });
        }
    };

    o.populateSearchBoxes = function () {
        var key = '';
        for (var i = 0; i < filters.length; i++) {
            for (key in filters[i]) {
                var names = filters[i][key];
                var loopedSelectBox = $(".filter-box")[i]
                $('.filter-by-box').find(loopedSelectBox).attr("name", key);
                for (var j = 0; j < names.length; j++) {
                    $(loopedSelectBox).append("<option>" + names[j] + "</option>")
                }
            }
        }

    };

    $(document).ready(function () {
        tfl.logs.create("tfl.searchFilters: looking for search filters");
        var selector = $(".search-filters-wrapper");
        if (selector.length > 0) {
            tfl.logs.create("tfl.searchFilters: loading search filters");
            o.loadViaAjax(selector);
        }
    });

})(window.tfl.searchFilters = window.tfl.searchFilters || {});
(function (o) {
    o.tabs = function (elm) {
        tfl.logs.create("tfl.navigation.tabs: loaded");
        var tabs = $(elm).children();
        tabs.click(function (event) {
            event.preventDefault();
            tabs.removeClass('selected');
            $(this).addClass('selected');
            tfl.logs.create("tfl.navigation.tabs: " + $(this).find('a').attr('href'));
        });
    };
    o.tabs("[data-tabs='true']");
}(window.tfl.navigation = window.tfl.navigation || {}));