(function (o) {
    var $body,
        rbrCounter = 0,
        margin = 10;

    function sortByWidth(a, b) {
        return a.width === b.width ? 0 : (a.width > b.width ? 1 : -1);
    }

    function responsiveButtonRow($row) {

        var t = this,
            prefix = "rbr-",
            childPrefix = "child-",
            buttons = 0,
            $snapTarget,
            snapTarget,
            snapTargetBP,
            currentBP = -1,
            snapExpanded = false;

        t.rbrId = "rbrid-" + rbrCounter++,
            t.widths = {};
        t.preSnapButtons = [];
        t.postSnapButtons = [];

        t.minWidth = 0;
        t.totalWidth = 0;

        // go through the immediate children of the row.
        $row.addClass(t.rbrId).children().each(function () {
            var $this = $(this),
                name;

            if (!$this.children().length) {
                $this.remove();
                return;
            }

            name = prefix + buttons++;

            $this.addClass(name);

            t.widths[name] = {
                $dom: $this,
                selector: "." + name,
                children: []
            };

            if (!$this.hasClass(prefix + 'group')) {

                // it's not a button group
                var w = $this.outerWidth();
                // as it is a button - label it and add it to the widths
                t.widths[name].width = w;
                t.preSnapButtons.push(t.widths[name]);
                t.postSnapButtons.push(t.widths[name]);

                // button always to be shown - add it to min width
                t.minWidth += w;
            } else {
                // it is a button group - lets look at the children
                var childNumber = 0;
                snapTarget = t.widths[name];

                $this.children().each(function () {
                    $child = $(this),
                        w = $child.outerWidth();

                    // this is the button to show if the group is collapsed 
                    if ($child.hasClass(prefix + 'group-button')) {
                        // it contributes to min width. It detracts from max width as it is not shown when its children are expanded
                        t.minWidth += w;
                        t.totalWidth -= w;
                        t.widths[name].width = w;
                        t.preSnapButtons.push(t.widths[name]);

                    } else {
                        // children of groups get their own names + selectors
                        var childName = childPrefix + childNumber++;
                        $child.addClass(childName);
                        t.widths[name].children.push({
                            width: w,
                            $dom: $child,
                            selector: "." + name + " ." + childName,
                            parent: t.widths[name]
                        });
                        t.postSnapButtons.push(t.widths[name].children[t.widths[name].children.length - 1]);
                        // they solely contribute to max width.
                        t.totalWidth += w;

                    }
                });
            }
        });

        t.totalWidth += t.minWidth;

        t.sortedPreSnapButtons = t.preSnapButtons.slice(0);
        t.sortedPostSnapButtons = t.postSnapButtons.slice(0);

        t.sortedPreSnapButtons.sort(sortByWidth);
        t.sortedPostSnapButtons.sort(sortByWidth);

        // css generation function;
        function generateCSS(bp, idx) {
            var CSSprefix = '.' + t.rbrId + ".BP-" + idx + " ",
                breakPointCSS = "",
                buttons;

            function helperAffix(side, btns) {
                if (side === "centre") {
                    var combinedW = 0,
                        totalW = 0;
                    for (var i = 0; i < btns.length; i++) {
                        totalW += btns[i].width;
                    }
                    for (var i = 0; i < btns.length; i++) {
                        breakPointCSS += CSSprefix + btns[i].selector + "{position:absolute;left:50%;margin-left:" + ((-totalW / 2) + combinedW) + "px;}";
                        combinedW += btns[i].width;
                    }
                    return totalW;
                } else {
                    var combinedW = 0;
                    if (side === "right") {
                        btns.reverse()
                    }
                    for (var i = 0; i < btns.length; i++) {
                        breakPointCSS += CSSprefix + btns[i].selector + "{position:absolute;" + side + ":" + combinedW + "px;}";
                        combinedW += btns[i].width;
                    }
                    breakPointCSS += CSSprefix + "{padding-" + side + ": " + combinedW + "px;}";
                }
            }

            function helperStretch(btns) {
                var i,
                    len = btns.length,
                    wid = 100 / len;

                for (i = 0; i < len; i++) {
                    if (btns[i].parent !== undefined) {
                        var siblings = btns[i].parent.children,
                            stretchSiblings = 0,
                            k;
                        for (k = 0; k < siblings.length; k++) {
                            if (btns.indexOf(siblings[k]) > -1) {
                                stretchSiblings++;
                            }
                        }

                        breakPointCSS += CSSprefix + btns[i].selector + "{width: " + 100 / stretchSiblings + "%;}";
                        breakPointCSS += CSSprefix + btns[i].parent.selector + "{width: " + wid * stretchSiblings + "%;}";
                    } else {
                        breakPointCSS += CSSprefix + btns[i].selector + "{width: " + wid + "%;}";
                        if (btns[i].children.length) {
                            breakPointCSS += CSSprefix + btns[i].selector + ' .' + prefix + 'group-button{width: 100%;}';
                        }
                    }
                }
            }

            // is it post snap?
            if (idx >= snapTargetBP) {
                breakPointCSS += CSSprefix + snapTarget.selector + " ." + prefix + "button{display: block;}";
                breakPointCSS += CSSprefix + snapTarget.selector + " ." + prefix + "group-button{display: none;}";

                buttons = t.postSnapButtons;
            } else {
                buttons = t.preSnapButtons;
            }

            // stretching only one element
            if (bp.toStretch.length === 1) {
                var toStretchIdx = $(buttons).index(bp.toStretch[0]);
                if (bp.toStretch[0].$dom.is(':first-child') && !bp.toStretch[0].$dom.parent().hasClass(prefix + 'group')) {
                    helperAffix("right", buttons.slice(1));
                } else if (bp.toStretch[0].$dom.is(':last-child') && !bp.toStretch[0].$dom.parent().hasClass(prefix + 'group')) {
                    helperAffix("left", buttons.slice(0, -1));
                } else {
                    helperAffix("left", buttons.slice(0, toStretchIdx));
                    helperAffix("right", buttons.slice(toStretchIdx + 1));
                }
                helperStretch(bp.toStretch);
            }

            // stretching 2 - needs to be generalised for 2 to i...
            if (bp.toStretch.length === 2) {
                var $btns = $(buttons),
                    toStretchIdx = [$btns.index(bp.toStretch[0]), $btns.index(bp.toStretch[1])];
                if (buttons.length === 2) {
                    // just two, dont do anything
                } else if (toStretchIdx[0] <= 1 && toStretchIdx[1] <= 1) {
                    // stretch two at start
                    helperAffix('right', buttons.slice(2));
                } else if (toStretchIdx[0] >= buttons.length - 2 && toStretchIdx[1] >= buttons.length - 2) {
                    // stretch two at end
                    helperAffix('left', buttons.slice(0, -2));
                } else if (Math.abs(toStretchIdx[0] - toStretchIdx[1]) === 1) {
                    // stretch two together in center
                    var low = toStretchIdx[0] < toStretchIdx[1] ? toStretchIdx[0] : toStretchIdx[1];
                    helperAffix("left", buttons.slice(0, low));
                    helperAffix("right", buttons.slice(low + 2));
                } else if (Math.abs(toStretchIdx[0] - toStretchIdx[1]) === buttons.length - 1) {
                    // stretch one at each end
                    var totalW = helperAffix("centre", buttons.slice(1, -1)),
                        low = toStretchIdx[0] < toStretchIdx[1] ? 0 : 1,
                        high = low ? 0 : 1;
                    breakPointCSS += CSSprefix + bp.toStretch[low].selector + "{padding-right: " + (totalW / 2) + "px;}";
                    breakPointCSS += CSSprefix + bp.toStretch[high].selector + "{padding-left: " + ((totalW / 2) + margin) + "px;}";
                } else {
                    // strip the edges + repeat section above.
                    var orderId = toStretchIdx.slice(0);
                    orderId.order();
                    if (orderId[0] > 0) {
                        helperAffix("left", buttons.slice(0, orderId[0]));
                    } else if (orderId[orderId.length - 1] < buttons.length - 1) {
                        helperAffix("right", buttons.slice(orderId[orderId.length - 1]));
                    }
                    // stretch one at each end
                    var totalW = helperAffix("centre", buttons.slice(1, -1)),
                        low = toStretchIdx[0] < toStretchIdx[1] ? 0 : 1,
                        high = low ? 0 : 1;
                    breakPointCSS += CSSprefix + bp.toStretch[low].selector + "{padding-right: " + (totalW / 2) + "px;}";
                    breakPointCSS += CSSprefix + bp.toStretch[high].selector + "{padding-left: " + ((totalW / 2) + margin) + "px;}";

                }
                helperStretch(bp.toStretch);
            }

            if (bp.toStretch.length === 3) {
                // stretch 3 - need to convert to N 
                if (buttons.length - bp.toStretch.length === 1) {
                    // 3 out of 4
                    var fixed;
                    for (var i = 0; i < buttons.length; i++) {
                        if (bp.toStretch.indexOf(buttons[i]) === -1) {
                            fixed = buttons[i];
                            break;
                        }
                    }
                    for (i = 0; i < bp.toStretch.length; i++) {
                        breakPointCSS += CSSprefix + bp.toStretch[i].selector + "{padding-right: " + fixed.width / bp.toStretch.length + "px;}";
                    }
                    var fixedIdx = $(buttons).index(fixed);
                    var $btns = $(buttons);
                    var cumulativeW = 0;
                    for (i = 0; i < bp.toStretch.length; i++) {
                        var thisIndex = $btns.index(bp.toStretch[i])
                        if (thisIndex > fixedIdx) {
                            breakPointCSS += CSSprefix + bp.toStretch[i].selector + "{margin-left:" + (fixed.width / bp.toStretch.length) * (buttons.length - thisIndex) + "px;margin-right:-" + (fixed.width / bp.toStretch.length) * (buttons.length - thisIndex) + "px;}";
                        } else {
                            breakPointCSS += CSSprefix + bp.toStretch[i].selector + "{margin-left:-" + (fixed.width / bp.toStretch.length) * thisIndex + "px;margin-right: " + (fixed.width / bp.toStretch.length) * thisIndex + "px;}";
                        }
                    }
                    breakPointCSS += CSSprefix + fixed.selector + "{position:absolute;left: " + (100 / bp.toStretch.length) * fixedIdx + "%;margin-left:" + (-(fixed.width / bp.toStretch.length) * fixedIdx) + "px;}";
                }

                helperStretch(bp.toStretch);
            }

            // stretching all the elements 
            if (bp.toStretch.length === buttons.length) {
                var i,
                    split = 100 / bp.toStretch.length;

                breakPointCSS += CSSprefix + ">." + prefix + "button{width:" + split + "%;}";
                if (idx >= snapTargetBP) {
                    var innerBtns = (snapTarget.$dom.children().length - 1);
                    breakPointCSS += CSSprefix + "." + prefix + "group ." + prefix + "button{width:" + 100 / innerBtns + "%;}";
                    breakPointCSS += CSSprefix + ">." + prefix + "group{width:" + split * innerBtns + "%;}";
                } else {
                    breakPointCSS += CSSprefix + "." + prefix + "group ." + prefix + "button{width:100%;}";
                    breakPointCSS += CSSprefix + ">." + prefix + "group{width:" + split + "%;}";
                }
            }
            return breakPointCSS;
        }

        // determine all possible breakpoints 
        function possibleBreakpoints(o) {
            var arr = [],
                i;

            for (i = 1; i < o.length; i += 1) {
                var stretch = (i + 1) * o[i].width,
                    j;
                for (j = i + 1; j < o.length; j += 1) {
                    stretch += o[j].width;
                }
                arr.push({
                    width: stretch,
                    toStretch: o.slice(0, i + 1)
                });
            }
            return arr;
        }

        t.breakpoints = [];

        // possible styling breakpoints before the snap 
        t.preSnapBreaks = possibleBreakpoints(t.sortedPreSnapButtons);

        t.breakpoints.push({
            width: t.minWidth,
            toStretch: [t.sortedPreSnapButtons[0]]
        });

        $row.css('min-width', t.minWidth);

        // get all the breaks at smaller widths than the snap into the breakpoints array
        var l;
        for (l = 0; l < t.preSnapBreaks.length; l++) {
            if (t.preSnapBreaks[l].width < t.totalWidth) {
                t.breakpoints.push(t.preSnapBreaks[l]);
            }
        }

        t.breakpoints.push({
            width: t.totalWidth,
            toStretch: [t.sortedPostSnapButtons[0]]
        });
        snapTargetBP = t.breakpoints.length - 1;

        // all the ones after the snap just go in
        t.breakpoints = t.breakpoints.concat(possibleBreakpoints(t.sortedPostSnapButtons));

        var m,
            styleBlock = '<style id="' + t.rbrId + '">';

        for (m = 0; m < t.breakpoints.length; m++) {
            styleBlock += generateCSS(t.breakpoints[m], m);
        }

        $('head').append(styleBlock + '</style>');

        function resizeFn() {
            var BP = t.breakpoints.length - 1,
                w = $row.outerWidth();

            while (w < t.breakpoints[BP].width) {
                BP--;
                if (BP === 0) {
                    $row.addClass('collapsed');
                    break;
                }
            }
            if (BP !== currentBP) {
                $row.removeClass('BP-' + currentBP);
                $row.addClass('BP-' + BP);
                if (BP < snapTargetBP && currentBP > BP) {
                    $row.addClass('collapsed');
                } else if (BP >= snapTargetBP && $row.hasClass('collapsed')) {
                    $row.removeClass('collapsed');
                }
                currentBP = BP;
            }
        }

        $(window).on('resize.' + t.rbrId, function () {
            tfl.utils.requestAnimFrame(resizeFn);
        }).trigger('resize.' + t.rbrId);

        $('.' + prefix + 'group-button', $row).on('click', function () {
            $row.toggleClass('opened');
        });

        $row.height($row.outerHeight());

        resizeFn();

        if (currentBP < snapTargetBP) {
            $row.addClass('collapsed');
        }

    }

    o.init = function () {
        $('.responsive-button-row').each(function () {
            new responsiveButtonRow($(this));
        });
    }

}(window.tfl.responsiveButtonRow = window.tfl.responsiveButtonRow || {}));