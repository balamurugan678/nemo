(function (o) {
    o.newItemsToShow = 10;
    o.numInitiallyShown = 3;

    var moreDeparturesHTML = "<a href='#' class='live-board-link'><span class='down-icon-blue'></span><span class='visually-hidden'>Load </span>More departures</a>";

    function setupBoard() {
        var currentlyShowing = o.numInitiallyShown;

        var $board = $(this);
        var $boardItems = $board.find(".live-board-feed-item");
        var numBoardItems = $boardItems.length;
        $board.after(moreDeparturesHTML);
        var $moreDeparturesLink = $board.next(".live-board-link");

        $moreDeparturesLink.click(function (e) {
            e.preventDefault();
            currentlyShowing = showMoreItems($boardItems, numBoardItems, currentlyShowing);
            if (currentlyShowing === numBoardItems) {
                $(this).addClass("hidden");
            }
        });

        for (var i = currentlyShowing; i < numBoardItems; i++) {
            $boardItems.eq(i).addClass("hidden");
        }
    }

    function showMoreItems($boardItems, numBoardItems, currentlyShowing) {
        var newItemsShown = Math.min(currentlyShowing + o.newItemsToShow, numBoardItems);
        if (currentlyShowing < newItemsShown) {
            for (var i = currentlyShowing; i < newItemsShown; i++) {
                $boardItems.eq(i).removeClass("hidden");
            }
        }
        return newItemsShown;
    }

    function init() {
        $(".live-board-feed").each(setupBoard);
    };

    init();

}(window.tfl.liveBoards = window.tfl.liveBoards || {}));