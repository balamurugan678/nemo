package com.novacroft.nemo.tfl.common.data_service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.novacroft.nemo.common.data_service.BaseDataServiceImpl;
import com.novacroft.nemo.tfl.common.constant.OrderStatus;
import com.novacroft.nemo.tfl.common.constant.SettlementStatus;
import com.novacroft.nemo.tfl.common.converter.WebCreditStatementLineConverter;
import com.novacroft.nemo.tfl.common.converter.impl.WebCreditSettlementConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.WebAccountCreditSettlementDAO;
import com.novacroft.nemo.tfl.common.data_service.WebCreditSettlementDataService;
import com.novacroft.nemo.tfl.common.domain.WebAccountCreditSettlement;
import com.novacroft.nemo.tfl.common.transfer.WebCreditSettlementDTO;
import com.novacroft.nemo.tfl.common.transfer.WebCreditStatementLineDTO;

/**
 * Settlement (by Web Account Credit) data service implementation
 */
@Service("webCreditSettlementDataService")
@Transactional(readOnly = true)
public class WebCreditSettlementDataServiceImpl extends BaseDataServiceImpl<WebAccountCreditSettlement, WebCreditSettlementDTO>
        implements WebCreditSettlementDataService {

    @Autowired
    protected WebCreditStatementLineConverter webAccountCreditStatementLineConverter;

    @Override
    public WebAccountCreditSettlement getNewEntity() {
        return new WebAccountCreditSettlement();
    }

    @Override
    public List<WebCreditStatementLineDTO> findByCustomerId(Long customerId) {
        final String hsql = "select s.id, s.status, s.settlementDate, s.amount," +
                " o.id, o.orderNumber, o.orderDate, o.totalAmount, o.status" +
                " from WebAccountCreditSettlement s, Order o" +
                " where o.id = s.orderId and o.customerId = ?" +
                " and o.status = ?" +
                " order by s.settlementDate desc";
        List statementLines = dao.findByQuery(hsql, customerId, OrderStatus.PAID.code());
        return statementLines == null ? Collections.<WebCreditStatementLineDTO>emptyList() :
                this.webAccountCreditStatementLineConverter.convertEntityListToDtoList(statementLines);
    }

    @Override
    public Integer getBalance(Long customerId) {
        final String hsql = "select sum(s.amount)" +
                " from WebAccountCreditSettlement s, Order o" +
                " where o.id = s.orderId and o.customerId = :customerId and s.status = :status";
        Object result = dao.createQuery(hsql)
                        .setLong("customerId", customerId)
                        .setParameter("status", SettlementStatus.COMPLETE.code()).uniqueResult();
        return (result != null) ? ((Long) result).intValue() : 0;
    }

    @Override
    public List<WebCreditSettlementDTO> findByOrderId(Long orderId) {
        final String hsql = " from WebAccountCreditSettlement s" +
                " where s.orderId = ?";
        return convert(dao.findByQuery(hsql, orderId));
    }

    @Autowired
    public void setDao(WebAccountCreditSettlementDAO dao) {
        this.dao = dao;
    }

    @Autowired
    public void setConverter(WebCreditSettlementConverterImpl converter) {
        this.converter = converter;
    }
}
