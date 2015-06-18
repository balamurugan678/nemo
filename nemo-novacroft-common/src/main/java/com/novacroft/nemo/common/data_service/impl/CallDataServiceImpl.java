package com.novacroft.nemo.common.data_service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.novacroft.nemo.common.converter.impl.CallConverterImpl;
import com.novacroft.nemo.common.data_access.CallDAO;
import com.novacroft.nemo.common.data_service.BaseDataServiceImpl;
import com.novacroft.nemo.common.data_service.CallDataService;
import com.novacroft.nemo.common.domain.Call;
import com.novacroft.nemo.common.transfer.CallDTO;

@Service(value = "callDataService")
@Transactional(readOnly = true)
public class CallDataServiceImpl extends BaseDataServiceImpl<Call, CallDTO> implements CallDataService {
    @Autowired
    public void setDao(CallDAO dao) {
        this.dao = dao;
    }

    @Autowired
    public void setConverter(CallConverterImpl converter) {
        this.converter = converter;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<CallDTO> findByCustomerId(Long customerId) {
        final String hsql = "from Call c where c.customerId = ?";
        List<Call> calls = dao.findByQuery(hsql, customerId);
        return convert(calls);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<CallDTO> findByCustomerIdDate(Long customerId, Date callDate) {
        final String hsql = "from Call c where c.customerId = ? and createdDateTime = ?";
        List<Call> calls = dao.findByQuery(hsql, customerId, callDate);
        return convert(calls);
    }

    @Override
    public Call getNewEntity() {
        return new Call();
    }


}
