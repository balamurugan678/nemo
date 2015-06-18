package com.novacroft.nemo.tfl.common.converter.impl;

import org.springframework.stereotype.Component;

import com.novacroft.nemo.common.converter.impl.BaseDtoEntityConverterImpl;
import com.novacroft.nemo.tfl.common.domain.PhoneTextMessage;
import com.novacroft.nemo.tfl.common.transfer.PhoneTextMessageDTO;

@Component("phoneTextMessageConverterConverter")
public class PhoneTextMessageConverterImpl extends BaseDtoEntityConverterImpl<PhoneTextMessage, PhoneTextMessageDTO> {

    @Override
    protected PhoneTextMessageDTO getNewDto() {
        return new PhoneTextMessageDTO();
    }

}
