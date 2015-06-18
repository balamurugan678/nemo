package com.novacroft.nemo.tfl.common.data_service.impl;

import static com.novacroft.nemo.common.constant.DateConstant.SHORT_DATE_PATTERN;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.DateType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.novacroft.nemo.common.converter.DtoEntityConverter;
import com.novacroft.nemo.common.data_service.BaseDataServiceImpl;
import com.novacroft.nemo.common.domain.Address;
import com.novacroft.nemo.common.transfer.AddressDTO;
import com.novacroft.nemo.tfl.common.constant.OrderStatus;
import com.novacroft.nemo.tfl.common.converter.impl.OrderConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.OrderDAO;
import com.novacroft.nemo.tfl.common.data_service.LocationDataService;
import com.novacroft.nemo.tfl.common.data_service.OrderDataService;
import com.novacroft.nemo.tfl.common.domain.Order;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;
import com.novacroft.nemo.tfl.common.transfer.LocationDTO;
import com.novacroft.nemo.tfl.common.transfer.OrderDTO;
import com.novacroft.nemo.tfl.common.transfer.RefundCeilingRuleLimitTally;
import com.novacroft.nemo.tfl.common.transfer.RefundOrderItemDTO;

/**
 * Order data service implementation
 */
@Service(value = "orderDataService")
@Transactional(readOnly = true)
public class OrderDataServiceImpl extends BaseDataServiceImpl<Order, OrderDTO> implements OrderDataService {

    @Autowired
    protected DtoEntityConverter<Address, AddressDTO> addressConverter;

    @Autowired
    protected LocationDataService locationDataService;

    @Autowired
    public void setDao(OrderDAO dao) {
        this.dao = dao;
    }

    @Autowired
    public void setConverter(OrderConverterImpl converter) {
        this.converter = converter;
    }

