$(document).ready(function () {
	$("#rejectAccordian").accordion({
        collapsible: true,
        active: false,
        heightStyle: "content"
    });
	
	var rejectVal = $("#rejectReason").val(); 
	var rejectionHasError = $(".field-validation-error").length != 0;
	if(rejectVal == null) {
		if(rejectionHasError) {
			$("#rejectSection").show();
			$("#rejectBox").hide();
		} else {
			$("#rejectSection").hide();
			$("#rejectBox").hide();
		}
	} else {
		$("rejectAccordian").accordion( "option", "active", 0 ); 
		$("#rejectSection").show();
		
		if(rejectVal == "Other") {
	    	$("#rejectBox").show();
		} else {
			$("#rejectBox").hide();
		}
	}
	
    $("#rejectReason").change(function(e) {
    	if($(this).val() == "Other") {
    		$("#rejectBox").show();
    	} else {
    		$("#rejectBox").hide();
    	}
    });
    
    $("#caseNoteTable").dataTable({
        "bFilter": false, "bProcessing": true, "sPaginationType": "full_numbers", "bDestroy": true, "bAutoWidth": false,
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

