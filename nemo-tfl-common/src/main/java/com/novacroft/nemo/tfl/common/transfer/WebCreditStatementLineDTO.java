package com.novacroft.nemo.tfl.common.transfer;

import com.novacroft.nemo.tfl.common.constant.HashCodeSeed;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;

/**
 * Transfer class for web account credit statement lines.
 */
public class WebCreditStatementLineDTO {
    protected Long settlementId;
    protected String settlementStatus;
    protected Date settlementDate;
    protected Integer settlementAmount;
    protected Long orderId;
    protected Long orderNumber;
    protected Date orderDate;
    protected Integer orderTotalAmount;
    protected String orderStatus;

    public WebCreditStatementLineDTO() {
    }

    public WebCreditStatementLineDTO(Long settlementId, String settlementStatus, Date settlementDate,
                                            Integer settlementAmount, Long orderId, Long orderNumber, Date orderDate,
                                            Integer orderTotalAmount, String orderStatus) {
        this.settlementId = settlementId;
        this.settlementStatus = settlementStatus;
        this.settlementDate = settlementDate;
        this.settlementAmount = settlementAmount;
        this.orderId = orderId;
        this.orderNumber = orderNumber;
        this.orderDate = orderDate;
        this.orderTotalAmount = orderTotalAmount;
        this.orderStatus = orderStatus;
    }

    public Long getSettlementId() {
        return settlementId;
    }

    public void setSettlementId(Long settlementId) {
        this.settlementId = settlementId;
    }

    public String getSettlementStatus() {
        return settlementStatus;
    }

    public void setSettlementStatus(String settlementStatus) {
        this.settlementStatus = settlementStatus;
    }

    public Date getSettlementDate() {
        return settlementDate;
    }

    public void setSettlementDate(Date settlementDate) {
        this.settlementDate = settlementDate;
    }

    public Integer getSettlementAmount() {
        return settlementAmount;
    }

    public void setSettlementAmount(Integer settlementAmount) {
        this.settlementAmount = settlementAmount;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Long orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Integer getOrderTotalAmount() {
        return orderTotalAmount;
    }

    public void setOrderTotalAmount(Integer orderTotalAmount) {
        this.orderTotalAmount = orderTotalAmount;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        WebCreditStatementLineDTO that = (WebCreditStatementLineDTO) object;

        return new EqualsBuilder().append(settlementId, that.settlementId).append(settlementStatus, that.settlementStatus)
                .append(settlementDate, that.settlementDate).append(settlementAmount, that.settlementAmount)
                .append(orderId, that.orderId).append(orderNumber, that.orderNumber).append(orderDate, that.orderDate)
                .append(orderTotalAmount, that.orderTotalAmount).append(orderStatus, that.orderStatus).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(HashCodeSeed.WEB_ACCOUNT_CREDIT_STATEMENT_LINE_DTO.initialiser(),
                HashCodeSeed.WEB_ACCOUNT_CREDIT_STATEMENT_LINE_DTO.multiplier()).append(settlementId).append(settlementStatus)
                .append(settlementDate).append(settlementAmount).append(orderId).append(orderNumber).append(orderDate)
                .append(orderTotalAmount).append(orderStatus).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("settlementId", settlementId).append("settlementStatus", settlementStatus)
                .append("settlementDate", settlementDate).append("settlementAmount", settlementAmount)
                .append("orderId", orderId).append("orderNumber", orderNumber).append("orderDate", orderDate)
                .append("orderTotalAmount", orderTotalAmount).append("orderStatus", orderStatus).toString();
    }
}
