package com.novacroft.nemo.tfl.common.data_service.impl;

import com.novacroft.nemo.common.data_service.BaseDataServiceImpl;
import com.novacroft.nemo.tfl.common.converter.impl.AutoLoadChangeSettlementConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.AutoLoadChangeSettlementDAO;
import com.novacroft.nemo.tfl.common.data_service.AutoLoadChangeSettlementDataService;
import com.novacroft.nemo.tfl.common.domain.AutoLoadChangeSettlement;
import com.novacroft.nemo.tfl.common.transfer.AutoLoadChangeSettlementDTO;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Settlement (for Auto Load Changes) data service specification
 */
@Service("autoLoadChangeSettlementDataService")
@Transactional(readOnly = true)
public class AutoLoadChangeSettlementDataServiceImpl
        extends BaseDataServiceImpl<AutoLoadChangeSettlement, AutoLoadChangeSettlementDTO>
        implements AutoLoadChangeSettlementDataService {
    @Override
    public AutoLoadChangeSettlementDTO findByRequestSequenceNumberAndCardNumber(Integer requestSequenceNumber,
                                                                                String cardNumber) {
        final String hsql = "select s" +
                " from AutoLoadChangeSettlement s, Card c" +
                " where c.id = s.cardId and s.requestSequenceNumber = ? and c.cardNumber = ?";
        AutoLoadChangeSettlement result = dao.findByQueryUniqueResult(hsql, requestSequenceNumber, cardNumber);
        return (result != null) ? this.converter.convertEntityToDto(result) : null;
    }

    @Override
    public AutoLoadChangeSettlementDTO findLatestByCardId(Long cardId) {
        final String hsql = "from AutoLoadChangeSettlement s where s.cardId = ? order by s.settlementDate desc, s.id";
        @SuppressWarnings("unchecked")
        List<AutoLoadChangeSettlement> settlements = dao.findByQueryWithLimit(hsql, 0, 1, cardId);
        return (!CollectionUtils.isEmpty(settlements)) ? this.converter.convertEntityToDto(settlements.get(0)) : null;
    }

    @Override
    public List<AutoLoadChangeSettlementDTO> findByOrderId(Long orderId) {
        final String hsql = "from AutoLoadChangeSettlement s where s.orderId = ?";
        return convert(dao.findByQuery(hsql, orderId));
    }

    @Override
    public AutoLoadChangeSettlement getNewEntity() {
        return new AutoLoadChangeSettlement();
    }

    @Autowired
    public void setDao(AutoLoadChangeSettlementDAO dao) {
        this.dao = dao;
    }

    @Autowired
    public void setConverter(AutoLoadChangeSettlementConverterImpl converter) {
        this.converter = converter;
    }
}