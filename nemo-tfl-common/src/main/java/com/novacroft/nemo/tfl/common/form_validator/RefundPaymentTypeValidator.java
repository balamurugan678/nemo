package com.novacroft.nemo.tfl.common.form_validator;

import static com.novacroft.nemo.common.constant.CommonContentCode.INVALID_ACCOUNT_NUMBER;
import static com.novacroft.nemo.common.constant.CommonContentCode.INVALID_POSTCODE;
import static com.novacroft.nemo.common.constant.CommonContentCode.INVALID_SORTCODE_PATTERN;
import static com.novacroft.nemo.common.constant.CommonPageCommandAttribute.FIELD_PAYEE_POSTCODE;
import static com.novacroft.nemo.tfl.common.constant.ContentCode.PAY_AS_YOU_GO_CREDIT_AD_HOC_REFUND_GREATER_THAN_LIMIT;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_PAYEE_ACCOUNT_NUMBER;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_PAYEE_ADDRESS_COUNTRY;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_PAYEE_ADDRESS_NUMBER;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_PAYEE_ADDRESS_STREET;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_PAYEE_ADDRESS_TOWN;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_PAYEE_FIRST_NAME;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_PAYEE_LAST_NAME;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_PAYEE_SORT_CODE;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_STATION_ID;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_TARGET_CARD_NUMBER;
import static com.novacroft.nemo.tfl.common.constant.SystemParameterCode.PAY_AS_YOU_GO_AD_HOC_REFUND_LIMIT;
import static com.novacroft.nemo.tfl.common.util.PayAsYouGoItemValidatorUtil.getPayAsYouGoItemField;
import static com.novacroft.nemo.tfl.common.util.PayAsYouGoItemValidatorUtil.getPayAsYouGoItemPrice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.utils.CurrencyUtil;
import com.novacroft.nemo.common.validator.BaseValidator;
import com.novacroft.nemo.common.validator.PostcodeValidator;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;

/**
 * Refunds payment type validator
 */
@Component("refundPaymentTypeValidator")
public class RefundPaymentTypeValidator extends BaseValidator {

    @Autowired
    protected SystemParameterService systemParameterService;
    
    @Autowired
    protected PostcodeValidator postcodeValidator;
    
    protected static final String FIELD_PAY_AS_YOU_GO_CREDIT = "cartItemList[0].price";
    protected static final String FIELD_ADMIN_FEE = "cartItemList[1].price";
    protected static final String AD_HOC_LOAD = "AdhocLoad";
    protected static final String BACS = "BACS";
    protected static final String CHEQUE = "Cheque";
    protected static final int PAY_AS_YOU_GO_MAXIMUM_CHARACTER_LIMIT = 6;
    protected static final String SORTCODE_PATTERN = "[0-9]{2}-[0-9]{2}-[0-9]{2}";
    protected static final String ACCOUNT_NUMBER_PATTERN = "[0-9]{8}";

