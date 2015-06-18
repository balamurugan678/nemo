package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.common.utils.Converter.convert;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novacroft.nemo.common.command.SecurityQuestionCmd;
import com.novacroft.nemo.common.utils.StringUtil;
import com.novacroft.nemo.tfl.common.application_service.SecurityQuestionService;
import com.novacroft.nemo.tfl.common.command.impl.SecurityQuestionCmdImpl;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.CartSessionData;

/**
 * security question service implementation
 */
@Service(value = "securityQuestionService")
public class SecurityQuestionServiceImpl implements SecurityQuestionService {
    static final Logger logger = LoggerFactory.getLogger(SecurityQuestionServiceImpl.class);
    
    @Autowired
    protected CardDataService cardDataService;

    @Override
    public SecurityQuestionCmdImpl getSecurityQuestionDetails(Long cardId) {
        SecurityQuestionCmdImpl cmd = new SecurityQuestionCmdImpl();
            CardDTO card = cardDataService.findById(cardId);
            if (card != null) {
                cmd.setCardId(card.getId());
                convert(card, cmd);
            } else {
                cmd.setCardId(cardId);
            }
        return cmd;
    }

    @Override
    public void addSecurityQuestionDetails(SecurityQuestionCmd cmd, Long customerId, CartSessionData cartSessionData) {
        CardDTO card = null;
        if (StringUtil.isBlank(cmd.getCardNumber())) {
            cartSessionData.setSecurityQuestion(cmd.getSecurityQuestion());
            cartSessionData.setSecurityAnswer(cmd.getSecurityAnswer());
        } else {
            card = cardDataService.findByCardNumber(cmd.getCardNumber());
            convert(cmd, card);
            card.setCustomerId(customerId);
            cardDataService.createOrUpdate(card);
        }
    }

    @Override
    public SecurityQuestionCmdImpl updateSecurityQuestionDetails(SecurityQuestionCmdImpl cmd) {
        assert (cmd.getCardId() != null);
        CardDTO card = cardDataService.findById(cmd.getCardId());
        convert(cmd, card);
        cardDataService.createOrUpdate(card);
        return cmd;
    }

    @Override
    public boolean verifySecurityQuestionDetails(SecurityQuestionCmd cmd, Long cardId) {
        SecurityQuestionCmdImpl cmdFromDB = getSecurityQuestionDetails(cardId);
        if (cmdFromDB.getSecurityAnswer() == null || cmdFromDB.getSecurityQuestion() == null) {
            return false;
        }
        if (cmdFromDB.getSecurityAnswer().equals(cmd.getSecurityAnswer()) && cmdFromDB.getSecurityQuestion().equals(cmd.getSecurityQuestion())) {
            return true;
        }
        return false;
    }

}
