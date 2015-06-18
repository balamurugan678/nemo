package com.novacroft.nemo.tfl.services.transfer;

import java.util.Date;


public class Item extends AbstractBase{

    private Long id;
    private Integer price;
    private String name;
    private Date startDate; 
    private String formattedStartDate; 
    private Date endDate; 
    private String formattedEndDate; 
    private Date tradedDate; 
    private String formattedTradedDate; 
    private String reminderDate;
    private Integer startZone;
    private Integer endZone;
    private String duration;
    private String productType;
    private Integer autoTopUpAmount;
    private Long prePaidProductReference;
    private String activationWindowStartDate; 
    private String activationWindowExpiryDate; 
    
    
    
    public Item() {
        
    }
    
    public Item(Integer startZone, Integer endZone, String duration, String productType) {
        this.startZone = startZone;
        this.endZone = endZone;
        this.duration = duration;
        this.productType = productType;
    }
    
    public Item(Integer startZone, Integer endZone, String duration) {
        this.startZone = startZone;
        this.endZone = endZone;
        this.duration = duration;
    }
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Date getTradedDate() {
        return tradedDate;
    }

    public void setTradedDate(Date tradedDate) {
        this.tradedDate = tradedDate;
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

    public Integer getAutoTopUpAmount() {
        return autoTopUpAmount;
    }

    public void setAutoTopUpAmount(Integer autoTopUpAmount) {
        this.autoTopUpAmount = autoTopUpAmount;
    }

    public String getFormattedStartDate() {
        return formattedStartDate;
    }

    public void setFormattedStartDate(String formattedStartDate) {
        this.formattedStartDate = formattedStartDate;
    }

    public String getFormattedEndDate() {
        return formattedEndDate;
    }

    public void setFormattedEndDate(String formattedEndDate) {
        this.formattedEndDate = formattedEndDate;
    }

    public String getFormattedTradedDate() {
        return formattedTradedDate;
    }

    public void setFormattedTradedDate(String formattedTradedDate) {
        this.formattedTradedDate = formattedTradedDate;
    }

	public Long getPrePaidProductReference() {
		return prePaidProductReference;
	}

	public void setPrePaidProductReference(Long prePaidProductReference) {
		this.prePaidProductReference = prePaidProductReference;
	}

	public String getActivationWindowStartDate() {
		return activationWindowStartDate;
	}

	public void setActivationWindowStartDate(String activationWindowStartDate) {
		this.activationWindowStartDate = activationWindowStartDate;
	}

	public String getActivationWindowExpiryDate() {
		return activationWindowExpiryDate;
	}

	public void setActivationWindowExpiryDate(String activationWindowExpiryDate) {
		this.activationWindowExpiryDate = activationWindowExpiryDate;
	}

}
