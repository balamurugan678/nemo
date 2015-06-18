package com.novacroft.nemo.tfl.common.form_validator;

import static com.novacroft.nemo.tfl.common.constant.ContentCode.FAILED_CARD_PRODUCTS_DATE_OF_REFUND_IN_FUTURE;
import static com.novacroft.nemo.tfl.common.constant.ContentCode.FAILED_CARD_PRODUCTS_ONE_OF_PRODUCTS_EXPIRED_ON_DATE_OF_REFUND;
import static com.novacroft.nemo.tfl.common.constant.ContentCode.PAY_AS_YOU_GO_VALUE_MUST_BE_GREATER_THAN_ZERO;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_BACKDATED_REFUND_OTHER_REASON;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_BACKDATED_REFUND_REASON;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_CARD_SURRENDER_DATE;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_CART_ITEM_CMD;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_DATE_OF_LAST_USAGE_FOR_DECEASED_CUSTOMER;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_DATE_OF_REFUND;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_DISCOUNT_TYPE;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_END_DATE;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_END_ZONE;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_EXCHANGE_DATE;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_PASSENGER_TYPE;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_PAY_AS_YOU_GO_VALUE;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_START_DATE;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_START_ZONE;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_TRADED_TICKET;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_TRAVEL_CARD_TYPE;
import static com.novacroft.nemo.tfl.common.constant.Refund.BUS_PASS;
import static com.novacroft.nemo.tfl.common.constant.Refund.DOT;
import static com.novacroft.nemo.tfl.common.constant.RefundConstants.BACKDATED_OTHERREASON_VALUE;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.common.validator.BaseValidator;
import com.novacroft.nemo.tfl.common.application_service.cubic.GetCardService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.constant.CartAttribute;
import com.novacroft.nemo.tfl.common.constant.ContentCode;
import com.novacroft.nemo.tfl.common.constant.PageCommandAttribute;
import com.novacroft.nemo.tfl.common.constant.TicketType;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;
import com.novacroft.nemo.tfl.common.util.PayAsYouGoItemValidatorUtil;

/**
 * Refunds main validator
 */
@Component("refundCartValidator")
public class RefundCartValidator extends BaseValidator {
    protected static final String FIELD_PAY_AS_YOU_GO_CREDIT = "itemDTO[0].price";
    protected static final String FIELD_ADMIN_FEE = "itemDTO[1].price";
    protected static final int PAY_AS_YOU_GO_MAXIMUM_CHARACTER_LIMIT = 6;
    protected static final String AD_HOC_LOAD = "AdhocLoad";
    public static final int PAY_AS_YOU_GO_MINIMUM_AMOUNT=0;

    @Autowired
    protected GetCardService getCardService;
    @Autowired
    protected SystemParameterService systemParameterService;
    @Autowired
    protected RefundPayAsYouGoItemValidator refundPayAsYouGoItemValidator;
    @Autowired
    protected AdministrationFeeValidator administrationFeeValidator;

