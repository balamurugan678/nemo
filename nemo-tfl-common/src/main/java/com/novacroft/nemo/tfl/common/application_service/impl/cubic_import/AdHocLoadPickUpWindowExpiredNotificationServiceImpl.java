package com.novacroft.nemo.tfl.common.application_service.impl.cubic_import;

import static com.novacroft.nemo.common.utils.CurrencyUtil.formatPenceWithoutCurrencySymbol;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novacroft.nemo.common.support.InformationBuilder;
import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.tfl.common.application_service.EmailMessageService;
import com.novacroft.nemo.tfl.common.application_service.cubic_import.AdHocLoadPickUpWindowExpiredNotificationService;
import com.novacroft.nemo.tfl.common.constant.ContentCode;
import com.novacroft.nemo.tfl.common.data_service.AdHocLoadSettlementDataService;
import com.novacroft.nemo.tfl.common.transfer.AdHocLoadSettlementDTO;

/**
 * Notify user when ad hoc pick up window has expired
 */
@Service("adHocLoadPickUpWindowExpiredNotificationService")
public class AdHocLoadPickUpWindowExpiredNotificationServiceImpl extends BaseAdHocLoadNotificationServiceImpl
        implements AdHocLoadPickUpWindowExpiredNotificationService {

    @Autowired
    protected AdHocLoadSettlementDataService adHocLoadSettlementDataService;
    
    @Autowired
    protected EmailMessageService adHocLoadPickupWindowExpiredEmailService;

   @Override
    protected EmailMessageService getEmailMessageService() {
        return this.adHocLoadPickupWindowExpiredEmailService;
    }

    @Override
    protected String buildAdditionalInformation(AdHocLoadSettlementDTO adHocLoadSettlementDTO, String cardNumber,
                                                String pickUpLocationName) {
        return new InformationBuilder().append(getContent(ContentCode.CUBIC_INTERACTION_PICKUP_WINDOW_EXPIRED_EMAILSENT.textCode()))
                .append("amount [%s]", formatPenceWithoutCurrencySymbol(adHocLoadSettlementDTO.getAmount()))
                .append("; Oyster card number [%s]", cardNumber)
                .append("; window expired on [%s]", DateUtil.formatDate(adHocLoadSettlementDTO.getExpiresOn())).toString();
    }
}
