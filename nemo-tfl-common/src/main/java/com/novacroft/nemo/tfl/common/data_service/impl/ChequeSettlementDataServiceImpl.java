package com.novacroft.nemo.tfl.common.data_service.impl;

import com.novacroft.nemo.common.data_service.BaseDataServiceImpl;
import com.novacroft.nemo.tfl.common.converter.impl.ChequeSettlementConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.ChequeSettlementDAO;
import com.novacroft.nemo.tfl.common.data_service.ChequeSettlementDataService;
import com.novacroft.nemo.tfl.common.domain.ChequeSettlement;
import com.novacroft.nemo.tfl.common.transfer.financial_services_centre.ChequeSettlementDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

import static org.apache.commons.collections.CollectionUtils.isNotEmpty;

/**
 * Cheque settlement data service
 */
@Service("chequeSettlementDataService")
@Transactional
public class ChequeSettlementDataServiceImpl extends BaseDataServiceImpl<ChequeSettlement, ChequeSettlementDTO>
        implements ChequeSettlementDataService {
    protected static final String FIND_BY_ORDER_NUMBER_HSQL =
            "select s from ChequeSettlement s, Order o where s.orderId = o.id and o.orderNumber = ?";
    protected static final String FIND_BY_SETTLEMENT_NUMBER_NUMBER_HSQL =
            "select s from ChequeSettlement s where s.settlementNumber = ?";

    @Override
    @SuppressWarnings("unchecked")
    public List<ChequeSettlementDTO> findByStatus(String status) {
        ChequeSettlement exampleChequeSettlement = new ChequeSettlement();
        exampleChequeSettlement.setStatus(status);
        List<ChequeSettlement> chequeSettlements = this.dao.findByExample(exampleChequeSettlement);
        return isNotEmpty(chequeSettlements) ? convert(chequeSettlements) : Collections.EMPTY_LIST;
    }

    @Override
    public List<ChequeSettlementDTO> findAllByOrderNumber(Long orderNumber) {
        return convert(dao.findByQuery(FIND_BY_ORDER_NUMBER_HSQL, orderNumber));
    }
   
    

    @Override
    public ChequeSettlementDTO findByOrderNumber(Long orderNumber) {
        ChequeSettlement chequeSettlement = this.dao.findByQueryUniqueResult(FIND_BY_ORDER_NUMBER_HSQL, orderNumber);
        return chequeSettlement != null ? this.converter.convertEntityToDto(chequeSettlement) : null;
    }

    @Override
    public ChequeSettlementDTO findByChequeSerialNumber(Long chequeSerialNumber) {
        ChequeSettlement exampleChequeSettlement = new ChequeSettlement();
        exampleChequeSettlement.setChequeSerialNumber(chequeSerialNumber);
        ChequeSettlement chequeSettlement = this.dao.findByExampleUniqueResult(exampleChequeSettlement);
        return chequeSettlement != null ? this.converter.convertEntityToDto(chequeSettlement) : null;
    }

    @Autowired
    public void setDao(ChequeSettlementDAO dao) {
        this.dao = dao;
    }

    @Autowired
    public void setConverter(ChequeSettlementConverterImpl converter) {
        this.converter = converter;
    }

    @Override
    public ChequeSettlement getNewEntity() {
        return new ChequeSettlement();
    }

    @Override
	@Transactional(readOnly=true)
	public ChequeSettlementDTO findBySettlementNumber(Long settlementNumber) {
		final ChequeSettlement chequeSettlement =  dao.findByQueryUniqueResult(FIND_BY_SETTLEMENT_NUMBER_NUMBER_HSQL, settlementNumber);
		 return chequeSettlement != null ? this.converter.convertEntityToDto(chequeSettlement) : null;
	}
}
