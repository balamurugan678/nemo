package com.novacroft.nemo.tfl.common.form_validator;

import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_DISCOUNT_TYPE;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_END_DATE;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_END_ZONE;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_EXCHANGE_DATE;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_PASSENGER_TYPE;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_START_DATE;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_START_ZONE;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_TRADED_TICKET;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_TRAVEL_CARD_TYPE;
import static com.novacroft.nemo.tfl.common.constant.Refund.BUS_PASS;
import static com.novacroft.nemo.tfl.common.constant.Refund.DOT;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.novacroft.nemo.common.validator.BaseValidator;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.constant.CartAttribute;
import com.novacroft.nemo.tfl.common.constant.ContentCode;
import com.novacroft.nemo.tfl.common.constant.PageCommandAttribute;
import com.novacroft.nemo.tfl.common.constant.TicketType;

@Component("tradedTicketValidator")
public class TradedTicketValidator extends BaseValidator {

    private static final String CARTITEMLIST = "cartItemList[";
    private static final String CLOSE_BRACE = "]";

    @Override
    public boolean supports(Class<?> targetClass) {
        return CartCmdImpl.class.equals(targetClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CartCmdImpl cartCmdImpl = (CartCmdImpl) target;

        List<CartItemCmdImpl> cartItemCmdList = cartCmdImpl.getCartItemList();
        int length = cartItemCmdList.size();
        for (int i = 0; i < length; i++) {
            CartItemCmdImpl cartItemCmd = cartItemCmdList.get(i);

            if (null != cartItemCmd.getPreviouslyExchanged() && cartItemCmd.getPreviouslyExchanged()) {
                validatePreviouslyExchangedTicketFields(errors, cartItemCmd, i);
            }
        }
    }

    protected void validatePreviouslyExchangedTicketFields(Errors errors, CartItemCmdImpl cartItemCmdImpl, int index) {
        rejectIfPreviouslyExchangedTicketMandatoryFieldsEmpty(errors, index);
        ignoreMandatoryValidationForBusPassTravelCardTypeOfPreviouslyExchangedTicket(cartItemCmdImpl, errors, index);
        validateEndDateForOtherTravelCardTypePrevioslyExchangedTicket(cartItemCmdImpl, errors, index);
        rejectIfPreviouslyExchangedTicketSameAsOriginalTicket(cartItemCmdImpl, errors);
    }

    protected void rejectIfPreviouslyExchangedTicketMandatoryFieldsEmpty(Errors errors, int index) {
        rejectIfMandatoryFieldEmpty(errors, CARTITEMLIST + index + CLOSE_BRACE + DOT + FIELD_TRADED_TICKET + DOT + FIELD_EXCHANGE_DATE);
        rejectIfMandatoryFieldEmpty(errors, CARTITEMLIST + index + CLOSE_BRACE + DOT + FIELD_TRADED_TICKET + DOT + FIELD_TRAVEL_CARD_TYPE);
        rejectIfMandatoryFieldEmpty(errors, CARTITEMLIST + index + CLOSE_BRACE + DOT + FIELD_TRADED_TICKET + DOT + FIELD_PASSENGER_TYPE);
        rejectIfMandatoryFieldEmpty(errors, CARTITEMLIST + index + CLOSE_BRACE + DOT + FIELD_TRADED_TICKET + DOT + FIELD_DISCOUNT_TYPE);
        rejectIfMandatoryFieldEmpty(errors, CARTITEMLIST + index + CLOSE_BRACE + DOT + FIELD_TRADED_TICKET + DOT + FIELD_START_DATE);
    }

    protected void ignoreMandatoryValidationForBusPassTravelCardTypeOfPreviouslyExchangedTicket(CartItemCmdImpl cartItemCmdImpl, Errors errors,
                    int index) {
        if (cartItemCmdImpl.getTradedTicket() != null && StringUtils.isNotBlank(cartItemCmdImpl.getTicketType())
                        && cartItemCmdImpl.getTicketType().equals(TicketType.TRAVEL_CARD.code())
                        && StringUtils.isNotBlank(cartItemCmdImpl.getTradedTicket().getTravelCardType())
                        && !cartItemCmdImpl.getTradedTicket().getTravelCardType().endsWith(BUS_PASS)) {
            rejectIfMandatoryFieldEmpty(errors, CARTITEMLIST + index + CLOSE_BRACE + DOT + FIELD_TRADED_TICKET + DOT + FIELD_START_ZONE);
            rejectIfMandatoryFieldEmpty(errors, CARTITEMLIST + index + CLOSE_BRACE + DOT + FIELD_TRADED_TICKET + DOT + FIELD_END_ZONE);
        }
    }

    protected void validateEndDateForOtherTravelCardTypePrevioslyExchangedTicket(CartItemCmdImpl cartItemCmdImpl, Errors errors, int index) {
        if (null != cartItemCmdImpl && cartItemCmdImpl.getTradedTicket().getTravelCardType() != null
                        && cartItemCmdImpl.getTradedTicket().getTravelCardType().equals(CartAttribute.OTHER_TRAVEL_CARD)) {
            rejectIfMandatoryFieldEmpty(errors, CARTITEMLIST + index + CLOSE_BRACE + DOT + FIELD_TRADED_TICKET + DOT + FIELD_END_DATE);
        }
    }

    protected void rejectIfPreviouslyExchangedTicketSameAsOriginalTicket(CartItemCmdImpl cartItemCmdImpl, Errors errors) {
        if (cartItemCmdImpl.equals(cartItemCmdImpl.getTradedTicket())) {
            errors.rejectValue(PageCommandAttribute.FIELD_TRAVEL_CARD_IDENTICAL, ContentCode.FIELD_TRAVEL_CARD_IDENTICAL.errorCode(), null, null);
        }
    }

}
