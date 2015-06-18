package com.novacroft.nemo.tfl.services.application_service;

import com.novacroft.nemo.tfl.services.transfer.Station;

import java.util.List;

/**
 * Application service for external stations service
 */
public interface StationService {
    List<Station> getStations();
    List<Station> getActiveStations();
    Station findStationForOutstandingOrder(Long externalCustomerId, Long externalCardId);
}
