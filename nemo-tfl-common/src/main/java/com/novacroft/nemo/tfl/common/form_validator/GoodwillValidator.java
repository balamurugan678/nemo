package com.novacroft.nemo.tfl.common.form_validator;

import static com.novacroft.nemo.tfl.common.constant.ContentCode.GOODWILL_MUST_BE_GREATER_THAN_ZERO;
import static com.novacroft.nemo.tfl.common.constant.ContentCode.GOODWILL_MUST_BE_LESS_THAN_MAX_REFUND_ALLOWED;
import static com.novacroft.nemo.tfl.common.constant.ContentCode.MULTIPLE_GOODWILL_PAYMENT_FOR_SAME_REASON;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_CART_ITEM_CMD;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_GOODWILL_PAYMENT_ID;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_GOODWILL_PRICE;
import static com.novacroft.nemo.tfl.common.constant.Refund.CART_ITEM_CMD_GOODWILL_PAYMENT_ID;
import static com.novacroft.nemo.tfl.common.constant.Refund.CART_ITEM_CMD_GOODWILL_PRICE;
import static com.novacroft.nemo.tfl.common.constant.Refund.DOT;
import static com.novacroft.nemo.tfl.common.constant.Refund.GOODWILL_MINIMUM_AMOUNT;
import static com.novacroft.nemo.tfl.common.constant.Refund.GOODWILL_REFUND_REASON_OTHER;
import static com.novacroft.nemo.tfl.common.constant.SystemParameterCode.GOODWILL_ANONYMOUS_REFUND_UPPER_LIMIT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.transfer.SelectListDTO;
import com.novacroft.nemo.common.transfer.SelectListOptionDTO;
import com.novacroft.nemo.common.utils.CurrencyUtil;
import com.novacroft.nemo.common.validator.BaseValidator;
import com.novacroft.nemo.tfl.common.application_service.GoodwillService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.constant.CartType;
import com.novacroft.nemo.tfl.common.constant.GoodwillReasonType;
import com.novacroft.nemo.tfl.common.transfer.GoodwillPaymentItemDTO;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;

/**
 * Failed Card Products validation
 */
@Component("goodwillValidator")
public class GoodwillValidator extends BaseValidator {
	@Autowired
    protected GoodwillService goodwillService;

	@Autowired
	protected SystemParameterService systemParameterService;
	
