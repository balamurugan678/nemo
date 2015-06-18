package com.novacroft.nemo.tfl.common.application_service.impl.cubic_import;

import static com.novacroft.nemo.common.utils.CurrencyUtil.formatPenceWithoutCurrencySymbol;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novacroft.nemo.common.support.InformationBuilder;
import com.novacroft.nemo.tfl.common.application_service.EmailMessageService;
import com.novacroft.nemo.tfl.common.application_service.cubic_import.AdHocLoadErrorNotificationService;
import com.novacroft.nemo.tfl.common.constant.ContentCode;
import com.novacroft.nemo.tfl.common.data_service.AdHocLoadSettlementDataService;
import com.novacroft.nemo.tfl.common.transfer.AdHocLoadSettlementDTO;

/**
 * Notify user on ad hoc load request error
 */
@Service("adHocLoadErrorNotificationService")
public class AdHocLoadErrorNotificationServiceImpl extends BaseAdHocLoadNotificationServiceImpl
        implements AdHocLoadErrorNotificationService {

    @Autowired
    protected AdHocLoadSettlementDataService adHocLoadSettlementDataService;
    
    @Autowired
    protected EmailMessageService adHocLoadErrorEmailService;

    @Override
    protected EmailMessageService getEmailMessageService() {
        return this.adHocLoadErrorEmailService;
    }

    @Override
    protected String buildAdditionalInformation(AdHocLoadSettlementDTO adHocLoadSettlementDTO, String cardNumber,
                                                String pickUpLocationName) {
        return new InformationBuilder().append(getContent(ContentCode.CUBIC_INTERACTION_ERROR_EMAILSENT.textCode()))
                .append("amount [%s]", formatPenceWithoutCurrencySymbol(adHocLoadSettlementDTO.getAmount()))
                .append("; pick up at [%s]", pickUpLocationName).append("; Oyster card number [%s]", cardNumber).toString();
    }
}
