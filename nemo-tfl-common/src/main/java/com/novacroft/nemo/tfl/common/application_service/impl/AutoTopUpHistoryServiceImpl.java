package com.novacroft.nemo.tfl.common.application_service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novacroft.nemo.tfl.common.application_service.AutoTopUpHistoryService;
import com.novacroft.nemo.tfl.common.data_service.ItemDataService;
import com.novacroft.nemo.tfl.common.transfer.AutoTopUpHistoryItemDTO;

/**
 * auto top-up history service implementation
 */
@Service(value = "autoTopUpHistoryService")
public class AutoTopUpHistoryServiceImpl implements AutoTopUpHistoryService {
    protected static final Logger logger = LoggerFactory.getLogger(AutoTopUpHistoryServiceImpl.class);

    @Autowired
    protected ItemDataService itemDataService; 

    @Override
    public List<AutoTopUpHistoryItemDTO> getAutoTopUpHistoryForOysterCard(Long cardId) {
        return itemDataService.findAllAutoTopUpsForCard(cardId);
    }

}