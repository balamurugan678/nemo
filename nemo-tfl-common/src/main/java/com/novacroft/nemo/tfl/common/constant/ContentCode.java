package com.novacroft.nemo.tfl.common.constant;

/**
 * Content (message) code constants.
 */
public enum ContentCode {
    CANCEL("cancel"),
    CONTINUE("continue"),
    ADD_EXISTING_CARD_TO_ACCOUNT_UPDATE_SUCCESSFUL("addExistingCardToAccount.update.successful"),
    CHANGE_PASSWORD_UPDATE_SUCCESSFUL("changePassword.update.successful"),
    CHANGE_SECURITY_QUESTION_UPDATE_SUCCESSFUL("changesecurityquestion.update.successful"),
    CHANGE_PERSONAL_DETAILS_UPDATE_SUCCESSFUL("changePersonalDetails.update.successful"),
    CHANGE_CARD_PREFERENCES_UPDATE_SUCCESSFUL("changeCardPreferences.update.successful"),
    NO_CHANGE_IN_AMOUNT_SET_FOR_AUTOTOPUP("updateAutotopUp.nochange.topupamount"),
    UPDATE_SUCCESSFUL("update.successful"),
    ODD_PERIOD_OTHER_TRAVEL_CARD_RESET_ANNUAL("oddPeriodOtherTravelCard.reset.annual"),
    STATUS_MESSAGE("statusMessage"),
    ALREADY_ADDED("alreadyAdded"),
    ALREADY_USED("alreadyUsed"),
    TRAVEL_CARD_OVERLAP("travelCardOverlap"),
    EXCEEDS_MAXIMUM_TRAVEL_CARDS("exceedsMaximumTravelCards"),
    EXCEEDS_MAXIMUM_TRAVEL_CARDS_INCLUDING_PENDING_OR_EXISTING("exceedsMaximumTravelCardsIncludingPendingOrExisting"),
    EXCEEDS_MAXIMUM_PENDING_ITEMS("exceedsMaximumPendingItems"),
    GREATER_THAN_START_DATE("greaterThanStartDate"),
    GREATER_THAN_EQUAL_CURRENT_DATE("greaterThanOrEqualCurrentDate"),
    INVALID("invalid"),
    INVALID_AMOUNT("invalid.amount"),
    INVALID_DATE_PATTERN("invalid.date.pattern"),
    INVALID_WEB_SERVICE_DATE_PATTERN("invalid.webservice.date.pattern"),
    INVALID_SINGLE_ZONE_SELECTION("invalid.singleZoneSelection"),
    ZONE_MAPPING_NOT_EXIST("zoneMappingNotExist"),
    DATE_DIFF_MORE_THAN_56("datediffmorethan56"),
    START_DATE_OLDER_THAN_56("startdateolderthan56"),
    TERMS_NOT_ACCEPTED("termsNotAccepted"),
    GREATER_THAN_ANNUAL_PERIOD("greaterThanAnnualPeriod"),
    LESS_THAN_OTHER_TRAVEL_CARD_MINIMUM_ALLOWED_MONTHS("lessThanOtherTravelCardMinimumAllowedMonths"),
    FIRSTNAME_CHARACTERS("firstName.characters"),
    LASTNAME_CHARACTERS("lastName.characters"),
    USERNAME_CHARACTERS("userName.characters"),
    CARD_TYPE("card_type"),
    CARD_NUMBER("card_number"),
    CARD_EXPIRY_DATE("card_expiry_date"),
    CARD_VERIFICATION_NUMBER("card_cvn"),
    BILL_TO_FIRSTNAME("bill_to_firstName"),
    BILL_TO_LASTNAME("bill_to_lastName"),
    BILL_TO_EMAIL("bill_to_email"),
    BILL_TO_ADDRESS_LINE1("bill_to_address_line1"),
    BILL_TO_ADDRESS_STATE("bill_to_address_state"),
    BILL_TO_ADDRESS_CITY("bill_to_address_city"),
    BILL_TO_ADDRESS_COUNTRY("bill_to_address_country"),
    MANDATORY_FIELD_EMPTY("mandatoryFieldEmpty"),
    SELECT_CARD_FIELD_EMPTY("selectCardFieldEmpty"),
    SELECT_FIELD_EMPTY("selectFieldEmpty"),
    INVALID_EMAIL("invalid.email"),
    INVALID_PAYMENT_CARD_NUMBER("invalid.paymentCardNumber"),
    INVALID_PAYMENT_CARD_EXPIRY_DATE("invalid.paymentCardExpiryDate"),
    INVALID_PAYMENT_CARD_VERIFICATION_NUMBER("invalid.paymentCardVerificationNumber"),
    CONFIRM_PAYMENT_AMOUNT_USING_NEW_CARD("confirmPaymentAmountUsingNewCard"),
    PAYMENT_AMOUNT_USING_NEW_CARD_INSTRUCTIONS("EnterPaymentCardDetails.instructions"),
    CONFIRM_PAYMENT_AMOUNT_USING_SAVED_CARD("confirmPaymentAmountUsingSavedCard"),
    PAYMENT_AMOUNT_USING_SAVED_CARD_INSTRUCTIONS("ConfirmExistingPaymentCardPayment.instructions"),
    CONFIRM_PAYMENT_AMOUNT_USING_EXISTING_SAVED_CARD("ConfirmExistingPaymentCardPayment"),
    CONFIRM_PAYMENT_AMOUNT_USING_WEB_CREDIT("confirmPaymentAmountUsingWebCredit"),
    PAYMENT_GATEWAY_REPLY("paymentGatewayReply"),
    REFUND_READY_FOR_COLLECTION_EMAIL_SUBJECT("refundReadyForCollectionEmail.subject"),
    REFUND_READY_FOR_COLLECTION_EMAIL_BODY("refundReadyForCollectionEmail.body"),
    REFUND_PICK_UP_WINDOW_EXPIRED_EMAIL_SUBJECT("refundPickUpWindowExpiredEmail.subject"),
    REFUND_PICK_UP_WINDOW_EXPIRED_EMAIL_BODY("refundPickUpWindowExpiredEmail.body"),
    REFUND_ERROR_EMAIL_SUBJECT("refundErrorEmail.subject"),
    REFUND_ERROR_EMAIL_BODY("refundErrorEmail.body"),
    STATEMENT_TERMS_NOT_ACCEPTED("statementTermsNotAccepted"),
    EMAIL_ADDRESS_NOT_FOUND("emailAddressNotFound"),
    INVALID_PASSWORD("invalidPassword"),
    INVALID_PASSWORD_RESET_TOKEN("invalidPasswordResetToken"),
    INVALID_CUBIC_FILE_FIELD("invalidCubicFileField"),
    TRAVEL_CARD_END_DATE_EXCEEDS_JOB_CENTRE_PLUS_EXPIRY_DATE("travelCardEndDateExceedsJobCentrePlusExpiryDate"),
    TRAVEL_CARD_LENGTH_GREATER_THAN_THREE_MONTHS_FOR_JOB_CENTRE_PLUS("travelCardLengthGreaterThanThreeMonthsForJobCentrePlus"),
    CARDNUMBER_NUMERIC_ONLY("numericCharactersOnly"),
    CARDNUMBER_CHECKSUM("cardCheckSumFailed"),
    WEB_CREDIT_STATEMENT_ITEM_PURCHASE("WebAccountCreditStatement.item.purchase"),
    WEB_CREDIT_STATEMENT_ITEM_REFUND("WebAccountCreditStatement.item.refund"),
    CARD_ALREADY_ASSOCIATED_ERROR("Card is already associated with a web account"),
    CARD_CANNOT_BE_EMPTY_ERROR("Card Number cannot be empty"),
    UNABLE_TO_CREATE_AN_ACCOUNT_ERROR("Unable to create a web account for this user"),
    UNABLE_TO_CREATE_A_CUSTOMER_ERROR("Unable to create a customer"),
    GREATER_THAN_FAILED_CARD_PRODUCTS_PAY_AS_YOU_GO_LIMIT("greaterThanFailedCardProducts.payAsYouGo.limit"),
    FAILED_CARD_PRODUCTS_DATE_OF_REFUND_IN_FUTURE("DateOfRefundInFuture"),
    FAILED_CARD_PRODUCTS_ONE_OF_PRODUCTS_EXPIRED_ON_DATE_OF_REFUND("OneOfProductsExpiredOnDateOfRefund"),
    NOT_AVAILABLE("notAvailable"),
    PAY_AS_YOU_GO_CREDIT_GREATER_THAN_LIMIT("payAsYouGoCreditGreaterThanLimit"),
    PAY_AS_YOU_GO_CREDIT_AD_HOC_REFUND_GREATER_THAN_LIMIT("payAsYouGoCreditAdHocLoadGreaterThanLimit"),
    PAY_AS_YOU_GO_CREDIT_GREATER_THAN_PURCHASE_LIMIT("payAsYouGoCreditGreaterThanPurchaseLimit"),
    AD_HOC_REFUND_OYSTER_CARD_BALANCE_GREATER_THAN_LIMIT("adHocRefundOysterCardBalanceGreaterThanLimit"),
    PAY_AS_YOU_GO_LENGTH_GREATER_THAN_LIMIT("payAsYouGoLengthGreaterThanLimit"),
    JOURNEY_DATE("journey.date"),
    JOURNEY_DATE_TIME("journey.dateTime"),
    JOURNEY_START_TIME("journey.startTime"),
    JOURNEY_END_TIME("journey.endTime"),
    JOURNEY_TIME_SEPARATOR("journey.time.separator"),
    JOURNEY_DESCRIPTION("journey.description"),
    JOURNEY_ORIGIN_DESTINATION_SEPARATOR("journey.origin.destination.separator"),
    JOURNEY_TOP_UP_SEPARATOR("journey.topUp.separator"),
    JOURNEY_BUS_ROUTE_PREFIX("journey.busRoute.prefix"),
    JOURNEY_CHARGE("journey.charge"),
    JOURNEY_CREDIT("journey.credit"),
    JOURNEY_BALANCE("journey.balance"),
    JOURNEY_NOTE("journey.note"),
    JOURNEY_DAILY_BALANCE("journey.dailyBalance"),
    JOURNEY_CAPPED("journey.capped"),
    JOURNEY_AUTO_COMPLETED("journey.autoCompleted"),
    JOURNEY_MANUALLY_CORRECTED("journey.manuallyCorrected"),
    SERVICE_BUSY("service.busy"),
    SELECTED_STATION_DIFFERENT_FROM_PENDINGITEM_STATION("selected.station.different.from.pendingitem.station"),
    JOURNEY_WEEKLY_SUBJECT("journey.weekly.subject"),
    JOURNEY_WEEKLY_BODY("journey.weekly.body"),
    JOURNEY_MONTHLY_SUBJECT("journey.monthly.subject"),
    JOURNEY_MONTHLY_BODY("journey.monthly.body"),
    REISSUE_AS_FAILED("reissue.as.failed"),
    ADD_PAY_AS_YOU_GO("add.pay.as.you.go"),
    PAY_AS_YOU_GO_BALANCE("pay.as.you.go.balance"),
    MULTIPLE_GOODWILL_PAYMENT_FOR_SAME_REASON("multiple.goodwill.reason.for.same.reason"),
    GOODWILL_MUST_BE_GREATER_THAN_ZERO("goodwill.must.be.greater.than.zero"),
    GOODWILL_MUST_BE_LESS_THAN_MAX_REFUND_ALLOWED("goodwill.max.refund.limit"),
    GOODWILL("goodwill"),
    GOODWILL_REASON("goodwill.reason"),
    OPEN_WEB_ACCOUNT_CONFIRM_MESSAGE_CALL_LOGGED("open.web.account.confirm.message.call.logged"),
    OPEN_WEB_ACCOUNT_CONFIRM_MESSAGE_LOGIN("open.web.account.confirm.message.login"),
    OPEN_WEB_ACCOUNT("open.web.account"),
    REFUND_DATE_IS_FUTURE("cancelAndSurrender.dateIsFutureDate"),
    TICKET_EXPIRED("cancelAndSurrender.ticketExpired"),
    ADD_PAYMENT_CARD("ChoosePaymentCard.paymentcards.add"),
    ADD_AND_SAVE_PAYMENT_CARD("ChoosePaymentCard.paymentcards.addandsave"),
    DELETE_PAYMENT_CARD_IN_USE("deletePaymentCardInUse"),
    MAGNETIC_TICKET_NUMBER_INVALID_FORMAT("magneticTicketInvalidFormat"),
    PAYMENT_CARD_IN_USE("PaymentCard.inUse"),
    BAD_CREDENTIALS("badCredentials"),
    NO_CHANGES_TO_SAVE("noChangesToSave"),
    TRAVEL_CARD_REFUND_LIMIT_REACHED("travelCardRefundLimitReached"),
    STOLEN_REFUND_MESSAGE("stolenRefundMessage"),
    PAYMENT_GATEWAY_UNAVAILABLE("paymentGateway.unavailable"),
    LINKED_WEB_ACCOUNT_IS_DEACTIVATED_ALERT_MESSAGE("linked.web.account.is.deactivated.alert.message"),
    AUTO_TOP_UP_CONFIGURED_FOR_ONE_OF_THE_CARDS("auto.top.up.configured.for.one.of.the.cards"),
    AUTO_TOP_UP_NO_TOP_UP("auto.top.up.no.top.up"),
    AUTO_TOP_UP_TOP_UP_AMOUNT_2("auto.top.up.top.up.amount.2"),
    AUTO_TOP_UP_TOP_UP_AMOUNT_3("auto.top.up.top.up.amount.3"),
    AUTO_TOP_UP_TOP_UP_AMOUNT_4("auto.top.up.top.up.amount.4"),
    ACCOUNT_DEACTIVATED("accountDeactivated"),
    ACCOUNT_DEACTIVATION_RULES("account.deactivation.rules"),
    FIELD_TRAVEL_CARD_IDENTICAL("travelCardIdentical"),
    INVALID_IMPORT_FILE_FIELD("invalidImportFileField"),
    DUPLICATE_ADHOC_CODE_FILE_FIELD("duplicateAdhocCodeFileField"),
    CANNOT_REFUND_TOTAL_AMOUNT_ZERO("cannot.refund.total.amount.zero"),
    STATIONS_LIST("stationsList"),
    CARDS_LIST("cardsList"),
    GREATER_THAN_CHARACTER_LIMIT("greaterThanCharacterLimit"),
    NATIONAL_RAIL_IDENTIFIER_TEXT("journey.nationalRail.identifier.pattern"),
    LONDON_UNDERGROUND_IDENTIFIER_TEXT("journey.londonUnderground.identifier.pattern"),
    BUS_ROUTE_ID_UNDEFINED_IDENTIFER_TEXT("journey.busRouteIdUndefined.identifier.pattern"),
    CUSTOMER_INVALID_OYSTER_CARD("customerInvalidOysterCard"),
    INVALID_PAYMENT_METHOD("invalidPaymentMethod"),
    INVALID_REFUND_SCENARIO_TYPE("invalidRefundScenarioType"),
    INVALID_REFUND_SCENARIO_SUB_TYPE("invalidRefundScenarioSubType"),
    INVALID_CALCULATION_BASIS("invalidCalculationBasis"),
    HOUSENAME_REQUIRED("houseNameRequired"),
    STREET_REQUIRED("streetRequired"),
    TOWN_REQUIRED("townRequired"),
    PAYMENT_CARD_REQUIRED("paymentCardRequired"),
    LINKED_PAYMENT_CARD_CANNOT_BE_DELETED("deleteLinkedPaymentCardNotAllowed"),
    AUTO_TOP_UP_CONFIRMATION_SUBJECT("autoTopUpConfirmEmail.subject"),
    AUTO_TOP_UP_CONFIRMATION_BODY("autoTopUpConfirmEmail.body"),
    CUBIC_INTERACTION_READYFORCOLLECTION_SUBJECT("cubicInteractionReadyForCollectionEmail.subject"),
    CUBIC_INTERACTION_READYFORCOLLECTION_BODY("cubicInteractionReadyForCollectionEmail.body"),
    CUBIC_INTERACTION_PICKED_UP_SUBJECT("cubicInteractionPickedUpEmail.subject"),
    CUBIC_INTERACTION_PICKED_UP_BODY("cubicInteractionPickedUpEmail.body"),
    CUBIC_INTERACTION_PICKUP_WINDOW_EXPIRED_SUBJECT("cubicInteractionPickupWindowExpiredEmail.subject"),
    CUBIC_INTERACTION_PICKUP_WINDOW_EXPIRED_BODY("cubicInteractionPickupWindowExpiredEmail.body"),
    CUBIC_INTERACTION_ERROR_SUBJECT("cubicInteractionErrorEmail.subject"),
    CUBIC_INTERACTION_ERROR_BODY("cubicInteractionErrorEmail.body"),
    CUBIC_INTERACTION_READYFORCOLLECTION("cubicInteractionReadyForCollection"),
    CUBIC_INTERACTION_READYFORCOLLECTION_EMAILSENT("cubicInteractionReadyForCollection.emailSent"),
    CUBIC_INTERACTION_PICKED_UP("cubicInteractionPickedUp"),
    CUBIC_INTERACTION_PICKED_UP_EMAILSENT("cubicInteractionPickedUp.emailSent"),
    CUBIC_INTERACTION_PICKUP_WINDOW_EXPIRED("cubicInteractionPickupWindowExpired"),
    CUBIC_INTERACTION_PICKUP_WINDOW_EXPIRED_EMAILSENT("cubicInteractionPickupWindowExpired.emailSent"),
    CUBIC_INTERACTION_ERROR("cubicInteractionError"),
    CUBIC_INTERACTION_ERROR_EMAILSENT("cubicInteractionError.emailSent"),
    ORDER_NOT_FOUND("order.notFound"),
    COLLECT_PURCHASE_INSTRUCTION("CollectPurchase.instruction"),
    ID_NOT_POPULATED("id.notPopulated"),
    CUSTOMER_OBJECT_NULL("customer.nullObject"),
    CUSTOMER_NOT_AUTHORIZED("customer.notAuthorizedToUpdateOrDelete"),
    MULTIPLE_CARDS_ON_ORDER("multipleCards.order"),
    COLLECTPURCHASE_INSTRUCTION("CollectPurchase.instruction"),
    ADMINSTRATION_FEE_MUST_BE_GREATER_THAN_ZERO("adminstration.fee.must.be.greater.than.zero"),
    PAY_AS_YOU_GO_VALUE_MUST_BE_GREATER_THAN_ZERO("payasyougo.value.must.be.greater.than.zero"),
    INVALID_POSTCODE("invalidPostcode.error"),
    HOTLISTED("hotlisted"),
    CANCEL_ORDER_SUCCESS("cancelOrder.success"),
    CANCEL_ORDER_AWAITING_REFUND_PAYMENT("cancelOrder.awaitingRefundPayment"),
    CANCEL_ORDER_GENERAL("cancelOrder.general"),
    CANCEL_ORDER_AFTER_CUT_OFF_TIME("cancelOrder.afterCutOffTime"),
    CANCEL_ORDER_CREATE_OR_UPDATE_REFUND_ORDER("cancelOrder.createOrUpdate.RefundOrder"),
    CANCEL_ORDER_CREATE_OR_UPDATE_REFUND_SETTLEMENT("cancelOrder.createOrUpdate.RefundSettlement"),
	NICK_NAME_ALREADY_USED("nickNameAlreadyUsed"),
    XML_PARSING("xml.parsing"),
    CUBIC_UNEXPECTED_SERVER("cubic.unexpectedServer"),
    CUBIC_AUTHENTICATION("cubic.authentication"),
    GREATER_THAN_WEB_CREDIT_AVAILABLE_OR_ORDER_AMOUNT("greaterThanWebCreditAmountOrOrderAmount"),
    WEBSERVICE_INVALID_PARAMETER("webservice.invalidInputParameter"),
    WEBSERVICE_OPERATION_NOT_COMPLETED_DUE_TO_EXCEPTION("webservice.operationNotCompleted"),
    WEBSERVICE_RECORD_NOT_FOUND("webservice.recordNotFound"),
    WEBSERVICE_RECORD_LOOKUP_FAILED_DUE_TO_EXCEPTION("webservice.recordLookupFailed"),
    WEBSERVICE_CUSTOMER_NOT_FOUND("webservice.customer.recordNotFound"),
    WEBSERVICE_CUSTOMER_UPDATE_ID_MISMATCH("webservice.customer.update.idMismatch"),
    WEBSERVICE_CARD_NOT_FOUND("webservice.card.recordNotFound"),
    TRAVEL_CARD_EMAIL_NOTIFICATION_IN_PAST("emailNotificationInPast"),
    SOURCE_CARD_INELIGIBLE_TRANSFER_PRODUCT("sourceCardIneligibleTransferProduct"),
    TRANSFERS_IS_PASSENGER_TYPE_ADULT("transfers.passenger.type"),
    TRANSFERS_TRAVELCARD_JOB_CENTRE_PLUS_DISCOUNTED("transfers.travelcard.jobcenter.discounted"),
    TRANSFERS_CARD_DISCOUNTED("transfers.card.discounted"),
    TRANSFERS_TRAVELCARD_ATLEASTONEDAY_REMAINING("transfers.travelcard.atleastoneday.remaining"),
    TRANSFERS_IS_TRAVELCARD_DISCOUNTED("transfers.travel.card.discounted"),
    TRANSFERS_HAS_PRODUCT_ITEM_OF_TYPE_BUS("transfers.producttype.bus"),
    TRANSFERS_TRAVELCARD_HAS_BEEN_TRADED_BEFORE("transfers.travelcard.previously.traded"),
    TRANSFERS_HAS_PAY_AS_YOU_GO_ITEM_MORE_THAN_FIFTY_POUNDS("transfers.payg.bigger.than.fifty"), 
    TRANSFERS_CARD_HAS_NO_DEPOSIT("transfers.card.nodeposit"), 
    TRANSFERS_PAYASYOUGO_HAS_NEGATIVE_AMOUNT("transfers.payasyougo.has.negative.amount"),
    TRANSFERS_AUTO_TOP_UP_DISABLED("transfers.auto.topup.disabled"),
    TRANSFER_PRODUCT_SUCCESSFUL("transfers.update.successful"),
    TRANSFERS_CARD_EIGHTEEN_PLUS_DISCOUNTED("transfers.card.eighteenplus.discounted"),
    TRANSFERS_CARD_JOB_CENTRE_PLUS_DISCOUNTED("transfers.card.jobcenterplus.discounted"),
    TRANSFERS_CARD_APPRENTICE_DISCOUNTED("transfers.card.apprentice.discounted"),
    TRANSFERS_CARD_HAS_NO_PRODUCTS_TO_TRANSFER("transfers.card.nothing.to.transfer"),
    CARD_ALREADY_ASSOCIATED("card.alreadyAssociated"),
    CARD_ALREADY_ASSOCIATED_TO_ANOTHER_CUSTOMER("card.alreadyAssociated.otherCustomer"),
    TRANSFERS_TRAVELCARD_EIGHTEEN_PLUS_DISCOUNT("transfers.travelcard.eighteen.plus.discounted"),
    TRANSFER_PRODUCTS_CONFIRMATION_SUBJECT("transferProductsConfirmEmail.subject"),
    TRANSFER_PRODUCTS_CONFIRMATION_BODY("transferProductsConfirmEmail.body"),
    TRANSFER_SUMMARY_CONFIRMATION("innovator.summaryConfirmation.text"),
    CARD_INELIGIBLE_TO_BE_REPORTED_LOST_OR_STOLEN("cardIneligibleToBeReportedLostOrStolen"),
    AUTO_TOP_UP_PENDING_AMOUNT_FOR_EXISTING_OYSTER_CARD("autoTopUp.PendingAmount.ForExisting.OysterCard.validation.message"),
    ELIGIBLE_SOURCE_CARD_INFORMATION_MESSAGE("eligibleSourceCardInformationMessage"),
    PASSENGER_TYPE("passengertType"),
    DISCOUNT_TYPE("discountType"),
    INELIGIBLE_SOURCE_CARD_CONTACT_TFL_MESSAGE("inEligibleSourceCardContactTFLMessage"),
    PRE_PAID_TICKET_PRICE_INVALID("prepaidTicketPriceInvalid"),
    PRE_PAID_TICKET_INVALID_PASSENGER_TYPE("prepaidTickePassengerTypeInvalid"),
    PRE_PAID_TICKET_INVALID_TO_DURATION("prepaidTickeInvalidToDuration"), 
    PRE_PAID_TICKET_INVALID_FROM__DURATION("prepaidTickeInvalidFromDuration"), 
    PRE_PAID_TICKET_INVALID_END_ZONE("prepaidTickeInvalidEndZone"), 
    PRE_PAID_TICKET_INVALID_START_ZONE("prepaidTickeInvalidStartZone"), 
    PRE_PAID_TCIKET_ADHOC_CODE_EMPTY("prepaidTickeAdhocCodeEmpty"),
    PRE_PAID_TICKET_EFFECTIVE_DATE_INVALID("prepaidTicketEffectiveDateInvalid"),
    IS_CARD_WITH_FAILED_AUTO_TOPUP_CASE("oystercard.with.failedAutoTopUpcase.amended"),
    INVALID_RESETTLEMENT_PERIOD_MORETHAN_LIMIT("resettlementPeriod.morethan.systemLimit"),
    CART_DATA_HAS_EXPIRED("webservice.cart.dataHasExpired");
    
