package com.novacroft.nemo.common.converter.impl;

import com.novacroft.nemo.common.domain.Content;
import com.novacroft.nemo.common.transfer.ContentDTO;
import org.springframework.stereotype.Component;

/**
 * Content entity/DTO converter.
 */
@Component(value = "contentConverter")
public class ContentConverterImpl extends BaseDtoEntityConverterImpl<Content, ContentDTO> {
    @Override
    protected ContentDTO getNewDto() {
        return new ContentDTO();
    }
}