    @Override
    public boolean supports(Class<?> targetClass) {
        return CartCmdImpl.class.equals(targetClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CartCmdImpl cartCmdImpl = (CartCmdImpl) target;

        rejectIfMandatoryFieldsEmpty(errors, cartCmdImpl);
        if (!errors.hasErrors()) {
            rejectIfPayAsYouGoCreditFieldEmpty(errors, cartCmdImpl);
            rejectIfPayAsYouGoIsLessThanZero(errors, cartCmdImpl);
            if(null != cartCmdImpl.getAdministrationFeeValue()){
            	administrationFeeValidator.validate(cartCmdImpl, errors);
            }
            if (!errors.hasErrors()) {
                rejectIfDateOfRefundIsInFuture(errors, cartCmdImpl.getDateOfRefund());
                rejectIfAnyOneOfSelectedProductsExpiredOnDateOfRefund(errors, cartCmdImpl);
                refundPayAsYouGoItemValidator.validate(cartCmdImpl, errors);
            }
        }
    }

    protected void rejectIfMandatoryFieldsEmpty(Errors errors, CartCmdImpl cartCmdImpl) {
    	rejectIfBackDatedReasonFieldEmpty(errors, cartCmdImpl);
        rejectIfPayAsYouGoValueFieldEmpty(errors, cartCmdImpl);
        rejectIfDateOfRefundFieldEmpty(errors, cartCmdImpl);
        rejectIfCardSurrenderDateFieldEmpty(errors, cartCmdImpl);
        rejectIfDateOfLastUsageFieldEmpty(errors, cartCmdImpl);
    }

    protected void rejectIfPayAsYouGoIsLessThanZero( Errors errors, CartCmdImpl cmd) {
        if(cmd.getPayAsYouGoValue() < PAY_AS_YOU_GO_MINIMUM_AMOUNT) {
            errors.rejectValue(FIELD_PAY_AS_YOU_GO_VALUE, PAY_AS_YOU_GO_VALUE_MUST_BE_GREATER_THAN_ZERO.errorCode());
        }
    }

    protected void rejectIfPayAsYouGoValueFieldEmpty(Errors errors, CartCmdImpl cartCmdImpl) {
        rejectIfMandatoryFieldEmpty(errors, FIELD_PAY_AS_YOU_GO_VALUE);
    }

    protected void rejectIfDateOfRefundFieldEmpty(Errors errors, CartCmdImpl cartCmdImpl) {
        rejectIfMandatoryFieldEmpty(errors, FIELD_DATE_OF_REFUND);
    }

    protected void rejectIfBackDatedReasonFieldEmpty(Errors errors, CartCmdImpl cartCmdImpl) {
    	CartItemCmdImpl cartItemCmdImpl = cartCmdImpl.getCartItemCmd();
    	if((null != cartItemCmdImpl) && (cartItemCmdImpl.getBackdated())){
    		rejectIfMandatoryFieldEmpty(errors, FIELD_BACKDATED_REFUND_REASON);
    		rejectIfMandatoryFieldOtherReasonEmpty(cartCmdImpl, errors); 
    	}
    }
    
    protected void rejectIfMandatoryFieldOtherReasonEmpty(CartCmdImpl cmd, Errors errors) {
        if(null != cmd.getCartItemCmd().getBackdatedRefundReasonId() && BACKDATED_OTHERREASON_VALUE.equals(cmd.getCartItemCmd().getBackdatedRefundReasonId())){
            rejectIfMandatoryFieldEmpty(errors, FIELD_BACKDATED_REFUND_OTHER_REASON);
        }
    }
    
    protected void rejectIfPayAsYouGoCreditFieldEmpty(Errors errors, CartCmdImpl cartCmdImpl) {
        if (PayAsYouGoItemValidatorUtil.cartDTONotEmpty(cartCmdImpl) && PayAsYouGoItemValidatorUtil.payAsYouGoItemFieldAvailable(cartCmdImpl)) {
            rejectIfMandatoryFieldEmpty(errors, PayAsYouGoItemValidatorUtil.getPayAsYouGoItemField(cartCmdImpl));
        }
    }

    protected void rejectIfDateOfRefundIsInFuture(Errors errors, Date dateOfRefund) {
        if (DateUtil.isAfter(DateUtil.getMidnightDay(dateOfRefund), DateUtil.getMidnightDay(new Date()))) {
            errors.rejectValue(FIELD_DATE_OF_REFUND, FAILED_CARD_PRODUCTS_DATE_OF_REFUND_IN_FUTURE.errorCode());
        }
    }

    protected void rejectIfAnyOneOfSelectedProductsExpiredOnDateOfRefund(Errors errors, CartCmdImpl cartCmdImpl) {
        if (PayAsYouGoItemValidatorUtil.cartDTONotEmpty(cartCmdImpl) && isAnyOneOfSelectedProductsExpiredOnDateOfRefund(cartCmdImpl)) {
            errors.rejectValue(FIELD_DATE_OF_REFUND, FAILED_CARD_PRODUCTS_ONE_OF_PRODUCTS_EXPIRED_ON_DATE_OF_REFUND.errorCode());
        }
    }

    protected boolean isAnyOneOfSelectedProductsExpiredOnDateOfRefund(CartCmdImpl cartCmdImpl) {
        for (ItemDTO itemDTO : cartCmdImpl.getCartDTO().getCartItems()) {
            if (isItemDTOAProductItemDTO(itemDTO) && isItemDTOExpiredOnDateOfRefund(itemDTO)) {
                return true;
            }
        }
        return false;
    }

    protected boolean isItemDTOAProductItemDTO(ItemDTO itemDTO) {
        return itemDTO instanceof ProductItemDTO;
    }

    protected boolean isItemDTOExpiredOnDateOfRefund(ItemDTO itemDTO) {
        ProductItemDTO productItemDTO = (ProductItemDTO) itemDTO;
        return DateUtil.isBefore(productItemDTO.getEndDate(), productItemDTO.getDateOfRefund());
    }
    
    protected void validatePreviouslyExchangedTicketFields(Errors errors, CartCmdImpl cmd) {
        if (null != cmd.getCartItemCmd() && null != cmd.getCartItemCmd().getPreviouslyExchanged() && cmd.getCartItemCmd().getPreviouslyExchanged()) {
            
            rejectIfPreviouslyExchangedTicketMandatoryFieldsEmpty(errors);
            ignoreMandatoryValidationForBusPassTravelCardTypeOfPreviouslyExchangedTicket(cmd, errors);
            validateEndDateForOtherTravelCardTypePreviouslyExchangedTicket(cmd, errors);
            rejectIfPreviouslyExchangedTicketSameAsOriginalTicket(cmd, errors);
        }
    }
    
    protected void rejectIfPreviouslyExchangedTicketMandatoryFieldsEmpty(Errors errors) {
        rejectIfMandatoryFieldEmpty(errors, getCompleteFieldStringForTradedTicket(FIELD_EXCHANGE_DATE));
        rejectIfMandatoryFieldEmpty(errors, getCompleteFieldStringForTradedTicket(FIELD_TRAVEL_CARD_TYPE));
        rejectIfMandatoryFieldEmpty(errors, getCompleteFieldStringForTradedTicket(FIELD_PASSENGER_TYPE));
        rejectIfMandatoryFieldEmpty(errors, getCompleteFieldStringForTradedTicket(FIELD_DISCOUNT_TYPE));
        rejectIfMandatoryFieldEmpty(errors, getCompleteFieldStringForTradedTicket(FIELD_START_DATE));
    }
    protected void ignoreMandatoryValidationForBusPassTravelCardTypeOfPreviouslyExchangedTicket(CartCmdImpl cmd, Errors errors) {
        if (cmd.getCartItemCmd().getTradedTicket() != null && StringUtils.isNotBlank(cmd.getCartItemCmd().getTicketType())
                        && cmd.getCartItemCmd().getTicketType().equals(TicketType.TRAVEL_CARD.code())
                        && StringUtils.isNotBlank(cmd.getCartItemCmd().getTradedTicket().getTravelCardType())
                        && !cmd.getCartItemCmd().getTradedTicket().getTravelCardType().endsWith(BUS_PASS)) {
            rejectIfMandatoryFieldEmpty(errors, getCompleteFieldStringForTradedTicket(FIELD_START_ZONE));
            rejectIfMandatoryFieldEmpty(errors, getCompleteFieldStringForTradedTicket(FIELD_END_ZONE));
        }
    }

    protected void validateEndDateForOtherTravelCardTypePreviouslyExchangedTicket(CartCmdImpl cmd, Errors errors) {
        if (null != cmd.getCartItemCmd() && cmd.getCartItemCmd().getTradedTicket().getTravelCardType() != null
                        && cmd.getCartItemCmd().getTradedTicket().getTravelCardType().equals(CartAttribute.OTHER_TRAVEL_CARD)) {
            rejectIfMandatoryFieldEmpty(errors, getCompleteFieldStringForTradedTicket(FIELD_END_DATE));
        }
    }

    protected String getCompleteFieldStringForTradedTicket(String field) {
        return FIELD_CART_ITEM_CMD + DOT + FIELD_TRADED_TICKET + DOT + field;
    }

    protected void rejectIfPreviouslyExchangedTicketSameAsOriginalTicket(CartCmdImpl cmd, Errors errors) {
        if (cmd.getCartItemCmd().equals(cmd.getCartItemCmd().getTradedTicket())) {
            errors.rejectValue(PageCommandAttribute.FIELD_TRAVEL_CARD_IDENTICAL, ContentCode.FIELD_TRAVEL_CARD_IDENTICAL.errorCode(), null, null);
        }
    }
    
    protected void rejectIfDateOfLastUsageFieldEmpty(Errors errors, CartCmdImpl cartCmdImpl) {
        CartItemCmdImpl cartItemCmdImpl = cartCmdImpl.getCartItemCmd();
        if((null != cartItemCmdImpl) && (cartItemCmdImpl.getDeceasedCustomer())){
            rejectIfMandatoryFieldEmpty(errors, FIELD_DATE_OF_LAST_USAGE_FOR_DECEASED_CUSTOMER);
        }
    }
 
    protected void rejectIfCardSurrenderDateFieldEmpty(Errors errors, CartCmdImpl cartCmdImpl){
        CartItemCmdImpl cartItemCmdImpl = cartCmdImpl.getCartItemCmd();
        if((null != cartItemCmdImpl) && (cartItemCmdImpl.getBackdated())){
            rejectIfMandatoryFieldEmpty(errors, FIELD_CARD_SURRENDER_DATE);
        }
    }
}
