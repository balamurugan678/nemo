package com.novacroft.nemo.mock_cubic.service;

import com.novacroft.nemo.common.converter.XmlModelConverter;
import com.novacroft.nemo.mock_cubic.command.EditAutoLoadChangeCmd;
import com.novacroft.nemo.mock_cubic.data_access.CubicCardResponseDAO;
import com.novacroft.nemo.mock_cubic.data_service.OysterCardDataService;
import com.novacroft.nemo.mock_cubic.domain.card.CubicCardResponse;
import com.novacroft.nemo.mock_cubic.transfer.OysterCardDTO;
import com.novacroft.nemo.tfl.common.domain.cubic.AutoLoadRequest;
import com.novacroft.nemo.tfl.common.domain.cubic.AutoLoadResponse;
import com.novacroft.nemo.tfl.common.domain.cubic.RequestFailure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;

import java.util.List;

/**
 * Service for mock auto load change set up
 */
@Service("editAutoLoadChangeService")
@Transactional
public class EditAutoLoadChangeServiceImpl implements EditAutoLoadChangeService {
    private static final Logger logger = LoggerFactory.getLogger(EditAutoLoadChangeServiceImpl.class);
    public static final String CHANGE_AUTO_LOAD_CONFIGURATION_ACTION = "changeAutoLoadConfiguration";
    public static final String AUTO_LOAD_CONFIGURATION_REQUEST = "AutoLoadRequest";
    public static final String AUTO_LOAD_CONFIGURATION_SUCCESS_RESPONSE = "AutoLoadResponse";
    public static final Integer CARD_NOT_FOUND_ERROR_CODE = 40;
    public static final String CARD_NOT_FOUND_ERROR_DESCRIPTION = "CARD NOT FOUND";
    @Autowired
    protected ApplicationContext applicationContext;
    @Autowired
    protected CubicCardResponseDAO cubicCardResponseDAO;
    @Autowired
    protected XmlModelConverter<AutoLoadRequest> autoLoadRequestConverter;
    @Autowired
    protected XmlModelConverter<AutoLoadResponse> autoLoadResponseConverter;
    @Autowired
    protected XmlModelConverter<RequestFailure> requestFailureConverter;
    @Autowired
    protected OysterCardDataService oysterCardDataService;

    @Override
    public void saveResponse(EditAutoLoadChangeCmd cmd) {
        CubicCardResponse cubicCardResponse = getSavedCubicCardResponse(cmd.getPrestigeId().toString());
        if (cubicCardResponse == null) {
            cubicCardResponse = new CubicCardResponse();
        }
        cubicCardResponse.setPrestigeId(cmd.getPrestigeId().toString());
        cubicCardResponse.setResponse(getResponse(cmd));
        cubicCardResponse.setAction(CHANGE_AUTO_LOAD_CONFIGURATION_ACTION);
        cubicCardResponse.setCreated();
        cubicCardResponseDAO.createOrUpdate(cubicCardResponse);
    }

    @Override
    public String getAutoLoadChangeResponse(Document xmlRequest) {
        return getAutoLoadChangeResponseForCard(this.autoLoadRequestConverter.convertXmlToModel(xmlRequest.toString()));
    }

    @Override
    @Transactional
    public String getAutoLoadChangeResponse(AutoLoadRequest autoLoadRequest) {
        return getAutoLoadChangeResponseForCard(autoLoadRequest);
    }

    @Override
    public EditAutoLoadChangeCmd getListOfAutoLoadChangeResponses() {
        EditAutoLoadChangeCmd cmd = new EditAutoLoadChangeCmd();
        CubicCardResponse exampleCubicCardResponse = new CubicCardResponse();
        exampleCubicCardResponse.setAction(CHANGE_AUTO_LOAD_CONFIGURATION_ACTION);
        List<CubicCardResponse> responses = this.cubicCardResponseDAO.findByExample(exampleCubicCardResponse);
        cmd.setResponses(responses);
        return cmd;
    }

    @Override
    public EditAutoLoadChangeCmd getSavedResponse(Long cubicCardResponseId) {
        EditAutoLoadChangeCmd cmd = new EditAutoLoadChangeCmd();
        CubicCardResponse cubicCardResponse = this.cubicCardResponseDAO.findById(cubicCardResponseId);
        cmd.setPrestigeId(cubicCardResponse.getPrestigeId());
        if (isSuccessResponse(cubicCardResponse.getResponse())) {
            cmd.setSuccessResponse(this.autoLoadResponseConverter.convertXmlToModel(cubicCardResponse.getResponse()));
        } else {
            cmd.setFailResponse(this.requestFailureConverter.convertXmlToModel(cubicCardResponse.getResponse()));
        }
        return cmd;
    }

