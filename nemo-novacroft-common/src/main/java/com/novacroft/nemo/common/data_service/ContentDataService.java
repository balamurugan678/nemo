package com.novacroft.nemo.common.data_service;

import com.novacroft.nemo.common.domain.Content;
import com.novacroft.nemo.common.transfer.ContentDTO;

import java.util.Date;

/**
 * Content data service specification.
 */
public interface ContentDataService extends BaseDataService<Content, ContentDTO> {
    ContentDTO findByLocaleAndCode(String code, String locale);

    Date findLatestCreatedDateTime();

    Date findLatestModifiedDateTime();
}
