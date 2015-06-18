package com.novacroft.nemo.tfl.common.data_service.impl.cubic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novacroft.nemo.common.converter.XmlModelConverter;
import com.novacroft.nemo.tfl.common.converter.cubic.GetCardConverter;
import com.novacroft.nemo.tfl.common.data_service.cubic.GetCardRequestDataService;
import com.novacroft.nemo.tfl.common.domain.cubic.CardInfoRequestV2;
import com.novacroft.nemo.tfl.common.domain.cubic.CardInfoResponseV2;
import com.novacroft.nemo.tfl.common.domain.cubic.RequestFailure;
import com.novacroft.nemo.tfl.common.service_access.cubic.CubicServiceAccess;
import com.novacroft.nemo.tfl.common.transfer.cubic.CardInfoRequestV2DTO;
import com.novacroft.nemo.tfl.common.transfer.cubic.CardInfoResponseV2DTO;

/**
 * Call CUBIC Get Card service
 */
@Service(value = "getCardRequestDataService")
public class GetCardRequestDataServiceImpl implements GetCardRequestDataService {
    protected static final Logger logger = LoggerFactory.getLogger(GetCardRequestDataServiceImpl.class);
    
    @Autowired
    protected GetCardConverter getCardConverter;
    @Autowired
    protected XmlModelConverter<CardInfoRequestV2> cardInfoRequestV2Converter;
    @Autowired
    protected XmlModelConverter<CardInfoResponseV2> cardInfoResponseV2Converter;
    @Autowired
    protected XmlModelConverter<RequestFailure> requestFailureConverter;
    @Autowired
    protected CubicServiceAccess cubicServiceAccess;
    
    @Override
    public CardInfoResponseV2DTO getCard(CardInfoRequestV2DTO request) {
        CardInfoRequestV2 cardInfoRequestV2 = this.getCardConverter.convertToModel(request);
        String xmlRequest = this.cardInfoRequestV2Converter.convertModelToXml(cardInfoRequestV2);
        String xmlResponse = this.cubicServiceAccess.callCubic(xmlRequest).toString();
        Object modelResponse = this.cardInfoResponseV2Converter.convertXmlToObject(xmlResponse);
        if (isSuccessResponse(modelResponse)) {
            return this.getCardConverter.convertToDto((CardInfoResponseV2) modelResponse);
        }
        return this.getCardConverter.convertToDto(this.requestFailureConverter.convertXmlToModel(xmlResponse));
    }
    
    protected boolean isSuccessResponse(Object modelResponse) {
        return modelResponse instanceof CardInfoResponseV2;
    }

}
