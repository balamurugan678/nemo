$(document).ready(function () {
    initialisegoodwillsection();
    showOrHideGoodwillReasonTextAndOtherTextWhenSelectGoodwillReasonChanges();
    activateAccordianIfErrors();
    setLineNoIfDeleteGoodwillClicked();
});

function initialisegoodwillsection() {
	$("#addGoodwillSection").hide();
	$("#goodwillAccordian").accordion({
		collapsible: true,
	    active: false,
	    heightStyle: "content"
	});
	showOrHideGoodwillOtherText($("#selectGoodwillReason option:selected").text());
}

function showOrHideGoodwillReasonTextAndOtherTextWhenSelectGoodwillReasonChanges() {
	$("#selectGoodwillReason").change(function() {
    	var extraValidationMessage = extraValidationMessages[$("#selectGoodwillReason").val()];
    	showGoodwillReasonText(extraValidationMessage);
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

function showGoodwillVerificationMessage(extraValidationMessage, pleaseSelectLabel) {
    if(extraValidationMessage  && $("#selectGoodwillReason option:selected").text() != pleaseSelectLabel) {
		$("#goodwillVerificationDialogText").text(extraValidationMessage);
		showGoodwillVerificationDialog();
	}
}

function showGoodwillVerificationDialog() {
	$("#goodwillVerificationDialog").dialog({
		resizable: false,
		modal: true,
		buttons: {
			OK: function() {
				$(this).dialog("close");
			},
			Cancel: function() {
				$(this).dialog("close");
			}
		}
	});
}

function activateAccordianIfErrors() {
	if ($("#addGoodwillSection span[id*='errors']").length) {
		$("#goodwillAccordian").accordion({ active: 0 });
	}
}

function setLineNoIfDeleteGoodwillClicked() {
	$(".deleteGoodwill").each(function(){
		$(this).click(function(){
			setLineNoFromDeleteGoodwillIdValue($(this).attr('id'));
			return true;
		});
	});
}

function setLineNoFromDeleteGoodwillIdValue(idValue) {
	idValue = idValue.replace("deleteGoodwill", "");
	idValue = idValue.replace("-submit", "");
	$("#lineNo").val(idValue);
}