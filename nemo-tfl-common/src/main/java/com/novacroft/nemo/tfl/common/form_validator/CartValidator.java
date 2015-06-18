package com.novacroft.nemo.tfl.common.form_validator;

import static com.novacroft.nemo.tfl.common.constant.CartAttribute.ANNUAL_BUS_PASS;
import static com.novacroft.nemo.tfl.common.constant.ContentCode.ALREADY_ADDED;
import static com.novacroft.nemo.tfl.common.constant.TicketType.BUS_PASS;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;

/**
 * Cart validation
 */
@Component("cartValidator")
public class CartValidator extends UserCartValidator {

    @Override
    public boolean supports(Class<?> targetClass) {
        return CartItemCmdImpl.class.equals(targetClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CartItemCmdImpl cartItemCmd = (CartItemCmdImpl) target;
        if (cartItemCmd != null) {
            validateCart(cartItemCmd, errors);
        }
    }

    protected void validateCart(CartItemCmdImpl cartItemCmd, Errors errors) {
        CartDTO cartDTO = getCartDTOByCartId(cartItemCmd.getCartId());

        if (isCartItemCmdABusPass(cartItemCmd)) {
            rejectIfBusPassAlreadyAddedToTheCart(cartDTO, errors);
        } else {
            rejectIfProductItemDTOsInCartDTOExceedMaximumAllowedTravelCardsLimit(cartDTO, cartItemCmd.getCartId(), errors);

            if (!errors.hasErrors()) {
                rejectIfZonesInCartItemCmdImplOverlapWithAlreadyAddedZonesInProductItemDTOsOfCartDTO(cartDTO, cartItemCmd, errors);
            }
        }
    }

    protected boolean isCartItemCmdABusPass(CartItemCmdImpl cartItemCmd) {
        return cartItemCmd.getTicketType().equalsIgnoreCase(BUS_PASS.code());
    }

    protected void rejectIfBusPassAlreadyAddedToTheCart(CartDTO cartDTO, Errors errors) {
        for (ItemDTO itemDTO : cartDTO.getCartItems()) {
            if (null != itemDTO.getName() && itemDTO.getName().equalsIgnoreCase(ANNUAL_BUS_PASS)) {
                errors.reject(ALREADY_ADDED.errorCode());
                break;
            }
        }
    }

}
