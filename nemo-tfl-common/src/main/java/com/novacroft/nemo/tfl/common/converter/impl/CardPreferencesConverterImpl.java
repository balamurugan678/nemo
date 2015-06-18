package com.novacroft.nemo.tfl.common.converter.impl;

import com.novacroft.nemo.common.converter.impl.BaseDtoEntityConverterImpl;
import com.novacroft.nemo.tfl.common.domain.CardPreferences;
import com.novacroft.nemo.tfl.common.transfer.CardPreferencesDTO;
import org.springframework.stereotype.Component;

/**
 * TfL card preferences converter implementation
 */
@Component(value = "cardPreferencesConverter")
public class CardPreferencesConverterImpl extends BaseDtoEntityConverterImpl<CardPreferences, CardPreferencesDTO> {
    @Override
    protected CardPreferencesDTO getNewDto() {
        return new CardPreferencesDTO();
    }
}
