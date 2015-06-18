package com.novacroft.nemo.tfl.services.transfer;

import java.util.Date;
import java.util.List;

public class Cart extends AbstractBase{

	private Long id;
	private Long cardId;
	private Date dateOfRefund;
	private String formattedDateOfRefund;
	private String cartType;
	private Long approvalId;
	private Long customerId;
	private String passengerType; 
    private String discountType;
    private String concessionEndDate;

	private List<Item> cartItems;
    private Long version;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getFormattedDateOfRefund() {
		return formattedDateOfRefund;
	}

	public void setFormattedDateOfRefund(String formattedDateOfRefund) {
		this.formattedDateOfRefund = formattedDateOfRefund;
	}

	public String getCartType() {
		return cartType;
	}

	public void setCartType(String cartType) {
		this.cartType = cartType;
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

	public List<Item> getCartItems() {
		return cartItems;
	}

	public void setCartItems(List<Item> cartItems) {
		this.cartItems = cartItems;
	}
	
	public String getPassengerType() {
        return passengerType;
    }

    public void setPassengerType(String passengerType) {
        this.passengerType = passengerType;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public String getConcessionEndDate() {
        return concessionEndDate;
    }

    public void setConcessionEndDate(String concessionEndDate) {
        this.concessionEndDate = concessionEndDate;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

}
