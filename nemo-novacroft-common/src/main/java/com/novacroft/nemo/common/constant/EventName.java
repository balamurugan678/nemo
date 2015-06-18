package com.novacroft.nemo.common.constant;

/**
 * Event Names
 */
public enum EventName {
    PASSWORD_EMAIL_SENT("PasswordEmailSent"),
    CALL("Call"),
    ISSUE_RAISED("IssueRaised"),
    UNSUCCESSFUL_CARD_QUERY("UnsuccessfulCardQuery"),
    SUCCESSFUL_CARD_QUERY("SuccessfulCardQuery"),
    PAYMENT_RESOLVED("PaymentResolved"),
    PAYMENT_CANCELLED("PaymentCancelled"),
    PAYMENT_INCOMPLETE("PaymentIncomplete"),
    CARD_PRINTED("CardPrinted"),
    LETTER_PRINTED("LetterPrinted"),
    PAYMENT_SUCCESS_EMAIL("PaymentSuccessEmail"),
    PAYMENT_FAILED_EMAIL("PaymentFailedEmail"),
    FIRST_ISSUE_CARD_DISPATCHED("FirstIssueCardDespatched"),
    FIRST_ISSUE_REQUESTED("FirstIssueRequested"),
    SUBSEQUENT_ISSUE_CARD_DISPATCHED("SubsequentIssueCardDespatched"),
    SUBSEQUENT_ISSUE_REQUESTED("SubsequentIssueRequested"),
    LETTER_DISPATCHED("LetterDespatched"),
    WHITEMAIL_RECEIVED("WhitemailReceived"),
    REFUND_REQUIRED("RefundRequired"),
    EMAIL_SEND("EmailSend"),
    WEB_ACCOUNT_CREATED("WebAccountCreated"),
    OYSTER_CARD_LOST("OysterCardLost"),
    OYSTER_CARD_STOLEN("OysterCardStolen"),
    OYSTER_CARD_HOTLISTED("OysterCardHotlisted"),
    AUTO_LOAD_CHANGE("AutoLoadChange"),
    OYSTER_CARD_TRANSFERRED("OysterCardTransferred"),
    AD_HOC_LOAD_REQUESTED("AdHocLoadRequested"),
    AD_HOC_LOAD_READY_FOR_PICK_UP("AdHocLoadReadyForPickUp"),
    AD_HOC_LOAD_PICKED_UP("AdHocLoadPickedUp"),
    AD_HOC_LOAD_PICK_UP_WINDOW_EXPIRED("AdHocLoadPickUpWindowExpired"),
    AD_HOC_LOAD_FAILED("AdHocLoadFailed");

    private EventName(String code) {
        this.code = code;
    }

    private String code;

    public String code() {
        return this.code;
    }
}

