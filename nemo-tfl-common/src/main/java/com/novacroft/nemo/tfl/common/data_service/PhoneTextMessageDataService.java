package com.novacroft.nemo.tfl.common.data_service;

import java.util.List;

import com.novacroft.nemo.common.data_service.BaseDataService;
import com.novacroft.nemo.tfl.common.domain.PhoneTextMessage;
import com.novacroft.nemo.tfl.common.transfer.PhoneTextMessageDTO;

public interface PhoneTextMessageDataService extends BaseDataService<PhoneTextMessage, PhoneTextMessageDTO> {

    List<PhoneTextMessageDTO> findPhoneTextMessagesToBeSent();

}
