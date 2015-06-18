package com.novacroft.nemo.tfl.common.application_service.impl.cubic_import;

import static com.novacroft.nemo.common.utils.CurrencyUtil.formatPenceWithoutCurrencySymbol;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novacroft.nemo.common.application_service.ApplicationEventService;
import com.novacroft.nemo.common.constant.EventName;
import com.novacroft.nemo.common.support.InformationBuilder;
import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.tfl.common.application_service.AdHocLoadReadyForCollectionStatusService;
import com.novacroft.nemo.tfl.common.application_service.OrderService;
import com.novacroft.nemo.tfl.common.constant.ContentCode;
import com.novacroft.nemo.tfl.common.constant.SettlementStatus;
import com.novacroft.nemo.tfl.common.data_service.AdHocLoadSettlementDataService;
import com.novacroft.nemo.tfl.common.transfer.AdHocLoadSettlementDTO;

/**
 * Manage ad-hoc load settlement statuses
 */
@Service("adHocLoadReadyForCollectionStatusService")
public class AdHocLoadReadyForCollectionStatusServiceImpl extends BaseAdHocLoadStatusService implements AdHocLoadReadyForCollectionStatusService {

    @Autowired
    protected AdHocLoadSettlementDataService adHocLoadSettlementDataService;
    @Autowired
    protected ApplicationEventService applicationEventService;
    @Autowired
    protected OrderService orderService;

    @Override
    public void updateAdHocLoadSettlementStatus(Integer requestSequenceNumber, String cardNumber) {
        AdHocLoadSettlementDTO adHocLoadSettlementDTO =
                this.adHocLoadSettlementDataService.findByRequestSequenceNumberAndCardNumber(requestSequenceNumber, cardNumber);
        updateStatus(adHocLoadSettlementDTO, SettlementStatus.READY_FOR_PICK_UP);
        orderService.updateOrderStatus(adHocLoadSettlementDTO.getOrderId());
        createEvent(cardNumber, adHocLoadSettlementDTO, EventName.AD_HOC_LOAD_READY_FOR_PICK_UP);
    }

    @Override
    protected String buildAdditionalInformation(String cardNumber, AdHocLoadSettlementDTO adHocLoadSettlementDTO, String pickUpLocationName) {
        return new InformationBuilder().append(getContent(ContentCode.CUBIC_INTERACTION_READYFORCOLLECTION.textCode()))
                        .append("amount [%s]", formatPenceWithoutCurrencySymbol(adHocLoadSettlementDTO.getAmount()))
                        .append("; pick up at [%s]", pickUpLocationName).append("; Oyster card number [%s]", cardNumber)
                        .append("; expires on [%s]", DateUtil.formatDate(adHocLoadSettlementDTO.getExpiresOn())).toString();
    }
}
