package com.novacroft.nemo.tfl.common.transfer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.novacroft.nemo.common.transfer.CommonOrderDTO;
import com.novacroft.nemo.tfl.common.constant.HashCodeSeed;

/**
 * TfL Order implementation
 */
public class OrderDTO extends CommonOrderDTO {

    protected List<ItemDTO> orderItems;

    public OrderDTO() {
    }

    public OrderDTO(Long customerId, Date orderDate, Integer totalAmount, String status, Long stationId) {
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.status = status;
        this.stationId = stationId;
        this.orderItems = new ArrayList<ItemDTO>();
    }

    public OrderDTO(Long customerId, Date orderDate, Integer totalAmount, String status, Long stationId, Long approvalId) {
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.status = status;
        this.stationId = stationId;
        this.approvalId = approvalId;
        this.orderItems = new ArrayList<ItemDTO>();
    }

    public OrderDTO(Long customerId, Long approvalId, Date orderDate, Integer totalAmount, String status, Date refundDate) {
        this.customerId = customerId;
        this.approvalId = approvalId;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.status = status;
        this.refundDate = refundDate;
    }
    
    public OrderDTO(Long customerId, Date orderDate, Integer totalAmount, String status) {
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.status = status;
        this.orderItems = new ArrayList<ItemDTO>();
    }
    
    public OrderDTO(Date orderDate, Integer totalAmount, String status) {
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.status = status;
        this.orderItems = new ArrayList<ItemDTO>();
    }
    
    public OrderDTO(Long id, String createdUserId, Date createdDateTime, String modifiedUserId, Date modifiedDateTime,
                    Long orderNumber, Date orderDate, Integer totalAmount, String status, Long customerId, Long stationId) {
        this.id = id;
        this.createdUserId = createdUserId;
        this.createdDateTime = createdDateTime;
        this.modifiedUserId = modifiedUserId;
        this.modifiedDateTime = modifiedDateTime;
        this.orderNumber = orderNumber;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.status = status;
        this.customerId = customerId;
        this.stationId = stationId;
        this.orderItems = new ArrayList<ItemDTO>();
    }

    public OrderDTO(Long id, String createdUserId, Date createdDateTime, String modifiedUserId, Date modifiedDateTime, Long orderNumber,
                    Date orderDate, Integer totalAmount, String status, Long customerId, Long stationId, Long approvalId) {
        this.id = id;
        this.createdUserId = createdUserId;
        this.createdDateTime = createdDateTime;
        this.modifiedUserId = modifiedUserId;
        this.modifiedDateTime = modifiedDateTime;
        this.orderNumber = orderNumber;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.status = status;
        this.customerId = customerId;
        this.stationId = stationId;
        this.approvalId = approvalId;
        this.orderItems = new ArrayList<ItemDTO>();
    }

    public List<ItemDTO> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<ItemDTO> orderItems) {
        this.orderItems = orderItems;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        OrderDTO that = (OrderDTO) object;

        return new EqualsBuilder().append(orderNumber, that.orderNumber).append(orderDate, that.orderDate).append(totalAmount, that.totalAmount)
                        .append(status, that.status).append(customerId, that.customerId).append(stationId, that.stationId)
                        .append(approvalId, that.approvalId).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(HashCodeSeed.ORDER_DTO.initialiser(), HashCodeSeed.ORDER_DTO.multiplier()).append(orderNumber).append(orderDate)
                        .append(totalAmount).append(status).append(customerId).append(stationId).append(approvalId).toHashCode();
    }
    
    
}