    @Override
    public Order getNewEntity() {
        return new Order();
    }

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        orderDTO.setOrderNumber(((OrderDAO) dao).getNextOrderNumber());
        return createOrUpdate(orderDTO);
    }

    @Override
    @Transactional
    public OrderDTO createOrUpdate(OrderDTO orderDTO) {
        if (CollectionUtils.isNotEmpty(orderDTO.getOrderItems())) {
            for (ItemDTO itemDTO : orderDTO.getOrderItems()) {
                if (null == itemDTO.getId()) {
                    itemDTO.setCreatedDateTime(new Date());
                    itemDTO.setCreatedUserId(this.nemoUserContext.getUserName());
                } else {
                    itemDTO.setModifiedDateTime(new Date());
                    itemDTO.setModifiedUserId(this.nemoUserContext.getUserName());
                }
            }
        }
        return super.createOrUpdate(orderDTO);
    }

    @Override
    public void flush() {
        dao.flush();
    }

    @Override
    public OrderDTO findByOrderNumber(Long orderNumber) {
        Order order = new Order();
        order.setOrderNumber(orderNumber);
        List<Order> orders = dao.findByExample(order);
        if (orders.size() > 0) {
            List<OrderDTO> ordersDTO = convert(orders.get(0));
            return ordersDTO.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<OrderDTO> findByCustomerId(Long customerId) {
        Order order = new Order();
        order.setCustomerId(customerId);
        List<Order> orders = dao.findByExampleWithOrderBy(order, org.hibernate.criterion.Order.desc("id"));
        return convert(orders);
    }

    @Override
    public List<OrderDTO> findByCustomerIdAndDuration(Long customerId, DateTime thresholdTime, DateTime now) {
        Order order = new Order();
        order.setCustomerId(customerId);
        final String hsql = "from Order co where co.customerId = ? AND co.orderDate >=  ? AND co.orderDate < ? ";
        return convert(dao.findByQuery(hsql, customerId, thresholdTime.toDate(), now.toDate()));
    }

    @Override
    public List<OrderDTO> findByAddressIdAndDuration(Long addressId, DateTime thresholdTime, DateTime now) {
        final String hsql = "select co from Customer cu, Order co where cu.id = co.customerId " +
                "AND cu.addressId = ? AND co.orderDate >= ? AND co.orderDate < ?";
        return convert(dao.findByQuery(hsql, addressId, thresholdTime.toDate(), now.toDate()));

    }

    @Override
    public List<OrderDTO> findByRefundedCardNumberPriceAndDate(String cardNumber, Long price, DateTime date) {
        DateTimeFormatter dateFormat = DateTimeFormat.forPattern(SHORT_DATE_PATTERN);
        final String hsql =
                "select co from Order co, Settlement s, Card ca where ca.cardNumber = ? AND ca.id = s.cardId AND co.id = s" +
                        ".orderId" +
                        " AND co.totalAmount = ? AND trunc(co.refundDate) = TO_DATE(?,'DD-MM-YY')";
        return convert(dao.findByQuery(hsql, cardNumber, price.intValue(), dateFormat.print(date)));
    }

    @Override
    public List<OrderDTO> findByCustomerCardNumberPriceAndDate(String cardNumber, Long customerId, Long price, DateTime date) {
        DateTimeFormatter dateFormat = DateTimeFormat.forPattern(SHORT_DATE_PATTERN);
        final String hsql =
                "select co from  Customer cu , Order co, Card ca where cu.id = co.customerId And ca.customerId = co" +
                        ".customerId " +
                        " AND cu.id  = ? AND ca.cardNumber  = ? AND co.totalAmount = ? AND trunc(co.refundDate) = TO_DATE(?," +
                        "'DD-MM-YY')";
        return convert(dao.findByQuery(hsql, customerId, cardNumber, price.intValue(), dateFormat.print(date)));
    }

    @Override
    public List<OrderDTO> findByExample(Order exampleEntity) {
        return convert(dao.findByExample(exampleEntity));
    }

    @Override
    public List<OrderDTO> findAllRefunds() {
        final String hsql = "select co from Order co where co.approvalId is NOT NULL";
        Query query = dao.createQuery(hsql);
        return convert(query.list());
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<RefundCeilingRuleLimitTally> findAllGoodwillsForCustomerInPast12Months(Long customerId) {

        List<RefundCeilingRuleLimitTally> result = new ArrayList<RefundCeilingRuleLimitTally>();

        String sql = "SELECT  Count(i.ITEMTYPE),i.ITEMTYPE, 'RCL' as Dept " + "FROM ITEM i, CUSTOMERORDER o, SETTLEMENT s " +
                "WHERE o.ID = i.CUSTOMERORDERID " + "AND o.ID = s.CUSTOMERORDERID " + "AND o.customerid = ? " +
                "AND i.ITEMTYPE= 'GoodwillPayment' " +
                "AND s.SETTLEMENTDATE >= ADD_MONTHS(to_date(SysDate, 'dd-mon-yy'), -12) " + "GROUP by i.ITEMTYPE";

        Query query = dao.createSQLQuery(sql);
        query.setParameter(0, customerId);

        List<Object[]> rows = query.list();
        for (Object[] row : rows) {
            RefundCeilingRuleLimitTally tally =
                    new RefundCeilingRuleLimitTally((String) row[1], (BigDecimal) row[0], (String) row[2]);
            result.add(tally);
        }
        return result;

    }

    @SuppressWarnings("unchecked")
    @Override
    public List<RefundCeilingRuleLimitTally> findAllRefundsForCustomerInPast12Months(Long customerId, String refundType) {

        List<RefundCeilingRuleLimitTally> result = new ArrayList<RefundCeilingRuleLimitTally>();

        String sql = "Select Count(i.REFUNDTYPE), REPLACE(i.REFUNDTYPE, 'Refund',''), 'RCL' as Dept " +
                "FROM ITEM i, CUSTOMERORDER o, SETTLEMENT s " + "WHERE o.ID = i.CUSTOMERORDERID " +
                "AND o.ID = s.CUSTOMERORDERID " + "AND i.REFUNDTYPE is Not null " + "AND INSTR(UPPER(i.REFUNDTYPE), ? ) > 0 " +
                "AND o.customerid = ? " + "AND s.SETTLEMENTDATE >= ADD_MONTHS(to_date(SysDate, 'dd-mon-yy'), -12) " +
                "GROUP by i.REFUNDTYPE ";

        Query query = dao.createSQLQuery(sql);
        query.setParameter(0, refundType.toUpperCase());
        query.setParameter(1, customerId);

        List<Object[]> rows = query.list();
        for (Object[] row : rows) {
            RefundCeilingRuleLimitTally tally =
                    new RefundCeilingRuleLimitTally((String) row[1], (BigDecimal) row[0], (String) row[2]);
            result.add(tally);
        }
        return result;

    }

    @Override
    @SuppressWarnings("unchecked")
    public List<RefundOrderItemDTO> findAllRefundsForCustomer(Long customerId) {
        final String sql = "SELECT o.ID \"customerOrderId\", o.ORDERNUMBER \"orderNumber\", ca.CARDNUMBER \"oysterCardNumber\",o.REFUNDDATE \"dateOfRefund\" from CUSTOMERORDER o "
                        + "LEFT OUTER JOIN CARD ca "
                        + "ON o.CARDID = ca.ID "
                        + "WHERE o.CUSTOMERID = :customerId AND o.TOTALAMOUNT < 0 "
                        + "AND EXISTS (SELECT s.ID FROM Settlement s where o.id = s.CUSTOMERORDERID AND s.SETTLEMENTMETHOD != 'WebAccountCredit') "
                        + "ORDER BY  o.ORDERNUMBER desc";
        SQLQuery query = (SQLQuery) dao.createSQLQuery(sql);
        query.addScalar("customerOrderId", new LongType());
        query.addScalar("orderNumber", new LongType());
        query.addScalar("oysterCardNumber", new StringType());
        query.addScalar("dateOfRefund", new DateType());
        query.setResultTransformer(Transformers.aliasToBean(RefundOrderItemDTO.class));
        query.setParameter("customerId", customerId);
        return query.list();
    }

    @Override
    public RefundOrderItemDTO findRefundDetailForItemAndCustomer(Long refundId, Long customerId) {

        final String hsql =
                "select new com.novacroft.nemo.tfl.common.transfer.RefundOrderItemDTO(o.id, o.orderNumber, o.status, ca" +
                        ".cardNumber, o.refundDate, cu.firstName , cu.lastName, o.stationId, o.totalAmount, ad, s.class) " +
                        " from Order o, Card ca, Customer cu, Address ad, Settlement s " +
                        " where o.id= ? AND o.customerId = ? AND  o.totalAmount < 0 AND o.customerId = ca.customerId AND  o" +
                        ".customerId = cu.id AND ca.id = o.cardId " +
                        " AND cu.addressId = ad.id AND o.id = s.orderId";

        List refunds = dao.findByQuery(hsql, refundId, customerId);
        final RefundOrderItemDTO refundOrder = (RefundOrderItemDTO) (!refunds.isEmpty() ? refunds.get(0) : null);
        if (null != refundOrder) {
            refundOrder.setAddressDTO(addressConverter.convertEntityToDto(refundOrder.getAddress()));

            if (refundOrder.getStationId() != null) {
                LocationDTO locationDTO = locationDataService.getActiveLocationById(refundOrder.getStationId().intValue());
                if (locationDTO != null) {
                    refundOrder.setStationName(locationDTO.getName());
                }
            }
        }
        return refundOrder;
    }

    @Override
    public List<OrderDTO> findRefundByBACSReferenceNumber(Long bacsReference) {
        final String hsql =
                "select co from Order co, Settlement s where co.id = s.orderId AND s.paymentReference = ? AND co.approvalId " +
                        "is NOT NULL";
        return convert(dao.findByQuery(hsql, bacsReference));
    }

    @Override
    public List<OrderDTO> findRefundByChequeSerialNumber(Long chequeSerialNumber) {
        final String hsql =
                "select co from Order co, Settlement s where co.id = s.orderId AND s.chequeSerialNumber = ? AND co.approvalId" +
                        " is NOT NULL";
        return convert(dao.findByQuery(hsql, chequeSerialNumber));
    }

    @Override
    public List<OrderDTO> findRefundsForCustomer(Long id) {
        final String hsql = "select co from Order co where co.customerId = ? AND co.approvalId is NOT NULL";
        return convert(dao.findByQuery(hsql, id));
    }

    @Override
    public List<OrderDTO> getOrdersByCaseNumber(String caseNumber) {
        Order exampleOrder = new Order();
        exampleOrder.setApprovalId(Long.valueOf(caseNumber));
        return findByExample(exampleOrder);
    }

    @Override
    public OrderDTO findByIdAndCustomerId(Long id, Long customerId) {
        String hsql = "from Order order where order.customerId = ? and order.id = ?";
        Order order = dao.findByQueryUniqueResult(hsql, customerId, id);
        if (order != null) {
            return converter.convertEntityToDto(order);
        }
        return null;
    }

    @Override
    public Long getExternalIdFromInternalId(Long internalId) {
        return dao.findById(internalId).getExternalId();
    }

    @Override
    public Long getNumberOfOrdersInQueue(OrderStatus orderStatus) {
        String hsql = "select count(*) from Order order where order.status = :status";
        final Map<String, Object> namedParameterMap = new HashMap<>();
        namedParameterMap.put("status", orderStatus.code());
        return dao.getNumberOfResultsFromQuery(hsql, namedParameterMap);
    }
    
    @Override
    public List<OrderDTO> findByFulfilmentQueue(String fulfilmentQueue) {
        Order exampleOrder = new Order();
        exampleOrder.setStatus(fulfilmentQueue);
        List<Order> orders = dao.findByExampleWithSoonestOrderBy(exampleOrder, fulfilmentQueue);
        return convert(orders);
    }

    @Override
    public RefundCeilingRuleLimitTally findAllOverlapRefundForCustomerInPast12Months(Long customerId) {
        String sql = "Select count(i.id), 'OVERLAP' AS name, 'RCL' AS Dept " +
                "FROM ITEM i, CUSTOMERORDER o, SETTLEMENT s " + 
                "WHERE o.id = i.customerOrderId AND o.id = s.customerOrderId " +
                "AND i.ticketOverlapped = 1 AND i.relatedItemID is not null " +
                "AND o.customerid = ? " + "AND s.SETTLEMENTDATE >= ADD_MONTHS(to_date(SysDate, 'dd-mon-yy'), -12) ";

        
        Query query = dao.createSQLQuery(sql);
        query.setParameter(0, customerId);
        
        Object[] row = (Object[]) query.list().get(0);
        
        return new RefundCeilingRuleLimitTally((String) row[1], (BigDecimal) row[0], (String) row[2]);
    }
}
