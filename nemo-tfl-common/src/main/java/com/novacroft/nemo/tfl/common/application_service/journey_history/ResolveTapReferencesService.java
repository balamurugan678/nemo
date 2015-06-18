package com.novacroft.nemo.tfl.common.application_service.journey_history;

import java.util.List;

import com.novacroft.nemo.tfl.common.transfer.oyster_journey_history.TapDTO;

public interface ResolveTapReferencesService {

    void resolveReferences(List<TapDTO> taps);

}
