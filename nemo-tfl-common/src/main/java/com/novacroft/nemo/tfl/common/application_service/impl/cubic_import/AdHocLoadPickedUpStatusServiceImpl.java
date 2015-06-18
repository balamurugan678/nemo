package com.novacroft.nemo.tfl.common.application_service.impl.cubic_import;

import static org.apache.commons.lang3.StringUtils.EMPTY;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novacroft.nemo.common.constant.EventName;
import com.novacroft.nemo.common.support.InformationBuilder;
import com.novacroft.nemo.tfl.common.application_service.cubic_import.AdHocLoadPickedUpStatusService;
import com.novacroft.nemo.tfl.common.constant.ContentCode;
import com.novacroft.nemo.tfl.common.constant.SettlementStatus;
import com.novacroft.nemo.tfl.common.data_service.LocationDataService;
import com.novacroft.nemo.tfl.common.transfer.AdHocLoadSettlementDTO;

/**
 * Record ad hoc load picked up
 */
@Service("adHocLoadPickedUpStatusService")
public class AdHocLoadPickedUpStatusServiceImpl extends BaseAdHocLoadStatusService implements AdHocLoadPickedUpStatusService {
    @Autowired
    protected LocationDataService locationDataService;

    @Override
    public void updateStatusToPickedUp(Integer requestSequenceNumber, String cardNumber) {
        AdHocLoadSettlementDTO adHocLoadSettlementDTO = findSettlement(requestSequenceNumber, cardNumber);
        updateStatus(adHocLoadSettlementDTO, SettlementStatus.PICKED_UP);
        updateOrderStatus(adHocLoadSettlementDTO.getOrderId());
        createEvent(cardNumber, adHocLoadSettlementDTO, EventName.AD_HOC_LOAD_PICKED_UP);
    }

    @Override
    protected String buildAdditionalInformation(String cardNumber, AdHocLoadSettlementDTO adHocLoadSettlementDTO, String pickUpLocationName) {
        return new InformationBuilder().append(getContent(ContentCode.CUBIC_INTERACTION_PICKED_UP.textCode())).append("Oyster card number [%s]", cardNumber)
                .append("; picked up at [%s]", getLocationName(adHocLoadSettlementDTO.getPickUpNationalLocationCode()))
                .toString();
    }

    protected String getLocationName(Integer nationalLocationCode) {
        return (nationalLocationCode != null) ? this.locationDataService.findById((long) nationalLocationCode).getName() :
                EMPTY;
    }
}
