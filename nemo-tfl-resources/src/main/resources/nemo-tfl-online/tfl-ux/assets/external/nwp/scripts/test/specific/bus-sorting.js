(function (o) {
    "use strict";
    var $body,
        alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    function busList($parent, settings) {
        var t = this,
            $buses = $parent.children('[data-' + settings.dataAttr + ']'),
            i;

        // setup array
        if (typeof (settings.baskets.numeric) !== "object") {
            var step = parseInt(settings.baskets.numeric, 10),
                j;
            settings.baskets.numeric = [];

            for (j = 0; j <= settings.highestBusNumber; j += step) {
                settings.baskets.numeric.push(j);
            }
        }
        if (settings.baskets.alpha[settings.baskets.alpha.length - 1] !== "Z") {
            settings.baskets.alpha.push("Z");
        }

        // create list
        this.sortedList = {};
        for (i = 0; i < $buses.length; i += 1) {
            var $bus = $buses.eq(i),
                busName = $bus.data(settings.dataAttr);
            if (!t.sortedList.hasOwnProperty(busName)) {
                t.sortedList[busName] = $bus;
            }
        }

        // function for adding new baskets
        function newBasket(type) {
            if ($currentBasket) {
                $parent.append($currentBasket);
            }
            $currentBasket = $('<div class="clearfix"/>')
            if (type === "numeric") {
                $currentBasket.prepend('<h3>' + settings.baskets[type][currentBasket] + ' to ' + settings.baskets[type][currentBasket + 1] + '</h3>');
            } else {
                var startLetter = settings.baskets[type][currentBasket] === "A" ? "A" : alphabet.charAt(alphabet.indexOf(settings.baskets[type][currentBasket]) + 1);
                $currentBasket.prepend('<h3>' + startLetter + ' to ' + settings.baskets[type][currentBasket + 1] + '</h3>');
            }
            currentBasket += 1;
            return;
        }

        var alpha = [],
            currentBasket = 0,
            $currentBasket,
            currentBus,
            $nightBasket;
        if (settings.seperateNight) {
            $nightBasket = $('<div class="clearfix"><h3>Night buses</h3></div>');
        }

        newBasket('numeric')
        // sort list into bins
        for (var bus in t.sortedList) {
            if (t.sortedList.hasOwnProperty(bus)) {
                if (!alpha.length) {
                    var busNumber = parseInt(bus, 10);
                    if (!isNaN(busNumber)) {
                        // new basket
                        while (busNumber > settings.baskets.numeric[currentBasket]) {
                            newBasket('numeric');
                        }
                        $currentBasket.append(t.sortedList[bus]);
                    } else {
                        currentBasket = 0;
                        newBasket('alpha');
                        alpha.push([bus, t.sortedList[bus]]);
                    }
                } else {
                    alpha.push([bus, t.sortedList[bus]]);
                }
            }
        }
        // sort alphas
        alpha.sort();
        for (i = 0; i < alpha.length; i += 1) {
            var currentChar = alpha[i][0].charAt(0);
            while (currentChar > settings.baskets.alpha[currentBasket]) {
                newBasket('alpha');
            }
            if (settings.seperateNight && currentChar === "N") {
                $nightBasket.append(alpha[i][1]);
            } else {
                $currentBasket.append(alpha[i][1]);
            }
        }
        $parent.append($currentBasket);
        if (settings.seperateNight) {
            $parent.append($nightBasket);
        }
    }

    o.init = function (arg, opts) {
        var $parent = $(arg);
        $body = $('body');

        if ($parent.length === 0) {
            return false;
        }

        var settings = {
            dataAttr: 'bus-code',
            baskets: {
                numeric: 50,
                alpha: ['A', 'K', 'P'],
                seperateNight: false
            },
            highestBusNumber: 1000
        };
        if (opts) {
            settings = $.extend(settings, opts);
        }

        return new busList($parent, settings);
    }


}(window.tfl.busSorting = window.tfl.busSorting || {}));