    @Override
    public boolean supports(Class<?> targetClass) {
        return CartCmdImpl.class.equals(targetClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CartCmdImpl cartCmdImpl = (CartCmdImpl) target;
        rejectIfValidationFailsForPaymentType(errors, cartCmdImpl);
    }
    
    protected void rejectIfValidationFailsForPaymentType(Errors errors, CartCmdImpl cartCmdImpl) {
    	if (isAdHocLoadPaymentType(cartCmdImpl)) {
            rejectIfValidationFailsForAdHocLoadPaymentType(cartCmdImpl, errors);
        } else if (isChequePaymentType(cartCmdImpl)) {
            rejectIfValidationFailsForChequePaymentType(cartCmdImpl, errors);
        } else if (isBacsPaymentType(cartCmdImpl)) {
            rejectIfValidationFailsForBacsPaymentType(cartCmdImpl, errors);
        }
    }
    
    protected boolean isAdHocLoadPaymentType(CartCmdImpl cartCmdImpl) {
    	return AD_HOC_LOAD.equals(cartCmdImpl.getPaymentType());
    }
    
    protected boolean isChequePaymentType(CartCmdImpl cartCmdImpl) {
    	return CHEQUE.equals(cartCmdImpl.getPaymentType());
    }
    
    protected boolean isBacsPaymentType(CartCmdImpl cartCmdImpl) {
    	return BACS.equals(cartCmdImpl.getPaymentType());
    }
    
    protected void rejectIfValidationFailsForAdHocLoadPaymentType(CartCmdImpl cartCmdImpl, Errors errors) {
    	rejectIfMandatoryFieldEmpty(errors, FIELD_TARGET_CARD_NUMBER);
        rejectIfMandatoryFieldEmpty(errors, FIELD_STATION_ID);
        rejectIfPayAsYouGoBalanceIsGreaterThanLimitInAdHocLoad(cartCmdImpl, errors);
    }
    
    protected void rejectIfValidationFailsForChequePaymentType(CartCmdImpl cartCmdImpl, Errors errors) {
		rejectIfMandatoryFieldEmpty(errors, FIELD_PAYEE_FIRST_NAME);
		rejectIfMandatoryFieldEmpty(errors, FIELD_PAYEE_LAST_NAME);
		rejectIfMandatoryFieldEmpty(errors, FIELD_PAYEE_ADDRESS_NUMBER);
		rejectIfMandatoryFieldEmpty(errors, FIELD_PAYEE_ADDRESS_STREET);
		rejectIfMandatoryFieldEmpty(errors, FIELD_PAYEE_ADDRESS_TOWN);
		rejectIfInvalidPostcode(errors, cartCmdImpl.getPayeeAddress().getPostcode());
		rejectIfMandatoryFieldEmpty(errors, FIELD_PAYEE_ADDRESS_COUNTRY);
    }
    
    protected void rejectIfValidationFailsForBacsPaymentType(CartCmdImpl cartCmdImpl, Errors errors) {
		rejectIfMandatoryFieldEmpty(errors, FIELD_PAYEE_FIRST_NAME);
		rejectIfMandatoryFieldEmpty(errors, FIELD_PAYEE_LAST_NAME);
		rejectIfMandatoryFieldEmpty(errors, FIELD_PAYEE_ADDRESS_NUMBER);
		rejectIfMandatoryFieldEmpty(errors, FIELD_PAYEE_ADDRESS_STREET);
		rejectIfMandatoryFieldEmpty(errors, FIELD_PAYEE_ADDRESS_TOWN);
		rejectIfInvalidPostcode(errors, cartCmdImpl.getPayeeAddress().getPostcode());
		rejectIfMandatoryFieldEmpty(errors, FIELD_PAYEE_ADDRESS_COUNTRY);
		rejectIfInvalidSortcode(errors, FIELD_PAYEE_SORT_CODE);
		rejectIfInvalidAccountNumber(errors, FIELD_PAYEE_ACCOUNT_NUMBER);
    }

    protected void rejectIfPayAsYouGoBalanceIsGreaterThanLimitInAdHocLoad(CartCmdImpl cartCmdImpl, Errors errors) {
    	rejectIfPayAsYouGoBalanceIsGreaterThanLimitInSystemInAdHocLoad(errors, getPayAsYouGoItemPrice(cartCmdImpl), getPayAsYouGoItemField(cartCmdImpl));
    }
    
    private void rejectIfPayAsYouGoBalanceIsGreaterThanLimitInSystemInAdHocLoad(Errors errors, int payAsYouGoBalance, String field) {
    	Integer limit = systemParameterService.getIntegerParameterValue(PAY_AS_YOU_GO_AD_HOC_REFUND_LIMIT.code());
        if (payAsYouGoBalance > limit) {
            errors.rejectValue(field, 
        	    PAY_AS_YOU_GO_CREDIT_AD_HOC_REFUND_GREATER_THAN_LIMIT.errorCode(),
        	    new String[]{CurrencyUtil.formatPenceWithoutCurrencySymbol(limit)}, null);
        }
    }
    
    protected void rejectIfInvalidPostcode(Errors errors, String field) {
    	if (!postcodeValidator.validate(field)) {
    	    errors.rejectValue(FIELD_PAYEE_POSTCODE, INVALID_POSTCODE.errorCode());
    	}
    }
    
    protected void rejectIfInvalidSortcode(Errors errors, String field) {
    	rejectIfPatternNotMatched(errors, field, INVALID_SORTCODE_PATTERN.errorCode(), SORTCODE_PATTERN);
    }
    
    protected void rejectIfInvalidAccountNumber(Errors errors, String field) {
    	rejectIfPatternNotMatched(errors, field, INVALID_ACCOUNT_NUMBER.errorCode(), ACCOUNT_NUMBER_PATTERN);
    }
}
