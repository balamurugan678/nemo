(function (o) {
    tfl.logs.create("tfl.timetableInteractions: loaded");

    o.initTimeSelect = function (form, list, ajaxUrl) {
        var $form = $(form),
            $list = $(list),
            $parent = $list.parent(),
            formValues = { },
            $inputs = $form.find('select, input:not([type=submit])'),
            ajaxCache = { },
            $selectDate,
            classList = "";

        $inputs.each(function () {
            var $this = $(this);
            formValues[$this.attr('name')] = $this.val();
        });

        formValues['lineId'] = $form.data("lineid");
        formValues['mode'] = $form.data("mode");
        formValues['direction'] = $form.data("direction");

        var calculateCacheId = function () {
            return formValues.SelectedDate + "" + formValues.SelectedTime;
        };

        function displayTimetableList(markup) {
            $list.before(markup).remove();
            $list = $(list);
            if ($list.hasClass('compact')) {
                $list.removeClass('compact');
            }
        }

        function reloadTimetableList(callback) {
            var cacheId = calculateCacheId();

            var ajaxSuccess = function (response) {
                ajaxCache[cacheId] = response;
                displayTimetableList(ajaxCache[cacheId]);
                if (callback) callback();

            };

            var ajaxComplete = function () {
                $parent.removeClass('loading');
            };

            if (ajaxCache[cacheId]) {
                displayTimetableList(ajaxCache[cacheId]);
            } else {
                $parent.addClass('loading');
                tfl.ajax({
                    url: ajaxUrl,
                    data: formValues,
                    success: ajaxSuccess,
                    complete: ajaxComplete,
                    dataType: 'html'
                });
            }
        }

        $selectDate = $inputs.filter('#SelectedDate');

        $selectDate.on('change', function () {
            var $this = $(this),
                attr = $this.attr('name'),
                val = $this.val();
            formValues[attr] = val;
            tfl.tools.setQueryStringParameter(attr, val);
            reloadTimetableList();
        });

        $selectDate.children().each(function () {
            classList += " " + $(this).attr('value');
        });

        var $fromToForm = $('.timetable-options .select-from-to');
        $fromToForm.find('a.edit').on('click', function (e) {
            e.preventDefault();
            $fromToForm.find('.from-field, .to-field, .to-value, .from-value, .edit').toggleClass('hidden');
        });
    };

    o.initDropDownSelector = function () {
        var $fromSelector = $('#FromId');
        var $toSelectorWrapper = $('#to-selector');
        var $selector = $toSelectorWrapper.children('.selector');

        // if arrive at from / to selector with from already populated (e.g. from SSP platform page)
        if ($('#FromId').val().length != 0) {
            $selector.removeClass("disabled");
        }
        else {
            $selector.addClass("disabled");
            $selector.children("select").prop("disabled", "true");
        }

        var ajaxSuccess = function (response) {
            var fromActiveCheck = $("#FromId").prop("selectedIndex");
            if (fromActiveCheck == 0) {
                $selector.children("select").prop("disabled", "true");
            }
            else {
                $toSelectorWrapper.empty();
                $toSelectorWrapper.append(response);
                tfl.forms.setupSelectBox('#to-selector .selector', 'To');
            }
        };

        var ajaxError = function () {
            $toSelectorWrapper.append('<div class="r"><span class="field-validation-error">' + tfl.apiErrorMessage + '</span></div>');
        };

        $fromSelector.on('change', function () {
            var $toSelectorWrapper = $('#to-selector');
            var $selector = $toSelectorWrapper.children(".selector");
            var $this = $(this),
                fromId = $this.val();

            if (fromId != null) {
                var fromActiveCheck = $("#FromId").prop("selectedIndex");
                if (fromActiveCheck != 0) {
                    //un-grey 'to' box
                    $selector.children("select").prop("disabled", "false");
                    $selector.removeClass("disabled");
                    // add throbber
                    $selector.children("span").addClass('downloading');
                }
                else {
                    $selector.addClass("disabled");
                    $selector.children("select").prop("disabled", "true");
                    $selector.children("span").removeClass('downloading');
                }

                $selector.children("select").empty();
                var ajaxData = {
                    lineId: $fromSelector.parents("form").data("lineid"),
                    fromId: fromId,
                    fromNotTo: false
                };

                tfl.ajax({
                    url: '/Timetables/StopsDropDownSelector',
                    data: ajaxData,
                    success: ajaxSuccess,
                    error: ajaxError,
                    dataType: 'text'
                });
            }
            ;
        });
    };

}(window.tfl.timetableInteractions = window.tfl.timetableInteractions || {}));