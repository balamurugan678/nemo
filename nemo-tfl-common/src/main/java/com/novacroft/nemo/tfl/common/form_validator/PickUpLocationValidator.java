package com.novacroft.nemo.tfl.common.form_validator;

import static com.novacroft.nemo.tfl.common.constant.ContentCode.SELECTED_STATION_DIFFERENT_FROM_PENDINGITEM_STATION;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_STATION_ID;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.novacroft.nemo.common.domain.cubic.PendingItems;
import com.novacroft.nemo.common.domain.cubic.PrePayTicket;
import com.novacroft.nemo.common.domain.cubic.PrePayValue;
import com.novacroft.nemo.common.validator.BaseValidator;
import com.novacroft.nemo.tfl.common.application_service.cubic.GetCardService;
import com.novacroft.nemo.tfl.common.command.PickUpLocationCmd;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.LocationDataService;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.cubic.CardInfoResponseV2DTO;

/**
 * PickUp location validator
 */
@Component(value = "pickUpLocationValidator")
public class PickUpLocationValidator extends BaseValidator {

    @Autowired
    protected CardDataService cardDataService;
    @Autowired
    protected GetCardService getCardService;
    @Autowired
    protected SelectStationValidator selectStationValidator;
    @Autowired
    protected LocationDataService locationDataService;

    @Override
    public boolean supports(Class<?> targetClass) {
        return PickUpLocationCmd.class.equals(targetClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PickUpLocationCmd cmd = (PickUpLocationCmd) target;
        selectStationValidator.validate(target, errors);
        if (hasNoFieldError(errors, FIELD_STATION_ID)) {
            CardDTO cardDTO = cardDataService.findById(cmd.getCardId());
            CardInfoResponseV2DTO cardInfoResponseV2DTO = getCardService.getCard(cardDTO.getCardNumber());
            rejectIfSelectedStationDifferentFromPendingItemStation(errors, cmd.getStationId().intValue(), cardInfoResponseV2DTO);
        }
    }

    protected void rejectIfSelectedStationDifferentFromPendingItemStation(Errors errors, Integer stationId,
                    CardInfoResponseV2DTO cardInfoResponseV2DTO) {
        if (cardInfoResponseV2DTO != null && cardInfoResponseV2DTO.getPendingItems() != null) {
            PendingItems pendingItems = cardInfoResponseV2DTO.getPendingItems();
            rejectIfSelectedStationDifferentFromPendingPrePayValuesStation(errors, stationId, pendingItems.getPpvs());
             rejectIfSelectedStationDifferentFromPendingPrePayTicketsStation(errors, stationId, pendingItems.getPpts());
        }
    }

    protected void rejectIfSelectedStationDifferentFromPendingPrePayTicketsStation(Errors errors, Integer stationId, List<PrePayTicket> prePayTickets) {
        if (prePayTickets != null && prePayTickets.size() > 0 && prePayTickets.get(0) != null && prePayTickets.get(0).getPickupLocation() != null) {
            int pendingStationId = prePayTickets.get(0).getPickupLocation().intValue();
            if (pendingStationId != stationId.intValue()) {
                errors.rejectValue(FIELD_STATION_ID, SELECTED_STATION_DIFFERENT_FROM_PENDINGITEM_STATION.errorCode(),
                                new String[] { locationDataService.getActiveLocationById(pendingStationId).getName() }, null);

            }
        }
    }
    
    protected void rejectIfSelectedStationDifferentFromPendingPrePayValuesStation(Errors errors, Integer stationId, List<PrePayValue> prePayValues){
        if (prePayValues != null && prePayValues.size() > 0 && prePayValues.get(0) != null && prePayValues.get(0).getPickupLocation() != null) {
            int pendingStationId = prePayValues.get(0).getPickupLocation().intValue();
            if (pendingStationId != stationId.intValue()) {
                errors.rejectValue(FIELD_STATION_ID, SELECTED_STATION_DIFFERENT_FROM_PENDINGITEM_STATION.errorCode(),
                                new String[] { locationDataService.getActiveLocationById(pendingStationId).getName() }, null);

            }
        }
    }

}
