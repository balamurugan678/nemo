package com.novacroft.nemo.tfl.common.converter.impl;

import org.springframework.stereotype.Component;

import com.novacroft.nemo.common.converter.impl.BaseDtoEntityConverterImpl;
import com.novacroft.nemo.tfl.common.domain.EmailMessage;
import com.novacroft.nemo.tfl.common.transfer.EmailMessageDTO;

@Component("emailMessageConverterConverter")
public class EmailMessageConverterImpl extends BaseDtoEntityConverterImpl<EmailMessage, EmailMessageDTO> {

    @Override
    protected EmailMessageDTO getNewDto() {
        return new EmailMessageDTO();
    }

}
