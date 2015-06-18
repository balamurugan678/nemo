package com.novacroft.nemo.tfl.common.converter.impl.cyber_source;

import com.novacroft.cyber_source.web_service.model.transaction.ObjectFactory;
import com.novacroft.cyber_source.web_service.model.transaction.PaySubscriptionDeleteService;
import com.novacroft.cyber_source.web_service.model.transaction.RecurringSubscriptionInfo;
import com.novacroft.cyber_source.web_service.model.transaction.RequestMessage;
import com.novacroft.nemo.tfl.common.converter.cyber_source.CyberSourceSoapDeleteTokenRequestConverter;
import com.novacroft.nemo.tfl.common.transfer.cyber_source.CyberSourceSoapRequestDTO;
import org.springframework.stereotype.Service;

/**
 * CyberSource payment gateway delete token request DTO/model converter
 */
@Service("cyberSourceSoapDeleteTokenRequestConverter")
public class CyberSourceSoapDeleteTokenRequestConverterImpl implements CyberSourceSoapDeleteTokenRequestConverter {
    @Override
    public RequestMessage convertDtoToModel(CyberSourceSoapRequestDTO requestDTO) {
        ObjectFactory objectFactory = new ObjectFactory();
        RequestMessage requestMessage = objectFactory.createRequestMessage();
        requestMessage.setMerchantReferenceCode(requestDTO.getMerchantReferenceCode());

        PaySubscriptionDeleteService paySubscriptionDeleteService = objectFactory.createPaySubscriptionDeleteService();
        paySubscriptionDeleteService.setRun(Boolean.TRUE.toString());
        requestMessage.setPaySubscriptionDeleteService(paySubscriptionDeleteService);

        RecurringSubscriptionInfo recurringSubscriptionInfo = objectFactory.createRecurringSubscriptionInfo();
        recurringSubscriptionInfo.setSubscriptionID(requestDTO.getPaymentCardToken());
        requestMessage.setRecurringSubscriptionInfo(recurringSubscriptionInfo);

        return requestMessage;
    }
}
