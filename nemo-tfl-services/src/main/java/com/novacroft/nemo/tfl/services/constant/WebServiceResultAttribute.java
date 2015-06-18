package com.novacroft.nemo.tfl.services.constant;

import com.novacroft.nemo.tfl.common.constant.ContentCode;

public enum WebServiceResultAttribute {
    FAILURE(), 
	SUCCESS(), 
	CREATE_CARD_FAILURE(),
	XML_PARSING_ERROR(ContentCode.XML_PARSING.errorCode()), 
	UNEXPECTED_SERVER_ERROR(ContentCode.CUBIC_UNEXPECTED_SERVER.errorCode()), 
	AUTHORISATION_FAILURE(ContentCode.CUBIC_AUTHENTICATION.errorCode()), 
	CARD_NOT_FOUND(ContentCode.WEBSERVICE_CARD_NOT_FOUND.errorCode()), 
	CUSTOMER_NOT_FOUND(ContentCode.WEBSERVICE_CUSTOMER_NOT_FOUND.errorCode()), 
	CARD_ALREADY_ASSOCIATED_ERROR(ContentCode.CARD_ALREADY_ASSOCIATED_ERROR.errorCode()),
    INVALID_NULL_PARAMETER(ContentCode.WEBSERVICE_INVALID_PARAMETER.errorCode()),
    OPERATION_NOT_COMPLETED_DUE_TO_EXCEPTION(ContentCode.WEBSERVICE_OPERATION_NOT_COMPLETED_DUE_TO_EXCEPTION.errorCode()),
    RECORD_NOT_FOUND(ContentCode.WEBSERVICE_RECORD_NOT_FOUND.errorCode()),
    RECORD_LOOKUP_FAILED_DUE_TO_EXCEPTION(ContentCode.WEBSERVICE_RECORD_LOOKUP_FAILED_DUE_TO_EXCEPTION.errorCode()),
    CARD_ALREADY_ASSOCIATED_TO_ANOTHER_CUSTOMER_ERROR(ContentCode.CARD_ALREADY_ASSOCIATED_TO_ANOTHER_CUSTOMER.errorCode()),
    CUSTOMER_NOT_AUTHORIZED(ContentCode.CUSTOMER_NOT_AUTHORIZED.errorCode()),
    CART_DATA_HAS_EXPIRED(ContentCode.CART_DATA_HAS_EXPIRED.errorCode());

    private String contentCode;

    private WebServiceResultAttribute(String contentCode) {
        this.contentCode = contentCode;
    }
    
    private WebServiceResultAttribute() {
    }

    public String contentCode() {
        return this.contentCode;
    }

    public static WebServiceResultAttribute getResultFromName(String name) {
        return WebServiceResultAttribute.valueOf(name);
    }
}
