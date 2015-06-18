var resultsTable = $("#orders").dataTable({
        "aoColumnDefs": [
             { "bSortable": true, "bSearchable": true, "bVisible": false, "aTargets": [ 0 ] },
             { "bSortable": false, "bSearchable": true, "bVisible": true, "aTargets": [ 1 ] }]
});

resultsTable.fnSort( [ [0,'desc'] ] );

jQuery(document).ready(function($) {
    $(".clickableItem").click(function() {
          window.document.location = $(this).attr("href");
    });
});