    private static final String SEPARATOR = ".";
    private String codeStem;

    private ContentCode(String codeStem) {
        this.codeStem = codeStem;
    }

    public String codeStem() {
        return this.codeStem;
    }

    public String labelCode() {
        return buildCode(this.codeStem, ContentCodeSuffix.LABEL);
    }

    public String textCode() {
        return buildCode(this.codeStem, ContentCodeSuffix.TEXT);
    }

    public String errorCode() {
        return buildCode(this.codeStem, ContentCodeSuffix.ERROR);
    }

    public String headingCode() {
        return buildCode(this.codeStem, ContentCodeSuffix.HEADING);
    }

    public String buttonLabelCode() {
        return buildCode(this.codeStem, ContentCodeSuffix.BUTTON, ContentCodeSuffix.LABEL);
    }

    public String buttonTipCode() {
        return buildCode(this.codeStem, ContentCodeSuffix.BUTTON, ContentCodeSuffix.TIP);
    }

    public String tipCode() {
        return buildCode(this.codeStem, ContentCodeSuffix.TIP);
    }

    public String abbreviationCode() {
        return buildCode(this.codeStem, ContentCodeSuffix.ABBR);
    }

    public String titleCode() {
        return buildCode(this.codeStem, ContentCodeSuffix.TITLE);
    }

