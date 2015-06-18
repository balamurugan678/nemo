package com.novacroft.nemo.tfl.common.application_service.impl.cubic_import;

import static com.novacroft.nemo.common.utils.CurrencyUtil.formatPenceWithoutCurrencySymbol;

import org.springframework.stereotype.Service;

import com.novacroft.nemo.common.constant.EventName;
import com.novacroft.nemo.common.support.InformationBuilder;
import com.novacroft.nemo.tfl.common.application_service.cubic_import.AdHocLoadErrorStatusService;
import com.novacroft.nemo.tfl.common.constant.ContentCode;
import com.novacroft.nemo.tfl.common.constant.SettlementStatus;
import com.novacroft.nemo.tfl.common.transfer.AdHocLoadSettlementDTO;

/**
 * Record ad hoc load error
 */
@Service("adHocLoadErrorStatusService")
public class AdHocLoadErrorStatusServiceImpl extends BaseAdHocLoadStatusService implements AdHocLoadErrorStatusService {

    @Override
    public void updateStatusToFailed(Integer requestSequenceNumber, String cardNumber) {
        AdHocLoadSettlementDTO adHocLoadSettlementDTO = findSettlement(requestSequenceNumber, cardNumber);
        updateStatus(adHocLoadSettlementDTO, SettlementStatus.FAILED);
        updateOrderStatus(adHocLoadSettlementDTO.getOrderId());
        createEvent(cardNumber, adHocLoadSettlementDTO, EventName.AD_HOC_LOAD_FAILED);
    }

    @Override
    protected String buildAdditionalInformation(String cardNumber, AdHocLoadSettlementDTO adHocLoadSettlementDTO, String pickUpLocationName) {
        return new InformationBuilder().append(getContent(ContentCode.CUBIC_INTERACTION_ERROR.textCode()))
                        .append("amount [%s]", formatPenceWithoutCurrencySymbol(adHocLoadSettlementDTO.getAmount()))
                        .append("; pick up tried at [%s]", pickUpLocationName).append("; Oyster card number [%s]", cardNumber).toString();
    }
    
    
}
