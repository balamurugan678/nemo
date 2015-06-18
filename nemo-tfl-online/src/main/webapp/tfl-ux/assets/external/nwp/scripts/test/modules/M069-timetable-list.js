(function (o) {
    var $body;
    var showOnLoad = 5;

    function timetableList($list) {
        var $loadMore = $list.find('.load-more > a');
        var after = 0;
        if ($list.children().length > showOnLoad + 1) {
            $list.children().each(function () {
                after += 1;
                if (after > showOnLoad) {
                    $(this).addClass('compact-hidden');
                }
            });

            if (after < showOnLoad) {
                $list.children().eq(-(showOnLoad + 2)).nextAll().removeClass('compact-hidden');
            }

            if ($loadMore.length) {
                $loadMore.one('click', function (e) {
                    e.preventDefault();
                    $list.removeClass('compact');
                });
            }
        }
    }

    o.init = function (selector) {
        $(selector).each(function () {
            new timetableList($(this));
        });
    }

}(window.tfl.timetableList = window.tfl.timetableList || {}));