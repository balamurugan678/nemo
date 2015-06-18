(function (o) {
    function addClickHandlers($list, multipleOpen) {
        $list.find('.parent > .link-wrapper a').on('click', function (e) {
            e.preventDefault();
            var $this = $(this),
                $parent = $this.closest('li'),
                $grandparent = $parent.parent().closest('li');

            if ($parent.hasClass('selected') && $parent.hasClass('parent')) {
                $parent.removeClass('selected child-selected');
                $grandparent.removeClass('child-selected');
                $parent.find('.selected, .child-selected').removeClass('selected child-selected');
            } else {
                if (!multipleOpen) {
                    $parent.siblings('.selected').removeClass('selected child-selected').find('.selected, .child-selected').removeClass('selected child-selected');
                }
                $parent.addClass('selected');
                $grandparent.addClass('child-selected');
            }
        });
    }

    function addClasses($list) {
        $list.find('li>ul,li>.content').parent().addClass('parent');
        var $selected = $list.find('.selected');
        $selected.parents('li.parent').addClass('selected child-selected');
        if (!$selected.find('>ul').length) {
            $selected.closest('.parent').addClass('no-grandchildren');
        }
    }

    list = function ($list, navigatable, multipleOpen) {
        addClasses($list);
        if (navigatable) {
            addClickHandlers($list, multipleOpen);
        }
    }
    o.init = function (selector, navigatable, multipleOpen) {
        var $parent = $(selector);

        if ($parent.length === 0) {
            return false;
        }

        new list($parent, navigatable, multipleOpen);
    }
    return o;
}(window.tfl.expandableList = window.tfl.expandableList || {}));