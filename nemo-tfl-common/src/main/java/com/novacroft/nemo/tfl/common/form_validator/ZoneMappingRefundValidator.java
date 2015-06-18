package com.novacroft.nemo.tfl.common.form_validator;

import static com.novacroft.nemo.tfl.common.constant.CartAttribute.OTHER_TRAVELCARD_SUBSTITUTION_TRAVELCARD_TYPE;
import static com.novacroft.nemo.tfl.common.constant.ContentCode.INVALID_SINGLE_ZONE_SELECTION;
import static com.novacroft.nemo.tfl.common.constant.ContentCode.ZONE_MAPPING_NOT_EXIST;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_CART_ITEM_CMD;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_END_ZONE;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_START_ZONE;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_TRADED_TICKET;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_TRAVEL_CARD_TYPE;
import static com.novacroft.nemo.tfl.common.constant.Refund.DOT;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.constant.Durations;
import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.common.validator.BaseValidator;
import com.novacroft.nemo.tfl.common.application_service.AddUnlistedProductService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.constant.CartAttribute;
import com.novacroft.nemo.tfl.common.constant.CubicConstant;
import com.novacroft.nemo.tfl.common.data_service.AlternativeZoneMappingDataService;
import com.novacroft.nemo.tfl.common.data_service.ProductDataService;
import com.novacroft.nemo.tfl.common.transfer.AlternativeZoneMappingDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductDTO;

/**
 * Zone Mapping validation for refund functionality
 */
@Component("zoneMappingRefundValidator")
public class ZoneMappingRefundValidator extends BaseValidator {

    @Autowired
    protected AlternativeZoneMappingDataService alternativeZoneMappingDataService;
    @Autowired
    protected SystemParameterService systemParameterService;
    @Autowired
    protected ProductDataService productDataService;
    @Autowired
    protected AddUnlistedProductService addUnlistedProductService;

