package com.novacroft.nemo.tfl.common.form_validator;

import static com.novacroft.nemo.tfl.common.constant.CartAttribute.MAXIMUM_ALLOWED_TRAVEL_CARDS;
import static com.novacroft.nemo.tfl.common.constant.ContentCode.MAGNETIC_TICKET_NUMBER_INVALID_FORMAT;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_DATE_OF_LAST_USAGE_FOR_DECEASED_CUSTOMER;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_MAGNETIC_TICKET_NUMBER;
import static com.novacroft.nemo.tfl.common.constant.Refund.BUS_PASS;
import static com.novacroft.nemo.tfl.common.constant.RefundConstants.BACKDATED_OTHERREASON_VALUE;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.constant.Durations;
import com.novacroft.nemo.tfl.common.application_service.AddUnlistedProductService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.constant.ContentCode;
import com.novacroft.nemo.tfl.common.constant.PageCommandAttribute;
import com.novacroft.nemo.tfl.common.constant.TicketType;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;

/**
 * Validator for Cancel and Surrender Cards controller
 */
@Component("cancelAndSurrenderValidator")
public class CancelAndSurrenderValidator extends RefundCartValidator {

    private static final String MAGNETIC_TICKET_FORMAT_PATTERN = "^[A-Za-z]?[A-Za-z]{1}\\d{6}$";
    private final Pattern pattern = Pattern.compile(MAGNETIC_TICKET_FORMAT_PATTERN);

    @Autowired
    protected ZoneMappingRefundValidator zoneMappingRefundValidator;

    @Autowired
    protected TravelCardRefundValidator travelCardRefundValidator;

    @Autowired
    protected AddUnlistedProductService addUnlistedProductService;

    @Autowired
    protected SystemParameterService systemParameterService;

