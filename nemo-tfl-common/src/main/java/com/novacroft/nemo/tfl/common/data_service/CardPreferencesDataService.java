package com.novacroft.nemo.tfl.common.data_service;

import com.novacroft.nemo.common.data_service.BaseDataService;
import com.novacroft.nemo.tfl.common.domain.CardPreferences;
import com.novacroft.nemo.tfl.common.transfer.CardPreferencesDTO;

import java.util.List;

/**
 * Card preferences data service specification
 */
public interface CardPreferencesDataService extends BaseDataService<CardPreferences, CardPreferencesDTO> {
    
    CardPreferencesDTO findByCardId(Long cardId);
    
    List<CardPreferencesDTO> findByEmailPreference(String emailPreference);

    Long getPreferredStationIdByCardId(Long cardId);
}
