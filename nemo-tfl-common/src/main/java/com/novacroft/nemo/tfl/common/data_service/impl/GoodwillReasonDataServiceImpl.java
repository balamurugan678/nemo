package com.novacroft.nemo.tfl.common.data_service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.novacroft.nemo.common.data_service.BaseDataServiceImpl;
import com.novacroft.nemo.tfl.common.converter.impl.GoodwillReasonConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.GoodwillReasonDAO;
import com.novacroft.nemo.tfl.common.data_service.GoodwillReasonDataService;
import com.novacroft.nemo.tfl.common.domain.GoodwillReason;
import com.novacroft.nemo.tfl.common.transfer.GoodwillReasonDTO;

/**
 * Goodwill reason data service implementation
 */
@Service(value = "goodwillReasonDataService")
@Transactional(readOnly = true)
public class GoodwillReasonDataServiceImpl extends BaseDataServiceImpl<GoodwillReason, GoodwillReasonDTO> implements GoodwillReasonDataService {
    protected static final String HQL_FIND_BY_TYPE = "from GoodwillReason gr where gr.type = ?";
    protected static final String HQL_FIND_BY_PRICE = "select payg.goodwillReason from GoodwillPayment payg where payg.ticketPrice = ?";
    protected static final String HQL_FIND_BY_REASON_ID = "from GoodwillReason gr where gr.reasonId = ?";
    
    @Autowired
    public void setDao(GoodwillReasonDAO dao) {
        this.dao = dao;
    }

    @Autowired
    public void setConverter(GoodwillReasonConverterImpl converter) {
        this.converter = converter;
    }

    @Override
    public GoodwillReason getNewEntity() {
        return new GoodwillReason();
    }

    @Override
    public GoodwillReasonDTO findByTicketPrice(Integer price) {
        if (price > 0) {
            GoodwillReason goodwillPayment = findGoodwillPayment(price);
            if (goodwillPayment == null) {
                goodwillPayment = findZeroValueGoodwillPayment();
            }
            return convertGoodwillPaymentToDTO(goodwillPayment);
        }
        return null;

    }

    @Override
    public List<GoodwillReasonDTO> findByType(String type) {
        return convert(dao.findByQuery(HQL_FIND_BY_TYPE, type));
    }

    public GoodwillReason findGoodwillPayment(Integer price) {
        return dao.findByQueryUniqueResult(HQL_FIND_BY_PRICE, price);
    }
    
    public GoodwillReasonDTO findByReasonId(Long reasonId) {
        GoodwillReason goodwillReason = dao.findByQueryUniqueResult(HQL_FIND_BY_REASON_ID, reasonId);
        return convertGoodwillPaymentToDTO(goodwillReason);
    }

    public GoodwillReason findZeroValueGoodwillPayment() {
        return dao.findByQueryUniqueResult(HQL_FIND_BY_PRICE, 0);
    }

    public GoodwillReasonDTO convertGoodwillPaymentToDTO(GoodwillReason goodwillPayment) {
        return this.converter.convertEntityToDto(goodwillPayment);
    }

}
