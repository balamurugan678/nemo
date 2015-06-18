package com.novacroft.nemo.tfl.common.data_service.impl.cubic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novacroft.nemo.common.application_service.ServiceCallLogService;
import com.novacroft.nemo.common.converter.XmlModelConverter;
import com.novacroft.nemo.common.support.NemoUserContext;
import com.novacroft.nemo.common.transfer.ServiceCallLogDTO;
import com.novacroft.nemo.tfl.common.application_service.cubic.CardRemoveUpdateService;
import com.novacroft.nemo.tfl.common.constant.WebServiceName;
import com.novacroft.nemo.tfl.common.converter.cubic.CardRemoveUpdateConverter;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.data_service.cubic.CardRemoveUpdateDataService;
import com.novacroft.nemo.tfl.common.domain.cubic.CardRemoveUpdateRequest;
import com.novacroft.nemo.tfl.common.domain.cubic.CardRemoveUpdateResponse;
import com.novacroft.nemo.tfl.common.domain.cubic.RequestFailure;
import com.novacroft.nemo.tfl.common.transfer.CardUpdateResponseDTO;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import com.novacroft.nemo.tfl.common.transfer.cubic.CardRemoveUpdateRequestDTO;

@Service("cardRemoveUpdateDataService")
public class CardRemoveUpdateDataServiceImpl implements CardRemoveUpdateDataService {

    protected static final Logger logger = LoggerFactory.getLogger(CardRemoveUpdateDataServiceImpl.class);
    @Autowired
    protected XmlModelConverter<CardRemoveUpdateResponse> cardRemoveUpdateResponseConverter;
    @Autowired
    protected CardRemoveUpdateService cardRemoveUpdateService;
    @Autowired
    protected XmlModelConverter<RequestFailure> requestFailureConverter;
    @Autowired
    protected XmlModelConverter<CardRemoveUpdateRequest> cardRemoveUpdateRequestConverter;
    @Autowired
    protected CardRemoveUpdateConverter cardRemoveUpdateConverter;
    @Autowired
    protected ServiceCallLogService serviceCallLogService;
    @Autowired
    protected NemoUserContext nemoUserContext;
    @Autowired
    protected CustomerDataService customerDataService;

    @Override
    public CardUpdateResponseDTO removePendingUpdate(CardRemoveUpdateRequestDTO cardRemoveUpdateRequestDTO) {
        CardRemoveUpdateRequest request = cardRemoveUpdateConverter.convertToModel(cardRemoveUpdateRequestDTO);
        String xmlRequest = cardRemoveUpdateRequestConverter.convertModelToXml(request);
        ServiceCallLogDTO serviceCallLogDTO = this.serviceCallLogService.initialiseCallLog(WebServiceName.CUBIC_REMOVE_PENDING_UPDATE.code(),
                        nemoUserContext.getUserName(), getCustomerId(request.getPrestigeId()));
        String xmlResponse = cardRemoveUpdateService.removePendingUpdate(xmlRequest).toString();
        this.serviceCallLogService.finaliseCallLog(serviceCallLogDTO, xmlRequest, xmlResponse);
        Object modelResponse = cardRemoveUpdateResponseConverter.convertXmlToObject(xmlResponse);
        if (isSuccessResponse(modelResponse)) {
            return cardRemoveUpdateConverter.convertToDTO((CardRemoveUpdateResponse) modelResponse);
        }
        return cardRemoveUpdateConverter.convertToDTO(requestFailureConverter.convertXmlToModel(xmlResponse));        
    }

    protected boolean isSuccessResponse(Object modelResponse) {
        return modelResponse instanceof CardRemoveUpdateResponse;
    }

    protected Long getCustomerId(String cardNumber) {
        CustomerDTO customerDTO = this.customerDataService.findByCardNumber(cardNumber);
        return customerDTO != null ? customerDTO.getId() : null;
    }
}
