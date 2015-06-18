//Fix IE7 bug which does not apply first-child CSS selector on elements adjacent to an html comment 
function removeComments() {
    // http://tanalin.com/en/blog/2011/08/ie7-css-first-child-adjacent/
    var elems = document.getElementsByTagName('*'),
        count = elems.length,
        comments = [];

    for (var i = 0; i < count; i++) {
        var elem = elems[i],
            childNodes = elem.childNodes,
            childCount = childNodes.length;

        for (var j = 0; j < childCount; j++) {
            var node = childNodes[j];

            if (8 === node.nodeType) {
                comments.push(node);
            }
        }
    }

    var commentsCount = comments.length;

    for (var k = 0; k < commentsCount; k++) {
        comments[k].removeNode();
    }

    // Forcing reflow (rerendering) to ensure styles are applied
    document.body.className = document.body.className;
}

removeComments();

//Fix to make a elements holding tables clickable
$(document).ready(function () {
    $('.csc-payment-row a table').click(function () {
        var href = ($(this).parent().attr('href'));
        if (href) window.location = href;
    });
})