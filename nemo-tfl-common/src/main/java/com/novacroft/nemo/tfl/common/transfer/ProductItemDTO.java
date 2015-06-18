package com.novacroft.nemo.tfl.common.transfer;

import java.util.Date;

import com.novacroft.nemo.tfl.common.domain.Refund;

/**
 * TfL product item transfer implementation
 */

public class ProductItemDTO extends ItemDTO {
      
    private static final long serialVersionUID = -7169191660308444834L;
    
    protected Long productId;
    protected Date startDate;
    protected Date endDate;
    protected Date tradedDate;
    protected String reminderDate;
    protected Integer startZone;
    protected Integer endZone;
    protected String refundCalculationBasis;
    protected String duration;
    protected String productType;
    protected String magneticTicketNumber;
    protected Long backdatedRefundReasonId;
    protected String refundType;
    protected Date dateOfCanceAndSurrender;


    protected Boolean ticketUnused = Boolean.FALSE;
    protected Boolean ticketBackdated = Boolean.FALSE;
    private Boolean deceasedCustomer = Boolean.FALSE;
    protected Refund refund;
    protected Boolean backdatedWarning = Boolean.FALSE;
    
  
    protected Date dateOfRefund;

    public ProductItemDTO() {
        super();
    }

    public ProductItemDTO(Long id, Long cardId, Long cartId, Integer ticketPrice, Long productId, Date startDate, Date endDate, Integer startZone, Integer endZone, String reminderDate,
            String refundCalculationBasis) {
        this.id = id;
        this.cardId = cardId;
        this.cartId = cartId;
        this.price = ticketPrice;
        this.productId = productId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startZone = startZone;
        this.endZone = endZone;
        this.reminderDate = reminderDate;
        this.refundCalculationBasis = refundCalculationBasis;
    }

