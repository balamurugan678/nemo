package com.novacroft.nemo.common.converter.impl;

import com.novacroft.nemo.common.domain.Email;
import com.novacroft.nemo.common.transfer.EmailDTO;
import org.springframework.stereotype.Component;

/**
 * Email entity/DTO converter. Automatically created.
 */

@Component(value = "emailConverter")
public class EmailConverterImpl extends BaseDtoEntityConverterImpl<Email, EmailDTO> {
    @Override
    public EmailDTO getNewDto() {
        return new EmailDTO();
    }
}
