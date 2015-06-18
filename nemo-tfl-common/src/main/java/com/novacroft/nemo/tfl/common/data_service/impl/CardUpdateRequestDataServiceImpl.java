package com.novacroft.nemo.tfl.common.data_service.impl;

import com.novacroft.nemo.tfl.common.service_access.cubic.CubicServiceAccess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.novacroft.nemo.common.application_service.ServiceCallLogService;
import com.novacroft.nemo.common.converter.XmlModelConverter;
import com.novacroft.nemo.common.support.NemoUserContext;
import com.novacroft.nemo.common.transfer.ServiceCallLogDTO;
import com.novacroft.nemo.tfl.common.application_service.cubic.CubicCardService;
import com.novacroft.nemo.tfl.common.constant.CartType;
import com.novacroft.nemo.tfl.common.constant.WebServiceName;
import com.novacroft.nemo.tfl.common.converter.CardUpdatePrePayTicketChangeConverter;
import com.novacroft.nemo.tfl.common.converter.CardUpdatePrePayValueChangeConverter;
import com.novacroft.nemo.tfl.common.data_service.CardUpdateRequestDataService;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.domain.cubic.CardUpdatePrePayTicketRequest;
import com.novacroft.nemo.tfl.common.domain.cubic.CardUpdatePrePayValueRequest;
import com.novacroft.nemo.tfl.common.domain.cubic.CardUpdateResponse;
import com.novacroft.nemo.tfl.common.domain.cubic.RequestFailure;
import com.novacroft.nemo.tfl.common.transfer.CardUpdatePrePayTicketRequestDTO;
import com.novacroft.nemo.tfl.common.transfer.CardUpdatePrePayValueRequestDTO;
import com.novacroft.nemo.tfl.common.transfer.CardUpdateResponseDTO;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;

/**
 * 
 * Allows the creation of the Cubic CardUpdateRequest message, using the values from CardUpdateRequest object.
 *
 * <pre>
 * {@code
 *      <CardUpdateRequest>
 *     <RealTimeFlag>N</RealTimeFlag>
 *     <PrestigeID>123456789</PrestigeID>
 *     <Action>ADD</Action>
 *     <PPT>
 *         <ProductCode>123</ProductCode>
 *         <StartDate>10/10/2002</StartDate>
 *         <ExpiryDate>11/10/2002</ExpiryDate>
 *         <ProductPrice>360</ProductPrice>
 *         <Currency>0</Currency>
 *     </PPT>
 *     <PickupLocation>740</PickupLocation>
 *     <PaymentMethod>32</PaymentMethod>
 *     <OriginatorInfo>
 *         <UserID>LTWebUser</UserID>
 *         <Password>secrets</Password>
 *     </OriginatorInfo>
 * </CardUpdateRequest>
 * }
 * </pre>
 */
@Service(value = "cardUpdateRequestDataService")
@Transactional(readOnly = true)
public class CardUpdateRequestDataServiceImpl implements CardUpdateRequestDataService {
    protected static final Logger logger = LoggerFactory.getLogger(CardUpdateRequestDataServiceImpl.class);

    @Autowired
    protected CardUpdatePrePayTicketChangeConverter cardUpdatePrePayTicketChangeConverter;
    @Autowired
    protected CardUpdatePrePayValueChangeConverter cardUpdatePrePayValueChangeConverter;
    @Autowired
    protected XmlModelConverter<CardUpdatePrePayTicketRequest> cardUpdatePrePayTicketRequestConverter;
    @Autowired
    protected XmlModelConverter<CardUpdatePrePayValueRequest> cardUpdatePrePayValueRequestConverter;
    @Autowired
    protected CubicCardService cubicCardService;
    @Autowired
    protected XmlModelConverter<CardUpdateResponse> cardUpdateResponseConverter;
    @Autowired
    protected XmlModelConverter<RequestFailure> requestFailureConverter;
    @Autowired
    protected CustomerDataService customerDataService;
    @Autowired
    protected ServiceCallLogService serviceCallLogService;
    @Autowired
    protected NemoUserContext nemoUserContext;
    @Autowired
    protected CubicServiceAccess cubicServiceAccess;

    @Override
    public CardUpdateResponseDTO addPrePayTicket(CardUpdatePrePayTicketRequestDTO request) {
        if (request != null && request.getPrestigeId() != null) {
            CardUpdatePrePayTicketRequest cardUpdateRequest = this.cardUpdatePrePayTicketChangeConverter.convertToModel(request);
            String xmlRequest = this.cardUpdatePrePayTicketRequestConverter.convertModelToXml(cardUpdateRequest);
            ServiceCallLogDTO serviceCallLogDTO = this.serviceCallLogService.initialiseCallLog(WebServiceName.CUBIC_ADD_PRE_PAY_TICKET.code(),
                            nemoUserContext.getUserName(), getCustomerId(request.getPrestigeId()));
            String xmlResponse = this.cubicServiceAccess.callCubic(xmlRequest).toString();
            this.serviceCallLogService.finaliseCallLog(serviceCallLogDTO, xmlRequest, xmlResponse);
            Object modelResponse = this.cardUpdateResponseConverter.convertXmlToObject(xmlResponse);
            if (isSuccessResponse(modelResponse)) {
                return this.cardUpdatePrePayTicketChangeConverter.convertToDto((CardUpdateResponse) modelResponse);
            }
            return this.cardUpdatePrePayTicketChangeConverter.convertToDto(this.requestFailureConverter.convertXmlToModel(xmlResponse));
        }
        return null;
    }

    protected boolean isSuccessResponse(Object modelResponse) {
        return modelResponse instanceof CardUpdateResponse;
    }

    @Override
    public CardUpdateResponseDTO addPrePayValue(CardUpdatePrePayValueRequestDTO request, String cartType) {
        if (request != null && request.getPrestigeId() != null) {
            CardUpdatePrePayValueRequest cardUpdateRequest = this.cardUpdatePrePayValueChangeConverter.convertToModel(request);
            String xmlRequest = this.cardUpdatePrePayValueRequestConverter.convertModelToXml(cardUpdateRequest);
            ServiceCallLogDTO serviceCallLogDTO = this.serviceCallLogService.initialiseCallLog(WebServiceName.CUBIC_ADD_PRE_PAY_VALUE.code(),
                            nemoUserContext.getUserName(), (CartType.ANONYMOUS_GOODWILL_REFUND.code().equalsIgnoreCase(cartType) ? null
                                            : getCustomerId(request.getPrestigeId())));
            String xmlResponse = this.cubicServiceAccess.callCubic(xmlRequest).toString();
            this.serviceCallLogService.finaliseCallLog(serviceCallLogDTO, xmlRequest, xmlResponse);
            Object modelResponse = this.cardUpdateResponseConverter.convertXmlToObject(xmlResponse);
            if (isSuccessResponse(modelResponse)) {
                return this.cardUpdatePrePayValueChangeConverter.convertToDto((CardUpdateResponse) modelResponse);
            }
            return this.cardUpdatePrePayValueChangeConverter.convertToDto(this.requestFailureConverter.convertXmlToModel(xmlResponse));
        }
        return null;
    }

    protected Long getCustomerId(String cardNumber) {
        CustomerDTO customerDTO = this.customerDataService.findByCardNumber(cardNumber);
        return customerDTO != null?customerDTO.getId():null;
    }
}
