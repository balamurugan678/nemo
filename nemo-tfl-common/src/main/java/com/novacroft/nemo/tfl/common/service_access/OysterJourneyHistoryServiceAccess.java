package com.novacroft.nemo.tfl.common.service_access;

import com.novacroft.tfl.web_service.model.oyster_journey_history.GetHistory;
import com.novacroft.tfl.web_service.model.oyster_journey_history.GetHistoryResponse;
import com.novacroft.tfl.web_service.model.oyster_journey_history.InsertSyntheticOysterTap;
import com.novacroft.tfl.web_service.model.oyster_journey_history.InsertSyntheticOysterTapResponse;

/**
 * Oyster Journey web service access
 */
public interface OysterJourneyHistoryServiceAccess {
    GetHistoryResponse getHistory(GetHistory request);
    InsertSyntheticOysterTapResponse insertSyntheticTap(InsertSyntheticOysterTap request);
    

    void setOnlineTimeout();

    void setBatchTimeout();
}
