(function ($) {
    if (typeof tfl === "undefined") tfl = {};
    if (typeof tfl.csc === "undefined") tfl.csc = {};
    tfl.csc.isOldIE = ($("html").hasClass("lt-ie8"));
    tfl.csc.init2 = function (root) {
        if (root == null) root = $(":root");
        else root = $(root);
        //Immediately submit any page elements marked .csc-js-autosubmit
        root.find('.csc-js-autosubmit').submit();
        //Immediately show any page elements marked .csc-js-show-on-javascript
        root.find('.csc-js-show-on-javascript').show();
        //Immediately show any page elements marked .csc-js-show-on-javascript
        root.find('.csc-js-hide-on-javascript').hide();

        //Bind defined functions to presence of classes on the webpage 
        root.find(".csc-hide").each(tfl.csc.hide);
        root.find(".csc-show").each(tfl.csc.show);

        //Add maxlength attribute to any textarea elements with data-val-length-max defined 
        root.find("textarea[data-val-length-max]").each(tfl.csc.setmaxlengthfromdataattribute);

        //Provide iframe autosize support
        root.find(".csc-iframe-autosize").each(tfl.csc.iframeautosize);

        root.find('a.bill-hdr').each(tfl.csc.expandDay);

        root.find('form.JourneyForm').each(tfl.csc.journeyforminit);

        root.find('div.download-box a').each(tfl.csc.downloadstatement);

        root.find('.csc-toggle-by-selector').click(tfl.csc.toggleBySelector);

        tfl.csc.autoSubmit();
        tfl.csc.toggles();
        tfl.csc.intraday();

        if (tfl.csc.isOldIE) {
            tfl.csc.nthchild();
            $('.todays-travel-statement').addClass('jsHide');
        }
        ;
    };

    tfl.csc.hide = function () {
        var rootElementName = $(this).data("rootElement");
        var rootElement = $(rootElementName);
        var fade = $(this).data("fade");
        $(this).click(function () {
            if (fade) {
                $(rootElement).fadeOut();
            }
            else {
                $(rootElement).hide();
            }
            return false;
        });
    };

    // Shows an on-page element
    tfl.csc.show = function () {

        var rootElementName = $(this).data("rootElement");
        var rootElement = $(rootElementName);
        var fade = $(this).data("fade");
        $(this).click(function () {
            if (fade) {
                $(rootElement).fadeIn();
            }
            else {
                $(rootElement).show();
            }
            $(":input[type=text]:visible:first", rootElement).focus();
            return false;
        });
    };

    tfl.csc.setmaxlengthfromdataattribute = function () {

        var $this = $(this);
        var data = $this.data();
        $this.attr("maxlength", data.valLengthMax);
    };

    tfl.csc.iframeautosize = function () {

        if (!window.postMessage) return;
        var bodyMargin = $(this).data('bodyMargin');
        if (bodyMargin == null) bodyMargin = 8;

        $(this).iFrameSizer({
            log: false,
            contentWindowBodyMargin: bodyMargin,
            doHeight: true,
            doWidth: false,
            enablePublicMethods: true
        });
    };

    tfl.csc.toggles = function () {
        var $toggles = $(".csc-toggles");
        $toggles.find("a.j-toggle").on("click", function () {
            $(this).toggleClass("expanded");
            return false;
        });

        $toggles.each(function () {
            var $toggle = $(this);
            //Add the expand/collapse icon via JS, since it's JS only functionality
            var $toClick = $toggle.find(".csc-toggle-content-always-visible");
            if (!$toClick.hasClass("no-controls")) {
                $toClick.append('<div class="csc-toggle-controls"><span class="icon hide-text expand-collapse-icon vertically-centred">Show more</span></div>');
            }
            $toClick.on("click", function () {
                $toggle.toggleClass("expanded");
            });
        });
    };

    tfl.csc.expandDay = function () {

        $(this).click(function () {
            var $this = $(this);
            var $header = $this.parent().parent();

            if ($header.hasClass("processed")) {
                $header.toggleClass("expanded");
                $header.find("div.csc-payment-rows").toggle();

                if (!$this.hasClass("no-controls")) {
                    $this.append('<div class="csc-toggle-controls"><span class="icon hide-text expand-collapse-icon vertically-centred">Show more</span></div>');
                }

                return false;
            }
            var partialUrl = $this.parent().find("[name='PartialUrl']").val();

            $.ajax({
                type: "GET",
                contentType: "application/json",
                url: partialUrl,
                dataType: "text",
                success: function (data) {
                    var $row = $this.parent().parent().parent();

                    $row.html(data);
                    $row.find('a.bill-hdr').each(tfl.csc.expandDay);
                    var $toClick = $row.find(".csc-toggle-content-always-visible");
                    if (!$toClick.hasClass("no-controls")) {
                        $toClick.append('<div class="csc-toggle-controls"><span class="icon hide-text expand-collapse-icon vertically-centred">Show less</span></div>');
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                }
            });
            return false;
        });
    };

    tfl.csc.autoSubmit = function () {

        $('.auto').change(function () {
            $('#filters').submit();
        });
    };

    tfl.csc.nthchild = function () {
        $('.csc-payment-rows .csc-payment.row').removeClass('nthchild');
        $('.csc-payment-rows .csc-payment-row:nth-child(even)').addClass('nthchild');
    };

    tfl.csc.journeyforminit = function () {
        $(".startstation").change(function () {
            tfl.csc.setfinishstations();
        });
    };

    tfl.csc.setfinishstations = function () {
        var startStation = $(".startstation").val();
        var finishStation = $(".finishstation").val();
        if (finishStation == startStation) {
            finishStation = "";
        }
        var fullStationlist = $('.fullstationlist').clone().html();
        $('.finishstation').html(fullStationlist);
        $('.finishstation').find("option[value='" + startStation + "']").remove();
        $('.finishstation').val(finishStation);
        var finishStationText = finishStation == "" ? "Select" : finishStation;
        $('.finishstation').parent().find('span.selector-text').text(finishStationText);

    };

    tfl.csc.downloadstatement = function () {
        $(this).click(function () {
            var $icon = $(this).find(".icon");
            if ($icon.hasClass("document-icon")) {
                tfl.csc.setdownloadicon("pdf");
            }
            if ($icon.hasClass("spreadsheet-icon")) {
                tfl.csc.setdownloadicon("csv");
            }
        });
    };

    tfl.csc.setdownloadicon = function (ext) {
        var $icon;
        if (ext == "pdf") {
            $icon = $("#pdficon");
            if (!$icon.hasClass("progress-icon")) {
                $icon.removeClass("document-icon");
                $icon.addClass("progress-icon");
                setTimeout(function () {
                    tfl.csc.checkstatus(ext);
                }, 10000);
            } else {
                return false;
            }

        }
        if (ext == "csv") {
            $icon = $("#csvicon");
            if (!$icon.hasClass("progress-icon")) {
                $icon.removeClass("spreadsheet-icon");
                $icon.addClass("progress-icon");
                setTimeout(function () {
                    tfl.csc.checkstatus(ext);
                }, 10000);
            } else {
                return false;
            }

        }
        return false;
    };

    tfl.csc.resetdownloadicon = function (ext) {
        var $icon;
        if (ext == "pdf") {
            $icon = $("#pdficon");
            $icon.removeClass("progress-icon");
            $icon.addClass("document-icon");

        }
        if (ext == "csv") {
            $icon = $("#csvicon");
            $icon.removeClass("progress-icon");
            $icon.addClass("spreadsheet-icon");

        }
    };

    tfl.csc.checkstatus = function (ext) {
        $.ajax({
            type: "POST",
            url: "FileReady",
            data: { Ext: ext },
            success: function (complete) {
                if (!complete) {
                    setTimeout(function () {
                        tfl.csc.checkstatus(ext);
                    }, 4000);
                } else {
                    tfl.csc.resetdownloadicon(ext);
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                tfl.csc.resetdownloadicon(ext);
            }
        });
    };

    tfl.csc.toggleBySelector = function () {
        var selector = $(this).data('selector');
        $(selector).toggleClass("show");
    };

    tfl.csc.intraday = function () {

        var control = $("#intraday-card");
        var button = $("#intraday-show");
        if (control.length == 0 || button.length == 0) return;

        tfl.csc.intraday.querystringCheck();


        tfl.csc.getIntradayHashParams(control, button);
        tfl.csc.intradayShowStatement(control, button);
        control.change(function () {
            tfl.csc.intradayUpdateSelectedCard(control, button);
            tfl.csc.setIntradayHashParams(control, button);
        });
        button.click(function () {
            if ($(this).hasClass('display-as-button')) {
                tfl.csc.intraday.toggleStatementDisplay(button);
                tfl.csc.setIntradayHashParams(control, button);
            }
        });
    };

    tfl.csc.intraday.querystringCheck = function () {
        /*Non js mode using query parameters. When running in js mode, 
         convert to hash parameters*/
        var s = location.search;
        if (s && s.length > 1) {
            location.assign(location.pathname + '#' + location.search.slice(1));
        }
    };

    tfl.csc.intraday.toggleStatementDisplay = function (button) {
        button.find('.icon').toggleClass("plus-icon minus-icon");
        button.toggleClass("show-statement");
        $(".todays-travel").toggleClass("show-statement");

    };

    tfl.csc.intradayUpdateSelectedCard = function (control, button) {
        var selected = control.find("option:selected");
        var cardId = selected.val();
        var cardName = selected.text();
        var charge = selected.data('charge');
        $('.intraday-card-name').text(cardName);
        $('.intraday-total-charge').text(charge);
        control.data("currentCard", cardId);
        tfl.csc.intradayShowStatement(control, button);

    };

    tfl.csc.intradayShowStatement = function (control, button) {
        var selectedCardId = control.data("currentCard");
        $('.todays-travel-statement').removeClass('show');
        var intradayStatement = tfl.csc.getIntradayStatementElementForCard(selectedCardId);
        intradayStatement.addClass('show');

        var hasJourneys = (intradayStatement.find('ul').length > 0);
        $('.todays-travel .journey-info').addClass(hasJourneys ? 'show' : '').removeClass(hasJourneys ? '' : 'show');
        $('.todays-travel .no-journey-info').addClass(hasJourneys ? '' : 'show').removeClass(hasJourneys ? 'show' : '');
        if (hasJourneys) {
            button.addClass('display-as-button');
        } else {
            button.removeClass('display-as-button');
        }
    };

    tfl.csc.getIntradayStatementElementForCard = function (cardId) {
        return $('.todays-travel-statement').filter("[data-card='" + cardId + "']");
    };

    tfl.csc.getHashParams = function () {

        var hashParams = {};
        var e,
            a = /\+/g, // Regex for replacing addition symbol with a space
            r = /([^&;=]+)=?([^&;]*)/g,
            d = function (s) {
                return decodeURIComponent(s.replace(a, " "));
            },
            q = window.location.hash.substring(1);

        while (e = r.exec(q))
            hashParams[d(e[1])] = d(e[2]);

        return hashParams;
    };

    tfl.csc.getIntradayHashParams = function (control, button) {
        var qs = tfl.csc.getHashParams();
        var autoExpand = (button.data("autoExpand").toLowerCase() === 'true');
        var pi = qs["pi"];
        var foundCard = (pi && tfl.csc.getIntradayStatementElementForCard(pi).length > 0);
        if (foundCard) {
            control.data("currentCard", pi).val(pi).change();
            tfl.csc.intradayUpdateSelectedCard(control, button);
        }

        if (qs["s"] == 1 || !foundCard && autoExpand) {
            tfl.csc.intraday.toggleStatementDisplay(button);
        }
    };

    tfl.csc.setIntradayHashParams = function (control, button) {
        var qs = {};
        qs.s = button.hasClass("show-statement") ? 1 : 0;
        qs.pi = control.data("currentCard");
        location.hash = $.param(qs);
    };

    tfl.csc.init2();
})(jQuery);

