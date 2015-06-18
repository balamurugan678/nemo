package com.novacroft.nemo.tfl.common.application_service.impl.cubic_import;

import static com.novacroft.nemo.common.utils.CurrencyUtil.formatPenceWithoutCurrencySymbol;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novacroft.nemo.common.support.InformationBuilder;
import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.tfl.common.application_service.EmailMessageService;
import com.novacroft.nemo.tfl.common.application_service.cubic_import.AdHocLoadReadyForCollectionNotificationService;
import com.novacroft.nemo.tfl.common.constant.ContentCode;
import com.novacroft.nemo.tfl.common.constant.SettlementStatus;
import com.novacroft.nemo.tfl.common.data_service.AdHocLoadSettlementDataService;
import com.novacroft.nemo.tfl.common.transfer.AdHocLoadSettlementDTO;

/**
 * Notify user when refund is ready for collection implementation
 */
@Service("refundReadyForCollectionNotificationService")
public class AdHocLoadReadyForCollectionNotificationServiceImpl extends BaseAdHocLoadNotificationServiceImpl
        implements AdHocLoadReadyForCollectionNotificationService {

    @Autowired
    protected AdHocLoadSettlementDataService adHocLoadSettlementDataService;
    @Autowired
    protected EmailMessageService adHocLoadReadyForCollectionEmailService;

    @Override
    public Boolean hasAdHocLoadBeenRequested(Integer requestSequenceNumber, String cardNumber) {
        AdHocLoadSettlementDTO adHocLoadSettlementDTO =
                this.adHocLoadSettlementDataService.findByRequestSequenceNumberAndCardNumber(requestSequenceNumber, cardNumber);
        if (adHocLoadSettlementDTO == null) {
            return Boolean.FALSE;
        }
        if (SettlementStatus.READY_FOR_PICK_UP.code().equals(adHocLoadSettlementDTO.getStatus())) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

   
    @Override
    protected EmailMessageService getEmailMessageService() {
        return this.adHocLoadReadyForCollectionEmailService;
    }

    @Override
    protected String buildAdditionalInformation(AdHocLoadSettlementDTO adHocLoadSettlementDTO, String cardNumber,
                                                String pickUpLocationName) {
        return new InformationBuilder().append(getContent(ContentCode.CUBIC_INTERACTION_READYFORCOLLECTION_EMAILSENT.textCode()))
                .append("amount [%s]", formatPenceWithoutCurrencySymbol(adHocLoadSettlementDTO.getAmount()))
                .append("; pick up at [%s]", pickUpLocationName).append("; Oyster card number [%s]", cardNumber)
                .append("; expires on [%s]", DateUtil.formatDate(adHocLoadSettlementDTO.getExpiresOn())).toString();
    }
}
