package com.novacroft.nemo.tfl.common.data_service.impl;

import com.novacroft.nemo.common.data_service.BaseDataServiceImpl;
import com.novacroft.nemo.common.exception.DataServiceException;
import com.novacroft.nemo.tfl.common.constant.PrivateError;
import com.novacroft.nemo.tfl.common.converter.impl.CardPreferencesConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.CardPreferencesDAO;
import com.novacroft.nemo.tfl.common.data_service.CardPreferencesDataService;
import com.novacroft.nemo.tfl.common.domain.CardPreferences;
import com.novacroft.nemo.tfl.common.transfer.CardPreferencesDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * TfL Card preferences data service implementation.
 */
@Service(value = "cardPreferencesDataService")
@Transactional(readOnly = true)
public class CardPreferencesDataServiceImpl extends BaseDataServiceImpl<CardPreferences, CardPreferencesDTO>
        implements CardPreferencesDataService {
    static final Logger logger = LoggerFactory.getLogger(CardPreferencesDataServiceImpl.class);

    @Override
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public CardPreferencesDTO findByCardId(Long cardId) {
        final String hsql = "select cp from CardPreferences cp where cp.cardId = ?";
        List<CardPreferences> results = dao.findByQuery(hsql, cardId);
        if (results.size() > 1) {
            String msg = String.format(PrivateError.MORE_THAN_ONE_RECORD_FOR_CARD_ID.message(), cardId);
            logger.error(msg);
            throw new DataServiceException(msg);
        }
        if (results.iterator().hasNext()) {
            return this.converter.convertEntityToDto(results.iterator().next());
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public Long getPreferredStationIdByCardId(Long cardId) {
        final String hsql = "select cp from CardPreferences cp where cp.cardId=?  ";
        List<CardPreferences> results = dao.findByQuery(hsql, cardId);
        if (results.iterator().hasNext()) {
            return (results.iterator().next().getStationId());
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CardPreferencesDTO> findByEmailPreference(String emailPreference) {
        CardPreferences cardPreference = new CardPreferences();
        cardPreference.setEmailFrequency(emailPreference);
        cardPreference.setStatementTermsAccepted(true);
        List<CardPreferences> results = dao.findByExample(cardPreference);
        if (results != null) {
            List<CardPreferencesDTO> dtoResult = new ArrayList<CardPreferencesDTO>();
            for (CardPreferences journeyHistory : results) {
                dtoResult.add(converter.convertEntityToDto(journeyHistory));
            }
            return dtoResult;
        }
        return null;
    }

    @Override
    public CardPreferences getNewEntity() {
        return new CardPreferences();
    }

    @Autowired
    public void setDao(CardPreferencesDAO dao) {
        this.dao = dao;
    }

    @Autowired
    public void setConverter(CardPreferencesConverterImpl converter) {
        this.converter = converter;
    }
}
