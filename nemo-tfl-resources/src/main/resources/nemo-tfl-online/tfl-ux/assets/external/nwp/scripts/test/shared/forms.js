(function (o) {

    o.settings = function () {
        $.validator.setDefaults({
            onfocusout: false,
            onfocusin: false,
            onkeyup: false,
            onclick: false
        });
    };

    o.setSelectBoxSpan = function (selectbox) {
        var text = $(selectbox).children("option:selected").text();
        $(selectbox).attr('title', text).siblings("span").text(text);
    };
    o.updateSelectBox = function () {
        o.setSelectBoxSpan(this);
    },
        o.setupSelectBoxes = function () {
            $(".selector select").before("<span />").each(function () {
                o.setSelectBoxSpan(this);
                $(this).focus(function () {
                    $(this).parent().addClass("focus");
                });
                $(this).blur(function () {
                    $(this).parent().removeClass("focus");
                });
            }).change(o.updateSelectBox);
        };

    o.setupSelectBox = function (selector, text) {
        $(selector + " select").before("<span />");
        $(selector + " select").attr('title', text).siblings("span").text(text);
        $(selector).focus(function () {
            $(selector).parent().addClass("focus");
        });
        $(selector).blur(function () {
            $(selector).parent().removeClass("focus");
        });
        $(selector + " select").change(o.updateSelectBox);
    };

    o.modeSelectAll = function (el) {
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
    };

    o.setupCheckBoxSelectall = function (el) {
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
        o.modeSelectAll(link);
    };
    o.setupCheckboxLists = function () {
        tfl.logs.create("tfl.forms: setup checkbox lists");
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
                o.setupCheckBoxSelectall(list);
            }

            $(this).hide();
        });
    };
    o.setupHorizontalToggles = function () {
        tfl.logs.create("tfl.forms: setup horizontal toggles");
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
    };


    o.setupCustomInputs = function () {
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
    };

    o.toggleCustomInput = function (e) {
        var radiobuttonList = $(this).parents(".radiobutton-list");
        if (radiobuttonList.length > 0) {
            $(this).parent().parent().children("li,.radiobutton-list-item").removeClass("ticked");
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
    };

    o.toggleMoreInfo = function (e) {
        e.preventDefault();
        $(this).closest('.more-info-header').toggleClass('expanded');
    }


    o.init = function (selector) {
        o.settings();

        o.setupSelectBoxes();
        o.setupCheckboxLists();
        o.setupHorizontalToggles();
        o.setupCustomInputs();

        $(".checkbox-list input[type='checkbox'], .radiobutton-list input[type='radio']").click(o.toggleCustomInput);
        $(".styled-checkbox input[type='checkbox']").click(o.toggleCustomInput);
        $('.more-info-header .show-more-info').click(o.toggleMoreInfo);


    }


    o.init();

}(window.tfl.forms = window.tfl.forms || {}));