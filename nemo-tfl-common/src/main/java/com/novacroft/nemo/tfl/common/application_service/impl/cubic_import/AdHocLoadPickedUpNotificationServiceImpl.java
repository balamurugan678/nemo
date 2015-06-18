package com.novacroft.nemo.tfl.common.application_service.impl.cubic_import;

import static com.novacroft.nemo.common.utils.CurrencyUtil.formatPenceWithoutCurrencySymbol;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novacroft.nemo.common.support.InformationBuilder;
import com.novacroft.nemo.tfl.common.application_service.EmailMessageService;
import com.novacroft.nemo.tfl.common.application_service.cubic_import.AdHocLoadPickedUpNotificationService;
import com.novacroft.nemo.tfl.common.constant.ContentCode;
import com.novacroft.nemo.tfl.common.transfer.AdHocLoadSettlementDTO;

/**
 * Notify user when ad hoc pick up window has expired
 */
@Service("adHocLoadPickedUpNotificationService")
public class AdHocLoadPickedUpNotificationServiceImpl extends BaseAdHocLoadNotificationServiceImpl
 implements AdHocLoadPickedUpNotificationService {

    @Autowired
    protected EmailMessageService adHocLoadPickedUpEmailService;

    @Override
    protected EmailMessageService getEmailMessageService() {
        return this.adHocLoadPickedUpEmailService;
    }

    @Override
    protected String buildAdditionalInformation(AdHocLoadSettlementDTO adHocLoadSettlementDTO, String cardNumber,
                                                String pickUpLocationName) {
        return new InformationBuilder().append(getContent(ContentCode.CUBIC_INTERACTION_PICKED_UP_EMAILSENT.textCode()))
                .append("amount [%s]", formatPenceWithoutCurrencySymbol(adHocLoadSettlementDTO.getAmount()))
                .append("; picked up at [%s]", pickUpLocationName).append("; Oyster card number [%s]", cardNumber).toString();
    }
}
