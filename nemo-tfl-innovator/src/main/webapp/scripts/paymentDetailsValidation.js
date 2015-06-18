$(document).ready(function() {
	
	var AMERICAN_EXPRESS = "003";
	
	$('select#card_type').change(function() {
		if ($(this).find('option:selected').val() == AMERICAN_EXPRESS) {
			$('input[name="card_cvn"]').attr('maxlength', 4);
	    } else {
	    	$('input[name="card_cvn"]').attr('maxlength', 3);
	    }
	});
	
	$("#enterPaymentCardDetails").validate( {
		errorElement: "span",
	    errorClass: "field-validation-error",
	    rules: {
	        card_type: {
	            required: true
	        },
	        card_number: {
	            required: true,
	            minlength: 12,
	            maxlength: 19,
	            luhnCardNumber: true
	        },
	        card_expiry_date: {
	            required: true,
	            expirydate: true
	        },
	        card_cvn: {
	            required: true,
	            minlength: function() {
	            	if ($('select[name="card_type"]').find('option:selected').val() == AMERICAN_EXPRESS) {
	            		return 4;
	                } else {
	                	return 3;
	                }
	            },
	            maxlength: function() {
	            	if ($('select[name="card_type"]').find('option:selected').val() == AMERICAN_EXPRESS) {
	            		return 4;
	                } else {
	                	return 3;
	                }
	            },
	            digits: true
	        },
	        bill_to_forename: {
	            required: true
	        },
	        bill_to_surname: {
	            required: true
	        },
	        bill_to_email: {
	            email: true
	        }
	    },
	    messages: {
	        card_type: {
	            required: getMessage(contentCode.MANDATORY_FIELD_EMPTY_ERROR, [getMessage(contentCode.CARD_TYPE_LABEL)])
	        },
	        card_number: {
	            required: getMessage(contentCode.MANDATORY_FIELD_EMPTY_ERROR, [getMessage(contentCode.CARD_NUMBER_LABEL)]),
	            minlength: getMessage(contentCode.INVALID_PAYMENT_CARD_NUMBER_ERROR),
	            maxlength: getMessage(contentCode.INVALID_PAYMENT_CARD_NUMBER_ERROR),
	            luhnCardNumber: getMessage(contentCode.INVALID_PAYMENT_CARD_NUMBER_ERROR)
	        },
	        card_expiry_date: {
	            required: getMessage(contentCode.MANDATORY_FIELD_EMPTY_ERROR, [getMessage(contentCode.CARD_EXPIRY_DATE_LABEL)]),
	            expirydate: getMessage(contentCode.INVALID_PAYMENT_CARD_EXPIRY_DATE_ERROR)
	        },
	        card_cvn: {
	            required: getMessage(contentCode.MANDATORY_FIELD_EMPTY_ERROR, [getMessage(contentCode.CARD_VERIFICATION_NUMBER_LABEL)]),
	            minlength: getMessage(contentCode.INVALID_PAYMENT_CARD_VERIFICATION_NUMBER_ERROR),
	            maxlength: getMessage(contentCode.INVALID_PAYMENT_CARD_VERIFICATION_NUMBER_ERROR),
	            digits: getMessage(contentCode.INVALID_PAYMENT_CARD_VERIFICATION_NUMBER_ERROR)
	        },
	        bill_to_forename: {
	            required: getMessage(contentCode.MANDATORY_FIELD_EMPTY_ERROR, [getMessage(contentCode.BILL_TO_FIRSTNAME_LABEL)])
	        },
	        bill_to_surname: {
	            required: getMessage(contentCode.MANDATORY_FIELD_EMPTY_ERROR, [getMessage(contentCode.BILL_TO_LASTNAME_LABEL)])
	        },
	        bill_to_email: {
	            email: getMessage(contentCode.INVALID_EMAIL_ERROR)
	        }}
	});
});