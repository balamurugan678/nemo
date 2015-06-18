package com.novacroft.nemo.tfl.common.converter.impl;

import com.novacroft.nemo.common.converter.impl.BaseDtoEntityConverterImpl;
import com.novacroft.nemo.tfl.common.domain.Contact;
import com.novacroft.nemo.tfl.common.transfer.ContactDTO;
import org.springframework.stereotype.Component;

/**
 * Transform between contact entity and DTO.
 */
@Component(value = "contactConverter")
public class ContactConverterImpl extends BaseDtoEntityConverterImpl<Contact, ContactDTO> {
    @Override
    protected ContactDTO getNewDto() {
        return new ContactDTO();
    }
}