    public ProductItemDTO(Integer startZone, Integer endZone, Date startDate, Date endDate, String productType ) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.startZone = startZone;
        this.endZone = endZone;
        this.productType = productType;

    }

    public ProductItemDTO(Long id, Long cardId, Long cartId, Integer ticketPrice, Long productId, Date startDate, Date endDate, Integer startZone, Integer endZone, String reminderDate,
            String refundCalculationBasis, String magneticTicketNumber, Boolean ticketUnused, Boolean ticketBackdated,Long backdatedRefundReasonId, Boolean deceasedCustomer) {
        this.id = id;
        this.cardId = cardId;
        this.cartId = cartId;
        this.price = ticketPrice;
        this.productId = productId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startZone = startZone;
        this.endZone = endZone;
        this.reminderDate = reminderDate;
        this.refundCalculationBasis = refundCalculationBasis;
        this.magneticTicketNumber = magneticTicketNumber;
        this.ticketBackdated = ticketBackdated;
        this.ticketUnused = ticketUnused;
        this.backdatedRefundReasonId = backdatedRefundReasonId;
        this.deceasedCustomer = deceasedCustomer;
    }
    
    public ProductItemDTO(Long id, Long cardId, Integer ticketPrice, Long productId, Date startDate, Date endDate, Integer startZone, Integer endZone, String reminderDate,
            String refundCalculationBasis, String magneticTicketNumber, Boolean ticketUnused, Boolean ticketBackdated,Long backdatedRefundReasonId, Boolean deceasedCustomer, Date exchangedDate, Date dateOfRefund, Date dateOfCanceAndSurrender) {
        this.id = id;
        this.cardId = cardId;
        this.price = ticketPrice;
        this.productId = productId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startZone = startZone;
        this.endZone = endZone;
        this.reminderDate = reminderDate;
        this.refundCalculationBasis = refundCalculationBasis;
        this.magneticTicketNumber = magneticTicketNumber;
        this.ticketBackdated = ticketBackdated;
        this.ticketUnused = ticketUnused;
        this.backdatedRefundReasonId = backdatedRefundReasonId;
        this.deceasedCustomer = deceasedCustomer;
        this.tradedDate = exchangedDate;
        this.dateOfRefund = dateOfRefund;
        this.dateOfCanceAndSurrender = dateOfCanceAndSurrender;
    }
    
    public ProductItemDTO(Long id, Long cardId, Integer ticketPrice, Long productId, Date startDate, Date endDate, Integer startZone, Integer endZone, String reminderDate,
            String refundCalculationBasis, String magneticTicketNumber, Boolean ticketUnused, Boolean ticketBackdated,Long backdatedRefundReasonId, Boolean deceasedCustomer, Date exchangedDate, Date dateOfRefund, String refundType, Date dateOfCanceAndSurrender) {
        this.id = id;
        this.cardId = cardId;
        this.price = ticketPrice;
        this.productId = productId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startZone = startZone;
        this.endZone = endZone;
        this.reminderDate = reminderDate;
        this.refundCalculationBasis = refundCalculationBasis;
        this.magneticTicketNumber = magneticTicketNumber;
        this.ticketBackdated = ticketBackdated;
        this.ticketUnused = ticketUnused;
        this.backdatedRefundReasonId = backdatedRefundReasonId;
        this.deceasedCustomer = deceasedCustomer;
        this.tradedDate = exchangedDate;
        this.dateOfRefund = dateOfRefund;
        this.refundType = refundType;
        this.dateOfCanceAndSurrender = dateOfCanceAndSurrender;
    }
    
    public ProductItemDTO(Long id, Long cardId, Long cartId, Integer ticketPrice, Long productId, Date startDate, Date endDate, Integer startZone, Integer endZone, String reminderDate,
            String refundCalculationBasis, String magneticTicketNumber) {
        this.id = id;
        this.cardId = cardId;
        this.cartId = cartId;
        this.price = ticketPrice;
        this.productId = productId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startZone = startZone;
        this.endZone = endZone;
        this.reminderDate = reminderDate;
        this.refundCalculationBasis = refundCalculationBasis;
        this.magneticTicketNumber = magneticTicketNumber;
    }
    
    public ProductItemDTO(Long id, String name, Long cardId, Integer ticketPrice, Long productId, Date startDate, Date endDate, Integer startZone, Integer endZone,
            String reminderDate, String refundCalculationBasis, String magneticTicketNumber) {
        this.id = id;
        this.name = name;
        this.cardId = cardId;
        this.price = ticketPrice;
        this.productId = productId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startZone = startZone;
        this.endZone = endZone;
        this.reminderDate = reminderDate;
        this.refundCalculationBasis = refundCalculationBasis;
        this.magneticTicketNumber = magneticTicketNumber;
    }
    
    public ProductItemDTO(Long id, String name, Long cardId, Integer ticketPrice, Long productId, Date startDate, Date endDate, Integer startZone, Integer endZone,
            String reminderDate, String refundCalculationBasis, String magneticTicketNumber, String refundType) {
        this.id = id;
        this.name = name;
        this.cardId = cardId;
        this.price = ticketPrice;
        this.productId = productId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startZone = startZone;
        this.endZone = endZone;
        this.reminderDate = reminderDate;
        this.refundCalculationBasis = refundCalculationBasis;
        this.magneticTicketNumber = magneticTicketNumber;
        this.refundType = refundType;
    }
    
    public ProductItemDTO(Long id, Long cardId, Integer ticketPrice, Long productId, Date startDate, Date endDate, Integer startZone, Integer endZone, String reminderDate,
            String refundCalculationBasis, String magneticTicketNumber, Boolean ticketUnused, Boolean ticketBackdated,Long backdatedRefundReasonId, Boolean deceasedCustomer, Date exchangedDate, Date dateOfRefund, String refundType, Date dateOfCanceAndSurrender, String productType) {
        this.id = id;
        this.cardId = cardId;
        this.price = ticketPrice;
        this.productId = productId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startZone = startZone;
        this.endZone = endZone;
        this.reminderDate = reminderDate;
        this.refundCalculationBasis = refundCalculationBasis;
        this.magneticTicketNumber = magneticTicketNumber;
        this.ticketBackdated = ticketBackdated;
        this.ticketUnused = ticketUnused;
        this.backdatedRefundReasonId = backdatedRefundReasonId;
        this.deceasedCustomer = deceasedCustomer;
        this.tradedDate = exchangedDate;
        this.dateOfRefund = dateOfRefund;
        this.refundType = refundType;
        this.dateOfCanceAndSurrender = dateOfCanceAndSurrender;
        this.productType = productType;
    }
    
    public ProductItemDTO(String name, Integer price, Long cardId) {
	this.name = name;
	this.price = price;
	this.cardId = cardId;
    }
    
    public ProductItemDTO(String name, Date startDate, Integer startZone, Integer endZone, String refundCalculationBasis, Long cardId, String ticketType, Date endDate) {
	this.name = name;
	this.startDate = startDate;
	this.startZone = startZone;
	this.endZone = endZone;
	this.refundCalculationBasis = refundCalculationBasis;
	this.cardId = cardId;
	this.productType = ticketType;
	this.endDate = endDate;
    }
    
    public ProductItemDTO (Date startDate, Date endDate, String duration, Integer startZone, Integer endZone, String reminderDate) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.duration = duration;
        this.startZone = startZone;
        this.endZone = endZone;
        this.reminderDate = reminderDate;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getReminderDate() {
        return reminderDate;
    }

    public void setReminderDate(String reminderDate) {
        this.reminderDate = reminderDate;
    }

    public Integer getStartZone() {
        return startZone;
    }

    public void setStartZone(Integer startZone) {
        this.startZone = startZone;
    }

    public Integer getEndZone() {
        return endZone;
    }

    public void setEndZone(Integer endZone) {
        this.endZone = endZone;
    }



    public String getRefundCalculationBasis() {
        return refundCalculationBasis;
    }

    public void setRefundCalculationBasis(String refundCalculationBasis) {
        this.refundCalculationBasis = refundCalculationBasis;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getMagneticTicketNumber() {
        return magneticTicketNumber;
    }

    public void setMagneticTicketNumber(String magneticTicketNumber) {
        this.magneticTicketNumber = magneticTicketNumber;
    }

    public Boolean getTicketUnused() {
        return ticketUnused;
    }

    public Boolean getTicketBackdated() {
        return ticketBackdated;
    }

    public void setTicketUnused(Boolean ticketUnused) {
        this.ticketUnused = ticketUnused;
    }

    public void setTicketBackdated(Boolean ticketBackdated) {
        this.ticketBackdated = ticketBackdated;
    }
    
   
    public Boolean getDeceasedCustomer() {
        return deceasedCustomer;
    }

    public void setDeceasedCustomer(Boolean deceasedCustomer) {
        this.deceasedCustomer = deceasedCustomer;
    }
    
    public Long getBackdatedRefundReasonId() {
        return backdatedRefundReasonId;
    }

    public void setBackdatedRefundReasonId(Long backdatedRefundReasonId) {
        this.backdatedRefundReasonId = backdatedRefundReasonId;
    }

    public String getRefundType() {
		return refundType;
	}

	public void setRefundType(String refundType) {
		this.refundType = refundType;
	}

	public Date getTradedDate() {
        return tradedDate;
    }

    public void setTradedDate(Date tradedDate) {
        this.tradedDate = tradedDate;
    }

    public Refund getRefund() {
        return refund;
    }

    public void setRefund(Refund refund) {
        this.refund = refund;
    } 
    
    @Override
    public String getName() {
	return name;
    }
    
    public Boolean getBackdatedWarning() {
        return backdatedWarning;
    }

    public void setBackdatedWarning(Boolean backdatedWarning) {
        this.backdatedWarning = backdatedWarning;
    }
    

    public Date getDateOfRefund() {
        return dateOfRefund;
    }

    public void setDateOfRefund(Date dateOfRefund) {
        this.dateOfRefund = dateOfRefund;
    }

	public Date getDateOfCanceAndSurrender() {
		return dateOfCanceAndSurrender;
	}

	public void setDateOfCanceAndSurrender(Date dateOfCanceAndSurrender) {
		this.dateOfCanceAndSurrender = dateOfCanceAndSurrender;
	}
    
    
}