    public String hintCode() {
        return buildCode(this.codeStem, ContentCodeSuffix.HINT);
    }

    public String urlCode() {
        return buildCode(this.codeStem, ContentCodeSuffix.URL);
    }

    public String linkCode() {
        return buildCode(this.codeStem, ContentCodeSuffix.LINK);
    }

    public String popupHeadingCode() {
        return buildCode(this.codeStem, ContentCodeSuffix.POPUP, ContentCodeSuffix.HEADING);
    }

    public String popupTextCode() {
        return buildCode(this.codeStem, ContentCodeSuffix.POPUP, ContentCodeSuffix.TEXT);
    }

    public String placeholderCode() {
        return buildCode(this.codeStem, ContentCodeSuffix.PLACEHOLDER);
    }

    public String optionCode() {
        return buildCode(this.codeStem, ContentCodeSuffix.OPTION);
    }

    public String breadcrumbLinkCode() {
        return buildCode(this.codeStem, ContentCodeSuffix.BREADCRUMB, ContentCodeSuffix.LINK);
    }

    public String breadcrumbUrlCode() {
        return buildCode(this.codeStem, ContentCodeSuffix.BREADCRUMB, ContentCodeSuffix.URL);
    }

    public String breadcrumbTipCode() {
        return buildCode(this.codeStem, ContentCodeSuffix.BREADCRUMB, ContentCodeSuffix.TIP);
    }

    protected String buildCode(String stem, ContentCodeSuffix... suffixes) {
        StringBuilder code = new StringBuilder();
        code.append(stem);
        for (ContentCodeSuffix suffix : suffixes) {
            code.append(SEPARATOR);
            code.append(suffix.code());
        }
        return code.toString();
    }
}
