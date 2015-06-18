package com.novacroft.nemo.common.data_service;

import com.novacroft.nemo.common.domain.ApplicationEvent;
import com.novacroft.nemo.common.transfer.ApplicationEventDTO;

import java.util.List;

public interface ApplicationEventDataService extends BaseDataService<ApplicationEvent, ApplicationEventDTO> {

    List<ApplicationEventDTO> findByCustomerId(Long customerId);

    List<ApplicationEventDTO> findByEventId(Long eventId);

    List<ApplicationEventDTO> findAllCustomerEventsByCustomerId(Long customerId);

}
