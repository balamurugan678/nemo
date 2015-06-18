package com.novacroft.nemo.tfl.common.form_validator;

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.utils.CurrencyUtil;
import com.novacroft.nemo.common.validator.BaseValidator;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import static com.novacroft.nemo.tfl.common.constant.ContentCode.PAY_AS_YOU_GO_CREDIT_GREATER_THAN_LIMIT;
import static com.novacroft.nemo.tfl.common.constant.ContentCode.PAY_AS_YOU_GO_LENGTH_GREATER_THAN_LIMIT;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_PAY_AS_YOU_GO_VALUE;
import static com.novacroft.nemo.tfl.common.constant.SystemParameterCode.PAY_AS_YOU_GO_LIMIT;
import static com.novacroft.nemo.tfl.common.util.PayAsYouGoItemValidatorUtil.cartItemListNotEmpty;

/**
 * Refunds payment type validator
 */
@Component("refundPayAsYouGoItemValidator")
public class RefundPayAsYouGoItemValidator extends BaseValidator {

    @Autowired
    protected SystemParameterService systemParameterService;

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
        rejectIfPayAsYouGoBalanceIsGreaterThanLimit(errors, cartCmdImpl);
        rejectIfPayAsYouGoCharacterLengthIsTooLong(errors, cartCmdImpl);
    }

    protected void rejectIfPayAsYouGoBalanceIsGreaterThanLimit(Errors errors, CartCmdImpl cartCmdImpl) {
        if (cartItemListNotEmpty(cartCmdImpl)) {
            rejectIfPayAsYouGoBalanceIsGreaterThanLimitInSystem(errors, cartCmdImpl.getPayAsYouGoValue(),
                    FIELD_PAY_AS_YOU_GO_VALUE);
        }
    }

    protected void rejectIfPayAsYouGoBalanceIsGreaterThanLimitInSystem(Errors errors, int payAsYouGoBalance, String field) {
        Integer limit = systemParameterService.getIntegerParameterValue(PAY_AS_YOU_GO_LIMIT.code());
        if (payAsYouGoBalance > limit) {
            errors.rejectValue(field, PAY_AS_YOU_GO_CREDIT_GREATER_THAN_LIMIT.errorCode(),
                    new String[]{CurrencyUtil.formatPenceWithoutCurrencySymbol(limit)}, null);
        }
    }

    protected void rejectIfPayAsYouGoCharacterLengthIsTooLong(Errors errors, CartCmdImpl cartCmdImpl) {
        if (cartItemListNotEmpty(cartCmdImpl)) {
            rejectIfPayAsYouGoCharacterLengthIsGreaterThanLimit(errors, cartCmdImpl.getPayAsYouGoValue(),
                    FIELD_PAY_AS_YOU_GO_VALUE);
        }
    }

    protected void rejectIfPayAsYouGoCharacterLengthIsGreaterThanLimit(Errors errors, int payAsYouGoBalance, String field) {
        int length = String.valueOf(payAsYouGoBalance).length();
        if (length >= PAY_AS_YOU_GO_MAXIMUM_CHARACTER_LIMIT) {
            errors.rejectValue(field, PAY_AS_YOU_GO_LENGTH_GREATER_THAN_LIMIT.errorCode());
        }
    }
}
