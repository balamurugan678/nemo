package com.novacroft.nemo.tfl.common.application_service.impl.cubic_import;

import org.springframework.stereotype.Service;

import com.novacroft.nemo.common.constant.EventName;
import com.novacroft.nemo.common.support.InformationBuilder;
import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.tfl.common.application_service.cubic_import.AdHocLoadPickUpWindowExpiredStatusService;
import com.novacroft.nemo.tfl.common.constant.ContentCode;
import com.novacroft.nemo.tfl.common.constant.SettlementStatus;
import com.novacroft.nemo.tfl.common.transfer.AdHocLoadSettlementDTO;

/**
 * Record ad hoc load pick up window expired
 */
@Service("adHocLoadPickUpWindowExpiredStatusService")
public class AdHocLoadPickUpWindowExpiredStatusServiceImpl extends BaseAdHocLoadStatusService
        implements AdHocLoadPickUpWindowExpiredStatusService {

    @Override
    public void updateStatusToPickUpExpired(Integer requestSequenceNumber, String cardNumber) {
        AdHocLoadSettlementDTO adHocLoadSettlementDTO = findSettlement(requestSequenceNumber, cardNumber);
        updateStatus(adHocLoadSettlementDTO, SettlementStatus.PICK_UP_EXPIRED);
        updateOrderStatus(adHocLoadSettlementDTO.getOrderId());
        createEvent(cardNumber, adHocLoadSettlementDTO, EventName.AD_HOC_LOAD_PICK_UP_WINDOW_EXPIRED);
    }

    @Override
    protected String buildAdditionalInformation(String cardNumber, AdHocLoadSettlementDTO adHocLoadSettlementDTO, String pickUpLocationName) {
        return new InformationBuilder().append(getContent(ContentCode.CUBIC_INTERACTION_PICKUP_WINDOW_EXPIRED.textCode())).append("Oyster card number [%s]", cardNumber)
                .append("; window expired on [%s]", DateUtil.formatDate(adHocLoadSettlementDTO.getExpiresOn())).toString();
    }
}
