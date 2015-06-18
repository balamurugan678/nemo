package com.novacroft.nemo.tfl.common.form_validator;

import static com.novacroft.nemo.tfl.common.constant.ContentCode.CANNOT_REFUND_TOTAL_AMOUNT_ZERO;
import static com.novacroft.nemo.tfl.common.constant.ContentCode.SELECTED_STATION_DIFFERENT_FROM_PENDINGITEM_STATION;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_CART_DTO_CART_REFUND_TOTAL;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_PAYMENT_TYPE;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_STATION_ID;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.novacroft.nemo.common.validator.BaseValidator;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.util.CartUtil;

/**
 * Refund Payment validation
 */
@Component("refundPaymentValidator")
public class RefundPaymentValidator extends BaseValidator {
    public static final Integer ZERO_TOTAL_AMOUNT = 0;

    @Override
    public boolean supports(Class<?> targetClass) {
        return CartDTO.class.equals(targetClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CartDTO cartDTO = (CartDTO) target;
        rejectIfTotalAmountIsZeroOrLess(cartDTO, errors);
        if (!errors.hasErrors()) {
            rejectIfMandatoryFieldEmpty(errors, FIELD_PAYMENT_TYPE);
            if (cartDTO.isPpvPickupLocationAddFlag()) {
                rejectIfPickUpStationIsDifferentFromPendingItemLocation(cartDTO, errors);
            }
        }
    }

    public void rejectIfTotalAmountIsZeroOrLess(CartDTO cartDTO, Errors errors) {
        boolean isCartFailedOrDestroyed = CartUtil.isDestroyedOrFaildCartType(cartDTO.getCartType());
        if (isCartFailedOrDestroyed && (cartDTO.getCartRefundTotal() + cartDTO.getCardRefundableDepositAmount()) == ZERO_TOTAL_AMOUNT) {
            errors.rejectValue(FIELD_CART_DTO_CART_REFUND_TOTAL, CANNOT_REFUND_TOTAL_AMOUNT_ZERO.errorCode());
            return;
        }

        if (!isCartFailedOrDestroyed && cartDTO.getCartRefundTotal() == ZERO_TOTAL_AMOUNT) {
            errors.rejectValue(FIELD_CART_DTO_CART_REFUND_TOTAL, CANNOT_REFUND_TOTAL_AMOUNT_ZERO.errorCode());
        }
    }

    public void rejectIfPickUpStationIsDifferentFromPendingItemLocation(CartDTO cartDTO, Errors errors) {
        if (cartDTO.isPpvPickupLocationAddFlag()) {
            errors.rejectValue(FIELD_STATION_ID, SELECTED_STATION_DIFFERENT_FROM_PENDINGITEM_STATION.errorCode(),
                            new String[] { cartDTO.getPpvPickupLocationName() }, null);
        }
    }
}
