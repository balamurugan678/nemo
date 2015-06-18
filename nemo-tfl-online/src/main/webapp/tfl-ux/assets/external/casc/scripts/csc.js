(function ($) {
    tfl = {};
    tfl.csc = {
        init: function () {
            tfl.csc.initToggles();
            tfl.csc.initFormTooltips();
            tfl.csc.initAppendAround();
            tfl.csc.customInputs.init();
            tfl.csc.addresses.setupAddresses();
            tfl.csc.popups.init();
            tfl.csc.tabs.init();
            tfl.csc.cookieNoticeInit();
            var breadcrumbs = $("ul.breadcrumbs");
            if (breadcrumbs) {
                var b = new tfl.csc.ResponsiveBreadcrumbs(breadcrumbs);
                b.init();
            }


        },
        initToggles: function () {
            var $toggles = $(".csc-toggle-content");
            $toggles.each(function () {
                var $toggle = $(this);
                //Add the expand/collapse icon via JS, since it's JS only functionality
                var $toClick = $toggle.find(".csc-toggle-content-always-visible");
                if (!$toClick.hasClass("no-controls")) {
                    $toClick.append('<div class="csc-toggle-controls"><span class="icon hide-text expand-collapse-icon vertically-centred">Expand/collapse</span></div>');
                }
                $toClick.click(function () {
                    $toggle.toggleClass("expanded");
                });
            });
        },
        initAppendAround: function () {
            $(".moving-source-order").appendAround();
        },
        initFormTooltips: function () {
            $(".form-tooltip-icon").each(function () {
                var $this = $(this);
                var $tooltip = $this.siblings(".form-tooltip");
                var hoverOrFocus = function () {
                    $tooltip.addClass("hover");
                };
                var hoverOffOrBlur = function () {
                    $tooltip.removeClass("hover");
                };
                $this.focus(hoverOrFocus).blur(hoverOffOrBlur).hover(hoverOrFocus, hoverOffOrBlur);
            });
        }
    };

    tfl.csc.customInputs = {
        init: function () {
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
            $(".checkbox-list input[type='checkbox'], .radiobutton-list input[type='radio'], .styled-checkbox input[type='checkbox']").click(tfl.csc.customInputs.toggleCustomInput);
            tfl.csc.customInputs.setupSelectBoxes();
            tfl.csc.customInputs.setupCheckboxLists();
        },
        toggleCustomInput: function () {
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
            $(".selector select").before("<span class='selector-text' />").after("<span class='icon down-arrow' />").each(function () {
                tfl.csc.customInputs.setSelectBoxSpan(this);
                $(this).focus(function () {
                    $(this).parent().addClass("focus");
                });
                $(this).blur(function () {
                    $(this).parent().removeClass("focus");
                });
            }).change(tfl.csc.customInputs.updateSelectBox);
        },
        setSelectBoxSpan: function (selectbox) {
            var $selectbox = $(selectbox);
            var $selected = $selectbox.children("option:selected");
            $selectbox.siblings(".selector-text").text($selected.text());
            var country = $selected.attr("data-country");
            if (country) {
                $selectbox.closest(".selector").attr("data-country", country);
            } else {
                $selectbox.closest(".selector").removeAttr("data-country");
            }
        },
        updateSelectBox: function (e) {
            tfl.csc.customInputs.setSelectBoxSpan(this);
        },
        setupCheckboxLists: function () {
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
                    /*tfl.interactions.setupCheckBoxSelectall(list);*/
                }

                $(this).hide();
            });
            $(".checkbox-list input[type='checkbox']").click(function () {
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
            });
        }
    };

    tfl.csc.tabs = {
        init: function () {
            $(".csc-tabs").each(function () {
                var $tabContainer = $(this);
                var $tabHeadings = $tabContainer.find(".csc-tab-heading");
                var $tabContent = $tabContainer.find(".csc-tab-content");
                $linkHolder = $("<div class='csc-tab-link-holder clearfix'></div>")
                $tabContainer.prepend($linkHolder.append($tabHeadings));
                $tabContent.addClass("hidden");
                $tabContent.eq(0).removeClass("hidden");
                $tabHeadings.eq(0).addClass("selected");
                $tabHeadings.click(function () {
                    $tabHeadings.removeClass("selected");
                    var $selectedHeading = $(this);
                    $selectedHeading.addClass("selected");
                    var idx = $.inArray(this, $tabHeadings);
                    console.log(idx);
                    $tabContent.addClass("hidden");
                    $tabContent.eq(idx).removeClass("hidden");
                });
            });
        }
    };

    tfl.csc.addresses = {
        setupAddresses: function () {
            $("#postcode-not-known").click(function (e) {
                e.preventDefault();
                tfl.addresses.showUKAddressLabels();
            })
        },
        showUKAddressLabels: function () {
            $(".waiting-for-postcode").removeClass("waiting-for-postcode");
            $("#postcode-buttons").addClass("not-waiting-for-postcode");
        }
    };

    tfl.csc.popups = {
        init: function () {
            $("[data-popup]").click(tfl.csc.popups.loadPopup)
        },
        loadPopup: function (e) {
            e.preventDefault();
            e.stopPropagation();
            var obj = $(this);
            var $target = $(obj.attr("data-popup"));
            if ($target.length === 0) {
                return;
            }
            var toRender = $('<div></div>').addClass('alert-box');
            toRender.append($target.html());
            var closeLink = $("<a href='javascript:void(0)' class='close-alert-box'>close</a>");
            toRender.prepend(closeLink);
            console.log($("#full-width-content").length);
            $("#full-width-content").after(toRender);
            closeLink.focus();
            $("head").append('<style>.popup-content-active:after { height: ' + Math.max($(document.body).height(), toRender.height() + 70) + 'px}</stlye>');

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
                // console.log("Clicked close link");
                toRender.remove();
                $(document.body).removeClass("popup-content-active");
                obj.focus();
            });
            $(document.body).addClass("popup-content-active");
        }
    };

    tfl.csc.ResponsiveBreadcrumbs = function (el) {
        var breadcrumbs = el;
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
        var resizeFunc = null;
        return {
            init: function () {
                //parent.scroll(this.onParentScroll);
                ellipsis = $("<li class='ellipsis hidden'><a href='javascript:void(0)'>...</a></li>");
                $(items[0]).after(ellipsis);
                ellipsisWidth = ellipsis.width();
                ellipsis.click(this.makeScrollable);
                items.each(function () {
                    var w = $(this).width();
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
                /*if (tfl.interactions.isTouchDevice()) {
                 parent.addClass("scrolling");
                 breadcrumbs.css({ width: totalWidth + "px" });
                 }*/
                ellipsis.addClass("hidden");
                items.each(function () {
                    $(this).show();
                });
                $(window).off("resize", resizeFunc);
                return false;
            }
        };
    };

    tfl.csc.cookieNoticeInit = function () {
        var cookieNotice = $('.cookie-policy-notice');
        if (cookieNotice.length == 0 || !$.localStorage) return;
        try {
            $.localStorage.settings.cookieOptions.expires = 365;
            if ($.localStorage && !$.localStorage.getItem('cookiePolicyNoticeHidden')) {
                cookieNotice.show();
                cookieNotice.find('a').click(function () {
                    cookieNotice.hide();
                    $.localStorage.setItem('cookiePolicyNoticeHidden', 'true');
                });
            }
        }
        catch (e) {
        }

    };


    // AppendAround Nov 21 2013
    $.fn.appendAround = function () {
        return this.each(function () {

            var $self = $(this),
                att = "data-set",
                $parent = $self.parent(),
                parent = $parent[0],
                attval = $parent.attr(att),
                $set = $("[" + att + "='" + attval + "']");

            function isHidden(elem) {
                return $(elem).css("display") === "none";
            }

            function appendToVisibleContainer() {
                if (isHidden(parent)) {
                    var found = 0;
                    $set.each(function () {
                        if (!isHidden(this) && !found) {
                            $self.appendTo(this);
                            found++;
                            parent = this;
                        }
                    });
                }
            }

            appendToVisibleContainer();

            $(window).bind("resize", appendToVisibleContainer);

        });
    };

    tfl.csc.init();
})(jQuery);