/* HTML, CSS and JS Templates for Oyster Online 
 Build v0.6.2 on 16-05-2014@04:05  by Creative*/
(function ($) {

    oyster = window.oyster || {};
    oyster.ltie9 = {
        layoutFixes: {
            init: function () {

            }
        },
        init: function () {
            oyster.ltie9.layoutFixes.init();
        }
    };
    oyster.ltie8 = {
        layoutFixes: {

            init: function () {
                $('.toggle-calendar').on('click', function () {
                    var form = $(this).parents('form');

                    if (form.find('a.hide-text[class*="month"]').length > 0) {
                        form.find('.calendar-previous-month').text('<').removeClass('hide-text');
                        form.find('.calendar-next-month').text('>').removeClass('hide-text');
                    }
                });
                $('.oo-date-picker').val('dd/mm/yyyy');
                $('.inline-side-control').addClass('before');
            }
        },
        init: function () {
            oyster.ltie8.layoutFixes.init();
        }

    };
    if ($('html').hasClass('lt-ie8')) {
        oyster.ltie8.init();
    }
    if ($('html').hasClass('lt-ie9')) {
        oyster.ltie9.init();
    }
})(jQuery);

