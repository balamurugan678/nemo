package com.novacroft.nemo.tfl.common.form_validator;

import static com.novacroft.nemo.common.constant.CommonContentCode.INVALID_ACCOUNT_NUMBER;
import static com.novacroft.nemo.common.constant.CommonContentCode.INVALID_POSTCODE;
import static com.novacroft.nemo.common.constant.CommonContentCode.INVALID_SORTCODE_PATTERN;
import static com.novacroft.nemo.common.constant.CommonPageCommandAttribute.FIELD_PAYEE_POSTCODE;
import static com.novacroft.nemo.tfl.common.constant.ContentCode.AD_HOC_REFUND_OYSTER_CARD_BALANCE_GREATER_THAN_LIMIT;
import static com.novacroft.nemo.tfl.common.constant.ContentCode.PAY_AS_YOU_GO_CREDIT_AD_HOC_REFUND_GREATER_THAN_LIMIT;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_PAYEE_ACCOUNT_NUMBER;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_PAYEE_ADDRESS_COUNTRY;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_PAYEE_ADDRESS_NUMBER;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_PAYEE_ADDRESS_STREET;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_PAYEE_ADDRESS_TOWN;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_PAYEE_FIRST_NAME;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_PAYEE_LAST_NAME;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_PAYEE_SORT_CODE;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_PAYMENT_TYPE;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_PAY_AS_YOU_GO_VALUE;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_STATION_ID;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_TARGET_CARD_NUMBER;
import static com.novacroft.nemo.tfl.common.constant.SystemParameterCode.AD_HOC_REFUND_TARGET_OYSTER_CARD;
import static com.novacroft.nemo.tfl.common.constant.SystemParameterCode.PAY_AS_YOU_GO_AD_HOC_REFUND_LIMIT;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.data_service.CountryDataService;
import com.novacroft.nemo.common.transfer.CountryDTO;
import com.novacroft.nemo.common.utils.CurrencyUtil;
import com.novacroft.nemo.common.validator.BaseValidator;
import com.novacroft.nemo.common.validator.PostcodeValidator;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;

/**
 * Validator to validate payment section of refund cart
 */
@Component("refundCartPaymentValidator")
public class RefundCartPaymentValidator extends BaseValidator {

    protected static final String AD_HOC_LOAD = "AdhocLoad";
    protected static final String BACS = "BACS";
    protected static final String CHEQUE = "Cheque";
    protected static final String SORTCODE_PATTERN = "[0-9]{2}-[0-9]{2}-[0-9]{2}";
    protected static final String ACCOUNT_NUMBER_PATTERN = "[0-9]{8}";

    @Autowired
    protected SystemParameterService systemParameterService;
    @Autowired
    protected PostcodeValidator postcodeValidator;

    @Autowired
    protected CountryDataService countryDataService;

