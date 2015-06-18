package com.novacroft.nemo.tfl.common.domain;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;

import org.hibernate.envers.Audited;

import com.novacroft.nemo.common.domain.AbstractBaseEntity;

/**
 * TfL Item domain definition
 */
@Audited
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "itemType", discriminatorType = DiscriminatorType.STRING)
public class Item extends AbstractBaseEntity {
    private static final long serialVersionUID = -4737628704444041743L;

    protected Long cardId;
    protected Long orderId;
    protected Integer price;
    protected Long customerId;
    protected Boolean ticketOverlapped = Boolean.FALSE;

    @SequenceGenerator(name = "ITEM_SEQ", sequenceName = "ITEM_SEQ", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ITEM_SEQ")
    @Column(name = "ID")
    @Override
    public Long getId() {
        return id;
    }

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    @Column(name = "CUSTOMERORDERID")
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long cId) {
        this.customerId = cId;
    }

    public Boolean isTicketOverlapped() {
        return ticketOverlapped;
    }

    public void setTicketOverlapped(Boolean ticketOverlapped) {
        this.ticketOverlapped = ticketOverlapped;
    }
    
}
