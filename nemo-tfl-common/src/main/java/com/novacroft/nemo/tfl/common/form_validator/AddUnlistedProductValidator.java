package com.novacroft.nemo.tfl.common.form_validator;

import static com.novacroft.nemo.tfl.common.constant.CartAttribute.MAXIMUM_ALLOWED_TRAVEL_CARDS;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_CART_ITEM_CMD;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_DISCOUNT_TYPE;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_END_DATE;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_END_ZONE;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_PASSENGER_TYPE;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_START_DATE;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_START_ZONE;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_TRAVEL_CARD_TYPE;
import static com.novacroft.nemo.tfl.common.constant.Refund.BUS_PASS;
import static com.novacroft.nemo.tfl.common.constant.Refund.DOT;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.constant.Durations;
import com.novacroft.nemo.tfl.common.application_service.AddUnlistedProductService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.constant.ContentCode;
import com.novacroft.nemo.tfl.common.constant.PageCommandAttribute;
import com.novacroft.nemo.tfl.common.constant.ProductItemType;
import com.novacroft.nemo.tfl.common.constant.Refund;
import com.novacroft.nemo.tfl.common.constant.TicketType;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;


/**
 * Validator for Add Unlisted Products in Failed Cart Refund Page
 */
@Component("addUnlistedProductValidator")
public class AddUnlistedProductValidator extends RefundCartValidator {

    @Autowired
    protected SystemParameterService systemParameterService;

    @Autowired
    protected ZoneMappingRefundValidator zoneMappingRefundValidator;

    @Autowired
    protected TravelCardRefundValidator travelCardRefundValidator;

    @Autowired
    protected AddUnlistedProductService addUnlistedProductService;
    

    @Override
    public boolean supports(Class<?> targetClass) {
	return CartDTO.class.equals(targetClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CartCmdImpl cmd = (CartCmdImpl) target;

        rejectIfTravelCardBusPassCountIsGreaterThanMaximumAllowedTravelCards(errors, cmd.getCartDTO().getCartItems());
        rejectIfMandatoryFieldsEmpty(errors, cmd);
        rejectIfZoneMappingValidatorHasErrors(errors, cmd);
        rejectIfTravelCardValidatorHasErrors(errors, cmd);
        validatePreviouslyExchangedTicketFields(errors, cmd);
    }
    
    protected void rejectIfTravelCardBusPassCountIsGreaterThanMaximumAllowedTravelCards(Errors errors, List<ItemDTO> itemDTOs) {
	if (getTravelCardBusPassCount(itemDTOs) >= systemParameterService.getIntegerParameterValue(MAXIMUM_ALLOWED_TRAVEL_CARDS)) {
            errors.rejectValue(PageCommandAttribute.FIELD_TRAVEL_CARD_REFUND_LIMIT,
                               ContentCode.TRAVEL_CARD_REFUND_LIMIT_REACHED.errorCode(),
                               new String[]{systemParameterService.getIntegerParameterValue(MAXIMUM_ALLOWED_TRAVEL_CARDS).toString()}, null);
        }
    }
    
    @Override
    protected void rejectIfMandatoryFieldsEmpty(Errors errors, CartCmdImpl cmd) {
	if (!errors.hasErrors()) {
	    rejectIfMandatoryFieldEmpty(errors, FIELD_CART_ITEM_CMD + DOT + FIELD_TRAVEL_CARD_TYPE);
            rejectIfMandatoryFieldEmpty(errors, FIELD_CART_ITEM_CMD + DOT + FIELD_PASSENGER_TYPE);
            rejectIfMandatoryFieldEmpty(errors, FIELD_CART_ITEM_CMD + DOT + FIELD_DISCOUNT_TYPE);
            rejectIfMandatoryFieldEmpty(errors, FIELD_CART_ITEM_CMD + DOT + FIELD_START_DATE);
            rejectIfStartZoneEmpty(errors, cmd);
            rejectIfEndZoneEmpty(errors, cmd);
            rejectIfEndDateEmpty(errors, cmd);
	}
    }
    
    protected void rejectIfZoneMappingValidatorHasErrors(Errors errors, CartCmdImpl cmd) {
	if (!errors.hasErrors()) { 
	    addUnlistedProductService.setTicketType(cmd.getCartItemCmd());
	    rejectIfZoneMappingRefundValidatorHasErrors(errors, cmd);
	}
   }
    
    protected void rejectIfZoneMappingRefundValidatorHasErrors(Errors errors, CartCmdImpl cmd) {
	if (cmd.getCartItemCmd().getTicketType() != null && 
	                cmd.getCartItemCmd().getTicketType().equalsIgnoreCase(ProductItemType.TRAVEL_CARD.databaseCode())) {
   	     zoneMappingRefundValidator.validate(cmd, errors);
   	 }
    }
   
   protected void rejectIfTravelCardValidatorHasErrors(Errors errors, CartCmdImpl cmd) {
	 if (!errors.hasErrors()) {
	     travelCardRefundValidator.validate(cmd, errors);
	 }
   }
    
    protected void rejectIfStartZoneEmpty(Errors errors, CartCmdImpl cmd) {
	if (cmd.getCartItemCmd().getTicketType() != null && 
	                cmd.getCartItemCmd().getTicketType().equals(TicketType.TRAVEL_CARD.code()) && 
	                cmd.getCartItemCmd().getTravelCardType() != null
                && !cmd.getCartItemCmd().getTravelCardType().contains(BUS_PASS)) {
            rejectIfMandatoryFieldEmpty(errors, FIELD_CART_ITEM_CMD + DOT + FIELD_START_ZONE);
        }
    }
    
    protected void rejectIfEndZoneEmpty(Errors errors, CartCmdImpl cmd) {
	if (cmd.getCartItemCmd().getTicketType() != null && 
	                cmd.getCartItemCmd().getTicketType().equals(TicketType.TRAVEL_CARD.code()) && 
	                cmd.getCartItemCmd().getTravelCardType() != null
                && !cmd.getCartItemCmd().getTravelCardType().contains(BUS_PASS)) {
	    rejectIfMandatoryFieldEmpty(errors, FIELD_CART_ITEM_CMD + DOT + FIELD_END_ZONE);
        }
    }
    
    protected void rejectIfEndDateEmpty(Errors errors, CartCmdImpl cmd) {
	if (cmd.getCartItemCmd().getTravelCardType() != null && 
	                cmd.getCartItemCmd().getTravelCardType().contains(Durations.OTHER.getDurationType())) {
            rejectIfMandatoryFieldEmpty(errors, FIELD_CART_ITEM_CMD + DOT + FIELD_END_DATE);
        }
    }

    protected int getTravelCardBusPassCount(List<ItemDTO> itemDTOs) {
	int travelCardBusPassCount = 0;
	for (ItemDTO itemDTO : itemDTOs) {
            if (itemDTO.getName() != null && 
                            (itemDTO.getName().contains(Refund.BUS_PASS) || 
                                            itemDTO.getName().contains(Refund.TRAVELCARD))) {
                travelCardBusPassCount++;
            }
        }
	return travelCardBusPassCount;
    }
    
}
