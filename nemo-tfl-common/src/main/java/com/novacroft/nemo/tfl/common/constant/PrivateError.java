package com.novacroft.nemo.tfl.common.constant;

/**
 * Error messages for internal use only, ie not for display on the UI.
 */
public enum PrivateError {
    USERNAME_NOT_FOUND("Username (or email) [%s] not found."),
    UNEXPECTED("Error: unhandled exception thrown."),
    CARD_NUMBER_EXIST("cardNumber.alreadyUsed"),
    CARD_DETAILS_NOT_AVAILABLE("Card details are not available for this webAccountId: [%s]."),
    MORE_THAN_ONE_RECORD_FOR_CARD_ID("Found more than one record for cardId [%s]."),
    MORE_THAN_ONE_RECORD_FOR_CUSTOMER("Found more than one preferences for customer [%s]."),
    MORE_THAN_ONE_CONTACT_FOR_CUSTOMER("Found more than one contact for type [%s] and customer [%s]."),
    INVALID_PAYMENT_GATEWAY_DECISION("Invalid payment gateway reply decision [%s]."),
    DATE_PARSE_ERROR("Error parsing date [%s]."),
    INVALID_GATEWAY_REPLY("Error: Invalid Gateway Reply"),
    UNABLE_TO_CREATE_PDF("Error: Unable to generate PDF"),
    UNABLE_TO_CREATE_LOCK_FILE("Error: Unable to create lock file."),
    UNABLE_TO_CREATE_JOURNEY_HISTORY("Error:Unable to create Journey History"),
    MORE_THAN_ONE_RECORD("Found more than one record. Query [%s]; Argument(s) [%s]."),
    FOUND_NO_RECORDS("No records found. Query [%s]; Argument(s) [%s]."),
    AUTO_LOAD_CHANGE_REQUEST_NOT_FOUND("Auto Load Change request not found. Request Sequence Number [%s]; Card Number [%s]."),
    PARSER_NOT_CONFIGURED("Parser not configured: [%s]"),
    PARSING_ERROR("Parsing error, line [%s], uri  [%s]."),
    INVALID_AUTO_AMOUNT("Invalid auto load amount [%s]"),
    INVALID_AUTO_STATE("Invalid auto load state [%s]"),
    AUTO_LOAD_CHANGE_REQUEST_FAILED("Auto Load Change request failed [%s - %s]"),
    CARD_UPDATE_REQUEST_FAILED("Card Update request failed [%s - %s]"),
    CARD_UPDATE_REQUEST_ERROR_DETECTED(
                    "At least one Card Update request failed PrePayValue Error:%s PrePayTicket Error:%s."),
    UNSUPPORTED_OPERATION("Error: Unsupported operation [%s]"),
    SET_TIMEOUT_ERROR_FOR_MESSAGE_SENDER("Error: do not know how to set timeout for message sender type [%s]"),
    INVALID_PAYMENT_GATEWAY_REPLY("Error: Invalid payment gateway reply: [%s]"),
    INVALID_TOP_UP_AMOUNT("Error: unrecognised top up: amount [%s]; card [%s]"),
    CYBER_SOURCE_HEARTBEAT_DEAD("Error: CyberSource heartbeat check failed [%s]"),
    UNSUPPORTED_EXPORT_FILE_TYPE("Error: export file type not supported [%s]"),
    REQUESTED_CHEQUE_MATCH_ERROR("Error: expected to find one requested cheque for order but found [%s]"),
    CHEQUE_DETAILS_MATCH_ERROR(
                    "Error: cheque details do not match.  Amount: expected [%s], actual [%s]; payee: expected [%s], actual [%s]."), 
    BACS_SETTLED_PAYMENT_MATCH_ERROR(
                    "Error: bacs payment settlement details do not match.  Amount: expected [%s], actual [%s]; payee: expected [%s], actual [%s]."),
    BACS_FAILED_PAYMENT_MATCH_ERROR("Error: bacs failed payment  details do not match.  Amount: expected [%s], actual [%s];"),
    REQUESTED_BACS_PAYMENT_MATCH_ERROR("Error: expected to find one requested Bacs Payment  but found [%s]"),
    FAILED_BACS_PAYMENT_REJECT_CODE_ERROR("Error: expected to find one of following as rejection code [%s], but found [%s]"),
    CHEQUE_SETTLEMENT_STATUS_ERROR("Error: invalid cheque settlement status: expected [%s]; found [%s]."),
    CYBER_SOURCE_PAYMENT_CARD_TOKEN_ERROR("Error: CyberSource payment card token failure: action [%s]; request [%s]; reply [%s]."),
	CARD_DETAILS_NOT_ASSIGNED_TO_USER("Card does not belong to the user, cardId:[%s]"),
	INVALID_SHIPPING_METHOD("Error: unrecognised shipping method: shipping type [%s];"), 
    CARD_REMOVE_UPDATE_FAILED("Error: Unable to remove update [%s - %s]"),
    CART_DETAILS_NOT_FOUND_FOR_CUSTOMER_AND_CART("Cart details not found, customerId:[%s], cartId:[%s]"),
    CART_DETAILS_NOT_FOUND_FOR_CART("Cart details not found, cartId:[%s]"),
    CART_NOT_DELETED_FOR_CUSTOMER_AND_CART("Cart not deleted due to an exception, customerId:[%s], cartId:[%s]"), 
    CHECKOUT_CREATE_ORDER_SETTLEMENT_NOT_COMPLETED("Checkout cart did not create order and settlement due to an unexpected error, cartId:[%s], stationId[%s]"),
    CART_CHECKOUT_AUTHORISATION_ERROR("Checkout payment authorisation not completed, cartId[%s], orderId[%s], paymentCardSettlementId[%s], paymentRef[%s]"),
    CART_CHECKOUT_COMPLETION_ERROR("Checkout completion failed, cartId[%s], stationId[%s], orderId[%s], paymentCardSettlementId[%s]"),
    CUSTOMER_NOT_FOUND("No record found for customer [%s]."),
    CUSTOMER_NOT_AUTHORIZED("Not authorized to update or delete customer with id [%s]."),
    INVALID_INPUT_PARAMETER("Invalid input parameter"),
    INVALID_SINGLE_INPUT_PARAMETER("Invalid input parameter [%s]"),
    UNABLE_TO_DETERMINE_ITEMDTO_SUBCLASS_WITH_TYPE_DETAIL("Unable to determine PayAsYouGo or TravelCard ItemDTO subclass from TicketType [%S]"),
    UNABLE_TO_DETERMINE_ITEMDTO_SUBCLASS("Unable to determine PayAsYouGo or TravelCard ItemDTO subclass"),
    STALE_DATA_ERROR("Stale Data Error - An attempt to persist data that has been updated by another request.");
    

    private PrivateError(String message) {
        this.message = message;
    }

    private String message;

    public String message() {
        return this.message;
    }

}
