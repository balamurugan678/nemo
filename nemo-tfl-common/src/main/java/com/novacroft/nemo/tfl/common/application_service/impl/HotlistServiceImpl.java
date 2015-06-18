package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.tfl.common.constant.CubicConstant.HOT_LISTED_CARD_CAPABILITY;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novacroft.nemo.tfl.common.application_service.HotlistService;
import com.novacroft.nemo.tfl.common.application_service.cubic.GetCardService;
import com.novacroft.nemo.tfl.common.transfer.cubic.CardInfoResponseV2DTO;

@Service("hotlistService")
public class HotlistServiceImpl implements HotlistService{
	protected static final Logger logger = LoggerFactory.getLogger(HotlistServiceImpl.class);
	
	@Autowired
	protected CardServiceImpl cardService;
	@Autowired
	protected GetCardService getCardService;
	
    @Override
    public boolean isCardHotlisted(String cardNumber) {
    	Long cardId = cardService.getCardIdFromCardNumber(cardNumber);
    	return (isCardHotListedInCubic(cardNumber) || (cardService.getCardDTOById(cardId).getHotlistReason() != null));
    }
    
    @Override
    public boolean isCardHotListedInCubic(String cardNumber) {
        try {
            CardInfoResponseV2DTO cardInfoResponseV2DTO = getCardService.getCard(cardNumber);
            return HOT_LISTED_CARD_CAPABILITY == cardInfoResponseV2DTO.getCardCapability();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }

}
