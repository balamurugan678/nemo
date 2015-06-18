package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.common.utils.Converter.convert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novacroft.nemo.tfl.common.application_service.CardPreferencesService;
import com.novacroft.nemo.tfl.common.command.impl.CardPreferencesCmdImpl;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.CardPreferencesDataService;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.CardPreferencesDTO;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;

/**
 * Card preferences service implementation
 */
@Service("cardPreferencesService")
public class CardPreferencesServiceImpl implements CardPreferencesService {

	@Autowired
	protected CardDataService cardDataService;
	@Autowired
	protected CardPreferencesDataService cardPreferencesDataService;
	@Autowired
	protected CustomerDataService customerDataService;

	@Override
	public CardPreferencesCmdImpl getPreferencesByCardNumber(String cardNumber) {
		CardDTO card = cardDataService.findByCardNumber(cardNumber);
		assert (card != null);
		return getCardPreferences(card);
	}

    @Override
    public CardPreferencesCmdImpl getPreferencesByCardId(Long cardId) {
        CardDTO card = cardDataService.findById(cardId);
        assert (card != null);
        return getCardPreferences(card);
    }

	public CardPreferencesCmdImpl getCardPreferences(CardDTO card) {
		CardPreferencesCmdImpl cardPreferncesCmd = new CardPreferencesCmdImpl();
		CustomerDTO customerDTO = this.customerDataService.findById(card
				.getCustomerId());
		cardPreferncesCmd.setEmailAddress(customerDTO.getEmailAddress());
		CardPreferencesCmdImpl cardPreferncesCmdForID = getPreferences(card.getId());
		convert(cardPreferncesCmdForID, cardPreferncesCmd);
		cardPreferncesCmd.setCardNumber(card.getCardNumber());
		return cardPreferncesCmd;
	}

	@Override
	public CardPreferencesCmdImpl getPreferences(Long cardId) {
		CardPreferencesCmdImpl cmd = new CardPreferencesCmdImpl();
		CardPreferencesDTO cardPreferences = this.cardPreferencesDataService
				.findByCardId(cardId);
		if (cardPreferences != null) {
			cmd.setCardPreferencesId(cardPreferences.getId());
			convert(cardPreferences, cmd);
		} else {
			cmd.setCardId(cardId);
		}

		return cmd;
	}

	@Override
	public CardPreferencesCmdImpl getPreferences(Long cardId, String username) {
		CardPreferencesCmdImpl cmd = getPreferences(cardId);
		CustomerDTO customerDTO = this.customerDataService
				.findByUsernameOrEmail(username);
		cmd.setEmailAddress(customerDTO.getEmailAddress());
        CardDTO card = cardDataService.findById(cardId);
        cmd.setCardNumber(card.getCardNumber());
		return cmd;
	}

	@Override
	public CardPreferencesCmdImpl updatePreferences(CardPreferencesCmdImpl cmd) {
		CardPreferencesDTO preferencesDTO = null;
		if (cmd.getCardPreferencesId() != null) {
			preferencesDTO = this.cardPreferencesDataService.findById(cmd.getCardPreferencesId());
        } else if (cmd.getCardId() != null) {
            preferencesDTO = this.cardPreferencesDataService.findByCardId(cmd.getCardId());
        }
		if (cmd.getStationId() == null) {
            preferencesDTO.setStationId(null);
            preferencesDTO.setNullable(true);
        }
        
        if (preferencesDTO == null) {
			preferencesDTO = new CardPreferencesDTO();
		}
		convert(cmd, preferencesDTO);
		preferencesDTO = this.cardPreferencesDataService.createOrUpdate(preferencesDTO);
		convert(preferencesDTO, cmd);
		cmd.setCardPreferencesId(preferencesDTO.getId());
		return cmd;
	}

	@Override
    public Long getPreferredStationIdByCardId(CardPreferencesCmdImpl cmd) {
        return this.cardPreferencesDataService.getPreferredStationIdByCardId(cmd.getCardId());

	}
}
