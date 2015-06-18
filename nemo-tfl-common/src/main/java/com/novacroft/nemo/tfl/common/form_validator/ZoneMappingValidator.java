package com.novacroft.nemo.tfl.common.form_validator;

import static com.novacroft.nemo.common.utils.DateUtil.parse;
import static com.novacroft.nemo.tfl.common.constant.ContentCode.INVALID_SINGLE_ZONE_SELECTION;
import static com.novacroft.nemo.tfl.common.constant.ContentCode.ZONE_MAPPING_NOT_EXIST;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_END_DATE;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_END_ZONE;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_START_ZONE;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_TRAVEL_CARD_TYPE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.constant.Durations;
import com.novacroft.nemo.common.validator.BaseValidator;
import com.novacroft.nemo.tfl.common.command.TravelCardCmd;
import com.novacroft.nemo.tfl.common.constant.ProductItemType;
import com.novacroft.nemo.tfl.common.data_service.AlternativeZoneMappingDataService;
import com.novacroft.nemo.tfl.common.data_service.PrePaidTicketDataService;
import com.novacroft.nemo.tfl.common.data_service.ProductDataService;
import com.novacroft.nemo.tfl.common.transfer.AlternativeZoneMappingDTO;
import com.novacroft.nemo.tfl.common.transfer.DurationPeriodDTO;
import com.novacroft.nemo.tfl.common.transfer.PrePaidTicketDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductDTO;
import com.novacroft.nemo.tfl.common.util.DurationUtil;

/**
 * Zone Mapping validation
 */
@Component("zoneMappingValidator")
public class ZoneMappingValidator extends BaseValidator {

    @Autowired
    protected AlternativeZoneMappingDataService alternativeZoneMappingDataService;
    @Autowired
    protected SystemParameterService systemParameterService;
    @Autowired
    protected PrePaidTicketDataService prePaidTicketDataService;
    @Autowired
    protected ProductDataService productDataService;

    @Override
    public boolean supports(Class<?> targetClass) {
        return TravelCardCmd.class.equals(targetClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        TravelCardCmd travelCardCmd = (TravelCardCmd) target;
        validateMandatoryFields(errors);
        if (!errors.hasFieldErrors(FIELD_START_ZONE) && !errors.hasFieldErrors(FIELD_END_ZONE)) {
            validateSingleZoneSelection(travelCardCmd, errors);
            validateAndGetZoneMapping(travelCardCmd, errors);
        }
    }

    protected void validateMandatoryFields(Errors errors) {
        rejectIfMandatoryFieldEmpty(errors, FIELD_START_ZONE);
        rejectIfMandatoryFieldEmpty(errors, FIELD_END_ZONE);
    }

    protected void validateSingleZoneSelection(TravelCardCmd travelCardCmd, Errors errors) {
        int startZone = travelCardCmd.getStartZone();
        int endZone = travelCardCmd.getEndZone();
        if (startZone == endZone) {
            errors.rejectValue(FIELD_START_ZONE, INVALID_SINGLE_ZONE_SELECTION.errorCode());
        } else if (startZone > endZone) {
            travelCardCmd.setStartZone(endZone);
            travelCardCmd.setEndZone(startZone);
        }
    }

    protected void validateAndGetZoneMapping(TravelCardCmd travelCardCmd, Errors errors) {
        if (!errors.hasFieldErrors(FIELD_START_ZONE) && !errors.hasFieldErrors(FIELD_TRAVEL_CARD_TYPE) && !errors.hasFieldErrors(FIELD_END_DATE)) {
            if (travelCardCmd.getTravelCardType().equalsIgnoreCase(Durations.OTHER.getDurationType())) {
                validateOtherTravelCardsZoneMapping(travelCardCmd, errors);
            } else {
                validateNonOtherTravelCardsZoneMapping(travelCardCmd, errors);
            }
        }
    }

    protected void validateNonOtherTravelCardsZoneMapping(TravelCardCmd travelCardCmd, Errors errors) {
        ProductDTO productDTO = productDataService.findByFromDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(travelCardCmd.getTravelCardType(),
                        travelCardCmd.getStartZone(), travelCardCmd.getEndZone(), parse(travelCardCmd.getStartDate()),
                        travelCardCmd.getPassengerType(), travelCardCmd.getDiscountType(), ProductItemType.TRAVEL_CARD.databaseCode());
        if (productDTO == null) {
            errors.rejectValue(FIELD_START_ZONE, ZONE_MAPPING_NOT_EXIST.errorCode());
            getAlternativeZoneMapping(travelCardCmd);
        }
    }

    protected void validateOtherTravelCardsZoneMapping(TravelCardCmd travelCardCmd, Errors errors) {
        DurationPeriodDTO durationPeriod = DurationUtil.getDurationForOddPeriod(parse(travelCardCmd.getStartDate()), parse(travelCardCmd.getEndDate()));
        ProductDTO productDTO = productDataService.findByFromAndToDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(
                        durationPeriod.getFromDurationCode(), durationPeriod.getToDurationCode(), travelCardCmd.getStartZone(),
                        travelCardCmd.getEndZone(), parse(travelCardCmd.getStartDate()), travelCardCmd.getPassengerType(),
                        travelCardCmd.getDiscountType(), ProductItemType.TRAVEL_CARD.databaseCode());
        if (productDTO == null) {
            errors.rejectValue(FIELD_START_ZONE, ZONE_MAPPING_NOT_EXIST.errorCode());
            getAlternativeZoneMapping(travelCardCmd);
        }
    }

    public TravelCardCmd getAlternativeZoneMapping(TravelCardCmd travelCardCmd) {
        int startZone = travelCardCmd.getStartZone();
        int endZone = travelCardCmd.getEndZone();
        AlternativeZoneMappingDTO alternativeZoneMapping = alternativeZoneMappingDataService.findByStartZoneAndEndZone(startZone, endZone);
        if (alternativeZoneMapping != null) {
            travelCardCmd.setStartZone(alternativeZoneMapping.getAlternativeStartZone());
            travelCardCmd.setEndZone(alternativeZoneMapping.getAlternativeEndZone());
        }
        return travelCardCmd;
    }

    protected PrePaidTicketDTO getPrePaidTickectDTOById(TravelCardCmd travelCardCmd) {
        PrePaidTicketDTO prePaidTicketDTO = null;
        if (travelCardCmd.getPrePaidTicketId() != null) {
            prePaidTicketDTO = prePaidTicketDataService.findById(travelCardCmd.getPrePaidTicketId());
        }
        return prePaidTicketDTO;
    }
}
