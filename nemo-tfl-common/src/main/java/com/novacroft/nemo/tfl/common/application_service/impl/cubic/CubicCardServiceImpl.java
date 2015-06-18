package com.novacroft.nemo.tfl.common.application_service.impl.cubic;

import com.novacroft.nemo.common.application_service.impl.BaseService;
import com.novacroft.nemo.common.domain.cubic.PrePayTicketSlot;
import com.novacroft.nemo.common.domain.cubic.PrePayValue;
import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.tfl.common.application_service.cubic.CubicCardService;
import com.novacroft.nemo.tfl.common.application_service.cubic.GetCardService;
import com.novacroft.nemo.tfl.common.constant.ContentCode;
import com.novacroft.nemo.tfl.common.service_access.cubic.CubicServiceAccess;
import com.novacroft.nemo.tfl.common.transfer.cubic.CardInfoResponseV2DTO;
import com.novacroft.nemo.tfl.common.transfer.cubic.CardStatusDTO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.novacroft.nemo.common.utils.StringUtil.isNotEmpty;

/**
 * Cubic card service implementation
 */
@Service("cubicCardService")
public class CubicCardServiceImpl extends BaseService implements CubicCardService {
    @Autowired
    protected GetCardService getCardService;
    @Autowired
    protected CubicServiceAccess cubicServiceAccess;

    @Override
    public String checkCardPrePayTicketStatusReturnMessage(String prestigeId, CardInfoResponseV2DTO cardInfoResponseV2DTO) {
        StringBuffer message = new StringBuffer();
        for (PrePayTicketSlot prePayTicketSlot : cardInfoResponseV2DTO.getPptDetails().getPptSlots()) {
            if (isPrePaidTicketExpired(prePayTicketSlot)) {
                message.append(
                        getContent("CubicCardServiceImpl.productSlotMessage.text", prePayTicketSlot.getSlotNumber().toString(),
                                getContent("CubicCardServiceImpl.expiredMessage.text"), prePayTicketSlot.getProduct(),
                                prePayTicketSlot.getStartDate(), prePayTicketSlot.getExpiryDate(), prePayTicketSlot.getZone()));
            } else {
                message.append(
                        getContent("CubicCardServiceImpl.productSlotMessage.text", prePayTicketSlot.getSlotNumber().toString(),
                                StringUtils.EMPTY, prePayTicketSlot.getProduct(), prePayTicketSlot.getStartDate(),
                                prePayTicketSlot.getExpiryDate(), prePayTicketSlot.getZone()));
            }
        }
        if (isNotEmpty(message.toString())) {
            message.append(getContent("CubicCardServiceImpl.travellingInZonesMessage.text"));
        }
        return message.toString();
    }

    @Override
    public String checkCardPrePayValueStatusReturnMessage(String prestigeId, CardInfoResponseV2DTO cardInfoResponseV2DTO) {
        PrePayValue ppvDetails = cardInfoResponseV2DTO.getPpvDetails();
        if (ppvDetails.getBalance() > 0) {
            return ppvDetails.getBalance().toString();
        }
        return getContent(ContentCode.ADD_PAY_AS_YOU_GO.errorCode());
    }

    @Override
    public CardStatusDTO checkCardStatusReturnMessage(String prestigeId) {
        CardInfoResponseV2DTO cardInfoResponseV2DTO = getCardService.getCard(prestigeId);
        CardStatusDTO cardStatus = new CardStatusDTO();
        cardStatus.setPrePayTicketMessage(checkCardPrePayTicketStatusReturnMessage(prestigeId, cardInfoResponseV2DTO));
        cardStatus.setPrePayValueMessage(checkCardPrePayValueStatusReturnMessage(prestigeId, cardInfoResponseV2DTO));
        return cardStatus;
    }

    protected boolean isPrePaidTicketExpired(PrePayTicketSlot prePayTicketSlot) {
        return prePayTicketSlot != null && isNotEmpty(prePayTicketSlot.getExpiryDate()) &&
                DateUtil.parse(prePayTicketSlot.getExpiryDate()).before(new Date());
    }
}
