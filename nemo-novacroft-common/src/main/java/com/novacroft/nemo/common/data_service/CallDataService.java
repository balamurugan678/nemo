package com.novacroft.nemo.common.data_service;

import java.util.Date;
import java.util.List;

import com.novacroft.nemo.common.domain.Call;
import com.novacroft.nemo.common.transfer.CallDTO;

public interface CallDataService extends BaseDataService<Call, CallDTO> {

    List<CallDTO> findByCustomerId(Long customerId);

    List<CallDTO> findByCustomerIdDate(Long customerId, Date callDate);

}
