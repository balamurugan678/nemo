package com.novacroft.nemo.tfl.common.data_service.impl;

import com.novacroft.nemo.common.data_service.BaseDataServiceImpl;
import com.novacroft.nemo.common.exception.DataServiceException;
import com.novacroft.nemo.tfl.common.constant.PrivateError;
import com.novacroft.nemo.tfl.common.converter.impl.AdHocLoadSettlementConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.AdHocLoadSettlementDAO;
import com.novacroft.nemo.tfl.common.data_service.AdHocLoadSettlementDataService;
import com.novacroft.nemo.tfl.common.domain.AdHocLoadSettlement;
import com.novacroft.nemo.tfl.common.transfer.AdHocLoadSettlementDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Settlement (by Payment Card) data service implementation
 */
@Service("adHocLoadSettlementDataService")
public class AdHocLoadSettlementDataServiceImpl extends BaseDataServiceImpl<AdHocLoadSettlement, AdHocLoadSettlementDTO>
        implements AdHocLoadSettlementDataService {
    protected static final Logger logger = LoggerFactory.getLogger(AdHocLoadSettlementDataServiceImpl.class);

    @Override
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public AdHocLoadSettlementDTO findByRequestSequenceNumberAndCardNumber(Integer requestSequenceNumber, String cardNumber) {
        final String hsql =
                "select s from AdHocLoadSettlement s, Card c where c.id = s.cardId and s.requestSequenceNumber = ? and c.cardNumber = ?";
        List<AdHocLoadSettlement> results = dao.findByQuery(hsql, requestSequenceNumber, cardNumber);
        if (results.size() > 1) {
            String msg = String.format(PrivateError.MORE_THAN_ONE_RECORD.message(), "findByRequestSequenceNumberAndCardNumber",
                    "requestSequenceNumber=" + String.valueOf(requestSequenceNumber) + "; cardNumber=" + cardNumber);
            logger.error(msg);
            throw new DataServiceException(msg);
        }
        if (results.iterator().hasNext()) {
            return this.converter.convertEntityToDto(results.iterator().next());
        }
        return null;
    }

    @Override
    public AdHocLoadSettlement getNewEntity() {
        return new AdHocLoadSettlement();
    }

    @Autowired
    public void setDao(AdHocLoadSettlementDAO dao) {
        this.dao = dao;
    }

    @Autowired
    public void setConverter(AdHocLoadSettlementConverterImpl converter) {
        this.converter = converter;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AdHocLoadSettlementDTO> findByOrderId(Long orderId) {
        AdHocLoadSettlement adHocLoadSettlement = new AdHocLoadSettlement();
        adHocLoadSettlement.setOrderId(orderId);
        List<AdHocLoadSettlement> adHocSettlements = dao.findByExample(adHocLoadSettlement);
        if (adHocSettlements.size() > 0) {
            return convert(adHocSettlements);
        } else {
            return null;
        }
    }
}
