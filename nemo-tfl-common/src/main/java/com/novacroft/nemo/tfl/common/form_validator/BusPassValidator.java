package com.novacroft.nemo.tfl.common.form_validator;

import static com.novacroft.nemo.tfl.common.constant.CartAttribute.ANNUAL_BUS_PASS;
import static com.novacroft.nemo.tfl.common.constant.ContentCode.ALREADY_ADDED;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_START_DATE;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.novacroft.nemo.common.validator.BaseValidator;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.command.BusPassCmd;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;

/**
 * Bus & Tram pass validation
 */
@Component("busPassValidator")
public class BusPassValidator extends BaseValidator {
    @Autowired
    protected CartService cartService;
    
    @Override
    public boolean supports(Class<?> targetClass) {
        return BusPassCmd.class.isAssignableFrom(targetClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
	CartItemCmdImpl cartItemCmd = (CartItemCmdImpl) target;
        
	if (cartItemCmd != null) {
            rejectIfBusPassAlreadyAddedToTheCart(cartItemCmd.getCartId(), errors);
            rejectIfStartDateValidationFails(errors);
            rejectIfEndDateValidationFails(errors);
        }
    }
    
    protected void rejectIfBusPassAlreadyAddedToTheCart(Long cartId, Errors errors) {
	for (ItemDTO itemDTO : getCartItemDTOsByCartId(cartId)) {
            if (isItemDTOAnAnnualBusPass(itemDTO)) {
                errors.reject(ALREADY_ADDED.errorCode());
                break;
            }
        }        
    }
    
    protected List<ItemDTO> getCartItemDTOsByCartId(Long cartId) {
	CartDTO cartDTO = getCartDTOByCartId(cartId);
	return cartDTO.getCartItems();
    }
    
    protected CartDTO getCartDTOByCartId(Long cartId) {
	return cartService.findById(cartId); 
    }
    
    protected boolean isItemDTOAnAnnualBusPass(ItemDTO itemDTO) {
	return itemDTO instanceof ProductItemDTO && itemDTO.getName() != null && itemDTO.getName().equalsIgnoreCase(ANNUAL_BUS_PASS);
    }
    
    protected void rejectIfStartDateValidationFails(Errors errors) {
	if (!errors.hasErrors()) {
	    rejectIfMandatoryFieldEmpty(errors, FIELD_START_DATE);
            
        }
    }
    
    protected void rejectIfEndDateValidationFails(Errors errors) {
	if (!errors.hasErrors() && !errors.hasFieldErrors(FIELD_START_DATE)) {
            rejectIfNotShortDate(errors, FIELD_START_DATE);
        }
    }
}
