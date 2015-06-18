package com.novacroft.nemo.tfl.common.application_service;

import com.novacroft.nemo.tfl.common.command.impl.CardPreferencesCmdImpl;

/**
 * Specification for card preferences service
 */
public interface CardPreferencesService {
    CardPreferencesCmdImpl getPreferences(Long cardId, String username);

    CardPreferencesCmdImpl getPreferences(Long cardId);

    CardPreferencesCmdImpl getPreferencesByCardNumber(String cardNumber);

    CardPreferencesCmdImpl getPreferencesByCardId(Long cardId);

    CardPreferencesCmdImpl updatePreferences(CardPreferencesCmdImpl cmd);

    Long getPreferredStationIdByCardId(CardPreferencesCmdImpl cmd);
}
