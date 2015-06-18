$(document).ready(function () {
	hidePaymentSections();
	showPaymentSection($("#paymentType option:selected").val());
	activateAdHocDropDownsIfErrors();
	
	$("#paymentType").on('change', function() {
    	if (this.value) {
    		showPaymentSection($("#paymentType option:selected").val());
    	} else {
    		hidePaymentSections();
    	}
    });
});

function hidePaymentSections() {
	$("#paymentTypeWebAccountCredit").hide();
	$("#paymentTypeAdhocLoad").hide();
	$("#paymentTypePaymentCard").hide();
	$("#paymentTypeBACS").hide();
	$("#paymentTypeCheque").hide();
	$("#bacsAndChequeAddress").hide();
}

function showPaymentSection(paymentType) {
	hidePaymentSections();
	if (paymentType != "") {
		$("#paymentType" + paymentType).toggle();
		if ((paymentType == "BACS" || paymentType == "Cheque") && $("#paymentType" + paymentType).is(":visible")) {
			$("#bacsAndChequeAddress").show();
		} else {
			$("#bacsAndChequeAddress").hide();
		}
	}
}

function activateAdHocDropDownsIfErrors() {
	if ($("#paymentTypeAdhocLoad span[id*='errors']").length) {
		$("#paymentTypeAdhocLoad").show();
	}
}