package com.novacroft.nemo.common.data_service.impl;

import com.novacroft.nemo.common.converter.impl.CallTypeConverterImpl;
import com.novacroft.nemo.common.data_access.CallTypeDAO;
import com.novacroft.nemo.common.data_service.BaseDataServiceImpl;
import com.novacroft.nemo.common.data_service.CallTypeDataService;
import com.novacroft.nemo.common.domain.CallType;
import com.novacroft.nemo.common.transfer.CallTypeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "callTypeDataService")
@Transactional(readOnly = true)
public class CallTypeDataServiceImpl extends BaseDataServiceImpl<CallType, CallTypeDTO> implements CallTypeDataService {
    @Autowired
    public void setDao(CallTypeDAO dao) {
        this.dao = dao;
    }

    @Autowired
    public void setConverter(CallTypeConverterImpl converter) {
        this.converter = converter;
    }

    @Override
    public CallType getNewEntity() {
        return new CallType();
    }

}