    @Override
    public boolean supports(Class<?> targetClass) {
        return CartCmdImpl.class.equals(targetClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CartCmdImpl cartCmdImpl = (CartCmdImpl) target;
        rejectIfPaymentTypeFieldEmpty(errors, cartCmdImpl);
        rejectIfMandatoryFieldsForAdHocLoadPaymentAreEmpty(errors, cartCmdImpl);
        rejectIfPayAsYouGoBalanceIsGreaterThanLimitInAdHocLoad(errors, cartCmdImpl);
        rejectIfMandatoryFieldsForChequePaymentAreEmpty(errors, cartCmdImpl);
        rejectIfMandatoryFieldsForBacsPaymentAreEmpty(errors, cartCmdImpl);
        rejectIfTotalAmountAndPreviousCreditIsGreaterThanLimitInAdHocLoad(errors, cartCmdImpl);
    }

    private void rejectIfMandatoryFieldsForAdHocLoadPaymentAreEmpty(Errors errors, CartCmdImpl cartCmdImpl) {
        if (isAdHocLoadPaymentType(cartCmdImpl)) {
            rejectIfMandatorySelectFieldEmpty(errors, FIELD_TARGET_CARD_NUMBER);
            rejectIfMandatorySelectFieldEmpty(errors, FIELD_STATION_ID);
        }
    }

    private boolean isAdHocLoadPaymentType(CartCmdImpl cartCmdImpl) {
        return AD_HOC_LOAD.equals(cartCmdImpl.getPaymentType());
    }

    private void rejectIfPayAsYouGoBalanceIsGreaterThanLimitInAdHocLoad(Errors errors, CartCmdImpl cartCmdImpl) {
        if (isAdHocLoadPaymentType(cartCmdImpl)) {
            rejectIfPayAsYouGoBalanceIsGreaterThanLimitInSystemInAdHocLoad(errors, cartCmdImpl.getToPayAmount());
        }
    }

    private void rejectIfPayAsYouGoBalanceIsGreaterThanLimitInSystemInAdHocLoad(Errors errors, int payAsYouGoBalance) {
        Integer limit = systemParameterService.getIntegerParameterValue(PAY_AS_YOU_GO_AD_HOC_REFUND_LIMIT.code());
        if (payAsYouGoBalance > limit) {
            errors.rejectValue(FIELD_PAY_AS_YOU_GO_VALUE, PAY_AS_YOU_GO_CREDIT_AD_HOC_REFUND_GREATER_THAN_LIMIT.errorCode(),
                            new String[] { CurrencyUtil.formatPenceWithoutCurrencySymbol(limit) }, null);
        }
    }

    private void rejectIfTotalAmountAndPreviousCreditIsGreaterThanLimitInAdHocLoad(Errors errors, CartCmdImpl cartCmdImpl) {
        if (isAdHocLoadPaymentType(cartCmdImpl) && StringUtils.isNotBlank(cartCmdImpl.getTargetCardNumber())) {
            Integer totalAmount = cartCmdImpl.getToPayAmount();
            Integer previousCredit = cartCmdImpl.getPreviousCreditAmountOnCard() == null ? 0 : cartCmdImpl.getPreviousCreditAmountOnCard();
            rejectIfTotalAmountAndPreviousCreditIsGreaterThanLimitInSystemInAdHocLoad(errors, totalAmount, previousCredit, FIELD_TARGET_CARD_NUMBER);
        }

    }

    private void rejectIfTotalAmountAndPreviousCreditIsGreaterThanLimitInSystemInAdHocLoad(Errors errors, int totalAmount, int previousCredit,
                    String field) {
        int limit = systemParameterService.getIntegerParameterValue(AD_HOC_REFUND_TARGET_OYSTER_CARD.code());
        if (totalAmount + previousCredit > limit) {
            errors.rejectValue(field, AD_HOC_REFUND_OYSTER_CARD_BALANCE_GREATER_THAN_LIMIT.errorCode(), null);
        }
    }

    private void rejectIfMandatoryFieldsForChequePaymentAreEmpty(Errors errors, CartCmdImpl cartCmdImpl) {
        if (isChequePaymentType(cartCmdImpl)) {
            rejectIfPayeeNameAndDetailsAreEmpty(errors);
            rejectIfPayeeAddressDetailsAreEmpty(errors, cartCmdImpl);
        }
    }

    private boolean isChequePaymentType(CartCmdImpl cartCmdImpl) {
        return CHEQUE.equals(cartCmdImpl.getPaymentType());
    }

    private void rejectIfPayeeNameAndDetailsAreEmpty(Errors errors) {
        rejectIfMandatoryFieldEmpty(errors, FIELD_PAYEE_FIRST_NAME);
        rejectIfMandatoryFieldEmpty(errors, FIELD_PAYEE_LAST_NAME);
    }

    private void rejectIfPayeeAddressDetailsAreEmpty(Errors errors, CartCmdImpl cartCmdImpl) {
        rejectIfMandatoryFieldEmpty(errors, FIELD_PAYEE_ADDRESS_NUMBER);
        rejectIfMandatoryFieldEmpty(errors, FIELD_PAYEE_ADDRESS_STREET);
        rejectIfMandatoryFieldEmpty(errors, FIELD_PAYEE_ADDRESS_TOWN);
        rejectIfInvalidPostcode(errors, cartCmdImpl.getPayeeAddress().getPostcode(), cartCmdImpl.getPayeeAddress().getCountry());
        rejectIfMandatoryFieldEmpty(errors, FIELD_PAYEE_ADDRESS_COUNTRY);
    }

    private void rejectIfInvalidPostcode(Errors errors, String field, CountryDTO country) {
        CountryDTO ukCountryDTO = countryDataService.findCountryByCode(ISO_UK_CODE);
        if (ObjectUtils.equals(country, ukCountryDTO) && !postcodeValidator.validate(field)) {
            errors.rejectValue(FIELD_PAYEE_POSTCODE, INVALID_POSTCODE.errorCode());
        }
    }

    private void rejectIfMandatoryFieldsForBacsPaymentAreEmpty(Errors errors, CartCmdImpl cartCmdImpl) {
        if (isBacsPaymentType(cartCmdImpl)) {
            rejectIfPayeeNameAndDetailsAreEmpty(errors);
            rejectIfPayeeAddressDetailsAreEmpty(errors, cartCmdImpl);
            rejectIfPayeeBankDetailsAreEmpty(errors);
        }
    }

    private boolean isBacsPaymentType(CartCmdImpl cartCmdImpl) {
        return BACS.equals(cartCmdImpl.getPaymentType());
    }

    private void rejectIfPayeeBankDetailsAreEmpty(Errors errors) {
        rejectIfInvalidSortcode(errors, FIELD_PAYEE_SORT_CODE);
        rejectIfInvalidAccountNumber(errors, FIELD_PAYEE_ACCOUNT_NUMBER);
    }

    protected void rejectIfInvalidSortcode(Errors errors, String field) {
        rejectIfPatternNotMatched(errors, field, INVALID_SORTCODE_PATTERN.errorCode(), SORTCODE_PATTERN);
    }

    protected void rejectIfInvalidAccountNumber(Errors errors, String field) {
        rejectIfPatternNotMatched(errors, field, INVALID_ACCOUNT_NUMBER.errorCode(), ACCOUNT_NUMBER_PATTERN);
    }

    protected void rejectIfPaymentTypeFieldEmpty(Errors errors, CartCmdImpl cartCmdImpl) {
        rejectIfMandatoryFieldEmpty(errors, FIELD_PAYMENT_TYPE);
    }
}