    @Override
    public boolean supports(Class<?> targetClass) {
        return CartCmdImpl.class.equals(targetClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CartCmdImpl cmd = (CartCmdImpl) target;

        rejectIfTravelCardBusPassCountOverLimit(cmd, errors);
        rejectIfIncomingItemIsDuplicate(cmd, errors);
        rejectIfMandatoryFieldsEmpty(errors);
        rejectIfMagneticTicketNumberFormatIsInvalid(cmd, errors);
        rejectIfZonesEmptyForTravelcardOrBusPass(cmd, errors);
        rejectIfEndDateEmptyForOtherTravelcard(cmd, errors);
        rejectIfMandatoryFieldsIfBackdated(cmd, errors);
        validatePreviouslyExchangedTicketFields(errors, cmd);
        rejectIfNotValidZoneMapping(cmd, errors);
        rejectIfNotValidTravelCard(cmd, errors);
        rejectIfMandatoryFieldDateOfLastUsageIsEmpty(cmd, errors);
    }

    protected void rejectIfTravelCardBusPassCountOverLimit(CartCmdImpl cmd, Errors errors) {
        int travelCardBusPassCount = 0;

        for (ItemDTO cartItem : cmd.getCartDTO().getCartItems()) {
            if (cartItem instanceof ProductItemDTO) {
                travelCardBusPassCount++;
            }
        }
        
        if (travelCardBusPassCount >= systemParameterService.getIntegerParameterValue(MAXIMUM_ALLOWED_TRAVEL_CARDS)) {
            errors.rejectValue(PageCommandAttribute.FIELD_TRAVEL_CARD_REFUND_LIMIT, ContentCode.TRAVEL_CARD_REFUND_LIMIT_REACHED.errorCode(),
                            new String[] { systemParameterService.getIntegerParameterValue(MAXIMUM_ALLOWED_TRAVEL_CARDS).toString() }, null);
        }
    }

    protected void rejectIfIncomingItemIsDuplicate(CartCmdImpl cmd, Errors errors) {
        if (addUnlistedProductService.isCartItemDuplicate(cmd.getCartDTO().getCartItems(), cmd.getCartItemCmd())) {
            errors.rejectValue(PageCommandAttribute.FIELD_TRAVEL_CARD_IDENTICAL, ContentCode.FIELD_TRAVEL_CARD_IDENTICAL.errorCode(), null, null);
        }
    }

    protected void rejectIfMandatoryFieldsEmpty(Errors errors) {
        rejectIfMandatoryFieldEmpty(errors, "cartItemCmd.travelCardType");
        rejectIfMandatoryFieldEmpty(errors, "cartItemCmd.passengerType");
        rejectIfMandatoryFieldEmpty(errors, "cartItemCmd.discountType");
        rejectIfMandatoryFieldEmpty(errors, "cartItemCmd.startDate");
    }

    protected void rejectIfMagneticTicketNumberFormatIsInvalid(CartCmdImpl cmd, Errors errors) {
        if (cmd.getCartItemCmd() != null && !StringUtils.isBlank(cmd.getCartItemCmd().getMagneticTicketNumber())) {
            Matcher matcher = pattern.matcher(cmd.getCartItemCmd().getMagneticTicketNumber());
            if (!matcher.find()) {
                errors.rejectValue("cartItemCmd." + FIELD_MAGNETIC_TICKET_NUMBER, MAGNETIC_TICKET_NUMBER_INVALID_FORMAT.errorCode());
            }
        }
    }

    protected void rejectIfZonesEmptyForTravelcardOrBusPass(CartCmdImpl cmd, Errors errors) {
        if (cmd.getCartItemCmd() != null && TicketType.TRAVEL_CARD.code().equalsIgnoreCase(cmd.getCartItemCmd().getTicketType())
                        && !StringUtils.endsWith(cmd.getCartItemCmd().getTravelCardType(), BUS_PASS)) {
            rejectIfMandatoryFieldEmpty(errors, "cartItemCmd.startZone");
            rejectIfMandatoryFieldEmpty(errors, "cartItemCmd.endZone");
        }
    }

    protected void rejectIfEndDateEmptyForOtherTravelcard(CartCmdImpl cmd, Errors errors) {
        if (cmd.getCartItemCmd() != null && StringUtils.equals(cmd.getCartItemCmd().getTravelCardType(), Durations.OTHER.getDurationType())) {
            rejectIfMandatoryFieldEmpty(errors, "cartItemCmd.endDate");
        }
    }

    protected void rejectIfMandatoryFieldsIfBackdated(CartCmdImpl cmd, Errors errors) {
        if (null != cmd.getCartItemCmd() && null != cmd.getCartItemCmd().getBackdated() && cmd.getCartItemCmd().getBackdated()) {
            rejectIfMandatoryFieldEmpty(errors, "cartItemCmd.backdatedRefundReasonId");
            rejectIfMandatoryFieldEmpty(errors, "cartItemCmd.dateOfCanceAndSurrender");
            rejectIfMandatoryFieldOtherReasonEmpty(cmd, errors); 
        }
    }


    protected void rejectIfMandatoryFieldOtherReasonEmpty(CartCmdImpl cmd, Errors errors) {
        if(null != cmd.getCartItemCmd().getBackdatedRefundReasonId() && BACKDATED_OTHERREASON_VALUE.equals(cmd.getCartItemCmd().getBackdatedRefundReasonId())){
            rejectIfMandatoryFieldEmpty(errors, "cartItemCmd.backdatedOtherReason");
        }
    }

    protected void rejectIfNotValidZoneMapping(CartCmdImpl cmd, Errors errors) {
        if (!errors.hasErrors() && null != cmd.getCartItemCmd() && 
                        StringUtils.equalsIgnoreCase(cmd.getCartItemCmd().getTicketType(), TicketType.TRAVEL_CARD.code())) {
            zoneMappingRefundValidator.validate(cmd, errors);
        }
    }

    protected void rejectIfNotValidTravelCard(CartCmdImpl cmd, Errors errors) {
        if (null != cmd.getCartItemCmd() && !errors.hasErrors()) {
            travelCardRefundValidator.validate(cmd, errors);
        }
    }
    
    protected void rejectIfMandatoryFieldDateOfLastUsageIsEmpty(CartCmdImpl cmd, Errors errors){
        if(null != cmd.getCartItemCmd() && cmd.getCartItemCmd().getDeceasedCustomer()) {
            rejectIfMandatoryFieldEmpty(errors, FIELD_DATE_OF_LAST_USAGE_FOR_DECEASED_CUSTOMER);
        }
    }
}
