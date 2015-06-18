package com.novacroft.nemo.tfl.common.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import com.novacroft.nemo.common.domain.CommonOrder;

/**
 * TfL order implementation
 */
@Audited
@Entity
@Table(name = "CUSTOMERORDER")
public class Order extends CommonOrder {

    protected Set<Item> items = new HashSet<Item>();

    @SequenceGenerator(name = "CUSTOMERORDER_SEQ", sequenceName = "CUSTOMERORDER_SEQ", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CUSTOMERORDER_SEQ")
    @Column(name = "ID")
    @Override
    public Long getId() {
        return id;
    }

    @OneToMany(cascade = CascadeType.ALL, targetEntity=Item.class, orphanRemoval = true)
    @JoinColumn(name="customerorderid", nullable = true)
    @OrderBy("startDate")
    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }

    public void addItem(Item item) {
        this.getItems().add(item);
    }

    public Order() {

    }

    public Order(Long id, String createdUserId, Date createdDateTime, String modifiedUserId, Date modifiedDateTime, Long orderNumber, Date orderDate,
                    Integer totalAmount, String status, Long customerId, Long stationId, Long approvalId, Date refundDate) {
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
        this.refundDate = refundDate;
    }
}
