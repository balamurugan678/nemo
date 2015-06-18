(function (o) {
    o.allTables = [];
    o.tableCellPadding = 5;
    o.tableShadowWidth = 15;

    o.ResponsiveTable = function (el) {
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
                var t = this;
                t.onResize();
                parent.scroll(t.onParentScroll);
                if ($(parent).closest('.expandable-box').length) {
                    $(parent).closest('.expandable-box').find('.always-visible').on('click', function () {
                        t.onResize();
                    });
                }
            },
            onResize: function () {

                var wasVisible = this.isVisible;
                if ($(parent).is(':visible')) {
                    this.isVisible = true;
                } else {
                    this.isVisible = false;
                }
                if (!wasVisible && this.isVisible) {
                    this.stopHiding();
                    this.startHiding();
                }

                var offset = $(parent).offset();
                left = offset.left;
                top = offset.top;

                width = $(table).width();
                height = $(table).height();

                parentWidth = $(parent).width();
                if (firstRow) {
                    firstRowHeight = firstRow.height() ? firstRow.height() : firstRowHeight;
                    var toffset = $(table).offset();
                    firstRowTop = toffset.top;
                    firstRowLeft = toffset.left;
                    if (hiding) {
                        firstRow.parent().css({ width: parentWidth, left: left });
                        this.onWindowScroll($(window).scrollTop());
                    }
                }
                if (firstCol) {
                    firstColWidth = firstCol.width() - 2 * o.tableCellPadding;
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
                var offset = $(parent).offset();
                left = offset.left;
                top = offset.top;
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
                leftShadow.css({ left: firstColWidth + 2 * o.tableCellPadding - o.tableShadowWidth + Math.min(pScrollLeft, o.tableShadowWidth) });
                var r = Math.min(scrollMax - pScrollLeft, o.tableShadowWidth) - o.tableShadowWidth;
                //Hack to fix a bug on Chrome for Android. Without this if statement, the right shadow
                //starts off visible, but never comes back if you scroll it out of view.
                if (r === -1 * o.tableShadowWidth) {
                    r++;
                }
                rightShadow.css({ right: r });
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
                width = $(table).width();

                firstRow = $("<table class='first-row'></table>");
                var fr = $(table).find("tr:first-child");
                firstRowHeight = fr.height();
                firstRow.append(fr.clone().css({ height: firstRowHeight, width: width}));

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

                height = $(table).height();

                firstRowTop = $(firstRow).parent().offset().top;
                firstRowLeft = $(table).offset().left;
                leftShadow = $("<div class='responsive-table-shadow left-shadow' />");
                leftShadow.css({ left: firstColWidth + 2 * o.tableCellPadding - o.tableShadowWidth, height: height });
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
    };

    o.init = function (selector) {
        //only tables in a table-container div will be made responsive
        $(selector).each(function (i) {
            var t = new o.ResponsiveTable(this);
            t.init();
            o.allTables.push(t);
        });
        if (o.allTables.length > 0) {
            $(window).resize(function () {
                for (var i = 0; i < o.allTables.length; i++) {
                    o.allTables[i].onResize();
                }
            });
            $(window).scroll(function () {
                var scrollTop = $(this).scrollTop();
                for (var i = 0; i < o.allTables.length; i++) {
                    o.allTables[i].onWindowScroll(scrollTop);
                }
            });
        }
    }

    o.init(".table-container table");

}(window.tfl.responsiveTables = window.tfl.responsiveTables || {}));