    protected boolean isSuccessResponse(String response) {
        return response.contains(AUTO_LOAD_CONFIGURATION_SUCCESS_RESPONSE);
    }

    protected String getResponse(EditAutoLoadChangeCmd cmd) {
        if (isSuccessResponse(cmd)) {
            cmd.getSuccessResponse().setPrestigeId(cmd.getPrestigeId());
            return this.autoLoadResponseConverter.convertModelToXml(cmd.getSuccessResponse());
        } else {
            return this.requestFailureConverter.convertModelToXml(cmd.getFailResponse());
        }
    }

    protected boolean isSuccessResponse(EditAutoLoadChangeCmd cmd) {
        return cmd.getSuccessResponse() != null && cmd.getSuccessResponse().getAvailableSlots() != null;
    }

    protected String getAutoLoadChangeResponseForCard(AutoLoadRequest autoLoadRequest) {
        String prestigeId = autoLoadRequest.getPrestigeId();
        Long autoLoadState = autoLoadRequest.getAutoLoadState().longValue();
        OysterCardDTO oysterCard = oysterCardDataService.findByCardNumber(prestigeId);
        if (oysterCard == null) {
            return getCardNotFoundResponse();
        } else {
            oysterCard.setAutoloadState(autoLoadState);
            oysterCardDataService.createOrUpdate(oysterCard);
            AutoLoadResponse response =
                    new AutoLoadResponse(prestigeId, this.cubicCardResponseDAO.getNextRequestSequenceNumber().intValue(),
                            Integer.valueOf(autoLoadState.intValue()), autoLoadRequest.getPickupLocation(),
                            Integer.valueOf(oysterCard.getCardCapability().intValue()));
            return (response == null) ? getCardNotFoundResponse() : autoLoadResponseConverter.convertModelToXml(response);
        }
    }

    protected CubicCardResponse getSavedCubicCardResponse(AutoLoadRequest autoLoadRequest) {
        return getSavedCubicCardResponse(autoLoadRequest.getPrestigeId().toString());
    }

    protected CubicCardResponse getSavedCubicCardResponse(String prestigeId) {
        CubicCardResponse exampleCubicCardResponse = new CubicCardResponse();
        exampleCubicCardResponse.setAction(CHANGE_AUTO_LOAD_CONFIGURATION_ACTION);
        exampleCubicCardResponse.setPrestigeId(prestigeId);
        return this.cubicCardResponseDAO.findByExampleUniqueResult(exampleCubicCardResponse);
    }

    protected String getCardNotFoundResponse() {
        RequestFailure requestFailure = new RequestFailure(CARD_NOT_FOUND_ERROR_CODE, CARD_NOT_FOUND_ERROR_DESCRIPTION);
        return this.requestFailureConverter.convertModelToXml(requestFailure);
    }

    protected String getConfiguredResponse(AutoLoadRequest autoLoadRequest, CubicCardResponse cubicCardResponse) {
        Object response = this.autoLoadResponseConverter.convertXmlToObject(cubicCardResponse.getResponse());
        if (response instanceof AutoLoadResponse) {
            AutoLoadResponse autoLoadResponse = (AutoLoadResponse) response;
            copyRequestAttributesToResponse(autoLoadRequest, autoLoadResponse);
            return this.autoLoadResponseConverter.convertModelToXml(autoLoadResponse);
        } else {
            return cubicCardResponse.getResponse();
        }
    }

    protected void copyRequestAttributesToResponse(AutoLoadRequest autoLoadRequest, AutoLoadResponse autoLoadResponse) {
        if (autoLoadResponse.getAutoLoadState() == null) {
            autoLoadResponse.setAutoLoadState(autoLoadRequest.getAutoLoadState());
        }
        if (autoLoadResponse.getRequestSequenceNumber() == null) {
            autoLoadResponse.setRequestSequenceNumber(this.cubicCardResponseDAO.getNextRequestSequenceNumber().intValue());
        }
        if (autoLoadResponse.getPickupLocation() == null) {
            autoLoadResponse.setPickupLocation(autoLoadRequest.getPickupLocation());
        }
    }
}
