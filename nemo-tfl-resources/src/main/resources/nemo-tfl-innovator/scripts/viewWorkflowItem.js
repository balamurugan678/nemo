$(document).ready(function () {
	$("#rejectAccordian").accordion({
        collapsible: true,
        active: false,
        heightStyle: "content"
    });
	
	var rejectVal = $("#rejectReason").val(); 
	if(rejectVal == null) {
		$("#rejectSection").hide();
		$("#rejectBox").hide();
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
    
    $("#travelCardType").change(function(e) {
    	var value = $(this).val();
    	if(value.toLowerCase().indexOf('bus pass') >= 0) {
    		$("#zones").hide();
    	} else {
    		$("#zones").show();
    	}
    });
    
    resultsTable = $("#caseNoteTable").dataTable({
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
    
    initialiseEditAccordian();
});

function initialiseEditAccordian() {
	$("#itemAccordian").accordion({
        collapsible: false,
        active: false,
        heightStyle: "content"
    });
	
	initialiseSections();
    showOrHideGoodwillReasonTextAndOtherTextWhenSelectGoodwillReasonChanges();
}

function initialiseSections() {
	$("#addCardSection").hide();
	$("#addGoodwillSection").hide();
	showOrHideGoodwillOtherText($("#selectGoodwillReason option:selected").text());
	$("#addPayAsYouGoSection").hide();
	console.log(ticketType);
	if(ticketType.toLowerCase().indexOf("travelcard") != -1 || ticketType.toLowerCase().indexOf("bus pass") != -1) {
		$("itemAccordian").accordion( "option", "active", 0 ); 
		$("#addCardSection").show();
	} else if(ticketType.toLowerCase().indexOf("goodwill") != -1) {
		$("itemAccordian").accordion( "option", "active", 1 ); 
		$("#addGoodwillSection").show();
	} else {
		$("itemAccordian").accordion( "option", "active", 2 ); 
		$("#addPayAsYouGoSection").show();
	}
	
}

function showOrHideGoodwillReasonTextAndOtherTextWhenSelectGoodwillReasonChanges() {
	$("#selectGoodwillReason").change(function() {
    	showOrHideGoodwillOtherText($("#selectGoodwillReason option:selected").text());
    });
}

function showOrHideGoodwillOtherText(goodwillReasonSelectedText) {
	if (goodwillReasonSelectedText == 'Other') {
		$("#goodwillOtherText").show();
	} else {
		$("#goodwillOtherText").hide();
	}
}

function showGoodwillReasonText(extraValidationMessage) {
	$("#goodwillReasonMessageDiv").text(extraValidationMessage);
	$("#goodwillReasonMessageDiv").show();	
}
