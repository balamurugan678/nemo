package com.novacroft.nemo.common.converter.impl;

import com.novacroft.nemo.common.domain.Emails;
import com.novacroft.nemo.common.transfer.EmailsDTO;
import org.springframework.stereotype.Component;

/**
 * Emails entity/DTO converter.
 * Automatically created.
 */

@Component(value = "emailsConverter")
public class EmailsConverterImpl extends BaseDtoEntityConverterImpl<Emails, EmailsDTO> {
    @Override
    public EmailsDTO getNewDto() {
        return new EmailsDTO();
    }
}