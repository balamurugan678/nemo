jQuery(document).ready(function($) {
    $(".clickableItem").click(function() {
          window.document.location = $(this).attr("href");
    });
});