package com.novacroft.nemo.tfl.common.application_service.impl.cubic;

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.tfl.common.application_service.cubic.CardRemoveUpdateService;
import com.novacroft.nemo.tfl.common.data_service.cubic.CardRemoveUpdateDataService;
import com.novacroft.nemo.tfl.common.service_access.cubic.CubicServiceAccess;
import com.novacroft.nemo.tfl.common.transfer.CardUpdateResponseDTO;
import com.novacroft.nemo.tfl.common.transfer.cubic.CardRemoveUpdateRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.novacroft.nemo.tfl.common.constant.SystemParameterCode.CUBIC_PASSWORD;
import static com.novacroft.nemo.tfl.common.constant.SystemParameterCode.CUBIC_USERID;

@Service("cardRemoveUpdateService")
public class CardRemoveUpdateServiceImpl extends BaseCubicService implements CardRemoveUpdateService {
    @Autowired
    protected SystemParameterService systemParameterService;
    @Autowired
    protected CardRemoveUpdateDataService cardRemoveUpdateDataService;
    @Autowired
    protected CubicServiceAccess cubicServiceAccess;

    @Override
    public StringBuffer removePendingUpdate(String xmlRequest) {
        return this.cubicServiceAccess.callCubic(xmlRequest);
    }

    @Override
    public CardUpdateResponseDTO removePendingUpdate(String cardNumber, Long originatingRequestSequenceNumber) {
        CardRemoveUpdateRequestDTO requestDTO = new CardRemoveUpdateRequestDTO(cardNumber, originatingRequestSequenceNumber,
                systemParameterService.getParameterValue(CUBIC_USERID.code()),
                systemParameterService.getParameterValue(CUBIC_PASSWORD.code()));
        return cardRemoveUpdateDataService.removePendingUpdate(requestDTO);
    }
}
