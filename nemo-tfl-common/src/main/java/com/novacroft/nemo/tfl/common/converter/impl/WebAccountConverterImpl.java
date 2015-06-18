package com.novacroft.nemo.tfl.common.converter.impl;

import com.novacroft.nemo.common.converter.impl.BaseDtoEntityConverterImpl;
import com.novacroft.nemo.tfl.common.domain.WebAccount;
import com.novacroft.nemo.tfl.common.transfer.WebAccountDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Transform between web account entity and DTO.
 */
@Component
public class WebAccountConverterImpl extends BaseDtoEntityConverterImpl<WebAccount, WebAccountDTO> {
    protected static final Logger logger = LoggerFactory.getLogger(WebAccountConverterImpl.class);

    @Override
    protected WebAccountDTO getNewDto() {
        return new WebAccountDTO();
    }
}
