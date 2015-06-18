package com.novacroft.nemo.mock_cubic.service;

import com.novacroft.nemo.mock_cubic.command.EditAutoLoadChangeCmd;
import com.novacroft.nemo.tfl.common.domain.cubic.AutoLoadRequest;
import org.w3c.dom.Document;

/**
 * Service for mock auto load change set up
 */
public interface EditAutoLoadChangeService {
    void saveResponse(EditAutoLoadChangeCmd cmd);

    String getAutoLoadChangeResponse(Document xmlRequest);

    String getAutoLoadChangeResponse(AutoLoadRequest autoLoadRequest);

    EditAutoLoadChangeCmd getListOfAutoLoadChangeResponses();

    EditAutoLoadChangeCmd getSavedResponse(Long cubicCardResponseId);
}
