package com.novacroft.nemo.tfl.common.data_service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.novacroft.nemo.common.data_service.BaseDataServiceImpl;
import com.novacroft.nemo.tfl.common.converter.impl.PayAsYouGoConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.PayAsYouGoDAO;
import com.novacroft.nemo.tfl.common.data_service.PayAsYouGoDataService;
import com.novacroft.nemo.tfl.common.domain.PayAsYouGo;
import com.novacroft.nemo.tfl.common.transfer.PayAsYouGoDTO;

/**
 * Pay as you go data service implementation
 */
@Service(value = "payAsYouGoDataService")
@Transactional(readOnly = true)
public class PayAsYouGoDataServiceImpl extends BaseDataServiceImpl<PayAsYouGo, PayAsYouGoDTO> implements PayAsYouGoDataService {
    @Autowired
    public void setDao(PayAsYouGoDAO dao) {
        this.dao = dao;
    }

    @Autowired
    public void setConverter(PayAsYouGoConverterImpl converter) {
        this.converter = converter;
    }

    @Override
    public PayAsYouGo getNewEntity() {
        return new PayAsYouGo();
    }

    @Override
    public PayAsYouGoDTO findByTicketPrice(Integer price) {
        PayAsYouGo payAsYouGo = findPayAsYouGo(price);
        if (payAsYouGo == null) {
            payAsYouGo = findZeroValuePayAsYouGo();
        }
        return convertPayAsYouGoToDTO(payAsYouGo);
    }

    public PayAsYouGo findPayAsYouGo(Integer price) {
        final String hsql = "from PayAsYouGo payg where payg.ticketPrice = ?";
        return dao.findByQueryUniqueResult(hsql, price);
    }

    public PayAsYouGo findZeroValuePayAsYouGo() {
        final String hsql = "from PayAsYouGo payg where payg.ticketPrice = ?";
        return dao.findByQueryUniqueResult(hsql, 0);
    }

    public PayAsYouGoDTO convertPayAsYouGoToDTO(PayAsYouGo payAsYouGo) {
        return this.converter.convertEntityToDto(payAsYouGo);
    }

}
