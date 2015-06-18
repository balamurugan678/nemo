(function (o) {
    "use strict";
    tfl.logs.create("tfl.listToDropdown: loaded");

    var throttle,
        $throttleTarget;
    o.init = function () {
        tfl.logs.create("tfl.listToDropdown: initialised");
        o.buildHtmlDropdown();
        o.dropDownMouseFunctionality();
        o.hideShowList();
        o.datePickerDropdownBuilding();
    };

    o.buildHtmlDropdown = function () {
        $(".links-list").each(function () {
            var linkList = $(this);
            linkList.addClass("hidden");
            var dropDownTarget = linkList.data('dropdown-target');
            var dropDownLabel = linkList.data('dropdown-label');
            var selectedItem = linkList.data('selected-item-id');
            var dropdownStyling = linkList.data('dropdown-option');
            //in the special case dropdown is datpicker, add a class
            var datesClass = "";
            if (dropDownTarget == "date-dropdown-placeholder") {
                datesClass = "datepicker-dropdown";
                linkList.addClass(datesClass);
            }
            linkList.find('li[data-item-id="' + selectedItem + '"]').hide();
            linkList.wrap('<div class="for-dropdown" />');
            //special case for GLA
            if (linkList.attr("id") == "gla-list") {
                linkList.parent().prepend('<div role="button" tabindex="0"  class="dropdown-button ' + dropdownStyling + ' dropdown-closed ' + datesClass + '"><img src="/static/cms/images/MoL.png" alt="Mayor of London"><span class="mol-gla">GLA</span></div>');
            }
            else {
                linkList.parent().prepend('<div tabindex="0" role="button" ' + 'aria-label="' + dropDownLabel + '" ' + 'class="dropdown-button ' + dropdownStyling + ' dropdown-closed ' + datesClass + '" >' + selectedItem + '</div>');
            }
            $("." + dropDownTarget).append(linkList.parent());
            //special case for !GLA
            if (!linkList.attr("id") == "gla-list" || !linkList.attr("id")) {
                $(linkList).find('li:visible:first').css('border-top', 'solid 1px #cacaca');
            }
            linkList.siblings(".dropdown-button").click(function () {
                o.hideShowList(linkList.siblings(".dropdown-button"));
            })
                .keypress(function (e) {
                    if (e.which == 13) {
                        $(this).click();
                    }
                });

            //return false;d
        });
    };

    o.hideShowList = function (el) {
        if ($(el).hasClass('dropdown-closed')) {
            clearTimeout(throttle);
            throttle = 0;
            $throttleTarget = undefined;
            $('ul.links-list:not(".hidden")').addClass("hidden").prev('.dropdown-button').addClass('dropdown-closed');
        }
        $(el).next("ul.links-list").toggleClass("hidden");
        $('ul.links-list li:visible:first').css("border-top-width", "1px");
        $(el).toggleClass("dropdown-closed");
        clearTimeout(throttle);
        throttle = 0;
        $throttleTarget = undefined;
    };

    o.dropDownMouseFunctionality = function () { // mouse entering/leaving drop-down hiding
        //hide drop-down if move out of drop-down and drop-down-button
        $(".for-dropdown").on("mouseleave", function () {
            var $this = $(this);
            $throttleTarget = $this;
            throttle = setTimeout(function () {
                if (!$this.children(".dropdown-button").hasClass("dropdown-closed")) {
                    o.hideShowList($this.children(".dropdown-button"));
                }
            }, 500);
        }).on("mouseenter", function () {
            if (throttle && $(this).is($throttleTarget)) {
                clearTimeout(throttle);
                throttle = 0;
                $throttleTarget = undefined;
            }
        });

    };

    o.datePickerDropdownBuilding = function () {
        //find parent of this element and takes the li hover off
        $(".advance-month-container").parent().addClass("no-hover");
    };
    o.init();
})(window.tfl.listToDropdown = window.tfl.listToDropdown || {});
