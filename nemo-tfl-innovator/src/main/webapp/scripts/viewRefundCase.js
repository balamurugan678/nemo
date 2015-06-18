$(document).ready(function () {
	$("#caseNoteTable").dataTable({
        "bFilter": false, "bProcessing": true, "sPaginationType": "full_numbers", "bDestroy": true, 
        "aoColumns": [
                      {"sWidth": "20%"}
                      ,{"sWidth": "40%"}
                      ,{"sWidth": "20%"}
                      ,{"sWidth": "20%"}
                      ],
    	"iDisplayLength": 50,
    	"aLengthMenu": [50, 100, 150, 200],
    	"aaSorting": [[ 0, 'desc' ]]
    }); 
});