    @Override
    public boolean supports(Class<?> targetClass) {
        return CartCmdImpl.class.equals(targetClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CartCmdImpl cmd = (CartCmdImpl) target;

        rejectIfMandatoryFieldEmpty(errors, CART_ITEM_CMD_GOODWILL_PRICE);
        if (!errors.hasErrors()) {
            rejectIfGoodwillAmountIsLessThanOrEqualToZero(cmd, errors);
            rejectIfGoodwillAmountIsGreaterThanMaxAllowed(cmd, errors);
        }
        rejectIfMandatoryFieldEmpty(errors, CART_ITEM_CMD_GOODWILL_PAYMENT_ID);
        if (!errors.hasErrors()) {
            rejectIfMultipleGoodwillPaymentForSameReason(cmd, errors);
            rejectIfOtherGoodwillReasonOtherTextEmpty(cmd, errors);
        }
    }
    
    protected void rejectIfMultipleGoodwillPaymentForSameReason(CartCmdImpl cmd, Errors errors) {
        boolean multipleGoodwillPaymentForSameReasonFound = false;
        
        multipleGoodwillPaymentForSameReasonFound = rejectIfMultipleGoodwillPaymentForSameReasonFromCartDTO(cmd);
        
        if (multipleGoodwillPaymentForSameReasonFound) {
            errors.rejectValue(getFieldNameWithCartItemCmd(FIELD_GOODWILL_PAYMENT_ID), MULTIPLE_GOODWILL_PAYMENT_FOR_SAME_REASON.errorCode());
        }
    }
    
    protected boolean rejectIfMultipleGoodwillPaymentForSameReasonFromCartDTO(CartCmdImpl cmd) {
	if (cmd.getCartDTO() != null && cmd.getCartDTO().getCartItems() != null) {
    	    for (ItemDTO itemDTO : cmd.getCartDTO().getCartItems()) {
    		if (isGoodwillPaymentIdInItemDTO(itemDTO, cmd.getCartItemCmd().getGoodwillPaymentId())) {
    		    return true;
    		} 
            }
	}
	return false;
    }
    
    protected boolean isGoodwillPaymentIdInItemDTO(ItemDTO itemDTO, Long goodwillPaymentId) {
	if (itemDTO instanceof GoodwillPaymentItemDTO) {
	    GoodwillPaymentItemDTO goodwillPaymentItemDTO = (GoodwillPaymentItemDTO) itemDTO;
	    if (goodwillPaymentId == goodwillPaymentItemDTO.getGoodwillReasonDTO().getReasonId()) {
		return true;
	    }
	}
	return false;
    }
    
    protected void rejectIfOtherGoodwillReasonOtherTextEmpty(CartCmdImpl cmd, Errors errors) {
    	CartItemCmdImpl cartItemCmd = cmd.getCartItemCmd();
    	
    	if (isGoodwillRefundTypeOther(cartItemCmd.getGoodwillPaymentId(), cmd.getCartType())) {
            rejectIfMandatoryFieldEmptyUsingAn(errors, CART_ITEM_CMD_GOODWILL_PAYMENT_ID);
    	}
    }
    
    protected boolean isGoodwillRefundTypeOther(Long goodwillPaymentId, String cartType) {
    	SelectListDTO selectListDTO = getGoodwillRefundTypes(cartType);
    	
    	for (SelectListOptionDTO selectListOptionDTO : selectListDTO.getOptions()) {
    		if (String.valueOf(goodwillPaymentId).equals(selectListOptionDTO.getValue()) && selectListOptionDTO.getMeaning().equals(GOODWILL_REFUND_REASON_OTHER)) {
    			return true;
    		}
    	}
    	return false;
    }
    
    protected SelectListDTO getGoodwillRefundTypes(String cartType) {
    	String goodwillReasonType = getGoodwillReasonTypeFromCartType(cartType);
    	return goodwillService.getGoodwillRefundTypes(goodwillReasonType);
    }
    
    protected String getGoodwillReasonTypeFromCartType(String cartType) {
        return isStandaloneGoodwillRefundCartType(cartType) ? GoodwillReasonType.CONTACTLESS_PAYMENT_CARD.code() : GoodwillReasonType.OYSTER
                .code();
    }
    
    protected boolean isStandaloneGoodwillRefundCartType(String cartType) {
        return cartType.equalsIgnoreCase(CartType.STANDALONE_GOODWILL_REFUND.code());
    }

    

    
    protected void rejectIfGoodwillAmountIsLessThanOrEqualToZero(CartCmdImpl cmd, Errors errors) {
        CartItemCmdImpl cartItemCmd = cmd.getCartItemCmd();
        if(cartItemCmd.getGoodwillPrice() <= GOODWILL_MINIMUM_AMOUNT) {
            errors.rejectValue(getFieldNameWithCartItemCmd(FIELD_GOODWILL_PRICE), GOODWILL_MUST_BE_GREATER_THAN_ZERO.errorCode());
        }
    }
    
    protected String getFieldNameWithCartItemCmd(String fieldName) {
        return FIELD_CART_ITEM_CMD + DOT + fieldName;
    }
    

    protected void rejectIfGoodwillAmountIsGreaterThanMaxAllowed(CartCmdImpl cmd, Errors errors) {
        CartItemCmdImpl cartItemCmd = cmd.getCartItemCmd();
        Integer maxRefund = systemParameterService.getIntegerParameterValue(GOODWILL_ANONYMOUS_REFUND_UPPER_LIMIT.code());
        if (cartItemCmd.getGoodwillPrice() > maxRefund) {
            errors.rejectValue(getFieldNameWithCartItemCmd(FIELD_GOODWILL_PRICE), GOODWILL_MUST_BE_LESS_THAN_MAX_REFUND_ALLOWED.errorCode(),
                            new String[] { CurrencyUtil.formatPenceWithoutCurrencySymbol(maxRefund) }, null);
        }
    }
}
