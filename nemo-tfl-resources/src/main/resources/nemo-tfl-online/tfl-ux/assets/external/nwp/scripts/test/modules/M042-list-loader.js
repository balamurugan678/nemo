(function (o) {
    var $body,
        alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    var listLoader = function ($list, settings) {
        var t = this,
            $tabletExpandedTarget = $('.list-loader-results', $list),
            breakpointExpandOn = false;

        // set up bus groups
        var $buses = $('[data-' + settings.dataAttr + ']'),
            i,
            $parent = $('<div/>'),
            $links = $('<div/>');
        // setup array
        if (typeof(settings.baskets.numeric) !== "object") {
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
        this.sortedList = { };
        for (i = 0; i < $buses.length; i += 1) {
            var $bus = $buses.eq(i),
                busName = $bus.data(settings.dataAttr);
            if (!t.sortedList.hasOwnProperty(busName)) {
                t.sortedList[busName] = $bus;
            }
        }

        // function for adding new baskets

        function newBasket(type) {
            var link;
            if (type === "numeric") {
                link = $('<a href="javascript:;" data-result="numeric-' + (currentBasket + 1) + '">' + (settings.baskets[type][currentBasket] + 1) + ' - ' + settings.baskets[type][currentBasket + 1] + '</a>');
            } else {
                var startLetter = settings.baskets[type][currentBasket] === "A" ? "A" : alphabet.charAt(alphabet.indexOf(settings.baskets[type][currentBasket]) + 1);
                link = $('<a href="javascript:;" data-result="alpha-' + (currentBasket + 1) + '">' + startLetter + ' - ' + settings.baskets[type][currentBasket + 1] + '</a>');
            }
            $links.append($('<li class="list-loader-option">').append($('<div class="list-loader-link"></div>').append(link)));
            if (currentBasket % 2) {
                $parent.append($links.contents());
                $links = $('<div/>');
            }
            if ($currentBasket) {
                if (type === "numeric") {
                    $parent.append($('<li class="list-loader-result numeric-' + currentBasket + '"></li>').append($currentBasket));
                } else {
                    $parent.append($('<li class="list-loader-result alpha-' + currentBasket + '"></li>').append($currentBasket));
                }
            }

            $currentBasket = $('<ul class="bus-list"/>');

            currentBasket += 1;
            return;
        }

        var alpha = [],
            currentBasket = 0,
            $currentBasket;

        newBasket('numeric');
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
        for (i = 0; i < alpha.length; i += 1) {
            var currentChar = alpha[i][0].charAt(0);
            while (currentChar > settings.baskets.alpha[currentBasket]) {
                newBasket('alpha');
            }
            $currentBasket.append(alpha[i][1]);
        }
        if (!alpha.length) {
            $parent.append($('<li class="list-loader-result numeric-' + currentBasket + '"></li>').append($currentBasket));
        } else {
            newBasket('alpha');
        }
        $('.bus-list').prepend($parent.contents());


        function clone() {
            if (settings.expand) {
                var $current = $list.find('.expanded');
                $('#routeSelector a').removeAttr('data-jumpto data-jumpback').removeData();
                if ($current.length) {
                    var med = $('<div class="medium"/>'),
                        large = $('<div class="large"/>'),
                        cont = $current.children('.list-loader-result').clone(true);
                    med.append(cont.clone(true));
                    large.append(cont.clone(true));

                    //conver large columns
                    var res = $(),
                        datas = large.find('[data-result]');

                    for (var i = 0; i < datas.length; i += 1) {
                        res = res.add(large.find('.' + datas.eq(i).data('result')));
                        if (!((i + 1) % 4)) {
                            datas.eq(i).closest('.list-loader-option').addClass('end-of-row').after(res);
                            res = $();
                        }
                    }
                    if (res.length) {
                        datas.eq(datas.length - 1).closest('.list-loader-option').addClass('end-of-row').after(res);
                    }

                    $tabletExpandedTarget.empty().append(med).append(large).removeClass('hidden');

                    var $firstLink = $tabletExpandedTarget.find('.routes-list:visible .route:first-child a, .bus-list:visible .list-loader-option:first-child a');
                    var $lastLink = $tabletExpandedTarget.find('.routes-list:visible .route:last-child a');
                    $lastLink = $lastLink.length ? $lastLink : $('.bus-list:visible li:last-child').prevAll('.list-loader-option').first().find('a');
                    var $next = $current.next().find('>.list-loader-link a');
                    $firstLink.focus().data('jumpback', $current.find('a'));
                    $lastLink.data('jumpto', $next);
                    $current.find('a').data('jumpto', $firstLink);
                    $current.next().find('a').data('jumpback', $lastLink);
                } else {
                    $tabletExpandedTarget.addClass('hidden');
                }
            }
        }

        function breakpointExpand() {
            if (!breakpointExpandOn) {
                $(window).off('enterBreakpointMedium.listLoader').on("enterBreakpointMedium.listLoader", function () {
                    clone();
                });
                breakpointExpandOn = true;
            }
        }

        $list.find('.list-loader-options > li.list-loader-option > .list-loader-result').prev('.list-loader-link').children('a').on('click', function (e) {
            e.preventDefault();
            var $this = $(this),
                $opt = $this.closest('.list-loader-option'),
                $list = $opt.parent();

            if ($opt.hasClass('expanded')) {
                $tabletExpandedTarget.addClass('hidden');
                $opt.removeClass('expanded');
                $('.hero-content-container').removeClass('hidden');
                $('#routeSelector a').removeAttr('data-jumpto data-jumpback').removeData();
            } else {
                $opt.siblings('.expanded').removeClass('expanded');
                $opt.addClass('expanded');
                $('.hero-content-container').addClass('hidden');
                if ($body.hasClass('breakpoint-Medium')) {
                    clone();
                } else {
                    breakpointExpand();
                }
            }
        });

        $list.find('.list-loader-result .list-loader-link > a').on('click', function (e) {
            e.preventDefault();
            var $this = $(this),
                $opt = $this.closest('.list-loader-option'),
                $list = $opt.parent().parent();
            if ($opt.hasClass('expanded')) {
                $list.removeClass('expanded').find('.expanded, .prev').removeClass('expanded prev');
                $list.closest('.list-loader-option').removeClass('secondary-expanded');

            } else {

                if ($list.hasClass('expanded')) {
                    var $prev = $list.find('.expanded, .prev');
                    $prev.removeClass('expanded prev');

                } else {
                    $list.addClass('expanded');
                    $list.closest('.list-loader-option').addClass('secondary-expanded');
                }

                var $prev = $opt.prev('.list-loader-option');
                while ($prev.length) {
                    $prev.addClass('prev');
                    $prev = $prev.prev('.list-loader-option');
                }

                $opt.addClass('expanded');
                $list.find('.' + $this.data('result')).toggleClass('expanded');

            }
            var $routeSel = $('#routeSelector');
            var $busListGroups = $routeSel.find('.bus-list.groups');
            $busListGroups.find('a').removeAttr('data-jumpto data-jumpback').removeData();

            var $firstLink = $tabletExpandedTarget.find('.bus-list:visible .list-loader-option:first-child a');
            var $lastLink = $tabletExpandedTarget.find('.bus-list:visible .list-loader-option:last-child a');
            var $next = $parent.find('.list-loader-options > .expanded + .list-loader-option a');
            $firstLink.data('jumpback', $routeSel.find('.list-loader-options > .expanded a'));
            $lastLink.data('jumpto', $next);

            var $this = $busListGroups.find('.list-loader-option.expanded >.list-loader-link a');
            var $first = $busListGroups.find('.bus-list:visible li:first-child a');
            var $next = $busListGroups.find('.list-loader-option.expanded + .list-loader-option >.list-loader-link a');
            var $last = $busListGroups.find('.bus-list:visible li:last-child a');
            $first.focus().data('jumpback', $this);
            $this.data('jumpto', $first);
            if ($next.length) {
                $last.data('jumpto', $next);
                $next.data('jumpback', $last);

                var $lastOpt = $this.closest('.list-loader-option.expanded').nextAll('.list-loader-result').first().prev('.list-loader-option').find('a');
                var lastNext = $lastOpt.closest('.list-loader-option').nextAll('.list-loader-option').first().find('a');
                if (lastNext.length) {
                    $lastOpt.data('jumpto', lastNext);
                    lastNext.data('jumpback', $lastOpt);
                }
            }
        });

        function switcharoo(res, $targ) {
            $targ.find('.expanded, .prev').removeClass('expanded prev');
            if (res !== undefined && res !== "") {
                var $prev = $targ.find('[data-result=' + res + ']').closest('.list-loader-option').addClass('expanded').prev('.list-loader-option');
                while ($prev.length) {
                    $prev.addClass('prev');
                    $prev = $prev.prev('.list-loader-option');
                }
                $targ.find('.' + res).addClass('expanded');
                $targ.children('.list-loader-result').addClass('expanded');
                if ($targ.hasClass('list-loader-option expanded')) {
                    $targ.addClass('secondary-expanded');
                }
                ;
            } else {
                $targ.removeClass('secondary-expanded');
            }
        }

        $(window).on('enterBreakpointLarge.listLoader-resizer', function () {
            var res = $tabletExpandedTarget.find('.medium > .list-loader-result .list-loader-option.expanded').find('.list-loader-link > a').data('result'),
                $targ = $tabletExpandedTarget.find('.large');
            switcharoo(res, $targ);
        });
        $(window).on('exitBreakpointMedium.listLoader-resizer', function () {
            var res = $tabletExpandedTarget.find('.medium > .list-loader-result .list-loader-option.expanded').find('.list-loader-link > a').data('result'),
                $targ = $list.find('.list-loader-options > .list-loader-option.expanded');
            switcharoo(res, $targ);
        });
        $(window).on('exitBreakpointLarge.listLoader-resizer', function () {
            var res = $tabletExpandedTarget.find('.large > .list-loader-result .list-loader-option.expanded').find('.list-loader-link > a').data('result'),
                $targ = $tabletExpandedTarget.find('.medium');
            switcharoo(res, $targ);
        });


    };

    o.init = function (target, options) {
        var $parent = $(target);
        $body = $('body');

        if ($parent.length === 0 || !$parent.hasClass('list-loader')) {
            return false;
        }

        var settings = {
            dataAttr: 'bus',
            baskets: {
                numeric: 50,
                alpha: ['A', 'F', 'L', 'Q']
            },
            highestBusNumber: 1000,
            expand: true
        };
        if (options) {
            settings = $.extend(settings, options);
        }

        new listLoader($parent, settings);
    };


}(window.tfl.listLoader = window.tfl.listLoader || {}));