    @Override
    public boolean supports(Class<?> targetClass) {
        return CartCmdImpl.class.equals(targetClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CartCmdImpl cmd = (CartCmdImpl) target;

        validateMandatoryFields(errors);
        if (fieldsHaveNoErrors(errors, FIELD_START_ZONE, FIELD_END_ZONE)) {
            validatNonMandatoryFields(cmd, errors);
        }
    }

    protected void validateMandatoryFields(Errors errors) {
        rejectIfMandatoryFieldEmpty(errors, getFieldNameWithCartItemCmd(FIELD_START_ZONE));
        rejectIfMandatoryFieldEmpty(errors, getFieldNameWithCartItemCmd(FIELD_END_ZONE));
    }

    protected boolean fieldsHaveNoErrors(Errors errors, String field1, String field2) {
        return !errors.hasFieldErrors(getFieldNameWithCartItemCmd(field1)) && !errors.hasFieldErrors(getFieldNameWithCartItemCmd(field2));
    }

    protected void validatNonMandatoryFields(CartCmdImpl cmd, Errors errors) {
        validateStartAndEndZone(cmd, errors);

        if (fieldsHaveNoErrors(errors, FIELD_START_ZONE, FIELD_TRAVEL_CARD_TYPE)) {
            validateAndUpdateZoneMapping(cmd, errors);
        }
    }

    protected void validateStartAndEndZone(CartCmdImpl cmd, Errors errors) {
        int startZone = cmd.getCartItemCmd().getStartZone();
        int endZone = cmd.getCartItemCmd().getEndZone();
        if (startZone == endZone) {
            errors.rejectValue(getFieldNameWithCartItemCmd(FIELD_START_ZONE), INVALID_SINGLE_ZONE_SELECTION.errorCode());
        } else if (startZone > endZone) {
            cmd.getCartItemCmd().setStartZone(endZone);
            cmd.getCartItemCmd().setEndZone(startZone);
        }
    }

    protected void validateAndUpdateZoneMapping(CartCmdImpl cmd, Errors errors) {
        if (cmd.getCartItemCmd().getTravelCardType().contains(Durations.OTHER.getDurationType())) {
            updateOtherZoneMapping(cmd, errors);
        } else {
            updateZoneMapping(cmd, errors);
        }
    }

    protected void updateZoneMapping(CartCmdImpl cmd, Errors errors) {
        if (!isZoneMappingAvailable(cmd.getCartItemCmd().getTravelCardType(), cmd.getCartItemCmd().getStartZone(), cmd.getCartItemCmd().getEndZone(),
                        DateUtil.parse(cmd.getCartItemCmd().getStartDate()), cmd.getCartItemCmd().getDiscountType(), cmd.getCartItemCmd()
                                        .getPassengerType(), cmd.getCartItemCmd().getTicketType())) {
            errors.rejectValue(getFieldNameWithCartItemCmd(FIELD_START_ZONE), ZONE_MAPPING_NOT_EXIST.errorCode());
            getAlternativeZoneMapping(cmd.getCartItemCmd());
        }
    }

    protected boolean isZoneMappingAvailable(String travelCardType, Integer startZone, Integer endZone, Date startDate, String discountType,
                    String passengerType, String ticketType) {
        assert (travelCardType != null && startZone != null && endZone != null);
        ProductDTO product = null;
        travelCardType = addUnlistedProductService.removeTravelCardSuffixFromTravelCardType(travelCardType);
        // TODO Remove Constants for passenger type and discount type
        product = productDataService.findByFromDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(travelCardType, startZone, endZone, startDate,
                        (passengerType != null) ? passengerType : CubicConstant.PASSENGER_TYPE_ADULT_CODE, (discountType != null) ? discountType
                                        : CubicConstant.NO_DISCOUNT_TYPE_CODE, ticketType);
        return (product != null);
    }

    protected void updateOtherZoneMapping(CartCmdImpl cmd, Errors errors) {
        String substitutionTravelCardType = systemParameterService.getParameterValue(OTHER_TRAVELCARD_SUBSTITUTION_TRAVELCARD_TYPE);
        if (!isZoneMappingAvailable(substitutionTravelCardType, cmd.getCartItemCmd().getStartZone(), cmd.getCartItemCmd().getEndZone(),
                        DateUtil.parse(cmd.getCartItemCmd().getStartDate()), cmd.getCartItemCmd().getDiscountType(), cmd.getCartItemCmd()
                                        .getPassengerType(), cmd.getTicketType())) {
            errors.rejectValue(getFieldNameWithCartItemCmd(FIELD_START_ZONE), ZONE_MAPPING_NOT_EXIST.errorCode());
            getAlternativeZoneMapping(cmd.getCartItemCmd());
        }
        if (isOtherTradedTicketTravelCardType(cmd)) {
            updatedOtherTradedTicketTravelCardTypeZoneMapping(substitutionTravelCardType, cmd, errors);
        }
    }

    protected String getFieldNameWithCartItemCmd(String fieldName) {
        return FIELD_CART_ITEM_CMD + DOT + fieldName;
    }

    protected String getFieldNameWithTradedTicketCartItemCmd(String fieldName) {
        return FIELD_CART_ITEM_CMD + DOT + FIELD_TRADED_TICKET + DOT + fieldName;
    }

    protected CartItemCmdImpl getAlternativeZoneMapping(CartItemCmdImpl cmd) {
        int startZone = cmd.getStartZone();
        int endZone = cmd.getEndZone();
        AlternativeZoneMappingDTO alternativeZoneMapping = alternativeZoneMappingDataService.findByStartZoneAndEndZone(startZone, endZone);
        if (alternativeZoneMapping != null) {
            cmd.setStartZone(alternativeZoneMapping.getAlternativeStartZone());
            cmd.setEndZone(alternativeZoneMapping.getAlternativeEndZone());
        }
        return cmd;
    }

    protected boolean isOtherTradedTicketTravelCardType(CartCmdImpl cmd) {
        return (cmd.getCartItemCmd().getTradedTicket() != null && CartAttribute.OTHER_TRAVEL_CARD.equals(cmd.getCartItemCmd().getTradedTicket()
                        .getTravelCardType()));
    }

    protected void updatedOtherTradedTicketTravelCardTypeZoneMapping(String substitutionTravelCardType, CartCmdImpl cmd, Errors errors) {
        if (!isZoneMappingAvailable(substitutionTravelCardType, cmd.getCartItemCmd().getTradedTicket().getStartZone(), cmd.getCartItemCmd()
                        .getTradedTicket().getEndZone(), DateUtil.parse(cmd.getCartItemCmd().getTradedTicket().getStartDate()), cmd.getCartItemCmd()
                        .getDiscountType(), cmd.getCartItemCmd().getPassengerType(), cmd.getTicketType())) {
            errors.rejectValue(getFieldNameWithTradedTicketCartItemCmd(FIELD_START_ZONE), ZONE_MAPPING_NOT_EXIST.errorCode());
            getAlternativeZoneMapping(cmd.getCartItemCmd().getTradedTicket());
        }
    }

}
