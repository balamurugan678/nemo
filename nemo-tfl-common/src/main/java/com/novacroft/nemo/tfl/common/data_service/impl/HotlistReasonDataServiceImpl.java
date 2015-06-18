package com.novacroft.nemo.tfl.common.data_service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.novacroft.nemo.common.data_service.BaseDataServiceImpl;
import com.novacroft.nemo.tfl.common.converter.impl.HotlistReasonConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.HotlistReasonDAO;
import com.novacroft.nemo.tfl.common.data_service.HotlistReasonDataService;
import com.novacroft.nemo.tfl.common.domain.HotlistReason;
import com.novacroft.nemo.tfl.common.transfer.HotlistReasonDTO;

@Service("hotlistReasonDataService")
@Transactional(readOnly = true)
public class HotlistReasonDataServiceImpl extends BaseDataServiceImpl<HotlistReason, HotlistReasonDTO> implements HotlistReasonDataService {

    @Autowired
    public void setDao(HotlistReasonDAO dao) {
        this.dao = dao;
    }

    @Autowired
    public void setConverter(HotlistReasonConverterImpl converter) {
        this.converter = converter;
    }

    @Override
    public HotlistReason getNewEntity() {
        return new HotlistReason();
    }

    @Override
    public HotlistReasonDTO findByDescription(String description){
    	  final String hsql = "from HotlistReason p where p.description = ?";
          HotlistReason hotlistReason = dao.findByQueryUniqueResult(hsql, new Object[] { description });
          return (hotlistReason != null) ? this.converter.convertEntityToDto(hotlistReason) : null;
    }
   

}
