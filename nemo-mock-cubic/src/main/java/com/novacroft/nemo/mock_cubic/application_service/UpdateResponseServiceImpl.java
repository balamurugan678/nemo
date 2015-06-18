package com.novacroft.nemo.mock_cubic.application_service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novacroft.nemo.common.converter.XmlModelConverter;
import com.novacroft.nemo.mock_cubic.command.AddRequestCmd;
import com.novacroft.nemo.mock_cubic.command.RemoveRequestCmd;
import com.novacroft.nemo.tfl.common.domain.cubic.CardRemoveUpdateResponse;
import com.novacroft.nemo.tfl.common.domain.cubic.CardUpdateResponse;
import com.novacroft.nemo.tfl.common.domain.cubic.RequestFailure;

@Service(value = "updateResponseService")
public class UpdateResponseServiceImpl implements UpdateResponseService {

    @Autowired
    protected XmlModelConverter<RequestFailure> requestFailureConverter;
    @Autowired
    protected XmlModelConverter<CardUpdateResponse> cardUpdateResponseConverter;
    @Autowired
    protected XmlModelConverter<CardRemoveUpdateResponse> cardRemoveUpdateResponseConverter;

    protected static final Logger logger = LoggerFactory.getLogger(UpdateResponseServiceImpl.class);
    protected static final int MAXIMUM_AVAILABLE_SLOTS = 100;
    public static final Integer CARD_NOT_FOUND_ERROR_CODE = 40;
    public static final String CARD_NOT_FOUND_ERROR_DESCRIPTION = "CARD NOT FOUND";
    public static final Integer NO_AVAILABLE_PPT_SLOT_ERROR_CODE = 60;
    public static final String NO_AVAILABLE_PPT_SLOT_ERROR_DESCRIPTION = "NO AVAILABLE PPT SLOT";
    public static final Integer REMOVAL_REQUEST_SEQUENCE_NUMBER_NOT_FOUND_ERROR_CODE = 140;
    public static final String REMOVAL_REQUEST_SEQUENCE_NUMBER_NOT_FOUND_ERROR_DESCRIPTION = "REMOVAL REQUEST SEQUENCE NUMBER NOT FOUND";
    public static final Integer PPV_AMOUNT_WOULD_EXCEED_MAX_ALLOWED_PPV_CODE = 70;
    public static final String PPV_AMOUNT_WOULD_EXCEED_MAX_ALLOWED_PPV_DESCRIPTION = "PPV AMOUNT WOULD EXCEED MAX ALLOWED PPV";

    @Override
    public String generateAddSuccessResponse(AddRequestCmd cmd) {
        /*
         * ADD 
         * <CardUpdateResponse> 
         *      <PrestigeID>123456789</PrestigeID> 
         *      <RequestSequenceNumber>1234</RequestSequenceNumber> 
         *      <LocationInfo>
         *          <PickupLocation>340</PickupLocation> 
         *          <AvailableSlots>100</AvailableSlots> 
         *      </LocationInfo> 
         * </CardUpdateResponse>
         */
        
        CardUpdateResponse cardUpdateResponse = new CardUpdateResponse(cmd.getPrestigeId(), cmd.getRequestSequenceNumber().intValue());
        String response = cardUpdateResponseConverter.convertModelToXml(cardUpdateResponse);
        logger.debug(response);
        return response;
    }

    @Override
    public String generateRemoveSuccessResponse(RemoveRequestCmd cmd) {
        /*
         * REMOVE 
         * <CardUpdateResponse> 
         *      <PrestigeID>123456789</PrestigeID> 
         *      <RequestSequenceNumber>1235</RequestSequenceNumber>
         *      <RemovedRequestSequenceNumber>1221</RemovedRequestSequenceNumber> 
         * </CardUpdateResponse>
         */

        cmd.setRemovedRequestSequenceNumber(cmd.getOriginalRequestSequenceNumber());
        
        CardRemoveUpdateResponse cardRemoveUpdateResponse = new CardRemoveUpdateResponse(cmd.getPrestigeId(), Integer.valueOf(cmd.getRequestSequenceNumber().intValue()), Integer.valueOf(cmd.getRemovedRequestSequenceNumber().intValue()));
        String response = cardRemoveUpdateResponseConverter.convertModelToXml(cardRemoveUpdateResponse);
        logger.debug(response);
        return response;
    }

    @Override
    public String generateErrorResponse(Integer errorCode, String errorDescription) {
        /*
         * <RequestFailure> 
         *      <ErrorCode>40</ErrorCode> 
         *      <ErrorDescription><![CDATA[CARD NOT FOUND]]></ErrorDescription> 
         * </RequestFailure>
         */
        RequestFailure requestFailure = new RequestFailure(errorCode, errorDescription);
        String response = requestFailureConverter.convertModelToXml(requestFailure);
        logger.debug(response);
        return response;
    }
}
