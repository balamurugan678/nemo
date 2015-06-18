package com.novacroft.nemo.tfl.common.data_service;

import java.util.List;

import com.novacroft.nemo.common.data_service.BaseDataService;
import com.novacroft.nemo.tfl.common.domain.EmailMessage;
import com.novacroft.nemo.tfl.common.transfer.EmailMessageDTO;

public interface EmailMessageDataService extends BaseDataService<EmailMessage, EmailMessageDTO> {

    List<EmailMessageDTO> findEmailMessagesToBeSent();

}
