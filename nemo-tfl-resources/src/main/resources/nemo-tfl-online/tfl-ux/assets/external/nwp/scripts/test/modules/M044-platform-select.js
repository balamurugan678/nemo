(function (o) {
    var $body;

    platformSelect = function ($parent, targetLists) {
        var t = this,
            $routesList = $('ul.routes-list', $parent),
            $selected = $routesList.find('.selected'),
            $button = $parent.find('.current-platform'),
            closing = 0;


        function appendSelected() {
            $button.empty().append($selected.clone());
        }

        function filterTableByIds(prop, value) {
            $(targetLists).find('.live-board-feed-item').each(function () {
                if ($(this).data(prop) !== value) {
                    $(this).addClass('hidden');
                } else {
                    $(this).removeClass('hidden');
                }
            });
            $(targetLists).each(function () {
                var $this = $(this);
                if ($this.find('.live-board-feed-item:not(".hidden")').length) {
                    $this.removeClass('hidden');
                } else {
                    $this.addClass('hidden');
                }
            });
        }

        function filterTableBySelected() {
            if ($selected.data('destination-id') !== undefined) {
                // specific destination
                filterTableByIds('destination-id', $selected.data('destination-id'));
            } else if ($selected.data('line-name') !== undefined) {
                filterTableByIds('line-name', $selected.data('line-name'));
            } else {
                $(targetLists).removeClass('hidden').find('.live-board-feed-item.hidden').removeClass('hidden');
            }
        }


        if (!$selected.length) {
            $selected = $routesList.find('>.line:first-child>.route:first-child,.line-group:first-child');
        }
        appendSelected();

        $button.on('click', function (e) {
            e.preventDefault();
            e.stopPropagation();
            $parent.toggleClass('expanded');
        });

        $parent
            .on('mouseleave', function () {
                if ($parent.hasClass('expanded')) {
                    closing = setTimeout(function () {
                        closing = 0;
                        $parent.removeClass('expanded');
                    }, 500);
                }
            })
            .on('mouseenter', function () {
                if (closing) {
                    clearTimeout(closing);
                    closing = 0;
                }
            });

        $routesList.find('.route > a').on('click', function (e) {
            e.preventDefault();
            clearTimeout(closing);
            closing = 0;
            $parent.removeClass('expanded');
            $selected.removeClass('selected');
            $selected = $(this).closest('.route').addClass('selected');
            appendSelected();

            filterTableBySelected();

        });


    };

    o.init = function (parent, targetLists) {
        var $parent = $(parent);

        $body = $('body');

        if ($parent.length === 0 || !$parent.hasClass('platform-select') && $targetLists.length === 0) {
            return false;
        }

        new platformSelect($parent, targetLists);
    };


}(window.tfl.platformSelect = window.tfl.platformSelect || {}));