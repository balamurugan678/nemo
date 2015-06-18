$(document).ready(function () {
	hidePaymentSections();
	showPaymentSection($("#paymentType option:selected").val());
	activateAdHocDropDownsIfErrors();
	
		$("#paymentType").change(function() {
		    if ($("#paymentType").val()) {
	    		showPaymentSection($("#paymentType option:selected").val());
	    	} else {
	    		hidePaymentSections();
	    	}
		});
		
	$("#selectCard").change(function() {
		$("#loading-icon").toggle();
		$("#messageArea").html("Loading");
		$.post(contextPath + '/' + pageName + '/getPreferredStation.htm', { cardNumber: $("#selectCard option:selected").val() }, function (response) {
	        $("#selectStation").val(response);
	        $("#loading-icon").toggle();
	        $("#messageArea").html("");
	    });
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