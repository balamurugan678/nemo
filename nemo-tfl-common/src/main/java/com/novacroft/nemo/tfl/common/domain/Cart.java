package com.novacroft.nemo.tfl.common.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.envers.Audited;

import com.novacroft.nemo.common.domain.AbstractBaseEntity;

/**
 * TfL cart domain definition
 */
@Audited
@Entity
@DiscriminatorColumn(name = "cartType", discriminatorType = DiscriminatorType.STRING)
public class Cart extends AbstractBaseEntity {
    private static final long serialVersionUID = -4737628704444041743L;

    protected Long webAccountId;
    protected Long customerId;
    protected Long cardId;
    protected Date dateOfRefund;
    protected Long approvalId;
    protected Set<Item> items = new HashSet<Item>();
    protected Boolean workFlowInFlight; 
    
    protected Long version = null;

    @SequenceGenerator(name = "CART_SEQ", sequenceName = "CART_SEQ", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CART_SEQ")
    @Column(name = "ID")
    @Override
    public Long getId() {
        return id;
    }
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="cartid", nullable = true)
    public Set<Item> getItems() {
    	return items;
    }
    
    public void setItems(Set<Item> items) {
	this.items = items;
    }
    
    public void addItem(Item item) {
	this.getItems().add(item);
    }

    public Long getWebAccountId() {
        return webAccountId;
    }

    public void setWebAccountId(Long webAccountId) {
        this.webAccountId = webAccountId;
    }

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

	public Date getDateOfRefund() {
        return dateOfRefund;
    }

    public void setDateOfRefund(Date dateOfRefund) {
        this.dateOfRefund = dateOfRefund;
    }
    
    @Transient
    public String getCartType(){
        DiscriminatorValue cartType = this.getClass().getAnnotation( DiscriminatorValue.class );
        return cartType == null ? null : cartType.value();
    }
    
    public Long getApprovalId() {
        return approvalId;
    }

    public void setApprovalId(Long approvalId) {
        this.approvalId = approvalId;
    }
    
    public Long getCustomerId() {
    	return customerId;
    }
    
    public void setCustomerId(Long customerId) {
    	this.customerId = customerId;
    }

    @Column(name="WORKFLOWIN_FLIGHT")
	public Boolean getWorkFlowInFlight() {
		return workFlowInFlight;
	}

	public void setWorkFlowInFlight(Boolean workFlowInFlight) {
		this.workFlowInFlight = workFlowInFlight;
	}

    @Version
    public Long getVersion() {
        return version;
    }

    private void setVersion(Long version) {
        this.version = version;
    }
    
}
